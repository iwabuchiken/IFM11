package ifm11.main;

import ifm11.adapters.Adp_MainList;
import ifm11.items.TI;
import ifm11.listeners.LOI_LCL;
import ifm11.listeners.button.BO_CL;
import ifm11.utils.CONS;
import ifm11.utils.DBUtils;
import ifm11.utils.Methods;
import ifm11.utils.Methods_dlg;
import ifm11.utils.STD;
import ifm11.utils.Tags;

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
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.http.impl.client.DefaultHttpClient;








import android.os.Bundle;
import android.os.Looper;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.app.Activity;
import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
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
    	
//    	_do_debug__Thumbnails();
    	
//    	_do_debug__Create_ImageFileList();
//    	_do_debug__Show_DBList();
//    	_do_debug__MillSec_to_TimeLabel();
//    	_do_debug__Add_LastRefreshed();
//    	_do_debug__Processing();

    	
	}//private void do_debug()

	private void _do_debug__Thumbnails() {
		// TODO Auto-generated method stub
	
		///////////////////////////////////
		//
		// build: TI list
		//
		///////////////////////////////////
		List<TI> list_TI = DBUtils.find_All_TI(this);
		
		List<TI> list_TI_filtered = new ArrayList<TI>();
		
		List<TI> list_TI_NoTN = new ArrayList<TI>();
		
		///////////////////////////////////
		//
		// filter: 2015-06 and beyond
		//
		///////////////////////////////////
		String filter = "2015-07";
		
		for (TI ti : list_TI) {

			if (ti.getFile_name().compareToIgnoreCase(filter) > 0) {

				list_TI_filtered.add(ti);

			}//if (ti.getFile_name().compareToIgnoreCase("2015-05") > 0)
			
		}

		// report
		String msg_Log;
		
		msg_Log = String.format(
				Locale.JAPAN,
				"filtered (%s) => %d", filter, list_TI_filtered.size()
				);
		
		Log.i("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);

		
		///////////////////////////////////
		//
		// detect: thumbnails in the media store?
		//
		///////////////////////////////////
		ContentResolver cr = ((Context)this).getContentResolver();
		
		Bitmap bmp = null;
		
		for (TI ti : list_TI_filtered) {
//			for (TI ti : list_TI) {
			
        	// Bitmap
        	bmp = 
    				MediaStore.Images.Thumbnails.getThumbnail(
    							cr, 
    							ti.getFileId(), 
    							MediaStore.Images.Thumbnails.MICRO_KIND, 
    							null);

        	// get TN-less TI
        	if (bmp == null) {

        		list_TI_NoTN.add(ti);

			}//if (bmp == null)
        	
		}
		
		///////////////////////////////////
		//
		// report
		//
		///////////////////////////////////
		// Log
//		String msg_Log;
		
		msg_Log = String.format(
				Locale.JAPAN,
				"TN-less => %d", list_TI_NoTN.size()
				);
		
		Log.i("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		
	}//_do_debug__Thumbnails
	

	private void 
	_do_debug__Create_ImageFileList() {
		// TODO Auto-generated method stub
		////////////////////////////////

		// get: TI names list

		////////////////////////////////
		String order = CONS.DB.col_names_IFM11_full[1] + " DESC";
		
		List<TI> tis_List = DBUtils.find_All_TI(this, CONS.DB.tname_IFM11, order);
//		List<TI> tis_List = DBUtils.find_All_TI(this, CONS.DB.tname_IFM11);
		
		List<String> tiNames_List = new ArrayList<String>();

		for (TI ti : tis_List) {
			/******************************
				validate
			 ******************************/
			if (ti == null) {
				
				continue;
				
			} else if (ti.getFile_name() == null || ti.getFile_name().equals("")) {
				
				continue;
				
			}
			
			////////////////////////////////

			// add name

			////////////////////////////////
			tiNames_List.add(ti.getFile_name());
			
		}//for (TI ti : tis_List)

		//REF http://stackoverflow.com/questions/1694751/java-array-sort-descending answered Nov 7 '09 at 23:12
		Collections.sort(tiNames_List, Collections.reverseOrder());
		
		// Log
		String msg_Log = "tiNames_List.size() => " + tiNames_List.size();
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
//		////////////////////////////////
//
//		// get: file names list in SDCard
//
//		////////////////////////////////
//		File f = new File(CONS.DB.dPath_Data_SDCard_Camera);
//		
//		/******************************
//			validate
//		 ******************************/
//		if (!f.exists()) {
//			
//			// Log
//			msg_Log = "Camera dir => doesn't exist";
//			Log.e("MainActv.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", msg_Log);
//		}
//		
//		String[] fnames = f.list();
//		
//		Arrays.sort(fnames, Collections.reverseOrder());
//		
//		// Log
//		msg_Log = "fnames.length => " + fnames.length;
//		Log.d("MainActv.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_Log);
//		
////		for (int i = 0; i < 10; i++) {
////			
////			// Log
////			msg_Log = "name => " + fnames[i];
////			Log.d("MainActv.java" + "["
////					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
////					+ "]", msg_Log);
////		}
//		
//		////////////////////////////////
//
//		// write: sdcard file names
//
//		////////////////////////////////
//		String fname_SDCardFiles_List = "SDCardFiles_List.txt";
//		
//		f = new File(CONS.DB.dPath_Data_Root, fname_SDCardFiles_List);
//		
//		try {
//			
//			BufferedWriter bw = new BufferedWriter(new FileWriter(f));
//
//			for (int i = 0; i < fnames.length; i++) {
//				
//				bw.write(fnames[i]);
//				bw.write("\n");
//				
//			}
//			
//			bw.close();
//			
//			// Log
//			msg_Log = "Dir file names => written";
//			Log.d("MainActv.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", msg_Log);
//			
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		
		////////////////////////////////

		// sort

		////////////////////////////////
		// Log
		msg_Log = "tis_List.get(0) => "
					+ tis_List.get(0).getFile_name();
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
//		List<TI> tmp_TIs_List = new ArrayList<TI>();
//		
//		tmp_TIs_List.addAll(
//				Methods.sort_List_TI_V2(
//						tis_List, 
//						CONS.Enums.SortType.CREATED_AT, 
////						CONS.Enums.SortOrder.ASC);
//				CONS.Enums.SortOrder.DESC)
//		);
//		
//		// Log
//		msg_Log = "tmp_TIs_List.get(0) => " + tmp_TIs_List.get(0).getFile_name();
//		Log.d("MainActv.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_Log);
		
//		List<TI> tmp_TIs_List = new ArrayList<TI>();
//		
//		tmp_TIs_List.addAll(tis_List.subList(0, 10));
//		
//		Methods.sort_List_TI(
////				tmp_TIs_List, 
//				tis_List, 
//				CONS.Enums.SortType.CREATED_AT, 
////				CONS.Enums.SortOrder.ASC);
//				CONS.Enums.SortOrder.DESC);
//
//		// Log
//		msg_Log = "[sorted]tmp_TIs_List.get(0) => "
////				msg_Log = "[sorted]tis_List.get(0) => "
//					+ tmp_TIs_List.get(0).getFile_name();
////		+ tis_List.get(0).getFile_name();
//		Log.d("MainActv.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_Log);

		////////////////////////////////
		
		// write: TI names
		
		////////////////////////////////
		
		
		String fname_TIs_List = "TIs_List"
						+ "_"
						+ Methods.get_TimeLabel(STD.getMillSeconds_now())
						+ ".txt";
		
		File f = new File(CONS.DB.dPath_Data_Root, fname_TIs_List);
//		f = new File(CONS.DB.dPath_Data_Root, fname_TIs_List);
		
		try {
			
			BufferedWriter bw = new BufferedWriter(new FileWriter(f));

			String text;
			
			for (int i = 0; i < tis_List.size(); i++) {
//				for (int i = 0; i < tiNames_List.size(); i++) {
			
				text = String.format(
							Locale.JAPAN,
							"%s|%s", 
							tis_List.get(i).getFile_name(),
							tis_List.get(i).getCreated_at()
							);
				
				bw.write(text);
//				bw.write(tiNames_List.get(i));
				bw.write("\n");
				
			}
			
			bw.close();
			
			// Log
			msg_Log = "TIs_List => written";
			Log.d("MainActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}//_do_debug__Create_ImageFileList

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
		
		/******************************
			validate: null
		 ******************************/
		if (fnames == null) {
			
			// Log
			msg_Log = "fnames => null";
			Log.d("MainActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			return;
			
		}
		
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
//		aa
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
			
			Methods.go_Down_Dir(this, itemName);
			
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
		
		// Log
		String msg_Log;
		
		msg_Log = String.format(
				Locale.JAPAN,
				"calling => super.onDestroy()"
				);
		
		Log.i("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		super.onDestroy();
		
		// Log
//		String msg_Log;
		
		msg_Log = String.format(
				Locale.JAPAN,
				"onDestroy() => done"
				);
		
		Log.i("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
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
			
		case R.id.main_opt_menu_search://------------------
			
//			Methods_dlg.dlg_SeratchItem(this);
			
			Methods.start_Activity_SearchActv(this);
			
			break;
			
		case R.id.main_opt_menu_others://------------------
			
			Methods_dlg.dlg_OptMenu_MainActv_Others(this);
			
			break;
			
		case R.id.main_opt_menu_history://------------------
			
			Methods.start_Activity_HistActv(this);
			
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

//		///////////////////////////////////
//		//
//		// re-install
//		//
//		///////////////////////////////////
//		reinstall_App();
//		
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
		
		////////////////////////////////

		// listeners

		////////////////////////////////
		_Setup_SetListeners();
		
		////////////////////////////////

		// auto backup

		////////////////////////////////
		_Setup_AutoBK();
		
	}//protected void onStart()

	private void reinstall_App() {
		// TODO Auto-generated method stub
		
        // Log
		String msg_Log;
		
		//REF model name http://stackoverflow.com/questions/7071281/get-android-device-name answered Aug 15 '11 at 22:07
		msg_Log = String.format(
				Locale.JAPAN,
				"model name => %s", android.os.Build.MODEL
				);
		
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);

		///////////////////////////////////
		//
		// edit: paths
		//
		///////////////////////////////////
		///////////////////////////////////
		//
		// adjust: adjust paths by model names
		//
		///////////////////////////////////
		// data root path
		if (android.os.Build.MODEL.equals(CONS.DB.modelname_IS13SH)) {

			CONS.DB.dPath_Data_SDCard_Ext = "/mnt/sdcard";
//			CONS.DB.dPath_Data_SDCard_Ext = "/mnt/sdcard-ext";
			
			CONS.DB.dPath_Data_SDCard_Camera = 
							CONS.DB.dPath_Data_SDCard_Ext + "/dcim/Camera";
			
			CONS.DB.dPath_Data_Root = "/mnt/sdcard/ifm11_data";
//			CONS.DB.dPath_Data_Root = "/mnt/sdcard-ext/ifm11_data";
			
			CONS.DB.dPath_dbFile_backup = CONS.DB.dPath_Data_Root + "/backup";
//			CONS.DB.dPath_dbFile_backup = dPath_Data_Root + "/ifm11_backup";
//			public static String dPath_dbFile_backup = "/mnt/sdcard-ext/ifm11_backup";
			
			CONS.DB.dPath_dbFile_backup_IFM10 = 
											"/mnt/sdcard/IFM10_backup";
//			"/mnt/sdcard-ext/IFM10_backup";
			
			CONS.DB.dPath_Log = CONS.DB.dPath_Data_Root + "/log";
			
			// thumbnails
			CONS.DB.dPath_TNs = CONS.DB.dPath_Data_Root + "/tns";
			
			CONS.Paths.dpath_Storage_Sdcard = "/mnt/sdcard";
			
			CONS.Paths.dpath_Storage_Camera = "/mnt/sdcard/dcim/Camera";
			
			// Log
//			String msg_Log;
			
			msg_Log = String.format(
					Locale.JAPAN,
					"paths => modified"
					);
			
			Log.i("MainActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
		}
		
	}//reinstall_App

	private void 
	_Setup_AutoBK() {
		// TODO Auto-generated method stub
		////////////////////////////////

		// get: pref

		////////////////////////////////
		String auto = Methods.get_Pref_String(
						this, 
						CONS.Pref.pname_MainActv, 
						"prefs_tnactv_db_auto_backup_key", 
						null);
		
		if (auto == null) {
			
			// Log
			String msg_Log = "auto bk => null";
			Log.d("MainActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			return;
			
		} else if (auto.equals("")) {
			
			// Log
			String msg_Log = "auto bk => no value set";
			Log.d("MainActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			return;
			
		}
		
		//
		int auto_days = Integer.parseInt(auto);
		
		// Log
		String msg_Log = "auto_days => " + auto_days;
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);

		////////////////////////////////

		// get: last bk-ed

		////////////////////////////////
		String last_bk = DBUtils.find_LastBK(this);
		
//		// Log
//		msg_Log = "last_bk => " + last_bk;
//		Log.d("MainActv.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_Log);
		
		////////////////////////////////

		// now + auto

		////////////////////////////////
//		String now = Methods.conv_MillSec_to_TimeLabel(STD.getMillSeconds_now());
		
		String schedule = Methods.conv_MillSec_to_TimeLabel(
								Methods.conv_TimeLabel_to_MillSec(last_bk)
								+ (1 * 60 * 60 * 24 * auto_days * 1000));

		// Log
		msg_Log = String.format(
							"last = %s ** sch = %s", 
							last_bk, schedule);
		
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);

		////////////////////////////////

		// comp

		////////////////////////////////
		String now = Methods.conv_MillSec_to_TimeLabel(STD.getMillSeconds_now());
		int res = schedule.compareToIgnoreCase(now);
		
//		int res = schedule.compareToIgnoreCase(last_bk);
		
		if (res <= 0) {
//			if (res > 0) {
			
			Methods.backup_DB(this);
			
			// Log
			msg_Log = "auto bk => done";
			Log.d("MainActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
		} else {
			
			// Log
			msg_Log = "auto bk => not yet";
			Log.d("MainActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
		}
		
	}//_Setup_AutoBK

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

			////////////////////////////////

			// setup button => if not at the top dir,
			//				then, enable the button

			////////////////////////////////
			String base_path = StringUtils.join(
					new String[]{
							CONS.Paths.dpath_Storage_Sdcard, CONS.Paths.dname_Base
					},
					File.separator);

			if (!currentPath.equals(base_path)) {
				
				// Log
				msg_Log = "currentPath.equals(base_path) => false";
				Log.d("MainActv.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", msg_Log);
				
				ImageButton bt_Up = (ImageButton) this.findViewById(R.id.main_bt_up);
				
				bt_Up.setEnabled(true);
				
				bt_Up.setImageDrawable(
						this.getResources().getDrawable(R.drawable.main_up));
				
			} else {
				
				// Log
				msg_Log = "currentPath.equals(base_path) => true";
				Log.d("MainActv.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", msg_Log);
			}
			
			////////////////////////////////

			// build: dir list

			////////////////////////////////
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

			////////////////////////////////

			// add: num of items

			////////////////////////////////
//			Methods.add_NumOfItems_MainActv_List(this, currentPath);
			
//			String tname = Methods.conv_CurrentPath_to_TableName(currentPath);
//			
//			int numOfItems = DBUtils.get_NumOfEntries_TI(this, tname);
//			
//			// Log
//			msg_Log = "numOfItems => " + numOfItems;
//			Log.d("MainActv.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", msg_Log);
//			
//			for (String name : CONS.MainActv.list_RootDir) {
//				
//				if (name.equals(CONS.Admin.fname_List)) {
//					
//					name = String.format("%s (%d)", name, numOfItems);
//					
//					// Log
//					msg_Log = "yes: list.txt";
//					Log.d("MainActv.java"
//							+ "["
//							+ Thread.currentThread().getStackTrace()[2]
//									.getLineNumber() + "]", msg_Log);
//				} else {
//					
//					// Log
//					msg_Log = "no";
//					Log.d("MainActv.java"
//							+ "["
//							+ Thread.currentThread().getStackTrace()[2]
//									.getLineNumber() + "]", msg_Log);
//				}
//			}
//			
//			//debug
//			for (String name : CONS.MainActv.list_RootDir) {
//				
//				// Log
//				msg_Log = "name = " + name;
//				Log.d("MainActv.java"
//						+ "["
//						+ Thread.currentThread().getStackTrace()[2]
//								.getLineNumber() + "]", msg_Log);
//			}
			
			
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
	_Setup_SetListeners() {
		
		String msg_Log;
		
		////////////////////////////////

		// listview => long click

		////////////////////////////////
    	ListView lv = this.getListView();
		
//    	lv.setTag(Tags.ListTags.actv_main_lv);
		lv.setTag(Tags.ListTags.actv_main_lv);
		
		lv.setOnItemLongClickListener(new LOI_LCL(this));
		
		////////////////////////////////

		// imagebutton: up

		////////////////////////////////
		ImageButton bt_Up = (ImageButton) this.findViewById(R.id.main_bt_up);
		
		bt_Up.setTag(Tags.ButtonTags.ib_up);
		bt_Up.setOnClickListener(new BO_CL(this));

		String currentPath = Methods.get_Pref_String(
				this, 
				CONS.Pref.pname_MainActv, 
				CONS.Pref.pkey_CurrentPath, 
				null);
		
		// Log
		msg_Log = "currentPath => " + currentPath;
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		if (currentPath == null
				|| Methods.conv_CurrentPath_to_TableName(currentPath).equals(CONS.DB.tname_IFM11)) {
			
			// Log
			msg_Log = "current path => at the top";
			Log.d("MainActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			bt_Up.setEnabled(false);
			
		}

	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		
		// Log
		String msg_Log;
		
		msg_Log = String.format(
				Locale.JAPAN,
				"onStop()"
				);
		
		Log.i("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
	}

}//public class MainActv extends Activity
