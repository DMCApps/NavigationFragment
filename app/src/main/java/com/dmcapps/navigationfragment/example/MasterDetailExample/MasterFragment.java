package com.dmcapps.navigationfragment.example.MasterDetailExample;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dmcapps.navigationfragment.R;
import com.dmcapps.navigationfragment.fragment.pattern.NavigationFragment;

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
        return inflater.inflate(R.layout.fragment_master, container, false);
    }
}
