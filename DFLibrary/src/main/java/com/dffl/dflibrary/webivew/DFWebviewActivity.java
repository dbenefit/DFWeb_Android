package com.dffl.dflibrary.webivew;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dffl.dflibrary.DFManager;
import com.dffl.dflibrary.PathUtils;
import com.dffl.dflibrary.client.DFWebviewChromeClient;
import com.dffl.dflibrary.client.DFWebviewClient;
import com.example.dflibrary.R;

import java.util.ArrayList;

public class DFWebviewActivity extends AppCompatActivity {
    DFWebview dfWebView;
    String mUrl = "";
    TextView tvTitle;
    ImageView ivBack, ivFinish;
    ValueCallback<Uri[]> filePathCallback;
    WebChromeClient.FileChooserParams fileChooserParams;
    private static final int REQUEST_TAKE_PHOTOES = 102;
    private static final int REQUEST_TAKE_VIDEO = 103;
    String[] mPermissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dfwebview);
        initView();
        initWebviewSettings();
        initWebviewClient();
        initData();
        dfWebView.loadUrl(mUrl);
    }


    private void initData() {
        mUrl = getIntent().getStringExtra("url");
    }

    private void initView() {
        dfWebView = findViewById(R.id.webview);

        tvTitle = findViewById(R.id.title_tv);
        ivBack = findViewById(R.id.title_back);
        ivFinish = findViewById(R.id.title_finish);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dfWebView.canGoBack()) {
                    dfWebView.goBack();
                } else {
                    finish();
                }
            }
        });
        ivFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initWebviewClient() {
        dfWebView.setWebViewClient(new DFWebviewClient());
        dfWebView.setWebChromeClient(new DFWebviewChromeClient(this));
        CookieManager cookieManager = CookieManager.getInstance();
        for (String key : DFManager.getSingleton().getCookieMap().keySet()) {
            cookieManager.setCookie(key, DFManager.getSingleton().getCookieMap().get(key));
        }
        cookieManager.flush();
    }

    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    @Override
    public void onBackPressed() {
        if (dfWebView.canGoBack()) {
            dfWebView.goBack();
        } else {
            finish();
        }
    }

    private void initWebviewSettings() {
        WebSettings settings = dfWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setAllowFileAccess(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setDomStorageEnabled(true);
        settings.setAllowFileAccessFromFileURLs(true);
        settings.setDefaultTextEncodingName("UTF-8");
        settings.setDatabaseEnabled(true);
//        settings.setAppCacheEnabled(true);
        settings.setNeedInitialFocus(true);
        settings.setUserAgentString(settings.getUserAgentString() + "-MAX-APP" + DFManager.getSingleton().getUserAgentString());
        settings.setDisplayZoomControls(false);
        settings.setBuiltInZoomControls(true);
        settings.setSupportZoom(true);
        settings.setUseWideViewPort(false);
        settings.setLoadWithOverviewMode(false);
        settings.setGeolocationEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setMixedContentMode(android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        settings.setLoadsImagesAutomatically(true);
        settings.setBlockNetworkImage(false);
        settings.setBlockNetworkLoads(false);
        settings.setTextZoom(100);
    }

    //private boolean
    public void onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
        this.filePathCallback = filePathCallback;
        this.fileChooserParams = fileChooserParams;
        if (fileChooserParams.getAcceptTypes()[0].contains("video")) {
            checkPermissions();
            chooseVideoFile();
        }
        if (fileChooserParams.getAcceptTypes()[0].contains("image") || fileChooserParams.getAcceptTypes()[0].contains("camera")) {
            chooseImageFile();
        }
    }

    private boolean hasAllPermissionGranted(int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (hasAllPermissionGranted(grantResults)) {
            chooseImageFile();
            if (fileChooserParams.getAcceptTypes()[0].contains("video")) {
                chooseVideoFile();
            }
            if (fileChooserParams.getAcceptTypes()[0].contains("image")) {
                chooseImageFile();
            }
        } else {
            Toast.makeText(this, "权限开启失败", Toast.LENGTH_LONG).show();
        }
    }

    public void chooseImageFile() {
        Intent gallery = new Intent(Intent.ACTION_PICK);
        gallery.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(gallery, REQUEST_TAKE_PHOTOES);
    }

    public void chooseVideoFile() {
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, REQUEST_TAKE_VIDEO);
    }

    private void checkPermissions() {
        int permissionCheck = 0;
        permissionCheck = this.checkSelfPermission(mPermissions[0]);       //允许一个程序访问精良位置(如GPS)
        permissionCheck += this.checkSelfPermission(mPermissions[1]);    //允许一个程序访问CellID或WiFi热点来获取粗略的位置
        permissionCheck += this.checkSelfPermission(mPermissions[2]);    //允许一个程序访问CellID或WiFi热点来获取粗略的位置
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(mPermissions, 100);
        } else {
            if (fileChooserParams.getAcceptTypes()[0].contains("video")) {
                chooseImageFile();
            } else {
                chooseVideoFile();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_TAKE_VIDEO && resultCode != RESULT_OK) {
            filePathCallback.onReceiveValue(new Uri[]{});
        }
        if (requestCode == REQUEST_TAKE_PHOTOES && resultCode != RESULT_OK) {
            filePathCallback.onReceiveValue(new Uri[]{});
        }
        if (requestCode == REQUEST_TAKE_PHOTOES && data != null && resultCode == RESULT_OK) {
            ArrayList<String> arrayList = new ArrayList<>();
            arrayList.add(PathUtils.getPath(this, data.getData()));
            filePathCallback.onReceiveValue(new Uri[]{Uri.parse(arrayList.get(0))});
        }
        if (requestCode == REQUEST_TAKE_VIDEO && data != null && resultCode == RESULT_OK) {
            Uri selectedVideo = data.getData();
            String[] filePathColumn = {MediaStore.Video.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedVideo,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String VIDEOPATH = cursor.getString(columnIndex);
            cursor.close();
            filePathCallback.onReceiveValue(new Uri[]{Uri.parse(VIDEOPATH)});

        }
    }
}