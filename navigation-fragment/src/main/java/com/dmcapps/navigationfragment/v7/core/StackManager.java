package com.dmcapps.navigationfragment.v7.core;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.dmcapps.navigationfragment.common.core.ConfigManager;
import com.dmcapps.navigationfragment.common.core.NavigationSettings;
import com.dmcapps.navigationfragment.common.helpers.utils.NavigationManagerUtils;
import com.dmcapps.navigationfragment.common.interfaces.Config;
import com.dmcapps.navigationfragment.common.interfaces.Navigation;
import com.dmcapps.navigationfragment.common.interfaces.NavigationManager;
import com.dmcapps.navigationfragment.common.interfaces.Stack;
import com.dmcapps.navigationfragment.common.interfaces.State;
import com.dmcapps.navigationfragment.v17.core.*;

/**
 * Created by dcarmo on 2016-02-25.
 */
public class StackManager implements Stack {

    @Override
    public Navigation pushFragment(NavigationManager navigationManager, Navigation navFragment) {
        return pushFragment(navigationManager, navFragment, null);
    }

    @Override
    public Navigation pushFragment(NavigationManager navigationManager, Navigation navFragment, NavigationSettings settings) {
        State state = navigationManager.getState();
        Config config = navigationManager.getConfig();

        if (settings != null) {
            navFragment.setNavBundle(settings.getNavBundle());
        }

        FragmentManager childFragManager = NavigationManagerUtils.getSupportFragmentManager(navigationManager);
        FragmentTransaction childFragTrans = childFragManager.beginTransaction();

        if (state.getStack().size() >= config.getMinStackSize()) {
            Integer presentAnimIn = config.getPresentAnimIn();
            Integer presentAnimOut = config.getPresentAnimOut();
            if (presentAnimIn != null && presentAnimOut != null) {
                childFragTrans.setCustomAnimations(presentAnimIn, presentAnimOut);
            }

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

    @Override
    public Navigation popFragment(NavigationManager navigationManager, NavigationSettings settings) {
        Navigation navFragment = null;

        State state = navigationManager.getState();
        Config config = navigationManager.getConfig();

        if (state.getStack().size() > config.getMinStackSize()) {
            FragmentManager childFragManager = NavigationManagerUtils.getSupportFragmentManager(navigationManager);
            FragmentTransaction childFragTrans = childFragManager.beginTransaction();

            Integer dismissAnimIn = config.getDismissAnimIn();
            Integer dismissAnimOut = config.getDismissAnimOut();
            if (dismissAnimIn != null && dismissAnimOut != null) {
                childFragTrans.setCustomAnimations(dismissAnimIn, dismissAnimOut);
            }

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

        if (navFragment != null && settings != null) {
            navFragment.setNavBundle(settings.getNavBundle());
        }
        return navFragment;
    }

    @Override
    public Navigation popFragment(NavigationManager navigationManager) {
        return popFragment(navigationManager, null);
    }

    @Override
    public void clearNavigationStackToPosition(NavigationManager navigationManager, int stackPosition) {
        State state = navigationManager.getState();

        FragmentManager childFragManager = NavigationManagerUtils.getSupportFragmentManager(navigationManager);
        FragmentTransaction childFragTrans = childFragManager.beginTransaction();
        childFragTrans.setCustomAnimations(ConfigManager.NO_ANIMATION, ConfigManager.NO_ANIMATION);

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
     *      navigationManager -> The current {@link com.dmcapps.navigationfragment.v17.core.NavigationManagerFragment} managing the state of the Navigation.
     * @param
     *      index -> The index of the {@link Navigation} you would like to get.
     * @return
     *      {@link Navigation} that is at the given index.
     */
    @Override
    public Navigation getFragmentAtIndex(NavigationManager navigationManager, int index) {
        String navFragTag = navigationManager.getState().getStack().get(index);
        FragmentManager childFragManager = NavigationManagerUtils.getSupportFragmentManager(navigationManager);
        return (Navigation)childFragManager.findFragmentByTag(navFragTag);
    }
}
