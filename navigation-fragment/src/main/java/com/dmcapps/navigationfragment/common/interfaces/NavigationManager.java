package com.dmcapps.navigationfragment.common.interfaces;

import android.app.Activity;
import android.os.Bundle;

import com.dmcapps.navigationfragment.common.core.NavigationSettings;

/**
 * Created by dcarmo on 2016-09-29.
 */
public interface NavigationManager {

    void setStack(Stack stack);

    Stack getStack();

    void setConfig(Config config);

    Config getConfig();

    void setState(State state);

    State getState();

    void setLifecycle(Lifecycle lifecycle);

    Lifecycle getLifecycle();

    /**
     * Get the current Fragment Manager from the {@link NavigationManager}
     *
     * @return
     *      Returns the Child Fragment Manager of the current fragment
     */
    Object getNavChildFragmentManager();

    /**
     * Overrides the default present animations for all present actions on the fragment manager.
     *
     * @param
     *      animIn -> Present animation in
     * @param
     *      animOut -> Present animation out
     */
    void setDefaultPresentAnimations(int animIn, int animOut);

    /**
     * Overrides the default dismiss animations for all dismiss actions on the fragment manager.
     *
     * @param
     *      animIn -> Dismiss animation in
     * @param
     *      animOut -> Dismiss animation out
     */
    void setDefaultDismissAnimations(int animIn, int animOut);

    /**
     * Override the animation in and out of the next present, dismiss or clear stack call.
     *
     * @param
     *      animIn -> The resource of the new in animation.
     * @param
     *      animOut -> The resource of the new in animation.
     * @deprecated
     *      This call is being replaced with {@link NavigationSettings} being passed in with the push and pop functions.
     *      To be removed in 1.2.0.
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
    void pushFragment(Navigation navFragment);

    /**
     * Push a new Fragment onto the stack and presenting it to the screen
     * Uses default animation of slide in from right and slide out to left.
     *
     * @param
     *      navFragment -> The Fragment to show. It must be a Fragment that implements {@link Navigation}
     * @param
     *      navBundle -> The navigation bundle to add to the fragment being pushed
     *
     * @deprecated
     *      This function is being replaced with the {@link NavigationManager#pushFragment(Navigation, NavigationSettings)} method call.
     *      Allowing for more parameters to be passed in with the call.
     *      To be removed in 1.2.0.
     */
    @Deprecated
    void pushFragment(Navigation navFragment, Bundle navBundle);

    /**
     * Push a new Fragment onto the stack and presenting it to the screen
     * Uses default animation of slide in from right and slide out to left.
     *
     * @param
     *      navFragment -> The Fragment to show. It must be a Fragment that implements {@link Navigation}
     * @param
     *      settings -> The {@link NavigationSettings} to be applied to the transaction
     */
    void pushFragment(Navigation navFragment, NavigationSettings settings);

    /**
     * Pop the current fragment off the top of the stack and dismiss it.
     * Uses default animation of slide in from left and slide out to right animation.
     */
    void popFragment();

    /**
     * Pop the current fragment off the top of the stack and dismiss it.
     * Uses default animation of slide in from left and slide out to right animation.
     *
     * @param
     *      navBundle -> The navigation bundle to add to the fragment after the pop occurs
     */
    void popFragment(Bundle navBundle);

    /**
     * Adds the fragment to the current stack.
     *
     * @param
     *      navFragment -> The navigation fragment to be added to the stack.
     */
    void addToStack(Navigation navFragment);

    /**
     * Access the fragment that is on the top of the navigation stack.
     *
     * @return
     *      {@link Navigation} that is on the top of the stack.
     */
    Navigation getTopFragment();

    /**
     * Returns the fragment at the 0 index.
     *
     * @return
     *      {@link Navigation} at the 0 index if available.
     */
    Navigation getRootFragment();

    /**
     * Access the fragment at the given index of the navigation stack.
     *
     * @return
     *      {@link Navigation} that is on the top of the stack.
     */
    Navigation getFragmentAtIndex(int index);

    /**
     * Remove the {@link Navigation} that is on the top of the stack.
     *
     * @return
     *      true -> A {@link Navigation} has been removed
     *      false -> No fragment has been removed because we are at the bottom of the stack for that stack.
     */
    boolean onBackPressed();

    /**
     * Remove all fragments from the stack including the Root. The add the given {@link Navigation}
     * as the new root fragment. The definition of the Root Fragment is the Fragment at the min stack size position.
     *
     * @param
     *      navFragment -> The fragment that you would like as the new Root of the stack.
     */
    void replaceRootFragment(Navigation navFragment);

    /**
     * Remove all fragments from the stack until we reach the Root Fragment (the fragment at the min stack size)
     */
    void clearNavigationStackToRoot();

    /**
     * Remove all fragments up until the given position.
     *
     * @param
     *      index -> The index (0 based) that you would like to pop to.
     */
    void clearNavigationStackToIndex(int index);


    /**
     * Remove all fragments up until the given position.
     *
     * @param
     *      index -> The index (0 based) that you would like to pop to.
     * @param
     *      inclusive -> Include removing the given index.
     */
    void clearNavigationStackToIndex(int index, boolean inclusive);

    /**
     * Check if the current top fragment is the root fragment
     *
     * @return
     *      true -> Stack is currently at the root fragment
     *      false -> Stack is not at the root fragment
     */
    boolean isOnRootFragment();

    /**
     * Returns the {@link NavigationManager} stack size. A stack size of 0 represents empty.
     *
     * @return
     *      The current stack size.
     */
    int getCurrentStackSize();

    Activity getActivity();

    // ===============================
    // START DEVICE STATE METHODS
    // ===============================

    /**
     * Helper method for the current device state for orientation. Uses the layout used as the determining factor.
     *
     * @return
     *      true -> Current device is in portrait mode based on the layout used.
     *      false -> Current device is in landscape mode based on the layout used.
     */
    boolean isPortrait();

    /**
     * Helper method for the current device state for type. Uses the layout used as the determining factor.
     *
     * @return
     *      true -> Current device is a tablet based on the layout used.
     *      false -> Current device is a phone based on the layout used.
     */
    boolean isTablet();

}
