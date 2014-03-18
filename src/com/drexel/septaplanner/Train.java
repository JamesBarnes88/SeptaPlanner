package com.drexel.septaplanner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Train {

	String departTime, arriveTime, origTrainNo, termTrainNo;

	public Train() {
	}

	public Train(String setDepartTime, String setArriveTime,
			String setOrigTrainNo, String setTermTrainNo) {
		departTime = setDepartTime;
		arriveTime = setArriveTime;
		origTrainNo = setOrigTrainNo;
		termTrainNo = setTermTrainNo;
	}

	public Train(String setDepartTime, String setArriveTime,
			String setOrigTrainNo) {
		departTime = setDepartTime;
		arriveTime = setArriveTime;
		origTrainNo = setOrigTrainNo;

	}

	public void setArriveTime(JSONObject train) throws JSONException {
		if (train.has("term_arrival_time")) {
			arriveTime = train.getString("term_arrival_time");
		} else
			arriveTime = train.getString("orig_arrival_time");
	}

	public void setDepartTime(JSONObject train) throws JSONException {
		departTime = train.getString("orig_departure_time");
	}

	public void setOrigTrainNo(JSONObject train) throws JSONException {
		origTrainNo = train.getString("orig_train");
	}

	public void setTermTrainNo(JSONObject train) throws JSONException {
		if (train.has("term_train")) {
			termTrainNo = train.getString("Term_train");
		}
		else termTrainNo =null;

	}

	public static String JSON(String s) {
		URL url;
		StringBuilder sb = null;
		try {
			url = new URL(s);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			sb = new StringBuilder();
			String line;
			while ((line = rd.readLine()) != null) {
				sb.append(line);
			}
		} catch (MalformedURLException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		return sb.toString();
	}

	public static int minTime(String event1, String event2) {
		Map<String, String> time1 = new HashMap<String, String>();

		time1.put("hour", (event1.substring(0, 2).replace(" ", "0")));
		time1.put("min", (event1.substring(3, 5)));
		time1.put("part", (event1.substring(5, 7)));

		Map<String, String> time2 = new HashMap<String, String>();

		time2.put("hour", (event2.substring(0, 2).replace(" ", "0")));
		time2.put("min", (event2.substring(3, 5)));
		time2.put("part", (event2.substring(5, 7)));

		if ((time1.get("part")).compareTo(time2.get("part")) < 0) {
			return 0;
		} else if (time1.get("part").compareTo(time2.get("part")) > 0) {
			return 1;
		}
		if (Integer.parseInt(time1.get("hour")) < Integer.parseInt(time2
				.get("hour"))) {
			return 0;
		} else if (Integer.parseInt(time1.get("hour")) < Integer.parseInt(time2
				.get("hour"))) {
			return 1;
		}
		if (Integer.parseInt(time1.get("min")) < Integer.parseInt(time2
				.get("min"))) {
			return 0;
		} else if (Integer.parseInt(time1.get("min")) < Integer.parseInt(time2
				.get("min"))) {
			return 1;
		} else
			return 1;
	}

	public static ArrayList<Train> getTrips(String sourceStation, String destStation, String arivalTime)
			throws JSONException {
		JSONArray trains= new JSONArray(JSON("http://www3.septa.org/hackathon/NextToArrive/"
				+ myData.encodeUrl(sourceStation) + "/"
				+ myData.encodeUrl(destStation) + "/200"));
		ArrayList<Train> trips = new ArrayList<Train>();
		String time;

		for (int i = 0; i < trains.length(); i++) {

			if (trains.getJSONObject(i).has("term_arrival_time")) {
				time = (trains.getJSONObject(i).getString("term_arrival_time"));
			} else
				time = (trains.getJSONObject(i).getString("orig_arrival_time"));

			if (minTime(time, arivalTime) == 0) {
				Train t = new Train();
				t.setDepartTime(trains.getJSONObject(i));
				t.setArriveTime(trains.getJSONObject(i));
				t.setOrigTrainNo(trains.getJSONObject(i));
				t.setTermTrainNo(trains.getJSONObject(i));
				trips.add(t);
			} else
				break;
		}

		return trips;

	}

	public static void main(String[] args) throws JSONException {

		JSONArray trains = new JSONArray(
				JSON("http://www3.septa.org/hackathon/NextToArrive/Airport%20Terminal%20B/Ardmore/255"));
		// System.out.println(trains);
		// System.out.println(getTrips(trains, "11:00PM"));

		JSONArray stations = new JSONArray(
				JSON("http://www3.septa.org/hackathon/locations/get_locations.php?lon=-75.183266&lat=39.954836&radius=500&type=rail_stations"));
		System.out.println(stations.length());

	}
}
