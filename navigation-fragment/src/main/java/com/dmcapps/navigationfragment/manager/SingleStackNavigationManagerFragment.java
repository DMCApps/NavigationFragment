package com.dmcapps.navigationfragment.manager;

import android.annotation.SuppressLint;

import com.dmcapps.navigationfragment.fragments.INavigationFragment;
import com.dmcapps.navigationfragment.manager.core.NavigationManagerFragment;
import com.dmcapps.navigationfragment.manager.core.micromanagers.lifecycle.SingleStackLifecycleManager;


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

    public static SingleStackNavigationManagerFragment newInstance(INavigationFragment rootFragment) {
        return new SingleStackNavigationManagerFragment(rootFragment);
    }

    public SingleStackNavigationManagerFragment() {}

    // NOTE I need to pass in the fragments to be used by the constructor.
    // If I serialize them into the bundle then whenever the application is backgrounded
    // or an activity is launched, the application will crash with NotSerializableException
    // if any of the Fragments in the stack have properties that are no Serializable.
    public SingleStackNavigationManagerFragment(INavigationFragment rootFragment) {
        mConfig.rootFragment = rootFragment;
        mLifecycleManager = new SingleStackLifecycleManager();
    }
}
