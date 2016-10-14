package com.ziv.zpcamera.filter.BlurFilter;

import android.content.Context;

import com.ziv.zpcamera.filter.CameraFilter;
import com.ziv.zpcamera.filter.FilterGroup;

public class ImageFilterGaussianBlur extends FilterGroup<CameraFilter> {

    public ImageFilterGaussianBlur(Context context, float blur) {
        super();
        addFilter(new ImageFilterGaussianSingleBlur(context, blur, false));
        addFilter(new ImageFilterGaussianSingleBlur(context, blur, true));
    }
}
