package com.dmcapps.navigationfragment.fragment.pattern.manager;

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
import com.dmcapps.navigationfragment.fragment.pattern.NavigationFragment;
import com.dmcapps.navigationfragment.fragment.pattern.helper.DeviceUtil;
import com.dmcapps.navigationfragment.fragment.pattern.helper.ViewUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class MasterDetailNavigationManagerFragment extends NavigationManagerFragment {
    private static final int ACTIONABLE_STACK_SIZE = 2;

    private static final String ARG_MASTER_FRAGMENT = "MASTER_FRAGMENT";
    private static final String ARG_DETAIL_FRAGMENT = "DETAIL_FRAGMENT";

    private RelativeLayout mMainLayout;
    private FrameLayout mMasterFrame;
    private FrameLayout mDetailFrame;

    private NavigationFragment mMasterFragment;
    private NavigationFragment mDetailFragment;

    public static MasterDetailNavigationManagerFragment newInstance(NavigationFragment masterFragment, NavigationFragment detailFragment) {
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
        if (mMainLayout == null) {
            mMainLayout = new RelativeLayout(getActivity());
            mMainLayout.setId(ViewUtil.generateViewId());
            mMainLayout.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            mMasterFrame = new FrameLayout(getActivity());
            mMasterFrame.setId(ViewUtil.generateViewId());

            mDetailFrame = new FrameLayout(getActivity());
            mDetailFrame.setId(ViewUtil.generateViewId());

            mMainLayout.addView(mMasterFrame);
            mMainLayout.addView(mDetailFrame);
        }

        // TODO: Depends on orientation

        // Layout Master
        RelativeLayout.LayoutParams masterParams = (RelativeLayout.LayoutParams)mMasterFrame.getLayoutParams();
        masterParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
        masterParams.width = DeviceUtil.getPixelValueFromDp(getActivity(), 200);
        masterParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, mMasterFrame.getId());
        mMasterFrame.setLayoutParams(masterParams);

        RelativeLayout.LayoutParams detailParams = (RelativeLayout.LayoutParams)mDetailFrame.getLayoutParams();
        detailParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
        detailParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        detailParams.addRule(RelativeLayout.RIGHT_OF, mMasterFrame.getId());
        detailParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, mDetailFrame.getId());
        mDetailFrame.setLayoutParams(detailParams);

        mMainLayout.invalidate();
        mMainLayout.requestLayout();

        return mMainLayout;
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
            childFragTrans.add(mMasterFrame.getId(), getMasterFragment(), getMasterFragment().getNavTag());
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
    public void pushFragment(NavigationFragment navFragment) {
        pushFragment(navFragment, R.anim.slide_in_from_right, R.anim.slide_out_to_left);
    }

    @Override
    public void pushFragment(NavigationFragment navFragment, int animationIn, int animationOut) {
        pushFragmentDetachAboveInContainer(ACTIONABLE_STACK_SIZE, mDetailFrame.getId(), navFragment, animationIn, animationOut);
    }

    @Override
    public void popFragment() {
        popFragment(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
    }

    @Override
    public void popFragment(int animationIn, int animationOut) {
        popFragmentAboveStackSize(ACTIONABLE_STACK_SIZE, animationIn, animationOut);
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

    private NavigationFragment getMasterFragment() {
        if (mMasterFragment == null) {
            mMasterFragment = (NavigationFragment)getArguments().getSerializable(ARG_MASTER_FRAGMENT);
        }
        return mMasterFragment;
    }

    private NavigationFragment getDetailFragment() {
        if (mDetailFragment == null) {
            mDetailFragment = (NavigationFragment)getArguments().getSerializable(ARG_DETAIL_FRAGMENT);
        }
        return mDetailFragment;
    }
}
