package com.example.myapplication;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dongffl.dfweb.DFManager;
import com.dongffl.dfweb.location.GPSResponseBean;
import com.dongffl.dfweb.location.LocationCallback;
import com.dongffl.dfweb.scan.callback.ResultCallBack;

public class TestActivity extends AppCompatActivity {
    EditText editText,editUA;
    TextView tvScan, tvGoWebview,tvLocationContent;
    String mUrl = "https://aaronlianggq.github.io/web_sdk_demo.html";
    LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onSuccessLocationListener(GPSResponseBean gpsResponseBean) {
            if (gpsResponseBean.getErrorCode() == -1) {
                Toast.makeText(TestActivity.this, "" + gpsResponseBean.getErrorInfo(), Toast.LENGTH_SHORT).show();
                return;
            }
            tvLocationContent.setText("经度   "+gpsResponseBean.getLongitude() +"\n纬度     "+ gpsResponseBean.getLatitude() +"\n地址   "+gpsResponseBean.getAddress());

            Toast.makeText(TestActivity.this, "" + gpsResponseBean.getAddress(), Toast.LENGTH_SHORT).show();

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.edit_url);
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
                    DFManager.getSingleton().openWebPage(TestActivity.this, mUrl, null);
                }
                return false;
            }
        });
        editUA.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    DFManager.getSingleton().setUserAgentFlag( v.getText().toString().trim());
                }
                return false;
            }
        });
        tvGoWebview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DFManager.getSingleton().setUserAgentFlag( editUA.getText().toString().trim());
                DFManager.getSingleton().openWebPage(TestActivity.this, mUrl, null);
            }
        });
        tvScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DFManager.getSingleton().openScan(TestActivity.this, new ResultCallBack() {
                    @Override
                    public void onResult(boolean success, boolean cancel, String result) {
                        if (result != null) {
                            Toast.makeText(TestActivity.this, result, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        findViewById(R.id.tv_location).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DFManager.getSingleton().getLocation(TestActivity.this, locationCallback);
            }
        });
        findViewById(R.id.tv_location1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DFManager.getSingleton().getLocation(TestActivity.this, new LocationCallback() {
                    @Override
                    public void onSuccessLocationListener(GPSResponseBean gpsResponseBean) {
                        if (gpsResponseBean.getErrorCode() == -1) {
                            Toast.makeText(TestActivity.this, "" + gpsResponseBean.getErrorInfo(), Toast.LENGTH_SHORT).show();
                            return;
                        }
                        tvLocationContent.setText("经度   "+gpsResponseBean.getLongitude() +"\n纬度     "+ gpsResponseBean.getLatitude() +"\n地址   "+gpsResponseBean.getAddress());
                        Toast.makeText(TestActivity.this, "" + gpsResponseBean.getAddress(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}