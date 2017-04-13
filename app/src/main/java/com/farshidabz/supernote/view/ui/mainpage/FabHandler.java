package com.farshidabz.supernote.view.ui.mainpage;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.DrawableRes;
import android.support.design.widget.FloatingActionButton;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.farshidabz.supernote.R;

import java.util.HashMap;

/**
 * Created by FarshidAbz.
 * Since 4/14/2017.
 */

public class FabHandler {
    private Context context;
    private OnFabClickListener onFabClickListener;

    private HashMap<Integer, FloatingActionButton> floatingActionButtonMap;
    private HashMap<Integer, Integer> floatingButtonOpenDrawable;
    private HashMap<Integer, Integer> floatingButtonCloseDrawable;

    private Animation openFabAnimation;
    private Animation closeFabAnimation;
    private boolean isFabsOpen;

    public FabHandler(Context context, OnFabClickListener onFabClickListener, FloatingActionButton... floatingActionButtons) {
        this.context = context;
        this.onFabClickListener = onFabClickListener;

        floatingButtonOpenDrawable = new HashMap<>();
        floatingButtonCloseDrawable = new HashMap<>();
        floatingActionButtonMap = new HashMap<>();

        for (FloatingActionButton floatingActionButton : floatingActionButtons) {
            floatingActionButtonMap.put(floatingActionButton.getId(), floatingActionButton);
            floatingActionButton.setOnClickListener(v -> {
                if (onFabClickListener != null) {
                    fabClicked();
                    new Handler().postDelayed(() -> onFabClickListener.onFabClicked(v.getId()), 100);
                }
            });
        }

        loadAnimations();
    }

    private void loadAnimations() {
        openFabAnimation = AnimationUtils.loadAnimation(context, R.anim.fab_open);
        closeFabAnimation = AnimationUtils.loadAnimation(context, R.anim.fab_close);
    }

    public void fabClicked() {
        if (isFabsOpen) {
            hideFab();
            isFabsOpen = false;
        } else {
            showFab();
            isFabsOpen = true;
        }
    }

    private void showFab() {
        for (FloatingActionButton floatingActionButton : floatingActionButtonMap.values()) {
            floatingActionButton.startAnimation(openFabAnimation);

            if (floatingButtonOpenDrawable.containsKey(floatingActionButton.getId())) {
                floatingActionButton.setImageResource(floatingButtonOpenDrawable.get(floatingActionButton.getId()));
            }
        }
    }

    private void hideFab() {
        for (FloatingActionButton floatingActionButton : floatingActionButtonMap.values()) {
            floatingActionButton.startAnimation(closeFabAnimation);

            if (floatingButtonCloseDrawable.containsKey(floatingActionButton.getId())) {
                floatingActionButton.setImageResource(floatingButtonCloseDrawable.get(floatingActionButton.getId()));
            }
        }
    }

    public void showSpecificFab(FloatingActionButton floatingActionButton) {
        floatingActionButton.startAnimation(openFabAnimation);
    }

    public void hideSpecificFab(FloatingActionButton floatingActionButton) {
        floatingActionButton.startAnimation(closeFabAnimation);
    }

    public void setDrawables(FloatingActionButton floatingActionButton,
                             @DrawableRes int openDrawableResId,
                             @DrawableRes int closeDrawableResId) {

        if (floatingButtonOpenDrawable.containsKey(floatingActionButton.getId())) {
            floatingButtonOpenDrawable.remove(floatingActionButton.getId());
        }

        if (floatingButtonCloseDrawable.containsKey(floatingActionButton.getId())) {
            floatingButtonCloseDrawable.remove(floatingActionButton.getId());
        }

        floatingButtonOpenDrawable.put(floatingActionButton.getId(), openDrawableResId);
        floatingButtonCloseDrawable.put(floatingActionButton.getId(), closeDrawableResId);
    }
}
