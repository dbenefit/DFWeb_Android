package com.dongffl.dfweb.location;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import com.dongffl.dfweb.LocationUtil;

public class LocationActivity extends AppCompatActivity {
    private String[] permissions = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
    };
    private static final int REQUEST_LOCATION_CODE = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!checkPermission()){
            ActivityCompat.requestPermissions( this, permissions, REQUEST_LOCATION_CODE);
        }else {
            LocationUtil.getInstance().startSingleLocation();
            finish();
        }
    }
    private boolean checkPermission() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
               return false;
        }
        return true;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            LocationUtil.getInstance().startSingleLocation();
            finish();
        } else {
            finish();
            Toast.makeText(this, "权限开启失败", Toast.LENGTH_LONG).show();
        }
    }
}