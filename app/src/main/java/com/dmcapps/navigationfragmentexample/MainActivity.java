package com.dmcapps.navigationfragmentexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.dmcapps.navigationfragmentexample.DrawerExample.NavigationDrawerExampleActivity;
import com.dmcapps.navigationfragmentexample.GitIssue5Example.GitIssue5ExampleActivity;
import com.dmcapps.navigationfragmentexample.ListExample.ListExampleActivity;
import com.dmcapps.navigationfragmentexample.MasterDetailExample.MasterDetailNavigationExampleActivity;
import com.dmcapps.navigationfragmentexample.OverrideDefaultAnimationsExample.OverrideDefaultAnimationsExampleActivity;
import com.dmcapps.navigationfragmentexample.SingleStackExample.SingleStackNavigationExampleActivity;
import com.dmcapps.navigationfragmentexample.ViewPagerExample.ViewPagerNavigationExampleActivity;

import java.util.ArrayList;

public class MainActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {

    ListView mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mList = (ListView)findViewById(android.R.id.list);

        ArrayList<String> items = new ArrayList<>();
        items.add("Single Stack Example");
        items.add("Master Detail Example");
        items.add("Tab Example");
        items.add("List Example");
        items.add("Navigation Drawer Example (uses replace and remove as well as non-Navigation Fragments)");
        items.add("Override Default Animations Example");
        items.add("Navigation Fragment with View Pager. Navigating on view pager tab and view pager itself (git issue 5)");
        mList.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items));
        mList.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = null;

        if (position == 0) {
            intent = new Intent(this, SingleStackNavigationExampleActivity.class);
        }
        else if (position == 1) {
            intent = new Intent(this, MasterDetailNavigationExampleActivity.class);
        }
        else if (position == 2) {
            intent = new Intent(this, ViewPagerNavigationExampleActivity.class);
        }
        else if (position == 3) {
            intent = new Intent(this, ListExampleActivity.class);
        }
        else if (position == 4) {
            intent = new Intent(this, NavigationDrawerExampleActivity.class);
        }
        else if (position == 5) {
            intent = new Intent(this, OverrideDefaultAnimationsExampleActivity.class);
        }
        else if (position == 6) {
            intent = new Intent(this, GitIssue5ExampleActivity.class);
        }

        startActivity(intent);
    }
}
