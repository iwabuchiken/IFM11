package ifm11.adapters;

import ifm11.items.TI;
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

public class Adp_TIList extends ArrayAdapter<TI> {

	/*--------------------------------------------------------
	 * Class fields
		--------------------------------------------------------*/
	// Context
	Context con;

	// Inflater
	LayoutInflater inflater;

	//
	CONS.Enums.MoveMode moveMode = null;

	FileInputStream fis = null;
	Bitmap imageBitmap = null;
	File	fname_Image;
	/*--------------------------------------------------------
	 * Constructor
		--------------------------------------------------------*/
	//
	public 
	Adp_TIList
	(Context con, int resourceId, List<TI> items) {
		// Super
		super(con, resourceId, items);

		// Context
		this.con = con;

		// Inflater
		inflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		////////////////////////////////

		// test: D-23-3

		////////////////////////////////

	}//Adp_TIList


	public Adp_TIList(Context con, int resourceId, List<TI> items, 
											CONS.Enums.MoveMode moveMode) {
		// Super
		super(con, resourceId, items);

		// Context
		this.con = con;
		this.moveMode = moveMode;

		// Inflater
		inflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		

	}//public TIListAdapter(Context con, int resourceId, List<TI> items, CONS.MoveMode moveMode)

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

    	if (moveMode == null || moveMode == CONS.Enums.MoveMode.OFF) {
    		
    		v = move_ModeOff(v, position, convertView);
    		
    	} else {

			v = move_mode_on(v, position, convertView);
			
    	}//if (moveMode == null || moveMode == Methods.MoveMode.OFF)
    	
		
		
//    	return null;
		return v;
    }//public View getView(int position, View convertView, ViewGroup parent)


	private View move_mode_on(View v, int position, View convertView) {
		
		/*----------------------------
		 * 2. ON
			----------------------------*/
		/*----------------------------
		 * 2.1. Set layout
		 * 2.2. Get view
		 * 2.3. Get item
		 * 
		 * 2.4. Get bitmap
		 * 2.5. Get memo, or, file name
		 * 2.6. CheckedBox => Set listener
		 * 
		 * 2.7. Return
			----------------------------*/
		
		// Log
		Log.d("[" + "TIListAdapter.java : "
				+ +Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "move_mode => on");
		
    	/*----------------------------
		 * 2.1. Set layout
			----------------------------*/
    	if (convertView != null) {

    		v = convertView;
    		
		} else {//if (convertView != null)

			v = inflater.inflate(R.layout.list_row_checked_box, null);

		}//if (convertView != null)

    	/*----------------------------
		 * 2.2. Get view
			----------------------------*/
    	ImageView iv = (ImageView) v.findViewById(R.id.list_row_checked_box_iv_thumbnail);

    	/*----------------------------
		 * 2.3. Get item
			----------------------------*/
    	TI ti = getItem(position);

    	/*----------------------------
		 * 2.4. Get bitmap
			----------------------------*/
    	// ContentResolver
    	ContentResolver cr = con.getContentResolver();
    	
    	// Bitmap
    	Bitmap bmp = 
				MediaStore.Images.Thumbnails.getThumbnail(
							cr, 
							ti.getFileId(), 
							MediaStore.Images.Thumbnails.MICRO_KIND, 
							null);
    	
    	/******************************
			validate: null
		 ******************************/
		if (bmp == null) {
			
			//REF http://stackoverflow.com/questions/3035692/how-to-convert-a-drawable-to-a-bitmap answered Jun 14 '10 at 8:32
			bmp = BitmapFactory.decodeResource(con.getResources(), R.drawable.ic_launcher);
			
		}
    	
    	// Set bitmap
    	iv.setImageBitmap(bmp);
    	
    	/*----------------------------
		 * 2.5. Get memo, or, file name
			----------------------------*/
		TextView tv = (TextView) v.findViewById(R.id.list_row_checked_box_textView1);
		
		tv.setText(ti.getFile_name());
		
//		// move_mode
//		if (TNActv.move_mode == true &&
//				TNActv.checkedPositions.contains((Integer) position)) {
//			
//			tv.setBackgroundColor(Color.BLUE);
//			
//		} else {//if (ThumbnailActivity.move_mode == true)
//				
//				tv.setBackgroundColor(Color.BLACK);
//				
//		}
		
		TextView tv_memo = (TextView) v.findViewById(R.id.list_row_checked_box_textView2);
		
		String memo = ti.getMemo();
		
		if (memo != null) {
			tv_memo.setText(memo);
			
		} else {//if (memo)

			tv_memo.setText("");
		}//if (memo)
		
		/*----------------------------
		 * 2.6. CheckedBox => Set listener
		 * 		1. Set up
		 * 		2. OnClick
		 * 		3. 
			----------------------------*/
		CheckBox cb = (CheckBox) v.findViewById(R.id.list_row_checked_box_checkBox1);
		
		/*----------------------------
		 * 2.7. Return
			----------------------------*/
		return v;
		
	}//private View move_mode_on()


	private View move_ModeOff(View v, int position, View convertView) {
		
		/*----------------------------
		 * 1. Set layout
		 * 2. Get view
		 * 3. Get item
		 * 
		 * 3-2. Set background
		 * 
		 * 4. Get bitmap
		 * 5. Get memo, or, file name
		 * 
		 * 5-2. Get => last viewed
		 * 
		 * 6. Return
			----------------------------*/
		
    	/*----------------------------
		 * 1. Set layout
			----------------------------*/
		
    	if (convertView != null) {
			
    		v = convertView;
    		
		} else {//if (convertView != null)

			v = inflater.inflate(R.layout.list_row, null);
			
		}//if (convertView != null)

    	/*----------------------------
		 * 2. Get view
			----------------------------*/
    	ImageView iv = (ImageView) v.findViewById(R.id.iv_thumbnail);

    	TextView tv = (TextView) v.findViewById(R.id.textView1);
    	
    	/*----------------------------
		 * 3. Get item
			----------------------------*/
    	TI ti = (TI) getItem(position);

    	////////////////////////////////

		// Set background

		////////////////////////////////
		int savedPosition = Methods.get_Pref_Int(
								(Activity)con, 
								CONS.Pref.pname_MainActv, 
								CONS.Pref.pkey_CurrentPosition_TNActv, 
								CONS.Pref.dflt_IntExtra_value);
		
		if (savedPosition == position) {
			
			tv.setBackgroundResource(R.color.gold2);
			tv.setTextColor(Color.BLACK);
			
		} else if (savedPosition == -1) {//if (savedPosition == position)
			
		} else {//if (savedPosition == position)
			
			tv.setBackgroundColor(Color.BLACK);
			tv.setTextColor(Color.WHITE);
			
		}//if (savedPosition == position)

    	
    	
		///////////////////////////////////
		//
		// bitmap
		//
		///////////////////////////////////
		move_ModeOff__Set_Bitmap(iv, ti);
		
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
		
		/*----------------------------
		 * 5.2. Memo
			----------------------------*/
		TextView tv_memo = (TextView) v.findViewById(R.id.textView2);
		
		tv_memo.setTextColor(Color.BLACK);
		tv_memo.setBackgroundColor(Color.WHITE);
		
		String memo = ti.getMemo();
		
		if (memo != null) {
			tv_memo.setText(memo);
			
		} else {//if (memo)
			
			tv_memo.setText("");
			
		}//if (memo)
		
		/*********************************
		 * 5-2. Get => last viewed
		 *********************************/
		
		
		/*----------------------------
		 * 6. Return
			----------------------------*/
		return v;
		
	}//private void move_mode_off()


	private void move_ModeOff__Set_Bitmap(ImageView iv, TI ti) {
		// TODO Auto-generated method stub
		
    	// ContentResolver
    	ContentResolver cr = con.getContentResolver();

//    	this.fname_Image = new File(ti.getFile_path(), ti.getFile_name());
    	this.fname_Image = new File(CONS.DB.dPath_TNs, ti.getFile_name());
    	
    	Bitmap bmp = null;
    	
    	//debug
    	String msg;
		msg = String.format(Locale.JAPAN, "[%s : %d] ti: file id = %d (name = %s)", 
				Thread.currentThread().getStackTrace()[2].getFileName(), 
				Thread.currentThread().getStackTrace()[2].getLineNumber(), 
//				Thread.currentThread().getStackTrace()[1].getFileName(), 
//				Thread.currentThread().getStackTrace()[1].getLineNumber(), 
				ti.getFileId(), ti.getFile_name());

		System.out.println(msg);
		
		/*******************************
		 * Either
				1) file instance is null
			Or
				2) thumbnail file doesn't exist in "TNs" folder
			Then
				=> use MediaStore file
		 *******************************/
    	if (this.fname_Image == null || !this.fname_Image.exists()) {
//    		if (this.fname_Image == null) {
			
    		
    		
    		// Log
			String msg_Log;
			
			msg_Log = String.format(
					Locale.JAPAN,
					"this.fname_Image == null || !this.fname_Image.exists()"
					);
			
			Log.i("Adp_TIList.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
    		
        	// Bitmap
        	bmp = 
    				MediaStore.Images.Thumbnails.getThumbnail(
    							cr, 
    							ti.getFileId(), 
    							MediaStore.Images.Thumbnails.MICRO_KIND, 
    							null);
    		
        	///////////////////////////////////
			//
			// validate
			//
			///////////////////////////////////
			if (bmp == null) {	// no thumbnail file in the dir,
								// 	nor in the media store

				// Log
//				String msg_Log;
				
				msg_Log = String.format(
						Locale.JAPAN,
						"bmp => null"
						);
				
				Log.i("Adp_TIList.java" + "["
						+ Thread.currentThread().getStackTrace()[2].getLineNumber()
						+ "]", msg_Log);
				
				return;

			} else {//if (bmp == null)
				
				msg_Log = String.format(
						Locale.JAPAN,
						"bmp => height = %d", bmp.getHeight()
						);
				
				Log.i("Adp_TIList.java" + "["
						+ Thread.currentThread().getStackTrace()[2].getLineNumber()
						+ "]", msg_Log);
				
			}//if (bmp == null)
        	
		} else {

			try {
				
				//REF http://stackoverflow.com/questions/2577221/android-how-to-create-runtime-thumbnail answered May 9 '11 at 9:31
				this.fis = new FileInputStream(this.fname_Image);
				
				bmp = BitmapFactory.decodeStream(fis);
				
//				bmp = Bitmap.createScaledBitmap(
//							bmp, 50, 50, false);
				
//				// Log
//				String msg_Log = "bmp => created from file: " 
//							+ this.fname_Image.getAbsolutePath();
//				
//				Log.d("Adp_TIList.java"
//						+ "["
//						+ Thread.currentThread().getStackTrace()[2]
//								.getLineNumber() + "]", msg_Log);
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				// Log
				String msg_Log = "Exception => " + e.toString();
				Log.e("Adp_TIList.java"
		
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", msg_Log);
				
				e.printStackTrace();
				
				bmp = 
	    				MediaStore.Images.Thumbnails.getThumbnail(
	    							cr, 
	    							ti.getFileId(), 
	    							MediaStore.Images.Thumbnails.MICRO_KIND, 
	    							null);
				
			}//try
			
//			Bitmap imageBitmap = BitmapFactory.decodeStream(fis);
			
		}//if (this.fname_Image == null)
    	
//    	// Log
//		String msg_Log;
//		
//		msg_Log = String.format(
//				Locale.JAPAN,
//				"setting imgage to image view"
//				);
//		
//		Log.i("Adp_TIList.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_Log);
    	
    	// Set bitmap
    	iv.setImageBitmap(bmp);

	}//private void move_ModeOff__Set_Bitmap()

}//public class TIListAdapter extends ArrayAdapter<TI>
