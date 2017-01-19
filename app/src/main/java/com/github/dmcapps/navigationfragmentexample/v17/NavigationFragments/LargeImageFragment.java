package com.github.dmcapps.navigationfragmentexample.v17.NavigationFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.dmcapps.navigationfragmentexample.R;
import com.github.dmcapps.navigationfragment.v17.NavigationFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class LargeImageFragment extends NavigationFragment {

    public static LargeImageFragment newInstance() {
        return new LargeImageFragment();
    }

    public LargeImageFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_large_image, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final ImageView largeImageView = (ImageView)getView().findViewById(R.id.iv_large_image);
        largeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissFragment();
            }
        });
    }
}
