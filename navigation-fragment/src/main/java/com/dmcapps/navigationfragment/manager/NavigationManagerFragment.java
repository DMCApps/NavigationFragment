package com.dmcapps.navigationfragment.manager;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.dmcapps.navigationfragment.R;
import com.dmcapps.navigationfragment.fragments.INavigationFragment;
import com.dmcapps.navigationfragment.fragments.NavigationFragment;
import com.dmcapps.navigationfragment.helper.RetainedChildFragmentManagerFragment;
import com.dmcapps.navigationfragment.manager.micromanagers.ManagerConfig;
import com.dmcapps.navigationfragment.manager.micromanagers.ManagerState;
import com.dmcapps.navigationfragment.manager.micromanagers.actionbar.ActionBarManager;
import com.dmcapps.navigationfragment.manager.micromanagers.lifecycle.ILifecycleManager;

import java.io.Serializable;
import java.util.Stack;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class NavigationManagerFragment extends RetainedChildFragmentManagerFragment implements Serializable {
    // TODO: Animation making child disappear http://stackoverflow.com/a/23276145/845038
    private static final String TAG = NavigationManagerFragment.class.getSimpleName();

    private NavigationManagerFragmentListener mListener;

    protected ILifecycleManager mLifecycleManager;

    protected ManagerConfig mConfig = new ManagerConfig();
    protected ManagerState mState = new ManagerState();

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
            Log.i(TAG, "Activity does not implement NavigationManagerFragmentListener. It is not required but may be helpful for displaying buttons for Master-Detail implementation.");
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        //((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mLifecycleManager.onResume(this, mState, mConfig);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return mLifecycleManager.onCreateView(inflater, container, savedInstanceState);
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
        pushFragment(mConfig.pushContainerId, navFragment, mConfig.presentAnimationIn, mConfig.presentAnimationOut);
    }

    /**
     * Push a new Fragment onto the stack of {@link INavigationFragment} and present it using
     * the animations defined.
     *
     * @param
     *      navFragment -> The Fragment to show. It must be a Fragment that implements {@link INavigationFragment}
     * @param
     *      animIn -> The animation of the fragment about to be shown.
     * @param
     *      animOut -> The animation of the fragment that is being sent to the back.
     */
    public void pushFragment(INavigationFragment navFragment, int animIn, int animOut) {
        pushFragment(mConfig.pushContainerId, navFragment, animIn, animOut);
    }

    /**
     * Push a new Fragment onto the stack of {@link INavigationFragment} and present it using
     * the animations defined.
     * Detaching the top fragment if the stack is at or above minStackSize
     * Adding the new fragment to the containerId
     *
     * @param
     *      containerId -> The id of the container the fragment should be attached to.
     * @param
     *      navFragment -> The Fragment to show. It must be a Fragment that implements {@link INavigationFragment}
     * @param
     *      animIn -> The animation of the fragment about to be shown.
     * @param
     *      animOut -> The animation of the fragment that is being sent to the back.
     */
    public void pushFragment(int containerId, INavigationFragment navFragment, int animIn, int animOut) {
        navFragment.setNavigationManager(this);
        FragmentManager childFragManager = getRetainedChildFragmentManager();
        FragmentTransaction childFragTrans = childFragManager.beginTransaction();

        if (mState.fragmentTagStack.size() >= mConfig.minStackSize) {
            childFragTrans.setCustomAnimations(animIn, animOut);
            Fragment topFrag = childFragManager.findFragmentByTag(mState.fragmentTagStack.peek());
            // Detach the top fragment such that it is kept in the stack and can be shown again without lose of state.
            childFragTrans.detach(topFrag);
        }

        // Add in the new fragment that we are presenting and add it's navigation tag to the stack.
        childFragTrans.add(containerId, (Fragment)navFragment, navFragment.getNavTag());
        addFragmentToStack(navFragment);

        childFragTrans.commit();

        if (mListener != null) {
            mListener.didPresentFragment();
        }
    }

    /**
     * Pop the current fragment off the top of the stack and dismiss it.
     * Uses default animation of slide in from left and slide out to right.
     */
    public void popFragment() {
        popFragment(mConfig.minStackSize, mConfig.dismissAnimationIn, mConfig.dismissAnimationOut);
    }

    /**
     * Pop the current fragment off the top of the stack and dismiss it using
     * the animations defined.
     *
     * @param
     *      animIn -> The animation of the fragment about to be shown.
     * @param
     *      animOut -> The animation of the fragment that is being dismissed.
     */
    public void popFragment(int animIn, int animOut) {
        popFragment(mConfig.minStackSize, animIn, animOut);
    }

    /**
     * Pop the current fragment off the top of the stack given it is above
     * the passed in stackSize. Dismiss it using the animations defined.
     *
     * @param
     *      stackSize -> The stack size that the fragment should handle
     * @param
     *      animIn -> The animation of the fragment about to be shown.
     * @param
     *      animOut -> The animation of the fragment that is being dismissed.
     */
    protected void popFragment(int stackSize, int animIn, int animOut) {
        if (mState.fragmentTagStack.size() > stackSize) {
            FragmentManager childFragManager = getRetainedChildFragmentManager();
            FragmentTransaction childFragTrans = childFragManager.beginTransaction();
            childFragTrans.setCustomAnimations(animIn, animOut);
            childFragTrans.remove(childFragManager.findFragmentByTag(mState.fragmentTagStack.pop()));

            if (mState.fragmentTagStack.size() > 0) {
                childFragTrans.attach(childFragManager.findFragmentByTag(mState.fragmentTagStack.peek()));
            }

            childFragTrans.commit();
        }
        else {
            // TODO: Nothing above stack size to dismiss ... Exception? Call activity onBackPressed()? what to do?
            // TODO: Dismiss root and self?
            getActivity().onBackPressed();
        }

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
    public INavigationFragment topFragment() {
        return (INavigationFragment)getRetainedChildFragmentManager().findFragmentByTag(mState.fragmentTagStack.peek());
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
        FragmentManager childFragManager = getRetainedChildFragmentManager();
        FragmentTransaction childFragTrans = childFragManager.beginTransaction();
        childFragTrans.setCustomAnimations(ManagerConfig.NO_ANIMATION, ManagerConfig.NO_ANIMATION);

        while (mState.fragmentTagStack.size() > stackPosition) {
            childFragTrans.remove(childFragManager.findFragmentByTag(mState.fragmentTagStack.pop()));
        }

        if (mState.fragmentTagStack.size() > 0) {
            childFragTrans.attach(childFragManager.findFragmentByTag(mState.fragmentTagStack.peek()));
        }

        childFragTrans.commit();
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
