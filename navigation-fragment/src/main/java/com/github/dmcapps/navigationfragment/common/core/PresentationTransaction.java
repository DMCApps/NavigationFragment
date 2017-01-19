package com.github.dmcapps.navigationfragment.common.core;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.transition.Transition;
import android.view.View;

import com.github.dmcapps.navigationfragment.common.interfaces.Navigation;

/**
 * Created by dcarmo on 2017-01-18.
 */

public class PresentationTransaction extends FragmentTransactionWrapper {
    private static final String TAG = PresentationTransaction.class.getSimpleName();

    private NavigationManager navigationManager;

    private Bundle navBundle;
    private Transition sharedElementEnterTransition;
    private Transition sharedElementReturnTransition;
    private Transition nextFragmentEnterTransition;

    PresentationTransaction(NavigationManager navigationManager, NavigationConfig config, @NonNull Object object) {
        super(object);
        this.navigationManager = navigationManager;

        if (config.getPresentInAnim() != null && config.getPresentOutAnim() != null
                && config.getDismissInAnim() != null && config.getDismissOutAnim() != null) {

            setCustomAnimations(config.getPresentInAnim(),
                    config.getPresentOutAnim(),
                    config.getDismissInAnim(),
                    config.getDismissOutAnim());
        }
        else if (config.getPresentInAnim() != null && config.getPresentOutAnim() != null) {
            setCustomAnimations(config.getPresentInAnim(), config.getPresentOutAnim());
        }
    }

    public PresentationTransaction setNavBundle(Bundle navBundle) {
        this.navBundle = navBundle;
        return this;
    }

    Bundle getNavBundle() {
        return this.navBundle;
    }

    /*
    public PresentationTransaction setSharedElementEnterTransition(Transition transition) {
        sharedElementEnterTransition = transition;
        return this;
    }

    Transition getSharedElementEnterTransition() {
        return sharedElementEnterTransition;
    }

    public PresentationTransaction setSharedElementReturnTransition(Transition transition) {
        sharedElementReturnTransition = transition;
        return this;
    }

    Transition getSharedElementReturnTransition() {
        return sharedElementReturnTransition;
    }

    public PresentationTransaction setNextFragmentEnterTransition(Transition transition) {
        nextFragmentEnterTransition = transition;
        return this;
    }

    Transition getNextFragmentEnterTransition() {
        return nextFragmentEnterTransition;
    }
    */

    public PresentationTransaction setCustomAnimations(int enter, int exit) {
        super.setCustomAnimations(enter, exit);
        return this;
    }

    public PresentationTransaction setCustomAnimations(int enter, int exit, int popEnter, int popExit) {
        super.setCustomAnimations(enter, exit, popEnter, popExit);
        return this;
    }

    public PresentationTransaction addSharedElement(View view, String name) {
        super.addSharedElement(view, name);
        return this;
    }

    public void presentFragment(Navigation navFragment) {
        navigationManager.presentFragment(this, navFragment);
    }
}
