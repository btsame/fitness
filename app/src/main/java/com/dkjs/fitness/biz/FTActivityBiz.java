package com.dkjs.fitness.biz;

import com.dkjs.fitness.comm.GlobalUserManager;
import com.dkjs.fitness.domain.FTActivity;
import com.dkjs.fitness.util.LogUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.maxleap.MLUser;
import com.maxleap.social.DataHandler;
import com.maxleap.social.DataListHandler;
import com.maxleap.social.HermsException;
import com.maxleap.social.MLHermes;
import com.maxleap.social.ShuoShuoManager;
import com.maxleap.social.entity.Constraint;
import com.maxleap.social.entity.ShuoShuo;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by administrator on 16/7/30.
 */
public class FTActivityBiz implements IFTActivityBiz {

    public static final String TAG = "FTActivityBiz";

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

                Gson gson = new Gson();
                String actDetail = gson.toJson(ftActivity);
                LogUtil.e(TAG, "actDetail:" + actDetail);

                ShuoShuo shuoShuo = new ShuoShuo();
                shuoShuo.setUserId(GlobalUserManager.getUserId());
                shuoShuo.setContent(actDetail);
                manager.createOrUpdateShuoShuo(shuoShuo, new DataHandler<String>() {
                    @Override
                    public void onSuccess(String objectId) {
                        if (paListener != null) {
                            paListener.onProgress(paListener.STEP_UPLOAD_FILE, 100);
                            paListener.onSuccess();
                        }

                    }

                    @Override
                    public void onError(HermsException e) {
                        if (paListener != null) {
                            paListener.onFailure(e.getMessage());
                        }
                    }
                });
            }

            @Override
            public void onFailure(String reason) {
                if (paListener != null) {
                    paListener.onFailure(reason);
                }
            }

            @Override
            public void onProgress(int i) {
                if (paListener != null) {
                    paListener.onProgress(paListener.STEP_UPLOAD_FILE, (int) (i * 0.9));
                }
            }
        });


    }

    @Override
    public void joinInAct() {

    }

    @Override
    public void queryLastestActs(@NotNull final QueryActivityListener qaListener) {
        Constraint constraint = new Constraint.Builder()
                .orderByTime()
                .desc()
                .build();

        manager.getLatestShuoShuos(constraint, new DataListHandler<ShuoShuo>() {
            @Override
            public void onSuccess(List<ShuoShuo> list) {
                List<FTActivity> actList = new ArrayList<FTActivity>();
                for (ShuoShuo temp : list) {
                    try {
//                        removeAct(temp.getObjectId(), null);
                        String content = temp.getContent();
                        content = content.replaceAll("\\\\", "\"");
                        LogUtil.e(TAG, "*************************content:" + content);
                        Gson gson = new Gson();
                        FTActivity act = gson.fromJson(content, FTActivity.class);
                        act.setShuoShuo(temp);
                        actList.add(act);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                qaListener.onSuccess(actList);
            }

            @Override
            public void onError(HermsException e) {
                qaListener.onFailure(e.getMessage());
            }
        });
    }

    @Override
    public void removeAct(@NotNull FTActivity actList, final RemoveActivityListener raListener) {
        manager.deleteShuoShuo(actList.getShuoShuo().getObjectId(), new DataHandler<Void>() {

            @Override
            public void onSuccess(Void aVoid) {
                if(raListener != null){
                    raListener.onSuccess();
                }
            }

            @Override
            public void onError(HermsException e) {
                if(raListener != null){
                    raListener.onFailure(e.getMessage());
                }
            }
        });
    }

    @Override
    public void removeAct(@NotNull String shuoShuoId, final RemoveActivityListener raListener) {
        manager.deleteShuoShuo(shuoShuoId, new DataHandler<Void>() {

            @Override
            public void onSuccess(Void aVoid) {
                if(raListener != null){
                    raListener.onSuccess();
                }
            }

            @Override
            public void onError(HermsException e) {
                if(raListener != null){
                    raListener.onFailure(e.getMessage());
                }
            }
        });
    }
}
