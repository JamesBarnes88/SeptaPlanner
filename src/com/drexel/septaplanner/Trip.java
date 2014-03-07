package com.drexel.septaplanner;

/**
 * This class is used to keep trip data. A trip is what we will use to query the
 * google directions api and the septa api the directions will be from the
 * longitude and latitude from this trip, to the station location of
 * sourceStation
 * 
 * @author buckyb
 * 
 */
public class Trip {

	String methodOfTravel;
	String sourceStation;
	String destStation;

	// the origin when the user is building the trip
	double latitude;
	double longitude;

	int arrivalHr;
	int arrivalMin;

	// should probably change this to an int, but we will deal with it when it
	// comes
	String timeLeft = "0:00";

	/**
	 * @param methodOftravel
	 *            Method of travel to source dest, walking, driving, bicycling
	 * @param sourceStation
	 *            Station to travel to
	 * @param destStation
	 *            Station to end at
	 * @param longitude
	 *            Longitude from gps
	 * @param latitude
	 *            Latitude from gps
	 * @param flag
	 *            flag to see if trip is reversed or not, -1 for reversed, 1 for
	 *            regular
	 */
	public Trip(String methodOftravel, String sourceStation,
			String destStation, int arrivalHr, int arrivalMin,
			double longitude, double latitude, int flag) {
		this.methodOfTravel = methodOftravel;
		this.sourceStation = sourceStation;
		this.destStation = destStation;
		this.arrivalHr = arrivalHr;
		this.arrivalMin = arrivalMin;
		this.longitude = longitude;
		this.latitude = latitude;

	}

	public String getTimeLeft() {
		return timeLeft;
	}

	public void setTimeLeft(String timeLeft) {
		this.timeLeft = timeLeft;
	}

	public String getMethodOfTravel() {
		return methodOfTravel;
	}

	public void setMethodOfTravel(String methodOfTravel) {
		this.methodOfTravel = methodOfTravel;
	}

	public String getSourceStation() {
		return sourceStation;
	}

	public void setSourceStation(String sourceStation) {
		this.sourceStation = sourceStation;
	}

	public String getDestStation() {
		return destStation;
	}

	public void setDestStation(String destStation) {
		this.destStation = destStation;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	@Override
	public String toString() {
		return "Trip [methodOfTravel=" + methodOfTravel + ", sourceStation="
				+ sourceStation + ", destStation=" + destStation
				+ ", latitude=" + latitude + ", longitude=" + longitude
				+ ", arrivalHr=" + arrivalHr + ", arrivalMin=" + arrivalMin
				+ "]";
	}

}
