package com.example.septaplanneralpha;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class RecentTripsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recent_trips);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.recent_trips, menu);
		return true;
	}

}
