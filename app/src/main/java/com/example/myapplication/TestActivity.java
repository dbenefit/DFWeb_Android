package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TestActivity extends AppCompatActivity {
    EditText editText, editUA;
    TextView tvScan, tvGoWebview, tvLocationContent,tvOpenFragmentWebview;
    String mUrl = "https://aaronlianggq.github.io/web_sdk_demo.html";
//    LocationCallback locationCallback = new LocationCallback() {
//        @Override
//        public void onSuccessLocationListener(GPSResponseBean gpsResponseBean) {
//            if (gpsResponseBean.getErrorCode() == -1) {
//                Toast.makeText(TestActivity.this, "" + gpsResponseBean.getErrorInfo(), Toast.LENGTH_SHORT).show();
//                return;
//            }
//            tvLocationContent.setText("经度   " + gpsResponseBean.getLongitude() + "\n纬度     " + gpsResponseBean.getLatitude() + "\n地址   " + gpsResponseBean.getAddress());
//
//            Toast.makeText(TestActivity.this, "" + gpsResponseBean.getAddress(), Toast.LENGTH_SHORT).show();
//
//        }
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.edit_url);
        tvOpenFragmentWebview = findViewById(R.id.tv_openFragment);
        editUA = findViewById(R.id.edit_UA);
        tvGoWebview = findViewById(R.id.tv_goWebview);
        tvScan = findViewById(R.id.tv_Scan);
        tvScan = findViewById(R.id.tv_Scan);
        tvLocationContent = findViewById(R.id.tv_location_content);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    mUrl = v.getText().toString().trim();
                    Intent intent = new Intent(TestActivity.this,WebviewActivity.class);
                    intent.putExtra("url", mUrl);
                    startActivity(intent);
                }
                return false;
            }
        });
        tvOpenFragmentWebview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TestActivity.this,MainActivity.class));
            }
        });
        editUA.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
//                    DFManager.getSingleton().setUserAgentFlag(v.getText().toString().trim());
                }
                return false;
            }
        });
        tvGoWebview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TestActivity.this,WebviewActivity.class);
                intent.putExtra("url", mUrl);
                startActivity(intent);
            }
        });
        tvScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        findViewById(R.id.tv_location).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

    }
}