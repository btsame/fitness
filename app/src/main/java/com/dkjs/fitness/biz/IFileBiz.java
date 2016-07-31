package com.dkjs.fitness.biz;

/**
 * Created by administrator on 16/7/30.
 */
public interface IFileBiz {

    void uploadFile(String filePath, FileUploadListener fileUploadListener);

    interface FileUploadListener{
        void onSucess(String url);
        void onFailure(String reason);
        void onProgress(int i);
    }
}
