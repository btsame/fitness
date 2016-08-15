package com.dkjs.fitness.mine;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.dkjs.fitness.R;
import com.dkjs.fitness.adapter.PicSelectAdapter;
import com.dkjs.fitness.biz.FTActivityBiz;
import com.dkjs.fitness.biz.IFTActivityBiz;
import com.dkjs.fitness.citydateselector.picker.AddressPicker;
import com.dkjs.fitness.citydateselector.picker.DateTimePicker;
import com.dkjs.fitness.citydateselector.utils.AssetsUtils;
import com.dkjs.fitness.comm.AppConfig;
import com.dkjs.fitness.comm.FitnessActivity;
import com.dkjs.fitness.comm.GlobalUserManager;
import com.dkjs.fitness.domain.FTActivity;
import com.dkjs.fitness.domain.User;
import com.dkjs.fitness.util.CameraProxy;
import com.dkjs.fitness.util.CameraResult;
import com.dkjs.fitness.util.ToastUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.maxleap.MLUser;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnItemClickListener;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/7/18.
 */
public class ActivityCreateActivity extends FitnessActivity implements View.OnClickListener {

    public static final int REQUEST_CAMERA_PERMISSION = 0x11;

    public static final int ACTIVITY_CREATE = 1;
    public static final int ACTIVITY_SHOW = 2;
    public static final String PARAM_SHOW_STYLE = "showSytle";
    public static final String PARAM_FTACTIVITY = "ftActivity";

    @Bind(R.id.ll_upload_pic)
    LinearLayout mUploadPicIV;
//    @Bind(R.id.ll_upload_video)
//    LinearLayout mUploadVideoIV;
    @Bind(R.id.sdv_show_act_pic)
    SimpleDraweeView mPicSDV;
    @Bind(R.id.tv_publish_activity)
    TextView mPublishActTV;
    @Bind(R.id.btn_publish_activity)
    Button mPublishActBtn;
    @Bind(R.id.et_act_subject)
    EditText mSubjextET;
    @Bind(R.id.et_act_totalnum)
    EditText mTotalNumET;
    @Bind(R.id.et_act_address)
    EditText mAddressET;
    @Bind(R.id.et_act_equipment)
    EditText mEquipmentET;
    @Bind(R.id.et_act_instruction)
    EditText mInstructionET;
    @Bind(R.id.left_button)
    ImageButton mBackIB;
    @Bind(R.id.et_act_price)
    EditText mPriceET;
    @Bind(R.id.et_act_shower_locker)
    Button mShowerLockerBtn;
    @Bind(R.id.et_act_type)
    Button mActTypeBtn;

    //时间 城市选择按钮
    @Bind(R.id.btn_start_time)
    Button btnStartTime;
    @Bind(R.id.btn_end_time)
    Button btnEndTime;
    @Bind(R.id.btn_act_city)
    Button mCityBtn;

    private Calendar calendar = Calendar.getInstance();
    private CameraProxy cameraProxy;
    private FTActivity ftActivity;

    public static String[] showerLockerStrs = new String[]{"不提供沐浴／锁柜", "只提供沐浴", "只提供锁柜", "提供沐浴／锁柜"};
    public static String[] actTypeStrs = new String[]{"室外", "场馆内"};

    int showStyle;  //标记此Acitvity的作用
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        ButterKnife.bind(this);
        showStyle = getIntent().getIntExtra(PARAM_SHOW_STYLE, ACTIVITY_CREATE);
        if (showStyle == ACTIVITY_CREATE) {
            ftActivity = new FTActivity();
        } else if (showStyle == ACTIVITY_SHOW) {
            ftActivity = (FTActivity) getIntent().getSerializableExtra(PARAM_FTACTIVITY);
        }

        initView();
        setListener();

