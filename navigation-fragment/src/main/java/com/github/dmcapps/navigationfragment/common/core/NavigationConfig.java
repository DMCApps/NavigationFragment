package com.github.dmcapps.navigationfragment.common.core;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;

import java.io.Serializable;

/**
 * Created by dcarmo on 2017-01-04.
 */

@AutoValue
public abstract class NavigationConfig implements Serializable {

    public abstract int getMinStackSize();
    public abstract int getPushContainerId();
    @Nullable
    public abstract Integer getPresentInAnim();
    @Nullable
    public abstract Integer getPresentOutAnim();
    @Nullable
    public abstract Integer getDismissInAnim();
    @Nullable
    public abstract Integer getDismissOutAnim();

    public static Builder builder() {
        return new AutoValue_NavigationConfig.Builder();
    }

    public abstract Builder toBuilder();

    public NavigationConfig withPresentAnimations(Integer animIn, Integer animOut) {
        return toBuilder()
                .setPresentInAnim(animIn)
                .setPresentOutAnim(animOut)
                .build();
    }

    public NavigationConfig withDismissAnimations(Integer animIn, Integer animOut) {
        return toBuilder()
                .setDismissInAnim(animIn)
                .setDismissOutAnim(animOut)
                .build();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setMinStackSize(int value);
        public abstract Builder setPushContainerId(int value);
        public abstract Builder setPresentInAnim(Integer value);
        public abstract Builder setPresentOutAnim(Integer value);
        public abstract Builder setDismissInAnim(Integer value);
        public abstract Builder setDismissOutAnim(Integer value);
        public abstract NavigationConfig build();
    }
}
