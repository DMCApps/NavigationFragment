package com.dmcapps.navigationfragment.common.core;

import com.dmcapps.navigationfragment.R;
import com.dmcapps.navigationfragment.common.interfaces.Config;
import com.dmcapps.navigationfragment.common.interfaces.Navigation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dcarmo on 2016-02-24.
 */
public class CofigManager implements Config {

    public static final int NO_ANIMATION = 0;

    private int mMinStackSize;

    private int mPushContainerId;

    private int mNextAnimIn = Integer.MIN_VALUE;
    private int mNextAnimOut = Integer.MIN_VALUE;

    private int mPresentAnimIn;
    private int mPresentAnimOut;

    private int mDismissAnimIn;
    private int mDismissAnimOut;

    private transient List<Navigation> mInitialNavigation;

    public CofigManager() {
        mPresentAnimIn = R.anim.slide_in_from_right;
        mPresentAnimOut = R.anim.slide_out_to_left;

        mDismissAnimIn = R.anim.slide_in_from_left;
        mDismissAnimOut = R.anim.slide_out_to_right;
    }

    @Override
    public void setInitialNavigation(List<Navigation> initialNavigation) {
        if (initialNavigation == null) {
            initialNavigation = new ArrayList<>();
        }
        mInitialNavigation = initialNavigation;
    }

    @Override
    public List<Navigation> getInitialNavigation() {
        return mInitialNavigation;
    }

    @Override
    public void addInitialNavigation(Navigation navigation) {
        getInitialNavigation().add(navigation);
    }

    @Override
    public void setMinStackSize(int minStackSize) {
        mMinStackSize = minStackSize;
    }

    @Override
    public int getMinStackSize() {
        return mMinStackSize;
    }

    @Override
    public void setPushContainerId(int pushContainerId) {
        mPushContainerId = pushContainerId;
    }

    @Override
    public int getPushContainerId() {
        return mPushContainerId;
    }

    @Override
    public void nullifyInitialFragments() {
        mInitialNavigation = null;
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
