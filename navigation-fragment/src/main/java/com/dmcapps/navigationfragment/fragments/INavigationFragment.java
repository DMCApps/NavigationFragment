package com.dmcapps.navigationfragment.fragments;

import com.dmcapps.navigationfragment.manager.NavigationManagerFragment;

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

    void setTitle(String title);
    void setTitle(int resId);

    void setMasterToggleTitle(String title);
    void setMasterToggleTitle(int resId);

    /*
    void setDisplayHomeAsUpEnabled(boolean enabled);

    void setHomeAsUpIndicator(Drawable indicator);
    void setHomeAsUpIndicator(int resId);
    */

    boolean isPortrait();
    boolean isTablet();
}
