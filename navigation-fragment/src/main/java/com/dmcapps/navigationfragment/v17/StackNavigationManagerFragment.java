package com.dmcapps.navigationfragment.v17;

import com.dmcapps.navigationfragment.common.interfaces.Config;
import com.dmcapps.navigationfragment.common.interfaces.Lifecycle;
import com.dmcapps.navigationfragment.common.interfaces.Navigation;
import com.dmcapps.navigationfragment.common.micromanagers.ManagerConfig;
import com.dmcapps.navigationfragment.support.v7.manager.core.micromanagers.lifecycle.StackLifecycleManager;

/**
 * Created by dcarmo on 2016-09-29.
 */
public class StackNavigationManagerFragment extends NavigationManagerFragment {

    public static NavigationManagerFragment newInstance(Navigation fragment) {
        NavigationManagerFragment navigationManagerFragment = new StackNavigationManagerFragment();

        Config config = new ManagerConfig();
        config.setRootFragment(fragment);

        navigationManagerFragment.setConfig(config);
        navigationManagerFragment.setLifecycle(new StackLifecycleManager());

        return navigationManagerFragment;
    }

    public StackNavigationManagerFragment() { }

}
