package com.dkjs.fitness.publish;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.dkjs.fitness.R;
import com.dkjs.fitness.citydateselector.picker.AddressPicker;
import com.dkjs.fitness.citydateselector.utils.AssetsUtils;
import com.dkjs.fitness.comm.FitnessActivity;
import com.dkjs.fitness.comm.GlobalUserManager;
import com.dkjs.fitness.domain.FTActivity;
import com.dkjs.fitness.domain.User;
import com.dkjs.fitness.util.ToastUtils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SetLocationActivity extends FitnessActivity {

    @Bind(R.id.tv_act_city)
    TextView tvSetCity;

    @Bind(R.id.et_act_address)
    EditText mAddressET;

    int showStyle;  //标记此Acitvity的作用
    private FTActivity ftActivity;
    public static final int ACTIVITY_CREATE = 1;
    public static final int ACTIVITY_SHOW = 2;
    public static final String PARAM_SHOW_STYLE = "showSytle";
    public static final String PARAM_FTACTIVITY = "ftActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_location);
        ButterKnife.bind(this);

        showStyle = getIntent().getIntExtra(PARAM_SHOW_STYLE, ACTIVITY_CREATE);
        if (showStyle == ACTIVITY_CREATE) {
            ftActivity = new FTActivity();
        } else if (showStyle == ACTIVITY_SHOW) {
            ftActivity = (FTActivity) getIntent().getSerializableExtra(PARAM_FTACTIVITY);
        }

        initView();
    }

    @Override
    protected void initView() {
        super.initView();

    }

    public void OnSetCity(View view) {
        try {
            ArrayList<AddressPicker.Province> data = new ArrayList<AddressPicker.Province>();
            String json = AssetsUtils.readText(this, "city2.json");
            data.addAll(JSON.parseArray(json, AddressPicker.Province.class));
            AddressPicker picker = new AddressPicker(this, data);
            picker.setHideProvince(true);
            picker.setSelectedItem("北京市", "北京市", "东城区");
            picker.setOnAddressPickListener(new AddressPicker.OnAddressPickListener() {
                @Override
                public void onAddressPicked(String province, String city, String county) {

                    tvSetCity.setText(city + " " + county);
                }
            });
            picker.show();
        } catch (Exception e) {

        }
    }


    public void OnSave(View view) {
        if (TextUtils.isEmpty(tvSetCity.getText())) {
            ToastUtils.showCustomToast(mContext, "请完善信息");
            return;
        }

        if (!TextUtils.isEmpty(tvSetCity.getText()) && !TextUtils.isEmpty(mAddressET.getText())) {
            Intent intent = new Intent();
            intent.putExtra("result", tvSetCity.getText().toString() + "##" + mAddressET.getText().toString());
            this.setResult(RESULT_OK, intent);
        }
        this.finish();
    }

    public void onBackPressed() {
        exitEdit(this);
    }

    public void OnBack(View view) {
        exitEdit(this);
    }
}
