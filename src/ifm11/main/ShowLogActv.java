package ifm11.main;

import ifm11.adapters.Adp_LogFileList;
import ifm11.adapters.Adp_MainList;
import ifm11.adapters.Adp_ShowLogFile_List;
import ifm11.items.LogItem;
import ifm11.utils.CONS;
import ifm11.utils.Methods;
import ifm11.utils.Methods_dlg;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

public class ShowLogActv extends ListActivity {

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	/*----------------------------
		 * 1. super
		 * 2. Set content
		 * 2-2. Set title
		 * 3. Initialize => vib
		 * 
		 *  4. Set list
		 *  5. Set listener => Image buttons
		 *  6. Set path label
		 *  
		 *  7. Initialize preferences
		 *  
		 *  8. Refresh DB
			----------------------------*/
		
        super.onCreate(savedInstanceState);
        
        this.setTitle(this.getClass().getName());
        
        setContentView(R.layout.actv_showlog);
        
    }//public void onCreate(Bundle savedInstanceState)

    private void do_debug() {
		

    	
	}//private void do_debug()

	@Override
	protected void onListItemClick(ListView lv, View v, int position, long id) {
		
		super.onListItemClick(lv, v, position, id);
		
		// Log
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "onListItemClick()");
		//
		CONS.Admin.vib.vibrate(CONS.Admin.vibLength_click);
//		
		////////////////////////////////

		// get: item name

		////////////////////////////////
		String itemName = (String) lv.getItemAtPosition(position);
		
		/******************************
			validate: null
		 ******************************/
		if (itemName != null) {
			
			// Log
			Log.d("MainActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "itemName=" + itemName);
			
		} else {//if (item_)
			
			String msg = "itemName => null";
			Methods_dlg.dlg_ShowMessage(this, msg);
			
			return;

		}//if (item_)

		////////////////////////////////

		// validate: file exists

		////////////////////////////////
		File fpath_Log = new File(CONS.DB.dPath_Log, itemName);
		
		if (!fpath_Log.exists()) {
			
			String msg = "File doesn't exist";
			Methods_dlg.dlg_ShowMessage(this, msg, R.color.red);
			
			return;
			
		}
		
		////////////////////////////////

		// start activity

		////////////////////////////////
		Methods.start_Activity_ShowLogActv(this, itemName);
		
	}//protected void onListItemClick(ListView l, View v, int position, long id)

	@Override
	protected void onDestroy() {
		/*----------------------------
		 * 1. Reconfirm if the user means to quit
		 * 2. super
		 * 3. Clear => prefs
		 * 4. Clear => file_names
			----------------------------*/
		
		super.onDestroy();
		
	}//protected void onDestroy()

//	@Override
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		
//		Methods.confirm_quit(this, keyCode);
//		
//		return super.onKeyDown(keyCode, event);
//	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// 
		MenuInflater mi = getMenuInflater();
		mi.inflate(R.menu.menu_main, menu);
		
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		
		case R.id.opt_menu_main_db://------------------------
			
			Methods_dlg.dlg_Db_Actv(this);
			
			break;
		
		case R.id.main_opt_menu_preferences://------------------
			
