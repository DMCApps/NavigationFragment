package com.dmcapps.navigationfragment.common.core;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dcarmo on 2016-11-19.
 */

public class NavigationSettings {
    private String mTitle = null;
    private Integer mInAnimation = null;
    private Integer mOutAnimation = null;
    private Bundle mNavBundle = null;
    private List<SharedElement> mSharedElements = null;

    public static class SharedElement {
        public View view;
        public String name;

        SharedElement(View view, String name) {
            this.view = view;
            this.name = name;
        }
    }

    public NavigationSettings(String title, Integer inAnimation, Integer outAnimation, Bundle navBundle, List<SharedElement> sharedElements) {
        this.mTitle = title;
        this.mInAnimation = inAnimation;
        this.mOutAnimation = outAnimation;
        this.mNavBundle = navBundle;
        this.mSharedElements = sharedElements;
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

    public List<SharedElement> getSharedElements() {
        return mSharedElements;
    }

    public static class Builder {
        private String mTitle = null;
        private Integer mInAnimation = null;
        private Integer mOutAnimation = null;
        private Bundle mNavBundle = null;
        private List<SharedElement> mSharedElements = null;

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

        public Builder addSharedElement(View view, String name) {
            if (mSharedElements == null) {
                mSharedElements = new ArrayList<>();
            }
            mSharedElements.add(new SharedElement(view, name));
            return this;
        }

        public NavigationSettings build() {
            return new NavigationSettings(mTitle, mInAnimation, mOutAnimation, mNavBundle, mSharedElements);
        }
    }
}
