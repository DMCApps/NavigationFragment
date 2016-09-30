package com.dmcapps.navigationfragment.common.helpers.utils;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by DCarmo on 16-02-07.
 */
public class ViewUtils {
    private static final String TAG = ViewUtils.class.getSimpleName();

    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

    private ViewUtils() { }

    /**
     * Generate a value suitable for use in View.setId(int).
     * This value will not collide with ID values generated at build time by aapt for R.id.
     *
     * @return a generated ID value
     */
    public static int generateViewId() {
        for (;;) {
            final int result = sNextGeneratedId.get();
            // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
            int newValue = result + 1;
            if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
            if (sNextGeneratedId.compareAndSet(result, newValue)) {
                return result;
            }
        }
    }
}
