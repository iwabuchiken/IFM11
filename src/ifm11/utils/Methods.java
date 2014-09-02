
package ifm11.utils;

import ifm11.adapters.Adp_TIList;
import ifm11.adapters.Adp_TIList_Move;
import ifm11.comps.Comp_TI;
import ifm11.items.LogItem;
import ifm11.items.TI;
import ifm11.items.WordPattern;
import ifm11.listeners.dialog.DL;
import ifm11.main.LogActv;
import ifm11.main.PrefActv;
import ifm11.main.R;
import ifm11.main.ShowLogActv;
import ifm11.main.TNActv;
import ifm11.tasks.Task_HTTP;
import ifm11.tasks.Task_Search;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.URLEncoder;
import java.nio.channels.FileChannel;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

// Apache
import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

// REF=> http://commons.apache.org/net/download_net.cgi
//REF=> http://www.searchman.info/tips/2640.html

//import org.apache.commons.net.ftp.FTPReply;

public class Methods {
	
	public static void confirm_quit(Activity actv, int keyCode) {
		
		if (keyCode==KeyEvent.KEYCODE_BACK) {
			
			AlertDialog.Builder dialog=new AlertDialog.Builder(actv);
			
	        dialog.setTitle(actv.getString(R.string.generic_tv_confirm));
	        dialog.setMessage(actv.getString(R.string.generic_tv_quit_app));
	        
	        dialog.setPositiveButton(
	        				actv.getString(R.string.generic_bt_ok),
	        				new DL(actv, dialog, 0));
	        
	        dialog.setNegativeButton(
	        				actv.getString(R.string.generic_bt_cancel),
	        				new DL(actv, dialog, 1));
	        
	        dialog.create();
	        dialog.show();
			
		}//if (keyCode==KeyEvent.KEYCODE_BACK)
		
	}//public static void confirm_quit(Activity actv, int keyCode)

	public static boolean backup_DB(Activity actv)
	{
		/****************************
		 * 1. Prep => File names
		 * 2. Prep => Files
		 * 2-2. Folder exists?
		 * 
		 * 2-3. Dst folder => Files within the limit?
		 * 3. Copy
			****************************/
		String time_label = Methods.get_TimeLabel(Methods.getMillSeconds_now());
		
		String db_Src = StringUtils.join(
					new String[]{
							actv.getDatabasePath(CONS.DB.dbName).getPath()},
//							CONS.fname_db},
					File.separator);
		
		String db_Dst_Folder = StringUtils.join(
					new String[]{
							CONS.DB.dPath_dbFile_backup,
							CONS.DB.fname_DB_Backup_Trunk},
//							CONS.dpath_db_backup,
//							CONS.fname_db_backup_trunk},
					File.separator);
		
		String db_Dst = db_Dst_Folder + "_"
				+ time_label
//				+ MainActv.fileName_db_backup_ext;
				+ CONS.DB.fname_DB_Backup_ext;
//		+ CONS.fname_db_backup_ext;
//				+ MainActv.fname_db_backup_trunk;

		// Log
		String msg_log = "db_Src = " + db_Src
						+ " / "
						+ "db_Dst = " + db_Dst;
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_log);
		
		/****************************
		 * 2. Prep => Files
			****************************/
		File src = new File(db_Src);
		File dst = new File(db_Dst);
		
		/****************************
		 * 2-2. Folder exists?
			****************************/
		File db_Backup = new File(CONS.DB.dPath_dbFile_backup);
//		File db_backup = new File(CONS.dpath_db_backup);
		
		if (!db_Backup.exists()) {
			
			try {
				
				boolean res = db_Backup.mkdirs();
//				boolean res = db_Backup.mkdir();
				
				if (res == true) {
					
					// Log
					Log.d("Methods.java" + "["
							+ Thread.currentThread().getStackTrace()[2].getLineNumber()
							+ "]", "Folder created: " + db_Backup.getAbsolutePath());
					
				} else {

					// Log
					Log.e("Methods.java" + "["
							+ Thread.currentThread().getStackTrace()[2].getLineNumber()
							+ "]", "Folder not created: " + db_Backup.getAbsolutePath());
					
				}
			} catch (Exception e) {
				
				// Log
				Log.e("Methods.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", "Create folder => Failed");
				
				return false;
				
			}
			
		} else {//if (!db_backup.exists())
			
			// Log
			Log.i("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Folder exists: ");
			
		}//if (!db_backup.exists())
		
		/*********************************
		 * 2-3. Dst folder => Files within the limit?
		 *********************************/
		File[] files_dst_folder = new File(CONS.DB.dPath_dbFile_backup).listFiles();
//		File[] files_dst_folder = new File(CONS.dpath_db_backup).listFiles();
		
		if (files_dst_folder != null) {
			
			int num_of_files = files_dst_folder.length;
			
			// Log
			Log.i("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "num of backup files = " + num_of_files);
			
		}
		
		
		/****************************
		 * 3. Copy
			****************************/
		try {
			FileChannel iChannel = new FileInputStream(src).getChannel();
			FileChannel oChannel = new FileOutputStream(dst).getChannel();
			iChannel.transferTo(0, iChannel.size(), oChannel);
			iChannel.close();
			oChannel.close();
			
			
			
			// Log
			Log.i("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "DB file copied");
			
			// debug
			Toast.makeText(actv, "DB backup => Done", Toast.LENGTH_LONG).show();

		} catch (FileNotFoundException e) {
			// Log
			Log.e("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception: " + e.toString());
			
			return false;
			
		} catch (IOException e) {
			// Log
			Log.e("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception: " + e.toString());
			
			return false;
			
		}//try

		return true;
		
	}//public static boolean db_backup(Activity actv)

	/****************************************
	 *	getMillSeconds_now()
	 * 
	 * <Caller> 
	 * 1. ButtonOnClickListener # case main_bt_start
	 * 
	 * <Desc> 1. <Params> 1.
	 * 
	 * <Return> 1.
	 * 
	 * <Steps> 1.
	 ****************************************/
	public static long getMillSeconds_now() {
		
		Calendar cal = Calendar.getInstance();
		
		return cal.getTime().getTime();
		
	}//private long getMillSeconds_now(int year, int month, int date)

	/******************************
		@return format => "yyyyMMdd_HHmmss"
	 ******************************/
	public static String get_TimeLabel(long millSec) {
		
		 SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd_HHmmss");
		 
		return sdf1.format(new Date(millSec));
		
	}//public static String get_TimeLabel(long millSec)

	public static List<String> 
	get_FileList(File dpath) {
		// TODO Auto-generated method stub
		
		////////////////////////////////

		// Directory exists?

		////////////////////////////////
				
		if (!dpath.exists()) {
			
			// Log
			Log.e("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Dir doesn't exist");
			
			return null;
			
		} else {//if (!dpath.exists() == condition)
			
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Dir exists: " + dpath.getAbsolutePath());
			
		}//if (!dpath.exists() == condition)

		////////////////////////////////

		// Get: File list

		////////////////////////////////
		
		List<String> list_Dir = new ArrayList<String>();
		
		File[] files_list = dpath.listFiles();
		
		if (files_list == null) {
			
			// Log
			String msg_log = "listFiles() => returned null";
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_log);
			
			return null;
			
		}

		////////////////////////////////

		// Sort list

		////////////////////////////////
		
		Methods.sort_list_files(files_list);
		
		for (File f : files_list) {
			
			list_Dir.add(f.getName());
			
		}//for (File f : files_list)
		
		/*********************************
		 * 3. Return
		 *********************************/
		return list_Dir;
		
	}//get_FileList

	public static void 
	sort_list_files(File[] files) {
		// REF=> http://android-coding.blogspot.jp/2011/10/sort-file-list-in-order-by-implementing.html
		/****************************
		 * 1. Prep => Comparator
		 * 2. Sort
			****************************/
		
		/****************************
		 * 1. Prep => Comparator
			****************************/
		Comparator<? super File> filecomparator = new Comparator<File>(){
			
			public int compare(File file1, File file2) {
				/****************************
				 * 1. Prep => Directory
				 * 2. Calculate
				 * 3. Return
					****************************/
				
				int pad1=0;
				int pad2=0;
				
				if(file1.isDirectory())pad1=-65536;
				if(file2.isDirectory())pad2=-65536;
				
				int res = pad2-pad1+file1.getName().compareToIgnoreCase(file2.getName());
				
				return res;
			} 
		 };//Comparator<? super File> filecomparator = new Comparator<File>()
		 
		/****************************
		 * 2. Sort
			****************************/
		Arrays.sort(files, filecomparator);

	}//public static void sort_list_files(File[] files)

	public static String 
	get_Pref_String
	(Activity actv, String pref_name,
			String pref_key, String defValue) {
		
		SharedPreferences prefs = 
				actv.getSharedPreferences(
						pref_name, Context.MODE_PRIVATE);

		/****************************
		 * Return
			****************************/
		return prefs.getString(pref_key, defValue);

	}//public static String get_Pref_String

	public static boolean
	set_Pref_String
	(Activity actv, String pName, String pKey, String value) {
		
		SharedPreferences prefs = 
				actv.getSharedPreferences(pName, Context.MODE_PRIVATE);
		
		/****************************
		 * 2. Get editor
		 ****************************/
		SharedPreferences.Editor editor = prefs.edit();
		
		/****************************
		 * 3. Set value
		 ****************************/
		editor.putString(pKey, value);
		
		try {
			
			editor.commit();
			
			return true;
			
		} catch (Exception e) {
			
			// Log
			Log.e("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Excption: " + e.toString());
			
			return false;
			
		}
		
	}//public static boolean setPref_long(Activity actv, String pref_name, String pref_key, long value)

	public static int 
	get_Pref_Int
	(Activity actv, String pref_name, String pref_key, int defValue) {
		
		SharedPreferences prefs = 
				actv.getSharedPreferences(pref_name, Context.MODE_PRIVATE);

		/****************************
		 * Return
			****************************/
		return prefs.getInt(pref_key, defValue);

	}//public static boolean set_pref(String pref_name, String value)

	/******************************
		@return true => pref set
	 ******************************/
	public static boolean 
	set_Pref_Int
	(Activity actv, 
			String pref_name, String pref_key, int value) {
		SharedPreferences prefs = 
				actv.getSharedPreferences(pref_name, Context.MODE_PRIVATE);
	
		/****************************
		 * 2. Get editor
			****************************/
		SharedPreferences.Editor editor = prefs.edit();
	
		/****************************
		 * 3. Set value
			****************************/
		editor.putInt(pref_key, value);
		
		try {
			editor.commit();
			
			return true;
			
		} catch (Exception e) {
			
			// Log
			Log.e("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Excption: " + e.toString());
			
			return false;
		}
	
	}//set_Pref_Int
	
	public static boolean
	restore_DB(Activity actv) {
    	
    	// Log
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "Starting: restore_DB()");

		/*********************************
		 * Get the absolute path of the latest backup file
		 *********************************/
		// Get the most recently-created db file
		String src_dir = CONS.DB.dPath_dbFile_backup;
//		String src_dir = "/mnt/sdcard-ext/IFM9_backup";
		
		File f_dir = new File(src_dir);
		
		File[] src_dir_files = f_dir.listFiles();
		
		// If no files in the src dir, quit the method
		if (src_dir_files.length < 1) {
			
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread()
						.getStackTrace()[2].getLineNumber()
					+ "]", "No files in the dir: " + src_dir);
			
