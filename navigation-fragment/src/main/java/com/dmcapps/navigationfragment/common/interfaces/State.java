package com.dmcapps.navigationfragment.common.interfaces;

import java.io.Serializable;
import java.util.Stack;

/**
 * Created by dcarmo on 2016-09-29.
 */
public interface State extends Serializable {

    Stack<String> getStack();

    void isTablet(boolean isTablet);

    boolean isTablet();

    void isPortrait(boolean isPortrait);

    boolean isPortrait();
}
