package com.dmcapps.navigationfragment.v17.manager;

import com.dmcapps.navigationfragment.common.core.CofigManager;
import com.dmcapps.navigationfragment.common.core.StateManager;
import com.dmcapps.navigationfragment.common.interfaces.Config;
import com.dmcapps.navigationfragment.common.interfaces.Navigation;
import com.dmcapps.navigationfragment.v17.manager.core.NavigationManagerFragment;
import com.dmcapps.navigationfragment.v17.manager.core.StackLifecycleManager;
import com.dmcapps.navigationfragment.v17.manager.core.StackManager;

/**
 * Created by dcarmo on 2016-09-29.
 */
public class StackNavigationManagerFragment extends NavigationManagerFragment {

    public static NavigationManagerFragment newInstance(Navigation fragment) {
        NavigationManagerFragment navigationManagerFragment = new StackNavigationManagerFragment();

        Config config = new CofigManager();
        config.setRootFragment(fragment);

        navigationManagerFragment.setConfig(config);
        navigationManagerFragment.setLifecycle(new StackLifecycleManager());
        navigationManagerFragment.setStack(new StackManager());
        navigationManagerFragment.setState(new StateManager());

        return navigationManagerFragment;
    }

    public StackNavigationManagerFragment() { }

}
