package com.dmcapps.navigationfragment.supportv7.manager;

import android.annotation.SuppressLint;

import com.dmcapps.navigationfragment.common.INavigationFragment;
import com.dmcapps.navigationfragment.supportv7.manager.core.SupportNavigationManagerFragment;
import com.dmcapps.navigationfragment.supportv7.manager.core.micromanagers.lifecycle.StackLifecycleManager;


/**
 * This Fragment manages the stack of single navigation on fragments.
 * The class allows for easy adding and removing of fragments as the user
 * traverses the screens. A self-contained class that requires no resources
 * in order to function. Each time a new manager is made a separate stack will be created
 * and no overlap will occur in the class.
 */
@SuppressLint("ValidFragment")
public class StackNavigationManagerFragment extends SupportNavigationManagerFragment {
    private static final String TAG = StackNavigationManagerFragment.class.getSimpleName();

    public static StackNavigationManagerFragment newInstance(INavigationFragment rootFragment) {
        return new StackNavigationManagerFragment(rootFragment);
    }

    public StackNavigationManagerFragment() {}

    // NOTE I need to pass in the fragments to be used by the constructor.
    // If I serialize them into the bundle then whenever the application is backgrounded
    // or an activity is launched, the application will crash with NotSerializableException
    // if any of the Fragments in the stack have properties that are no Serializable.
    public StackNavigationManagerFragment(INavigationFragment rootFragment) {
        mConfig.rootFragment = rootFragment;
        mLifecycleManager = new StackLifecycleManager();
    }
}
