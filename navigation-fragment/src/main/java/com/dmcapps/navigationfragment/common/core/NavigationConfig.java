package com.dmcapps.navigationfragment.common.core;

import com.google.auto.value.AutoValue;

/**
 * Created by dcarmo on 2017-01-04.
 */

@AutoValue
public abstract class NavigationConfig {

    abstract int getMinStackSize();
    abstract int getPushContainer();

}
