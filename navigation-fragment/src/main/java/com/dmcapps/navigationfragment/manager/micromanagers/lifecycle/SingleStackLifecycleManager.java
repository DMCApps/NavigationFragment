package com.dmcapps.navigationfragment.manager.micromanagers.lifecycle;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.dmcapps.navigationfragment.manager.NavigationManagerFragment;
import com.dmcapps.navigationfragment.manager.micromanagers.ManagerConfig;
import com.dmcapps.navigationfragment.manager.micromanagers.ManagerState;

/**
 * Created by dcarmo on 2016-02-24.
 */
public class SingleStackLifecycleManager implements ILifecycleManager {

    @Override
    public void onResume(NavigationManagerFragment navMgrFragment, ManagerState state, ManagerConfig config) {
        // No Fragments have been added. Attach the root.
        if (state.fragmentTagStack.size() == 0) {
            navMgrFragment.pushFragment(config.rootFragment);
        }
        // Fragments are in the stack, resume at the top.
        else {
            FragmentManager childFragManager = navMgrFragment.getRetainedChildFragmentManager();
            FragmentTransaction childFragTrans = childFragManager.beginTransaction();
            childFragTrans.setCustomAnimations(ManagerConfig.NO_ANIMATION, ManagerConfig.NO_ANIMATION);
            childFragTrans.attach(childFragManager.findFragmentByTag(state.fragmentTagStack.peek()));
            childFragTrans.commit();
        }

        config.nullifyInitialFragments();
    }

    @Override
    public void onPause(NavigationManagerFragment navMgrFragment, ManagerState state) {
        FragmentManager childFragManager = navMgrFragment.getRetainedChildFragmentManager();
        FragmentTransaction childFragTrans = childFragManager.beginTransaction();
        childFragTrans.setCustomAnimations(ManagerConfig.NO_ANIMATION, ManagerConfig.NO_ANIMATION);
        childFragTrans.detach(childFragManager.findFragmentByTag(state.fragmentTagStack.peek()));
        childFragTrans.commit();
    }

}
