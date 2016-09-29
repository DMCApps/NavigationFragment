package com.dmcapps.navigationfragment.common.helpers;

import android.view.animation.Animation;

/**
 * Created by DCarmo on 16-02-18.
 */
public abstract class AnimationEndListener implements Animation.AnimationListener {
    @Override
    public void onAnimationStart(Animation animation) {

    }

    public abstract void onAnimationEnd(Animation animation);

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
