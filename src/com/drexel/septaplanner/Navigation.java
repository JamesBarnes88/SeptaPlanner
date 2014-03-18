package com.drexel.septaplanner;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;


public class Navigation {
	
	//api key for google directions
	final private static String API_KEY="AIzaSyC71ifFUDF03mvjQ-lp31YkZlso9OSxw-U";
	
	//gets time from the directions api in format 0hr 5min, point 1 is start, point2 is end. 
	public static String getTime(LatLng point1, LatLng point2, String travelmode){

		JsonElement jsonE= myData.getJson("https://maps.googleapis.com/maps/api/directions/json?" +
				"origin="+point1.longitude+","+point1.latitude+"&destination="+
				point2.longitude+","+point2.latitude+"&sensor=true&key="+API_KEY+
				"&mode="+travelmode);
		 return timeToMin(parseDirections(jsonE));
	}
	private static double parseDirections(JsonElement jsonE){
		JsonObject json= jsonE.getAsJsonObject();
		JsonArray jsonarray=json.get("routes").getAsJsonArray();
		json= jsonarray.get(0).getAsJsonObject();
		jsonarray= json.get("legs").getAsJsonArray();
		
		double time=0;
		double minTime=0;
		
		if (jsonarray.size()>0)
			minTime=jsonarray.get(0).getAsJsonObject().get("duration").getAsJsonObject().get("value").getAsInt();
		
		for(int i=1; i<jsonarray.size(); i++){
			time= jsonarray.get(i).getAsJsonObject().get("duration").getAsJsonObject().get("value").getAsDouble();
			if (time< minTime)
				minTime=time;
		}
		System.out.println("minTime= "+minTime);
		return minTime;
	}
	
	//convert time from seconds, to a String of readable time for the user
	private static String timeToMin(double time){
		double hour=time/3600;
		int min;
		min=(int) ((hour-(int)hour)*60+.5);
		if ((int)hour==0){
			return Integer.toString(min)+"min";
		}
		else
			return Integer.toString((int)hour)+"hr "+Integer.toString(min)+"min";
		
	}
	
}
