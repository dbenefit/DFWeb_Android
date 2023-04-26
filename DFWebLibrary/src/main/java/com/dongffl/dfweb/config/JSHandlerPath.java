package com.dongffl.dfweb.config;

public interface JSHandlerPath {

    // 关闭页面方法
    String FINISH = "/handler/finish";
    // 获取设备mac地址
    //  String MAC_INFO = "/handler/getMacAddressInfo";
    // 设备信息
    String DEVICE_INFO = "/handler/getDeviceInfo";
    // 只拍照
    String TAKE_PHOTO_ONLY = "/handler/takePhotoOnly";
    // 打开新webview
    String OPEN_NEW_WEB = "/handler/openNewWeb";
    // 获取gps
    String GET_GPS_LOC = "/handler/getGpsLoc";
    // 分享
    String SHARE = "/handler/share";
    // 设置标题
    String SET_PAGE_TITLE = "/handler/setPageTitle";
    // 获取wifi信息
    String GET_WIFI_INFO = "/handler/getWifiInfo";
    // 选择照片上传
    String CHOOSE_PIC = "/handler/choosePic";
    // 打开第三方
    String OPEN_THIRD_PKG = "/handler/openThirdPkg";
    // 清除缓存
    String CLEAR_CACHE = "/handler/clearCache";
    // 获取app信息
    String GET_APP_INFO = "/handler/getAppInfo";
    // 打开收银台
    String OPEN_CHECK_STAND = "/handler/openCheckStand";
    // 打开页面
    String SHOW_PAGE = "/handler/showPage";
    // 重新登录
    String RELOGIN = "/handler/relogin";
    // 获取城市定位
    String APP_GET_CITY_INFO = "/handler/getCityInfo";
    // 回到首页
    String BACK_TO_HOME = "/handler/backToHome";
    // 回到商城首页
    String BACK_TO_STORE_HOME = "/handler/backToStorehome";
    // 切换城市
    String SWITCH_CITY_INFO = "/handler/switchCityInfo";

    // 支付宝支付
    String ALI_PAY = "/handler/alipay";
    // 微信支付
    String WE_CHAT_PAY = "/handler/wxpay";
    //
    String IS_WX_INSTALLED = "/handler/isWXInstalled";
    // 调用android返回键功能
    String GO_BACK = "/handler/goback";

    // 获取app相关授权状态
    String AUTH_STATE = "/handler/getAuthorizedStatus";
    // 跳转系统app的设置页
    String SYSTEM_SETTING = "/handler/goSystemSetting";
    // 跳转到喜马拉雅播放页
    String PLAY_MUSIC = "/handler/playAlbumTrack";
    // 获取到喜马拉雅支付信息
    String BUY_MUSIC_INFO = "/handler/getTrackPayInfo";
    //唤起小程序
    String CALL_APPLETS = "/handler/callApplets";
    //扫码
    String SCAN_CODE = "/handler/openAppScan";
    //
    String NO_SUCH_HANDLER = "/handler/noSuchHandler";
    //获取app内H5悬浮窗数据
    String GET_FLOATWINDOW = "/handler/getFloatWindow";
    //设置app内H5悬浮窗数据
    String SET_FLOATWINDOW = "/handler/setFloatWindow";
}
