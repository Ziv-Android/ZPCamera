package com.ziv.zptoolsbox;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

/**
 * UncaughtException processing class, when the program Uncaught exception
 * occurs, the class to take over procedures, and reports the error to the
 * record.
 * 
 * @author Ziv
 * 
 */
public class CrashHandler implements UncaughtExceptionHandler {

	public static final String TAG = "CrashHandler";

	// The system default UncaughtException processing class
	private UncaughtExceptionHandler mDefaultHandler;
	// CrashHandler instance
	private static CrashHandler INSTANCE = new CrashHandler();
	// The Context object of the program
	private Context mContext;
	// Used to store equipment information and exception information
	private Map<String, String> infos = new HashMap<String, String>();

	// For formatting dates, as part of the log file name
	private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

	/** Ensure that only a CrashHandler instance */
	private CrashHandler() {
	}

	/** Get CrashHandler instance, singleton pattern */
	public static CrashHandler getInstance() {
		return INSTANCE;
	}

	/**
	 * Initialize
	 * 
	 * @param context
	 */
	public void init(Context context) {
		mContext = context;
		// Access to the system default UncaughtException processor
		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		// Sets the default this CrashHandler for application processor
		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	/**
	 * When UncaughtException happened into the function to deal with
	 */
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		if (!handleException(ex) && mDefaultHandler != null) {
			// If the user does not deal with the system default exception
			// handler to handle
			mDefaultHandler.uncaughtException(thread, ex);
		} else {
			try {
				Thread.sleep(1000);
				Intent intent = new Intent(mContext, getTopActivity());
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
				// mContext.startActivity(intent);
			} catch (InterruptedException e) {
				Log.e(TAG, "error : ", e);
			}
			android.os.Process.killProcess(android.os.Process.myPid());
			System.exit(0);
		}
	}

	public Class<?> getTopActivity() {
		ActivityManager manager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
		String className = manager.getRunningTasks(1).get(0).topActivity.getClassName();
		Class<?> cls = null;
		try {
			cls = Class.forName(className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return cls;
	}

	/**
	 * Custom error handling, collection error message send error reports are
	 * completed in this operation.
	 * 
	 * @param ex
	 * @return true:If the exception handling information;Otherwise it returns
	 *         false.
	 */
	private boolean handleException(final Throwable ex) {
		if (ex == null) {
			return false;
		}
		new Thread() {
			@Override
			public void run() {
				Looper.prepare();
				if (ex.getMessage().equals("wrong device")) {
					Toast.makeText(mContext, mContext.getString(R.string.authorization_exit), Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(mContext, mContext.getString(R.string.exception_exit), Toast.LENGTH_LONG).show();
				}
				Looper.loop();
			}
		}.start();
		collectDeviceInfo(mContext);
		saveCrashInfo2File(ex);
		return true;
	}

	/**
	 * Collect equipment parameter information
	 * 
	 * @param ctx
	 */
	public void collectDeviceInfo(Context ctx) {
		try {
			PackageManager pm = ctx.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
			if (pi != null) {
				String versionName = pi.versionName == null ? "null" : pi.versionName;
				String versionCode = pi.versionCode + "";
				infos.put("versionName", versionName);
				infos.put("versionCode", versionCode);
			}
		} catch (NameNotFoundException e) {
			Log.e(TAG, "an error occured when collect package info", e);
		}
		Field[] fields = Build.class.getDeclaredFields();
		for (Field field : fields) {
			try {
				field.setAccessible(true);
				infos.put(field.getName(), field.get(null).toString());
				Log.d(TAG, field.getName() + " : " + field.get(null));
			} catch (Exception e) {
				Log.e(TAG, "an error occured when collect crash info", e);
			}
		}
	}

	/**
	 * Save error information to a file
	 * 
	 * @param ex
	 * @return Returns the file name, easy to transfer files to the server
	 */
	private String saveCrashInfo2File(Throwable ex) {

		StringBuffer sb = new StringBuffer();
		for (Map.Entry<String, String> entry : infos.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			sb.append(key + "=" + value + "\n");
		}

		Writer writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		ex.printStackTrace(printWriter);
		Throwable cause = ex.getCause();
		while (cause != null) {
			cause.printStackTrace(printWriter);
			cause = cause.getCause();
		}
		printWriter.close();
		String result = writer.toString();
		sb.append(result);
		try {
			long timestamp = System.currentTimeMillis();
			String time = formatter.format(new Date());
			String fileName = "crash-" + time + "-" + timestamp + ".log";
			if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
				String path = "/sdcard/crash/";
				File dir = new File(path);
				if (!dir.exists()) {
					dir.mkdirs();
				}
				FileOutputStream fos = new FileOutputStream(path + fileName);
				fos.write(sb.toString().getBytes());
				fos.close();
			}
			return fileName;
		} catch (Exception e) {
			Log.e(TAG, "an error occured while writing file...", e);
		}
		return null;
	}
}
