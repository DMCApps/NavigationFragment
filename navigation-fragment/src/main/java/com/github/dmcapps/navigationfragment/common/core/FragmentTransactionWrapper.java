package com.github.dmcapps.navigationfragment.common.core;

import android.os.Build;
import android.support.annotation.NonNull;
import android.transition.Transition;
import android.view.Gravity;
import android.view.View;

import com.github.dmcapps.navigationfragment.common.utils.ObjectUtils;

/**
 * Created by dcarmo on 2016-11-19.
 */

class FragmentTransactionWrapper {
    private static final String TAG = FragmentTransactionWrapper.class.getSimpleName();

    private android.app.FragmentTransaction fragmentTransaction;
    private android.support.v4.app.FragmentTransaction supportFragmentTransaction;

    FragmentTransactionWrapper(@NonNull Object object) {
        fragmentTransaction = ObjectUtils.as(android.app.FragmentTransaction.class, object);
        supportFragmentTransaction = ObjectUtils.as(android.support.v4.app.FragmentTransaction.class, object);
        validateFragmentTransaction();
    }

    FragmentTransactionWrapper setCustomAnimations(int enter, int exit) {
        if (fragmentTransaction != null) {
            fragmentTransaction.setCustomAnimations(enter, exit);
        }
        else {
            supportFragmentTransaction.setCustomAnimations(enter, exit);
        }
        return this;
    }

    FragmentTransactionWrapper setCustomAnimations(int enter, int exit, int popEnter, int popExit) {
        if (fragmentTransaction != null) {
            fragmentTransaction.setCustomAnimations(enter, exit, popEnter, popExit);
        }
        else {
            supportFragmentTransaction.setCustomAnimations(enter, exit, popEnter, popExit);
        }
        return this;
    }

    FragmentTransactionWrapper add(int containerId, Object object, String tag) {
        if (fragmentTransaction != null) {
            fragmentTransaction.add(containerId, (android.app.Fragment)object, tag);
        }
        else {
            supportFragmentTransaction.add(containerId, (android.support.v4.app.Fragment)object, tag);
        }
        return this;
    }

    FragmentTransactionWrapper addSharedElement(View view, String name) {
        if (fragmentTransaction != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            fragmentTransaction.addSharedElement(view, name);
        }
        else if (supportFragmentTransaction != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            supportFragmentTransaction.addSharedElement(view, name);
        }
        return this;
    }

    FragmentTransactionWrapper addToBackStack(String name) {
        if (fragmentTransaction != null) {
            fragmentTransaction.addToBackStack(name);
        }
        else {
            supportFragmentTransaction.addToBackStack(name);
        }
        return this;
    }

    FragmentTransactionWrapper remove(Object object) {
        if (fragmentTransaction != null) {
            fragmentTransaction.remove((android.app.Fragment)object);
        }
        else {
            supportFragmentTransaction.remove((android.support.v4.app.Fragment)object);
        }
        return this;
    }

    FragmentTransactionWrapper attach(Object object) {
        if (fragmentTransaction != null) {
            fragmentTransaction.attach((android.app.Fragment)object);
        }
        else {
            supportFragmentTransaction.attach((android.support.v4.app.Fragment)object);
        }
        return this;
    }

    FragmentTransactionWrapper detach(Object object) {
        if (fragmentTransaction != null) {
            fragmentTransaction.detach((android.app.Fragment)object);
        }
        else {
            supportFragmentTransaction.detach((android.support.v4.app.Fragment)object);
        }
        return this;
    }

    int commit() {
        if (fragmentTransaction != null) {
            return fragmentTransaction.commit();
        }
        else {
            return supportFragmentTransaction.commit();
        }
    }

    private void validateFragmentTransaction() {
        if (fragmentTransaction == null && supportFragmentTransaction == null) {
            throw new RuntimeException("The object used to create the FragmentManagerWrapper is not a fragment!");
        }
    }
}
