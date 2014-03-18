package com.drexel.septaplanner;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Septa {

	public Septa() {
		// do nothing
	}

	public septaTrain[] getNTA(String stationFrom, String stationTo) {
		String URL = "http://www3.septa.org/hackathon/NextToArrive/"
				+ myData.encodeUrl(stationFrom) + "/"
				+ myData.encodeUrl(stationTo) + "/200";
		String trainviewurl = "http://www3.septa.org/hackathon/TrainView/";

		JsonElement json = myData.getJson(URL);
		JsonElement trainviewjson = myData.getJson(trainviewurl);

		return parseNTA(json, trainviewjson, stationTo);
	}
	public static JsonElement getJson(String stationFrom, String stationTo) {
		String URL = "http://www3.septa.org/hackathon/NextToArrive/"
				+ myData.encodeUrl(stationFrom) + "/"
				+ myData.encodeUrl(stationTo) + "/200";
		String trainviewurl = "http://www3.septa.org/hackathon/TrainView/";

		JsonElement json = myData.getJson(URL);
		JsonElement trainviewjson = myData.getJson(trainviewurl);

		return json;
	}

	public String[] getTrainSchedule(String train) {
		String URL = "http://www3.septa.org/hackathon/RRSchedules/" + train;

		JsonElement json = myData.getJson(URL);

		return parseSchedule(json);
	}

	private String[] parseSchedule(JsonElement json) {

		JsonObject jsonOb = new JsonObject();
		JsonArray jsonarray = json.getAsJsonArray();
		String[] result = new String[3];
		result[0]=null;
		result[1]=null;
		result[2]=null;
		
		for (int i = 0; i < jsonarray.size(); i++) {
			jsonOb = jsonarray.get(i).getAsJsonObject();
			if (!jsonOb.get("act_tm").toString().replaceAll("\"", "")
					.equals("na")) {
				result[0] = jsonOb.get("station").toString()
						.replaceAll("\"", "");
				result[1] = jsonOb.get("sched_tm").toString()
						.replaceAll("\"", "");
				result[2] = jsonOb.get("act_tm").toString()
						.replaceAll("\"", "");
			}
			else 
				break;
		}
		return result;
	}

	private septaTrain[] parseNTA(JsonElement jsone, JsonElement trainviewJson,
			String stationTo) {
		// objects for NTA json
		JsonArray jsonarray = jsone.getAsJsonArray();
		JsonObject json = new JsonObject();

		// objects for Trainview json
		JsonArray jsontrainview = trainviewJson.getAsJsonArray();
		JsonObject trainjson = new JsonObject();

		// class object to be returned
		septaTrain[] trains = new septaTrain[jsonarray.size()];

		// temp string
		String temp;
		String temptrainno;
		// add from NTA json
		for (int i = 0; i < jsonarray.size(); i++) {
			json = jsonarray.get(i).getAsJsonObject();
			trains[i] = new septaTrain();
			trains[i].setArrive(json.get("orig_arrival_time").toString()
					.replaceAll("\"", ""));
			trains[i].setDepart(json.get("orig_departure_time").toString()
					.replaceAll("\"", ""));
			trains[i].setOrigin_line(json.get("orig_line").toString()
					.replaceAll("\"", ""));
			trains[i].setTrainNum(json.get("orig_train").toString()
					.replaceAll("\"", ""));
			trains[i].setDelay(json.get("orig_delay").toString()
					.replaceAll("\"", ""));
			temp = json.get("orig_train").toString().replaceAll("\"", "");

			// get lat long values from trainview json and destination value
			// also
			for (int j = 0; j < jsontrainview.size(); j++) {
				trainjson = jsontrainview.get(j).getAsJsonObject();

				if (trainjson.get("trainno").toString().replaceAll("\"", "")
						.equals(temp)) {
					double longitude = Double.valueOf(trainjson.get("lon")
							.toString().replaceAll("\"", ""));
					double latitude = Double.valueOf(trainjson.get("lat")
							.toString().replaceAll("\"", ""));
					temptrainno = trainjson.get("dest").toString()
							.replaceAll("\"", "");
					trains[i].setLatlong(new LatLng(latitude, longitude));
					trains[i].setDestination(temptrainno);
					break;
				} else {
					trains[i].setDestination(stationTo);
					trains[i].setLatlong(0, 0);
				}
			}
		}
		return trains;
	}

}
