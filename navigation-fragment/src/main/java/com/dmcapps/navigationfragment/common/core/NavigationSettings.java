package com.dmcapps.navigationfragment.common.core;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.dmcapps.navigationfragment.common.interfaces.Config;

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

    // Few Ideas:
    // - Make a method in the Navigation for defaultSettingsBuilder() which return the NavigationSettings.Builder() with the default settings in it
    // - Change the Navigation to use a builder pattern, BUT still have the helper present/dismiss methods for when the more complete navigation is not needed
    // - Check the settings that come in and add the default if not provided (e.g. if the values are null).
    private static Config mDefaultConfig;

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

    public static void setDefaultConfig(Config config) {
        mDefaultConfig = config;
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
            // See notes above this is pretty ugly ...
            mPresentInAnimation = mDefaultConfig.getPresentAnimIn();
            mPresentOutAnimation = mDefaultConfig.getPresentAnimOut();
            mDismissInAnimation = mDefaultConfig.getDismissAnimIn();
            mDismissOutAnimation = mDefaultConfig.getDismissAnimOut();
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
