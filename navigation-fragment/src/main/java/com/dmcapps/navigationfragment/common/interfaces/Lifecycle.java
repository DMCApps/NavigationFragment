package com.dmcapps.navigationfragment.common.interfaces;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dmcapps.navigationfragment.support.v7.manager.core.NavigationManagerFragment;

import java.io.Serializable;

/**
 * Created by dcarmo on 2016-02-24.
 */
public interface Lifecycle extends Serializable {

    void onResume(NavigationManagerFragment navMgrFragment, State state, Config config);

    View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    void onViewCreated(View view, State state, Config config);

    void onPause(NavigationManagerFragment navMgrFragment, State state);

}
