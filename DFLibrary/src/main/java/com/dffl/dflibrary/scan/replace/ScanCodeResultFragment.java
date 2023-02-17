package com.dffl.dflibrary.scan.replace;

import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.dffl.dflibrary.scan.DFCaptureActivity;
import com.dffl.dflibrary.scan.ScanResultType;
import com.dffl.dflibrary.scan.callback.ResultCallBack;
import com.dffl.dflibrary.scan.litezxing.CameraScan;


public class ScanCodeResultFragment extends Fragment implements IContainer {


    //在重中之重重中之重
    private Integer REQUEST_CODE = 10086;

    //
    private ResultCallBack mResultCallBack;

    //0  表示  全局扫一扫  需要对外部链接进行拦截

    private int mType = -1;

    @Override
    public void startType(int type) {
        mType = type;
    }

    /**
     *
     */
    @Override
    public void forResult(ResultCallBack call) {
        this.mResultCallBack = call;
        Intent intent = new Intent(requireContext(), DFCaptureActivity.class);
        intent.putExtra("type", mType);
        startActivityForResult(intent, REQUEST_CODE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (this.mResultCallBack == null) {
            return;
        }
        if (resultCode == ScanResultType.SUCCESS) {
            if (data == null) {
                mResultCallBack.onResult(false, false, null);
            } else {
                String result = data.getStringExtra(CameraScan.SCAN_RESULT);
                mResultCallBack.onResult(true, false, result);
            }
        } else if (resultCode == ScanResultType.CANCEL) {
            mResultCallBack.onResult(false, true, null);
        } else if (resultCode == ScanResultType.FAILED) {
            mResultCallBack.onResult(false, false, null);
        } else {
            mResultCallBack.onResult(false, false, null);
        }
    }
}
