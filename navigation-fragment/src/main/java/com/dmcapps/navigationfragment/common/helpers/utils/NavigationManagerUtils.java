package com.dmcapps.navigationfragment.common.helpers.utils;

import android.app.FragmentManager;

import com.dmcapps.navigationfragment.common.interfaces.NavigationManager;

/**
 * Created by dcarmo on 2016-09-29.
 */
public final class NavigationManagerUtils {
    private static final String TAG = NavigationManagerUtils.class.getSimpleName();

    private NavigationManagerUtils() { }

    public static android.app.FragmentManager getFragmentManager(NavigationManager navigationManager) {
        android.app.FragmentManager childFragManager = ObjectUtils.as(android.app.FragmentManager.class, navigationManager.getNavigationFragmentManager());

        if (childFragManager == null) {
            throw new RuntimeException("NavigationManager#getNavigationFragmentManager() must return a android.app.FragmentManager to use the StackLifecycleManager.");
        }

        return childFragManager;
    }

    public static android.support.v4.app.FragmentManager getSupportFragmentManager(NavigationManager navigationManager) {
        android.support.v4.app.FragmentManager childFragManager = ObjectUtils.as(android.support.v4.app.FragmentManager.class, navigationManager.getNavigationFragmentManager());

        if (childFragManager == null) {
            throw new RuntimeException("NavigationManager#getNavigationFragmentManager() must return a android.support.v4.app.FragmentManager to use the StackLifecycleManager.");
        }

        return childFragManager;
    }
}
