package com.dmcapps.navigationfragment.fragments;

import com.dmcapps.navigationfragment.manager.NavigationManagerFragment;

import java.io.Serializable;

/**
 * Created by DCarmo on 16-02-09.
 */
public interface INavigationFragment {

    String getNavTag();

    NavigationManagerFragment getNavigationManager();

    void overrideNextAnimation(int animIn, int animOut);

    /**
     * Push a new Fragment onto the stack and presenting it to the screen
     * Uses default animation of slide in from right and slide out to left.
     *
     * @param
     *      navFragment -> The Fragment to show. It must be a Fragment that implements {@link INavigationFragment}
     */
    void presentFragment(INavigationFragment navFragment);
    void presentFragment(INavigationFragment navFragment, int animationIn, int animationOut);

    void dismissToRoot();
    void dismissFragment();
    void dismissFragment(int animationIn, int animationOut);

    void replaceRootFragment(INavigationFragment navFragment);

    void setTitle(String title);
    void setTitle(int resId);
    String getTitle();

    void setMasterToggleTitle(String title);
    void setMasterToggleTitle(int resId);

    boolean isPortrait();
    boolean isTablet();
}
