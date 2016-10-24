package com.dmcapps.navigationfragment.v17;

import android.annotation.TargetApi;
import android.os.Build;

import com.dmcapps.navigationfragment.common.core.StateManager;
import com.dmcapps.navigationfragment.common.interfaces.Config;
import com.dmcapps.navigationfragment.common.interfaces.Navigation;
import com.dmcapps.navigationfragment.v17.core.ConfigManager;
import com.dmcapps.navigationfragment.v17.core.NavigationManagerFragment;
import com.dmcapps.navigationfragment.v17.core.StackLifecycleManager;
import com.dmcapps.navigationfragment.v17.core.StackManager;

/**
 * Created by dcarmo on 2016-09-29.
 */
@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
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
