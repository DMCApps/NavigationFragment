package com.github.dmcapps.navigationfragmentexample.v17.SingleStackExample;

import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.github.dmcapps.navigationfragment.v17.StackNavigationManagerFragment;
import com.github.dmcapps.navigationfragment.v17.NavigationManagerFragment;
import com.github.dmcapps.navigationfragmentexample.v17.NavigationFragments.SampleFragment;

import java.util.UUID;

public class SingleStackNavigationExampleActivity extends AppCompatActivity {

    private static final String STATE_NAV_TAG = "NAV_TAG";

    private String mSingleStackNavigationManagerFragmentTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            mSingleStackNavigationManagerFragmentTag = savedInstanceState.getString(STATE_NAV_TAG);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mSingleStackNavigationManagerFragmentTag == null) {
            StackNavigationManagerFragment navManager = StackNavigationManagerFragment.newInstance(SampleFragment.newInstance("Root Fragment in the Stack", 0));

            mSingleStackNavigationManagerFragmentTag = UUID.randomUUID().toString();

            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(android.R.id.content, navManager, mSingleStackNavigationManagerFragmentTag);
            ft.commit();
        }
        else {
            Fragment fragment = getFragmentManager().findFragmentByTag(mSingleStackNavigationManagerFragmentTag);

            if (fragment != null && fragment.isDetached()) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.attach(fragment);
                ft.commit();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment fragment = getFragmentManager().findFragmentByTag(mSingleStackNavigationManagerFragmentTag);
        ft.detach(fragment);
        ft.commit();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(STATE_NAV_TAG, mSingleStackNavigationManagerFragmentTag);
    }

    @Override
    public void onBackPressed() {
        NavigationManagerFragment fragment = (NavigationManagerFragment)getFragmentManager().findFragmentByTag(mSingleStackNavigationManagerFragmentTag);
        if (!fragment.getNavigationManager().onBackPressed()) {
            super.onBackPressed();
        }
    }
}
