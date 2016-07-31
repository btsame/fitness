package com.dkjs.fitness.biz;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.dkjs.fitness.util.LogUtil;
import com.maxleap.MLFile;
import com.maxleap.MLFileManager;
import com.maxleap.MLPrivateFile;
import com.maxleap.MLPrivateFileManager;
import com.maxleap.ProgressCallback;
import com.maxleap.SaveCallback;
import com.maxleap.exception.MLException;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;

/**
 * Created by administrator on 16/7/30.
 */
public class FileBiz implements IFileBiz{

    @Override
    public void uploadFile(@NotNull String filePath, final FileUploadListener fileUploadListener) {
        String fileName = "";
        fileName = filePath.substring(filePath.lastIndexOf("/") + 1);


//        final MLPrivateFile fileDirectory = MLPrivateFile.createDirectory("/pic");
//        MLPrivateFileManager.createDirectoryInBackground(fileDirectory, new SaveCallback() {
//            @Override
//            public void done(MLException e) {
//                LogUtil.e("FileBiz", e.getMessage());
//            }
//        });
//        final MLPrivateFile privateFile = MLPrivateFile.createFile(filePath, "/pic/" + fileName);
//        MLPrivateFileManager.saveInBackground(privateFile, new SaveCallback() {
//            @Override
//            public void done(MLException e) {
//                if(e == null){
//                    if(fileUploadListener != null){
//                        fileUploadListener.onSucess(privateFile.getSharedUrl());
//                    }
//                }else{
//                    if(fileUploadListener != null){
//                        fileUploadListener.onFailure(e.getMessage());
//                    }
//                }
//            }
//        }, new ProgressCallback() {
//            @Override
//            public void done(int i) {
//                if(fileUploadListener != null){
//                    fileUploadListener.onProgress(i);
//                }
//            }
//        });


//        final MLFile mlFile = new MLFile(fileName, filePath);
        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] imgBytes = baos.toByteArray();
        final MLFile mlFile = new MLFile(fileName, imgBytes);

        MLFileManager.saveInBackground(mlFile, new SaveCallback() {
            @Override
            public void done(MLException e) {
                if(e == null){
                    if(fileUploadListener != null){
                        fileUploadListener.onSucess(mlFile.getUrl());
                    }
                }else{
                    if(fileUploadListener != null){
                        fileUploadListener.onFailure(e.getMessage());
                    }
                }
            }
        }, new ProgressCallback() {
            @Override
            public void done(int i) {
                if(fileUploadListener != null){
                    fileUploadListener.onProgress(i);
                }
            }
        });

    }

}
