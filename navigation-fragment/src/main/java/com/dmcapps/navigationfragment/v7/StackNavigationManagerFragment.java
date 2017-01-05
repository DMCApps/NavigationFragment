package com.dmcapps.navigationfragment.v7;

import com.dmcapps.navigationfragment.R;
import com.dmcapps.navigationfragment.common.core.ConfigManager;
import com.dmcapps.navigationfragment.common.core.NavigationManager;
import com.dmcapps.navigationfragment.common.core.StackLifecycleManager;
import com.dmcapps.navigationfragment.common.core.StackManager;
import com.dmcapps.navigationfragment.common.core.StateManager;
import com.dmcapps.navigationfragment.common.interfaces.Config;
import com.dmcapps.navigationfragment.common.interfaces.Navigation;

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
        StackNavigationManagerFragment container = new StackNavigationManagerFragment();

        Config config = new ConfigManager();
        config.addInitialNavigation(fragment);
        config.setDefaultPresetAnim(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
        config.setDefaultDismissAnim(R.anim.slide_in_from_left, R.anim.slide_out_to_right);

        container.setNavigationManager(new NavigationManager());
        container.getNavigationManager().setConfig(config);
        container.getNavigationManager().setLifecycle(new StackLifecycleManager());
        container.getNavigationManager().setStack(new StackManager());
        container.getNavigationManager().setState(new StateManager());

        return container;
    }

    public StackNavigationManagerFragment() { }
}
