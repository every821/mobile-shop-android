package com.bateeqshop.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class BateeqShopAuthenticatorService extends Service
{
    private BateeqShopAuthenticator mAuthenticator;

    @Override
    public void onCreate() {
        mAuthenticator = new BateeqShopAuthenticator(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mAuthenticator.getIBinder();
    }
}
