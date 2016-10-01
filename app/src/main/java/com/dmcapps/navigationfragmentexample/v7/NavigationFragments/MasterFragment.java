package com.dmcapps.navigationfragmentexample.v7.NavigationFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.dmcapps.navigationfragmentexample.R;
import com.dmcapps.navigationfragment.v7.fragments.NavigationFragment;

public class MasterFragment extends NavigationFragment {

    public static MasterFragment newInstance() {
        MasterFragment fragment = new MasterFragment();
        return fragment;
    }

    public MasterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_master, container, false);

        ((Button)view.findViewById(R.id.master_btn_add)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Example when we have a master detail showing
                if (getNavigationManager().getTopFragment() instanceof SampleFragment) {
                    // Just for the example so that we can keep the count correct.
                    int fragCount = ((SampleFragment)getNavigationManager().getTopFragment()).getFragCount();
                    presentFragment(SampleFragment.newInstance("Fragment added to the Stack", fragCount + 1));
                }
                // This example is when we are collapsed on a phone.
                else {
                    presentFragment(SampleFragment.newInstance("Fragment added to the Stack", 0));
                }
            }
        });

        ((Button)view.findViewById(R.id.master_btn_replace)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceRootFragment(SampleFragment.newInstance("This is a replaced root Fragment", 0));
            }
        });

        ((Button)view.findViewById(R.id.master_btn_back)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissFragment();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        setTitle("Master Fragment");
    }
}
