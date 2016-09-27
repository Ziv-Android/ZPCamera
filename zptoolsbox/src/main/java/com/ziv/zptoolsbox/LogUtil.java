package com.ziv.zptoolsbox;

import android.util.Log;

/**
 * Log Print Class,Apply the method to print E or D or I or V or W info,you can use default tag(LogUtil) or define tag;
 * @author Ziv
 *
 */
public class LogUtil {

	public static boolean DEBUG = true;
	public static String TAG = "LogUtil";
	
	static void e(Object object){
		e(TAG,object);
	}
	
	/**
	 * Loacat error
	 * @param tag
	 * @param object
	 */
	public static void e(String tag,Object object) {
		if (DEBUG) {
			if (null != object) {
				Log.e(tag, object.toString());
			} else {
				Log.e(tag, "null");
			}
		}
	}
	
	static void d(Object object){
		d(TAG,object);
	}
	
	/**
	 * Logcat debug
	 * @param tag
	 * @param object
	 */
	public static void d(String tag,Object object) {
		if (DEBUG) {
			if (null != object) {
				Log.d(tag, object.toString());
			} else {
				Log.d(tag, "null");
			}
		}
	}
	
	static void w(Object object){
		w(TAG,object);
	}
	
	/**
	 * Logcat warn
	 * @param tag
	 * @param object
	 */
	public static void w(String tag,Object object) {
		if (DEBUG) {
			if (null != object) {
				Log.w(tag, object.toString());
			} else {
				Log.w(tag, "null");
			}
		}
	}
	
	static void i(Object object){
		i(TAG,object);
	}
	
	/**
	 * Logcat info
	 * @param tag
	 * @param object
	 */
	static void i(String tag,Object object) {
		if (DEBUG) {
			if (null != object) {
				Log.i(TAG, object.toString());
			} else {
				Log.i(TAG, "null");
			}
		}
	}
	
	static void v(Object object){
		v(TAG,object);
	}
	
	/**
	 * Locat verbose
	 * @param tag
	 * @param object
	 */
	public static void v(String tag,Object object) {
		if (DEBUG) {
			if (null != object) {
				Log.v(TAG, object.toString());
			} else {
				Log.v(TAG, "null");
			}
		}
	}
	
	/**
	 * 
	 * @param logStr
	 */
	public static void printLog(String logStr){
		if (LogUtil.DEBUG) {
			Log.d(TAG, logStr);
		}
	}
	
}
