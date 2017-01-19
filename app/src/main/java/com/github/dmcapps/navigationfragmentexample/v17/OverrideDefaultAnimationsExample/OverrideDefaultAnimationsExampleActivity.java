package com.github.dmcapps.navigationfragmentexample.v17.OverrideDefaultAnimationsExample;

import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.github.dmcapps.navigationfragment.v17.StackNavigationManagerFragment;
import com.github.dmcapps.navigationfragment.v17.NavigationManagerFragment;
import com.github.dmcapps.navigationfragmentexample.R;
import com.github.dmcapps.navigationfragmentexample.v17.NavigationFragments.SampleFragment;

import java.util.UUID;

public class OverrideDefaultAnimationsExampleActivity extends AppCompatActivity {

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
            navManager.getNavigationManager().setDefaultPresentAnimations(R.anim.slide_in_from_bottom, R.anim.slide_out_to_top);
            navManager.getNavigationManager().setDefaultDismissAnimations(R.anim.slide_in_from_top, R.anim.slide_out_to_bottom);
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
        if (!fragment.getNavigationManager().onBackPressed()) {
            super.onBackPressed();
        }
    }
}
