package com.dkjs.fitness.dialog;

import android.app.Dialog;
import android.content.Context;

import com.dkjs.fitness.R;

/**
 * Created by administrator on 16/6/22.
 */
public class LoadingDialog extends Dialog{

    public LoadingDialog(Context context) {
        super(context);

        setContentView(R.layout.dialog_loading);
    }

    public LoadingDialog(Context context, int themeResId) {
        super(context, themeResId);

        setContentView(R.layout.dialog_loading);
    }


}
