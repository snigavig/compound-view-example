package com.goodcodeforfun.compoundviewexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    CustomProgressBar customProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        customProgressBar = (CustomProgressBar) findViewById(R.id.customProgressBar);
    }

    @Override
    protected void onResume() {
        customProgressBar.startProgress();
        super.onResume();
    }

    @Override
    protected void onPause() {
        customProgressBar.stopProgress();
        super.onPause();
    }
}
