package com.ziv.zpcamera;

import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

// 使用静态导入的方法，使用GLES2.0的方法
import static android.opengl.GLES20.*;

/**
 * GLSurfaceView的渲染线程
 * Created by ziv on 16-9-27.
 */
public class GLRenderer implements GLSurfaceView.Renderer {
    /**
     * Called when the surface is created or recreated.
     * 当GLSurfaceView初始创建时、设备唤醒时、重新启动该Activity时被回调
     * 在这个方法中主要用来设置一些绘制时不常变化的参数，比如：背景色
     */
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        /**
         * 每个屏幕窗口都会为GL分配buffer，最多为16个
         * 4个color buffer
         * 1个depth buffer
         * 1个stencil buffer
         * 1个多采样buffer
         * 1个或多个辅助buffer
         */
        // 完成简单的添加背景色的功能
        glClearColor(1.0f, 0.0f, 0.0f, 0.0f);
    }

    /**
     * Called when the surface changed size.
     * 当GLSurfaceView初始创建时，或者GLSurfaceView的大小发生改变时，比如设备旋转屏幕
     * 如果设备支持屏幕横向和纵向切换，在横向<->纵向互换时，该方法可以重新设置绘制的纵横比率
     */
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        glViewport(0, 0, width, height);
    }

    /**
     * Called to draw the current frame.
     * 会被GL线程循环使用，每循环一次，就会将内容绘制在屏幕上
     * 定义实际的绘图操作
     */
    @Override
    public void onDrawFrame(GL10 gl) {
        glClear(GL10.GL_COLOR_BUFFER_BIT);
    }
}
