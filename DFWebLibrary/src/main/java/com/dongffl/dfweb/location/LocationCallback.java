package com.dongffl.dfweb.location;

import com.dongffl.dfweb.bean.GPSResponseBean;

public interface LocationCallback {
   void   onSuccessLocationListener(GPSResponseBean gpsResponseBean);

}
