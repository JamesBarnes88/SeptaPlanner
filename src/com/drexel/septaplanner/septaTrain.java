package com.drexel.septaplanner;

public class septaTrain {
	//train and location data
	String trainNum;
	String latitude;
	String longitude;
	//times
	String depart;
	String arrive;
	String delay;

	String origin_line;
	String destination;

	//not sure if this is right yet. 
	String lastStation;
	String lastScheduledDepart;
	String lastActualDepart;

	public septaTrain() {
		// do nothing
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getDelay() {
		return delay;
	}

	public void setDelay(String delay) {
		this.delay = delay;
	}

	public String getOrigin_line() {
		return origin_line;
	}

	public void setOrigin_line(String origin_line) {
		this.origin_line = origin_line;
	}

	public String getTrainNum() {
		return trainNum;
	}

	public void setTrainNum(String trainNum) {
		this.trainNum = trainNum;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getDepart() {
		return depart;
	}

	public void setDepart(String depart) {
		this.depart = depart;
	}

	public String getArrive() {
		return arrive;
	}

	public void setArrive(String arrive) {
		this.arrive = arrive;
	}

	public String getLastStation() {
		return lastStation;
	}

	public void setLastStation(String lastStation) {
		this.lastStation = lastStation;
	}

	public String getLastScheduledDepart() {
		return lastScheduledDepart;
	}

	public void setLastScheduledDepart(String lastScheduledDepart) {
		this.lastScheduledDepart = lastScheduledDepart;
	}

	public String getLastActualDepart() {
		return lastActualDepart;
	}

	public void setLastActualDepart(String lastActualDepart) {
		this.lastActualDepart = lastActualDepart;
	}
	
	@Override
	public String toString() {
		return "septaTrain [trainNum=" + trainNum + ", depart=" + depart
				+ ", arrive=" + arrive + ", origin_line=" + origin_line + "]";
	}

}
