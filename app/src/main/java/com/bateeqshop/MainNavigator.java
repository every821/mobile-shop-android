package com.bateeqshop;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.bateeqshop.fragments.AddToCartDialogFragment;
import com.bateeqshop.fragments.CatalogueFragment;
import com.bateeqshop.fragments.ChangeColorSizeShoppingCartDialogFragment;
import com.bateeqshop.fragments.CheckOrderFragment;
import com.bateeqshop.fragments.DashboardFragment;
import com.bateeqshop.fragments.DevFragment;
import com.bateeqshop.fragments.ProductDetailFragment;
import com.bateeqshop.fragments.ProductFragment;
import com.bateeqshop.fragments.RemoveItemShoppingCartDialogFragment;
import com.bateeqshop.fragments.ShippingAddressFragment;
import com.bateeqshop.fragments.ShoppingCartFragment;
import com.bateeqshop.model.Product;

public class MainNavigator
{
    private static final int CONTAINER_ID = R.id.content_main;

    public static void navigateToMainActivity(Context context)
    {
        final Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    public static void navigateToLoadingActivity(Context context)
    {
        final Intent intent = new Intent(context, LoadingActivity.class);
        context.startActivity(intent);
    }

    public static void navigateToVeritransActivity(Context context)
    {
        final Intent intent = new Intent(context, VeritransActivity.class);
        context.startActivity(intent);
    }

    public static void showDashboardFragment(Context context)
    {
        final FragmentTransaction transaction = getFragmentManager(context).beginTransaction();
        transaction.replace(CONTAINER_ID, new DashboardFragment());
        transaction.commit();
    }

    public static void showCatalogueFragment(Context context, String keyword, String path)
    {
        final FragmentTransaction transaction = getFragmentManager(context).beginTransaction();
        transaction.replace(CONTAINER_ID, CatalogueFragment.newInstance(keyword, path));
        transaction.commit();
    }

    private static FragmentManager getFragmentManager(Context context) {

        if (context instanceof FragmentActivity) {
            return ((FragmentActivity) context).getSupportFragmentManager();
        }
        throw new IllegalStateException(
                "The context being used is not an instance of FragmentActivity.");
    }

    public static void navigateToDevActivity(Context context)
    {
        final Intent intent = new Intent(context, DevActivity.class);
        context.startActivity(intent);
    }

    public static void showDevFragment(Context context)
    {
        final FragmentTransaction transaction = getFragmentManager(context).beginTransaction();
        transaction.replace(CONTAINER_ID, new DevFragment());
        transaction.commit();
    }


    public static void showAddToCartDialogFragment(Context context, Product product, String currentImagePath)
    {
        final FragmentTransaction transaction = getFragmentManager(context).beginTransaction();
        Fragment prev = getFragmentManager(context).findFragmentByTag("dialog");
        if(prev != null)
        {
            transaction.remove(prev);
        }
        transaction.addToBackStack(null);

        AddToCartDialogFragment _diag = AddToCartDialogFragment.newInstance(product,currentImagePath);
        _diag.show(transaction, "dialog");
    }

    public static void showChangeColorSizeShoppingCartDialogFragment(Context context, Product product, String showFor, String currentSelectionSize, String currentSelectionColor, int position)
    {
        final FragmentTransaction transaction = getFragmentManager(context).beginTransaction();
        Fragment prev = getFragmentManager(context).findFragmentByTag("dialog");
        if(prev != null)
        {
            transaction.remove(prev);
        }
        transaction.addToBackStack(null);

        ChangeColorSizeShoppingCartDialogFragment _diag = ChangeColorSizeShoppingCartDialogFragment.newInstance(product, showFor, currentSelectionSize, currentSelectionColor, Integer.toString(position));
        _diag.show(transaction, "dialog");
    }

    public static void showRemoveItemFromShoppingCartDialogFragment(Context context, int position)
    {
        final FragmentTransaction transaction = getFragmentManager(context).beginTransaction();
        Fragment prev = getFragmentManager(context).findFragmentByTag("dialog");
        if(prev != null)
        {
            transaction.remove(prev);
        }
        transaction.addToBackStack(null);

        RemoveItemShoppingCartDialogFragment _diag = RemoveItemShoppingCartDialogFragment.newInstance(position);
        _diag.show(transaction, "dialog");
    }

    public static void showShoppingCartFragment(Context context)
    {
        final FragmentTransaction transaction = getFragmentManager(context).beginTransaction();
        ShoppingCartFragment frag = new ShoppingCartFragment();
        transaction.replace(CONTAINER_ID, frag);
        transaction.commit();
    }

    public static void showProductDetailFragment(Context context, Product product)
    {
        final FragmentTransaction transaction = getFragmentManager(context).beginTransaction();
        transaction.replace(CONTAINER_ID, ProductDetailFragment.newInstance(product));
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public static void showProductFragment(Context context, Product product)
    {
        final FragmentTransaction transaction = getFragmentManager(context).beginTransaction();
        transaction.replace(CONTAINER_ID, ProductFragment.newInstance(product));
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public static void showCheckOrder(Context context) {

        final FragmentTransaction transaction = getFragmentManager(context).beginTransaction();
        transaction.replace(CONTAINER_ID, new CheckOrderFragment());
        transaction.commit();
    }

    public static void showShippingCartFragment(Context context)
    {
        final FragmentTransaction transaction = getFragmentManager(context).beginTransaction();
        transaction.replace(CONTAINER_ID, new ShippingAddressFragment());
        transaction.commit();
    }
}
