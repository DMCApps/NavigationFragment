package com.dmcapps.navigationfragmentexample.v7.DrawerExample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.dmcapps.navigationfragment.common.interfaces.Navigation;
import com.dmcapps.navigationfragment.v7.fragments.NavigationFragment;
import com.dmcapps.navigationfragment.v7.core.NavigationManagerFragment;
import com.dmcapps.navigationfragment.v7.StackNavigationManagerFragment;
import com.dmcapps.navigationfragmentexample.v7.NavigationFragments.SampleFragment;
import com.dmcapps.navigationfragmentexample.R;

public class NavigationDrawerExampleActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer_example);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        initialNavigationFragmentManager(SampleFragment.newInstance("Nav Camera", 0), "Camera");
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            if(mVisibleFragment instanceof NavigationManagerFragment) {
                if (!((NavigationManagerFragment)mVisibleFragment).onBackPressed()) {
                    super.onBackPressed();
                }
            }
            else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camara) {
            initialNavigationFragmentManager(SampleFragment.newInstance("Nav Camera", 0), "Camera");
        }
        else if (id == R.id.nav_gallery) {
            initialNavigationFragmentManager(NonNavigationFragment.newInstance("Non-Nav Gallery"), "Gallery");
        }
        else if (id == R.id.nav_slideshow) {
            initialNavigationFragmentManager(SampleFragment.newInstance("Nav Slideshow", 0), "Slideshow");
        }
        else if (id == R.id.nav_manage) {
            initialNavigationFragmentManager(NonNavigationFragment.newInstance("Non-Nav Manage"), "Gallery");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private int targetHomeFrame() {
        return R.id.frag_container;
    }

    private Fragment mVisibleFragment;

    /**
     * require android-support-v4 import and the regular android fragment
     *
     * @param fragment    the unknown typed fragment
     * @param title       the string in title
     * @param oldFragment the previous fragment
     * @param closeDrawer if it needs to close the drawer after the new fragment has been rendered
     */
    public void setFragment(Fragment fragment, String title, @Nullable Fragment oldFragment, boolean closeDrawer) {
        mVisibleFragment = fragment;
        setTitle(title);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (oldFragment != null && oldFragment != fragment)
            ft.remove(oldFragment);

        ft.replace(targetHomeFrame(), fragment).commit();
    }

    private String mSingleStackNavigationManagerFragmentTag;

    public void initialNavigationFragmentManager(Fragment firstFragment, @Nullable String title) {
        if (title != null) {
            setTitle(title);
        }
        if (firstFragment instanceof NavigationFragment) {
            StackNavigationManagerFragment navManager = StackNavigationManagerFragment.newInstance((Navigation)firstFragment);
            setFragment(navManager, title, mVisibleFragment, false);
        }
        else if (firstFragment instanceof Fragment) {
            setFragment(firstFragment, title, mVisibleFragment, false);
        }
    }
}