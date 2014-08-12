package ifm11.main;

import ifm11.adapters.Adp_MainList;
import ifm11.utils.CONS;
import ifm11.utils.DBUtils;
import ifm11.utils.Methods;
import ifm11.utils.Methods_dlg;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.Bundle;
import android.os.Looper;
import android.os.Vibrator;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
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

public class MainActv extends ListActivity {
	
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
        
        setContentView(R.layout.actv_main);
        
    }//public void onCreate(Bundle savedInstanceState)

    private void do_debug() {
		
    	_do_debug__Show_DBList();
//    	_do_debug__MillSec_to_TimeLabel();
//    	_do_debug__Add_LastRefreshed();
//    	_do_debug__Processing();

    	
	}//private void do_debug()

	private void 
	_do_debug__Show_DBList() {
		// TODO Auto-generated method stub
		String dst = StringUtils.join(
				new String[]{
						//REF http://stackoverflow.com/questions/9810430/get-database-path answered Jan 23 at 11:24
						this.getDatabasePath(CONS.DB.dbName).getPath()
				},
//						actv.getFilesDir().getPath() , 
//						CONS.DB.dbName},
				File.separator);

		String tmp_str = Methods.get_Dirname(this, dst);

		// Log
		String msg_Log = "tmp_str => " + tmp_str;
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		////////////////////////////////

		// file

		////////////////////////////////
		File dbDir = new File(tmp_str);
		
		String[] fnames = dbDir.list();
		
		////////////////////////////////

		// show

		////////////////////////////////
		for (String name : fnames) {
			
			// Log
			msg_Log = "name = " + name;
			Log.d("MainActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
		}
		
	}//_do_debug__Show_DBList

	private void 
	_do_debug__MillSec_to_TimeLabel() {
		// TODO Auto-generated method stub
		
		long t1 = 1407533202;
		long t2 = 1407569625;
		
		// Log
		String msg_Log = String.format(
					"t1 => %d(%s) / t2 => %d(%s)", 
					t1, Methods.conv_MillSec_to_TimeLabel(t1 * 1000),
					t2, Methods.conv_MillSec_to_TimeLabel(t2 * 1000)
				);
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
	}

	private void _do_debug__Add_LastRefreshed() {
		// TODO Auto-generated method stub
		
//		DBUtils dbu = new DBUtils(this, CONS.DB.dbName);
//		
//		SQLiteDatabase rdb = dbu.getReadableDatabase();
//
//		dbu.insert_Data_RefreshDate(rdb, 5);
//		
//		rdb.close();

		boolean res = DBUtils.insert_Data_RefreshDate(this, "2014/08/09 04:19:39.220", 1);
		
		// Log
		String msg_Log = "insertion => " + res;
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
	}

	private void 
	_do_debug__Processing() {
		// TODO Auto-generated method stub
		/***************************************
		 * Processing
		 ***************************************/
		File dname = new File("/mnt/sdcard-ext/processing");
		
		if (dname.exists()) {
			
			for (String fname : dname.list()) {
				
				// Log
				Log.d("MainActv.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber()
						+ ":"
						+ Thread.currentThread().getStackTrace()[2]
								.getMethodName() + "]",
					"fname=" + fname);
				
			}
			
		} else {//if (dname.exists())
			
			// Log
			Log.d("MainActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ ":"
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", dname.getName() + " => Doesn't exist");
			
		}//if (dname.exists())
		
	}//_do_debug__Processing()

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
			
//			// Log
//			Log.d("MainActv.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", "item == null");
			
		}//if (item_)
		
		////////////////////////////////

		// Set pref: Current position

		////////////////////////////////
		_ItemClick_SetPref_CurrentPosition(position);

		////////////////////////////////

		// Get file object

		////////////////////////////////
//		SharedPreferences prefs = 
//				this.getSharedPreferences(
//						CONS.Pref.pname_MainActv, MODE_PRIVATE);
//		
//		String currentPath = prefs.getString(CONS.Pref.pkey_CurrentPath, null);
		String currentPath = Methods.get_Pref_String(
						this, 
						CONS.Pref.pname_MainActv, 
						CONS.Pref.pkey_CurrentPath, 
						null);
		
		// Log
		String msg_Log = "currentPath = " + currentPath;
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		/******************************
			Validate: Current path => null?
		 ******************************/
		if (currentPath == null) {
			
			// Log
			msg_Log = "currentPath => null";
			Log.d("MainActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			return;
			
		}
		
		////////////////////////////////

		// File path

		////////////////////////////////
		File f = new File(currentPath, itemName);
		
		// Log
		msg_Log = "File path = " + f.getAbsolutePath();
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		
		boolean res = f.exists();
		
		/******************************
			validate: file exists?
		 ******************************/
		if (res == false) {
			
			// Log
			msg_Log = "File => doesnt exist: " + f.getAbsolutePath();
			Log.d("MainActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			// debug
			Toast.makeText(this, msg_Log, Toast.LENGTH_SHORT).show();
			
			return;
			
		}

		////////////////////////////////

		// Is file?

		////////////////////////////////
		if (f.isFile()) {
			
			////////////////////////////////

			// list.txt?

			////////////////////////////////
			if (itemName.equals(CONS.Admin.fname_List)) {
				
				Methods.start_Activity_TNActv(this);
				
			} else {
				
				String msg = "File => not list.txt";
				Methods_dlg.dlg_ShowMessage(this, msg);
				
				return;

			}
			
		} else if (f.isDirectory()) {
			
//			Ops.go_Down_Dir(this, item);
			
		} else {
			
			String msg = "File => unknown type : " + f.getName();
			Methods_dlg.dlg_ShowMessage(this, msg);
			
			return;
			
		}

	}//protected void onListItemClick(ListView l, View v, int position, long id)

	private void
	_ItemClick_SetPref_CurrentPosition(int position) {
		// TODO Auto-generated method stub
		Methods.set_Pref_Int(
				this,
				CONS.Pref.pname_MainActv,
				CONS.Pref.pkey_CurrentPosition_MainActv,
//				CONS.Pref.pkey_CurrentPosition,
				position);
		
		// Log
//		String msg_log = "Pref: " + CONS.Pref.pkey_CurrentPosition
		String msg_log = "Pref: " + CONS.Pref.pkey_CurrentPosition_MainActv
						+ " => "
						+ "Set to: " + position;
		
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_log);
		
		CONS.MainActv.aAdapter.notifyDataSetChanged();

	}

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

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		Methods.confirm_quit(this, keyCode);
		
		return super.onKeyDown(keyCode, event);
	}

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
		
		////////////////////////////////

		// debug

		////////////////////////////////
		do_debug();
		
		////////////////////////////////

		// Init vars

		////////////////////////////////
		_Setup_InitVars();
