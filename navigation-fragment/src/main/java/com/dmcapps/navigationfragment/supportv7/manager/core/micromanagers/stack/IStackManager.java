package com.dmcapps.navigationfragment.supportv7.manager.core.micromanagers.stack;

import android.os.Bundle;

import com.dmcapps.navigationfragment.common.INavigationFragment;
import com.dmcapps.navigationfragment.supportv7.manager.core.SupportNavigationManagerFragment;
import com.dmcapps.navigationfragment.supportv7.manager.core.micromanagers.ManagerConfig;
import com.dmcapps.navigationfragment.supportv7.manager.core.micromanagers.ManagerState;

import java.io.Serializable;

/**
 * Created by dcarmo on 2016-02-25.
 */
public interface IStackManager extends Serializable {

    INavigationFragment pushFragment(SupportNavigationManagerFragment manager, ManagerState state, ManagerConfig config, INavigationFragment navFragment);

    INavigationFragment pushFragment(SupportNavigationManagerFragment manager, ManagerState state, ManagerConfig config, INavigationFragment navFragment, Bundle navBundle);

    INavigationFragment popFragment(SupportNavigationManagerFragment manager, ManagerState state, ManagerConfig config);

    INavigationFragment popFragment(SupportNavigationManagerFragment manager, ManagerState state, ManagerConfig config, Bundle navBundle);

    void clearNavigationStackToPosition(SupportNavigationManagerFragment manager, ManagerState state, int stackPosition);

}
