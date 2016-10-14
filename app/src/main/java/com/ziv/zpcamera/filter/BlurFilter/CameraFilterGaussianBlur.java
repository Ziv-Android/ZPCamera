package com.ziv.zpcamera.filter.BlurFilter;

import android.content.Context;

import com.ziv.zpcamera.filter.CameraFilter;
import com.ziv.zpcamera.filter.FilterGroup;

public class CameraFilterGaussianBlur extends FilterGroup<CameraFilter> {

    public CameraFilterGaussianBlur(Context context, float blur) {
        super();
        addFilter(new CameraFilterGaussianSingleBlur(context, blur, false));
        addFilter(new CameraFilterGaussianSingleBlur(context, blur, true));
    }
}