			return false;
			
		}//if (src_dir_files.length == condition)
		
		// Latest file
		File f_src_latest = src_dir_files[0];
		
		
		for (File file : src_dir_files) {
			
			if (f_src_latest.lastModified() < file.lastModified()) {
						
				f_src_latest = file;
				
			}//if (variable == condition)
			
		}//for (File file : src_dir_files)
		
		// Show the path of the latest file
		// Log
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "f_src_latest=" + f_src_latest.getAbsolutePath());
		
		/*********************************
		 * Restore file
		 *********************************/
		String src = f_src_latest.getAbsolutePath();
		String dst = StringUtils.join(
				new String[]{
						//REF http://stackoverflow.com/questions/9810430/get-database-path answered Jan 23 at 11:24
						actv.getDatabasePath(CONS.DB.dbName).getPath()
				},
//						actv.getFilesDir().getPath() , 
//						CONS.DB.dbName},
				File.separator);
		
		boolean res = Methods.restore_DB(
							actv, 
							CONS.DB.dbName, 
							src, dst);
		
		// Log
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "res=" + res);
		
		////////////////////////////////

		// return

		////////////////////////////////
		return res;
		
	}//private void restore_DB()

	/*********************************
	 * @return true => File copied(i.e. restored)<br>
	 * 			false => Copying failed
	 *********************************/
	public static boolean
	restore_DB
	(Activity actv, String dbName, 
			String src, String dst) {
		/*********************************
		 * 1. Setup db
		 * 2. Setup: File paths
		 * 3. Setup: File objects
		 * 4. Copy file
		 * 
		 *********************************/
		// Setup db
		DBUtils dbu = new DBUtils(actv, dbName);
		
		SQLiteDatabase wdb = dbu.getWritableDatabase();
	
		wdb.close();
	
		/*********************************
		 * 2. Setup: File paths
	
		/*********************************
		 * 3. Setup: File objects
		 *********************************/
	
		/*********************************
		 * 4. Copy file
		 *********************************/
		FileChannel iChannel = null;
		FileChannel oChannel = null;
		
		try {
			iChannel = new FileInputStream(src).getChannel();
			oChannel = new FileOutputStream(dst).getChannel();
			iChannel.transferTo(0, iChannel.size(), oChannel);
			
			iChannel.close();
			oChannel.close();
			
			// Log
			Log.d("ThumbnailActivity.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "File copied: " + src);
			
			// debug
			Toast.makeText(actv, "DB restoration => Done", Toast.LENGTH_LONG).show();
			
			return true;
	
		} catch (FileNotFoundException e) {
			// Log
			Log.e("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception: " + e.toString());
			if (iChannel != null) {
				
				try {
					
					iChannel.close();
					
				} catch (IOException e1) {
					
					// Log
					Log.e("Methods.java" + "["
						+ Thread.currentThread().getStackTrace()[2].getLineNumber()
						+ "]", "Exception: " + e.toString());
	
				}
				
			}
			
			if (iChannel != null) {
				
				try {
					
					iChannel.close();
					
				} catch (IOException e1) {
					
					// Log
					Log.e("Methods.java" + "["
							+ Thread.currentThread().getStackTrace()[2].getLineNumber()
							+ "]", "Exception: " + e.toString());
					
				}
				
			}
			
			if (oChannel != null) {
				
				try {
					oChannel.close();
				} catch (IOException e1) {
					
					// Log
					Log.e("Methods.java" + "["
							+ Thread.currentThread().getStackTrace()[2].getLineNumber()
							+ "]", "Exception: " + e.toString());
					
				}
				
			}
	
			return false;
			
		} catch (IOException e) {
			// Log
			Log.e("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception: " + e.toString());
			
			if (iChannel != null) {
				
				try {
					
					iChannel.close();
					
				} catch (IOException e1) {
					
					// Log
					Log.e("Methods.java" + "["
							+ Thread.currentThread().getStackTrace()[2].getLineNumber()
							+ "]", "Exception: " + e.toString());
					
				}
				
			}
			
			if (oChannel != null) {
				
				try {
					oChannel.close();
				} catch (IOException e1) {
					
					// Log
					Log.e("Methods.java" + "["
							+ Thread.currentThread().getStackTrace()[2].getLineNumber()
							+ "]", "Exception: " + e.toString());
					
				}
				
			}
	
			
			return false;
			
		}//try
		
	}//restore_DB

	/****************************************
	 *	refreshMainDB(Activity actv)
	 * 
	 *  @return -1 => Can't create a table<br>
	 *  		-2 => Can't build cursor<br>
	 *  		-3 => No entry<br>
	 *  		-4 => Can't build TI list<br>
	 *  		0~	Number of items added
	 ****************************************/
	public static int 
	refresh_MainDB
	(Activity actv) {
		////////////////////////////////

		// Set up DB(writable)
		// Execute query for image files
		// build: TI list from cursor
		// Insert data into db
		// close: db

		////////////////////////////////
		////////////////////////////////

		// vars

		////////////////////////////////
		boolean res;
		
		////////////////////////////////

		// Set up DB(writable)

		////////////////////////////////
		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
		
		SQLiteDatabase wdb = dbu.getWritableDatabase();

		////////////////////////////////

		// Table exists?
		// If no, then create one
		//	1. baseDirName
		//	2. backupTableName

		////////////////////////////////
		res = Methods._refresh_MainDB__SetupTable(wdb, dbu);
//		boolean res = refreshMainDB_1_set_up_table(wdb, dbu);

		if (res == false) {
			
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Can't  create table");
			
			wdb.close();
			
			return -1;
			
		}//if (res == false)
		
//		//debug
//		wdb.close();
//		
//		return -1;
		
		
		////////////////////////////////

		// Execute query for image files

		////////////////////////////////
		Cursor c = _refresh_MainDB__ExecQuery(actv, wdb, dbu);
		
		/******************************
			validate: null
		 ******************************/
		if (c == null) {
			
			// Log
			String msg_Log = "can't build cursor";
			Log.e("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			return -2;
			
		}

		/******************************
			validate: any entry?
		 ******************************/
		if (c.getCount() < 1) {
			
			// Log
			String msg_Log = "No entry";
			Log.e("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			return -3;
			
		}
		
		////////////////////////////////

		// build: TI list from cursor

		////////////////////////////////
		List<TI> list_TI = Methods._refresh_MainDB__Build_TIList(actv, c);

		/******************************
			validate: null
		 ******************************/
		if (list_TI == null) {
			
			// Log
			String msg_Log = "list_TI => Null";
			Log.e("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			return -4;
			
		}
		
		////////////////////////////////

		// close: db

		////////////////////////////////
		wdb.close();
		
		////////////////////////////////

		// Insert data into db

		////////////////////////////////
		int numOfItemsAdded = _refresh_MainDB__InsertData_TIs(actv, list_TI);
//		int numOfItemsAdded = _refresh_MainDB__InsertData_Image(actv, wdb, dbu, c);
		
		// Log
		String msg_Log = "numOfItemsAdded => " + numOfItemsAdded;
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
//			
		////////////////////////////////

		// Insert: refresh date
		//		=> only if there is/are new entry/entries

		////////////////////////////////
		res = Methods._refresh_MainDB__InsertData_RefreshDate(
										actv, numOfItemsAdded, list_TI);
		
		// Log
		msg_Log = "insert refresh date => " + res;
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		return numOfItemsAdded;
		
	}//public static int refreshMainDB(Activity actv)

	private static boolean 
	_refresh_MainDB__InsertData_RefreshDate
	(Activity actv, 
			int numOfItemsAdded, List<TI> list_TI) {
		// TODO Auto-generated method stub
		////////////////////////////////

		// prep: last refresh date

		////////////////////////////////
		long lastRefreshed = -1;
		
		String label = null;
		
		for (TI ti : list_TI) {
			
			if (Methods.conv_TimeLabel_to_MillSec(ti.getDate_added())
					> lastRefreshed) {
//				if (ti.getDate_added() > lastRefreshed) {
				
				lastRefreshed = Methods.conv_TimeLabel_to_MillSec(ti.getDate_added());
//				lastRefreshed = ti.getDate_added();
				
			}
			
		}

		if (lastRefreshed == -1) {
			
			// In seconds. 
			label = Methods.conv_MillSec_to_TimeLabel(Methods.getMillSeconds_now());
			
		} else {
			
			// Converting sec to mill sec
			label = Methods.conv_MillSec_to_TimeLabel(lastRefreshed);
//			label = Methods.conv_MillSec_to_TimeLabel(lastRefreshed * 1000);
			
		}
		
		////////////////////////////////

		// save data

		////////////////////////////////
		return DBUtils.insert_Data_RefreshDate(actv, label, numOfItemsAdded);
		
//		return false;
		
	}//_refresh_MainDB__InsertData_RefreshDate
	

	private static int 
	_refresh_MainDB__InsertData_TIs
	(Activity actv, List<TI> list_TI) {
		// TODO Auto-generated method stub
		
		boolean res;
		
		int counter = 0;
		
		for (TI ti : list_TI) {
			
			res = DBUtils.insert_Data_TI(actv, ti);
			
			if (res == true) {
				
				counter += 1;
				
			}
			
		}
		
		return counter;
		
	}//_refresh_MainDB__InsertData_TIs

	/******************************
		@return Ti list => the below fields remain null<br>
				1. created_at<br>
				2. modified_at<br>
				==> these fields are to be filled later<br>
					when inserting the list into DB
	 ******************************/
	private static List<TI> 
	_refresh_MainDB__Build_TIList
	(Activity actv, Cursor c) {
		// TODO Auto-generated method stub
		
		List<TI> list_TI = new ArrayList<TI>();
		
		while(c.moveToNext()) {
			
			String time = Methods.conv_MillSec_to_TimeLabel(Methods.getMillSeconds_now());
			
			TI ti = new TI.Builder()
						.setFileId(c.getLong(0))
//						.setCreated_at(time)
//						.setModified_at(time)
						
						.setFile_name(c.getString(2))
						
						.setDate_added(
								Methods.conv_MillSec_to_TimeLabel(c.getLong(3) * 1000))
//								c.getLong(3))
						.setDate_modified(
								Methods.conv_MillSec_to_TimeLabel(c.getLong(4) * 1000))
//								c.getLong(4))
						
						.setTable_name(CONS.DB.tname_IFM11)
						.setFile_path(CONS.Paths.dpath_Storage_Camera)
						.build();
			
			list_TI.add(ti);
			
		}
		
//		// Log
//		String msg_Log = "list_TI.size => " + list_TI.size();
//		Log.d("Methods.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_Log);
		
		return list_TI;
		
	}//_refresh_MainDB__Build_TIList

	private static int 
	_refresh_MainDB__InsertData_Image
	(Activity actv, SQLiteDatabase wdb, DBUtils dbu, Cursor c) {
		/*----------------------------
		 * 4. Insert data into db
			----------------------------*/
//		int numOfItemsAdded = Methods.insertDataIntoDB(actv, MainActv.dirName_base, c);
		int numOfItemsAdded = 0;
			
//		int numOfItemsAdded = -1;
		
		/*----------------------------
		 * 5. Update table "refresh_log"
			----------------------------*/
		c.moveToPrevious();
		
		long lastItemDate = c.getLong(3);
		
//		updateRefreshLog(actv, wdb, dbu, lastItemDate, numOfItemsAdded);
		
		// Log
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "c.getLong(3) => " + c.getLong(3));
		

		return numOfItemsAdded;
		
	}//private static int refreshMainDB_3_insert_data(Cursor c)

	/******************************
		@return false => Table doesn't exist; can't create one
	 ******************************/
	private static boolean 
	_refresh_MainDB__SetupTable
	(SQLiteDatabase wdb, DBUtils dbu) {
		/*----------------------------
		 * 2-1.1. baseDirName
			----------------------------*/
		String tableName = CONS.DB.tname_IFM11;
		boolean result = dbu.tableExists(wdb, tableName);
		
		// If the table doesn't exist, create one
		if (result == false) {

			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Table doesn't exist: " + tableName);
			
			result = dbu.createTable(
							wdb, 
							tableName, 
							CONS.DB.col_names_IFM11, 
							CONS.DB.col_types_IFM11);
			
			if (result == false) {

				Log.d("Methods.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", "Can't create a table: "+ tableName);
				
				return false;
				
			} else {//if (result == false)
				
				Log.d("Methods.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", "Table created: "+ tableName);
				
				return true;
				
			}//if (result == false)

		} else {//if (result == false)
			
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Table exists: "+ tableName);

			return true;
			
		}//if (result == false)
	}//private static boolean refreshMainDB_1_set_up_table(SQLiteDatabase wdb, DBUtils dbu)

	/******************************
		@return null => 1. Can't prepare the table 'refresh log'<br>
						2. Cursor => null<br>
						3. Cursor => count < 1<br>
	 ******************************/
	private static Cursor 
	_refresh_MainDB__ExecQuery
	(Activity actv, SQLiteDatabase wdb, DBUtils dbu) {
		/*----------------------------
		 * 3. Execute query for image files
		 * 		1. ContentResolver
		 * 		2. Uri
		 * 		3. proj
		 * 		4. Last refreshed date
		 * 		5. Execute query
			----------------------------*/
		/*----------------------------
		 * 3.1. ContentResolver, Uri, proj
			----------------------------*/
		ContentResolver cr = actv.getContentResolver();
		
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        
        // Log
		String msg_Log = "uri.path => " + uri.getPath()
					+ " / "
					+ "uri.encodedPath =>" + uri.getEncodedPath()
					+ " / "
					+ "uri.getHost =>" + uri.getHost()
					;
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
        
		String[] proj = CONS.DB.proj;

		////////////////////////////////

		// setup: table: refresh log

		////////////////////////////////
		boolean res = Methods._refresh_MainDB__Setup_RefreshLog(actv, wdb, dbu);
		
		if (res == false) {
			
			// Log
			msg_Log = "Setup can't be done => refresh_log  table";
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			return null;
			
		} else {

			// Log
			msg_Log = "setup done => rehresh log";
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
		}
		
//		boolean result = dbu.tableExists(wdb, CONS.DB.tna.tableName_refreshLog);
//		
		////////////////////////////////

		// get: last refreshed date

		////////////////////////////////
//		long lastRefreshedDate = 0;		// Initial value => 0
		String lastRefreshedDate = 
				Methods._refresh_MainDB__Get_LastRefreshed(actv, wdb, dbu);
		
		long last_Refreshed;
		
		/******************************
			validate: gotten data?
		 ******************************/
		if (lastRefreshedDate == null) {
			
			last_Refreshed = 0;
			
		} else {
			
			last_Refreshed = Methods.conv_TimeLabel_to_MillSec(lastRefreshedDate);
			
		}
		
		// Log
		msg_Log = String.format(
						"last_Refreshed => %d (%s)", 
						last_Refreshed, 
						Methods.conv_MillSec_to_TimeLabel(last_Refreshed));
//		msg_Log = "lastRefreshedDate => " + lastRefreshedDate
//				+ ;
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		////////////////////////////////

		// modify: refreshed date
		//		=> convert to seconds

		////////////////////////////////
		last_Refreshed = last_Refreshed / 1000;
		
		msg_Log = String.format(
						"last_Refreshed(converted) => %d (%s)", 
						last_Refreshed, 
						Methods.conv_MillSec_to_TimeLabel(last_Refreshed));
		//msg_Log = "lastRefreshedDate => " + lastRefreshedDate
		//		+ ;
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		////////////////////////////////

		// Execute query

		////////////////////////////////
		// REF=> http://blog.csdn.net/uoyevoli/article/details/4970860
		Cursor c = actv.managedQuery(
						uri, 
						proj,
						MediaStore.Images.Media.DATE_ADDED + " > ?",
						new String[] {String.valueOf(last_Refreshed)},
//						new String[] {String.valueOf(lastRefreshedDate)},
						null);

		////////////////////////////////

		// validate

		////////////////////////////////
		/******************************
			null
		 ******************************/
		if (c == null) {
			
			// Log
			msg_Log = "cursor => null";
			Log.e("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			return null;
			
		/******************************
		no entry
		 ******************************/
		} else if (c.getCount() < 1) {
			
			// Log
			msg_Log = "EXTERNAL_CONTENT_URI => no entry";
			Log.e("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			return null;
			
		}
		
		// Log
		msg_Log = "cursor: count => " + c.getCount();
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
//		// Log
//		Log.d("Methods.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", "Last refreshed (in sec): " + String.valueOf(lastRefreshedDate / 1000));
//
//        actv.startManagingCursor(c);
//        
//        // Log
//		Log.d("Methods.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", "c.getCount() => " + c.getCount());
//
//		return c;
		
        return c;
        
	}//_refresh_MainDB__ExecQuery

	/******************************
	 * Data is stored in TEXT type. The method returns a String<br>
	 * 
		@return null => 1. query returned null<br>
						2. query found no entry<br>
	 ******************************/
	private static String 
	_refresh_MainDB__Get_LastRefreshed
	(Activity actv, SQLiteDatabase wdb, DBUtils dbu) {
		// TODO Auto-generated method stub
		
//		long lastRefreshedDate = 0;
		
		String orderBy = android.provider.BaseColumns._ID + " DESC";
		
		Cursor c = wdb.query(
				CONS.DB.tname_RefreshLog,
				CONS.DB.col_names_refresh_log_full,
//				CONS.DB.col_types_refresh_log_full,
				null, null,		// selection, args 
				null, 			// group by
				null, 		// having
				orderBy);

		/******************************
			validate: null
		 ******************************/
		if (c == null) {

			// Log
			String msg_Log = "query => null";
			Log.e("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			return null;
			
		}
		
		/******************************
			validate: any entry?
		 ******************************/
		if (c.getCount() < 1) {

			// Log
			String msg_Log = "entry => < 1";
			Log.e("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			return null;
			
		}
		
		////////////////////////////////

		// get: data

		////////////////////////////////
		c.moveToFirst();
		
		String lastRefreshed = c.getString(3);
		
		return lastRefreshed;
//		return Methods.conv_TimeLabel_to_MillSec(lastRefreshed);
		
//		return 0;
		
	}//_refresh_MainDB__Get_LastRefreshed

	private static boolean 
	_refresh_MainDB__Setup_RefreshLog
	(Activity actv, SQLiteDatabase wdb, DBUtils dbu) {
		// TODO Auto-generated method stub
		
		boolean result = dbu.tableExists(wdb, CONS.DB.tname_RefreshLog);
		
		if (result != false) {
			
			// Log
			String msg_Log = "table exists => " + CONS.DB.tname_RefreshLog;
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			return true;
			
		}
		
		////////////////////////////////

		// create: table

		////////////////////////////////
		result = dbu.createTable(
					wdb, 
					CONS.DB.tname_RefreshLog, 
					CONS.DB.col_names_refresh_log, 
					CONS.DB.col_types_refresh_log);

		if (result == true) {
			// Log
			Log.d("Methods.java"
				+ "["
				+ Thread.currentThread().getStackTrace()[2]
				.getLineNumber() + "]", "Table created => " + CONS.DB.tname_RefreshLog);
			
			return true;
			
		} else {//if (result == true)
			
			// Log
			Log.d("Methods.java"
				+ "["
				+ Thread.currentThread().getStackTrace()[2]
				.getLineNumber() + "]", 
				"Create table failed: " + CONS.DB.tname_RefreshLog);
		
			return false;
			
		}//if (result == true)
		
	}//_refresh_MainDB__Setup_RefreshLog

	public static void 
	drop_Table
	(Activity actv, 
			Dialog dlg1, Dialog dlg2, String tname) {
		// TODO Auto-generated method stub
		
		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
		
		boolean res = dbu.dropTable(actv, tname);

		////////////////////////////////

		// report

		////////////////////////////////
		if (res == true) {
			
			dlg2.dismiss();
			dlg1.dismiss();
			
			String msg = "Table dropped => " + tname;
			Methods_dlg.dlg_ShowMessage(actv, msg);
			
		} else {

			dlg2.dismiss();
			
			String msg = "Can't drop table => " + tname;
			Methods_dlg.dlg_ShowMessage(actv, msg);
			
		}
		
	}//drop_Table

	public static String
	conv_MillSec_to_TimeLabel(long millSec)
	{
		//REF http://stackoverflow.com/questions/7953725/how-to-convert-milliseconds-to-date-format-in-android answered Oct 31 '11 at 12:59
		String dateFormat = CONS.Admin.format_Date;
//		String dateFormat = "yyyy/MM/dd hh:mm:ss.SSS";
		
		DateFormat formatter = new SimpleDateFormat(dateFormat, Locale.JAPAN);
//		DateFormat formatter = new SimpleDateFormat(dateFormat);

		// Create a calendar object that will convert the date and time value in milliseconds to date. 
		Calendar calendar = Calendar.getInstance();
		
		calendar.setTimeInMillis(millSec);
		
		return formatter.format(calendar.getTime());
		
	}//conv_MillSec_to_TimeLabel(long millSec)

	public static long
	conv_TimeLabel_to_MillSec(String timeLabel)
//	conv_MillSec_to_TimeLabel(long millSec)
	{
//		String input = "Sat Feb 17 2012";
		Date date;
		try {
			date = new SimpleDateFormat(
						CONS.Admin.format_Date, Locale.JAPAN).parse(timeLabel);
			
			return date.getTime();
//			long milliseconds = date.getTime();
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			// Log
			String msg_Log = "Exception: " + e.toString();
			Log.e("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			return -1;
			
		}
		
//		Locale.ENGLISH).parse(input);
		
//		Date date = new SimpleDateFormat("EEE MMM dd yyyy", Locale.ENGLISH).parse(input);
//		long milliseconds = date.getTime();
		
	}//conv_TimeLabel_to_MillSec(String timeLabel)

	private static boolean 
	updateRefreshLog
	(Activity actv, 
			SQLiteDatabase wdb, DBUtils dbu, 
			long lastItemDate, int numOfItemsAdded) {
		////////////////////////////////

		// validate: Table exists?

		////////////////////////////////
		String tableName = CONS.DB.tname_RefreshLog;
		
		if(!dbu.tableExists(wdb, tableName)) {
		
			Log.d("Methods.java" + "["
			+ Thread.currentThread().getStackTrace()[2].getLineNumber()
			+ "]", "Table doesn't exitst: " + tableName);
		
			/*----------------------------
			* 2. If no, create one
			----------------------------*/
			if(dbu.createTable(
					wdb, tableName, 
					CONS.DB.col_names_refresh_log, 
					CONS.DB.col_types_refresh_log)) {
				
				//toastAndLog(actv, "Table created: " + tableName, Toast.LENGTH_LONG);
				
				// Log
				Log.d("Methods.java"
				+ "["
				+ Thread.currentThread().getStackTrace()[2]
				.getLineNumber() + "]", "Table created: " + tableName);
			
			} else {//if
				/*----------------------------
				* 2-2. Create table failed => Return
				----------------------------*/
				// Log
				Log.d("Methods.java"
				+ "["
				+ Thread.currentThread().getStackTrace()[2]
				.getLineNumber() + "]", "Create table failed: " + tableName);
				
				
				return false;
			
			}//if
		
		} else {//if(dbu.tableExists(wdb, ImageFileManager8Activity.refreshLogTableName))
		
			// Log
			Log.d("Methods.java" + "["
			+ Thread.currentThread().getStackTrace()[2].getLineNumber()
			+ "]", "Table exitsts: " + tableName);
		
		
		}//if(dbu.tableExists(wdb, ImageFileManager8Activity.refreshLogTableName))
		
		////////////////////////////////

		// Insert data

		////////////////////////////////
		try {
			
			return dbu.insert_Data_RefreshDate(wdb, numOfItemsAdded);
			
//			return true;
			
		} catch (Exception e) {
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Insert data failed");
			
			return false;
		}
		
	}//private static boolean updateRefreshLog(SQLiteDatabase wdb, long lastItemDate)

	public static String
	conv_CurrentPath_to_TableName(String currentPath)
	{
		String full = currentPath;
//		String full = CONS.Paths.dpath_Storage_Sdcard + CONS.Paths.dname_Base;
		
		////////////////////////////////

		// Get: raw strings

		////////////////////////////////
		String head = CONS.Paths.dpath_Storage_Sdcard;
		
		int len = head.length();
		
		String target = full.substring(len + 1);

//		// Log
//		String msg_log = "full = " + full
//						+ " // "
//						+ "target = " + target;
//		Log.d("Methods.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_log);

		////////////////////////////////

		// Split: target

		////////////////////////////////
//		// Log
//		String msg_log = "File.separator = " + File.separator;
//		Log.d("Methods.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_log);
		
		String[] tokens = target.split(File.separator);
		
		////////////////////////////////

		// Build: table name

		////////////////////////////////
		if (tokens == null) {
			
			// Log
			String msg_log = "Split => returned null";
			Log.e("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_log);
			
			return null;
			
		} else if (tokens.length == 1) {
			
			return target;
			
		} else {

			return StringUtils.join(tokens, CONS.DB.jointString_TableName);
			
		}
		
	}//conv_CurrentPath_to_TableName(String currentPath)

	public static String 
	conv_CurrentPathMove_to_TableName
	(String choice) {
		// TODO Auto-generated method stub
		
		String[] tokens = choice.split(File.separator);
		
		/******************************
			validate: null
		 ******************************/
		if (tokens == null) {
			
			// Log
			String msg_Log = "tokens => null";
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			return choice;
			
		}
		
		////////////////////////////////

		// size => 1

		////////////////////////////////
		if (tokens.length == 1) {
			
			return tokens[0];
			
		}

		////////////////////////////////

		// size > 1

		////////////////////////////////
		return StringUtils.join(tokens, CONS.DB.jointString_TableName);
		
	}//conv_CurrentPathMove_to_TableName

	public static void 
	start_Activity_TNActv
	(Activity actv) {
		// TODO Auto-generated method stub
		Intent i = new Intent();
		
		i.setClass(actv, TNActv.class);
		
		i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		
		actv.startActivity(i);
		
	}//start_Activity_ImpActv

	public static void
	sort_List_TI
	(List<TI> ti_List, 
		final CONS.Enums.SortType sortType, 
		final CONS.Enums.SortOrder sortOrder) {
		
		Comp_TI aiComp = new Comp_TI(ti_List, sortType, sortOrder);
		
		Collections.sort(ti_List, aiComp);

	}//sort_List_ai_List

	public static void 
	start_Activity_PrefActv
	(Activity actv) {
		// TODO Auto-generated method stub
		Intent i = new Intent();
		
		i.setClass(actv, PrefActv.class);
		
		i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		
		actv.startActivity(i);

	}//start_Activity_PrefActv

	
	public static boolean 
	file_Exists
	(Activity actv, String fpath) {
		// TODO Auto-generated method stub
		
		File f = new File(fpath);
		
		return f.exists();
		
	}//file_Exists

	/******************************
		@return
			false => 1. No db file<br>
	 ******************************/
	public static boolean 
	import_DB
	(Activity actv, Dialog dlg1) {
		// TODO Auto-generated method stub
		
		////////////////////////////////

		// setup: src, dst

		////////////////////////////////
		// IFM10
		String src_dir = CONS.DB.dPath_dbFile_backup_IFM10;
//		String src_dir = CONS.DB.dPath_dbFile_backup;
		
		File f_dir = new File(src_dir);
		
		File[] src_dir_files = f_dir.listFiles();
		
		// If no files in the src dir, quit the method
		if (src_dir_files.length < 1) {
			
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread()
						.getStackTrace()[2].getLineNumber()
					+ "]", "No files in the dir: " + src_dir);
			
			return false;
			
		}//if (src_dir_files.length == condition)
		
		// Latest file
		File f_src_latest = src_dir_files[0];
		
		
		for (File file : src_dir_files) {
			
			if (f_src_latest.lastModified() < file.lastModified()) {
						
				f_src_latest = file;
				
			}//if (variable == condition)
			
		}//for (File file : src_dir_files)
		
		// Show the path of the latest file
		// Log
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "f_src_latest=" + f_src_latest.getAbsolutePath());
		
		////////////////////////////////

		// Restore file

		////////////////////////////////
		String src = f_src_latest.getAbsolutePath();
		
		String dst = StringUtils.join(
				new String[]{
						//REF http://stackoverflow.com/questions/9810430/get-database-path answered Jan 23 at 11:24
						actv.getDatabasePath(CONS.DB.dbName).getPath()
				},
//						actv.getFilesDir().getPath() , 
//						CONS.DB.dbName},
				File.separator);
		
		// Log
		String msg_Log = "db path => " 
					+ actv.getDatabasePath(CONS.DB.dbName).getPath();
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		////////////////////////////////

		// build: db file path (dst)

		////////////////////////////////
		String tmp_str = Methods.get_Dirname(actv, dst);
		
		String dst_New = StringUtils.join(
					new String[]{
							
							tmp_str,
							CONS.DB.dbName_IFM10
							
					}, 
					File.separator);
		
		// Log
		msg_Log = String.format(
							"src = %s // dst = %s", 
							src, dst_New);
		
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		////////////////////////////////

		// import (using restoration-related method)

		////////////////////////////////
		boolean res = Methods.restore_DB(
							actv, 
							CONS.DB.dbName, 
							src, dst_New);
		
		// Log
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "res=" + res);
		
		////////////////////////////////

		// dismiss: dlg

		////////////////////////////////
		dlg1.dismiss();
		
		////////////////////////////////

		// return

		////////////////////////////////
		return res;

//		return false;
		
	}//import_DB

	public static String 
	get_Dirname
	(Activity actv, String target) {

		String[] tokens = target.split(File.separator);
	
		////////////////////////////////
		
		// tokens => null
		
		////////////////////////////////
		if (tokens == null) {
			
			// Log
			String msg_Log = "tokens => null";
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			return target;
			
		}
		
		////////////////////////////////

		// tokens => 1

		////////////////////////////////
		if (tokens.length == 1) {
			
			return target;
			
		}
		
		////////////////////////////////

		// tokens > 1

		////////////////////////////////
		String[] tokens_New = Arrays.copyOfRange(tokens, 0, tokens.length - 1);
		
		return StringUtils.join(tokens_New, File.separator);
	
	}//get_Dirname

	public static void 
	import_Patterns
	(Activity actv, Dialog dlg1) {
		// TODO Auto-generated method stub
	
		////////////////////////////////

		// get: patterns list

		////////////////////////////////
		List<String> patterns_List = _import_Patterns__Get_PatternsList(actv);
		
		/******************************
			validate: null
		 ******************************/
		// Log
		if (patterns_List == null) {
			
			// Log
			String msg_Log = "patterns_List => null";
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			return;
			
		}
		
		String msg_Log = "patterns_List => " + patterns_List.size();
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		////////////////////////////////

		// insert patterns

		////////////////////////////////
		int res = Methods._import_Patterns__SavePatterns(actv, patterns_List);
		
		// Log
		msg_Log = "save pattern: res => " + res;
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		////////////////////////////////

		// dismiss

		////////////////////////////////
		if (res == -1) {
			
			String msg = "Couldn't import patterns";
			Methods_dlg.dlg_ShowMessage(actv, msg, R.color.red);
			
		} else if (res == 0) {
			
			String msg = "No patterns saved";
			Methods_dlg.dlg_ShowMessage(actv, msg, R.color.gold2);
			
			dlg1.dismiss();
			
		} else if (res > 0) {
			
			String msg = "Patterns saved => " + res;
			Methods_dlg.dlg_ShowMessage(actv, msg, R.color.green4);
			
			dlg1.dismiss();
			
		} else {
			
			String msg = "Unknown result => " + res;
			Methods_dlg.dlg_ShowMessage(actv, msg, R.color.yello);
			
			dlg1.dismiss();
			
		}
		
	}//import_Patterns

	private static int 
	_import_Patterns__SavePatterns
	(Activity actv, List<String> patterns_List) {
		// TODO Auto-generated method stub
		
		////////////////////////////////

		// insert

		////////////////////////////////
		int counter = DBUtils.insert_Data_Patterns(actv, patterns_List);
			
		return counter;
		
	}//_import_Patterns__SavePatterns

	/******************************
		@return null => 1. No such table<br>
						2. Cursor => null<br>
						3. Cursor < 1 <br>
	 ******************************/
	private static List<String> 
	_import_Patterns__Get_PatternsList
	(Activity actv) {
		// TODO Auto-generated method stub
		////////////////////////////////

		// db

		////////////////////////////////
		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName_IFM10);
		
		SQLiteDatabase rdb = dbu.getReadableDatabase();

		////////////////////////////////

		// Table exists?

		////////////////////////////////
		String tableName = CONS.DB.tname_MemoPatterns;
		
		boolean res = dbu.tableExists(rdb, tableName);
		
		if (res == true) {
			
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Table exists: " + tableName);
			
		} else {//if (res == false)
			////////////////////////////////

			// no table => return

			////////////////////////////////
//			// Log
//			Log.d("Methods.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", "Table doesn't exist: " + tableName);
			
			String msg = "Table doesn't exist: " + tableName;
			Methods_dlg.dlg_ShowMessage(actv, msg);
			
			rdb.close();
			
			return null;
			
		}//if (res == false)
		
		
		////////////////////////////////

		// Get cursor

		////////////////////////////////
		// "_id"
		String orderBy = CONS.DB.col_names_MemoPatterns[0] + " ASC"; 
		
		Cursor c = rdb.query(
						CONS.DB.tname_MemoPatterns,
						CONS.DB.col_names_MemoPatterns,
		//				CONS.DB.col_types_refresh_log_full,
						null, null,		// selection, args 
						null, 			// group by
						null, 		// having
						orderBy);

		/******************************
			validate: null
		 ******************************/
		if (c == null) {
	
			String msg = "query => null";
			Methods_dlg.dlg_ShowMessage(actv, msg);
			
			rdb.close();
			
			return null;
			
		}
		
		/******************************
			validate: any entry?
		 ******************************/
		if (c.getCount() < 1) {

			String msg = "entry => < 1";
			Methods_dlg.dlg_ShowMessage(actv, msg);
			
			rdb.close();
			
			return null;
			
		}

		////////////////////////////////

		// cursor: move to first

		////////////////////////////////
		c.moveToFirst();
		
		////////////////////////////////

		// Get list

		////////////////////////////////
		List<String> patternList = new ArrayList<String>();
		
		if (c.getCount() > 0) {
			
			for (int i = 0; i < c.getCount(); i++) {
				
				patternList.add(c.getString(0));	// 0 => "word"
				
				c.moveToNext();
				
			}//for (int i = 0; i < patternList.size(); i++)
			
		} else {//if (c.getCount() > 0)
			
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "!c.getCount() > 0");
			
		}//if (c.getCount() > 0)
		
		
		Collections.sort(patternList);

		////////////////////////////////

		// return

		////////////////////////////////
		return patternList;
		
	}//_import_Patterns__Get_PatternsList

	public static void 
	add_Memo
	(Activity actv, Dialog dlg1, long db_Id) {
		// TODO Auto-generated method stub
		
		////////////////////////////////

		// get: TI

		////////////////////////////////
		TI ti = DBUtils.get_TI_From_DbId(actv, db_Id);
		
		////////////////////////////////

		// view

		////////////////////////////////
		EditText et_Content = 
				(EditText) dlg1.findViewById(R.id.dlg_add_memos_et_content);

		////////////////////////////////

		// get: text

		////////////////////////////////
		String content = et_Content.getText().toString();

		////////////////////////////////

		// update: text

		////////////////////////////////
		ti.setMemo(content);

		boolean res = DBUtils.update_TI__Memo(actv, ti);
		
		////////////////////////////////

		// report

		////////////////////////////////
		if (res == true) {
			
			String msg = "Update => done";
			Methods_dlg.dlg_ShowMessage(actv, msg);
			
			dlg1.dismiss();
			
		}
		
	}//add_Memo

	public static void 
	add_Memo_to_GridView
	(Activity actv, Dialog dlg1,
			String word) {
		// TODO Auto-generated method stub
		
		////////////////////////////////

		// get: view

		////////////////////////////////
		EditText et_Content = 
				(EditText) dlg1.findViewById(R.id.dlg_add_memos_et_content);

		////////////////////////////////

		// get: text

		////////////////////////////////
		String content = et_Content.getText().toString();

		////////////////////////////////

		// update: text

		////////////////////////////////
		String content_New = content + word;

		// modify
		if (!word.equals(" ")) {
			
			content_New += " ";
			
		}
		
		et_Content.setText(content_New);
		
		et_Content.setSelection(content_New.length());

		
	}//add_Memo_to_GridView

	public static void 
	create_Dir
	(Activity actv, Dialog dlg1, Dialog dlg2) {
		// TODO Auto-generated method stub
		
		/******************************
			1. Create dir	=> Dir exists?
			2. Create a table	=> table exists?
			3. Re-build the listview
			4. Notify the adapter
		 ******************************/
		////////////////////////////////

		// get: dir name

		////////////////////////////////
		TextView tv = (TextView) dlg2.findViewById(
							R.id.dlg_confirm_create_folder_tv_table_name);
		
		String dname_New = tv.getText().toString();
		
		////////////////////////////////

		// validate: pref

		////////////////////////////////
		String currentPath = Methods.get_Pref_String(
				actv, 
				CONS.Pref.pname_MainActv, 
				CONS.Pref.pkey_CurrentPath, 
				null);
		
		if (currentPath == null) {
//			if (CONS.MainActv.prefval_CurrentPath == null) {
			
			// Log
			String msg_Log = "currentPath == null";
//			String msg_Log = "CONS.MainActv.prefval_CurrentPath == null";
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
//			String path = StringUtils.join(
			currentPath = StringUtils.join(
								new String[]{
										CONS.Paths.dpath_Storage_Sdcard, 
										CONS.Paths.dname_Base
								},
								File.separator);
			
			Methods.set_Pref_String(
					actv, 
					CONS.Pref.pname_MainActv, 
					CONS.Pref.pkey_CurrentPath, 
					currentPath);
			
//			CONS.MainActv.prefval_CurrentPath = path;
			
		}

		////////////////////////////////
		
		// build: file path
		
		////////////////////////////////
		File newDir = new File(currentPath, dname_New);
//		File newDir = new File(CONS.MainActv.prefval_CurrentPath, dname_New);
		
		// Log
		String msg_Log = "new dir path => " + newDir.getAbsolutePath();
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		

		////////////////////////////////

		// new dir: exists?

		////////////////////////////////
		boolean dirExists = newDir.exists();

		if (dirExists == false) {
			
			boolean tmp_b = newDir.mkdir();
			
			if (tmp_b == true) {
				
				// Log
				msg_Log = "new dir created: " + newDir.getAbsolutePath();
				Log.d("Methods.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", msg_Log);
				
			} else {
				
				dlg2.dismiss();
				
				String message = "Can't create dir: " + newDir.getName();
				
				Methods_dlg.dlg_ShowMessage(actv, message, R.color.red);
				
//				Dialog dlg3 = Methods_dlg.dlg_Template_Cancel(
//									actv, 
//									R.layout.dlg_tmpl_message_simple, 
//									R.string.generic_notice, 
//									R.id.dlg_tmpl_message_simple_btn_ok, 
//									Tags.DialogTags.DLG_GENERIC_DISMISS);
//				
//				TextView tv_Message = (TextView) dlg3.findViewById(
//								R.id.dlg_tmpl_message_simple_tv_message);
//				
//				tv_Message.setText("Can't create dir: " + newDir.getName());
//				
//				dlg3.show();
				
				return;

			}
			
		} else {
			
			// Log
			msg_Log = "Dir exists => " + newDir.getAbsolutePath();
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
//			dlg2.dismiss();
//			
//			return;
			
		}

		////////////////////////////////

		// created: list.txt

		////////////////////////////////
		File listFile = new File(
							newDir.getAbsolutePath(), 
							CONS.Admin.fname_List);

		boolean fileExists = listFile.exists();
		
		if (fileExists == true) {
			
			// Log
			msg_Log = "list.txt => exists";
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
		} else {
			
			try {
				
				listFile.createNewFile();
				
				// Log
				msg_Log = "list.txt => Created";
				Log.d("Methods.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", msg_Log);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				dlg2.dismiss();
				
				String message = "Can't create list.txt";
				
				Methods_dlg.dlg_ShowMessage(actv, message, R.color.red);
				
//				Dialog dlg3 = Methods_dlg.dlg_Template_Cancel(
//									actv, 
//									R.layout.dlg_tmpl_message_simple, 
//									R.string.generic_notice, 
//									R.id.dlg_tmpl_message_simple_btn_ok, 
//									Tags.DialogTags.DLG_GENERIC_DISMISS);
//				
//				TextView tv_Message = (TextView) dlg3.findViewById(
//								R.id.dlg_tmpl_message_simple_tv_message);
//				
//				tv_Message.setText("Can't create list.txt");
//				
//				dlg3.show();
				
				return;
//				e.printStackTrace();
				
			}//try

		}//if (fileExists == true)
		
//		////////////////////////////////
//
//		// create: table
//
//		////////////////////////////////
//		String tname_New = 
//				Methods.conv_CurrentPath_to_TableName(newDir.getAbsolutePath());
//		
//		// Log
//		msg_Log = "tname_New => " + tname_New;
//		Log.d("Methods.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_Log);
//		
//		Methods.create_Table_Audio(actv, tname_New);
////		Methods.create_Table(actv, tname_New);
		
		////////////////////////////////

		// rebuild: listview

		////////////////////////////////
		File currentDir = new File(currentPath);
		CONS.MainActv.list_RootDir.clear();
		
		List<String> tmp_List = Methods.get_FileList(currentDir);
//		Collections.sort(tmp_List);
		
		CONS.MainActv.list_RootDir.addAll(tmp_List);
		
		CONS.MainActv.aAdapter.notifyDataSetChanged();

		////////////////////////////////

		// dismiss

		////////////////////////////////
		dlg1.dismiss();
		dlg2.dismiss();

	}//create_Dir(Activity actv)

	public static void 
	del_Folder
	(Activity actv, Dialog dlg1, Dialog dlg2,
			String folderName) {
		// TODO Auto-generated method stub
		
		String currentPath = Methods.get_Pref_String(
									actv, 
									CONS.Pref.pname_MainActv, 
									CONS.Pref.pkey_CurrentPath, 
									null);
		
		// Log
		String msg_Log = "currentPath => " + currentPath;
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		File target_Dir = new File(currentPath, folderName);
		
		/******************************
			validate: exists?
		 ******************************/
		if (!target_Dir.exists()) {
			
			String msg = "Folder doesn't exist: " + folderName;
			Toast.makeText(actv, msg, Toast.LENGTH_SHORT).show();
//			Methods_dlg.dlg_ShowMessage(actv, msg);
			
			dlg2.dismiss();
			
			return;
			
		}
		
		////////////////////////////////

		// del: folder

		////////////////////////////////
		boolean res = Methods.deleteDirectory(target_Dir);
		
		if (res == false) {
			
			String msg = "delete dir => not done";
			Toast.makeText(actv, msg, Toast.LENGTH_SHORT).show();
//			Methods_dlg.dlg_ShowMessage(actv, msg);
			
			dlg2.dismiss();
			
			return;
			
		}
		
//		////////////////////////////////
//
//		// del: table
//
//		////////////////////////////////
//		String tname = Methods.conv_CurrentPath_to_TableName(target_Dir.getAbsolutePath());
//		
//		res = Methods.drop_Table(actv, tname);
//		
//		if (res == false) {
//			
//			String msg = "Table => not dropped: " + tname;
//			Methods_dlg.dlg_ShowMessage(actv, msg);
//			
//		}
		
		////////////////////////////////

		// del: list item

		////////////////////////////////
		CONS.MainActv.list_RootDir.remove(folderName);
//		CONS.MainActv.list_RootDir.clear();
//		
//		CONS.MainActv.list_RootDir.addAll(
//						Methods.get_FileList(new File(currentPath)));
		
		////////////////////////////////

		// notify

		////////////////////////////////
		CONS.MainActv.aAdapter.notifyDataSetChanged();
//		CONS.MainActv.adp_dir_list.notifyDataSetChanged();
		
		// Log
		msg_Log = "adapter => notified";
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		////////////////////////////////
		
		// dismiss
		
		////////////////////////////////
		dlg2.dismiss();
		dlg1.dismiss();
		
		
	}//del_Folders

	/****************************
	 * deleteDirectory(File target)()
	 * 
	 * 1. REF=> http://www.rgagnon.com/javadetails/java-0483.html
		****************************/
	public static boolean 
	deleteDirectory
	(File target) {
		
		if(target.exists()) {
			//
			File[] files = target.listFiles();
			
			//
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					
					deleteDirectory(files[i]);
					
				} else {//if (files[i].isDirectory())
					
					String path = files[i].getAbsolutePath();
					
					files[i].delete();
					
					// Log
					Log.d("Methods.java"
							+ "["
							+ Thread.currentThread().getStackTrace()[2]
									.getLineNumber() + "]", "Removed => " + path);
					
					
				}//if (files[i].isDirectory())
				
			}//for (int i = 0; i < files.length; i++)
			
		}//if(target.exists())
		
		return (target.delete());
	}//public static boolean deleteDirectory(File target)

	public static boolean drop_Table
	(Activity actv, String tname) {
		// TODO Auto-generated method stub

		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
		
		return dbu.dropTable(actv, tname);
		
	}

	public static void 
	move_Mode
	(Activity actv, MenuItem item) {
		// TODO Auto-generated method stub

		// Log
		String msg_Log = "CONS.TNActv.moveMode => " + CONS.TNActv.moveMode;
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		////////////////////////////////

		// dispatch

		////////////////////////////////
		if (CONS.TNActv.moveMode == true) {
			
			_moveMode_True(actv, item);
			
		} else {// move_mode => false
			
			_moveMode_False(actv, item);
			
		}//if (move_mode == true)

		
	}//move_Mode

	/******************************
		this method is used when<br>
			=> changing from move_mode off to<br>
				move_mode on<br>
				i.e. when you want to move files
	 ******************************/
	private static void 
	_moveMode_False
	(Activity actv, MenuItem item) {
		// TODO Auto-generated method stub
		////////////////////////////////
		
		// get: environs
		
		////////////////////////////////
		String currentPath = Methods.get_Pref_String(
				actv, 
				CONS.Pref.pname_MainActv, 
				CONS.Pref.pkey_CurrentPath, 
				null);
		
		/******************************
			validate: null
		 ******************************/
		if (currentPath == null) {
			
			String msg = "Current path => null";
			Methods_dlg.dlg_ShowMessage(actv, msg, R.color.red);
			
			return;
			
		}
		
		
		String tableName = Methods.conv_CurrentPath_to_TableName(currentPath);
	
		////////////////////////////////

		// icon => change

		////////////////////////////////
		item.setIcon(R.drawable.ifm8_thumb_actv_opt_menu_move_mode_on);
		
		if (CONS.TNActv.menu != null) {
			
			// Log
			String msg_Log = "CONS.TNActv.menu => not null";
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
		}
		
		////////////////////////////////

		// move mode

		////////////////////////////////
		CONS.TNActv.moveMode = true;

		////////////////////////////////

		// prep: list type

		////////////////////////////////
		CONS.TNActv.list_TNActv_Main.clear();
		
		// List type
		String listType = Methods.get_Pref_String(
				actv, 
				CONS.Pref.pname_MainActv, 
				CONS.Pref.pkey_TNActv__ListType, 
				null);
		
		if (listType == null) {
			
			listType = CONS.Enums.ListType.STANDARD.toString();
			
		}

		////////////////////////////////

		// rebuild: tiList

		////////////////////////////////
		if (listType.equals(
						CONS.Enums.ListType.SEARCH.toString())
				&& CONS.TNActv.searchedItems != null) {
			
			CONS.TNActv.list_TNActv_Main.addAll(
					_moveMode_False__Search(actv, tableName));
			
		} else {

			CONS.TNActv.list_TNActv_Main.addAll(DBUtils.find_All_TI(actv, tableName));
			
		}

		
		Methods.sort_List_TI(
						CONS.TNActv.list_TNActv_Main, 
						CONS.Enums.SortType.CREATED_AT, 
						CONS.Enums.SortOrder.ASC);
		
		////////////////////////////////

		// adapter

		////////////////////////////////
		CONS.TNActv.adp_TNActv_Main_Move = new Adp_TIList_Move(
				actv,
				R.layout.list_row_checked_box,
//				R.layout.list_row,
//				R.layout.thumb_activity,
				CONS.TNActv.list_TNActv_Main
				);
		
		
		
		////////////////////////////////
		
		// Set adapter
		
		////////////////////////////////
		((ListActivity) actv).setListAdapter(CONS.TNActv.adp_TNActv_Main_Move);

	}//_moveMode_False

	private static List<TI> 
	_moveMode_False__Search
	(Activity actv, String tableName) {
		// TODO Auto-generated method stub
		
		////////////////////////////////

		// build: TI list

		////////////////////////////////
		List<TI> list_TNActv_Main = DBUtils.find_All_TI__Search(actv);
//		CONS.TNActv.list_TNActv_Main = DBUtils.find_All_TI__Search(this);
		
		if (list_TNActv_Main == null) {
//			if (list_TNActv_Main == null) {
			
			list_TNActv_Main = DBUtils.find_All_TI(actv, tableName);
			
		}
		
		return list_TNActv_Main;
		
	}//_moveMode_False__Search

	/******************************
		this method is used when<br>
			=> changing from move_mode on to<br>
				move_mode off<br>
				i.e. when you want to go back to<br>
				non-moving mode
	 ******************************/
	private static void 
	_moveMode_True
	(Activity actv, MenuItem item) {
		// TODO Auto-generated method stub

		////////////////////////////////
		
		// get: environs
		
		////////////////////////////////
		String currentPath = Methods.get_Pref_String(
				actv, 
				CONS.Pref.pname_MainActv, 
				CONS.Pref.pkey_CurrentPath, 
				null);
		
		/******************************
			validate: null
		 ******************************/
		if (currentPath == null) {
			
			String msg = "Current path => null";
			Methods_dlg.dlg_ShowMessage(actv, msg, R.color.red);
			
			return;
			
		}
		
		
		String tableName = Methods.conv_CurrentPath_to_TableName(currentPath);
		
		////////////////////////////////
		
		// icon => change
		
		////////////////////////////////
		item.setIcon(R.drawable.ifm8_thumb_actv_opt_menu_move_mode_off);

		if (CONS.TNActv.menu != null) {
			
			// Log
			String msg_Log = "CONS.TNActv.menu => not null";
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
		}

		////////////////////////////////
		
		// move mode
		
		////////////////////////////////
		CONS.TNActv.moveMode = false;

		////////////////////////////////

		// rebuild: tiList

		////////////////////////////////
		CONS.TNActv.list_TNActv_Main.clear();
		
		List<TI> ti_List_Move = DBUtils.find_All_TI(actv, tableName);
		
		if (ti_List_Move != null) {
			
			CONS.TNActv.list_TNActv_Main.addAll(ti_List_Move);
//			DBUtils.find_All_TI(actv, cur_TableName));
			
		}

//		CONS.TNActv.list_TNActv_Main.addAll(DBUtils.find_All_TI(actv, tableName));
		
		Methods.sort_List_TI(
						CONS.TNActv.list_TNActv_Main, 
						CONS.Enums.SortType.CREATED_AT, 
						CONS.Enums.SortOrder.ASC);
		
		////////////////////////////////

		// checked positions

		////////////////////////////////
		if (CONS.TNActv.checkedPositions != null) {
			
			CONS.TNActv.checkedPositions.clear();
			
		}
		
		////////////////////////////////

		// adapter

		////////////////////////////////
		CONS.TNActv.adp_TNActv_Main = new Adp_TIList(
				actv,
				R.layout.list_row,
//			R.layout.thumb_activity,
				CONS.TNActv.list_TNActv_Main
				);
		
		////////////////////////////////
		
		// Set adapter
		
		////////////////////////////////
		((ListActivity) actv).setListAdapter(CONS.TNActv.adp_TNActv_Main);
		
		
	}//_moveMode_True

	/******************************
		@return null => 1. dpath_Target ==> Dir doesn't exist<br>
						2. listFiles ==> returned null
	 ******************************/
	public static List<String> get_DirList(String dpath_Target) {
		/*********************************
		 * 1. Directory exists?
		 * 2. Build list
		 * 2-1. Sort list
		 * 
		 * 3. Return
		 *********************************/
		File dir_Target = new File(dpath_Target);
		
		////////////////////////////////
		
		// Directory exists?
		
		////////////////////////////////
		
		if (!dir_Target.exists()) {
			
			// Log
			Log.e("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Dir doesn't exist");
			
			return null;
			
		} else {//if (!dpath.exists() == condition)
			
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Dir exists: " + dir_Target.getAbsolutePath());
			
		}//if (!dpath.exists() == condition)
		
		////////////////////////////////
		
		// Get: Dir list (Directories only)
		
		////////////////////////////////
		List<String> list_Dir = new ArrayList<String>();
		
		File[] files_list = dir_Target.listFiles(new FileFilter(){
	
			@Override
			public boolean accept(File f) {
				
				return f.isDirectory();
				
			}
			
		});
		
		if (files_list == null) {
			
			// Log
			String msg_log = "listFiles() => returned null";
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_log);
			
			return null;
			
		}
		
		////////////////////////////////
		
		// Sort list
		
		////////////////////////////////
		
		Methods.sort_list_files(files_list);
		
		for (File f : files_list) {
			
			list_Dir.add(f.getName());
			
		}//for (File f : files_list)
		
		/*********************************
		 * 3. Return
		 *********************************/
		return list_Dir;
		
	}//public static List<String> get_file_list(File dpath)

//<<<<<<< HEAD
	public static void 
	move_Files
	(Activity actv, 
			Dialog dlg1, Dialog dlg2, Dialog dlg3) {

		////////////////////////////////

		// get: choice

		////////////////////////////////
		TextView tv_ItemName = 
				(TextView) dlg3.findViewById(R.id.dlg_tmpl_confirm_simple_tv_item_name);
		
		String choice = tv_ItemName.getText().toString();
		
		// Log
		String msg_Log = "target path => " + choice;

		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		////////////////////////////////

		// conv: choice string to => table name

		////////////////////////////////
		String tname_New = Methods.conv_CurrentPathMove_to_TableName(choice);
		
		// Log
		msg_Log = "tname_New => " + tname_New;
		
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		////////////////////////////////

		// build: TI list from => checkedPositions

		////////////////////////////////
		List<TI> toMoveFiles = _move_Files__Get_ToMoveList();
		
		// Log
		msg_Log = "toMoveFiles.size => " + toMoveFiles.size();
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);

		////////////////////////////////

		// update: table name

		////////////////////////////////
		for (TI ti : toMoveFiles) {
			
			ti.setTable_name(tname_New);
			
		}
		
		////////////////////////////////

		// update: DB

		////////////////////////////////
		int counter = DBUtils.update_TI_All__TableName(actv, toMoveFiles);
		
		// Log
		msg_Log = "moved => " + counter;
		
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		////////////////////////////////

		// clear: checkedPositions

		////////////////////////////////
		CONS.TNActv.checkedPositions.clear();
		
		////////////////////////////////

		// re-build: TI list

		////////////////////////////////
		// current path
		String currentPath = Methods.get_Pref_String(
				actv, 
				CONS.Pref.pname_MainActv, 
				CONS.Pref.pkey_CurrentPath, 
				null);

		/******************************
			validate: null
		 ******************************/
		if (currentPath == null) {
			
			String msg = "Can't get current path";
			Methods_dlg.dlg_ShowMessage(actv, msg, R.color.red);
			
			return;
			
		}
		
		////////////////////////////////

		// re-build: TI list (move)

		////////////////////////////////
		// conv to => table
		String cur_TableName = Methods.conv_CurrentPath_to_TableName(currentPath);
		
		// list
		CONS.TNActv.list_TNActv_Main.clear();
		
		List<TI> ti_List_Move = DBUtils.find_All_TI(actv, cur_TableName);
		
		if (ti_List_Move != null) {
			
			CONS.TNActv.list_TNActv_Main.addAll(ti_List_Move);
//			DBUtils.find_All_TI(actv, cur_TableName));
			
		}
		
		////////////////////////////////
	
		// notify
	
		////////////////////////////////
		CONS.TNActv.adp_TNActv_Main_Move.notifyDataSetChanged();
		
		// Log
		msg_Log = "adapter(move) => notified";
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		////////////////////////////////
	
		// dismiss dlgs
	
		////////////////////////////////
		dlg3.dismiss();
		dlg2.dismiss();
		dlg1.dismiss();
		
	}//public static void move_Files

	public static CharSequence conv_CurrentPath_to_DisplayPath(String path) {
		// TODO Auto-generated method stub
		
		String head = CONS.Paths.dpath_Storage_Sdcard;
		
		int len = head.length();
		
		return path.substring(len + 1);
		
	}

	public static void 
	go_Up_Dir
	(Activity actv) {
		// TODO Auto-generated method stub
		
		////////////////////////////////
		
		// new file
		
		////////////////////////////////
		String currentPath = Methods.get_Pref_String(
				actv, 
				CONS.Pref.pname_MainActv, 
				CONS.Pref.pkey_CurrentPath, 
				null);

		/******************************
			validate: null
		 ******************************/
		if (currentPath == null) {
			
			String msg = "Can't get current path";
			Methods_dlg.dlg_ShowMessage(actv, msg, R.color.red);
			
			return;
			
		}

		File dir_New = new File(currentPath).getParentFile();
		
		String newPath = dir_New.getAbsolutePath();
		
		String tname_New = 
				Methods.conv_CurrentPath_to_TableName(newPath);
		
		String msg_Log = "tname_New =>" +
				" " + tname_New;
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		////////////////////////////////
		
		// list_RootDir
		
		////////////////////////////////
		CONS.MainActv.list_RootDir.clear();
		
		CONS.MainActv.list_RootDir.addAll(
				Methods.get_FileList(new File(newPath)));
		
//		////////////////////////////////
//		
//		// notify
//		
//		////////////////////////////////
//		CONS.MainActv.aAdapter.update_Cur_TableName();
//		
//		CONS.MainActv.aAdapter.notifyDataSetChanged();
		
		////////////////////////////////
		
		// pref
		
		////////////////////////////////
		boolean res = Methods.set_Pref_String(
				actv, 
				CONS.Pref.pname_MainActv, 
				CONS.Pref.pkey_CurrentPath, 
				newPath);
		
		////////////////////////////////
		
		// notify
		
		////////////////////////////////
		CONS.MainActv.aAdapter.update_Cur_TableName();
		
		CONS.MainActv.aAdapter.notifyDataSetChanged();
		
		////////////////////////////////
		
		// Button: up
		
		////////////////////////////////
		String root_DirPath = StringUtils.join(
				new String[]{
						CONS.Paths.dpath_Storage_Sdcard, 
						CONS.Paths.dname_Base},
				File.separator);
		
		// If the new dir is the root dir,
		//		then, disable the "Up" button
		if (newPath.equals(root_DirPath)) {
			
			ImageButton bt_Up = (ImageButton) actv.findViewById(R.id.main_bt_up);
			
			bt_Up.setEnabled(false);
			
			bt_Up.setImageDrawable(
					actv.getResources().getDrawable(R.drawable.main_up_disenabled));
			
			// Log
			msg_Log = "button => disabled";
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
		}
		
		////////////////////////////////
		
		// Show path
		
		////////////////////////////////
		TextView tv_Path = (TextView) actv.findViewById(R.id.main_tv_dir_path);
		
		tv_Path.setText(Methods.conv_CurrentPath_to_DisplayPath(newPath));
		
	}//go_Down_Dir	
	
	private static List<TI>
	_move_Files__Get_ToMoveList() {
		// TODO Auto-generated method stub
		
		List<TI> toMoveFiles = new ArrayList<TI>();
		
		for (int position : CONS.TNActv.checkedPositions) {
			
			toMoveFiles.add(CONS.TNActv.list_TNActv_Main.get(position));
			
		}//for (int position : ThumbnailActivity.checkedPositions)
		
		
		return toMoveFiles;
		
	}//_move_Files__Get_ToMoveList

	/******************************
	 * Used in MainActv. Click on a list item,
	 * 	then move down into the directory
	 * 
	@param dirName => The function doesn't validate if the dir exists
	******************************/
	public static void 
	go_Down_Dir
	(Activity actv, String dirName) {
		// TODO Auto-generated method stub
		
		////////////////////////////////
		
		// new file
		
		////////////////////////////////
		String currentPath = Methods.get_Pref_String(
				actv, 
				CONS.Pref.pname_MainActv, 
				CONS.Pref.pkey_CurrentPath, 
				null);
		
		File dpath_New = new File(currentPath, dirName);
		
		String newPath = dpath_New.getAbsolutePath();
		
		// Log
		String msg_Log = "new path => " + dpath_New.getAbsolutePath();

		String tname_New = 
				Methods.conv_CurrentPath_to_TableName(dpath_New.getAbsolutePath());
		
		msg_Log = "tname_New =>" +
				" " + tname_New;

		////////////////////////////////
		
		// list_RootDir
		
		////////////////////////////////
		CONS.MainActv.list_RootDir.clear();
		
		CONS.MainActv.list_RootDir.addAll(
							Methods.get_FileList(new File(newPath)));
		
//		////////////////////////////////
//
//		// add: num of items
//
//		////////////////////////////////
//		Methods.add_NumOfItems_MainActv_List(actv, newPath);
		
//		////////////////////////////////
//	
//		// notify
//	
//		////////////////////////////////
//		CONS.MainActv.aAdapter.notifyDataSetChanged();
		
		////////////////////////////////
	
		// pref
	
		////////////////////////////////
		boolean res = Methods.set_Pref_String(
				actv, 
				CONS.Pref.pname_MainActv, 
				CONS.Pref.pkey_CurrentPath, 
				newPath);
		
		////////////////////////////////
		
		// notify
	
		////////////////////////////////
		CONS.MainActv.aAdapter.update_Cur_TableName();
		
		CONS.MainActv.aAdapter.notifyDataSetChanged();
		
		////////////////////////////////
	
		// Button: up
	
		////////////////////////////////
		ImageButton bt_Up = (ImageButton) actv.findViewById(R.id.main_bt_up);
		
		bt_Up.setEnabled(true);
		
	//	bt_Up.setBackgroundResource(R.drawable.main_up);
		
		bt_Up.setImageDrawable(actv.getResources().getDrawable(R.drawable.main_up));
		
		// Log
		msg_Log = "button => enabled";
		
		////////////////////////////////
		
		// Show path
		
		////////////////////////////////
		TextView tv_Path = (TextView) actv.findViewById(R.id.main_tv_dir_path);
		
		tv_Path.setText(Methods.conv_CurrentPath_to_DisplayPath(newPath));

	}//go_Down_Dir

	public static void
	go_Up_Dir_Move
	(Activity actv) {
		// TODO Auto-generated method stub
	
		////////////////////////////////

		// pref: current path

		////////////////////////////////
		String curPath_Move = Methods.get_Pref_String(
				actv, 
				CONS.Pref.pname_MainActv, 
				CONS.Pref.pkey_TNActv__CurPath_Move, 
				CONS.DB.tname_IFM11);
		
		String newPath_Move = 
				Methods.conv_CurrentPathMove_to_CurrentPathMove_New(curPath_Move);

		String new_Path = StringUtils.join(
					new String[]{
							
						CONS.Paths.dpath_Storage_Sdcard,
						newPath_Move
							
					}, 
					File.separator);

		String msg_Log = "newPath_Move => " + newPath_Move;
		Log.d("Methods.java"
				+ "["
				+ Thread.currentThread().getStackTrace()[2]
						.getLineNumber() + "]", msg_Log);

		// Log
		msg_Log = "new_Path => " + new_Path;
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		////////////////////////////////

		// list

		////////////////////////////////
		List<String> dir_List = Methods.get_DirList(new_Path);
		CONS.TNActv.dir_List.clear();
		
		for (String dirName : dir_List) {
//			for (String dirName : CONS.ALActv.dir_List) {
			
			CONS.TNActv.dir_List.add(newPath_Move + File.separator + dirName);
//			CONS.ALActv.dir_List.add(CONS.DB.tname_CM7 + File.separator + dirName);
//			dirName = CONS.DB.tname_CM7 + File.separator + dirName;
			
		}
		
		// Log
		msg_Log = "dir list => modified";
		Log.d("Methods_dlg.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		CONS.TNActv.dir_List.add(CONS.Admin.dirString_UpperDir);
		
		////////////////////////////////

		// notify

		////////////////////////////////
		CONS.TNActv.adp_DirList.notifyDataSetChanged();
		
		// Log
		msg_Log = "adapter => notified";
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		////////////////////////////////

		// set: pref: pkey_ALActv__CurPath_Move

		////////////////////////////////
		boolean res = Methods.set_Pref_String(
				actv, 
				CONS.Pref.pname_MainActv, 
				CONS.Pref.pkey_TNActv__CurPath_Move, 
				newPath_Move); 
		
		if (res == true) {
			
			// Log
			msg_Log = "new pref set => " + newPath_Move;
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
		} else {

			// Log
			msg_Log = "new pref not set! => " + newPath_Move;
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
				
		}
		
	}//go_Up_Dir_Move

	public static String 
	conv_CurrentPathMove_to_CurrentPathMove_New
	(String curPath_Move) {
		// TODO Auto-generated method stub
		
		String[] tokens = curPath_Move.split(File.separator);
		
		////////////////////////////////

		// tokens == 1

		////////////////////////////////
		if (tokens == null) {
			
			return curPath_Move;
			
		} else if (tokens.length == 1) {
			
			return curPath_Move;
			
		}
		
		////////////////////////////////

		// tokens > 1

		////////////////////////////////
		int len = tokens.length;
		
		String[] tokens_New = Arrays.copyOfRange(tokens, 0, len - 1);
//		String[] tokens_New = Arrays.copyOfRange(tokens, 0, len - 2);
		
		return StringUtils.join(tokens_New, File.separator);
		
	}//conv_CurrentPathMove_to_CurrentPathMove_New

	public static void 
	go_Down_Dir_Move
	(Activity actv, String item) {
		// TODO Auto-generated method stub
		////////////////////////////////

		// update list
		// update: pref: current path-move

		////////////////////////////////
		////////////////////////////////

		// build: paths

		////////////////////////////////
		String new_DirPath = StringUtils.join(
				new String[]{
		
						CONS.Paths.dpath_Storage_Sdcard,
						item
				},
				File.separator);
		
		// Log
		String msg_Log = "new_DirPath => " + new_DirPath;
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);

		////////////////////////////////

		// get: current path-move

		////////////////////////////////
		String curPath_Move = Methods.get_Pref_String(
						actv, 
						CONS.Pref.pname_MainActv, 
						CONS.Pref.pkey_TNActv__CurPath_Move, 
						CONS.DB.tname_IFM11);

		// Log
		msg_Log = "curPath_Move => " + curPath_Move;
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		////////////////////////////////

		// build: list

		////////////////////////////////
		List<String> dir_List = Methods.get_DirList(new_DirPath);
		
		CONS.TNActv.dir_List.clear();
		
		for (String dirName : dir_List) {
//			for (String dirName : CONS.ALActv.dir_List) {
			
			CONS.TNActv.dir_List.add(item + File.separator + dirName);
//			CONS.ALActv.dir_List.add(CONS.DB.tname_CM7 + File.separator + dirName);
//			dirName = CONS.DB.tname_CM7 + File.separator + dirName;
			
		}
		
		// Log
		msg_Log = "dir list => modified";
		Log.d("Methods_dlg.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		CONS.TNActv.dir_List.add(CONS.Admin.dirString_UpperDir);

		////////////////////////////////

		// notify

		////////////////////////////////
		CONS.TNActv.adp_DirList.notifyDataSetChanged();
		
		// Log
		msg_Log = "adapter => notified";
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);

		////////////////////////////////

		// update: pref: current_path_move

		////////////////////////////////
		boolean res = 
				Methods.set_Pref_String(
							actv, 
							CONS.Pref.pname_MainActv, 
							CONS.Pref.pkey_TNActv__CurPath_Move, 
							item);
		
		if (res == true) {
			
			// Log
			msg_Log = "pkey_TNActv__CurPath_Move => set: "
							+ CONS.Pref.pkey_TNActv__CurPath_Move;
			Log.i("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
					
		} else {

			// Log
			msg_Log = "pkey_TNActv__CurPath_Move => NOT set: "
					+ CONS.Pref.pkey_TNActv__CurPath_Move;
			Log.e("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
		}

		
	}//go_Down_Dir_Move

	public static void
	add_NumOfItems_MainActv_List
	(Activity actv, String currentPath) {
		// TODO Auto-generated method stub
	
		////////////////////////////////

		// setup: vars

		////////////////////////////////
		List<String> tmp_List = new ArrayList<String>();
		
		String tname = Methods.conv_CurrentPath_to_TableName(currentPath);
		
		int numOfItems = DBUtils.get_NumOfEntries_TI(
							actv, 
							tname);
		
		// Log
		String msg_Log = "numOfItems => " + numOfItems;
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		for (String name : CONS.MainActv.list_RootDir) {
			
			if (name.equals(CONS.Admin.fname_List)) {
				
				tmp_List.add(String.format("%s (%d)", name, numOfItems));
//				name = String.format("%s (%d)", name, numOfItems);
				
				// Log
				msg_Log = "yes: list.txt";
				Log.d("MainActv.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", msg_Log);
			} else {
				
				tmp_List.add(name);
				
				// Log
				msg_Log = "no";
				Log.d("MainActv.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", msg_Log);
			}
		}
		
		////////////////////////////////

		// update: list

		////////////////////////////////
		CONS.MainActv.list_RootDir.clear();
		
		CONS.MainActv.list_RootDir.addAll(tmp_List);
		
		//debug
		for (String name : CONS.MainActv.list_RootDir) {
			
			// Log
			msg_Log = "name = " + name;
			Log.d("MainActv.java"
					+ "["
					+ Thread.currentThread().getStackTrace()[2]
							.getLineNumber() + "]", msg_Log);
		}
		
	}//add_NumOfItems_MainActv_List

	public static void 
	searchItem
	(Activity actv, Dialog dlg) {
		/*----------------------------
		 * Steps
		 * 1. Get search words
		 * 2. Format words
		 * 
		 * 2-2. Get table name from current path
		 * 3. Search task
		 * 
		 * 9. Dismiss dialog
			----------------------------*/
		EditText et = (EditText) dlg.findViewById(R.id.dlg_search_et);
		
		String words = et.getText().toString();
		
		if (words.equals("")) {
			
			// debug
//			Toast.makeText(actv, "������ĂȂ���", Toast.LENGTH_LONG).show();
			Toast.makeText(actv, "語句を入れてないよ", Toast.LENGTH_LONG).show();
			
			return;
			
		}//if (words.equals(""))
		
		////////////////////////////////

		// Format words

		////////////////////////////////
		words = words.replace(CONS.Admin.char_Space_Whole, CONS.Admin.char_Space_Half);
//		words = words.replace('　', ' ');
		
		String[] a_words = words.split(" ");
		
		//debug
		for (String w : a_words) {
			
			// Log
			String msg_Log = "word = " + w;
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
		}
		
//		// Log
//		Log.d("Methods.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", "a_words.length => " + a_words.length);
		
		////////////////////////////////

		// Get table name from current path

		////////////////////////////////
		String currentPath = Methods.get_Pref_String(
				actv, 
				CONS.Pref.pname_MainActv, 
				CONS.Pref.pkey_CurrentPath, 
				null);

		/******************************
			validate: null
		 ******************************/
		if (currentPath == null) {
			
			String msg = "Can't get the current path => Use the top table";
			Methods_dlg.dlg_ShowMessage(actv, msg, R.color.gold2);
			
			return;
			
		}
		
		String tableName = Methods.conv_CurrentPath_to_TableName(currentPath);
		
		////////////////////////////////

		// prep: search

		////////////////////////////////
		// Checkbox => all table
		CheckBox cb_AllTable = (CheckBox) dlg.findViewById(R.id.dlg_search_cb_all_table);
		
		int search_mode = 0;	// 0 => Specific table (default)
		
		if (cb_AllTable.isChecked()) {
			
			search_mode = 1;	// 1 => All tables
			
		}//if (condition)
		
		// Log
		String msg_Log = "search mode => " + search_mode;
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);

		// Checkbox => all table
		CheckBox cb_FileName = (CheckBox) dlg.findViewById(R.id.dlg_search_cb_file_name);
		
		int search_Type = 0;	// 0 => Specific table (default)
		
		if (cb_FileName.isChecked()) {
			
			search_Type = 1;	// 1 => Search by file name
			
		}//if (condition)
		
		// Log
		msg_Log = "search_Type => " + search_Type;
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		////////////////////////////////

		// search

		////////////////////////////////
		Task_Search st = new Task_Search(actv, search_mode, search_Type);
//		Task_Search st = new Task_Search(actv, search_mode);

		st.execute(a_words, new String[]{tableName});
		
		////////////////////////////////

		// Dismiss dialog

		////////////////////////////////
		dlg.dismiss();
		
	}//public static void searchItem(Activity actv, Dialog dlg)

	public static void 
	delete_TI
	(Activity actv, Dialog dlg1, Dialog dlg2, TI ti) {
		// TODO Auto-generated method stub
		
		CheckBox cb = 
				(CheckBox) dlg2.findViewById(R.id.dlg_tmpl_confirm_simple_cb_delete_file);
		
		////////////////////////////////////////////////////////////////

		// Delete from: Table

		////////////////////////////////////////////////////////////////
		int res = DBUtils.delete_TI(actv, ti, cb.isChecked());

		////////////////////////////////

		// validt: done

		////////////////////////////////
		if (res < 1) {
			
			String msg = null;
			
			switch(res) {
			
			case -1: msg = "TI doesn't exist in db";
				
				break;
				
			case -2: msg = "TI has no table_name value";
				
				break;
				
			case -3: msg = "delete method returned 0";
				
				break;
				
			default:
				
				msg = "Unknown error";
				
				break;
				
			}
			
			Methods_dlg.dlg_ShowMessage(actv, msg, R.color.red);
			
			dlg2.dismiss();
			
			return;
			
		}

		////////////////////////////////

		// delete from: listview

		////////////////////////////////
		CONS.TNActv.list_TNActv_Main.remove(ti);

		if (CONS.TNActv.adp_TNActv_Main != null) {
		
			CONS.TNActv.adp_TNActv_Main.notifyDataSetChanged();
			
		}//if (TNActv.aAdapter == condition)
		
		
		////////////////////////////////////////////////////////////////

		// delete from: sdcard

		////////////////////////////////////////////////////////////////
		////////////////////////////////

		// validate: checkbox => checked

		////////////////////////////////
		if (!cb.isChecked()) {
			
			String msg = "TI => deleted from DB";
			Methods_dlg.dlg_ShowMessage(actv, msg);
			
			dlg2.dismiss();
			dlg1.dismiss();
			
			return;
			
		}
		
		// Log
		String msg_Log = "deleting file from sdcard...";
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		/*********************************
		 * Delete from memory
		 *********************************/
		String msg = null;
		
		int colorID = 0;
		
		File f = new File(ti.getFile_path(), ti.getFile_name());
//		File f = new File(ti.getFile_path());
		
		if (f.exists()) {

			// REF http://stackoverflow.com/questions/1248292/how-to-delete-a-file-from-sd-card Niko Gamulin
//			boolean res = f.delete();
			boolean res_b = f.delete();
			
			if (res_b == true) {
		
				msg = "TI deleted from DB/sdcard";
				
				colorID = R.color.green4;
				
			} else {//if (res == true)
			
				msg = "TI deleted from DB\nnot from sdcard";
				
				colorID = R.color.gold2;
				
			}//if (res == true)
			
		} else {//if (f.exists())
			
			msg = "TI deleted from DB\n" +
					"File doesn't exist";
			
			colorID = R.color.gold2;
			
		}//if (f.exists())
		
		////////////////////////////////

		// dismiss

		////////////////////////////////
		dlg2.dismiss();
		dlg1.dismiss();
		
		// Log
		msg_Log = "dlgs => dismissed";
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		////////////////////////////////

		// report

		////////////////////////////////
		Methods_dlg.dlg_ShowMessage(actv, msg, colorID);
		
		
	}//delete_TI

	public static void
	edit_TI
	(Activity actv, Dialog dlg1, Dialog d2, TI ti) {
		// TODO Auto-generated method stub
		
		////////////////////////////////

		// get: view

		////////////////////////////////
		EditText etFileName = (EditText) d2.findViewById(
				R.id.dlg_edit_ti_et_file_name);

		EditText etFilePath = (EditText) d2.findViewById(
			R.id.dlg_edit_ti_et_file_path);
		
		EditText etMemos = (EditText) d2.findViewById(
			R.id.dlg_edit_ti_et_memos);
		
		////////////////////////////////
		
		// get: stirngs
		
		////////////////////////////////
		String new_Memo = etMemos.getText().toString();
		
		String cur_Memo = ti.getMemo();
	
		////////////////////////////////

		// set: new memo

		////////////////////////////////
		ti.setFile_name(etFileName.getText().toString());
		ti.setFile_path(etFilePath.getText().toString());
		ti.setMemo(new_Memo);
//		ti.setMemo(etMemos.getText().toString());

		
//		// TODO Auto-generated method stub
//		DBUtils dbu = new DBUtils(actv, MainActv.dbName);
		
		int res = DBUtils.update_TI__All(actv, ti);
//		boolean res = dbu.updateData_TI(actv, ti);

		////////////////////////////////

		// Update all images?

		////////////////////////////////
		CheckBox cb = (CheckBox) d2.findViewById(R.id.dlg_edit_ti_cb_add_to_all_images);
		
		int res_i = 0;
		
		if (res == 1 && cb.isChecked()) {
			
			String diff = StringUtils.strip(new_Memo, cur_Memo);
			
			if (diff != null && diff != "") {
				
				res_i = DBUtils.update_TIs__Memo(actv, ti.getTable_name(), diff);
				
				
			}
			
//			// Log
//			String msg_Log = String.format(
//								"cur = %s // new = %s // diff = %s", 
//								cur_Memo, new_Memo, diff);
//			Log.d("Methods.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", msg_Log);
			
			
		}
		
		////////////////////////////////

		// report

		////////////////////////////////
		String msg = null;
		
		int colorID = 0;
		
		if (res == -1) {

			msg = "Insertion failed";
			
			colorID = R.color.red;
			
		} else if (res == -2) {
			
			msg = "database exception";
			
			colorID = R.color.red;
			
		} else if (res == 1) {
			
			if (res_i > 0) {
				
//				// Update TI list
//				CONS.TNActv.list_TNActv_Main.clear();
//				
//				CONS.TNActv.list_TNActv_Main.addAll(
//								DBUtils.find_All_TI(actv, CONS.DB.tname_IFM11));
				
				msg = "TI => updated\nOther memos updated";
				
				colorID = R.color.green4;
				
			} else {
				
				msg = "TI => updated\nOther memos not updated";
				
				colorID = R.color.gold2;
				
			}
			
			////////////////////////////////

			// dismiss

			////////////////////////////////
			// dismiss
			d2.dismiss();
			dlg1.dismiss();
			
			////////////////////////////////

			// update: listview

			////////////////////////////////
			CONS.TNActv.adp_TNActv_Main.notifyDataSetChanged();
			
			// Log
			String msg_Log = "adapter => notified";
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
		}
		
		Methods_dlg.dlg_ShowMessage(actv, msg, colorID);
		
		
//		/***************************************
//		 * Close dialogues
//		 ***************************************/
//		if (res == true) {
//			
//			// debug
//			Toast.makeText(actv, "Data updated", Toast.LENGTH_LONG).show();
//			
//			dlg2.dismiss();
//			dlg1.dismiss();
//			
//		} else {//if (res == true)
//			
//			// debug
//			Toast.makeText(actv, "Update data => Failed", Toast.LENGTH_LONG).show();
//			
//		}//if (res == true)
		
	}//edit_TI

	public static void 
	del_Pattern
	(Activity actv, 
		Dialog dlg1, Dialog dlg2, Dialog dlg3, 
		WordPattern wp) {
		// TODO Auto-generated method stub
		
		////////////////////////////////////////////////////////////////

		// Delete from: Table

		////////////////////////////////////////////////////////////////
		int res = DBUtils.del_Pattern(actv, wp);

		////////////////////////////////

		// validt: done

		////////////////////////////////
		if (res < 1) {
			
			String msg = null;
			
			switch(res) {
			
			case -1: msg = "Pattern doesn't exist in db: " + wp.getWord();
				
				break;
				
			case -2: msg = "Deletion failed => " + wp.getWord();
				
				break;
				
			default:
				
				msg = "Unknown error";
				
				break;
				
			}
			
			Methods_dlg.dlg_ShowMessage(actv, msg, R.color.red);
			
			dlg3.dismiss();
			
			return;
			
		}

		////////////////////////////////

		// delete from: listview

		////////////////////////////////
		CONS.IMageActv.patternList.remove(wp);

		if (CONS.IMageActv.adapter != null) {
		
			CONS.IMageActv.adapter.notifyDataSetChanged();
			
		}//if (TNActv.aAdapter == condition)
		
		////////////////////////////////

		// dismiss

		////////////////////////////////
		dlg3.dismiss();
		dlg2.dismiss();
		dlg1.dismiss();
		
	}//del_Pattern

	public static void 
	exec_Sql
	(Activity actv, 
		Dialog dlg1, Dialog dlg2,
		String sql_Type) {
		// TODO Auto-generated method stub
		////////////////////////////////

		// dispatch

		////////////////////////////////
		// Log
		String msg_Log = "sql_Type => " + sql_Type;
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		if (sql_Type.equals(
				CONS.DB.Sqls._20140817_154650_addCol_IFM11_UpdatedAt_TITLE)) {
			
			Methods._exec_Sql__AddCol_IFM11(actv, dlg1, dlg2, sql_Type);
			
		} else {

			// Log
			msg_Log = "Unknown case => " + sql_Type;
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
		}
		
		
		
	}//exec_Sql

	private static void 
	_exec_Sql__AddCol_IFM11
	(Activity actv, Dialog dlg1,
			Dialog dlg2, String sql_Type) {
		// TODO Auto-generated method stub
		
//		// Log
//		String msg_Log = "_exec_Sql__AddCol_IFM11";
//		Log.d("Methods.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_Log);
		
		////////////////////////////////

		// execute

		////////////////////////////////
		int res = DBUtils.exec_Sql(actv, sql_Type);
		
		////////////////////////////////

		// report

		////////////////////////////////
		String msg = null;
		int colorId = 0;
		
		switch(res) {
		
		case 1:
			
			dlg2.dismiss();
			dlg1.dismiss();
			
			msg = "Sql => done";
			colorId = R.color.green4;
			
			break;
			
		case -1:
			
			dlg2.dismiss();
			
			msg = "Unknown sql type";
			colorId = R.color.gold2;
			
			break;

		case -2:
			
			dlg2.dismiss();
			
			msg = "Exception";
			colorId = R.color.red;
			
			break;
			
		default:
			
			dlg2.dismiss();
			
			msg = "Unknown result";
			colorId = R.color.gold2;
			
			break;
			
		}
			
		Methods_dlg.dlg_ShowMessage(actv, msg, colorId);

		
	}//_exec_Sql__AddCol_IFM11

	/*********************************
	 * REF=> http://www.searchman.info/tips/2640.html
	 * 
	 * #sqlite db file: "database disk image is malformed"
	 * REF=> http://stackoverflow.com/questions/9058169/sqlite-database-disk-image-is-malformed-on-windows-but-fine-on-android
	 * 
	 * @return
		 * -1	=> SocketException
		 * -2	=> IOException
		 * -3	=> IOException in disconnecting
		 * 
		 * -2	=> Log in failed
		 * >0	=> Reply code
	 * 
	 *********************************/
	public static int 
	ftp_connect_disconnect
	(Activity actv) {
		/*********************************
		 * memo
		 *********************************/
		// FTP client
		FTPClient fp = new FTPClient();
		
		int reply_code;
		
		String server_name = "ftp.benfranklin.chips.jp";
		
		String uname = "chips.jp-benfranklin";

		String passwd = "9x9jh4";
		
//		String fpath = StringUtils.join(
//				new String[]{
//						MainActv.dirPath_db,
//						MainActv.fileName_db
//				}, File.separator);
//		
//		String fpath_audio = StringUtils.join(
//				new String[]{
//						MainActv.dirName_ExternalStorage,
//						"Audios",
//						"Fiddle_music",
//						"Gaelic Folk Song.mp3"
//				}, File.separator);

//		String fpath_remote = "./" + MainActv.fileName_db;
		
		String fpath_remote = "./" + "Gaelic Folk Song.mp3.v2";
		
		/*********************************
		 * Connect
		 *********************************/
		try {
			
			fp.connect(server_name);
			
			reply_code = fp.getReplyCode();
			
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "fp.getReplyCode()=" + fp.getReplyCode());
			
		} catch (SocketException e) {
			
			// Log
			Log.e("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Error: " + e.toString());
			
			return -1;
			
		} catch (IOException e) {

			// Log
			Log.e("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Error: " + e.toString());
			
			return -2;
		}

		//debug
		/*********************************
		 * Disconnect
		 *********************************/
		try {
			
			fp.disconnect();
			
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "fp => Disconnected");

			return reply_code;
			
		} catch (IOException e) {
			
			// Log
			Log.e("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Error: " + e.toString());
			
			return -3;
			
		}

		
