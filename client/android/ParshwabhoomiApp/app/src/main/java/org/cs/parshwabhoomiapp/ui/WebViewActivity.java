package org.cs.parshwabhoomiapp.ui;

import android.app.Activity;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.cs.parshwabhoomiapp.R;

public class WebViewActivity extends Activity {
    public static final String TAG = WebViewActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_view_activity);
        WebView webView = (WebView)findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                Log.i(TAG, "Proceeding with ssl error...");
                handler.proceed();
            }
        });
        String url = getIntent().getStringExtra("url");
        Log.i(TAG, "Rendering web page with url="+url);
        webView.loadUrl(url);
    }
}
