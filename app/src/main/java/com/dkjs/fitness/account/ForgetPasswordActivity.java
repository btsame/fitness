package com.dkjs.fitness.account;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.dkjs.fitness.R;
import com.dkjs.fitness.comm.FitnessActivity;
import com.dkjs.fitness.util.AccoutUtil;
import com.maxleap.MLUser;
import com.maxleap.MLUserManager;
import com.maxleap.RequestSmsCodeCallback;
import com.maxleap.SignUpCallback;
import com.maxleap.VerifySmsCodeCallback;
import com.maxleap.exception.MLException;
import com.maxleap.utils.ToastUtils;

import java.util.Timer;
import java.util.TimerTask;

public class ForgetPasswordActivity extends FitnessActivity implements View.OnClickListener {
    public EditText editUserName, editPwd, editYzm, editReconfrim;
    private Button btn_getYzm, register_commit;
    //账号 密码 验证码
    private String userName, passWord, authCode, reConfrim;
    //标记验证码是否合法
    int authTag;

    String yzm = null;
    private int time = 60;
    private Timer timer = new Timer();
    TimerTask task;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        initView();
        register_commit.setOnClickListener(this);
        btn_getYzm.setOnClickListener(this);

    }


    @Override
    protected void initView() {
        editUserName = (EditText) findViewById(R.id.edit_phone_user);
        editPwd = (EditText) findViewById(R.id.edit_phone_pwd);
        editYzm = (EditText) findViewById(R.id.edit_reister_phone_auth);
        editReconfrim= (EditText) findViewById(R.id.edit_reconfrim_phone_pwd);
        btn_getYzm = (Button) findViewById(R.id.btn_reister_getphone_auth);
        register_commit = (Button) findViewById(R.id.register_phone);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_reister_getphone_auth: // 发送验证码
                if (AccoutUtil.checkPhone(ForgetPasswordActivity.this, editUserName) && AccoutUtil.checkPassword(ForgetPasswordActivity.this, editUserName, editPwd) && AccoutUtil.checkRePassWord(ForgetPasswordActivity.this, editPwd, editReconfrim)) {
                    new Send_YzmMessage().execute();
                    MLUserManager.requestSmsCodeInBackground(editUserName.getText().toString(), new RequestSmsCodeCallback() {
                        @Override
                        public void done(final MLException e) {
                            if (e != null) {
                                //  发生错误
                                ToastUtils.showToast(ForgetPasswordActivity.this, "发送失败");
                            } else {
                                ToastUtils.showToast(ForgetPasswordActivity.this, "发送成功");
                            }
                        }
                    });
                }
                break;
            case R.id.register_phone:// 注册按钮事件

                userName = editUserName.getText().toString();
                passWord = editPwd.getText().toString();
                yzm = editYzm.getText().toString();
                reConfrim=editReconfrim.getText().toString();

                if (AccoutUtil.checkPhone(ForgetPasswordActivity.this, editUserName) && AccoutUtil.checkPassword(ForgetPasswordActivity.this, editUserName, editPwd) && checkAuth() && AccoutUtil.checkRePassWord(ForgetPasswordActivity.this, editPwd, editReconfrim)) {

                    MLUser user = new MLUser();
                    user.setUserName(userName);
                    user.setPassword(passWord);
                    MLUserManager.signUpInBackground(user, new SignUpCallback() {
                        @Override
                        public void done(MLException e) {
                            if (e == null) {
                                ToastUtils.showToast(ForgetPasswordActivity.this, "重置成功");
                                startActivity(new Intent(ForgetPasswordActivity.this, RegisterAndLoginActivity.class));
                            } else {
                                ToastUtils.showToast(ForgetPasswordActivity.this, "重置失败");
                            }
                        }
                    });
                } else {

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
                                btn_getYzm.setBackgroundResource(R.drawable.btn_register_selector);
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
