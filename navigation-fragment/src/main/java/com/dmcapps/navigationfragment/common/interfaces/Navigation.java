package com.dmcapps.navigationfragment.common.interfaces;

import android.os.Bundle;

/**
 * Created by DCarmo on 16-02-09.
 */
public interface Navigation {

    String getNavTag();

    void setNavBundle(Bundle bundle);
    Bundle getNavBundle();

    NavigationManager getNavigationManager();

    void overrideNextAnimation(int animIn, int animOut);

    /**
     * Push a new Fragment onto the stack and presenting it to the screen
     * Uses default animation of slide in from right and slide out to left.
     *
     * @param
     *      navFragment -> The Fragment to show. It must be a Fragment that implements {@link Navigation}
     */
    void presentFragment(Navigation navFragment);

    /**
     * Push a new Fragment onto the stack and presenting it to the screen
     * Uses default animation of slide in from right and slide out to left.
     * Sends a Bundle with the Fragment that can be retrieved using {@link Navigation#getNavBundle()}
     *
     * @param
     *      navFragment -> The Fragment to show. It must be a Fragment that implements {@link Navigation}
     * @param
     *      navBundle -> Bundle to add to the presenting of the Fragment.
     */
    void presentFragment(Navigation navFragment, Bundle navBundle);

    void dismissToRoot();
    void dismissFragment();
    void dismissFragment(Bundle navBundle);

    void replaceRootFragment(Navigation navFragment);

    void setTitle(String title);
    void setTitle(int resId);
    String getTitle();

    void setMasterToggleTitle(String title);
    void setMasterToggleTitle(int resId);

    boolean isPortrait();
    boolean isTablet();
}
