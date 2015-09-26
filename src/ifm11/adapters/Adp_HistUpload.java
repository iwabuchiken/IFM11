package ifm11.adapters;

import ifm11.items.HistUpload;
import ifm11.main.R;
import ifm11.utils.CONS;
import ifm11.utils.Methods;
import ifm11.utils.Tags;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class Adp_HistUpload extends ArrayAdapter<HistUpload> {

	/*--------------------------------------------------------
	 * Class fields
		--------------------------------------------------------*/
	// Context
	Context con;

	// Inflater
	LayoutInflater inflater;

	/*--------------------------------------------------------
	 * Constructor
		--------------------------------------------------------*/
	//
	public 
	Adp_HistUpload
	(Context con, int resourceId, List<HistUpload> items) {
		// Super
		super(con, resourceId, items);

		// Context
		this.con = con;

		// Inflater
		inflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		////////////////////////////////

		// test: D-23-3

		////////////////////////////////

	}//Adp_HistUploadList


	public Adp_HistUpload(Context con, int resourceId, List<HistUpload> items, 
											CONS.Enums.MoveMode moveMode) {
		// Super
		super(con, resourceId, items);

		// Context
		this.con = con;

		// Inflater
		inflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}//public HistUploadListAdapter(Context con, int resourceId, List<HistUpload> items, CONS.MoveMode moveMode)

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
    	////////////////////////////////

		// setup: positions

		////////////////////////////////
		CONS.TNActv.list_Pos_Prev = CONS.TNActv.list_Pos_Current;
		
		CONS.TNActv.list_Pos_Current = position;
    	
    	/*----------------------------
		 * 0. View
			----------------------------*/
    	View v = null;

    	/*----------------------------
		 * 1. Set layout
			----------------------------*/
		
    	if (convertView != null) {
			
    		v = convertView;
    		
		} else {//if (convertView != null)

			v = inflater.inflate(R.layout.list_row_histupload, null);
//			v = inflater.inflate(R.layout.list_row, null);
			
		}//if (convertView != null)

    	/*----------------------------
		 * 2. Get view
			----------------------------*/
    	TextView tv_Filename = (TextView) v.findViewById(R.id.list_row_histupload_tv_filename);
    	
    	/*----------------------------
		 * 3. Get item
			----------------------------*/
    	HistUpload hu = (HistUpload) getItem(position);

    	///////////////////////////////////
		//
		// validate: null
		//
		///////////////////////////////////
    	
		if (hu == null) {

			// Log
			String msg_Log;
			
			msg_Log = String.format(
					Locale.JAPAN,
					"ti => null"
					);
			
			Log.e("Adp_TIList.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
		
			///////////////////////////////////
			//
			// new TI
			//
			///////////////////////////////////
			hu = new HistUpload.Builder()
					.setFile_name("null")
					.setCreated_at("null")
					.build();
			
		}//if (ti == null)
    	
    	////////////////////////////////

		// Set background

		////////////////////////////////
//		///////////////////////////////////
//		//
//		// today
//		//
//		///////////////////////////////////
//		Calendar cal = Calendar.getInstance();
////		
//		int tmp_i = cal.get(Calendar.YEAR) - 1900;
//		
//		cal.set(Calendar.HOUR, 0);
//		cal.set(Calendar.MINUTE, 0);
//		cal.set(Calendar.SECOND, 0);
//		cal.set(Calendar.MILLISECOND, 0);
//		
//		long start = cal.getTime().getTime();
//		
//		long target = Methods.conv_TimeLabel_to_MillSec(hu.getCreated_at());
//
//		TextView tv_Date = (TextView) v.findViewById(R.id.list_row_histupload_tv_date);
////		TextView tv = (TextView) v.findViewById(R.id.list_row_histupload_tv_filename);
//		
//		// Log
//		String msg_Log;
//		
//		msg_Log = String.format(
//				Locale.JAPAN,
//				"start = %d | target = %d", start, target
////				"start = %ld | target = %ld", start, target
//				);
//		
//		Log.i("Adp_HistUpload.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_Log);
//		
//		///////////////////////////////////
//		//
//		// bg color
//		//
//		///////////////////////////////////
//		if (target >= start) {
//
//			msg_Log = String.format(
//					Locale.JAPAN,
//					"target >= start"
////					"start = %ld | target = %ld", start, target
//					);
//			
//			Log.i("Adp_HistUpload.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", msg_Log);
//
//			tv_Date.setBackgroundColor(Color.BLUE);
////			tv.setBackgroundColor(((Activity)con).getResources().getColor(R.color.blue1));
//			
//		} else {//if (target >= start)
//
//			msg_Log = String.format(
//					Locale.JAPAN,
//					"!(target >= start)"
////					"start = %ld | target = %ld", start, target
//					);
//			
//			Log.i("Adp_HistUpload.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", msg_Log);
//
//			tv_Date.setBackgroundColor(Color.LTGRAY);
////			tv.setBackgroundColor(((Activity)con).getResources().getColor(R.color.white));
//			
//		}//if (target >= start)

		
//		v = _getView__Background(v, hu);
		
//		_getView__Background(v, hu);
		
//		Calendar cal = Calendar.getInstance();
////		
//		int tmp_i = cal.get(Calendar.YEAR) - 1900;
//		
//		cal.set(Calendar.HOUR, 0);
//		cal.set(Calendar.MINUTE, 0);
//		cal.set(Calendar.SECOND, 0);
//		cal.set(Calendar.MILLISECOND, 0);
//		
//		long 
		
//		Date d = cal.getTime();
//		
//		d.getye
//		
////		cal.set(Calendar., value);
//		
//		cal.getTime().getTime();
		
		
//		int savedPosition = Methods.get_Pref_Int(
//								(Activity)con, 
//								CONS.Pref.pname_MainActv, 
//								CONS.Pref.pkey_CurrentPosition_HistUploadActv, 
//								CONS.Pref.dflt_IntExtra_value);
//		
//		if (savedPosition == position) {
//			
//			tv.setBackgroundResource(R.color.gold2);
//			tv.setTextColor(Color.BLACK);
//			
//		} else if (savedPosition == -1) {//if (savedPosition == position)
//			
//		} else {//if (savedPosition == position)
//			
//			tv.setBackgroundColor(Color.BLACK);
//			tv.setTextColor(Color.WHITE);
//			
//		}//if (savedPosition == position)

    	////////////////////////////////

		// file name

		////////////////////////////////
		tv_Filename.setText(hu.getFile_name());
		
		String pref_FontSize = Methods.get_Pref_String(
				(Activity)con, 
				CONS.Pref.pname_MainActv, 
				((Activity)con).getString(R.string.prefs_tnactv_list_font_size_key), 
				null);
		
		if (pref_FontSize != null && !pref_FontSize.equals("")) {
			
			tv_Filename.setTextSize(Integer.parseInt(pref_FontSize));
			
		}

		///////////////////////////////////
		//
		// created_at
		//
		///////////////////////////////////
		TextView tv_Created_at = (TextView) v.findViewById(R.id.list_row_histupload_tv_date);
		
		tv_Created_at.setTextColor(Color.BLACK);
		tv_Created_at.setBackgroundColor(Color.WHITE);
		
		String created_at = hu.getCreated_at();
		
		if (created_at != null) {
			
			tv_Created_at.setText(created_at);
			
		} else {//if (memo)
			
			tv_Created_at.setText("");
			
		}//if (memo)

		///////////////////////////////////
		//
		// created_at: bg
		//
		///////////////////////////////////
		v = _getView__Background(v, hu);
		
		///////////////////////////////////
		//
		// return: view
		//
		///////////////////////////////////
		return v;
		
    }//public View getView(int position, View convertView, ViewGroup parent)


	private View 
	_getView__Background(View v, HistUpload hu) {
		// TODO Auto-generated method stub

		///////////////////////////////////
		//
		// today
		//
		///////////////////////////////////
		Calendar cal = Calendar.getInstance();
//		
		int tmp_i = cal.get(Calendar.YEAR) - 1900;
		
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		
		long start = cal.getTime().getTime();
		
		long target = Methods.conv_TimeLabel_to_MillSec(hu.getCreated_at());

		TextView tv_Created_at = (TextView) v.findViewById(R.id.list_row_histupload_tv_date);
//		TextView tv = (TextView) v.findViewById(R.id.list_row_histupload_tv_filename);
		
		///////////////////////////////////
		//
		// bg color
		// text color
		//
		///////////////////////////////////
		if (target >= start) {
			
			tv_Created_at.setBackgroundColor(Color.BLUE);
//			tv.setBackgroundColor(((Activity)con).getResources().getColor(R.color.blue1));
			
			tv_Created_at.setTextColor(Color.WHITE);
			
		} else {//if (target >= start)
			
			tv_Created_at.setBackgroundColor(Color.LTGRAY);
//			tv.setBackgroundColor(((Activity)con).getResources().getColor(R.color.white));
			
			tv_Created_at.setTextColor(Color.BLACK);
			
		}//if (target >= start)
		
		///////////////////////////////////
		//
		// return
		//
		///////////////////////////////////
		return v;
		
	}//_getView__Background

}//public class TIListAdapter extends ArrayAdapter<TI>
