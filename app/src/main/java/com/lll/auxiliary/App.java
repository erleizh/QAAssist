package com.lll.auxiliary;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

/**
 * Created by lll on 2018/1/14.
 * app
 */
public class App extends Application {

    @SuppressLint("StaticFieldLeak")
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this.getBaseContext();
    }

    public static Context getContext() {
        return mContext;
    }
}
