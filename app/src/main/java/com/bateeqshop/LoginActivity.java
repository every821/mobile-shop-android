package com.bateeqshop;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;

import com.bateeqshop.fragments.LoginFragment;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;

public class LoginActivity  extends BaseActivity
{
    AccessTokenTracker accessTokenTracker;
    AccessToken accessToken;

    private final String LOG_TAG = LoginActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.content_login);
        if (fragment == null) showLogin();
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.content_login);

        if (fragment instanceof LoginFragment)
        {
            super.onBackPressed();
        }
        else
        {
            showLogin();
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    private void showLogin()
    {
        LoginNavigator.showLoginFragment(this);
    }
}
