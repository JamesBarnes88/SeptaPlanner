package com.drexel.septaplanner;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;


public class Septa {
	
	
	public Septa(){
		//do nothing
	}
	public septaTrain[] getNTA(String stationFrom, String stationTo){
		String URL= "http://www3.septa.org/hackathon/NextToArrive/"+myData.encodeUrl(stationFrom)+"/"+myData.encodeUrl(stationTo)+"/200";
		JsonElement json= myData.getJson(URL);
		return parseNTA(json, stationTo);
	}
	
	private septaTrain[] parseNTA(JsonElement jsone, String stationTo){
		JsonArray jsonarray= jsone.getAsJsonArray();
		JsonObject json= new JsonObject();
		septaTrain[] trains= new septaTrain[jsonarray.size()];
		for (int i=0; i< jsonarray.size(); i++){
			json=jsonarray.get(i).getAsJsonObject();
			trains[i]= new septaTrain();
			trains[i].setArrive(json.get("orig_arrival_time").toString().replaceAll("\"", ""));
			trains[i].setDepart(json.get("orig_departure_time").toString().replaceAll("\"", ""));
			trains[i].setDestination(stationTo);
			trains[i].setOrigin_line(json.get("orig_line").toString().replaceAll("\"", ""));
			trains[i].setTrainNum(json.get("orig_train").toString().replaceAll("\"", ""));
			
			
		}
		//parsing code
		return trains;
	}
	

}
