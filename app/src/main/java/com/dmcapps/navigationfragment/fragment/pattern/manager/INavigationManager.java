package com.dmcapps.navigationfragment.fragment.pattern.manager;

import com.dmcapps.navigationfragment.fragment.pattern.NavigationFragment;

public interface INavigationManager {

    /**
     * Push a new Fragment onto the stack and presenting it to the screen
     *
     * @param
     *      navFragment -> The Fragment to show. It must extend {@link NavigationFragment}
     */
    void pushFragment(NavigationFragment navFragment);

    /**
     * Push a new Fragment onto the stack {@link NavigationFragment} and present it using
     * the animations defined.
     *
     * @param
     *      navFragment -> The Fragment to show. It must extend {@link NavigationFragment}
     * @param
     *      animationIn -> The animation of the fragment about to be shown.
     * @param
     *      animationOut -> The animation of the fragment that is being sent to the back.
     */
    void pushFragment(NavigationFragment navFragment, int animationIn, int animationOut);

    /**
     * Pop the current fragment off the top of the stack and dismiss it.
     */
    void popFragment();

    /**
     * Pop the current fragment off the top of the stack and dismiss it using
     * the animations defined.
     *
     * @param
     *      animationIn -> The animation of the fragment about to be shown.
     * @param
     *      animationOut -> The animation of the fragment that is being dismissed.
     */
    void popFragment(int animationIn, int animationOut);

    /**
     * Access the fragment that is on the top of the navigation stack.
     *
     * @return
     *      {@link NavigationFragment} that is on the top of the stack.
     */
    NavigationFragment topFragment();

    /**
     * Remove the {@link NavigationFragment} that is on the top of the stack.
     *
     * @return
     *      true -> A {@link NavigationFragment} has been removed
     *      false -> No fragment has been removed because we are at the bottom of the stack for that stack.
     */
    boolean onBackPressed();
}
