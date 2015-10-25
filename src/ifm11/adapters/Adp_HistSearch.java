package ifm11.adapters;

import ifm11.items.SearchHistory;
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

import org.apache.commons.lang.StringUtils;

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

public class Adp_HistSearch extends ArrayAdapter<SearchHistory> {

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
	Adp_HistSearch
	(Context con, int resourceId, List<SearchHistory> items) {
		// Super
		super(con, resourceId, items);

		// Context
		this.con = con;

		// Inflater
		inflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		////////////////////////////////

		// test: D-23-3

		////////////////////////////////

	}//Adp_SearchHistoryList


	/*--------------------------------------------------------
	 * Methods
		--------------------------------------------------------*/
    @Override
    public View getView
    (int position, View convertView, ViewGroup parent) {
    	
    	///////////////////////////////////
		//
		// View
		//
		///////////////////////////////////
    	View v = null;

    	///////////////////////////////////
    	//
    	// Get item
    	//
    	///////////////////////////////////
    	SearchHistory sh = (SearchHistory) getItem(position);

    	///////////////////////////////////
		//
		// validate: null
		//
		///////////////////////////////////
    	
		if (sh == null) {

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
			sh = new SearchHistory.Builder()
			
					.setCreated_at(CONS.SearchHistory.dflt_DateTime_String)
			
					.build();
			
		}//if (ti == null)
    	
    	///////////////////////////////////
		//
		// Set layout
		//
		///////////////////////////////////
    	if (convertView != null) {
			
    		v = convertView;
    		
		} else {//if (convertView != null)

			v = inflater.inflate(R.layout.list_row_search_history, null);
//			v = inflater.inflate(R.layout.list_row_histupload, null);
//			v = inflater.inflate(R.layout.list_row, null);
			
		}//if (convertView != null)

    	///////////////////////////////////
		//
		// view: date, time
		//
		///////////////////////////////////
//    	TextView tv_Filename = (TextView) v.findViewById(R.id.list_row_Search_History__TV__created_at);
    	this._Setup_Views__TV_ModifiedAt_Date(v, sh);
    	
    	this._Setup_Views__TV_ModifiedAt_Time(v, sh);

    	///////////////////////////////////
		//
		// view: keywords, type
		//
		///////////////////////////////////
		this._Setup_Views__TV_Keywords(v, sh);
		
		this._Setup_Views__TV_Type(v, sh);
		
		this._Setup_Views__TV_All_Table(v, sh);
		
		this._Setup_Views__TV_File_Name(v, sh);
		
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
//		Log.i("Adp_SearchHistory.java" + "["
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

//    	////////////////////////////////
//
//		// file name
//
//		////////////////////////////////
//		tv_Filename.setText(sh.getFile_name());
//		
//		String pref_FontSize = Methods.get_Pref_String(
//				(Activity)con, 
//				CONS.Pref.pname_MainActv, 
//				((Activity)con).getString(R.string.prefs_tnactv_list_font_size_key), 
//				null);
//		
//		if (pref_FontSize != null && !pref_FontSize.equals("")) {
//			
//			tv_Filename.setTextSize(Integer.parseInt(pref_FontSize));
//			
//		}
//
//		///////////////////////////////////
//		//
//		// created_at
//		//
//		///////////////////////////////////
//		TextView tv_Created_at = (TextView) v.findViewById(R.id.list_row_histupload_tv_date);
//		
//		tv_Created_at.setTextColor(Color.BLACK);
//		tv_Created_at.setBackgroundColor(Color.WHITE);
//		
//		String created_at = sh.getCreated_at();
//		
//		if (created_at != null) {
//			
//			tv_Created_at.setText(created_at);
//			
//		} else {//if (memo)
//			
//			tv_Created_at.setText("");
//			
//		}//if (memo)
//
//		///////////////////////////////////
//		//
//		// created_at: bg
//		//
//		///////////////////////////////////
//		v = _getView__Background(v, sh);
		
		///////////////////////////////////
		//
		// return: view
		//
		///////////////////////////////////
		return v;
		
    }//public View getView(int position, View convertView, ViewGroup parent)

