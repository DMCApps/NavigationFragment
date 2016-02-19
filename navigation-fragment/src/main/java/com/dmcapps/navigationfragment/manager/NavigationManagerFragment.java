package com.dmcapps.navigationfragment.manager;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.dmcapps.navigationfragment.R;
import com.dmcapps.navigationfragment.fragments.INavigationFragment;
import com.dmcapps.navigationfragment.fragments.NavigationFragment;
import com.dmcapps.navigationfragment.helper.RetainedChildFragmentManagerFragment;

import java.util.Stack;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class NavigationManagerFragment extends RetainedChildFragmentManagerFragment {
    // TODO: Animation making child disappear http://stackoverflow.com/a/23276145/845038
    // TODO: Move all onPause/Resume/attach/detach code here then make abstract methods for just the attach/detach portions
    private static final String TAG = NavigationManagerFragment.class.getSimpleName();

    protected static final int NO_ANIMATION = 0;

    private Stack<String> mFragmentTags;

    private NavigationManagerFragmentListener mListener;

    public interface NavigationManagerFragmentListener {
        void didPresentFragment();
        void didDismissFragment();
    }

    public NavigationManagerFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            // This is not mandatory. Only if the user wants to listen for push and pop events.
            mListener = (NavigationManagerFragmentListener)activity;
        }
        catch (ClassCastException classCastException) {
            classCastException.printStackTrace();
            Log.i(TAG, "Activity does not implement NavigationManagerFragmentListener. It is not required but may be helpful for displaying buttons for Master-Detail implementation.");
            // throw new ClassCastException("Activity does not implement NavigationManagerFragmentListener");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    /**
     * Push a new Fragment onto the stack and presenting it to the screen
     * Uses default animation of slide in from right and slide out to left.
     *
     * @param
     *      navFragment -> The Fragment to show. It must be a Fragment that implements {@link INavigationFragment}
     */
    public void pushFragment(INavigationFragment navFragment) {
        pushFragment(navFragment, R.anim.slide_in_from_right, R.anim.slide_out_to_left);
    }

    /**
     * Push a new Fragment onto the stack of {@link INavigationFragment} and present it using
     * the animations defined.
     *
     * @param
     *      navFragment -> The Fragment to show. It must be a Fragment that implements {@link INavigationFragment}
     * @param
     *      animationIn -> The animation of the fragment about to be shown.
     * @param
     *      animationOut -> The animation of the fragment that is being sent to the back.
     */
    public void pushFragment(INavigationFragment navFragment, int animationIn, int animationOut) {
        pushFragment(getMinStackSize(), getPushStackFrameId(), navFragment, animationIn, animationOut);
    }

    /**
     * Push a new Fragment onto the stack of {@link INavigationFragment} and present it using
     * the animations defined.
     * Detaching the top fragment if the stack is at or above stackSize
     * Adding the new fragment to the containerId
     *
     * @param
     *      detachStackSize -> The size of the stack required before detaching begins
     * @param
     *      containerId -> The id of the container the fragment should be attached to.
     * @param
     *      navFragment -> The Fragment to show. It must be a Fragment that implements {@link INavigationFragment}
     * @param
     *      animationIn -> The animation of the fragment about to be shown.
     * @param
     *      animationOut -> The animation of the fragment that is being sent to the back.
     */
    public void pushFragment(int detachStackSize, int containerId, INavigationFragment navFragment, int animationIn, int animationOut) {
        navFragment.setNavigationManager(this);
        FragmentManager childFragManager = getRetainedChildFragmentManager();
        FragmentTransaction childFragTrans = childFragManager.beginTransaction();

        // TODO: Better way to do this?
        if (getFragmentTags().size() >= detachStackSize) {
            childFragTrans.setCustomAnimations(animationIn, animationOut);
            Fragment topFrag = childFragManager.findFragmentByTag(getFragmentTags().peek());
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
        popFragment(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
    }

    /**
     * Pop the current fragment off the top of the stack and dismiss it using
     * the animations defined.
     *
     * @param
     *      animationIn -> The animation of the fragment about to be shown.
     * @param
     *      animationOut -> The animation of the fragment that is being dismissed.
     */
    public void popFragment(int animationIn, int animationOut) {
        popFragment(getMinStackSize(), true, animationIn, animationOut);
    }

    /**
     * Pop the current fragment off the top of the stack given it is above
     * the passed in stackSize. Dismiss it using the animations defined.
     *
     * @param
     *      stackSize -> The stack size that the fragment should handle
     * @param
     *      shouldAttach -> After completing the pop should the previous fragment be attached
     * @param
     *      animationIn -> The animation of the fragment about to be shown.
     * @param
     *      animationOut -> The animation of the fragment that is being dismissed.
     */
    protected void popFragment(int stackSize, boolean shouldAttach, int animationIn, int animationOut) {
        if (getFragmentTags().size() > stackSize) {
            FragmentManager childFragManager = getRetainedChildFragmentManager();
            FragmentTransaction childFragTrans = childFragManager.beginTransaction();
            childFragTrans.setCustomAnimations(animationIn, animationOut);
            childFragTrans.remove(childFragManager.findFragmentByTag(getFragmentTags().pop()));
            // TODO: Clean up this logic
            if ((shouldAttach || stackSize == getFragmentTags().size()) && getFragmentTags().size() > 0) {
                childFragTrans.attach(childFragManager.findFragmentByTag(getFragmentTags().peek()));
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

    /**
     * Access the fragment that is on the top of the navigation stack.
     *
     * @return
     *      {@link INavigationFragment} that is on the top of the stack.
     */
    public INavigationFragment topFragment() {
        return (INavigationFragment)getRetainedChildFragmentManager().findFragmentByTag(getFragmentTags().peek());
    }

    /**
     * Remove the {@link INavigationFragment} that is on the top of the stack.
     *
     * @return
     *      true -> A {@link INavigationFragment} has been removed
     *      false -> No fragment has been removed because we are at the bottom of the stack for that stack.
     */
    public boolean onBackPressed() {
        if (getFragmentTags().size() > getMinStackSize()) {
            popFragment();
            return true;
        }

        return false;
    }

    public void clearNavigationStackToRoot() {
        clearNavigationStackToPosition(getMinStackSize(), false);
    }

    public void replaceRootFragment(INavigationFragment navFragment) {
        clearNavigationStackToPosition(getMinStackSize() - 1, false);
        pushFragment(navFragment, NO_ANIMATION, NO_ANIMATION);
    }

    public boolean isOnRootFragment() {
        return getFragmentTags().size() == getMinStackSize();
    }

    public void setTitle(String title) {
        if (getActivity() != null) {
            if (getActivity() instanceof AppCompatActivity) {
                getActivity().setTitle(title);
            }
            else {
                Log.e(TAG, "Unable to set title, is not ActionBarActivity or AppCompatActivity.");
            }
        }
        else {
            Log.e(TAG, "Unable to set title, Activity is null");
        }
    }

    public void setTitle(int resId) {
        if (getActivity() != null) {
            if (getActivity() instanceof AppCompatActivity) {
                getActivity().setTitle(resId);
            }
            else {
                Log.e(TAG, "Unable to set title, is not ActionBarActivity or AppCompatActivity.");
            }
        }
        else {
            Log.e(TAG, "Unable to set title, Activity is null");
        }
    }

    protected abstract int getPushStackFrameId();

    protected abstract int getMinStackSize();

    protected void clearNavigationStackToPosition(int stackPosition, boolean shouldAttach) {
        while (getFragmentTags().size() > stackPosition) {
            popFragment(stackPosition, shouldAttach, NO_ANIMATION, NO_ANIMATION);
        }
    }

    protected void addFragmentToStack(INavigationFragment navFragment) {
        getFragmentTags().add(navFragment.getNavTag());
    }

    protected Stack<String> getFragmentTags() {
        if (mFragmentTags == null) {
            mFragmentTags = new Stack<>();
        }
        return mFragmentTags;
    }
}
