package com.example.cool_chat_supreme;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ActivityLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {
    public int activityCount = 0;

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
        activityCount++;
    }

    @Override
    public void onActivityPaused(Activity activity) {
        activityCount--;
    }

    @Override
    public void onActivityStopped(Activity activity) {
        // No action needed
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        // No action needed
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        // No action needed
    }
}