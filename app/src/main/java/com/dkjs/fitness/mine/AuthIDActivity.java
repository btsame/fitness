package com.dkjs.fitness.mine;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dkjs.fitness.R;
import com.dkjs.fitness.adapter.PicSelectAdapter;
import com.dkjs.fitness.comm.AppConfig;
import com.dkjs.fitness.comm.FitnessActivity;
import com.dkjs.fitness.util.ToastUtils;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnItemClickListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AuthIDActivity extends FitnessActivity {

    TextView tvAddNew, tvUserType;
    //达人认证是否添加多项
    LinearLayout llAddSecond, llAddThird, llAddSecond2, llAddThird2, llTypeCoach, llTypeSuper;
    //上传证件照
    RelativeLayout rl_identity_photo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_apply_new);
        initView();
    }

    @Override
    protected void initView() {
        tvAddNew = (TextView) findViewById(R.id.add_new_cj);
        tvUserType = (TextView) findViewById(R.id.tv_user_type);
        llAddSecond = (LinearLayout) findViewById(R.id.ll_add_second);

        llAddThird = (LinearLayout) findViewById(R.id.ll_add_third);
        llAddThird2 = (LinearLayout) findViewById(R.id.ll_add_third2);
        llAddSecond2 = (LinearLayout) findViewById(R.id.ll_add_second2);
        llTypeCoach = (LinearLayout) findViewById(R.id.ll_coach_auth);
        llTypeSuper = (LinearLayout) findViewById(R.id.ll_super_auth);

    }

    //切换用户类型
    public void OnChangeUserType(View view) {

        final DialogPlus dialogPlus = DialogPlus.newDialog(mContext)
                .setGravity(Gravity.BOTTOM)
                .setCancelable(true)
                .setAdapter(new UserTypeAdapter(mContext))
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(DialogPlus dialog, Object item, View view, int position) {

                        if (position == 0) {
                            tvUserType.setText("健身达人");
                            llTypeSuper.setVisibility(View.VISIBLE);
                            llTypeCoach.setVisibility(View.GONE);
                            dialog.dismiss();
                        } else if (position == 1) {
                            tvUserType.setText("健身教练");
                            llTypeSuper.setVisibility(View.GONE);
                            llTypeCoach.setVisibility(View.VISIBLE);
                            dialog.dismiss();
                        }

                    }
                }).create();
        dialogPlus.show();

    }


    //上传证件照
    public void upLoadIDImg(View view) {
        startActivity(new Intent(AuthIDActivity.this, AuthIDUpLoadActivity.class));
    }


    //上传资格证书

    public void upLoadCoachImg(View view){
        startActivity(new Intent(AuthIDActivity.this, UpLoadCoachImgActivity.class));
    }

    //健身达人添加一项
    public void addNewOne(View view) {

        if (llAddSecond.getVisibility() == View.GONE) {
            llAddSecond.setVisibility(View.VISIBLE);
        } else if (llAddSecond.getVisibility() == View.VISIBLE) {
            llAddThird.setVisibility(View.VISIBLE);
        } else{
            ToastUtils.showCustomToast(AuthIDActivity.this,"最多添加三项");
        }

    }

    //健身教练添加一项
    public void addNewOne2(View view) {

        if (llAddSecond2.getVisibility() == View.GONE) {
            llAddSecond2.setVisibility(View.VISIBLE);
        } else if (llAddSecond2.getVisibility() == View.VISIBLE) {
            llAddThird2.setVisibility(View.VISIBLE);
        }else{
            ToastUtils.showCustomToast(AuthIDActivity.this,"最多添加三项");
        }

    }

    //健身达人移除第二项
    public void removSecondAuth(View view) {
        llAddSecond.setVisibility(View.GONE);
    }

    //健身达人移除第三项
    public void removThirdAuth(View view) {
        llAddThird.setVisibility(View.GONE);
    }

    //健身教练移除第二项
    public void removSecondAuth2(View view) {
        llAddSecond2.setVisibility(View.GONE);
    }

    //健身教练移除第三项
    public void removThirdAuth2(View view) {
        llAddThird2.setVisibility(View.GONE);
    }

    public class UserTypeAdapter extends BaseAdapter {

        Context mContext;
        private String[] itemNames = new String[]{"健身达人", "健身教练"};
        private int[] itemPics = new int[]{R.drawable.vip_icon,
                R.drawable.vip_icon};

        public UserTypeAdapter() {
        }

        public UserTypeAdapter(Context context) {
            mContext = context;
        }

        @Override
        public int getCount() {
            return itemNames.length;
        }

        @Override
        public Object getItem(int position) {
            return itemNames[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            View itemView = layoutInflater.inflate(R.layout.item_user_type_select, null);
            ((ImageView) itemView.findViewById(R.id.iv_pic_select)).setImageResource(itemPics[position]);
            ((TextView) itemView.findViewById(R.id.tv_pic_select)).setText(itemNames[position]);
            return itemView;
        }
    }
}
