package com.dmcapps.navigationfragment.fragment.pattern;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

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
    private Stack<NavigationFragment> mFragments;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment NavigationManagerFragment.
     */
    public static NavigationManagerFragment newInstance() {
        NavigationManagerFragment fragment = new NavigationManagerFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public NavigationManagerFragment() {
        // Required empty public constructor
        mViewId = ++mUniqueViewIdGenerator;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mFragmentFrame = new FrameLayout(getActivity());
        mFragmentFrame.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mFragmentFrame.setId(mViewId);
        return mFragmentFrame;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setRetainInstance(true);
        presentFragment(getFragments().peek());
    }

    public void presentFragment(NavigationFragment fragment) {
        if (mFragmentFrame == null) {
            // Add the fragment to the stack as the first fragment to load once the frame is ready.
            getFragments().add(fragment);
        }
        else {
            NavigationFragment navFragment = (NavigationFragment)fragment;

            navFragment.setNavigationManager(this);
            FragmentManager childFragManager = getChildFragmentManager();
            FragmentTransaction fragTrans = childFragManager.beginTransaction();

            NavigationFragment topFrag = getFragments().peek();

            if (childFragManager.findFragmentByTag(topFrag.getNavTag()) == null) {
                fragTrans.add(mFragmentFrame.getId(), fragment, navFragment.getNavTag());
            }
            else {
                fragTrans.detach(topFrag);
            }

            fragTrans.commit();
        }
    }

    public void dismissTopFragment() {

    }

    public Stack<NavigationFragment> getFragments() {
        if (mFragments == null) {
            mFragments = new Stack<>();
        }
        return mFragments;
    }

}
