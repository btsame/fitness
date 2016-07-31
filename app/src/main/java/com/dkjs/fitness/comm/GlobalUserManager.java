package com.dkjs.fitness.comm;

import com.maxleap.MLAnonymousUtils;
import com.maxleap.MLUser;

/**
 * Created by administrator on 16/7/31.
 */
public class GlobalUserManager {

    public static boolean isUserLogin(){
        if(MLUser.getCurrentUser() != null ||
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
            return MLUser.getCurrentUser().getString("objectId");
        }

        return null;
    }
}
