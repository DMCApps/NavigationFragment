package com.dmcapps.navigationfragmentexample.v7;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.dmcapps.navigationfragmentexample.R;
import com.dmcapps.navigationfragmentexample.v7.DrawerClearStackExample.NavigationDrawerClearStackExampleActivity;
import com.dmcapps.navigationfragmentexample.v7.DrawerExample.NavigationDrawerExampleActivity;
import com.dmcapps.navigationfragmentexample.v7.GitIssue5Example.GitIssue10ExampleActivity;
import com.dmcapps.navigationfragmentexample.v7.GitIssue5Example.GitIssue5ExampleActivity;
import com.dmcapps.navigationfragmentexample.v7.ListExample.ListExampleActivity;
import com.dmcapps.navigationfragmentexample.v7.MasterDetailExample.MasterDetailNavigationExampleActivity;
import com.dmcapps.navigationfragmentexample.v7.OverrideDefaultAnimationsExample.OverrideDefaultAnimationsExampleActivity;
import com.dmcapps.navigationfragmentexample.v7.SingleStackExample.SingleStackNavigationExampleActivity;
import com.dmcapps.navigationfragmentexample.v7.ViewPagerExample.ViewPagerNavigationExampleActivity;

import java.util.ArrayList;

/**
 * Created by dcarmo on 2016-10-01.
 */
public class SupportExamplesActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {

    ListView mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mList = (ListView) findViewById(android.R.id.list);

        ArrayList<String> items = new ArrayList<>();
        items.add("Single Stack Example");
        items.add("Master Detail Example");
        items.add("Tab Example");
        items.add("List Example");
        items.add("Navigation Drawer Example (uses replace and remove as well as non-Navigation Fragments)");
        items.add("Override Default Animations Example");
        items.add("Navigation Fragment with View Pager. Navigating on view pager tab and view pager itself (git issue 5)");
        items.add("Navigation Fragment with View Pager and SmartTabLayout. Navigating on view pager tab and view pager itself (git issue 10)");
        items.add("Navigation Fragment clears stack before presenting");

        mList.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items));
        mList.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = null;

        if (position == 0) {
            intent = new Intent(this, SingleStackNavigationExampleActivity.class);
        } else if (position == 1) {
            intent = new Intent(this, MasterDetailNavigationExampleActivity.class);
        } else if (position == 2) {
            intent = new Intent(this, ViewPagerNavigationExampleActivity.class);
        } else if (position == 3) {
            intent = new Intent(this, ListExampleActivity.class);
        } else if (position == 4) {
            intent = new Intent(this, NavigationDrawerExampleActivity.class);
        } else if (position == 5) {
            intent = new Intent(this, OverrideDefaultAnimationsExampleActivity.class);
        } else if (position == 6) {
            intent = new Intent(this, GitIssue5ExampleActivity.class);
        } else if (position == 7) {
            intent = new Intent(this, GitIssue10ExampleActivity.class);
        } else if (position == 8) {
            intent = new Intent(this, NavigationDrawerClearStackExampleActivity.class);
        }

        startActivity(intent);
    }
}
