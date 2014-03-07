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
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesUtil;

public class SeptaRRActivity extends Activity {

	SeptaTrainAdapter adapter;
	Context context = this;
	String tag = "mySeptaViewer";
	String stationTo;
	String stationFrom;
	String[] scheduleResult;


	Button mapbutton;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);

		Log.d("mygooglemaps", Integer.toString(GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(getApplicationContext())));

		setContentView(R.layout.activity_septa_rr);
		ListView lv = (ListView) findViewById(R.id.listview);
		Spinner spinTo = (Spinner) findViewById(R.id.spinner_to);
		Spinner spinFrom = (Spinner) findViewById(R.id.spinner_from);
		Button button = (Button) findViewById(R.id.button_go);
		mapbutton = (Button) findViewById(R.id.button_map);

		// listview stuff
		adapter = new SeptaTrainAdapter(this, R.layout.list_item_trains);
		lv.setAdapter(adapter);
		adapter.setNotifyOnChange(true);

		// Spinner stuff
		ArrayList<String> stations = getStations();
		ArrayAdapter<String> stationAdapter1 = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, stations);
		ArrayAdapter<String> stationAdapter2 = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, stations);

		spinTo.setAdapter(stationAdapter1);
		spinFrom.setAdapter(stationAdapter2);
		
		//this may not need to be used in this act
//		lv.setOnItemLongClickListener(new OnItemLongClickListener() {
//
//			@Override
//			public boolean onItemLongClick(AdapterView<?> adapter, View v,
//					int position, long id) {
//				
//				septaTrain train = (septaTrain) adapter
//				.getItemAtPosition(position);
//				Intent i= new Intent(MainActivity.this, TrainSchedule.class);
//				i.putExtra("trainnum", train.getTrainNum());
//				startActivity(i);
//
//				return true;
//			}
//		});

		// listeners
		spinTo.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> adapter, View v,
					int position, long id) {
				stationTo = (String) adapter.getItemAtPosition(position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		});
		spinFrom.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> adapter, View v,
					int position, long id) {
				stationFrom = (String) adapter.getItemAtPosition(position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		});

		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!stationFrom.equals(stationTo)) {
					SeptaAsyncTask stask = new SeptaAsyncTask();
					stask.setStations(stationFrom, stationTo);
					stask.execute();
				} else
					Toast.makeText(
							SeptaRRActivity.this,
							"Please select different stations for starting and destination",
							Toast.LENGTH_SHORT).show();
			}
		});

		mapbutton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (adapter.getCount() != 0) {
					ArrayList<septaTrain> trains = new ArrayList<septaTrain>();
					for (int i = 0; i < adapter.getCount(); i++) {
						trains.add(adapter.getItem(i));
					}
					Intent i = new Intent(SeptaRRActivity.this, MyMapFrag.class);
					i.putParcelableArrayListExtra("trains", trains);
					startActivity(i);
				} else
					Toast.makeText(
							SeptaRRActivity.this,
							"Please search for trains first before attempting to use map",
							Toast.LENGTH_SHORT).show();
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
