package com.dmcapps.navigationfragment.manager.micromanagers.lifecycle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dmcapps.navigationfragment.manager.NavigationManagerFragment;
import com.dmcapps.navigationfragment.manager.micromanagers.ManagerConfig;
import com.dmcapps.navigationfragment.manager.micromanagers.ManagerState;

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
