package com.dkjs.fitness.domain;

import java.io.Serializable;

/**
 * Created by administrator on 16/8/7.
 */
public class User implements Serializable{

    private String userId;
    private String nickName;
    private String portrait;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }
}
