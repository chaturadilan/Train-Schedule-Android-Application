package me.dilan.ui;

import me.dilan.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ActivityDashboard extends Activity {    
	
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);       
    }
    
    public void onBtnTrainScheduleClick(View v){
    	startActivity(new Intent(this, ActivitySelectTrainLines.class));    	
    }
    
    public void onBtnTrainDelayClick(View v){
    	startActivity(new Intent(this, ActivityDisplayTrainDelays.class));
    }
    

}