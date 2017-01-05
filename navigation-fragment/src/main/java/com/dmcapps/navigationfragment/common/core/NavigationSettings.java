package com.dmcapps.navigationfragment.common.core;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dcarmo on 2016-11-19.
 */

public class NavigationSettings {
    private String mTitle = null;
    private Integer mPresentInAnimation = null;
    private Integer mPresentOutAnimation = null;
    private Integer mDismissInAnimation = null;
    private Integer mDismissOutAnimation = null;
    private Bundle mNavBundle = null;
    private List<SharedElement> mSharedElements = null;

    // TODO: I need a way such that when the builder is made the default in and out present and dismiss animation are used
    // This is just a quick fix to it before I make a design decision.

    public static class SharedElement {
        public View view;
        public String name;

        SharedElement(View view, String name) {
            this.view = view;
            this.name = name;
        }
    }

    private NavigationSettings(String title,
                               Bundle navBundle,
                               Integer presentInAnimation, Integer presentOutAnimation,
                               Integer dismissInAnimation, Integer mDismissOutAnimation,
                               List<SharedElement> sharedElements) {

        this.mTitle = title;
        this.mNavBundle = navBundle;
        this.mPresentInAnimation = presentInAnimation;
        this.mPresentOutAnimation = presentOutAnimation;
        this.mDismissInAnimation = dismissInAnimation;
        this.mDismissOutAnimation = mDismissOutAnimation;
        this.mSharedElements = sharedElements;
    }

    public String getTitle() {
        return mTitle;
    }

    public Bundle getNavBundle() {
        return mNavBundle;
    }

    public Integer getPresentInAnimation() {
        return mPresentInAnimation;
    }

    public Integer getPresentOutAnimation() {
        return mPresentOutAnimation;
    }

    public Integer getDismissInAnimation() {
        return mDismissInAnimation;
    }

    public Integer getDismissOutAnimation() {
        return mDismissOutAnimation;
    }

    public List<SharedElement> getSharedElements() {
        return mSharedElements;
    }

    public static class Builder {
        private String mTitle = null;
        private Integer mPresentInAnimation = null;
        private Integer mPresentOutAnimation = null;
        private Integer mDismissInAnimation = null;
        private Integer mDismissOutAnimation = null;
        private Bundle mNavBundle = null;
        private List<SharedElement> mSharedElements = null;

        public Builder() {
        }

        public Builder setTitle(String title) {
            mTitle = title;
            return this;
        }

        public Builder setAnimations(Integer inAnimation, Integer outAnimation) {
            return setPresentInAnimation(inAnimation)
                    .setPresentOutAnimation(outAnimation);
        }

        public Builder setAnimations(Integer presentInAnimation, Integer presentOutAnimation, Integer dismissInAnimation, Integer dismissOutAnimation) {
            return setPresentInAnimation(presentInAnimation)
                    .setPresentOutAnimation(presentOutAnimation)
                    .setDismissInAnimation(dismissInAnimation)
                    .setDismissOutAnimation(dismissOutAnimation);
        }

        public Builder setPresentInAnimation(Integer inAnimation) {
            mPresentInAnimation = inAnimation;
            return this;
        }

        public Builder setPresentOutAnimation(Integer outAnimation) {
            mPresentOutAnimation = outAnimation;
            return this;
        }

        public Builder setDismissInAnimation(Integer inAnimation) {
            mDismissInAnimation = inAnimation;
            return this;
        }

        public Builder setDismissOutAnimation(Integer outAnimation) {
            mDismissOutAnimation = outAnimation;
            return this;
        }

        public Builder setNavBundle(Bundle navBundle) {
            mNavBundle = navBundle;
            return this;
        }

        @TargetApi(Build.VERSION_CODES.KITKAT)
        public Builder addSharedElement(View view, String name) {
            if (mSharedElements == null) {
                mSharedElements = new ArrayList<>();
            }
            mSharedElements.add(new SharedElement(view, name));
            return this;
        }

        public NavigationSettings build() {
            return new NavigationSettings(mTitle,
                    mNavBundle,
                    mPresentInAnimation,
                    mPresentOutAnimation,
                    mDismissInAnimation,
                    mDismissOutAnimation,
                    mSharedElements);
        }
    }
}
