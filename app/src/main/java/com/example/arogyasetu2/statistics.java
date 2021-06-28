package com.example.arogyasetu2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class statistics extends AppCompatActivity {

    WebView wb1;
    Window window;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        window=getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        wb1=findViewById(R.id.webView);
        wb1.setWebViewClient(new WebViewClient());
        wb1.clearCache(true);

        wb1.getSettings().setDomStorageEnabled(true);
        wb1.getSettings().setJavaScriptEnabled(true);
//        webSettings.setJavaScriptEnabled(true);
//        webSettings.setAppCacheEnabled(true);
        wb1.loadUrl("https://www.covid19india.org");
    }
}
