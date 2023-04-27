package com.dfweb.choosepicture.matisse.replace;

import android.os.Build;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;


/**
 *
 */
public class ContainerFactory {
    private ContainerFactory() {
    }

    public static ContainerFactory getSingleton() {
         return ContainerFactory.Inner.instance;

    }

    private static class Inner {
        private static final ContainerFactory instance = new ContainerFactory();

    }


        private static final ContainerFactory instance = new ContainerFactory();
        private static String TAG = "MatisseResultFragment";

        public IContainer create(FragmentActivity fragmentActivity) {
          FragmentManager  fragmentmanager = fragmentActivity.getSupportFragmentManager();
           Fragment fragment=  fragmentmanager.findFragmentByTag(TAG);
           if (fragment!=null){
               return ((MatisseResultFragment )fragment);
           }else {
               return  getAcceptResultFragment(fragmentmanager);
           }

        }

        public IContainer create(Fragment  fragment ) {
            FragmentManager fragmentManager;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
                fragmentManager=  fragment.getChildFragmentManager();
            }else {
                fragmentManager=fragment.getParentFragmentManager();
            }
                Fragment resultFragment=  fragmentManager.findFragmentByTag(TAG);
            if (resultFragment!=null){
                return ((MatisseResultFragment )resultFragment);
            }else {
                return  getAcceptResultFragment(fragmentManager);
            }

        }
        private MatisseResultFragment getAcceptResultFragment(  FragmentManager fm) {
            MatisseResultFragment fragment = new MatisseResultFragment();
            fm.beginTransaction()
                    .add(fragment, TAG)
                    .commitAllowingStateLoss();
            fm.executePendingTransactions();
            return fragment;
        }
}