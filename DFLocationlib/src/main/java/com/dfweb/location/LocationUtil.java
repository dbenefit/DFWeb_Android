package com.dfweb.location;

import static android.Manifest.permission.ACCESS_NETWORK_STATE;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


import com.dffl.dfbaselibrary.bean.GPSResponseBean;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class LocationUtil {
    private static final String DEFAULT_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    private volatile static LocationUtil locationUtil = null;
    private final String TAG = "LocationManagerUtil";
    private Context mContext;
    ;
    private LocationManager locationManager;
    private ArrayList<LocationCallback> listeners = new ArrayList<>();
    private final Handler handler = new Handler(Looper.myLooper());

    /**
     * 超时时间10秒
     */
    private final long TIME_OUT = 10000;

    /**
     * 超时结束监听标记
     */
    private boolean endlistenerFlag = false;

    private LocationUtil() {
    }

    public static LocationUtil getInstance() {
        if (null == locationUtil) {
            synchronized (LocationUtil.class) {
                if (null == locationUtil) {
                    locationUtil = new LocationUtil();
                }
            }
        }
        return locationUtil;
    }

    public void removeLocationCallback(LocationCallback locationCallback) {
        listeners.remove(locationCallback);
    }

    public void startLocationCheck(Context context, LocationCallback locationCallback) {
        if (context == null) {
            new Throwable("DFSDK---  请设置上下文！").printStackTrace();
            return;
        }
        if (locationCallback != null) {
            if (!listeners.contains(locationCallback)) {
                listeners.add(locationCallback);
            }
        } else {
            new Throwable("DFSDK--- 请设置接口回调！").printStackTrace();
            return;
        }
        this.mContext = context;
        locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);

        if (context instanceof AppCompatActivity) {
            context.startActivity(new Intent(context, LocationActivity.class));
        } else {
            Intent intent = new Intent(context, LocationActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }

    /**
     * 开始定位 单次定位
     *
     * @param
     */
    public void startSingleLocation() {

        endlistenerFlag = false;

        if (null == locationManager) {
            return;
        }

        //检查权限
        boolean permission = checkPermission();
        if (!permission) {
            return;
        }
        String provider = LocationManager.GPS_PROVIDER;
        boolean providerEnabled = locationManager.isProviderEnabled(provider);
        if (!providerEnabled) {
            Toast.makeText(mContext, "GPS未打开!", Toast.LENGTH_SHORT).show();
            return;
        }
        String bestProvider = locationManager.getBestProvider(getCriteria(), true);
        // 获取位置信息
        // 如果不设置查询要求，getLastKnownLocation方法传人的参数为LocationManager.GPS_PROVIDER
        Location location = locationManager.getLastKnownLocation(bestProvider);
        //获取缓存中的位置信息getLastKnownLocation
        if (null != location) {
            String locationAddr = getLocationAddr(location.getLongitude(), location.getLatitude());
            location.reset();
        }
        //获取一次定位结果requestSingleUpdate  单次定位
        locationManager.requestSingleUpdate(bestProvider, locationListener, handler.getLooper());
        //超时结束定位
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                locationManager.removeUpdates(locationListener);
                handler.removeCallbacks(this);
                if (!endlistenerFlag) {
                    GPSResponseBean gpsResponseBean = new GPSResponseBean();
                    gpsResponseBean.setErrorInfo("定位超时");
                    gpsResponseBean.setErrorCode(-1);
                    for (int i = 0; i < listeners.size(); i++) {
                        listeners.get(i).onSuccessLocationListener(gpsResponseBean);
                    }

                }
            }
        }, TIME_OUT);
    }

    private Criteria getCriteria() {
        Criteria criteria = new Criteria();
// 设置定位精确度 Criteria.ACCURACY_COARSE比较粗略，Criteria.ACCURACY_FINE则比较精细
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
// 设置是否要求速度
        criteria.setSpeedRequired(false);
// 设置是否允许运营商收费
        criteria.setCostAllowed(false);
// 设置是否需要方位信息
        criteria.setBearingRequired(false);
// 设置是否需要海拔信息
        criteria.setAltitudeRequired(false);
// 设置对电源的需求
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        return criteria;
    }


    @RequiresPermission(ACCESS_NETWORK_STATE)
    private NetworkInfo getActiveNetworkInfo() {
        ConnectivityManager cm =
                (ConnectivityManager) this.mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) return null;
        return cm.getActiveNetworkInfo();
    }

    public static String formatDate(Date date, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    public static String formatDateTime(Date date) {
        return formatDate(date, DEFAULT_DATETIME_PATTERN);
    }

    /**
     * 定位监听
     */
    private final LocationListener locationListener = new LocationListener() {
        /**
         * 位置信息变化时触发
         * 当位置发生改变后就会回调该方法，经纬度相关信息存在Location里面；
         */
        @Override
        public void onLocationChanged(Location location) {
            //赋值为true
            endlistenerFlag = true;
            if (null != locationManager) {
                //解除注册监听 结束定位
                locationManager.removeUpdates(this);
            }
            String time = formatDateTime(new Date(location.getTime()));
            final double longitude = location.getLongitude();
            final double latitude = location.getLatitude();
//            double altitude = location.getAltitude();
//            double longitudeGd = location.getLongitude() + 0.0052719116;//经度 系统定位相对于高德定位坐标相差0.0052719116
//            double latitudeGd = location.getLatitude() + 0.0010604858;//纬度 系统定位相对于高德定位坐标相差0.0010604858

            //获取地理位置
            String locationAddr = getLocationAddr(longitude, latitude);
            //获取高德经纬度地址
//            String locationAddrGd = getLocationAddr(longitudeGd, latitudeGd);
            if (TextUtils.isEmpty(locationAddr)) {
                //失败回调
                GPSResponseBean gpsResponseBean = new GPSResponseBean();
                gpsResponseBean.setErrorInfo("定位失败");
                gpsResponseBean.setErrorCode(-1);
                for (int i = 0; i < listeners.size(); i++) {
                    listeners.get(i).onSuccessLocationListener(gpsResponseBean);
                }
            } else {
                //成功回调
                GPSResponseBean gpsResponseBean = new GPSResponseBean();
                gpsResponseBean.setLongitude(longitude);
                gpsResponseBean.setLatitude(latitude);
                gpsResponseBean.setAddress(locationAddr);
                for (int i = 0; i < listeners.size(); i++) {
                    listeners.get(i).onSuccessLocationListener(gpsResponseBean);
                }
            }
        }

        /**
         * GPS状态变化时触发
         * 我们所采用的provider状态改变时会回调
         */
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            switch (status) {
                //GPS状态为可见时
                case LocationProvider.AVAILABLE:
                    break;
                //GPS状态为服务区外时
                case LocationProvider.OUT_OF_SERVICE:
                    break;
                //GPS状态为暂停服务时
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    break;
                default:
                    break;
            }
        }

        /**
         * GPS开启时触发
         * 当provider可用时被触发，比如定位模式切换到了使用精确位置时GPSProvider就会回调该方法；
         */
        @Override
        public void onProviderEnabled(String provider) {

        }

        /**
         * GPS禁用时触发
         * 当provider不可用时被触发，比如定位模式切换到了使用使用网络定位时GPSProvider就会回调该方法；
         */
        @Override
        public void onProviderDisabled(String proider) {

        }
    };

    /**
     * 检查权限
     *
     * @return
     */
    private boolean checkPermission() {
        if (ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return false;
        }
        return true;
    }

    /**
     * 获取地理位置
     *
     * @param longitude
     * @param latitude
     * @return
     */
    private String getLocationAddr(double longitude, double latitude) {
        Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
        boolean flag = Geocoder.isPresent();
        if (!flag) {
            Log.d(TAG, "地理编码不可使用");
            return "";
        }

//        locality(地址位置) 属性
//        并且有featureName(地址要素)
//        比如国家(countryName)
//        邮编(postalCode)
//        国家编码(countryCode)
//        省份(adminArea)
//        二级省份(subAdminArea)
//        二级城市(subLocality)
//        道路(thoroughfare) 等；

        //  getFromLocationName()：返回描述地理位置信息的集合，maxResults是返回地址的数目，建议使用1-5；
//        List<Address> addresses = geocoder.getFromLocationName("西二旗", 1);
//        if (addresses.size() > 0) {
//            //返回当前位置，精度可调
//            Address address = addresses.get(0);
//            if (address != null) {
//                Log.e("gzq", "sAddress：" + address.getLatitude());
//                Log.e("gzq", "sAddress：" + address.getLongitude());
//            }
//        }

        try {
            //根据经纬度返回对应的地理位置信息，参数maxResults表示返回地址的数目，建议使用1-5；
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() == 0) {
                return "";
            }
            Address address = addresses.get(0);
            if (null == address) {
                return "";
            }

            //获取最大地址行索引
            int maxAddressLineIndex = address.getMaxAddressLineIndex();
            //循环打印周围位置地址
            for (int i = 0; i < maxAddressLineIndex; i++) {
                String addressStr = address.getAddressLine(i);
            }

            StringBuilder addressBuilder = new StringBuilder();
            String addressLine = address.getAddressLine(0);
            String addressLine1 = address.getAddressLine(1);
            if (null != addressLine) {
                addressBuilder.append(addressLine);
            }
            if (null != addressLine1) {
                addressBuilder.append("靠近").append(addressLine1);
            }
            return addressBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
