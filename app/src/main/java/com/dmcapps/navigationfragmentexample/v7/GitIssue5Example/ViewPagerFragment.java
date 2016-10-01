package com.dmcapps.navigationfragmentexample.v7.GitIssue5Example;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dmcapps.navigationfragment.v7.fragments.NavigationFragment;
import com.dmcapps.navigationfragmentexample.v7.DrawerExample.NonNavigationFragment;
import com.dmcapps.navigationfragmentexample.v7.NavigationFragments.SampleFragment;
import com.dmcapps.navigationfragmentexample.v7.NavigationFragments.UltimateSwipeRefreshFragment;
import com.dmcapps.navigationfragmentexample.R;

/**
 */
public class ViewPagerFragment extends NavigationFragment {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    public static ViewPagerFragment newInstance() {
        return new ViewPagerFragment();
    }

    public ViewPagerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_view_pager, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) view.findViewById(R.id.view_pager_container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position) {
                case 0:
                    return NonNavigationFragment.newInstance("Non Nav Fragment 1");
                case 1:
                    return SampleFragment.newInstance("Start Frag 3", 0);
                case 2:
                    return NonNavigationFragment.newInstance("Non Nav Fragment 2");
                case 3:
                    return UltimateSwipeRefreshFragment.newInstance("NavUlt", 0);
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Single Stack 1";
                case 1:
                    return "View Pager Navigation";
                case 2:
                    return "Single Stack 2";
                case 3:
                    return "urv recyclerview";
            }
            return null;
        }
    }
}
