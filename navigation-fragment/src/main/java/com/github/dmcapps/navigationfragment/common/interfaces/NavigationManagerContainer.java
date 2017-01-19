package com.github.dmcapps.navigationfragment.common.interfaces;

import android.app.Activity;
import android.app.FragmentManager;

import com.github.dmcapps.navigationfragment.common.core.NavigationManager;

/**
 * Created by dcarmo on 2016-12-18.
 */

public interface NavigationManagerContainer {

    NavigationManager getNavigationManager();

    /**
     * Get the current {@link FragmentManager} from the {@link NavigationManager}
     *
     * @return
     *      Returns the Child Fragment Manager of the current fragment as an object to maintain a generic instance between
     *      support and non support fragments
     */
    Object getNavChildFragmentManager();

    Activity getFragmentActivity();

}
