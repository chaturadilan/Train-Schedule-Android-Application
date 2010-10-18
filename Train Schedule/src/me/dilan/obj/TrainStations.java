package me.dilan.obj;

import android.os.Parcel;
import android.os.Parcelable;

public final class TrainStations implements Parcelable {
	private String[] codes;
	private String[] names;
	
	public String[] getCodes() {
		return codes;
	}
	public void setCodes(String[] codes) {
		this.codes = codes;
	}
	public String[] getNames() {
		return names;
	}
	public void setNames(String[] names) {
		this.names = names;
	}
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeArray(codes);
		dest.writeArray(names);
	}
	
	private void readFromParcel(Parcel in) {		
		//codes = in.readStringArray();
		//in.readStringArray(names);
	}
	
	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public TrainStations createFromParcel(Parcel in) {
            return new TrainStations(in);
        }
		public TrainStations[] newArray(int size) {			
			return null;
		}       
    };
    
    public TrainStations(Parcel in) {
		readFromParcel(in);
	}
	public TrainStations() {
		// TODO Auto-generated constructor stub
	}



	
}
