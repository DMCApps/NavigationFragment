package com.dmcapps.navigationfragment.common.interfaces;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by dcarmo on 2016-09-29.
 */
public interface NavigationManager<FragmentType> {

    void setStack(Stack stack);

    Stack getStack();

    void setConfig(Config config);

    Config getConfig();

    void setState(State state);

    State getState();

    void setLifecycle(Lifecycle lifecycle);

    Lifecycle getLifecycle();

    Object getNavigationFragmentManager();

    Activity getActivity();

    void setDefaultPresentAnimations(int animIn, int animOut);

    void setDefaultDismissAnimations(int animIn, int animOut);

    /**
     * Override the animation in and out of the next present, dismiss or clear stack call.
     *
     * @param
     *      animIn -> The resource of the new in animation.
     * @param
     *      animOut -> The resource of the new in animation.
     */
    void overrideNextAnimation(int animIn, int animOut);

    /**
     * Push a new Fragment onto the stack and presenting it to the screen
     * Uses default animation of slide in from right and slide out to left.
     *
     * @param
     *      navFragment -> The Fragment to show. It must be a Fragment that implements {@link FragmentType}
     */
    void pushFragment(FragmentType navFragment);

    /**
     * Push a new Fragment onto the stack and presenting it to the screen
     * Uses default animation of slide in from right and slide out to left.
     *
     * @param
     *      navFragment -> The Fragment to show. It must be a Fragment that implements {@link FragmentType}
     */
    void pushFragment(FragmentType navFragment, Bundle navBundle);

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
     * Adds the fragment to the given stack.
     *
     * @param
     *      navFragment -> The navigation fragment to be added to the stack.
     */
    void addToStack(FragmentType navFragment);

    /**
     * Access the fragment that is on the top of the navigation stack.
     *
     * @return
     *      {@link FragmentType} that is on the top of the stack.
     */
    FragmentType getTopFragment();

    /**
     * Returns the fragment at the 0 index.
     *
     * @return
     *      {@link FragmentType} at the 0 index if available.
     */
    FragmentType getRootFragment();

    /**
     * Access the fragment at the given index of the navigation stack.
     *
     * @return
     *      {@link FragmentType} that is on the top of the stack.
     */
    FragmentType getFragmentAtIndex(int index);

    /**
     * Remove the {@link FragmentType} that is on the top of the stack.
     *
     * @return
     *      true -> A {@link FragmentType} has been removed
     *      false -> No fragment has been removed because we are at the bottom of the stack for that stack.
     */
    boolean onBackPressed();

    /**
     * Remove all fragments from the stack including the Root. The add the given {@link FragmentType}
     * as the new root fragment. The definition of the Root Fragment is the Fragment at the min stack size position.
     *
     * @param
     *      navFragment -> The fragment that you would like as the new Root of the stack.
     */
    void replaceRootFragment(FragmentType navFragment);

    /**
     * Remove all fragments from the stack until we reach the Root Fragment (the fragment at the min stack size)
     */
    void clearNavigationStackToRoot();

    /**
     * Remove all fragments up until the given position.
     *
     * @param
     *      stackPosition -> The position (0 indexed) that you would like to pop to.
     */
    void clearNavigationStackToPosition(int stackPosition);

    /**
     * Check if the current top fragment is the root fragment
     *
     * @return
     *      true -> Stack is currently at the root fragment
     *      false -> Stack is not at the root fragment
     */
    boolean isOnRootFragment();

    /**
     * Returns the {@link NavigationManagerFragment} stack size. A stack size of 0 represents empty.
     *
     * @return
     *      The current stack size.
     */
    int getCurrentStackSize();

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
