package me.dilan.adapters;

import me.dilan.R;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DashboardButtonImage extends BaseAdapter {
	
	private String[] names;
	private String[] images;
	
	private Activity parent;

	public DashboardButtonImage(Activity parent) {
		super();
		this.parent = parent;	
	}

	public Activity getParent() {
		return parent;
	}

	
	public int getCount() {		
		return 5;
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater layoutInflater = this.parent.getLayoutInflater();
		View layoutDashboardButton = layoutInflater.inflate(R.layout.layout_dashboard_button, null);
		ImageView buttonImage = (ImageView) layoutDashboardButton.findViewById(R.id.act_dash_button_image_icon);
		TextView buttonText = (TextView) layoutDashboardButton.findViewById(R.id.act_dash_button_image_text);
		buttonImage.setImageResource(R.drawable.icon);
		buttonText.setText("test");
		return layoutDashboardButton;
	}

}
