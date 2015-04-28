package com.parse.starter;


import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.Marker;
import com.parse.ParseGeoPoint;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends FragmentActivity implements LocationListener, GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener
{

	private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;

	// Milliseconds per second　(1秒→1000ミリ秒)
	private static final int MILLISECONDS_PER_SECOND = 1000;

	// The update interval(更新間隔)
	private static final int UPDATE_INTERVAL_IN_SECONDS = 5;

	// A fast interval celling
	private  static final int FAST_CEILING_IN_SECONDS = 1;

	//Update interval in milliseconds
	private static final long UPDATE_INTERVAL_IN_MILLISECONDS = MILLISECONDS_PER_SECOND * UPDATE_INTERVAL_IN_SECONDS;

	//A fast ceiling of update intervals, used when the app is visible
	private static final long FAST_INTERVAL_CEILING_IN_MILLISECONDS = MILLISECONDS_PER_SECOND * FAST_CEILING_IN_SECONDS;

	/*
	 * Constants for handling location results
	 */
	// Conversion from feet to meters(フィート→メートル)
	private static final float METERS_PER_FEET = 0.3048f;
	// Conversion from kilometers to meters
	private static final float METERS_PER_KILOMETER = 1000;
	// Initial offset for calculating the map bounds
	private static final double OFFSET_CALCULATION_INIT_DIFF = 1.0;
	// Accuracy for calculating the map bounds
	private static final float OFFSET_CALCULATING_ACCURACY = 0.01F;
	// Maximum results returned from a Parse query
	private static final int MAX_POST_SEARCH_RESULTS = 20;
	// Maximum post search radius for map in kilometers
	private static final int MAX_POST_SEARCH_DISTANCE = 100;

	//A request to connect to Location Services
	private LocationRequest locationRequest;
	//Fields for the map in feet(領域の半径)
	private float radius;
	private float lastRadius;

	//Fields for helping process map and location changes
	private final Map<String,Marker>mapMarkers = new HashMap<String,Marker>();
	private int mostRecentMapUpdate;
	private boolean hasSetUpInitialLocation;
	private String selectedPostObjectId;
	private Location lastLocation;
	private Location currentLocation;

	// Stores the current instantiation of the location client in this object(このオブジェクトの位置クライアントの現在のインスタンスを格納します)
	private GoogleApiClient locationClient;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		radius = ParseApplication.getSearchDistance();
		lastRadius = radius;

		// Create a new global location parameters object
		locationRequest = LocationRequest.create();
		//set the update interval
		locationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
		// use high accuracy(高精度さを優先)
		locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
		// Create a new location client, using the enclosing class to handle callbacks(コールバックを処理するために外側のクラスを使用して、新しい場所のクライアントを作成します。).
		locationClient = new GoogleApiClient.Builder(this).addApi(LocationServices.API).addConnectionCallbacks(this).addOnConnectionFailedListener(this).build();

		// Set up a customized query
		ParseQueryAdapter.QueryFactory<GeoShare> factory = new ParseQueryAdapter.QueryFactory<GeoShare>(){
			public ParseQuery<GeoShare> create(){
				Location myLoc = (currentLocation == null) ? lastLocation : currentLocation;
				ParseQuery<GeoShare> query = GeoShare.getQuery();
				query.include("user");
				query.orderByDescending("createAt");
				query.whereWithinKilometers("location", geoPointFromLocation(myLoc), radius * METERS_PER_FEET / METERS_PER_KILOMETER);
				query.setLimit(MAX_POST_SEARCH_RESULTS);
				return query;
			}
		};
	}

	private ParseGeoPoint geoPointFromLocation(Location loc) {
		return new ParseGeoPoint(loc.getLatitude(),loc.getLongitude());
	}

	@Override
	public void onConnected(Bundle bundle) {

	}

	@Override
	public void onConnectionSuspended(int i) {

	}

	@Override
	public void onLocationChanged(Location location) {

	}

	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {

	}
}
