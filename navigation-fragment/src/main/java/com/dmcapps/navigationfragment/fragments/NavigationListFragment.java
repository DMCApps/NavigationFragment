package com.dmcapps.navigationfragment.fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;

import com.dmcapps.navigationfragment.manager.MasterDetailNavigationManagerFragment;
import com.dmcapps.navigationfragment.manager.NavigationManagerFragment;

import java.util.UUID;

/**
 * This is the NavigationFragment that all classes in the stack must implement
 * The extension of this class in the child fragments allows access to the presenting
 * and dismissing of existing {@link Fragment} in the stack. The class will also generate
 * and maintain a constant TAG for the class allowing the navigation manager to
 * effectively store and present the Fragments as needed.
 */
public class NavigationListFragment extends ListFragment implements INavigationFragment {

    private NavigationManagerFragment mNavigationManager;
    private final String TAG;

    public NavigationListFragment() {
        TAG = UUID.randomUUID().toString();
    }

    public String getNavTag() {
        return TAG;
    }

    public void setNavigationManager(NavigationManagerFragment navigationManager) {
        mNavigationManager = navigationManager;
    }

    public NavigationManagerFragment getNavigationManager() {
        return mNavigationManager;
    }

    public void presentFragment(INavigationFragment navFragment) {
        mNavigationManager.pushFragment(navFragment);
    }

    public void presentFragment(INavigationFragment navFragment, int animationIn, int animationOut) {
        mNavigationManager.pushFragment(navFragment, animationIn, animationOut);
    }

    public void dismissToRoot() {
        mNavigationManager.clearNavigationStackToRoot();
    }

    public void dismissFragment() {
        mNavigationManager.popFragment();
    }

    public void dismissFragment(int animationIn, int animationOut) {
        mNavigationManager.popFragment(animationIn, animationOut);
    }

    public void replaceRootFragment(INavigationFragment navFragment) {
        mNavigationManager.replaceRootFragment(navFragment);
    }

    public void setTitle(String title) {
        mNavigationManager.setTitle(title);
    }

    public void setTitle(int resId) {
        mNavigationManager.setTitle(resId);
    }

    @Override
    public void setMasterToggleTitle(String title) {
        if (mNavigationManager instanceof MasterDetailNavigationManagerFragment) {
            ((MasterDetailNavigationManagerFragment)mNavigationManager).setMasterToggleTitle(title);
        }
        else {
            Log.e("NavigationFragment", "Navigation Manager must be a MasterDetailNavigationManagerFragment");
        }
    }

    @Override
    public void setMasterToggleTitle(int resId) {
        if (mNavigationManager instanceof MasterDetailNavigationManagerFragment) {
            ((MasterDetailNavigationManagerFragment)mNavigationManager).setMasterToggleTitle(resId);
        }
        else {
            Log.e("NavigationFragment", "Navigation Manager must be a MasterDetailNavigationManagerFragment");
        }
    }

    @Override
    public boolean isPortrait() {
        return mNavigationManager.isPortrait();
    }

    @Override
    public boolean isTablet() {
        return mNavigationManager.isTablet();
    }
}
