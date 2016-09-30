package com.dmcapps.navigationfragment.common.core;

import com.dmcapps.navigationfragment.R;
import com.dmcapps.navigationfragment.common.interfaces.Config;
import com.dmcapps.navigationfragment.common.interfaces.Navigation;

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

    private transient Navigation mRootFragment;

    private transient Navigation mMasterFragment;
    private transient Navigation mDetailFragment;

    public CofigManager() {
        mPresentAnimIn = R.anim.slide_in_from_right;
        mPresentAnimOut = R.anim.slide_out_to_left;

        mDismissAnimIn = R.anim.slide_in_from_left;
        mDismissAnimOut = R.anim.slide_out_to_right;
    }

    @Override
    public void setRootFragment(Navigation rootFragment) {
        mRootFragment = rootFragment;
    }

    @Override
    public Navigation getRootFragment() {
        return mRootFragment;
    }

    @Override
    public void setMasterFragment(Navigation masterFragment) {
        mMasterFragment = masterFragment;
    }

    @Override
    public Navigation getMasterFragment() {
        return mMasterFragment;
    }

    @Override
    public void setDetailFragment(Navigation detailFragment) {
        mDetailFragment = detailFragment;
    }

    @Override
    public Navigation getDetailFragment() {
        return mDetailFragment;
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
        mRootFragment = null;
        mMasterFragment = null;
        mDetailFragment = null;
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
