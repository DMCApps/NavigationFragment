package com.github.dmcapps.navigationfragmentexample.v17.NavigationFragments;


import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.transition.Slide;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.dmcapps.navigationfragment.common.core.PresentationTransaction;
import com.github.dmcapps.navigationfragment.v17.NavigationFragment;
import com.github.dmcapps.navigationfragmentexample.R;
import com.github.dmcapps.navigationfragmentexample.v17.TransitionExample.DetailTransition;

/**
 * A simple {@link Fragment} subclass.
 */
public class SmallImageFragment extends NavigationFragment {

    public static SmallImageFragment newInstance() {
        return new SmallImageFragment();
    }

    public SmallImageFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_small_image, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final ImageView smallImageView = (ImageView)getView().findViewById(R.id.iv_small_image);
        smallImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationFragment fragment = LargeImageFragment.newInstance();
                PresentationTransaction transaction = beginPresentation();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    setExitTransition(new Slide(Gravity.START));
                    setEnterTransition(new Slide(Gravity.START));

                    fragment.setSharedElementEnterTransition(new DetailTransition());
                    fragment.setSharedElementReturnTransition(new DetailTransition());

                    fragment.setEnterTransition(new Slide(Gravity.END));

                    transaction.addSharedElement(smallImageView, "trans_largeImageView");
                }
                transaction.presentFragment(fragment);
            }
        });
    }
}
