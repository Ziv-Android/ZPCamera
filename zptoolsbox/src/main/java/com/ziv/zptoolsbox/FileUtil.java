package com.ziv.zptoolsbox;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.text.TextUtils;

/**
 * File operation class,Apply create\load\copy\exist\delete or other method;
 * 
 * @author Ziv
 *
 */
public class FileUtil {
	private static final String TAG = "FileUtil";
	private static final String SD_CARD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
	private static final String SYSTEM_PHOTO_PATH = SD_CARD_PATH + "/DCIM/Camera/";

	private static final FileComparator sFileComparator = new FileComparator();
	private static final String IMAGE_JPG = ".jpg";

	/**
	 * file is exist and the file is not Directory
	 * 目标是文件并且存在
	 * @param filePath 文件路径
	 * @return boolean
	 */
	public static boolean isFileExist(String filePath) {
		File file = new File(filePath);
		return file.exists() && !file.isDirectory();
	}

	/**
	 * file is exist and the file is Directory
	 * 目标是文件夹并且存在
	 * @param dirPath 文件夹目录
	 * @return boolean
	 */
	public static boolean isDirExist(String dirPath) {
		File file = new File(dirPath);
		return file.exists() && file.isDirectory();
	}

	public static boolean isPathExist(String path) {
		if (StringUtil.isEmptyString(path)) {
			printLog("isPathExist path is null");
			return false;
		}
		return new File(path).exists();
	}

