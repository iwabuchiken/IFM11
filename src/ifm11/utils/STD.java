package ifm11.utils;

import ifm11.items.TI;
import ifm11.main.R;
import ifm11.tasks.Task_RefreshDB;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

import android.app.Activity;
import android.app.Dialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

/******************************
	STD.java
	
	collection of standard methods
 ******************************/

public class STD {

	public static boolean
	restore_DB(Activity actv) {
    	
    	// Log
		Log.d("STD.java" + "["
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
			Log.d("STD.java" + "["
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
		Log.d("STD.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "f_src_latest=" + f_src_latest.getAbsolutePath());
		
		/*********************************
		 * Restore file
		 *********************************/
//		f_src_latest = new File(CONS.DB.dPath_dbFile_backup, 
//								"ifm11_backup_20150820_115900.bk");
//		
//		// Log
//		String msg_Log;
//		
//		msg_Log = String.format(
//				Locale.JAPAN,
//				"f_src => %s", f_src_latest.getAbsolutePath()
//				);
//		
//		Log.i("STD.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_Log);
		
		String src = f_src_latest.getAbsolutePath();
		
		String dst = StringUtils.join(
				new String[]{
						//REF http://stackoverflow.com/questions/9810430/get-database-path answered Jan 23 at 11:24
						actv.getDatabasePath(CONS.DB.dbName).getPath()
				},
//						actv.getFilesDir().getPath() , 
//						CONS.DB.dbName},
				File.separator);
		
		// debug
//		boolean res = false;
		
		boolean res = STD.restore_DB(
							actv, 
							CONS.DB.dbName, 
							src, dst);
		
		// Log
		Log.d("STD.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "res=" + res);
		
		///////////////////////////////////
		//
		// log
		//
		///////////////////////////////////
		if (res == true) {
			
			String log_msg = "Restore db / done => " + f_src_latest.getName();
			
			Methods.write_Log(actv, log_msg, Thread.currentThread()
					.getStackTrace()[2].getFileName(), Thread
					.currentThread().getStackTrace()[2].getLineNumber());
			
		} else {//if (res == true)
			
			String log_msg = "Restore db / NOT done => " + f_src_latest.getName();
			
			Methods.write_Log(actv, log_msg, Thread.currentThread()
					.getStackTrace()[2].getFileName(), Thread
					.currentThread().getStackTrace()[2].getLineNumber());
			
		}//if (res == true)
		
		
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
			Log.e("STD.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception: " + e.toString());
			if (iChannel != null) {
				
				try {
					
					iChannel.close();
					
				} catch (IOException e1) {
					
					// Log
					Log.e("STD.java" + "["
						+ Thread.currentThread().getStackTrace()[2].getLineNumber()
						+ "]", "Exception: " + e.toString());
	
				}
				
			}
			
			if (iChannel != null) {
				
				try {
					
					iChannel.close();
					
				} catch (IOException e1) {
					
					// Log
					Log.e("STD.java" + "["
							+ Thread.currentThread().getStackTrace()[2].getLineNumber()
							+ "]", "Exception: " + e.toString());
					
				}
				
			}
			
			if (oChannel != null) {
				
				try {
					oChannel.close();
				} catch (IOException e1) {
					
					// Log
					Log.e("STD.java" + "["
							+ Thread.currentThread().getStackTrace()[2].getLineNumber()
							+ "]", "Exception: " + e.toString());
					
				}
				
			}
	
			return false;
			
		} catch (IOException e) {
			// Log
			Log.e("STD.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception: " + e.toString());
			
			if (iChannel != null) {
				
				try {
					
					iChannel.close();
					
				} catch (IOException e1) {
					
					// Log
					Log.e("STD.java" + "["
							+ Thread.currentThread().getStackTrace()[2].getLineNumber()
							+ "]", "Exception: " + e.toString());
					
				}
				
			}
			
			if (oChannel != null) {
				
				try {
					oChannel.close();
				} catch (IOException e1) {
					
					// Log
					Log.e("STD.java" + "["
							+ Thread.currentThread().getStackTrace()[2].getLineNumber()
							+ "]", "Exception: " + e.toString());
					
				}
				
			}
	
			
			return false;
			
		}//try
		
	}//restore_DB

	/*******************************
	 * 	 * @return
	 * 	-1	=> table "ifm11" ~~> doesn't exist<br>
	 * 	-2	=> can't build cursor<br>
	 * 	-3	=> no entry<br>
	 *******************************/
	public static int 
	refresh_MainDB
	(Activity actv) {
		
		String log_msg = "refresh_MainDB => starts";
		
		Methods.write_Log(actv, log_msg, Thread.currentThread()
				.getStackTrace()[2].getFileName(), Thread
				.currentThread().getStackTrace()[2].getLineNumber());

		////////////////////////////////

		// Set up DB(writable)
		// Execute query for image files
		// build: TI list from cursor
		// Insert data into db
		// close: db

		////////////////////////////////
		
		///////////////////////////////////
		//
		// IS13SH files	=> change dir values
		//
		///////////////////////////////////
//		_refresh_MainDB__IS13SH_ChangeDir(actv);
		
		///////////////////////////////////
		//
		// IS13SH files	=> add files: 2015-08-05_...
		//
		///////////////////////////////////
//		_refresh_MainDB__IS13SH_AddFiles_20150805(actv);
//		
//		
//		return -1;
		
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
		res = STD._refresh_MainDB__SetupTable(wdb, dbu);
//		boolean res = refreshMainDB_1_set_up_table(wdb, dbu);

		if (res == false) {
			
			// Log
			Log.d("STD.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Can't  create table");
			
			wdb.close();
			
			return -1;
			
		}//if (res == false)
		
//		//debug
//		wdb.close();
//		
//		return -1;

		///////////////////////////////////
		//
		// IS13SH files
		//
		///////////////////////////////////
//		_refresh_MainDB__IS13SH_Files();
		
		
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
			Log.e("STD.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			wdb.close();
			
			return -2;
			
		}

		/******************************
			validate: any entry?
		 ******************************/
		if (c.getCount() < 1) {
			
			// Log
			String msg_Log = "No entry";
			Log.e("STD.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			wdb.close();
			
			return -3;
			
		}
		
		////////////////////////////////

		// build: TI list from cursor

		////////////////////////////////
		List<TI> list_TI = STD._refresh_MainDB__Build_TIList(actv, c);

		/******************************
			validate: null
		 ******************************/
		if (list_TI == null) {
			
			// Log
			String msg_Log = "list_TI => Null";
			Log.e("STD.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			wdb.close();
			
			return -4;
			
		}
		
		
		// Log
		String msg_Log;
		
		msg_Log = String.format(
				Locale.JAPAN,
				"TI list: size => %d", list_TI.size()
				);
		
		Log.i("STD.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		////////////////////////////////

		// close: db

		////////////////////////////////
		wdb.close();
		
		///////////////////////////////////
		//
		// filter list
		//
		//	1. this method is used when the SD card is once
		//		pulled out from the device, and then re-inserted.
		//	2. When you do the above action, if you refresh the
		//		DB, all the image file will be in the refreshed list.
		//	3. To avoid the result described in the above step 2,
		//		you need to execute this method, so that only those image
		//		files with the dates after a certain date will be
		//		in the refreshed list.
		//	4. Once you execute the step 3, you need to remove the below
		//		executing line by commenting out or any other means.
		//
		///////////////////////////////////
//		list_TI = __refresh_MainDB__FilterList_ByFileName(list_TI);
//		
//		//debug
//		for (TI ti : list_TI) {
//			
//			// Log
////			String msg_Log;
//			
//			msg_Log = String.format(
//					Locale.JAPAN,
//					"TI file name => %s", ti.getFile_name()
//					);
//			
//			Log.i("STD.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", msg_Log);
//		}
//
//		// Log
////		String msg_Log;
//		
//		msg_Log = String.format(
//				Locale.JAPAN,
//				"__refresh_MainDB__FilterList_By_FileDate"
//				);
//		
//		Log.i("STD.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_Log);
		
//		// filter
//		list_TI = __refresh_MainDB__FilterList_By_FileDate(list_TI);
//		
//		// Log
////		String msg_Log;
//		
//		msg_Log = String.format(
//				Locale.JAPAN,
//				"list_TI size => %d", list_TI.size()
//				);
//		
//		Log.i("STD.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_Log);
//		
//		//debug
//		for (TI ti : list_TI) {
//			
//			// Log
////			String msg_Log;
//			
//			msg_Log = String.format(
//					Locale.JAPAN,
//					"TI file name => %s", ti.getFile_name()
//					);
//			
//			Log.i("STD.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", msg_Log);
//		}
		
//		////////////////////////////////
//
//		// test
		/*
		 * - SDCard => reinserted to the device
		 * - seems: last update of each file => reset to the current date
		 * - hence, need a work to fix the TI table
		 */
//
//		////////////////////////////////
//		List<TI> list_New = Methods
//						._refresh_MainDB__RecoveryFrom_SDCard_Reset(actv, list_TI);
		
		////////////////////////////////

		// Insert data into db

		////////////////////////////////
		int numOfItemsAdded = _refresh_MainDB__InsertData_TIs(actv, list_TI);
		
		// Log
//		String 
		msg_Log = "numOfItemsAdded => " + numOfItemsAdded;
		Log.d("STD.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
			
//		String log_msg = "refresh_MainDB => starts";
		
		Methods.write_Log(actv, msg_Log, Thread.currentThread()
				.getStackTrace()[2].getFileName(), Thread
				.currentThread().getStackTrace()[2].getLineNumber());

		////////////////////////////////

		// Insert: refresh date
		//		=> only if there is/are new entry/entries

		////////////////////////////////
		res = STD._refresh_MainDB__InsertData_RefreshDate(
//										actv, numOfItemsAdded, list_New);
										actv, numOfItemsAdded, list_TI);

		// Log
		msg_Log = "insert refresh date => " + res;
		Log.d("STD.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		///////////////////////////////////
		//
		// create: TNs
		//
		///////////////////////////////////
		int tmp_i = Methods._create_TNs_V2(actv, list_TI);
		
		// Log
//		String msg_Log;
		
		msg_Log = String.format(
				Locale.JAPAN,
				"TNs created => %d", tmp_i
				);
		
		Log.i("STD.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		
		return numOfItemsAdded;
		
//		return 0;
		
	}//public static int refreshMainDB(Activity actv)

	/*******************************
	 * [ backgrounds ]
	 * 1. files "2015-08-05_06..." through "..._10..."
	 * 		were not refreshed, yet the refreshed log
	 * 		had been updated<br>
	 * 2. Hence, this method was needed<br>
	 * 3. Idea is:<br>
	 * 		3-1. Get files list through MediaStore.Images.Media.EXTERNAL_CONTENT_URI<br>
	 * 		3-2. Filter the list by the above period criteria<br>
	 * 		3-3. Once you got the final list, just use
	 * 				the method "STD._refresh_MainDB__Build_TIList()"<br>
	 * 
	 *******************************/
	private static void 
	_refresh_MainDB__IS13SH_AddFiles_20150805
	(Activity actv) {
		// TODO Auto-generated method stub

		Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
		
        // Log
		String msg_Log = "uri.path => " + uri.getPath()
					+ " / "
					+ "uri.encodedPath =>" + uri.getEncodedPath()
					+ " / "
					+ "uri.getHost =>" + uri.getHost()
					;
		Log.d("STD.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
        
		String[] proj = CONS.DB.proj;

		////////////////////////////////

		// Execute query

		////////////////////////////////
		// REF=> http://blog.csdn.net/uoyevoli/article/details/4970860
		String start = "2015-08-04";
		String end = "2015-08-06";
//		String start = "2015-08-05 06";
//		String end = "2015-08-05 11";
//		String start = "2015/08/05 06:00:00.000";
//		String end = "2015/08/05 11:00:00.000";
		
//		// Log
////		String msg_Log;
//		
//		msg_Log = String.format(
//				Locale.JAPAN,
//				"start => %s, %d mills, %d secs", 
//				start, 
//				Methods.conv_TimeLabel_to_MillSec(start),
//				Methods.conv_TimeLabel_to_MillSec(start)/1000
////				"start > %s(%d)", start, Methods.conv_TimeLabel_to_MillSec(start)
//				);
//		
//		Log.i("STD.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_Log);
		
//		String  where = MediaStore.Images.Media.DISPLAY_NAME + " LIKE ?";
		
		String  where = MediaStore.Images.Media.DISPLAY_NAME + " > ? AND"
				+ " "
				+ MediaStore.Images.Media.DISPLAY_NAME + " < ?";
//		String  where = MediaStore.Images.Media.DISPLAY_NAME + " > ?";
//		String  where = MediaStore.Images.Media.DATE_ADDED + " > ?";
//		String  where = MediaStore.Images.Media.DATE_ADDED + " > ? AND "
//				+ MediaStore.Images.Media.DATE_ADDED + " < ?";
		
		String[] args = new String[]{
				
//				"2015-08-05 %"
				start, end
//				String.valueOf(Methods.conv_TimeLabel_to_MillSec(start)/1000), 
//				String.valueOf(Methods.conv_TimeLabel_to_MillSec(end)/1000), 
//				String.valueOf(Methods.conv_TimeLabel_to_MillSec(start)), 
//				String.valueOf(Methods.conv_TimeLabel_to_MillSec(end)), 
//				start, 
//				end
				
		};

		
		Cursor c = actv.managedQuery(
						uri, 
						proj,
						where, args,
//						MediaStore.Images.Media.DATE_ADDED + " > ?",
//						new String[] {String.valueOf(last_Refreshed)},
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
			Log.e("STD.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			return;
			
		/******************************
		no entry
		 ******************************/
		} else if (c.getCount() < 1) {
			
			// Log
			msg_Log = "EXTERNAL_CONTENT_URI => no entry";
			Log.e("STD.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			c.close();
			
			return;
			
		}
		
		// Log
		msg_Log = "cursor: count => " + c.getCount();
		Log.d("STD.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		
		///////////////////////////////////
		//
		// build: TI list
		//
		///////////////////////////////////
		List<TI> list_TI = STD._refresh_MainDB__Build_TIList(actv, c);
		
		/******************************
			validate: null
		 ******************************/
		if (list_TI == null) {
			
			// Log
//			String 
			msg_Log = "list_TI => Null";
			Log.e("STD.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			return;
			
		}
		
		
		// Log
//		String msg_Log;
		
		msg_Log = String.format(
				Locale.JAPAN,
				"TI list: size => %d", list_TI.size()
				);
		
		Log.i("STD.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
//		// report
//		for (TI ti : list_TI) {
//			
//			// Log
////			String msg_Log;
//			
//			msg_Log = String.format(
//					Locale.JAPAN,
//					"TI => %s", ti.getFile_name()
//					);
//			
//			Log.i("STD.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", msg_Log);
//		}
		
		///////////////////////////////////
		//
		// filter list
		//
		///////////////////////////////////
		List<TI> list_TI_filtered = new ArrayList<TI>();
		
		String tmp_str;
		
		int cmp_start;
		int cmp_end;
		
		for (TI ti : list_TI) {
			
			tmp_str = ti.getFile_name();
			
			cmp_start = tmp_str.compareToIgnoreCase("2015-08-05_06");
			cmp_end = tmp_str.compareToIgnoreCase("2015-08-05_11");
			
			if (cmp_start > 0 && cmp_end < 0) {

				list_TI_filtered.add(ti);

			}//if (cmp_start > 0 && cmp_end < 0)
			
//			// Log
////			String msg_Log;
//			
//			msg_Log = String.format(
//					Locale.JAPAN,
//					"ti => %s / cmp_start = %d, cmp_end = %d", 
//					ti.getFile_name(), cmp_start, cmp_end
//					);
//			
//			Log.i("STD.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", msg_Log);
			
		}
		
		// report
		for (TI ti : list_TI_filtered) {
			
			// Log
//			String msg_Log;
			
			msg_Log = String.format(
					Locale.JAPAN,
					"ti_filtered => %s (file id = %d)", ti.getFile_name(), ti.getFileId()
					);
			
			Log.i("STD.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
		}
		
		///////////////////////////////////
		//
		// close: cursor
		//
		///////////////////////////////////
		c.close();
		
		////////////////////////////////

		// Insert data into db

		////////////////////////////////
//		int numOfItemsAdded = _refresh_MainDB__InsertData_TIs(actv, list_New);
		int numOfItemsAdded = _refresh_MainDB__InsertData_TIs(actv, list_TI_filtered);
//		int numOfItemsAdded = _refresh_MainDB__InsertData_Image(actv, wdb, dbu, c);
		
		// Log
//		String 
		msg_Log = "numOfItemsAdded => " + numOfItemsAdded;
		Log.d("STD.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);

		
	}//_refresh_MainDB__IS13SH_AddFiles_20150805

	private static void 
	_refresh_MainDB__IS13SH_ChangeDir(Activity actv) {
		// TODO Auto-generated method stub
		
		String start = "2015-08-05_19";
		String end = "2015-08-10_08";
		
		////////////////////////////////

		// Set up DB(writable)

		////////////////////////////////
		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
		
		SQLiteDatabase wdb = dbu.getWritableDatabase();

		///////////////////////////////////
		//
		// query
		//
		///////////////////////////////////
//		"file_id", "file_path", "file_name",	// 0,1,2
		
		String where = CONS.DB.col_names_IFM11[2] + " > ? AND "
//				String where = CONS.DB.col_names_IFM11[2] + " > ? OR "
						+ CONS.DB.col_names_IFM11[2] + " < ?";
		
		String[] args = new String[]{
			
					start, end
					
		};

		Cursor c = wdb.query(
//				Cursor c = qb.query(
//					this.db, 
					CONS.DB.tname_IFM11,
					CONS.DB.col_names_IFM11_full, 
					where, args, 
//					null, null, 
					null, null, null);

		///////////////////////////////////
		//
		// validate: null
		//
		///////////////////////////////////
		if (c == null) {

			// Log
			String msg_Log;
			
			msg_Log = String.format(
					Locale.JAPAN,
					"cursor => null"
					);
			
			Log.e("STD.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			wdb.close();
			
			return;

		} else if (c.getCount() < 1) {//if (c == null)
			
			// Log
			String msg_Log;
			
			msg_Log = String.format(
					Locale.JAPAN,
					"cursor => less than 1"
					);
			
			Log.e("STD.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			wdb.close();
			
			return;
			
		}//if (c == null)
		
		// Log
		String msg_Log;
		
		msg_Log = String.format(
				Locale.JAPAN,
				"cursor size => %d", c.getCount()
				);
		
		Log.i("STD.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);

		///////////////////////////////////
		//
		// conv: cursor to TI list
		//
		///////////////////////////////////
		List<TI> tmp_list = Methods.conv_Cursor_2_TIList(actv, c);
//		
		// validate: null
		if (tmp_list == null) {

			msg_Log = String.format(
					Locale.JAPAN,
					"tmp_list => null"
					);
			
			Log.i("STD.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			wdb.close();
			
			return;

		} else if (tmp_list.size() < 1) {//if (tmp_list == null)

			msg_Log = String.format(
					Locale.JAPAN,
					"tmp_list => less than 1"
					);
			
			Log.i("STD.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			wdb.close();
			
			return;

		}//if (tmp_list == null)
		
		// Log
//		String msg_Log;
		
		msg_Log = String.format(
				Locale.JAPAN,
				"tmp_list => %d", tmp_list.size()
				);
		
		Log.i("STD.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		///////////////////////////////////
		//
		// close: cursor
		//
		///////////////////////////////////
		c.close();
		
		///////////////////////////////////
		//
		// TI list: update
		//
		///////////////////////////////////
		String tmp_str;
		
		int res_i;
		
		for (TI ti : tmp_list) {

//			/mnt/sdcard-ext/dcim/Camera
//			/mnt/sdcard-ext/dcim/100SHARP
			ti.setFile_path("/mnt/sdcard-ext/dcim/100SHARP");
			
			// update
			res_i = DBUtils.update_TI__All(actv, ti);
			
			// Log
//			String msg_Log;
			
			msg_Log = String.format(
					Locale.JAPAN,
					"TI: %s(%d) => %d", ti.getFile_name(), ti.getDb_Id(), res_i
					);
			
			Log.i("STD.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
		}
		
		
		///////////////////////////////////
		//
		// close
		//
		///////////////////////////////////
		wdb.close();
		
		
		
		
	}//_refresh_MainDB__IS13SH_ChangeDir

	private static void 
	_refresh_MainDB__IS13SH_Files() {
		// TODO Auto-generated method stub
		///////////////////////////////////
		//
		// get: file name list
		//
		///////////////////////////////////
		File dpath_Src = new File("mnt/sdcard-ext/dcim/100SHARP");
		File dpath_Dst = new File("mnt/sdcard-ext/dcim/100SHARP");
		
		
//		String[] fname_list = dpath_Src.list(new FilenameFilter(){
		File[] fname_list = dpath_Src.listFiles(new FilenameFilter(){

			@Override
			public boolean accept(File dir, String name) {
				// TODO Auto-generated method stub

				if (name.startsWith("DSC")) {
					
					return true;
					
				} else {//if (name.startsWith("DSC"))
					
					return false;
					
				}//if (name.startsWith("DSC"))
				
			}
			
		});

		int tmp_len = fname_list.length;
		
		String tmp_str;
		File tmp_file;
		
		for (int i = 0; i < tmp_len; i++) {
			
			tmp_file = fname_list[i];
//			tmp_str = fname_list[i];
			
			// Log
			String msg_Log;
			
			msg_Log = String.format(
					Locale.JAPAN,
					"fname => %s (%s)", 
					tmp_file.getName(), 
					Methods.conv_MillSec_to_TimeLabel(tmp_file.lastModified())
//					"fname => %s", tmp_str
					);
			
			Log.i("STD.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
//			
//			if (tmp_str.startsWith("DSC")) {
//
//				// Log
//				String msg_Log;
//				
//				msg_Log = String.format(
//						Locale.JAPAN,
//						"100SHART: %s", fname_list[i]
//						);
//				
//				Log.i("STD.java" + "["
//						+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//						+ "]", msg_Log);
//
//			}//if (tmp_str.startsWith("DSC"))
			
			
		}

		///////////////////////////////////
		//
		// copy
		//
		///////////////////////////////////
		File f_Src = null;
		File f_Dst = null;
		
		String[] a_tmp_str;
		String[] a_tmp_str_2;
		String[] a_tmp_str_3;
		
		String tmp_micros;
		
		StringBuilder sb = null;
		
		for (File file : fname_list) {
			
			f_Src = new File(dpath_Src, file.getName());
			
//			08-13 11:01:39.446: I/STD.java[541](13063): fname => DSC_0051.JPG (2015/08/10 07:05:24.000)
			a_tmp_str = Methods.conv_MillSec_to_TimeLabel(file.lastModified()).split(" ");
			
			a_tmp_str_2 = a_tmp_str[0].split("/");
			
			tmp_micros = a_tmp_str[1].split("\\.")[1];
			
			a_tmp_str_3 = a_tmp_str[1].split("\\.")[0].split(":");
			
			sb = new StringBuilder();
			
			sb.append(a_tmp_str_2[0]);
			sb.append("-");
			sb.append(a_tmp_str_2[1]);
			sb.append("-");
			sb.append(a_tmp_str_2[2]);
			
			sb.append("_");
			
			sb.append(a_tmp_str_3[0]);
			sb.append("-");
			sb.append(a_tmp_str_3[1]);
			sb.append("-");
			sb.append(a_tmp_str_3[2]);
			
			sb.append("_");
			sb.append(tmp_micros);
			
			sb.append(".jpg");
			
			f_Dst = new File(dpath_Dst, sb.toString());
			
			// Log
			String msg_Log;
			
			msg_Log = String.format(
					Locale.JAPAN,
					"src = %s / dst = %s", f_Src.getName(), f_Dst.getName()
					);
			
			Log.i("STD.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);

			///////////////////////////////////
			//
			// copy
			//
			///////////////////////////////////
			FileChannel iChannel;
			FileChannel oChannel;
			
			try {
				iChannel = new FileInputStream(f_Src).getChannel();
				
				oChannel = new FileOutputStream(f_Dst).getChannel();
				
				iChannel.transferTo(0, iChannel.size(), oChannel);
				
				iChannel.close();
				oChannel.close();
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}//for (File file : fname_list)
		
////		File f_Src = new File("mnt/sdcard-ext/dcim/100SHARP");
////		File f_Dst = new File("mnt/sdcard-ext/dcim/100SHARP");
//		
//		FileChannel iChannel;
//		FileChannel oChannel;
//		
//		try {
//			iChannel = new FileInputStream(f_Src).getChannel();
//			
//			oChannel = new FileOutputStream(f_Dst).getChannel();
//			
//			iChannel.transferTo(0, iChannel.size(), oChannel);
//			
//			iChannel.close();
//			oChannel.close();
//			
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		
	}//_refresh_MainDB__IS13SH_Files
	

	private static List<TI> 
	__refresh_MainDB__FilterList_ByFileName
	(List<TI> list_TI) {
		// TODO Auto-generated method stub
		
		List<TI> tmp_list = new ArrayList();
		
		TI tmp_ti = list_TI.get(0);
		
//		// Log
//		String msg_Log;
//		
//		msg_Log = String.format(
//				Locale.JAPAN,
//				"list[0] file name => %s", tmp_ti.getFile_name()
//				);
//		
//		Log.i("STD.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_Log);
		
		///////////////////////////////////
		//
		// threshold
		//
		///////////////////////////////////
//		String th = "2015-08-14";
		String th = "2015-08-01";
		
		int res;
		
		for (TI ti : list_TI) {
			
			res = ti.getFile_name().compareToIgnoreCase(th);
			
			if (res == 1) {

				tmp_list.add(ti);

			}//if (res == 1)
			
		}
		
		// Log
		String msg_Log;
		
		msg_Log = String.format(
				Locale.JAPAN,
				"tmp_list: size => %d", tmp_list.size()
				);
		
		Log.i("STD.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		return tmp_list;
//		return list_TI;
		
	}//__refresh_MainDB__FilterList_ByFileName

	private static List<TI> 
	__refresh_MainDB__FilterList_By_FileDate
	(List<TI> list_TI) {
		// TODO Auto-generated method stub
		
		List<TI> tmp_list = new ArrayList();
		
		///////////////////////////////////
		//
		// threshold
		//
		///////////////////////////////////
		String th = "2015-08-14_07";
		
		int res;
		
		for (TI ti : list_TI) {
			
			// validate: "2015"
			if (!ti.getFile_name().startsWith("2015")) {

				continue;

			}//if (!ti.getFile_name().startsWith("2015"))
			
			res = ti.getFile_name().compareToIgnoreCase(th);
			
			if (res > 0) {
//				if (res == 1) {
				
				tmp_list.add(ti);
				
			}//if (res == 1)
			
		}
		
		// Log
		String msg_Log;
		
		msg_Log = String.format(
				Locale.JAPAN,
				"tmp_list: size => %d", tmp_list.size()
				);
		
		Log.i("STD.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		return tmp_list;
//		return list_TI;
		
	}//__refresh_MainDB__FilterList_ByFileName
	
	private static List<TI> 
	_refresh_MainDB__RecoveryFrom_SDCard_Reset
	(Activity actv, List<TI> list_TI) {

		////////////////////////////////

		// steps
		/*
		 * 	1. build TI list (all files => number is: 4501 or something)
		 * 	2. filter the list => only those starting with "2014-11"
		 * 							(currently, 12 of them)
		 * 	3. Insert those filtered ones
		 */

		////////////////////////////////
		
		////////////////////////////////

		// setup

		////////////////////////////////
		List<TI> list_New = new ArrayList<TI>();
		
		int size = list_TI.size();
		
		// Log
		String msg_Log = "list_TI.size() => " + size;
		Log.d("STD.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);

		////////////////////////////////

		// filter list

		////////////////////////////////
		for (int i = 0; i < size; i++) {
			
			if (list_TI.get(i).getFile_name().startsWith("2014-11")) {
				
				list_New.add(list_TI.get(i));
				
			}
		}
		
		// Log
		msg_Log = "list_New.size() => " + list_New.size();
		Log.d("STD.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
//		int limit = 30;
		
		for (int i = 0; i < list_New.size(); i++) {
//			for (int i = size - limit; i < size; i++) {
			
			// Log
			msg_Log = "ti(new) => " + list_New.get(i).getFile_name();
//			msg_Log = "ti => " + list_TI.get(i).getFile_name();
			Log.d("STD.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
		}
		
		////////////////////////////////

		// return

		////////////////////////////////
		return list_New;

	}//_refresh_MainDB__RecoveryFrom_SDCard_Reset
	

	static boolean 
	_refresh_MainDB__InsertData_RefreshDate
	(Activity actv, 
			int numOfItemsAdded, List<TI> list_TI) {
		
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
			label = Methods.conv_MillSec_to_TimeLabel(STD.getMillSeconds_now());
			
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
	

	static int 
	_refresh_MainDB__InsertData_TIs
	(Activity actv, List<TI> list_TI) {
		
		
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
	static List<TI> 
	_refresh_MainDB__Build_TIList
	(Activity actv, Cursor c) {
		
		
		List<TI> list_TI = new ArrayList<TI>();
		
		while(c.moveToNext()) {
			
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
//		Log.d("STD.java" + "["
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
		
		// Log
		Log.d("STD.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "c.getLong(3) => " + c.getLong(3));
		

		return numOfItemsAdded;
		
	}//private static int refreshMainDB_3_insert_data(Cursor c)

	/******************************
		@return false => Table(ifm11) doesn't exist; can't create one
	 ******************************/
	static boolean 
	_refresh_MainDB__SetupTable
	(SQLiteDatabase wdb, DBUtils dbu) {
		/*----------------------------
		 * 2-1.1. baseDirName
			----------------------------*/
		String tableName = CONS.DB.tname_IFM11;
		boolean result = dbu.tableExists(wdb, tableName);
		
		// If the table doesn't exist, create one
		if (result == false) {

			Log.d("STD.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Table doesn't exist: " + tableName);
			
			result = dbu.createTable(
							wdb, 
							tableName, 
							CONS.DB.col_names_IFM11, 
							CONS.DB.col_types_IFM11);
			
			if (result == false) {

				Log.d("STD.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", "Can't create a table: "+ tableName);
				
				return false;
				
			} else {//if (result == false)
				
				Log.d("STD.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", "Table created: "+ tableName);
				
				return true;
				
			}//if (result == false)

		} else {//if (result == false)
			
			Log.d("STD.java" + "["
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
	static Cursor 
	_refresh_MainDB__ExecQuery
	(Activity actv, SQLiteDatabase wdb, DBUtils dbu) {
		Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        
        // Log
		String msg_Log = "uri.path => " + uri.getPath()
					+ " / "
					+ "uri.encodedPath =>" + uri.getEncodedPath()
					+ " / "
					+ "uri.getHost =>" + uri.getHost()
					;
		Log.d("STD.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
        
		String[] proj = CONS.DB.proj;

		////////////////////////////////

		// setup: table: refresh log

		////////////////////////////////
		boolean res = STD._refresh_MainDB__Setup_RefreshLog(actv, wdb, dbu);
		
		if (res == false) {
			
			// Log
			msg_Log = "Setup can't be done => refresh_log  table";
			Log.d("STD.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			return null;
			
		} else {

			// Log
			msg_Log = "setup done => rehresh log";
			Log.d("STD.java" + "["
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
				STD._refresh_MainDB__Get_LastRefreshed(actv, wdb, dbu);
		
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
		msg_Log = String.format(Locale.JAPAN,
						"last_Refreshed => %d (%s)", 
						last_Refreshed, 
						Methods.conv_MillSec_to_TimeLabel(last_Refreshed));
//		msg_Log = "lastRefreshedDate => " + lastRefreshedDate
//				+ ;
		Log.d("STD.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		////////////////////////////////

		// modify: refreshed date
		//		=> convert to seconds

		////////////////////////////////
		last_Refreshed = last_Refreshed / 1000;
		
		msg_Log = String.format(Locale.JAPAN,
						"last_Refreshed(converted) => %d (%s)", 
						last_Refreshed, 
						Methods.conv_MillSec_to_TimeLabel(last_Refreshed));
		//msg_Log = "lastRefreshedDate => " + lastRefreshedDate
		//		+ ;
		Log.d("STD.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
//		String log_msg = "Log in => Succeeded: " + ti.getFile_name();
		Methods.write_Log(actv, 
				msg_Log, 
				Thread.currentThread()
					.getStackTrace()[2].getFileName(), Thread
					.currentThread().getStackTrace()[2].getLineNumber());
		
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
			Log.e("STD.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			return null;
			
		/******************************
		no entry
		 ******************************/
		} else if (c.getCount() < 1) {
			
			// Log
			msg_Log = "EXTERNAL_CONTENT_URI => no entry";
			Log.e("STD.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			return null;
			
		}
		
		// Log
		msg_Log = "cursor: count => " + c.getCount();
		Log.d("STD.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
//		// Log
//		Log.d("STD.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", "Last refreshed (in sec): " + String.valueOf(lastRefreshedDate / 1000));
//
//        actv.startManagingCursor(c);
//        
//        // Log
//		Log.d("STD.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", "c.getCount() => " + c.getCount());
//
//		return c;
		
        return c;
        
	}//_refresh_MainDB__ExecQuery

	/******************************
		@return null => 1. Can't prepare the table 'refresh log'<br>
						2. Cursor => null<br>
						3. Cursor => count < 1<br>
	 ******************************/
	static Cursor 
	_refresh_MainDB__ExecQuery__Period
	(Activity actv,
		SQLiteDatabase wdb, DBUtils dbu, long start, long end) {
		
		Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
		
		String[] proj = CONS.DB.proj;
		
		String msg_Log;
		
		////////////////////////////////
		
		// setup: table: refresh log
		
		////////////////////////////////
		boolean res = STD._refresh_MainDB__Setup_RefreshLog(actv, wdb, dbu);
		
		if (res == false) {
			
			// Log
			msg_Log = "Setup can't be done => refresh_log  table";
			Log.d("STD.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			return null;
			
		} else {
			
			// Log
			msg_Log = "setup done => rehresh log";
			Log.d("STD.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
		}
		
		long last_Refreshed = end;
		
		////////////////////////////////
		
		// Execute query
		
		////////////////////////////////
		
		// Log
		msg_Log = String.format(
					Locale.JAPAN,
					"start => %d(%s), end => %d(%s)", 
					start,
					Methods.conv_MillSec_to_TimeLabel(start),
					end,
					Methods.conv_MillSec_to_TimeLabel(end));
		
		Log.d("STD.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		// REF=> http://blog.csdn.net/uoyevoli/article/details/4970860
		Cursor c = actv.managedQuery(
				uri, 
				proj,
				MediaStore.Images.Media.DATE_ADDED + " > ?",
//					+ " AND "
//					+ MediaStore.Images.Media.DATE_ADDED + " < ?",
				new String[] {
						
//						String.valueOf(start / 1000),
						String.valueOf(end / 1000),
						
				},
//						new String[] {String.valueOf(lastRefreshedDate)},
				null);
		
		/******************************
			debug
		 ******************************/
		String lastRefreshedDate = 
				STD._refresh_MainDB__Get_LastRefreshed(actv, wdb, dbu);

		last_Refreshed = Methods.conv_TimeLabel_to_MillSec(lastRefreshedDate);
		
		// Log
		msg_Log = String.format(
				Locale.JAPAN,
				"start => %d(%s), end => %d(%s), last_Refreshed => %d(%s)", 
				start,
				Methods.conv_MillSec_to_TimeLabel(start),
				end,
				Methods.conv_MillSec_to_TimeLabel(end),
				last_Refreshed,
				Methods.conv_MillSec_to_TimeLabel(last_Refreshed));
				
		Log.d("STD.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		c = actv.managedQuery(
				uri, 
				proj,
				MediaStore.Images.Media.DATE_ADDED + " > ?",
//					+ " AND "
//					+ MediaStore.Images.Media.DATE_ADDED + " < ?",
				new String[] {
						
//						String.valueOf(start / 1000),
						String.valueOf(last_Refreshed / 1000),
						
				},
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
			Log.e("STD.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			return null;
			
			/******************************
		no entry
			 ******************************/
		} else if (c.getCount() < 1) {
			
			// Log
			msg_Log = "EXTERNAL_CONTENT_URI => no entry";
			Log.e("STD.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			return null;
			
		}
		
		// Log
		msg_Log = "cursor: count => " + c.getCount();
		Log.d("STD.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
//		// Log
//		Log.d("STD.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", "Last refreshed (in sec): " + String.valueOf(lastRefreshedDate / 1000));
//
//        actv.startManagingCursor(c);
//        
//        // Log
//		Log.d("STD.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", "c.getCount() => " + c.getCount());
//
//		return c;
		
		return c;
		
	}//_refresh_MainDB__ExecQuery__Period
	
	/******************************
	 * Data is stored in TEXT type. The method returns a String<br>
	 * 
		@return null => 1. query returned null<br>
						2. query found no entry<br>
	 ******************************/
	static String 
	_refresh_MainDB__Get_LastRefreshed
	(Activity actv, SQLiteDatabase wdb, DBUtils dbu) {
		
		
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
			Log.e("STD.java" + "["
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
			Log.e("STD.java" + "["
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
		
		
		boolean result = dbu.tableExists(wdb, CONS.DB.tname_RefreshLog);
		
		if (result != false) {
			
			// Log
			String msg_Log = "table exists => " + CONS.DB.tname_RefreshLog;
			Log.d("STD.java" + "["
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
			Log.d("STD.java"
				+ "["
				+ Thread.currentThread().getStackTrace()[2]
				.getLineNumber() + "]", "Table created => " + CONS.DB.tname_RefreshLog);
			
			return true;
			
		} else {//if (result == true)
			
			// Log
			Log.d("STD.java"
				+ "["
				+ Thread.currentThread().getStackTrace()[2]
				.getLineNumber() + "]", 
				"Create table failed: " + CONS.DB.tname_RefreshLog);
		
			return false;
			
		}//if (result == true)
		
	}//_refresh_MainDB__Setup_RefreshLog

	/******************************
		@return
			false => 1. No db file<br>
	 ******************************/
	public static boolean 
	import_DB
	(Activity actv, Dialog dlg1) {
		
		
		////////////////////////////////
	
		// setup: src, dst
	
		////////////////////////////////
		// IFM10
		String src_dir = CONS.DB.dPath_dbFile_backup_IFM10;
	//	String src_dir = CONS.DB.dPath_dbFile_backup;
		
		File f_dir = new File(src_dir);
		
		File[] src_dir_files = f_dir.listFiles();
		
		// If no files in the src dir, quit the method
		if (src_dir_files.length < 1) {
			
			// Log
			Log.d("STD.java" + "["
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
		Log.d("STD.java" + "["
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
	//					actv.getFilesDir().getPath() , 
	//					CONS.DB.dbName},
				File.separator);
		
		// Log
		String msg_Log = "db path => " 
					+ actv.getDatabasePath(CONS.DB.dbName).getPath();
		Log.d("STD.java" + "["
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
		msg_Log = String.format(Locale.JAPAN,
							"src = %s // dst = %s", 
							src, dst_New);
		
		Log.d("STD.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		////////////////////////////////
	
		// import (using restoration-related method)
	
		////////////////////////////////
		boolean res = STD.restore_DB(
							actv, 
							CONS.DB.dbName, 
							src, dst_New);
		
		// Log
		Log.d("STD.java" + "["
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
	
	//	return false;
		
	}//import_DB
	
	/******************************
		@return
			false => 1. No db file<br>
	 ******************************/
	public static boolean 
	import_DB
	(Activity actv, String fpath_DB) {
		
		////////////////////////////////
		
		// Restore file
		
		////////////////////////////////
		String src = fpath_DB;
		
		String dst = actv.getDatabasePath(CONS.DB.dbName).getPath();
		
		// Log
		String msg_Log = "db path => " 
				+ actv.getDatabasePath(CONS.DB.dbName).getPath();
		Log.d("STD.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		////////////////////////////////
		
		// build: db file path (dst)
		
		////////////////////////////////
		String tmp_str = Methods.get_Dirname(actv, dst);
		
		String dst_New = StringUtils.join(
				new String[]{
						
						tmp_str,
						CONS.DB.dbName_Previous
						
				}, 
				File.separator);
		
		// Log
		msg_Log = String.format(Locale.JAPAN,
				"src = %s // dst = %s", 
				src, dst_New);
		
		Log.d("STD.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		////////////////////////////////
		
		// import (using restoration-related method)
		
		////////////////////////////////
	//	boolean res = true;
		boolean res = STD.restore_DB(
				actv, 
				CONS.DB.dbName, 
				src, dst_New);
		
		// Log
		Log.d("STD.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "res=" + res);
		
		////////////////////////////////
		
		// return
		
		////////////////////////////////
		return res;
		
	//	return false;
		
	}//import_DB

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

	/*******************************
	 * 	 * 	 * @return
	 * 	-1	=> table "ifm11" ~~> doesn't exist<br>
	 * 	-2	=> can't build cursor<br>
	 * 	-3	=> no entry<br>
	 * @param task 
	 *******************************/
	public static int 
	refresh_MainDB__PreExecute
	(Activity actv, Task_RefreshDB task) {
		// TODO Auto-generated method stub
		
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
		res = STD._refresh_MainDB__SetupTable(wdb, dbu);
//		boolean res = refreshMainDB_1_set_up_table(wdb, dbu);

		if (res == false) {
			
			// Log
			Log.d("STD.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Can't  create table");
			
			wdb.close();
			
			return -1;
			
		}//if (res == false)
		
//		//debug
//		wdb.close();
//		
//		return -1;

		///////////////////////////////////
		//
		// IS13SH files
		//
		///////////////////////////////////
//		_refresh_MainDB__IS13SH_Files();
		
		
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
			Log.e("STD.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			wdb.close();
			
			return -2;
			
		}

		/******************************
			validate: any entry?
		 ******************************/
		if (c.getCount() < 1) {
			
			// Log
			String msg_Log = "No entry";
			Log.e("STD.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			wdb.close();
			
			return -3;
			
		}

		/*******************************
		 * more than X files to be refreshed
		 *******************************/
		if (c.getCount() > CONS.DB.REFRESH_MAX) {

			// set the flag --> false
			CONS.DB.REFRESH_YES = false;
			
			
			String message = String.format(
					Locale.JAPAN,
					"More than %d files --> refresh?", CONS.DB.REFRESH_MAX
					);

			Dialog d1 = Methods_dlg.dlg_Tmpl_OkCancel(
							actv, 
							R.layout.dlg_tmpl_toast_ok_cancel, 
							R.string.generic_tv_confirm, 
							
							R.id.dlg_tmpl_toast_ok_cancel_bt_ok, 
							R.id.dlg_tmpl_toast_ok_cancel_bt_cancel, 
							Tags.DialogTags.REFRESH_YES, 
							Tags.DialogTags.REFRESH_NO,
							task);
			
			TextView tv = (TextView) d1.findViewById(R.id.dlg_tmpl_toast_ok_cancel_tv_message);
			
			tv.setText(message);
			
			d1.show();
			
//			Methods_dlg.dlg_ShowMessage(
//							actv, 
//							message, 
//							R.color.gold2, 
//							Tags.DialogTags.REFRESH_YES);

		}//if (c.getCount() > CONS.DB.REFRESH_MAX)
		
		////////////////////////////////

		// close: db

		////////////////////////////////
		wdb.close();

		// Log
		String msg_Log;
		
		msg_Log = String.format(
				Locale.JAPAN,
				"db => closed"
				);
		
		Log.i("STD.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		return -1;
		
	}//refresh_MainDB__PreExecute

}
