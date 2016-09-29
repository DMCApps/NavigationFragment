package com.dmcapps.navigationfragment.supportv7.manager.core.micromanagers.stack;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.dmcapps.navigationfragment.common.INavigationFragment;
import com.dmcapps.navigationfragment.supportv7.manager.core.SupportNavigationManagerFragment;
import com.dmcapps.navigationfragment.supportv7.manager.core.micromanagers.ManagerConfig;
import com.dmcapps.navigationfragment.supportv7.manager.core.micromanagers.ManagerState;

/**
 * Created by dcarmo on 2016-02-25.
 */
public class StackManager implements IStackManager {

    public INavigationFragment pushFragment(SupportNavigationManagerFragment manager, ManagerState state, ManagerConfig config, INavigationFragment navFragment, Bundle navBundle) {
        navFragment.setNavBundle(navBundle);
        pushFragment(manager, state, config, navFragment);
        return navFragment;
    }

    public INavigationFragment pushFragment(SupportNavigationManagerFragment manager, ManagerState state, ManagerConfig config, INavigationFragment navFragment) {
        FragmentManager childFragManager = manager.getRetainedChildFragmentManager();
        FragmentTransaction childFragTrans = childFragManager.beginTransaction();

        if (state.fragmentTagStack.size() >= config.minStackSize) {
            childFragTrans.setCustomAnimations(config.getPresentAnimIn(), config.getPresentAnimOut());
            Fragment topFrag = childFragManager.findFragmentByTag(state.fragmentTagStack.peek());
            // Detach the top fragment such that it is kept in the stack and can be shown again without lose of state.
            childFragTrans.detach(topFrag);
        }

        // Add in the new fragment that we are presenting and add it's navigation tag to the stack.
        childFragTrans.add(config.pushContainerId, (Fragment) navFragment, navFragment.getNavTag());
        childFragTrans.commit();

        manager.addFragmentToStack(navFragment);

        return navFragment;
    }

    public INavigationFragment popFragment(SupportNavigationManagerFragment manager, ManagerState state, ManagerConfig config, Bundle navBundle) {
        INavigationFragment navFragment = popFragment(manager, state, config);
        if (navFragment != null) {
            navFragment.setNavBundle(navBundle);
        }
        return navFragment;
    }

    public INavigationFragment popFragment(SupportNavigationManagerFragment manager, ManagerState state, ManagerConfig config) {
        INavigationFragment navFragment = null;

        if (state.fragmentTagStack.size() > config.minStackSize) {
            FragmentManager childFragManager = manager.getRetainedChildFragmentManager();
            FragmentTransaction childFragTrans = childFragManager.beginTransaction();
            childFragTrans.setCustomAnimations(config.getDismissAnimIn(), config.getDismissAnimOut());
            childFragTrans.remove(childFragManager.findFragmentByTag(state.fragmentTagStack.pop()));

            if (state.fragmentTagStack.size() > 0) {
                navFragment = (INavigationFragment)childFragManager.findFragmentByTag(state.fragmentTagStack.peek());
                childFragTrans.attach((Fragment)navFragment);
            }

            childFragTrans.commit();
        }
        else {
            manager.getActivity().onBackPressed();
        }

        return navFragment;
    }

    @Override
    public void clearNavigationStackToPosition(SupportNavigationManagerFragment manager, ManagerState state, int stackPosition) {
        FragmentManager childFragManager = manager.getRetainedChildFragmentManager();
        FragmentTransaction childFragTrans = childFragManager.beginTransaction();
        childFragTrans.setCustomAnimations(ManagerConfig.NO_ANIMATION, ManagerConfig.NO_ANIMATION);

        while (state.fragmentTagStack.size() > stackPosition) {
            childFragTrans.remove(childFragManager.findFragmentByTag(state.fragmentTagStack.pop()));
        }

        if (state.fragmentTagStack.size() > 0) {
            childFragTrans.attach(childFragManager.findFragmentByTag(state.fragmentTagStack.peek()));
        }

        childFragTrans.commit();
        childFragManager.executePendingTransactions();
    }

    /**
     * Access the fragment at the given index of the navigation stack.
     *
     * @param
     *      manager -> The current {@link SupportNavigationManagerFragment} managing the state of the Navigation.
     * @param
     *      state -> The current {@link ManagerState} that has the current navigation stack state.
     * @param
     *      index -> The index of the {@link INavigationFragment} you would like to get.
     * @return
     *      {@link INavigationFragment} that is at the given index.
     */
    public INavigationFragment getFragmentAtIndex(SupportNavigationManagerFragment manager, ManagerState state, int index) {
        String navFragTag = state.fragmentTagStack.get(index);
        FragmentManager childFragManager = manager.getRetainedChildFragmentManager();
        return (INavigationFragment)childFragManager.findFragmentByTag(navFragTag);
    }
}
