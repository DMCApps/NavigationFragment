package com.dmcapps.navigationfragment.v17;

import android.app.Fragment;

import com.dmcapps.navigationfragment.common.interfaces.Config;
import com.dmcapps.navigationfragment.common.interfaces.Lifecycle;
import com.dmcapps.navigationfragment.common.interfaces.NavigationManager;
import com.dmcapps.navigationfragment.common.interfaces.Stack;
import com.dmcapps.navigationfragment.common.interfaces.State;

/**
 * Created by dcarmo on 2016-09-29.
 */
public class NavigationManagerFragment extends Fragment implements NavigationManager{

    Stack mStack;
    Config mConfig;
    State mState;
    Lifecycle mLifecycle;

    @Override
    public void setStack(Stack stack) {
        mStack = stack;
    }

    @Override
    public Stack getStack() {
        return mStack;
    }

    @Override
    public void setConfig(Config config) {
        mConfig = config;
    }

    @Override
    public Config getConfig() {
        return mConfig;
    }

    @Override
    public void setState(State state) {
        mState = state;
    }

    @Override
    public State getState() {
        return mState;
    }

    @Override
    public void setLifecycle(Lifecycle lifecycle) {
        mLifecycle = lifecycle;
    }

    @Override
    public Lifecycle getLifecycle() {
        return mLifecycle;
    }
}
