package com.dmcapps.navigationfragment.common.helpers.fragmenttransactionwrapper;

import android.view.View;

import com.dmcapps.navigationfragment.common.core.NavigationTransaction.SharedElement;

/**
 * Created by dcarmo on 2016-11-19.
 */

public interface FragmentTransactionWrapper {

    void setCustomAnimations(int enter, int exit);

    void setCustomAnimations(int enter, int exit, int popEnter, int popExit);

    void add(int containerId, Object object, String tag);

    void addSharedElement(SharedElement sharedElement);

    void addSharedElement(View view, String name);

    void addToBackStack(String name);

    void remove(Object object);

    void attach(Object object);

    void detach(Object object);

    int commit();

}