	private void 
	_Setup_Views__TV_ModifiedAt_Date
	(View v, SearchHistory sh) {
		// TODO Auto-generated method stub

		////////////////////////////////

		// created at

		////////////////////////////////
		TextView tv_Modified = 
				(TextView) v.findViewById(R.id.list_row_Search_History__TV__created_at);

		String[] tokens = sh.getModified_at().split(" ");
		
		String[] tokens_Date = tokens[0].split("/");
		
		String new_DateLabel = StringUtils.join(
						new String[]{
						
								tokens_Date[1],
								tokens_Date[2]
										
						}, 
						"/");

		tv_Modified.setText(new_DateLabel);
		
	}//_Setup_Views__TV

	private void 
	_Setup_Views__TV_ModifiedAt_Time
	(View v, SearchHistory fh) {
		// TODO Auto-generated method stub
		
		////////////////////////////////
		
		// created at
		
		////////////////////////////////
		TextView tv_Time = 
				(TextView) v.findViewById(R.id.list_row_Search_History__TV__modified_at);
//		(TextView) v.findViewById(R.id.list_row_filter_history_tv_modified_at);
		
		String[] tokens = fh.getModified_at().split(" ");
		
		String[] tokens_Time = tokens[1].split("\\.");
		
		String new_TimeLabel = tokens_Time[0];

		
		tv_Time.setText(new_TimeLabel);
		
	}//_Setup_Views__TV_ModifiedAt_Time
	
	private void 
	_Setup_Views__TV_Keywords
	(View v, SearchHistory sh) {
		// TODO Auto-generated method stub
		
		////////////////////////////////
		
		// created at
		
		////////////////////////////////
		TextView tv_Keywords = 
				(TextView) v.findViewById(R.id.list_row_Search_History__TV__keywords);
//		(TextView) v.findViewById(R.id.list_row_filter_history_tv_modified_at);

		tv_Keywords.setText(sh.getKeywords());
		
	}//_Setup_Views__TV_ModifiedAt_Time

	private void
	_Setup_Views__TV_Type
	(View v, SearchHistory sh) {
		// TODO Auto-generated method stub
		
		////////////////////////////////
		
		// created at
		
		////////////////////////////////
		TextView tv_KW_Type = 
				(TextView) v.findViewById(R.id.list_row_Search_History__TV__KW_Type);
//		(TextView) v.findViewById(R.id.list_row_filter_history_tv_modified_at);
		
		///////////////////////////////////
		//
		// label
		//
		///////////////////////////////////
		String label = null;
		
		int type = sh.getType();
		
		switch (type) {
//		"INTEGER", 			// 3	=> AND: 1, OR: 2, NOT: -1
		
		case 1:
			
			label = CONS.SearchHistory.lbl_Type_AND;
			
			break;

		case 2:
			
			label = CONS.SearchHistory.lbl_Type_OR;
			
			break;
			
		case -1:
			
			label = CONS.SearchHistory.lbl_Type_NOT;
			
			break;
			
		default:
			
			label = CONS.SearchHistory.lbl_Type_UNKNOWN;
			
			break;
			
		}
		
		tv_KW_Type.setText(label);
//		tv_KW_Type.setText(sh.getKeywords());
		
	}//_Setup_Views__TV_ModifiedAt_Time
	
	private void
	_Setup_Views__TV_All_Table
	(View v, SearchHistory sh) {
		// TODO Auto-generated method stub
		
		////////////////////////////////
		
		// created at
		
		////////////////////////////////
		TextView tv_Table_Type = 
				(TextView) v.findViewById(R.id.list_row_Search_History__TV__Table_Type);
		
		///////////////////////////////////
		//
		// label
		//
		///////////////////////////////////
		String label = null;
		
		int type = sh.getAll_table();

//		public static int val_All_Table_TRUE	= 1;
//		public static int val_All_Table_FALSE	= 0;
		
		switch (type) {
//		"INTEGER", 			// 3	=> AND: 1, OR: 2, NOT: -1
		
		case 1:
			
			label = CONS.SearchHistory.lbl_All_Table_TRUE;
			
			break;
			
		case 0:
			
			label = CONS.SearchHistory.lbl_All_Table_FALSE;
			
			break;
			
		default:
			
			label = CONS.SearchHistory.lbl_Type_UNKNOWN;
			
			break;
			
		}
		
		tv_Table_Type.setText(label);
//		tv_KW_Type.setText(sh.getKeywords());
		
	}//_Setup_Views__TV_All_Table
	
