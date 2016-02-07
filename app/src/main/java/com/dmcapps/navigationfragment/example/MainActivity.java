package com.dmcapps.navigationfragment.example;

import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dmcapps.navigationfragment.R;
import com.dmcapps.navigationfragment.fragment.SampleFragment;
import com.dmcapps.navigationfragment.fragment.pattern.NavigationManagerFragment;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private String mNavigationFragmentTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            mNavigationFragmentTag = savedInstanceState.getString("NavTag");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        addFragment(NavigationManagerFragment.newInstance());
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Detach topmost fragment otherwise it will not be correctly displayed
        // after orientation change
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(mNavigationFragmentTag);
        ft.detach(fragment);
        ft.commit();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("NavTag", mNavigationFragmentTag);
    }

    private void addFragment(NavigationManagerFragment fragment)
    {
        boolean didShowFragment = showFragment(mNavigationFragmentTag);
        if (!didShowFragment) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            addFragment(fragment, ft);
            ft.commit();

            fragment.setRootFragment(SampleFragment.newInstance("Root Fragment in the Stack"));
        }
    }

    private void addFragment(NavigationManagerFragment fragment, FragmentTransaction ft)
    {
        mNavigationFragmentTag = UUID.randomUUID().toString();
        ft.add(android.R.id.content, fragment, mNavigationFragmentTag);
    }

    private boolean showFragment(String tag)
    {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);

        if (fragment != null) {
            if (fragment.isDetached()) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.attach(fragment);
                ft.commit();
            }
            return true;
        }
        return false;
    }
}
