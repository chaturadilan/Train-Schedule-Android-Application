package me.dilan;

import me.dilan.adapters.DashboardButtonImage;
import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;

public class ActivityDashboard extends Activity {
    
	GridView mainLayout;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        
        
        mainLayout = (GridView) this.findViewById(R.id.act_dash_mainLayout);
        mainLayout.setAdapter(new DashboardButtonImage(this));
    }
}