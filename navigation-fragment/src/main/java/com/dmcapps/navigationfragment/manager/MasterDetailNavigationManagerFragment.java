package com.dmcapps.navigationfragment.manager;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
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
    private static final String TAG = MasterDetailNavigationManagerFragment.class.getSimpleName();

    // TODO: Should this manager take care of making the home up enabled show?

    // TODO: I think I need callbacks to the activity to tell it that the stack
    // size is small and hence it needs to show the home button for the master view
    // and also to tell the activity that a view has been pushed and hence to now show
    // the home button anymore.
    // TODO: Also need to send information about orientation changes so that it can adjust
    // it's button showing.

    private static final int TABLET_ACTIONABLE_STACK_SIZE = 2;
    private static final int PHONE_ACTIONABLE_STACK_SIZE = 1;

    private static final String ARG_MASTER_FRAGMENT = "MASTER_FRAGMENT";
    private static final String ARG_DETAIL_FRAGMENT = "DETAIL_FRAGMENT";

    private INavigationFragment mMasterFragment;
    private INavigationFragment mDetailFragment;

    private FrameLayout mMasterFrame;

    private boolean mIsTablet;
    private boolean mIsPortrait;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_master_detail_navigation_manager, container, false);

        mIsTablet = view.findViewById(R.id.master_detail_container_master) != null;
        mIsPortrait = view.findViewById(R.id.master_detail_layout_main_portrait) != null;

        if (mIsTablet) {
            mMasterFrame = (FrameLayout)view.findViewById(R.id.master_detail_container_master);
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // No Fragments have been added. Attach the master and detail.
        if (getFragmentTags().size() == 0) {
            getMasterFragment().setNavigationManager(this);
            FragmentManager childFragManager = getRetainedChildFragmentManager();
            FragmentTransaction childFragTrans = childFragManager.beginTransaction();
            if (mIsTablet) {
                // Add in the new fragment that we are presenting and add it's navigation tag to the stack.
                childFragTrans.add(R.id.master_detail_container_master, (Fragment) getMasterFragment(), getMasterFragment().getNavTag());
                addFragmentToStack(getMasterFragment());
            }
            else {
                pushFragment(getMasterFragment());
            }
            childFragTrans.commit();

            if (mIsTablet) {
                pushFragment(getDetailFragment());
            }

        }
        // Fragments are in the stack, resume at the top.
        else {
            FragmentManager childFragManager = getRetainedChildFragmentManager();
            FragmentTransaction childFragTrans = childFragManager.beginTransaction();
            childFragTrans.setCustomAnimations(NO_ANIMATION, NO_ANIMATION);
            if (mIsTablet) {
                childFragTrans.attach(childFragManager.findFragmentByTag(getFragmentTags().firstElement()));
            }
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
        if (mIsTablet) {
            childFragTrans.detach(childFragManager.findFragmentByTag(getFragmentTags().firstElement()));
        }
        childFragTrans.detach(childFragManager.findFragmentByTag(getFragmentTags().peek()));
        childFragTrans.commit();
    }

    @Override
    public int getPushStackFrameId() {
        return R.id.master_detail_container_detail;
    }

    @Override
    public int getMinStackSize() {
        return mIsTablet ? TABLET_ACTIONABLE_STACK_SIZE : PHONE_ACTIONABLE_STACK_SIZE;
    }

    public void toggleMaster() {
        if (shouldMasterToggle()) {
            if (mMasterFrame.getVisibility() == View.GONE) {
                showMaster();
            } else {
                hideMaster();
            }
        }
    }

    public boolean shouldMasterToggle() {
        return mIsTablet && mIsPortrait;
    }

    // TODO: Animations
    public void showMaster() {
        if (shouldMasterToggle()) {
            mMasterFrame.setVisibility(View.VISIBLE);
        }
    }

    // TODO: Animations
    public void hideMaster() {
        if (shouldMasterToggle()) {
            mMasterFrame.setVisibility(View.GONE);
        }
    }

    // Feel like this stuff shouldn't be exposed :S how do I manage this better.
    public boolean isPortrait() {
        return mIsPortrait;
    }

    public boolean isTablet() {
        return mIsTablet;
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
