package com.dkjs.fitness.comm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.dkjs.fitness.util.LogUtil;
import com.maxleap.MLUser;
import com.maxleap.social.MLHermes;

/**
 * Created by administrator on 16/7/30.
 */
public class LoginBroadcastReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        LogUtil.d("LoginBroadcastReceiver", "登录成功");


        MLHermes.setSessionToken(MLUser.getCurrentUser().getSessionToken());
    }
}
