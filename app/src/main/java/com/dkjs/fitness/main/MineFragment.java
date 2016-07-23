package com.dkjs.fitness.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dkjs.fitness.R;
import com.dkjs.fitness.comm.FitnessFragment;
import com.dkjs.fitness.mine.ActivityCreateActivity;
import com.dkjs.fitness.mine.ActivityManagerActivity;
import com.dkjs.fitness.mine.UserInfoActivity;

/**
 * Created by administrator on 16/7/10.
 */
public class MineFragment extends FitnessFragment {

    Button editUserInfo;
    LinearLayout lvCreate,lvManager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_mine_vipuser,container,false);

        //初始化控件
        initView(view);
        //编辑个人资料
        editUserInfo();
        //发起活动
        createActivity();
        //管理活动
        managerActivity();

        return view;
    }

    private void managerActivity() {
        lvCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ActivityManagerActivity.class));
            }
        });
    }

    private void createActivity() {
        lvManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),ActivityCreateActivity.class));
            }
        });
    }

    public void initView(View view){
        editUserInfo= (Button) view.findViewById(R.id.btn_editUserInfo);
        lvCreate= (LinearLayout) view.findViewById(R.id.iv_create_activity);
        lvManager=(LinearLayout) view.findViewById(R.id.iv_managerActivity);
    }

    private void editUserInfo(){
        editUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), UserInfoActivity.class));
            }
        });
    }

}
