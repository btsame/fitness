package com.dkjs.fitness.mine;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.dkjs.fitness.R;
import com.dkjs.fitness.comm.FitnessActivity;
import com.dkjs.fitness.util.ToastUtils;

public class SetPriceActivity extends FitnessActivity {

    EditText etPrice;
    String price;
    TextView tvPrice1,tvPrice2,tvPrice3,tvPrice4,tvPrice5,tvPrice6,tvPriceSum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_price);
        initView();


    }


    //保存

    public void OnSave(View view){
        price=etPrice.getText().toString();

        if (!TextUtils.isEmpty(price)&&Integer.parseInt(price)>0){
            Intent intent=new Intent();
            intent.putExtra("result",price);
            SetPriceActivity.this.setResult(RESULT_OK,intent);
            SetPriceActivity.this.finish();
        }else{
            ToastUtils.showCustomToast(this,"请输入活动价格");
            return;
        }


    }

    @Override
    protected void initView() {
        super.initView();
        etPrice= (EditText) findViewById(R.id.et_act_price);

        tvPrice1= (TextView) findViewById(R.id.tv_act_price);
        tvPrice2= (TextView) findViewById(R.id.tv_act_price2);
        tvPrice3= (TextView) findViewById(R.id.tv_act_price3);
        tvPrice4= (TextView) findViewById(R.id.tv_act_price4);
        tvPrice5= (TextView) findViewById(R.id.tv_act_price5);
        tvPrice6= (TextView) findViewById(R.id.tv_act_price6);
        tvPriceSum= (TextView) findViewById(R.id.tv_act_price_sum);
    }

    //计算价格

    public void OnCalculePrice(View view){

        price=etPrice.getText().toString();
        if(!TextUtils.isEmpty(price)&&Integer.parseInt(price)>0){

            tvPrice1.setText(price+"元/人");
            tvPrice2.setText(Integer.parseInt(price)*0.8+"元/人");
            tvPrice3.setText(Integer.parseInt(price)*0.7+"元/人");
            tvPrice4.setText(Integer.parseInt(price)*0.6+"元/人");
            tvPrice5.setText(Integer.parseInt(price)*0.3+"元/人");
            tvPrice6.setText(Integer.parseInt(price)*0.1+"元/人");
            tvPriceSum.setText(Integer.parseInt(price)*3.5+"元/人");

        }else{
            ToastUtils.showCustomToast(this,"请输入活动价格");
            return;
        }
    }
}
