package com.dkjs.fitness.act;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dkjs.fitness.R;
import com.dkjs.fitness.comm.FitnessActivity;
import com.dkjs.fitness.domain.FTActivity;
import com.dkjs.fitness.mine.ActivityCreateActivity;
import com.dkjs.fitness.util.URIUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by administrator on 16/8/7.
 */
public class ActShowActivity extends FitnessActivity implements View.OnClickListener{

    public static final String EXTRA_ACT_DATA = "ftActivity";

    @Bind(R.id.sdv_act_pic)
    SimpleDraweeView mActPicSDV;
    @Bind(R.id.sdv_user_header)
    SimpleDraweeView mUserHeaderSDV;
    @Bind(R.id.tv_user_nickname)
    TextView mNickNameTV;
    @Bind(R.id.tv_course_name)
    TextView mActNameTV;
    @Bind(R.id.tv_time)
    TextView mActTimeTV;
    @Bind(R.id.tv_course_Price)
    TextView mActPriceTV;
    @Bind(R.id.tv_remain_equip)
    TextView mActEquipmentTV;
    @Bind(R.id.tv_show_shower_locker)
    TextView mShowerLockerTV;
    @Bind(R.id.tv_act_intro)
    TextView mActIntroTV;
    @Bind(R.id.btn_introDetail)
    Button mIntroCtlBtn;
    @Bind(R.id.btn_ok)
    Button mJoinActBtn;

    FTActivity ftActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        ButterKnife.bind(this);

        if(getIntent().getSerializableExtra(EXTRA_ACT_DATA) != null){
            ftActivity = (FTActivity) getIntent().getSerializableExtra(EXTRA_ACT_DATA);

        }

        initData();
        setListener();
    }

    @Override
    protected void setListener() {
        super.setListener();
        mIntroCtlBtn.setOnClickListener(this);
        mJoinActBtn.setOnClickListener(this);
    }

    private void initData(){
        if(ftActivity != null){
            mActPicSDV.setImageURI(URIUtil.handleNetworkUri(ftActivity.getSourceUrl()));
            mActNameTV.setText(ftActivity.getSubject());
            mActTimeTV.setText(ftActivity.getBeginTime() + "到" + ftActivity.getEndTime());
            mActPriceTV.setText(getString(R.string.act_price, ftActivity.getPrice()));
            mActEquipmentTV.setText(ftActivity.getSelfEquipment());
            mShowerLockerTV.setText(
                    ActivityCreateActivity.showerLockerStrs[ftActivity.getShowerAndLocker()]);
            mActIntroTV.setText(ftActivity.getIntruction());

            if(ftActivity.getOwner() != null){
                mUserHeaderSDV.setImageURI(URIUtil.handleNetworkUri(ftActivity.getOwner().getPortrait()));
                mNickNameTV.setText(ftActivity.getOwner().getNickName());
            }

        }
    }

    @Override
    public void onClick(View v) {
        if(v == mIntroCtlBtn){
            if(mActIntroTV.getVisibility() == View.VISIBLE){
                mActIntroTV.setVisibility(View.INVISIBLE);
                mIntroCtlBtn.setText("查看详情 >");
            }else{
                mActIntroTV.setVisibility(View.VISIBLE);
                mIntroCtlBtn.setText("关闭详情 >");
            }
        }else if(v == mJoinActBtn){

        }
    }
}
