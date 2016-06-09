package com.bateeqshop;

import com.activeandroid.app.Application;

public class BateeqApplication extends Application
{
    @Override
    public void onCreate() {
        super.onCreate();
        Session.initialize();
    }
}
