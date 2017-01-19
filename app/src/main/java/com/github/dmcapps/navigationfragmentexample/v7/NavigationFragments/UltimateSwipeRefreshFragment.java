package com.github.dmcapps.navigationfragmentexample.v7.NavigationFragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.github.dmcapps.navigationfragment.v7.NavigationFragment;
import com.github.dmcapps.navigationfragmentexample.R;
import com.github.dmcapps.navigationfragmentexample.TestIntentLaunchingActivity;
import com.marshalchen.ultimaterecyclerview.ItemTouchListenerAdapter;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.quickAdapter.easyRegularAdapter;
import com.marshalchen.ultimaterecyclerview.uiUtils.ScrollSmoothLineaerLayoutManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hesk on 29/2/16.
 */
public class UltimateSwipeRefreshFragment extends NavigationFragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_FRAG_TEXT = "ARG_FRAG_TEXT";
    private static final String ARG_FRAG_COUNT = "ARG_FRAG_COUNT";

    private class vholder extends UltimateRecyclerviewViewHolder {
        TextView tv;

        public vholder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(android.R.id.text1);
        }


    }

    private class adp extends easyRegularAdapter<String, UltimateSwipeRefreshFragment.vholder> {


        /**
         * dynamic object to start
         *
         * @param list the list source
         */
        public adp(List list) {
            super(list);
        }

        /**
         * the layout id for the normal data
         *
         * @return the ID
         */
        @Override
        protected int getNormalLayoutResId() {
            return android.R.layout.simple_list_item_1;
        }

        /**
         * this is the Normal View Holder initiation
         *
         * @param view view
         * @return holder
         */
        @Override
        protected vholder newViewHolder(View view) {
            return new vholder(view);
        }

        /**
         * binding normal view holder
         *
         * @param holder   holder class
         * @param data     data
         * @param position position
         */
        @Override
        protected void withBindHolder(vholder holder, String data, int position) {
            holder.tv.setText(data);
        }
    }

    public static UltimateSwipeRefreshFragment newInstance(String param1, int fragCount) {
        UltimateSwipeRefreshFragment fragment = new UltimateSwipeRefreshFragment();
        Bundle args = new Bundle();
        args.putString(ARG_FRAG_TEXT, param1);
        args.putInt(ARG_FRAG_COUNT, fragCount);
        fragment.setArguments(args);
        return fragment;
    }

    public static Bundle getBundle() {
        Bundle args = new Bundle();
        args.putString(ARG_FRAG_TEXT, "sample");
        args.putInt(ARG_FRAG_COUNT, 0);
        return args;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View iv = inflater.inflate(R.layout.fragment_urv, container, false);
        return iv;
    }

    private int mFragCount;
    private UltimateRecyclerView listview_layout;
    private adp madapter;
    private final ArrayList<String> strr = new ArrayList<>();

    private ArrayList<String> getList() {
        strr.add("new");
        strr.add("new1");
        strr.add("new2");
        strr.add("new3");
        strr.add("new4");
        strr.add("new322225");
        strr.add("new12225");
        strr.add("new235");
        strr.add("new55");
        strr.add("new521121");
        strr.add("new6");
        strr.add("new7");
        return strr;
    }


    protected void enableItemClick() {
        ItemTouchListenerAdapter itemTouchListenerAdapter = new ItemTouchListenerAdapter(listview_layout.mRecyclerView,
                new ItemTouchListenerAdapter.RecyclerViewOnItemClickListener() {
                    @Override
                    public void onItemClick(RecyclerView parent, View clickedView, int position) {

                        SampleFragment another_nav = SampleFragment.newInstance("This is a replaced root Fragment", 0);
                        Log.d("nice", "this is something nice to happen");
                        replaceRootFragment(another_nav);
                    }

                    @Override
                    public void onItemLongClick(RecyclerView parent, View clickedView, int position) {
                        presentFragment(SampleFragment.newInstance("Fragment added to Stack.", (mFragCount + 1)));
                    }
                });
        listview_layout.mRecyclerView.addOnItemTouchListener(itemTouchListenerAdapter);
    }

    private void top_line_buttons(View view) {

        ((Button) view.findViewById(R.id.sample_btn_back)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissFragment();
            }
        });

        ((Button) view.findViewById(R.id.sample_btn_replace_root)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceRootFragment(SampleFragment.newInstance("This is a replaced root Fragment", 0));
            }
        });

        ((Button) view.findViewById(R.id.sample_btn_launch_activity)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getContext(), TestIntentLaunchingActivity.class));
            }
        });

        ((Button) view.findViewById(R.id.sample_btn_continue)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presentFragment(SampleFragment.newInstance("Fragment added to Stack.", (mFragCount + 1)));
            }
        });
    }


    public int getFragCount() {
        return mFragCount;
    }

    private void processInsertHeader() {
        listview_layout.setParallaxHeader(LayoutInflater.from(getActivity()).inflate(R.layout.empty, listview_layout, false));
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listview_layout = (UltimateRecyclerView) view.findViewById(R.id.superlist);
        listview_layout.setInflater(LayoutInflater.from(getActivity()));
        listview_layout.setHasFixedSize(true);
        listview_layout.setSaveEnabled(true);

        ScrollSmoothLineaerLayoutManager linearLayoutManager = new ScrollSmoothLineaerLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false, 300);
        listview_layout.setLayoutManager(linearLayoutManager);
        madapter = new adp(getList());
        listview_layout.setAdapter(madapter);
        enableItemClick();
        top_line_buttons(view);
        processInsertHeader();

    }
}
