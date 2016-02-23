package com.dmcapps.navigationfragment.manager;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.dmcapps.navigationfragment.R;
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
    private static final String TAG = SingleStackNavigationManagerFragment.class.getSimpleName();

    private static final int ACTIONABLE_STACK_SIZE = 1;

    private INavigationFragment mRootFragment;

    public static SingleStackNavigationManagerFragment newInstance() {
        return new SingleStackNavigationManagerFragment();
    }

    public SingleStackNavigationManagerFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_single_stack_navigation_manager, container, false);

        mIsPortrait = view.findViewById(R.id.single_stack_phone_layout_main_portrait) != null
                || view.findViewById(R.id.single_stack_tablet_layout_main_portrait) != null;
        mIsTablet = view.findViewById(R.id.single_stack_tablet_layout_main_portrait) != null
                || view.findViewById(R.id.single_stack_tablet_layout_main_land) != null;

        return view;
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

    public void setRootFragment(INavigationFragment rootFragment) {
        mRootFragment = rootFragment;
    }

    @Override
    public int getMinStackSize() {
        return ACTIONABLE_STACK_SIZE;
    }

    @Override
    public int getPushStackFrameId() {
        return R.id.single_stack_content;
    }

    private INavigationFragment getRootFragment() {
        if (mRootFragment == null) {
            throw new RuntimeException("You must call setRootFragment before attaching the Manager to a Fragment Transaction");
        }

        return mRootFragment;
    }
}
