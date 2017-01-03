package com.dmcapps.navigationfragment.v17.fragments;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dmcapps.navigationfragment.common.helpers.utils.ObjectUtils;
import com.dmcapps.navigationfragment.common.interfaces.NavigationManager;
import com.dmcapps.navigationfragment.common.interfaces.NavigationManagerContainer;
import com.dmcapps.navigationfragment.common.interfaces.NavigationManagerListener;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
public class NavigationManagerFragment extends Fragment implements NavigationManagerContainer {
    // TODO: Animation making child disappear http://stackoverflow.com/a/23276145/845038
    private static final String TAG = NavigationManagerFragment.class.getSimpleName();

    private static final String KEY_NAVIGATION_MANAGER_CORE = "KEY_NAVIGATION_MANAGER_CORE";

    private NavigationManager mNavigationManager;

    // =========================================
    // Object Lifecycle
    // =========================================

    public NavigationManagerFragment() {}

    // =========================================
    // Fragment Lifecycle
    // =========================================

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            mNavigationManager = (NavigationManager)savedInstanceState.getSerializable(KEY_NAVIGATION_MANAGER_CORE);
        }

        mNavigationManager.setNavigationListener(ObjectUtils.as(NavigationManagerListener.class, getActivity()));
        mNavigationManager.setContainer(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return mNavigationManager.getLifecycle().onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mNavigationManager.getLifecycle().onViewCreated(view, mNavigationManager);
    }

    @Override
    public void onResume() {
        super.onResume();
        mNavigationManager.getLifecycle().onResume(mNavigationManager);
    }

    @Override
    public void onPause() {
        super.onPause();
        mNavigationManager.getLifecycle().onPause(mNavigationManager);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable(KEY_NAVIGATION_MANAGER_CORE, mNavigationManager);
    }

    // =========================================
    // NavigationManagerContainer
    // =========================================

    @Override
    public NavigationManager getNavigationManager() {
        return mNavigationManager;
    }

    @Override
    public Object getNavChildFragmentManager() {
        return getChildFragmentManager();
    }

    @Override
    public Activity getFragmentActivity() {
        return getActivity();
    }

    // =========================================
    // Private
    // =========================================

    protected void setNavigationManager(NavigationManager navigationManager) {
        mNavigationManager = navigationManager;
    }

