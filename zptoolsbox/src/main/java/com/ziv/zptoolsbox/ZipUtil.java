package com.ziv.zptoolsbox;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

import android.text.TextUtils;
import android.util.Log;

/**
 * Zip Class,Used to save and unzip the files
 * @author Ziv
 *
 */
public class ZipUtil {
	private static final String TAG = "ZipUtil";

	/**
	 * Copy resource file(zipFilePath) to targetPath and unzip the file
	 * 
	 * @param zipFilePath
	 * @param targetPath
	 */
	public static void unzip(String zipFilePath, String targetPath) {
		if (TextUtils.isEmpty(zipFilePath)) {
			if (LogUtil.DEBUG) {
				Log.d(TAG, "unzip---> zipFilePath is  empty");
				return;
			}
		}
		if (TextUtils.isEmpty(targetPath)) {
			targetPath = zipFilePath.substring(0, zipFilePath.indexOf("."));
		}
		File file;
		try {
//			ZipFile zipFile = new ZipFile(zipFilePath, "GBK");
			ZipFile zipFile = new ZipFile(zipFilePath, "UTF-8");
			for (Enumeration<?> entries = zipFile.getEntries(); entries.hasMoreElements();) {
				ZipEntry entry = (ZipEntry) entries.nextElement();
				file = new File(targetPath, entry.getName());
				if (entry.isDirectory()) {
					file.mkdirs();
				} else {
					File parent = file.getParentFile();
					if (!parent.exists()) {
						parent.mkdirs();
					}
					InputStream inputStream = zipFile.getInputStream(entry);
					FileOutputStream fos = new FileOutputStream(file.getAbsolutePath());
					byte[] buf = new byte[1024];
					int len;
					while ((len = inputStream.read(buf)) > 0) {
						fos.write(buf, 0, len);
						fos.flush();
					}
					fos.close();
					inputStream.close();
				}
			}
			zipFile.close();
		} catch (IOException io) {
			io.printStackTrace();
		}
	}
}