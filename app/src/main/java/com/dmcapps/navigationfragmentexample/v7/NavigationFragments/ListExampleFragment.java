package com.dmcapps.navigationfragmentexample.v7.NavigationFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.dmcapps.navigationfragment.v7.fragments.NavigationListFragment;
import com.dmcapps.navigationfragmentexample.TestIntentLaunchingActivity;

/**
 * A fragment representing a list of Items.
 */
public class ListExampleFragment extends NavigationListFragment {

    public static ListExampleFragment newInstance() {
        ListExampleFragment fragment = new ListExampleFragment();
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ListExampleFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("List Example");

        setListAdapter(new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                new String[] { "Next Fragment", "Launch Activity"}));
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        if (position == 0) {
            presentFragment(SampleFragment.newInstance("From the List Fragment", 0));
        }
        else {
            getActivity().startActivity(new Intent(getContext(), TestIntentLaunchingActivity.class));
        }
    }

}
