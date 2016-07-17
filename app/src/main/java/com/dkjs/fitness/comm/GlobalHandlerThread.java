package com.dkjs.fitness.comm;

import android.os.HandlerThread;
import android.os.Looper;

public class GlobalHandlerThread {
	private static HandlerThread handlerThread;
	private static HandlerThread uiHandlerThread;
	
	public static synchronized Looper getHandlerThreadLooper() {
		if (handlerThread == null || !handlerThread.isAlive()){
			handlerThread = new HandlerThread("global_ht");
			handlerThread.start();
		}
		
		return handlerThread.getLooper();
	}
	
	public static synchronized Looper getUiHandlerThreadLooper() {
		if (uiHandlerThread == null || !uiHandlerThread.isAlive()){
			uiHandlerThread = new HandlerThread("global_ht_ui");
			uiHandlerThread.start();
		}
		
		return uiHandlerThread.getLooper();
	}
	
}
