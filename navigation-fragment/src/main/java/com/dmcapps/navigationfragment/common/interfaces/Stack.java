package com.dmcapps.navigationfragment.common.interfaces;

import android.os.Bundle;

import com.dmcapps.navigationfragment.common.core.NavigationManager;
import com.dmcapps.navigationfragment.common.core.NavigationTransaction;

import java.io.Serializable;

/**
 * Created by dcarmo on 2016-02-25.
 */
public interface Stack extends Serializable {

    Navigation pushFragment(NavigationManager navigationManager, Navigation navFragment);

    Navigation pushFragment(NavigationManager navigationManager, Navigation navFragment, NavigationTransaction transaction);

    Navigation popFragment(NavigationManager navigationManager);

    Navigation popFragment(NavigationManager navigationManager, Bundle navBundle);

    void clearNavigationStackToIndex(NavigationManager navigationManager, int index);

    void clearNavigationStackToIndex(NavigationManager navigationManager, int index, boolean inclusive);

    Navigation getFragmentAtIndex(NavigationManager navigationManager, int index);

}
