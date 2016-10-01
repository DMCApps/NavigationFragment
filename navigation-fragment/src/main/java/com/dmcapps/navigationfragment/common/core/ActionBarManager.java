package com.dmcapps.navigationfragment.common.core;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.dmcapps.navigationfragment.common.interfaces.NavigationManager;
import com.dmcapps.navigationfragment.v7.MasterDetailNavigationManagerFragment;

/**
 * Created by dcarmo on 2016-02-25.
 */
public class ActionBarManager {
    private static final String TAG = ActionBarManager.class.getSimpleName();

    /**
     * A method for setting the title of the action bar. (Saves you from having to call getActivity().setTitle())
     *
     * @param
     *      resId -> Resource Id of the title you would like to set.
     */
    public static void setTitle(Activity activity, int resId) {
        if (isAppCompatActivity(activity) && ((AppCompatActivity)activity).getSupportActionBar() != null) {
            ((AppCompatActivity)activity).getSupportActionBar().setTitle(resId);
        }
    }

    /**
     * A method for setting the title of the action bar. (Saves you from having to call getActivity().setTitle())
     *
     * @param
     *      title -> String of the title you would like to set.
     */
    public static void setTitle(Activity activity, String title) {
        if (isAppCompatActivity(activity) && ((AppCompatActivity)activity).getSupportActionBar() != null) {
            ((AppCompatActivity)activity).getSupportActionBar().setTitle(title);
        }
    }

    public static void setDisplayHomeAsUpEnabled(Activity activity, boolean enabled) {
        if (isAppCompatActivity(activity) && ((AppCompatActivity)activity).getSupportActionBar() != null) {
            ((AppCompatActivity)activity).getSupportActionBar().setDisplayHomeAsUpEnabled(enabled);
        }
    }

    public static void setHomeAsUpIndicator(Activity activity, Drawable drawable) {
        if (isAppCompatActivity(activity) && ((AppCompatActivity)activity).getSupportActionBar() != null) {
            ((AppCompatActivity)activity).getSupportActionBar().setHomeAsUpIndicator(drawable);
        }
    }

    public static void setHomeAsUpIndicator(Activity activity, int resId) {
        if (isAppCompatActivity(activity) && ((AppCompatActivity)activity).getSupportActionBar() != null) {
            ((AppCompatActivity)activity).getSupportActionBar().setHomeAsUpIndicator(resId);
        }
    }

    public static void setMasterToggleTitle(NavigationManager manager, String title) {
        if (isMasterDetailManager(manager)) {
            ((MasterDetailNavigationManagerFragment)manager).setMasterToggleTitle(title);
        }
    }

    public static void setMasterToggleTitle(NavigationManager manager, int resId) {
        if (isMasterDetailManager(manager)) {
            ((MasterDetailNavigationManagerFragment) manager).setMasterToggleTitle(resId);
        }
    }

    private static boolean isAppCompatActivity(Activity activity) {
        if (activity != null && activity instanceof AppCompatActivity) {
            return true;
        }
        else {
            Log.e(TAG, "Unable to set title, Activity is null or is not an ActionBarActivity or AppCompatActivity");
            return false;
        }
    }

    private static boolean isMasterDetailManager(NavigationManager manager) {
        if (manager instanceof MasterDetailNavigationManagerFragment) {
            return true;
        }
        else {
            Log.e(TAG, "setMasterToggleTitle(int) - Navigation Manager must be a MasterDetailNavigationManagerFragment");
            return false;
        }
    }
}
