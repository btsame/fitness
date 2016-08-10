package com.dkjs.fitness.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.dkjs.fitness.R;
import com.dkjs.fitness.comm.FitnessActivity;

/**
 * Created by Administrator on 2016/8/3.
 */
public class MineMoneyActivity extends FitnessActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_income);
    }


    //提现

    public void withDraw(View view){
        startActivity(new Intent(MineMoneyActivity.this,WithDrawActivity.class));
    }
}
