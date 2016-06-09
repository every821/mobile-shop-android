package com.bateeqshop.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class BateeqShopSyncService extends Service {
    private static final Object sSyncAdapterLock = new Object();
    private static BateeqShopSyncAdapter sBateeqShopSyncAdapter = null;

    @Override
    public void onCreate() {
        synchronized (sSyncAdapterLock) {
            if (sBateeqShopSyncAdapter == null) {
                sBateeqShopSyncAdapter = new BateeqShopSyncAdapter(getApplicationContext(), true);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return sBateeqShopSyncAdapter.getSyncAdapterBinder();
    }
}
