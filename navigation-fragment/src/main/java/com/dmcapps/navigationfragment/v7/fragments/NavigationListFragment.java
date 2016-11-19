package com.dmcapps.navigationfragment.v7.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;

import com.dmcapps.navigationfragment.common.core.NavigationSettings;
import com.dmcapps.navigationfragment.common.interfaces.Navigation;
import com.dmcapps.navigationfragment.common.helpers.utils.ObjectUtils;
import com.dmcapps.navigationfragment.common.core.ActionBarManager;
import com.dmcapps.navigationfragment.common.interfaces.NavigationManager;
import com.dmcapps.navigationfragment.v7.core.NavigationManagerFragment;

import java.util.UUID;

/**
 * This is the NavigationFragment that all classes in the stack must implement
 * The extension of this class in the child fragments allows access to the presenting
 * and dismissing of existing {@link Fragment} in the stack. The class will also generate
 * and maintain a constant TAG for the class allowing the navigation manager to
 * effectively store and present the Fragments as needed.
 */
public class NavigationListFragment extends ListFragment implements Navigation {

    private final String TAG = UUID.randomUUID().toString();
    private String mTitle;
    private Bundle mNavBundle;

    public NavigationListFragment() { }

    @Override
    public String getNavTag() {
        return TAG;
    }

    @Override
    public void setNavBundle(Bundle navBundle) {
        mNavBundle = navBundle;
    }

    @Override
    public Bundle getNavBundle() {
        return mNavBundle;
    }

    /**
     * Get the {@link NavigationManagerFragment} of the Fragment in the stack. This method will crash with
     * a RuntimeException if no Parent fragment is a {@link NavigationManagerFragment}.
     *
     * @return
     *      The Parent Fragment in the stack of fragments that is the Navigation Manager of the Fragment.
     */
    @Override
    public NavigationManagerFragment getNavigationManager() {
        Fragment parent = this;

        // Loop until we find a parent that is a NavigationFragmentManager or there are no parents left to check.
        do {
            parent = parent.getParentFragment();

            NavigationManagerFragment navFragment = ObjectUtils.as(NavigationManagerFragment.class, parent);
            if (navFragment != null) {
                return navFragment;
            }
        } while(parent != null);

        throw new RuntimeException("No parent NavigationManagerFragment found. In order to use the Navigation Manager Fragment you must have a parent in your Fragment Manager.");
    }

    /**
     * Override the animation in and out of the next present, dismiss or clear stack call.
     *
     * @param
     *      animIn -> The resource of the new in animation.
     * @param
     *      animOut -> The resource of the new in animation.
     * @deprecated
     *      This call is being replaced with {@link NavigationSettings} being passed in with the present and dismiss functions.
     *      To be removed in 1.2.0.
     */
    @Deprecated
    public void overrideNextAnimation(int animIn, int animOut) {
        getNavigationManager().overrideNextAnimation(animIn, animOut);
    }

    /**
     * Present a fragment on the Navigation Manager using the default slide in and out.
     *
     * @param
     *      navFragment -> The Fragment to present.
     */
    @Override
    public void presentFragment(Navigation navFragment) {
        getNavigationManager().pushFragment(navFragment);
    }

    /**
     * Push a new Fragment onto the stack and presenting it to the screen
     * Uses default animation of slide in from right and slide out to left.
     * Sends a Bundle with the Fragment that can be retrieved using {@link Navigation#getNavBundle()}
     *
     * @param
     *      navFragment -> The Fragment to show. It must be a Fragment that implements {@link Navigation}
     * @param
     *      navBundle -> Bundle to add to the presenting of the Fragment.
     *
     * @deprecated
     *      This function is being replaced with the {@link NavigationManager#pushFragment(Navigation, NavigationSettings)} method call.
     *      Allowing for more parameters to be passed in with the call.
     *      To be removed in 1.2.0.
     */
    @Deprecated
    public void presentFragment(Navigation navFragment, Bundle navBundle) {
        getNavigationManager().pushFragment(navFragment, navBundle);
    }

    /**
     * Push a new Fragment onto the stack and presenting it to the screen
     * Uses default animation of slide in from right and slide out to left.
     *
     * @param
     *      navFragment -> The Fragment to show. It must be a Fragment that implements {@link Navigation}
     * @param
     *      settings -> The {@link NavigationSettings} to be applied to the transaction
     */
    @Override
    public void presentFragment(Navigation navFragment, NavigationSettings settings) {
        getNavigationManager().pushFragment(navFragment, settings);
    }

    /**
     * Dismiss all the fragments on the Navigation Manager stack until the root fragment using the default slide in and out.
     * Override the animation by calling {@link #overrideNextAnimation(int, int)} before calling dismissToRoot.
     */
    @Override
    public void dismissToRoot() {
        getNavigationManager().clearNavigationStackToRoot();
    }

    /**
     * Dimiss the current fragment off the top of the stack and dismiss it.
     * Uses default animation of slide in from left and slide out to right animation.
     */
    @Override
    public void dismissFragment() {
        getNavigationManager().popFragment();
    }

    /**
     * Pop the current fragment off the top of the stack and dismiss it.
     * Uses default animation of slide in from left and slide out to right animation.
     *
     * @param
     *      navBundle -> The navigation bundle to add to the fragment after the pop occurs
     *
     * @deprecated
     *      This function is being replaced with the {@link Navigation#dismissFragment(NavigationSettings)} method call.
     *      Allowing for more parameters to be passed in with the call.
     *      To be removed in 1.2.0.
     */
    @Deprecated
    @Override
    public void dismissFragment(Bundle navBundle) {
        getNavigationManager().popFragment(navBundle);
    }

    /**
     * Pop the current fragment off the top of the stack and dismiss it.
     * Uses default animation of slide in from left and slide out to right animation.
     *
     * @param
     *      settings -> The {@link NavigationSettings} to be performed on the popping of the fragment
     */
    @Override
    public void dismissFragment(NavigationSettings settings) {
        getNavigationManager().popFragment(settings);
    }

    /**
     * Dismiss all the fragments on the Navigation Manager stack including the root fragment using the default slide in and out.
     * Present a fragment on the Navigation Manager using the default slide in and out.
     * Override the animation by calling {@link #overrideNextAnimation(int, int)} before calling dismissToRoot.
     *
     * @param
     *      navFragment -> The Fragment to present.
     */
    @Override
    public void replaceRootFragment(Navigation navFragment) {
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
