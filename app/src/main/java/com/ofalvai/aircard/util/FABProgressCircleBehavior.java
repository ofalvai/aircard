package com.ofalvai.aircard.util;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.util.AttributeSet;
import android.view.View;

import com.github.jorgecastilloprz.FABProgressCircle;

/**
 * Custom FAB-like behavior for the FabProgressCircle library.
 * The original FAB is wrapped in that view, so we need to attach a new behavior to the view.
 * From: https://github.com/JorgeCastilloPrz/FABProgressCircle/issues/23
 */
public class FABProgressCircleBehavior extends CoordinatorLayout.Behavior<FABProgressCircle> {

    public FABProgressCircleBehavior(Context context, AttributeSet attrs) {

    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, FABProgressCircle child, View dependency) {
        return dependency instanceof Snackbar.SnackbarLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, FABProgressCircle child, View dependency) {
        float translationY = Math.min(0, dependency.getTranslationY() - dependency.getHeight());
        child.setTranslationY(translationY);

        return true;
    }
}
