package com.ziv.zpcamera;

import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    // 窗口
    private GLSurfaceView glSurfaceView;
    // 判断GL是否开始运行
    private boolean hasRenderer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        glSurfaceView = new GLSurfaceView(this);
        // 使用ActivityManager的getDeviceConfigureationInfo().reqGLEsVersion
        // 如果大于0x20000，则支持OpenGL ES2.0

        // 为GLSurfaceView的窗口配置OpenGL ES2.0的上下文信息
        glSurfaceView.setEGLContextClientVersion(2);
        // 为GLSurfaceView的窗口配置渲染线程
        glSurfaceView.setRenderer(new GLRenderer());

        // 优化选项
        // RENDERMODE_WHEN_DIRTY    只在创建和调用requestRender时才会重新绘制
        // RENDERMODE_CONTINUOUSLY  始终重绘
        glSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

        hasRenderer = true;

        setContentView(glSurfaceView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (hasRenderer){
            glSurfaceView.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (hasRenderer){
            glSurfaceView.onPause();
        }
    }
}
