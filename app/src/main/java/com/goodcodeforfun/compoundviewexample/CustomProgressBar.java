package com.goodcodeforfun.compoundviewexample;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created by snigavig on 30.03.16.
 */
public class CustomProgressBar extends RelativeLayout {
    private static final ScheduledExecutorService worker =
            Executors.newSingleThreadScheduledExecutor();

    private static final int DELAY = 200;
    private static final float ANIMATION_START = 1.0f;
    private static final float ANIMATION_END = 0.0f;
    private static int mAnimationDuration;
    private View mTopLeft;
    private View mTopCenter;
    private View mTopRight;
    private View mRightCenter;
    private View mBottomRight;
    private View mBottomCenter;
    private View mBottomLeft;
    private View mLeftCenter;
    private ArrayList<View> mViewArrayList = new ArrayList<>();
    private ArrayList<ScheduledFuture<?>> scheduledFutures = new ArrayList<>();

    public CustomProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_custom_progressbar, this, true);
        mTopLeft = findViewById(R.id.top_left);
        mTopCenter = findViewById(R.id.top_center);
        mTopRight = findViewById(R.id.top_right);
        mRightCenter = findViewById(R.id.right_center);
        mBottomRight = findViewById(R.id.bottom_right);
        mBottomCenter = findViewById(R.id.bottom_center);
        mBottomLeft = findViewById(R.id.bottom_left);
        mLeftCenter = findViewById(R.id.left_center);
        mViewArrayList.add(mTopLeft);
        mViewArrayList.add(mTopCenter);
        mViewArrayList.add(mTopRight);
        mViewArrayList.add(mRightCenter);
        mViewArrayList.add(mBottomRight);
        mViewArrayList.add(mBottomCenter);
        mViewArrayList.add(mBottomLeft);
        mViewArrayList.add(mLeftCenter);
        mAnimationDuration = (DELAY * mViewArrayList.size()) / 2;
    }

    public void startProgress() {
        invalidateViews();
        for (View view : mViewArrayList) {
            scheduledFutures.add(
                    worker.schedule(
                            fadeTask(view),
                            DELAY * (mViewArrayList.indexOf(view) + 1),
                            TimeUnit.MILLISECONDS
                    )
            );
        }
    }

    public void stopProgress() {
        for (ScheduledFuture<?> future : scheduledFutures) {
            future.cancel(true);
        }
        scheduledFutures.clear();
    }

    private void invalidateViews() {
        for (View view : mViewArrayList) {
            view.clearAnimation();
            view.setAlpha(ANIMATION_START);
        }
    }

    private Runnable fadeTask(final View view) {
        return new Runnable() {
            public void run() {
                post(new Runnable() {
                    @Override
                    public void run() {
                        AlphaAnimation anim = new AlphaAnimation(ANIMATION_START, ANIMATION_END);
                        anim.setDuration(mAnimationDuration);
                        anim.setRepeatCount(Animation.INFINITE);
                        anim.setRepeatMode(Animation.REVERSE);
                        view.startAnimation(anim);
                    }
                });
            }
        };
    }
}
