package ifm11.main;


import ifm11.adapters.Adp_TIList;
import ifm11.items.TI;
import ifm11.utils.CONS;
import ifm11.utils.DBUtils;
import ifm11.utils.Methods;
import ifm11.utils.Methods_dlg;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

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
		
		////////////////////////////////

		// debug

		////////////////////////////////
		do_debug();
		
		////////////////////////////////

		// set: list

		////////////////////////////////
		boolean res = _Setup_SetList();
		
		if (res == false) {
			
			return;
			
		}

		////////////////////////////////

		// adapter

		////////////////////////////////
		_Setup_Adapter();
		
		/*----------------------------
		 * 5. Initialize vars
			----------------------------*/
		checkedPositions = new ArrayList<Integer>();

	}//protected void onStart()

	private void do_debug() {
		// TODO Auto-generated method stub
//		_do_debug__Pref_FontSize();
		
	}


	private void _do_debug__Pref_FontSize() {
		// TODO Auto-generated method stub
		
		String fontSize = Methods.get_Pref_String(
				this, 
				CONS.Pref.pname_MainActv, 
				this.getString(R.string.prefs_tnactv_list_font_size_key), 
				null);
		
		// Log
		String msg_Log = "fontSize => " + fontSize;
		Log.d("TNActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);

		
	}


	private void _Setup_Adapter() {
		// TODO Auto-generated method stub
		CONS.TNActv.adp_TNActv_Main = new Adp_TIList(
				this,
				R.layout.list_row,
//				R.layout.thumb_activity,
				CONS.TNActv.list_TNActv_Main
				);
		
//		CONS.ALActv.adp_AIList = new Adp_AIList(
//				this,
//				R.layout.list_row_ai_list,
//				CONS.ALActv.list_AI
//				);
		
		////////////////////////////////
		
		// Set adapter
		
		////////////////////////////////
		this.setListAdapter(CONS.TNActv.adp_TNActv_Main);
	
	}


	/******************************
		@return false => 1. can't build list<br>
	 ******************************/
	private boolean 
	_Setup_SetList() {
		// TODO Auto-generated method stub
		
		////////////////////////////////

		// get: pref: current path

		////////////////////////////////
		String currentPath = Methods.get_Pref_String(
				this, 
				CONS.Pref.pname_MainActv, 
				CONS.Pref.pkey_CurrentPath, 
				null);

		/******************************
			validate
		 ******************************/
		if (currentPath == null) {
			
			String msg = "Can't get current path";
			Methods_dlg.dlg_ShowMessage(this, msg);
			
			return false;
			
		}
		
		// Log
		String msg_Log = "currentPath => " + currentPath;
		Log.d("TNActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		////////////////////////////////

		// conv: to table name

		////////////////////////////////
		String tname = Methods.conv_CurrentPath_to_TableName(currentPath);
		
		// Log
		msg_Log = "tname => " + tname;
		Log.d("TNActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		////////////////////////////////

		// build: list

		////////////////////////////////
		CONS.TNActv.list_TNActv_Main = DBUtils.find_All_TI(this, tname);
		
		/******************************
			validate: null
		 ******************************/
		if (CONS.TNActv.list_TNActv_Main == null) {
			
			String msg = "list_TNActv_Main => null";
			Methods_dlg.dlg_ShowMessage(this, msg);
			
			return false;
			
		}
		
		// Log
		msg_Log = "list.size => " + CONS.TNActv.list_TNActv_Main.size();
		Log.d("TNActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		////////////////////////////////

		// sort

		////////////////////////////////
		Methods.sort_List_TI(
				CONS.TNActv.list_TNActv_Main, 
				CONS.Enums.SortType.CREATED_AT, 
				CONS.Enums.SortOrder.ASC);
		
		return true;
		
	}//_Setup_SetList


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
	protected void 
	onListItemClick
	(ListView lv, View v, int position, long id) {
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
		
		////////////////////////////////

		// get: item

		////////////////////////////////
		TI ti = (TI) lv.getItemAtPosition(position);
		
		////////////////////////////////

		// set: pref: position

		////////////////////////////////
		boolean res = Methods.set_Pref_Int(
							this, 
							CONS.Pref.pname_MainActv, 
							CONS.Pref.pkey_CurrentPosition_TNActv, 
							position);
		
		// Log
		String msg_Log = "set pref: current position => " + res;
		Log.d("TNActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		// Notify
		CONS.TNActv.adp_TNActv_Main.notifyDataSetChanged();
		
		////////////////////////////////

		// validate: file exists

		////////////////////////////////
		String fpath = StringUtils.join(
				new String[]{
						
						ti.getFile_path(),
						ti.getFile_name()
				}, 
				File.separator);
		
		res = Methods.file_Exists(this, fpath);
		
		if (res == false) {
			
			String msg = "File doesn't exist => " + fpath;
			Methods_dlg.dlg_ShowMessage(this, msg);
			
			return;
			
		}
		
		////////////////////////////////

		// Setup: intent

		////////////////////////////////
		Intent i = new Intent();
		
		i.setClass(this, ImageActv.class);
		
		
		i.putExtra("file_id", ti.getFileId());
		i.putExtra("file_path", fpath);
//		i.putExtra("file_path", ti.getFile_path());
		i.putExtra("file_name", ti.getFile_name());
		
		i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

		startActivity(i);
		
	}//onListItemClick

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
