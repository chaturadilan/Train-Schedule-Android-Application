package me.dilan.ui;

import java.util.Calendar;

import me.dilan.R;
import me.dilan.obj.Rates;
import me.dilan.obj.TrainSchedules;
import me.dilan.util.Functions;
import me.dilan.webservice.RailwayWebServiceV2;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;



public class ActivityDisplaySchedule extends Activity {	

	GridView mGridViewSchedules;
	TextView mTextViewNoShedule;
	
	String mFromStationCode;
	String mFromStationName;
	String mToStationCode;
	String mToStationName;
	boolean mIsDailySchedule;
	
	TrainSchedules mtrainSchedules;
	Rates rates;

	Handler mWSGetTrainScheduleHandler;

	ProgressDialog mProgressDialog;
	
	public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_schedule);
        
        mGridViewSchedules =  (GridView) findViewById(R.id.display_schedule_gridview_schedules);
        mTextViewNoShedule =  (TextView) findViewById(R.id.display_schedule_no_shedule);
        
        mFromStationCode = getIntent().getExtras().getString("fromStationCode");
        mFromStationName = getIntent().getExtras().getString("fromStationName");
        mToStationCode = getIntent().getExtras().getString("toStationCode");
        mToStationName = getIntent().getExtras().getString("toStationName");
        mIsDailySchedule = getIntent().getExtras().getBoolean("isDailySchedule");
        
        
        mProgressDialog = Functions.getProgressDialog(this, getString(R.string.all_retriving_data));
        new WSGetTrainSchedule().execute(null, null, null);
        
        mWSGetTrainScheduleHandler = new Handler() { 
        	public void handleMessage(Message message) {
        		if(mtrainSchedules.getCount() <= 0){
        			mTextViewNoShedule.setVisibility(View.VISIBLE);
        		}else{
        			mGridViewSchedules.setAdapter(new AdapterTrainSchedule());
        		}
        		
        		mProgressDialog.dismiss(); 
 	        }
        };      
	    
        
	}
	
	
	 public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {  
		 super.onCreateContextMenu(menu, v, menuInfo);  
	     menu.setHeaderTitle("Actions");  
	     menu.add(0, v.getId(), 0, "Ticket Prices"); 
	       
	 } 
	 
	 public boolean onContextItemSelected(MenuItem item) {
		 if(item.getTitle()=="Ticket Prices"){
			 AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
			 
	            StringBuilder pricesAlert = new StringBuilder("");
			 	int i = 0;
	            for(float price: rates.getPrices()){
	            	pricesAlert.append("Class " + ++i + ": Rs. " +  price +"\n");
	            }
			 
	            alertbox.setTitle("Ticket Prices");
	            alertbox.setMessage(pricesAlert);	           
	            alertbox.setNeutralButton("Ok", null); 
	 
	            alertbox.show();  
	            return true;
		 }else{
			 return false;
		 }
		 
	     
	} 
	
	class WSGetTrainSchedule extends AsyncTask<Object, Object, Object>{

		@Override
		protected Object doInBackground(Object... params) {
			try {	  		
				Calendar now = Calendar.getInstance();
				String todayDate = String.format("%1$tY-%1$tm-%1$te", now);
				String todayTime = String.format("%1$tH:%1$tM:%1$tS", now);
				
				if(mIsDailySchedule){					
					mtrainSchedules = RailwayWebServiceV2.getSchedule(mFromStationCode, mToStationCode, "00:00:00", "23:59:59", todayDate, todayTime);
				}else{
					mtrainSchedules = RailwayWebServiceV2.getSchedule(mFromStationCode, mToStationCode, todayTime, "23:59:59", todayDate, todayTime);
				}
				
				rates = RailwayWebServiceV2.getRates(mFromStationCode, mToStationCode);
				
				mWSGetTrainScheduleHandler.sendMessage(mWSGetTrainScheduleHandler.obtainMessage());
					
			        return null;
			} catch (Exception e) {			
					e.printStackTrace();
			}
			return null;
		}    	
    }
	
	public void onBackPressed() {		
		super.onBackPressed();
		finish();
	}
	
	public void onHomeClick(View v) {			
    	startActivity(new Intent(this, ActivityDashboard.class));
    	finish();    
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
			TextView textViewTrainDelay = (TextView) customTrainSchedule.findViewById(R.id.custom_train_schedule_train_delay);
			TextView textViewTrainArrival = (TextView) customTrainSchedule.findViewById(R.id.custom_train_schedule_train_arrival);
			TextView textViewTrainDepature = (TextView) customTrainSchedule.findViewById(R.id.custom_train_schedule_train_depature);
			TextView textViewTrainArrivalAt = (TextView) customTrainSchedule.findViewById(R.id.custom_train_schedule_train_arrival_destination);
			
			textViewTrainName.setText(Functions.capitalizeFirstLetters(mtrainSchedules.getTrainNames()[position] + " - " + mtrainSchedules.getTyDescriptions()[position] + " - " + mtrainSchedules.getFdDescriptions()[position] +" ("+ mtrainSchedules.getStartStationName()[position] + " - "+ mtrainSchedules.getEndStationName()[position] + ")"));
			if(mtrainSchedules.getDelayTimes()[position] != null){
				textViewTrainDelay.setText("Train is delayed for " + mtrainSchedules.getDelayTimes()[position] + ". Comment: " +  mtrainSchedules.getComments()[position]);
			}else{
				textViewTrainDelay.setVisibility(View.GONE);
			}
			
			textViewTrainArrival.setText("Train Arrival At: " + mtrainSchedules.getArrivalTimes()[position]);
			textViewTrainDepature.setText("Train Depature At: " + mtrainSchedules.getDepatureTimes()[position]);
			textViewTrainArrivalAt.setText("Train Arrival At Destination: " + mtrainSchedules.getArrivalAtDestinationTimes()[position]);
			
			ActivityDisplaySchedule.this.registerForContextMenu(layoutTrainSchedule);
									
			return layoutTrainSchedule;
		}

	}

}
