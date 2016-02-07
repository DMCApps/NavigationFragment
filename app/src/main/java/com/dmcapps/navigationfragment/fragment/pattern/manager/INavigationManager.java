package com.dmcapps.navigationfragment.fragment.pattern.manager;

import com.dmcapps.navigationfragment.fragment.pattern.NavigationFragment;

public interface INavigationManager {
    void presentFragment(NavigationFragment navFragment);
    void presentFragment(NavigationFragment navFragment, int animationIn, int animationOut);
    void dismissTopFragment();
    void dismissTopFragment(int animationIn, int animationOut);
    boolean onBackPressed();
}
