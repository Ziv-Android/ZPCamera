package com.ziv.zptoolsbox;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

public class DeviceUtil {
	
	public static String getIMEI(Context context) {
		String imei = "";
		StringBuffer sb = new StringBuffer();
		try {
			TelephonyManager tm = (TelephonyManager) context
					.getSystemService(Application.TELEPHONY_SERVICE);
			if (tm != null) {
				imei = tm.getDeviceId();
				if (TextUtils.isEmpty(imei) || imei.indexOf("000000")!= -1 || imei.equals("012345678912345")) {//平板可能为15个0,012345678912345为公司通用安卓板子，该板子imei相同
					String androidId =  getAndroidId(context);
					if(!TextUtils.isEmpty(androidId)){
						for(int i=0;i<androidId.length();i++){
							char c = androidId.charAt(i);
							if( '0' <= c  && c <= '9'){
								sb.append(c);
							}
						}
					}
					sb.append(Build.BOARD.length() % 10)
					  .append(Build.BRAND.length() % 10)
					  .append(Build.CPU_ABI.length() % 10)
					  .append(Build.DEVICE.length() % 10)
					  .append(Build.DISPLAY.length() % 10)
					  .append(Build.HOST.length()% 10)
					  .append(Build.ID.length() % 10)
					  .append(Build.MANUFACTURER.length() % 10)
					  .append(Build.MODEL.length() % 10)
					  .append(Build.PRODUCT.length() % 10)
					  .append(Build.TAGS.length() % 10)
					  .append(Build.TYPE.length() % 10)
					  .append(Build.USER.length() % 10);
					imei = "35" + sb.toString().substring(0, 13);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return imei;
	}

	public static String getAndroidId(Context context) {
		String androidID = "";
		try {
			androidID = Secure.getString(context.getContentResolver(),
					Secure.ANDROID_ID);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return androidID;
	}
}
