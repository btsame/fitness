package com.dkjs.fitness.find;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dkjs.fitness.R;
import com.dkjs.fitness.comm.FitnessActivity;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnItemClickListener;

public class JoinPartyAndPayActivity extends FitnessActivity {


    TextView tvPayType;
    ImageView ivPayIcon;
    private String[] itemNames = new String[]{"支付宝客户端支付", "支付宝网页支付","微信客户端支付"};
    private int[] itemPics = new int[]{R.drawable.pay_icon_alipay_app,
            R.drawable.pay_icon_alipay_web,R.drawable.pay_icon_wechat};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_party_and_pay);

        initView();
    }

    @Override
    protected void initView() {
        super.initView();
        tvPayType= (TextView) findViewById(R.id.tv_pay_type);
        ivPayIcon= (ImageView) findViewById(R.id.iv_pay_icon);


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
                            tvPayType.setText(itemNames[position]);
                            ivPayIcon.setImageResource(itemPics[position]);
                            dialog.dismiss();
                    }
                }).create();
        dialogPlus.show();

    }

    public class UserTypeAdapter extends BaseAdapter {

        Context mContext;


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
            View itemView = layoutInflater.inflate(R.layout.item_pay_type_select, null);
            ((ImageView) itemView.findViewById(R.id.iv_pic_select)).setImageResource(itemPics[position]);
            ((TextView) itemView.findViewById(R.id.tv_pic_select)).setText(itemNames[position]);
            return itemView;
        }
    }
}
