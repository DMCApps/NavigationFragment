package com.dmcapps.navigationfragment.manager;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.dmcapps.navigationfragment.fragments.INavigationFragment;
import com.dmcapps.navigationfragment.helper.ViewUtil;


/**
 * This Fragment manages the stack of single navigation on fragments.
 * The class allows for easy adding and removing of fragments as the user
 * traverses the screens. A self-contained class that requires no resources
 * in order to function. Each time a new manager is made a separate stack will be created
 * and no overlap will occur in the class.
 */
public class SingleStackNavigationManagerFragment extends NavigationManagerFragment {
    // TODO: This should be abstract method. Would allow me to pull ALL methods into the parent.
    private static final int ACTIONABLE_STACK_SIZE = 1;

    private static final String ARG_ROOT_FRAGMENT = "ROOT_FRAGMENT";

    private FrameLayout mFragmentFrame;
    private INavigationFragment mRootFragment;

    public static SingleStackNavigationManagerFragment newInstance(INavigationFragment rootFragment) {
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

    @Override
    public int getMinStackSize() {
        return ACTIONABLE_STACK_SIZE;
    }

    @Override
    public int getPushStackFrameId() {
        return mFragmentFrame.getId();
    }

    private INavigationFragment getRootFragment() {
        if (mRootFragment == null) {
            mRootFragment = (INavigationFragment)getArguments().getSerializable(ARG_ROOT_FRAGMENT);
        }
        return mRootFragment;
    }
}
