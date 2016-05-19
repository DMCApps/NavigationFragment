package com.dmcapps.navigationfragment.manager;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dmcapps.navigationfragment.fragments.INavigationFragment;
import com.dmcapps.navigationfragment.manager.micromanagers.ManagerConfig;
import com.dmcapps.navigationfragment.manager.micromanagers.ManagerState;
import com.dmcapps.navigationfragment.manager.micromanagers.lifecycle.ILifecycleManager;
import com.dmcapps.navigationfragment.manager.micromanagers.stack.StackManager;
import com.dmcapps.navigationfragment.helper.RetainedChildFragmentManagerFragment;

import java.io.Serializable;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class NavigationManagerFragment extends RetainedChildFragmentManagerFragment implements Serializable {
    // TODO: Animation making child disappear http://stackoverflow.com/a/23276145/845038
    private static final String TAG = NavigationManagerFragment.class.getSimpleName();

    private static final String KEY_MANAGER_CONFIG = "KEY_MANAGER_CONFIG";
    private static final String KEY_MANAGER_STATE = "KEY_MANAGER_STATE";
    private static final String KEY_MANAGER_STACK_MANAGER = "KEY_MANAGER_STACK_MANAGER";
    private static final String KEY_LIFECYCLE_MANAGER = "KEY_LIFECYCLE_MANAGER";

    private NavigationManagerFragmentListener mListener;

    protected ILifecycleManager mLifecycleManager;

    protected ManagerConfig mConfig = new ManagerConfig();
    protected ManagerState mState = new ManagerState();
    protected StackManager mStackManager = new StackManager();

    public interface NavigationManagerFragmentListener {
        void didPresentFragment();
        void didDismissFragment();
    }

    public NavigationManagerFragment() {
    }

    public void setDefaultPresentAnimations(int animIn, int animOut) {
        mConfig.presentAnimationIn = animIn;
        mConfig.presentAnimationOut = animOut;
    }

    public void setDefaultDismissAnimations(int animIn, int animOut) {
        mConfig.dismissAnimationIn = animIn;
        mConfig.dismissAnimationOut = animOut;
    }

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

        outState.putSerializable(KEY_LIFECYCLE_MANAGER, mLifecycleManager);
        outState.putSerializable(KEY_MANAGER_CONFIG, mConfig);
        outState.putSerializable(KEY_MANAGER_STACK_MANAGER, mStackManager);
        outState.putSerializable(KEY_MANAGER_STATE, mState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            mLifecycleManager = (ILifecycleManager)savedInstanceState.getSerializable(KEY_LIFECYCLE_MANAGER);
            mConfig = (ManagerConfig)savedInstanceState.getSerializable(KEY_MANAGER_CONFIG);
            mStackManager = (StackManager)savedInstanceState.getSerializable(KEY_MANAGER_STACK_MANAGER);
            mState = (ManagerState)savedInstanceState.getSerializable(KEY_MANAGER_STATE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return mLifecycleManager.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLifecycleManager.onViewCreated(view, mState, mConfig);
    }

    @Override
    public void onResume() {
        super.onResume();

        //((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mLifecycleManager.onResume(this, mState, mConfig);
    }

    @Override
    public void onPause() {
        super.onPause();
        mLifecycleManager.onPause(this, mState);
    }

    /*
     * This needs to be moved to the NavigationFragment to automatically handle the back button.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
     */

    /**
     * Push a new Fragment onto the stack and presenting it to the screen
     * Uses default animation of slide in from right and slide out to left.
     *
     * @param
     *      navFragment -> The Fragment to show. It must be a Fragment that implements {@link INavigationFragment}
     */
    public void pushFragment(INavigationFragment navFragment) {
        pushFragment(navFragment, mConfig.presentAnimationIn, mConfig.presentAnimationOut);
    }

    /**
     * Push a new Fragment onto the stack of {@link INavigationFragment} and present it using
     * the animations defined.
     * Detaching the top fragment if the stack is at or above minStackSize
     * Adding the new fragment to the containerId
     *
     * @param
     *      navFragment -> The Fragment to show. It must be a Fragment that implements {@link INavigationFragment}
     * @param
     *      animIn -> The animation of the fragment about to be shown.
     * @param
     *      animOut -> The animation of the fragment that is being sent to the back.
     */
    public void pushFragment(INavigationFragment navFragment, int animIn, int animOut) {
        mStackManager.pushFragment(this, mState, mConfig, navFragment, animIn, animOut);

        if (mListener != null) {
            mListener.didPresentFragment();
        }
    }

    /**
     * Pop the current fragment off the top of the stack and dismiss it.
     * Uses default animation of slide in from left and slide out to right animation.
     */
    public void popFragment() {
        popFragment(mConfig.dismissAnimationIn, mConfig.dismissAnimationOut);
    }

    /**
     * Pop the current fragment off the top of the stack given it is above
     * the passed in stackSize. Dismiss it using the animations defined.
     *
     * @param
     *      animIn -> The animation of the fragment about to be shown.
     * @param
     *      animOut -> The animation of the fragment that is being dismissed.
     */
    public void popFragment(int animIn, int animOut) {
        mStackManager.popFragment(this, mState, mConfig, animIn, animOut);

        if (mListener != null) {
            mListener.didDismissFragment();
        }
    }

    public void addFragmentToStack(INavigationFragment navFragment) {
        mState.fragmentTagStack.add(navFragment.getNavTag());
    }

    /**
     * Access the fragment that is on the top of the navigation stack.
     *
     * @return
     *      {@link INavigationFragment} that is on the top of the stack.
     */
    public INavigationFragment getTopFragment() {
        if (mState.fragmentTagStack.size() > 0) {
            return getFragmentAtIndex(mState.fragmentTagStack.size() - 1);
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
     *      {@link INavigationFragment} at the 0 index if available.
     */
    public INavigationFragment getRootFragment() {
        return getFragmentAtIndex(0);
    }

    /**
     * Access the fragment at the given index of the navigation stack.
     *
     * @return
     *      {@link INavigationFragment} that is on the top of the stack.
     */
    public INavigationFragment getFragmentAtIndex(int index) {
        if (mState.fragmentTagStack.size() > index) {
            return mStackManager.getFragmentAtIndex(this, mState, index);
        }
        else {
            Log.e(TAG, "No fragment at that position in the navigation stack, returning null. (Stack size: " + mState.fragmentTagStack.size() + ". Index attempted: " + index + ".");
            return null;
        }
    }

    /**
     * Remove the {@link INavigationFragment} that is on the top of the stack.
     *
     * @return
     *      true -> A {@link INavigationFragment} has been removed
     *      false -> No fragment has been removed because we are at the bottom of the stack for that stack.
     */
    public boolean onBackPressed() {
        if (mState.fragmentTagStack.size() > mConfig.minStackSize) {
            popFragment();
            return true;
        }

        return false;
    }

    /**
     * Remove all fragments from the stack including the Root. The add the given {@link INavigationFragment}
     * as the new root fragment. The definition of the Root Fragment is the Fragment at the min stack size position.
     *
     * @param
     *      navFragment -> The fragment that you would like as the new Root of the stack.
     */
    public void replaceRootFragment(INavigationFragment navFragment) {
        clearNavigationStackToPosition(mConfig.minStackSize - 1);
        pushFragment(navFragment, ManagerConfig.NO_ANIMATION, ManagerConfig.NO_ANIMATION);
    }

    /**
     * Remove all fragments from the stack until we reach the Root Fragment (the fragment at the min stack size)
     */
    public void clearNavigationStackToRoot() {
        clearNavigationStackToPosition(mConfig.minStackSize);
    }

    /**
     * Remove all fragments up until the given position.
     *
     * @param
     *      stackPosition -> The position (0 indexed) that you would like to pop to.
     */
    protected void clearNavigationStackToPosition(int stackPosition) {
        mStackManager.clearNavigationStackToPosition(this, mState, stackPosition);
    }

    /**
     * Check if the current top fragment is the root fragment
     *
     * @return
     *      true -> Stack is currently at the root fragment
     *      false -> Stack is not at the root fragment
     */
    public boolean isOnRootFragment() {
        return mState.fragmentTagStack.size() == mConfig.minStackSize;
    }

    /**
     * Returns the {@link NavigationManagerFragment} stack size. A stack size of 0 represents empty.
     *
     * @return
     *      The current stack size.
     */
    public int getCurrentStackSize() {
        return mState.fragmentTagStack.size();
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
        return mState.isPortrait;
    }

    /**
     * Helper method for the current device state for type. Uses the layout used as the determining factor.
     *
     * @return
     *      true -> Current device is a tablet based on the layout used.
     *      false -> Current device is a phone based on the layout used.
     */
    public boolean isTablet() {
        return mState.isTablet;
    }

    // ===============================
    // END DEVICE STATE METHODS
    // ===============================
}
