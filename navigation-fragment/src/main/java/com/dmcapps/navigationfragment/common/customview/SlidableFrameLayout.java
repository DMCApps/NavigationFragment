package com.dmcapps.navigationfragment.common.customview;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by dcarmo on 2016-10-01.
 */
public class SlidableFrameLayout extends FrameLayout {

    public SlidableFrameLayout(Context context) {
        super(context);
    }

    public SlidableFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SlidableFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SlidableFrameLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public float getXFraction() {
        if (getWidth() == 0) {
            return 0;
        }
        return getX() / getWidth();
    }

    public void setXFraction(float xFraction) {
        final int width = getWidth();
        setX((width > 0) ? (xFraction * width) : -9999);
    }
}
