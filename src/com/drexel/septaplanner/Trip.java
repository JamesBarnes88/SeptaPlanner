package com.drexel.septaplanner;

import java.util.ArrayList;

import org.json.JSONException;

import android.os.Parcel;
import android.os.Parcelable;

import com.cloudmine.api.CMObject;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.JsonArray;

/**
 * This class is used to keep trip data. A trip is what we will use to query the
 * google directions api and the septa api the directions will be from the
 * longitude and latitude from this trip, to the station location of
 * sourceStation
 * 
 * @author buckyb
 * 
 */
public class Trip extends CMObject implements Parcelable {
	public static final String CLASS_NAME = "Trip";

	String methodOfTravel;
	String sourceStation;
	String destStation;

	// the origin when the user is building the trip
	LatLng originLocation;
	LatLng destinationLocation;

	String arrivalTime;

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
			String destStation, String arrivalTime, LatLng originLocation,
			LatLng destinLocation, int flag) {
		this();
		this.methodOfTravel = methodOftravel;
		this.sourceStation = sourceStation;
		this.destStation = destStation;
		this.arrivalTime = arrivalTime;
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

	public String getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	@Override
	public String toString() {
		return "Trip [methodOfTravel=" + methodOfTravel + ", sourceStation="
				+ sourceStation + ", destStation=" + destStation
				+ ", origindest=" + originLocation.toString()
				+ ", destinationLocation=" + destinationLocation.toString()
				+ ", arrivalTime=" + arrivalTime + "]";
	}

	
	/** can only be called from asyncTask
	 * @param trip
	 * @return
	 */
	public static ArrayList<Trip> getTrips(Trip trip) {
		ArrayList<Trip> trips = new ArrayList<Trip>();
		// get trains that arrive before arrival time from trip
		String navTime = Navigation.getTime(trip.getOriginLatlng(),
				trip.getDestinationLatlng(), trip.getMethodOfTravel());
		ArrayList<Train> trains;
		try {
			trains = Train.getTrips(trip.getSourceStation(), trip.getDestStation(), trip.getArrivalTime());
			Trip tempTrip = null;
			for (int i = 0; i < trains.size(); i++) {
				tempTrip = new Trip(tempTrip.getMethodOfTravel(),
						tempTrip.getSourceStation(), tempTrip.getDestStation(),
						trains.get(i).arriveTime, tempTrip.getOriginLatlng(),
						tempTrip.getDestinationLatlng(), 0);
				tempTrip.setTimeLeft(navTime);
				
				trips.add(tempTrip);
				System.out.println(tempTrip.toString());
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		dest.writeString(this.arrivalTime);
	}

	public static final Parcelable.Creator<Trip> CREATOR = new Creator<Trip>() {

		public Trip createFromParcel(Parcel source) {

			Trip trip = new Trip();
			trip.setMethodOfTravel(source.readString());
			trip.setSourceStation(source.readString());
			trip.setDestStation(source.readString());
			trip.setTimeLeft(source.readString());
			trip.setOriginLatlng(new LatLng(source.readDouble(), source
					.readDouble()));
			trip.setOriginLatlng(new LatLng(source.readDouble(), source
					.readDouble()));
			trip.setArrivalTime(source.readString());

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
