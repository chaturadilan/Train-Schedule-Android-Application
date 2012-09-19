/**
* @copyright	Copyright (C) 2010 - 2010 Chatura Dilan Perera
* @license		GNU/GPL
* This Application is released on behalf of ICTA (Information and Communication Technology Agency of Sri Lanka)
* to the public under the GNU General Public License
*/

package lk.icta.mobile.apps.railway.ui;


import lk.icta.mobile.apps.railway.R;
import lk.icta.mobile.apps.railway.obj.TrainStations;
import lk.icta.mobile.apps.railway.util.Functions;
import lk.icta.mobile.apps.railway.webservice.RailwayWebServiceV2;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class ActivitySelectStations extends Activity{
	
	Spinner mSpinnerFromStation;
	Spinner mSpinnerToStation;
	Handler mWSGetTrainStationsHandler;
	TrainStations mTrainStations;
	ProgressDialog mProgressDialog;
	
	int lineId;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_stations);
        
        mSpinnerFromStation = (Spinner) findViewById(R.id.select_stations_spinner_from_station);
        mSpinnerToStation = (Spinner) findViewById(R.id.select_stations_spinner_to_station);
        
        
        lineId = (int) getIntent().getExtras().getInt("lineId");
        Log.v("lineid", String.valueOf(lineId));
        
        mProgressDialog = Functions.getProgressDialog(this, getString(R.string.all_retriving_data));
        new WSGetTrainStations().execute(null, null, null);
        
        mWSGetTrainStationsHandler = new Handler() {  
        	public void handleMessage(Message message) {
        		ArrayAdapter<String> stationsAdapter = new ArrayAdapter<String>(ActivitySelectStations.this, android.R.layout.simple_spinner_item, mTrainStations.getNames());
        		mSpinnerFromStation.setAdapter(stationsAdapter);
        		mSpinnerToStation.setAdapter(stationsAdapter);
        		mProgressDialog.dismiss();
 	        }
        };
    }
    
    public void onBtnDailyScheduleClick(View v){    	
    	onBtnSceduleClick(true);
    }
    
    public void onBtnNextScheduleClick(View v){
    	onBtnSceduleClick(false);
    }
    
    public void onHomeClick(View v) {			
    	startActivity(new Intent(this, ActivityDashboard.class));
    	finish();    
	}
    
    private void onBtnSceduleClick(boolean isDailySchedule){    	
    	Log.v("fromS", mTrainStations.getCodes()[mSpinnerFromStation.getSelectedItemPosition()]);
    	Intent nextScreen = new Intent(this, ActivityDisplaySchedule.class);
		nextScreen.putExtra("fromStationCode", mTrainStations.getCodes()[(int)mSpinnerFromStation.getSelectedItemId()]);
		nextScreen.putExtra("fromStationName", mTrainStations.getNames()[(int)mSpinnerFromStation.getSelectedItemId()]);
		nextScreen.putExtra("toStationCode", mTrainStations.getCodes()[(int)mSpinnerToStation.getSelectedItemId()]);
		nextScreen.putExtra("toStationName", mTrainStations.getNames()[(int)mSpinnerToStation.getSelectedItemId()]);
		nextScreen.putExtra("isDailySchedule", isDailySchedule);
		startActivity(nextScreen);		
	}
    
    public void onBackPressed() {		
		super.onBackPressed();
		finish();
	}
    
    
    class WSGetTrainStations extends AsyncTask<Object, Object, Object>{

		@Override
		protected Object doInBackground(Object... params) {
			try {				
					mTrainStations = RailwayWebServiceV2.getTrainStations(lineId);
					mWSGetTrainStationsHandler.sendMessage(mWSGetTrainStationsHandler.obtainMessage());
					
			        return null;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			return null;
		}    	
    } 

}
