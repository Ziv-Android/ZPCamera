package com.ziv.zpcamera.filter;//package com.ziv.zpcamera.filter;
//
//import android.content.Context;
//import android.opengl.GLES10;
//import com.ziv.zpcamera.R;
//import com.ziv.zpcamera.gles.GlUtil;
//
//public class ImageFilterBlur extends CameraFilterGroup {
//    public ImageFilterBlur(Context applicationContext) {
//        super(applicationContext);
//    }
//
//    @Override public int getTextureTarget() {
//        return GLES10.GL_TEXTURE_2D;
//    }
//
//    @Override protected int createProgram(Context applicationContext) {
//        return GlUtil.createProgram(applicationContext, R.raw.vertex_shader,
//                R.raw.fragment_shader_2d_kernel);
//    }
//}
