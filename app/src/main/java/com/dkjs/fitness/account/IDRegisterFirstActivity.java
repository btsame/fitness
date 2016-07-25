package com.dkjs.fitness.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.dkjs.fitness.R;
import com.dkjs.fitness.comm.FitnessActivity;

/**
 * Created by Administrator on 2016/7/24.
 */
public class IDRegisterFirstActivity extends FitnessActivity {

    TextView tvNextStep;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cfirst);


        initView();

        tvNextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(IDRegisterFirstActivity.this,IDRegisterSecondActivity.class));
            }
        });
    }

    @Override
    protected void initView() {
        tvNextStep= (TextView) findViewById(R.id.next_temp);
    }
}
