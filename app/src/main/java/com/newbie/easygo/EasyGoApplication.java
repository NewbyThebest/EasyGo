package com.newbie.easygo;

import android.app.Application;

public class EasyGoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MainManager.getInstance().initRetrofit();
    }
}