//		CONS.Admin.vib = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
//		
//		// Log
//		Log.d("[" + "MainActv.java : "
//				+ +Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", "onStart!");
//		
		////////////////////////////////

		// pref: current path

		////////////////////////////////
		_Setup_Prefs_CurrentPath();
		
		////////////////////////////////

		// root dir

		////////////////////////////////
		File rootDir = _Setup_CreateRootDir();
		
		if (rootDir == null) {
			Log.e("MainActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "file == null");
			
			return;
			
		}//if (file == null)
		
		////////////////////////////////

		// list.txt

		////////////////////////////////
		boolean res = _Setup_CreateListFile(rootDir);

		if (res == false) {
			
			Log.e("MainActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "can't create a list.txt");
			
			return;
			
		}//if (file == null)

		////////////////////////////////

		// set: pref: current path

		////////////////////////////////
		res = _Setup_InitPref_CurrentPath();
		
		if (res == false) {
			
			Log.e("MainActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "can't set pref: current path");
			
			return;
			
		}//if (file == null)
		
		////////////////////////////////

		// set: listview

		////////////////////////////////
		res = _Setup_SetList(rootDir);
		
		if (res == false) {
			
			Log.e("MainActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "can't setup list");
			
			return;
			
		}//if (file == null)
		
		////////////////////////////////

		// adapter

		////////////////////////////////
		res = _Setup_SetAdapter();
		
	}//protected void onStart()

	private void 
	_Setup_InitVars() {
		// TODO Auto-generated method stub
		
		CONS.Admin.vib = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
		
	}//_Setup_InitVars

	private boolean 
	_Setup_SetAdapter() {
		// TODO Auto-generated method stub
		
		CONS.MainActv.adp_dir_list = new ArrayAdapter<String>(
				this,
				R.layout.list_row_simple_1,
//				android.R.layout.simple_list_item_1,
				CONS.MainActv.list_RootDir
				);
		
//		Adp_MainList aAdapter = new Adp_MainList(
		CONS.MainActv.aAdapter = new Adp_MainList(
				this,
				R.layout.list_row_simple_1,
//				android.R.layout.simple_list_item_1,
				CONS.MainActv.list_RootDir
				);

		
		if (CONS.MainActv.adp_dir_list == null) {
			
			// Log
			String msg_log = "CONS.MainActv.adp_dir_list == null";
			Log.d("MainActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_log);
			
			return false;
			
		}//if (adapter == null)

		this.setListAdapter(CONS.MainActv.aAdapter);
//		this.setListAdapter(aAdapter);
//		this.setListAdapter(CONS.MainActv.adp_dir_list);
		
		return true;
		
	}//_Setup_SetAdapter()

	/******************************
		@return false => 1. root directory: Can't be built<br>
						2. File list: Can't be built
	 ******************************/
	private boolean 
	_Setup_SetList(File rootDir) {
		// TODO Auto-generated method stub
		
		if (CONS.MainActv.list_RootDir == null) {
			
			CONS.MainActv.list_RootDir = Methods.get_FileList(rootDir);

			if (CONS.MainActv.list_RootDir == null) {
				
				// Log
				String msg_log = "CONS.MainActv.list_RootDir => can't build";
				Log.d("MainActv.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", msg_log);
				
				return false;
				
			}
			
		} else {//if (this.CONS.MainActv.list_RootDir == null)
			
			String currentPath = Methods.get_Pref_String(
					this, 
					CONS.Pref.pname_MainActv, 
					CONS.Pref.pkey_CurrentPath, 
					null);
			
			if (currentPath == null) {
				
				currentPath = rootDir.getAbsolutePath();
				
				Methods.set_Pref_String(
						this, 
						CONS.Pref.pname_MainActv, 
						CONS.Pref.pkey_CurrentPath, 
						currentPath);
				
			}
			
			// Log
			String msg_Log = "currentPath => " + currentPath;
			Log.d("MainActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			CONS.MainActv.list_RootDir = Methods.get_FileList(new File(currentPath));
			
			if (CONS.MainActv.list_RootDir == null) {
				
				// Log
				String msg_log = "CONS.MainActv.list_RootDir => can't build";
				Log.d("MainActv.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", msg_log);
				
				return false;
				
			}

		}//if (this.CONS.MainActv.list_RootDir == null)
		
		return true;
		
	}//_Setup_SetList
	

	private boolean 
	_Setup_InitPref_CurrentPath() {
		// TODO Auto-generated method stub
		
		////////////////////////////////

		// Get: Pref

		////////////////////////////////
		CONS.Pref.prefs_MainActv = 
				this.getSharedPreferences(
						CONS.Pref.pname_MainActv,
						MODE_PRIVATE);
		
		////////////////////////////////

		// Prefs set already?

		////////////////////////////////
		String temp = CONS.Pref.prefs_MainActv
				.getString(CONS.Pref.pkey_CurrentPath, null);
		
		if (temp != null) {
//			if (temp != null && !temp.equals("IFM8")) {
			
			// Log
			String msg_log = "pref: current path => " + temp;
			Log.d("MainActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_log);
			
			return true;
			
		}//if (temp == null)
		
		////////////////////////////////

		// Set: base current path

		////////////////////////////////
		SharedPreferences.Editor editor = CONS.Pref.prefs_MainActv.edit();
		
		// New path
		String base_path = StringUtils.join(
				new String[]{
						CONS.Paths.dpath_Storage_Sdcard, CONS.Paths.dname_Base
				},
				File.separator);
		
		// Log
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "base_path=" + base_path);

		// Commit
		editor.putString(CONS.Pref.pkey_CurrentPath, base_path);
		
		return editor.commit();
		
//		// Log
//		String msg_log = "Bae path => Set: " + base_path;
//		Log.d("MainActv.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_log);
//		
//		
//		return false;
		
	}//_Setup_InitPref_CurrentPath()

	/******************************
		@return false => IOException
	 ******************************/
	private boolean 
	_Setup_CreateListFile
	(File rootDir) {
		// TODO Auto-generated method stub
		
		File list_file = new File(rootDir, CONS.Admin.fname_List);
		
		if (list_file.exists()) {
			
			return true;
			
		} else {//if (list_file.exists())
			
			try {
				
				return list_file.createNewFile();
//				BufferedWriter br = new BufferedWriter(new FileWriter(list_file));
//				br.close();
//				
//				return true;
				
			} catch (IOException e) {
				// Log
				Log.e("MainActv.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", "BufferedWriter: Exception => " + e.toString());
				
				return false;
			}
			
		}//if (list_file.exists())
		
	}//_Setup_CreateListFile

	private File 
	_Setup_CreateRootDir() {
		// TODO Auto-generated method stub
		
		String baseDirPath = StringUtils.join(
				new String[]{
						CONS.Paths.dpath_Storage_Sdcard, CONS.Paths.dname_Base},
				File.separator);

		File file = new File(baseDirPath);
		
		if (!file.exists()) {
			try {
				file.mkdir();
				
				// Log
				Log.d("MainActv.java"
				+ "["
				+ Thread.currentThread().getStackTrace()[2]
						.getLineNumber() + "]", "Dir created => " + file.getAbsolutePath());
				
				return file;
				
			} catch (Exception e) {
				// Log
				Log.e("MainActv.java"
				+ "["
				+ Thread.currentThread().getStackTrace()[2]
						.getLineNumber() + "]", "Exception => " + e.toString());
				
				return null;
			}
			
		} else {//if (file.exists())
			
			return file;
			
		}//if (file.exists())
		
	}//_Setup_CreateRootDir()

	private void 
	_Setup_Prefs_CurrentPath() {
		// TODO Auto-generated method stub
		////////////////////////////////

		// Get: Pref

		////////////////////////////////
		CONS.Pref.prefs_MainActv = 
				this.getSharedPreferences(
						CONS.Pref.pname_MainActv,
						MODE_PRIVATE);
		
		////////////////////////////////

		// Prefs set already?

		////////////////////////////////
		String temp = CONS.Pref.prefs_MainActv
				.getString(CONS.Pref.pkey_CurrentPath, null);
		
		if (temp != null) {
//			if (temp != null && !temp.equals("IFM8")) {
			
			// Log
			String msg_log = "Current path => " + temp;
			Log.d("MainActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_log);
			
			return;
			
		}//if (temp == null)
		
		////////////////////////////////

		// Set: base current path

		////////////////////////////////
		SharedPreferences.Editor editor = CONS.Pref.prefs_MainActv.edit();
		
		// New path
		String base_path = StringUtils.join(
				new String[]{
						CONS.Paths.dpath_Storage_Sdcard, CONS.Paths.dname_Base
				},
				File.separator);
		
		// Log
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "base_path=" + base_path);

		// Commit
		editor.putString(CONS.Pref.pkey_CurrentPath, base_path);
		
		editor.commit();
		
		// Log
		String msg_log = "Bae path => Set: " + base_path;
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_log);
		
	}//private void initPrefs_CurrentPath()

}//public class MainActv extends Activity
