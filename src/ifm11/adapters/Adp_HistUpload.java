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
    	TextView tv = (TextView) v.findViewById(R.id.list_row_histupload_tv_filename);
    	
    	/*----------------------------
		 * 3. Get item
			----------------------------*/
    	HistUpload ti = (HistUpload) getItem(position);

    	///////////////////////////////////
		//
		// validate: null
		//
		///////////////////////////////////
    	
		if (ti == null) {

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
			ti = new HistUpload.Builder()
					.setFile_name("null")
					.setCreated_at("null")
					.build();
			
		}//if (ti == null)
    	
    	////////////////////////////////

		// Set background

		////////////////////////////////
		int savedPosition = Methods.get_Pref_Int(
								(Activity)con, 
								CONS.Pref.pname_MainActv, 
								CONS.Pref.pkey_CurrentPosition_HistUploadActv, 
								CONS.Pref.dflt_IntExtra_value);
		
		if (savedPosition == position) {
			
			tv.setBackgroundResource(R.color.gold2);
			tv.setTextColor(Color.BLACK);
			
		} else if (savedPosition == -1) {//if (savedPosition == position)
			
		} else {//if (savedPosition == position)
			
			tv.setBackgroundColor(Color.BLACK);
			tv.setTextColor(Color.WHITE);
			
		}//if (savedPosition == position)

    	////////////////////////////////

		// file name

		////////////////////////////////
		tv.setText(ti.getFile_name());
		
		String pref_FontSize = Methods.get_Pref_String(
				(Activity)con, 
				CONS.Pref.pname_MainActv, 
				((Activity)con).getString(R.string.prefs_tnactv_list_font_size_key), 
				null);
		
		if (pref_FontSize != null && !pref_FontSize.equals("")) {
			
			tv.setTextSize(Integer.parseInt(pref_FontSize));
			
		}

		///////////////////////////////////
		//
		// created_at
		//
		///////////////////////////////////
		TextView tv_Created_at = (TextView) v.findViewById(R.id.list_row_histupload_tv_date);
		
		tv_Created_at.setTextColor(Color.BLACK);
		tv_Created_at.setBackgroundColor(Color.WHITE);
		
		String created_at = ti.getCreated_at();
		
		if (created_at != null) {
			
			tv_Created_at.setText(created_at);
			
		} else {//if (memo)
			
			tv_Created_at.setText("");
			
		}//if (memo)

		///////////////////////////////////
		//
		// return: view
		//
		///////////////////////////////////
		return v;
		
    }//public View getView(int position, View convertView, ViewGroup parent)

}//public class TIListAdapter extends ArrayAdapter<TI>