//		return 0;
		
//		/*********************************
//		 * Log in
//		 *********************************/
//		boolean res;
//		
//		try {
//			
//			res = fp.login(uname, passwd);
//			
//			if(res == false) {
//				
//				reply_code = fp.getReplyCode();
//				
//				// Log
//				Log.e("Methods.java"
//						+ "["
//						+ Thread.currentThread().getStackTrace()[2]
//								.getLineNumber() + "]", "Log in failed => " + reply_code);
//				
//				fp.disconnect();
//				
//				return -2;
//				
//			} else {
//				
//				// Log
//				Log.d("Methods.java" + "["
//						+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//						+ "]", "Log in => Succeeded");
//				
//			}
//
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		
//		/*********************************
//		 * FTP files
//		 *********************************/
//		// �t�@�C�����M
//		FileInputStream is;
//		
//		try {
//			
//			is = new FileInputStream(fpath);
////			is = new FileInputStream(fpath_audio);
//			
////			fp.storeFile("./" + MainActv.fileName_db, is);// �T�[�o�[��
//			res = fp.storeFile(fpath_remote, is);// �T�[�o�[��
//			
////			fp.makeDirectory("./ABC");
//			
//			if (res == true) {
//				
//				// Log
//				Log.d("Methods.java" + "["
//						+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//						+ "]", "File => Stored");
//				
//			} else {//if (res == true)
//
//				// Log
//				Log.d("Methods.java" + "["
//						+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//						+ "]", "Store file => Failed");
//
//			}//if (res == true)
//			
//			is.close();
//
//		} catch (FileNotFoundException e) {
//
//			// Log
//			Log.e("Methods.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", "Exception: " + e.toString());
//			
//		} catch (IOException e) {
//			
//			// Log
//			Log.e("Methods.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", "Exception: " + e.toString());
//
//		}
//						
//		/*********************************
//		 * Disconnect
//		 *********************************/
//		try {
//			
//			fp.disconnect();
//			
//			// Log
//			Log.d("Methods.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", "fp => Disconnected");
//
//			return reply_code;
//			
//		} catch (IOException e) {
//			
//			// Log
//			Log.e("Methods.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", "Error: " + e.toString());
//			
//			return -1;
//			
//		}
		
	}//ftp_connect_disconnect

	/*********************************
	 * REF=> http://www.searchman.info/tips/2640.html
	 * 
	 * #sqlite db file: "database disk image is malformed"
	 * REF=> http://stackoverflow.com/questions/9058169/sqlite-database-disk-image-is-malformed-on-windows-but-fine-on-android
	 * @param ti 
	 * 
	 * @return
	 * -1	=> SocketException<br>
	 * -2	=> IOException<br>
	 * -3	=> IOException in disconnecting<br>
	 * -4	=> Login failed<br>
	 * -5	=> IOException in logging-in<br>
	 * 
	 * -6	=> storeFile returned false<br>
	 * -7	=> can't find the source file<br>
	 * -8	=> can't find the source file; can't disconnect FTP client<br>
	 * -9	=> storeFile ---> IOException<br>
	 * -10	=> storeFile ---> IOException; can't disconnect FTP client<br>
	 * 
	 * -11	=> set file type ---> failed<br>
	 * -12	=> IOException in logging-in; can't disconnect FTP client<br>
	 * 
	 * >0	=> Reply code
	 * 
	 *********************************/
	public static int 
	ftp_Image_to_Remote
	(Activity actv, TI ti) {
		/*********************************
		 * memo
		 *********************************/
		// FTP client
		FTPClient fp = new FTPClient();
		
		int reply_code;
		
//		String server_name = CONS.Remote.server_Name;
////		String server_name = "ftp.benfranklin.chips.jp";
//		
//		String uname = "chips.jp-benfranklin";
//		
//		String passwd = "9x9jh4";
		
		String fpath_Src = StringUtils.join(
				new String[]{
						ti.getFile_path(),
						ti.getFile_name()
				}, File.separator);
//		
//		String fpath_audio = StringUtils.join(
//				new String[]{
//						MainActv.dirName_ExternalStorage,
//						"Audios",
//						"Fiddle_music",
//						"Gaelic Folk Song.mp3"
//				}, File.separator);
		
//		String fpath_remote = "./" + MainActv.fileName_db;
		
//		String fpath_remote = "./cake_apps/images"
		String fpath_remote = StringUtils.join(
					new String[]{
							CONS.Remote.remote_Root_Image,
//							"./cake_apps/images",
							ti.getFile_name()
//							+ "_"
//							+ Methods.get_TimeLabel(
//									Methods.getMillSeconds_now())
					}, File.separator);
//				"./cake_apps/images"
//							+ ti.getFile_name()
//							+ "_"
//							+ Methods.get_TimeLabel(Methods.getMillSeconds_now());
//		String fpath_remote = "./" + "Gaelic Folk Song.mp3.v2";
		
		/*********************************
		 * Connect
		 *********************************/
		try {
			
			// Log
			String msg_Log = "connecting...";
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			fp.connect(CONS.Remote.server_Name);
//			fp.connect(server_name);
			
			reply_code = fp.getReplyCode();
			
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "fp.getReplyCode()=" + fp.getReplyCode());
			
		} catch (SocketException e) {
			
			// Log
			Log.e("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Error: " + e.toString());
			
			return -1;
			
		} catch (IOException e) {
			
			// Log
			Log.e("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Error: " + e.toString());
			
			return -2;
		}
		
		/*********************************
		 * Log in
		 *********************************/
		boolean res;
		
		try {
			
//			res = fp.login(uname, passwd);
			res = fp.login(
						CONS.Remote.uname, 
						CONS.Remote.passwd);
			
			if(res == false) {
				
				reply_code = fp.getReplyCode();
				
				// Log
				Log.e("Methods.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", "Log in failed => " + reply_code);
				
				fp.disconnect();
				
				return -4;
				
			} else {
				
				// Log
				Log.d("Methods.java" + "["
						+ Thread.currentThread().getStackTrace()[2].getLineNumber()
						+ "]", "Log in => Succeeded");
				
			}

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			// Log
			String msg_Log = "IOException";
			Log.e("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			e1.printStackTrace();
			
			try {
				
				fp.disconnect();
				
				return -5;
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
				return -12;
				
			}
			
		}
		
		/*********************************
		 * FTP files
		 *********************************/
		// �t�@�C�����M
		FileInputStream is;
		
		try {
			
			// Log
			String msg_Log = "fpath_Src => " + fpath_Src;
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			is = new FileInputStream(fpath_Src);
//			is = new FileInputStream(fpath_audio);
			
			// Log
			msg_Log = "Input stream => created";
//			String msg_Log = "Input stream => created";
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			////////////////////////////////

			// set: file type

			////////////////////////////////
			// REF http://stackoverflow.com/questions/7740817/how-to-upload-an-image-to-ftp-using-ftpclient answered Oct 12 '11 at 13:52
			res = fp.setFileType(FTP.BINARY_FILE_TYPE);

			/******************************
				validate
			 ******************************/
			if (res == false) {
				
				// Log
				msg_Log = "set file type => failed";
				Log.e("Methods.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", msg_Log);
				
				is.close();
				
				fp.disconnect();
				
				return -11;
				
			}
			
			////////////////////////////////

			// store

			////////////////////////////////
			// Log
			msg_Log = "Stroing file to remote... => "
						+ fpath_remote;
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
//			fp.storeFile("./" + MainActv.fileName_db, is);// �T�[�o�[��
			res = fp.storeFile(fpath_remote, is);// �T�[�o�[��
			
//			fp.makeDirectory("./ABC");
			
			if (res == true) {
				
				// Log
				Log.d("Methods.java" + "["
						+ Thread.currentThread().getStackTrace()[2].getLineNumber()
						+ "]", "File => Stored");
				
			} else {//if (res == true)

				// Log
				Log.d("Methods.java" + "["
						+ Thread.currentThread().getStackTrace()[2].getLineNumber()
						+ "]", "Store file => Failed");
				
				fp.disconnect();
				
				return -6;

			}//if (res == true)
			
			is.close();

		} catch (FileNotFoundException e) {

			// Log
			Log.e("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception: " + e.toString());
			
			try {
				
				fp.disconnect();
				
				return -7;
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				
				return -8;
				
			}
			
			
		} catch (IOException e) {
			
			// Log
			Log.e("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception: " + e.toString());
			
			try {
				fp.disconnect();
				
				return -9;
				
			} catch (IOException e1) {
				
				// TODO Auto-generated catch block
				e1.printStackTrace();
				
				return -10;
				
			}

		}

		
		//debug
		/*********************************
		 * Disconnect
		 *********************************/
		try {
			
			// Log
			String msg_Log = "disconnecting...";
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			fp.disconnect();
			
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "fp => Disconnected");
			
			return reply_code;
			
		} catch (IOException e) {
			
			// Log
			Log.e("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Error: " + e.toString());
			
			return -3;
			
		}
		
		
