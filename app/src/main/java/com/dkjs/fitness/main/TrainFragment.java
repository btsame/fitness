package com.dkjs.fitness.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.dkjs.fitness.R;
import com.dkjs.fitness.comm.FitnessFragment;
import com.dkjs.fitness.domain.BodyIndex;
import com.dkjs.fitness.train.RoundProgressBar;
import com.dkjs.fitness.util.SpUtil;
import com.dkjs.fitness.util.ToastUtils;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.ViewHolder;

import java.math.BigDecimal;

/**
 * Created by administrator on 16/7/10.
 */
public class TrainFragment extends FitnessFragment implements View.OnClickListener {

    private RoundProgressBar rpBFP, rpBMI, rpBFT;
    String sex, mHeight, mWeight, mAge;
    BodyIndex mBodyIndex;
    double bfp, bmi, bmr, weight, height, age;
    RadioButton rbMan, rbWomen;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_timer, container, false);

        initView(view);
        rpBFP.setOnClickListener(this);
        rpBMI.setOnClickListener(this);
        rpBFT.setOnClickListener(this);

        setBodyIndex();

        return view;
    }

    private void setBodyIndex() {

        mHeight = SpUtil.getFromLocal(mContext, "config", "height", "0");
        mWeight = SpUtil.getFromLocal(mContext, "config", "weight", "0");
        mAge = SpUtil.getFromLocal(mContext, "config", "age", "0");
        sex = SpUtil.getFromLocal(mContext, "config", "sex", "女");

        ToastUtils.showCustomToast(mContext, "height=" + mHeight + "weight=" + mWeight + "mAge=" + mAge + "sex=" + sex);

        if (!TextUtils.isEmpty(mHeight) && !TextUtils.isEmpty(mWeight) && !TextUtils.isEmpty(mAge)) {

            height = Float.parseFloat(mHeight);
            weight = Float.parseFloat(mWeight);
            age = Float.parseFloat(mAge);


            if (weight != 0 && height != 0 & age != 0) {
                bmi = (weight / (height / 100) / (height / 100));

                if (sex.equals("男")) {
                    bfp = (1.2 * bmi + 0.23 * age - 5.4 - 10.8);
                    bmr = (13.7 * weight) + (5.0 * height) - (6.8 * age) + 66;
                } else {
                    bfp = (1.2 * bmi + 0.23 * (float) age - 5.4);
                    bmr = (9.6 * weight) + (1.8 * height) - (4.7 * age) + 655;
                }

                // new BigDecimal(bfp).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();

                rpBFP.setProgress(new BigDecimal(bfp).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()
                        , "BFP", "标准", 50, "%");
                rpBMI.setProgress(new BigDecimal(bmi).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(), "BMI", "标准", 50, "");
                rpBFT.setProgress(new BigDecimal(bmr).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(), "BMR", "标准", 5000, "KCal");
            }
        }


    }

    private void initView(View view) {
        mBodyIndex = new BodyIndex();

        rpBFP = (RoundProgressBar) view.findViewById(R.id.roundProgressBarBfp);
        rpBMI = (RoundProgressBar) view.findViewById(R.id.roundProgressBarBmi);
        rpBFT = (RoundProgressBar) view.findViewById(R.id.roundProgressBarBft);
    }


    @Override
    public void onClick(View v) {
        if (v == rpBFP || v == rpBFT || v == rpBMI) {
            //startActivity(new Intent(getActivity(), SetBodyParmesActivity.class));

            final DialogPlus dialogPlus = DialogPlus.newDialog(mContext)
                    .setGravity(Gravity.CENTER)
                    .setCancelable(true)
                    .setContentHolder(new ViewHolder(R.layout.activity_set_body_parmes)).setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(DialogPlus dialog, View view) {
                            if (view == dialog.findViewById(R.id.btn_save)) {

                                EditText etHeight = (EditText) dialog.findViewById(R.id.et_height);
                                EditText etWeight = (EditText) dialog.findViewById(R.id.et_weight);
                                EditText etAge = (EditText) dialog.findViewById(R.id.et_age);

                                rbMan = (RadioButton) dialog.findViewById(R.id.sex_man);
                                rbWomen = (RadioButton) dialog.findViewById(R.id.sex_woman);

                                //监听性别radiogroup
                                RadioGroup mRadioGroup = (RadioGroup) dialog.findViewById(R.id.radio_group_sex);

                                if (rbMan.isChecked()) {
                                    SpUtil.saveToLocal(mContext, "config", "sex", "男");

                                } else {
                                    SpUtil.saveToLocal(mContext, "config", "sex", "女");

                                }

                               /* mHeight = SpUtil.getFromLocal(mContext, "config", "height", "0");
                                mWeight = SpUtil.getFromLocal(mContext, "config", "weight", "0");
                                mAge = SpUtil.getFromLocal(mContext, "config", "age", "0");
                                sex = SpUtil.getFromLocal(mContext, "config", "sex", "女");
                              *//*  if (!mHeight.equals(0) && !mWeight.equals(0) && !mWeight.equals(mAge)) {*//*
                                    etHeight.setText(mHeight);
                                    etWeight.setText(mWeight);
                                    etAge.setText(mAge);
                                    if (sex.equals("男")) {
                                        rbMan.setChecked(true);
                                    } else {
                                        rbWomen.setChecked(true);
                                    }
                            *//*    }*/


                                if (!TextUtils.isEmpty(etHeight.getText().toString()) && !TextUtils.isEmpty(etWeight.getText().toString()) && !TextUtils.isEmpty(etAge.getText().toString())) {


                                    SpUtil.saveToLocal(mContext, "config", "height", etHeight.getText().toString());
                                    SpUtil.saveToLocal(mContext, "config", "weight", etWeight.getText().toString());
                                    SpUtil.saveToLocal(mContext, "config", "age", etAge.getText().toString());
                                    // SpUtil.saveToLocal(mContext, "config", "sex", sex);

                                    // SpUtil.saveToLocal(mContext,"config","height",sex);

                                }
                                setBodyIndex();
                                // ToastUtils.showCustomToast(mContext,"height="+etHeight.getText().toString()+"height="+height);
                                dialog.dismiss();
                            }

                        }
                    })
                    .create();
            dialogPlus.show();


        }
    }
}
