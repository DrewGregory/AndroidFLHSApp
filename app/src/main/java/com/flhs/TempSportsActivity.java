package com.flhs;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class TempSportsActivity extends FLHSActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.temp_sports);
        SetupNavDrawer();
        WebView mWebView = (WebView) findViewById(R.id.webView);
        mWebView.setWebViewClient(new WebViewClient());
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        mWebView.loadUrl("http://sportspak.swboces.org/sportspak/oecgi3.exe/O4W_GAMES_SCHEDS");
    }





}
