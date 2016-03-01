package com.dmcapps.navigationfragmentexample.GitIssues;

import com.dmcapps.navigationfragment.fragments.NavigationFragment;

public class GitIssue5ExampleActivity extends basicImplementation {
    @Override
    protected NavigationFragment initFragment() {
        return ViewPagerFragment.newInstance();
    }
}
