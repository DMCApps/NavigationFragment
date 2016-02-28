package com.dmcapps.navigationfragment.manager.micromanagers.stack;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.dmcapps.navigationfragment.fragments.INavigationFragment;
import com.dmcapps.navigationfragment.manager.NavigationManagerFragment;
import com.dmcapps.navigationfragment.manager.micromanagers.ManagerConfig;
import com.dmcapps.navigationfragment.manager.micromanagers.ManagerState;

/**
 * Created by dcarmo on 2016-02-25.
 */
public class StackManager implements IStackManager {

    public void pushFragment(NavigationManagerFragment manager, ManagerState state, ManagerConfig config, INavigationFragment navFragment, int animIn, int animOut) {
        navFragment.setNavigationManager(manager);
        FragmentManager childFragManager = manager.getRetainedChildFragmentManager();
        FragmentTransaction childFragTrans = childFragManager.beginTransaction();

        if (state.fragmentTagStack.size() >= config.minStackSize) {
            childFragTrans.setCustomAnimations(animIn, animOut);
            Fragment topFrag = childFragManager.findFragmentByTag(state.fragmentTagStack.peek());
            // Detach the top fragment such that it is kept in the stack and can be shown again without lose of state.
            childFragTrans.detach(topFrag);
        }

        // Add in the new fragment that we are presenting and add it's navigation tag to the stack.
        childFragTrans.add(config.pushContainerId, (Fragment) navFragment, navFragment.getNavTag());
        childFragTrans.commit();

        manager.addFragmentToStack(navFragment);
    }

    public void popFragment(NavigationManagerFragment manager, ManagerState state, ManagerConfig config, int animIn, int animOut) {
        if (state.fragmentTagStack.size() > config.minStackSize) {
            FragmentManager childFragManager = manager.getRetainedChildFragmentManager();
            FragmentTransaction childFragTrans = childFragManager.beginTransaction();
            childFragTrans.setCustomAnimations(animIn, animOut);
            childFragTrans.remove(childFragManager.findFragmentByTag(state.fragmentTagStack.pop()));

            if (state.fragmentTagStack.size() > 0) {
                childFragTrans.attach(childFragManager.findFragmentByTag(state.fragmentTagStack.peek()));
            }

            childFragTrans.commit();
        }
        else {
            // TODO: Nothing above stack size to dismiss ... Exception? Call activity onBackPressed()? what to do?
            // TODO: Dismiss root and self?
            manager.getActivity().onBackPressed();
        }
    }

    @Override
    public void clearNavigationStackToPosition(NavigationManagerFragment manager, ManagerState state, int stackPosition) {
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
    }
}