	private void
	_Setup_Views__TV_File_Name
	(View v, SearchHistory sh) {
		// TODO Auto-generated method stub
		
		////////////////////////////////
		
		// created at
		
		////////////////////////////////
		TextView tv_By_FileName = 
				(TextView) v.findViewById(R.id.list_row_Search_History__TV__By_FileName);
//		(TextView) v.findViewById(R.id.list_row_Search_History__TV__Table_Type);
		
		///////////////////////////////////
		//
		// label
		//
		///////////////////////////////////
		String label = null;
		
		int type = sh.getBy_file_name();
//		int type = sh.getAll_table();
		
//		public static String lbl_By_FileName_TRUE	= "File name";
//		public static String lbl_By_FileName_FALSE	= "Memo";		
		
		switch (type) {
//		"INTEGER", 			// 3	=> AND: 1, OR: 2, NOT: -1
		
		case 1:
			
			label = CONS.SearchHistory.lbl_By_FileName_TRUE;
			
			break;
			
		case 0:
			
			label = CONS.SearchHistory.lbl_By_FileName_FALSE;
			
			break;
			
		default:
			
			label = CONS.SearchHistory.lbl_Type_UNKNOWN;
			
			break;
			
		}
		
		tv_By_FileName.setText(label);
//		tv_KW_Type.setText(sh.getKeywords());
		
	}//_Setup_Views__TV_File_Name
	


