package com.dfweb.location;


import com.dffl.dfbaselibrary.bean.GPSResponseBean;

public interface LocationCallback {
   void   onSuccessLocationListener(GPSResponseBean gpsResponseBean);

}
