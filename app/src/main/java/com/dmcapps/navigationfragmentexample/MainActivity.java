package com.dmcapps.navigationfragmentexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.dmcapps.navigationfragmentexample.R;
import com.dmcapps.navigationfragmentexample.MasterDetailExample.MasterDetailNavigationExampleActivity;
import com.dmcapps.navigationfragmentexample.SingleStackExample.SingleStackNavigationExampleActivity;
import com.dmcapps.navigationfragmentexample.TabExample.TabNavigationExample;

import java.util.ArrayList;

public class MainActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {

    ListView mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mList = (ListView)findViewById(android.R.id.list);

        ArrayList<String> items = new ArrayList<String>();
        items.add("Single Stack Example");
        items.add("Master Detail Example");
        items.add("Tab Example");
        mList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items));
        mList.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position == 0) {
            Intent intent = new Intent(this, SingleStackNavigationExampleActivity.class);
            startActivity(intent);
        }
        else if (position == 1) {
            Intent intent = new Intent(this, MasterDetailNavigationExampleActivity.class);
            startActivity(intent);
        }
        else if (position == 2) {
            Intent intent = new Intent(this, TabNavigationExample.class);
            startActivity(intent);
        }
    }
}
