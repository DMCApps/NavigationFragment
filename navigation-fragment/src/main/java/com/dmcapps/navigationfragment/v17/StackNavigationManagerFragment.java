package com.dmcapps.navigationfragment.v17;

import android.annotation.TargetApi;
import android.os.Build;

import com.dmcapps.navigationfragment.common.core.ConfigManager;
import com.dmcapps.navigationfragment.common.core.NavigationManagerCore;
import com.dmcapps.navigationfragment.common.core.StackLifecycleManager;
import com.dmcapps.navigationfragment.common.core.StateManager;
import com.dmcapps.navigationfragment.common.interfaces.Config;
import com.dmcapps.navigationfragment.common.interfaces.Navigation;
import com.dmcapps.navigationfragment.common.core.StackManager;
import com.dmcapps.navigationfragment.v17.fragments.NavigationManagerFragment;

/**
 * Created by dcarmo on 2016-09-29.
 */
@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
public class StackNavigationManagerFragment extends NavigationManagerFragment {

    public static StackNavigationManagerFragment newInstance(Navigation fragment) {
        StackNavigationManagerFragment container = new StackNavigationManagerFragment();

        Config config = new ConfigManager();
        config.addInitialNavigation(fragment);
        config.setDefaultPresetAnim(null, null);
        config.setDefaultDismissAnim(null, null);

        container.setNavigationManager(new NavigationManagerCore());
        container.getNavigationManager().setConfig(config);
        container.getNavigationManager().setLifecycle(new StackLifecycleManager());
        container.getNavigationManager().setStack(new StackManager());
        container.getNavigationManager().setState(new StateManager());

        return container;
    }

    public StackNavigationManagerFragment() { }

}
