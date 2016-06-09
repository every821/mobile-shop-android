package com.bateeqshop;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bateeqshop.sync.BateeqShopSyncAdapter;

public class LoadingActivity extends AppCompatActivity {

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_loading);
        initSyncStatusBroadcastReceiver();
        BateeqShopSyncAdapter.syncImmediately(this);
    }

    private void initSyncStatusBroadcastReceiver()
    {
        IntentFilter syncIntentFilter = new IntentFilter();
        syncIntentFilter.addAction(BateeqShopSyncAdapter.SYNC_FINISHED);
        syncIntentFilter.addAction(BateeqShopSyncAdapter.SYNC_FAILED);

        BroadcastReceiver syncBroadcastReceiver = new BroadcastReceiver() {
            @Override public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(BateeqShopSyncAdapter.SYNC_FINISHED))
                {
                    unregisterReceiver(this);
                    finish();
                }

                if (intent.getAction().equals(BateeqShopSyncAdapter.SYNC_FAILED))
                {
                    BateeqShopSyncAdapter.syncImmediately(mContext);
                }
            }
        };
        registerReceiver(syncBroadcastReceiver, syncIntentFilter);

    }
}