//		return 0;
		
//		/*********************************
//		 * Log in
//		 *********************************/
//		boolean res;
//		
//		try {
//			
//			res = fp.login(uname, passwd);
//			
//			if(res == false) {
//				
//				reply_code = fp.getReplyCode();
//				
//				// Log
//				Log.e("Methods.java"
//						+ "["
//						+ Thread.currentThread().getStackTrace()[2]
//								.getLineNumber() + "]", "Log in failed => " + reply_code);
//				
//				fp.disconnect();
//				
//				return -2;
//				
//			} else {
//				
//				// Log
//				Log.d("Methods.java" + "["
//						+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//						+ "]", "Log in => Succeeded");
//				
//			}
//
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		
//		/*********************************
//		 * FTP files
//		 *********************************/
//		// �t�@�C�����M
//		FileInputStream is;
//		
//		try {
//			
//			is = new FileInputStream(fpath);
////			is = new FileInputStream(fpath_audio);
//			
////			fp.storeFile("./" + MainActv.fileName_db, is);// �T�[�o�[��
//			res = fp.storeFile(fpath_remote, is);// �T�[�o�[��
//			
////			fp.makeDirectory("./ABC");
//			
//			if (res == true) {
//				
//				// Log
//				Log.d("Methods.java" + "["
//						+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//						+ "]", "File => Stored");
//				
//			} else {//if (res == true)
//
//				// Log
//				Log.d("Methods.java" + "["
//						+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//						+ "]", "Store file => Failed");
//
//			}//if (res == true)
//			
//			is.close();
//
//		} catch (FileNotFoundException e) {
//
//			// Log
//			Log.e("Methods.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", "Exception: " + e.toString());
//			
//		} catch (IOException e) {
//			
//			// Log
//			Log.e("Methods.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", "Exception: " + e.toString());
//
//		}
//						
//		/*********************************
//		 * Disconnect
//		 *********************************/
//		try {
//			
//			fp.disconnect();
//			
//			// Log
//			Log.d("Methods.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", "fp => Disconnected");
//
//			return reply_code;
//			
//		} catch (IOException e) {
//			
//			// Log
//			Log.e("Methods.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", "Error: " + e.toString());
//			
//			return -1;
//			
//		}
		
	}//ftp_connect_disconnect

	/*********************************
	 * REF=> http://www.searchman.info/tips/2640.html
	 * 
	 * #sqlite db file: "database disk image is malformed"
	 * REF=> http://stackoverflow.com/questions/9058169/sqlite-database-disk-image-is-malformed-on-windows-but-fine-on-android
	 * @return
	 * -1	=> SocketException
	 * -2	=> IOException
	 * -3	=> IOException in disconnecting
	 * -4	=> Login failed
	 * -5	=> IOException in logging-in
	 * 
	 * -6	=> storeFile returned false
	 * -7	=> can't find the source file
	 * -8	=> can't find the source file; can't disconnect FTP client
	 * -9	=> storeFile ---> IOException
	 * -10	=> storeFile ---> IOException; can't disconnect FTP client
	 * 
	 * -11	=> set file type ---> failed
	 * -12	=> IOException in logging-in; can't disconnect FTP client
	 * 
	 * -2	=> Log in failed
	 * >0	=> Reply code
	 * 
	 *********************************/
	public static int 
	ftp_Remote_DB
	(Activity actv) {
		/*********************************
		 * memo
		 *********************************/
		// FTP client
		FTPClient fp = new FTPClient();
		
		int reply_code;
		
		// backup db name
		String dbName_backup = String.format(
					"%s_%s%s", 
					CONS.DB.fname_DB_Backup_Trunk,
					Methods.get_TimeLabel(
							Methods.getMillSeconds_now()),
					CONS.DB.fname_DB_Backup_ext
					);
		
		String fpath_Src = 
					actv.getDatabasePath(CONS.DB.dbName).getAbsolutePath();
		
		String fpath_remote = StringUtils.join(
				new String[]{
						CONS.Remote.remote_Root_DBFile,
						dbName_backup
				}, File.separator);
		
		/*********************************
		 * Connect
		 *********************************/
		try {
			
			// Log
			String msg_Log = "connecting...";
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			fp.connect(CONS.Remote.server_Name);
//			fp.connect(server_name);
			
			reply_code = fp.getReplyCode();
			
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "fp.getReplyCode()=" + fp.getReplyCode());
			
		} catch (SocketException e) {
			
			// Log
			Log.e("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Error: " + e.toString());
			
			return -1;
			
		} catch (IOException e) {
			
			// Log
			Log.e("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Error: " + e.toString());
			
			return -2;
		}
		
		/*********************************
		 * Log in
		 *********************************/
		boolean res;
		
		try {
			
//			res = fp.login(uname, passwd);
			res = fp.login(
					CONS.Remote.uname, 
					CONS.Remote.passwd);
			
			if(res == false) {
				
				reply_code = fp.getReplyCode();
				
				// Log
				Log.e("Methods.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", "Log in failed => " + reply_code);
				
				fp.disconnect();
				
				return -4;
				
			} else {
				
				// Log
				Log.d("Methods.java" + "["
						+ Thread.currentThread().getStackTrace()[2].getLineNumber()
						+ "]", "Log in => Succeeded");
				
			}
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			// Log
			String msg_Log = "IOException";
			Log.e("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			e1.printStackTrace();
			
			try {
				
				fp.disconnect();
				
				return -5;
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
				return -12;
				
			}
			
		}
		
		/*********************************
		 * FTP files
		 *********************************/
		// �t�@�C�����M
		FileInputStream is;
		
		try {
			
			// Log
			String msg_Log = "fpath_Src => " + fpath_Src;
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			is = new FileInputStream(fpath_Src);
//			is = new FileInputStream(fpath_audio);
			
			// Log
			msg_Log = "Input stream => created";
//			String msg_Log = "Input stream => created";
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			////////*////////////////////////
			
			// set: file type
			
			////////////////////////////////
			// REF http://stackoverflow.com/questions/7740817/how-to-upload-an-image-to-ftp-using-ftpclient answered Oct 12 '11 at 13:52
			res = fp.setFileType(FTP.BINARY_FILE_TYPE);
			
			/******************************
				validate
			 ******************************/
			if (res == false) {
				
				// Log
				msg_Log = "set file type => failed";
				Log.e("Methods.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", msg_Log);
				
				is.close();
				
				fp.disconnect();
				
				return -11;
				
			}
			
			////////////////////////////////
			
			// store
			
			////////////////////////////////
			// Log
			msg_Log = "Stroing file to remote... => "
					+ fpath_remote;
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
//			fp.storeFile("./" + MainActv.fileName_db, is);// �T�[�o�[��
			res = fp.storeFile(fpath_remote, is);// �T�[�o�[��
			
//			fp.makeDirectory("./ABC");
			
			if (res == true) {
				
				// Log
				Log.d("Methods.java" + "["
						+ Thread.currentThread().getStackTrace()[2].getLineNumber()
						+ "]", "File => Stored");
				
			} else {//if (res == true)
				
				// Log
				Log.d("Methods.java" + "["
						+ Thread.currentThread().getStackTrace()[2].getLineNumber()
						+ "]", "Store file => Failed");
				
				fp.disconnect();
				
				return -6;
				
			}//if (res == true)
			
			is.close();
			
		} catch (FileNotFoundException e) {
			
			// Log
			Log.e("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception: " + e.toString());
			
			try {
				
				fp.disconnect();
				
				return -7;
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				
				return -8;
				
			}
			
			
		} catch (IOException e) {
			
			// Log
			Log.e("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception: " + e.toString());
			
			try {
				fp.disconnect();
				
				return -9;
				
			} catch (IOException e1) {
				
				// TODO Auto-generated catch block
				e1.printStackTrace();
				
				return -10;
				
			}
			
		}
		
		
		//debug
		/*********************************
		 * Disconnect
		 *********************************/
		try {
			
			// Log
			String msg_Log = "disconnecting...";
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			fp.disconnect();
			
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "fp => Disconnected");
			
			return reply_code;
			
		} catch (IOException e) {
			
			// Log
			Log.e("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Error: " + e.toString());
			
			return -3;
			
		}
		
	}//ftp_DB_to_Remote
	
	//REF http://stackoverflow.com/questions/1560788/how-to-check-internet-access-on-android-inetaddress-never-timeouts answered Oct 24 '10 at 16:28
	public static boolean isOnline(Activity actv) {
	    ConnectivityManager cm =
	        (ConnectivityManager) actv.getSystemService(Context.CONNECTIVITY_SERVICE);
	    
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    
	    if (netInfo != null && netInfo.isConnectedOrConnecting()) {
	    	
	        return true;
	        
	    }
	    
	    return false;
	    
	}

	/******************************
		@return 
			-20 UnsupportedEncodingException<br>
			-21 ClientProtocolException in executing post<br>
			-22 IOException in executing post<br>
			-23 HttpResponse => null<br>
			-24 EntityUtils.toString => ParseException<br>
			-25 EntityUtils.toString => IOException<br>
			-26 StrinEntity => UnsupportedEncodingException<br>
	 ******************************/
	public static int 
	post_ImageData_to_Remote
	(Activity actv, TI ti) {
		////////////////////////////////

		// setup

		////////////////////////////////
		String url = CONS.Remote.Http.url_Post_ImageData;
		
//		String param = _post_ImageData_to_Remote__GetParam(actv, ti);
		HttpEntity param = _post_ImageData_to_Remote__GetParam(actv, ti);
		
		/******************************
			validate
		 ******************************/
		if (param == null) {
			
			// Log
//			String msg_Log = "Building param => JSONException";
			String msg_Log = "Building param => UnsupportedEncodingException";
	//		msg_Log = "Building param => UnsupportedEncodingException";
	//		String msg_Log = "Building param => UnsupportedEncodingException";
			Log.e("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			return -20;
			
		}
		
//		////////////////////////////////
//
//		// conv: HttpEntity => StringEntity
//
//		////////////////////////////////
//		
//		// Log
//		String tmp = null;
//		
//		try {
//			
//			tmp = EntityUtils.toString( param, HTTP.UTF_8 );
//			
//			String msg_Log = "param => " + tmp ;
//			
//			Log.d("Methods.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", msg_Log);
//			
//		} catch (org.apache.http.ParseException e2) {
//			// TODO Auto-generated catch block
//			e2.printStackTrace();
//			
//			return -24;
//			
//		} catch (IOException e2) {
//			// TODO Auto-generated catch block
//			e2.printStackTrace();
//			
//			return -25;
//			
//		}
		
		////////////////////////////////

		// HttpPost

		////////////////////////////////
		HttpPost httpPost = new HttpPost(url);
		
		//REF content-type http://d.hatena.ne.jp/hogem/20091023/1256304878
		httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
//		httpPost.setHeader("Content-type", "text/html");
		
//		httpPost.setHeader("Accept", "application/json");
//	    httpPost.setHeader("Content-type", "application/json");
		
//		try {
//			
//			httpPost.setEntity(new StringEntity(param, HTTP.UTF_8));
////			httpPost.setEntity(new StringEntity(tmp, HTTP.UTF_8));
//			
//		} catch (UnsupportedEncodingException e2) {
//			// TODO Auto-generated catch block
//			e2.printStackTrace();
//			
//			return -26;
//			
//		}
		
		httpPost.setEntity(param);
		
		// Log
//		msg_Log;
		String msg_Log;
		
		try {
			
			msg_Log = "url => " + httpPost.getURI().toURL().toString();
			
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		DefaultHttpClient dhc = new DefaultHttpClient();
		
		HttpResponse hr = null;
		
		try {
			
//			hr = dhc.execute(postRequest);
//			hr = dhc.execute(httpGet);
			hr = dhc.execute(httpPost);
			
		} catch (ClientProtocolException e) {
			
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", e.toString());
			
			return -21;
			
		} catch (IOException e) {
			
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", e.toString());
			
			return -22;
			
		}

		////////////////////////////////

		// Validate: Return

		////////////////////////////////
		if (hr == null) {
			
//			// debug
//			Toast.makeText(actv, "hr == null", 2000).show();
			
			// Log
			Log.d("TaskHTTP.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "hr == null");
			
//			return CONS.Task_GetTexts.EXECUTE_POST_NULL;
			return -23;
			
		} else {//if (hr == null)
			
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ ":"
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", "Http response => Obtained");

			
//			return null;
			
		}//if (hr == null)

		////////////////////////////////

		// Status code

		////////////////////////////////
		int status = hr.getStatusLine().getStatusCode();
		
//		if (status == CONS.HTTP_Response.CREATED
//				|| status == CONS.HTTP_Response.OK) {
		if (status == CONS.Remote.status_Created
				|| status == CONS.Remote.status_OK) {

			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ ":"
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", "status=" + status);

//			return CONS.HTTP_Response.CREATED;
			
		} else {//if (status == CONS.HTTP_Response.CREATED)
			
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ ":"
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", "status=" + status);
			
			return CONS.Remote.status_NOT_CREATED;
			
		}//if (status == CONS.HTTP_Response.CREATED)

		
		return status;
		
	}//post_ImageData_to_Remote

	/******************************
		@return null => UnsupportedEncodingException
	 ******************************/
//	private static String 
	private static HttpEntity 
	_post_ImageData_to_Remote__GetParam
	(Activity actv, TI ti) {
		
		//REF http://stackoverflow.com/questions/3288823/how-to-add-parameters-in-android-http-post answered Jul 20 '10 at 15:10
		List<NameValuePair> params = new LinkedList<NameValuePair>();

		// Android
//		android.provider.BaseColumns._ID,		// 0
//		"created_at", "modified_at",			// 1,2
//		"file_id", "file_path", "file_name",	// 3,4,5
//		"date_added", "date_modified",			// 6,7

		// Cake
//		   id			INTEGER PRIMARY KEY     AUTOINCREMENT	NOT NULL,
//		   created_at			VARCHAR(30),
//		   updated_at			VARCHAR(30),
//		   
//		   local_id				INTEGER,
//		   local_created_at		VARCHAR(30),
//		   local_modified_at	VARCHAR(30),
//		   
//		   file_id				INTEGER,
//		   file_path			TEXT,
//		   file_name			TEXT,
//		   
//		   local_date_added		VARCHAR(30),
//		   local_date_modified	VARCHAR(30),
//		   
//		   memos				TEXT,
//		   tags					TEXT,
//		   
//		   local_last_viewed_at	TEXT,
//		   table_name			TEXT

		
		params.add(new BasicNameValuePair(
				"data[Image][local_id]", 
				String.valueOf(ti.getDb_Id())));
		
		params.add(
				new BasicNameValuePair(
						"data[Image][local_created_at]", 
						ti.getCreated_at()
						)
				);
		
		params.add(
				new BasicNameValuePair(
						"data[Image][local_modified_at]", 
						ti.getModified_at()
						)
				);
		
		params.add(
				new BasicNameValuePair(
					"data[Image][file_id]", 
					String.valueOf(ti.getFileId())
				)
		);
		
		params.add(new BasicNameValuePair(
						"data[Image][file_path]", 
						ti.getFile_name()
					)
		);

		params.add(new BasicNameValuePair(
						"data[Image][file_name]", 
						ti.getFile_name()
					)
		);

//		   local_date_added		VARCHAR(30),
//		   local_date_modified	VARCHAR(30),

		
		
		params.add(new BasicNameValuePair(
				"data[Image][local_date_added]", 
//						tmp_Memo
				ti.getDate_added()
				)
		);
		
		params.add(new BasicNameValuePair(
				"data[Image][local_date_modified]", 
//						tmp_Memo
				ti.getDate_modified()
				)
		);
		
		params.add(new BasicNameValuePair(
						"data[Image][memos]", 
//						tmp_Memo
						ti.getMemo()
						)
		);
		
		params.add(
				new BasicNameValuePair(
						"data[Image][tags]", 
						ti.getTags()
						)
				);
		
//		"memos", "tags",						// 8,9
		params.add(
				new BasicNameValuePair(
						"data[Image][local_last_viewed_at]", 
						ti.getLast_viewed_at()
						)
				);
		
//		"last_viewed_at",						// 10
		params.add(new BasicNameValuePair(
						"data[Image][table_name]", 
						ti.getTable_name()
						)
		);
		
//		"table_name",							// 11
//		"uploaded_at",							// 12

		HttpEntity entity = null;
		
		try {
			
			entity = new UrlEncodedFormEntity(params, HTTP.UTF_8);
//			entity = new UrlEncodedFormEntity(params);
			
			// Log
			String msg_Log = "entity => created";
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
		} catch (UnsupportedEncodingException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return null;
			
		}
		
		return entity;
		
//		return null;
		
	}//_post_ImageData_to_Remote__GetParam

	/******************************
		@return null => JSONException
	 ******************************/
	private static String 
//	private static HttpEntity 
	_post_ImageData_to_Remote__GetParam_Json
	(Activity actv, TI ti) {
		// TODO Auto-generated method stub
//		   id			INTEGER PRIMARY KEY     AUTOINCREMENT	NOT NULL,
//		   created_at			VARCHAR(30),
//		   updated_at			VARCHAR(30),
//		   
//		   
//		   
//		   
		
		JSONObject joBody = new JSONObject();
//		JSONObject joRoot = new JSONObject();
		
		try {

//			   local_id				INTEGER,
//			   local_created_at		VARCHAR(30),
//			   local_modified_at	VARCHAR(30),
			joBody.put("local_id", ti.getDb_Id());
			joBody.put("local_created_at", ti.getCreated_at());
			joBody.put("local_modified_at", ti.getModified_at());
			
//			   file_id				INTEGER,
//			   file_path			TEXT,
//			   file_name			TEXT,
			joBody.put("file_id", ti.getFileId());
			joBody.put("file_path", ti.getFile_path());
			joBody.put("file_name", ti.getFile_name());
			
//			   local_date_added		VARCHAR(30),
//			   local_date_modified	VARCHAR(30),
			joBody.put(
						"local_date_added",
						ti.getDate_added()
			);

			joBody.put(
					"local_date_modified",
					ti.getDate_modified()
					);
			
			
//			   memos				TEXT,
//			   tags					TEXT,
//			   
//			   local_last_viewed_at	TEXT,
//			   table_name			TEXT
			joBody.put("memos", ti.getMemo());
			joBody.put("tags", ti.getTags());
			joBody.put("local_last_viewed_at", ti.getLast_viewed_at());
			joBody.put("table_name", ti.getTable_name());
			
//			joRoot.put("image_dev", joBody);
//			joRoot.put("image", joBody);
//			joBody.put("file_name", "1_v1.png");
//			joBody.put("table_name", "IFM_Android");
			
		} catch (JSONException e) {
			
			// Log
			Log.d("TaskHTTP.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ ":"
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", e.toString());
			
			return null;
			
		}
		
		// Log
		String msg_Log = "joBody => " + joBody.toString();
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		return joBody.toString();
		
	}//_post_ImageData_to_Remote__GetParam

	public static void 
	start_Activity_LogActv
	(Activity actv, Dialog d1) {
		// TODO Auto-generated method stub

		Intent i = new Intent();
		
		i.setClass(actv, LogActv.class);
		
		i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		
		actv.startActivity(i);

		d1.dismiss();
		
	}//start_Activity_LogActv

	public static void 
	start_Activity_ShowLogActv
	(Activity actv, String itemName) {
		// TODO Auto-generated method stub
		
		// Log
		String msg_Log = "itemName => " + itemName;
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		Intent i = new Intent();
		
		i.setClass(actv, ShowLogActv.class);

		i.putExtra(CONS.Intent.iKey_LogActv_LogFileName, itemName);
		
		i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		
		actv.startActivity(i);
		
		
	}//start_Activity_LogActv
	
	public static void 
	write_Log
	(Activity actv, String message,
			String fileName, int lineNumber) {
		// TODO Auto-generated method stub
		
		////////////////////////////////

		// file

		////////////////////////////////
		File fpath_Log = new File(CONS.DB.dPath_Log, CONS.DB.fname_Log);
		
		////////////////////////////////

		// file exists?

		////////////////////////////////
		if (!fpath_Log.exists()) {
			
			try {
				
				fpath_Log.createNewFile();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
				String msg = "Can't create a log file";
				Methods_dlg.dlg_ShowMessage_Duration(actv, msg, R.color.gold2);
				
				return;
				
			}
		} else {
			
			// Log
			String msg_Log = "log file => exists";
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
		}
		
		////////////////////////////////

		// write

		////////////////////////////////
		try {
			
			//REF append http://stackoverflow.com/questions/8544771/how-to-write-data-with-fileoutputstream-without-losing-old-data answered Dec 17 '11 at 12:37
			FileOutputStream fos = new FileOutputStream(fpath_Log, true);
//			FileOutputStream fos = new FileOutputStream(fpath_Log);
			
			String text = String.format(
							"[%s] [%s : %d] %s\n", 
							Methods.conv_MillSec_to_TimeLabel(
											Methods.getMillSeconds_now()),
							fileName, lineNumber,
							message
						);
			
			//REF getBytes() http://www.adakoda.com/android/000240.html
			fos.write(text.getBytes());
//			fos.write(message.getBytes());
			
//			fos.write("\n".getBytes());
			
			fos.close();
			
			// Log
			String msg_Log = "log => written";
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
//			FileChannel oChannel = new FileOutputStream(fpath_Log).getChannel();
//			
//			oChannel.wri
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}//write_Log

	public static List<String> 
	get_LogLines
	(Activity actv, String fpath_LogFile) {
		// TODO Auto-generated method stub
		
		int count_Lines = 0;
		int count_Read = 0;
		
		List<String> list = new ArrayList<String>();
		
//		File f = new File(fpath_LogFile);
		
		try {
			
//			fis = new FileInputStream(fpath_Log);

			//REF BufferedReader http://stackoverflow.com/questions/7537833/filereader-for-text-file-in-android answered Sep 24 '11 at 8:29
			BufferedReader br = new BufferedReader(
						new InputStreamReader(new FileInputStream(fpath_LogFile)));
//			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			
			String line = null;
			
			line = br.readLine();
					
			while(line != null) {
				
				list.add(line);
				
				count_Lines += 1;
				count_Read += 1;
				
				line = br.readLine();
				
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			String msg = "FileNotFoundException";
			Methods_dlg.dlg_ShowMessage(actv, msg, R.color.red);
			
			return null;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			count_Lines += 1;
			
		}
		
		// Log
		String msg_Log = String.format(
							Locale.JAPAN,
							"count_Lines => %d / count_Read => %d", 
							count_Lines, count_Read);
		
		Log.d("ShowLogActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);

		
		return list;
		
	}//get_LogLines

	public static List<LogItem> 
	conv_LogLinesList_to_LogItemList
	(Activity actv, List<String> list_RawLines) {
		// TODO Auto-generated method stub
		String msg_Log;
		
		List<LogItem> list_LogItems = new ArrayList<LogItem>();
		
		String reg = "\\[(.+?)\\] \\[(.+?)\\] (.+)";
//		String reg = "\\[(.+)\\] \\[(.+)\\] (.+)";
		
		Pattern p = Pattern.compile(reg);
		
		Matcher m = null;
		
		LogItem loi = null;
		
		for (String string : list_RawLines) {
			
			m = p.matcher(string);
			
			if (m.find()) {

				loi = _build_LogItem_from_Matcher(actv, m);
				
				if (loi != null) {
					
					list_LogItems.add(loi);
					
				}
				
			}//if (m.find())
			
		}//for (String string : list_RawLines)
		
		/******************************
			validate
		 ******************************/
		if (list_LogItems.size() < 1) {
			
			// Log
			msg_Log = "list_LogItems.size() => " + list_LogItems.size();
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
		}
		
		return list_LogItems;
		
	}//conv_LogLinesList_to_LogItemList

	/******************************
		@return
			null => Matcher.groupCount() != 3
	 ******************************/
	private static LogItem 
	_build_LogItem_from_Matcher
	(Activity actv, Matcher m) {
		// TODO Auto-generated method stub

		////////////////////////////////

		// validate

		////////////////////////////////
		if (m.groupCount() != 3) {
			
			return null;
			
		}
		
		////////////////////////////////

		// prep: data

		////////////////////////////////
		String[] tokens_TimeLabel = m.group(1).split(" ");
		
		String[] tokens_FileInfo = m.group(2).split(" : ");
		
		String text = m.group(3);
		
		String date = tokens_TimeLabel[0];
		
		String time = tokens_TimeLabel[1].split("\\.")[0];
		
		String fileName = tokens_FileInfo[0];
		
		String line = tokens_FileInfo[1];
		
		////////////////////////////////

		// LogItem

		////////////////////////////////
		LogItem loi = new LogItem.Builder()
					
					.setDate(date)
					.setTime(time)
					.setMethod(fileName)
					.setLine(Integer.parseInt(line))
					.setText(text)
					.build();
		
		return loi;
		
	}//_build_LogItem_from_Matcher

	/******************************
		@return
			null => 1. Log file => doesn't exist<br>
			//REF http://stackoverflow.com/questions/2290757/how-can-you-escape-the-character-in-javadoc answered Dec 11 '11 at 11:11<br>
			2. {@literal List<String>} list => null<br>
			3. list_LogItem => null<br>
	 ******************************/
	public static List<LogItem> 
	get_LogItem_List
	(Activity actv) {
		// TODO Auto-generated method stub
		
		String msg_Log;
		
		////////////////////////////////

		// validate: files exists

		////////////////////////////////
		File fpath_Log = new File(
				CONS.DB.dPath_Log,
				CONS.ShowLogActv.fname_Target_LogFile);
		
		if (!fpath_Log.exists()) {
			
			String msg = "Log file => doesn't exist";
			Methods_dlg.dlg_ShowMessage(actv, msg, R.color.red);
			
			return null;
			
		}
		
		////////////////////////////////

		// read file

		////////////////////////////////
		FileInputStream fis = null;
		
//		CONS.ShowLogActv.list_RawLines = new ArrayList<String>();
		
		List<String> list = 
						Methods.get_LogLines(actv, fpath_Log.getAbsolutePath());
		
		/******************************
			validate
		 ******************************/
		if (list == null) {
			
			return null;
			
		} else {
			
			////////////////////////////////
			
			// list => reverse
			
			////////////////////////////////
			Collections.reverse(list);
			
			////////////////////////////////

			// add all

			////////////////////////////////
			CONS.ShowLogActv.list_RawLines.addAll(list);
			
		}

		////////////////////////////////

		// build: LogItem list

		////////////////////////////////
		List<LogItem> list_LogItem = 
				Methods.conv_LogLinesList_to_LogItemList(
									actv, CONS.ShowLogActv.list_RawLines);

		/******************************
			validate
		 ******************************/
		if (list_LogItem == null) {
			
			// Log
			msg_Log = "list_LogItem => null";
			Log.e("ShowLogActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			return null;
			
		} else {

			// Log
			msg_Log = "list_LogItem => not null"
						+ "(" + list_LogItem.size() + ")";
			Log.d("ShowLogActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
//			CONS.ShowLogActv.list_ShowLog_Files.addAll(list_LogItem);
			
			return list_LogItem;
			
		}
		
	}//get_LogItem_List

}//public class Methods

/*
 * [2014/09/02 10:12:47.283] [MainActv.java : 84] Starting MainActv...
 * 
 * 
 */
