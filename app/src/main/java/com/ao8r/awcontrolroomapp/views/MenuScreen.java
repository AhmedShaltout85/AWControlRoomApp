package com.ao8r.awcontrolroomapp.views;

import static androidx.core.content.PackageManagerCompat.LOG_TAG;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.ao8r.awcontrolroomapp.R;
import com.ao8r.awcontrolroomapp.customiz_widgets.CustomToast;
import com.ao8r.awcontrolroomapp.utils.ControlRoomConstants;

import java.util.List;
import java.util.Locale;


public class MenuScreen extends AppCompatActivity implements LocationListener {
    private LocationManager locationManager;
    private String locationLong, locationLat;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_screen);


        // Find the toolbar view inside the activity layout
        Toolbar toolbarMenu = (Toolbar) findViewById(R.id.customToolbarId);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbarMenu);

        toolbarMenu.setSubtitle(ControlRoomConstants.LOGIN_USER);
        toolbarMenu.setSubtitleTextColor(Color.WHITE);
        toolbarMenu.setPadding(1,2,1,2);
//        set title
        setTitle(ControlRoomConstants.MENU_TITLE);
//        setTitle("القائمة الرئيسية");
        toolbarMenu.setTitleTextColor(Color.WHITE);
        //        set Back Button in action Bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
        //        back Icon change its color
        toolbarMenu.getNavigationIcon().setColorFilter(getResources()
                .getColor(R.color.whiteColor), PorterDuff.Mode.SRC_ATOP);


        //      Call find Location fun
        getLocation();

        //ask for location permission  ** run time permission
        if (ContextCompat.checkSelfPermission(MenuScreen.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MenuScreen.this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, 100);
        }




}
    //    Go Back to LoginPage
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(MenuScreen.this, LoginScreen.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();

    }
    // Menu icons are inflated just as they were with actionbar
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_menu_bar, menu);
//        return true;
//    }

// nav to search and fav Views and Menus Items

    //    views
    public void navToLiveVideo(View view) {

        intentToLiveVideo();
    }

    public void navToLiveLocation(View view) {
        intentToLiveLocation();
    }

    //   Menus



//    public void navToAppGuideBook(MenuItem item) {
//
//        intentAppGuideBook();
//    }

//    content of Intents

    private void intentToYouTubeLive() {

        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://us05web.zoom.us/j/81354627467?pwd=Qqd5xgG1Txnk593LNL3Ze8lzZxzUHw.1#success"));
        try {
            MenuScreen.this.startActivity(webIntent);
        } catch (ActivityNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    private void intentToLiveLocation() {

//        if(locationLat ==null || locationLong == null){
//            CustomToast.customToast(getApplicationContext(), "GPS not active");
//        }else {
////            sendMessage("new point", "message body");31.197056, 29.909812
//        }
        CustomToast.customToast(getApplicationContext(),"kom eldekah" +" -- "+31.1971506 +" -- "+ 29.910532);
        String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?daddr=%f,%f (%s)", 31.197056, 29.909812, "مكتب البرمجيات كوم الدكه");
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        intent.setPackage("com.google.android.apps.maps");
        startActivity(intent);

//        Intent intent = new Intent(this, LiveLocationScreen.class);
//        startActivity(intent);
    }

    private void intentToLiveVideo() {

//        intentToYouTubeLive();
        Intent intent = new Intent(this, LiveYouTubeVideos.class);
        startActivity(intent);
        validateMobileLiveIntent(MenuScreen.this);

    }


    @SuppressLint("MissingPermission")
    private void getLocation() {
        try {
            locationManager = (LocationManager) getApplicationContext()
                    .getSystemService(LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    0, 0, MenuScreen.this);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                    0, 0, MenuScreen.this);
            if (locationManager == null) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        locationManager = (LocationManager) getApplicationContext()
                                .getSystemService(LOCATION_SERVICE);
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                                0, 0, MenuScreen.this);
                        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                                0, 0, MenuScreen.this);

                    }
                }, 3000);


            } else {
            }
        } catch (Exception e) {
            System.out.println("000000000000000000000000000" + e);


            e.printStackTrace();
        }

    }

    @Override
    public void onLocationChanged(Location location) {
        locationLat = String.valueOf(location.getLatitude());
        locationLong = String.valueOf(location.getLongitude());

        try {
            String locationLongLat = locationLat + locationLong;
            System.out.println(locationLongLat);
        } catch (Exception e) {
            System.out.println("111111111111111" + e);
            e.printStackTrace();
        }

    }


    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        CustomToast.customToast(getApplicationContext(), "فضلا قم بتفعيل GPS");


    }




    @SuppressLint("RestrictedApi")
    private void sendMessage(String subject, String message) {

        try {
            Intent messageIntent = new Intent(android.content.Intent.ACTION_SEND);
            messageIntent.setType("message/rfc822");

            String aEmailList[] = { "ahraahab85@gmail.com" };
            messageIntent.putExtra(android.content.Intent.EXTRA_EMAIL, aEmailList);

            messageIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);

            messageIntent.putExtra(android.content.Intent.EXTRA_TEXT,
                    message + "http://maps.google.com/maps?q=loc:" + locationLat +","+ locationLong);

            
            messageIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            messageIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                messageIntent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                messageIntent.addFlags(Intent.FLAG_GRANT_PREFIX_URI_PERMISSION);
            }
            startActivity(Intent.createChooser(messageIntent, "Send mail..."));
        }
        catch (Exception e) {
            Log.e(LOG_TAG, "sendPictureMessage() failed to start activity.", e);
            Toast.makeText(this, "There are no email clients installed.", Toast.LENGTH_LONG).show();
        }
    }


//

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

    private void validateMobileLiveIntent(MenuScreen context) {
        if (canResolveMobileLiveIntent(MenuScreen.this)) {
            // Launch the live stream Activity
            startMobileLive(context);
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