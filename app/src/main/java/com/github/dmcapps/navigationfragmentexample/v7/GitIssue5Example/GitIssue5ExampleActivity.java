package com.github.dmcapps.navigationfragmentexample.v7.GitIssue5Example;

import com.github.dmcapps.navigationfragment.v7.NavigationFragment;

public class GitIssue5ExampleActivity extends basicImplementation {
    @Override
    protected NavigationFragment initFragment() {
        return ViewPagerFragment.newInstance();
    }
}
