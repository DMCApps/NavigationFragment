package com.dmcapps.navigationfragment.helper;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;

import java.lang.reflect.Field;

/**
 * A simple {@link Fragment} subclass.
 */
public class RetainedChildFragmentManagerFragment extends Fragment {

    // As we setRetainInstanceState(true), this field will hold the reference of
    // the old ChildFragmentManager. In order to retain the child fragment listener
    // and prevent the "Previously focused view reported id ..." error from happening
    // we need to keep an instance of it for ourselves and cause it to not be dismissed
    // on Screen rotation.
    // For more info read:
    // https://code.google.com/p/android/issues/detail?id=74222
    // More indepth read here:
    // http://ideaventure.blogspot.com.au/2014/10/nested-retained-fragment-lost-state.html
    private FragmentManager mRetainedChildFragmentManager;

    public RetainedChildFragmentManagerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(mRetainedChildFragmentManager != null) {
            // Restore the last retained child fragment manager to the new
            // created fragment
            try {
                Field childFMField = Fragment.class.getDeclaredField("mChildFragmentManager");
                childFMField.setAccessible(true);
                childFMField.set(this, mRetainedChildFragmentManager);
            }
            catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setRetainInstance(true);
    }

    protected FragmentManager getRetainedChildFragmentManager() {
        if(mRetainedChildFragmentManager == null) {
            // Hold the reference of child fragment manager created by
            // Fragment internally and hold it for the future recreated
            // fragment since Fragment#getChildFragmentManager() always
            // creates new fragment manager after rev 20.
            mRetainedChildFragmentManager = getChildFragmentManager();
        }
        return mRetainedChildFragmentManager;
    }
}
