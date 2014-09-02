package ifm11.main;

import ifm11.adapters.Adp_MainList;
import ifm11.listeners.LOI_LCL;
import ifm11.listeners.button.BO_CL;
import ifm11.utils.CONS;
import ifm11.utils.DBUtils;
import ifm11.utils.Methods;
import ifm11.utils.Methods_dlg;
import ifm11.utils.Tags;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class LogActv extends ListActivity {

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
        
        setContentView(R.layout.actv_log);
        
    }//public void onCreate(Bundle savedInstanceState)

    private void do_debug() {
		

    	
	}//private void do_debug()

	@Override
	protected void onListItemClick(ListView lv, View v, int position, long id) {
		/*----------------------------
		 * Steps
		 * 0. Vibrate
		 * 
		 * 1. Get item name
		 * 2. Get file object
		 * 3. Is a directory?
		 * 		=> If yes, update the current path
			----------------------------*/
		
		super.onListItemClick(lv, v, position, id);
		
		// Log
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "onListItemClick()");
		//
		CONS.Admin.vib.vibrate(CONS.Admin.vibLength_click);
//		
//		String itemName = (String) lv.getItemAtPosition(position);
//		
//		/******************************
//			validate: null
//		 ******************************/
//		if (itemName != null) {
//			
//			// Log
//			Log.d("MainActv.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", "itemName=" + itemName);
//			
//		} else {//if (item_)
//			
//			String msg = "itemName => null";
//			Methods_dlg.dlg_ShowMessage(this, msg);
//			
//			return;
//			
////			// Log
////			Log.d("MainActv.java" + "["
////					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
////					+ "]", "item == null");
//			
//		}//if (item_)
//		

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
	protected void onStart() {
		
		super.onStart();

		boolean res;
		
		////////////////////////////////

		// debug

		////////////////////////////////
		do_debug();
		
		////////////////////////////////

		// Init vars

		////////////////////////////////
		_Setup_InitVars();
		
		////////////////////////////////

		// list

		////////////////////////////////
		res = _Setup_List();
		
		if (res == false) {
			
			return;
			
		}
		
		////////////////////////////////

		// adapter

		////////////////////////////////
		_Setup_Adapter();
		
	}//protected void onStart()

	private boolean
	_Setup_Adapter() {
		// TODO Auto-generated method stub
	
		CONS.LogActv.adp_LogFile_List = new Adp_MainList(
				this,
				R.layout.list_row_simple_1,
//				android.R.layout.simple_list_item_1,
				CONS.LogActv.list_LogFiles
				);

		
		if (CONS.LogActv.adp_LogFile_List == null) {
			
			// Log
			String msg_log = "CONS.LogActv.adp_LogFile_List => null";
			Log.d("MainActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_log);
			
			return false;
			
		}//if (adapter == null)

		this.setListAdapter(CONS.LogActv.adp_LogFile_List);
		
		return true;
		
	}//_Setup_Adapter
	

	private boolean 
	_Setup_List() {
		// TODO Auto-generated method stub
		
		File dir_Log = new File(CONS.DB.dPath_Log);
		
		/******************************
			validate: exists
		 ******************************/
		if (!dir_Log.exists()) {

			boolean res = dir_Log.mkdirs();
			
			if (res == true) {
				
				// Log
				String msg_Log = "Log dir => created: " + dir_Log.getAbsolutePath();
				Log.d("LogActv.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", msg_Log);
				
			} else {

				// Log
				String msg_Log = "Log dir => not created: " + dir_Log.getAbsolutePath();
				Log.e("LogActv.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", msg_Log);
				
				String msg = "Log dir doesn't exist\nCan't be created";
				Methods_dlg.dlg_ShowMessage(this, msg, R.color.red);
				
				return false;
				
			}
//			String msg = "Log directory => doesn't exist";
//			Methods_dlg.dlg_ShowMessage(this, msg, R.color.red);
//
//			return false;
			
		}
		
		////////////////////////////////

		// get: files list

		////////////////////////////////
		String[] list_LogFiles = dir_Log.list();
		
		/******************************
			validate: any log files
		 ******************************/
		if (list_LogFiles == null || list_LogFiles.length < 1) {
			
			String msg = "Log files => doesn't exist";
			Methods_dlg.dlg_ShowMessage(this, msg, R.color.red);

			return false;
			
		}
		
		// Log
		String msg_Log = "list_LogFiles.length => " + list_LogFiles.length;
		Log.d("LogActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		////////////////////////////////

		// build: list

		////////////////////////////////
		for (String name : list_LogFiles) {
			
			CONS.LogActv.list_LogFiles.add(name);
			
		}
		
		// Log
		msg_Log = "CONS.LogActv.list_LogFiles.size() => "
						+ CONS.LogActv.list_LogFiles.size();
		Log.d("LogActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		return true;
		
	}//_Setup_List

	private void 
	_Setup_InitVars() {
		// TODO Auto-generated method stub
		
		CONS.Admin.vib = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
		
		CONS.LogActv.list_LogFiles = new ArrayList<String>();
		
	}//_Setup_InitVars

	@Override
	public void onBackPressed() {
		/*----------------------------
		 * memo
			----------------------------*/
		this.finish();
		
		overridePendingTransition(0, 0);
		
	}//public void onBackPressed()


}
