package com.ao8r.awcontrolroomapp.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ao8r.awcontrolroomapp.R;

import java.util.List;

public class LiveYouTubeVideos extends AppCompatActivity {

    // creating a variable for button
    // to start youtube live stream
    private Button startBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_you_tube_videos);


        // adding on click listener to our button.
        startBtn = findViewById(R.id.idBtnStartLiveStream);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calling a method to check if the device has
                // support to make youtube live stream or not.
                validateMobileLiveIntent(LiveYouTubeVideos.this);
            }
        });
    }

    private boolean canResolveMobileLiveIntent(Context context) {
        // in this method we are calling a youtube live  intent package name
        // and we are checking if youtube live intent is present or not.
        Intent intent = new Intent("com.google.android.youtube.intent.action.CREATE_LIVE_STREAM").setPackage("com.google.android.youtube");
        PackageManager pm = context.getPackageManager();
        List resolveInfo = pm.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);

        // returning the result after checking
        // the youtube live stream intent.
        return resolveInfo != null && !resolveInfo.isEmpty();
    }

    private void validateMobileLiveIntent(LiveYouTubeVideos context) {
        if (canResolveMobileLiveIntent(context)) {
            // Launch the live stream Activity
            startMobileLive(LiveYouTubeVideos.this);
        } else {
            // on below line displaying a toast message if the intent is not present.
            Toast.makeText(context, "Please Update your Youtube app.", Toast.LENGTH_SHORT).show();
            // Prompt user to install or upgrade the YouTube app
        }
    }

    // method to create our intent for youtube live stream.
    private Intent createMobileLiveIntent(Context context, String description) {

        // on below line we are creating a new intent and we are setting package name to it.
        Intent intent = new Intent("com.google.android.youtube.intent.action.CREATE_LIVE_STREAM").setPackage("com.google.android.youtube");

        // on below line we are creating a new uri and setting
        // a scheme to it and appending our path with our package name.
        Uri referrer = new Uri.Builder()
                .scheme("android-app")
                .appendPath(context.getPackageName())
                .build();
        // on above line we are building our intent.
        // on below line we are adding our referrer
        // and subject for our live video.
        intent.putExtra(Intent.EXTRA_REFERRER, referrer);
        if (!TextUtils.isEmpty(description)) {
            intent.putExtra(Intent.EXTRA_SUBJECT, description);
        }
        // at last we are returning intent.
        return intent;
    }

    private void startMobileLive(Context context) {

        // calling a method to create an intent.
        Intent mobileLiveIntent = createMobileLiveIntent(context, "Streaming via ...");

        // on below line we are calling
        // our activity to start stream
        startActivity(mobileLiveIntent);
    }
}