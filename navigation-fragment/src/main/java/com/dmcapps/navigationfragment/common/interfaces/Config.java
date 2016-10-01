package com.dmcapps.navigationfragment.common.interfaces;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dcarmo on 2016-09-29.
 */
public interface Config extends Serializable {

    void setInitialNavigation(List<Navigation> initialNavigation);

    List<Navigation> getInitialNavigation();

    void addInitialNavigation(Navigation navigation);

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
