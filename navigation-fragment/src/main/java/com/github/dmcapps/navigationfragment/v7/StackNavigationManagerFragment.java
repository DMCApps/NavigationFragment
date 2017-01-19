package com.github.dmcapps.navigationfragment.v7;

import com.github.dmcapps.navigationfragment.R;
import com.github.dmcapps.navigationfragment.common.core.NavigationConfig;
import com.github.dmcapps.navigationfragment.common.core.NavigationManager;
import com.github.dmcapps.navigationfragment.common.core.StackLifecycleManager;
import com.github.dmcapps.navigationfragment.common.core.StackManager;
import com.github.dmcapps.navigationfragment.common.core.StateManager;
import com.github.dmcapps.navigationfragment.common.interfaces.Navigation;

/**
 * This Fragment manages the stack of single navigation on fragments.
 * The class allows for easy adding and removing of fragments as the user
 * traverses the screens. A self-contained class that requires no resources
 * in order to function. Each time a new manager is made a separate stack will be created
 * and no overlap will occur in the class.
 */
public class StackNavigationManagerFragment extends NavigationManagerFragment {
    private static final String TAG = StackNavigationManagerFragment.class.getSimpleName();

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
