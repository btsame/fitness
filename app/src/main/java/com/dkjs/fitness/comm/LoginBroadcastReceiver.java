package com.dkjs.fitness.comm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.dkjs.fitness.util.LogUtil;
import com.maxleap.MLUser;
import com.maxleap.im.DataHandler;
import com.maxleap.im.ParrotException;
import com.maxleap.social.MLHermes;

/**
 * Created by administrator on 16/7/30.
 */
public class LoginBroadcastReceiver extends BroadcastReceiver{

    private static final String TAG = "LoginBroadcastReceiver";

    public static final String Login_Action = "com.dkjs.fitness.login";
    @Override
    public void onReceive(Context context, Intent intent) {
        LogUtil.d("LoginBroadcastReceiver", "********************登录成功");
        LogUtil.d("LoginBroadcastReceiver", "sessionToken:" + GlobalUserManager.getUserSessionToken());
        LogUtil.d("LoginBroadcastReceiver", "userId:" + GlobalUserManager.getUserId());


        MLHermes.setSessionToken(MLUser.getCurrentUser().getSessionToken());

        FitnessApplication.getmGlobalParrot().initWithCustomAccount(
                AppConfig.ML_APP_ID, AppConfig.ML_API_KEY, GlobalUserManager.getUserId(), null);
        FitnessApplication.getmGlobalParrot().login(new DataHandler<String>() {
            @Override
            public void onSuccess(String s) {
                LogUtil.e(TAG, "***************************IM login success");
            }

            @Override
            public void onError(ParrotException e) {
                LogUtil.e(TAG, "***************************IM login failed");
            }
        });
    }
}
