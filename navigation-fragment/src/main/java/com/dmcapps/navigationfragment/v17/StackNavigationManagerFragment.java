package com.dmcapps.navigationfragment.v17;

import com.dmcapps.navigationfragment.common.core.StackLifecycleManager;
import com.dmcapps.navigationfragment.common.core.StateManager;
import com.dmcapps.navigationfragment.common.interfaces.Config;
import com.dmcapps.navigationfragment.common.interfaces.Navigation;
import com.dmcapps.navigationfragment.common.core.StackManager;
import com.dmcapps.navigationfragment.v17.core.ConfigManager;
import com.dmcapps.navigationfragment.v17.core.NavigationManagerFragment;

/**
 * Created by dcarmo on 2016-09-29.
 */
public class StackNavigationManagerFragment extends NavigationManagerFragment {

    public static StackNavigationManagerFragment newInstance(Navigation fragment) {
        StackNavigationManagerFragment navigationManagerFragment = new StackNavigationManagerFragment();

        Config config = new ConfigManager();
        config.addInitialNavigation(fragment);

        navigationManagerFragment.setConfig(config);
        navigationManagerFragment.setLifecycle(new StackLifecycleManager());
        navigationManagerFragment.setStack(new StackManager());
        navigationManagerFragment.setState(new StateManager());

        return navigationManagerFragment;
    }

    public StackNavigationManagerFragment() { }

}
