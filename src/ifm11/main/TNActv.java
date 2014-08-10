package ifm11.main;


import ifm11.adapters.Adp_TIList;
import ifm11.items.TI;
import ifm11.utils.CONS;
import ifm11.utils.DBUtils;
import ifm11.utils.Methods;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class TNActv extends ListActivity {

	public static Vibrator vib;

	public static List<TI> tiList;

	public static Adp_TIList aAdapter;
	public static Adp_TIList bAdapter;

	public static boolean move_mode = false;

	/*********************************
	 * Special intent data
	 *********************************/
	public static long[] long_searchedItems; //=> Used in initial_setup()
	
	public static long[] history_file_ids = null;
	
	public static String[] history_table_names = null;
	
	public static String[] string_searchedItems_table_names = null;
	
	/*********************************
	 * List-related
	 *********************************/
	public static ArrayList<Integer> checkedPositions;

	public static List<String> fileNameList;
	
	public static ArrayAdapter<String> dirListAdapter;
	
	/*----------------------------
	 * Preference names
		----------------------------*/
	public static String tnactv_selected_item = "tnactv_selected_item";

	/*********************************
	 * Views
	 *********************************/
	public static ListView lv_main;
	
	/****************************************
	 * Methods
	 ****************************************/
	@Override
	public void onCreate(Bundle savedInstanceState) {
		/*----------------------------
		 * Steps
		 * 1. Super
		 * 2. Set content
		 * 3. Basics
		 * 
		 * 4. Set up
		 * 5. Initialize vars
		----------------------------*/
		super.onCreate(savedInstanceState);

		// Log
		Log.d("TNActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "onCreate()");
		
		//
		setContentView(R.layout.thumb_activity);
		
		/*----------------------------
		 * 3. Basics
			----------------------------*/
		this.setTitle(this.getClass().getName());
		
//		vib = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
//		
//		/*----------------------------
//		 * 4. Set up
//			----------------------------*/
//		set_listeners();
//		
//		set_list();
//
////		// Log
////		Log.d("TNActv.java" + "["
////				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
////				+ "]", "Table name: " + Methods.convert_path_into_table_name(this));
//		
//		/*----------------------------
//		 * 5. Initialize vars
//			----------------------------*/
//		checkedPositions = new ArrayList<Integer>();
		
	}//public void onCreate(Bundle savedInstanceState)


	@Override
	protected void onPause() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		super.onPause();
	}

	@Override
	protected void onResume() {
		/*********************************
		 * 1. super
		 * 2. Notify adapter
		 * 
		 * 3. Set selection
		 *********************************/
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		super.onResume();
		
		// Log
		Log.d("TNActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "onResume()");

		if (TNActv.aAdapter != null) {
					
			TNActv.aAdapter.notifyDataSetChanged();
			
		}
//		TNActv.aAdapter.notifyDataSetChanged();

//		/*********************************
//		 * 3. Set selection
//		 *********************************/
//		lv_main = this.getListView();
//		
//		SharedPreferences prefs = this.getSharedPreferences(
//				MainActv.prefName_tnActv,
//				MODE_PRIVATE);
//	
//
//		//Methods.PrefenceLabels.thumb_actv.name()
//		
//		//int savedPosition = prefs.getInt("chosen_list_item", -1);
//		int savedPosition = prefs.getInt(
//							MainActv.prefName_tnActv_current_image_position,
//							-1);
//		
//		int target_position = savedPosition - (lv_main.getChildCount() / 2);
//		
//		if (target_position < 0) {
//			
//			target_position = 0;
//			
//		}//if (target_position == 0)
//
//		// Log
//		Log.d("TNActv.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", "target_position=" + target_position);
//		
//		
//		lv_main.setSelection(target_position);

		
//		// Log
//		Log.d("TNActv.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", "lv_main.getCheckedItemPosition()=" + lv_main.getCheckedItemPosition());
		
	}//protected void onResume()

	@Override
	protected void onStart() {
		/*********************************
		 * 1. super
		 * 2. Set selection
		 *********************************/
		super.onStart();
		
		// Log
		Log.d("TNActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "onStart()");
		
		vib = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
		
		/*----------------------------
		 * 4. Set up
			----------------------------*/
//		set_listeners();
//		
//		set_list();

//		// Log
//		Log.d("TNActv.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", "Table name: " + Methods.convert_path_into_table_name(this));
		
		/*----------------------------
		 * 5. Initialize vars
			----------------------------*/
		checkedPositions = new ArrayList<Integer>();

	}//protected void onStart()

	@Override
	protected void onStop() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		super.onStop();
		
		// Log
		Log.d("TNActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "onStop()");

	}

	@Override
	protected void onDestroy() {
		/*********************************
		 * 1. super
		 * 2. move_mode => falsify
		 * 
		 * 3. History mode => Off
		 *********************************/
		
		super.onDestroy();
		
		// Log
		Log.d("TNActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "onDestroy()");
		
		
	}//protected void onDestroy()

	@Override
	public void onBackPressed() {
		/*----------------------------
		 * memo
			----------------------------*/
		this.finish();
		
		overridePendingTransition(0, 0);
		
	}//public void onBackPressed()

	@Override
	protected void onListItemClick(ListView lv, View v, int position, long id) {
		/*----------------------------
		 * Steps
		 * 0. Vibrate
		 * 1. Get item
		 * 2. Intent
		 * 		2.1. Set data
		 * 
		 * 2-2. Record history
		 * 2-2-a. Update data
		 * 
		 * 2-3. Save preferences
		 * 
		 * 3. Start intent
			----------------------------*/
		/*----------------------------
		 * 0. Vibrate
			----------------------------*/
		vib.vibrate(CONS.Admin.vibLength_click);
		
		
		super.onListItemClick(lv, v, position, id);
		
	}//protected void onListItemClick(ListView lv, View v, int position, long id)

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// 
		MenuInflater mi = getMenuInflater();
		mi.inflate(R.menu.thumb_actv_menu, menu);
		
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		/*----------------------------
		 * Steps
		 * 1. R.id.thumb_actv_menu_move_mode
		 * 2. R.id.thumb_actv_menu_move_files
			----------------------------*/
		
		
		case R.id.thumb_actv_menu_move_mode://---------------------------------------
			
			break;// case R.id.thumb_actv_menu_move_files
		
		case R.id.thumb_actv_menu_move_files:	//------------------------------------------
			
			if (move_mode == false) {
				
				// debug
				Toast.makeText(this, "Move mode is not on", 2000)
						.show();
				
				return false;
				
			} else if (move_mode == true) {
				/*----------------------------
				 * Steps
				 * 1. checkedPositions => Has contents?
				 * 2. If yes, show dialog
					----------------------------*/
				if (checkedPositions.size() < 1) {
					
					// debug
					Toast.makeText(TNActv.this, "No item selected", 2000).show();
					
					return false;
					
				}//if (checkedPositions.size() < 1)
				
				
				/*----------------------------
				 * 2. If yes, show dialog
					----------------------------*/
//				Methods_dlg.dlg_ChooseMoveMode(this);
//				Methods_dlg.dlg_moveFiles(this);
				
			}//if (move_mode == false)
			
			break;// case R.id.thumb_actv_menu_move_files
			
		}//switch (item.getItemId())
		
		
		
		return super.onOptionsItemSelected(item);
		
	}//public boolean onOptionsItemSelected(MenuItem item)

}//public class TNActv
