package com.dmcapps.navigationfragment.manager.micromanagers;

import com.dmcapps.navigationfragment.R;
import com.dmcapps.navigationfragment.fragments.INavigationFragment;

/**
 * Created by dcarmo on 2016-02-24.
 */
public class ManagerConfig {

    public static final int NO_ANIMATION = 0;

    public int minStackSize;

    public int pushContainerId;

    public int presentAnimationIn;
    public int presentAnimationOut;

    public int dismissAnimationIn;
    public int dismissAnimationOut;

    public INavigationFragment rootFragment;

    public INavigationFragment masterFragment;
    public INavigationFragment detailFragment;

    public ManagerConfig() {
        presentAnimationIn = R.anim.slide_in_from_right;
        presentAnimationOut = R.anim.slide_out_to_left;

        dismissAnimationIn = R.anim.slide_in_from_left;
        dismissAnimationOut = R.anim.slide_out_to_right;
    }

    public void nullifyInitialFragments() {
        rootFragment = null;
        masterFragment = null;
        detailFragment = null;
    }

}
