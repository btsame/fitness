package com.dkjs.fitness.mine;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.widget.Toast;

import com.dkjs.fitness.R;
import com.dkjs.fitness.citydateselector.picker.DatePicker;
import com.dkjs.fitness.citydateselector.task.AddressInitTask;
import com.dkjs.fitness.comm.FitnessActivity;

import java.util.Calendar;

/**
 * Created by Administrator on 2016/7/16.
 */
public class UserInfoActivity extends FitnessActivity {

    private Calendar calendar = Calendar.getInstance();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
    }

    public void setCity(View view) {
        new AddressInitTask(this).execute("北京", "北京", "东城区");
    }

    public void setDate(View view) {
        DatePicker picker = new DatePicker(this);
        picker.setRange(1900, 2030);
        picker.setSelectedItem(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
        picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
//                showToast(year + "-" + month + "-" + day);

                Toast.makeText(UserInfoActivity.this,year + "-" + month + "-" + day,Toast.LENGTH_LONG).show();
            }
        });
        picker.show();
    }
}
