package com.drexel.septaplanner;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TripAdapter extends ArrayAdapter<Trip> {

	private Context context;
	private int resources;
	String tag = "myweatherapp";

	public TripAdapter(Context context, int resources) {
		super(context, resources);
		this.context = context;
		this.resources = resources;
		Log.d(tag, "adapter created");
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View itemView = inflater.inflate(resources, parent, false);
		// get views
		ImageView imageView = (ImageView) itemView
				.findViewById(R.id.item_trips_method_image);
		TextView sourceView = (TextView) itemView
				.findViewById(R.id.item_trips_list_item_source_station);
		TextView destView = (TextView) itemView
				.findViewById(R.id.item_trips_list_item_dest_station);
		TextView timeLeftView = (TextView) itemView
				.findViewById(R.id.item_trips_time_left);

		Trip trip = super.getItem(position);
		// init views

		sourceView.setText(trip.getSourceStation());
		destView.setText(trip.getDestStation());
		timeLeftView.setText(trip.getTimeLeft());
		String method = trip.getMethodOfTravel();
		if (method.equals("walking"))
			imageView.setImageBitmap(getBitmap("walking.bmp"));
		if (method.equals("driving"))
			imageView.setImageBitmap(getBitmap("driving.bmp"));
		if (method.equals("bicycling"))
			imageView.setImageBitmap(getBitmap("bicycling.bmp"));

		return itemView;
	}
	
	/** just returns the bitmap for the specified file. not sure how to implement
	 * @param file
	 * @return
	 */
	protected Bitmap getBitMap(String file){
		Bitmap bitmap=null;
		
		return bitmap;
	}

}
