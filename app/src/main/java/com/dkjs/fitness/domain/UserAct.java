package com.dkjs.fitness.domain;

import com.maxleap.MLClassName;
import com.maxleap.MLObject;

/**
 * Created by administrator on 16/8/7.
 */
@MLClassName("UserAct")
public class UserAct extends MLObject {
    private String userId;
    private String nickname;
    private String portrait;
    private String actId;
    private boolean isPay;

    public String getUserId() {
        return getString("userId");
    }

    public void setUserId(String userId) {
        put("userId", userId);
    }

    public String getActId() {
        return getString("actId");
    }

    public void setActId(String actId) {
        put("actId", actId);
    }

    public boolean isPay() {
        return getBoolean("isPay");
    }

    public void setIsPay(boolean isPay) {
        put("isPay", isPay);
    }

    public String getNickname() {
        return getString("nickname");
    }

    public void setNickname(String nickname) {
        put("nickname", nickname);
    }

    public String getPortrait() {
        return getString("portrait");
    }

    public void setPortrait(String portrait) {
        put("portrait", portrait);
    }
}
