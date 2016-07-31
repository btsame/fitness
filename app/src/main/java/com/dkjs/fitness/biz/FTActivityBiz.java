package com.dkjs.fitness.biz;

import com.dkjs.fitness.comm.GlobalUserManager;
import com.dkjs.fitness.domain.FTActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.maxleap.MLUser;
import com.maxleap.social.DataHandler;
import com.maxleap.social.HermsException;
import com.maxleap.social.MLHermes;
import com.maxleap.social.ShuoShuoManager;
import com.maxleap.social.entity.ShuoShuo;

/**
 * Created by administrator on 16/7/30.
 */
public class FTActivityBiz implements IFTActivityBiz{

    ShuoShuoManager manager;

    public FTActivityBiz() {
        manager = MLHermes.getShuoShuoManager();
    }

    @Override
    public void publishAct(final FTActivity ftActivity, final PublishActivityListener paListener) {


        IFileBiz fileBiz = new FileBiz();
        fileBiz.uploadFile(ftActivity.getSourceUrl(), new IFileBiz.FileUploadListener() {
            @Override
            public void onSucess(String url) {
                ftActivity.setSourceUrl(url);
            }

            @Override
            public void onFailure(String reason) {
                if(paListener != null){
                    paListener.onFailure("文件上传失败");
                }
            }

            @Override
            public void onProgress(int i) {
                if(paListener != null){
                    paListener.onProgress(paListener.STEP_UPLOAD_FILE, (int)(i * 0.9));
                }
            }
        });
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String actDetail = gson.toJson(ftActivity);


        ShuoShuo shuoShuo = new ShuoShuo();
        shuoShuo.setUserId(GlobalUserManager.getUserId());
        shuoShuo.setContent(actDetail);
        manager.createOrUpdateShuoShuo(shuoShuo, new DataHandler<String>() {
            @Override
            public void onSuccess(String objectId) {
                if(paListener != null){
                    paListener.onProgress(paListener.STEP_UPLOAD_FILE, 100);
                    paListener.onSucess();
                }

            }

            @Override
            public void onError(HermsException e) {
                if(paListener != null){
                    paListener.onFailure(e.getMessage());
                }
            }
        });

    }
}
