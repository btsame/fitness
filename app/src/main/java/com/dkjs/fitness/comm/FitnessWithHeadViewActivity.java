package com.dkjs.fitness.comm;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.dkjs.fitness.R;
import com.dkjs.fitness.dialog.LoadingDialog;

import org.w3c.dom.Text;

/**
 * Created by administrator on 16/6/21.
 */
public class FitnessWithHeadViewActivity extends AppCompatActivity {

    public static final int SHOW_TOAST_MSG = 100;
    private long clickTime = 0;              //记录第一次点击的时间

    private LoadingDialog mLoadingDialog;
    protected Context mContext;

    ImageButton ibBack;
    TextView tvAction,tvTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        
        initTitleView();
    }

    private void initTitleView() {
        super.setContentView(R.layout.layout_common_head);
        ibBack= (ImageButton) findViewById(R.id.left_button);
        tvAction= (TextView) findViewById(R.id.right_text);
        tvTitle= (TextView) findViewById(R.id.tv_title);
    }

    protected void initView(){}
    protected void setListener(){}

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    /*Toast提示处理*/
    @SuppressLint("HandlerLeak")
    public Handler mToastHandler = new Handler(){
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_TOAST_MSG:
                    String str = (String)msg.obj;
                    if(!TextUtils.isEmpty(str)){
                        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
                    }
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 退出应用
     */
    public void exitApp() {
        if ((System.currentTimeMillis() - clickTime) > 2000) {
            Toast.makeText(this, "再按一次后退键退出程序", Toast.LENGTH_SHORT).show();
            clickTime = System.currentTimeMillis();
        } else {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
            System.exit(0);
        }
    }

    protected void showLoadingDialog(){
        if(mLoadingDialog == null){
            mLoadingDialog = new LoadingDialog.Builder(mContext)
                    .setCancelable(true)
                    .setCanceledOnTouchOutside(false)
                    .setDuration(90)
                    .setExternalRadius(45)
                    .create();
        }

        mLoadingDialog.show();
    }

    protected void dismissLoadingDialog(){
        if(mLoadingDialog == null) return;

        if(mLoadingDialog.isShowing()) mLoadingDialog.dismiss();
    }


    public void back(View v) {
        
    }

    public void action(View v) {

    }


    protected void showBackwardView(int backwardResid, boolean show) {
        if (ibBack != null) {
            if (show) {
                ibBack.setVisibility(View.VISIBLE);
            } else {
                ibBack.setVisibility(View.INVISIBLE);
            }
        } // else ignored
    }

    protected void showRightView(int forwardResId, boolean show) {
        if (tvAction != null) {
            if (show) {
                tvAction.setVisibility(View.VISIBLE);
                tvAction.setText(forwardResId);
            } else {
                tvAction.setVisibility(View.INVISIBLE);
            }
        } // else ignored
    }
    protected void onBackward(View backwardView) {

    }

    /**
     * 提交按钮点击后触发
     * @param forwardView
     */
  /*  protected void onForward(View forwardView) {
        @Override
        public void setContentView(int layoutResID) {
            mContentLayout.removeAllViews();
            View.inflate(this, layoutResID, mContentLayout);
            onContentChanged();
        }
    }*/

}
