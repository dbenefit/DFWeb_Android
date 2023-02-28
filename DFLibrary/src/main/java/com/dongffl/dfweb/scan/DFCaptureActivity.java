package com.dongffl.dfweb.scan;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.dongffl.dfweb.PathUtils;
import com.dongffl.dfweb.scan.litezxing.CameraScan;
import com.dongffl.dfweb.scan.litezxing.CaptureActivity;
import com.dongffl.dfweb.scan.litezxing.DecodeConfig;
import com.dongffl.dfweb.scan.litezxing.DecodeFormatManager;
import com.dongffl.dfweb.scan.litezxing.analyze.MultiFormatAnalyzer;
import com.dongffl.dfweb.scan.litezxing.config.ResolutionCameraConfig;
import com.dongffl.dfweb.scan.litezxing.util.CodeUtils;
import com.example.dflibrary.R;
import com.google.zxing.Result;


import java.util.ArrayList;

public class DFCaptureActivity extends CaptureActivity {
    private static final int REQUEST_TAKE_PHOTOES = 101;
    private int mType;
    private int RequestCode = 100;
    String[] mPermissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    boolean isSelectPictureClicked = false;

    @Override
    public int getLayoutId() {
        return R.layout.df_scan_m_capture_activity;
    }

    @Override
    public void initUI() {

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        super.initUI();
        mType = getIntent().getIntExtra("type", -1);
        initListener();

    }

    @Override
    public void initCameraScan() {
        super.initCameraScan();

        //初始化解码配置
        DecodeConfig decodeConfig = new DecodeConfig();
        decodeConfig
                .setHints(DecodeFormatManager.DEFAULT_HINTS) //如果只有识别二维码的需求，这样设置效率会更高，不设置默认为DecodeFormatManager.DEFAULT_HINTS
                .setFullAreaScan(false) //设置是否全区域识别，默认false
                .setAreaRectRatio(0.8f) //设置识别区域比例，默认0.8，设置的比例最终会在预览区域裁剪基于此比例的一个矩形进行扫码识别
                .setAreaRectVerticalOffset(0).setAreaRectHorizontalOffset(0);
        //设置识别区域水平方向偏移量，默认为0，为0表示居中，可以为负数
        //在启动预览之前，设置分析器，只识别二维码
        getCameraScan()
                .setVibrate(true) //设置是否震动，默认为false
                .setCameraConfig(new ResolutionCameraConfig(this)) //设置CameraConfig，可以根据自己的需求去自定义配置
                .setNeedAutoZoom(true) //二维码太小时可自动缩放，默认为false
                .setAnalyzer(new MultiFormatAnalyzer(decodeConfig)); //设置分析器,如果内置实现的一些分析器不满足您的需求，你也可以自定义去实现
    }

    private void handleSelectImgs(ArrayList<String> paths) {
        if (paths == null || paths.size() == 0) {
            return;
        }
        String path = paths.get(0);
//        if (!FileUtils.isFile(path)) {
//            return
//        }
        String result = getParseCodeResult(path);
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (TextUtils.isEmpty(result)) {
                    Toast.makeText(DFCaptureActivity.this, "未发现二维码", Toast.LENGTH_SHORT).show();
                } else {
                    handleScanQRCodeResult(result);
                }

            }
        });
    }

    private String getParseCodeResult(String url) {
        Result result = CodeUtils.parseCodeResult(url, DecodeFormatManager.ALL_HINTS);
        if (result == null) {
            return null;
        }
        return result.getText();
    }

    @Override
    public boolean onScanResultCallback(Result result) {
        if (TextUtils.isEmpty(result.getText())) {
            setResult(ScanResultType.FAILED, null);
            return true;
        }
        getCameraScan().setAnalyzeImage(false);
        handleScanQRCodeResult(result.getText());
        return true;
    }

    private void handleScanQRCodeResult(String result) {
        if (TextUtils.isEmpty(result)) {
            setResult(ScanResultType.FAILED, null);
            finish();
            return;
        }
        if (result.contains("dongfangfuli.com")) {
            Intent intent = new Intent();
            intent.putExtra(CameraScan.SCAN_RESULT, result);
            setResult(RESULT_OK, intent);
            finish();
        }
        if (mType == 0 &&
                (result.startsWith("http://")
                        || result.startsWith("https://")
                        || result.startsWith("ftp")
                )
        ) {
            new AlertDialog.Builder(this).setTitle("可能存在风险，是否打开链接？")
                    .setPositiveButton("打开", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            openUrlByBrowser(result);
                            Intent intent = new Intent();
                            intent.putExtra(CameraScan.SCAN_RESULT, result);
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent();
                            intent.putExtra(CameraScan.SCAN_RESULT, result);
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    }).create().show();


            return;
        }
        //
        if (result.toUpperCase().contains("DFC") || result.contains("dfc")) {
            Intent intent = new Intent();
            intent.putExtra(CameraScan.SCAN_RESULT, result);
            setResult(RESULT_OK, intent);
            finish();
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(CameraScan.SCAN_RESULT, result);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void openUrlByBrowser(String result) {
        Uri uri = Uri.parse(result);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
        finish();
    }

    private void initListener() {
        findViewById(R.id.iv_select_pic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSelectPictureClicked = true;
                checkPermissions();
            }
        });

        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(ScanResultType.CANCEL, null);
                finish();
            }
        });
    }


    private void checkPermissions() {
        int permissionCheck = 0;
        permissionCheck = this.checkSelfPermission(mPermissions[0]);       //允许一个程序访问精良位置(如GPS)
        permissionCheck += this.checkSelfPermission(mPermissions[1]);    //允许一个程序访问CellID或WiFi热点来获取粗略的位置
        permissionCheck += this.checkSelfPermission(mPermissions[2]);    //允许一个程序访问CellID或WiFi热点来获取粗略的位置

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(mPermissions, RequestCode);
        } else {
            selectPic();
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (hasAllPermissionGranted(grantResults)) {
            if (isSelectPictureClicked) {
                selectPic();
            }
        } else {
            Toast.makeText(this, "权限开启失败", Toast.LENGTH_LONG).show();
        }
    }

    private void selectPic() {
        Intent gallery = new Intent(Intent.ACTION_PICK);
        gallery.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(gallery, REQUEST_TAKE_PHOTOES);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_TAKE_PHOTOES && data != null && resultCode == RESULT_OK) {
            ArrayList<String> arrayList = new ArrayList<>();
            arrayList.add(PathUtils.getPath(this, data.getData()));
            handleSelectImgs(arrayList);
        }
    }
}
