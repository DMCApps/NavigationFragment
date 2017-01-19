package com.github.dmcapps.navigationfragmentexample.v7.GitIssue5Example;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.dmcapps.navigationfragment.common.core.NavigationManager;
import com.github.dmcapps.navigationfragment.v7.NavigationFragment;
import com.github.dmcapps.navigationfragmentexample.v7.NavigationFragments.UltimateSwipeRefreshFragment;
import com.github.dmcapps.navigationfragmentexample.R;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.util.ArrayList;

/**
 * Created by hesk on 29/2/16.
 */
public class SmartTabLayout extends NavigationFragment {

    public static SmartTabLayout newInstance() {
        return new SmartTabLayout();
    }

    public SmartTabLayout() {
        // Required empty public constructor
    }

    public class NavFragmentPagerItemAdapter extends com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter {

        private final NavigationManager nmf;

        public NavFragmentPagerItemAdapter(FragmentManager fm, NavigationManager navh, FragmentPagerItems pages) {
            super(fm, pages);
            nmf = navh;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }
    }


    private com.ogaclejapan.smarttablayout.SmartTabLayout mTab;
    private ViewPager pager;
    private FragmentManager mChildFragmentManager;
    private int toolBarHeight, customBarHeight;
    //  private boolean remote_event_to_show, remote_event_to_force_hide;
    private ArrayList<String> tab_list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_smpager, container, false);
    }

    public NavFragmentPagerItemAdapter getAdapter() {
        final FragmentPagerItems.Creator itemCreator = FragmentPagerItems.with(getActivity());

        itemCreator.add(
                "k1",
                UltimateSwipeRefreshFragment.class,
                UltimateSwipeRefreshFragment.getBundle()
        );
        itemCreator.add(
                "k2",
                UltimateSwipeRefreshFragment.class,
                UltimateSwipeRefreshFragment.getBundle()
        );
        itemCreator.add(
                "k3",
                UltimateSwipeRefreshFragment.class,
                UltimateSwipeRefreshFragment.getBundle()
        );
        itemCreator.add(
                "k4",
                UltimateSwipeRefreshFragment.class,
                UltimateSwipeRefreshFragment.getBundle()
        );


        return new NavFragmentPagerItemAdapter(getChildFragmentManager(), getNavigationManager(), itemCreator.create());
    }


    @SuppressLint("ResourceAsColor")
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onViewCreated(View v, Bundle b) {
        mTab = (com.ogaclejapan.smarttablayout.SmartTabLayout) v.findViewById(R.id.tabs);
        pager = (ViewPager) v.findViewById(R.id.view_pager_container);
        pager.setAdapter(getAdapter());
        pager.setOffscreenPageLimit(3);
        //pager.setOnScrollChangeListener(this);
        mTab.setViewPager(pager);
        // mTab.addOnAttachStateChangeListener(this);
        pager.setCurrentItem(0, true);
    }


}
