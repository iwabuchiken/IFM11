package ifm11.utils;



import ifm11.items.TI;
import ifm11.listener.dialog.DL;
import ifm11.main.R;
import ifm11.main.TNActv;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

// Apache
import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTPClient;

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
				db_Backup.mkdir();
				
				// Log
				Log.d("Methods.java" + "["
						+ Thread.currentThread().getStackTrace()[2].getLineNumber()
						+ "]", "Folder created: " + db_Backup.getAbsolutePath());
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
		
		int num_of_files = files_dst_folder.length;
		
		// Log
		Log.i("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "num of backup files = " + num_of_files);
		
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
			
			if (ti.getDate_added() > lastRefreshed) {
				
				lastRefreshed = ti.getDate_added();
				
			}
			
		}

		if (lastRefreshed == -1) {
			
			// In seconds. 
			label = Methods.conv_MillSec_to_TimeLabel(Methods.getMillSeconds_now());
			
		} else {
			
			// Converting sec to mill sec
			label = Methods.conv_MillSec_to_TimeLabel(lastRefreshed * 1000);
			
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
						.setDate_added(c.getLong(3))
						.setDate_modified(c.getLong(4))
						
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
		long lastRefreshedDate = 
				Methods._refresh_MainDB__Get_LastRefreshed(actv, wdb, dbu);
		
		/******************************
			validate: gotten data?
		 ******************************/
		if (lastRefreshedDate == -1) {
			
			lastRefreshedDate = 0;
			
		}
		
		// Log
		msg_Log = String.format(
						"lastRefreshedDate => %d (%s)", 
						lastRefreshedDate, 
						Methods.conv_MillSec_to_TimeLabel(lastRefreshedDate));
//		msg_Log = "lastRefreshedDate => " + lastRefreshedDate
//				+ ;
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		////////////////////////////////

		// modify: refreshed date
		//		=> convert to seconds

		////////////////////////////////
		lastRefreshedDate = lastRefreshedDate / 1000;
		
		msg_Log = String.format(
						"lastRefreshedDate(converted) => %d (%s)", 
						lastRefreshedDate, 
						Methods.conv_MillSec_to_TimeLabel(lastRefreshedDate));
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
						new String[] {String.valueOf(lastRefreshedDate)},
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
	 * Data is store in TEXT type. The method converts the value<br>
	 * 		to long type
		@return -1 => 1. query returned null<br>
	 ******************************/
	private static long 
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
			
			return -1;
			
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
			
			return -1;
			
		}
		
		////////////////////////////////

		// get: data

		////////////////////////////////
		c.moveToFirst();
		
		String lastRefreshed = c.getString(3);
		
		return Methods.conv_TimeLabel_to_MillSec(lastRefreshed);
		
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

	public static void 
	start_Activity_TNActv
	(Activity actv) {
		// TODO Auto-generated method stub
		Intent i = new Intent();
		
		i.setClass(actv, TNActv.class);
		
		i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		
		actv.startActivity(i);
		
	}//start_Activity_ImpActv

}//public class Methods

