package com.dmcapps.navigationfragment.common.helpers.fragmenttransactionwrapper;

/**
 * Created by dcarmo on 2016-11-19.
 */

public interface FragmentTransactionWrapper {

    void setCustomAnimations(int inAnim, int outAnim);

    void add(int containerId, Object object, String tag);

    void remove(Object object);

    void attach(Object object);

    void detach(Object object);

    int commit();

}
