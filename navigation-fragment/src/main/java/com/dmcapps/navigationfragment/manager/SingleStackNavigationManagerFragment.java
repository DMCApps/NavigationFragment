package com.dmcapps.navigationfragment.manager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dmcapps.navigationfragment.R;
import com.dmcapps.navigationfragment.fragments.INavigationFragment;
import com.dmcapps.navigationfragment.manager.micromanagers.lifecycle.SingleStackLifecycleManager;


/**
 * This Fragment manages the stack of single navigation on fragments.
 * The class allows for easy adding and removing of fragments as the user
 * traverses the screens. A self-contained class that requires no resources
 * in order to function. Each time a new manager is made a separate stack will be created
 * and no overlap will occur in the class.
 */
@SuppressLint("ValidFragment")
public class SingleStackNavigationManagerFragment extends NavigationManagerFragment {
    private static final String TAG = SingleStackNavigationManagerFragment.class.getSimpleName();

    private static final int ACTIONABLE_STACK_SIZE = 1;

    public static SingleStackNavigationManagerFragment newInstance(INavigationFragment rootFragment) {
        return new SingleStackNavigationManagerFragment(rootFragment);
    }

    public SingleStackNavigationManagerFragment() {

    }

    public SingleStackNavigationManagerFragment(INavigationFragment rootFragment) {
        mConfig.rootFragment = rootFragment;
        mLifecycleManager = new SingleStackLifecycleManager();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mState.isTablet = view.findViewById(R.id.single_stack_tablet_layout_main_portrait) != null
                || view.findViewById(R.id.single_stack_tablet_layout_main_land) != null;
        mState.isPortrait = view.findViewById(R.id.single_stack_phone_layout_main_portrait) != null
                || view.findViewById(R.id.single_stack_tablet_layout_main_portrait) != null;

        mConfig.minStackSize = ACTIONABLE_STACK_SIZE;
        mConfig.pushContainerId = R.id.single_stack_content;
    }
}
