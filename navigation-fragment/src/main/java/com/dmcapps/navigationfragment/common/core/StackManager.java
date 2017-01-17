package com.dmcapps.navigationfragment.common.core;

import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;

import com.dmcapps.navigationfragment.common.core.NavigationTransaction.SharedElement;
import com.dmcapps.navigationfragment.common.helpers.fragmentmanagerwrapper.FragmentManagerWrapper;
import com.dmcapps.navigationfragment.common.helpers.fragmentmanagerwrapper.NavigationFragmentManagerWrapper;
import com.dmcapps.navigationfragment.common.helpers.fragmenttransactionwrapper.FragmentTransactionWrapper;
import com.dmcapps.navigationfragment.common.interfaces.Navigation;
import com.dmcapps.navigationfragment.common.interfaces.Stack;
import com.dmcapps.navigationfragment.common.interfaces.State;

import java.util.Locale;

/**
 * Created by dcarmo on 2016-11-19.
 */

public class StackManager implements Stack {
    private static final String TAG = StackManager.class.getSimpleName();

    @Override
    public Navigation pushFragment(NavigationManager navigationManager, Navigation navFragment) {
        return pushFragment(navigationManager, navFragment, null);
    }

    @Override
    public Navigation pushFragment(NavigationManager navigationManager, Navigation navFragment, NavigationTransaction transaction) {
        State state = navigationManager.getState();
        NavigationConfig config = navigationManager.getConfig();

        FragmentManagerWrapper fragmentManagerWrapper = new NavigationFragmentManagerWrapper(navigationManager.getContainer().getNavChildFragmentManager());
        FragmentTransactionWrapper fragmentTransactionWrapper = fragmentManagerWrapper.beginTransactionWrapped();

        if (transaction != null) {
            navFragment.setNavBundle(transaction.getNavBundle());
            if (transaction.getSharedElements() != null) {
                for (SharedElement sharedElement : transaction.getSharedElements()) {
                    fragmentTransactionWrapper.addSharedElement(sharedElement);
                }
            }
        }

        if (state.getStack().size() >= config.getMinStackSize() && transaction != null) {
            Integer enter = transaction.getPresentInAnim();
            Integer exit = transaction.getPresentOutAnim();
            Integer popEnter = transaction.getDismissInAnim();
            Integer popExit = transaction.getDismissOutAnim();
            if (enter != null && exit != null && popEnter != null && popExit != null) {
                fragmentTransactionWrapper.setCustomAnimations(enter, exit, popEnter, popExit);
            }
            else if (enter != null && exit != null) {
                fragmentTransactionWrapper.setCustomAnimations(enter, exit);
            }
        }

        // Add in the new fragment that we are presenting and add it's navigation tag to the stack.
        fragmentTransactionWrapper.add(config.getPushContainerId(), navFragment, navFragment.getNavTag());
        fragmentTransactionWrapper.addToBackStack(navFragment.getNavTag());
        fragmentTransactionWrapper.commit();

        navigationManager.addToStack(navFragment);

        return navFragment;
    }

    @Override
    public Navigation popFragment(NavigationManager navigationManager) {
        return popFragment(navigationManager, null);
    }

    @Override
    public Navigation popFragment(NavigationManager navigationManager, Bundle navBundle) {
        Navigation navFragment = null;

        State state = navigationManager.getState();
        NavigationConfig config = navigationManager.getConfig();

        if (state.getStack().size() > config.getMinStackSize()) {
            FragmentManagerWrapper fragmentManagerWrapper = new NavigationFragmentManagerWrapper(navigationManager.getContainer().getNavChildFragmentManager());
            fragmentManagerWrapper.popBackStack();
            state.getStack().pop();

            if (state.getStack().size() > 0) {
                navFragment = (Navigation) fragmentManagerWrapper.findFragmentByTag(state.getStack().peek());
            }
        }
        else if (navigationManager.getContainer() != null){
            navigationManager.getContainer().getFragmentActivity().onBackPressed();
        }

        if (navFragment != null && navBundle != null) {
            navFragment.setNavBundle(navBundle);
        }
        return navFragment;
    }

    @Override
    public void clearNavigationStackToIndex(NavigationManager navigationManager, int index) {
        clearNavigationStackToIndex(navigationManager, index, false);
    }

    @Override
    public void clearNavigationStackToIndex(NavigationManager navigationManager, int index, boolean inclusive) {
        if (index < 0) {
            throw new RuntimeException("You cannot remove fragments below the 0 index. Please use the 0 index with inclusive = true to remove it. It's likely better to use replaceRootFragment() to make sure you always have a root fragment");
        }

        State state = navigationManager.getState();
        java.util.Stack<String> stack = state.getStack();

        if ((index + 1) >= stack.size()) {
            Log.e(TAG, String.format(Locale.ENGLISH, "Cannot clear navigation to index (%d) when the stack size is (%d)", index, stack.size()));
            return;
        }

        String tag = stack.get(index);
        int popToStackSize = inclusive ? index : index + 1;

        while (stack.size() > popToStackSize) {
            stack.pop();
        }

        FragmentManagerWrapper fragmentManager = new NavigationFragmentManagerWrapper(navigationManager.getContainer().getNavChildFragmentManager());
        fragmentManager.popBackStackImmediate(tag, inclusive ? FragmentManager.POP_BACK_STACK_INCLUSIVE : 0);
    }

    @Override
    public Navigation getFragmentAtIndex(NavigationManager navigationManager, int index) {
        State state = navigationManager.getState();
        FragmentManagerWrapper fragmentManagerWrapper = new NavigationFragmentManagerWrapper(navigationManager.getContainer().getNavChildFragmentManager());

        return (Navigation) fragmentManagerWrapper.findFragmentByTag(state.getStack().get(index));
    }
}
