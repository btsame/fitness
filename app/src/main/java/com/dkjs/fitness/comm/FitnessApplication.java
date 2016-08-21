package com.dkjs.fitness.comm;

import android.app.Application;
import android.content.Context;

import com.dkjs.fitness.domain.UserAct;
import com.dkjs.fitness.util.LogUtil;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.maxleap.GetCallback;
import com.maxleap.MLDataManager;
import com.maxleap.MLObject;
import com.maxleap.MLUser;
import com.maxleap.MaxLeap;
import com.maxleap.exception.MLException;
import com.maxleap.im.DataHandler;
import com.maxleap.im.MLParrot;
import com.maxleap.im.ParrotException;
import com.maxleap.social.MLHermes;

/**
 * Created by administrator on 16/6/21.
 */
public class FitnessApplication extends Application {

    private static final String TAG = "FitnessApplication";
    private static Context mContext;

    private static MLParrot mGlobalParrot;


    @Override
    public void onCreate() {
        super.onCreate();

        mContext = this;

        MaxLeap.setLogLevel(MaxLeap.LOG_LEVEL_ERROR);


        MLObject.registerSubclass(UserAct.class);
        MaxLeap.initialize(this, AppConfig.ML_APP_ID,
                AppConfig.ML_API_KEY, MaxLeap.REGION_CN);

        LogUtil.e(TAG, "***************************FitnessApplication");

        //Maxleap测试
        MLDataManager.fetchInBackground(MLObject.createWithoutData("foobar", "123"),
                new GetCallback<MLObject>() {
                    @Override
                    public void done(MLObject mlObject, MLException e) {
                        if (e != null && e.getCode() == MLException.INVALID_OBJECT_ID) {
                            LogUtil.e(TAG, "***************************SDK sucess");
                        } else {
                            LogUtil.e(TAG, "***************************SDK failed");
                        }
                    }
                });


        MLHermes.initialize(this, AppConfig.ML_APP_ID,
                AppConfig.ML_API_KEY);
        mGlobalParrot = MLParrot.getInstance();

        if(GlobalUserManager.isUserLogin()){
            MLUser currentUser = MLUser.getCurrentUser();
            MLHermes.setSessionToken(currentUser.getSessionToken());
            mGlobalParrot.initWithCustomAccount(AppConfig.ML_APP_ID, AppConfig.ML_API_KEY,
                    currentUser.getSessionToken(), null);
            mGlobalParrot.login(new DataHandler<String>() {
                @Override
                public void onSuccess(String s) {
                    LogUtil.e(TAG, "***************************IM login success");
                }

                @Override
                public void onError(ParrotException e) {
                    LogUtil.e(TAG, "***************************IM login failed");
                }
            });

        } else {
            //
        }


        /** 初始化fresco图片加载库 */
        Fresco.initialize(this);



    }

    public static Context getGlobalContext() {
        return mContext;
    }

    public static MLParrot getmGlobalParrot() {
        return mGlobalParrot;
    }
}
