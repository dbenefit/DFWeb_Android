package com.dffl.dflibrary;

import static android.Manifest.permission.ACCESS_NETWORK_STATE;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresPermission;
import androidx.core.app.ActivityCompat;

import com.dffl.dflibrary.location.GPSResponseBean;
import com.dffl.dflibrary.location.LocationCallback;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;

public class LocationManagerUtil {
    private static final String DEFAULT_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final int MY_REQUEST_CODE = 100;

    private volatile static LocationManagerUtil locationManagerUtil = null;
    private final String TAG = "LocationManagerUtil";
    private final Context context;
    private LocationManager locationManager;
    private ArrayList<LocationCallback> listeners = new ArrayList<>();
    private final Handler handler = new Handler(Looper.myLooper());
    private String[] permissions = new String[]{
            Manifest.permission.LOCATION_HARDWARE,
            Manifest.permission.ACCESS_FINE_LOCATION,
    };

    /**
     * 超时时间10秒
     */
    private final long TIME_OUT = 10000;

    /**
     * 超时结束监听标记
     */
    private boolean endlistenerFlag = false;

    private LocationManagerUtil() {
        context = Utils.getApp();
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    public static LocationManagerUtil getInstance() {
        if (null == locationManagerUtil) {
            synchronized (LocationManagerUtil.class) {
                if (null == locationManagerUtil) {
                    locationManagerUtil = new LocationManagerUtil();
                }
            }
        }
        return locationManagerUtil;
    }

    /**
     * 开始定位 单次定位
     *
     * @param
     */
    public void startSingleLocation(ArrayList<LocationCallback> listeners) {
        this.listeners = listeners;
        endlistenerFlag = false;

        if (null == locationManager) {
            Log.d(TAG, "locationManager=null return");
            return;
        }

        //获取当前网络状态
        boolean networkState = isConnected();

        if (!networkState) {
            Log.d(TAG, "无网络 return");
            return;
        }

        //检查权限
        boolean permission = checkPermission();
        if (!permission) {
            Log.d(TAG, "定位权限未开启 return");
            return;
        }

        String provider = LocationManager.NETWORK_PROVIDER;
        Log.d(TAG, "provider:" + provider);

        //判断provider是否可用
        boolean providerEnabled = locationManager.isProviderEnabled(provider);
        if (!providerEnabled) {
            Log.d(TAG, provider + " 不可用 return");
            return;
        }

        //获取缓存中的位置信息getLastKnownLocation
        Location location = locationManager.getLastKnownLocation(provider);
        if (null != location) {
            String locationAddr = getLocationAddr(location.getLongitude(), location.getLatitude());
            Log.d(TAG, "缓存中的位置信息location:" + location.toString());
            Log.d(TAG, "缓存中的位置信息locationAddr:" + locationAddr);
            //清除定位信息
            location.reset();
        }

//        getWifi();
//
//        getTelephonyManager();

        //        Criteria crite = new Criteria();
//        crite.setAccuracy(Criteria.ACCURACY_FINE); //精度
//        crite.setPowerRequirement(Criteria.POWER_LOW); //功耗类型选择
//        String provider = locationManager.getBestProvider(crite, true);

//        String networkProvider = LocationManager.NETWORK_PROVIDER;
//        String gpsProvider = LocationManager.GPS_PROVIDER;
//        String passiveProvider = LocationManager.PASSIVE_PROVIDER;

        //添加地理围栏
//        locationManager.addProximityAlert(38.234, 114.234, 5, -1, PendingIntent.getBroadcast(this, 1, new Intent(), 3));
//        可以设置一个区域，当进入或离开这个区域的时候会收到通知，前两个参数指定一个点，第三个参数是半径，第四个参数是超时时间，设置为-1表示不存在超时，最后一个是广播接收器。
//        触发的Intent将使用键KEY_PROXIMITY_ENTERING，如果值为true，则设备进入邻近区域，如果是false，说明设备离开该区域。

        //获取一次定位结果requestSingleUpdate  单次定位
        locationManager.requestSingleUpdate(provider, locationListener, handler.getLooper());

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

    @RequiresPermission(ACCESS_NETWORK_STATE)
    private static NetworkInfo getActiveNetworkInfo() {
        ConnectivityManager cm =
                (ConnectivityManager) Utils.getApp().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) return null;
        return cm.getActiveNetworkInfo();
    }

    public static boolean isConnected() {
        NetworkInfo info = getActiveNetworkInfo();
        return info != null && info.isConnected();
    }

    /**
     * 开始定位 持续定位
     *
     * @param
     */
//    public void startLocationUpdates(LocationCallback listener) {
//        this.listener = listener;
//        endlistenerFlag = false;
//
//        if (null == locationManager) {
//            Log.d(TAG, "locationManager=null return");
//            return;
//        }
//
//        //获取当前网络状态
//        boolean networkState = isConnected();
//
//        if (!networkState) {
//            Log.d(TAG, "无网络 return");
//            return;
//        }
//
//        //检查权限
//        boolean permission = checkPermission();
//        if (!permission) {
//            Log.d(TAG, "定位权限未开启 return");
//            return;
//        }
//
//        String provider = LocationManager.NETWORK_PROVIDER;
//        Log.d(TAG, "provider:" + provider);
//
//        //判断provider是否可用
//        boolean providerEnabled = locationManager.isProviderEnabled(provider);
//        if (!providerEnabled) {
//            Log.d(TAG, provider + " 不可用 return");
//            return;
//        }
//
//        //获取缓存中的位置信息getLastKnownLocation
//        Location location = locationManager.getLastKnownLocation(provider);
//        if (null != location) {
//            String locationAddr = getLocationAddr(location.getLongitude(), location.getLatitude());
//            Log.d(TAG, "缓存中的位置信息location:" + location.toString());
//            Log.d(TAG, "缓存中的位置信息locationAddr:" + locationAddr);
//            //清除定位信息
//            location.reset();
//        }
//
////        Criteria crite = new Criteria();
////        crite.setAccuracy(Criteria.ACCURACY_FINE); //精度
////        crite.setPowerRequirement(Criteria.POWER_LOW); //功耗类型选择
////        String provider = locationManager.getBestProvider(crite, true);
//
////        String networkProvider = LocationManager.NETWORK_PROVIDER;
////        String gpsProvider = LocationManager.GPS_PROVIDER;
////        String passiveProvider = LocationManager.PASSIVE_PROVIDER;
//
//        //添加地理围栏
////        locationManager.addProximityAlert(38.234, 114.234, 5, -1, PendingIntent.getBroadcast(this, 1, new Intent(), 3));
////        可以设置一个区域，当进入或离开这个区域的时候会收到通知，前两个参数指定一个点，第三个参数是半径，第四个参数是超时时间，设置为-1表示不存在超时，最后一个是广播接收器。
////        触发的Intent将使用键KEY_PROXIMITY_ENTERING，如果值为true，则设备进入邻近区域，如果是false，说明设备离开该区域。
//
//        //持续定位：
//        //绑定监听，有4个参数
//        //参数1，设备：有GPS_PROVIDER、NETWORK_PROVIDER、PASSIVE_PROVIDER  三种
//        //参数2，位置信息更新周期，单位毫秒
//        //参数3，位置变化最小距离：当位置距离变化超过此值时，将更新位置信息
//        //参数4，监听
//        //备注：参数2和3，如果参数3不为0，则以参数3为准；参数3为0，则通过时间来定时更新；两者为0，则随时刷新
//        // 1分钟更新一次，或最小位移变化超过1米更新一次；
//        locationManager.requestLocationUpdates(provider, 60 * 1000, 0, locationListener, handler.getLooper());
//
//        //超时结束定位
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                locationManager.removeUpdates(locationListener);
//                handler.removeCallbacks(this);
//                if (!endlistenerFlag) {
//
//                    GPSResponseBean gpsResponseBean = new GPSResponseBean();
//                    gpsResponseBean.setErrorInfo("定位超时");
//                    gpsResponseBean.setErrorCode(-1);
//                    listener.onSuccessLocationListener(gpsResponseBean);
//                }
//            }
//        }, TIME_OUT);
//    }
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
            double altitude = location.getAltitude();
            double longitudeGd = location.getLongitude() + 0.0052719116;//经度 系统定位相对于高德定位坐标相差0.0052719116
            double latitudeGd = location.getLatitude() + 0.0010604858;//纬度 系统定位相对于高德定位坐标相差0.0010604858

            //获取地理位置
            String locationAddr = getLocationAddr(longitude, latitude);
            //获取高德经纬度地址
            String locationAddrGd = getLocationAddr(longitudeGd, latitudeGd);

            Log.d(TAG, "时间：" + time);
            Log.d(TAG, "经度：" + longitude);
            Log.d(TAG, "纬度：" + latitude);
            Log.d(TAG, "海拔：" + altitude);
            Log.d(TAG, "位置：" + locationAddr);
            Log.d(TAG, "高德经度：" + longitudeGd);
            Log.d(TAG, "高德纬度：" + latitudeGd);
            Log.d(TAG, "高德位置：" + locationAddrGd);


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
                    Log.d(TAG, "onLocationChanged: "+i);
                    listeners.get(i).onSuccessLocationListener(gpsResponseBean);
                }            }
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
                    Log.d(TAG, "当前GPS状态为可见状态 provider可用");
                    break;
                //GPS状态为服务区外时
                case LocationProvider.OUT_OF_SERVICE:
                    Log.d(TAG, "当前GPS状态为服务区外状态 无服务");
                    break;
                //GPS状态为暂停服务时
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    Log.d(TAG, "当前GPS状态为暂停服务状态 provider不可用");
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
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
     * wifi定位 不准确
     */
    private void getWifi() {
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (null == wifiManager) {
            Log.d(TAG, "null == wifiManager return");
            return;
        }
        if (!wifiManager.isWifiEnabled()) {
            Log.d(TAG, "wifi未启用 return");
            return;
        }
        WifiInfo connectionInfo = wifiManager.getConnectionInfo();
        String ssid = connectionInfo.getSSID();
        final String bssid = connectionInfo.getBSSID();
        int ipAddress = connectionInfo.getIpAddress();
//        @SuppressLint("HardwareIds") String macAddress = connectionInfo.getMacAddress();
//        String string = connectionInfo.toString();
//        Log.d(TAG, "wifi名称:" + ssid);
//        Log.d(TAG, "wifi的mac:" + bssid);
//        Log.d(TAG, "ipAddress:" + ipAddress);
//        Log.d(TAG, "macAddress:" + macAddress);
//        Log.d(TAG, "string:" + string);

        Executors.newFixedThreadPool(5).submit(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://api.cellocation.com:83/wifi/?mac=" + bssid + "&output=json");
                    Log.d(TAG, "url:" + url.toString());
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    //超时时间
                    connection.setConnectTimeout(3000);
                    //表示设置本次http请求使用GET方式
                    connection.setRequestMethod("GET");
                    //返回至为响应编号，如：HTTP_OK表示连接成功。
                    int responsecode = connection.getResponseCode();
                    if (responsecode == HttpURLConnection.HTTP_OK) {
                        InputStream inputStream = connection.getInputStream();
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
                        String result = bufferedReader.readLine();
                        Log.d(TAG, "result:" + result);
                    } else {
                        Log.d(TAG, "result responsecode:" + responsecode);
                    }
                    connection.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 手机基站信息定位 不准确
     */
    private void getTelephonyManager() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (null == manager) {
            return;
        }
        String networkOperator = manager.getNetworkOperator();
        if (TextUtils.isEmpty(networkOperator)) {
            return;
        }
        GsmCellLocation location = (GsmCellLocation) manager.getCellLocation();
        final int mcc = Integer.parseInt(networkOperator.substring(0, 3));
        final int mnc = Integer.parseInt(networkOperator.substring(3));
        final int lac = location.getLac();
        final int cid = location.getCid();
        Log.d(TAG, "mcc:" + mcc);
        Log.d(TAG, "mnc:" + mnc);
        Log.d(TAG, "lac:" + lac);
        Log.d(TAG, "cid:" + cid);

        Executors.newFixedThreadPool(5).submit(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://api.cellocation.com:83/cell/?mcc=" + mcc + "&mnc=" + mnc + "&lac=" + lac + "&ci=" + cid + "&output=json");
                    Log.d(TAG, "url:" + url.toString());
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    //超时时间
                    connection.setConnectTimeout(3000);
                    //表示设置本次http请求使用GET方式
                    connection.setRequestMethod("GET");
                    //返回至为响应编号，如：HTTP_OK表示连接成功。
                    int responsecode = connection.getResponseCode();
                    if (responsecode == HttpURLConnection.HTTP_OK) {
                        InputStream inputStream = connection.getInputStream();
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
                        String result = bufferedReader.readLine();
                        Log.d(TAG, "result:" + result);
                    } else {
                        Log.d(TAG, "result responsecode:" + responsecode);
                    }
                    connection.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 获取网络位置
     *
     * @param longitude
     * @param latitude
     */
    private void getNetworkAddress(final double longitude, final double latitude) {
        Executors.newFixedThreadPool(5).submit(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://api.cellocation.com:83/regeo/?lat=" + latitude + "&lon=" + longitude + "&output=json");
                    Log.d(TAG, "url:" + url.toString());
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    //超时时间
                    connection.setConnectTimeout(3000);
                    //表示设置本次http请求使用GET方式
                    connection.setRequestMethod("GET");
                    //返回至为响应编号，如：HTTP_OK表示连接成功。
                    int responsecode = connection.getResponseCode();
                    if (responsecode == HttpURLConnection.HTTP_OK) {
                        InputStream inputStream = connection.getInputStream();
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
                        String result = bufferedReader.readLine();
                    } else {
                    }
                    connection.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 获取地理位置
     *
     * @param longitude
     * @param latitude
     * @return
     */
    private String getLocationAddr(double longitude, double latitude) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
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
                Log.d(TAG, "addresses.size() == 0 return");
                return "";
            }
            Address address = addresses.get(0);
            if (null == address) {
                Log.d(TAG, "null == address return");
                return "";
            }

            //获取最大地址行索引
            int maxAddressLineIndex = address.getMaxAddressLineIndex();
            //循环打印周围位置地址
            for (int i = 0; i < maxAddressLineIndex; i++) {
                String addressStr = address.getAddressLine(i);
                Log.d(TAG, i + " addressStr:" + addressStr);
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
            Log.d(TAG, "addressBuilder:" + addressBuilder);
            return addressBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
