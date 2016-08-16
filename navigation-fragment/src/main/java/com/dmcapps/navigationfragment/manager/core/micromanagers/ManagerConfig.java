package com.dmcapps.navigationfragment.manager.core.micromanagers;

import com.dmcapps.navigationfragment.R;
import com.dmcapps.navigationfragment.fragments.INavigationFragment;

import java.io.Serializable;

/**
 * Created by dcarmo on 2016-02-24.
 */
public class ManagerConfig implements Serializable {

    public static final int NO_ANIMATION = 0;

    public int minStackSize;

    public int pushContainerId;

    private int mNextAnimIn = Integer.MIN_VALUE;
    private int mNextAnimOut = Integer.MIN_VALUE;

    private int mPresentAnimIn;
    private int mPresentAnimOut;

    private int mDismissAnimIn;
    private int mDismissAnimOut;

    public transient INavigationFragment rootFragment;

    public transient INavigationFragment masterFragment;
    public transient INavigationFragment detailFragment;

    public ManagerConfig() {
        mPresentAnimIn = R.anim.slide_in_from_right;
        mPresentAnimOut = R.anim.slide_out_to_left;

        mDismissAnimIn = R.anim.slide_in_from_left;
        mDismissAnimOut = R.anim.slide_out_to_right;
    }

    public void nullifyInitialFragments() {
        rootFragment = null;
        masterFragment = null;
        detailFragment = null;
    }

    public void setDefaultPresetAnim(int animIn, int animOut) {
        mPresentAnimIn = animIn;
        mPresentAnimOut = animOut;
    }

    public void setDefaultDismissAnim(int animIn, int animOut) {
        mDismissAnimIn = animIn;
        mDismissAnimOut = animOut;
    }

    public void setNextAnim(int animIn, int animOut) {
        mNextAnimIn = animIn;
        mNextAnimOut = animOut;
    }

    public int getPresentAnimIn() {
        int nextAnim = mPresentAnimIn;
        if (mNextAnimIn > Integer.MIN_VALUE) {
            nextAnim = mNextAnimIn;
            mNextAnimIn = Integer.MIN_VALUE;
        }
        return nextAnim;
    }

    public int getPresentAnimOut() {
        int nextAnim = mPresentAnimOut;
        if (mNextAnimOut > Integer.MIN_VALUE) {
            nextAnim = mNextAnimOut;
            mNextAnimOut = Integer.MIN_VALUE;
        }
        return nextAnim;
    }

    public int getDismissAnimIn() {
        int nextAnim = mDismissAnimIn;
        if (mNextAnimIn > Integer.MIN_VALUE) {
            nextAnim = mNextAnimIn;
            mNextAnimIn = Integer.MIN_VALUE;
        }
        return nextAnim;
    }

    public int getDismissAnimOut() {
        int nextAnim = mDismissAnimOut;
        if (mNextAnimOut > Integer.MIN_VALUE) {
            nextAnim = mNextAnimOut;
            mNextAnimOut = Integer.MIN_VALUE;
        }
        return nextAnim;
    }
}
