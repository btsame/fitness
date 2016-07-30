package com.dkjs.fitness.account;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.dkjs.fitness.R;
import com.dkjs.fitness.comm.FitnessActivity;
import com.dkjs.fitness.util.AccoutUtil;
import com.dkjs.fitness.util.GetSmsContent;
import com.maxleap.MLUser;
import com.maxleap.MLUserManager;
import com.maxleap.RequestSmsCodeCallback;
import com.maxleap.SignUpCallback;
import com.maxleap.VerifySmsCodeCallback;
import com.maxleap.exception.MLException;
import com.maxleap.utils.ToastUtils;

import java.util.Timer;
import java.util.TimerTask;


public class RegisterActivity extends FitnessActivity implements OnClickListener {
    public EditText edit_user, edit_pwd, edit_yzm;
    private Button btn_getYzm, register_commit;
    CheckBox chek_xianyi;
    //账号 密码 验证码
    private String userName, password, authCode;
    //标记验证码是否合法
    int authTag;

    String yzm = null;
    private int time = 60;
    private Timer timer = new Timer();
    TimerTask task;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        register_commit.setOnClickListener(this);
        btn_getYzm.setOnClickListener(this);

    }


    @Override
    protected void initView() {
        edit_user = (EditText) findViewById(R.id.edit_phone_user);
        edit_pwd = (EditText) findViewById(R.id.edit_phone_pwd);
        edit_yzm = (EditText) findViewById(R.id.edit_reister_phone_auth);
        btn_getYzm = (Button) findViewById(R.id.btn_reister_getphone_auth);
        register_commit = (Button) findViewById(R.id.register_phone);
        chek_xianyi = (CheckBox) findViewById(R.id.register_chkphone_enoughAge);
        authCode=edit_yzm.getText().toString();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_reister_getphone_auth: // 发送验证码
                if (AccoutUtil.checkPhone(RegisterActivity.this, edit_user) && AccoutUtil.checkPassword(RegisterActivity.this, edit_user, edit_pwd)
                        ) {
                    new Send_YzmMessage().execute();
                    MLUserManager.requestSmsCodeInBackground(userName, new RequestSmsCodeCallback() {
                        @Override
                        public void done(final MLException e) {
                            if (e != null) {
                                //  发生错误
                                ToastUtils.showToast(RegisterActivity.this, "发送失败");
                            } else {
                                ToastUtils.showToast(RegisterActivity.this, "发送成功");
                            }
                        }
                    });
                }
                break;
            case R.id.register_phone:// 注册按钮事件
                userName=edit_user.getText().toString();
                password=edit_pwd.getText().toString();
                yzm=edit_yzm.getText().toString();
                if (AccoutUtil.checkPhone(RegisterActivity.this, edit_user) && AccoutUtil.checkPassword(RegisterActivity.this, edit_user, edit_pwd) && checkAgree() && checkAuth()) {
                    System.out.print("用户名"+userName+"密码"+password+"验证码"+yzm);
                    MLUser user = new MLUser();
                    user.setUserName(userName);
                    user.setPassword(password);
                    MLUserManager.signUpInBackground(user, new SignUpCallback() {
                        @Override
                        public void done(MLException e) {
                            if (e == null) {
                                ToastUtils.showToast(RegisterActivity.this, "注册成功");
                                startActivity(new Intent(RegisterActivity.this,RegisterAndLoginActivity.class));
                            } else {
                                ToastUtils.showToast(RegisterActivity.this, "注册失败");
                            }
                        }
                    });
                } else {
                    System.out.print("hello"+"用户名"+userName+"密码"+password+"验证码"+yzm);
                }
                break;
        }

    }
    //
    private Boolean checkAuth() {
        MLUserManager.verifySmsCodeInBackground(userName, authCode, new VerifySmsCodeCallback() {
            @Override
            public void done(final MLException e) {
                if (e != null) {
                    //  发生错误
                    authTag = 0;
                } else {
                    authTag = 1;
                }
            }
        });
        if (authTag == 1) {
            return true;
        } else {
            return false;
        }

    }

    private Boolean checkAgree() {
        if (chek_xianyi.isChecked()) {
            return true;
        } else {
            ToastUtils.showToast(RegisterActivity.this, "请同意用户注册协议");
            return false;
        }
    }


    class Send_YzmMessage extends AsyncTask<Integer, Integer, Integer> {

        private String message;

        @Override
        protected Integer doInBackground(Integer... params) {
            return 1;
        }

        @Override
        protected void onPostExecute(Integer result) {
            btn_getYzm.setEnabled(false);
            btn_getYzm.setBackgroundResource(R.drawable.daojishi);
            task = new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() { // UI thread
                        @Override
                        public void run() {
                            if (time <= 0) {
                                // 当倒计时小余=0时记得还原图片，可以点击
                                btn_getYzm.setEnabled(true);
                                btn_getYzm.setBackgroundResource(R.drawable.gray_button_selector);
                                btn_getYzm.setTextColor(Color.parseColor("#454545"));
                                btn_getYzm.setText("获取验证码");
                                task.cancel();
                            } else {
                                btn_getYzm.setText(time + "秒后重试");
                                btn_getYzm.setTextColor(Color.rgb(125, 125, 125));
                            }
                            time--;
                        }
                    });
                }
            };

            time = 60;
            timer.schedule(task, 0, 1000);
        }
    }


}

