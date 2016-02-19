package com.dmcapps.navigationfragment.manager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

import com.dmcapps.navigationfragment.R;
import com.dmcapps.navigationfragment.fragments.INavigationFragment;
import com.dmcapps.navigationfragment.helper.AnimationEndListener;
import com.dmcapps.navigationfragment.helper.AnimationStartListener;

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

    private static final int TABLET_ACTIONABLE_STACK_SIZE = 2;
    private static final int PHONE_ACTIONABLE_STACK_SIZE = 1;

    private static final String ARG_MASTER_FRAGMENT = "MASTER_FRAGMENT";
    private static final String ARG_DETAIL_FRAGMENT = "DETAIL_FRAGMENT";

    private INavigationFragment mMasterFragment;
    private INavigationFragment mDetailFragment;

    private FrameLayout mMasterFrame;

    private boolean mIsTablet;
    private boolean mIsPortrait;

    private String mMasterToggleTitle;
    private int mMasterToggleResId = -1;

    MenuItem mMasterToggle;

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_master_detail, menu);
        mMasterToggle = menu.findItem(R.id.menu_master_detail_toggle_master);

        mMasterToggle.setVisible(isOnRootAndMasterIsToggleable());

        if (mMasterToggleResId > 0) {
            mMasterToggle.setTitle(mMasterToggleResId);
        }
        else if (mMasterToggleTitle != null && !mMasterToggleTitle.equals("")) {
            mMasterToggle.setTitle(mMasterToggleTitle);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_master_detail_toggle_master) {
            toggleMaster();
        }
        return super.onOptionsItemSelected(item);
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

        getActivity().invalidateOptionsMenu();
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
    public void pushFragment(int detachStackSize, int containerId, INavigationFragment navFragment, int animationIn, int animationOut) {
        hideMaster();
        super.pushFragment(detachStackSize, containerId, navFragment, animationIn, animationOut);
    }

    @Override
    public int getPushStackFrameId() {
        return R.id.master_detail_container_detail;
    }

    @Override
    public int getMinStackSize() {
        return mIsTablet ? TABLET_ACTIONABLE_STACK_SIZE : PHONE_ACTIONABLE_STACK_SIZE;
    }

    public boolean shouldMasterToggle() {
        return isOnRootFragment() && mIsTablet && mIsPortrait;
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

    // TODO: Better animation handling. This doesn't allow for custom animations.
    public void showMaster() {
        if (shouldMasterToggle()) {
            Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);
            anim.setAnimationListener(new AnimationStartListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    mMasterFrame.setVisibility(View.VISIBLE);
                }
            });
            mMasterFrame.startAnimation(anim);
        }
    }

    // TODO: Better animation handling. This doesn't allow for custom animations.
    public void hideMaster() {
        if (shouldMasterToggle() && mMasterFrame.getVisibility() == View.VISIBLE) {
            Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.fade_out);
            anim.setAnimationListener(new AnimationEndListener() {
                @Override
                public void onAnimationEnd(Animation animation) {
                    mMasterFrame.setVisibility(View.GONE);
                }
            });
            mMasterFrame.startAnimation(anim);
        }
    }

    public boolean isOnRootAndMasterIsToggleable() {
        return isOnRootFragment() && mIsPortrait && mIsTablet;
    }

    // TODO: Move to super and have protected variables that both report to.
    public boolean isPortrait() {
        return mIsPortrait;
    }

    // TODO: Move to super and have protected variables that both report to.
    public boolean isTablet() {
        return mIsTablet;
    }

    public void setMasterToggleTitle(String title) {
        mMasterToggleTitle = title;
        getActivity().invalidateOptionsMenu();
    }

    public void setMasterToggleTitle(int resId) {
        mMasterToggleResId = resId;
        getActivity().invalidateOptionsMenu();
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
