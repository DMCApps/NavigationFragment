package com.dmcapps.navigationfragment.manager.micromanagers;

import java.util.Stack;

/**
 * Created by dcarmo on 2016-02-24.
 */
public class ManagerState {

    public boolean isTablet;
    public boolean isPortrait;

    public Stack<String> fragmentTagStack;

    public ManagerState() {
        fragmentTagStack = new Stack<>();
    }

}
