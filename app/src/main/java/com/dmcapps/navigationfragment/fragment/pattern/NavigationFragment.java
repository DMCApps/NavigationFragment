package com.dmcapps.navigationfragment.fragment.pattern;

import android.support.v4.app.Fragment;

import com.dmcapps.navigationfragment.fragment.pattern.manager.INavigationManager;

import java.util.UUID;

/**
 * This is the NavigationFragment that all classes in the stack must implement
 * The extension of this class in the child fragments allows access to the presenting
 * and dismissing of existing {@link Fragment} in the stack. The class will also generate
 * and maintain a constant TAG for the class allowing the navigation manager to
 * effectively store and present the Fragments as needed.
 */
public abstract class NavigationFragment extends Fragment {

    private INavigationManager mNavigationManager;
    private final String TAG;

    public NavigationFragment() {
        TAG = UUID.randomUUID().toString();
    }

    public String getNavTag() {
        return TAG;
    }

    public void setNavigationManager(INavigationManager navigationManager) {
        mNavigationManager = navigationManager;
    }

    public INavigationManager getNavigationManager() {
        return mNavigationManager;
    }

    public void presentFragment(NavigationFragment navFragment) {
        mNavigationManager.pushFragment(navFragment);
    }

    public void presentFragment(NavigationFragment navFragment, int animationIn, int animationOut) {
        mNavigationManager.pushFragment(navFragment, animationIn, animationOut);
    }

    public void dismissTopFragment() {
        mNavigationManager.popFragment();
    }

    public void dismissTopFragment(int animationIn, int animationOut) {
        mNavigationManager.popFragment(animationIn, animationOut);
    }
}
