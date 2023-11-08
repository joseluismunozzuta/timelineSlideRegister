package com.example.ourstory;

import android.os.Bundle;
import android.webkit.WebView;
import androidx.appcompat.app.AppCompatActivity;

public class TimeLine extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        WebView webView = findViewById(R.id.timeline);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://timelinebeljose.netlify.app/");
    }
}
