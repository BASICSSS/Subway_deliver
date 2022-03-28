package com.example.subway_deliver;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AddressWebviewActivity extends AppCompatActivity {


    private WebView webView;

    class MyJavaScriptInterface
    {
        @JavascriptInterface
        @SuppressWarnings("unused")
        public void processDATA(String data) {
            Bundle extra = new Bundle();
            Intent intent = new Intent();
            extra.putString("data", data); //프래그먼트 송신시
            intent.putExtras(extra);
            setResult(RESULT_OK, intent);
            finish();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_webview);

        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new MyJavaScriptInterface(), "Android");

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                webView.loadUrl("javascript:sample2_execDaumPostcode();");
            }
        });

        webView.loadUrl("https://river97.cafe24.com/daum.html");


    }

//    private WebView webView;
//    private Handler handler;
//    private TextView result;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_address_webview);
//
//        result = (TextView) findViewById(R.id.result);
//
//        init_webView();
//
//        handler = new Handler();
//
//    }
//
//    public void init_webView() {
//        webView = (WebView)findViewById(R.id.webView);
//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
//
//        webView.addJavascriptInterface(new AndroidBridge(), "TestApp");
//        webView.setWebChromeClient(new WebChromeClient());
//        webView.loadUrl("river97.cafe24.com/getAddress.php");
//
//    }
//
//    private class AndroidBridge {
//
//        @JavascriptInterface
//
//        public void setAddress(final String arg1, final String arg2, final String arg3) {
//
//            handler.post(new Runnable() {
//
//                @Override
//
//                public void run() {
//
//                    result.setText(String.format("(%s) %s %s", arg1, arg2, arg3));
//
//                    // WebView를 초기화 하지않으면 재사용할 수 없음
//
//                    init_webView();
//
//                }
//
//            });
//
//        }
//
//    }
}