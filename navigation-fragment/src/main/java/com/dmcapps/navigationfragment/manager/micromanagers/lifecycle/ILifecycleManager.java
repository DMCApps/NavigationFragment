package com.dmcapps.navigationfragment.manager.micromanagers.lifecycle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.dmcapps.navigationfragment.R;
import com.dmcapps.navigationfragment.manager.NavigationManagerFragment;
import com.dmcapps.navigationfragment.manager.micromanagers.ManagerConfig;
import com.dmcapps.navigationfragment.manager.micromanagers.ManagerState;

/**
 * Created by dcarmo on 2016-02-24.
 */
public interface ILifecycleManager {

    void onResume(NavigationManagerFragment navMgrFragment, ManagerState state, ManagerConfig config);
    void onPause(NavigationManagerFragment navMgrFragment, ManagerState state);

}
