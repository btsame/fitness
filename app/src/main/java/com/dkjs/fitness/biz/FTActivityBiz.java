package com.dkjs.fitness.biz;

import com.dkjs.fitness.domain.FTActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.maxleap.social.MLHermes;
import com.maxleap.social.ShuoShuoManager;

/**
 * Created by administrator on 16/7/30.
 */
public class FTActivityBiz implements IFTActivityBiz{

    ShuoShuoManager manager;

    public FTActivityBiz() {
        manager = MLHermes.getShuoShuoManager();
    }

    @Override
    public boolean publishAct(FTActivity ftActivity, final PublishActivityListener paListener) {


        IFileBiz fileBiz = new FileBiz();
        fileBiz.uploadFile(ftActivity.getSourceUrl(), new IFileBiz.FileUploadListener() {
            @Override
            public void onSucess() {

            }

            @Override
            public void onFailure(String reason) {
                if(paListener != null){
                    paListener.onFailure("文件上传失败");
                }
            }

            @Override
            public void onProgress(int i) {

            }
        });
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String actDetail = gson.toJson(ftActivity);

        return false;
    }
}
