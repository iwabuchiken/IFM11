package ifm11.adapters;

import ifm11.items.TI;
import ifm11.listener.LCL;
import ifm11.listener.button.BO_CL;
import ifm11.main.R;
import ifm11.utils.CONS;
import ifm11.utils.Methods;
import ifm11.utils.Tags;

import java.util.ArrayList;
import java.util.List;


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

public class Adp_TIList_Move extends ArrayAdapter<TI> {

	/*--------------------------------------------------------
	 * Class fields
		--------------------------------------------------------*/
	// Context
	Context con;

	// Inflater
	LayoutInflater inflater;

	//
	CONS.Enums.MoveMode moveMode = null;
//	Methods.MoveMode moveMode = Methods.MoveMode.OFF;

//	public static ArrayList<Integer> checkedPositions;
	
	/*--------------------------------------------------------
	 * Constructor
		--------------------------------------------------------*/
	//
	public 
	Adp_TIList_Move
	(Context con, int resourceId, List<TI> items) {
		// Super
		super(con, resourceId, items);

		// Context
		this.con = con;

		// Inflater
		inflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	
		// Log
		String msg_Log = "Adp_TIList_Move => created";
		Log.d("Adp_TIList_Move.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);

	}//public TIListAdapter(Context con, int resourceId, List<TI> items)


	public Adp_TIList_Move(Context con, int resourceId, List<TI> items, 
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

		v = moveMode_on(v, position, convertView);
			
		return v;
    }//public View getView(int position, View convertView, ViewGroup parent)


	private View 
	moveMode_on
	(View v, int position, View convertView) {
		
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

    	////////////////////////////////

		// bitmap

		////////////////////////////////
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
    	
    	////////////////////////////////

		// memo, file name

		////////////////////////////////
		TextView tv_FileName = (TextView) v.findViewById(R.id.list_row_checked_box_textView1);
		
		tv_FileName.setText(ti.getFile_name());

		tv_FileName.setClickable(true);

    	////////////////////////////////

		// View: File name
		// Set background: current position

		////////////////////////////////
		int savedPosition = Methods.get_Pref_Int(
								(Activity)con, 
								CONS.Pref.pname_MainActv, 
								CONS.Pref.pkey_CurrentPosition_TNActv, 
								CONS.Pref.dflt_IntExtra_value);
		
		if (savedPosition == position) {
			
			tv_FileName.setBackgroundResource(R.color.gold2);
			tv_FileName.setTextColor(Color.BLACK);
			
		} else if (savedPosition == -1) {//if (savedPosition == position)
			
		} else {//if (savedPosition == position)
			
			tv_FileName.setBackgroundColor(Color.BLACK);
			tv_FileName.setTextColor(Color.WHITE);
			
		}//if (savedPosition == position)

		// move_mode
		if (CONS.TNActv.checkedPositions.contains((Integer) position)) {
			
			tv_FileName.setBackgroundColor(Color.BLUE);
			
		} else {//if (ThumbnailActivity.move_mode == true)
				
			tv_FileName.setBackgroundColor(Color.BLACK);
				
		}
		
		////////////////////////////////

		// view: memo

		////////////////////////////////
		TextView tv_memo = (TextView) v.findViewById(R.id.list_row_checked_box_textView2);
		
		String memo = ti.getMemo();
		
		if (memo != null) {
			tv_memo.setText(memo);
			
		} else {//if (memo)

			tv_memo.setText("");
		}//if (memo)
		
		////////////////////////////////

		// view: checkbox

		////////////////////////////////
		CheckBox cb = (CheckBox) v.findViewById(R.id.list_row_checked_box_checkBox1);
		
//		cb.setTag(Tags.ButtonTags.tilist_cb);
		cb.setTag(Tags.ButtonTags.TILIST_CB);
		
		if (CONS.TNActv.checkedPositions.contains((Integer) position)) {
			
			cb.setChecked(true);
			
		} else {//if (ThumbnailActivity.checkedPositions.contains((Integer) position)
			
			cb.setChecked(false);
			
		}//if (ThumbnailActivity.checkedPositions.contains((Integer) position)
		
		cb.setOnClickListener(new BO_CL((Activity) con, position));
		
		
		cb.setOnLongClickListener(new LCL((Activity) con, position));

		/*----------------------------
		 * 2.7. Return
			----------------------------*/
		return v;
		
	}//moveMode_on

}//public class TIListAdapter extends ArrayAdapter<TI>
