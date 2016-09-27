package com.ziv.zptoolsbox;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;

/**
 * Used the class of AppInfoUtil to get Application info include VersionName or
 * VersionCode or other;
 * 
 * @author Ziv
 *
 */
public class AppInfoUtil {
	private static final String TAG = "AppUtil";

	/**
	 * Used to get app name
	 * 
	 * @param context
	 * @return String
	 */
	public static String getAppName(Context context) {
		if (context == null) {
			printLog("getAppName:context is null");
			return null;
		}
		PackageInfo packageInfo = getPackageInfo(context);
		if (packageInfo == null) {
			printLog("getAppName:NameNotFoundException");
			return null;
		}
		int labelRes = packageInfo.applicationInfo.labelRes;
		String appName = context.getResources().getString(labelRes);
		return appName;
	}

	/**
	 * used to get version name
	 * 
	 * @param context
	 * @return String
	 */
	public static String getVersionName(Context context) {
		if (context == null) {
			printLog("getVersionName:context is null");
			return null;
		}
		PackageInfo packageInfo = getPackageInfo(context);
		if (packageInfo == null) {
			printLog("getPackageName:NameNotFoundException");
			return null;
		}
		return packageInfo.versionName;
	}

	/**
	 * Used to get version code
	 * 
	 * @param context
	 * @return int
	 */
	public static int getVersionCode(Context context) {
		if (context == null) {
			printLog("getVersionCode:context is null");
			return 0;
		}
		PackageInfo packageInfo = getPackageInfo(context);
		if (packageInfo == null) {
			printLog("getVersionCode:NameNotFoundException");
			return 0;
		}
		return packageInfo.versionCode;
	}

	/**
	 * Used to judge the version of app is latestest version or not;
	 * 
	 * @param context
	 * @param versionName
	 * @return boolean
	 */
	public static boolean isLatestVersion(Context context, String versionName) {
		if (versionName.equals(getVersionName(context))) {
			return true;
		}

		return false;
	}

	/**
	 * Used to get package info
	 * 
	 * @param context
	 * @return PackageInfo
	 */
	public static PackageInfo getPackageInfo(Context context) {
		PackageManager packageManager = context.getPackageManager();
		PackageInfo packageInfo = null;
		try {
			packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return packageInfo;
	}

	/**
	 * print log
	 * 
	 * @param logStr
	 */
	public static void printLog(String logStr) {
		if (LogUtil.DEBUG) {
			Log.d(TAG, logStr);
		}
	}
}
