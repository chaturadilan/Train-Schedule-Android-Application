package me.dilan.ui;

import java.util.Calendar;

import me.dilan.R;
import me.dilan.obj.TrainSchedules;
import me.dilan.util.Functions;
import me.dilan.webservice.RailwayWebServiceV2;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;



public class ActivityDisplaySchedule extends Activity {	

	GridView mGridViewSchedules;
	
	String fromStationCode;
	String fromStationName;
	String toStationCode;
	String toStationName;
	boolean isDailySchedule;
	
	TrainSchedules mtrainSchedules;

	Handler mWSGetTrainScheduleHandler;

	ProgressDialog mProgressDialog;
	
	public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_schedule);
        
        mGridViewSchedules =  (GridView) findViewById(R.id.display_schedule_gridview_schedules);
                
        
        fromStationCode = getIntent().getExtras().getString("fromStationCode");
        fromStationName = getIntent().getExtras().getString("fromStationName");
        toStationCode = getIntent().getExtras().getString("toStationCode");
        toStationName = getIntent().getExtras().getString("toStationName");
        isDailySchedule = getIntent().getExtras().getBoolean("isDailySchedule");
        
        
        mProgressDialog = Functions.getProgressDialog(this, getString(R.string.all_retriving_data));
        new WSGetTrainSchedule().execute(null, null, null);
        
        mWSGetTrainScheduleHandler = new Handler() { 
        	public void handleMessage(Message message) {
        		mGridViewSchedules.setAdapter(new AdapterTrainSchedule());
        		mProgressDialog.dismiss();
 	        }
        };      
	    
        
	}
	
	class WSGetTrainSchedule extends AsyncTask<Object, Object, Object>{

		@Override
		protected Object doInBackground(Object... params) {
			try {				
				Calendar now = Calendar.getInstance();
				String todayDate = String.format("%1$tY-%1$tm-%1$te", now);
				String todayTime = String.format("%1$tH:%1$tM:%1$tS", now);
				
				if(isDailySchedule){					
					mtrainSchedules = RailwayWebServiceV2.getSchedule(fromStationCode, toStationCode, "00:00:00", "23:59:59", todayDate, todayTime);
				}else{
					mtrainSchedules = RailwayWebServiceV2.getSchedule(fromStationCode, toStationCode, todayTime, "23:59:59", todayDate, todayTime);
				}			
				
				mWSGetTrainScheduleHandler.sendMessage(mWSGetTrainScheduleHandler.obtainMessage());
					
			        return null;
			} catch (Exception e) {			
					e.printStackTrace();
			}
			return null;
		}    	
    }
	
	
	class AdapterTrainSchedule extends BaseAdapter {		
		
		public int getCount() {			
			return mtrainSchedules.getCount();
		}	
		
		
		public Object getItem(int position) {			
			return null;
		}

		public long getItemId(int position) {			
			return 0;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater layoutInflater = ActivityDisplaySchedule.this.getLayoutInflater();
			View customTrainSchedule = layoutInflater.inflate(R.layout.custom_train_shedule, null);
			LinearLayout layoutTrainSchedule = (LinearLayout) customTrainSchedule.findViewById(R.id.custom_train_schedule_root);
			TextView textViewTrainName = (TextView) customTrainSchedule.findViewById(R.id.custom_train_schedule_train_name);
			TextView textViewTrainArrival = (TextView) customTrainSchedule.findViewById(R.id.custom_train_schedule_train_arrival);
			TextView textViewTrainDepature = (TextView) customTrainSchedule.findViewById(R.id.custom_train_schedule_train_depature);
			TextView textViewTrainArrivalAt = (TextView) customTrainSchedule.findViewById(R.id.custom_train_schedule_train_arrival_destination);
			
			textViewTrainName.setText(Functions.capitalizeFirstLetters(mtrainSchedules.getTrainNames()[position] + " - " + mtrainSchedules.getTyDescriptions()[position] + " - " + mtrainSchedules.getFdDescriptions()[position]));
			textViewTrainArrival.setText("Train Arrival At: " + mtrainSchedules.getArrivalTimes()[position]);
			textViewTrainDepature.setText("Train Depature At: " + mtrainSchedules.getDepatureTimes()[position]);
			textViewTrainArrivalAt.setText("Train Depature At Destination: " + mtrainSchedules.getArrivalAtDestinationTimes()[position]);
			
			return layoutTrainSchedule;
		}

	}

}
