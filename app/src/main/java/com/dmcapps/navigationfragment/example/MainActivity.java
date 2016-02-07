package com.dmcapps.navigationfragment.example;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dmcapps.navigationfragment.R;
import com.dmcapps.navigationfragment.fragment.pattern.manager.NavigationManagerFragment;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private String mNavigationFragmentTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example_single_navigation_stack);

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

    private void addFragment(NavigationManagerFragment fragment)  {
        if (!showFragment(mNavigationFragmentTag)) {
            mNavigationFragmentTag = UUID.randomUUID().toString();

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.main_container, fragment, mNavigationFragmentTag);
            ft.commit();

            fragment.setRootFragment(SampleFragment.newInstance("Root Fragment in the Stack"));
        }
    }

    private boolean showFragment(String tag)  {
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

    @Override
    public void onBackPressed() {
        NavigationManagerFragment fragment = (NavigationManagerFragment)getSupportFragmentManager().findFragmentByTag(mNavigationFragmentTag);
        if (!fragment.onBackPressed()) {
            super.onBackPressed();
        }
    }
}
