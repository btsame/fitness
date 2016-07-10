package com.dkjs.fitness.domain;

import com.maxleap.MLClassName;
import com.maxleap.MLObject;

/**
 * Created by administrator on 16/7/9.
 */

@MLClassName("accountProfile")
public class AccountProfile extends MLObject{

    private int sex;

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }
}
