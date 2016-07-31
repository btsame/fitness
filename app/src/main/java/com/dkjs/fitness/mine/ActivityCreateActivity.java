package com.dkjs.fitness.mine;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dkjs.fitness.R;
import com.dkjs.fitness.biz.FTActivityBiz;
import com.dkjs.fitness.biz.IFTActivityBiz;
import com.dkjs.fitness.comm.AppConfig;
import com.dkjs.fitness.comm.FitnessActivity;
import com.dkjs.fitness.domain.FTActivity;
import com.dkjs.fitness.util.CameraProxy;
import com.dkjs.fitness.util.CameraResult;
import com.dkjs.fitness.util.ToastUtils;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.AbstractDraweeController;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnItemClickListener;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/7/18.
 */
public class ActivityCreateActivity extends FitnessActivity implements View.OnClickListener{

    public static final int REQUEST_CAMERA_PERMISSION = 0x11;

    @Bind(R.id.ll_upload_pic)
    LinearLayout mUploadPicIV;
    @Bind(R.id.ll_upload_video)
    LinearLayout mUploadVideoIV;
    @Bind(R.id.tv_publish_activity)
    TextView mPublishActTV;
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


    private CameraProxy cameraProxy;
    private FTActivity ftActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        ButterKnife.bind(this);
        setListener();

        ftActivity = new FTActivity();

        cameraProxy = new CameraProxy(new CameraResult() {
            @Override
            public void onSuccess(String path) {
                //射入图片
                ToastUtils.showCustomToast(mContext, "获取图片成功：" + path);
                ftActivity.setSourceUrl(path);
            }

            @Override
            public void onFail(String message) {
                ToastUtils.showCustomToast(mContext, "获取图片失败");
            }
        }, ActivityCreateActivity.this);
    }

    @Override
    protected void setListener() {
        super.setListener();
        mUploadPicIV.setOnClickListener(this);
        mUploadVideoIV.setOnClickListener(this);
        mPublishActTV.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if(v == mUploadPicIV){
            showSelectPicDialog();
        }else if(v == mUploadVideoIV){

        }else if(v == mPublishActTV){
            publishAct();
        }
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
                cameraProxy.getPhoto2Camera(AppConfig.PHOTO_DIRECTORY + "/" + fileName);
            } else {
                ToastUtils.showCustomToast(mContext, "没有权限无法进行拍照！");
            }
        }
    }

    private void showSelectPicDialog(){
        final DialogPlus dialogPlus = DialogPlus.newDialog(mContext)
                .setGravity(Gravity.BOTTOM)
                .setCancelable(true)
                .setAdapter(new PicSelectAdapter())
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                        String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date()) + ".jpg";
                        if(position == 0){//拍照
                            /**
                             * 适配M的动态权限
                             */
                            if (Build.VERSION.SDK_INT >= 23) {
                                int checkCameraPermission = ContextCompat.checkSelfPermission(mContext,
                                        android.Manifest.permission.CAMERA);
                                if (checkCameraPermission != PackageManager.PERMISSION_GRANTED) {
                                    ActivityCompat.requestPermissions(ActivityCreateActivity.this,
                                            new String[]{Manifest.permission.CAMERA},
                                            REQUEST_CAMERA_PERMISSION);
                                    dialog.dismiss();
                                    return;
                                }
                            }
                            cameraProxy.getPhoto2Camera(AppConfig.PHOTO_DIRECTORY + "/" + fileName);
                        }else{
                            cameraProxy.getPhoto2Album(AppConfig.PHOTO_DIRECTORY + "/" + fileName);
                        }

                        dialog.dismiss();
                    }
                })
                .create();
        dialogPlus.show();
    }

    private void publishAct(){
        if(TextUtils.isEmpty(mSubjextET.getText()) ||
                TextUtils.isEmpty(mTotalNumET.getText()) ||
                TextUtils.isEmpty(mAddressET.getText()) ||
                TextUtils.isEmpty(mInstructionET.getText()) ||
                TextUtils.isEmpty(ftActivity.getSourceUrl())){
            ToastUtils.showCustomToast(mContext, "请完善信息");
            return;
        }

        if(ftActivity.getSourceUrl().endsWith(".png") ||
                ftActivity.getSourceUrl().endsWith(".jpg")){
            ftActivity.setSourceType(FTActivity.SOURCE_TYPE_PIC);
        }else{
            ftActivity.setSourceType(FTActivity.SOURCE_TYPE_VIDEO);
        }

        ftActivity.setSubject(mSubjextET.getText().toString());
        ftActivity.setTotalNum(Integer.parseInt(mTotalNumET.getText().toString()));
        ftActivity.setAddress(mAddressET.getText().toString());
        ftActivity.setSelfEquipment(mEquipmentET.getText().toString());
        ftActivity.setIntruction(mInstructionET.getText().toString());

        IFTActivityBiz ftActivityBiz = new FTActivityBiz();
        ftActivityBiz.publishAct(ftActivity, new IFTActivityBiz.PublishActivityListener() {
            @Override
            public void onSucess() {
                ToastUtils.showCustomToast(mContext, "发布成功");
            }

            @Override
            public void onFailure(String msg) {
                ToastUtils.showCustomToast(mContext, "发布失败");
            }

            @Override
            public void onProgress(String step, int i) {

            }
        });
    }

    public class PicSelectAdapter extends BaseAdapter{

        private String[] itemNames = new String[]{"拍照", "相册"};
        private int[] itemPics = new int[]{android.R.drawable.ic_menu_camera,
        android.R.drawable.ic_menu_gallery};

        public PicSelectAdapter() {

        }

        @Override
        public int getCount() {
            return itemNames.length;
        }

        @Override
        public Object getItem(int position) {
            return itemNames[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            View itemView = layoutInflater.inflate(R.layout.item_pic_select, null);
            ((ImageView)itemView.findViewById(R.id.iv_pic_select)).setImageResource(itemPics[position]);
            ((TextView)itemView.findViewById(R.id.tv_pic_select)).setText(itemNames[position]);
            return itemView;
        }
    }
}
