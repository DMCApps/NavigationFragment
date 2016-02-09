package com.dmcapps.navigationfragment.fragment.pattern.fragments;

import com.dmcapps.navigationfragment.fragment.pattern.manager.NavigationManagerFragment;

import java.io.Serializable;

/**
 * Created by DCarmo on 16-02-09.
 */
public interface INavigationFragment extends Serializable {

    String getNavTag();

    void setNavigationManager(NavigationManagerFragment navigationManager);
    NavigationManagerFragment getNavigationManager();

    void presentFragment(INavigationFragment navFragment);
    void presentFragment(INavigationFragment navFragment, int animationIn, int animationOut);

    void dismissToRoot();
    void dismissFragment();
    void dismissFragment(int animationIn, int animationOut);

    void replaceRootFragment(INavigationFragment navFragment);
}
