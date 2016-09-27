package com.ziv.zptoolsbox;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;

import android.util.Log;
/**
 * MD5 operation,Apply some methods to get the MD5 of file;
 * @author Ziv
 *
 */
public class MD5Util {

	public static final String TAG = "MD5Util";
	public static final int MAGIC_NUM_3 = 3;

	public static final int MAGIC_NUM_4 = 4;

	public static final int MAGIC_NUM_16 = 16;

	public static final int MAGIC_NUM_HEX = 0xf;

	private static char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	/**
	 * Used to get MD5 of file which path is filePath
	 * 
	 * @param filePath
	 * @return String
	 */
	public static String getMD5(String filePath) {
		String s = null;
		try {
			byte[] source = filePath.getBytes();
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(source);
			return toHexString(md.digest());
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
		return s;
	}

	/**
	 * Converts a byte array into a hexadecimal string
	 * 
	 * @param data
	 * @return String
	 */
	public static String toHexString(byte[] data) {
		try {
			if (data != null && data.length > 0) {
				StringBuilder builder = new StringBuilder();

				for (int i = 0; i < MAGIC_NUM_16; i++) {
					byte byte0 = data[i];
					builder.append(hexDigits[byte0 >>> MAGIC_NUM_4 & MAGIC_NUM_HEX]);
					builder.append(hexDigits[byte0 & MAGIC_NUM_HEX]);
				}
				return builder.toString();
			}
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
		return null;
	}

	/**
	 * Used to get  md5 of file 
	 * @param filePath
	 * @return String
	 */
	public static String md5sum(String filePath) {
		return md5sum(new File(filePath));
	}

	/**
	 * Used to get file md5
	 * @param file
	 * @return String
	 */
	public static String md5sum(File file) {
		InputStream fis = null;
		byte[] buffer = new byte[1024 * 8];
		int numRead = 0;
		MessageDigest md5;
		try {
			fis = new FileInputStream(file);
			md5 = MessageDigest.getInstance("MD5");
			while ((numRead = fis.read(buffer)) > 0) {
				md5.update(buffer, 0, numRead);
			}
			return toHexStringMd5(md5.digest());
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
			return null;
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * bytes to md5
	 * 
	 * @param bytes
	 * @return String
	 */
	public static String toHexStringMd5(byte[] bytes) {
		if (bytes == null || bytes.length == 0) {
			return null;
		}

		int j = bytes.length;
		char str[] = new char[j * 2];
		int k = 0;
		for (int i = 0; i < j; i++) {
			byte byte0 = bytes[i];
			str[k++] = hexDigits[byte0 >>> 4 & 0xf];
			str[k++] = hexDigits[byte0 & 0xf];
		}
		return new String(str);
	}

}
