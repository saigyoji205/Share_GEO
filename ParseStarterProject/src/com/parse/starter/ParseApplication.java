package com.parse.starter;


import android.content.Context;
import android.content.SharedPreferences;

import com.parse.Parse;
import com.parse.ParseCrashReporting;
import com.parse.ParseObject;

public class ParseApplication extends android.app.Application {

  public static final boolean APPDEBUG = false;

  public static final String APPTAG = "GeoShare";

  public static final String INTENT_EXTRA_LOCATION = "location";

  private static final String KEY_SEARCH_DISTANCE = "searchDistance";

  private static final float  DEFAULT_SEARCH_DISTANCE = 250.0f;

  private static final String APPLICATION_ID = "A4NGzVcXMCW1OUM9hICSMuiRgUNMVwg9DnkxKSlq";

  private static final String APPLICATION_PASS = "XHKMXqBPW40O398YfVPlSlDfxqCJRV5zCqYPQXol";

  private static SharedPreferences preferences;

  private static ConfigHelper configHelper;

  @Override
  public void onCreate() {
    super.onCreate();


    // Initialize Crash Reporting.
    ParseCrashReporting.enable(this);

    // Enable Local Datastore.
    Parse.enableLocalDatastore(this);

    ParseObject.registerSubclass(GeoShare.class);

    // Add your initialization code here
    Parse.initialize(this,APPLICATION_ID ,APPLICATION_PASS );

    preferences = getSharedPreferences("com.parse.starter", Context.MODE_PRIVATE);

    configHelper = new ConfigHelper();
    configHelper.fetchConfigNeeded();

  }

  public static float getSearchDistance()
  {
    return preferences.getFloat(KEY_SEARCH_DISTANCE,DEFAULT_SEARCH_DISTANCE);
  }

  public static ConfigHelper getConfigHelper()
  {
    return configHelper;
  }

  public static void setSearchDistance(float value)
  {
    preferences.edit().putFloat(KEY_SEARCH_DISTANCE,value).commit();
  }
}
