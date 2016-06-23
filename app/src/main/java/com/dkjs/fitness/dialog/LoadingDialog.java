package com.dkjs.fitness.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import com.dkjs.fitness.R;

import me.wangyuwei.loadingview.LoadingView;

/**
 * Created by administrator on 16/6/22.
 */
public class LoadingDialog extends AlertDialog{

    Context mContext;
    LoadingView mLoadingView;
    Parameters mParameters;

    private LoadingDialog(Context context) {
        this(context, R.style.dm_alert_dialog);
    }

    private LoadingDialog(Context context, int themeResId) {
        super(context, themeResId);

        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialog_loading);
        mLoadingView = (LoadingView) findViewById(R.id.lv_loading_dialog);
        if(mParameters != null){
            mLoadingView.setDuration(mParameters.duration);
            mLoadingView.setExternalRadius(mParameters.externalRadius);
            mLoadingView.setInternalRadius(mParameters.internalRadius);
            setCanceledOnTouchOutside(mParameters.canceledOnTouchOutside);
            setCancelable(mParameters.cancelable);
        }
    }

    private LoadingDialog apply(Parameters parameters){
        mParameters = parameters;
        return this;
    }


    public static class Builder{

        Context mContext;
        Parameters mParameters;
        public Builder(Context context) {
            mContext = context;
            mParameters = new Parameters();
        }

        public Builder setCancelable(boolean cancelable){
            mParameters.cancelable = cancelable;
            return this;
        }

        public Builder setCanceledOnTouchOutside(boolean canceledOnTouchOutside){
            mParameters.canceledOnTouchOutside = canceledOnTouchOutside;
            return this;
        }

        public Builder setExternalRadius(int progress){
            mParameters.externalRadius = progress;
            return this;
        }

        public Builder setInternalRadius(int progress){
            mParameters.internalRadius = progress;
            return this;
        }

        public Builder setDuration(int progress){
            mParameters.duration = progress;
            return this;
        }

        public LoadingDialog create(){
            return new LoadingDialog(mContext).apply(mParameters);
        }
    }

    private static class Parameters{
        boolean cancelable = true;
        boolean canceledOnTouchOutside = false;
        int externalRadius = 600, internalRadius = 30, duration = 1000;
    }
}
