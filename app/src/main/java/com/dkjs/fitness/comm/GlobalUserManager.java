package com.dkjs.fitness.comm;

import com.maxleap.MLAnonymousUtils;
import com.maxleap.MLUser;

/**
 * Created by administrator on 16/7/31.
 */
public class GlobalUserManager {

    public static boolean isUserLogin(){
        if(MLUser.getCurrentUser() != null &&
                !MLAnonymousUtils.isLinked(MLUser.getCurrentUser())){
            return true;
        }

        return false;
    }

    public static String getUserSessionToken(){
        if(isUserLogin()){
            return MLUser.getCurrentUser().getSessionToken();
        }

        return null;
    }

    public static String getUserId(){
        if(isUserLogin()){
            return MLUser.getCurrentUser().getSessionToken();
        }

        return null;
    }

    public static String getNickName(){
        if(isUserLogin()){
            return MLUser.getCurrentUser().getUserName();
        }

        return null;
    }

    public static String getPortrait(){
        if(isUserLogin()){
            return MLUser.getCurrentUser().getString("portrait");
        }

        return null;
    }
}
