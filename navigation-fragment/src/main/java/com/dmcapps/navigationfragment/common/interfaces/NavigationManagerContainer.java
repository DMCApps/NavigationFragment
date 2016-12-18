package com.dmcapps.navigationfragment.common.interfaces;

import android.app.Activity;
import android.app.FragmentManager;

/**
 * Created by dcarmo on 2016-12-18.
 */

public interface NavigationManagerContainer {

    NavigationManager getNavigationCore();

    /**
     * Get the current {@link FragmentManager} from the {@link NavigationManager}
     *
     * @return
     *      Returns the Child Fragment Manager of the current fragment
     */
    Object getNavChildFragmentManager();

    Activity getActivity();

}
