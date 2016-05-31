package com.dmcapps.navigationfragment.manager.core.micromanagers.stack;

import android.os.Bundle;

import com.dmcapps.navigationfragment.fragments.INavigationFragment;
import com.dmcapps.navigationfragment.manager.core.NavigationManagerFragment;
import com.dmcapps.navigationfragment.manager.core.micromanagers.ManagerConfig;
import com.dmcapps.navigationfragment.manager.core.micromanagers.ManagerState;

import java.io.Serializable;

/**
 * Created by dcarmo on 2016-02-25.
 */
public interface IStackManager extends Serializable {

    INavigationFragment pushFragment(NavigationManagerFragment manager, ManagerState state, ManagerConfig config, INavigationFragment navFragment);

    INavigationFragment pushFragment(NavigationManagerFragment manager, ManagerState state, ManagerConfig config, INavigationFragment navFragment, Bundle navBundle);

    INavigationFragment popFragment(NavigationManagerFragment manager, ManagerState state, ManagerConfig config);

    INavigationFragment popFragment(NavigationManagerFragment manager, ManagerState state, ManagerConfig config, Bundle navBundle);

    void pushFragment(NavigationManagerFragment manager, ManagerState state, ManagerConfig config, INavigationFragment navFragment, int animIn, int animOut);

    void popFragment(NavigationManagerFragment manager, ManagerState state, ManagerConfig config, int animIn, int animOut);

    void clearNavigationStackToPosition(NavigationManagerFragment manager, ManagerState state, int stackPosition);

}
