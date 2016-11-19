package com.dmcapps.navigationfragment.common.core;

import android.os.Bundle;

/**
 * Created by dcarmo on 2016-11-19.
 */

public class NavigationSettings {
    private String mTitle = null;
    private Integer mInAnimation = null;
    private Integer mOutAnimation = null;
    private Bundle mNavBundle = null;

    public NavigationSettings(String title, Integer inAnimation, Integer outAnimation, Bundle navBundle) {
        this.mTitle = title;
        this.mInAnimation = inAnimation;
        this.mOutAnimation = outAnimation;
        this.mNavBundle = navBundle;
    }

    public String getTitle() {
        return mTitle;
    }

    public Integer getInAnimation() {
        return mInAnimation;
    }

    public Integer getOutAnimation() {
        return mOutAnimation;
    }

    public Bundle getNavBundle() {
        return mNavBundle;
    }

    public class Builder {
        private String mTitle = null;
        private Integer mInAnimation = null;
        private Integer mOutAnimation = null;
        private Bundle mNavBundle = null;

        public Builder() {}

        public Builder setTitle(String title) {
            mTitle = title;
            return this;
        }

        public Builder setInAnimation(Integer inAnimation) {
            mInAnimation = inAnimation;
            return this;
        }

        public Builder setOutAnimation(Integer outAnimation) {
            mOutAnimation = outAnimation;
            return this;
        }

        public Builder setNavBundle(Bundle navBundle) {
            mNavBundle = navBundle;
            return this;
        }

        public NavigationSettings build() {
            return new NavigationSettings(mTitle, mInAnimation, mOutAnimation, mNavBundle);
        }
    }
}
