package com.drexel.septaplanner;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MyArrayAdapter extends ArrayAdapter<septaTrain> {

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
		//get views
		TextView originView = (TextView) itemView.findViewById(R.id.origin);
		TextView destView = (TextView) itemView.findViewById(R.id.dest);
		TextView departView = (TextView) itemView.findViewById(R.id.depart);
		TextView arriveView = (TextView) itemView.findViewById(R.id.arrive);
		TextView trainView = (TextView) itemView.findViewById(R.id.train);
		
		septaTrain sTrain= super.getItem(position);
		//init views
		originView.setText(sTrain.getOrigin_line());
		destView.setText(sTrain.getDestination());
		departView.setText(sTrain.getDepart());
		arriveView.setText(sTrain.getArrive());
		trainView.setText(sTrain.getTrainNum());

		return itemView;
	}

}
