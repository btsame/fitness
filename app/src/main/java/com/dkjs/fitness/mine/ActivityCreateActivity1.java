package com.dkjs.fitness.mine;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dkjs.fitness.R;
import com.dkjs.fitness.adapter.PicSelectAdapter;
import com.dkjs.fitness.biz.FTActivityBiz;
import com.dkjs.fitness.biz.IFTActivityBiz;
import com.dkjs.fitness.comm.AppConfig;
import com.dkjs.fitness.comm.FitnessActivity;
import com.dkjs.fitness.comm.GlobalUserManager;
import com.dkjs.fitness.comm.HeadTitleView;
import com.dkjs.fitness.domain.FTActivity;
import com.dkjs.fitness.domain.User;
import com.dkjs.fitness.publish.CreateActMoreSettingsActivity;
import com.dkjs.fitness.publish.SetAccountActivity;
import com.dkjs.fitness.publish.SetLocationActivity;
import com.dkjs.fitness.publish.SetPriceActivity;
import com.dkjs.fitness.publish.SetTagActivity;
import com.dkjs.fitness.publish.SetTimeActivity;
import com.dkjs.fitness.util.CameraProxy;
import com.dkjs.fitness.util.CameraResult;
import com.dkjs.fitness.util.ToastUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnItemClickListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/7/18.
 */
public class ActivityCreateActivity1 extends FitnessActivity implements View.OnClickListener {

    public static final int REQUEST_CAMERA_PERMISSION = 0x11;
    public static final int ACTIVITY_CREATE = 1;
    public static final int ACTIVITY_SHOW = 2;
    public static final String PARAM_SHOW_STYLE = "showSytle";
    public static final String PARAM_FTACTIVITY = "ftActivity";
    public static final int FEE_RESULT_CODE = 3;
    public static final int RESULT_TIME = 4;
    public static final int RESULT_LOCATION = 5;
    public static final int RESULT_ACCOUNT = 6;
    public static final int RESULT_FEE = 7;
    public static final int RESULT_TAG = 8;
    public static final int RESULT_MORE_SETTINGS = 9;

    String TAG = "ActivityCreateActivity";
    //获取onactivityforresult返回值
    String returnData;
    String[] returnDatas;
    @Bind(R.id.fl_upload_img)
    FrameLayout mUploadPicIV;

    @Bind(R.id.ll_upload_pic)
    LinearLayout mUploadPicLl;
    @Bind(R.id.sdv_show_act_pic)
    SimpleDraweeView mPicSDV;

    //时间
    @Bind(R.id.ll_set_time)
    LinearLayout llSetTime;

    @Bind(R.id.ll_set_location)
    LinearLayout llSetLocation;

    @Bind(R.id.ll_set_account)
    LinearLayout llSetAccount;

    @Bind(R.id.ll_set_fee)
    LinearLayout llSetFee;

    @Bind(R.id.ll_set_tag)
    LinearLayout llSetTag;

    @Bind(R.id.ll_set_more)
    LinearLayout llSetMore;

    /* @Bind(R.id.btn_publish_activity)
     Button mPublishActBtn;*/
    @Bind(R.id.et_act_subject)
    EditText mSubjextET;

    @Bind(R.id.et_act_instruction)
    EditText mInstructionET;
    @Bind(R.id.left_button)
    ImageButton mBackIB;


    @Bind(R.id.tv_publish_activity)
    TextView tvPublish;

    private Calendar calendar = Calendar.getInstance();
    private CameraProxy cameraProxy;
    private FTActivity ftActivity;
    HeadTitleView headTitleView;


