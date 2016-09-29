package com.dmcapps.navigationfragmentexample.GitIssue5Example;

import com.dmcapps.navigationfragment.supportv7.fragments.SupportNavigationFragment;

/**
 * Created by hesk on 29/2/16.
 */
public class GitIssue10ExampleActivity extends basicImplementation {
    @Override
    protected SupportNavigationFragment initFragment() {
        return SmartTabLayout.newInstance();
    }
}
