package com.dkjs.fitness.mine;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dkjs.fitness.R;
import com.dkjs.fitness.comm.FitnessActivity;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnItemClickListener;

public class WithDrawActivity extends FitnessActivity {

    TextView tvAccoutType;
    LinearLayout llAlipay,llBank;

    //银行卡号格式化
    String beforeStr = "";
    String afterStr = "";
    String changeStr = "";
    int index = 0;
    boolean changeIndex = true;
    EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_with_draw);


        initView();
        //监听输入卡号信息
        initListener();

    }


    @Override
    protected void initView() {
        super.initView();

        tvAccoutType= (TextView) findViewById(R.id.tv_accout_type);
        llAlipay= (LinearLayout) findViewById(R.id.l1_account_aplipay);
        llBank= (LinearLayout) findViewById(R.id.ll_account_bank_number);
        et= (EditText) findViewById(R.id.et_account_bank_number);
    }


    public void initListener(){
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                beforeStr = s.toString();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                afterStr = s.toString();
                if (changeIndex)
                    index = et.getSelectionStart();
            }

            @Override
            public void afterTextChanged(Editable s) {
                if ("".equals(s.toString()) || s.toString() == null || beforeStr.equals(afterStr)) {
                    changeIndex = true;
                    return;
                }
                changeIndex = false;
                char c[] = s.toString().replace(" ", "").toCharArray();
                changeStr = "";
                for (int i = 0; i < c.length; i++) {
                    changeStr = changeStr + c[i] + ((i + 1) % 4 == 0 && i + 1 != c.length ? " " : "");
                }
                if (afterStr.length() > beforeStr.length()) {
                    if (changeStr.length() == index + 1) {
                        index = changeStr.length() - afterStr.length() + index;
                    }
                    if (index % 5 == 0 && changeStr.length() > index + 1) {
                        index++;
                    }
                } else if (afterStr.length() < beforeStr.length()) {
                    if ((index + 1) % 5 == 0 && index > 0 && changeStr.length() > index + 1) {
                        //  index--;
                    } else {
                        index = changeStr.length() - afterStr.length() + index;
                        if (afterStr.length() % 5 == 0 && changeStr.length() > index + 1) {
                            index++;
                        }
                    }
                }
                et.setText(changeStr);
                et.setSelection(index);

            }
        });
    }

    //设置收款账户
    public void setMoneyAccount(View view){
        {

            final DialogPlus dialogPlus = DialogPlus.newDialog(mContext)
                    .setGravity(Gravity.BOTTOM)
                    .setCancelable(true)
                    .setAdapter(new UserTypeAdapter(mContext))
                    .setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(DialogPlus dialog, Object item, View view, int position) {

                            if (position == 0) {
                                tvAccoutType.setText("");
                                llAlipay.setVisibility(View.VISIBLE);
                                llBank.setVisibility(View.GONE);

                                dialog.dismiss();
                            } else if (position == 1) {
                                tvAccoutType.setText("");
                                llBank.setVisibility(View.VISIBLE);
                                llAlipay.setVisibility(View.GONE);
                                dialog.dismiss();
                            }

                        }
                    }).create();
            dialogPlus.show();

        }
    }
    public class UserTypeAdapter extends BaseAdapter {

        Context mContext;
        private String[] itemNames = new String[]{"支付宝账户", "银行卡账户"};
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
