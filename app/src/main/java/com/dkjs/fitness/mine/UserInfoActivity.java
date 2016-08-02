package com.dkjs.fitness.mine;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.dkjs.fitness.R;
import com.dkjs.fitness.citydateselector.picker.AddressPicker;
import com.dkjs.fitness.citydateselector.picker.DatePicker;
import com.dkjs.fitness.citydateselector.utils.AssetsUtils;
import com.dkjs.fitness.comm.FitnessActivity;
import com.dkjs.fitness.util.ToastUtils;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Administrator on 2016/7/16.
 */
public class UserInfoActivity extends FitnessActivity {

    Button btnDate, btnCity;
    private Calendar calendar = Calendar.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_setting);
        initView();
    }


    @Override
    protected void initView() {
        btnCity = (Button) findViewById(R.id.btn_city_set);
        btnDate = (Button) findViewById(R.id.btn_date_set);
        btnCity.setText("北京 东城区");
        btnDate.setText(" ");
    }

    public void setCity(View view) {
        new AddressInitTask(this).execute("北京", "北京", "东城区");
    }

    public void setDate(View view) {
        DatePicker picker = new DatePicker(this);
        picker.setRange(1900, 2030);
        picker.setSelectedItem(1990, 01, 01);
        picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                if (calendar.get(Calendar.YEAR) - (Integer.parseInt(year)) > 0) {
                    btnDate.setText(calendar.get(Calendar.YEAR) - (Integer.parseInt(year)) + "");
                } else {
                    btnDate.setText(0 + "");
                }

            }
        });
        picker.show();
    }


    public class AddressInitTask extends AsyncTask<String, Void, ArrayList<AddressPicker.Province>> {
        private Activity activity;
        private ProgressDialog dialog;
        private String selectedProvince = "", selectedCity = "", selectedCounty = "";
        private boolean hideCounty = false;

        /**
         * 初始化为不显示区县的模式
         *
         * @param activity
         * @param hideCounty is hide County
         */
        public AddressInitTask(Activity activity, boolean hideCounty) {
            this.activity = activity;
            this.hideCounty = hideCounty;
            dialog = ProgressDialog.show(activity, null, "正在初始化数据...", true, true);
        }

        public AddressInitTask(Activity activity) {
            this.activity = activity;
            dialog = ProgressDialog.show(activity, null, "正在初始化数据...", true, true);
        }

        @Override
        protected ArrayList<AddressPicker.Province> doInBackground(String... params) {
            if (params != null) {
                switch (params.length) {
                    case 1:
                        selectedProvince = params[0];
                        break;
                    case 2:
                        selectedProvince = params[0];
                        selectedCity = params[1];
                        break;
                    case 3:
                        selectedProvince = params[0];
                        selectedCity = params[1];
                        selectedCounty = params[2];
                        break;
                    default:
                        break;
                }
            }
            ArrayList<AddressPicker.Province> data = new ArrayList<AddressPicker.Province>();
            try {
                String json = AssetsUtils.readText(activity, "city.json");
                data.addAll(JSON.parseArray(json, AddressPicker.Province.class));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return data;
        }

        @Override
        protected void onPostExecute(ArrayList<AddressPicker.Province> result) {
            dialog.dismiss();
            if (result.size() > 0) {
                AddressPicker picker = new AddressPicker(activity, result);
                picker.setHideCounty(hideCounty);
                picker.setSelectedItem(selectedProvince, selectedCity, selectedCounty);
                picker.setOnAddressPickListener(new AddressPicker.OnAddressPickListener() {
                    @Override
                    public void onAddressPicked(String province, String city, String county) {
                        if (county == null) {
                            btnCity.setText(province + " " + city);
                        } else {
                            btnCity.setText(province + " " + city + " " + county);
                        }
                    }
                });
                picker.show();
            } else {
                Toast.makeText(activity, "数据初始化失败", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
