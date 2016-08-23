package com.dkjs.fitness.mine;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.dkjs.fitness.R;
import com.dkjs.fitness.citydateselector.picker.AddressPicker;
import com.dkjs.fitness.citydateselector.picker.DatePicker;
import com.dkjs.fitness.citydateselector.utils.AssetsUtils;
import com.dkjs.fitness.comm.AppConfig;
import com.dkjs.fitness.comm.FitnessActivity;
import com.dkjs.fitness.domain.FTActivity;
import com.dkjs.fitness.util.CameraProxy;
import com.dkjs.fitness.util.CameraResult;
import com.dkjs.fitness.util.ToastUtils;
import com.dkjs.fitness.util.UploadPic;
import com.facebook.drawee.view.SimpleDraweeView;
import com.makeramen.roundedimageview.RoundedImageView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2016/7/16.
 */
public class UserInfoActivity extends FitnessActivity {

    Button btnDate, btnCity;
    SimpleDraweeView ivIcon;
    private Calendar calendar = Calendar.getInstance();

    private FTActivity ftActivity;

    private CameraProxy cameraProxy;
    public static final int REQUEST_CAMERA_PERMISSION = 0x11;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_setting);


        initView();

        cameraProxy = new CameraProxy(new CameraResult() {
            @Override
            public void onSuccess(String path) {
                //射入图片
                ToastUtils.showCustomToast(mContext, "获取图片成功：" + path);
               // ftActivity.setSourceUrl(path);
                ivIcon.setImageURI(Uri.parse("file://" + path));
            }
            @Override
            public void onFail(String message) {
                ToastUtils.showCustomToast(mContext, "获取图片失败");
            }
        }, UserInfoActivity.this);


    }


    @Override
    protected void initView() {
        btnCity = (Button) findViewById(R.id.btn_city_set);
        btnDate = (Button) findViewById(R.id.btn_date_set);
        ivIcon= (SimpleDraweeView) findViewById(R.id.cuser_head);
        btnCity.setText("北京 北京 东城区");
        btnDate.setText(" ");
    }

    //更改头像
    public void upLoadImg(View view){

        UploadPic.showSelectPicDialog(cameraProxy,this,UserInfoActivity.this,REQUEST_CAMERA_PERMISSION);

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

    public void setCity(View view) {
        new AddressInitTask(this).execute("北京", "北京", "东城区");
    }

    public void setDate(View view) {
        DatePicker picker = new DatePicker(this);
        picker.setRange(1900, 2030);
        picker.setSelectedItem(1990, 01, 01);
        picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                if (calendar.get(Calendar.YEAR) - (Integer.parseInt(year)) > 0) {
                    btnDate.setText(calendar.get(Calendar.YEAR) - (Integer.parseInt(year)) + "");
                } else {
                    btnDate.setText(0 + "");
                }

            }
        });
        picker.show();
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
                            btnCity.setText(province + " " + city);
                        } else {
                            btnCity.setText(province + " " + city + " " + county);
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