//			Methods_dlg.dlg_Db_Actv(this);
			Methods.start_Activity_PrefActv(this);
			
			break;
			
		case R.id.main_opt_menu_create_folder://------------------

			Methods_dlg.dlg_Create_Dir(this);
			
			break;
			
		case R.id.main_opt_menu_search://------------------
			
			Methods_dlg.dlg_SeratchItem(this);
			
			break;
			
		case R.id.main_opt_menu_others://------------------
			
			Methods_dlg.dlg_OptMenu_MainActv_Others(this);
			
			break;
			
		default://------------------------
			break;

		}//switch (item.getItemId())
		
		return super.onOptionsItemSelected(item);
		
	}//public boolean onOptionsItemSelected(MenuItem item)

	@Override
	protected void onPause() {
		
		super.onPause();

		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "onPause()");
		
	}

	@Override
	protected void onResume() {
		// TODO ?��?��?��?��?��?��?��?��?��?��?��ꂽ?��?��?��\?��b?��h?��E?��X?��^?��u
		super.onResume();

	}//protected void onResume()

	@Override
	protected void 
	onStart() {
		
		super.onStart();

		boolean res;
		
		////////////////////////////////

		// debug

		////////////////////////////////
		do_debug();
		
		////////////////////////////////

		// Init vars

		////////////////////////////////
		res = _Setup_InitVars();
		
		if (res == false) return;
		
		////////////////////////////////

		// build: list

		////////////////////////////////
		res = _Setup_List();

		if (res == false) return;

		////////////////////////////////

		// adapter

		////////////////////////////////
		res = _Setup_Adapter();
		
		if (res == false) return;
		
//		////////////////////////////////
//
//		// set: selection
//
//		////////////////////////////////
//		ListView lv = this.getListView();
//		
//		int numOfGroups = CONS.ShowLogActv.list_ShowLog_Files.size() / lv.getChildCount();
//		
//		int indexOfLastChild = lv.getChildCount() * numOfGroups;
//
//		
//		lv.setSelection(indexOfLastChild);
		
	}//protected void onStart()

	private boolean 
	_Setup_Adapter() {
		// TODO Auto-generated method stub
		////////////////////////////////

		// build: adapter

		////////////////////////////////
		CONS.ShowLogActv.adp_ShowLog_File_List = new Adp_ShowLogFile_List(
				
				this,
				R.layout.list_row_logitem,
				CONS.ShowLogActv.list_ShowLog_Files
				);

		/******************************
			validate
		 ******************************/
		if (CONS.ShowLogActv.adp_ShowLog_File_List == null) {
			
			// Log
			String msg_Log = "CONS.ShowLogActv.adp_ShowLog_File_List => null";
			Log.e("ShowLogActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			String msg = "can't build adapter";
			Methods_dlg.dlg_ShowMessage(this, msg, R.color.red);
			
			return false;
			
		}
		
		////////////////////////////////

		// set dapter

		////////////////////////////////
		this.setListAdapter(CONS.ShowLogActv.adp_ShowLog_File_List);
		
		return true;
		
	}//_Setup_Adapter
	

	/******************************
		@return
			false => 1. Log file => doesn't exist<br>
					2. CONS.ShowLogActv.list_RawLines => null<br>
	 ******************************/
	private boolean 
	_Setup_List() {
		// TODO Auto-generated method stub
		
		////////////////////////////////

		// validate: files exists

		////////////////////////////////
		File fpath_Log = new File(
				CONS.DB.dPath_Log,
				CONS.ShowLogActv.fname_Target_LogFile);
		
		if (!fpath_Log.exists()) {
			
			String msg = "Log file => doesn't exist";
			Methods_dlg.dlg_ShowMessage(this, msg, R.color.red);
			
			return false;
		}
		
		////////////////////////////////

		// read file

		////////////////////////////////
		FileInputStream fis = null;
		
//		CONS.ShowLogActv.list_RawLines = new ArrayList<String>();
		
		List<String> list = 
						Methods.get_LogLines(this, fpath_Log.getAbsolutePath());
		
		/******************************
			validate
		 ******************************/
		if (list == null) {
			
			return false;
			
		} else {
			
			CONS.ShowLogActv.list_RawLines.addAll(list);
			
		}

		////////////////////////////////

		// build: LogItem list

		////////////////////////////////
		List<LogItem> list_LogItem = 
				Methods.conv_LogLinesList_to_LogItemList(
									this, CONS.ShowLogActv.list_RawLines);

		/******************************
			validate
		 ******************************/
		if (list_LogItem == null) {
			
			// Log
			String msg_Log = "list_LogItem => null";
			Log.e("ShowLogActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			return false;
			
		} else {

			// Log
			String msg_Log = "list_LogItem => not null"
						+ "(" + list_LogItem.size() + ")";
			Log.d("ShowLogActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			CONS.ShowLogActv.list_ShowLog_Files.addAll(list_LogItem);
			
			////////////////////////////////

			// sort

			////////////////////////////////
			
			
			return true;
			
		}
		
	}//_Setup_List

	private boolean
	_Setup_InitVars() {
		// TODO Auto-generated method stub
		
		CONS.Admin.vib = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);

		CONS.ShowLogActv.list_ShowLog_Files = new ArrayList<LogItem>();
		
		CONS.ShowLogActv.list_RawLines = new ArrayList<String>();
		
		////////////////////////////////

		// intent

		////////////////////////////////
		boolean res = _Setup_InitVars__Intent();
		
		if (res == false) {
			
			return false;
		}
		
		return true;
		
	}//_Setup_InitVars

	private boolean 
	_Setup_InitVars__Intent() {
		// TODO Auto-generated method stub
		
//		Intent i = new Intent();
		Intent i = this.getIntent();
		
		CONS.ShowLogActv.fname_Target_LogFile = 
						i.getStringExtra(CONS.Intent.iKey_LogActv_LogFileName);
		
		// Log
		String msg_Log = "CONS.ShowLogActv.fname_Target_LogFile => " 
					+ CONS.ShowLogActv.fname_Target_LogFile;
		Log.d("ShowLogActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		/******************************
			validate
		 ******************************/
		if (CONS.ShowLogActv.fname_Target_LogFile == null) {
			
			String msg = "Can't get the log file name";
			Methods_dlg.dlg_ShowMessage(this, msg, R.color.red);
			
			// Log
			Log.d("ShowLogActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg);
			
			return false;
			
		}
		
		return true;
		
	}

	@Override
	public void onBackPressed() {
		/*----------------------------
		 * memo
			----------------------------*/
		this.finish();
		
		overridePendingTransition(0, 0);
		
	}//public void onBackPressed()


}