        cameraProxy = new CameraProxy(new CameraResult() {
            @Override
            public void onSuccess(String path) {
                //射入图片
                ToastUtils.showCustomToast(mContext, "获取图片成功：" + path);
                ftActivity.setSourceUrl(path);
                mUploadPicIV.setVisibility(View.INVISIBLE);
                mPicSDV.setImageURI(Uri.parse("file://" + path));
            }
            @Override
            public void onFail(String message) {
                ToastUtils.showCustomToast(mContext, "获取图片失败");
            }
        }, ActivityCreateActivity.this);
    }


    @Override
    protected void initView() {
        super.initView();
        if (showStyle == ACTIVITY_SHOW) {
            if(ftActivity.getSourceType() == FTActivity.SOURCE_TYPE_PIC){
                mUploadPicIV.setVisibility(View.INVISIBLE);
                if(ftActivity.getSourceUrl().startsWith("http") ||
                        ftActivity.getSourceUrl().startsWith("https")){
                    mPicSDV.setImageURI(ftActivity.getSourceUrl());
                }else{
                    mPicSDV.setImageURI("http://" + ftActivity.getSourceUrl());
                }
            }
            mSubjextET.setText(ftActivity.getSubject());
            mTotalNumET.setText("" + ftActivity.getTotalNum());
            mAddressET.setText(ftActivity.getAddress());
            mEquipmentET.setText(ftActivity.getSelfEquipment());
            mInstructionET.setText(ftActivity.getIntruction());

            btnStartTime.setText(ftActivity.getBeginTime());
            btnEndTime.setText(ftActivity.getEndTime());
            mCityBtn.setText(ftActivity.getCity());
            mPriceET.setText("" + ftActivity.getPrice());
            mShowerLockerBtn.setText(showerLockerStrs[ftActivity.getShowerAndLocker()]);
            mShowerLockerBtn.setTag(ftActivity.getShowerAndLocker());
            mActTypeBtn.setText(actTypeStrs[ftActivity.getActType()]);
            mActTypeBtn.setTag(ftActivity.getActType());
        }
    }

    @Override
    protected void setListener() {
        super.setListener();
        mUploadPicIV.setOnClickListener(this);
        mPicSDV.setOnClickListener(this);
       // mUploadVideoIV.setOnClickListener(this);
        mPublishActTV.setOnClickListener(this);
        mPublishActBtn.setOnClickListener(this);
        mBackIB.setOnClickListener(this);
        mShowerLockerBtn.setOnClickListener(this);
        mActTypeBtn.setOnClickListener(this);

        //设置活动开始时间 结束时间
        btnStartTime.setOnClickListener(this);
        btnEndTime.setOnClickListener(this);
        mCityBtn.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        if (v == mUploadPicIV || v == mPicSDV) {
            showSelectPicDialog();
        } else if (v == mPublishActTV || v == mPublishActBtn) {
            publishAct();
        } else if (v == mBackIB) {
            finish();
        } else if (v == btnStartTime) {
            setStartTime();
        } else if (v == btnEndTime) {
            setEndTime();
        } else if (v == mCityBtn) {
            setCity();
        } else if(v == mShowerLockerBtn){
            showShowerLockerDialog();
        } else if(v == mActTypeBtn){
            showActTypeDialog();
        }
    }

    private void setCity() {
        new AddressInitTask(this).execute("北京", "北京", "东城区");
    }


    private void setEndTime() {
        DateTimePicker picker = new DateTimePicker(this, DateTimePicker.HOUR_OF_DAY);
        picker.setRange(2000, 2030);
        picker.setSelectedItem(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH),
                20, 00);
        picker.setOnDateTimePickListener(new DateTimePicker.OnYearMonthDayTimePickListener() {
            @Override
            public void onDateTimePicked(String year, String month, String day, String hour, String minute) {
                // showToast(year + "-" + month + "-" + day + " " + hour + ":" + minute);
                btnEndTime.setText(year + "-" + month + "-" + day + " " + hour + ":" + minute);
            }
        });
        picker.show();
    }

    private void setStartTime() {
        DateTimePicker picker = new DateTimePicker(this, DateTimePicker.HOUR_OF_DAY);
        picker.setRange(2000, 2030);
        picker.setSelectedItem(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH),
                19, 00);
        picker.setOnDateTimePickListener(new DateTimePicker.OnYearMonthDayTimePickListener() {
            @Override
            public void onDateTimePicked(String year, String month, String day, String hour, String minute) {
                // showToast(year + "-" + month + "-" + day + " " + hour + ":" + minute);
                btnStartTime.setText(year + "-" + month + "-" + day + " " + hour + ":" + minute);
            }
        });
        picker.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        cameraProxy.onResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date()) + ".jpg";
