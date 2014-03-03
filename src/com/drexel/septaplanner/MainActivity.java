package com.drexel.septaplanner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesUtil;

public class MainActivity extends Activity {

	MyArrayAdapter adapter;
	Context context = this;
	String tag = "mySeptaViewer";
	String stationTo;
	String stationFrom;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Log.d("mygooglemaps", Integer.toString(GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext())));
		
		setContentView(R.layout.activity_main);
		ListView lv = (ListView) findViewById(R.id.listview);
		Spinner spinTo = (Spinner) findViewById(R.id.spinner_to);
		Spinner spinFrom = (Spinner) findViewById(R.id.spinner_from);
		Button button = (Button) findViewById(R.id.button_go);
		Button mapbutton= (Button) findViewById(R.id.button_map);
		
		//listview stuff
		adapter = new MyArrayAdapter(this, R.layout.list_item_trips);
		lv.setAdapter(adapter);
		adapter.setNotifyOnChange(true);

		//Spinner stuff
		ArrayList<String> stations = getStations();
		ArrayAdapter<String> stationAdapter1= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, stations);
		ArrayAdapter<String> stationAdapter2= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, stations);

		spinTo.setAdapter(stationAdapter1);
		spinFrom.setAdapter(stationAdapter2);

		//listeners
		spinTo.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> adapter, View v, int position,
					long id) {
				stationTo= (String) adapter.getItemAtPosition(position);	
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		});
		spinFrom.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> adapter, View v, int position,
					long id) {
				stationFrom= (String) adapter.getItemAtPosition(position);	
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		});

		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(stationFrom !=null && stationTo !=null){
					SeptaAsyncTask stask = new SeptaAsyncTask();
					stask.setStations(stationFrom, stationTo);
					stask.execute();
				}
				else
					Toast.makeText(MainActivity.this, "Please select a departure and arrival station", Toast.LENGTH_SHORT).show();
			}
		});
		
		mapbutton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i= new Intent(MainActivity.this, MyMapFrag.class);
//				i.put
				startActivity(i);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public class SeptaAsyncTask extends AsyncTask<Void, Void, Void> {
		septaTrain[] trains;
		String stationFrom, stationTo;

		public SeptaAsyncTask() {
			// TODO Auto-generated constructor stub
		}

		@Override
		protected void onPostExecute(Void arg0) {
			adapter.clear();
			for (int i = 0; i < trains.length; i++) {
				adapter.add(trains[i]);
			}
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			Log.d(tag, "thread started");

			Septa s = new Septa();
			Log.d(tag, "calling getdata");

			trains = s.getNTA(stationFrom, stationTo);
			System.out.println(trains.toString());

			return null;
		}

		public void setStations(String stationFrom, String stationTo) {
			this.stationFrom = stationFrom;
			this.stationTo = stationTo;
		}
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
