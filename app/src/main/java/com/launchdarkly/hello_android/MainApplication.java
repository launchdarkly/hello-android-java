package com.launchdarkly.hello_android;

import android.app.Application;

import com.launchdarkly.sdk.ContextKind;
import com.launchdarkly.sdk.LDContext;
import com.launchdarkly.sdk.android.LDClient;
import com.launchdarkly.sdk.android.LDConfig;

public class MainApplication extends Application {

    // Set LAUNCHDARKLY_MOBILE_KEY to your LaunchDarkly SDK mobile key.
    static final String LAUNCHDARKLY_MOBILE_KEY = "mobile-key-from-launch-darkly-website";

    @Override
    public void onCreate() {
        super.onCreate();

        // Set LAUNCHDARKLY_MOBILE_KEY to your LaunchDarkly mobile key found on the LaunchDarkly
        // dashboard in the start guide.
        LDConfig ldConfig = new LDConfig.Builder()
                .mobileKey(LAUNCHDARKLY_MOBILE_KEY)
                .build();

        // Set up the context properties. This context should appear on your LaunchDarkly context
        // dashboard soon after you run the demo.
        LDContext context;
        if (isUserLoggedIn()) {
            context = LDContext.builder(ContextKind.DEFAULT, getUserKey())
                    .name(getUserName())
                    .build();
        } else {
            context = LDContext.builder(ContextKind.DEFAULT, "example-user-key")
                    .anonymous(true)
                    .build();
        }

        LDClient.init(this, ldConfig, context);
    }

    private boolean isUserLoggedIn() {
        return false;
    }

    private String getUserKey() {
        return "user-key-123abc";
    }

    private String getUserName(){
        return "Sandy";
    }
}
