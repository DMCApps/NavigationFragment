package com.github.dmcapps.navigationfragment.common.core;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;

import com.github.dmcapps.navigationfragment.common.utils.ObjectUtils;

/**
 * Created by dcarmo on 2016-11-19.
 */


@SuppressLint("CommitTransaction")
class FragmentManagerWrapper {

    private android.app.FragmentManager fragmentManager;
    private android.support.v4.app.FragmentManager supportFragmentManager;

    FragmentManagerWrapper(@NonNull Object object) {
        fragmentManager = ObjectUtils.as(android.app.FragmentManager.class, object);
        supportFragmentManager = ObjectUtils.as(android.support.v4.app.FragmentManager.class, object);
        validateFragmentManager();
    }

    FragmentTransactionWrapper beginTransactionWrapped() {
        if (fragmentManager != null) {
            return new FragmentTransactionWrapper(fragmentManager.beginTransaction());
        }
        else {
            return new FragmentTransactionWrapper(supportFragmentManager.beginTransaction());
        }
    }

    Object beginTransaction() {
        if (fragmentManager != null) {
            return fragmentManager.beginTransaction();
        }
        else {
            return supportFragmentManager.beginTransaction();
        }
    }

    public Object findFragmentByTag(String tag) {
        if (fragmentManager != null) {
            return fragmentManager.findFragmentByTag(tag);
        }
        else {
            return supportFragmentManager.findFragmentByTag(tag);
        }
    }

    void popBackStack() {
        if (fragmentManager != null) {
            fragmentManager.popBackStack();
        }
        else {
            supportFragmentManager.popBackStack();
        }
    }

    void popBackStackImmediate(String name, int flags) {
        if (fragmentManager != null) {
            fragmentManager.popBackStackImmediate(name, flags);
        }
        else {
            supportFragmentManager.popBackStackImmediate(name, flags);
        }
    }

    private void validateFragmentManager() {
        if (fragmentManager == null && supportFragmentManager == null) {
            throw new RuntimeException("The object used to create the FragmentManagerWrapper is not a fragment!");
        }
    }
}
