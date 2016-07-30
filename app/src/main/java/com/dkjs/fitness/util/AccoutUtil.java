package com.dkjs.fitness.util;

import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/7/24.
 */
public class AccoutUtil {

    public static boolean checkRePassWord(Context context, EditText editPwd, EditText editRePwd){
        String passWord = editPwd.getText().toString();
        String rePassWord = editRePwd.getText().toString();
        if (!passWord.equals(rePassWord) ) {
            Toast.makeText(context, "密码不一致，请重新确认", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    public static Boolean checkPhone(Context context, EditText edit_user) {
        String phone = edit_user.getText().toString();
        if (null == phone || "".equals(phone) || "".equals(phone.trim())) {
            Toast.makeText(context, "请输入手机号码", Toast.LENGTH_SHORT).show();
            return false;
        } else if (phone.length() != 11 || !phone.startsWith("1")) {
            Toast.makeText(context, "手机号码格式不对", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public static boolean checkPassword(Context context, EditText edit_user, EditText edit_pwd) {
        String password = edit_pwd.getText().toString();
        if (null == password || "".equals(password) || "".equals(password.trim())) {
            Toast.makeText(context, "请输入密码", Toast.LENGTH_SHORT).show();
            return false;
        } else if (password.contains(" ")) {
            Toast.makeText(context, "密码不能包含空格", Toast.LENGTH_SHORT).show();
            return false;
        } else if (password.length() < 6 || password.length() > 20) {
            Toast.makeText(context, "密码长度为6-20字符", Toast.LENGTH_SHORT).show();
            return false;
        } else if (edit_user.getText().toString().equals(password.trim())) {
            Toast.makeText(context, "密码不能跟账号一致,请重新输入", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

}
