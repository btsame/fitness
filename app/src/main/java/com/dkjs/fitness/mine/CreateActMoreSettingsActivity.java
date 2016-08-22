package com.dkjs.fitness.mine;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dkjs.fitness.R;
import com.dkjs.fitness.comm.FitnessActivity;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnItemClickListener;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CreateActMoreSettingsActivity extends FitnessActivity {

    @Bind(R.id.tv_act_sex)
    TextView tvSetSex;

    @Bind(R.id.tv_act_shower)
    TextView tvSetShower;

    public static String[] actSexStrs = new String[]{"没有限制", "仅限男生","仅限女生"};
    public static String[] showerLockerStrs = new String[]{"不提供沐浴／锁柜", "只提供沐浴", "只提供锁柜", "提供沐浴／锁柜"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_act_more_settings);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected void initView() {
        super.initView();


    }


    public void OnSetSex(View view){
        DialogPlus dialogPlus = DialogPlus.newDialog(mContext)
                .setGravity(Gravity.CENTER)
                .setCancelable(true)
                .setAdapter(new ArrayAdapter<String>(mContext,
                        android.R.layout.simple_list_item_1, actSexStrs))
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                        tvSetSex.setText(actSexStrs[position]);
                        tvSetSex.setTag(position);
                        dialog.dismiss();
                    }
                })
                .create();
        dialogPlus.show();
    }


    public void OnSetShower(View view){
        DialogPlus dialogPlus = DialogPlus.newDialog(mContext)
                .setGravity(Gravity.CENTER)
                .setCancelable(true)
                .setAdapter(new ArrayAdapter<String>(mContext,
                        android.R.layout.simple_list_item_1, showerLockerStrs))
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                        tvSetShower.setText(showerLockerStrs[position]);
                        tvSetShower.setTag(position);
                        dialog.dismiss();
                    }
                })
                .create();
        dialogPlus.show();
    }
}
