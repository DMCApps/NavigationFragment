package com.dmcapps.navigationfragment.fragment.pattern;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.dmcapps.navigationfragment.R;

import java.util.Stack;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NavigationManagerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NavigationManagerFragment extends Fragment {

    private static int mUniqueViewIdGenerator;
    private int mViewId;

    private FrameLayout mFragmentFrame;
    private Stack<String> mFragmentTags;
    private NavigationFragment mRootFragment;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment NavigationManagerFragment.
     */
    public static NavigationManagerFragment newInstance() {
        NavigationManagerFragment fragment = new NavigationManagerFragment();
        return fragment;
    }

    public NavigationManagerFragment() {
        // Required empty public constructor
        mViewId = ++mUniqueViewIdGenerator;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mFragmentFrame == null) {
            mFragmentFrame = new FrameLayout(getActivity());
            mFragmentFrame.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            mFragmentFrame.setId(mViewId);
        }
        return mFragmentFrame;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setRetainInstance(true);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (getFragmentTags().size() == 0) {
            presentFragment(mRootFragment);
        }
        else {
            // TODO: Resume state how to do this.
        }
    }

    public void setRootFragment(NavigationFragment rootFragment) {
        mRootFragment = rootFragment;
    }

    public void presentFragment(NavigationFragment navFragment) {
        navFragment.setNavigationManager(this);
        FragmentManager childFragManager = getChildFragmentManager();
        FragmentTransaction childFragTrans = childFragManager.beginTransaction();

        // TODO: Better way to do this?
        if (getFragmentTags().size() > 0) {
            childFragTrans.setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
            Fragment topFrag = childFragManager.findFragmentByTag(getFragmentTags().peek());
            if (topFrag != null) {
                childFragTrans.detach(topFrag);
            }
        }

        childFragTrans.add(mFragmentFrame.getId(), navFragment, navFragment.getNavTag());
        addFragmentToStack(navFragment);

        childFragTrans.commit();
    }

    public void dismissTopFragment() {
        if (getFragmentTags().size() > 1) {
            FragmentManager childFragManager = getChildFragmentManager();
            FragmentTransaction childFragTrans = childFragManager.beginTransaction();
            childFragTrans.setCustomAnimations(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
            childFragTrans.remove(childFragManager.findFragmentByTag(getFragmentTags().pop()));
            childFragTrans.attach(childFragManager.findFragmentByTag(getFragmentTags().peek()));
            childFragTrans.commit();

            Log.i("NavigationManager", "Fragments in child stack " + childFragManager.getFragments().size());
        }
    }

    public boolean onBackPressed() {
        if (getFragmentTags().size() > 1) {
            dismissTopFragment();
            return true;
        }

        return false;
    }

    private void addFragmentToStack(NavigationFragment navFragment) {
        getFragmentTags().add(navFragment.getNavTag());
    }

    private Stack<String> getFragmentTags() {
        if (mFragmentTags == null) {
            mFragmentTags = new Stack<>();
        }
        return mFragmentTags;
    }

    private class FragmentTransitionHelper {
        //public static Animation
    }

}
