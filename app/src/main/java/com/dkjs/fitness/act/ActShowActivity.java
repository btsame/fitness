package com.dkjs.fitness.act;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dkjs.fitness.R;
import com.dkjs.fitness.biz.FTActivityBiz;
import com.dkjs.fitness.biz.IFTActivityBiz;
import com.dkjs.fitness.chat.ChatRoomActivity;
import com.dkjs.fitness.comm.FitnessActivity;
import com.dkjs.fitness.comm.GlobalUserManager;
import com.dkjs.fitness.domain.FTActivity;
import com.dkjs.fitness.domain.UserAct;
import com.dkjs.fitness.find.JoinPartyAndPayActivity;
import com.dkjs.fitness.mine.ActivityCreateActivity;
import com.dkjs.fitness.util.ToastUtils;
import com.dkjs.fitness.util.URIUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

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
    IFTActivityBiz ftActivityBiz;

    boolean hasJoinAct = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        ButterKnife.bind(this);

        if(getIntent().getSerializableExtra(EXTRA_ACT_DATA) != null){
            ftActivity = (FTActivity) getIntent().getSerializableExtra(EXTRA_ACT_DATA);

        }
        ftActivityBiz = new FTActivityBiz();

        judgeJoinAct();
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

    private void judgeJoinAct(){
        if(ftActivity.getOwner().getUserId().equals(GlobalUserManager.getUserId())){
            hasJoinAct = true;
            mJoinActBtn.setText("进入群聊");
            return;
        }
        ftActivityBiz.queryActMember(ftActivity.getActId(), new IFTActivityBiz.QueryMemberListener() {
            @Override
            public void onSuccess(List<UserAct> userList) {
                for(UserAct temp : userList){
                    if(temp.getUserId().equals(GlobalUserManager.getUserId())){
                        hasJoinAct = true;
                        mJoinActBtn.setText("进入群聊");
                        break;
                    }
                }

            }

            @Override
            public void onFailure(String msg) {
                ToastUtils.showCustomToast(mContext, msg);
            }
        });

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
            startActivity(new Intent(this, JoinPartyAndPayActivity.class));
            if(hasJoinAct){
                Intent intent = new Intent(mContext, ChatRoomActivity.class);
                intent.putExtra(ChatRoomActivity.GROUPID_EXTRA, ftActivity.getShuoShuo().getObjectId());
                startActivity(intent);
            }else{
                ftActivityBiz.joinInAct(ftActivity, GlobalUserManager.getUserId(), GlobalUserManager.getNickName(),
                        GlobalUserManager.getPortrait(), new IFTActivityBiz.JoinQuitActListener() {
                            @Override
                            public void onSuccess() {
                                ToastUtils.showCustomToast(mContext, "参加活动成功");
                            }

                            @Override
                            public void onFailure(String msg) {
                                ToastUtils.showCustomToast(mContext, "参加活动失败");
                            }
                        });
            }

        }
    }



}
