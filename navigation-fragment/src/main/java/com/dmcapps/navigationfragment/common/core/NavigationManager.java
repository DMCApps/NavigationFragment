package com.dmcapps.navigationfragment.common.core;

import android.os.Bundle;
import android.util.Log;

import com.dmcapps.navigationfragment.common.interfaces.Config;
import com.dmcapps.navigationfragment.common.interfaces.Lifecycle;
import com.dmcapps.navigationfragment.common.interfaces.Navigation;
import com.dmcapps.navigationfragment.common.interfaces.NavigationManagerContainer;
import com.dmcapps.navigationfragment.common.interfaces.NavigationManagerListener;
import com.dmcapps.navigationfragment.common.interfaces.Stack;
import com.dmcapps.navigationfragment.common.interfaces.State;

import java.io.Serializable;
import java.lang.ref.WeakReference;

/**
 * Created by dcarmo on 2016-12-18.
 */

public class NavigationManager implements Serializable {
    private static final String TAG = NavigationManager.class.getSimpleName();

    private transient NavigationManagerListener mListener;
    private transient WeakReference<NavigationManagerContainer> mContainer;

    private Lifecycle mLifecycle;
    private NavigationConfig mNavigationConfig;
    private Config mConfig;
    private State mState;
    private Stack mStack;

    public NavigationManager() { }

    public void setNavigationListener(NavigationManagerListener listener) {
        mListener = listener;
    }

    public void setContainer(NavigationManagerContainer container) {
        mContainer = new WeakReference<>(container);
    }

    public NavigationManagerContainer getContainer() {
        if (mContainer == null || mContainer.get() == null) {
            return null;
        }
        return mContainer.get();
    }

    public void setStack(Stack stack) {
        mStack = stack;
    }

    public Stack getStack() {
        return mStack;
    }

    public void setConfig(Config config) {
        mConfig = config;
        NavigationSettings.setDefaultConfig(config);
    }

    public Config getConfig() {
        return mConfig;
    }

    public void setState(State state) {
        mState = state;
    }

    public State getState() {
        return mState;
    }

    public void setLifecycle(Lifecycle lifecycle) {
        mLifecycle = lifecycle;
    }

    public Lifecycle getLifecycle() {
        return mLifecycle;
    }

    /**
     * Overrides the default present animations for all present actions on the fragment manager.
     *
     * @param
     *      animIn -> Present animation in
     * @param
     *      animOut -> Present animation out
     */
    public void setDefaultPresentAnimations(int animIn, int animOut) {
        mConfig.setDefaultPresetAnim(animIn, animOut);
        // TODO: Remove this it's ugly. See notes in NavigationSettings for ideas
        NavigationSettings.setDefaultConfig(mConfig);
    }

    /**
     * Overrides the default dismiss animations for all dismiss actions on the fragment manager.
     *
     * @param
     *      animIn -> Dismiss animation in
     * @param
     *      animOut -> Dismiss animation out
     */
    public void setDefaultDismissAnimations(int animIn, int animOut) {
        mConfig.setDefaultDismissAnim(animIn, animOut);
        // TODO: Remove this it's ugly. See notes in NavigationSettings for ideas
        NavigationSettings.setDefaultConfig(mConfig);
    }

    /**
     * Override the animation in and out of the next present, dismiss or clear stack call.
     *
     * @param
     *      animIn -> The resource of the new in animation.
     * @param
     *      animOut -> The resource of the new in animation.
     * @deprecated
     *      This call is being replaced with {@link NavigationSettings} being passed in with the push function.
     *      To be removed in 1.2.0.
     */
    @Deprecated
    public void overrideNextAnimation(int animIn, int animOut) {
        mConfig.setNextAnim(animIn, animOut);
    }

    /**
     * Push a new Fragment onto the stack and presenting it to the screen
     * Uses default animation of slide in from right and slide out to left.
     *
     * @param
     *      navFragment -> The Fragment to show. It must be a Fragment that implements {@link Navigation}
     */
    public void pushFragment(Navigation navFragment) {
        pushFragment(navFragment, new NavigationSettings.Builder().build());
    }

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
    public void pushFragment(Navigation navFragment, Bundle navBundle) {
        pushFragment(navFragment, new NavigationSettings.Builder().setNavBundle(navBundle).build());
    }

    /**
     * Push a new Fragment onto the stack and presenting it to the screen
     * Uses default animation of slide in from right and slide out to left.
     *
     * @param
     *      navFragment -> The Fragment to show. It must be a Fragment that implements {@link Navigation}
     * @param
     *      settings -> The {@link NavigationSettings} to be applied to the transaction
     */
    public void pushFragment(Navigation navFragment, NavigationSettings settings) {
        mStack.pushFragment(this, navFragment, settings);

        if (mListener != null) {
            mListener.didPresentFragment();
        }
    }

