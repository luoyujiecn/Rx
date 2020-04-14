package com.kxj.rx.ui.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.kxj.rx.R;
import com.kxj.rx.basemvp.BaseActivity;

public class InformationInfoActivity extends BaseActivity {
    private WebView webView;

    @Override
    protected void initLayout(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_information_info);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initViews() {
        super.initViews();
        webView = findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);
        String url = getIntent().getStringExtra("url");
        Log.e("information", url);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
        webView.loadUrl(url);
    }
}
