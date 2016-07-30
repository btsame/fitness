package com.dkjs.fitness.domain;

import com.maxleap.social.entity.ShuoShuo;

/**
 * Created by administrator on 16/7/17.
 */
public class FTActivity {

    public static final int SOURCE_TYPE_PIC = 1;
    public static final int SOURCE_TYPE_VIDEO = 2;

    private String subject;
    private int totalNum;
    private String address;
    private String selfEquipment;
    private String intruction;


    private int sourceType; //1图片；2视频；
    private String sourceUrl;

    private transient ShuoShuo shuoShuo;

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSelfEquipment() {
        return selfEquipment;
    }

    public void setSelfEquipment(String selfEquipment) {
        this.selfEquipment = selfEquipment;
    }

    public String getIntruction() {
        return intruction;
    }

    public void setIntruction(String intruction) {
        this.intruction = intruction;
    }

    public int getSourceType() {
        return sourceType;
    }

    public void setSourceType(int sourceType) {
        this.sourceType = sourceType;
    }

    public ShuoShuo getShuoShuo() {
        return shuoShuo;
    }

    public void setShuoShuo(ShuoShuo shuoShuo) {
        this.shuoShuo = shuoShuo;
    }
}
