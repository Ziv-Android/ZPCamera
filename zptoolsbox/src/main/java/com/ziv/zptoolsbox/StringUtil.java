package com.ziv.zptoolsbox;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.text.TextUtils;

/**
 * String Operation Class
 * 
 * @author Ziv
 *
 */
public class StringUtil {

	/**
	 * Used judge String is empty
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isEmptyString(CharSequence str) {
		if (TextUtils.isEmpty(str) || str == null) {
			printLog("isEmtyString string is empty or null");
			return true;
		}
		return false;
	}

	/**
	 * Substring
	 * 
	 * @param soure
	 * @param cutlenth
	 * @return String
	 */
	public static String cutStringIfNeed(String soure, int cutlenth) {
		if (StringUtil.isEmptyString(soure) || cutlenth <= 0) {
			return "";
		}
		if (soure != null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(soure);
			soure = m.replaceAll("");
		}

		if (soure.length() <= cutlenth) {
			return soure;
		}
		return soure.substring(0, cutlenth);
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
