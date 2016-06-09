package com.bateeqshop;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.bateeqshop.fragments.ChangePasswordFragment;
import com.bateeqshop.fragments.ForgotPasswordFragment;
import com.bateeqshop.fragments.LoginFragment;
import com.bateeqshop.fragments.RegisterFragment;

public class LoginNavigator
{
    private static final int CONTAINER_ID = R.id.content_login;

    public static void navigateToLoginActivity(Context context)
    {
        final Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    public static void showLoginFragment(Context context)
    {
        final FragmentTransaction transaction = getFragmentManager(context).beginTransaction();
        transaction.replace(CONTAINER_ID, new LoginFragment());
        transaction.commit();
    }

    public static void showRegisterFragment(Context context)
    {
        final FragmentTransaction transaction = getFragmentManager(context).beginTransaction();
        transaction.replace(CONTAINER_ID, new RegisterFragment());
        transaction.commit();
    }

    public static void showForgotPasswordFragment(Context context)
    {
        final FragmentTransaction transaction = getFragmentManager(context).beginTransaction();
        transaction.replace(CONTAINER_ID, new ForgotPasswordFragment());
        transaction.commit();
    }

    public static void showChangePasswordFragment(Context context)
    {
        final FragmentTransaction transaction = getFragmentManager(context).beginTransaction();
        transaction.replace(CONTAINER_ID, new ChangePasswordFragment());
        transaction.commit();
    }

    private static FragmentManager getFragmentManager(Context context) {

        if (context instanceof FragmentActivity) {
            return ((FragmentActivity) context).getSupportFragmentManager();
        }
        throw new IllegalStateException(
                "The context being used is not an instance of FragmentActivity.");
    }

}
