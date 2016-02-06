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

        addFragment(NavigationManagerFragment.newInstance());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("NavTag", mNavigationFragmentTag);
    }

    private void addFragment(NavigationManagerFragment fragment)
    {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (!showFragment(ft)) {
            addFragment(fragment, ft);
        }
        ft.commit();

        fragment.presentFragment(SampleFragment.newInstance("This is a title of the fragment to show"));
    }

    private void addFragment(NavigationManagerFragment fragment, FragmentTransaction ft)
    {
        mNavigationFragmentTag = UUID.randomUUID().toString();
        ft.add(android.R.id.content, fragment, mNavigationFragmentTag);
    }

    private boolean showFragment(FragmentTransaction ft)
    {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(mNavigationFragmentTag);
        boolean fragmentExists = fragment != null;
        if (fragmentExists) {
            ft.attach(fragment);
        }
        return fragmentExists;
    }
}
