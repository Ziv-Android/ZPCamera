package com.ziv.zptoolsbox;

import java.io.File;

import android.os.Environment;

/**
 * Storage Path Class,you can get the sdcard file(dir) path ;
 * @author Ziv
 *
 */
public class StorageUtil {
	/**
	 * Check the SD card
	 * 
	 * @return boolean
	 */
	public static boolean checkSDCardAvailable() {
		return Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED);
	}
	
	/**
	 * get external storage path
	 * 
	 * @return Environment.getExternalStorageDirectory();
	 */
	public static File getExternalStorage() {
		return Environment.getExternalStorageDirectory();
	}
	
	/**
	 * get external storage absolute path
	 * 
	 * @return String
	 */
	public static String getExternalStoragePath() {
		printLog("getExternalStoragePictureDirectory = "+ Environment.getExternalStorageDirectory().getAbsolutePath());
		return Environment.getExternalStorageDirectory().getAbsolutePath();
	}
	
	/**
	 * print log
	 * 
	 * @param logStr
	 */
	public static void printLog(String logStr) {
		LogUtil.printLog(logStr);
	}
}
