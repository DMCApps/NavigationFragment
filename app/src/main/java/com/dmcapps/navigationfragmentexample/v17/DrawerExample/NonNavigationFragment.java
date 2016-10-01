package com.dmcapps.navigationfragmentexample.v17.DrawerExample;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dmcapps.navigationfragmentexample.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NonNavigationFragment extends Fragment {

    private static final String ARG_TEXT = "ARG_TEXT";

    public static NonNavigationFragment newInstance(String text) {

        Bundle args = new Bundle();
        args.putString(ARG_TEXT, text);

        NonNavigationFragment fragment = new NonNavigationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public NonNavigationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_non_navigation, container, false);

        ((TextView)view.findViewById(R.id.non_nav_tv_text)).setText(getArguments().getString(ARG_TEXT));

        return view;
    }

}