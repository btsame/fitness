package com.dkjs.fitness.mine;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.dkjs.fitness.R;
import com.dkjs.fitness.comm.FitnessActivity;

public class CheckOutTicketsActivity extends FitnessActivity {

    String beforeStr = "";
    String afterStr = "";
    String changeStr = "";
    int index = 0;
    boolean changeIndex = true;
    EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
        et= (EditText) findViewById(R.id.et_num);
        initListener();
    }


    public void initListener() {
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
}
