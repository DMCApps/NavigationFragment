package com.github.dmcapps.navigationfragment.common.core;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.dmcapps.navigationfragment.R;
import com.github.dmcapps.navigationfragment.common.interfaces.Lifecycle;
import com.github.dmcapps.navigationfragment.common.interfaces.Navigation;
import com.github.dmcapps.navigationfragment.common.interfaces.State;
import com.github.dmcapps.navigationfragment.v7.NavigationManagerFragment;

/**
 * Created by dcarmo on 2016-11-19.
 */

public class StackLifecycleManager implements Lifecycle {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_navigation_manager, container, false);
    }

    @Override
    public void onViewCreated(View view, NavigationManager navigationManager) {
        State state = navigationManager.getState();

        state.isTablet(view.findViewById(R.id.navigation_manager_tablet_land) != null
                || view.findViewById(R.id.navigation_manager_tablet_portrait) != null);
        state.isPortrait(view.findViewById(R.id.navigation_manager_phone_portrait) != null
                || view.findViewById(R.id.navigation_manager_tablet_portrait) != null);

        if (view.findViewById(R.id.navigation_manager_container_master) != null) {
            view.findViewById(R.id.navigation_manager_container_master).setVisibility(View.GONE);
        }
    }

    @Override
    public void onResume(NavigationManager navigationManager) {
        State state = navigationManager.getState();

        // No Fragments have been added. Attach the root.
        if (state.getStack().size() == 0) {
            if (navigationManager.getInitialNavigation().size() < 1) {
                throw new RuntimeException("StackNavigationManagerFragment requires an initial Navigation components. On your config please call addInitialNavigation(Navigation) in order to add your initial navigation component(s).");
            }

            Navigation rootFragment = navigationManager.getInitialNavigation().get(0);
            navigationManager.presentFragment(rootFragment);
        }
        // Check if using support version
        // Fragments are in the stack, resume at the top.
        /*else if (navigationManager.getContainer() != null && navigationManager.getContainer() instanceof NavigationManagerFragment) {
            FragmentManagerWrapper fragmentManager = new FragmentManagerWrapper(navigationManager.getContainer().getNavChildFragmentManager());
            FragmentTransactionWrapper fragmentTransaction = fragmentManager.beginTransactionWrapped();
            fragmentTransaction.setCustomAnimations(0, 0);
            fragmentTransaction.attach(fragmentManager.findFragmentByTag(state.getStack().peek()));
            fragmentTransaction.commit();
        }*/

        navigationManager.nullifyInitialNavigation();
    }

    @Override
    public void onPause(NavigationManager navigationManager) {
        State state = navigationManager.getState();

        // Check if using support version
        /*if (navigationManager.getContainer() != null && navigationManager.getContainer() instanceof NavigationManagerFragment) {
            FragmentManagerWrapper fragmentManager = new FragmentManagerWrapper(navigationManager.getContainer().getNavChildFragmentManager());
            FragmentTransactionWrapper fragmentTransaction = fragmentManager.beginTransactionWrapped();
            fragmentTransaction.setCustomAnimations(0, 0);
            fragmentTransaction.detach(fragmentManager.findFragmentByTag(state.getStack().peek()));
            fragmentTransaction.commit();
        }*/
    }
}
