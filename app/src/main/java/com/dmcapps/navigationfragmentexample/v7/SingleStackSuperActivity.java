package com.dmcapps.navigationfragmentexample.v7;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.dmcapps.navigationfragment.v7.fragments.NavigationFragment;
import com.dmcapps.navigationfragment.v7.core.NavigationManagerFragment;
import com.dmcapps.navigationfragment.v7.StackNavigationManagerFragment;

import java.util.UUID;

/**
 * Created by DCarmo on 16-03-11.
 */
public abstract class SingleStackSuperActivity extends AppCompatActivity {

    private static final String STATE_NAV_TAG = "NAV_TAG";

    private String mSingleStackNavigationManagerFragmentTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            mSingleStackNavigationManagerFragmentTag = savedInstanceState.getString(STATE_NAV_TAG);
        }
    }

    protected abstract NavigationFragment rootFragment();
    protected abstract int getContainerId();

    @Override
    protected void onResume() {
        super.onResume();

        if (mSingleStackNavigationManagerFragmentTag == null) {
            StackNavigationManagerFragment navManager = StackNavigationManagerFragment.newInstance(rootFragment());
            addFragment(navManager, getContainerId());
        } else {
            showFragment(mSingleStackNavigationManagerFragmentTag);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(mSingleStackNavigationManagerFragmentTag);
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
        NavigationManagerFragment fragment = getSingleStackNavigationFragmentManager();
        if (!fragment.onBackPressed()) {
            super.onBackPressed();
        }
    }

    protected StackNavigationManagerFragment getSingleStackNavigationFragmentManager() {
        return (StackNavigationManagerFragment)getSupportFragmentManager().findFragmentByTag(mSingleStackNavigationManagerFragmentTag);
    }

    private void addFragment(StackNavigationManagerFragment fragment, int container) {
        mSingleStackNavigationManagerFragmentTag = UUID.randomUUID().toString();

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(container, fragment, mSingleStackNavigationManagerFragmentTag);
        ft.commit();
    }

    private void showFragment(String tag) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);

        if (fragment != null && fragment.isDetached()) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.attach(fragment);
            ft.commit();
        }
    }
}
