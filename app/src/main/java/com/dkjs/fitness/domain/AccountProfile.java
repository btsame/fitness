package com.dkjs.fitness.domain;

import com.maxleap.MLClassName;
import com.maxleap.MLObject;

/**
 * Created by administrator on 16/7/9.
 */

@MLClassName("accountProfile")
public class AccountProfile extends MLObject{

    private int sex;
    private int height;
    private int weight;

    public int getSex() {
        return getInt("sex");
    }

    public void setSex(int sex) {
        put("sex", sex);
    }

    public int getHeight() {
        return getInt("height");
    }

    public void setHeight(int height) {
        put("height", height);
    }

    public int getWeight() {
        return getInt("weight");
    }

    public void setWeight(int weight) {
        put("weight", weight);
    }
}
