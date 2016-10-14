package com.ziv.zpcamera;

import android.app.Application;

import com.ziv.zpcamera.video.TextureMovieEncoder;

public class App extends Application {

    @Override public void onCreate() {
        super.onCreate();
        TextureMovieEncoder.initialize(getApplicationContext());
    }
}
