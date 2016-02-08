package com.dmcapps.navigationfragment.example;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.dmcapps.navigationfragment.R;
import com.dmcapps.navigationfragment.example.MasterDetailExample.MasterDetailNavigationExampleActivity;
import com.dmcapps.navigationfragment.example.SingleStackExample.SingleStackNavigationExampleActivity;

import java.util.ArrayList;

public class MainActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<String> items = new ArrayList<String>();
        items.add("Single Stack Example");
        items.add("Master Detail Example");
        // TODO: Tab Example
        // items.add("Tab Example");
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        if (position == 0) {
            Intent intent = new Intent(this, SingleStackNavigationExampleActivity.class);
            startActivity(intent);
        }
        else if (position == 1) {
            Intent intent = new Intent(this, MasterDetailNavigationExampleActivity.class);
            startActivity(intent);
        }
    }
}
