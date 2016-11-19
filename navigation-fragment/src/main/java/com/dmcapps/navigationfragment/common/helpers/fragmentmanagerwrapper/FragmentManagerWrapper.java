package com.dmcapps.navigationfragment.common.helpers.fragmentmanagerwrapper;

import com.dmcapps.navigationfragment.common.helpers.fragmenttransactionwrapper.FragmentTransactionWrapper;

/**
 * Created by dcarmo on 2016-11-19.
 */

public interface FragmentManagerWrapper {

    FragmentTransactionWrapper beginTransactionWrapped();

    Object findFragmentByTag(String tag);

    boolean executePendingTransactions();

}
