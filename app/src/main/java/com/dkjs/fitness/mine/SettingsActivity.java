package com.dkjs.fitness.mine;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.dkjs.fitness.R;
import com.dkjs.fitness.comm.FitnessActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SettingsActivity extends FitnessActivity implements View.OnClickListener {

    //意见反馈
    @Bind(R.id.yjfk_layout)
    RelativeLayout rlFankui;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);

        setListener();
    }


    @Override
    protected void setListener() {
        super.setListener();
        rlFankui.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==rlFankui){
            startActivity(new Intent(SettingsActivity.this,FankuiActivity.class));
        }
    }
}
