package com.dmcapps.navigationfragment.manager.core.micromanagers.lifecycle;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dmcapps.navigationfragment.R;
import com.dmcapps.navigationfragment.manager.core.NavigationManagerFragment;
import com.dmcapps.navigationfragment.manager.core.micromanagers.ManagerConfig;
import com.dmcapps.navigationfragment.manager.core.micromanagers.ManagerState;

/**
 * Created by dcarmo on 2016-02-24.
 */
public class SingleStackLifecycleManager implements ILifecycleManager {

    private static final int SINGLE_STACK_MIN_ACTION_SIZE = 1;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_navigation_manager, container, false);
    }

    @Override
    public void onViewCreated(View view, ManagerState state, ManagerConfig config) {
        state.isTablet = view.findViewById(R.id.navigation_manager_tablet_land) != null
                || view.findViewById(R.id.navigation_manager_tablet_portrait) != null;
        state.isPortrait = view.findViewById(R.id.navigation_manager_phone_portrait) != null
                || view.findViewById(R.id.navigation_manager_tablet_portrait) != null;

        config.minStackSize = SINGLE_STACK_MIN_ACTION_SIZE;
        config.pushContainerId = R.id.navigation_manager_fragment_container;

        if (view.findViewById(R.id.navigation_manager_container_master) != null) {
            view.findViewById(R.id.navigation_manager_container_master).setVisibility(View.GONE);
        }
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
