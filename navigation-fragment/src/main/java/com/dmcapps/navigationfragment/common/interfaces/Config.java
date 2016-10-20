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

    void setDefaultPresetAnim(Integer animIn, Integer animOut);

    void setDefaultDismissAnim(Integer animIn, Integer animOut);

    void setNextAnim(Integer animIn, Integer animOut);

    Integer getPresentAnimIn();

    Integer getPresentAnimOut();

    Integer getDismissAnimIn();

    Integer getDismissAnimOut();

}
