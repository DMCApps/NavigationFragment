package com.github.dmcapps.navigationfragmentexample.v17;

import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.github.dmcapps.navigationfragment.v17.NavigationFragment;
import com.github.dmcapps.navigationfragment.v17.StackNavigationManagerFragment;
import com.github.dmcapps.navigationfragment.v17.NavigationManagerFragment;

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
        NavigationManagerFragment fragment = getSingleStackNavigationFragmentManager();
        if (!fragment.getNavigationManager().onBackPressed()) {
            super.onBackPressed();
        }
    }

    protected StackNavigationManagerFragment getSingleStackNavigationFragmentManager() {
        return (StackNavigationManagerFragment)getFragmentManager().findFragmentByTag(mSingleStackNavigationManagerFragmentTag);
    }

    private void addFragment(StackNavigationManagerFragment fragment, int container) {
        mSingleStackNavigationManagerFragmentTag = UUID.randomUUID().toString();

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(container, fragment, mSingleStackNavigationManagerFragmentTag);
        ft.commit();
    }

    private void showFragment(String tag) {
        Fragment fragment = getFragmentManager().findFragmentByTag(tag);

        if (fragment != null && fragment.isDetached()) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.attach(fragment);
            ft.commit();
        }
    }
}
