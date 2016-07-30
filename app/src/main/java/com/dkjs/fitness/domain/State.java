package com.dkjs.fitness.domain;

import com.maxleap.MLClassName;
import com.maxleap.MLGeoPoint;
import com.maxleap.MLObject;

/**
 * Created by administrator on 16/7/17.
 */
public class State {

    protected String sourceUrl;
    protected int type;
    protected String desc;
    protected MLGeoPoint loc;

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public MLGeoPoint getLoc() {
        return loc;
    }

    public void setLoc(MLGeoPoint loc) {
        this.loc = loc;
    }
}
