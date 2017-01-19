package com.github.dmcapps.navigationfragment.common.core;

import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;

import com.github.dmcapps.navigationfragment.common.interfaces.Navigation;
import com.github.dmcapps.navigationfragment.common.interfaces.Stack;
import com.github.dmcapps.navigationfragment.common.interfaces.State;

import java.util.Locale;

/**
 * Created by dcarmo on 2016-11-19.
 */

public class StackManager implements Stack {
    private static final String TAG = StackManager.class.getSimpleName();

    @Override
    public Navigation pushFragment(NavigationManager navigationManager, Navigation navFragment, PresentationTransaction transaction) {
        NavigationConfig config = navigationManager.getConfig();

        if (transaction.getNavBundle() != null) {
            navFragment.setNavBundle(transaction.getNavBundle());
        }

        // Don't animate the initial fragments being added to the stack.
        if (navigationManager.getCurrentStackSize() < config.getMinStackSize()) {
            transaction.setCustomAnimations(0, 0, 0, 0);
        }

        // Add in the new fragment that we are presenting and add it's navigation tag to the stack.
        transaction.add(config.getPushContainerId(), navFragment, navFragment.getNavTag());
        transaction.addToBackStack(navFragment.getNavTag());
        transaction.commit();

        navigationManager.addToStack(navFragment);

        return navFragment;
    }

    @Override
    public Navigation popFragment(NavigationManager navigationManager, Bundle navBundle) {
        Navigation navFragment = null;

        State state = navigationManager.getState();
        NavigationConfig config = navigationManager.getConfig();

        if (state.getStack().size() > config.getMinStackSize()) {
            Object childFragmentManager = navigationManager.getContainer().getNavChildFragmentManager();
            FragmentManagerWrapper fragmentManagerWrapper = new FragmentManagerWrapper(childFragmentManager);
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

        FragmentManagerWrapper fragmentManager = new FragmentManagerWrapper(navigationManager.getContainer().getNavChildFragmentManager());
        fragmentManager.popBackStackImmediate(tag, inclusive ? FragmentManager.POP_BACK_STACK_INCLUSIVE : 0);
    }

    @Override
    public Navigation getFragmentAtIndex(NavigationManager navigationManager, int index) {
        State state = navigationManager.getState();
        FragmentManagerWrapper fragmentManagerWrapper = new FragmentManagerWrapper(navigationManager.getContainer().getNavChildFragmentManager());

        return (Navigation) fragmentManagerWrapper.findFragmentByTag(state.getStack().get(index));
    }
}
