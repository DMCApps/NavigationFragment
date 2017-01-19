package com.github.dmcapps.navigationfragmentexample.v7.GitIssue5Example;

import com.github.dmcapps.navigationfragment.v7.NavigationFragment;

/**
 * Created by hesk on 29/2/16.
 */
public class GitIssue10ExampleActivity extends basicImplementation {
    @Override
    protected NavigationFragment initFragment() {
        return SmartTabLayout.newInstance();
    }
}
