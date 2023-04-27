package com.dfweb.choosepicture.matisse.replace;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.dfweb.choosepicture.matisse.callback.PathResultCallBack;
import com.dfweb.choosepicture.matisse.callback.ResultCallBack;
import com.dfweb.choosepicture.matisse.callback.UriResultCallBack;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.ui.MatisseActivity;

class MatisseResultFragment extends Fragment implements IContainer {
    //
    private static int REQUEST_CODE = 10086;

    //
    private ResultCallBack mResultCallBack;

    /**
     *
     */


    @Override
    public void forResult(ResultCallBack call) {
        this.mResultCallBack = call;
        Intent intent = new Intent(getContext(), MatisseActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mResultCallBack == null) {
            return;
        }
        if (resultCode == Activity.RESULT_OK) {
            if (mResultCallBack instanceof UriResultCallBack) {
                ((UriResultCallBack) mResultCallBack).onUriResult(Matisse.obtainResult(data));
            } else {
                ((PathResultCallBack) mResultCallBack).onStrResult(Matisse.obtainPathResult(data));
            }
        } else {
            if (mResultCallBack instanceof UriResultCallBack) {
                ((UriResultCallBack) mResultCallBack).onUriResult(null);
            } else {
                ((PathResultCallBack) mResultCallBack).onStrResult(null);
            }
        }
    }

}
