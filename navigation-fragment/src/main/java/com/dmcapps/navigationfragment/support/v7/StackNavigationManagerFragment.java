package com.dmcapps.navigationfragment.support.v7;

import com.dmcapps.navigationfragment.common.interfaces.Config;
import com.dmcapps.navigationfragment.common.interfaces.Navigation;
import com.dmcapps.navigationfragment.common.core.CofigManager;
import com.dmcapps.navigationfragment.common.core.StateManager;
import com.dmcapps.navigationfragment.support.v7.core.NavigationManagerFragment;
import com.dmcapps.navigationfragment.support.v7.core.StackManager;
import com.dmcapps.navigationfragment.support.v7.core.lifecycle.StackLifecycleManager;

/**
 * This Fragment manages the stack of single navigation on fragments.
 * The class allows for easy adding and removing of fragments as the user
 * traverses the screens. A self-contained class that requires no resources
 * in order to function. Each time a new manager is made a separate stack will be created
 * and no overlap will occur in the class.
 */
public class StackNavigationManagerFragment extends NavigationManagerFragment {
    private static final String TAG = StackNavigationManagerFragment.class.getSimpleName();

    public static StackNavigationManagerFragment newInstance(Navigation fragment) {
        StackNavigationManagerFragment navigationManagerFragment = new StackNavigationManagerFragment();

        Config config = new CofigManager();
        config.addInitialNavigation(fragment);

        navigationManagerFragment.setConfig(config);
        navigationManagerFragment.setLifecycle(new StackLifecycleManager());
        navigationManagerFragment.setStack(new StackManager());
        navigationManagerFragment.setState(new StateManager());

        return navigationManagerFragment;
    }

    public StackNavigationManagerFragment() { }
}