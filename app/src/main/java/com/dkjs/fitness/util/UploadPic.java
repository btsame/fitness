package com.dkjs.fitness.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;

import com.dkjs.fitness.adapter.PicSelectAdapter;
import com.dkjs.fitness.comm.AppConfig;
import com.dkjs.fitness.mine.ActivityCreateActivity;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnItemClickListener;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/7/31.
 */
public class UploadPic implements View.OnClickListener {



    @Override
    public void onClick(View v) {

    }

    public UploadPic() {

    }

    public static void showSelectPicDialog(final CameraProxy cameraProxy, final Activity activity, final Context mContext,final int REQUEST_CAMERA_PERMISSION) {
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
                                    ActivityCompat.requestPermissions(activity,
                                            new String[]{Manifest.permission.CAMERA},
                                            REQUEST_CAMERA_PERMISSION);
                                    dialog.dismiss();
                                    return;
                                }
                            }
                            cameraProxy.getPhoto2Camera(AppConfig.PHOTO_DIRECTORY + "/" + fileName);
                        } else {
                            cameraProxy.getPhoto2Album(AppConfig.PHOTO_DIRECTORY + "/" + fileName);
                        }

                        dialog.dismiss();
                    }
                })
                .create();
        dialogPlus.show();
    }

}
