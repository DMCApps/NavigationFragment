package com.dmcapps.navigationfragment.fragments;

import android.support.v4.app.Fragment;

import com.dmcapps.navigationfragment.helper.utils.ObjectUtils;
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
public class NavigationFragment extends Fragment implements INavigationFragment {

    private final String TAG;
    private String mTitle;

    public NavigationFragment() {
        TAG = UUID.randomUUID().toString();
    }

    public String getNavTag() {
        return TAG;
    }

    public NavigationManagerFragment getNavigationManager() {
        return getNavigationManager(getParentFragment());
    }

    private NavigationManagerFragment getNavigationManager(Fragment fragment) {
        NavigationManagerFragment navFragment = ObjectUtils.as(NavigationManagerFragment.class, fragment);

        if (navFragment != null) {
            return navFragment;
        }
        else if (fragment.getParentFragment() != null) {
            return getNavigationManager(fragment.getParentFragment());
        }
        else {
            throw new RuntimeException("No parent NavigationManagerFragment found. In order to use the Navigation Manager Fragment you must have a parent in your Fragment Manager.");
        }
    }

    public void presentFragment(INavigationFragment navFragment) {
        getNavigationManager().pushFragment(navFragment);
    }

    public void presentFragment(INavigationFragment navFragment, int animationIn, int animationOut) {
        getNavigationManager().pushFragment(navFragment, animationIn, animationOut);
    }

    public void dismissToRoot() {
        getNavigationManager().clearNavigationStackToRoot();
    }

    public void dismissFragment() {
        getNavigationManager().popFragment();
    }

    public void dismissFragment(int animationIn, int animationOut) {
        getNavigationManager().popFragment(animationIn, animationOut);
    }

    public void replaceRootFragment(INavigationFragment navFragment) {
        getNavigationManager().replaceRootFragment(navFragment);
    }

    /**
     * A method for setting the title of the action bar. (Saves you from having to call getActivity().setTitle())
     *
     * @param
     *      resId -> Resource Id of the title you would like to set.
     */
    @Override
    public void setTitle(int resId) {
        setTitle(getString(resId));
    }

    /**
     * A method for setting the title of the action bar. (Saves you from having to call getActivity().setTitle())
     *
     * @param
     *      title -> String of the title you would like to set.
     */
    @Override
    public void setTitle(String title) {
        mTitle = title;
        ActionBarManager.setTitle(getActivity(), mTitle);
    }

    /**
     * A method for retrieving the currently set title for the NavigationFragment
     *
     * @return
     *      The current title of the NavigationFragment
     */
    @Override
    public String getTitle() {
        return mTitle;
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
        ActionBarManager.setMasterToggleTitle(getNavigationManager(), title);
    }

    @Override
    public void setMasterToggleTitle(int resId) {
        ActionBarManager.setMasterToggleTitle(getNavigationManager(), resId);
    }

    @Override
    public boolean isPortrait() {
        return getNavigationManager().isPortrait();
    }

    @Override
    public boolean isTablet() {
        return getNavigationManager().isTablet();
    }
}
