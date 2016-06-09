package com.bateeqshop.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SyncResult;
import android.os.Bundle;
import android.util.Log;

import com.bateeqshop.R;
import com.bateeqshop.model.DashboardModel;
import com.bateeqshop.model.ProductCategory;
import com.bateeqshop.model.Query;
import com.bateeqshop.model.SlideShow;
import com.bateeqshop.rest.RestProvider;

import java.util.List;

import retrofit2.Call;

public class BateeqShopSyncAdapter extends AbstractThreadedSyncAdapter
{

    public final static String LOG_TAG = BateeqShopSyncAdapter.class.getSimpleName();
    public static final int SYNC_INTERVAL = 60 * 20;
    public static final int SYNC_FLEXTIME = SYNC_INTERVAL/3;
    public static final String SYNC_FINISHED = "com.bateeqshop.SYNC_FINISHED";
    public static final String SYNC_FAILED = "com.bateeqshop.SYNC_FAILED";
    private static boolean mIsPeriodicSyncStarted = false;

    public BateeqShopSyncAdapter(Context context, boolean autoInitialize)
    {
        super(context, autoInitialize);
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult)
    {
        try
        {
            Log.i(LOG_TAG, "Sync start");

            truncateAllData();
            blockSyncDashboardItems();

            Intent i = new Intent(SYNC_FINISHED);
            getContext().sendBroadcast(i);
            Log.i(LOG_TAG, "Sync done");
        }
        catch (Exception ex)
        {
            Intent i = new Intent(SYNC_FAILED);
            getContext().sendBroadcast(i);
            Log.e(LOG_TAG, "Sync failed : " + ex.getMessage());
        }

    }

    private void blockSyncDashboardItems() throws Exception
    {
        Call<DashboardModel> call = RestProvider.getResourceRest().getDashboardItems();
        DashboardModel dashboardModel = call.execute().body();
        List<ProductCategory> productCategories = dashboardModel.getData().getProductCategories();
        List<SlideShow> slideShows = dashboardModel.getData().getSlideShows();
        for (ProductCategory headerProductCategory: productCategories)
        {
            headerProductCategory.save();
            for (ProductCategory subProductCategory1 : headerProductCategory.getSubCategories())
            {
                subProductCategory1.save();
                for (ProductCategory subProductCategory2 : subProductCategory1.getSubCategories())
                {
                    subProductCategory2.save();
                }
            }
        }

        for (SlideShow slideShowItem : slideShows)
        {
            slideShowItem.save();
        }
    }

    private void truncateAllData()
    {
        Log.i(LOG_TAG, "Start truncate");
        Query.truncate(ProductCategory.class);
        Query.truncate(SlideShow.class);
    }

    public static Account getSyncAccount(Context context) {
        AccountManager accountManager =
                (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);

        Account newAccount = new Account(
                context.getString(R.string.app_name), context.getString(R.string.sync_account_type));

        if (null == accountManager.getPassword(newAccount)) {

            if (!accountManager.addAccountExplicitly(newAccount, "", null)) {
                return null;
            }
        }

        return newAccount;
    }

    public static void syncImmediately(Context context)
    {
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        ContentResolver.requestSync(getSyncAccount(context),
                context.getString(R.string.content_authority), bundle);
    }
}
