package com.bateeqshop;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public abstract class BaseActivity extends AppCompatActivity
{
    ProgressDialog mProgressDialog;

    protected void showLoadingDialog()
    {
        mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setMessage("Loading");
        mProgressDialog.show();
    }

    protected void hideLoadingDialog()
    {
        if (mProgressDialog != null && mProgressDialog.isShowing()) mProgressDialog.hide();
    }

    protected void showToast(String toastMessage)
    {
        Toast.makeText(getContext(), toastMessage, Toast.LENGTH_LONG).show();
    }

    public abstract Context getContext();
}
