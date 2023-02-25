package com.dongffl.dfweb.webivew;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.widget.ImageView;
import android.widget.TextView;

import com.dongffl.dfweb.DFManager;
import com.dongffl.dfweb.OnFileChooseCallBack;
import com.dongffl.dfweb.PathUtils;
import com.dongffl.dfweb.client.DFWebviewChromeClient;
import com.dongffl.dfweb.client.DFWebviewClient;
import com.dongffl.dfweb.webivew.jsbridge.JSBridgeInterface;
import com.example.dflibrary.R;

import java.util.ArrayList;

public class DFWebviewActivity extends AppCompatActivity {
    DFWebview dfWebView;
    String mUrl = "";
    TextView tvTitle;
    ImageView ivBack, ivFinish;
    private static final int REQUEST_TAKE_PHOTOES = 1002;
    private static final int REQUEST_TAKE_VIDEO = 1003;

    private static final int REQUEST_TAKE_FILE = 1004;
    String[] mFilePermissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
//    String[] mCameraPermissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    ValueCallback<Uri[]> mFilePathCallback;
    int CHOOSE_TYPE_IMAGE = 0;
    int CHOOSE_TYPE_VIDEO = 1;
    int CHOOSE_TYPE_FILE = 1;
    int mFileType = 0;

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
        DFWebviewChromeClient dfWebviewChromeClient = new DFWebviewChromeClient();
        register(dfWebviewChromeClient);
        dfWebView.setWebViewClient(new DFWebviewClient());

        CookieManager cookieManager = CookieManager.getInstance();
        for (String key : DFManager.getSingleton().getCookieMap().keySet()) {
            cookieManager.setCookie(key, DFManager.getSingleton().getCookieMap().get(key));
        }
        cookieManager.flush();
    }

    private void register(DFWebviewChromeClient dfWebviewChromeClient) {
        dfWebviewChromeClient.setOnFileChooseCallBack(new OnFileChooseCallBack() {
            @Override
            public void onShowFileChooser(ValueCallback<Uri[]> filePathCallback) {
                mFilePathCallback = filePathCallback;
                mFileType = CHOOSE_TYPE_FILE;
                checkPermissions();
            }

            @Override
            public void onVideoChooser(ValueCallback<Uri[]> filePathCallback) {
                mFilePathCallback = filePathCallback;
                mFileType = CHOOSE_TYPE_VIDEO;
                checkPermissions();
            }

            @Override
            public void onImageChooser(ValueCallback<Uri[]> filePathCallback) {
                mFilePathCallback = filePathCallback;
                mFileType = CHOOSE_TYPE_IMAGE;
                checkPermissions();
            }
        });
        dfWebView.setWebChromeClient(dfWebviewChromeClient);
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
        settings.setNeedInitialFocus(true);
        settings.setUserAgentString(settings.getUserAgentString() + DFManager.getSingleton().getUserAgentString());
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
        JSBridgeInterface jsBridge = new JSBridgeInterface(DFWebviewActivity.this, dfWebView);
        dfWebView.addJavascriptInterface(jsBridge, "android");
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
            if (mFileType == CHOOSE_TYPE_VIDEO) {
                chooseVideoFile();
            } else if (mFileType == CHOOSE_TYPE_IMAGE) {
                chooseImageFile();
            } else {

                chooseFile();
            }
        }
    }

    public void chooseImageFile() {
        Intent gallery = new Intent(Intent.ACTION_PICK);
        gallery.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(gallery, REQUEST_TAKE_PHOTOES);
    }

    public void chooseFile() {
        //调用系统文件夹
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, REQUEST_TAKE_FILE);
    }

    public void chooseVideoFile() {
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, REQUEST_TAKE_VIDEO);
    }

    private void checkPermissions() {
        int permissionCheck = 0;
        permissionCheck = this.checkSelfPermission(mFilePermissions[0]);       //允许一个程序访问精良位置(如GPS)
        permissionCheck += this.checkSelfPermission(mFilePermissions[1]);    //允许一个程序访问CellID或WiFi热点来获取粗略的位置
        permissionCheck += this.checkSelfPermission(mFilePermissions[2]);    //允许一个程序访问CellID或WiFi热点来获取粗略的位置
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(mFilePermissions, 100);
        } else {
            if (mFileType == CHOOSE_TYPE_VIDEO) {
                chooseVideoFile();
            } else if (mFileType == CHOOSE_TYPE_IMAGE) {
                chooseImageFile();
            } else {
                chooseFile();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mFilePathCallback==null){
            return;
        }
        if (requestCode == REQUEST_TAKE_VIDEO && resultCode != RESULT_OK) {
            mFilePathCallback.onReceiveValue(new Uri[]{});
            return;
        }
        if (requestCode == REQUEST_TAKE_FILE && resultCode != RESULT_OK) {
            mFilePathCallback.onReceiveValue(new Uri[]{});
            return;
        }
        if (requestCode == REQUEST_TAKE_PHOTOES && resultCode != RESULT_OK) {
            mFilePathCallback.onReceiveValue(new Uri[]{});
            return;
        }
        if (requestCode == REQUEST_TAKE_PHOTOES && data != null && resultCode == RESULT_OK) {
            ArrayList<String> arrayList = new ArrayList<>();
            arrayList.add(PathUtils.getPath(this, data.getData()));
            mFilePathCallback.onReceiveValue(new Uri[]{Uri.parse(arrayList.get(0))});
            return;
        }
        if (requestCode == REQUEST_TAKE_FILE && data != null && resultCode == RESULT_OK) {
            ArrayList<String> arrayList = new ArrayList<>();
            arrayList.add(PathUtils.getPath(this, data.getData()));
            mFilePathCallback.onReceiveValue(new Uri[]{Uri.parse(arrayList.get(0))});
            return;
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
            mFilePathCallback.onReceiveValue(new Uri[]{Uri.parse(VIDEOPATH)});

        }
    }
}