package com.dmcapps.navigationfragment.fragments;

import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;

import com.dmcapps.navigationfragment.manager.MasterDetailNavigationManagerFragment;
import com.dmcapps.navigationfragment.manager.NavigationManagerFragment;
import com.dmcapps.navigationfragment.manager.micromanagers.actionbar.ActionBarManager;

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

    /**
     * A method for setting the title of the action bar. (Saves you from having to call getActivity().setTitle())
     *
     * @param
     *      title -> String of the title you would like to set.
     */
    @Override
    public void setTitle(String title) {
        ActionBarManager.setTitle(getActivity(), title);
    }

    /**
     * A method for setting the title of the action bar. (Saves you from having to call getActivity().setTitle())
     *
     * @param
     *      resId -> Resource Id of the title you would like to set.
     */
    @Override
    public void setTitle(int resId) {
        ActionBarManager.setTitle(getActivity(), resId);
    }

    /*
    @Override
    public void setDisplayHomeAsUpEnabled(boolean enabled) {
        ActionBarManager.setDisplayHomeAsUpEnabled(getActivity(), enabled);
    }

    @Override
    public void setHomeAsUpIndicator(Drawable indicator) {
        ActionBarManager.setHomeAsUpIndicator(getActivity(), indicator);
    }

    @Override
    public void setHomeAsUpIndicator(int resId) {
        ActionBarManager.setHomeAsUpIndicator(getActivity(), resId);
    }
    */

    @Override
    public void setMasterToggleTitle(String title) {
        ActionBarManager.setMasterToggleTitle(mNavigationManager, title);
    }

    @Override
    public void setMasterToggleTitle(int resId) {
        ActionBarManager.setMasterToggleTitle(mNavigationManager, resId);
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