    /**
     * Pop the current fragment off the top of the stack and dismiss it.
     * Uses default animation of slide in from left and slide out to right animation.
     */
    public void popFragment() {
        mStack.popFragment(this, null);

        if (mListener != null) {
            mListener.didDismissFragment();
        }
    }

    /**
     * Pop the current fragment off the top of the stack and dismiss it.
     * Uses default animation of slide in from left and slide out to right animation.
     *
     * @param
     *      navBundle -> The navigation bundle to add to the fragment after the pop occurs
     */
    public void popFragment(Bundle navBundle) {
        mStack.popFragment(this, new NavigationSettings.Builder().setNavBundle(navBundle).build());

        if (mListener != null) {
            mListener.didDismissFragment();
        }
    }

    /**
     * Adds the fragment to the current stack.
     *
     * @param
     *      navFragment -> The navigation fragment to be added to the stack.
     */
    public void addToStack(Navigation navFragment) {
        mState.getStack().add(navFragment.getNavTag());
    }

    /**
     * Access the fragment that is on the top of the navigation stack.
     *
     * @return
     *      {@link Navigation} that is on the top of the stack.
     */
    public Navigation getTopFragment() {
        if (mState.getStack().size() > 0) {
            return getFragmentAtIndex(mState.getStack().size() - 1);
        }
        else {
            Log.e(TAG, "No fragments in the navigation stack, returning null.");
            return null;
        }
    }

    /**
     * Returns the fragment at the 0 index.
     *
     * @return
     *      {@link Navigation} at the 0 index if available.
     */
    public Navigation getRootFragment() {
        return getFragmentAtIndex(0);
    }

    /**
     * Access the fragment at the given index of the navigation stack.
     *
     * @return
     *      {@link Navigation} that is on the top of the stack.
     */
    public Navigation getFragmentAtIndex(int index) {
        if (mState.getStack().size() > index) {
            return mStack.getFragmentAtIndex(this, index);
        }
        else {
            Log.e(TAG, "No fragment at that position in the navigation stack, returning null. (Stack size: " + mState.getStack().size() + ". Index attempted: " + index + ".");
            return null;
        }
    }

    /**
     * Remove the {@link Navigation} that is on the top of the stack.
     *
     * @return
     *      true -> A {@link Navigation} has been removed
     *      false -> No fragment has been removed because we are at the bottom of the stack for that stack.
     */
    public boolean onBackPressed() {
        if (mState.getStack().size() > mConfig.getMinStackSize()) {
            popFragment();
            return true;
        }

        return false;
    }

    /**
     * Remove all fragments from the stack including the Root. The add the given {@link Navigation}
     * as the new root fragment. The definition of the Root Fragment is the Fragment at the min stack size position.
     *
     * @param
     *      navFragment -> The fragment that you would like as the new Root of the stack.
     */
    public void replaceRootFragment(Navigation navFragment) {
        clearNavigationStackToIndex(mConfig.getMinStackSize() - 1, true);
        pushFragment(navFragment);
    }

    /**
     * Remove all fragments from the stack until we reach the Root Fragment (the fragment at the min stack size)
     */
    public void clearNavigationStackToRoot() {
        clearNavigationStackToIndex(mConfig.getMinStackSize());
    }

    /**
     * Remove all fragments up until the given position.
     *
     * @param
     *      stackPosition -> The position (0 indexed) that you would like to pop to.
     */
    public void clearNavigationStackToIndex(int stackPosition) {
        clearNavigationStackToIndex(stackPosition, false);
    }

    /**
     * Remove all fragments up until the given position.
     *
     * @param
     *      index -> The index (0 based) that you would like to pop to.
     */
    public void clearNavigationStackToIndex(int index, boolean inclusive) {
        mStack.clearNavigationStackToIndex(this, index, inclusive);
    }

    /**
     * Check if the current top fragment is the root fragment
     *
     * @return
     *      true -> Stack is currently at the root fragment
     *      false -> Stack is not at the root fragment
     */
    public boolean isOnRootFragment() {
        return mState.getStack().size() == mConfig.getMinStackSize();
    }

    /**
     * Returns the {@link NavigationManager} stack size. A stack size of 0 represents empty.
     *
     * @return
     *      The current stack size.
     */
    public int getCurrentStackSize() {
        return mState.getStack().size();
    }

    // ===============================
    // START DEVICE STATE METHODS
    // ===============================

    public boolean isPortrait() {
        return mState.isPortrait();
    }

    public boolean isTablet() {
        return mState.isTablet();
    }

    // ===============================
    // END DEVICE STATE METHODS
    // ===============================

}
