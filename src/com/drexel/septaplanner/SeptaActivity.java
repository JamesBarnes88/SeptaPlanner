package com.drexel.septaplanner;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SeptaActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_septa);

		
		/*Buttons on Activity*/
		Button buttonBuses = (Button) findViewById(R.id.button_septa_buses);
		Button buttonTrains = (Button) findViewById(R.id.button_septa_trains);
		Button buttonRegRail= (Button) findViewById(R.id.button_septa_regrail);
		
		buttonBuses.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//we need a bus activity to use this
//				Intent i = new Intent(SeptaActivity.this, TripActivity.class);
//				startActivity(i);
			}
		});
		buttonTrains.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//we need a train activity to use this
//				Intent i = new Intent(SeptaActivity.this, SeptaActivity.class);
//				startActivity(i);
			}
		});
		buttonRegRail.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(SeptaActivity.this, SeptaRRActivity.class);
				startActivity(i);
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.septa, menu);
		return true;
	}

}
