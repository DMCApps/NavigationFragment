package com.github.dmcapps.navigationfragment.v7;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.dmcapps.navigationfragment.common.core.NavigationManager;
import com.github.dmcapps.navigationfragment.common.utils.ObjectUtils;
import com.github.dmcapps.navigationfragment.common.interfaces.NavigationManagerContainer;
import com.github.dmcapps.navigationfragment.common.interfaces.NavigationManagerListener;

public abstract class NavigationManagerFragment extends Fragment implements NavigationManagerContainer {
    // TODO: Animation making child disappear http://stackoverflow.com/a/23276145/845038
    private static final String TAG = NavigationManagerFragment.class.getSimpleName();

    private static final String KEY_NAVIGATION_MANAGER_CORE = "KEY_NAVIGATION_MANAGER_CORE";

    private NavigationManager mNavigationManager;

    // =========================================
    // Object Lifecycle
    // =========================================

    public NavigationManagerFragment() {}

    // =========================================
    // Fragment Lifecycle
    // =========================================

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            mNavigationManager = (NavigationManager)savedInstanceState.getSerializable(KEY_NAVIGATION_MANAGER_CORE);
        }

        mNavigationManager.setNavigationListener(ObjectUtils.as(NavigationManagerListener.class, getContext()));
        mNavigationManager.setContainer(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return mNavigationManager.getLifecycle().onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mNavigationManager.getLifecycle().onViewCreated(view, mNavigationManager);
    }

    @Override
    public void onResume() {
        super.onResume();
        mNavigationManager.getLifecycle().onResume(mNavigationManager);
    }

    @Override
    public void onPause() {
        super.onPause();
        mNavigationManager.getLifecycle().onPause(mNavigationManager);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(KEY_NAVIGATION_MANAGER_CORE, mNavigationManager);
    }

    // =========================================
    // Public
    // =========================================

    public void setDefaultPresentAnimations(int animIn, int animOut) {
        getNavigationManager().setDefaultPresentAnimations(animIn, animOut);
    }

    public void setDefaultDismissAnimations(int animIn, int animOut) {
        getNavigationManager().setDefaultDismissAnimations(animIn, animOut);
    }

    // =========================================
    // NavigationManagerContainer
    // =========================================

    @Override
    public NavigationManager getNavigationManager() {
        return mNavigationManager;
    }

    @Override
    public Object getNavChildFragmentManager() {
        return getChildFragmentManager();
    }

    @Override
    public Activity getFragmentActivity() {
        return getActivity();
    }

    // =========================================
    // Private
    // =========================================

    protected void setNavigationManager(NavigationManager navigationManager) {
        mNavigationManager = navigationManager;
    }

}
