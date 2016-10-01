package com.dmcapps.navigationfragment.v17.core;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dmcapps.navigationfragment.R;
import com.dmcapps.navigationfragment.common.core.CofigManager;
import com.dmcapps.navigationfragment.common.helpers.utils.NavigationManagerUtils;
import com.dmcapps.navigationfragment.common.interfaces.Config;
import com.dmcapps.navigationfragment.common.interfaces.Lifecycle;
import com.dmcapps.navigationfragment.common.interfaces.NavigationManager;
import com.dmcapps.navigationfragment.common.interfaces.State;

/**
 * Created by dcarmo on 2016-02-24.
 */
public class StackLifecycleManager implements Lifecycle {

    private static final int SINGLE_STACK_MIN_ACTION_SIZE = 1;

    @Override
    public void onResume(NavigationManager navigationManager) {
        State state = navigationManager.getState();
        Config config = navigationManager.getConfig();

        // No Fragments have been added. Attach the root.
        if (state.getStack().size() == 0) {
            navigationManager.pushFragment(config.getRootFragment());
        }
        // Fragments are in the stack, resume at the top.
        else {
            FragmentManager childFragManager = NavigationManagerUtils.getFragmentManager(navigationManager);
            FragmentTransaction childFragTrans = childFragManager.beginTransaction();
            childFragTrans.setCustomAnimations(CofigManager.NO_ANIMATION, CofigManager.NO_ANIMATION);
            childFragTrans.attach(childFragManager.findFragmentByTag(state.getStack().peek()));
            childFragTrans.commit();
        }

        config.nullifyInitialFragments();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_navigation_manager, container, false);
    }

    @Override
    public void onViewCreated(View view, NavigationManager navigationManager) {
        State state = navigationManager.getState();
        Config config = navigationManager.getConfig();

        state.isTablet(view.findViewById(R.id.navigation_manager_tablet_land) != null
                || view.findViewById(R.id.navigation_manager_tablet_portrait) != null);
        state.isPortrait(view.findViewById(R.id.navigation_manager_phone_portrait) != null
                || view.findViewById(R.id.navigation_manager_tablet_portrait) != null);

        config.setMinStackSize(SINGLE_STACK_MIN_ACTION_SIZE);
        config.setPushContainerId(R.id.navigation_manager_fragment_container);

        if (view.findViewById(R.id.navigation_manager_container_master) != null) {
            view.findViewById(R.id.navigation_manager_container_master).setVisibility(View.GONE);
        }
    }

    @Override
    public void onPause(NavigationManager navigationManager) {
        State state = navigationManager.getState();

        FragmentManager childFragManager = NavigationManagerUtils.getFragmentManager(navigationManager);
        FragmentTransaction childFragTrans = childFragManager.beginTransaction();
        childFragTrans.setCustomAnimations(CofigManager.NO_ANIMATION, CofigManager.NO_ANIMATION);
        childFragTrans.detach(childFragManager.findFragmentByTag(state.getStack().peek()));
        childFragTrans.commit();
    }
}
