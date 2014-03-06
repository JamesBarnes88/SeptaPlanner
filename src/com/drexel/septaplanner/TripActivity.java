package com.drexel.septaplanner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

public class TripActivity extends Activity {
	private int hour = 0;
	private int min = 0;
	private String source;
	private String dest;
	private String method;
	private Spinner spinSource;
	private Spinner spinDest;
	private Spinner spinTravelMethod;
	private TimePicker timePicker;
	private Button buttonProceed;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_trip);

		hour = 0;
		min = 0;

		spinTravelMethod= (Spinner) findViewById(R.id.spin_travelmethod);
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
				source= spinSource.getSelectedItem().toString();
				dest= spinDest.getSelectedItem().toString();
				method= spinTravelMethod.getSelectedItem().toString();
				Toast.makeText(
						TripActivity.this,
						"Method: "+ method+" souce: "+source+" dest: "+dest+" hour: "+hour+" min: "+min,
						Toast.LENGTH_LONG).show();
				
/*				put code to fetch data here, display loading icon while it 
				is getting the json data then call for new intent with that 
				information for the TripDisplayActivity.
				
				this could be a AsyncTask or a function that calls the task, 
				on postExecute you could call the intent, and cancel the 
				loading view here. */
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.trip, menu);
		return true;
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

}
