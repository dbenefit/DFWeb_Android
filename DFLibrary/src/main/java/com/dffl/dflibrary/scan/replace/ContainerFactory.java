package com.dffl.dflibrary.scan.replace;

import android.os.Build;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

public class ContainerFactory {

    private static String TAG = "ScanCodeResultFragment";

        public static IContainer create(FragmentActivity activity )  {

            Fragment resultFragment =  activity.getSupportFragmentManager().findFragmentByTag(TAG);

            if (resultFragment!=null) {
                return (ScanCodeResultFragment)resultFragment ;
            }
            return getAcceptResultFragment(activity.getSupportFragmentManager());

        }

        public static IContainer create(Fragment fragment)  {
            FragmentManager fm;
             if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                 fm=  fragment.getChildFragmentManager();
            } else {
                 fm=  fragment.getParentFragmentManager();
            }
           Fragment resultFragment = fm.findFragmentByTag(TAG);
            if (resultFragment!=null) {
                return (ScanCodeResultFragment)resultFragment;
            }
            return getAcceptResultFragment(fm);

        }

        private static ScanCodeResultFragment getAcceptResultFragment(FragmentManager fm )  {
            try {
                ScanCodeResultFragment fragment = new ScanCodeResultFragment();
                fm.beginTransaction()
                        .add(fragment, TAG)
                        .commitAllowingStateLoss();
                fm.executePendingTransactions();
                return fragment;

            }catch (Exception e){
                e.printStackTrace();
                return null ;
            }

        }
}
