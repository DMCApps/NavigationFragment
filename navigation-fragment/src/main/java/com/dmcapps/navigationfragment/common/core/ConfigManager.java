package com.dmcapps.navigationfragment.common.core;

import com.dmcapps.navigationfragment.R;
import com.dmcapps.navigationfragment.common.interfaces.Config;
import com.dmcapps.navigationfragment.common.interfaces.Navigation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dcarmo on 2016-02-24.
 */
public class ConfigManager implements Config {

    public static final int NO_ANIMATION = 0;

    private int mMinStackSize;

    private int mPushContainerId;

    private Integer mNextAnimIn = null;
    private Integer mNextAnimOut = null;

    private Integer mPresentAnimIn;
    private Integer mPresentAnimOut;

    private Integer mDismissAnimIn;
    private Integer mDismissAnimOut;

    private transient List<Navigation> mInitialNavigation;

    public ConfigManager() { }

    @Override
    public void setInitialNavigation(List<Navigation> initialNavigation) {
        mInitialNavigation = initialNavigation;
    }

    @Override
    public List<Navigation> getInitialNavigation() {
        if (mInitialNavigation == null) {
            mInitialNavigation = new ArrayList<>();
        }
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

    public void setDefaultPresetAnim(Integer animIn, Integer animOut) {
        mPresentAnimIn = animIn;
        mPresentAnimOut = animOut;
    }

    public void setDefaultDismissAnim(Integer animIn, Integer animOut) {
        mDismissAnimIn = animIn;
        mDismissAnimOut = animOut;
    }

    public void setNextAnim(Integer animIn, Integer animOut) {
        mNextAnimIn = animIn;
        mNextAnimOut = animOut;
    }

    public Integer getPresentAnimIn() {
        Integer nextAnim = mPresentAnimIn;
        if (mNextAnimIn != null) {
            nextAnim = mNextAnimIn;
            mNextAnimIn = null;
        }
        return nextAnim;
    }

    public Integer getPresentAnimOut() {
        Integer nextAnim = mPresentAnimOut;
        if (mNextAnimOut != null) {
            nextAnim = mNextAnimOut;
            mNextAnimOut = null;
        }
        return nextAnim;
    }

    public Integer getDismissAnimIn() {
        Integer nextAnim = mDismissAnimIn;
        if (mNextAnimIn != null) {
            nextAnim = mNextAnimIn;
            mNextAnimIn = null;
        }
        return nextAnim;
    }

    public Integer getDismissAnimOut() {
        Integer nextAnim = mDismissAnimOut;
        if (mNextAnimOut != null) {
            nextAnim = mNextAnimOut;
            mNextAnimOut = null;
        }
        return nextAnim;
    }
}
