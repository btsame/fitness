package com.dkjs.fitness.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.dkjs.fitness.R;
import com.dkjs.fitness.comm.FitnessFragment;
import com.dkjs.fitness.mine.UserInfoActivity;

/**
 * Created by administrator on 16/7/10.
 */
public class MineFragment extends FitnessFragment {

    Button editUserInfo;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_mine_vipuser,container,false);
        editUserInfo= (Button) view.findViewById(R.id.btn_editUserInfo);
        editUserInfo();
        return view;
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
