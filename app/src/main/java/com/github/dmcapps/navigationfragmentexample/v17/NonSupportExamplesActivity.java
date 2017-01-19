package com.github.dmcapps.navigationfragmentexample.v17;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.github.dmcapps.navigationfragmentexample.R;
import com.github.dmcapps.navigationfragmentexample.v17.DrawerClearStackExample.NavigationDrawerClearStackExampleActivity;
import com.github.dmcapps.navigationfragmentexample.v17.DrawerExample.NavigationDrawerExampleActivity;
import com.github.dmcapps.navigationfragmentexample.v17.OverrideDefaultAnimationsExample.OverrideDefaultAnimationsExampleActivity;
import com.github.dmcapps.navigationfragmentexample.v17.SingleStackExample.SingleStackNavigationExampleActivity;
import com.github.dmcapps.navigationfragmentexample.v17.TransitionExample.TransitionExampleActivity;
import com.github.dmcapps.navigationfragmentexample.v17.ViewPagerExample.ViewPagerNavigationExampleActivity;

import java.util.ArrayList;

/**
 * Created by dcarmo on 2016-10-01.
 */
public class NonSupportExamplesActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {

    ListView mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mList = (ListView) findViewById(android.R.id.list);

        ArrayList<String> items = new ArrayList<>();
        items.add("Single Stack Example");
        items.add("Tab Example");
        items.add("Navigation Drawer Example (uses replace and remove as well as non-Navigation Fragments)");
        items.add("Override Default Animations Example");
        items.add("Navigation Fragment clears stack before presenting");
        items.add("Transition Example");

        mList.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items));
        mList.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = null;

        if (position == 0) {
            intent = new Intent(this, SingleStackNavigationExampleActivity.class);
        } else if (position == 1) {
            intent = new Intent(this, ViewPagerNavigationExampleActivity.class);
        } else if (position == 2) {
            intent = new Intent(this, NavigationDrawerExampleActivity.class);
        } else if (position == 3) {
            intent = new Intent(this, OverrideDefaultAnimationsExampleActivity.class);
        } else if (position == 4) {
            intent = new Intent(this, NavigationDrawerClearStackExampleActivity.class);
        } else if (position == 5) {
            intent = new Intent(this, TransitionExampleActivity.class);
        }

        startActivity(intent);
    }
}
