package com.github.dmcapps.navigationfragment.v17;

import android.annotation.TargetApi;
import android.os.Build;

import com.github.dmcapps.navigationfragment.R;
import com.github.dmcapps.navigationfragment.common.core.NavigationConfig;
import com.github.dmcapps.navigationfragment.common.core.NavigationManager;
import com.github.dmcapps.navigationfragment.common.core.StackLifecycleManager;
import com.github.dmcapps.navigationfragment.common.core.StateManager;
import com.github.dmcapps.navigationfragment.common.interfaces.Navigation;
import com.github.dmcapps.navigationfragment.common.core.StackManager;

/**
 * Created by dcarmo on 2016-09-29.
 */
@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
public class StackNavigationManagerFragment extends NavigationManagerFragment {

    private static final int SINGLE_STACK_MIN_ACTION_SIZE = 1;

    public static StackNavigationManagerFragment newInstance(Navigation fragment) {
        StackNavigationManagerFragment container = new StackNavigationManagerFragment();

        NavigationConfig config = NavigationConfig.builder()
                .setMinStackSize(SINGLE_STACK_MIN_ACTION_SIZE)
                .setPushContainerId(R.id.navigation_manager_fragment_container)
                .build();

        NavigationManager manager = new NavigationManager();
        manager.addInitialNavigation(fragment);
        manager.setNavigationConfig(config);
        manager.setLifecycle(new StackLifecycleManager());
        manager.setStack(new StackManager());
        manager.setState(new StateManager());

        container.setNavigationManager(manager);

        return container;
    }

    public StackNavigationManagerFragment() { }

}
