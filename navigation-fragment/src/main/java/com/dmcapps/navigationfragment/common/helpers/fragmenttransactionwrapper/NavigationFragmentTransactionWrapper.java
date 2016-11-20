package com.dmcapps.navigationfragment.common.helpers.fragmenttransactionwrapper;

import android.os.Build;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.dmcapps.navigationfragment.common.core.NavigationSettings.SharedElement;
import com.dmcapps.navigationfragment.common.helpers.utils.ObjectUtils;

/**
 * Created by dcarmo on 2016-11-19.
 */

public class NavigationFragmentTransactionWrapper implements FragmentTransactionWrapper {
    private static final String TAG = NavigationFragmentTransactionWrapper.class.getSimpleName();

    private android.app.FragmentTransaction fragmentTransaction;
    private android.support.v4.app.FragmentTransaction supportFragmentTransaction;

    public NavigationFragmentTransactionWrapper(@NonNull Object object) {
        fragmentTransaction = ObjectUtils.as(android.app.FragmentTransaction.class, object);
        supportFragmentTransaction = ObjectUtils.as(android.support.v4.app.FragmentTransaction.class, object);
        validateFragmentTransaction();
    }

    @Override
    public void setCustomAnimations(int enter, int exit) {
        if (fragmentTransaction != null) {
            fragmentTransaction.setCustomAnimations(enter, exit);
        }
        else {
            supportFragmentTransaction.setCustomAnimations(enter, exit);
        }
    }

    @Override
    public void setCustomAnimations(int enter, int exit, int popEnter, int popExit) {
        if (fragmentTransaction != null) {
            fragmentTransaction.setCustomAnimations(enter, exit, popEnter, popExit);
        }
        else {
            supportFragmentTransaction.setCustomAnimations(enter, exit, popEnter, popExit);
        }
    }

    @Override
    public void add(int containerId, Object object, String tag) {
        if (fragmentTransaction != null) {
            fragmentTransaction.add(containerId, (android.app.Fragment)object, tag);
        }
        else {
            supportFragmentTransaction.add(containerId, (android.support.v4.app.Fragment)object, tag);
        }
    }

    @Override
    public void addSharedElement(SharedElement sharedElement) {
        addSharedElement(sharedElement.view, sharedElement.name);
    }

    @Override
    public void addSharedElement(View view, String name) {
        if (fragmentTransaction != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            fragmentTransaction.addSharedElement(view, name);
        }
        else {
            Log.i(TAG, "Cannot perform shared element transactions on a support fragment transaction or below API 21!");
        }
    }

    @Override
    public void addToBackStack(String name) {
        if (fragmentTransaction != null) {
            fragmentTransaction.addToBackStack(name);
        }
        else {
            supportFragmentTransaction.addToBackStack(name);
        }
    }

    @Override
    public void remove(Object object) {
        if (fragmentTransaction != null) {
            fragmentTransaction.remove((android.app.Fragment)object);
        }
        else {
            supportFragmentTransaction.remove((android.support.v4.app.Fragment)object);
        }
    }

    @Override
    public void attach(Object object) {
        if (fragmentTransaction != null) {
            fragmentTransaction.attach((android.app.Fragment)object);
        }
        else {
            supportFragmentTransaction.attach((android.support.v4.app.Fragment)object);
        }
    }

    @Override
    public void detach(Object object) {
        if (fragmentTransaction != null) {
            fragmentTransaction.detach((android.app.Fragment)object);
        }
        else {
            supportFragmentTransaction.detach((android.support.v4.app.Fragment)object);
        }
    }

    @Override
    public int commit() {
        if (fragmentTransaction != null) {
            return fragmentTransaction.commit();
        }
        else {
            return supportFragmentTransaction.commit();
        }
    }

    private void validateFragmentTransaction() {
        if (fragmentTransaction == null && supportFragmentTransaction == null) {
            throw new RuntimeException("The object used to create the NavigationFragmentManagerWrapper is not a fragment!");
        }
    }
}
