package ifm11.adapters;

import ifm11.items.TI;
import ifm11.main.R;
import ifm11.utils.CONS;
import ifm11.utils.Tags;

import java.util.ArrayList;
import java.util.List;


import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
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
//	Methods.MoveMode moveMode = Methods.MoveMode.OFF;

//	public static ArrayList<Integer> checkedPositions;
	
	/*--------------------------------------------------------
	 * Constructor
		--------------------------------------------------------*/
	//
	public Adp_TIList(Context con, int resourceId, List<TI> items) {
		// Super
		super(con, resourceId, items);

		// Context
		this.con = con;

		// Inflater
		inflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		

	}//public TIListAdapter(Context con, int resourceId, List<TI> items)


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
    	/*----------------------------
		 * 0. View
			----------------------------*/
    	View v = null;

    	if (moveMode == null || moveMode == CONS.Enums.MoveMode.OFF) {
    		
    		v = move_mode_off(v, position, convertView);
    		
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
							cr, ti.getFileId(), MediaStore.Images.Thumbnails.MICRO_KIND, null);
    	
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
		
//		cb.setTag(Tags.ButtonTags.tilist_cb);
		
//		if (TNActv.checkedPositions.contains((Integer) position)) {
//			
//			cb.setChecked(true);
//			
//			// Log
//			Log.d("TIListAdapter.java"
//					+ "["
//					+ Thread.currentThread().getStackTrace()[2]
//							.getLineNumber() + "]", 
//					"cb => true" + "(position => " + TNActv.checkedPositions.size() + ")");
//			
//			
//		} else {//if (ThumbnailActivity.checkedPositions.contains((Integer) position)
//			
//			cb.setChecked(false);
//			
//		}//if (ThumbnailActivity.checkedPositions.contains((Integer) position)
		
//		cb.setOnClickListener(new ButtonOnClickListener((Activity) con, position));
//		
//		
//		cb.setOnLongClickListener(
//					new CustomOnLongClickListener(
//									(Activity) con, position, Tags.ItemTags.tilist_checkbox));

		/*----------------------------
		 * 2.7. Return
			----------------------------*/
		return v;
		
	}//private View move_mode_on()


	private View move_mode_off(View v, int position, View convertView) {
		
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

    	/*********************************
		 * 3-2. Set background
		 *********************************/
//		SharedPreferences prefs = ((Activity) con)
//						.getSharedPreferences(
//							MainActv.prefName_tnActv,
//							con.MODE_PRIVATE);
//
//		int savedPosition = prefs.getInt(
//							MainActv.prefName_tnActv_current_image_position,
//							-1);
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

    	
    	
    	/*----------------------------
		 * 4. Get bitmap
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
    	
    	// Set bitmap
    	iv.setImageBitmap(bmp);

    	/*----------------------------
		 * 5. Get memo, or, file name
		 * 		1. File name
		 * 		2. Memo
			----------------------------*/
		tv.setText(ti.getFile_name());

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

}//public class TIListAdapter extends ArrayAdapter<TI>
