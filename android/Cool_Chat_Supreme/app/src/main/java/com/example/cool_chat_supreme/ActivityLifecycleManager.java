package com.example.cool_chat_supreme;

import android.app.Application;
import android.content.Context;

public class ActivityLifecycleManager {

    private static ActivityLifecycleCallbacks activityLifecycleCallbacks = null;

    private ActivityLifecycleManager() {
    }

    public static ActivityLifecycleCallbacks getInstance() {
        if (activityLifecycleCallbacks == null) {
            activityLifecycleCallbacks = new ActivityLifecycleCallbacks();
            Context applicationContext = Cool_Chat_Supreme.context;

            // Register the activity lifecycle callbacks
            Application application = (Application) applicationContext;
            application.registerActivityLifecycleCallbacks(activityLifecycleCallbacks);
        }
        return activityLifecycleCallbacks;
    }


}
