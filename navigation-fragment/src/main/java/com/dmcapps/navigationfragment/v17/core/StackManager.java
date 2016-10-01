package com.dmcapps.navigationfragment.v17.core;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.dmcapps.navigationfragment.common.core.CofigManager;
import com.dmcapps.navigationfragment.common.helpers.utils.NavigationManagerUtils;
import com.dmcapps.navigationfragment.common.interfaces.Config;
import com.dmcapps.navigationfragment.common.interfaces.Navigation;
import com.dmcapps.navigationfragment.common.interfaces.NavigationManager;
import com.dmcapps.navigationfragment.common.interfaces.Stack;
import com.dmcapps.navigationfragment.common.interfaces.State;

/**
 * Created by dcarmo on 2016-02-25.
 */
public class StackManager implements Stack {

    public Navigation pushFragment(NavigationManager navigationManager, Navigation navFragment, Bundle navBundle) {
        navFragment.setNavBundle(navBundle);
        pushFragment(navigationManager, navFragment);
        return navFragment;
    }

    public Navigation pushFragment(NavigationManager navigationManager, Navigation navFragment) {
        State state = navigationManager.getState();
        Config config = navigationManager.getConfig();

        FragmentManager childFragManager = NavigationManagerUtils.getFragmentManager(navigationManager);
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

        navigationManager.addToStack(navFragment);

        return navFragment;
    }

    public Navigation popFragment(NavigationManager navigationManager, Bundle navBundle) {
        Navigation navFragment = popFragment(navigationManager);
        if (navFragment != null) {
            navFragment.setNavBundle(navBundle);
        }
        return navFragment;
    }

    public Navigation popFragment(NavigationManager navigationManager) {
        Navigation navFragment = null;

        State state = navigationManager.getState();
        Config config = navigationManager.getConfig();

        if (state.getStack().size() > config.getMinStackSize()) {
            FragmentManager childFragManager = NavigationManagerUtils.getFragmentManager(navigationManager);
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
            navigationManager.getActivity().onBackPressed();
        }

        return navFragment;
    }

    @Override
    public void clearNavigationStackToPosition(NavigationManager navigationManager, int stackPosition) {
        State state = navigationManager.getState();

        FragmentManager childFragManager = NavigationManagerUtils.getFragmentManager(navigationManager);
        FragmentTransaction childFragTrans = childFragManager.beginTransaction();
        childFragTrans.setCustomAnimations(CofigManager.NO_ANIMATION, CofigManager.NO_ANIMATION);

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
     *      navigationManager -> The current {@link NavigationManagerFragment} managing the state of the Navigation.
     * @param
     *      index -> The index of the {@link Navigation} you would like to get.
     * @return
     *      {@link Navigation} that is at the given index.
     */
    public Navigation getFragmentAtIndex(NavigationManager navigationManager, int index) {
        String navFragTag = navigationManager.getState().getStack().get(index);
        FragmentManager childFragManager = NavigationManagerUtils.getFragmentManager(navigationManager);
        return (Navigation)childFragManager.findFragmentByTag(navFragTag);
    }
}