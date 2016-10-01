package com.dmcapps.navigationfragment.v7.core.lifecycle;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dmcapps.navigationfragment.R;
import com.dmcapps.navigationfragment.common.helpers.utils.NavigationManagerUtils;
import com.dmcapps.navigationfragment.common.interfaces.Config;
import com.dmcapps.navigationfragment.common.interfaces.Lifecycle;
import com.dmcapps.navigationfragment.common.interfaces.Navigation;
import com.dmcapps.navigationfragment.common.interfaces.NavigationManager;
import com.dmcapps.navigationfragment.common.core.ConfigManager;
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
            if (config.getInitialNavigation().size() < 1) {
                throw new RuntimeException("StackNavigationManagerFragment requires an initial Navigation components. On your config please call addInitialNavigation(Navigation) in order to add your initial navigation components.");
            }

            Navigation rootFragment = config.getInitialNavigation().get(0);
            navigationManager.pushFragment(rootFragment);
        }
        // Fragments are in the stack, resume at the top.
        else {
            FragmentManager childFragManager = NavigationManagerUtils.getSupportFragmentManager(navigationManager);
            FragmentTransaction childFragTrans = childFragManager.beginTransaction();
            childFragTrans.setCustomAnimations(ConfigManager.NO_ANIMATION, ConfigManager.NO_ANIMATION);
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

        FragmentManager childFragManager = NavigationManagerUtils.getSupportFragmentManager(navigationManager);
        FragmentTransaction childFragTrans = childFragManager.beginTransaction();
        childFragTrans.setCustomAnimations(ConfigManager.NO_ANIMATION, ConfigManager.NO_ANIMATION);
        childFragTrans.detach(childFragManager.findFragmentByTag(state.getStack().peek()));
        childFragTrans.commit();
    }
}
