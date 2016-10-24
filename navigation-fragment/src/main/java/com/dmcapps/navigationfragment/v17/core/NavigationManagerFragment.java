package com.dmcapps.navigationfragment.v17.core;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dmcapps.navigationfragment.common.core.ConfigManager;
import com.dmcapps.navigationfragment.common.core.StateManager;
import com.dmcapps.navigationfragment.common.interfaces.Config;
import com.dmcapps.navigationfragment.common.interfaces.Lifecycle;
import com.dmcapps.navigationfragment.common.interfaces.Navigation;
import com.dmcapps.navigationfragment.common.interfaces.NavigationManager;
import com.dmcapps.navigationfragment.common.interfaces.Stack;
import com.dmcapps.navigationfragment.common.interfaces.State;
import com.dmcapps.navigationfragment.v17.fragments.NavigationFragment;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
public class NavigationManagerFragment extends Fragment implements NavigationManager<NavigationFragment> {
    // TODO: Animation making child disappear http://stackoverflow.com/a/23276145/845038
    private static final String TAG = NavigationManagerFragment.class.getSimpleName();

    private static final String KEY_MANAGER_CONFIG = "KEY_MANAGER_CONFIG";
    private static final String KEY_MANAGER_STATE = "KEY_MANAGER_STATE";
    private static final String KEY_MANAGER_STACK_MANAGER = "KEY_MANAGER_STACK_MANAGER";
    private static final String KEY_LIFECYCLE_MANAGER = "KEY_LIFECYCLE_MANAGER";

    private NavigationManagerFragmentListener mListener;

    private Lifecycle mLifecycle;
    private Config mConfig;
    private State mState;
    private Stack<NavigationFragment> mStack;

    public interface NavigationManagerFragmentListener {
        void didPresentFragment();
        void didDismissFragment();
    }

    public NavigationManagerFragment() { }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            // This is not mandatory. Only if the user wants to listen for push and pop events.
            mListener = (NavigationManagerFragmentListener)context;
        }
        catch (ClassCastException classCastException) {
            Log.i(TAG, "Activity does not implement NavigationManagerFragmentListener. It is not required but may be helpful for listening to present and dismiss events.");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            mLifecycle = (Lifecycle)savedInstanceState.getSerializable(KEY_LIFECYCLE_MANAGER);
            mConfig = (ConfigManager)savedInstanceState.getSerializable(KEY_MANAGER_CONFIG);
            mStack = (StackManager)savedInstanceState.getSerializable(KEY_MANAGER_STACK_MANAGER);
            mState = (StateManager)savedInstanceState.getSerializable(KEY_MANAGER_STATE);
        }
        else if (mLifecycle == null || mConfig == null || mStack == null || mState == null) {
            throw new RuntimeException("Your NavigationManagerFragment must call setLifecycle, setConfig, setStack, setState before onCreate()");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return mLifecycle.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLifecycle.onViewCreated(view, this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mLifecycle.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        mLifecycle.onPause(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable(KEY_LIFECYCLE_MANAGER, mLifecycle);
        outState.putSerializable(KEY_MANAGER_CONFIG, mConfig);
        outState.putSerializable(KEY_MANAGER_STACK_MANAGER, mStack);
        outState.putSerializable(KEY_MANAGER_STATE, mState);
    }

    @Override
    public void setStack(Stack stack) {
        mStack = stack;
    }

    @Override
    public Stack getStack() {
        return mStack;
    }

    @Override
    public void setConfig(Config config) {
        mConfig = config;
    }

    @Override
    public Config getConfig() {
        return mConfig;
    }

    @Override
    public void setState(State state) {
        mState = state;
    }

    @Override
    public State getState() {
        return mState;
    }

    @Override
    public void setLifecycle(Lifecycle lifecycle) {
        mLifecycle = lifecycle;
    }

    @Override
    public Lifecycle getLifecycle() {
        return mLifecycle;
    }

    @Override
    public FragmentManager getNavigationFragmentManager() {
        return getChildFragmentManager();
    }

    @Override
    public void setDefaultPresentAnimations(int animIn, int animOut) {
        mConfig.setDefaultPresetAnim(animIn, animOut);
    }

    @Override
    public void setDefaultDismissAnimations(int animIn, int animOut) {
        mConfig.setDefaultDismissAnim(animIn, animOut);
    }

    @Override
    public void overrideNextAnimation(int animIn, int animOut) {
        mConfig.setNextAnim(animIn, animOut);
    }

    @Override
    public void pushFragment(NavigationFragment navFragment) {
        mStack.pushFragment(this, navFragment);

        if (mListener != null) {
            mListener.didPresentFragment();
        }
    }

    @Override
    public void pushFragment(NavigationFragment navFragment, Bundle navBundle) {
        mStack.pushFragment(this, navFragment, navBundle);

        if (mListener != null) {
            mListener.didPresentFragment();
        }
    }

    @Override
    public void popFragment() {
        mStack.popFragment(this);

        if (mListener != null) {
            mListener.didDismissFragment();
        }
    }

    @Override
    public void popFragment(Bundle navBundle) {
        mStack.popFragment(this, navBundle);

        if (mListener != null) {
            mListener.didDismissFragment();
        }
    }

    @Override
    public void addToStack(NavigationFragment navFragment) {
        mState.getStack().add(navFragment.getNavTag());
    }

    @Override
    public NavigationFragment getTopFragment() {
        if (mState.getStack().size() > 0) {
            return getFragmentAtIndex(mState.getStack().size() - 1);
        }
        else {
            Log.e(TAG, "No fragments in the navigation stack, returning null.");
            return null;
        }
    }

    @Override
    public NavigationFragment getRootFragment() {
        return getFragmentAtIndex(0);
    }

    @Override
    public NavigationFragment getFragmentAtIndex(int index) {
        if (mState.getStack().size() > index) {
            return mStack.getFragmentAtIndex(this, index);
        }
        else {
            Log.e(TAG, "No fragment at that position in the navigation stack, returning null. (Stack size: " + mState.getStack().size() + ". Index attempted: " + index + ".");
            return null;
        }
    }

    @Override
    public boolean onBackPressed() {
        if (mState.getStack().size() > mConfig.getMinStackSize()) {
            popFragment();
            return true;
        }

        return false;
    }

    @Override
    public void replaceRootFragment(NavigationFragment navFragment) {
        clearNavigationStackToPosition(mConfig.getMinStackSize() - 1);
        pushFragment(navFragment);
    }

    @Override
    public void clearNavigationStackToRoot() {
        clearNavigationStackToPosition(mConfig.getMinStackSize());
    }

    @Override
    public void clearNavigationStackToPosition(int stackPosition) {
        mStack.clearNavigationStackToPosition(this, stackPosition);
    }

    @Override
    public boolean isOnRootFragment() {
        return mState.getStack().size() == mConfig.getMinStackSize();
    }

    @Override
    public int getCurrentStackSize() {
        return mState.getStack().size();
    }

    // ===============================
    // START DEVICE STATE METHODS
    // ===============================

    @Override
    public boolean isPortrait() {
        return mState.isPortrait();
    }

    @Override
    public boolean isTablet() {
        return mState.isTablet();
    }

    // ===============================
    // END DEVICE STATE METHODS
    // ===============================
}
