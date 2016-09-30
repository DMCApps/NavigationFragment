package com.dmcapps.navigationfragment.support.v7.manager.core;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dmcapps.navigationfragment.common.interfaces.Navigation;
import com.dmcapps.navigationfragment.common.interfaces.Config;
import com.dmcapps.navigationfragment.common.interfaces.NavigationManager;
import com.dmcapps.navigationfragment.common.micromanagers.ManagerConfig;
import com.dmcapps.navigationfragment.common.interfaces.Stack;
import com.dmcapps.navigationfragment.common.micromanagers.ManagerState;
import com.dmcapps.navigationfragment.common.interfaces.Lifecycle;
import com.dmcapps.navigationfragment.support.v7.manager.core.micromanagers.StackManager;
import com.dmcapps.navigationfragment.common.helpers.RetainedChildFragmentManagerFragment;
import com.dmcapps.navigationfragment.common.interfaces.State;

public class NavigationManagerFragment extends RetainedChildFragmentManagerFragment implements NavigationManager {
    // TODO: Animation making child disappear http://stackoverflow.com/a/23276145/845038
    private static final String TAG = NavigationManagerFragment.class.getSimpleName();

    private static final String KEY_MANAGER_CONFIG = "KEY_MANAGER_CONFIG";
    private static final String KEY_MANAGER_STATE = "KEY_MANAGER_STATE";
    private static final String KEY_MANAGER_STACK_MANAGER = "KEY_MANAGER_STACK_MANAGER";
    private static final String KEY_LIFECYCLE_MANAGER = "KEY_LIFECYCLE_MANAGER";

    private NavigationManagerFragmentListener mListener;

    protected Lifecycle mLifecycle;

    protected Config mConfig = new ManagerConfig();
    protected State mState = new ManagerState();
    protected Stack mStack = new StackManager();

    public interface NavigationManagerFragmentListener {
        void didPresentFragment();
        void didDismissFragment();
    }

    public NavigationManagerFragment() { }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            // This is not mandatory. Only if the user wants to listen for push and pop events.
            mListener = (NavigationManagerFragmentListener)context;
        }
        catch (ClassCastException classCastException) {
            Log.i(TAG, "Activity does not implement NavigationManagerFragmentListener. It is not required but may be helpful for listening to present and dismiss events.");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable(KEY_LIFECYCLE_MANAGER, mLifecycle);
        outState.putSerializable(KEY_MANAGER_CONFIG, mConfig);
        outState.putSerializable(KEY_MANAGER_STACK_MANAGER, mStack);
        outState.putSerializable(KEY_MANAGER_STATE, mState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            mLifecycle = (Lifecycle)savedInstanceState.getSerializable(KEY_LIFECYCLE_MANAGER);
            mConfig = (ManagerConfig)savedInstanceState.getSerializable(KEY_MANAGER_CONFIG);
            mStack = (StackManager)savedInstanceState.getSerializable(KEY_MANAGER_STACK_MANAGER);
            mState = (ManagerState)savedInstanceState.getSerializable(KEY_MANAGER_STATE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return mLifecycle.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLifecycle.onViewCreated(view, mState, mConfig);
    }

    @Override
    public void onResume() {
        super.onResume();
        mLifecycle.onResume(this, mState, mConfig);
    }

    @Override
    public void onPause() {
        super.onPause();
        mLifecycle.onPause(this, mState);
    }

    @Override
    public void setStack(Stack stack) {
        mStack = stack;
    }

    @Override
    public Stack getStack() {
        return mStack;
    }

    @Override
    public void setConfig(Config config) {
        mConfig = config;
    }

    @Override
    public Config getConfig() {
        return mConfig;
    }

    @Override
    public void setState(State state) {
        mState = state;
    }

    @Override
    public State getState() {
        return mState;
    }

    @Override
    public void setLifecycle(Lifecycle lifecycle) {
        mLifecycle = lifecycle;
    }

    @Override
    public Lifecycle getLifecycle() {
        return mLifecycle;
    }

    public void setDefaultPresentAnimations(int animIn, int animOut) {
        mConfig.setDefaultPresetAnim(animIn, animOut);
    }

    public void setDefaultDismissAnimations(int animIn, int animOut) {
        mConfig.setDefaultDismissAnim(animIn, animOut);
    }

    /**
     * Override the animation in and out of the next present, dismiss or clear stack call.
     *
     * @param
     *      animIn -> The resource of the new in animation.
     * @param
     *      animOut -> The resource of the new in animation.
     */
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
        mStack.pushFragment(this, mState, mConfig, navFragment);

        if (mListener != null) {
            mListener.didPresentFragment();
        }
    }

    /**
     * Push a new Fragment onto the stack and presenting it to the screen
     * Uses default animation of slide in from right and slide out to left.
     *
     * @param
     *      navFragment -> The Fragment to show. It must be a Fragment that implements {@link Navigation}
     */
    public void pushFragment(Navigation navFragment, Bundle navBundle) {
        mStack.pushFragment(this, mState, mConfig, navFragment, navBundle);

        if (mListener != null) {
            mListener.didPresentFragment();
        }
    }

    /**
     * Pop the current fragment off the top of the stack and dismiss it.
     * Uses default animation of slide in from left and slide out to right animation.
     */
    public void popFragment() {
        mStack.popFragment(this, mState, mConfig);

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
        mStack.popFragment(this, mState, mConfig, navBundle);

        if (mListener != null) {
            mListener.didDismissFragment();
        }
    }

    public void addFragmentToStack(Navigation navFragment) {
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
            return mStack.getFragmentAtIndex(this, mState, index);
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
        clearNavigationStackToPosition(mConfig.getMinStackSize() - 1);
        pushFragment(navFragment);
    }

    /**
     * Remove all fragments from the stack until we reach the Root Fragment (the fragment at the min stack size)
     */
    public void clearNavigationStackToRoot() {
        clearNavigationStackToPosition(mConfig.getMinStackSize());
    }

    /**
     * Remove all fragments up until the given position.
     *
     * @param
     *      stackPosition -> The position (0 indexed) that you would like to pop to.
     */
    protected void clearNavigationStackToPosition(int stackPosition) {
        mStack.clearNavigationStackToPosition(this, mState, stackPosition);
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
     * Returns the {@link NavigationManagerFragment} stack size. A stack size of 0 represents empty.
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

    /**
     * Helper method for the current device state for orientation. Uses the layout used as the determining factor.
     *
     * @return
     *      true -> Current device is in portrait mode based on the layout used.
     *      false -> Current device is in landscape mode based on the layout used.
     */
    public boolean isPortrait() {
        return mState.isPortrait();
    }

    /**
     * Helper method for the current device state for type. Uses the layout used as the determining factor.
     *
     * @return
     *      true -> Current device is a tablet based on the layout used.
     *      false -> Current device is a phone based on the layout used.
     */
    public boolean isTablet() {
        return mState.isTablet();
    }

    // ===============================
    // END DEVICE STATE METHODS
    // ===============================
}
