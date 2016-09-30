package com.dmcapps.navigationfragment.common.interfaces;

/**
 * Created by dcarmo on 2016-09-29.
 */
public interface NavigationManager {

    void setStack(Stack stack);

    Stack getStack();

    void setConfig(Config config);

    Config getConfig();

    void setState(State state);

    State getState();

    void setLifecycle(Lifecycle lifecycle);

    Lifecycle getLifecycle();

}
