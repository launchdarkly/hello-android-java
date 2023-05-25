package com.launchdarkly.hello_android;

import static com.launchdarkly.hello_android.MainApplication.LAUNCHDARKLY_MOBILE_KEY;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.launchdarkly.sdk.android.LDClient;
import com.launchdarkly.sdk.android.LaunchDarklyException;

// Set FLAG_KEY to the feature flag key you want to evaluate.
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    // Set BOOLEAN_FLAG_KEY to the feature flag key you want to evaluate.
    private static final String BOOLEAN_FLAG_KEY = "my-boolean-flag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = findViewById(R.id.textview);

        if (BOOLEAN_FLAG_KEY == "my-boolean-flag") {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("BOOLEAN_FLAG_KEY was not customized for this application.");
            builder.create().show();
        }

        if (LAUNCHDARKLY_MOBILE_KEY == "mobile-key-from-launch-darkly-website") {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("LAUNCHDARKLY_MOBILE_KEY was not customized for this application.");
            builder.create().show();
        }

        LDClient  client;
        try {
            client = LDClient.get();
        } catch (LaunchDarklyException e) {
            Log.e(TAG, "Unexpected error getting client.", e);
            return;
        }

        // to get the variation the SDK has cached
        textView.setText(
                getString(
                        R.string.flag_evaluated,
                        BOOLEAN_FLAG_KEY,
                        Boolean.toString(client.boolVariation(BOOLEAN_FLAG_KEY, false))
                )
        );

        // to register a listener to get updates in real time
        client.registerFeatureFlagListener(BOOLEAN_FLAG_KEY, flagKey -> {
            textView.setText(
                    getString(
                            R.string.flag_evaluated,
                            BOOLEAN_FLAG_KEY,
                            Boolean.toString(client.boolVariation(BOOLEAN_FLAG_KEY, false))
                    )
            );
        });

        // This call is just to make sure all evaluation events show up immediately for this demo.
        // Otherwise they will be sent at some point in the future.  You don't need to call this
        // in production, it is handled automatically at a interval (it is customizable).
        client.flush();
    }
}
