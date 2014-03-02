package com.drexel.septaplanner;
import com.drexel.septaplanner.myData;
import com.google.gson.JsonObject;


public class Septa {
	
	
	public Septa(){
		//do nothing
	}
	public void getNTA(String stationFrom, String stationTo){
		String URL= "http://www3.septa.org/hackathon/NextToArrive/"+myData.encodeUrl(stationFrom)+"/"+myData.encodeUrl(stationTo)+"/200";
		JsonObject json= myData.getJson(URL);
		parseNTA(json);
	}
	
	private JsonObject parseNTA(JsonObject json){
		
		//parsing code
		return null;
	}
	

}
