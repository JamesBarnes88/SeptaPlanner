package com.drexel.septaplanner;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;

public class TripDisplayActivity extends Activity {

	TripAdapter tripAdapter;
	Trip trip;
	ArrayList<Trip> trips;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_trip_display);
		ListView l = (ListView) findViewById(R.id.list_trip_display);
		tripAdapter = new TripAdapter(this, R.layout.list_item_trips);
		tripAdapter.setNotifyOnChange(true);
		l.setAdapter(tripAdapter);

		Intent i = getIntent();
		System.out.println(i.hasExtra("trip"));

		trip = i.getParcelableExtra("trip");
		
		TripAsyncTask tripTask= new TripAsyncTask();
		tripTask.execute(trip);

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.trip_display, menu);
		return true;
	}

	public class TripAsyncTask extends AsyncTask<Trip, Void, Void> {
		ArrayList<Trip> trips;
		
		public TripAsyncTask() {
			// TODO Auto-generated constructor stub
		}

		@Override
		protected void onPostExecute(Void arg0) {
			tripAdapter.clear();
			System.out.println(trips.size());
			for (int i = 0; i < trips.size(); i++) {
				tripAdapter.add(trips.get(i));
			}
		}

		@Override
		protected Void doInBackground(Trip... arg0) {
			trips=Trip.getTrips(trip);

			return null;
			
		}
	}

}
