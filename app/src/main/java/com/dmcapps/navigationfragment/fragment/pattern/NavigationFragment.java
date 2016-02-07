package com.dmcapps.navigationfragment.fragment.pattern;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import java.util.UUID;

public abstract class NavigationFragment extends Fragment {

    private NavigationManagerFragment mNavigationManager;
    private final String TAG;

    public NavigationFragment() {
        TAG = UUID.randomUUID().toString();
    }

    public String getNavTag() {
        return TAG;
    }

    public void setNavigationManager(NavigationManagerFragment navigationManager) {
        mNavigationManager = navigationManager;
    }

    public NavigationManagerFragment getNavigationManager() {
        return mNavigationManager;
    }

    // ===========
    // Fragment Lifecycle in order of calling
    // http://developer.android.com/guide/components/fragments.html
    // ===========

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d("NavigationFragment", "onAttach()");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("NavigationFragment", "onCreate()");
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("NavigationFragment", "onViewCreated()");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("NavigationFragment", "onStart()");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("NavigationFragment", "onResume()");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("NavigationFragment", "onPause()");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("NavigationFragment", "onStop()");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("NavigationFragment", "onDestroyView()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("NavigationFragment", "onDestroy()");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("NavigationFragment", "onDetach()");
    }
}
