package com.dmcapps.navigationfragmentexample.MasterDetailExample;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.dmcapps.navigationfragment.manager.managers.MasterDetailNavigationManagerFragment;
import com.dmcapps.navigationfragment.manager.parent.NavigationManagerFragment;
import com.dmcapps.navigationfragmentexample.NavigationFragments.MasterFragment;
import com.dmcapps.navigationfragmentexample.NavigationFragments.SampleFragment;

import java.util.UUID;

public class MasterDetailNavigationExampleActivity extends AppCompatActivity {
    private static final String STATE_NAV_TAG = "NAV_TAG";

    private String mNavigationManagerFragmentTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            mNavigationManagerFragmentTag = savedInstanceState.getString(STATE_NAV_TAG);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mNavigationManagerFragmentTag == null) {
            MasterFragment masterFrag = MasterFragment.newInstance();
            SampleFragment detailFrag = SampleFragment.newInstance("Detail Fragment in the Stack", 0);
            MasterDetailNavigationManagerFragment managerFragment = MasterDetailNavigationManagerFragment.newInstance(masterFrag, detailFrag);
            managerFragment.setManageMasterActionBarToggle(true);
            addFragment(managerFragment);
        }
        else {
            showFragment(mNavigationManagerFragmentTag);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(mNavigationManagerFragmentTag);
        ft.detach(fragment);
        ft.commit();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(STATE_NAV_TAG, mNavigationManagerFragmentTag);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            MasterDetailNavigationManagerFragment fragment = (MasterDetailNavigationManagerFragment)getSupportFragmentManager().findFragmentByTag(mNavigationManagerFragmentTag);
            fragment.toggleMaster();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void addFragment(MasterDetailNavigationManagerFragment fragment)  {
        mNavigationManagerFragmentTag = UUID.randomUUID().toString();

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(android.R.id.content, fragment, mNavigationManagerFragmentTag);
        ft.commit();
        fm.executePendingTransactions();
    }

    private void showFragment(String tag)  {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);

        if (fragment != null && fragment.isDetached()) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.attach(fragment);
            ft.commit();
            getSupportFragmentManager().executePendingTransactions();
        }
    }

    @Override
    public void onBackPressed() {
        NavigationManagerFragment fragment = (NavigationManagerFragment)getSupportFragmentManager().findFragmentByTag(mNavigationManagerFragmentTag);
        if (!fragment.onBackPressed()) {
            super.onBackPressed();
        }
    }
}
