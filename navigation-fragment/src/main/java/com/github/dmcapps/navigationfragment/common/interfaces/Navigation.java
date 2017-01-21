package com.github.dmcapps.navigationfragment.common.interfaces;

import android.os.Bundle;

import com.github.dmcapps.navigationfragment.common.core.NavigationManager;
import com.github.dmcapps.navigationfragment.common.core.PresentationTransaction;

/**
 * TODO: Once android supports Default Methods across more platforms I can easily add in default implementations
 * for almost all these methods to reduce the duplication in the support and non-support fragments.
 * Reference: https://developer.android.com/guide/platform/j8-jack.html
 *
 * Created by DCarmo on 16-02-09.
 */
public interface Navigation {

    String getNavTag();

    void setNavBundle(Bundle bundle);
    Bundle getNavBundle();

    NavigationManager getNavigationManager();

    /**
     * Begins a Transaction for presenting the next fragment allowing for overriding of animations, bundles, etc.
     *
     * @return
     *      returns an instance of {@link PresentationTransaction} for the programmer to describe the next presentation
     */
    PresentationTransaction beginPresentation();

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

    void setTitle(String title);
    void setTitle(int resId);
    String getTitle();

    boolean isPortrait();
    boolean isTablet();
}
