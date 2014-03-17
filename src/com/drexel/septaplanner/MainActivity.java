package com.drexel.septaplanner;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//Defined Buttons
		Button buttonTrip = (Button) findViewById(R.id.button_trip);
		Button buttonRecentTrips = (Button) findViewById(R.id.button_recent);
		

		//Implemented if clicked 
		buttonTrip.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this, TripActivity.class);
				startActivity(i);
			}
		});
		buttonRecentTrips.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this, RecentTripsActivity.class);
				startActivity(i);
			}
		});
		


	}
}
