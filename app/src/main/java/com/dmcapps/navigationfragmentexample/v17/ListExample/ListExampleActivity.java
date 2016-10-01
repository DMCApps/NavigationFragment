package com.dmcapps.navigationfragmentexample.v17.ListExample;

import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.dmcapps.navigationfragment.v17.StackNavigationManagerFragment;
import com.dmcapps.navigationfragment.v17.core.NavigationManagerFragment;
import com.dmcapps.navigationfragmentexample.v17.NavigationFragments.ListExampleFragment;

import java.util.UUID;

public class ListExampleActivity extends AppCompatActivity {
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
            StackNavigationManagerFragment navManager = StackNavigationManagerFragment.newInstance(ListExampleFragment.newInstance());
            addFragment(navManager);
        }
        else {
            showFragment(mSingleStackNavigationManagerFragmentTag);
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

    private void addFragment(StackNavigationManagerFragment fragment)  {
        mSingleStackNavigationManagerFragmentTag = UUID.randomUUID().toString();

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(android.R.id.content, fragment, mSingleStackNavigationManagerFragmentTag);
        ft.commit();
    }

    private void showFragment(String tag)  {
        Fragment fragment = getFragmentManager().findFragmentByTag(tag);

        if (fragment != null && fragment.isDetached()) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.attach(fragment);
            ft.commit();
        }
    }

    @Override
    public void onBackPressed() {
        NavigationManagerFragment fragment = (NavigationManagerFragment)getFragmentManager().findFragmentByTag(mSingleStackNavigationManagerFragmentTag);
        if (!fragment.onBackPressed()) {
            super.onBackPressed();
        }
    }
}
