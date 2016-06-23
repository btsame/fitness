package com.dkjs.fitness;

import android.os.Bundle;
import android.view.View;

import com.dkjs.fitness.comm.FitnessActivity;

public class MainActivity extends FitnessActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoadingDialog();
            }
        });
    }
}
