package com.drexel.septaplanner;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapLoadedCallback;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.LatLngBounds.Builder;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MyMapFrag extends FragmentActivity {

	String tag = "mymapfrag";
	LatLngBounds bounds;

	public GoogleMap mMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map_fragment);
		// get arraylist passed into intent
		Intent i = getIntent();
		ArrayList<septaTrain> trains = i.getParcelableArrayListExtra("trains");

		// make map object
		mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
				.getMap();

		// set markers on map
		ArrayList<Marker> markers = new ArrayList<Marker>();
		LatLng l;
		Builder bBounds = new LatLngBounds.Builder();
		
		//get bounds for the group of trains and also the add markers on 
		//map for those trains
		for (int k = 0; k < trains.size(); k++) {
			l = trains.get(k).getLatlong();
			if (l.latitude != 0 && l.longitude != 0) {
				bBounds.include(l);
				markers.add(mMap.addMarker(new MarkerOptions().position(l)
						.title("#" + trains.get(k).getTrainNum())));
			}
		}
		if (markers.size() == 0){
			//create bounds for philadelphia septa area
			bBounds.include(new LatLng(39.863371,-75.411728));
			bBounds.include(new LatLng(40.295894,-74.681652));

			Toast.makeText(
					MyMapFrag.this,
					"There are no trains with gps coordinates on this route",
					Toast.LENGTH_LONG).show();
		}
			bounds = bBounds.build();

		mMap.setOnMapLoadedCallback(new OnMapLoadedCallback() {
			@Override
			public void onMapLoaded() {
				mMap.animateCamera(
						CameraUpdateFactory.newLatLngBounds(bounds, 100), 1500,
						null);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.map, menu);
		return true;
	}

}
