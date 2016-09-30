package com.dmcapps.navigationfragment.support.v7.manager.core.micromanagers;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.dmcapps.navigationfragment.common.interfaces.Navigation;
import com.dmcapps.navigationfragment.common.micromanagers.ManagerConfig;
import com.dmcapps.navigationfragment.common.micromanagers.ManagerState;
import com.dmcapps.navigationfragment.common.interfaces.Stack;
import com.dmcapps.navigationfragment.support.v7.manager.core.NavigationManagerFragment;
import com.dmcapps.navigationfragment.common.interfaces.Config;
import com.dmcapps.navigationfragment.common.interfaces.State;

/**
 * Created by dcarmo on 2016-02-25.
 */
public class StackManager implements Stack {

    public Navigation pushFragment(NavigationManagerFragment manager, State state, Config config, Navigation navFragment, Bundle navBundle) {
        navFragment.setNavBundle(navBundle);
        pushFragment(manager, state, config, navFragment);
        return navFragment;
    }

    public Navigation pushFragment(NavigationManagerFragment manager, State state, Config config, Navigation navFragment) {
        FragmentManager childFragManager = manager.getRetainedChildFragmentManager();
        FragmentTransaction childFragTrans = childFragManager.beginTransaction();

        if (state.getStack().size() >= config.getMinStackSize()) {
            childFragTrans.setCustomAnimations(config.getPresentAnimIn(), config.getPresentAnimOut());
            Fragment topFrag = childFragManager.findFragmentByTag(state.getStack().peek());
            // Detach the top fragment such that it is kept in the stack and can be shown again without lose of state.
            childFragTrans.detach(topFrag);
        }

        // Add in the new fragment that we are presenting and add it's navigation tag to the stack.
        childFragTrans.add(config.getPushContainerId(), (Fragment) navFragment, navFragment.getNavTag());
        childFragTrans.commit();

        manager.addFragmentToStack(navFragment);

        return navFragment;
    }

    public Navigation popFragment(NavigationManagerFragment manager, State state, Config config, Bundle navBundle) {
        Navigation navFragment = popFragment(manager, state, config);
        if (navFragment != null) {
            navFragment.setNavBundle(navBundle);
        }
        return navFragment;
    }

    public Navigation popFragment(NavigationManagerFragment manager, State state, Config config) {
        Navigation navFragment = null;

        if (state.getStack().size() > config.getMinStackSize()) {
            FragmentManager childFragManager = manager.getRetainedChildFragmentManager();
            FragmentTransaction childFragTrans = childFragManager.beginTransaction();
            childFragTrans.setCustomAnimations(config.getDismissAnimIn(), config.getDismissAnimOut());
            childFragTrans.remove(childFragManager.findFragmentByTag(state.getStack().pop()));

            if (state.getStack().size() > 0) {
                navFragment = (Navigation)childFragManager.findFragmentByTag(state.getStack().peek());
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
    public void clearNavigationStackToPosition(NavigationManagerFragment manager, State state, int stackPosition) {
        FragmentManager childFragManager = manager.getRetainedChildFragmentManager();
        FragmentTransaction childFragTrans = childFragManager.beginTransaction();
        childFragTrans.setCustomAnimations(ManagerConfig.NO_ANIMATION, ManagerConfig.NO_ANIMATION);

        while (state.getStack().size() > stackPosition) {
            childFragTrans.remove(childFragManager.findFragmentByTag(state.getStack().pop()));
        }

        if (state.getStack().size() > 0) {
            childFragTrans.attach(childFragManager.findFragmentByTag(state.getStack().peek()));
        }

        childFragTrans.commit();
        childFragManager.executePendingTransactions();
    }

    /**
     * Access the fragment at the given index of the navigation stack.
     *
     * @param
     *      manager -> The current {@link NavigationManagerFragment} managing the state of the Navigation.
     * @param
     *      state -> The current {@link ManagerState} that has the current navigation stack state.
     * @param
     *      index -> The index of the {@link Navigation} you would like to get.
     * @return
     *      {@link Navigation} that is at the given index.
     */
    public Navigation getFragmentAtIndex(NavigationManagerFragment manager, State state, int index) {
        String navFragTag = state.getStack().get(index);
        FragmentManager childFragManager = manager.getRetainedChildFragmentManager();
        return (Navigation)childFragManager.findFragmentByTag(navFragTag);
    }
}
