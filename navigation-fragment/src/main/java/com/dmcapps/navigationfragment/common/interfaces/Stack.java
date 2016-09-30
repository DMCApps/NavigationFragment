package com.dmcapps.navigationfragment.common.interfaces;

import android.os.Bundle;

import com.dmcapps.navigationfragment.support.v7.manager.core.NavigationManagerFragment;

import java.io.Serializable;

/**
 * Created by dcarmo on 2016-02-25.
 */
public interface Stack extends Serializable {

    Navigation pushFragment(NavigationManagerFragment manager, State state, Config config, Navigation navFragment);

    Navigation pushFragment(NavigationManagerFragment manager, State state, Config config, Navigation navFragment, Bundle navBundle);

    Navigation popFragment(NavigationManagerFragment manager, State state, Config config);

    Navigation popFragment(NavigationManagerFragment manager, State state, Config config, Bundle navBundle);

    void clearNavigationStackToPosition(NavigationManagerFragment manager, State state, int stackPosition);

    Navigation getFragmentAtIndex(NavigationManagerFragment manager, State state, int index);

}
