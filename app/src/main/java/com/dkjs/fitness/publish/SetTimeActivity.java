package com.dkjs.fitness.publish;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dkjs.fitness.R;
import com.dkjs.fitness.citydateselector.picker.DateTimePicker;
import com.dkjs.fitness.comm.FitnessActivity;
import com.dkjs.fitness.comm.GlobalUserManager;
import com.dkjs.fitness.domain.FTActivity;
import com.dkjs.fitness.domain.User;
import com.dkjs.fitness.util.ToastUtils;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SetTimeActivity extends FitnessActivity implements View.OnClickListener {

    int showStyle;  //标记此Acitvity的作用
    private FTActivity ftActivity;
    public static final int ACTIVITY_CREATE = 1;
    public static final int ACTIVITY_SHOW = 2;
    public static final String PARAM_SHOW_STYLE = "showSytle";
    public static final String PARAM_FTACTIVITY = "ftActivity";
    private long clickTime = 0;              //记录第一次点击的时间

    //时间 城市选择按钮
    @Bind(R.id.rl_act_strat_time)
    RelativeLayout rlStartTime;

    @Bind(R.id.tv_act_time)
    TextView tvStartTime;

    @Bind(R.id.rl_end_time)
    RelativeLayout rlEndTime;

    @Bind(R.id.tv_act_end_time)
    TextView tvEndTime;
    private Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_time);
        ButterKnife.bind(this);

        showStyle = getIntent().getIntExtra(PARAM_SHOW_STYLE, ACTIVITY_CREATE);
        if (showStyle == ACTIVITY_CREATE) {
            ftActivity = new FTActivity();
        } else if (showStyle == ACTIVITY_SHOW) {
            ftActivity = (FTActivity) getIntent().getSerializableExtra(PARAM_FTACTIVITY);
        }

        setListener();
    }


    @Override
    protected void setListener() {
        rlStartTime.setOnClickListener(this);
        rlEndTime.setOnClickListener(this);
    }

    public void OnSave(View view) {
        if (TextUtils.isEmpty(tvStartTime.getText()) || TextUtils.isEmpty(tvEndTime.getText())) {
            ToastUtils.showCustomToast(mContext, "请完善信息");
            return;
        }

        if (!TextUtils.isEmpty(tvStartTime.getText()) && !TextUtils.isEmpty(tvEndTime.getText())) {
            Intent intent = new Intent();
            intent.putExtra("result", tvStartTime.getText().toString() + "##" + tvEndTime.getText().toString());
            this.setResult(RESULT_OK, intent);
        }
        this.finish();
    }

    @Override
    public void onClick(View v) {
        if (v == rlStartTime) {
            setStartTime();
        } else if (v == rlEndTime) {
            setEndTime();
        }
    }


    private void setEndTime() {
        DateTimePicker picker = new DateTimePicker(this, DateTimePicker.HOUR_OF_DAY);
        picker.setRange(2000, 2050);
        picker.setSelectedItem(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH),
                20, 00);
        picker.setOnDateTimePickListener(new DateTimePicker.OnYearMonthDayTimePickListener() {
            @Override
            public void onDateTimePicked(String year, String month, String day, String hour, String minute) {
                // showToast(year + "-" + month + "-" + day + " " + hour + ":" + minute);
                tvEndTime.setText(year + "-" + month + "-" + day + " " + hour + ":" + minute);
            }
        });
        picker.show();
    }

    private void setStartTime() {
        DateTimePicker picker = new DateTimePicker(this, DateTimePicker.HOUR_OF_DAY);
        picker.setRange(2000, 2050);
        picker.setSelectedItem(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH),
                19, 00);
        picker.setOnDateTimePickListener(new DateTimePicker.OnYearMonthDayTimePickListener() {
            @Override
            public void onDateTimePicked(String year, String month, String day, String hour, String minute) {
                // showToast(year + "-" + month + "-" + day + " " + hour + ":" + minute);
                tvStartTime.setText(year + "-" + month + "-" + day + " " + hour + ":" + minute);
            }
        });
        picker.show();
    }

    public void onBackPressed() {
        exitEdit(this);
    }

    public void OnBack(View view) {
        exitEdit(this);
    }


}