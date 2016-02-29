package com.dmcapps.navigationfragmentexample.GitIssue5Example;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dmcapps.navigationfragment.fragments.NavigationFragment;
import com.dmcapps.navigationfragment.manager.NavigationManagerFragment;
import com.dmcapps.navigationfragment.manager.SingleStackNavigationManagerFragment;
import com.dmcapps.navigationfragmentexample.NavigationFragments.SampleFragment;
import com.dmcapps.navigationfragmentexample.R;

import java.util.UUID;

public class GitIssue5ExampleActivity extends basicImplementation {
    @Override
    protected NavigationFragment initFragment() {
        return ViewPagerFragment.newInstance();
    }
}
