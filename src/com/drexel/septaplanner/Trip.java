package com.drexel.septaplanner;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

import com.cloudmine.api.CMObject;
import com.google.android.gms.maps.model.LatLng;

/**
 * This class is used to keep trip data. A trip is what we will use to query the
 * google directions api and the septa api the directions will be from the
 * longitude and latitude from this trip, to the station location of
 * sourceStation
 * 
 * @author buckyb
 * 
 */
public class Trip extends CMObject implements Parcelable{
	public static final String CLASS_NAME = "Trip";

	String methodOfTravel;
	String sourceStation;
	String destStation;

	// the origin when the user is building the trip
	LatLng originLocation;
	LatLng destinationLocation;

	int arrivalHr;
	int arrivalMin;

	// should probably change this to an int, but we will deal with it when it
	// comes
	String timeLeft = "0:00";

	public Trip() {
		super();
	}

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
			LatLng originLocation, LatLng destinLocation, int flag) {
		this();
		this.methodOfTravel = methodOftravel;
		this.sourceStation = sourceStation;
		this.destStation = destStation;
		this.arrivalHr = arrivalHr;
		this.arrivalMin = arrivalMin;
		this.originLocation = originLocation;
		this.destinationLocation = destinLocation;

	}

	public String getClassName() {
		return CLASS_NAME;
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

	public LatLng getOriginLatlng() {
		return originLocation;
	}

	public void setOriginLatlng(LatLng originLocation) {
		this.originLocation = originLocation;
	}

	public LatLng getDestinationLatlng() {
		return destinationLocation;
	}

	public void setDestinationLatlng(LatLng destinationLocation) {
		this.destinationLocation = destinationLocation;
	}

	public int getArrivalHr() {
		return arrivalHr;
	}

	public void setArrivalHr(int arrivalHr) {
		this.arrivalHr = arrivalHr;
	}

	public int getArrivalMin() {
		return arrivalMin;
	}

	public void setArrivalMin(int arrivalMin) {
		this.arrivalMin = arrivalMin;
	}

	@Override
	public String toString() {
		return "Trip [methodOfTravel=" + methodOfTravel + ", sourceStation="
				+ sourceStation + ", destStation=" + destStation
				+ ", origindest=" + originLocation.toString()
				+ ", destinationLocation=" + destinationLocation.toString()
				+ ", arrivalHr=" + arrivalHr + ", arrivalMin=" + arrivalMin
				+ "]";
	}

	public static ArrayList<Trip> getTrips(Trip trip) {
		ArrayList<Trip> trips = new ArrayList<Trip>();
		// get trains that arrive before arrival time from trip
		int hour = trip.getArrivalHr();
		String time;
		String navTime = Navigation.getTime(trip.getOriginLatlng(),
				trip.getDestinationLatlng(), trip.getMethodOfTravel());
		septaTrain[] trains = null;

		if (hour < 12)
			time = Integer.toString(trip.getArrivalHr()) + ":"
					+ Integer.toString(trip.getArrivalMin()) + "AM";
		else
			time = Integer.toString(trip.getArrivalHr() - 12) + ":"
					+ Integer.toString(trip.getArrivalMin()) + "PM";

		// trains[] trains= getTrains(time);
		for (int i = 0; i < trains.length; i++) {
			
		}
		return trips;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.methodOfTravel);
		dest.writeString(this.sourceStation);
		dest.writeString(this.destStation);
		dest.writeString(this.timeLeft);
		dest.writeDouble(this.originLocation.longitude);
		dest.writeDouble(this.originLocation.latitude);
		dest.writeDouble(this.destinationLocation.longitude);
		dest.writeDouble(this.destinationLocation.latitude);
		dest.writeInt(this.arrivalHr);
		dest.writeInt(this.arrivalMin);
	}

	public static final Parcelable.Creator<Trip> CREATOR = new Creator<Trip>() {

		public Trip createFromParcel(Parcel source) {

			Trip trip = new Trip();
			trip.setMethodOfTravel(source.readString());
			trip.setSourceStation(source.readString());
			trip.setDestStation(source.readString());
			trip.setTimeLeft(source.readString());
			trip.setOriginLatlng(new LatLng(source.readDouble(), source.readDouble()));
			trip.setOriginLatlng(new LatLng(source.readDouble(), source.readDouble()));
			trip.setArrivalHr(source.readInt());
			trip.setArrivalMin(source.readInt());

			return trip;
		}

		public Trip[] newArray(int size) {

			return new Trip[size];
		}

	};

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

}
