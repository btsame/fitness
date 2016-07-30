package com.dkjs.fitness.biz;

import com.maxleap.MLFile;
import com.maxleap.MLFileManager;
import com.maxleap.ProgressCallback;
import com.maxleap.SaveCallback;
import com.maxleap.exception.MLException;

import org.jetbrains.annotations.NotNull;

/**
 * Created by administrator on 16/7/30.
 */
public class FileBiz implements IFileBiz{

    @Override
    public void uploadFile(@NotNull String filePath, final FileUploadListener fileUploadListener) {
        String fileName = "";
        fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
        MLFile mlFile = new MLFile(fileName, filePath);

        MLFileManager.saveInBackground(mlFile, new SaveCallback() {
            @Override
            public void done(MLException e) {
                if(e == null){
                    if(fileUploadListener != null){
                        fileUploadListener.onSucess();
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
