package com.dkjs.fitness.domain;

import com.maxleap.MLClassName;
import com.maxleap.MLGeoPoint;
import com.maxleap.MLObject;

/**
 * Created by administrator on 16/7/17.
 */
@MLClassName("state")
public class State extends MLObject {

    protected String sourceUrl;
    protected int type;
    protected String desc;
    protected MLGeoPoint loc;

    public String getSourceUrl() {
        return getString("sourceUrl");
    }

    public void setSourceUrl(String sourceUrl) {
        put("sourceUrl", sourceUrl);
    }

    public int getType() {
        return getInt("type");
    }

    public void setType(int type) {
        put("type", type);
    }

    public String getDesc() {
        return getString("desc");
    }

    public void setDesc(String desc) {
        put("desc", desc);
    }

    public MLGeoPoint getLoc() {
        return getMLGeoPoint("loc");
    }

    public void setLoc(MLGeoPoint loc) {
        put("loc", loc);
    }
}
