package com.lfc.textx5.text1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lfc.textx5.R;
import com.lfc.textx5.text1.util.X5WebView;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.tencent.smtt.utils.TbsLog;

public class Textx5 extends AppCompatActivity {
    private static final String TAG = "X5Webview";
    //    private static final String mHomeUrl = "http://app.html5.qq.com/navi/index";
    private static final String mHomeUrl = "https://www.baidu.com/?tn=48021271_4_hao_pg";
    private static final String path = "https://raw.githubusercontent.com/iamlfc/MyTextLayout/master/app/src/main/assets/%E6%96%B0%E5%91%98%E5%B7%A5%E5%91%A8%E8%BE%85%E5%AF%BC%E8%80%83%E6%A0%B8%E8%A1%A8.docx";

    private static final int MAX_LENGTH = 14;
    private final float disable = 0.2f;
    private final float enable = 1.0f;
    private TextView mTitletv;
    private ProgressBar mProgressBar1;
    private LinearLayout mToolbar1;
    private ImageButton mBtnBack1;
    private ImageButton mBtnForward1;
    private ImageButton mBtnHome1;
    private ImageButton mBtnRefresh1;
    private X5WebView mWebFilechooser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);//网页中的视频，上屏幕的时候，可能出现闪烁的情况
        setContentView(R.layout.activity_textx5);
        initView();
    }

    private void initView() {


        mTitletv = (TextView) findViewById(R.id.titletv);
        mProgressBar1 = (ProgressBar) findViewById(R.id.progressBar1);
        mToolbar1 = (LinearLayout) findViewById(R.id.toolbar1);
        mBtnBack1 = (ImageButton) findViewById(R.id.btnBack1);
        mBtnForward1 = (ImageButton) findViewById(R.id.btnForward1);
        mBtnHome1 = (ImageButton) findViewById(R.id.btnHome1);
        mBtnRefresh1 = (ImageButton) findViewById(R.id.btnRefresh1);
        mWebFilechooser = (X5WebView) findViewById(R.id.web_filechooser);
        initX5();
        mWebFilechooser.loadUrl(mHomeUrl);
    }

    private void initX5() {
        X5WebView.setSmallWebViewEnabled(true);
        WebSettings webSetting = mWebFilechooser.getSettings();
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(false);
        //webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(true);
        //webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setJavaScriptEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        webSetting.setAppCachePath(this.getDir("appcache", 0).getPath());
        webSetting.setDatabasePath(this.getDir("databases", 0).getPath());
        webSetting.setGeolocationDatabasePath(this.getDir("geolocation", 0)
                .getPath());
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);

        mWebFilechooser.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view,
                                                              WebResourceRequest request) {

                Log.e("should", "request.getUrl().toString() is " + request.getUrl().toString());

                return super.shouldInterceptRequest(view, request);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (Integer.parseInt(Build.VERSION.SDK) >= 16)
                    changGoForwardButton(view);
            }
        });

        mWebFilechooser.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                TbsLog.d(TAG, "title: " + title);
                if (mTitletv == null)
                    return;
                if (!mWebFilechooser.getUrl().equalsIgnoreCase(mHomeUrl)) {
                    if (title != null && title.length() > MAX_LENGTH)
                        mTitletv.setText(title.subSequence(0, MAX_LENGTH) + "...");
                    else
                        mTitletv.setText(title);
                } else {
                    mTitletv.setText("");
                }
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                mProgressBar1.setProgress(newProgress);
                if (mProgressBar1 != null && newProgress != 100) {
                    mProgressBar1.setVisibility(View.VISIBLE);
                } else if (mProgressBar1 != null) {
                    mProgressBar1.setVisibility(View.GONE);
                }
            }
        });

        mWebFilechooser.setDownloadListener(new com.tencent.smtt.sdk.DownloadListener() {
            @Override
            public void onDownloadStart(String s, String s1, String s2, String s3, long l) {
                TbsLog.d(TAG, "url: " + s);
                new AlertDialog.Builder(Textx5.this)
                        .setTitle("下载提示")
                        .setPositiveButton("yes",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        Toast.makeText(
                                                Textx5.this,
                                                "fake message: i'll download...",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                })
                        .setNegativeButton("no",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        Toast.makeText(
                                                Textx5.this,
                                                "fake message: refuse download...",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                })
                        .setOnCancelListener(
                                new DialogInterface.OnCancelListener() {

                                    @Override
                                    public void onCancel(DialogInterface dialog) {
                                        Toast.makeText(
                                                Textx5.this,
                                                "fake message: refuse download...",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }).show();
            }
        });
    }

    private void changGoForwardButton(WebView view) {
        if (view.canGoBack()) {
            mBtnBack1.setAlpha(enable);
        } else {
            mBtnBack1.setAlpha(disable);
            mTitletv.setText("首页");
        }
        if (view.canGoForward())
            mBtnForward1.setAlpha(enable);
        else
            mBtnForward1.setAlpha(disable);
        if (view.getUrl() != null && view.getUrl().equalsIgnoreCase(mHomeUrl)) {
            mBtnHome1.setAlpha(disable);
            mBtnHome1.setEnabled(false);
        } else {
            mBtnHome1.setAlpha(enable);
            mBtnHome1.setEnabled(true);
        }
    }

}
