package ifm11.adapters;

import ifm11.items.ListItem;
import ifm11.main.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class Adp_ListItems extends ArrayAdapter<ListItem> {

	/*--------------------------------------------------------
	 * Class fields
		--------------------------------------------------------*/
	// Context
	Context con;

	// Inflater
	LayoutInflater inflater;

	private TextView tv;
	
	/*--------------------------------------------------------
	 * Constructor
		--------------------------------------------------------*/
	public Adp_ListItems(Context con, int resourceId, List<ListItem> items) {
		// Super
		super(con, resourceId, items);

		// Context
		this.con = con;

		// Inflater
		inflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		

	}//public TIListAdapter(Context con, int resourceId, List<TI> items)

	/*--------------------------------------------------------
	 * Methods
		--------------------------------------------------------*/
    @Override
    public View 
    getView
    (int position, View convertView, ViewGroup parent) {
    	/*----------------------------
		 * Steps
		 * 0. View
		 * 1. Set layout
		 * 2. Get view
		 * 3. Get item
		 * 4. Get bitmap
		 * 5. Get memo, or, file name
			----------------------------*/
    	/*----------------------------
		 * 0. View
			----------------------------*/
    	View v = null;

    	if (convertView != null) {

    		v = convertView;
    		
		} else {//if (convertView != null)

			v = inflater.inflate(R.layout.list_row_simple_iv_1, null);

		}//if (convertView != null)
		
    	////////////////////////////////

		// get: item

		////////////////////////////////
    	ListItem li = getItem(position);
    	
    	////////////////////////////////

		// setup: view

		////////////////////////////////
		_Setup_Views(v, li);
		
		return v;
		
    }//public View getView(int position, View convertView, ViewGroup parent)

	private void 
	_Setup_Views
	(View v, ListItem li) {
		// TODO Auto-generated method stub
		
		
		////////////////////////////////
		
		// view: text
		
		////////////////////////////////
		_Setup_Views__TV(v, li);
		
		////////////////////////////////
		
		// view: icon
		
		////////////////////////////////
		_Setup_Views__IV(v, li);
		
	}//_Setup_Views

	private void 
	_Setup_Views__IV
	(View v, ListItem li) {
		// TODO Auto-generated method stub

		////////////////////////////////

		// layout

		////////////////////////////////
		LinearLayout.LayoutParams params =
				new LinearLayout.LayoutParams(
//								li.getText().length() * 60,
//								200,
//								LayoutParams.FILL_PARENT,
								LayoutParams.WRAP_CONTENT,
//								row_Height
//						);
								LayoutParams.WRAP_CONTENT);
		
		//REF margin http://stackoverflow.com/questions/3416087/how-to-set-margin-of-imageview-using-code-not-xml answered Aug 5 '10 at 15:19
		params.setMargins(2, 2, 2, 2);
		
		params.gravity = 
//						Gravity.CENTER_HORIZONTAL;
					Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL;

		////////////////////////////////

		// view

		////////////////////////////////
		ImageView iv = (ImageView) v.findViewById(R.id.list_row_slimple_iv_1_iv);
		
		iv.setLayoutParams(params);
		
		iv.setImageDrawable(((Activity)con).getResources().getDrawable(li.getIconID()));
//		iv.setBackgroundResource(li.getIconID());
		
	}//_Setup_Views__IV

	private void 
	_Setup_Views__TV
	(View v, ListItem li) {
		// TODO Auto-generated method stub
		
		tv = (TextView) v.findViewById(R.id.list_row_slimple_iv_1_tv);
//		TextView tv = (TextView) v.findViewById(R.id.list_row_slimple_iv_1_tv);
		
		tv.setText(li.getText());
		
		tv.setTextColor(((Activity)con).getResources().getColor(li.getTextColor_ID()));
//		tv.setTextColor(li.getTextColor_ID());
		
//		int row_Height = (int) (tv.getTextSize() * ((float)200 / 100));
//		
//		// Log
//		String msg_Log = "row_Height => " + row_Height;
//		Log.d("Adp_ListItems.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_Log);
		
//		li.getText().length()
		
		LinearLayout.LayoutParams params =
				new LinearLayout.LayoutParams(
//								li.getText().length() * 60,
//								200,
								LayoutParams.FILL_PARENT,
//								LayoutParams.WRAP_CONTENT,
//								row_Height
//						);
								LayoutParams.WRAP_CONTENT);
		
		//REF margin http://stackoverflow.com/questions/3416087/how-to-set-margin-of-imageview-using-code-not-xml answered Aug 5 '10 at 15:19
		params.setMargins(5, 10, 10, 10);
		
		tv.setLayoutParams(params);
		
//		////////////////////////////////
//
//		// listener: onTouch
//
//		////////////////////////////////
//		tv.setOnTouchListener(new OnTouchListener(){
//
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				// TODO Auto-generated method stub
//				switch (event.getActionMasked()) {
//				
//				case MotionEvent.ACTION_DOWN:
//					
//					tv.setBackgroundColor(
//							((Activity)con).getResources().getColor(R.color.gray1));
//					
//					break;
//					
//				case MotionEvent.ACTION_UP:
//
//					tv.setBackgroundColor(
//							((Activity)con).getResources().getColor(R.color.white));
//					
//					break;
//					
//				}
//				
//				return false;
//			}});
		
	}//_Setup_Views__TV

}//public class TIListAdapter extends ArrayAdapter<TI>
