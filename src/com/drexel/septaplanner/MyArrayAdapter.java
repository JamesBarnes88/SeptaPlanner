package com.drexel.septaplanner;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MyArrayAdapter extends ArrayAdapter<Forecast> {

	private Context context;
	private int resources;
	String tag="myweatherapp";
	
	public MyArrayAdapter(Context context, int resources) {
		super(context, resources);
		this.context = context;
		this.resources = resources;
		Log.d(tag, "adapter created");
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View itemView = inflater.inflate(resources, parent, false);
		TextView titleView = (TextView) itemView.findViewById(R.id.title);
		TextView sumView = (TextView) itemView.findViewById(R.id.summary);
		titleView.setText(super.getItem(position).getTitle());
		sumView.setText(super.getItem(position).getFctext());

		return itemView;
	}

}
