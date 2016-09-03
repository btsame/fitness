package com.dkjs.fitness.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dkjs.fitness.R;
import com.dkjs.fitness.account.RegisterAndLoginActivity;
import com.dkjs.fitness.comm.FitnessFragment;
import com.dkjs.fitness.mine.ActivityCreateActivity;
import com.dkjs.fitness.mine.ActivityCreateActivity1;
import com.dkjs.fitness.mine.ActivityManagerActivity;
import com.dkjs.fitness.mine.AuthIDActivity;
import com.dkjs.fitness.mine.CheckOutTicketsActivity;
import com.dkjs.fitness.mine.MineMoneyActivity;
import com.dkjs.fitness.mine.SettingsActivity;
import com.dkjs.fitness.mine.UserInfoActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by administrator on 16/7/10.
 */
public class MineFragment extends FitnessFragment implements View.OnClickListener {

    @Bind(R.id.iv_accout_icon)
    ImageView portraitIV;
    TextView editUserInfo;
    LinearLayout llCreate, llManager, llSettings, llAuthID,llCheckTicket;

    @Bind(R.id.ll_my_money)
    LinearLayout llMyMoney;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine_vipuser, container, false);
        ButterKnife.bind(this, view);

        //初始化控件
        initView(view);
        setListener();
        //编辑个人资料
        editUserInfo();
        //发起活动
        createActivity();
        //管理活动
        managerActivity();
        //验票
        checkTicket();

        //设置管理
        managerSettings();

        //我的钱包
        goHeadMyMoney();

        //身份认证
        authID();

        //活动指南


        return view;
    }

    private void checkTicket() {

        llCheckTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CheckOutTicketsActivity.class));
            }
        });
    }

    private void authID() {
        llAuthID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AuthIDActivity.class));
            }
        });
    }

    private void goHeadMyMoney() {
        llMyMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MineMoneyActivity.class));
            }
        });
    }

    private void managerSettings() {
        llSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SettingsActivity.class));
            }
        });
    }

    private void managerActivity() {
        llManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ActivityManagerActivity.class));
            }
        });
    }

    private void createActivity() {
        llCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ActivityCreateActivity1.class));
            }
        });
    }

    public void initView(View view) {
        editUserInfo = (TextView) view.findViewById(R.id.btn_editUserInfo);
        llCreate = (LinearLayout) view.findViewById(R.id.iv_create_activity);
        llManager = (LinearLayout) view.findViewById(R.id.iv_managerActivity);
        llSettings = (LinearLayout) view.findViewById(R.id.lv_settings);
        llAuthID = (LinearLayout) view.findViewById(R.id.layout_auth);
        llCheckTicket=(LinearLayout) view.findViewById(R.id.ll_check_out_ticket);
    }

    private void setListener() {
        portraitIV.setOnClickListener(this);
    }

    private void editUserInfo() {
        editUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), UserInfoActivity.class));
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == portraitIV) {
            startActivity(new Intent(mContext, RegisterAndLoginActivity.class));
        }
    }
}
