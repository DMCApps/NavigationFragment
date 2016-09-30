package com.dmcapps.navigationfragmentexample.GitIssue5Example;

import com.dmcapps.navigationfragment.common.fragments.NavigationFragment;

/**
 * Created by hesk on 29/2/16.
 */
public class GitIssue10ExampleActivity extends basicImplementation {
    @Override
    protected NavigationFragment initFragment() {
        return SmartTabLayout.newInstance();
    }
}
