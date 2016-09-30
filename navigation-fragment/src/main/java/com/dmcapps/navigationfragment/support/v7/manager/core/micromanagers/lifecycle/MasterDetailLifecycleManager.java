package com.dmcapps.navigationfragment.support.v7.manager.core.micromanagers.lifecycle;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dmcapps.navigationfragment.R;
import com.dmcapps.navigationfragment.common.interfaces.Lifecycle;
import com.dmcapps.navigationfragment.support.v7.manager.core.NavigationManagerFragment;
import com.dmcapps.navigationfragment.common.interfaces.Config;
import com.dmcapps.navigationfragment.common.micromanagers.ManagerConfig;
import com.dmcapps.navigationfragment.common.interfaces.State;

/**
 * Created by dcarmo on 2016-02-24.
 */
public class MasterDetailLifecycleManager implements Lifecycle {

    private static final int MASTER_DETAIL_PHONE_MIN_ACTION_SIZE = 1;
    private static final int MASTER_DETAIL_TABLET_MIN_ACTION_SIZE = 2;

    public void onResume(NavigationManagerFragment navMgrFragment, State state, Config config) {

        // No Fragments have been added. Attach the master and detail.
        if (state.getStack().size() == 0) {
            FragmentManager childFragManager = navMgrFragment.getRetainedChildFragmentManager();
            FragmentTransaction childFragTrans = childFragManager.beginTransaction();
            if (state.isTablet()) {
                // Add in the new fragment that we are presenting and add it's navigation tag to the stack.
                childFragTrans.add(R.id.navigation_manager_container_master, (Fragment) config.getMasterFragment(), config.getMasterFragment().getNavTag());
                navMgrFragment.addFragmentToStack(config.getMasterFragment());
            }
            else {
                navMgrFragment.pushFragment(config.getMasterFragment());
            }
            childFragTrans.commit();

            if (state.isTablet()) {
                navMgrFragment.pushFragment(config.getDetailFragment());
            }

            config.nullifyInitialFragments();
        }
        // Fragments are in the stack, resume at the top.
        else {
            FragmentManager childFragManager = navMgrFragment.getRetainedChildFragmentManager();
            FragmentTransaction childFragTrans = childFragManager.beginTransaction();
            childFragTrans.setCustomAnimations(ManagerConfig.NO_ANIMATION, ManagerConfig.NO_ANIMATION);
            if (state.isTablet()) {
                childFragTrans.attach(childFragManager.findFragmentByTag(state.getStack().firstElement()));
            }
            childFragTrans.attach(childFragManager.findFragmentByTag(state.getStack().peek()));
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
    public void onViewCreated(View view, State state, Config config) {
        state.isTablet(view.findViewById(R.id.navigation_manager_tablet_land) != null
                || view.findViewById(R.id.navigation_manager_tablet_portrait) != null);
        state.isPortrait(view.findViewById(R.id.navigation_manager_phone_portrait) != null
                || view.findViewById(R.id.navigation_manager_tablet_portrait) != null);

        config.setMinStackSize(state.isTablet() ? MASTER_DETAIL_TABLET_MIN_ACTION_SIZE : MASTER_DETAIL_PHONE_MIN_ACTION_SIZE);
        config.setPushContainerId(R.id.navigation_manager_fragment_container);
    }

    @Override
    public void onPause(NavigationManagerFragment navMgrFragment, State state) {

        FragmentManager childFragManager = navMgrFragment.getRetainedChildFragmentManager();
        FragmentTransaction childFragTrans = childFragManager.beginTransaction();
        childFragTrans.setCustomAnimations(ManagerConfig.NO_ANIMATION, ManagerConfig.NO_ANIMATION);
        if (state.isTablet()) {
            childFragTrans.detach(childFragManager.findFragmentByTag(state.getStack().firstElement()));
        }
        childFragTrans.detach(childFragManager.findFragmentByTag(state.getStack().peek()));
        childFragTrans.commit();

    }

}
