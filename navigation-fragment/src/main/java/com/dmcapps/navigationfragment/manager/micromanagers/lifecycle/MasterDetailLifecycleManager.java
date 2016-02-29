package com.dmcapps.navigationfragment.manager.micromanagers.lifecycle;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dmcapps.navigationfragment.R;
import com.dmcapps.navigationfragment.manager.NavigationManagerFragment;
import com.dmcapps.navigationfragment.manager.micromanagers.ManagerConfig;
import com.dmcapps.navigationfragment.manager.micromanagers.ManagerState;

/**
 * Created by dcarmo on 2016-02-24.
 */
public class MasterDetailLifecycleManager implements ILifecycleManager {

    private static final int MASTER_DETAIL_PHONE_MIN_ACTION_SIZE = 1;
    private static final int MASTER_DETAIL_TABLET_MIN_ACTION_SIZE = 2;

    public void onResume(NavigationManagerFragment navMgrFragment, ManagerState state, ManagerConfig config) {

        // No Fragments have been added. Attach the master and detail.
        if (state.fragmentTagStack.size() == 0) {
            config.masterFragment.setNavigationManager(navMgrFragment);
            FragmentManager childFragManager = navMgrFragment.getRetainedChildFragmentManager();
            FragmentTransaction childFragTrans = childFragManager.beginTransaction();
            if (state.isTablet) {
                // Add in the new fragment that we are presenting and add it's navigation tag to the stack.
                childFragTrans.add(R.id.navigation_manager_container_master, (Fragment) config.masterFragment, config.masterFragment.getNavTag());
                navMgrFragment.addFragmentToStack(config.masterFragment);
            }
            else {
                navMgrFragment.pushFragment(config.masterFragment);
            }
            childFragTrans.commit();

            if (state.isTablet) {
                navMgrFragment.pushFragment(config.detailFragment);
            }

            config.nullifyInitialFragments();
        }
        // Fragments are in the stack, resume at the top.
        else {
            FragmentManager childFragManager = navMgrFragment.getRetainedChildFragmentManager();
            FragmentTransaction childFragTrans = childFragManager.beginTransaction();
            childFragTrans.setCustomAnimations(ManagerConfig.NO_ANIMATION, ManagerConfig.NO_ANIMATION);
            if (state.isTablet) {
                childFragTrans.attach(childFragManager.findFragmentByTag(state.fragmentTagStack.firstElement()));
            }
            childFragTrans.attach(childFragManager.findFragmentByTag(state.fragmentTagStack.peek()));
            childFragTrans.commit();
        }

        if (navMgrFragment.getActivity() != null) {
            navMgrFragment.getActivity().invalidateOptionsMenu();
        }
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

        config.minStackSize = state.isTablet ? MASTER_DETAIL_TABLET_MIN_ACTION_SIZE : MASTER_DETAIL_PHONE_MIN_ACTION_SIZE;
        config.pushContainerId = R.id.navigation_manager_fragment_container;
    }

    @Override
    public void onPause(NavigationManagerFragment navMgrFragment, ManagerState state) {

        FragmentManager childFragManager = navMgrFragment.getRetainedChildFragmentManager();
        FragmentTransaction childFragTrans = childFragManager.beginTransaction();
        childFragTrans.setCustomAnimations(ManagerConfig.NO_ANIMATION, ManagerConfig.NO_ANIMATION);
        if (state.isTablet) {
            childFragTrans.detach(childFragManager.findFragmentByTag(state.fragmentTagStack.firstElement()));
        }
        childFragTrans.detach(childFragManager.findFragmentByTag(state.fragmentTagStack.peek()));
        childFragTrans.commit();

    }

}
