package ifm11.adapters;

import ifm11.items.LogItem;
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

public class Adp_ShowLogFile_List extends ArrayAdapter<LogItem> {

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
//	public Adp_ShowLogFile_List(Context con, int resourceId, List<String> items) {
//		// Super
//		super(con, resourceId, items);
//
//		// Context
//		this.con = con;
//
//		String currentPath = Methods.get_Pref_String(
//				(Activity)con, 
//				CONS.Pref.pname_MainActv, 
//				CONS.Pref.pkey_CurrentPath, 
//				null);
//
//		this.cur_TableName = Methods.conv_CurrentPath_to_TableName(currentPath);
//		
//		// Inflater
//		inflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//		
//
//	}//public TIListAdapter(Context con, int resourceId, List<String> items)

	public Adp_ShowLogFile_List
	(Context con, int layoutID, List<LogItem> list_LogItems) {
		// TODO Auto-generated constructor stub
		
		super(con, layoutID, list_LogItems);
		
		this.con = con;
		
		inflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
	}

	/*--------------------------------------------------------
	 * Methods
		--------------------------------------------------------*/
    @Override
    public View 
    getView
    (int position, View convertView, ViewGroup parent) {
    	
    	////////////////////////////////

		// setup: View

		////////////////////////////////
    	View v = null;

    	if (convertView != null) {

    		v = convertView;
    		
		} else {//if (convertView != null)

			v = inflater.inflate(R.layout.list_row_logitem, null);

		}//if (convertView != null)
		
    	////////////////////////////////

		// get item

		////////////////////////////////
    	LogItem loi = (LogItem) getItem(position);
    	
		////////////////////////////////

		// view: date, time

		////////////////////////////////
    	_getView__Date_Time(v, loi);
    	
    	////////////////////////////////

		// view: file name, line

		////////////////////////////////
		this._getView__FileName_Line(v, loi);
    	
		////////////////////////////////
		
		// view: text
		
		////////////////////////////////
		this._getView__Text(v, loi);
		
		return v;
		
    }//public View getView(int position, View convertView, ViewGroup parent)

	private void 
	_getView__Date_Time
	(View v, LogItem loi) {
		// TODO Auto-generated method stub
		////////////////////////////////

		// view: date

		////////////////////////////////
		TextView tv_Date = (TextView) v.findViewById(R.id.list_row_logitem_tv_date);
		
		tv_Date.setText(loi.getDate());
		
		////////////////////////////////
		
		// view: time
		
		////////////////////////////////
		TextView tv_Time = (TextView) v.findViewById(R.id.list_row_logitem_tv_time);
		
		tv_Time.setText(loi.getTime());
		
		
	}//_getView__Date_Time
    
	private void 
	_getView__FileName_Line
	(View v, LogItem loi) {
		// TODO Auto-generated method stub
		////////////////////////////////
		
		// view: file name
		
		////////////////////////////////
		TextView tv_FileName = (TextView) v.findViewById(R.id.list_row_logitem_TV_filename);
		
		tv_FileName.setText(loi.getMethod());
		
		////////////////////////////////
		
		// view: line
		
		////////////////////////////////
		TextView tv_Line = (TextView) v.findViewById(R.id.list_row_logitem_TV_line);
		
		tv_Line.setText(String.valueOf(loi.getLine()));
		
		
	}//_getView__Date_Time
	
	private void 
	_getView__Text
	(View v, LogItem loi) {
		// TODO Auto-generated method stub
		////////////////////////////////
		
		// view: text
		
		////////////////////////////////
		TextView tv_Text = (TextView) v.findViewById(R.id.list_row_logitem_TV_text);
		
		tv_Text.setText(loi.getText());
		
	}//_getView__Date_Time
	
    
}//public class TIListAdapter extends ArrayAdapter<TI>
