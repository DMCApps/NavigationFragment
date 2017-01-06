package com.dmcapps.navigationfragment.common.core;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;

/**
 * Created by dcarmo on 2017-01-05.
 */

@AutoValue
public abstract class NavigationTransaction {

    public static class SharedElement {
        public View view;
        public String name;

        SharedElement(View view, String name) {
            this.view = view;
            this.name = name;
        }
    }

    @Nullable
    public abstract Integer getPresentInAnim();
    @Nullable
    public abstract Integer getPresentOutAnim();
    @Nullable
    public abstract Integer getDismissInAnim();
    @Nullable
    public abstract Integer getDismissOutAnim();
    @Nullable
    public abstract Bundle getNavBundle();
    @Nullable
    public abstract ImmutableList<SharedElement> getSharedElements();

    public static Builder builder() {
        return new AutoValue_NavigationTransaction.Builder();
    }

    public static Builder withConfig(NavigationConfig config) {
        return builder()
                .setPresentInAnim(config.getPresentInAnim())
                .setPresentOutAnim(config.getPresentOutAnim())
                .setDismissInAnim(config.getDismissInAnim())
                .setDismissOutAnim(config.getDismissOutAnim());
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setPresentInAnim(Integer value);
        public abstract Builder setPresentOutAnim(Integer value);
        public abstract Builder setDismissInAnim(Integer value);
        public abstract Builder setDismissOutAnim(Integer value);
        public abstract Builder setNavBundle(Bundle bundle);

        public abstract ImmutableList.Builder<SharedElement> sharedElementsBuilder();

        @TargetApi(Build.VERSION_CODES.KITKAT)
        public Builder addSharedElement(View view, String name) {
            sharedElementsBuilder().add(new SharedElement(view, name));
            return this;
        }
        public abstract NavigationTransaction build();
    }

}
