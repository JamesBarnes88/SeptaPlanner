package com.drexel.septaplanner;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

public class septaTrain implements Parcelable {
	// train and location data
	String trainNum;
	LatLng latlong;

	// times
	String depart;
	String arrive;
	String delay;

	String origin_line;
	String destination;

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

	public LatLng getLatlong() {
		return latlong;
	}

	public void setLatlong(LatLng latlong) {
		this.latlong = latlong;
	}
	public void setLatlong(double latitude, double longitude) {
		this.latlong = new LatLng(latitude, longitude);
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



	@Override
	public String toString() {
		return "septaTrain [trainNum=" + trainNum + ", latlong=" + latlong
				+ "]";
	}


	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.trainNum);
		dest.writeString(this.depart);
		dest.writeString(this.arrive);
		dest.writeString(this.delay);
		dest.writeString(this.origin_line);
		dest.writeString(this.destination);
		dest.writeDouble(this.latlong.latitude);
		dest.writeDouble(this.latlong.longitude);
		
	}
	
	public static final Parcelable.Creator<septaTrain> CREATOR = new Creator<septaTrain>() {

		public septaTrain createFromParcel(Parcel source) {

			septaTrain mTrain = new septaTrain();
			mTrain.setTrainNum(source.readString());
			mTrain.setDepart(source.readString());
			mTrain.setArrive(source.readString());
			mTrain.setDelay(source.readString());
			mTrain.setOrigin_line(source.readString());
			mTrain.setDestination(source.readString());
			mTrain.setLatlong(source.readDouble(), source.readDouble());

			return mTrain;
		}

		public septaTrain[] newArray(int size) {

			return new septaTrain[size];
		}

	};
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

}
