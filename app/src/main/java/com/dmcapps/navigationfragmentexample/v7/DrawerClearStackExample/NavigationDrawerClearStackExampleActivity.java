package com.dmcapps.navigationfragmentexample.v7.DrawerClearStackExample;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.dmcapps.navigationfragment.v7.fragments.NavigationFragment;
import com.dmcapps.navigationfragmentexample.v7.NavigationFragments.SampleFragment;
import com.dmcapps.navigationfragmentexample.R;
import com.dmcapps.navigationfragmentexample.v7.SingleStackSuperActivity;

public class NavigationDrawerClearStackExampleActivity extends SingleStackSuperActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer_clear_stack_example);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camara) {
            getSingleStackNavigationFragmentManager().pushFragment(SampleFragment.newInstance("Fragment B", 1));
        }
        else if (id == R.id.nav_gallery) {
            getSingleStackNavigationFragmentManager().clearNavigationStackToRoot();
            getSingleStackNavigationFragmentManager().pushFragment(SampleFragment.newInstance("Fragment C", 1));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected NavigationFragment rootFragment() {
        return SampleFragment.newInstance("StartFragment", 0);
    }

    @Override
    protected int getContainerId() {
        return R.id.frag_container;
    }
}
