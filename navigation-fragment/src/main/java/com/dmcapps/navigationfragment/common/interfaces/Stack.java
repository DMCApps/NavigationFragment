package com.dmcapps.navigationfragment.common.interfaces;

import android.os.Bundle;

import java.io.Serializable;

/**
 * Created by dcarmo on 2016-02-25.
 */
public interface Stack<FragmentType> extends Serializable {

    FragmentType pushFragment(NavigationManager navigationManager, FragmentType navFragment);

    FragmentType pushFragment(NavigationManager navigationManager, FragmentType navFragment, Bundle navBundle);

    FragmentType popFragment(NavigationManager navigationManager);

    FragmentType popFragment(NavigationManager navigationManager, Bundle navBundle);

    void clearNavigationStackToPosition(NavigationManager navigationManager, int stackPosition);

    FragmentType getFragmentAtIndex(NavigationManager navigationManager, int index);

}
