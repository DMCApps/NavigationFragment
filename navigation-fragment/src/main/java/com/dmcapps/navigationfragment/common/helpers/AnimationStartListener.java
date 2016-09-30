package com.dmcapps.navigationfragment.common.helpers;

import android.view.animation.Animation;

/**
 * Created by DCarmo on 16-02-18.
 */
public abstract class AnimationStartListener implements Animation.AnimationListener {
    public abstract void onAnimationStart(Animation animation);

    @Override
    public void onAnimationEnd(Animation animation) { }

    @Override
    public void onAnimationRepeat(Animation animation) { }
}
