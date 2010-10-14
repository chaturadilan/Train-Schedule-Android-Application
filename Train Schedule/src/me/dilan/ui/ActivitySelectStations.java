package me.dilan.ui;

import me.dilan.R;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class ActivitySelectStations extends Activity{
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_stations);
        
        final int lineId = (int) getIntent().getExtras().getInt("lineId");
        Log.v("lineid", String.valueOf(lineId));
    }

}
