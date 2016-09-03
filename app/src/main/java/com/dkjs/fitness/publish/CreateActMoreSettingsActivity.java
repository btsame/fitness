package com.dkjs.fitness.publish;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dkjs.fitness.R;
import com.dkjs.fitness.comm.FitnessActivity;
import com.dkjs.fitness.comm.GlobalUserManager;
import com.dkjs.fitness.domain.FTActivity;
import com.dkjs.fitness.domain.User;
import com.dkjs.fitness.util.ToastUtils;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnItemClickListener;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CreateActMoreSettingsActivity extends FitnessActivity {

    @Bind(R.id.tv_act_sex)
    TextView tvSetSex;

    @Bind(R.id.tv_act_shower)
    TextView tvSetShower;

    public static final int ACTIVITY_CREATE = 1;
    public static final int ACTIVITY_SHOW = 2;
    public static final String PARAM_SHOW_STYLE = "showSytle";
    public static final String PARAM_FTACTIVITY = "ftActivity";
    int showStyle;  //标记此Acitvity的作用

    @Bind(R.id.et_act_equipment)
    EditText mEquipmentET;
    @Bind(R.id.rl_act_type)
    RelativeLayout mActTypeBtn;
    @Bind(R.id.tv_act_type)
    TextView mActTypeTv;


    private FTActivity ftActivity;


    public static String[] actSexStrs = new String[]{"没有限制", "仅限男生", "仅限女生"};
    public static String[] showerLockerStrs = new String[]{"不提供沐浴／锁柜", "只提供沐浴", "只提供锁柜", "提供沐浴／锁柜"};
    public static String[] actTypeStrs = new String[]{"室外", "场馆内"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_act_more_settings);
        ButterKnife.bind(this);
        initView();

        if (showStyle == ACTIVITY_CREATE) {
            ftActivity = new FTActivity();
        } else if (showStyle == ACTIVITY_SHOW) {
            ftActivity = (FTActivity) getIntent().getSerializableExtra(PARAM_FTACTIVITY);
        }

    }

    @Override
    protected void initView() {
        super.initView();

        if (showStyle == ACTIVITY_SHOW) {
            mEquipmentET.setText(ftActivity.getSelfEquipment());

            tvSetShower.setText(showerLockerStrs[ftActivity.getShowerAndLocker()]);
            tvSetShower.setTag(ftActivity.getShowerAndLocker());

            tvSetSex.setText(actSexStrs[ftActivity.getActSex()]);
            tvSetSex.setTag(ftActivity.getActSex());//
        }
    }


    //保存

    public void OnSave(View view) {

        if (!TextUtils.isEmpty(mActTypeTv.getText()) || !TextUtils.isEmpty(tvSetSex.getText()) || !TextUtils.isEmpty(mEquipmentET.getText())) {
            ftActivity.setActSex(Integer.parseInt(tvSetSex.getTag().toString()));
            ftActivity.setSelfEquipment(mEquipmentET.getText().toString());
            ftActivity.setShowerAndLocker(Integer.parseInt(tvSetShower.getTag().toString()));
        }

        if (TextUtils.isEmpty(mEquipmentET.getText()) || TextUtils.isEmpty(mActTypeTv.getText()) || TextUtils.isEmpty(tvSetSex.getText()) || TextUtils.isEmpty(tvSetShower.getText())) {
            ToastUtils.showCustomToast(mContext, "请完善信息");
            return;
        }

        if (!TextUtils.isEmpty(mEquipmentET.getText()) && !TextUtils.isEmpty(mActTypeTv.getText()) && !TextUtils.isEmpty(tvSetSex.getText()) && !TextUtils.isEmpty(tvSetShower.getText())) {
            Intent intent = new Intent();
            intent.putExtra("result", mEquipmentET.getText().toString() + "##" + mActTypeTv.getText().toString() + "##" + tvSetSex.getText().toString() + "##" + tvSetShower.getText().toString());
            this.setResult(RESULT_OK, intent);
        }
        this.finish();
    }


    public void OnSetType(View view) {
        DialogPlus dialogPlus = DialogPlus.newDialog(mContext)
                .setGravity(Gravity.CENTER)
                .setCancelable(true)
                .setAdapter(new ArrayAdapter<String>(mContext,
                        android.R.layout.simple_list_item_1, actTypeStrs))
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                        mActTypeTv.setText(actTypeStrs[position]);
                        mActTypeTv.setTag(position);
                        dialog.dismiss();
                    }
                })
                .create();
        dialogPlus.show();
    }

    public void OnSetSex(View view) {
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


    public void OnSetShower(View view) {
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

    public void OnBack(View view) {
        exitEdit(this);
    }
}
