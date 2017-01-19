package com.github.dmcapps.navigationfragmentexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.github.dmcapps.navigationfragmentexample.v7.SupportExamplesActivity;
import com.github.dmcapps.navigationfragmentexample.v17.NonSupportExamplesActivity;

import java.util.ArrayList;

public class MainActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {

    ListView mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mList = (ListView) findViewById(android.R.id.list);

        ArrayList<String> items = new ArrayList<>();
        items.add("Support Examples");
        items.add("Non-Support Examples");

        mList.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items));
        mList.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = null;

        if (position == 0) {
            intent = new Intent(this, SupportExamplesActivity.class);
        } else if (position == 1) {
            intent = new Intent(this, NonSupportExamplesActivity.class);
        }

        startActivity(intent);
    }
}
