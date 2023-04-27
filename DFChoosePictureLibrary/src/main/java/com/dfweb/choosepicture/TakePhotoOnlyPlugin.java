package com.dfweb.choosepicture;

import android.content.pm.ActivityInfo;

import androidx.fragment.app.FragmentActivity;

import com.dffl.dfbaselibrary.plugin.DFJSBridgePlugin;
import com.dffl.dfbaselibrary.plugin.DFJsBridgePluginCallback;
import com.dfweb.choosepicture.matisse.MatisseStartup;
import com.dfweb.choosepicture.matisse.callback.PathResultCallBack;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.util.List;

public class TakePhotoOnlyPlugin extends DFJSBridgePlugin {
    @Override
    public void implJsBridge(FragmentActivity activity, DFJsBridgePluginCallback call) {
        {
            new MatisseStartup().from(activity).choose(MimeType.ofImage()).countable(false)
                    .capture(true) //拍照图片保存路径
                    .captureStrategy(
                            new CaptureStrategy(
                                    true,
                                    activity.getApplicationInfo().packageName + ".fileprovider",
                                    "webview"
                            )
                    )
                    .maxSelectable(9)
                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                    .thumbnailScale(0.8f)
                    .imageEngine(new GlideEngine())
                    .showSingleMediaType(false)
                    .originalEnable(true)
                    .maxOriginalSize(100)
                    .autoHideToolbarOnSingleTap(true)
                    .spanCount(4).forPathResult(new PathResultCallBack() {
                        @Override
                        public void onStrResult(List<String> data) {

                            if (data != null && !data.isEmpty()) {
                                 call.success(data);

                            } else {
                                call.failed();
                            }
                        }
                    });
            ;
        }
    }
}