	/**
	 * Create Directory For Path
	 * 
	 * @param path
	 * @return boolean
	 */
	public static boolean createDirectory(String path) {
		if (TextUtils.isEmpty(path)) {
			printLog("createDirectory path is empty");
			return false;
		}
		File dirFile = new File(path);
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			return dirFile.mkdirs();
		}
		return true;
	}

	/**
	 * assert_path resources copy into Local SD card
	 * 
	 * @param assertPathDir
	 * @param dirPath
	 */
	public static void copyAssertDirToLocalIfNeed(Context context, String assertPathDir, String dirPath) {
		File pictureDir = new File(dirPath);
		if (!pictureDir.exists() || !pictureDir.isDirectory()) {
			pictureDir.mkdirs();
		}
		try {
			String[] fileNames = context.getAssets().list(assertPathDir);
			if (fileNames.length == 0)
				return;
			for (int i = 0; i < fileNames.length; i++) {
				File file = new File(dirPath + File.separator + fileNames[i]);
				if (file.exists() && file.isFile()) {
					if (compareFile(context, dirPath + File.separator + fileNames[i], fileNames[i])) {
						printLog("-->copyAssertDirToLocalIfNeed " + file.getName() + " exists");
						continue;
					}
				}
				InputStream is = context.getAssets().open(assertPathDir + File.separator + fileNames[i]);
				int size = is.available();
				byte[] buffer = new byte[size];
				is.read(buffer);
				is.close();
				String mypath = dirPath + File.separator + fileNames[i];
				FileOutputStream fop = new FileOutputStream(mypath);
				fop.write(buffer);
				fop.flush();
				fop.close();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Used to copy assert file into Local SD card
	 * 
	 * @param context
	 * @param assertPath
	 * @param strOutFileName
	 * @param isCover
	 * @throws IOException
	 */
	public static void copyAssertFileToLocalIfNeed(Context context, String assertPath, String strOutFileName, boolean isCover) throws IOException {
		File file = new File(strOutFileName);
		if (file.exists() && file.isFile() && !isCover) {
			printLog("copyAssertFileToLocalIfNeed " + file.getName() + " exists");
			return;
		}
		InputStream myInput;
		OutputStream myOutput = new FileOutputStream(strOutFileName);
		myInput = context.getAssets().open(assertPath);
		byte[] buffer = new byte[1024];
		int length = myInput.read(buffer);
		while (length > 0) {
			myOutput.write(buffer, 0, length);
			length = myInput.read(buffer);
		}
		myOutput.flush();
		myInput.close();
		myOutput.close();
	}

	/**
	 * 获取指定文件大小
	 * 
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static long getFileSize(String path) {
		File f = new File(path);
		return getFileSize(f);
	}

	/**
	 * 获取指定文件大小
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static long getFileSize(File file) {
		long size = 0;
		if (!file.exists() || file.isDirectory()) {
			printLog("getFileSize file is not exists or isDirectory !");
			return 0;
		}
		if (file.exists()) {
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(file);
				size = fis.available();
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return size;
	}

	/**
	 * 获取Asset目录下某个文件的大小，非目录
	 * 
	 * @param context
	 * @param path
	 * @return
	 */
	public static long getAssertFileSize(Context context, String path) {
		if (context == null || path == null || "".equals(path)) {
			printLog("getAssertFileSize context is null or path is null !");
			return 0;
		}
		AssetManager assetManager = context.getAssets();
		int size = 0;
		try {
			InputStream inStream = assetManager.open(path);
			size = inStream.available();
			inStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return size;
	}

	/**
	 * 比较sd卡文件与asset目录下的文件大小是否一致
	 * 
	 * @param context
	 * @param filePath
	 * @param assetPath
	 * @return
	 */
	public static boolean compareFile(Context context, String filePath, String assetPath) {
		boolean isSameFile = false;
		File file = new File(filePath);
		if (!file.exists() || file.isDirectory()) {
			isSameFile = false;
		}
		if (getFileSize(file) == getAssertFileSize(context, assetPath)) {
			isSameFile = true;
		}
		return isSameFile;
	}

	/**
	 * Delete file by File
	 * 
	 * @param filePath
	 */
	public static void deleteFile(String filePath) {
		if (StringUtil.isEmptyString(filePath)) {
			printLog("deleteFile path is null");
			return;
		}
		File file = new File(filePath);
		deleteFile(file);
	}

	/**
	 * Delete file by File
	 * 
	 * @param file
	 */
	public static void deleteFile(File file) {
		if (null == file) {
			printLog("deleteFile file is null");
			return;
		}

		if (!file.exists() || !file.isFile()) {
			printLog("deleteFile file is not exists or file is dir!");
			return;
		}

		file.delete();
	}

	/**
	 * Used to clear file of directory
	 * 
	 * @param path
	 * @param isDeleteThisDir
	 */
	public static void clearDir(String path, boolean isDeleteThisDir) {
		if (StringUtil.isEmptyString(path)) {
			printLog("clearDir path is null");
			return;
		}
		File file = new File(path);
		clearDir(file, isDeleteThisDir);
	}

	/**
	 * Delete the dir in SD card
	 * 
	 * @param dirFile
	 * @param isDeleteThisDir
	 * 
	 */
	public static void clearDir(File dirFile, boolean isDeleteThisDir) {
		if (!StorageUtil.checkSDCardAvailable() || dirFile == null) {
			printLog("clearDir dirFile is null");
			return;
		}
		try {
			if (dirFile.isDirectory()) {
				File files[] = dirFile.listFiles();
				for (int i = 0; i < files.length; i++) {
					clearDir(files[i], true);
				}
			}
			if (isDeleteThisDir) {
				if (!dirFile.isDirectory()) {
					dirFile.delete();
				} else {
					if (dirFile.listFiles().length == 0) {
						dirFile.delete();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Used to get System Camera Photo Path
	 * 
	 * @return String
	 */
	@SuppressLint("SimpleDateFormat")
	public static String getSystemCameraPhotoPath() {
		printLog("genSystemCameraPhotoPath SYSTEM_PHOTO_PATH ---> " + SYSTEM_PHOTO_PATH);
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		createDirectory(SYSTEM_PHOTO_PATH);
		return SYSTEM_PHOTO_PATH + "IMAGE_" + timeStamp + IMAGE_JPG;
	}

	/**
	 * Used to get the file name if file is exist
	 * 
	 * @param filePath
	 * @return String
	 */
	public static String getFileName(String filePath) {
		File file = new File(filePath);
		if (file.exists()) {
			return file.getName();
		}
		return null;
	}

	/**
	 * Used to get the files of direction and put into list<String>
	 * 
	 * @param dirPath
	 * @return List<String>
	 */
	public static List<String> loadChildFiles(String dirPath) {
		if (StringUtil.isEmptyString(dirPath)) {
			printLog("loadImages---> path is null");
			return null;
		}
		List<String> fileList = new ArrayList<String>();
		File file = new File(dirPath);
		if (!file.exists()) {
			return fileList;
		}
		File[] files = file.listFiles();
		List<File> allFiles = new ArrayList<File>();
		for (File img : files) {
			allFiles.add(img);
		}
		Collections.sort(allFiles, sFileComparator);
		for (File img : allFiles) {
			fileList.add(img.getAbsolutePath());
		}
		return fileList;
	}

	/**
	 * Put the file into byte[]
	 * 
	 * @param filePath
	 * @return byte[]
	 */
	public static byte[] readFile(String filePath) {
		if (StringUtil.isEmptyString(filePath) && isPathExist(filePath)) {
			printLog("readFile path is null");
			return null;
		}
		try {
			FileInputStream fis = new FileInputStream(filePath);
			int length = fis.available();
			byte[] buffer = new byte[length];
			fis.read(buffer);
			fis.close();
			return buffer;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Save the file to savePath if file is exist
	 * 
	 * @param savePath
	 * @param data
	 */
	public static void saveFile(String savePath, byte[] data) {
		if (data == null || data.length == 0) {
			printLog("saveFile data is null");
			return;
		}
		try {
			File file = new File(savePath);
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(data);
			fos.flush();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static class FileComparator implements Comparator<File> {
		public int compare(File file1, File file2) {
			if (file1.lastModified() < file2.lastModified()) {
				return -1;
			} else {
				return 1;
			}
		}
	}

	/**
	 * Print log
	 * 
	 * @param logStr
	 */
	public static void printLog(String logStr) {
		LogUtil.printLog(logStr);
	}
}