//    private static final String TAG = NavigationManagerFragment.class.getSimpleName();
//
//    private static final String KEY_MANAGER_CONFIG = "KEY_MANAGER_CONFIG";
//    private static final String KEY_MANAGER_STATE = "KEY_MANAGER_STATE";
//    private static final String KEY_MANAGER_STACK_MANAGER = "KEY_MANAGER_STACK_MANAGER";
//    private static final String KEY_LIFECYCLE_MANAGER = "KEY_LIFECYCLE_MANAGER";
//
//    private NavigationManagerListener mListener;
//
//    private Lifecycle mLifecycle;
//    private Config mConfig;
//    private State mState;
//    private Stack mStack;
//
//    public NavigationManagerFragment() { }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//
//        try {
//            // This is not mandatory. Only if the user wants to listen for push and pop events.
//            mListener = (NavigationManagerListener)context;
//        }
//        catch (ClassCastException classCastException) {
//            Log.i(TAG, "Activity does not implement NavigationManagerFragmentListener. It is not required but may be helpful for listening to present and dismiss events.");
//        }
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        if (savedInstanceState != null) {
//            mLifecycle = (Lifecycle)savedInstanceState.getSerializable(KEY_LIFECYCLE_MANAGER);
//            mConfig = (ConfigManager)savedInstanceState.getSerializable(KEY_MANAGER_CONFIG);
//            mStack = (StackManager)savedInstanceState.getSerializable(KEY_MANAGER_STACK_MANAGER);
//            mState = (StateManager)savedInstanceState.getSerializable(KEY_MANAGER_STATE);
//        }
//        else if (mLifecycle == null || mConfig == null || mStack == null || mState == null) {
//            throw new RuntimeException("Your NavigationManagerFragment must call setLifecycle, setConfig, setStack, setState before onCreate()");
//        }
//
//        // TODO: Remove this it's ugly. See notes in NavigationSettings for ideas
//        NavigationSettings.setDefaultConfig(mConfig);
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        return mLifecycle.onCreateView(inflater, container, savedInstanceState);
//    }
//
//    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        mLifecycle.onViewCreated(view, this);
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        mLifecycle.onResume(this);
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        mLifecycle.onPause(this);
//    }
//
//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//
//        outState.putSerializable(KEY_LIFECYCLE_MANAGER, mLifecycle);
//        outState.putSerializable(KEY_MANAGER_CONFIG, mConfig);
//        outState.putSerializable(KEY_MANAGER_STACK_MANAGER, mStack);
//        outState.putSerializable(KEY_MANAGER_STATE, mState);
//    }
//
//    @Override
//    public void setNavigationListener(NavigationManagerListener listener) {
//
//    }
//
//    @Override
//    public void setStack(Stack stack) {
//        mStack = stack;
//    }
//
//    @Override
//    public Stack getStack() {
//        return mStack;
//    }
//
//    @Override
//    public void setConfig(Config config) {
//        mConfig = config;
//    }
//
//    @Override
//    public Config getConfig() {
//        return mConfig;
//    }
//
//    @Override
//    public void setState(State state) {
//        mState = state;
//    }
//
//    @Override
//    public State getState() {
//        return mState;
//    }
//
//    @Override
//    public void setLifecycle(Lifecycle lifecycle) {
//        mLifecycle = lifecycle;
//    }
//
//    @Override
//    public Lifecycle getLifecycle() {
//        return mLifecycle;
//    }
//
//    /**
//     * Get the current {@link FragmentManager} from the {@link NavigationManager}
//     *
//     * @return
//     *      Returns the Child Fragment Manager of the current fragment
//     */
//    @Override
//    public Object getNavChildFragmentManager() {
//        return getChildFragmentManager();
//    }
//
//    /**
//     * Overrides the default present animations for all present actions on the fragment manager.
//     *
//     * @param
//     *      animIn -> Present animation in
//     * @param
//     *      animOut -> Present animation out
//     */
//    @Override
//    public void setDefaultPresentAnimations(int animIn, int animOut) {
//        mConfig.setDefaultPresetAnim(animIn, animOut);
//        // TODO: Remove this it's ugly. See notes in NavigationSettings for ideas
//        NavigationSettings.setDefaultConfig(mConfig);
//    }
//
//    /**
//     * Overrides the default dismiss animations for all dismiss actions on the fragment manager.
//     *
//     * @param
//     *      animIn -> Dismiss animation in
//     * @param
//     *      animOut -> Dismiss animation out
//     */
//    @Override
//    public void setDefaultDismissAnimations(int animIn, int animOut) {
//        mConfig.setDefaultDismissAnim(animIn, animOut);
//        // TODO: Remove this it's ugly. See notes in NavigationSettings for ideas
//        NavigationSettings.setDefaultConfig(mConfig);
//    }
//
//    /**
//     * Override the animation in and out of the next present, dismiss or clear stack call.
//     *
//     * @param
//     *      animIn -> The resource of the new in animation.
//     * @param
//     *      animOut -> The resource of the new in animation.
//     * @deprecated
//     *      This call is being replaced with {@link NavigationSettings} being passed in with the push function.
//     *      To be removed in 1.2.0.
//     */
//    @Deprecated
//    @Override
//    public void overrideNextAnimation(int animIn, int animOut) {
//        mConfig.setNextAnim(animIn, animOut);
//    }
//
//    /**
//     * Push a new Fragment onto the stack and presenting it to the screen
//     * Uses default animation of slide in from right and slide out to left.
//     *
//     * @param
//     *      navFragment -> The Fragment to show. It must be a Fragment that implements {@link Navigation}
//     */
//    @Override
//    public void pushFragment(Navigation navFragment) {
//        pushFragment(navFragment, new NavigationSettings.Builder().build());
//    }
//
//    /**
//     * Push a new Fragment onto the stack and presenting it to the screen
//     * Uses default animation of slide in from right and slide out to left.
//     *
//     * @param
//     *      navFragment -> The Fragment to show. It must be a Fragment that implements {@link Navigation}
//     * @param
//     *      navBundle -> The navigation bundle to add to the fragment being pushed
//     *
//     * @deprecated
//     *      This function is being replaced with the {@link NavigationManager#pushFragment(Navigation, NavigationSettings)} method call.
//     *      Allowing for more parameters to be passed in with the call.
//     *      To be removed in 1.2.0.
//     */
//    @Deprecated
//    @Override
//    public void pushFragment(Navigation navFragment, Bundle navBundle) {
//        pushFragment(navFragment, new NavigationSettings.Builder().setNavBundle(navBundle).build());
//    }
//
//    /**
//     * Push a new Fragment onto the stack and presenting it to the screen
//     * Uses default animation of slide in from right and slide out to left.
//     *
//     * @param
//     *      navFragment -> The Fragment to show. It must be a Fragment that implements {@link Navigation}
//     * @param
//     *      settings -> The {@link NavigationSettings} to be applied to the transaction
//     */
//    @Override
//    public void pushFragment(Navigation navFragment, NavigationSettings settings) {
//        mStack.pushFragment(this, navFragment, settings);
//
//        if (mListener != null) {
//            mListener.didPresentFragment();
//        }
//    }
//
//    /**
//     * Pop the current fragment off the top of the stack and dismiss it.
//     * Uses default animation of slide in from left and slide out to right animation.
//     */
//    @Override
//    public void popFragment() {
//        mStack.popFragment(this, null);
//
//        if (mListener != null) {
//            mListener.didDismissFragment();
//        }
//    }
//
//    /**
//     * Pop the current fragment off the top of the stack and dismiss it.
//     * Uses default animation of slide in from left and slide out to right animation.
//     *
//     * @param
//     *      navBundle -> The navigation bundle to add to the fragment after the pop occurs
//     */
//    @Override
//    public void popFragment(Bundle navBundle) {
//        mStack.popFragment(this, new NavigationSettings.Builder().setNavBundle(navBundle).build());
//
//        if (mListener != null) {
//            mListener.didDismissFragment();
//        }
//    }
//
//    /**
//     * Adds the fragment to the current stack.
//     *
//     * @param
//     *      navFragment -> The navigation fragment to be added to the stack.
//     */
//    @Override
//    public void addToStack(Navigation navFragment) {
//        mState.getStack().add(navFragment.getNavTag());
//    }
//
//    /**
//     * Access the fragment that is on the top of the navigation stack.
//     *
//     * @return
//     *      {@link Navigation} that is on the top of the stack.
//     */
//    @Override
//    public Navigation getTopFragment() {
//        if (mState.getStack().size() > 0) {
//            return getFragmentAtIndex(mState.getStack().size() - 1);
//        }
//        else {
//            Log.e(TAG, "No fragments in the navigation stack, returning null.");
//            return null;
//        }
//    }
//
//    /**
//     * Returns the fragment at the 0 index.
//     *
//     * @return
//     *      {@link Navigation} at the 0 index if available.
//     */
//    @Override
//    public Navigation getRootFragment() {
//        return getFragmentAtIndex(0);
//    }
//
//    /**
//     * Access the fragment at the given index of the navigation stack.
//     *
//     * @return
//     *      {@link Navigation} that is on the top of the stack.
//     */
//    @Override
//    public Navigation getFragmentAtIndex(int index) {
//        if (mState.getStack().size() > index) {
//            return mStack.getFragmentAtIndex(this, index);
//        }
//        else {
//            Log.e(TAG, "No fragment at that position in the navigation stack, returning null. (Stack size: " + mState.getStack().size() + ". Index attempted: " + index + ".");
//            return null;
//        }
//    }
//
//    /**
//     * Remove the {@link Navigation} that is on the top of the stack.
//     *
//     * @return
//     *      true -> A {@link Navigation} has been removed
//     *      false -> No fragment has been removed because we are at the bottom of the stack for that stack.
//     */
//    @Override
//    public boolean onBackPressed() {
//        if (mState.getStack().size() > mConfig.getMinStackSize()) {
//            popFragment();
//            return true;
//        }
//
//        return false;
//    }
//
//    /**
//     * Remove all fragments from the stack including the Root. The add the given {@link Navigation}
//     * as the new root fragment. The definition of the Root Fragment is the Fragment at the min stack size position.
//     *
//     * @param
//     *      navFragment -> The fragment that you would like as the new Root of the stack.
//     */
//    @Override
//    public void replaceRootFragment(Navigation navFragment) {
//        clearNavigationStackToIndex(mConfig.getMinStackSize() - 1, true);
//        pushFragment(navFragment);
//    }
//
//    /**
//     * Remove all fragments from the stack until we reach the Root Fragment (the fragment at the min stack size)
//     */
//    @Override
//    public void clearNavigationStackToRoot() {
//        clearNavigationStackToIndex(mConfig.getMinStackSize());
//    }
//
//    /**
//     * Remove all fragments up until the given position.
//     *
//     * @param
//     *      stackPosition -> The position (0 indexed) that you would like to pop to.
//     */
//    @Override
//    public void clearNavigationStackToIndex(int stackPosition) {
//        clearNavigationStackToIndex(stackPosition, false);
//    }
//
//    /**
//     * Remove all fragments up until the given position.
//     *
//     * @param
//     *      index -> The index (0 based) that you would like to pop to.
//     */
//    @Override
//    public void clearNavigationStackToIndex(int index, boolean inclusive) {
//        mStack.clearNavigationStackToIndex(this, index, inclusive);
//    }
//
//    /**
//     * Check if the current top fragment is the root fragment
//     *
//     * @return
//     *      true -> Stack is currently at the root fragment
//     *      false -> Stack is not at the root fragment
//     */
//    @Override
//    public boolean isOnRootFragment() {
//        return mState.getStack().size() == mConfig.getMinStackSize();
//    }
//
//    /**
//     * Returns the {@link NavigationManager} stack size. A stack size of 0 represents empty.
//     *
//     * @return
//     *      The current stack size.
//     */
//    @Override
//    public int getCurrentStackSize() {
//        return mState.getStack().size();
//    }
//
//    // ===============================
//    // START DEVICE STATE METHODS
//    // ===============================
//
//    @Override
//    public boolean isPortrait() {
//        return mState.isPortrait();
//    }
//
//    @Override
//    public boolean isTablet() {
//        return mState.isTablet();
//    }
//
//    // ===============================
//    // END DEVICE STATE METHODS
//    // ===============================
}
