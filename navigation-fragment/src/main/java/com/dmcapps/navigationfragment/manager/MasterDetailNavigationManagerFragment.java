package com.dmcapps.navigationfragment.manager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.dmcapps.navigationfragment.R;
import com.dmcapps.navigationfragment.fragments.INavigationFragment;
import com.dmcapps.navigationfragment.helper.DeviceUtil;
import com.dmcapps.navigationfragment.helper.ViewUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class MasterDetailNavigationManagerFragment extends NavigationManagerFragment {
    // TODO: This should be abstract method. Would allow me to pull ALL methods into the parent.
    private static final int ACTIONABLE_STACK_SIZE = 2;

    private static final String ARG_MASTER_FRAGMENT = "MASTER_FRAGMENT";
    private static final String ARG_DETAIL_FRAGMENT = "DETAIL_FRAGMENT";

    private INavigationFragment mMasterFragment;
    private INavigationFragment mDetailFragment;

    public static MasterDetailNavigationManagerFragment newInstance(INavigationFragment masterFragment, INavigationFragment detailFragment) {
        MasterDetailNavigationManagerFragment managerFragment = new MasterDetailNavigationManagerFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_MASTER_FRAGMENT, masterFragment);
        bundle.putSerializable(ARG_DETAIL_FRAGMENT, detailFragment);
        managerFragment.setArguments(bundle);
        return managerFragment;
    }

    public MasterDetailNavigationManagerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_master_detail_navigation_manager, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        // No Fragments have been added. Attach the master and detail.
        if (getFragmentTags().size() == 0) {
            getMasterFragment().setNavigationManager(this);
            FragmentManager childFragManager = getRetainedChildFragmentManager();
            FragmentTransaction childFragTrans = childFragManager.beginTransaction();
            // Add in the new fragment that we are presenting and add it's navigation tag to the stack.
            childFragTrans.add(R.id.master_detail_container_master, (Fragment)getMasterFragment(), getMasterFragment().getNavTag());
            addFragmentToStack(getMasterFragment());
            childFragTrans.commit();

            pushFragment(getDetailFragment());
        }
        // Fragments are in the stack, resume at the top.
        else {
            FragmentManager childFragManager = getRetainedChildFragmentManager();
            FragmentTransaction childFragTrans = childFragManager.beginTransaction();
            childFragTrans.setCustomAnimations(NO_ANIMATION, NO_ANIMATION);
            childFragTrans.attach(childFragManager.findFragmentByTag(getFragmentTags().firstElement()));
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
        childFragTrans.detach(childFragManager.findFragmentByTag(getFragmentTags().firstElement()));
        childFragTrans.detach(childFragManager.findFragmentByTag(getFragmentTags().peek()));
        childFragTrans.commit();
    }

    @Override
    public int getPushStackFrameId() {
        return R.id.master_detail_container_detail;
    }

    @Override
    public int getMinStackSize() {
        return ACTIONABLE_STACK_SIZE;
    }

    private INavigationFragment getMasterFragment() {
        if (mMasterFragment == null) {
            mMasterFragment = (INavigationFragment)getArguments().getSerializable(ARG_MASTER_FRAGMENT);
        }
        return mMasterFragment;
    }

    private INavigationFragment getDetailFragment() {
        if (mDetailFragment == null) {
            mDetailFragment = (INavigationFragment)getArguments().getSerializable(ARG_DETAIL_FRAGMENT);
        }
        return mDetailFragment;
    }
}
