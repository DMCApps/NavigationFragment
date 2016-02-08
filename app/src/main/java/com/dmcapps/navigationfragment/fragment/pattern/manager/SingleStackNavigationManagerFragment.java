package com.dmcapps.navigationfragment.fragment.pattern.manager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.dmcapps.navigationfragment.R;
import com.dmcapps.navigationfragment.fragment.pattern.NavigationFragment;
import com.dmcapps.navigationfragment.fragment.pattern.helper.ViewUtil;

import java.util.Stack;

/**
 * This Fragment manages the stack of single navigation on fragments.
 * The class allows for easy adding and removing of fragments as the user
 * traverses the screens. A self-contained class that requires no resources
 * in order to function. Each time a new manager is made a separate stack will be created
 * and no overlap will occur in the class.
 */
public class SingleStackNavigationManagerFragment extends NavigationManagerFragment {
    private static final int ACTIONABLE_STACK_SIZE = 1;

    private static final String ARG_ROOT_FRAGMENT = "ROOT_FRAGMENT";

    private FrameLayout mFragmentFrame;
    private NavigationFragment mRootFragment;

    public static SingleStackNavigationManagerFragment newInstance(NavigationFragment rootFragment) {
        SingleStackNavigationManagerFragment managerFragment = new SingleStackNavigationManagerFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_ROOT_FRAGMENT, rootFragment);
        managerFragment.setArguments(bundle);
        return managerFragment;
    }

    public SingleStackNavigationManagerFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mFragmentFrame == null) {
            mFragmentFrame = new FrameLayout(getActivity());
            mFragmentFrame.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            mFragmentFrame.setId(ViewUtil.generateViewId());
        }
        return mFragmentFrame;
    }

    @Override
    public void onResume() {
        super.onResume();

        // No Fragments have been added. Attach the root.
        if (getFragmentTags().size() == 0) {
            pushFragment(getRootFragment());
        }
        // Fragments are in the stack, resume at the top.
        else {
            FragmentManager childFragManager = getRetainedChildFragmentManager();
            FragmentTransaction childFragTrans = childFragManager.beginTransaction();
            childFragTrans.setCustomAnimations(NO_ANIMATION, NO_ANIMATION);
            childFragTrans.attach(childFragManager.findFragmentByTag(getFragmentTags().peek()));
            childFragTrans.commit();
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        FragmentManager childFragManager = getRetainedChildFragmentManager();
        FragmentTransaction childFragTrans = childFragManager.beginTransaction();
        childFragTrans.setCustomAnimations(NO_ANIMATION, NO_ANIMATION);
        childFragTrans.detach(childFragManager.findFragmentByTag(getFragmentTags().peek()));
        childFragTrans.commit();
    }

    public void pushFragment(NavigationFragment navFragment) {
        pushFragment(navFragment, R.anim.slide_in_from_right, R.anim.slide_out_to_left);
    }

    @Override
    public void pushFragment(NavigationFragment navFragment, int animationIn, int animationOut) {
        pushFragment(ACTIONABLE_STACK_SIZE, mFragmentFrame.getId(), navFragment, animationIn, animationOut);
    }

    public void popFragment() {
        popFragment(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
    }

    public void popFragment(int animationIn, int animationOut) {
        popFragment(ACTIONABLE_STACK_SIZE, animationIn, animationOut);
    }

    @Override
    public void clearNavigationStackToRoot() {
        clearNavigationStackToPosition(ACTIONABLE_STACK_SIZE);
    }

    @Override
    public NavigationFragment topFragment() {
        return (NavigationFragment)getRetainedChildFragmentManager().findFragmentByTag(getFragmentTags().peek());
    }

    @Override
    public boolean onBackPressed() {
        if (getFragmentTags().size() > ACTIONABLE_STACK_SIZE) {
            popFragment();
            return true;
        }

        return false;
    }

    private NavigationFragment getRootFragment() {
        if (mRootFragment == null) {
            mRootFragment = (NavigationFragment)getArguments().getSerializable(ARG_ROOT_FRAGMENT);
        }
        return mRootFragment;
    }
}
