package com.dkjs.fitness.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dkjs.fitness.MainActivity;
import com.dkjs.fitness.R;
import com.dkjs.fitness.comm.FitnessActivity;
import com.maxleap.LogInCallback;
import com.maxleap.MLUser;
import com.maxleap.MLUserManager;
import com.maxleap.exception.MLException;
import com.maxleap.utils.ToastUtils;

/**
 * Created by administrator on 16/7/9.
 */
public class ResigterAndLoginActivity extends FitnessActivity {

    //账号 密码输入框
    EditText etAccount,etPwd;

    //账号 密码
    String account,password;

    //登录按钮
    Button btnLogin;

    //注册账号 忘记密码
    TextView tvRegister,tvForget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resigterandloginactivity);

        //初始化控件
        initView();
    }

    private void initView() {
        etAccount= (EditText) findViewById(R.id.et_account);
        etPwd= (EditText) findViewById(R.id.et_pwd);
        btnLogin= (Button) findViewById(R.id.btn_login);
        tvRegister= (TextView) findViewById(R.id.tv_register);
        tvForget= (TextView) findViewById(R.id.tv_forget);
    }

    //登录
    public void login(View view){
        account=etAccount.getText().toString();
        password=etPwd.getText().toString();

        MLUserManager.logInInBackground(account, password, new LogInCallback() {
            @Override
            public void done(MLUser mlUser, MLException e) {
                if (mlUser!=null){
                    //登录成功
                    ToastUtils.showToast(ResigterAndLoginActivity.this,"登录成功");
                    startActivity(new Intent(ResigterAndLoginActivity.this, MainActivity.class));

                }else{

                    //登录失败
                    ToastUtils.showToast(ResigterAndLoginActivity.this,"登录失败");
                }
            }
        });

    }

    //注册账号

    public void register(View view){

        startActivity(new Intent(ResigterAndLoginActivity.this,RegisterActivity.class));

    }


    public void forgetPassword(View view){
        startActivity(new Intent(ResigterAndLoginActivity.this,ForgetPasswordActivity.class));
    }



}
