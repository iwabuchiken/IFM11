package ifm11.main;

import java.util.Locale;

import ifm11.utils.CONS;
import ifm11.utils.DBUtils;
import ifm11.utils.Methods_dlg;
import android.app.Activity;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

public class CV extends ContentProvider {

	Activity actv;
	Context con;
	
	private SQLiteDatabase db;

	public static final String AUTHORITY = "ifm11.main.CV";
//	public static final String AUTHORITY = "ifm11.provider.db";
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/");
//	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/");
	
	@Override
	public int delete(Uri arg0, String arg1, String[] arg2) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getType(Uri arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri arg0, ContentValues arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		
		// Log
		String msg_Log = "onCreate";
		Log.d("CV.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		Context context = getContext();
	    DatabaseHelper dbHelper = new DatabaseHelper(context, CONS.DB.dbName);
//	    DatabaseHelper dbHelper = new DatabaseHelper(context);
		
	    db = dbHelper.getWritableDatabase();
	    
//		this.actv = new MainActv();
////		this.actv = (Activity) this.getContext();
//		this.con	= this.getContext();
		
		return true;
		
	}

	@Override
	public Cursor 
	query
	(Uri uri, 
		String[] projection, String selection, 
		String[] args, String order) {
		// TODO Auto-generated method stub
		String msg_Log;
		
		// Log
		msg_Log = String.format(
						Locale.JAPAN,
						"uri = %s, selection = %s, order = %s",
						uri, selection, order);
		
		Log.d("CV.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		
//		Cursor c = null;
//		
//		c = DBUtils.get_Cursor_IFM11();
//		
//		
//		return c;
		
		////////////////////////////////

		// DB

		////////////////////////////////
//		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
//		
//		qb.setTables(CONS.DB.tname_IFM11);
		
		////////////////////////////////

		// query

		////////////////////////////////
		
		Cursor c = this.db.query(
//				Cursor c = qb.query(
//					this.db, 
					CONS.DB.tname_IFM11,
					CONS.DB.col_names_IFM11_full, 
					selection, args, 
//					null, null, 
					null, null, order);
		
		// Log
		msg_Log = "returning query...";
		Log.d("CV.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		return c;
		
////		DBUtils dbu = new DBUtils(this.con, CONS.DB.dbName);
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

		
	}//query

	@Override
	public int 
	update
	(Uri uri, ContentValues cv, String where, String[] args) {
		// TODO Auto-generated method stub

		////////////////////////////////

		// db

		////////////////////////////////
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		
		qb.setTables(CONS.DB.tname_IFM11);

		// Log
		String msg_Log = "uri.getAuthority() => " + uri.getAuthority();
		Log.d("CV.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);

		////////////////////////////////

		// update

		////////////////////////////////
		String tname = CONS.DB.tname_IFM11;
		
		try {
			
			// Start transaction
			this.db.beginTransaction();
//			wdb.beginTransaction();
			
			// Insert data
			long res = this.db.update(tname, cv, where, args);
			
			if (res < 1) {
				// Log
				msg_Log = String.format(
						"update => failed (result = %d): table = %s"
						, res, tname);
				
				this.db.endTransaction();
				
				this.db.close();
				
				return (int)res;
				
			} else {
				
				// Log
				msg_Log = "update => done: " + tname;
				Log.d("DBUtils.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", msg_Log);
				
			}
			
			// Set as successful
			this.db.setTransactionSuccessful();
			
			// End transaction
			this.db.endTransaction();
			
			this.db.close();
			
			return (int)res;
			
		} catch (Exception e) {
			
			// Log
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception! => " + e.toString());
			
			this.db.close();
			
			return -1;
			
		}//try		
		
//
//		
//		return 2;
////		return 0;
		
	}//update

	private static class DatabaseHelper extends SQLiteOpenHelper {
		
	       DatabaseHelper(Context context, String dbName){
	          super(context, dbName, null, 1);
//	          super(context, DATABASE_NAME, null, DATABASE_VERSION);
	       }

	       @Override
	       public void onCreate(SQLiteDatabase db)
	       {
//	          db.execSQL(CREATE_DB_TABLE);
	       }
	       
	       @Override
	       public void onUpgrade(SQLiteDatabase db, int oldVersion, 
	                             int newVersion) {
//	          db.execSQL("DROP TABLE IF EXISTS " +  STUDENTS_TABLE_NAME);
//	          onCreate(db);
	       }
	   }
}
