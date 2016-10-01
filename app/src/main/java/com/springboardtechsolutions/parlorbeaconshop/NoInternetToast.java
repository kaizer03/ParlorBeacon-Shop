package com.springboardtechsolutions.parlorbeaconshop;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.view.View;

import me.arulnadhan.AchievementUnlockedLib.AchievementUnlocked;


public class NoInternetToast {

    public static void nointernettoast(final Context activity)
    {
        int bg = 0xffffffff;
        boolean rounded = false;
        if (Build.VERSION.SDK_INT >= 21) {
            rounded = true;
        }

        final AchievementUnlocked toastCool =
                new AchievementUnlocked(activity).isRounded(rounded).setBackgroundColor(bg).isPersistent(true).setTitle("Internet not connected").setSubTitle("Tap to cancel").setIcon(getDrawableFromRes(R.drawable.delete,activity)).isPersistent(true).build();


        final View popUp = toastCool.getAchievementView();

        final View icon = toastCool.getIconView();
        int delta = toastCool.height;
        final ObjectAnimator transout = ObjectAnimator.ofFloat(icon, "translationY", 0, delta);
        final ObjectAnimator transin = ObjectAnimator.ofFloat(icon, "translationY", -delta, 0);
        transin.setDuration(250);
        transout.setDuration(250);
        final ValueAnimator blue = ValueAnimator.ofObject(new ArgbEvaluator(), bg, 0xff2196F3);
        blue.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                if (Build.VERSION.SDK_INT >= 21) {
                    popUp.getBackground().setTint((Integer) animator.getAnimatedValue());
                } else {
                    popUp.setBackgroundColor((Integer) animator.getAnimatedValue());
                }
            }

        });

        final ValueAnimator red = ValueAnimator.ofObject(new ArgbEvaluator(), bg, 0xffF44336);
        red.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                if (Build.VERSION.SDK_INT >= 21) {
                    popUp.getBackground().setTint((Integer) animator.getAnimatedValue());
                } else {
                    popUp.setBackgroundColor((Integer) animator.getAnimatedValue());
                }
            }

        });
        popUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (toastCool.expanded && !blue.isRunning()) {
                    toastCool.dismiss();
                    red.start();
                    transout.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            if (Build.VERSION.SDK_INT >= 16) {
                                icon.setBackground(getDrawableFromRes(R.drawable.no,activity));
                            } else {
                                icon.setBackgroundDrawable(getDrawableFromRes(R.drawable.no,activity));
                            }
                            transin.start();

                        }
                    });
                    transout.start();
                }

            }
        });

        toastCool.setAchievementListener(new AchievementUnlocked.achievementListener() {
            @Override
            public void onAchievementBeingCreated(AchievementUnlocked achievement, boolean created) {

            }

            @Override
            public void onAchievementExpanding(AchievementUnlocked achievement, boolean expanded) {
                if (expanded)

                    (new android.os.Handler()).postDelayed(new Runnable() {
                        public void run() {
                            if (toastCool.expanded && !red.isRunning()) {
                                popUp.setOnClickListener(null);
                                toastCool.dismiss();
                                blue.start();

                                transout.addListener(new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        super.onAnimationEnd(animation);
                                        if (Build.VERSION.SDK_INT >= 16) {
                                            icon.setBackground(getDrawableFromRes(R.drawable.send,activity));
                                        } else {
                                            icon.setBackgroundDrawable(getDrawableFromRes(R.drawable.send,activity));
                                        }

                                        transin.start();
                                    }
                                });
                                transout.start();
                            }
                        }
                    }, 3000);
            }

            @Override
            public void onAchievementShrinking(AchievementUnlocked achievement, boolean shrunken) {
                achievement.getTitleTextView().setVisibility(View.INVISIBLE);

                achievement.getSubtitleTextView().setVisibility(View.INVISIBLE);

            }

            @Override
            public void onAchievementBeingDestroyed(AchievementUnlocked achievement, boolean destroyed) {

            }
        });


        toastCool.show();
    }

    private static Drawable getDrawableFromRes(int ResID, Context context) {
        if (Build.VERSION.SDK_INT >= 21)context.getDrawable(ResID);
        return context.getResources().getDrawable((ResID));
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

}
