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
import com.maxleap.social.MLHermes;

/**
 * Created by administrator on 16/6/21.
 */
public class FitnessApplication extends Application {

    private static final String TAG = "FitnessApplication";
    private Context mContext;


    @Override
    public void onCreate() {
        super.onCreate();

        mContext = this;

        MaxLeap.setLogLevel(MaxLeap.LOG_LEVEL_ERROR);


        MLObject.registerSubclass(UserAct.class);
        MaxLeap.initialize(this, "57807296a5ff7f00013e797b",
                "ZXNhTWdKWk1xUWVXUEVQakZxVnpNUQ", MaxLeap.REGION_CN);


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


        MLHermes.initialize(this, "57807296a5ff7f00013e797b",
                "ZXNhTWdKWk1xUWVXUEVQakZxVnpNUQ");
        MLUser currentUser = MLUser.getCurrentUser();
        if(currentUser != null){
            MLHermes.setSessionToken(currentUser.getSessionToken());
        } else {
            //
        }


        /** 初始化fresco图片加载库 */
        Fresco.initialize(this);



    }

    public Context getGlobalContext() {
        return mContext;
    }


}
