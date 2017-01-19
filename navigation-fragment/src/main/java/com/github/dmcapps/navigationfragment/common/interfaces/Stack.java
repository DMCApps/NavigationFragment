package com.github.dmcapps.navigationfragment.common.interfaces;

import android.os.Bundle;

import com.github.dmcapps.navigationfragment.common.core.NavigationManager;
import com.github.dmcapps.navigationfragment.common.core.PresentationTransaction;

import java.io.Serializable;

/**
 * Created by dcarmo on 2016-02-25.
 */
public interface Stack extends Serializable {

    Navigation pushFragment(NavigationManager navigationManager, Navigation navFragment, PresentationTransaction transaction);

    Navigation popFragment(NavigationManager navigationManager, Bundle navBundle);

    void clearNavigationStackToIndex(NavigationManager navigationManager, int index);

    void clearNavigationStackToIndex(NavigationManager navigationManager, int index, boolean inclusive);

    Navigation getFragmentAtIndex(NavigationManager navigationManager, int index);

}
