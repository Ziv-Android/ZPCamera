package com.ziv.zpcamera;

import android.app.Application;
import android.os.Environment;

import com.ziv.zptoolsbox.CrashHandler;

/**
 * 自定义Application类，实现文件拷贝，信息初始化，崩溃日志捕获
 * Created by ziv on 16-9-27.
 */

public class ZPCameraApplication extends Application{
    public static final String SD_CARD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
    public static final String TAG = "ZPCameraApplication";
    public static final boolean DEBUG = true;

    @Override
    public void onCreate() {
        super.onCreate();
        // 崩溃日志
        CrashHandler.getInstance().init(this);
    }
}
