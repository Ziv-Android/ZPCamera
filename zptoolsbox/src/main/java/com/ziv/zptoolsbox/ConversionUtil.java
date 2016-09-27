package com.ziv.zptoolsbox;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore.Images;
import android.provider.MediaStore.MediaColumns;
import android.util.TypedValue;

/**
 * About Conversion between dp and sp and px ,and Conversion between url and path;
 * @author Ziv
 *
 */
public class ConversionUtil {
//	private static final String TAG = "ConversionUtil";

	/**
	 * Dp to px
	 * 
	 * @param context
	 * @param dpVal
	 * @return int
	 */
	public static int dp2px(Context context, float dpVal) {
		int result = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, context.getResources().getDisplayMetrics());
		return result;
	}

	/**
	 * Sp to px
	 * 
	 * @param context
	 * @param spVal
	 * @return int
	 */
	public static int sp2px(Context context, float spVal) {
		int result = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal, context.getResources().getDisplayMetrics());
		return result;
	}

	/**
	 * Px to dp
	 * 
	 * @param context
	 * @param pxVal
	 * @return int
	 */
	public static int px2dp(Context context, float pxVal) {
		final float scale = context.getResources().getDisplayMetrics().density;
		int result = (int) (pxVal / scale);
		return result;
	}

	/**
	 * Px to sp
	 * 
	 * @param context
	 * @param pxVal
	 * @return float
	 */
	public static float px2sp(Context context, float pxVal) {
		int result = (int) (pxVal / context.getResources().getDisplayMetrics().scaledDensity);
		return result;
	}

	/**
	 * InputStream to byte[]
	 * 
	 * @param inputStream
	 * @return byte[]
	 */
	public static byte[] inputStreamToBytes(InputStream inputStream) {
		if (inputStream == null) {
			printLog("inputStreamToBytes---> InputStream is null");
			return null;
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] result = null;
		byte[] buffer = new byte[1024];
		int num;
		try {
			while ((num = inputStream.read(buffer)) != -1) {
				out.write(buffer, 0, num);
			}
			out.flush();
			result = out.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				inputStream.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * InputStream to byte[]
	 * 
	 * @param is
	 * @return
	 */
	public static byte[] inputStream2Bytes(InputStream is) {
		if (is == null) {
			printLog("inputStream2Bytes---> InputStream is null");
			return null;
		}
		String str = "";
		byte[] readByte = new byte[1024];
		try {
			while ((is.read(readByte, 0, 1024)) != -1) {
				str += new String(readByte).trim();
			}
			return str.getBytes();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * InputStream to Bitmap
	 * 
	 * @param InputStream
	 * @return Bitmap
	 */
	public static Bitmap inputStream2Bitmap(InputStream InputStream) {
		if (InputStream == null) {
			printLog("inputStream2Bitmap---> InputStream is null");
			return null;
		}
		return BitmapFactory.decodeStream(InputStream);
	}

	/**
	 * InputStream 2 drawable
	 * 
	 * @param inputStream
	 * @return
	 */
	public static Drawable inputStream2Drawable(InputStream inputStream) {
		if (inputStream == null) {
			printLog("inputStream2Drawable---> InputStream is null");
			return null;
		}
		Bitmap bitmap = inputStream2Bitmap(inputStream);
		return bitmap2Drawable(bitmap);
	}

	/**
	 * Byte[] to drawable
	 * 
	 * @param bytes
	 * @return Drawable
	 */
	public static Drawable bytes2Drawable(byte[] bytes) {
		if (bytes == null || bytes.length == 0) {
			printLog("bytes2Drawable---> b==null || b.length==0");
			return null;
		}
		Bitmap bitmap = bytes2Bitmap(bytes);
		return bitmap2Drawable(bitmap);
	}

	/**
	 * byte[] to InputStream
	 * 
	 * @param bytes
	 * @return InputStream
	 */
	public static InputStream byte2InputStream(byte[] bytes) {
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		return bais;
	}

	/**
	 * byte[] to Bitmap
	 * 
	 * @param bytes
	 * @return Bitmap
	 */
	public static Bitmap bytes2Bitmap(byte[] bytes) {
		if (bytes.length != 0) {
			return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
		}
		return null;
	}

	/**
	 * Bitmap to InputStream
	 * 
	 * @param bitmap
	 * @return InputStream
	 */
	public static InputStream bitmap2InputStream(Bitmap bitmap) {
		if (BitmapUtil.isEmty(bitmap)) {
			printLog("bitmap2InputStream---> bitmap is null");
			return null;
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		InputStream is = new ByteArrayInputStream(baos.toByteArray());
		return is;
	}

	/**
	 * Bitmap to byte[]
	 * 
	 * @param bitmap
	 * @return byte[]
	 */
	public static byte[] bitmap2Bytes(Bitmap bitmap) {
		if (BitmapUtil.isEmty(bitmap)) {
			printLog("bitmap2Bytes---> bitmap is null");
			return null;
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}

	/**
	 * Bitmap to drawable
	 * 
	 * @param bitmap
	 * @return Drawable
	 */
	@SuppressWarnings("deprecation")
	public static Drawable bitmap2Drawable(Bitmap bitmap) {
		if (BitmapUtil.isEmty(bitmap)) {
			printLog("bitmap2Drawable---> bitmap is null");
			return null;
		}
		BitmapDrawable bd = new BitmapDrawable(bitmap);
		Drawable d = (Drawable) bd;
		return d;
	}

	/**
	 * Drawable 2 InputStream
	 * 
	 * @param drawable
	 * @return InputStream
	 */
	public static InputStream drawable2InputStream(Drawable drawable) {
		if (drawable == null) {
			printLog("drawable2InputStream---> drawable is null");
			return null;
		}
		Bitmap bitmap = drawable2Bitmap(drawable);
		return bitmap2InputStream(bitmap);
	}

	/**
	 * Drawable to byte[]
	 * 
	 * @param drawable
	 * @return byte[]
	 */
	public static byte[] drawable2Bytes(Drawable drawable) {
		if (drawable == null) {
			printLog("drawable2Bytes---> drawable is null");
			return null;
		}
		Bitmap bitmap = drawable2Bitmap(drawable);
		return bitmap2Bytes(bitmap);
	}

	/**
	 * Drawable to bitmap
	 * 
	 * @param drawable
	 * @return Bitmap
	 */
	public static Bitmap drawable2Bitmap(Drawable drawable) {
		if (drawable == null) {
			printLog("drawable2Bitmap---> drawable is null");
			return null;
		}
		Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
				drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
		drawable.draw(canvas);
		return bitmap;
	}

	/**
	 * Uri transform path(/mnt/storage/emulated/0" transform to "/sdcard")
	 * 
	 * @param context
	 * @param fileUrl
	 * @return String
	 */
	@SuppressLint("SdCardPath")
	public static String uri2Path(Context context, Uri fileUrl) {
		String fileName = null;
		Uri filePathUri = fileUrl;
		if (fileUrl != null) {
			if (fileUrl.getScheme().toString().compareTo("content") == 0) {
				Cursor cursor = context.getContentResolver().query(Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
				if (cursor != null && cursor.moveToFirst()) {
					int column_index = cursor.getColumnIndexOrThrow(Images.Media.DATA);
					fileName = cursor.getString(column_index);
					if (fileName.startsWith("/mnt/storage/emulated/0")) {
						fileName = fileName.replace("/mnt/storage/emulated/0", "/sdcard");
					}
					cursor.close();
				}
			} else if (fileUrl.getScheme().compareTo("file") == 0) {
				fileName = filePathUri.toString();
				fileName = filePathUri.toString().replace("file://", "");
			}
		}
		return fileName;
	}

	/**
	 * Uri 2 path if uri is null or "" ,the method will return null;
	 * 
	 * @param activity
	 * @param fileUrl
	 * @return String
	 */
	public static String uri2Path(Activity activity, Uri fileUrl) {
		if (fileUrl == null || fileUrl.equals("")) {
			printLog("--uri2Path-->originalUri null ----");
			return null;
		}
		String[] proj = { MediaColumns.DATA };
		@SuppressWarnings("deprecation")
		Cursor cursor = activity.managedQuery(fileUrl, proj, null, null, null);
		int column_index = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
		cursor.moveToFirst();
		String imagePath = cursor.getString(column_index);
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			cursor.close();
		}
		return imagePath;
	}

	/**
	 * path transform Uri("content://media/external/images/media/")
	 * 
	 * @param activity
	 * @param path
	 * @return
	 */
	public static Uri path2Uri(Activity activity, String path) {
		Uri uri = Uri.parse("content://media/external/images/media/");
		path = uri.getEncodedPath();
		if (path != null) {
			path = Uri.decode(path);
			ContentResolver cr = activity.getContentResolver();
			StringBuffer buff = new StringBuffer();
			buff.append("(").append(Images.ImageColumns.DATA).append("=").append("'" + path + "'").append(")");
			Cursor cur = cr.query(Images.Media.EXTERNAL_CONTENT_URI, new String[] { Images.ImageColumns._ID }, buff.toString(), null, null);
			int index = 0;
			for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
				index = cur.getColumnIndex(Images.ImageColumns._ID);
				index = cur.getInt(index);
			}
			cur.close();
			if (index == 0) {
			} else {
				Uri uri_temp = Uri.parse("content://media/external/images/media/" + index);
				printLog("path2Uri " + uri_temp.toString());
				if (uri_temp != null) {
					uri = uri_temp;
				}
			}
		}
		return uri;
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
