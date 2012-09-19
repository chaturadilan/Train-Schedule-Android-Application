/**
* @copyright	Copyright (C) 2010 - 2010 Chatura Dilan Perera
* @license		GNU/GPL
* This Application is released on behalf of ICTA (Information and Communication Technology Agency of Sri Lanka)
* to the public under the GNU General Public License
*/

package lk.icta.mobile.apps.railway.ui;

import lk.icta.mobile.apps.railway.R;
import lk.icta.mobile.apps.railway.obj.TrainLines;
import lk.icta.mobile.apps.railway.util.Functions;
import lk.icta.mobile.apps.railway.webservice.RailwayWebServiceV2;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ActivitySelectTrainLines extends Activity {
	
	ListView mListViewLines;	
	ProgressDialog mProgressDialog;
	Handler mWSGetTrainLinesHandler;
	static TrainLines mTrainLines;	


	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_train_lines);  
       
        
        mListViewLines = (ListView) findViewById(R.id.select_train_lines_listview_lines);
        
        
        
       mProgressDialog = Functions.getProgressDialog(this, getString(R.string.all_retriving_data));
       new WSGetTrainLines().execute(null,null,null);
           
        
        
        mWSGetTrainLinesHandler = new Handler() { 
        	public void handleMessage(Message message) {        		
				mListViewLines.setAdapter(new ArrayAdapter<String>(ActivitySelectTrainLines.this,android.R.layout.simple_list_item_1 , mTrainLines.getNames()));
				mProgressDialog.dismiss();
 	        }
        };
        
        mListViewLines.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
				Intent nextScreen = new Intent(ActivitySelectTrainLines.this, ActivitySelectStations.class);
				nextScreen.putExtra("lineId", mTrainLines.getIds()[position]);
				ActivitySelectTrainLines.this.startActivity(nextScreen);				
			}
        	
        });
        
    }
    
    @Override
	public void onBackPressed() {		
		super.onBackPressed();
		finish();
	}
    
    public void onHomeClick(View v) {			
    	startActivity(new Intent(this, ActivityDashboard.class));
    	finish();    
	}
      
    
    class WSGetTrainLines extends AsyncTask<Object, Object, Object>{

		@Override
		protected Object doInBackground(Object... params) {
			try {				
					
						mTrainLines = RailwayWebServiceV2.getLines();				
						mWSGetTrainLinesHandler.sendMessage(mWSGetTrainLinesHandler.obtainMessage());
					   
					return null;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			return null;
		}    	
    }   
    
}
