package com.dmcapps.navigationfragment.common.core;

import com.google.auto.value.AutoValue;

/**
 * Created by dcarmo on 2017-01-04.
 */

@AutoValue
public abstract class NavigationConfig {

    public abstract int getMinStackSize();
    public abstract int getPushContainer();

    public static Builder builder() {
        return new AutoValue_NavigationConfig.Builder();
    }

    public abstract Builder toBuilder();

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setMinStackSize(int value);
        public abstract Builder setPushContainer(int value);
        public abstract NavigationConfig build();
    }
}
