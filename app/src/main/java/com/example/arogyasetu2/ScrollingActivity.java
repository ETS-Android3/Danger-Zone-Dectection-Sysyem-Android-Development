package com.example.arogyasetu2;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ScrollingActivity extends AppCompatActivity {

    WebView wb1;
    Window window;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i=new Intent(ScrollingActivity.this, feedbackPage.class);
//                startActivity(i);
//            }
//        });

        window=getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        wb1=findViewById(R.id.wb1);
        wb1.setWebViewClient(new WebViewClient());
        wb1.clearCache(true);

        wb1.getSettings().setDomStorageEnabled(true);
        wb1.getSettings().setJavaScriptEnabled(true);
//        webSettings.setJavaScriptEnabled(true);
//        webSettings.setAppCacheEnabled(true);
        wb1.loadUrl("https://timesofindia.indiatimes.com/india/coronavirus-live-updates-feb-21-covid-cases-increase-for-6th-day-to-14k-says-govt/liveblog/81132536.cms");

    }
}
