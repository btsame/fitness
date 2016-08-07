package com.dkjs.fitness.domain;

import android.os.Parcel;
import android.os.Parcelable;

import com.maxleap.social.entity.ShuoShuo;

import java.io.Serializable;

/**
 * Created by administrator on 16/7/17.
 */
public class FTActivity implements Serializable{

    public static final int SOURCE_TYPE_PIC = 1;
    public static final int SOURCE_TYPE_VIDEO = 2;
    public static final int PIC_WIDTH = 800;
    public static final int PIC_HEIGHT = 400;


    private String subject;
    private int totalNum;
    private String address;
    private String selfEquipment;
    private String intruction;

    private String beginTime;
    private String endTime;
    private String city;
    private float price;
    private int showerAndLocker;    //0都不提供；1只提供沐浴；2只提供锁柜；3都提供
    private int actType;    //0户外；1室内


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

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getShowerAndLocker() {
        return showerAndLocker;
    }

    public void setShowerAndLocker(int showerAndLocker) {
        this.showerAndLocker = showerAndLocker;
    }

    public int getActType() {
        return actType;
    }

    public void setActType(int actType) {
        this.actType = actType;
    }
}
