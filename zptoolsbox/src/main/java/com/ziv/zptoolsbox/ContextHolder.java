package com.ziv.zptoolsbox;

import android.content.Context;

/**
 * Provide a global variable of Context
 * 
 * @author SenseTime
 *
 */
public class ContextHolder {
	private Context mContext;
	// TODO: 16-9-27 static reference will lead to memory leak
	private static ContextHolder sHolder = null;

	public static void init(Context context) {
		sHolder = new ContextHolder(context);
	}

	private ContextHolder(Context context) {
		mContext = context;
	}

	public static void setContext(Context context) {
		sHolder.mContext = context;
	}

	public static Context getContext() {
		if (sHolder == null) {
			throw new RuntimeException("before using ContextHolder,you should init ContextHolder to get global variable");
		}
		return sHolder.mContext;
	}

}
