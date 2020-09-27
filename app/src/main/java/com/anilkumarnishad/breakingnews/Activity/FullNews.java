package com.anilkumarnishad.breakingnews.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.anilkumarnishad.breakingnews.CustomLoader;
import com.anilkumarnishad.breakingnews.R;

public class FullNews extends AppCompatActivity {


    WebView webView;
    ProgressBar progressBar;
    String url,title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_news);

        Toolbar toolbar = findViewById(R.id.toolbardis);
        setSupportActionBar(toolbar);
        final CustomLoader customLoader = new CustomLoader(FullNews.this, android.R.style.Theme_Translucent_NoTitleBar);
        customLoader.show();
        toolbar.setTitle("Breaking News");
        Intent intent = getIntent();
        url = intent.getExtras().getString("news detail");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setMax(100);
        progressBar.setProgress(1);
        webView=findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webView.loadUrl(url);

        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                progressBar.setProgress(progress);
            }
        });

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(View.VISIBLE);
                customLoader.dismiss();

            }

        });
    }
}
