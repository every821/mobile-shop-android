package com.bateeqshop.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bateeqshop.R;
import com.bateeqshop.config.MessageConfig;

public abstract class BaseFragment extends Fragment
{
    private RelativeLayout rl_progress;
    private RelativeLayout rl_retry;

    private ProgressDialog mProgressDialog;
    private Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rl_progress = (RelativeLayout) view.findViewById(R.id.rl_progress);
        rl_retry = (RelativeLayout) view.findViewById(R.id.rl_retry);

        Button btn_retry = (Button) view.findViewById(R.id.btn_retry);
        if (btn_retry != null) {
            btn_retry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hideContent();
                    hideRetry();
                    showLoadingProgress();
                    loadContent();
                }
            });
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        syncNavigationViewSelection();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mProgressDialog != null)
        {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    protected void showContent()
    {
        getContentLayout().setVisibility(View.VISIBLE);
    }

    protected void hideContent()
    {
        getContentLayout().setVisibility(View.GONE);
    }

    protected void showLoadingProgress() {
        if (rl_progress != null) {
            this.rl_progress.setVisibility(View.VISIBLE);
            this.getActivity().setProgressBarIndeterminateVisibility(true);
        }
    }

    protected void hideLoadingProgress() {
        if (rl_progress != null) {
            this.rl_progress.setVisibility(View.GONE);
            this.getActivity().setProgressBarIndeterminateVisibility(false);
        }
    }

    protected void showRetry() {
        if (rl_retry != null) {
            this.rl_retry.setVisibility(View.VISIBLE);
        }
    }

    protected void hideRetry() {
        if (rl_retry != null) {
            this.rl_retry.setVisibility(View.GONE);
        }
    }

    protected void showLoadingDialog()
    {
        showLoadingDialog(null);
    }

    protected void showLoadingDialog(String message)
    {
        if (message == null) message = MessageConfig.LOADING_MESSAGE;
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setMessage(message);
        mProgressDialog.show();
    }

    protected void hideLoadingDialog()
    {
        if (mProgressDialog != null && mProgressDialog.isShowing()) mProgressDialog.hide();
    }

    protected void showToast(String toastMessage)
    {
        Toast.makeText(mContext, toastMessage, Toast.LENGTH_LONG).show();
    }

    public abstract void syncNavigationViewSelection();

    public abstract ViewGroup getContentLayout();

    public abstract void loadContent();
}
