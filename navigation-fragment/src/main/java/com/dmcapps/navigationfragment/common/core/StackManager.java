package com.dmcapps.navigationfragment.common.core;

import com.dmcapps.navigationfragment.common.core.fragmentmanagerwrapper.FragmentManagerWrapper;
import com.dmcapps.navigationfragment.common.core.fragmentmanagerwrapper.NavigationFragmentManagerWrapper;
import com.dmcapps.navigationfragment.common.core.fragmenttransactionwrapper.FragmentTransactionWrapper;
import com.dmcapps.navigationfragment.common.interfaces.Config;
import com.dmcapps.navigationfragment.common.interfaces.Navigation;
import com.dmcapps.navigationfragment.common.interfaces.NavigationManager;
import com.dmcapps.navigationfragment.common.interfaces.Stack;
import com.dmcapps.navigationfragment.common.interfaces.State;

/**
 * Created by dcarmo on 2016-11-19.
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

        FragmentManagerWrapper fragmentManagerWrapper = new NavigationFragmentManagerWrapper(navigationManager.getNavigationFragmentManager());

        if (settings != null) {
            navFragment.setNavBundle(settings.getNavBundle());
        }

        FragmentTransactionWrapper fragmentTransactionWrapper = fragmentManagerWrapper.beginTransactionWrapped();

        if (state.getStack().size() >= config.getMinStackSize()) {
            Integer presentAnimIn = config.getPresentAnimIn();
            Integer presentAnimOut = config.getPresentAnimOut();
            if (presentAnimIn != null && presentAnimOut != null) {
                fragmentTransactionWrapper.setCustomAnimations(presentAnimIn, presentAnimOut);
            }

            Object topFrag = fragmentManagerWrapper.findFragmentByTag(state.getStack().peek());
            // Detach the top fragment such that it is kept in the stack and can be shown again without lose of state.
            fragmentTransactionWrapper.detach(topFrag);
        }

        // Add in the new fragment that we are presenting and add it's navigation tag to the stack.
        fragmentTransactionWrapper.add(config.getPushContainerId(), navFragment, navFragment.getNavTag());
        fragmentTransactionWrapper.commit();

        navigationManager.addToStack(navFragment);

        return navFragment;
    }

    @Override
    public Navigation popFragment(NavigationManager navigationManager) {
        return popFragment(navigationManager, null);
    }

    @Override
    public Navigation popFragment(NavigationManager navigationManager, NavigationSettings settings) {
        Navigation navFragment = null;

        State state = navigationManager.getState();
        Config config = navigationManager.getConfig();

        if (state.getStack().size() > config.getMinStackSize()) {
            FragmentManagerWrapper fragmentManagerWrapper = new NavigationFragmentManagerWrapper(navigationManager.getNavigationFragmentManager());
            FragmentTransactionWrapper fragmentTransactionWrapper = fragmentManagerWrapper.beginTransactionWrapped();

            Integer dismissAnimIn = config.getDismissAnimIn();
            Integer dismissAnimOut = config.getDismissAnimOut();
            if (dismissAnimIn != null && dismissAnimOut != null) {
                fragmentTransactionWrapper.setCustomAnimations(dismissAnimIn, dismissAnimOut);
            }

            fragmentTransactionWrapper.remove(fragmentManagerWrapper.findFragmentByTag(state.getStack().pop()));

            if (state.getStack().size() > 0) {
                navFragment = (Navigation) fragmentManagerWrapper.findFragmentByTag(state.getStack().peek());
                fragmentTransactionWrapper.attach(navFragment);
            }

            fragmentTransactionWrapper.commit();
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
    public void clearNavigationStackToPosition(NavigationManager navigationManager, int stackPosition) {
        State state = navigationManager.getState();

        FragmentManagerWrapper fragmentManager = new NavigationFragmentManagerWrapper(navigationManager.getNavigationFragmentManager());
        FragmentTransactionWrapper fragmentTransaction = fragmentManager.beginTransactionWrapped();

        fragmentTransaction.setCustomAnimations(ConfigManager.NO_ANIMATION, ConfigManager.NO_ANIMATION);

        while (state.getStack().size() > stackPosition) {
            fragmentTransaction.remove(fragmentManager.findFragmentByTag(state.getStack().pop()));
        }

        if (state.getStack().size() > 0) {
            fragmentTransaction.attach(fragmentManager.findFragmentByTag(state.getStack().peek()));
        }

        fragmentTransaction.commit();
        fragmentManager.executePendingTransactions();
    }

    @Override
    public Navigation getFragmentAtIndex(NavigationManager navigationManager, int index) {
        return null;
    }
}
