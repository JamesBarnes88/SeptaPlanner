package com.drexel.septaplanner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.cloudmine.api.CMApiCredentials;
import com.google.android.gms.maps.model.LatLng;

public class TripActivity extends Activity implements LocationListener {

	// Cloudmine APP_KEY and ID
	private static String APP_ID = "a1fd3e9b5488440084885a4205f0af96";
	private static String APP_KEY = "e03802a295e54275b2d054789c232951";

	// tag for Logcat
	String tag = "com.drexel.septaplanner.tripActivity";

	// variables for main functionality
	private int hour = 0;
	private int min = 0;
	private String source;
	private String dest;
	private String method; // method of travel

	// views
	private Spinner spinSource;
	private Spinner spinDest;
	private Spinner spinTravelMethod;
	private TimePicker timePicker;
	private Button buttonProceed;

	// variables for location data
	private LocationManager locationManager;
	private String provider;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_trip);

		// Credentials for Cloudmine
		CMApiCredentials.initialize(APP_ID, APP_KEY, getApplicationContext());

		hour = 0;
		min = 0;

		spinTravelMethod = (Spinner) findViewById(R.id.spin_travelmethod);
		spinSource = (Spinner) findViewById(R.id.spin_start_station);
		spinDest = (Spinner) findViewById(R.id.spin_end_station);
		timePicker = (TimePicker) findViewById(R.id.timePicker1);
		buttonProceed = (Button) findViewById(R.id.button_proceed);

		// get stations and make adapters
		ArrayList<String> stations = getStations();
		ArrayAdapter<String> stationAdapter1 = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, stations);
		ArrayAdapter<String> stationAdapter2 = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, stations);

		// set adapters
		spinSource.setAdapter(stationAdapter1);
		spinDest.setAdapter(stationAdapter2);

		buttonProceed.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				hour = timePicker.getCurrentHour();
				min = timePicker.getCurrentMinute();
				source = spinSource.getSelectedItem().toString();
				dest = spinDest.getSelectedItem().toString();
				method = spinTravelMethod.getSelectedItem().toString()
						.toLowerCase();
				Location location = getLocationData();
				// getdestLocation()
				String time = "";
				String shour="";
				if (hour < 12){
					if(hour==0)
						shour=Integer.toString(12);
					else
						shour="0"+Integer.toString(hour);
					time = shour + ":" + Integer.toString(min)
							+ "AM";
				}
				else
					time = Integer.toString(hour - 12) + ":"
							+ Integer.toString(min) + "PM";

				// second latlng should be the destination latlng from the csv
				Trip trip = new Trip(method.toLowerCase(), source, dest, time,
						new LatLng(location.getLatitude(), location
								.getLongitude()), new LatLng(40.0295,
								-75.181375), 1);

				Toast.makeText(TripActivity.this, trip.toString(),
						Toast.LENGTH_LONG).show();
				
                //Save cloudmine object
                trip.save();

				Intent i = new Intent(TripActivity.this,
						TripDisplayActivity.class);
				i.putExtra("trip", trip);
				startActivity(i);
				
				

				/*
				 * put code to fetch data here, display loading icon while it is
				 * getting the json data then call for new intent with that
				 * information for the TripDisplayActivity.
				 * 
				 * this could be a AsyncTask or a function that calls the task,
				 * on postExecute you could call the intent, and cancel the
				 * loading view here.
				 */

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.trip, menu);
		return true;
	}

	public Location getLocationData() {
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		provider = locationManager.getBestProvider(criteria, false);
		Location location = locationManager.getLastKnownLocation(provider);

		if (location != null) {
			onLocationChanged(location);
		} else {
			Toast.makeText(getApplicationContext(), "Location not found!!! =)",
					Toast.LENGTH_LONG).show();

			// ask user to enter address
		}
		return location;
	}

	public ArrayList<String> getStations() {
		ArrayList<String> stations = new ArrayList<String>();
		AssetManager assetman = getAssets();
		try {
			String line = "";
			BufferedReader in = new BufferedReader(new InputStreamReader(
					assetman.open("stations.csv")));
			line = in.readLine(); // skip first line
			while ((line = in.readLine()) != null) {
				line = line.split(",")[1];
				stations.add(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return stations;
	}

	/* **********************************************************
	 * I need to figure out what these do. I have no clue
	 */

	@Override
	public void onLocationChanged(Location location) {
		Log.d(tag, "on location changed ran");

	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}
	// **********************************************************

}
