package com.dmcapps.navigationfragment.manager.core.micromanagers.lifecycle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dmcapps.navigationfragment.manager.core.NavigationManagerFragment;
import com.dmcapps.navigationfragment.manager.core.micromanagers.ManagerConfig;
import com.dmcapps.navigationfragment.manager.core.micromanagers.ManagerState;

import java.io.Serializable;

/**
 * Created by dcarmo on 2016-02-24.
 */
public interface ILifecycleManager extends Serializable {

    void onResume(NavigationManagerFragment navMgrFragment, ManagerState state, ManagerConfig config);

    View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    void onViewCreated(View view, ManagerState state, ManagerConfig config);

    void onPause(NavigationManagerFragment navMgrFragment, ManagerState state);

}
