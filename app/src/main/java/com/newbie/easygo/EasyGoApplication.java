package com.newbie.easygo;

import android.app.Application;

import com.newbie.easygo.common.MainManager;

public class EasyGoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MainManager.getInstance().initRetrofit();
    }
}
