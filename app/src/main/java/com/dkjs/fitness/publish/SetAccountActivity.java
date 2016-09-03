package com.dkjs.fitness.publish;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.dkjs.fitness.R;
import com.dkjs.fitness.comm.FitnessActivity;
import com.dkjs.fitness.comm.GlobalUserManager;
import com.dkjs.fitness.domain.FTActivity;
import com.dkjs.fitness.domain.User;
import com.dkjs.fitness.util.ToastUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SetAccountActivity extends FitnessActivity {
    int showStyle;  //标记此Acitvity的作用
    private FTActivity ftActivity;
    public static final int ACTIVITY_CREATE = 1;
    public static final int ACTIVITY_SHOW = 2;
    public static final String PARAM_SHOW_STYLE = "showSytle";
    public static final String PARAM_FTACTIVITY = "ftActivity";
    @Bind(R.id.et_act_totalnum)
    EditText mTotalNumET;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_account);
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



    public void OnSave(View view){
        if (TextUtils.isEmpty(mTotalNumET.getText())) {
            ToastUtils.showCustomToast(mContext, "请完善信息");
            return;
        }

        if (!TextUtils.isEmpty(mTotalNumET.getText()) ) {
            Intent intent = new Intent();
            intent.putExtra("result", mTotalNumET.getText().toString() + "##" );
            this.setResult(RESULT_OK, intent);
        }
        this.finish();
    }

    public void onBackPressed() {
        exitEdit(this);
    }
    public void OnBack(View view){
        exitEdit(this);
    }
}
