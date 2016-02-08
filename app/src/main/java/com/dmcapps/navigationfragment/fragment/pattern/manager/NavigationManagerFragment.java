package com.dmcapps.navigationfragment.fragment.pattern.manager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.dmcapps.navigationfragment.R;
import com.dmcapps.navigationfragment.fragment.pattern.NavigationFragment;
import com.dmcapps.navigationfragment.fragment.pattern.helper.RetainedChildFragmentManagerFragment;

import java.util.Stack;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class  NavigationManagerFragment extends RetainedChildFragmentManagerFragment {
    protected static final int NO_ANIMATION = 0;

    private Stack<String> mFragmentTags;

    public NavigationManagerFragment() {
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
     *
     * @param
     *      navFragment -> The Fragment to show. It must extend {@link NavigationFragment}
     */
    public void pushFragment(NavigationFragment navFragment) {
        pushFragment(navFragment, R.anim.slide_in_from_right, R.anim.slide_out_to_left);
    }

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
    public abstract void pushFragment(NavigationFragment navFragment, int animationIn, int animationOut);

    public void pushFragmentDetachAboveInContainer(int stackSize, int containerId, NavigationFragment navFragment, int animationIn, int animationOut) {
        navFragment.setNavigationManager(this);
        FragmentManager childFragManager = getRetainedChildFragmentManager();
        FragmentTransaction childFragTrans = childFragManager.beginTransaction();

        // TODO: Better way to do this?
        if (getFragmentTags().size() > stackSize) {
            childFragTrans.setCustomAnimations(animationIn, animationOut);
            Fragment topFrag = childFragManager.findFragmentByTag(getFragmentTags().peek());
            // Detach the top fragment such that it is kept in the stack and can be shown again without lose of state.
            childFragTrans.detach(topFrag);
        }

        // Add in the new fragment that we are presenting and add it's navigation tag to the stack.
        childFragTrans.add(containerId, navFragment, navFragment.getNavTag());
        addFragmentToStack(navFragment);

        childFragTrans.commit();
    }

    /**
     * Pop the current fragment off the top of the stack and dismiss it.
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
    public abstract void popFragment(int animationIn, int animationOut);

    protected void popFragmentAboveStackSize(int stackSize, int animationIn, int animationOut) {
        if (getFragmentTags().size() > stackSize) {
            FragmentManager childFragManager = getRetainedChildFragmentManager();
            FragmentTransaction childFragTrans = childFragManager.beginTransaction();
            childFragTrans.setCustomAnimations(animationIn, animationOut);
            childFragTrans.remove(childFragManager.findFragmentByTag(getFragmentTags().pop()));
            childFragTrans.attach(childFragManager.findFragmentByTag(getFragmentTags().peek()));
            childFragTrans.commit();
        }
        else {
            // TODO: Nothing to dismiss ... Exception? Call activity onBackPressed()? what to do?
            // TODO: Dismiss root and self?
            getActivity().onBackPressed();
        }
    }

    /**
     * Access the fragment that is on the top of the navigation stack.
     *
     * @return
     *      {@link NavigationFragment} that is on the top of the stack.
     */
    public abstract NavigationFragment topFragment();

    /**
     * Remove the {@link NavigationFragment} that is on the top of the stack.
     *
     * @return
     *      true -> A {@link NavigationFragment} has been removed
     *      false -> No fragment has been removed because we are at the bottom of the stack for that stack.
     */
    public abstract boolean onBackPressed();

    protected void addFragmentToStack(NavigationFragment navFragment) {
        getFragmentTags().add(navFragment.getNavTag());
    }

    protected Stack<String> getFragmentTags() {
        if (mFragmentTags == null) {
            mFragmentTags = new Stack<>();
        }
        return mFragmentTags;
    }

}
