package com.dmcapps.navigationfragment.common.core.fragmentmanagerwrapper;

import com.dmcapps.navigationfragment.common.core.fragmenttransactionwrapper.FragmentTransactionWrapper;

/**
 * Created by dcarmo on 2016-11-19.
 */

public interface FragmentManagerWrapper {

    FragmentTransactionWrapper beginTransaction();

    Object findFragmentByTag(String tag);

    boolean executePendingTransactions();

}
