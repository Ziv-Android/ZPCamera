package com.ziv.zptoolsbox;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

/**
 * Screen Info Class ,you can get relative info of mobile phone screen include width or height or other;
 * @author Ziv
 *
 */
public class ScreenUtil {

	/**
	 * get screen width
	 * 
	 * @param context
	 * @return int
	 */
	public static int getScreenWidth(Context context) {
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		int width = outMetrics.widthPixels;
		return width;
	}

	/**
	 * get screen height
	 * 
	 * @param context
	 * @return int
	 */
	public static int getScreenHeight(Context context) {
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		int height = outMetrics.heightPixels;
		return height;
	}

	/**
	 * get screen status height
	 * 
	 * @param context
	 * @return int
	 */
	public static int getStatusHeight(Context context) {
		int statusHeight = -1;
		try {
			Class<?> clazz = Class.forName("com.android.internal.R$dimen");
			Object object = clazz.newInstance();
			int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
			statusHeight = context.getResources().getDimensionPixelSize(height);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return statusHeight;
	}

	/**
	 * get relative top
	 * 
	 * @param myView
	 * @return int
	 */
	public static int getRelativeTop(View myView) {
		if (myView.getId() == android.R.id.content)
			return myView.getTop();
		else
			return myView.getTop() + getRelativeTop((View) myView.getParent());
	}

	/**
	 * get relative left
	 * 
	 * @param myView
	 * @return int
	 */
	public static int getRelativeLeft(View myView) {
		if (myView.getId() == android.R.id.content)
			return myView.getLeft();
		else
			return myView.getLeft() + getRelativeLeft((View) myView.getParent());
	}

}
