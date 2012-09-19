/**
* @copyright	Copyright (C) 2010 - 2010 Chatura Dilan Perera
* @license		GNU/GPL
* This Application is released on behalf of ICTA (Information and Communication Technology Agency of Sri Lanka)
* to the public under the GNU General Public License
*/

package lk.icta.mobile.apps.railway.ui;

import java.util.Calendar;

import lk.icta.mobile.apps.railway.R;
import lk.icta.mobile.apps.railway.obj.Delays;
import lk.icta.mobile.apps.railway.util.Functions;
import lk.icta.mobile.apps.railway.webservice.RailwayWebServiceV2;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ActivityDisplayTrainDelays extends Activity {
	
	GridView mListViewDelays;
	TextView mTextViewNoDelays;
	ProgressDialog mProgressDialog;
	Handler mWSGetTrainDelaysHandler;
	Delays mDelays;
	
	public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_train_delays);
        
        mListViewDelays = (GridView) findViewById(R.id.dispaly_train_delays_gridview_delays);
        mTextViewNoDelays =  (TextView) findViewById(R.id.dispaly_train_delays_no_delays);
        
        mProgressDialog = Functions.getProgressDialog(this, getString(R.string.all_retriving_data));
        new WSGetTrainDelays().execute(null,null,null);
        
        mWSGetTrainDelaysHandler = new Handler() { 
         	public void handleMessage(Message message) { 
         		if(mDelays.getCount() <= 0){
         			mTextViewNoDelays.setVisibility(View.VISIBLE);
        		}else{
        			mListViewDelays.setAdapter(new AdapterTrainDelay());
        		}        		
    				
    				mProgressDialog.dismiss();
    	        }
         };
	}
	
	
	class WSGetTrainDelays extends AsyncTask<Object, Object, Object>{

		@Override
		protected Object doInBackground(Object... params) {
				try {				
					Calendar now = Calendar.getInstance();
					String todayDate = String.format("%1$tY-%1$tm-%1$td", now);
					String todayTime = String.format("%1$tH:%1$tM:%1$tS", now);						
					mDelays = RailwayWebServiceV2.getDelays(todayDate, todayTime);
					mWSGetTrainDelaysHandler.sendMessage(mWSGetTrainDelaysHandler.obtainMessage());
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
	
	
class AdapterTrainDelay extends BaseAdapter {		
		
		public int getCount() {			
			return mDelays.getCount();
		}	
		
		
		public Object getItem(int position) {			
			return null;
		}

		public long getItemId(int position) {			
			return 0;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater layoutInflater = ActivityDisplayTrainDelays.this.getLayoutInflater();
			View customTrainDelay = layoutInflater.inflate(R.layout.custom_train_delay, null);
			LinearLayout layoutTrainSchedule = (LinearLayout) customTrainDelay.findViewById(R.id.custom_train_delay_root);
			TextView textViewTrainName = (TextView) customTrainDelay.findViewById(R.id.custom_train_delay_train_name);
			TextView textViewTrainDelay = (TextView) customTrainDelay.findViewById(R.id.custom_train_delay_train_delay);
						
			textViewTrainName.setText(mDelays.getTrainNo()[position] + " (" + mDelays.getFromStationName()[position] + " - " + mDelays.getToStationName()[position] + ")");
			textViewTrainDelay.setText(getString(R.string.display_delays_train_delayed_for) + mDelays.getDelayTime()[position] + ".");
			if(mDelays.getComments()[position] != null){
				textViewTrainDelay.setText(textViewTrainDelay.getText() + getString(R.string.display_delays_train_comments) + mDelays.getComments()[position]);
			}
			                                            
			return layoutTrainSchedule;
		}

	}

}
