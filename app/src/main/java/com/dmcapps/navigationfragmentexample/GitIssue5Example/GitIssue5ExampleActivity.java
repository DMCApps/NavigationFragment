package com.dmcapps.navigationfragmentexample.GitIssue5Example;

import com.dmcapps.navigationfragment.supportv7.fragments.SupportNavigationFragment;

public class GitIssue5ExampleActivity extends basicImplementation {
    @Override
    protected SupportNavigationFragment initFragment() {
        return ViewPagerFragment.newInstance();
    }
}
