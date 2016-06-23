package com.dkjs.fitness.comm;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.dkjs.fitness.dialog.LoadingDialog;

/**
 * Created by administrator on 16/6/21.
 */
public class FitnessActivity extends FragmentActivity {

    private LoadingDialog mLoadingDialog;
    Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    protected void showLoadingDialog(){
        if(mLoadingDialog == null){
            mLoadingDialog = new LoadingDialog(mContext);
        }

        mLoadingDialog.show();
    }
}