	private View 
	_getView__Background(View v, SearchHistory hu) {
		// TODO Auto-generated method stub

		///////////////////////////////////
		//
		// today
		//
		///////////////////////////////////
		Calendar cal_Now = Calendar.getInstance();
//		
//		int tmp_i = cal_Now.get(Calendar.YEAR) - 1900;
		
		cal_Now.set(Calendar.HOUR, 0);
		cal_Now.set(Calendar.MINUTE, 0);
		cal_Now.set(Calendar.SECOND, 0);
		cal_Now.set(Calendar.MILLISECOND, 0);

		// today
		long time_Today_L = cal_Now.getTime().getTime();
		
		// yesterday
		cal_Now.add(Calendar.DATE, -1);
		
		long time_Yesterday_L = cal_Now.getTime().getTime();

		// 3 days ago
		cal_Now.add(Calendar.DATE, -2);	// -1 + (-2) = -3
		
		long time_3DaysAgo_L = cal_Now.getTime().getTime();

		// this week
		cal_Now.add(Calendar.DATE, -4);	// -3 + (-4) = -7
		
		long time_ThisWeek_L = cal_Now.getTime().getTime();
		
		// created at
		long time_Created_At_L = Methods.conv_TimeLabel_to_MillSec(hu.getCreated_at());

		TextView tv_Created_at = (TextView) v.findViewById(R.id.list_row_histupload_tv_date);
//		TextView tv = (TextView) v.findViewById(R.id.list_row_histupload_tv_filename);
		
		///////////////////////////////////
		//
		// bg color
		// text color
		//
		///////////////////////////////////
		if (time_Created_At_L >= time_Today_L) {
			
			tv_Created_at.setBackgroundColor(Color.BLUE);
//			tv.setBackgroundColor(((Activity)con).getResources().getColor(R.color.blue1));
			
			tv_Created_at.setTextColor(Color.WHITE);
		
		} else if (time_Created_At_L >= time_Yesterday_L) {//if (target >= start)

			tv_Created_at.setBackgroundColor(Color.GREEN);
	//		tv.setBackgroundColor(((Activity)con).getResources().getColor(R.color.blue1));
			
			tv_Created_at.setTextColor(Color.BLACK);

		} else if (time_Created_At_L >= time_3DaysAgo_L) {//if (target >= start)

			tv_Created_at.setBackgroundColor(
			((Activity)con).getResources().getColor(R.color.gold2));
			//tv.setBackgroundColor(((Activity)con).getResources().getColor(R.color.blue1));
			
			tv_Created_at.setTextColor(Color.BLACK);

		} else if (time_Created_At_L >= time_ThisWeek_L) {//if (target >= start)
			
			tv_Created_at.setBackgroundColor(
			((Activity)con).getResources().getColor(R.color.purple4));
		//	tv.setBackgroundColor(((Activity)con).getResources().getColor(R.color.blue1));
			
			tv_Created_at.setTextColor(Color.WHITE);
			
		} else {//if (target >= start)
			
			tv_Created_at.setBackgroundColor(Color.LTGRAY);
//			tv.setBackgroundColor(((Activity)con).getResources().getColor(R.color.white));
			
			tv_Created_at.setTextColor(Color.BLACK);
			
		}//if (target >= start)
		
//		///////////////////////////////////
//		//
//		// yesterday
//		//
//		///////////////////////////////////
//		cal_Now.add(Calendar.DATE, -1);
//		
//		long time_Yesterday_L = cal_Now.getTime().getTime();
//
//		if (time_Created_At_L < time_Today_L
//				&& time_Created_At_L >= time_Yesterday_L) {
//			
//			tv_Created_at.setBackgroundColor(Color.GREEN);
////			tv.setBackgroundColor(((Activity)con).getResources().getColor(R.color.blue1));
//			
//			tv_Created_at.setTextColor(Color.BLACK);
//		
//		} else {//if (target >= start)
//			
//			tv_Created_at.setBackgroundColor(Color.LTGRAY);
////			tv.setBackgroundColor(((Activity)con).getResources().getColor(R.color.white));
//			
//			tv_Created_at.setTextColor(Color.BLACK);
//			
//		}//if (target >= start)
//
//		///////////////////////////////////
//		//
//		// 3 days ago
//		//
//		///////////////////////////////////
//		cal_Now.add(Calendar.DATE, -2);	// -1 + (-2) = -3
//		
//		long time_3DaysAgo_L = cal_Now.getTime().getTime();
//		
//		if (time_Created_At_L < time_Yesterday_L
//				&& time_Created_At_L >= time_3DaysAgo_L) {
//			
//			tv_Created_at.setBackgroundColor(
//						((Activity)con).getResources().getColor(R.color.gold2));
////			tv.setBackgroundColor(((Activity)con).getResources().getColor(R.color.blue1));
//			
//			tv_Created_at.setTextColor(Color.BLACK);
//			
//		} else {//if (target >= start)
//			
//			tv_Created_at.setBackgroundColor(Color.LTGRAY);
////			tv.setBackgroundColor(((Activity)con).getResources().getColor(R.color.white));
//			
//			tv_Created_at.setTextColor(Color.BLACK);
//			
//		}//if (target >= start)
//		
//		///////////////////////////////////
//		//
//		// this week
//		//
//		///////////////////////////////////
//		cal_Now.add(Calendar.DATE, -4);	// -3 + (-4) = -7
//		
//		long time_ThisWeek_L = cal_Now.getTime().getTime();
//		
//		if (time_Created_At_L < time_3DaysAgo_L
//				&& time_Created_At_L >= time_ThisWeek_L) {
//			
//			tv_Created_at.setBackgroundColor(
//					((Activity)con).getResources().getColor(R.color.purple4));
////			tv.setBackgroundColor(((Activity)con).getResources().getColor(R.color.blue1));
//			
//			tv_Created_at.setTextColor(Color.WHITE);
//			
//		} else {//if (target >= start)
//			
//			tv_Created_at.setBackgroundColor(Color.LTGRAY);
////			tv.setBackgroundColor(((Activity)con).getResources().getColor(R.color.white));
//			
//			tv_Created_at.setTextColor(Color.BLACK);
//			
//		}//if (target >= start)
		
		///////////////////////////////////
		//
		// return
		//
		///////////////////////////////////
		return v;
		
	}//_getView__Background

}//public class TIListAdapter extends ArrayAdapter<TI>
