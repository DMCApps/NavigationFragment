package com.github.dmcapps.navigationfragmentexample.v17.TransitionExample;

import android.os.Bundle;

import com.github.dmcapps.navigationfragment.v17.NavigationFragment;
import com.github.dmcapps.navigationfragmentexample.R;
import com.github.dmcapps.navigationfragmentexample.v17.NavigationFragments.SmallImageFragment;
import com.github.dmcapps.navigationfragmentexample.v17.SingleStackSuperActivity;

public class TransitionExampleActivity extends SingleStackSuperActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition_example);
    }

    @Override
    protected NavigationFragment rootFragment() {
        return SmallImageFragment.newInstance();
    }

    @Override
    protected int getContainerId() {
        return R.id.frag_container;
    }
}
