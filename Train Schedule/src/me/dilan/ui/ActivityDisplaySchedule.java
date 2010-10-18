package me.dilan.ui;

import me.dilan.R;
import me.dilan.obj.TrainStations;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;



public class ActivityDisplaySchedule extends Activity {	

	String fromStationCode;
	String toStationCode;
	boolean isDailySchedule;
	
	public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_schedule);
        
        TrainStations trainStations = getIntent().getExtras().getParcelable("stations");
        Log.v("from to", trainStations.getNames()[0]);
	}

}
