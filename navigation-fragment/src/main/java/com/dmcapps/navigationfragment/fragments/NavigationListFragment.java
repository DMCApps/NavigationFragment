package com.dmcapps.navigationfragment.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;

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
public class NavigationListFragment extends ListFragment implements INavigationFragment {

    private final String TAG = UUID.randomUUID().toString();
    private String mTitle;

    public NavigationListFragment() { }

    public String getNavTag() {
        return TAG;
    }

    public Bundle getNavBundle() {
        return getArguments().getBundle(ARG_NAVIGATION_FRAGMENT_BUNDLE);
    }

    /**
     * Get the {@link NavigationManagerFragment} of the Fragment in the stack. This method will crash with
     * a RuntimeException if no Parent fragment is a {@link NavigationManagerFragment}.
     *
     * @return
     *      The Parent Fragment in the stack of fragments that is the Navigation Manager of the Fragment.
     */
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
            throw new RuntimeException("No parent NavigationManagerFragment found. In order to use the NavigationManagerFragment this fragment must be presented on a NavigationManagerFragment.");
        }
    }

    /**
     * Override the default animation for the next present or dismiss action.
     *
     * @param
     *      animIn -> Resource for the in animation.
     * @param
     *      animOut -> Resource for the out animation.
     */
    @Override
    public void overrideNextAnimation(int animIn, int animOut) {
        getNavigationManager().overrideNextAnimation(animIn, animOut);
    }

    /**
     * Present a fragment on the Navigation Manager using the default slide in and out.
     * Override the animation by calling {@link #overrideNextAnimation(int, int)} before calling presentFragment.
     *
     * @param
     *      navFragment -> The Fragment to present.
     */
    @Override
    public void presentFragment(INavigationFragment navFragment) {
        getNavigationManager().pushFragment(navFragment);
    }

    /**
     * Present a fragment on the Navigation Manager overriding the default animation
     *
     * @param
     *      navFragment -> The Fragment to present.
     * @param
     *      animationIn -> The Resource to override the animation in with.
     * @param
     *      animationOut -> The Resource to override the animation out with.
     *
     * @deprecated Depreciated as of 0.3.0. Use {@link #overrideNextAnimation(int, int)} instead before presenting. This method will be removed as of 0.4.0.
     */
    @Override
    @Deprecated
    public void presentFragment(INavigationFragment navFragment, int animationIn, int animationOut) {
        getNavigationManager().pushFragment(navFragment, animationIn, animationOut);
    }

    /**
     * Dismiss all the fragments on the Navigation Manager stack until the root fragment using the default slide in and out.
     * Override the animation by calling {@link #overrideNextAnimation(int, int)} before calling dismissToRoot.
     */
    @Override
    public void dismissToRoot() {
        getNavigationManager().clearNavigationStackToRoot();
    }

    @Override
    public void dismissFragment() {
        getNavigationManager().popFragment();
    }

    /**
     * Dimiss a fragment on the Navigation Manager overriding the default animation.
     *
     * @param
     *      animationIn -> The Resource to override the animation in with.
     * @param
     *      animationOut -> The Resource to override the animation out with.
     *
     * @deprecated Depreciated as of 0.3.0. Use {@link #overrideNextAnimation(int, int)} instead before dismissing. This method will be removed as of 0.4.0.
     */
    @Override
    @Deprecated
    public void dismissFragment(int animationIn, int animationOut) {
        getNavigationManager().popFragment(animationIn, animationOut);
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