    int showStyle;  //标记此Acitvity的作用

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create1);
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
                mUploadPicLl.setVisibility(View.INVISIBLE);
                mPicSDV.setImageURI(Uri.parse("file://" + path));
            }

            @Override
            public void onFail(String message) {
                ToastUtils.showCustomToast(mContext, "获取图片失败");
            }
        }, ActivityCreateActivity1.this);
    }


    @Override
    protected void initView() {
        super.initView();

       /* headTitleView = (HeadTitleView) findViewById(R.id.headview);
        headTitleView.setRightButtonVisibility(true);*/


        if (showStyle == ACTIVITY_SHOW) {
            if (ftActivity.getSourceType() == FTActivity.SOURCE_TYPE_PIC) {
                mUploadPicIV.setVisibility(View.INVISIBLE);
                if (ftActivity.getSourceUrl().startsWith("http") ||
                        ftActivity.getSourceUrl().startsWith("https")) {
                    mUploadPicIV.setVisibility(View.INVISIBLE);
                    mPicSDV.setImageURI(ftActivity.getSourceUrl());
                } else {
                    mPicSDV.setImageURI("http://" + ftActivity.getSourceUrl());
                }
            }
            mSubjextET.setText(ftActivity.getSubject());

            mInstructionET.setText(ftActivity.getIntruction());


        }
    }

    @Override
    protected void setListener() {
        super.setListener();
        mUploadPicIV.setOnClickListener(this);
        mPicSDV.setOnClickListener(this);
        llSetTime.setOnClickListener(this);
        llSetLocation.setOnClickListener(this);
        llSetAccount.setOnClickListener(this);
        llSetFee.setOnClickListener(this);
        llSetTag.setOnClickListener(this);
        llSetMore.setOnClickListener(this);
        tvPublish.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v == mUploadPicIV || v == mPicSDV) {
            showSelectPicDialog();
        } /*else if (v == mPublishActTV || v == mPublishActBtn) {
            publishAct();
        }*/ else if (v == mBackIB) {
            finish();
        } else if (v == llSetTime) {
            setTime();
        } else if (v == llSetLocation) {
            setCity();
        } else if (v == llSetAccount) {
            setAccount();
        } else if (v == llSetFee) {
            setPrice();
        } else if (v == llSetTag) {
            setTag();
        } else if (v == llSetMore) {
            setMore();
        } else if (v == tvPublish) {
            publishAct();
        }
    }

    private void setAccount() {
        startActivityForResult(new Intent(this, SetAccountActivity.class), RESULT_ACCOUNT);

    }

    private void setTime() {
        startActivityForResult(new Intent(this, SetTimeActivity.class), RESULT_TIME);
    }

    private void setTag() {
        startActivityForResult(new Intent(this, SetTagActivity.class), RESULT_TAG);
    }

    private void setMore() {
        startActivityForResult(new Intent(this, CreateActMoreSettingsActivity.class), RESULT_MORE_SETTINGS);
    }

    private void setPrice() {
        startActivityForResult(new Intent(this, SetPriceActivity.class), FEE_RESULT_CODE);
    }

    private void setCity() {
        startActivityForResult(new Intent(this, SetLocationActivity.class), RESULT_TAG);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            cameraProxy.onResult(requestCode, resultCode, data);
            if (requestCode == FEE_RESULT_CODE) {
                returnData = data.getExtras().getString("result");
                if (returnData != null && "" != returnData) {
                    //获取活动价格
                    ftActivity.setPrice(Integer.parseInt(returnData));
                }
            } else if (requestCode == RESULT_TIME) {
                returnData = data.getExtras().getString("result");
                if (returnData != null && "" != returnData) {
                    returnDatas = returnData.split("##");
                    ftActivity.setBeginTime(returnDatas[0]);
                    ftActivity.setBeginTime(returnDatas[1]);
                }
            }
        } else if (requestCode == RESULT_LOCATION) {
            returnData = data.getExtras().getString("result");
            if (returnData != null && "" != returnData) {
                returnDatas = returnData.split("##");
                ftActivity.setCity(returnDatas[0]);
                ftActivity.setAddress(returnDatas[1]);
            }

        } else if (requestCode == RESULT_ACCOUNT) {
            returnData = data.getExtras().getString("result");
            if (returnData != null && "" != returnData) {
                returnDatas = returnData.split("##");
                ftActivity.setTotalNum(Integer.parseInt(returnDatas[0]));
            }
        } else if (requestCode == RESULT_TAG) {
            returnData = data.getExtras().getString("result");
            if (returnData != null && "" != returnData) {
                returnDatas = returnData.split("##");
                ftActivity.setActTag(returnDatas[0]);
            }
        } else if (requestCode == RESULT_MORE_SETTINGS) {
            returnData = data.getExtras().getString("result");
            if (returnData != null && "" != returnData) {
                returnDatas = returnData.split("##");
                ftActivity.setSelfEquipment(returnDatas[0]);
                ftActivity.setActType(Integer.parseInt(returnDatas[1]));
                ftActivity.setActSex(Integer.parseInt(returnDatas[2]));
                ftActivity.setShowerAndLocker(Integer.parseInt(returnDatas[3]));

            }
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date()) + ".jpg";

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
                                        Manifest.permission.CAMERA);
                                if (checkCameraPermission != PackageManager.PERMISSION_GRANTED) {
                                    ActivityCompat.requestPermissions(ActivityCreateActivity1.this,
                                            new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
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


    private void publishAct() {


        if (ftActivity.getBeginTime() != null) {
            ToastUtils.showCustomToast(mContext, ftActivity.getBeginTime());
            return;
        }

        if (ftActivity.getSourceUrl().endsWith(".png") ||
                ftActivity.getSourceUrl().endsWith(".jpg")) {
            ftActivity.setSourceType(FTActivity.SOURCE_TYPE_PIC);
        } else {
            ftActivity.setSourceType(FTActivity.SOURCE_TYPE_VIDEO);
        }

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


}
