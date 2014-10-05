package ifm11.utils;



import ifm11.items.TI;
import ifm11.items.WordPattern;
import ifm11.main.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
//import android.view
import android.widget.LinearLayout.LayoutParams;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

/****************************************
 * Copy & pasted from => C:\WORKS\WORKSPACES_ANDROID\ShoppingList\src\shoppinglist\main\DBUtils.java
 ****************************************/
public class DBUtils extends SQLiteOpenHelper{

	/*****************************************************************
	 * Class fields
	 *****************************************************************/
	 // DB name
	String dbName = null;
//	static String dbName = null;
	
	// Activity
	Activity activity;
	
	//
	Context context;

	/*********************************
	 * DB
	 *********************************/
	// Database
	SQLiteDatabase db = null;

	/*****************************************************************
	 * Constructor
	 *****************************************************************/
	public DBUtils(Context context, String dbName) {
		super(context, dbName, null, 1);
		
		// Initialize activity
		this.activity = (Activity) context;
		
		this.context = context;
		
		this.dbName = dbName;
		
	}//public DBUtils(Context context)

	/*******************************************************
	 * Methods
	 *******************************************************/
	@Override
	public void onCreate(SQLiteDatabase db) {
		
	}//public void onCreate(SQLiteDatabase db)

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		
	}

	/****************************************
	 * createTable_generic()
	 * 
	 * <Caller> 
	 * 1. 
	 * 
	 * <Desc> 1. <Params> 1.
	 * 
	 * <Return> 1.
	 * 
	 * <Steps> 1.
	 ****************************************/
	public boolean createTable
	(SQLiteDatabase db, String tableName, 
			String[] columns, String[] types) {
		/*----------------------------
		 * Steps
		 * 1. Table exists?
		 * 2. Build sql
		 * 3. Exec sql
			----------------------------*/
		
		//
//		if (!tableExists(db, tableName)) {
		if (tableExists(db, tableName)) {
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Table exists => " + tableName);
			
			return false;
		}//if (!tableExists(SQLiteDatabase db, String tableName))
		
		/*----------------------------
		 * 2. Build sql
			----------------------------*/
		//
		StringBuilder sb = new StringBuilder();
		
		sb.append("CREATE TABLE " + tableName + " (");
		sb.append(android.provider.BaseColumns._ID +
							" INTEGER PRIMARY KEY AUTOINCREMENT, ");
		
		// created_at, modified_at
		sb.append("created_at TEXT, modified_at TEXT, ");
		
		int i = 0;
		for (i = 0; i < columns.length - 1; i++) {
			sb.append(columns[i] + " " + types[i] + ", ");
		}//for (int i = 0; i < columns.length - 1; i++)
		
		sb.append(columns[i] + " " + types[i]);
		
		sb.append(");");
		
		// Log
		Log.d("DBUtils.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "sql => " + sb.toString());
		
		/*----------------------------
		 * 3. Exec sql
			----------------------------*/
		//
		try {
//			db.execSQL(sql);
			db.execSQL(sb.toString());
			
			// Log
			Log.d(this.getClass().getName() + 
					"["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Table created => " + tableName);
			
			
			return true;
		} catch (SQLException e) {
			// Log
			Log.e(this.getClass().getName() + 
					"[" + Thread.currentThread().getStackTrace()[2].getLineNumber() + "]", 
					"Exception => " + e.toString());
			
			return false;
		}//try
		
	}//public boolean createTable(SQLiteDatabase db, String tableName)

	/******************************
		createTable()
		
		@param columns, types => use non-full version
		@return true => Table created or exists
	 ******************************/
	public boolean createTable
	(Activity actv, String tableName, String[] columns, String[] types)
	{
		/*----------------------------
		 * Steps
		 * 1. Table exists?
		 * 2. Build sql
		 * 3. Exec sql
			----------------------------*/
//		DBUtils dbu = new DBUtils(actv, dbName);
		
		//
		SQLiteDatabase wdb = this.getWritableDatabase();
//		SQLiteDatabase wdb = dbu.getWritableDatabase();
		
		//
		//if (!tableExists(db, tableName)) {
		if (tableExists(wdb, tableName)) {
			// Log
			Log.i("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Table exists => " + tableName);
			
			// debug
			String msg_Toast = "Table exists => " + tableName;
			Toast.makeText(actv, msg_Toast, Toast.LENGTH_SHORT).show();
			
			
			return true;
//			return false;
		}//if (!tableExists(SQLiteDatabase db, String tableName))
		
		/*----------------------------
		 * 2. Build sql
			----------------------------*/
		//
		StringBuilder sb = new StringBuilder();
		
		sb.append("CREATE TABLE " + tableName + " (");
		sb.append(android.provider.BaseColumns._ID +
							" INTEGER PRIMARY KEY AUTOINCREMENT, ");
		
		// created_at, modified_at
		sb.append("created_at TEXT, modified_at TEXT, ");
//		sb.append("created_at INTEGER, modified_at INTEGER, ");
		
		int i = 0;
		for (i = 0; i < columns.length - 1; i++) {
			sb.append(columns[i] + " " + types[i] + ", ");
		}//for (int i = 0; i < columns.length - 1; i++)
		
		sb.append(columns[i] + " " + types[i]);
		
		sb.append(");");
		
		// Log
		Log.d("DBUtils.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "sql => " + sb.toString());
		
		/*----------------------------
		 * 3. Exec sql
			----------------------------*/
		//
		try {
		//	db.execSQL(sql);
			wdb.execSQL(sb.toString());
			
			// Log
			Log.d(this.getClass().getName() + 
					"["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Table created => " + tableName);
			
			wdb.close();
			
			return true;
			
		} catch (SQLException e) {
			
			// Log
			Log.e(this.getClass().getName() + 
					"[" + Thread.currentThread().getStackTrace()[2].getLineNumber() + "]", 
					"Exception => " + e.toString());
			
			wdb.close();
			
			return false;
			
		}//try

	}//public boolean createTable(SQLiteDatabase db, String tableName)

	/******************************
		createTable()<br>
		1. Columns "created_at" and "modified_at" => auto-inserted
		
		@param columns, types => use non-full version
		@return 
			-1	Table exists<br>
			-2	SQLException<br>
			1	Table created<br>
	 ******************************/
	public static int createTable_static
	(Activity actv, 
			String tableName, String[] columns, String[] types)
	{
		/*----------------------------
		 * Steps
		 * 1. Table exists?
		 * 2. Build sql
		 * 3. Exec sql
			----------------------------*/
		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
		
		//
//		SQLiteDatabase wdb = this.getWritableDatabase();
		SQLiteDatabase wdb = dbu.getWritableDatabase();
		
		//
		//if (!tableExists(db, tableName)) {
		if (DBUtils.tableExists(actv, CONS.DB.dbName, tableName)) {
//			if (tableExists(wdb, tableName)) {
			// Log
			Log.i("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Table exists => " + tableName);
			
			// debug
			String msg_Toast = "Table exists => " + tableName;
			Toast.makeText(actv, msg_Toast, Toast.LENGTH_SHORT).show();
			
			
			return -1;
//			return false;
		}//if (!tableExists(SQLiteDatabase db, String tableName))
		
		/*----------------------------
		 * 2. Build sql
			----------------------------*/
		//
		StringBuilder sb = new StringBuilder();
		
		sb.append("CREATE TABLE " + tableName + " (");
		sb.append(android.provider.BaseColumns._ID +
				" INTEGER PRIMARY KEY AUTOINCREMENT, ");
		
		// created_at, modified_at
		sb.append("created_at TEXT, modified_at TEXT, ");
//		sb.append("created_at INTEGER, modified_at INTEGER, ");
		
		int i = 0;
		for (i = 0; i < columns.length - 1; i++) {
			sb.append(columns[i] + " " + types[i] + ", ");
		}//for (int i = 0; i < columns.length - 1; i++)
		
		sb.append(columns[i] + " " + types[i]);
		
		sb.append(");");
		
		// Log
		Log.d("DBUtils.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "sql => " + sb.toString());
		
		/*----------------------------
		 * 3. Exec sql
			----------------------------*/
		//
		try {
			//	db.execSQL(sql);
			wdb.execSQL(sb.toString());
			
			// Log
			Log.d(actv.getClass().getName() + 
					"["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Table created => " + tableName);
			
			wdb.close();
			
			return 1;
			
		} catch (SQLException e) {
			
			// Log
			Log.e(actv.getClass().getName() + 
					"[" + Thread.currentThread().getStackTrace()[2].getLineNumber() + "]", 
					"Exception => " + e.toString());
			
			wdb.close();
			
			return -2;
			
		}//try
		
	}//public boolean createTable(SQLiteDatabase db, String tableName)
	
	public boolean tableExists(SQLiteDatabase db, String tableName) {
		// The table exists?
		Cursor cursor = db.rawQuery(
									"SELECT * FROM sqlite_master WHERE tbl_name = '" + 
									tableName + "'", null);
		
		((Activity) context).startManagingCursor(cursor);
//		actv.startManagingCursor(cursor);
		
		// Judge
		if (cursor.getCount() > 0) {
			return true;
		} else {//if (cursor.getCount() > 0)
			return false;
		}//if (cursor.getCount() > 0)
	}//public boolean tableExists(String tableName)
	
	public static boolean 
	tableExists
	(Activity actv, String dbName, String tableName) {
		// The table exists?
		DBUtils dbu = new DBUtils(actv, dbName);
		
		//
		SQLiteDatabase rdb = dbu.getReadableDatabase();

		Cursor cursor = rdb.rawQuery(
				"SELECT * FROM sqlite_master WHERE tbl_name = '" + 
						tableName + "'", null);
		
		actv.startManagingCursor(cursor);
//		actv.startManagingCursor(cursor);
		
		// Judge
		if (cursor.getCount() > 0) {
		
			rdb.close();
			return true;
			
		} else {//if (cursor.getCount() > 0)
			
			rdb.close();
			return false;
			
		}//if (cursor.getCount() > 0)
		
	}//public boolean tableExists(String tableName)

	public boolean dropTable(Activity actv, SQLiteDatabase db, String tableName) {
		// Log
		Log.d("DBUtils.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "Starting: dropTable()");
		
		/*------------------------------
		 * The table exists?
		 *------------------------------*/
		// The table exists?
		boolean tempBool = tableExists(db, tableName);
		
		if (tempBool == true) {
		
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Table exists: " + tableName);

		} else {//if (tempBool == true)
			// Log
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Table doesn't exist: " + tableName);

			return false;
		}//if (tempBool == true)

		/*------------------------------
		 * Drop the table
		 *------------------------------*/
		// Define the sql
        String sql 
             = "DROP TABLE " + tableName;
        
        // Execute
        try {
			db.execSQL(sql);
			
			// Vacuum
			db.execSQL("VACUUM");
			
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "The table dropped => " + tableName);
			
			// Return
			return true;
			
		} catch (SQLException e) {
			// TODO ?��?��?��?��?��?��?��?��?��?��?��ꂽ catch ?��u?��?��?��b?��N
			// Log
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "DROP TABLE => failed (table=" + tableName + "): " + e.toString());
			
			// debug
			Toast.makeText(actv, 
						"DROP TABLE => failed(table=" + tableName, 
						3000).show();
			
			// Return
			return false;
		}//try

	}//public boolean dropTable(String tableName) 

	public boolean drop_table(Activity actv, SQLiteDatabase db, String tableName) {
		// Log
		Log.d("DBUtils.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "Starting: dropTable()");
		
		/*------------------------------
		 * The table exists?
		 *------------------------------*/
		// The table exists?
		boolean tempBool = tableExists(db, tableName);
		
		if (tempBool == true) {
		
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Table exists: " + tableName);

		} else {//if (tempBool == true)
			// Log
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Table doesn't exist: " + tableName);

			return false;
		}//if (tempBool == true)

		/*------------------------------
		 * Drop the table
		 *------------------------------*/
		// Define the sql
        String sql 
             = "DROP TABLE " + tableName;
        
        // Execute
        try {
			db.execSQL(sql);
			
			// Vacuum
			db.execSQL("VACUUM");
			
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "The table dropped => " + tableName);
			
			// Return
			return true;
			
		} catch (SQLException e) {
			// TODO ?��?��?��?��?��?��?��?��?��?��?��ꂽ catch ?��u?��?��?��b?��N
			// Log
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "DROP TABLE => failed (table=" + tableName + "): " + e.toString());
			
			// debug
			Toast.makeText(actv, 
						"DROP TABLE => failed(table=" + tableName, 
						3000).show();
			
			// Return
			return false;
		}//try

	}//public boolean dropTable(String tableName) 

	public boolean insertData(SQLiteDatabase db, String tableName, 
												String[] columnNames, String[] values) {
		
////		String sql = "SELECT * FROM TABLE " + DBUtils.table_name_memo_patterns;
//		String sql = "SELECT * FROM " + DBUtils.table_name_memo_patterns;
//		
//		Cursor c = db.rawQuery(sql, null);
//		
//		
//		
//		// Log
//		Log.d("DBUtils.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", "c.getCount() => " + c.getCount() + " / " +
//				"c.getColumnCount() => " + c.getColumnCount());
//		
//		c.close();
		
		
		/*----------------------------
		* 1. Insert data
		----------------------------*/
		try {
			// Start transaction
			db.beginTransaction();
			
			// ContentValues
			ContentValues val = new ContentValues();
			
			// Put values
			for (int i = 0; i < columnNames.length; i++) {
				val.put(columnNames[i], values[i]);
			}//for (int i = 0; i < columnNames.length; i++)
			
			// Insert data
			db.insert(tableName, null, val);
			
			// Set as successful
			db.setTransactionSuccessful();
			
			// End transaction
			db.endTransaction();
			
			// Log
//			Log.d("DBUtils.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", "Data inserted => " + "(" + columnNames[0] + " => " + values[0] + 
//				" / " + columnNames[3] + " => " + values[3] + ")");
			
			return true;
		} catch (Exception e) {
			// Log
			Log.e("DBUtils.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "Exception! => " + e.toString());
			
			return false;
		}//try
		
//		//debug
//		return false;
		
	}//public insertData(String tableName, String[] columnNames, String[] values)

	/******************************
		public boolean dropTable
		
		@return
			false	=> Table can't be dropped or doesn't exist
	 ******************************/
	public boolean dropTable
	(Activity actv, String tableName) {
		/***************************************
		 * Setup: DB
		 ***************************************/
//		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
		
		SQLiteDatabase wdb = this.getWritableDatabase();
		
		/*------------------------------
		 * The table exists?
		 *------------------------------*/
		// The table exists?
		boolean tempBool = tableExists(wdb, tableName);
		
		if (tempBool == true) {
		
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Table exists: " + tableName);

		} else {//if (tempBool == true)
			// Log
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Table doesn't exist: " + tableName);
			
			// debug
			String msg_Toast = "Table doesn't exist: " + tableName;
			Toast.makeText(actv, msg_Toast, Toast.LENGTH_SHORT).show();

			return false;
		}//if (tempBool == true)

		/*------------------------------
		 * Drop the table
		 *------------------------------*/
		// Define the sql
        String sql 
             = "DROP TABLE " + tableName;
        
        // Execute
        try {
			wdb.execSQL(sql);
			
			// Vacuum
			wdb.execSQL("VACUUM");
			
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "The table dropped => " + tableName);
			
			// debug
			String msg_Toast = "The table dropped => " + tableName;
			Toast.makeText(actv, msg_Toast, Toast.LENGTH_SHORT).show();
			
			
			wdb.close();
			
			// Return
			return true;
			
		} catch (SQLException e) {
			// TODO ?��?��?��?��?��?��?��?��?��?��?��ꂽ catch ?��u?��?��?��b?��N
			// Log
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "DROP TABLE => failed (table=" + tableName + "): " + e.toString());
			
			// debug
			Toast.makeText(actv, 
						"DROP TABLE => failed(table=" + tableName, 
						Toast.LENGTH_LONG).show();
			
			wdb.close();
			
			// Return
			return false;
		}//try

	}//public boolean dropTable(String tableName) 

	/******************************
		public boolean dropTable
		
		@return
			-1	Table doesn't exist<br>
			-2	SQLException<br>
			1	Table dropped<br>
	 ******************************/
	public static int dropTable_2
	(Activity actv, String tableName) {
		/***************************************
		 * Setup: DB
		 ***************************************/
		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
		
		SQLiteDatabase wdb = dbu.getWritableDatabase();
		
		/*------------------------------
		 * The table exists?
		 *------------------------------*/
		// The table exists?
		boolean tempBool = DBUtils.tableExists(actv, CONS.DB.dbName, tableName);
		
		if (tempBool == true) {
			
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Table exists: " + tableName);
			
		} else {//if (tempBool == true)
			// Log
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Table doesn't exist: " + tableName);
			
			// debug
			String msg_Toast = "Table doesn't exist: " + tableName;
			Toast.makeText(actv, msg_Toast, Toast.LENGTH_SHORT).show();
			
			return -1;
			
		}//if (tempBool == true)
		
		/*------------------------------
		 * Drop the table
		 *------------------------------*/
		// Define the sql
		String sql 
		= "DROP TABLE " + tableName;
		
		// Execute
		try {
			wdb.execSQL(sql);
			
			// Vacuum
			wdb.execSQL("VACUUM");
			
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "The table dropped => " + tableName);
			
			// debug
			String msg_Toast = "The table dropped => " + tableName;
			Toast.makeText(actv, msg_Toast, Toast.LENGTH_SHORT).show();
			
			
			wdb.close();
			
			// Return
			return 1;
			
		} catch (SQLException e) {
			// TODO ?��?��?��?��?��?��?��?��?��?��?��ꂽ catch ?��u?��?��?��b?��N
			// Log
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "DROP TABLE => failed (table=" + tableName + "): " + e.toString());
			
			// debug
			Toast.makeText(actv, 
					"DROP TABLE => failed(table=" + tableName, 
					Toast.LENGTH_LONG).show();
			
			wdb.close();
			
			// Return
			return -2;
		}//try
		
	}//public boolean dropTable(String tableName) 
	
	public boolean 
	insertData(SQLiteDatabase db, String tableName, 
											String[] columnNames, long[] values) {
		/*----------------------------
		* 1. Insert data
		----------------------------*/
		try {
			// Start transaction
			db.beginTransaction();
			
			// ContentValues
			ContentValues val = new ContentValues();
			
			// Put values
			for (int i = 0; i < columnNames.length; i++) {
				val.put(columnNames[i], values[i]);
			}//for (int i = 0; i < columnNames.length; i++)
			
			// Insert data
			db.insert(tableName, null, val);
			
			// Set as successful
			db.setTransactionSuccessful();
			
			// End transaction
			db.endTransaction();
			
			// Log
			Log.d("DBUtils.java" + "["
			+ Thread.currentThread().getStackTrace()[2].getLineNumber()
			+ "]", "Data inserted => " + "(" + columnNames[0] + " => " + values[0] + "), and others");
			
			return true;
		} catch (Exception e) {
			// Log
			Log.e("DBUtils.java" + "["
			+ Thread.currentThread().getStackTrace()[2].getLineNumber()
			+ "]", "Exception! => " + e.toString());
			
			return false;
		}//try
	}//public insertData(String tableName, String[] columnNames, String[] values)
	
	/******************************
		@return false => 1. Insertion failed<br>
						2. Exception
	 ******************************/
	public boolean 
	insert_Data_RefreshDate
	(SQLiteDatabase db, int numOfData) {
		/*----------------------------
		 * 1. Insert data
		----------------------------*/
		////////////////////////////////

		// prep: content values

		////////////////////////////////
		// ContentValues
		ContentValues val = new ContentValues();
		
//		android.provider.BaseColumns._ID,		// 0
//		"created_at", "modified_at",			// 1,2
//		"last_refreshed", "num_of_items_added"
		
		// Put values
		val.put("created_at",
				Methods.conv_MillSec_to_TimeLabel(Methods.getMillSeconds_now()));
		val.put("modified_at",
				Methods.conv_MillSec_to_TimeLabel(Methods.getMillSeconds_now()));
		val.put("last_refreshed",
				Methods.conv_MillSec_to_TimeLabel(Methods.getMillSeconds_now()));
		val.put("num_of_items_added", numOfData);
		
		try {
			// Start transaction
			db.beginTransaction();
			
//			// ContentValues
//			ContentValues val = new ContentValues();
//			
//			// Put values
//			for (int i = 0; i < columnNames.length; i++) {
//				val.put(columnNames[i], values[i]);
//			}//for (int i = 0; i < columnNames.length; i++)
			
			// Insert data
			long res = db.insert(CONS.DB.tname_RefreshLog, null, val);
			
			if (res == -1) {
				
				// Log
				String msg_Log = "insertion => failed";
				Log.e("DBUtils.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", msg_Log);
				
				db.endTransaction();
				
				return false;
				
			} else {
				
				// Log
				String msg_Log = "insertion => done";
				Log.d("DBUtils.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", msg_Log);

			}
			
			// Set as successful
			db.setTransactionSuccessful();
			
			// End transaction
			db.endTransaction();
			
//			// Log
//			Log.d("DBUtils.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", "Data inserted => " + "(" + columnNames[0] + " => " + values[0] + "), and others");
			
			return true;
			
		} catch (Exception e) {
			
			// Log
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception! => " + e.toString());
			
			return false;
			
		}//try
		
	}//insertData_RefreshDate
	
	/******************************
		@return false => 1. Insertion failed<br>
						2. Exception
	 ******************************/
	public static boolean 
	insert_Data_generic
	(Activity actv, String tname, ContentValues val) {
		/*----------------------------
		 * 1. Insert data
		----------------------------*/
		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
		
		SQLiteDatabase wdb = dbu.getWritableDatabase();
		

		////////////////////////////////

		// insert

		////////////////////////////////
		try {
			// Start transaction
			wdb.beginTransaction();
			
			// Insert data
			long res = wdb.insert(tname, null, val);
			
			if (res == -1) {
				
				// Log
				String msg_Log = "insertion => failed";
				Log.e("DBUtils.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", msg_Log);
				
				wdb.endTransaction();
		
				wdb.close();
				
				return false;
				
			} else {
				
				// Log
				String msg_Log = "insertion => done";
				Log.d("DBUtils.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", msg_Log);
				
			}
			
			// Set as successful
			wdb.setTransactionSuccessful();
			
			// End transaction
			wdb.endTransaction();
			
			wdb.close();
			
			return true;
			
		} catch (Exception e) {
			
			// Log
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception! => " + e.toString());
			
			wdb.close();
			
			return false;
			
		}//try
		
	}//insertData_RefreshDate
	
	/******************************
		@return false => 1. Insertion failed<br>
						2. Exception
	 ******************************/
	public static boolean 
	insert_Data_RefreshDate
	(Activity actv, String lastRefresh, int numOfData) {
		/*----------------------------
		 * 1. Insert data
		----------------------------*/
		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
		
		SQLiteDatabase wdb = dbu.getWritableDatabase();
		
		////////////////////////////////
		
		// prep: content values
		
		////////////////////////////////
		// ContentValues
		ContentValues val = new ContentValues();
		
//		android.provider.BaseColumns._ID,		// 0
//		"created_at", "modified_at",			// 1,2
//		"last_refreshed", "num_of_items_added"
		
		// Put values
		val.put("created_at",
				Methods.conv_MillSec_to_TimeLabel(Methods.getMillSeconds_now()));
		val.put("modified_at",
				Methods.conv_MillSec_to_TimeLabel(Methods.getMillSeconds_now()));
		
		val.put("last_refreshed",lastRefresh);
		val.put("num_of_items_added", numOfData);
		
		try {
			// Start transaction
			wdb.beginTransaction();
			
//			// ContentValues
//			ContentValues val = new ContentValues();
//			
//			// Put values
//			for (int i = 0; i < columnNames.length; i++) {
//				val.put(columnNames[i], values[i]);
//			}//for (int i = 0; i < columnNames.length; i++)
			
			// Insert data
			long res = wdb.insert(CONS.DB.tname_RefreshLog, null, val);
			
			if (res == -1) {
				
				// Log
				String msg_Log = "insertion => failed";
				Log.e("DBUtils.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", msg_Log);
				
				wdb.endTransaction();
				
				wdb.close();
				
				return false;
				
			} else {
				
				// Log
				String msg_Log = "insertion => done";
				Log.d("DBUtils.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", msg_Log);
				
			}
			
			// Set as successful
			wdb.setTransactionSuccessful();
			
			// End transaction
			wdb.endTransaction();
			
//			// Log
//			Log.d("DBUtils.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", "Data inserted => " + "(" + columnNames[0] + " => " + values[0] + "), and others");
			
			wdb.close();
			
			return true;
			
		} catch (Exception e) {
			
			// Log
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception! => " + e.toString());
			
			wdb.close();
			
			return false;
			
		}//try
		
	}//insertData_RefreshDate
	
	/******************************
		@return false => 1. Insertion failed<br>
						2. Exception
	 ******************************/
	public static boolean 
	insert_Data_TI
	(Activity actv, TI ti) {
		/*----------------------------
		 * 1. Insert data
		----------------------------*/
		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
		
		SQLiteDatabase wdb = dbu.getWritableDatabase();
		////////////////////////////////
		
		// prep: content values
		
		////////////////////////////////
		// ContentValues
		ContentValues val = _build_Values__TI(actv, ti);
//		ContentValues val = new ContentValues();
		
//		android.provider.BaseColumns._ID,		// 0
//		"created_at", "modified_at",			// 1,2
//		"last_refreshed", "num_of_items_added"
		
		// Put values
//		val.put("created_at",
//				Methods.conv_MillSec_to_TimeLabel(Methods.getMillSeconds_now()));
//		val.put("modified_at",
//				Methods.conv_MillSec_to_TimeLabel(Methods.getMillSeconds_now()));
//		val.put("last_refreshed",
//				Methods.conv_MillSec_to_TimeLabel(Methods.getMillSeconds_now()));
//		val.put("num_of_items_added", numOfData);
		
		try {
			// Start transaction
			wdb.beginTransaction();
			
//			// ContentValues
//			ContentValues val = new ContentValues();
//			
//			// Put values
//			for (int i = 0; i < columnNames.length; i++) {
//				val.put(columnNames[i], values[i]);
//			}//for (int i = 0; i < columnNames.length; i++)
			
			// Insert data
			long res = wdb.insert(CONS.DB.tname_IFM11, null, val);
//			long res = wdb.insert(CONS.DB.tname_RefreshLog, null, val);
			
			if (res == -1) {
				
				// Log
				String msg_Log = "insertion => failed";
				Log.e("DBUtils.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", msg_Log);
				
				wdb.endTransaction();
				wdb.close();
				
				return false;
				
			} else {
				
				// Log
				String msg_Log = "insertion => done";
				Log.d("DBUtils.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", msg_Log);
				
			}
			
			// Set as successful
			wdb.setTransactionSuccessful();
			
			// End transaction
			wdb.endTransaction();
			
//			// Log
//			Log.d("DBUtils.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", "Data inserted => " + "(" + columnNames[0] + " => " + values[0] + "), and others");
			
			wdb.close();
			
			return true;
			
		} catch (Exception e) {
			
			// Log
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception! => " + e.toString());
			
			wdb.close();
			
			return false;
			
		}//try
		
	}//insertData_TI

	private static ContentValues 
	_build_Values__TI
	(Activity actv, TI ti) {
		// TODO Auto-generated method stub
		ContentValues val = new ContentValues();
		
//		android.provider.BaseColumns._ID,		// 0
//		"created_at", "modified_at",			// 1,2
//		"file_id", "file_path", "file_name",	// 3,4,5
//		"date_added", "date_modified",			// 6,7
//		"memos", "tags",						// 8,9
//		"last_viewed_at",						// 10
//		"table_name"							// 11
		
		val.put("created_at",
				Methods.conv_MillSec_to_TimeLabel(Methods.getMillSeconds_now()));
		val.put("modified_at",
				Methods.conv_MillSec_to_TimeLabel(Methods.getMillSeconds_now()));
		
		val.put("file_id", ti.getFileId());
		val.put("file_path", ti.getFile_path());
		val.put("file_name", ti.getFile_name());
		
		val.put("date_added", ti.getDate_added());
		val.put("date_modified", ti.getDate_modified());
		
		val.put("memos", ti.getMemo());
		val.put("tags", ti.getTags());
		
		val.put("last_viewed_at", ti.getLast_viewed_at());
		val.put("table_name", ti.getTable_name());
		
		return val;
		
	}//_build_Values__TI

	public boolean deleteData(Activity actv, SQLiteDatabase db, String tableName, long file_id) {
		/*----------------------------
		 * Steps
		 * 1. Item exists in db?
		 * 2. If yes, delete it
			----------------------------*/
		/*----------------------------
		 * 1. Item exists in db?
			----------------------------*/
		boolean result = isInDB_long(db, tableName, file_id);
		
		if (result == false) {		// Result is false ==> Meaning the target data doesn't exist
											//							in db; Hence, not executing delete op
			
			// debug
			Toast.makeText(actv, 
					"Data doesn't exist in db: " + String.valueOf(file_id), 
					2000).show();
			
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", 
					"Data doesn't exist in db => Delete the data (file_id = " + String.valueOf(file_id) + ")");
			
			return false;
			
		} else {//if (result == false)
			
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", 
					"Data exists in db" + String.valueOf(file_id) + ")");
			
		}//if (result == false)
		
		
		String sql = 
						"DELETE FROM " + tableName + 
						" WHERE file_id = '" + String.valueOf(file_id) + "'";
		
		try {
			db.execSQL(sql);
			
//			// Log
//			Log.d("DBUtils.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", "Data deleted => file id = " + String.valueOf(file_id));
			
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Sql executed: " + sql);
			
			return true;
			
		} catch (SQLException e) {
			// Log
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception => " + e.toString());
			
			return false;
			
		}//try
		
	}//public boolean deleteData(SQLiteDatabase db, String tableName, long file_id)

	public boolean deleteData_ai(Activity actv,
						SQLiteDatabase db, String tableName, long db_id) {
		/*----------------------------
		 * Steps
		 * 1. Item exists in db?
		 * 2. If yes, delete it
			----------------------------*/
		/*----------------------------
		 * 1. Item exists in db?
			----------------------------*/
		boolean result = DBUtils.isInDB_long_ai(db, tableName, db_id);
		
		if (result == false) {		// Result is false ==> Meaning the target data doesn't exist
											//							in db; Hence, not executing delete op
			
			// debug
			Toast.makeText(actv, 
					"Data doesn't exist in db: " + String.valueOf(db_id), 
					2000).show();
			
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", 
					"Data doesn't exist in db => Delete the data (db_id = " + String.valueOf(db_id) + ")");
			
			return false;
			
		} else {//if (result == false)
			
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", 
					"Data exists in db" + String.valueOf(db_id) + ")");
			
		}//if (result == false)
		
		
		String sql = 
						"DELETE FROM " + tableName
						+ " WHERE " + android.provider.BaseColumns._ID
						+ " = "
						+ String.valueOf(db_id);
		
		try {
			db.execSQL(sql);
			
//			// Log
//			Log.d("DBUtils.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", "Data deleted => file id = " + String.valueOf(db_id));
			
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Sql executed: " + sql);
			
			return true;
			
		} catch (SQLException e) {
			// Log
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception => " + e.toString());
			
			// Log
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "sql=" + sql);
			
			return false;
			
		}//try
		
	}//public boolean deleteData_ai(Activity actv, SQLiteDatabase db, String tableName, long db_id)

	/****************************************
	 *
	 * 
	 * <Caller> 
	 * 1. deleteData(Activity actv, SQLiteDatabase db, String tableName, long file_id)
	 * 
	 * <Desc> 
	 * 1. REF=> http://stackoverflow.com/questions/3369888/android-sqlite-insert-unique
	 * 
	 * <Params> 1.
	 * 
	 * <Return> 1.
	 * 
	 * <Steps> 1.
	 ****************************************/
	public static boolean 
	isInDB_long
	(SQLiteDatabase db, String tableName, long file_id) {
		
		String sql = "SELECT COUNT(*) FROM " + tableName + " WHERE file_id = '" +
						String.valueOf(file_id) + "'";
		
		long result = DatabaseUtils.longForQuery(db, sql, null);
		
		// Log
		Log.d("DBUtils.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "result => " + String.valueOf(result));
		
		if (result > 0) {

			return true;
			
		} else {//if (result > 0)
			
			return false;
			
		}//if (result > 0)
		
//		return false;
		
	}//public boolean isInDB_long(SQLiteDatabase db, String tableName, long file_id)

	public static boolean isInDB_long_ai(
						SQLiteDatabase db, String tableName, long db_id) {
		
		String sql = "SELECT COUNT(*) FROM " + tableName
					+ " WHERE " + android.provider.BaseColumns._ID + " = "
					+ String.valueOf(db_id);
		
		long result = DatabaseUtils.longForQuery(db, sql, null);
		
		// Log
		Log.d("DBUtils.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "result => " + String.valueOf(result));
		
		if (result > 0) {

			return true;
			
		} else {//if (result > 0)
			
			return false;
			
		}//if (result > 0)
		
//		return false;
		
	}//public static boolean isInDB_long_ai

	public boolean insert_data_refresh_history(SQLiteDatabase wdb,
			String tableName, long[] data) {
		/*----------------------------
		* 1. Insert data
		----------------------------*/
		try {
			// Start transaction
			wdb.beginTransaction();
			
			// ContentValues
			ContentValues val = new ContentValues();
			
//			// Put values
//			for (int i = 0; i < columnNames.length; i++) {
//				val.put(columnNames[i], values[i]);
//			}//for (int i = 0; i < columnNames.length; i++)
			
//			"last_refreshed", "num_of_items_added"
			
			val.put("last_refreshed", data[0]);
			
			val.put("num_of_items_added", data[1]);
			
			// Insert data
			wdb.insert(tableName, null, val);
			
			// Set as successful
			wdb.setTransactionSuccessful();
			
			// End transaction
			wdb.endTransaction();
			
			// Log
//			Log.d("DBUtils.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", "Data inserted => " + "(" + columnNames[0] + " => " + values[0] + 
//				" / " + columnNames[3] + " => " + values[3] + ")");
			
			return true;
			
		} catch (Exception e) {
			// Log
			Log.e("DBUtils.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "Exception! => " + e.toString());
			
			return false;
			
		}//try
		
	}//public boolean insert_data_refresh_history

	/******************************
		@return true => update successful
	 ******************************/
	public static boolean
	update_Data_AI
	(Activity actv, String dbName, String tableName,
			long db_id, String col_name, String value) {
		/*********************************
		 * memo
		 *********************************/
		DBUtils dbu = new DBUtils(actv, dbName);
		
		//
		SQLiteDatabase wdb = dbu.getWritableDatabase();
		
		
//		String sql = "UPDATE " + CONS.tname_main + " SET " + 
	
		String sql = "UPDATE " + tableName + " SET " + 
//				"last_viewed_at='" + Methods.getMillSeconds_now() + "' " +

				col_name + " = '" + value + "' "
				+ " WHERE " + android.provider.BaseColumns._ID + " = '"
				+ db_id + "'";
		
		
		//			"file_id", 		"file_path", "file_name", "date_added", "date_modified"
		//static String[] cols = 
		//{"file_id", 		"file_path", "file_name", "date_added",
		//"date_modified", "memos", "tags"};
		
		
		try {
			
			wdb.execSQL(sql);
			
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "sql => Done: " + sql);
			
			//Methods.toastAndLog(actv, "Data updated", 2000);
			
			wdb.close();
			
			return true;
			
			
		} catch (SQLException e) {
			// Log
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception => " + e.toString() + " / " + "sql: " + sql);
			
			wdb.close();
			
			return false;
		}
		
	}//public static boolean update_data_ai()

	
	public void updateData_aiLength(Activity actv, String table_name,
			long db_id, int length) {
		
		DBUtils dbu = new DBUtils(actv, dbName);
		
		//
		SQLiteDatabase wdb = dbu.getWritableDatabase();

		
		String sql = "UPDATE " + table_name + " SET " + 
				"length" + " = " + length + " "
				+ " WHERE " + android.provider.BaseColumns._ID + " = '"
				+ db_id + "'";
				
		// Log
		Log.d("DBUtils.java" + "["
		+ Thread.currentThread().getStackTrace()[2].getLineNumber()
		+ "]", "sql=" + sql);

		try {
			
			wdb.execSQL(sql);
			
			// Log
			Log.d("DBUtils.java" + "["
			+ Thread.currentThread().getStackTrace()[2].getLineNumber()
			+ "]", "Exec sql => Done");
			
		} catch (SQLException e) {

			// Log
			Log.e("DBUtils.java" + "["
			+ Thread.currentThread().getStackTrace()[2].getLineNumber()
			+ "]", "Exception => " + e.toString());

		}//try
		
		// Close
		wdb.close();
		
	}//public void updateData_aiLength

	/******************************
		@return
			false<br>
			1. table doesn't exist<br>
			2. SQLException<br>
	 ******************************/
	public boolean
	updateData_generic
	(Activity actv, String tableName, 
		long dbId, String colName, String colValue) {

		////////////////////////////////

		// validate: table exists

		////////////////////////////////
		if (!DBUtils.tableExists(actv, CONS.DB.dbName, tableName)) {
			// Log
			Log.i("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Table doesn't exists => " + tableName);
			
			return false;
			
		}//if (!tableExists(SQLiteDatabase db, String tableName))
		
		/***************************************
		 * Setup: DB
		 ***************************************/
		SQLiteDatabase wdb = this.getWritableDatabase();
		
		/***************************************
		 * Build SQL
		 ***************************************/
		String sql = "UPDATE " + tableName + " SET "
//				+ colName + "='" + colValue + "', "
				+ colName + "='" + colValue + "'"
				+ " WHERE " + android.provider.BaseColumns._ID + " = '" + dbId + "'";
				
		/***************************************
		 * Exec: Query
		 ***************************************/
		try {
			
			wdb.execSQL(sql);
			
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "sql => Done: " + sql);
			
		//	Methods.toastAndLog(actv, "Data updated", 2000);

			wdb.close();
			
			return true;
			
			
		} catch (SQLException e) {
			// Log
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception => " + e.toString() + " / " + "sql: " + sql);
			
			wdb.close();
			
			return false;
		}

	}//updateData_generic()

	/******************************
		@return
			false<br>
			1. table doesn't exist<br>
			2. SQLException<br>
	 ******************************/
	public static boolean
	updateData_generic_static
	(Activity actv, String tableName, 
			long dbId, String colName, String colValue) {
		
		////////////////////////////////
		
		// validate: table exists
		
		////////////////////////////////
		if (!DBUtils.tableExists(actv, CONS.DB.dbName, tableName)) {
			// Log
			Log.i("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Table doesn't exists => " + tableName);
			
			return false;
			
		}//if (!tableExists(SQLiteDatabase db, String tableName))
		
		/***************************************
		 * Setup: DB
		 ***************************************/
		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
		
		SQLiteDatabase wdb = dbu.getWritableDatabase();
		
		ContentValues val = new ContentValues();
		
		val.put(colName, colValue);
		
		String where = android.provider.BaseColumns._ID
						+ " = ?";
		
		String[] args = new String[]{String.valueOf(dbId)};
		
		try {
			// Start transaction
			wdb.beginTransaction();
			
			// Insert data
			long res = wdb.update(tableName, val, where, args);
			
			if (res < 1) {
				// Log
				String msg_Log = String.format(
									"insertion => failed (result = %d): table = %s"
									, res, tableName);

//				Methods_dlg.dlg_ShowMessage(actv, msg_Log, R.color.red);
				
				wdb.endTransaction();
		
				wdb.close();
				
				return false;
				
			} else {
				
				// Log
				String msg_Log = "insertion => done: " + tableName;
				Log.d("DBUtils.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", msg_Log);
				
			}
			
			// Set as successful
			wdb.setTransactionSuccessful();
			
			// End transaction
			wdb.endTransaction();
			
			wdb.close();
			
			return true;
			
		} catch (Exception e) {
			
			// Log
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception! => " + e.toString());
			
			wdb.close();
			
			return false;
			
		}//try		

		
		
//		/***************************************
//		 * Build SQL
//		 ***************************************/
//		String sql = "UPDATE " + tableName + " SET "
//			//				+ colName + "='" + colValue + "', "
//			+ colName + "='" + colValue + "'"
//			+ " WHERE " + android.provider.BaseColumns._ID + " = '" + dbId + "'";
//		
//		/***************************************
//		 * Exec: Query
//		 ***************************************/
//		try {
//			
//			wdb.execSQL(sql);
//			
//			// Log
//			Log.d("DBUtils.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", "sql => Done: " + sql);
//			
//			//	Methods.toastAndLog(actv, "Data updated", 2000);
//			
//			wdb.close();
//			
//			return true;
//			
//			
//		} catch (SQLException e) {
//			// Log
//			Log.e("DBUtils.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", "Exception => " + e.toString() + " / " + "sql: " + sql);
//			
//			wdb.close();
//			
//			return false;
//		}
		
	}//updateData_generic()
	
	/******************************
		@return
			false<br>
			1. table doesn't exist<br>
			2. SQLException<br>
	 ******************************/
	public static boolean
	updateData_generic_static
	(Activity actv, String tableName, 
		String colName, String colValue,
		String where, String[] args) {
		
		////////////////////////////////
		
		// validate: table exists
		
		////////////////////////////////
		if (!DBUtils.tableExists(actv, CONS.DB.dbName, tableName)) {
			// Log
			Log.i("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Table doesn't exists => " + tableName);
			
			return false;
			
		}//if (!tableExists(SQLiteDatabase db, String tableName))
		
		/***************************************
		 * Setup: DB
		 ***************************************/
		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
		
		SQLiteDatabase wdb = dbu.getWritableDatabase();
		
		ContentValues val = new ContentValues();
		
		val.put(colName, colValue);
		
//		String where = android.provider.BaseColumns._ID
//				+ " = ?";
//		
//		String[] args = new String[]{String.valueOf(dbId)};
		
		try {
			// Start transaction
			wdb.beginTransaction();
			
			// Insert data
			long res = wdb.update(tableName, val, where, args);
			
			if (res < 1) {
				// Log
				String msg_Log = String.format(
						"insertion => failed (result = %d): table = %s"
						, res, tableName);
				
//				Methods_dlg.dlg_ShowMessage(actv, msg_Log, R.color.red);
				
				wdb.endTransaction();
				
				wdb.close();
				
				return false;
				
			} else {
				
				// Log
				String msg_Log = "insertion => done: " + tableName;
				Log.d("DBUtils.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", msg_Log);
				
			}
			
			// Set as successful
			wdb.setTransactionSuccessful();
			
			// End transaction
			wdb.endTransaction();
			
			wdb.close();
			
			return true;
			
		} catch (Exception e) {
			
			// Log
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception! => " + e.toString());
			
			wdb.close();
			
			return false;
			
		}//try		
		
		
		
//		/***************************************
//		 * Build SQL
//		 ***************************************/
//		String sql = "UPDATE " + tableName + " SET "
//			//				+ colName + "='" + colValue + "', "
//			+ colName + "='" + colValue + "'"
//			+ " WHERE " + android.provider.BaseColumns._ID + " = '" + dbId + "'";
//		
//		/***************************************
//		 * Exec: Query
//		 ***************************************/
//		try {
//			
//			wdb.execSQL(sql);
//			
//			// Log
//			Log.d("DBUtils.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", "sql => Done: " + sql);
//			
//			//	Methods.toastAndLog(actv, "Data updated", 2000);
//			
//			wdb.close();
//			
//			return true;
//			
//			
//		} catch (SQLException e) {
//			// Log
//			Log.e("DBUtils.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", "Exception => " + e.toString() + " / " + "sql: " + sql);
//			
//			wdb.close();
//			
//			return false;
//		}
		
	}//updateData_generic()
	
	/******************************
		@return
			false<br>
			1. table doesn't exist<br>
			2. SQLException<br>
	 ******************************/
	public static boolean
	updateData_generic_static
	(Activity actv, String tableName, 
			ContentValues val,
			String where, String[] args) {
		
		////////////////////////////////
		
		// validate: table exists
		
		////////////////////////////////
		if (!DBUtils.tableExists(actv, CONS.DB.dbName, tableName)) {
			// Log
			Log.i("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Table doesn't exists => " + tableName);
			
			return false;
			
		}//if (!tableExists(SQLiteDatabase db, String tableName))
		
		/***************************************
		 * Setup: DB
		 ***************************************/
		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
		
		SQLiteDatabase wdb = dbu.getWritableDatabase();
		
//		ContentValues val = new ContentValues();
//		
//		val.put(colName, colValue);
		
//		String where = android.provider.BaseColumns._ID
//				+ " = ?";
//		
//		String[] args = new String[]{String.valueOf(dbId)};
		
		try {
			// Start transaction
			wdb.beginTransaction();
			
			// Insert data
			long res = wdb.update(tableName, val, where, args);
			
			if (res < 1) {
				// Log
				String msg_Log = String.format(
						"insertion => failed (result = %d): table = %s"
						, res, tableName);
				
//				Methods_dlg.dlg_ShowMessage(actv, msg_Log, R.color.red);
				
				wdb.endTransaction();
				
				wdb.close();
				
				return false;
				
			} else {
				
				// Log
				String msg_Log = "insertion => done: " + tableName;
				Log.d("DBUtils.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", msg_Log);
				
			}
			
			// Set as successful
			wdb.setTransactionSuccessful();
			
			// End transaction
			wdb.endTransaction();
			
			wdb.close();
			
			return true;
			
		} catch (Exception e) {
			
			// Log
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception! => " + e.toString());
			
			wdb.close();
			
			return false;
			
		}//try		
		
		
		
//		/***************************************
//		 * Build SQL
//		 ***************************************/
//		String sql = "UPDATE " + tableName + " SET "
//			//				+ colName + "='" + colValue + "', "
//			+ colName + "='" + colValue + "'"
//			+ " WHERE " + android.provider.BaseColumns._ID + " = '" + dbId + "'";
//		
//		/***************************************
//		 * Exec: Query
//		 ***************************************/
//		try {
//			
//			wdb.execSQL(sql);
//			
//			// Log
//			Log.d("DBUtils.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", "sql => Done: " + sql);
//			
//			//	Methods.toastAndLog(actv, "Data updated", 2000);
//			
//			wdb.close();
//			
//			return true;
//			
//			
//		} catch (SQLException e) {
//			// Log
//			Log.e("DBUtils.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", "Exception => " + e.toString() + " / " + "sql: " + sql);
//			
//			wdb.close();
//			
//			return false;
//		}
		
	}//updateData_generic()
	
	public int
	getNumOfEntries(Activity actv, String table_name) {
		/*********************************
		 * memo
		 *********************************/
//		DBUtils dbu = new DBUtils(actv, CONS.dbName);
		
		SQLiteDatabase rdb = this.getReadableDatabase();

		String sql = "SELECT * FROM " + table_name;
		
		Cursor c = null;
		
		try {
			
			c = rdb.rawQuery(sql, null);
			
		} catch (Exception e) {
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception => " + e.toString());
			
			rdb.close();
			
			return -1;
		}
		
		int num_of_entries = c.getCount();
		
		rdb.close();

		return num_of_entries;
		
	}//public int getNumOfEntries(Activity actv, String table_name)

	public int
	getNumOfEntries_BM(Activity actv, String table_name, long aiDbId) {
		/*********************************
		 * memo
		 *********************************/
//		DBUtils dbu = new DBUtils(actv, CONS.dbName);
		
		SQLiteDatabase rdb = this.getReadableDatabase();

		String sql = "SELECT * FROM " + table_name
					+ " WHERE "
					+ "ai_id = "
					+ aiDbId;
		
		Cursor c = null;
		
		try {
			
			c = rdb.rawQuery(sql, null);
			
		} catch (Exception e) {
			// Log
			Log.e("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception => " + e.toString());
			
			rdb.close();
			
			return -1;
		}
		
		int num_of_entries = c.getCount();
		
		rdb.close();

		return num_of_entries;
		
	}//public int getNumOfEntries_BM(Activity actv, String table_name, long aiDbId)

	/****************************************
	 *		insertDataIntoDB()
	 * 
	 * <Caller> 
	 * 1. private static boolean refreshMainDB_3_insert_data(Activity actv, Cursor c)
	 * 
	 * <Desc> 1. <Params> 1.
	 * 
	 * <Return> 1.
	 * 
	 * <Steps> 1.
	 ****************************************/
	private static int 
	insertDataIntoDB
	(Activity actv, String tableName, Cursor c) {
		/*----------------------------
		 * Steps
		 * 0. Set up db
		 * 1. Move to first
		 * 2. Set variables
		 * 3. Obtain data
		 * 4. Insert data
		 * 5. Close db
		 * 6. Return => counter
			----------------------------*/
		/*----------------------------
		 * 0. Set up db
			----------------------------*/
//		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
//		
//		SQLiteDatabase wdb = dbu.getWritableDatabase();
//		
//		/*----------------------------
//		 * 1. Move to first
//			----------------------------*/
//		c.moveToFirst();
//
//		/*----------------------------
//		 * 2. Set variables
//			----------------------------*/
//		int counter = 0;
//		int counter_failed = 0;
//		
//		/*----------------------------
//		 * 3. Obtain data
//			----------------------------*/
//		for (int i = 0; i < c.getCount(); i++) {
//
//			String[] values = {
//					String.valueOf(c.getLong(0)),
//					c.getString(1),
//					c.getString(2),
//					String.valueOf(c.getLong(3)),
////					String.valueOf(c.getLong(4))
//					String.valueOf(c.getLong(4)),
//					MainActv.tname_main,
//			};
//
//			/*----------------------------
//			 * 4. Insert data
//			 * 		1. Insert data to tableName
//			 * 		2. Record result
//			 * 		3. Insert data to backupTableName
//			 * 		4. Record result
//				----------------------------*/
//			boolean blResult = 
//						dbu.insertData(wdb, tableName, CONS.cols_for_insert_data, values);
//				
//			if (blResult == false) {
//				// Log
//				Log.d("Methods.java"
//						+ "["
//						+ Thread.currentThread().getStackTrace()[2]
//								.getLineNumber() + "]", "i => " + i + "/" + "c.getLong(0) => " + c.getLong(0));
//				
//				counter_failed += 1;
//				
//			} else {//if (blResult == false)
//				counter += 1;
//			}
//
//			//
//			c.moveToNext();
//			
//			if (i % 100 == 0) {
//				// Log
//				Log.d("Methods.java" + "["
//						+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//						+ "]", "Done up to: " + i);
//				
//			}//if (i % 100 == 0)
//			
//		}//for (int i = 0; i < c.getCount(); i++)
//		
//		// Log
//		Log.d("Methods.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", "All data inserted: " + counter);
//		
//		/*----------------------------
//		 * 5. Close db
//			----------------------------*/
//		wdb.close();
//		
//		/*----------------------------
//		 * 6. Return => counter
//			----------------------------*/
//		//debug
//		// Log
//		Log.d("Methods.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", "counter_failed(sum): " + counter_failed);
//		
//		return counter;
		return 0;
		
	}//private static int insertDataIntoDB(Activity actv, Cursor c)

	/******************************
		@return null => 1. No DB file<br>
						2. No such table<br>
						3. Query exception<br>
						4. Query returned null<br>
						5. Query found 0<br>
	 ******************************/
	public static List<TI>
	find_All_TI
	(Activity actv, String tableName) {
		
		////////////////////////////////

		// validate: DB file exists?

		////////////////////////////////
		File dpath_DBFile = actv.getDatabasePath(CONS.DB.dbName);

		if (!dpath_DBFile.exists()) {
			
			String msg = "No DB file: " + CONS.DB.dbName;
			Methods_dlg.dlg_ShowMessage(actv, msg);
			
			return null;
			
		}
		
		////////////////////////////////

		// DB

		////////////////////////////////
		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
		
		SQLiteDatabase rdb = dbu.getReadableDatabase();
		
		////////////////////////////////
		
		// validate: table exists?
		
		////////////////////////////////
		boolean res = dbu.tableExists(rdb, CONS.DB.tname_IFM11);
//		boolean res = dbu.tableExists(rdb, tableName);

		if (res == false) {
			
			String msg = "No such table: " + tableName;
			Methods_dlg.dlg_ShowMessage(actv, msg);
			
			rdb.close();
			
			return null;
			
		}

		////////////////////////////////
		
		// Query
		
		////////////////////////////////
		Cursor c = null;
		
		String where = CONS.DB.col_names_IFM11[8] + " = ?";
		String[] args = new String[]{
				
							tableName
						};
		
		try {
			
			c = rdb.query(
					
					CONS.DB.tname_IFM11,			// 1
					CONS.DB.col_names_IFM11_full,	// 2
					where, args,		// 3,4
					null, null,		// 5,6
					null,			// 7
					null);
			
		} catch (Exception e) {

			// Log
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ ":"
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", e.toString());
			
			rdb.close();
			
			return null;
			
		}//try
		
		/***************************************
		 * Validate
		 * 	Cursor => Null?
		 * 	Entry => 0?
		 ***************************************/
		if (c == null) {
			
			// Log
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ ":"
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", "Query failed");
			
			rdb.close();
			
			return null;
			
		} else if (c.getCount() < 1) {//if (c == null)
			
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ ":"
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", "No entry in the table");
			
			rdb.close();
			
			return null;
			
		}//if (c == null)
		
		/***************************************
		 * Build list
		 ***************************************/
//		android.provider.BaseColumns._ID,		// 0
//		"created_at", "modified_at",			// 1,2
//		"file_id", "file_path", "file_name",	// 3,4,5
//		"date_added", "date_modified",			// 6,7
//		"memos", "tags",						// 8,9
//		"last_viewed_at",						// 10
//		"table_name"							// 11
//		"uploaded_at",							// 12
		
		List<TI> ti_List = new ArrayList<TI>();
		
		while(c.moveToNext()) {
			
			TI ti = new TI.Builder()

					.setDb_Id(c.getLong(0))
					.setCreated_at(c.getString(1))
					.setModified_at(c.getString(2))
					
					.setFileId(c.getLong(3))
					.setFile_path(c.getString(4))
					.setFile_name(c.getString(5))
					
					.setDate_added(c.getString(6))
					.setDate_modified(c.getString(7))
					
					.setMemo(c.getString(8))
					.setTags(c.getString(9))
					
					.setLast_viewed_at(c.getString(10))
					.setTable_name(c.getString(11))

					.setUploaded_at(c.getString(12))
					
					.build();
			
			ti_List.add(ti);
			
		}

		rdb.close();
		
		return ti_List;
		
	}//find_All_TI

	/******************************
		@return null => 1. No DB file<br>
						2. No such table<br>
						3. Query exception<br>
						4. Query returned null<br>
						5. Query found 0<br>
	 ******************************/
	public static List<TI>
	find_TIs_History
	(Activity actv, int limit) {
		
		////////////////////////////////
		
		// validate: DB file exists?
		
		////////////////////////////////
		File dpath_DBFile = actv.getDatabasePath(CONS.DB.dbName);
		
		if (!dpath_DBFile.exists()) {
			
			String msg = "No DB file: " + CONS.DB.dbName;
			Methods_dlg.dlg_ShowMessage(actv, msg);
			
			return null;
			
		}
		
		////////////////////////////////
		
		// DB
		
		////////////////////////////////
		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
		
		SQLiteDatabase rdb = dbu.getReadableDatabase();
		
		////////////////////////////////
		
		// validate: table exists?
		
		////////////////////////////////
		String tname = CONS.DB.tname_IFM11;
		
		boolean res = dbu.tableExists(rdb, tname);
//		boolean res = dbu.tableExists(rdb, tableName);
		
		if (res == false) {
			
			String msg = "No such table: " + tname;
			Methods_dlg.dlg_ShowMessage(actv, msg);
			
			rdb.close();
			
			return null;
			
		}
		
		////////////////////////////////
		
		// Query
		
		////////////////////////////////
		Cursor c = null;
		
//		String where = CONS.DB.col_names_IFM11[8] + " = ?";
//		String[] args = new String[]{
//				
//				tableName
//		};
		
//		android.provider.BaseColumns._ID,		// 0
//		"created_at", "modified_at",			// 1,2
//		"file_id", "file_path", "file_name",	// 3,4,5
//		"date_added", "date_modified",			// 6,7
//		"memos", "tags",						// 8,9
//		"last_viewed_at",						// 10
//		"table_name",							// 11
//		"uploaded_at",							// 12
		
		String orderBy = CONS.DB.col_names_IFM11_full[10] + " DESC";
		
		try {
			
			c = rdb.query(
					CONS.DB.tname_IFM11,			// 1
					CONS.DB.col_names_IFM11_full,	// 2
					null, null,		// 3,4
//					where, args,		// 3,4
					null, null,		// 5,6
					orderBy,
					String.valueOf(limit));
//			null);
			
		} catch (Exception e) {
			
			// Log
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ ":"
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", e.toString());
			
			rdb.close();
			
			return null;
			
		}//try
		
		/***************************************
		 * Validate
		 * 	Cursor => Null?
		 * 	Entry => 0?
		 ***************************************/
		if (c == null) {
			
			// Log
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ ":"
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", "Query failed");
			
			rdb.close();
			
			return null;
			
		} else if (c.getCount() < 1) {//if (c == null)
			
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ ":"
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", "No entry in the table");
			
			rdb.close();
			
			return null;
			
		}//if (c == null)
		
		/***************************************
		 * Build list
		 ***************************************/
//		android.provider.BaseColumns._ID,		// 0
//		"created_at", "modified_at",			// 1,2
//		"file_id", "file_path", "file_name",	// 3,4,5
//		"date_added", "date_modified",			// 6,7
//		"memos", "tags",						// 8,9
//		"last_viewed_at",						// 10
//		"table_name"							// 11
//		"uploaded_at",							// 12
		
		List<TI> ti_List = new ArrayList<TI>();
		
		while(c.moveToNext()) {
			
			TI ti = new TI.Builder()
			
			.setDb_Id(c.getLong(0))
			.setCreated_at(c.getString(1))
			.setModified_at(c.getString(2))
			
			.setFileId(c.getLong(3))
			.setFile_path(c.getString(4))
			.setFile_name(c.getString(5))
			
			.setDate_added(c.getString(6))
			.setDate_modified(c.getString(7))
			
			.setMemo(c.getString(8))
			.setTags(c.getString(9))
			
			.setLast_viewed_at(c.getString(10))
			.setTable_name(c.getString(11))
			
			.setUploaded_at(c.getString(12))
			
			.build();
			
			ti_List.add(ti);
			
		}
		
		rdb.close();
		
		return ti_List;
		
	}//find_TIs_History
	
	/******************************
		@return null => 1. No such table: ifm11<br>
	 ******************************/
	public static TI
	get_TI_From_FileId
	(Activity actv, long file_id) {
		// TODO Auto-generated method stub
		
		////////////////////////////////

		// DB

		////////////////////////////////
		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
		
		SQLiteDatabase rdb = dbu.getReadableDatabase();
		
		////////////////////////////////
		
		// validate: table exists?
		
		////////////////////////////////
		boolean res = dbu.tableExists(rdb, CONS.DB.tname_IFM11);

		if (res == false) {
			
			String msg = "No such table: " + CONS.DB.tname_IFM11;
			Methods_dlg.dlg_ShowMessage(actv, msg);
			
			return null;
			
		}
		
		////////////////////////////////
		
		// Query
		
		////////////////////////////////
		Cursor c = null;
		
//		android.provider.BaseColumns._ID,		// 0
//		"created_at", "modified_at",			// 1,2
//		"file_id", "file_path", "file_name",	// 3,4,5
//		"date_added", "date_modified",			// 6,7
//		"memos", "tags",						// 8,9
//		"last_viewed_at",						// 10
//		"table_name"							// 11
		
		String where = String.format(
					"%s = ? ", 
					CONS.DB.col_names_IFM11_full[3]);	// file_id
//					CONS.DB.col_names_IFM11_full[11]);	// table_name
//				CONS.DB.col_names_IFM11[8] + " = ?";
					
		String[] args = new String[]{
				
							String.valueOf(file_id),
							
						};
		
		try {
			
			c = rdb.query(
					
					CONS.DB.tname_IFM11,			// 1
					CONS.DB.col_names_IFM11_full,	// 2
					where, args,		// 3,4
					null, null,		// 5,6
					null,			// 7
					null);
			
		} catch (Exception e) {

			// Log
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ ":"
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", e.toString());
			
			rdb.close();
			
			return null;
			
		}//try
		
		/***************************************
		 * Validate
		 * 	Cursor => Null?
		 * 	Entry => 0?
		 ***************************************/
		if (c == null) {
			
			// Log
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ ":"
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", "Query failed");
			
			rdb.close();
			
			return null;
			
		} else if (c.getCount() < 1) {//if (c == null)
			
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ ":"
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", "No entry in the table");
			
			rdb.close();
			
			return null;
			
		}//if (c == null)

		////////////////////////////////

		// cursor: to first

		////////////////////////////////
		c.moveToFirst();

		////////////////////////////////

		// Build list

		////////////////////////////////
//		android.provider.BaseColumns._ID,		// 0
//		"created_at", "modified_at",			// 1,2
//		"file_id", "file_path", "file_name",	// 3,4,5
//		"date_added", "date_modified",			// 6,7
//		"memos", "tags",						// 8,9
//		"last_viewed_at",						// 10
//		"table_name"							// 11
			
		TI ti = new TI.Builder()

				.setDb_Id(c.getLong(0))
				.setCreated_at(c.getString(1))
				.setModified_at(c.getString(2))
				
				.setFileId(c.getLong(3))
				.setFile_path(c.getString(4))
				.setFile_name(c.getString(5))
				
				.setDate_added(c.getString(6))
				.setDate_modified(c.getString(7))
				
				.setMemo(c.getString(8))
				.setTags(c.getString(9))
				
				.setLast_viewed_at(c.getString(10))
				.setTable_name(c.getString(11))
				
				.setUploaded_at(c.getString(12))
				
				.build();
		
		////////////////////////////////

		// close

		////////////////////////////////
		rdb.close();
		
		return ti;
		
	}//get_TI_From_FileId

	/******************************
		@return null => 1. No such table: ifm11<br>
	 ******************************/
	public static TI
	get_TI_From_DbId
	(Activity actv, long db_Id) {
		// TODO Auto-generated method stub
		
		////////////////////////////////
		
		// DB
		
		////////////////////////////////
		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
		
		SQLiteDatabase rdb = dbu.getReadableDatabase();
		
		////////////////////////////////
		
		// validate: table exists?
		
		////////////////////////////////
		boolean res = dbu.tableExists(rdb, CONS.DB.tname_IFM11);
		
		if (res == false) {
			
			String msg = "No such table: " + CONS.DB.tname_IFM11;
			Methods_dlg.dlg_ShowMessage(actv, msg);
			
			return null;
			
		}
		
		////////////////////////////////
		
		// Query
		
		////////////////////////////////
		Cursor c = null;
		
//		android.provider.BaseColumns._ID,		// 0
//		"created_at", "modified_at",			// 1,2
//		"file_id", "file_path", "file_name",	// 3,4,5
//		"date_added", "date_modified",			// 6,7
//		"memos", "tags",						// 8,9
//		"last_viewed_at",						// 10
//		"table_name"							// 11
		
		String where = String.format(
				"%s = ? ", 
				CONS.DB.col_names_IFM11_full[0]);	// file_id
//					CONS.DB.col_names_IFM11_full[11]);	// table_name
//				CONS.DB.col_names_IFM11[8] + " = ?";
		
		String[] args = new String[]{
				
				String.valueOf(db_Id),
				
		};
		
		try {
			
			c = rdb.query(
					
					CONS.DB.tname_IFM11,			// 1
					CONS.DB.col_names_IFM11_full,	// 2
					where, args,		// 3,4
					null, null,		// 5,6
					null,			// 7
					null);
			
		} catch (Exception e) {
			
			// Log
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ ":"
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", e.toString());
			
			rdb.close();
			
			return null;
			
		}//try
		
		/***************************************
		 * Validate
		 * 	Cursor => Null?
		 * 	Entry => 0?
		 ***************************************/
		if (c == null) {
			
			// Log
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ ":"
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", "Query failed");
			
			rdb.close();
			
			return null;
			
		} else if (c.getCount() < 1) {//if (c == null)
			
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ ":"
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", "No entry in the table");
			
			rdb.close();
			
			return null;
			
		}//if (c == null)
		
		////////////////////////////////
		
		// cursor: to first
		
		////////////////////////////////
		c.moveToFirst();
		
		////////////////////////////////
		
		// Build list
		
		////////////////////////////////
//		android.provider.BaseColumns._ID,		// 0
//		"created_at", "modified_at",			// 1,2
//		"file_id", "file_path", "file_name",	// 3,4,5
//		"date_added", "date_modified",			// 6,7
//		"memos", "tags",						// 8,9
//		"last_viewed_at",						// 10
//		"table_name"							// 11
		
		TI ti = new TI.Builder()
		
						.setDb_Id(c.getLong(0))
						.setCreated_at(c.getString(1))
						.setModified_at(c.getString(2))
						
						.setFileId(c.getLong(3))
						.setFile_path(c.getString(4))
						.setFile_name(c.getString(5))
						
						.setDate_added(c.getString(6))
						.setDate_modified(c.getString(7))
						
						.setMemo(c.getString(8))
						.setTags(c.getString(9))
						
						.setLast_viewed_at(c.getString(10))
						.setTable_name(c.getString(11))
						
						.setUploaded_at(c.getString(12))
						
						.build();
		
		////////////////////////////////
		
		// close
		
		////////////////////////////////
		rdb.close();
		
		return ti;
		
	}//get_TI_From_DbId
	
	/******************************
		@return -1 => Table doesn't exist<br>
	 ******************************/
	public static int 
	insert_Data_Patterns
	(Activity actv, List<String> patterns_List) {
		// TODO Auto-generated method stub
		
		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
		
		SQLiteDatabase wdb = dbu.getWritableDatabase();
		
		////////////////////////////////

		// validate: table exists

		////////////////////////////////
		if (!DBUtils.tableExists(
					actv, CONS.DB.dbName, CONS.DB.tname_MemoPatterns)) {
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Table doesn't exist => " + CONS.DB.tname_MemoPatterns);
			
			String msg = "Table doesn't exist => " + CONS.DB.tname_MemoPatterns;
			Methods_dlg.dlg_ShowMessage(actv, msg, R.color.red);
			
			return -1;
			
		}//if (!tableExists(SQLiteDatabase db, String tableName))
		
		////////////////////////////////

		// Iteration

		////////////////////////////////
		int counter = 0;
		
		ContentValues val = null;
//		
		for (String pattern : patterns_List) {
			
			////////////////////////////////
			
			// prep: content values
			
			////////////////////////////////
			val = _insert_Data_Patterns__ContentValues(pattern);
			
			try {
				// Start transaction
				wdb.beginTransaction();
				
				// Insert data
				long res = wdb.insert(CONS.DB.tname_MemoPatterns, null, val);
	//			long res = wdb.insert(CONS.DB.tname_RefreshLog, null, val);
			
				if (res == -1) {
					
					// Log
					String msg_Log = "insertion => failed: " + pattern;
					Log.e("DBUtils.java"
							+ "["
							+ Thread.currentThread().getStackTrace()[2]
									.getLineNumber() + "]", msg_Log);
	
				} else {
					
					counter += 1;
					
					// Set as successful
					wdb.setTransactionSuccessful();
					
				}
				
				// End transaction
				wdb.endTransaction();
				
			} catch (Exception e) {
				
				// Log
				// Log
				String msg_Log = String.format(
									"Exception(%s) => %s", 
									pattern, e.toString());
				Log.e("DBUtils.java" + "["
						+ Thread.currentThread().getStackTrace()[2].getLineNumber()
						+ "]", msg_Log);
				
			}//try
			
		}//for (String pattern : patterns_List)

		////////////////////////////////

		// close

		////////////////////////////////
		wdb.close();

		////////////////////////////////

		// return

		////////////////////////////////
		return counter;
		
	}//insert_Data_Patterns

	/******************************
		@return -1 => Table doesn't exist<br>
	 ******************************/
	public static int 
	insert_Data_Patterns_New
	(Activity actv, List<String> patterns_List) {
		// TODO Auto-generated method stub
		
		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
		
		SQLiteDatabase wdb = dbu.getWritableDatabase();
		
		////////////////////////////////
		
		// validate: table exists
		
		////////////////////////////////
		if (!DBUtils.tableExists(
				actv, CONS.DB.dbName, CONS.DB.tname_MemoPatterns)) {
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Table doesn't exist => " + CONS.DB.tname_MemoPatterns);
			
			String msg = "Table doesn't exist => " + CONS.DB.tname_MemoPatterns;
			Methods_dlg.dlg_ShowMessage(actv, msg, R.color.red);
			
			return -1;
			
		}//if (!tableExists(SQLiteDatabase db, String tableName))
		
		////////////////////////////////
		
		// Iteration
		
		////////////////////////////////
		int counter = 0;
		
		ContentValues val = null;
//		
		for (String pattern : patterns_List) {
			
			////////////////////////////////
			
			// prep: content values
			
			////////////////////////////////
			val = _insert_Data_Patterns__ContentValues_New(pattern);
			
			try {
				// Start transaction
				wdb.beginTransaction();
				
				// Insert data
				long res = wdb.insert(CONS.DB.tname_MemoPatterns, null, val);
				//			long res = wdb.insert(CONS.DB.tname_RefreshLog, null, val);
				
				if (res == -1) {
					
					// Log
					String msg_Log = "insertion => failed: " + pattern;
					Log.e("DBUtils.java"
							+ "["
							+ Thread.currentThread().getStackTrace()[2]
									.getLineNumber() + "]", msg_Log);
					
				} else {
					
					counter += 1;
					
					// Set as successful
					wdb.setTransactionSuccessful();
					
				}
				
				// End transaction
				wdb.endTransaction();
				
			} catch (Exception e) {
				
				// Log
				// Log
				String msg_Log = String.format(
						"Exception(%s) => %s", 
						pattern, e.toString());
				Log.e("DBUtils.java" + "["
						+ Thread.currentThread().getStackTrace()[2].getLineNumber()
						+ "]", msg_Log);
				
			}//try
			
		}//for (String pattern : patterns_List)
		
		////////////////////////////////
		
		// close
		
		////////////////////////////////
		wdb.close();
		
		////////////////////////////////
		
		// return
		
		////////////////////////////////
		return counter;
		
	}//insert_Data_Patterns_Previous
	
	/******************************
		@return -1	=> Table doesn't exist<br>
				-2	=> Insertion failed<br>
				-3	=> Exception<br>
				1 <	=> The row ID
	 ******************************/
	public static int 
	insert_Data_Patterns_single
	(Activity actv, String pattern) {
		// TODO Auto-generated method stub
		
		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
		
		SQLiteDatabase wdb = dbu.getWritableDatabase();
		
		////////////////////////////////
		
		// validate: table exists
		
		////////////////////////////////
		if (!DBUtils.tableExists(
				actv, CONS.DB.dbName, CONS.DB.tname_MemoPatterns)) {
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Table doesn't exist => " + CONS.DB.tname_MemoPatterns);
			
			return -1;
			
		}//if (!tableExists(SQLiteDatabase db, String tableName))
		
		////////////////////////////////
		
		// Iteration
		
		////////////////////////////////
		ContentValues val = null;
//		
		////////////////////////////////
		
		// prep: content values
		
		////////////////////////////////
		val = _insert_Data_Patterns__ContentValues(pattern);
		
		try {
			// Start transaction
			wdb.beginTransaction();
			
			// Insert data
			long res = wdb.insert(CONS.DB.tname_MemoPatterns, null, val);
			//			long res = wdb.insert(CONS.DB.tname_RefreshLog, null, val);
			
			if (res == -1) {
				
				// Log
				String msg_Log = "insertion => failed: " + pattern;
				Log.e("DBUtils.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", msg_Log);
				
				wdb.close();
				
				return -2;
				
			} else {
				
				// Set as successful
				wdb.setTransactionSuccessful();
				
			}
			
			//			// Set as successful
			//			wdb.setTransactionSuccessful();
			
			// End transaction
			wdb.endTransaction();
			
			wdb.close();
			
			return (int) res;
			
		} catch (Exception e) {
			
			// Log
			// Log
			String msg_Log = String.format(
					"Exception(%s) => %s", 
					pattern, e.toString());
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			wdb.close();
			
			return -3;
			
		}//try
			
	}//insert_Data_Patterns_single
	
	private static ContentValues 
	_insert_Data_Patterns__ContentValues
	(String pattern) {
		// TODO Auto-generated method stub
		ContentValues val = new ContentValues();
		
//		android.provider.BaseColumns._ID,		// 0
//		"created_at", "modified_at",			// 1,2
//		"word",									// 3
//		"table_name"							// 4
		
		val.put(
				"created_at", 
				Methods.conv_MillSec_to_TimeLabel(
								Methods.getMillSeconds_now()));
		
		val.put(
				"modified_at", 
				Methods.conv_MillSec_to_TimeLabel(
						Methods.getMillSeconds_now()));
		
		val.put("word", pattern);
		
//		val.put("table_name", CONS.DB.tname_IFM11);

		return val;
		
	}//_insert_Data_Patterns__ContentValues

	private static ContentValues 
	_insert_Data_Patterns__ContentValues_New
	(String pattern) {
		// TODO Auto-generated method stub
		ContentValues val = new ContentValues();
		
//		android.provider.BaseColumns._ID,		// 0
//		"created_at", "modified_at",			// 1,2
//		"word",									// 3
//		"table_name"							// 4
		
		val.put(
				"created_at", 
				Methods.conv_MillSec_to_TimeLabel(
						Methods.getMillSeconds_now()));
		
		val.put(
				"modified_at", 
				Methods.conv_MillSec_to_TimeLabel(
						Methods.getMillSeconds_now()));
		
		val.put("word", pattern);
		
//		val.put("table_name", CONS.DB.tname_IFM11);
		
		return val;
		
	}//_insert_Data_Patterns__ContentValues
	
	
	public static boolean 
	update_TI__Memo
	(Activity actv, TI ti) {
		// TODO Auto-generated method stub
		
		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
		
		SQLiteDatabase wdb = dbu.getWritableDatabase();
		
		////////////////////////////////
		
		// prep: content values
		
		////////////////////////////////
		// ContentValues
		ContentValues val = _update_TI__Get_ContentValues_Memo(actv, ti);
//		ContentValues val = new ContentValues();
		
		String where = CONS.DB.col_names_IFM11_full[0]
						+ " = ?";
		
		String[] args = new String[]{String.valueOf(ti.getDb_Id())};
		
		try {
			// Start transaction
			wdb.beginTransaction();
			
			// Insert data
			long res = wdb.update(CONS.DB.tname_IFM11, val, where, args);
//			long res = wdb.insert(CONS.DB.tname_RefreshLog, null, val);
			
			if (res < 1) {
//				if (res == -1) {
				
				// Log
				String msg_Log = String.format(
									"insertion => failed (result = %d)"
									, res);

				Methods_dlg.dlg_ShowMessage(actv, msg_Log, R.color.red);
				
				wdb.endTransaction();
		
				wdb.close();
				
				return false;
				
			} else {
				
				// Log
				String msg_Log = "insertion => done";
				Log.d("DBUtils.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", msg_Log);
				
			}
			
			// Set as successful
			wdb.setTransactionSuccessful();
			
			// End transaction
			wdb.endTransaction();
			
//			// Log
//			Log.d("DBUtils.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", "Data inserted => " + "(" + columnNames[0] + " => " + values[0] + "), and others");
			
			wdb.close();
			
			return true;
			
		} catch (Exception e) {
			
			// Log
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception! => " + e.toString());
			
			wdb.close();
			
			return false;
			
		}//try		
		
//		return false;
		
	}//update_TI

	public static boolean 
	update_TI__UploadedAt
	(Activity actv, TI ti) {
		// TODO Auto-generated method stub
		
		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
		
		SQLiteDatabase wdb = dbu.getWritableDatabase();
		
		////////////////////////////////
		
		// prep: content values
		
		////////////////////////////////
//		android.provider.BaseColumns._ID,		// 0
//		"created_at", "modified_at",			// 1,2
//		"file_id", "file_path", "file_name",	// 3,4,5
//		"date_added", "date_modified",			// 6,7
//		"memos", "tags",						// 8,9
//		"last_viewed_at",						// 10
//		"table_name",							// 11
//		"uploaded_at",							// 12
		
		// ContentValues
		ContentValues val = _update_TI__Get_ContentValues_General(
						actv, 
						ti,
						CONS.DB.col_names_IFM11_full[12],
						Methods.conv_MillSec_to_TimeLabel(Methods.getMillSeconds_now())
//						Methods.get_TimeLabel(Methods.getMillSeconds_now())
		);
		
//		ContentValues val = new ContentValues();
		
		String where = CONS.DB.col_names_IFM11_full[0]
				+ " = ?";
		
		String[] args = new String[]{String.valueOf(ti.getDb_Id())};
		
		try {
			// Start transaction
			wdb.beginTransaction();
			
			// Insert data
			long res = wdb.update(CONS.DB.tname_IFM11, val, where, args);
//			long res = wdb.insert(CONS.DB.tname_RefreshLog, null, val);
			
			if (res < 1) {
//				if (res == -1) {
				
				// Log
				String msg_Log = String.format(
						"insertion => failed (result = %d)"
						, res);
				
				Methods_dlg.dlg_ShowMessage(actv, msg_Log, R.color.red);
				
				wdb.endTransaction();
				
				wdb.close();
				
				return false;
				
			} else {
				
				// Log
				String msg_Log = "insertion => done";
				Log.d("DBUtils.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", msg_Log);
				
			}
			
			// Set as successful
			wdb.setTransactionSuccessful();
			
			// End transaction
			wdb.endTransaction();
			
//			// Log
//			Log.d("DBUtils.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", "Data inserted => " + "(" + columnNames[0] + " => " + values[0] + "), and others");
			
			wdb.close();
			
			return true;
			
		} catch (Exception e) {
			
			// Log
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception! => " + e.toString());
			
			wdb.close();
			
			return false;
			
		}//try		
		
//		return false;
		
	}//update_TI__UploadedAt
	
	private static ContentValues 
	_update_TI__Get_ContentValues_Memo
	(Activity actv, TI ti) {
		// TODO Auto-generated method stub
		ContentValues val = new ContentValues();
		
//		android.provider.BaseColumns._ID,		// 0
//		"created_at", "modified_at",			// 1,2
//		"file_id", "file_path", "file_name",	// 3,4,5
//		"date_added", "date_modified",			// 6,7
//		"memos", "tags",						// 8,9
//		"last_viewed_at",						// 10
//		"table_name"							// 11
		
		val.put(
				CONS.DB.col_names_IFM11_full[2],		// modified_at 
				Methods.conv_MillSec_to_TimeLabel(
						Methods.getMillSeconds_now()));
		
		val.put(
				CONS.DB.col_names_IFM11_full[8],		// memos
				ti.getMemo());
		
		return val;
		
	}//_insert_Data_Patterns__ContentValues
	
	private static ContentValues 
	_update_TI__Get_ContentValues_General
	(Activity actv, TI ti, String key, String value) {
		// TODO Auto-generated method stub
		ContentValues val = new ContentValues();
		
//		android.provider.BaseColumns._ID,		// 0
//		"created_at", "modified_at",			// 1,2
//		"file_id", "file_path", "file_name",	// 3,4,5
//		"date_added", "date_modified",			// 6,7
//		"memos", "tags",						// 8,9
//		"last_viewed_at",						// 10
//		"table_name"							// 11
		
		val.put(
				CONS.DB.col_names_IFM11_full[2],		// modified_at 
				Methods.conv_MillSec_to_TimeLabel(
						Methods.getMillSeconds_now()));
		
		val.put(key, value);
		
		return val;
		
	}//_insert_Data_Patterns__ContentValues
	
	/******************************
		update => modified_at, file_path, file_name, memos
	 ******************************/
	private static ContentValues 
	_update_TI__Get_ContentValues_All
	(Activity actv, TI ti) {
		// TODO Auto-generated method stub
		ContentValues val = new ContentValues();
		
//		android.provider.BaseColumns._ID,		// 0
//		"created_at", "modified_at",			// 1,2
//		"file_id", "file_path", "file_name",	// 3,4,5
//		"date_added", "date_modified",			// 6,7
//		"memos", "tags",						// 8,9
//		"last_viewed_at",						// 10
//		"table_name"							// 11
		
		val.put(
				CONS.DB.col_names_IFM11_full[2],		// modified_at 
				Methods.conv_MillSec_to_TimeLabel(
						Methods.getMillSeconds_now()));
		
		val.put(
				CONS.DB.col_names_IFM11_full[4],		// file_path
				ti.getFile_path());
		
		val.put(
				CONS.DB.col_names_IFM11_full[5],		// file_name
				ti.getFile_name());
		
		val.put(
				CONS.DB.col_names_IFM11_full[8],		// memos
				ti.getMemo());
		
		return val;
		
	}//_insert_Data_Patterns__ContentValues
	
//	private static ContentValues 
//	_insert_Data_Patterns__ContentValues
//	(String pattern) {
//		// TODO Auto-generated method stub
//		ContentValues val = new ContentValues();
//		
////		android.provider.BaseColumns._ID,		// 0
////		"created_at", "modified_at",			// 1,2
////		"word",									// 3
////		"table_name"							// 4		
//		
//		val.put(
//				CONS.DB.col_names_MemoPatterns_full[2],		// modified_at 
//				Methods.conv_MillSec_to_TimeLabel(
//						Methods.getMillSeconds_now()));
//		
//		val.put(
//				CONS.DB.col_names_MemoPatterns_full[3],		// word
//				pattern);
//		
//		return val;
//		
//	}//_insert_Data_Patterns__ContentValues

	private static ContentValues 
	get_ContentValues__TI_TableName
	(Activity actv, String tableName) {
		// TODO Auto-generated method stub
		ContentValues val = new ContentValues();
		
//		android.provider.BaseColumns._ID,		// 0
//		"created_at", "modified_at",			// 1,2
//		"file_id", "file_path", "file_name",	// 3,4,5
//		"date_added", "date_modified",			// 6,7
//		"memos", "tags",						// 8,9
//		"last_viewed_at",						// 10
//		"table_name"							// 11
		
		val.put(
				CONS.DB.col_names_IFM11_full[2],		// modified_at 
				Methods.conv_MillSec_to_TimeLabel(
						Methods.getMillSeconds_now()));
		
		val.put(
				CONS.DB.col_names_IFM11_full[11],		// memos
				tableName);
		
		return val;
		
	}//_insert_Data_Patterns__ContentValues

	public static int 
	update_TI_All__TableName
	(Activity actv, List<TI> toMoveFiles) {
		// TODO Auto-generated method stub

		////////////////////////////////

		// vars

		////////////////////////////////
		int counter = 0;
		
		////////////////////////////////

		// setup db

		////////////////////////////////
		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
		
		SQLiteDatabase wdb = dbu.getWritableDatabase();

		String sql = null;
		
		ContentValues val = null;
		
		////////////////////////////////

		// update

		////////////////////////////////
		for (TI ti : toMoveFiles) {
			
			val = DBUtils.get_ContentValues__TI_TableName(actv, ti.getTable_name());
			
			String where = CONS.DB.col_names_IFM11_full[0]
							+ " = ?";
			
			String[] args = new String[]{String.valueOf(ti.getDb_Id())};
			
			try {
				// Start transaction
				wdb.beginTransaction();
				
				// Insert data
				long res = wdb.update(CONS.DB.tname_IFM11, val, where, args);
//				long res = wdb.insert(CONS.DB.tname_RefreshLog, null, val);
				
				if (res < 1) {
//					if (res == -1) {
					
					// Log
					String msg_Log = String.format(
										"insertion => failed: file name = %s" +
										" (result = %d)"
										, ti.getFile_name(), res);

					// Log
					Log.d("DBUtils.java"
							+ "["
							+ Thread.currentThread().getStackTrace()[2]
									.getLineNumber() + "]", msg_Log);
					
					wdb.endTransaction();
			
					continue;
					
				} else {
					
					// Log
					String msg_Log = String.format(
									"insertion => done (file name = %s)"
									, ti.getFile_name());
					Log.d("DBUtils.java"
							+ "["
							+ Thread.currentThread().getStackTrace()[2]
									.getLineNumber() + "]", msg_Log);
					
				}
				
				// Set as successful
				wdb.setTransactionSuccessful();
				
				// End transaction
				wdb.endTransaction();
				
				// count
				counter += 1;
				
			} catch (Exception e) {
				
				String msg_Log = String.format(
							"Exception (%s) => " + e.toString(), 
							ti.getFile_name());
				// Log
				Log.e("DBUtils.java" + "["
						+ Thread.currentThread().getStackTrace()[2].getLineNumber()
						+ "]", msg_Log);

				continue;
				
			}//try					
			
		}//for (TI ti : toMoveFiles)

		////////////////////////////////

		// close db

		////////////////////////////////
		wdb.close();
		
		
		return counter;
		
	}//update_TI_All__TableName

	/******************************
		@return -1 => Query exception<br>
				-2 => Query returned null<br>
				-3 => Query returned no entry<br>
	 ******************************/
	public static int 
	get_NumOfEntries_TI
	(Activity actv, String tname) {
		// TODO Auto-generated method stub
		
		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
		
		//
		SQLiteDatabase rdb = dbu.getReadableDatabase();

//		////////////////////////////////
//		
//		// setup: cols
//		
//		////////////////////////////////
//		String[] cols = null;
//		
//		if (tname.equals(CONS.DB.tname_IFM11)) {
			
		String[] cols = CONS.DB.col_names_IFM11_full;
					
//		} else if (tname.equals(CONS.DB.tname_MemoPatterns)) {
//
//			cols = CONS.DB.col_names_MemoPatterns_full;
//			
//		} else if (tname.equals(CONS.DB.tname_RefreshLog)) {
//			
//			cols = CONS.DB.col_names_refresh_log_full;
//			
//		}
		
		////////////////////////////////

		// Query

		////////////////////////////////
		Cursor c = null;
		
		actv.startManagingCursor(c);
		
//		android.provider.BaseColumns._ID,		// 0
//		"created_at", "modified_at",			// 1,2
//		"file_id", "file_path", "file_name",	// 3,4,5
//		"date_added", "date_modified",			// 6,7
//		"memos", "tags",						// 8,9
//		"last_viewed_at",						// 10
//		"table_name"							// 11
		
		String where = CONS.DB.col_names_IFM11_full[11]
						+ " = ?";
		
		String[] args = new String[]{
				
				tname
				
		};
		
		try {
			
			c = rdb.query(
					
					CONS.DB.tname_IFM11,			// 1
					CONS.DB.col_names_IFM11_full,	// 2
					where, args,		// 3,4
					null, null,		// 5,6
					null,			// 7
					null);
			
		} catch (Exception e) {

			// Log
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ ":"
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", e.toString());
			
			rdb.close();
			
			return -1;
			
		}//try
		
		/***************************************
		 * Validate
		 * 	Cursor => Null?
		 * 	Entry => 0?
		 ***************************************/
		if (c == null) {
			
			// Log
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ ":"
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", "Query failed");
			
			rdb.close();
			
			return -2;
			
		} else if (c.getCount() < 1) {//if (c == null)
			
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ ":"
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", "No entry in the table");
			
			rdb.close();
			
			return -3;
			
		}//if (c == null)		
		
		////////////////////////////////

		// prep: return

		////////////////////////////////
		int numOfItems = c.getCount();
		
		////////////////////////////////

		// close

		////////////////////////////////
		rdb.close();
		
		
		return numOfItems;
		
	}//get_NumOfEntries_TI

	public static List<TI> 
	find_All_TI__Search
	(Activity actv) {
		// TODO Auto-generated method stub
		
		////////////////////////////////

		// validate: DB file exists?

		////////////////////////////////
		File dpath_DBFile = actv.getDatabasePath(CONS.DB.dbName);

		if (!dpath_DBFile.exists()) {
			
			String msg = "No DB file: " + CONS.DB.dbName;
			Methods_dlg.dlg_ShowMessage(actv, msg);
			
			return null;
			
		}
		
		////////////////////////////////

		// DB

		////////////////////////////////
		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
		
		SQLiteDatabase rdb = dbu.getReadableDatabase();
		
		////////////////////////////////

		// iteration: searched TI ids

		////////////////////////////////
		TI ti = null;
//		String tableName = null;
//		String where = null;
//		String[] args = null;
//		TI ti_tmp = null;
		
		List<TI> ti_List = new ArrayList<TI>();
		
		for (Long id : CONS.TNActv.searchedItems) {
			
			////////////////////////////////

			// get: TI

			////////////////////////////////
			ti = DBUtils.get_TI_From_DbId(actv, id.longValue());
			
			ti_List.add(ti);
			
//			////////////////////////////////
//			
//			// Query
//			
//			////////////////////////////////
//			Cursor c = null;
//
////			"file_id", "file_path", "file_name",	// 0,1,2
////			"date_added", "date_modified",			// 3,4
////			"memos", "tags",						// 5,6
////			"last_viewed_at",						// 7
////			"table_name"							// 8
//			
//			where = CONS.DB.col_names_IFM11[8] + " = ?";
//			args = new String[]{
//					
//					ti.getTable_name()
////					tableName
//			};
//			
//			try {
//				
//				c = rdb.query(
//						
//						CONS.DB.tname_IFM11,			// 1
//						CONS.DB.col_names_IFM11_full,	// 2
//						where, args,		// 3,4
//						null, null,		// 5,6
//						null,			// 7
//						null);
//				
//			} catch (Exception e) {
//				
//				// Log
//				Log.e("DBUtils.java" + "["
//						+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//						+ ":"
//						+ Thread.currentThread().getStackTrace()[2].getMethodName()
//						+ "]", e.toString());
//				
//				continue;
//				
////				rdb.close();
////				
////				return null;
//				
//			}//try

//			/***************************************
//			 * Validate
//			 * 	Cursor => Null?
//			 * 	Entry => 0?
//			 ***************************************/
//			if (c == null) {
//				
//				// Log
//				Log.e("DBUtils.java" + "["
//						+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//						+ ":"
//						+ Thread.currentThread().getStackTrace()[2].getMethodName()
//						+ "]", "Query failed");
//				
//				continue;
//				
////				rdb.close();
////				
////				return null;
//				
//			} else if (c.getCount() < 1) {//if (c == null)
//				
//				// Log
//				Log.d("DBUtils.java" + "["
//						+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//						+ ":"
//						+ Thread.currentThread().getStackTrace()[2].getMethodName()
//						+ "]", "No entry in the table");
//				
//				continue;
//				
////				rdb.close();
////				
////				return null;
//				
//			}//if (c == null)
//
//			/***************************************
//			 * Build list
//			 ***************************************/
////			android.provider.BaseColumns._ID,		// 0
////			"created_at", "modified_at",			// 1,2
////			"file_id", "file_path", "file_name",	// 3,4,5
////			"date_added", "date_modified",			// 6,7
////			"memos", "tags",						// 8,9
////			"last_viewed_at",						// 10
////			"table_name"							// 11
//			
//			
//			while(c.moveToNext()) {
//				
//				ti_tmp = new TI.Builder()
//
//						.setDb_Id(c.getLong(0))
//						.setCreated_at(c.getString(1))
//						.setModified_at(c.getString(2))
//						
//						.setFileId(c.getLong(3))
//						.setFile_path(c.getString(4))
//						.setFile_name(c.getString(5))
//						
//						.setDate_added(c.getString(6))
//						.setDate_modified(c.getString(7))
//						
//						.setMemo(c.getString(8))
//						.setTags(c.getString(9))
//						
//						.setLast_viewed_at(c.getString(10))
//						.setTable_name(c.getString(11))
//						.build();
//				
//				ti_List.add(ti);
//				
//			}//while(c.moveToNext())
			
		}//for (Long id : CONS.TNActv.searchedItems)

		////////////////////////////////

		// close: db

		////////////////////////////////
		rdb.close();		
		
		////////////////////////////////

		// return

		////////////////////////////////
		return ti_List;
		
	}//find_All_TI__Search

	/******************************
		@return -1	=> TI doesn't exist in db<br>
				-2	=> ti.table_name ==> null<br>
				-3	=> deletion => returned 0<br>
				> 1	=> Number of items affected
	 ******************************/
	public static int 
	delete_TI
	(Activity actv, TI ti, boolean checked) {
		// TODO Auto-generated method stub
		////////////////////////////////

		// delete: from db

		////////////////////////////////
		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
		
		SQLiteDatabase wdb = dbu.getWritableDatabase();
		
		////////////////////////////////

		// validate: is in db

		////////////////////////////////
		boolean res = DBUtils.isInDB_long_ai(wdb, CONS.DB.tname_IFM11, ti.getDb_Id());

		if (res == false) {
			
			wdb.close();
			
			return -1;
			
		}
		
		////////////////////////////////

		// validate: table name => set

		////////////////////////////////
		String tableName = ti.getTable_name();
		
		if (tableName == null) {
			
			wdb.close();
			
			return -2;
			
		}
		
		////////////////////////////////

		// Query

		////////////////////////////////
		String where = CONS.DB.col_names_IFM11_full[0] + " = ?";
//		String where = CONS.DB.col_names_IFM11[1] + " = ?";
		
		String[] args = new String[]{
				
							String.valueOf(ti.getDb_Id())
							
						};
 
		int res_int = wdb.delete(CONS.DB.tname_IFM11, where, args);
		
		/******************************
			validate: success
		 ******************************/
		if (res_int < 1) {
			
			wdb.close();
			
			return -3;
			
		}
		
		////////////////////////////////

		// close

		////////////////////////////////
		wdb.close();
		
		////////////////////////////////

		// log

		////////////////////////////////
		String log_msg = String.format(
					"Delete from DB => done: %s (table = %s)",
					ti.getFile_name(), ti.getTable_name()
				);
		
		Methods.write_Log(actv, log_msg,
				Thread.currentThread().getStackTrace()[2].getFileName(), Thread
						.currentThread().getStackTrace()[2].getLineNumber());
		
		////////////////////////////////

		// return

		////////////////////////////////
		return res_int;
		
	}//delete_TI

	/******************************
		@return -1 => Insertion failed<br>
				-2 => Exception<br>
				1 => Insertion done<br>
	 ******************************/
	public static int 
	update_TI__All
	(Activity actv, TI ti) {
		// TODO Auto-generated method stub
		////////////////////////////////

		// setup: db

		////////////////////////////////
		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
		
		SQLiteDatabase wdb = dbu.getWritableDatabase();

		////////////////////////////////

		// setup: contentvals

		////////////////////////////////
		
		ContentValues val = _update_TI__Get_ContentValues_All(actv, ti);
//		ContentValues val = new ContentValues();
		
		String where = CONS.DB.col_names_IFM11_full[0]
						+ " = ?";
		
		String[] args = new String[]{String.valueOf(ti.getDb_Id())};
		
		try {
			// Start transaction
			wdb.beginTransaction();
			
			// Insert data
			long res = wdb.update(CONS.DB.tname_IFM11, val, where, args);
//			long res = wdb.insert(CONS.DB.tname_RefreshLog, null, val);
			
			if (res < 1) {
//				if (res == -1) {
				
//				// Log
//				String msg_Log = String.format(
//									"insertion => failed (result = %d)"
//									, res);
//
//				Methods_dlg.dlg_ShowMessage(actv, msg_Log, R.color.red);
				
				wdb.endTransaction();
		
				wdb.close();
				
				return -1;
				
			} else {
				
				// Log
				String msg_Log = "insertion => done";
				Log.d("DBUtils.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", msg_Log);
				
			}
			
			// Set as successful
			wdb.setTransactionSuccessful();
			
			// End transaction
			wdb.endTransaction();
			
//			// Log
//			Log.d("DBUtils.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", "Data inserted => " + "(" + columnNames[0] + " => " + values[0] + "), and others");
			
			wdb.close();
			
			return 1;
			
		} catch (Exception e) {
			
			// Log
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception! => " + e.toString());
			
			wdb.close();
			
			return -2;
			
		}//try		
		
//		return false;
		
	}//update_TI__All

	/******************************
		@return -1 => Insertion failed<br>
				-2 => Exception<br>
				1 => Insertion done<br>
	 ******************************/
	public static int 
	update_TI
	(Activity actv, TI ti, String colName, String value) {
		// TODO Auto-generated method stub
		////////////////////////////////
		
		// setup: db
		
		////////////////////////////////
		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
		
		SQLiteDatabase wdb = dbu.getWritableDatabase();
		
		////////////////////////////////
		
		// setup: contentvals
		
		////////////////////////////////
		ContentValues val = new ContentValues();
		
//		android.provider.BaseColumns._ID,		// 0
//		"created_at", "modified_at",			// 1,2
//		"file_id", "file_path", "file_name",	// 3,4,5
//		"date_added", "date_modified",			// 6,7
//		"memos", "tags",						// 8,9
//		"last_viewed_at",						// 10
//		"table_name",							// 11
//		"uploaded_at",							// 12		
		
		val.put(
				CONS.DB.col_names_IFM11_full[2],		// modified_at 
				Methods.conv_MillSec_to_TimeLabel(
						Methods.getMillSeconds_now()));
		
		val.put(colName, value);
		
		////////////////////////////////

		// args

		////////////////////////////////
		String where = CONS.DB.col_names_IFM11_full[0]
				+ " = ?";
		
		String[] args = new String[]{String.valueOf(ti.getDb_Id())};

		////////////////////////////////

		// update

		////////////////////////////////
		try {
			// Start transaction
			wdb.beginTransaction();
			
			// Insert data
			long res = wdb.update(CONS.DB.tname_IFM11, val, where, args);
//			long res = wdb.insert(CONS.DB.tname_RefreshLog, null, val);
			
			if (res < 1) {
//				if (res == -1) {
				
//				// Log
//				String msg_Log = String.format(
//									"insertion => failed (result = %d)"
//									, res);
//
//				Methods_dlg.dlg_ShowMessage(actv, msg_Log, R.color.red);
				
				wdb.endTransaction();
				
				wdb.close();
				
				return -1;
				
			} else {
				
				// Log
				String msg_Log = "insertion => done";
				Log.d("DBUtils.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", msg_Log);
				
			}
			
			// Set as successful
			wdb.setTransactionSuccessful();
			
			// End transaction
			wdb.endTransaction();
			
//			// Log
//			Log.d("DBUtils.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", "Data inserted => " + "(" + columnNames[0] + " => " + values[0] + "), and others");
			
			wdb.close();
			
			return 1;
			
		} catch (Exception e) {
			
			// Log
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception! => " + e.toString());
			
			wdb.close();
			
			return -2;
			
		}//try		
		
//		return false;
		
	}//update_TI__All
	
	/******************************
		@return
				-1	=> Pattern not in db<br>
				-2	=> Deletion failed<br>
	 ******************************/
	public static int 
	del_Pattern
	(Activity actv, WordPattern wp) {
		// TODO Auto-generated method stub
		
		////////////////////////////////

		// delete: from db

		////////////////////////////////
		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
		
		SQLiteDatabase wdb = dbu.getWritableDatabase();
		
		////////////////////////////////

		// validate: is in db

		////////////////////////////////
		boolean res = DBUtils.isInDB_long_ai(
							wdb, 
							CONS.DB.tname_MemoPatterns, 
							wp.getDb_Id());
		
		// Log
		String msg_Log = "res => " + res;
		Log.d("DBUtils.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);

		if (res == false) {
			
			wdb.close();
			
			return -1;
			
		}

		////////////////////////////////

		// Query

		////////////////////////////////
		String where = CONS.DB.col_names_MemoPatterns_full[0] + " = ?";
//		String where = CONS.DB.col_names_IFM11[1] + " = ?";
		
		String[] args = new String[]{
				
							String.valueOf(wp.getDb_Id())
							
						};
 
		int res_int = wdb.delete(CONS.DB.tname_MemoPatterns, where, args);
		
		/******************************
			validate: success
		 ******************************/
		if (res_int == 0) {
			
			wdb.close();
			
			return -2;
			
		}
		
		////////////////////////////////

		// close

		////////////////////////////////
		wdb.close();
		
		////////////////////////////////

		// return

		////////////////////////////////
		return res_int;
		
	}//del_Pattern

	/******************************
		@return
			-1 => Unknown sql type<br>
			-2 => Exception<br>
			1 => Sql done<br>
	 ******************************/
	public static int 
	exec_Sql
	(Activity actv, String sql_Type) {
		// TODO Auto-generated method stub
		////////////////////////////////

		// setup

		////////////////////////////////
		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
		
		SQLiteDatabase wdb = dbu.getWritableDatabase();		
		
		String sql = null;

		////////////////////////////////

		// dispatch

		////////////////////////////////
		if (sql_Type.equals(
				CONS.DB.Sqls._20140817_154650_addCol_IFM11_UpdatedAt_TITLE)) {
			
			sql = CONS.DB.Sqls._20140817_154650_addCol_IFM11_UpdatedAt_SQL;
			
		} else {

			wdb.close();
			
			// Log
			String msg_Log = "Unknown sql type => " + sql_Type;
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			return -1;
			
		}
		
		try {
			
			wdb.execSQL(sql);
			
			// Log
			String msg_Log = "sql done => " + sql;
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
		} catch (Exception e) {
			// TODO: handle exception

			// Log
			String msg_Log = "Exception => " + e.toString();
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			wdb.close();
			
			return -2;
			
		}
		
		
		////////////////////////////////

		// close

		////////////////////////////////
		wdb.close();
		
		return 1;
		
	}//exec_Sql

	/******************************
		@param diff2 
	 * @return
			-1	list_TIs => null<br>
			> 0	Number of items updated<br>
	 ******************************/
	public static int 
	update_TIs__Memo
	(Activity actv, String tname, String diff) {
		// TODO Auto-generated method stub
		
		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
		
		SQLiteDatabase wdb = dbu.getWritableDatabase();

//		String tname = CONS.DB.tname_IFM11;
		
		int res_i = 0;
		
		////////////////////////////////

		// list

		////////////////////////////////
		List<TI> list_TIs = DBUtils.find_All_TI(actv, tname);
		
		/******************************
			validate
		 ******************************/
		if (list_TIs == null) {
			
			// Log
			String msg_Log = "list_TIs => null";
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			return -1;
			
		}
		
		////////////////////////////////

		// iterate

		////////////////////////////////
		ContentValues val = new ContentValues();;
		
		String where = null;
		String[] args = null;
		
		String tmp = null;
		
		for (TI ti : list_TIs) {

			////////////////////////////////

			// content values

			////////////////////////////////
			val.put(
					CONS.DB.col_names_IFM11_full[2],		// modified_at 
					Methods.conv_MillSec_to_TimeLabel(
							Methods.getMillSeconds_now()));
			
			tmp = ti.getMemo();
			
			// Memo
			if (tmp == null) {
				
				val.put(
						CONS.DB.col_names_IFM11_full[8],		// memos
						diff + " ");
				
			} else {

				val.put(
						CONS.DB.col_names_IFM11_full[8],		// memos
						tmp + " " + diff + " ");
				
			}

			try {
				
				where = CONS.DB.col_names_IFM11_full[0] + " = ?";
				
				args = new String[]{String.valueOf(ti.getDb_Id())};
				
				// Start transaction
				wdb.beginTransaction();
				
				// Insert data
				long res = wdb.update(CONS.DB.tname_IFM11, val, where, args);
//				long res = wdb.insert(CONS.DB.tname_RefreshLog, null, val);
				
				if (res < 1) {
//					if (res == -1) {

					// Log
					String msg_Log = "res < 1: " + ti.getDb_Id();
					Log.d("DBUtils.java"
							+ "["
							+ Thread.currentThread().getStackTrace()[2]
									.getLineNumber() + "]", msg_Log);
					
					continue;
					
//					// Log
//					String msg_Log = String.format(
//										"insertion => failed (result = %d)"
//										, res);
//
//					Methods_dlg.dlg_ShowMessage(actv, msg_Log, R.color.red);
//					
//					wdb.endTransaction();
//			
//					wdb.close();
//					
//					return false;
					
				} else {
					
					// Log
					String msg_Log = "insertion => done";
					Log.d("DBUtils.java"
							+ "["
							+ Thread.currentThread().getStackTrace()[2]
									.getLineNumber() + "]", msg_Log);
					
				}
				
				// Set as successful
				wdb.setTransactionSuccessful();
				
				// End transaction
				wdb.endTransaction();

				res_i += 1;
				
			} catch (Exception e) {

				// Log
				String msg_Log = String.format(
							"%s%d (%s)", 
							"Exception: ", ti.getDb_Id(), e.toString());
				
				continue;
				
			}//try		

		}//for (TI ti : list_TIs)
		
		////////////////////////////////

		// close

		////////////////////////////////
		wdb.close();
		
		return res_i;
		
	}//update_TIs__Memo

	/******************************
		@return
		null =>
				1. No DB file<br>
				2. No such table<br>
				3. Query exception<br>
				4. Query returned null<br>
				5. No entry in the table<br>
	 ******************************/
	public static List<WordPattern> 
	find_All_WP
	(Activity actv) {
		// TODO Auto-generated method stub
		////////////////////////////////
	
		// validate: DB file exists?
	
		////////////////////////////////
		File dpath_DBFile = actv.getDatabasePath(CONS.DB.dbName);
	
		if (!dpath_DBFile.exists()) {
			
			String msg = "No DB file: " + CONS.DB.dbName;
			Methods_dlg.dlg_ShowMessage(actv, msg);
			
			return null;
			
		}
		
		////////////////////////////////
	
		// DB
	
		////////////////////////////////
		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
		
		SQLiteDatabase rdb = dbu.getReadableDatabase();
		
		////////////////////////////////
		
		// validate: table exists?
		
		////////////////////////////////
		String tname = CONS.DB.tname_MemoPatterns;
		
		boolean res = dbu.tableExists(rdb, tname);
	//	boolean res = dbu.tableExists(rdb, tableName);
	
		if (res == false) {
			
			String msg = "No such table: " + tname;
			Methods_dlg.dlg_ShowMessage(actv, msg);
			
			rdb.close();
			
			return null;
			
		}
	
		////////////////////////////////
		
		// Query
		
		////////////////////////////////
		Cursor c = null;
		
	//	String where = CONS.DB.col_names_IFM11[8] + " = ?";
	//	String[] args = new String[]{
	//			
	//						tableName
	//					};
		
		try {
			
			c = rdb.query(
					
					tname,			// 1
					CONS.DB.col_names_MemoPatterns_full,	// 2
					null, null,		// 3,4
	//				where, args,		// 3,4
					null, null,		// 5,6
					null,			// 7
					null);
			
		} catch (Exception e) {
	
			// Log
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ ":"
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", e.toString());
			
			String msg = "Query exception";
			Methods_dlg.dlg_ShowMessage(actv, msg, R.color.red);
			
			rdb.close();
			
			return null;
			
		}//try
		
		/***************************************
		 * Validate
		 * 	Cursor => Null?
		 * 	Entry => 0?
		 ***************************************/
		if (c == null) {
			
			String msg = "Query failed";
			
			// Log
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ ":"
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", msg);
	
			Methods_dlg.dlg_ShowMessage(actv, msg, R.color.red);
			
			rdb.close();
			
			return null;
			
		} else if (c.getCount() < 1) {//if (c == null)
			
			String msg = "No entry in the table";
			
			// Log
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ ":"
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", msg);
	
			Methods_dlg.dlg_ShowMessage(actv, msg, R.color.red);
			
			rdb.close();
			
			return null;
			
		}//if (c == null)
		
		/***************************************
		 * Build list
		 ***************************************/
	//	android.provider.BaseColumns._ID,		// 0
	//	"created_at", "modified_at",			// 1,2
	//	"word",									// 3
		
		List<WordPattern> list_WP = new ArrayList<WordPattern>();
		
		// Log
		String msg_Log = "building WP list...";
		Log.d("DBUtils.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		while(c.moveToNext()) {
			
			WordPattern wp = new WordPattern.Builder()
	
					.setDb_Id(c.getLong(0))
					.setCreated_at(c.getString(1))
					.setModified_at(c.getString(2))
					
					.setWord(c.getString(3))
					.setUsed(c.getInt(4))
					.setUsed_at(c.getString(5))
					
					.build();
			
			list_WP.add(wp);
			
			//debug
			if (c.isNull(4)) {
				
				// Log
				msg_Log = "cursor(4) => null";
				Log.d("DBUtils.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", msg_Log);
				
			} else {

				// Log
				msg_Log = "cursor(4) => not null";
				Log.d("DBUtils.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", msg_Log);

			}
			
			// Log
			msg_Log = String.format(
					Locale.JAPAN,
					"(%d) %s (used = %d)", 
					wp.getDb_Id(), wp.getWord(), wp.getUsed());
			
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
		}
	
		rdb.close();
		
		return list_WP;
		
	}//find_All_WP

	/******************************
		@return
			-1 find pattern => failed<br>
			-2 SQLException<br>
			1 update => executed<br>
	 ******************************/
	public static int 
	update_Pattern_Used
	(Activity actv, long db_Id) {
		// TODO Auto-generated method stub
		////////////////////////////////
	
		// prep: vars
	
		////////////////////////////////
		WordPattern wp = DBUtils.find_Pattern_From_Id(actv, db_Id);
	
		/******************************
			validate
		 ******************************/
		if (wp == null) {
			
			// Log
			String msg_Log = "find pattern => failed: " + db_Id;
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			return -1;
			
		}
		
		int used_Current = wp.getUsed();
		int used_Updated = used_Current + 1;

		// Log
		String msg_Log = "used_Current => " + used_Current;
		Log.d("DBUtils.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		msg_Log = "used_Updated => " + used_Updated;
		Log.d("DBUtils.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		String used_at = Methods.conv_MillSec_to_TimeLabel(Methods.getMillSeconds_now());
		
		////////////////////////////////
	
		// setup: db
	
		////////////////////////////////
		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
		
		//
		SQLiteDatabase wdb = dbu.getWritableDatabase();
		
//		android.provider.BaseColumns._ID,		// 0
//		"created_at", "modified_at",			// 1,2
//		"word",									// 3
//		"used",									// 4
//		"used_at",								// 5
		
		String sql = "UPDATE " + CONS.DB.tname_MemoPatterns + 
				" SET " + 
				
				CONS.DB.col_names_MemoPatterns_full[4] + 
//				" = " + used_Updated + " " +
				" = '" + String.valueOf(used_Updated) + "' " +
//				" = '" + used_Updated + "' " +
				", " +
				CONS.DB.col_names_MemoPatterns_full[2] +
				" = '" + 
				Methods.conv_MillSec_to_TimeLabel(Methods.getMillSeconds_now()) + 
				"' " +
				", " +
				CONS.DB.col_names_MemoPatterns_full[5] +
				" = '" + 
				used_at +
				"' " +
				
				" WHERE " + android.provider.BaseColumns._ID + " = '" + 
				db_Id + "'";
		
		try {
			
			wdb.execSQL(sql);
			
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "sql => Done: " + sql);
			
			//Methods.toastAndLog(actv, "Data updated", 2000);
			
			wdb.close();
			
			return 1;
			
			
		} catch (SQLException e) {
			// Log
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception => " + e.toString() + " / " + "sql: " + sql);
			
			wdb.close();
			
			return -2;
		}
		
	}//update_Pattern_Used

	/******************************
		@return
			-1 find pattern => failed<br>
			<!-- -2 SQLException<br> -->
			1 update => executed<br>
	 ******************************/
	public static int 
	update_Pattern_Used_2
	(Activity actv, long db_Id) {
		// TODO Auto-generated method stub
		////////////////////////////////
		
		// prep: vars
		
		////////////////////////////////
		WordPattern wp = DBUtils.find_Pattern_From_Id(actv, db_Id);
		
		/******************************
			validate
		 ******************************/
		if (wp == null) {
			
			// Log
			String msg_Log = "find pattern => failed: " + db_Id;
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			return -1;
			
		}
		
		int used_Current = wp.getUsed();
		int used_Updated = used_Current + 1;
		
		// Log
		String msg_Log = "used_Current => " + used_Current;
		Log.d("DBUtils.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		msg_Log = "used_Updated => " + used_Updated;
		Log.d("DBUtils.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		String used_at = Methods.conv_MillSec_to_TimeLabel(Methods.getMillSeconds_now());
		
		////////////////////////////////
		
		// setup: db
		
		////////////////////////////////
		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
		
		//
		SQLiteDatabase wdb = dbu.getWritableDatabase();
		
//		android.provider.BaseColumns._ID,		// 0
//		"created_at", "modified_at",			// 1,2
//		"word",									// 3
//		"used",									// 4
//		"used_at",								// 5
		
		ContentValues cv = new ContentValues();
		
		cv.put(CONS.DB.col_names_MemoPatterns_full[4], String.valueOf(used_Updated));
		
		cv.put(
				CONS.DB.col_names_MemoPatterns_full[2], 
				Methods.conv_MillSec_to_TimeLabel(Methods.getMillSeconds_now()));
		
		cv.put(CONS.DB.col_names_MemoPatterns_full[5], used_at);

		String where = android.provider.BaseColumns._ID + " = ?";
		String[] args = new String[]{String.valueOf(db_Id)};
	
		// update
		int res = wdb.update(CONS.DB.tname_MemoPatterns, cv, where, args);
		
		// Log
		msg_Log = "res => " + res;
		Log.d("DBUtils.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		////////////////////////////////

		// close

		////////////////////////////////
		wdb.close();
		
		return res;
		
//		String sql = "UPDATE " + CONS.DB.tname_MemoPatterns + 
//				" SET " + 
//				
//				CONS.DB.col_names_MemoPatterns_full[4] + 
////				" = " + used_Updated + " " +
//				" = '" + String.valueOf(used_Updated) + "' " +
////				" = '" + used_Updated + "' " +
//					", " +
//					CONS.DB.col_names_MemoPatterns_full[2] +
//					" = '" + 
//					Methods.conv_MillSec_to_TimeLabel(Methods.getMillSeconds_now()) + 
//					"' " +
//					", " +
//					CONS.DB.col_names_MemoPatterns_full[5] +
//					" = '" + 
//					used_at +
//					"' " +
//
//				" WHERE " + android.provider.BaseColumns._ID + " = '" + 
//				db_Id + "'";
		
//		try {
//			
//			wdb.execSQL(sql);
//			
//			// Log
//			Log.d("DBUtils.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", "sql => Done: " + sql);
//			
//			//Methods.toastAndLog(actv, "Data updated", 2000);
//			
//			wdb.close();
//			
//			return 1;
//			
//			
//		} catch (SQLException e) {
//			// Log
//			Log.e("DBUtils.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", "Exception => " + e.toString() + " / " + "sql: " + sql);
//			
//			wdb.close();
//			
//			return -2;
//		}
//		
	}//update_Pattern_Used
	
	/******************************
		@return
			null => 
				1. No DB file<br>
				2. No such file<br>
				3. Query exception<br>
				4. Query failed<br>
				5. No entry in the table<br>
	 ******************************/
	public static WordPattern 
	find_Pattern_From_Id
	(Activity actv, long db_Id) {
		// TODO Auto-generated method stub
		////////////////////////////////
		
		// validate: DB file exists?
		
		////////////////////////////////
		File dpath_DBFile = actv.getDatabasePath(CONS.DB.dbName);
		
		if (!dpath_DBFile.exists()) {
			
			String msg = "No DB file: " + CONS.DB.dbName;
			Methods_dlg.dlg_ShowMessage(actv, msg);
			
			return null;
			
		}
		
		////////////////////////////////
		
		// DB
		
		////////////////////////////////
		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
		
		SQLiteDatabase rdb = dbu.getReadableDatabase();
		
		////////////////////////////////
		
		// validate: table exists?
		
		////////////////////////////////
		String tname = CONS.DB.tname_MemoPatterns;
		
		boolean res = dbu.tableExists(rdb, tname);
	//	boolean res = dbu.tableExists(rdb, tableName);
		
		if (res == false) {
			
			String msg = "No such table: " + tname;
			Methods_dlg.dlg_ShowMessage(actv, msg);
			
			rdb.close();
			
			return null;
			
		}
		
		////////////////////////////////
		
		// Query
		
		////////////////////////////////
		Cursor c = null;
		
		String where = CONS.DB.col_names_MemoPatterns_full[0] + " = ?";
		
		String[] args = new String[]{
				
				String.valueOf(db_Id)
				
		};
		
		try {
			
			c = rdb.query(
					
					tname,			// 1
					CONS.DB.col_names_MemoPatterns_full,	// 2
	//				null, null,		// 3,4
					where, args,		// 3,4
					null, null,		// 5,6
					null,			// 7
					null);
			
		} catch (Exception e) {
			
			// Log
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ ":"
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", e.toString());
			
			String msg = "Query exception";
			Methods_dlg.dlg_ShowMessage(actv, msg, R.color.red);
			
			rdb.close();
			
			return null;
			
		}//try
		
		/***************************************
		 * Validate
		 * 	Cursor => Null?
		 * 	Entry => 0?
		 ***************************************/
		if (c == null) {
			
			String msg = "Query failed";
			
			// Log
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ ":"
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", msg);
			
			Methods_dlg.dlg_ShowMessage(actv, msg, R.color.red);
			
			rdb.close();
			
			return null;
			
		} else if (c.getCount() < 1) {//if (c == null)
			
			String msg = "No entry in the table";
			
			// Log
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ ":"
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", msg);
			
			Methods_dlg.dlg_ShowMessage(actv, msg, R.color.red);
			
			rdb.close();
			
			return null;
			
		}//if (c == null)
		
		/***************************************
		 * Build list
		 ***************************************/
	//	android.provider.BaseColumns._ID,		// 0
	//	"created_at", "modified_at",			// 1,2
	//	"word",									// 3		
		c.moveToFirst();
		
		WordPattern wp = new WordPattern.Builder()
					.setDb_Id(c.getLong(0))
					.setCreated_at(c.getString(1))
					.setModified_at(c.getString(2))
					
					.setWord(c.getString(3))
					.setUsed(c.getInt(4))
					.setUsed_at(c.getString(5))
					
					.build();
		
		rdb.close();
		
		return wp;
		
	}//find_Memo_From_Id

	/******************************
		@return
		
	 ******************************/
	public static List<WordPattern> 
	find_All_WP_symbols
	(Activity actv) {
		// TODO Auto-generated method stub
		
		////////////////////////////////
	
		// get all WP
	
		////////////////////////////////
		List<WordPattern> list_WP = DBUtils.find_All_WP(actv);
	
		////////////////////////////////
		
		// prep: filter
		
		////////////////////////////////
		//REF http://stackoverflow.com/questions/1047342/how-to-run-a-query-with-regexp-in-android answered Jun 26 '09 at 5:25
		//REF http://ocpsoft.org/opensource/guide-to-regular-expressions-in-java-part-1/ "2.4. Extracting/Capturing"
		String regex = "[a-zA-Z]";
		
		Pattern p = Pattern.compile(regex);
		
		Matcher m = null;
		
		////////////////////////////////
	
		// filter
	
		////////////////////////////////
		List<WordPattern> list_WP_filtered = 
								new ArrayList<WordPattern>();
		
		// If the word DOES NOT contain [a-zA-Z]
		//		=> then, put it into the new list
		for (WordPattern wp : list_WP) {
			
			m = p.matcher(wp.getWord());
			
			if (m.find()) {
				
				continue;
				
			}
			
			list_WP_filtered.add(wp);
			
		}
		
		
		return list_WP_filtered;
		
	}//find_All_WP_symbols
	
	/******************************
		@return
		
	 ******************************/
	public static List<WordPattern> 
	find_All_WP_tags
	(Activity actv) {
		// TODO Auto-generated method stub
		
		////////////////////////////////
		
		// get all WP
		
		////////////////////////////////
		List<WordPattern> list_WP = DBUtils.find_All_WP(actv);
		
		////////////////////////////////
		
		// prep: filter
		
		////////////////////////////////
		//REF http://stackoverflow.com/questions/1047342/how-to-run-a-query-with-regexp-in-android answered Jun 26 '09 at 5:25
		//REF http://ocpsoft.org/opensource/guide-to-regular-expressions-in-java-part-1/ "2.4. Extracting/Capturing"
	//	String regex = "^[:#]";
	//	String regex = "^(:|\\#)";
	//	String regex = "^(:|#)";
	//	String regex = "^(:|#)[a-zA-Z]";
		String regex = "^[:#][a-zA-Z]";
		
		Pattern p = Pattern.compile(regex);
		
		Matcher m = null;
		
		////////////////////////////////
		
		// filter
		
		////////////////////////////////
		List<WordPattern> list_WP_filtered = 
				new ArrayList<WordPattern>();
		
		// If the word DOES NOT contain [a-zA-Z]
		//		=> then, put it into the new list
		for (WordPattern wp : list_WP) {
			
			m = p.matcher(wp.getWord());
			
			if (m.find()) {
				
				list_WP_filtered.add(wp);
	//			continue;
				
			}
			
	//		// Log
	//		String msg_Log = String.format("match => %s", wp.getWord());
	//		Log.d("DBUtils.java" + "["
	//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
	//				+ "]", msg_Log);
			
	//		list_WP_filtered.add(wp);
			
		}
		
		
		return list_WP_filtered;
		
	}//find_All_WP_tags
	
	/******************************
		@return
		
	 ******************************/
	public static List<WordPattern> 
	find_All_WP_literals
	(Activity actv) {
		// TODO Auto-generated method stub
		
		////////////////////////////////
		
		// get all WP
		
		////////////////////////////////
		List<WordPattern> list_WP = DBUtils.find_All_WP(actv);
		
		////////////////////////////////
		
		// prep: filter
		
		////////////////////////////////
		//REF http://stackoverflow.com/questions/1047342/how-to-run-a-query-with-regexp-in-android answered Jun 26 '09 at 5:25
		//REF http://ocpsoft.org/opensource/guide-to-regular-expressions-in-java-part-1/ "2.4. Extracting/Capturing"
	//	String regex = "^[:#]";
	//	String regex = "^(:|\\#)";
	//	String regex = "^(:|#)";
	//	String regex = "^(:|#)[a-zA-Z]";
	//	String regex = "^[:#][a-zA-Z]";
		String reg1 = "^[:#][a-zA-Z]";
		String reg2 = "^[^\\w]+$";
		
		Pattern p1_tags = Pattern.compile(reg1);
		Pattern p2_symbols = Pattern.compile(reg2);
		
		Matcher m1_tags = null;
		Matcher m2_symbols = null;
		
		////////////////////////////////
		
		// filter
		
		////////////////////////////////
		List<WordPattern> list_WP_filtered = 
				new ArrayList<WordPattern>();
		
		// If the word DOES NOT contain [a-zA-Z]
		//		=> then, put it into the new list
		for (WordPattern wp : list_WP) {
			
			m1_tags = p1_tags.matcher(wp.getWord());
			m2_symbols = p2_symbols.matcher(wp.getWord());
			
			// Neither tag, nor symbol
			if (!m1_tags.find() && !m2_symbols.find()) {
				
				list_WP_filtered.add(wp);
	//			continue;
				
			}
			
	//		// Log
	//		String msg_Log = String.format("match => %s", wp.getWord());
	//		Log.d("DBUtils.java" + "["
	//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
	//				+ "]", msg_Log);
			
	//		list_WP_filtered.add(wp);
			
		}
		
		
		return list_WP_filtered;
		
	}//find_All_WP_literals

	public static boolean 
	_backup_DB_SaveDate
	(Activity actv) {
		// TODO Auto-generated method stub
		////////////////////////////////

		// validate: any entry

		////////////////////////////////
		int count = DBUtils.count_Entry(
								actv, 
								CONS.DB.tname_Admin, 
								CONS.DB.col_names_Admin);
		
		/******************************
			validate
		 ******************************/
		if (count < 0 && count != -4) {
			
			// Log
			String msg_Log = String.format("table => not ready: %s (count = %d)",
									CONS.DB.tname_Admin,
									count);
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			return false;
			
		}
		
		////////////////////////////////

		// save date

		////////////////////////////////
		boolean res;
		
		if (count >= 1) {

//			android.provider.BaseColumns._ID,		// 0
//			"created_at", "modified_at",			// 1,2
//			"name",									// 3
//			"val",									// 4			
			
			String where = CONS.DB.col_names_Admin_full[3] + " = ?";
			
			String[] args = new String[]{
					
						CONS.DB.admin_LastBackup
						
			};

			String now = Methods.conv_MillSec_to_TimeLabel(Methods.getMillSeconds_now());
			
			ContentValues val = new ContentValues();
			
			val.put(
					CONS.DB.col_names_Admin_full[2],
					now);

			val.put(
					CONS.DB.col_names_Admin_full[4],
					now);

			res = DBUtils.updateData_generic_static(
							actv, CONS.DB.tname_Admin, 
							
							val, where, args);
			
		} else {

			ContentValues val = new ContentValues();
			
			val.put(
					CONS.DB.col_names_Admin_full[1],
					Methods.conv_MillSec_to_TimeLabel(Methods.getMillSeconds_now()));
			
			val.put(
					CONS.DB.col_names_Admin_full[2],
					Methods.conv_MillSec_to_TimeLabel(Methods.getMillSeconds_now()));
			
			val.put(
					CONS.DB.col_names_Admin_full[3],
					CONS.DB.admin_LastBackup);
			
			val.put(
					CONS.DB.col_names_Admin_full[4],
					Methods.conv_MillSec_to_TimeLabel(Methods.getMillSeconds_now()));
			
			res = DBUtils.insert_Data_generic(actv, CONS.DB.tname_Admin, val);
			
		}
		
		return res;
		
	}//backup_DB_SaveDate

	/******************************
		@return
			-1	table doesn't exist<br>
			-2	query exception<br>
			-3	query => returned null<br>
			-4	entry => less than 1<br>
	 ******************************/
	private static int 
	count_Entry
	(Activity actv, String tname, String[] cols) {
		// TODO Auto-generated method stub
		////////////////////////////////
		
		// validate: table exists
		
		////////////////////////////////
		if (!DBUtils.tableExists(actv, CONS.DB.dbName, tname)) {
			// Log
			Log.i("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Table doesn't exists => " + tname);
			
			return -1;
			
		}//if (!tableExists(SQLiteDatabase db, String tableName))

		////////////////////////////////

		// prep: db

		////////////////////////////////
		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
		
		SQLiteDatabase rdb = dbu.getReadableDatabase();

		////////////////////////////////
		
		// Query
		
		////////////////////////////////
		Cursor c = null;
		
		try {
			
			c = rdb.query(
					
					tname,			// 1
					cols,	// 2
					null, null,		// 3,4
//					where, args,		// 3,4
					null, null,		// 5,6
					null,			// 7
					null);
			
		} catch (Exception e) {

			// Log
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ ":"
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", e.toString());
			
			rdb.close();
			
			return -2;
			
		}//try
		
		/***************************************
		 * Validate
		 * 	Cursor => Null?
		 * 	Entry => 0?
		 ***************************************/
		if (c == null) {
			
			// Log
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ ":"
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", "Query failed");
			
			rdb.close();
			
			return -3;
			
		} else if (c.getCount() < 1) {//if (c == null)
			
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ ":"
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", "No entry in the table");
			
			rdb.close();
			
			return -4;
			
		}//if (c == null)

		////////////////////////////////

		// return

		////////////////////////////////
		int count = c.getCount();
		
		rdb.close();
		
		return count;
		
	}//count_Entry

	
	/******************************
		@return
			null<br>
			1. No DB file<br>
			2. No such table<br>
			3. query => Exception<br>
			4. cursor => returned null<br>
			5. cursor => no entry<br>
	 ******************************/
	public static String 
	find_LastBK
	(Activity actv) {
		// TODO Auto-generated method stub
		
		////////////////////////////////

		// validate: DB file exists?

		////////////////////////////////
		File dpath_DBFile = actv.getDatabasePath(CONS.DB.dbName);

		if (!dpath_DBFile.exists()) {
			
			String msg = "No DB file: " + CONS.DB.dbName;
			Methods_dlg.dlg_ShowMessage(actv, msg);
			
			return null;
			
		}
		
		////////////////////////////////

		// DB

		////////////////////////////////
		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
		
		SQLiteDatabase rdb = dbu.getReadableDatabase();
		
		////////////////////////////////
		
		// validate: table exists?
		
		////////////////////////////////
		String tname = CONS.DB.tname_Admin;
		
		boolean res = dbu.tableExists(rdb, tname);

		if (res == false) {
			
			String msg = "No such table: " + tname;
			Methods_dlg.dlg_ShowMessage(actv, msg);
			
			rdb.close();
			
			return null;
			
		}

		////////////////////////////////
		
		// Query
		
		////////////////////////////////
		Cursor c = null;
		
//		android.provider.BaseColumns._ID,		// 0
//		"created_at", "modified_at",			// 1,2
//		"name",									// 3
//		"val",									// 4
		
		String where = CONS.DB.col_names_Admin_full[3] + " = ?";
		String[] args = new String[]{
				
							CONS.DB.admin_LastBackup
						};
		
		String orderBy = CONS.DB.col_names_Admin_full[0] + " ASC";
		
		try {
			
			c = rdb.query(
					
					tname,			// 1
					CONS.DB.col_names_Admin_full,	// 2
					where, args,		// 3,4
					null, null,		// 5,6
					orderBy,			// 7
					null);
			
		} catch (Exception e) {

			// Log
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ ":"
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", e.toString());
			
			rdb.close();
			
			return null;
			
		}//try
		
		/***************************************
		 * Validate
		 * 	Cursor => Null?
		 * 	Entry => 0?
		 ***************************************/
		if (c == null) {
			
			// Log
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ ":"
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", "Query failed");
			
			rdb.close();
			
			return null;
			
		} else if (c.getCount() < 1) {//if (c == null)
			
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ ":"
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", "No entry in the table");
			
			rdb.close();
			
			return null;
			
		}//if (c == null)

		////////////////////////////////

		// value

		////////////////////////////////
		c.moveToFirst();
		
		String val = c.getString(4);

		rdb.close();
		
		return val;
		
	}//find_LastBK

//	public static Cursor 
//	get_Cursor_IFM11() {
//		// TODO Auto-generated method stub
//		
//		////////////////////////////////
//
//		// DB
//
//		////////////////////////////////
//		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
//		
//		SQLiteDatabase rdb = dbu.getReadableDatabase();
//		
//		////////////////////////////////
//		
//		// validate: table exists?
//		
//		////////////////////////////////
//		String tname = CONS.DB.tname_IFM11;
//		
//		boolean res = dbu.tableExists(rdb, tname);
////		boolean res = dbu.tableExists(rdb, tableName);
//
//		if (res == false) {
//			
//			String msg = "No such table: " + tname;
//			Methods_dlg.dlg_ShowMessage(actv, msg);
//			
//			rdb.close();
//			
//			return null;
//			
//		}
//		
//		Cursor c = null;
//		
////		String where = CONS.DB.col_names_IFM11[8] + " = ?";
////		String[] args = new String[]{
////				
////							tableName
////						};
//		
//		try {
//			
//			c = rdb.query(
//					
//					tname,			// 1
//					CONS.DB.col_names_IFM11_full,	// 2
//					null, null,		// 3,4
////					where, args,		// 3,4
//					null, null,		// 5,6
//					null,			// 7
//					null);
//			
//			return c;
//			
//		} catch (Exception e) {
//
//			// Log
//			Log.e("DBUtils.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ ":"
//					+ Thread.currentThread().getStackTrace()[2].getMethodName()
//					+ "]", e.toString());
//			
//			rdb.close();
//			
//			return null;
//			
//		}//try
//
//	}//get_Cursor_IFM11

}//public class DBUtils

