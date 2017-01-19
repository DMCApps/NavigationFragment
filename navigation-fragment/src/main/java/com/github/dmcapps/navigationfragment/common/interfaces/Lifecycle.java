package com.github.dmcapps.navigationfragment.common.interfaces;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.dmcapps.navigationfragment.common.core.NavigationManager;

import java.io.Serializable;

/**
 * Created by dcarmo on 2016-02-24.
 */
public interface Lifecycle extends Serializable {

    View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    void onViewCreated(View view, NavigationManager navigationManager);

    void onResume(NavigationManager navigationManager);

    void onPause(NavigationManager navigationManager);

}
