package com.bateeqshop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CompleteOrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_order);
    }

    @Override
    public void onBackPressed() {
        MainNavigator.navigateToMainActivity(this);
    }
}
