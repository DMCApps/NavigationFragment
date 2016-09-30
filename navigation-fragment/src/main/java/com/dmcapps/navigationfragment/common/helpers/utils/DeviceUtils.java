package com.dmcapps.navigationfragment.common.helpers.utils;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by DCarmo on 16-02-07.
 */
public final class DeviceUtils {
    private static final String TAG = DeviceUtils.class.getSimpleName();

    private DeviceUtils() { }

    /**
     * Returns the density of the screen
     *
     * @param
     *      context -> current context
     * @return
     *      Density of the screen.
     */
    public static float getDeviceDensity(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.density;
    }

    /**
     * Takes the given value in dp and converts it to pixel for the device.
     *
     * @param
     *      context -> current context
     * @param
     *      dpValue -> value in dp to convert to pixels
     * @return
     *      dpValue converted to pixels.
     */
    public static int getPixelValueFromDp(Context context, int dpValue) {
        float density = getDeviceDensity(context);
        return (int)(dpValue * density);
    }

}
