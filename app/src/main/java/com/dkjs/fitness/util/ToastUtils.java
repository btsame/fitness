package com.dkjs.fitness.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dkjs.fitness.R;


public class ToastUtils {
	
	public static void showCustomToast(Context context, int textResId){
		if (context == null) {
			return;
		}
		LayoutInflater inflater = LayoutInflater.from(context.getApplicationContext());
		View view = inflater.inflate(R.layout.custom_toast, null);
		TextView textView = (TextView) view.findViewById(R.id.toast_text);
		textView.setText(textResId);
		Toast toast = new Toast(context);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(view);
		toast.show();
	}

	public static void makeText(final Context context, @StringRes final int resId, final int duration){
		if (Looper.myLooper() == Looper.getMainLooper()){
			Toast.makeText(context,resId,duration).show();
		}else {
			new Handler(Looper.getMainLooper()).post(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(context,resId,duration).show();
				}
			});
		}

	}
	
	public static void showCustomToast(Context context, String message){
		LayoutInflater inflater = LayoutInflater.from(context.getApplicationContext());
		View view = inflater.inflate(R.layout.custom_toast, null);
		TextView textView = (TextView) view.findViewById(R.id.toast_text);
		textView.setText(message);
		Toast toast = new Toast(context);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(view);
		toast.show();
	}
	
	
	
	public static void showShareToast(Context context, boolean isSucceed){
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.custom_share_toast, null);
		if(isSucceed){
			Toast toast = new Toast(context);
			toast.setDuration(Toast.LENGTH_SHORT);
			toast.setView(view);
			toast.show();
		}
	}
}
