package com.drexel.septaplanner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;

import com.cloudmine.api.CMApiCredentials;
import com.cloudmine.api.CMObject;
import com.cloudmine.api.persistance.ClassNameRegistry;
import com.cloudmine.api.rest.CMStore;
import com.cloudmine.api.rest.callbacks.CMObjectResponseCallback;
import com.cloudmine.api.rest.response.CMObjectResponse;

public class RecentTripsActivity extends Activity {

	// Cloudmine APP_KEY and ID
	private static String APP_ID = "a1fd3e9b5488440084885a4205f0af96";
	private static String APP_KEY = "e03802a295e54275b2d054789c232951";

	// Global Variables
	List<Map<String, String>> tripList = new ArrayList<Map<String, String>>();
	TripAdapter adapter;
	ListView lv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recent_trips);
		lv = (ListView) findViewById(R.id.list_recent_trips);
		adapter = new TripAdapter(this, R.layout.list_item_trips);
		lv.setAdapter(adapter);
		adapter.setNotifyOnChange(true);

		// Credentials for Cloudmine
		CMApiCredentials.initialize(APP_ID, APP_KEY, getApplicationContext());
		ClassNameRegistry.register(Trip.CLASS_NAME, Trip.class);
		System.out.println("registered class");
		CMStore store = CMStore.getStore();

		store.loadAllApplicationObjects(new CMObjectResponseCallback() {
			public void onCompletion(CMObjectResponse response) {
				System.out.println("count of response"
						+ response.getObjects().size());
				List<CMObject> temp = new ArrayList<CMObject>();
				temp = response.getObjects();
				for (int i = 0; i < temp.size(); i++) {
					// do something with each object
					Trip trip = (Trip) temp.get(i);
					adapter.add(trip);
				}
			}

			public void onFailure(Throwable e, String msg) {
				System.out.println("We failed: " + e.getMessage());
			}
		});
		System.out.println("ran cm stuff");

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.recent_trips, menu);
		return true;
	}

	private Map<String, String> createTrips(String key, String name) {
		HashMap<String, String> trips = new HashMap<String, String>();
		trips.put(key, name);

		return trips;
	}

	// public class RecentAsyncTask extends AsyncTask<Void, Void, Void> {
	//
	// List<CMObject> displayedTrips = new ArrayList<CMObject>();
	// @Override
	// protected void onPostExecute(Void arg0) {
	//
	// System.out.println(tripList.size());
	// //Create the adapter
	// simpleAdpt = new SimpleAdapter(
	// com.drexel.septaplanner.RecentTripsActivity.this,
	// tripList, android.R.layout.simple_list_item_1,
	// new String[] { "Trip" }, new int[] { android.R.id.text1 });
	//
	// //Set the adapter
	// lv.setAdapter(simpleAdpt);
	//
	// //If item is clicked on in ListView
	// lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
	//
	// @SuppressWarnings("unchecked")
	// @Override
	// public void onItemClick(AdapterView<?> parentAdapter, View view,
	// int position, long id) {
	// // TODO Auto-generated method stub
	//
	// //Get lot info and put it in an intent for edit Activity
	// Trip intentTrip = new Trip();
	// intentTrip = (Trip) displayedTrips.get((int)id);
	// Intent i = new Intent(RecentTripsActivity.this, TripActivity.class);
	// i.putExtra("Trip", intentTrip);
	// startActivity(i);
	//
	//
	// }
	// });
	//
	// }
	//
	// protected Void doInBackground(Void... arg0) {
	//
	// return null;
	//
	// }

}