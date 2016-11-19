package com.dmcapps.navigationfragment.common.helpers.fragmentmanagerwrapper;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;

import com.dmcapps.navigationfragment.common.helpers.fragmenttransactionwrapper.FragmentTransactionWrapper;
import com.dmcapps.navigationfragment.common.helpers.fragmenttransactionwrapper.NavigationFragmentTransactionWrapper;
import com.dmcapps.navigationfragment.common.helpers.utils.ObjectUtils;

/**
 * Created by dcarmo on 2016-11-19.
 */


public class NavigationFragmentManagerWrapper implements FragmentManagerWrapper {

    private android.app.FragmentManager fragmentManager;
    private android.support.v4.app.FragmentManager supportFragmentManager;

    public NavigationFragmentManagerWrapper(@NonNull Object object) {
        fragmentManager = ObjectUtils.as(android.app.FragmentManager.class, object);
        supportFragmentManager = ObjectUtils.as(android.support.v4.app.FragmentManager.class, object);
        validateFragmentManager();
    }

    @Override
    @SuppressLint("CommitTransaction")
    public FragmentTransactionWrapper beginTransactionWrapped() {
        if (fragmentManager != null) {
            return new NavigationFragmentTransactionWrapper(fragmentManager.beginTransaction());
        }
        else {
            return new NavigationFragmentTransactionWrapper(supportFragmentManager.beginTransaction());
        }
    }

    @Override
    public Object findFragmentByTag(String tag) {
        if (fragmentManager != null) {
            return fragmentManager.findFragmentByTag(tag);
        }
        else {
            return supportFragmentManager.findFragmentByTag(tag);
        }
    }

    @Override
    public boolean executePendingTransactions() {
        if (fragmentManager != null) {
            return fragmentManager.executePendingTransactions();
        }
        else {
            return supportFragmentManager.executePendingTransactions();
        }
    }

    private void validateFragmentManager() {
        if (fragmentManager == null && supportFragmentManager == null) {
            throw new RuntimeException("The object used to create the NavigationFragmentManagerWrapper is not a fragment!");
        }
    }
}
