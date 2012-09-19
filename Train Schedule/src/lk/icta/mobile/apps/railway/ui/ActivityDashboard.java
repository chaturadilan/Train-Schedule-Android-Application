/**
* @copyright	Copyright (C) 2010 - 2010 Chatura Dilan Perera
* @license		GNU/GPL
* This Application is released on behalf of ICTA (Information and Communication Technology Agency of Sri Lanka)
* to the public under the GNU General Public License
*/


package lk.icta.mobile.apps.railway.ui;

import lk.icta.mobile.apps.railway.R;
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
    
    public void onHomeClick(View v) {			
    	startActivity(new Intent(this, ActivityDashboard.class)); 
    	finish();     
	}
       

}