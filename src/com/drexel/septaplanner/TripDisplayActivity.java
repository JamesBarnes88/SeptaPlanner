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
		int flag = i.getIntExtra("flag", 0);

		trip = i.getParcelableExtra("trip");
		

	}
	public void getTrips(){
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.trip_display, menu);
		return true;
	}

	public class SeptaAsyncTask extends AsyncTask<String, Void, Void> {
		Trip[] trips;
		
		public SeptaAsyncTask() {
			// TODO Auto-generated constructor stub
		}

		@Override
		protected void onPostExecute(Void arg0) {
			tripAdapter.clear();
			for (int i = 0; i < trips.length; i++) {
				tripAdapter.add(trips[i]);
			}
		}

		@Override
		protected Void doInBackground(String... arg0) {
			Log.d(tag, "thread started");

			Septa s = new Septa();
			Log.d(tag, "calling getdata");

			trips = s.getNTA(arg0[0], arg0[1]);
			System.out.println(trips.toString());

			return null;
		}
	}

}
