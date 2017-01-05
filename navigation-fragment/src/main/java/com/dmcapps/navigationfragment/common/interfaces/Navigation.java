package com.dmcapps.navigationfragment.common.interfaces;

import android.os.Bundle;

import com.dmcapps.navigationfragment.common.core.NavigationManager;
import com.dmcapps.navigationfragment.common.core.NavigationSettings;

/**
 * Created by DCarmo on 16-02-09.
 */
public interface Navigation {

    String getNavTag();

    void setNavBundle(Bundle bundle);
    Bundle getNavBundle();

    NavigationManager getNavigationManager();

    /**
     * Override the animation in and out of the next present, dismiss or clear stack call.
     *
     * @param
     *      animIn -> The resource of the new in animation.
     * @param
     *      animOut -> The resource of the new in animation.
     * @deprecated
     *      This call is being replaced with {@link NavigationSettings}. PLEASE UPDATE TO {@link Navigation#presentFragment(Navigation, NavigationSettings)}
     */
    @Deprecated
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
     *
     * @deprecated
     *      This function is being replaced with the {@link NavigationManager#pushFragment(Navigation, NavigationSettings)} method call.
     *      Allowing for more parameters to be passed in with the call.
     *      To be removed in 1.2.0.
     */
    @Deprecated
    void presentFragment(Navigation navFragment, Bundle navBundle);

    /**
     * Push a new Fragment onto the stack and presenting it to the screen
     * Uses default animation of slide in from right and slide out to left.
     *
     * @param
     *      navFragment -> The Fragment to show. It must be a Fragment that implements {@link Navigation}
     * @param
     *      settings -> The {@link NavigationSettings} to be applied to the transaction
     */
    void presentFragment(Navigation navFragment, NavigationSettings settings);

    /**
     * Dimiss the current fragment off the top of the stack and dismiss it.
     * Uses default animation of slide in from left and slide out to right animation.
     */
    void dismissFragment();

    /**
     * Pop the current fragment off the top of the stack and dismiss it.
     * Uses default animation of slide in from left and slide out to right animation.
     *
     * @param
     *      navBundle -> The navigation bundle to add to the fragment after the pop occurs
     */
    @Deprecated
    void dismissFragment(Bundle navBundle);

    /**
     * Dismiss all fragments to the given index in the stack
     */
    void dismissToIndex(int index);

    /**
     * Dismiss all fragments from the stack until we reach the Root Fragment (the fragment at the min stack size)
     */
    void dismissToRoot();

    /**
     * Remove all fragments from the stack including the Root. The add the given {@link Navigation}
     * as the new root fragment. The definition of the Root Fragment is the Fragment at the min stack size position.
     *
     * @param
     *      navFragment -> The fragment that you would like as the new Root of the stack.
     */
    void replaceRootFragment(Navigation navFragment);

    /**
     * Gets the fragment that is in the stack before the current one. This is to help with setting the Transitions
     *
     * @return
     *      {@link Navigation} object that is before this one.
     */
    //Navigation getPreviousFragment();

    void setTitle(String title);
    void setTitle(int resId);
    String getTitle();

    boolean isPortrait();
    boolean isTablet();
}
