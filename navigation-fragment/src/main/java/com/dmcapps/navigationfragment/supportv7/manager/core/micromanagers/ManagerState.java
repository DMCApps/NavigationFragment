package com.dmcapps.navigationfragment.supportv7.manager.core.micromanagers;

import java.io.Serializable;
import java.util.Stack;

/**
 * Created by dcarmo on 2016-02-24.
 */
public class ManagerState implements Serializable {

    public boolean isTablet;
    public boolean isPortrait;

    public Stack<String> fragmentTagStack;

    public ManagerState() {
        fragmentTagStack = new Stack<>();
    }

}