//                cameraProxy.getPhoto2Camera(AppConfig.PHOTO_DIRECTORY + "/" + fileName);
                cameraProxy.getPhoto2CameraCrop(AppConfig.PHOTO_DIRECTORY + "/" + fileName, FTActivity.PIC_WIDTH,
                        FTActivity.PIC_HEIGHT);
            } else {
                ToastUtils.showCustomToast(mContext, "没有权限无法进行拍照！");
            }
        }
    }

    private void showSelectPicDialog() {
        final DialogPlus dialogPlus = DialogPlus.newDialog(mContext)
                .setGravity(Gravity.BOTTOM)
                .setCancelable(true)
                .setAdapter(new PicSelectAdapter(mContext))
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                        String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date()) + ".jpg";
                        if (position == 0) {//拍照
                            /**
                             * 适配M的动态权限
                             */
                            if (Build.VERSION.SDK_INT >= 23) {
                                int checkCameraPermission = ContextCompat.checkSelfPermission(mContext,
                                        android.Manifest.permission.CAMERA);
                                if (checkCameraPermission != PackageManager.PERMISSION_GRANTED) {
                                    ActivityCompat.requestPermissions(ActivityCreateActivity.this,
                                            new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                            REQUEST_CAMERA_PERMISSION);
                                    dialog.dismiss();
                                    return;
                                }
                            }
//                            cameraProxy.getPhoto2Camera(AppConfig.PHOTO_DIRECTORY + "/" + fileName);
                            cameraProxy.getPhoto2CameraCrop(AppConfig.PHOTO_DIRECTORY + "/" + fileName,
                                    FTActivity.PIC_WIDTH, FTActivity.PIC_HEIGHT);
                        } else {
//                            cameraProxy.getPhoto2Album(AppConfig.PHOTO_DIRECTORY + "/" + fileName);
                            cameraProxy.getPhoto2AlbumCrop(AppConfig.PHOTO_DIRECTORY + "/" + fileName,
                                    FTActivity.PIC_WIDTH, FTActivity.PIC_HEIGHT);
                        }

                        dialog.dismiss();
                    }
                })
                .create();
        dialogPlus.show();
    }

    private void showShowerLockerDialog(){
        DialogPlus dialogPlus = DialogPlus.newDialog(mContext)
                .setGravity(Gravity.CENTER)
                .setCancelable(true)
                .setAdapter(new ArrayAdapter<String>(mContext,
                        android.R.layout.simple_list_item_1, showerLockerStrs))
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                        mShowerLockerBtn.setText(showerLockerStrs[position]);
                        mShowerLockerBtn.setTag(position);
                        dialog.dismiss();
                    }
                })
                .create();
        dialogPlus.show();
    }

    private void showActTypeDialog(){
        DialogPlus dialogPlus = DialogPlus.newDialog(mContext)
                .setGravity(Gravity.CENTER)
                .setCancelable(true)
                .setAdapter(new ArrayAdapter<String>(mContext,
                        android.R.layout.simple_list_item_1, actTypeStrs))
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                        mActTypeBtn.setText(actTypeStrs[position]);
                        mActTypeBtn.setTag(position);
                        dialog.dismiss();
                    }
                })
                .create();
        dialogPlus.show();
    }

    private void publishAct() {
        if (TextUtils.isEmpty(mSubjextET.getText()) ||
                TextUtils.isEmpty(mTotalNumET.getText()) ||
                TextUtils.isEmpty(mAddressET.getText()) ||
                TextUtils.isEmpty(mInstructionET.getText()) ||
                TextUtils.isEmpty(ftActivity.getSourceUrl()) ||
                TextUtils.isEmpty(btnStartTime.getText()) ||
                TextUtils.isEmpty(btnEndTime.getText()) ||
                TextUtils.isEmpty(mCityBtn.getText()) ||
                TextUtils.isEmpty(mShowerLockerBtn.getText()) ||
                TextUtils.isEmpty(mActTypeBtn.getText()) ||
                TextUtils.isEmpty(mPriceET.getText()) ||
                TextUtils.isEmpty(mEquipmentET.getText())) {
            ToastUtils.showCustomToast(mContext, "请完善信息");
            return;
        }

        if (ftActivity.getSourceUrl().endsWith(".png") ||
                ftActivity.getSourceUrl().endsWith(".jpg")) {
            ftActivity.setSourceType(FTActivity.SOURCE_TYPE_PIC);
        } else {
            ftActivity.setSourceType(FTActivity.SOURCE_TYPE_VIDEO);
        }

        ftActivity.setSubject(mSubjextET.getText().toString());
        ftActivity.setTotalNum(Integer.parseInt(mTotalNumET.getText().toString()));
        ftActivity.setAddress(mAddressET.getText().toString());
        ftActivity.setSelfEquipment(mEquipmentET.getText().toString());
        ftActivity.setIntruction(mInstructionET.getText().toString());

        ftActivity.setBeginTime(btnStartTime.getText().toString());
        ftActivity.setEndTime(btnEndTime.getText().toString());
        ftActivity.setCity(mCityBtn.getText().toString());
        ftActivity.setPrice(Float.parseFloat(mPriceET.getText().toString()));
        ftActivity.setShowerAndLocker(Integer.parseInt(mShowerLockerBtn.getTag().toString()));
        ftActivity.setActType(Integer.parseInt(mActTypeBtn.getTag().toString()));

        User user = new User();
        user.setUserId(GlobalUserManager.getUserId());
        user.setUserId(GlobalUserManager.getNickName());
        user.setPortrait(GlobalUserManager.getPortrait());
        ftActivity.setOwner(user);


        IFTActivityBiz ftActivityBiz = new FTActivityBiz();
        ftActivityBiz.publishAct(ftActivity, new IFTActivityBiz.PublishActivityListener() {
            @Override
            public void onSuccess() {
                ToastUtils.showCustomToast(mContext, "发布成功");
            }

            @Override
            public void onFailure(String msg) {
                ToastUtils.showCustomToast(mContext, msg);
            }

            @Override
            public void onProgress(String step, int i) {

            }
        });
    }

    public class AddressInitTask extends AsyncTask<String, Void, ArrayList<AddressPicker.Province>> {
        private Activity activity;
        private ProgressDialog dialog;
        private String selectedProvince = "", selectedCity = "", selectedCounty = "";
        private boolean hideCounty = false;

        /**
         * 初始化为不显示区县的模式
         *
         * @param activity
         * @param hideCounty is hide County
         */
        public AddressInitTask(Activity activity, boolean hideCounty) {
            this.activity = activity;
            this.hideCounty = hideCounty;
            dialog = ProgressDialog.show(activity, null, "正在初始化数据...", true, true);
        }

        public AddressInitTask(Activity activity) {
            this.activity = activity;
            dialog = ProgressDialog.show(activity, null, "正在初始化数据...", true, true);
        }

        @Override
        protected ArrayList<AddressPicker.Province> doInBackground(String... params) {
            if (params != null) {
                switch (params.length) {
                    case 1:
                        selectedProvince = params[0];
                        break;
                    case 2:
                        selectedProvince = params[0];
                        selectedCity = params[1];
                        break;
                    case 3:
                        selectedProvince = params[0];
                        selectedCity = params[1];
                        selectedCounty = params[2];
                        break;
                    default:
                        break;
                }
            }
            ArrayList<AddressPicker.Province> data = new ArrayList<AddressPicker.Province>();
            try {
                String json = AssetsUtils.readText(activity, "city.json");
                data.addAll(JSON.parseArray(json, AddressPicker.Province.class));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return data;
        }

        @Override
        protected void onPostExecute(ArrayList<AddressPicker.Province> result) {
            dialog.dismiss();
            if (result.size() > 0) {
                AddressPicker picker = new AddressPicker(activity, result);
                picker.setHideCounty(hideCounty);
                picker.setSelectedItem(selectedProvince, selectedCity, selectedCounty);
                picker.setOnAddressPickListener(new AddressPicker.OnAddressPickListener() {
                    @Override
                    public void onAddressPicked(String province, String city, String county) {
                        if (county == null) {
                            mCityBtn.setText(province + " " + city);
                        } else {
                            mCityBtn.setText(province + " " + city + " " + county);
                        }
                    }
                });
                picker.show();
            } else {
                Toast.makeText(activity, "数据初始化失败", Toast.LENGTH_SHORT).show();
            }
        }

    }

}
