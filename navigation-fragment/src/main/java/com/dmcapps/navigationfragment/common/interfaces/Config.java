package com.dmcapps.navigationfragment.common.interfaces;

import java.io.Serializable;

/**
 * Created by dcarmo on 2016-09-29.
 */
public interface Config extends Serializable {

    void setRootFragment(Navigation rootFragment);

    Navigation getRootFragment();

    void setMasterFragment(Navigation masterFragment);

    Navigation getMasterFragment();

    void setDetailFragment(Navigation detailFragment);

    Navigation getDetailFragment();

    void setMinStackSize(int minStackSize);

    int getMinStackSize();

    void setPushContainerId(int pushContainerId);

    int getPushContainerId();

    void nullifyInitialFragments();

    void setDefaultPresetAnim(int animIn, int animOut);

    void setDefaultDismissAnim(int animIn, int animOut);

    void setNextAnim(int animIn, int animOut);

    int getPresentAnimIn();

    int getPresentAnimOut();

    int getDismissAnimIn();

    int getDismissAnimOut();

}
