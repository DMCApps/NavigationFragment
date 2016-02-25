package com.dmcapps.navigationfragment.manager.micromanagers.lifecycle;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dmcapps.navigationfragment.R;
import com.dmcapps.navigationfragment.manager.NavigationManagerFragment;
import com.dmcapps.navigationfragment.manager.micromanagers.ManagerConfig;
import com.dmcapps.navigationfragment.manager.micromanagers.ManagerState;

import java.util.zip.Inflater;

/**
 * Created by dcarmo on 2016-02-24.
 */
public interface ILifecycleManager {

    void onResume(NavigationManagerFragment navMgrFragment, ManagerState state, ManagerConfig config);

    View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    void onPause(NavigationManagerFragment navMgrFragment, ManagerState state);

}
