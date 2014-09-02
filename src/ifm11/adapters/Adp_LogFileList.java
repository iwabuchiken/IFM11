package ifm11.adapters;

import ifm11.main.R;
import ifm11.utils.CONS;
import ifm11.utils.DBUtils;
import ifm11.utils.Methods;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class Adp_LogFileList extends ArrayAdapter<String> implements OnTouchListener {

	/*--------------------------------------------------------
	 * Class fields
		--------------------------------------------------------*/
	// Context
	Context con;

	// Inflater
	LayoutInflater inflater;
	
	//
	String cur_TableName;

	/*--------------------------------------------------------
	 * Constructor
		--------------------------------------------------------*/
	//
	public Adp_LogFileList(Context con, int resourceId, List<String> items) {
		// Super
		super(con, resourceId, items);

		// Context
		this.con = con;

		String currentPath = Methods.get_Pref_String(
				(Activity)con, 
				CONS.Pref.pname_MainActv, 
				CONS.Pref.pkey_CurrentPath, 
				null);

		this.cur_TableName = Methods.conv_CurrentPath_to_TableName(currentPath);
		
		// Inflater
		inflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		

	}//public TIListAdapter(Context con, int resourceId, List<String> items)

	/*--------------------------------------------------------
	 * Methods
		--------------------------------------------------------*/
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
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

    	/*----------------------------
		 * 2.1. Set layout
			----------------------------*/
    	if (convertView != null) {

    		v = convertView;
    		
		} else {//if (convertView != null)

			v = inflater.inflate(R.layout.list_row_simple_2, null);

		}//if (convertView != null)
		
    	//test
    	String item = (String) getItem(position);
    	
//    	// Log
//		String msg_log = "item => " + item;
//		Log.d("Adp_MainList.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_log);
//    	
		
		////////////////////////////////

		// Get: view

		////////////////////////////////
		TextView tv_Main = (TextView) v.findViewById(R.id.list_row_simple_2_tv);
		
		////////////////////////////////

		// list.txt => add num of entries

		////////////////////////////////
		
		if (item.equals(CONS.Admin.fname_List)) {
			
			int numOfItems = DBUtils.get_NumOfEntries_TI((Activity)con, this.cur_TableName);
			
			item = String.format(Locale.JAPAN, "%s (%d)", item, numOfItems);
			
		}
		////////////////////////////////

		// Set: text

		////////////////////////////////
		
		
		tv_Main.setText(item);
		
		////////////////////////////////

		// Set: background

		////////////////////////////////
		int pref_CurrentPosition = Methods.get_Pref_Int(
						(Activity)con, 
						CONS.Pref.pname_MainActv, 
						CONS.Pref.pkey_CurrentPosition_LogActv, 
						CONS.Pref.dflt_IntExtra_value);

		if (pref_CurrentPosition == position) {
			
			tv_Main.setBackgroundColor(
					((Activity)con).getResources().getColor(R.color.blue1));
			
			tv_Main.setTextColor(
					((Activity)con).getResources().getColor(R.color.white));
			
//			this.notifyDataSetChanged();
			
		} else {
			
			tv_Main.setBackgroundColor(
					((Activity)con).getResources().getColor(R.color.white));
			
			tv_Main.setTextColor(
					((Activity)con).getResources().getColor(R.color.black));
//			this.notifyDataSetChanged();

		}
		
//		// Log
//		msg_log = "item = " + item
//				+ " / "
//				+ "position = " + position
//				;
//		Log.d("Adp_MainList.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_log);
		////////////////////////////////

		// Set: listener

		////////////////////////////////
//		v = super.getView(position, convertView, parent);
        v.setOnTouchListener(this);
//        View v2 = super.getView(position, convertView, parent);
//        v2.setOnTouchListener(this);
		
//    	return null;
		return v;
    }//public View getView(int position, View convertView, ViewGroup parent)

    //REF http://nimroddayan.wordpress.com/2013/01/01/hooking-ontouchlistener-event-to-android-listviews-item/
    @Override
    public boolean onTouch(View v, MotionEvent event) {
//        float currentX = event.getX();
//        TextView tv = (TextView)v;
        switch(event.getAction()) {
        
        case  MotionEvent.ACTION_DOWN:
        	
        	// Log
			String msg_log = "Down";
			Log.d("Adp_MainList.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_log);
//            mLastX = currentX;
//            Log.v(TAG, String.format("onTouch: ACTION_DOWN for %s", tv.getText().toString()));
            break;
            
        case MotionEvent.ACTION_OUTSIDE:
        	
        	// Log
			msg_log = "Outside";
			Log.d("Adp_MainList.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_log);
        	
        	break;
        	
        case MotionEvent.ACTION_CANCEL:
        	
        	// Log
			msg_log = "Cancel";
			Log.d("Adp_MainList.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_log);
//        case MotionEvent.ACTION_MOVE:
//            Log.v(TAG, "onTouch: ACTION_MOVE " + currentX);
            break;
            
        case MotionEvent.ACTION_UP:
        	
        	// Log
			msg_log = "Up";
			Log.d("Adp_MainList.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_log);
//            if (currentX > mLastX + v.getWidth() / 6) {
//                Log.v(TAG, "Swiped!");
//                tv.setPaintFlags(tv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//            }
			
//			return false;
			
            break;
            
        }
        
        return false;
//        return true;
        
    }//public boolean onTouch(View v, MotionEvent event)

    public void update_Cur_TableName() {

		String currentPath = Methods.get_Pref_String(
				(Activity)con, 
				CONS.Pref.pname_MainActv, 
				CONS.Pref.pkey_CurrentPath, 
				null);

		this.cur_TableName = Methods.conv_CurrentPath_to_TableName(currentPath);
		
		// Log
		String msg_Log = "cur_TableName => updated to: " + cur_TableName;
		Log.d("Adp_MainList.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);

    }
    
    
}//public class TIListAdapter extends ArrayAdapter<TI>
