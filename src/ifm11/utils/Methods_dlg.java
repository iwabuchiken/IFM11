package ifm11.utils;

import ifm11.adapters.Adp_ListItems;
import ifm11.adapters.Adp_WordPatterns;
import ifm11.items.ListItem;
import ifm11.items.TI;
import ifm11.items.WordPattern;
import ifm11.listeners.DLOI_LCL;
import ifm11.listeners.LOI_LCL;
import ifm11.listeners.dialog.DB_OCL;
import ifm11.listeners.dialog.DB_OTL;
import ifm11.listeners.dialog.DOI_CL;
import ifm11.main.R;
import ifm11.utils.Tags.DialogTags;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.lang.StringUtils;

import android.app.Activity;
import android.app.Dialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Methods_dlg {

	public static void 
	dlg_Db_Actv
	(Activity actv) {
		/****************************
		 * 1. Dialog
		 * 2. Prep => List
		 * 3. Adapter
		 * 4. Set adapter
		 * 
		 * 5. Set listener to list
		 * 6. Show dialog
			****************************/
		Dialog dlg1 = Methods_dlg.dlg_Template_Cancel(
									actv, R.layout.dlg_tmpl_list_cancel, 
									R.string.dlg_db_admin_title, 
									R.id.dlg_tmpl_list_cancel_bt_cancel, 
//									R.id.dlg_db_admin_bt_cancel, 
									Tags.DialogTags.GENERIC_DISMISS);
		
		//test
		// Log
		String msg_Log = "actv => " + actv.getClass().getName();
		Log.d("Methods_dlg.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		/****************************
		 * 2. Prep => List
			****************************/
		List<ListItem> list = _dlg_Db_Actv__Get_ItemList(actv);
//		String[] choices = {
//
//				////////////////////////////////
//
//				// DB
//
//				////////////////////////////////
//				actv.getString(R.string.dlg_db_admin_item_backup_db),
//				actv.getString(R.string.dlg_db_admin_item_refresh_db),
//				
//				actv.getString(R.string.dlg_db_admin_item_upload_db),
//				
//				////////////////////////////////
//
//				// drop/create tables
//
//				////////////////////////////////
//				actv.getString(R.string.dlg_db_admin_item_drop_table_ifm11),
//				actv.getString(R.string.dlg_db_admin_item_create_table_ifm11),
//				
//				actv.getString(
//							R.string.dlg_db_admin_item_drop_table_refresh_log),
//				actv.getString(
//							R.string.dlg_db_admin_item_create_table_refresh_log),
//				
//				actv.getString(
//						R.string.dlg_db_admin_item_create_table_memo_patterns),
//				actv.getString(
//						R.string.dlg_db_admin_item_drop_table_memo_patterns),
//
//				////////////////////////////////
//
//				// others
//
//				////////////////////////////////
//				actv.getString(R.string.dlg_db_admin_item_restore_db),
//				
//				actv.getString(R.string.dlg_db_admin_item_import_db_file),
//				
//				actv.getString(R.string.dlg_db_admin_item_import_patterns),
//				
//				CONS.DB.Sqls._20140817_154650_addCol_IFM11_UpdatedAt_TITLE
////				actv.getString(R.string.dlg_db_admin_item_exec_sql)
////					+ "/"
////					+ actv.getString(R.string.dlg_db_admin_exec_sql_add_col)
//							,
//				
//					};
		
//		List<String> list = new ArrayList<String>();
//		
//		for (String item : choices) {
//			
//			list.add(item);
//			
//		}
		
		/****************************
		 * 3. Adapter
			****************************/
		Adp_ListItems adapter = new Adp_ListItems(
				actv,
//				R.layout.dlg_db_admin,
//				android.R.layout.simple_list_item_1,
				R.layout.list_row_simple_1,
				list
				);
//		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
//				actv,
////				R.layout.dlg_db_admin,
////				android.R.layout.simple_list_item_1,
//				R.layout.list_row_simple_1,
//				list
//				);

		/****************************
		 * 4. Set adapter
			****************************/
		ListView lv = (ListView) dlg1.findViewById(R.id.dlg_tmpl_list_cancel_lv);
//		ListView lv = (ListView) dlg.findViewById(R.id.dlg_db_admin_lv);
		
		lv.setAdapter(adapter);
		
		/****************************
		 * 5. Set listener to list
			****************************/
		lv.setTag(Tags.DialogItemTags.DLG_DB_ADMIN_LV);
		
		lv.setOnItemClickListener(new DOI_CL(actv, dlg1));
		
		/****************************
		 * 6. Show dialog
			****************************/
		dlg1.show();
		
		
	}//public static void dlg_db_activity(Activity actv)

	private static List<ListItem> 
	_dlg_Db_Actv__Get_ItemList
	(Activity actv) {
		// TODO Auto-generated method stub
		
		List<ListItem> list = new ArrayList<ListItem>();
		
		list.add(new ListItem.Builder()
					.setText(actv.getString(
								R.string.dlg_db_admin_item_backup_db))
					.setIconID(R.drawable.menu_icon_admin_32x32_blue)
					.setTextColor_ID(R.color.blue1)
					.build());

		list.add(new ListItem.Builder()
					.setText(actv.getString(
							R.string.dlg_db_admin_item_refresh_db))
							.setIconID(R.drawable.menu_icon_admin_32x32_brown)
							.setTextColor_ID(R.color.black)
							.build());
		
		list.add(new ListItem.Builder()
					.setText(actv.getString(
							R.string.dlg_db_admin_item_upload_db))
							.setIconID(R.drawable.menu_icon_admin_32x32_purple)
							.setTextColor_ID(R.color.purple4)
							.build());
		
		list.add(new ListItem.Builder()
					.setText(actv.getString(
							R.string.dlg_db_admin_item_operations))
							.setIconID(R.drawable.menu_icon_admin_32x32_yellow)
							.setTextColor_ID(R.color.yellow_dark)
							.build());
		
		return list;
		
//		////////////////////////////////
//
//		// DB
//
//		////////////////////////////////
//		actv.getString(R.string.dlg_db_admin_item_backup_db),
//		actv.getString(R.string.dlg_db_admin_item_refresh_db),
//		
//		actv.getString(R.string.dlg_db_admin_item_upload_db),
//		
//		////////////////////////////////
//
//		// drop/create tables
//
//		////////////////////////////////
//		actv.getString(R.string.dlg_db_admin_item_drop_table_ifm11),
//		actv.getString(R.string.dlg_db_admin_item_create_table_ifm11),
//		
//		actv.getString(
//					R.string.dlg_db_admin_item_drop_table_refresh_log),
//		actv.getString(
//					R.string.dlg_db_admin_item_create_table_refresh_log),
//		
//		actv.getString(
//				R.string.dlg_db_admin_item_create_table_memo_patterns),
//		actv.getString(
//				R.string.dlg_db_admin_item_drop_table_memo_patterns),
//
//		////////////////////////////////
//
//		// others
//
//		////////////////////////////////
//		actv.getString(R.string.dlg_db_admin_item_restore_db),
//		
//		actv.getString(R.string.dlg_db_admin_item_import_db_file),
//		
//		actv.getString(R.string.dlg_db_admin_item_import_patterns),
//		
//		CONS.DB.Sqls._20140817_154650_addCol_IFM11_UpdatedAt_TITLE
////		actv.getString(R.string.dlg_db_admin_item_exec_sql)
////			+ "/"
////			+ actv.getString(R.string.dlg_db_admin_exec_sql_add_col)
//					,
//		
//			};

//		return null;
		
	}//_dlg_Db_Actv__Get_ItemList

	public static
	Dialog dlg_Template_Cancel
	(Activity actv, int layoutId, int titleStringId,
			int cancelButtonId, Tags.DialogTags cancelTag) {
		/****************************
		* Steps
		* 1. Set up
		* 2. Add listeners => OnTouch
		* 3. Add listeners => OnClick
		****************************/
		
		// 
		Dialog dlg = new Dialog(actv);
		
		//
		dlg.setContentView(layoutId);
		
		// Title
		dlg.setTitle(titleStringId);
		
		/****************************
		* 2. Add listeners => OnTouch
		****************************/
		//
		Button btn_cancel = (Button) dlg.findViewById(cancelButtonId);
		
		//
		btn_cancel.setTag(cancelTag);
		
		//
		btn_cancel.setOnTouchListener(new DB_OTL(actv, dlg));
		
		/****************************
		* 3. Add listeners => OnClick
		****************************/
		//
		btn_cancel.setOnClickListener(new DB_OCL(actv, dlg));
		
		//
		//dlg.show();
		
		return dlg;
	
	}//public static Dialog dlg_template_okCancel()
	
	public static Dialog 
	dlg_Template_Cancel_SecondDialog
	(Activity actv, Dialog dlg1,
			int layoutId, int titleStringId,
			int cancelButtonId, Tags.DialogTags cancelTag) {
		/****************************
		 * Steps
		 * 1. Set up
		 * 2. Add listeners => OnTouch
		 * 3. Add listeners => OnClick
		 ****************************/
		
		// 
		Dialog dlg2 = new Dialog(actv);
		
		//
		dlg2.setContentView(layoutId);
		
		// Title
		dlg2.setTitle(titleStringId);
		
		/****************************
		 * 2. Add listeners => OnTouch
		 ****************************/
		//
		Button btn_cancel = (Button) dlg2.findViewById(cancelButtonId);
		
		//
		btn_cancel.setTag(cancelTag);
		
		//
		btn_cancel.setOnTouchListener(new DB_OTL(actv, dlg2));
		
		/****************************
		 * 3. Add listeners => OnClick
		 ****************************/
		//
		btn_cancel.setOnClickListener(new DB_OCL(actv, dlg1, dlg2));
		
		//
		//dlg.show();
		
		return dlg2;
		
	}//public static Dialog dlg_template_okCancel()

	public static Dialog 
	dlg_Template_OkCancel_SecondDialog
	(Activity actv, Dialog dlg1,
			int layoutId, int titleStringId,
			
			int okButtonId, Tags.DialogTags okTag,
			int cancelButtonId, Tags.DialogTags cancelTag) {
		/****************************
		 * Steps
		 * 1. Set up
		 * 2. Add listeners => OnTouch
		 * 3. Add listeners => OnClick
		 ****************************/
		
		// 
		Dialog dlg2 = new Dialog(actv);
		
		//
		dlg2.setContentView(layoutId);
		
		// Title
		dlg2.setTitle(titleStringId);
		
		////////////////////////////////
		
		// button: cancel
		
		////////////////////////////////
		Button btn_Ok = (Button) dlg2.findViewById(okButtonId);
		
		btn_Ok.setTag(okTag);
		
		btn_Ok.setOnTouchListener(new DB_OTL(actv, dlg1, dlg2));
		
		btn_Ok.setOnClickListener(new DB_OCL(actv, dlg1, dlg2));
		
		////////////////////////////////

		// button: cancel

		////////////////////////////////
		Button btn_cancel = (Button) dlg2.findViewById(cancelButtonId);
		
		btn_cancel.setTag(cancelTag);
		
		btn_cancel.setOnTouchListener(new DB_OTL(actv, dlg1, dlg2));
		
		btn_cancel.setOnClickListener(new DB_OCL(actv, dlg1, dlg2));
		
		//
		//dlg.show();
		
		return dlg2;
		
	}//public static Dialog dlg_template_okCancel()
	
	public static Dialog 
	dlg_Template_Cancel_SecondDialog
	(Activity actv, Dialog dlg1,
			int layoutId, String title,
			int cancelButtonId, Tags.DialogTags cancelTag) {
		/****************************
		 * Steps
		 * 1. Set up
		 * 2. Add listeners => OnTouch
		 * 3. Add listeners => OnClick
		 ****************************/
		
		// 
		Dialog dlg2 = new Dialog(actv);
		
		//
		dlg2.setContentView(layoutId);
		
		// Title
		dlg2.setTitle(title);
		
		/****************************
		 * 2. Add listeners => OnTouch
		 ****************************/
		//
		Button btn_cancel = (Button) dlg2.findViewById(cancelButtonId);
		
		//
		btn_cancel.setTag(cancelTag);
		
		//
		btn_cancel.setOnTouchListener(new DB_OTL(actv, dlg2));
		
		/****************************
		 * 3. Add listeners => OnClick
		 ****************************/
		//
		btn_cancel.setOnClickListener(new DB_OCL(actv, dlg1, dlg2));
		
		//
		//dlg.show();
		
		return dlg2;
		
	}//public static Dialog dlg_template_okCancel()
	
	public static void 
	conf_DropTable
	(Activity actv, Dialog dlg1, String tname) {
		// TODO Auto-generated method stub
		
		Dialog dlg2 = new Dialog(actv);
		
		//
		dlg2.setContentView(R.layout.dlg_tmpl_confirm_simple);
		
		// Title
		dlg2.setTitle(R.string.generic_tv_confirm);
		
		////////////////////////////////

		// set: message

		////////////////////////////////
		TextView tv_Message = (TextView) dlg2.findViewById(
								R.id.dlg_tmpl_confirm_simple_tv_message);
		
		tv_Message.setText(actv.getString(
					R.string.dlg_db_admin_item_drop_table));

		////////////////////////////////
		
		// set: item name
		
		////////////////////////////////
		TextView tv_ItemName = (TextView) dlg2.findViewById(
				R.id.dlg_tmpl_confirm_simple_tv_item_name);
		
		tv_ItemName.setText(tname);
		
		////////////////////////////////

		// button: cancel

		////////////////////////////////
		Button btn_Cancel = (Button) dlg2.findViewById(
						R.id.dlg_tmpl_confirm_simple_btn_cancel);
		
		//
//		btn_Cancel.setTag(Tags.DialogTags.dlg_generic_dismiss_second_dialog);
		btn_Cancel.setTag(Tags.DialogTags.GENERIC_DISMISS_SECOND_DIALOG);
		
		//
		btn_Cancel.setOnTouchListener(new DB_OTL(actv, dlg2));
		
		btn_Cancel.setOnClickListener(new DB_OCL(actv, dlg1, dlg2, tname));
		
		////////////////////////////////
		
		// button: ok
		
		////////////////////////////////
		Button btn_Ok = (Button) dlg2.findViewById(
				R.id.dlg_tmpl_confirm_simple_btn_ok);
		
		//
		btn_Ok.setTag(Tags.DialogTags.DROP_TABLE_OK);
		
		//
		btn_Ok.setOnTouchListener(new DB_OTL(actv, dlg2));
		
		btn_Ok.setOnClickListener(new DB_OCL(actv, dlg1, dlg2, tname));
		
		////////////////////////////////

		// show

		////////////////////////////////
		dlg2.show();
		
	}//conf_DropTable
	
	public static void
	dlg_ShowMessage(Activity actv, String message) {
		
		Dialog dlg = Methods_dlg.dlg_Template_Cancel(
				actv, R.layout.dlg_tmpl_toast_ok, 
				R.string.generic_tv_confirm, 
				R.id.dlg_tmpl_toast_ok_bt_cancel, 
//				R.id.dlg_db_admin_bt_cancel, 
				Tags.DialogTags.GENERIC_DISMISS);
		
		TextView tv_Message = 
				(TextView) dlg.findViewById(R.id.dlg_tmpl_toast_ok_tv_message);
		
		tv_Message.setText(message);
		
		dlg.show();
		
	}
	
	public static void
	dlg_ShowMessage
	(Activity actv, String message, int colorId) {
		
		Dialog dlg = Methods_dlg.dlg_Template_Cancel(
				actv, R.layout.dlg_tmpl_toast_ok, 
				R.string.generic_tv_confirm, 
				R.id.dlg_tmpl_toast_ok_bt_cancel, 
//				R.id.dlg_db_admin_bt_cancel, 
				Tags.DialogTags.GENERIC_DISMISS);
		
		TextView tv_Message = 
				(TextView) dlg.findViewById(R.id.dlg_tmpl_toast_ok_tv_message);
		
		tv_Message.setText(message);
		
		////////////////////////////////

		// background

		////////////////////////////////
		tv_Message.setBackgroundColor(actv.getResources().getColor(colorId));
		
		dlg.show();
		
	}//dlg_ShowMessage

	public static void
	dlg_ShowMessage_SecondDialog
	(Activity actv, String message, Dialog dlg1) {
		
		Dialog dlg2 = Methods_dlg.dlg_Template_Cancel_SecondDialog(
				actv, dlg1,
				R.layout.dlg_tmpl_toast_ok, 
				R.string.generic_tv_confirm, 
				
				R.id.dlg_tmpl_toast_ok_bt_cancel, 
				Tags.DialogTags.GENERIC_DISMISS_SECOND_DIALOG);
		
		TextView tv_Message = 
				(TextView) dlg2.findViewById(R.id.dlg_tmpl_toast_ok_tv_message);
		
		tv_Message.setText(message);
		
		dlg2.show();
		
	}

	public static void 
	dlg_AddMemo
	(Activity actv, long db_Id) {
		// TODO Auto-generated method stub
		
		////////////////////////////////

		// get: dlg

		////////////////////////////////
		Dialog dlg = Methods_dlg._dlg_AddMemo_GetDialog(actv, db_Id);

		/******************************
			validate: null
		 ******************************/
		if (dlg == null) {
			
			String msg = "dlg => null";
			Methods_dlg.dlg_ShowMessage(actv, msg);
			
			return;
			
		}
		
		// Log
		String msg_Log = "dlg => obtained";
		Log.d("Methods_dlg.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
//		////////////////////////////////
//
//		// gridview
//
//		////////////////////////////////
//		dlg = _dlg_AddMemo_Set_Gridview(actv, dlg);

		////////////////////////////////

		// listviews

		////////////////////////////////
		dlg = _dlg_AddMemo_Set_Listviews(actv, dlg);
		
		////////////////////////////////

		// show

		////////////////////////////////
		dlg.show();
		
	}//dlg_addMemo
	
	private static Dialog 
	_dlg_AddMemo_Set_Listviews
	(Activity actv, Dialog d) {
		// TODO Auto-generated method stub
		
		////////////////////////////////

		// lv 1

		////////////////////////////////
		ListView lv = (ListView) d.findViewById(R.id.dlg_add_memos_lv1);
		
		List<WordPattern> list_WP = Methods.get_WPList(actv);
		
		
		
		return d;
		
	}//_dlg_AddMemo_Set_Listviews

	public static Dialog 
	_dlg_AddMemo_GetDialog
	(Activity actv, long db_Id) {
		
		// 
		Dialog dlg = new Dialog(actv);
		
		//
		dlg.setContentView(R.layout.dlg_add_memos);
		
		// Title
		dlg.setTitle(R.string.dlg_add_memos_tv_title);
		
		/*----------------------------
		 * 1-2. Set text to edit text
			----------------------------*/
		TI ti = DBUtils.get_TI_From_DbId(actv, db_Id);
//		TI ti = DBUtils.get_TI_From_FileId(actv, db_Id);
		
		// Log
		String msg_Log = "ti.name => " + ti.getFile_name();
		Log.d("Methods_dlg.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		EditText et = (EditText) dlg.findViewById(R.id.dlg_add_memos_et_content);
		
		if (ti.getMemo() != null) {
			
			et.setText(ti.getMemo());
			
			et.setSelection(ti.getMemo().length());
			
		} else {//if (ti.getMemo() != null)
			
			et.setSelection(0);
			
		}//if (ti.getMemo() != null)
		
		////////////////////////////////

		// Add listeners: OnTouch

		////////////////////////////////
		
		Button btn_add = (Button) dlg.findViewById(R.id.dlg_add_memos_bt_add);
		Button btn_cancel = (Button) dlg.findViewById(R.id.dlg_add_memos_cancel);
		
//		Button btn_patterns = (Button) dlg.findViewById(R.id.dlg_add_memos_bt_patterns);
		
		// Tags
//		btn_add.setTag(DialogTags.dlg_add_memos_bt_add);
		btn_add.setTag(DialogTags.DLG_ADD_MEMOS_BT_ADD);
		btn_cancel.setTag(DialogTags.GENERIC_DISMISS);
//		btn_cancel.setTag(DialogTags.dlg_generic_dismiss);
		
//		btn_patterns.setTag(DialogTags.dlg_add_memos_bt_patterns);
//		btn_patterns.setTag(DialogTags.DLG_ADD_MEMOS_BT_PATTERNS);
		
		//
		btn_add.setOnTouchListener(new DB_OTL(actv, dlg));
		btn_cancel.setOnTouchListener(new DB_OTL(actv, dlg));
		
//		btn_patterns.setOnTouchListener(new DB_OTL(actv, dlg));
		
		////////////////////////////////

		// Add listeners => OnClick

		////////////////////////////////
		btn_add.setOnClickListener(new DB_OCL(actv, dlg, db_Id));
		btn_cancel.setOnClickListener(new DB_OCL(actv, dlg));
		
//		btn_patterns.setOnClickListener(new DB_OCL(actv, dlg));

		
		return dlg;
		
	}//public static Dialog dlg_addMemo(Activity actv, long file_id, String tableName)

//	/******************************
//		@return 
//		1. Cursor returned null => parameter dlg<br>
//		2. Cursor has no entry => parameter dlg<br>
//		
//	 ******************************/
//	public static Dialog 
//	_dlg_AddMemo_Set_Gridview
//	(Activity actv, Dialog dlg) {
//		////////////////////////////////
//
//		// setup: db, view
//
//		////////////////////////////////
//		GridView gv = (GridView) dlg.findViewById(R.id.dlg_add_memos_gv);
//		
//		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
//		
//		SQLiteDatabase rdb = dbu.getReadableDatabase();
//
//		////////////////////////////////
//
//		// Table exists?
//
//		////////////////////////////////
//		String tableName = CONS.DB.tname_MemoPatterns;
//		
//		boolean res = dbu.tableExists(rdb, tableName);
//		
//		if (res == true) {
//			
//			// Log
//			Log.d("Methods.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", "Table exists: " + tableName);
//			
//			rdb.close();
//			
////			return;
//			
//		} else {//if (res == false)
//			////////////////////////////////
//
//			// no table => creating one
//
//			////////////////////////////////
//			// Log
//			Log.d("Methods.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", "Table doesn't exist: " + tableName);
//			
//			rdb.close();
//			
//			SQLiteDatabase wdb = dbu.getWritableDatabase();
//			
//			res = dbu.createTable(
//							wdb, 
//							tableName, 
//							CONS.DB.col_names_MemoPatterns, 
//							CONS.DB.col_types_MemoPatterns);
//			
//			if (res == true) {
//				// Log
//				Log.d("Methods.java"
//						+ "["
//						+ Thread.currentThread().getStackTrace()[2]
//								.getLineNumber() + "]", "Table created: " + tableName);
//				
//				wdb.close();
//				
//			} else {//if (res == true)
//				// Log
////				Log.d("Methods.java"
////						+ "["
////						+ Thread.currentThread().getStackTrace()[2]
////								.getLineNumber() + "]", "Create table failed: " + tableName);
//				
//				String msg = "Create table failed: " + tableName;
//				Methods_dlg.dlg_ShowMessage(actv, msg);
//				
//				
//				wdb.close();
//				
//				return dlg;
//				
//			}//if (res == true)
//
//			
//		}//if (res == false)
//		
//		
//		////////////////////////////////
//
//		// Get cursor
//
//		////////////////////////////////
//		rdb = dbu.getReadableDatabase();
//		
////		String sql = "SELECT * FROM " + tableName + " ORDER BY word ASC";
////		
////		Cursor c = rdb.rawQuery(sql, null);
////		
////		actv.startManagingCursor(c);
//		
//		// "word"
//		String orderBy = CONS.DB.col_names_MemoPatterns_full[3] + " ASC"; 
//		
//		Cursor c = rdb.query(
//						CONS.DB.tname_MemoPatterns,
//						CONS.DB.col_names_MemoPatterns_full,
//		//				CONS.DB.col_types_refresh_log_full,
//						null, null,		// selection, args 
//						null, 			// group by
//						null, 		// having
//						orderBy);
//
//		actv.startManagingCursor(c);
//		
//		/******************************
//			validate: null
//		 ******************************/
//		if (c == null) {
//	
//			// Log
//			String msg_Log = "query => null";
//			Log.e("Methods.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", msg_Log);
//
//			rdb.close();
//			
//			return dlg;
//			
//		}
//		
//		/******************************
//			validate: any entry?
//		 ******************************/
//		if (c.getCount() < 1) {
//	
//			// Log
//			String msg_Log = "entry => < 1";
//			Log.e("Methods.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", msg_Log);
//			
//			rdb.close();
//			
//			return dlg;
//			
//		}
//
//		////////////////////////////////
//
//		// cursor: move to first
//
//		////////////////////////////////
//		c.moveToFirst();
//		
//		////////////////////////////////
//
//		// Get list
//
//		////////////////////////////////
////		CONS.IMageActv.patternList = new ArrayList<String>();
////		List<String> patternList = new ArrayList<String>();
//
//		List<WordPattern> patternList = new ArrayList<WordPattern>();
//		
//		WordPattern wp = null;
//		
//		if (c.getCount() > 0) {
//			
//			for (int i = 0; i < c.getCount(); i++) {
//				
////				CONS.IMageActv.patternList.add(c.getString(3));	// 3 => "word"
////				patternList.add(c.getString(3));	// 3 => "word"
////				patternList.add(c.getString(1));
//		
//				wp = new WordPattern.Builder()
//				.setDb_Id(c.getLong(0))
//				.setCreated_at(c.getString(1))
//				.setModified_at(c.getString(2))
//				.setWord(c.getString(3))
//				.build();
//	
//				patternList.add(wp);
//
//				c.moveToNext();
//				
//			}//for (int i = 0; i < patternList.size(); i++)
//			
//		} else {//if (c.getCount() > 0)
//			
//			// Log
//			Log.d("Methods.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", "!c.getCount() > 0");
//			
//		}//if (c.getCount() > 0)
//		
//		
////		Collections.sort(CONS.IMageActv.patternList);
//////		Collections.sort(patternList);
////
////		// Log
////		String msg_Log = "CONS.IMageActv.patternList.size() => " 
////						+ CONS.IMageActv.patternList.size();
//////		String msg_Log = "patternList => " + patternList.size();
////		Log.d("Methods_dlg.java" + "["
////				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
////				+ "]", msg_Log);
//
//		////////////////////////////////
//
//		// Adapter
//
//		////////////////////////////////
////		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
////		CONS.IMageActv.adp_ImageActv_GridView = new ArrayAdapter<String>(
////										actv,
////										R.layout.add_memo_grid_view,
////										CONS.IMageActv.patternList
//////										patternList
////										);
//		
//		Adp_WordPatterns adapter = new Adp_WordPatterns(
//				actv,
//				R.layout.add_memo_grid_view,
//				patternList
//				);
//
//		
////		gv.setAdapter(CONS.IMageActv.adp_ImageActv_GridView);
//		gv.setAdapter(adapter);
//
////		// Log
////		String msg_Log = "adapter.getCount() => " 
////					+ CONS.IMageActv.adp_ImageActv_GridView.getCount();
////		Log.d("Methods_dlg.java" + "["
////				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
////				+ "]", msg_Log);
//		
//		////////////////////////////////
//
//		// Set listener
//
//		////////////////////////////////
//////		gv.setTag(DialogTags.dlg_add_memos_gv);
//		gv.setTag(Tags.DialogItemTags.DLG_ADD_MEMOS_GV);
////		
//		gv.setOnItemClickListener(new DOI_CL(actv, dlg));
////		gv.setOnItemClickListener(new DialogOnItemClickListener(actv, dlg));
////		
////		
////		// Log
////		Log.d("Methods.java" + "["
////				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
////				+ "]", "GridView setup => Done");
//		
//		/*----------------------------
//		 * 8. Close db
//			----------------------------*/
//		rdb.close();
//		
//
//		////////////////////////////////
//
//		// return
//
//		////////////////////////////////
//		// Log
//		String msg_Log = "gridview => set";
//		Log.d("Methods_dlg.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_Log);
//		
////		// Log
////		msg_Log = "patternList => " + CONS.IMageActv.patternList.size();
//////		msg_Log = "patternList => " + patternList.size();
////		Log.d("Methods_dlg.java" + "["
////				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
////				+ "]", msg_Log);
//
//		// Log
//		msg_Log = "gv.getChildCount() => " + gv.getChildCount();
//		Log.d("Methods_dlg.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_Log);
//		
//		return dlg;
//		
//	}//public static Dialog dlg_addMemo(Activity actv, long file_id, String tableName)

	public static void 
	dlg_Create_Dir
	(Activity actv) {
		// TODO Auto-generated method stub
		
		Dialog dlg1 = new Dialog(actv);
		
		//
		dlg1.setContentView(R.layout.dlg_tmpl_edittext_simple);
		
		// Title
		dlg1.setTitle(R.string.dlg_create_dir_title);
//		dlg2.setTitle(titleStringId);
		
		////////////////////////////////

		// message

		////////////////////////////////
		TextView tv_Message = 
				(TextView) dlg1.findViewById(R.id.dlg_tmpl_edittext_simple_tv_message);
		
		tv_Message.setText(actv.getString(R.string.dlg_create_dir_message));
		
		/****************************
		 * 2. Add listeners => OnTouch
		 ****************************/
		//
		Button btn_ok = 
				(Button) dlg1.findViewById(R.id.dlg_tmpl_edittext_simple_btn_ok);
		Button btn_cancel = 
				(Button) dlg1.findViewById(R.id.dlg_tmpl_edittext_simple_btn_cancel);
		
		//
		btn_ok.setTag(Tags.DialogTags.DLG_CREATE_DIR_OK);
		btn_cancel.setTag(Tags.DialogTags.GENERIC_DISMISS);
		
		//
		btn_ok.setOnTouchListener(new DB_OTL(actv, dlg1));
		btn_cancel.setOnTouchListener(new DB_OTL(actv, dlg1));
		
		/****************************
		 * 3. Add listeners => OnClick
		 ****************************/
		//
		btn_ok.setOnClickListener(new DB_OCL(actv, dlg1));
		btn_cancel.setOnClickListener(new DB_OCL(actv, dlg1));
		
		
		////////////////////////////////

		// show

		////////////////////////////////
		dlg1.show();
		
	}//dlg_Create_Folder

	public static void 
	dlg_IsEmpty
	(Activity actv, Dialog dlg1) {
		// TODO Auto-generated method stub
		EditText et = (EditText) dlg1.findViewById(R.id.dlg_tmpl_edittext_simple_et);
//		EditText et = (EditText) dlg1.findViewById(R.id.dlg_create_folder_et);
		String folderName = et.getText().toString();
		
		//
		if (!folderName.equals("")) {
			/*----------------------------
			 * 2. If yes, go to Methods.createFolder()
				----------------------------*/
			Methods_dlg.dlg_CreateDir_Confirm(actv, dlg1);
			
			return;
			
		}//if (!folderName.equals(""))

		Dialog dlg2 = new Dialog(actv);
		
		//
		dlg2.setContentView(R.layout.dlg_input_empty);
		
		// Title
		dlg2.setTitle(R.string.generic_notice);
		
		/*----------------------------
		 * 2. Add listeners => OnTouch
			----------------------------*/
		//
		Button btn_ok = (Button) dlg2.findViewById(R.id.dlg_input_empty_btn_reenter);
//		Button btn_cancel = (Button) dlg2.findViewById(R.id.dlg_input_empty_btn_cancel);
		
		//
//		btn_ok.setTag(DialogTags.dlg_input_empty_reenter);
		btn_ok.setTag(DialogTags.GENERIC_DISMISS_SECOND_DIALOG);
//		btn_cancel.setTag(DialogTags.dlg_input_empty_cancel);
		
		//
		btn_ok.setOnTouchListener(new DB_OTL(actv, dlg2));
//		btn_cancel.setOnTouchListener(new DialogButtonOnTouchListener(actv, dlg2));
		
		/*----------------------------
		 * 3. Add listeners => OnClick
			----------------------------*/
		//
		btn_ok.setOnClickListener(new DB_OCL(actv, dlg1, dlg2));
//		btn_cancel.setOnClickListener(new DialogButtonOnClickListener(actv, dlg, dlg2));
		
		//
		dlg2.show();
		
	}//dlg_IsEmpty

	private static void 
	dlg_CreateDir_Confirm
	(Activity actv, Dialog dlg1) {
		// TODO Auto-generated method stub
		
		Dialog dlg2 = new Dialog(actv);
		
		//
		dlg2.setContentView(R.layout.dlg_confirm_create_folder);
		
		// Title
		dlg2.setTitle(R.string.generic_tv_confirm);
		
		/*----------------------------
		 * 2. Set folder name to text view
			----------------------------*/
		EditText et = (EditText) dlg1.findViewById(R.id.dlg_tmpl_edittext_simple_et);
//		EditText et = (EditText) dlg1.findViewById(R.id.dlg_create_folder_et);
		
		TextView tv = (TextView) dlg2.findViewById(
							R.id.dlg_confirm_create_folder_tv_table_name);
		
		// Log
		String msg_Log = "et => " + et;
		Log.d("Methods_dlg.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		tv.setText(et.getText().toString());
		
		/*----------------------------
		 * 3. Add listeners => OnTouch
			----------------------------*/
		//
		Button btn_ok = (Button) dlg2.findViewById(
							R.id.dlg_confirm_create_folder_btn_ok);
		
		Button btn_cancel = (Button) dlg2.findViewById(
							R.id.dlg_confirm_create_folder_btn_cancel);
		
		//
//		btn_ok.setTag(DialogTags.dlg_confirm_create_folder_ok);
		btn_ok.setTag(DialogTags.DLG_CONFIRM_CREATE_FOLDER_OK);
//		btn_cancel.setTag(DialogTags.dlg_confirm_create_folder_cancel);
//		btn_cancel.setTag(DialogTags.DLG_CONFIRM_CREATE_FOLDER_CANCEL);
//		btn_cancel.setTag(DialogTags.dlg_generic_dismiss_second_dialog);
		btn_cancel.setTag(DialogTags.GENERIC_DISMISS_SECOND_DIALOG);
		
		//
		btn_ok.setOnTouchListener(new DB_OTL(actv, dlg2));
		btn_cancel.setOnTouchListener(new DB_OTL(actv, dlg2));
		
		/*----------------------------
		 * 4. Add listeners => OnClick
			----------------------------*/
		//
		btn_ok.setOnClickListener(new DB_OCL(actv, dlg1, dlg2));
		btn_cancel.setOnClickListener(new DB_OCL(actv, dlg1, dlg2));
		
		/*----------------------------
		 * 5. Show dialog
			----------------------------*/
		dlg2.show();
		
	}//dlg_CreateDir_Confirmed

	public static void 
	dlg_ACTV_MAIN_LV
	(Activity actv, String item) {
		// TODO Auto-generated method stub
		
		Dialog dlg1 = Methods_dlg.dlg_Template_Cancel(
				actv,
				R.layout.dlg_tmpl_cancel_lv,
				item,
				
				R.id.dlg_tmpl_cancel_lv_bt_cancel,
				Tags.DialogTags.GENERIC_DISMISS);
		
		/****************************
		* 2. Prep => List
		****************************/
		String[] choices = {
					actv.getString(R.string.dlg_actvmain_lv_delete),
					};
		
		List<String> list = new ArrayList<String>();
		
		for (String choice : choices) {
		
			list.add(choice);
		
		}
		
		/****************************
		* 3. Adapter
		****************************/
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
		actv,
		//R.layout.dlg_db_admin,
		R.layout.list_row_simple_1,
		//android.R.layout.simple_list_item_1,
		list
		);
		
		/****************************
		* 4. Set adapter
		****************************/
		ListView lv = (ListView) dlg1.findViewById(R.id.dlg_tmpl_cancel_lv_lv);
		
		lv.setAdapter(adapter);
		
		/****************************
		* 5. Set listener to list
		****************************/
		lv.setTag(Tags.DialogItemTags.DLG_ACTVMAIN_LONGCLICK);
		
		lv.setOnItemClickListener(new DOI_CL(actv, dlg1, item));
		
		/***************************************
		* Modify the list view height
		***************************************/
		lv.setLayoutParams(
				new LinearLayout.LayoutParams(
						300,	//	Width
						LayoutParams.WRAP_CONTENT	//	Height
				));
		
		/***************************************
		* Modify: Button layout
		***************************************/
		LinearLayout llButton =
					(LinearLayout) dlg1.findViewById(R.id.dlg_tmpl_cancel_lv_ll_filepath);
//		(LinearLayout) dlg1.findViewById(R.id.actv_imp_ll_filepath);
		
		LinearLayout.LayoutParams params =
				new LinearLayout.LayoutParams(
								LayoutParams.WRAP_CONTENT,
								LayoutParams.WRAP_CONTENT);
		
		params.gravity = Gravity.CENTER_HORIZONTAL;
		
		llButton.setLayoutParams(params);
		
		/****************************
		* 6. Show dialog
		****************************/
		dlg1.show();
		
	}//dlg_ACTV_MAIN_LV

	public static Dialog 
	dlg_Template_Cancel
	(Activity actv,
			int layoutId, String title, 
			int cancelButtonId, DialogTags cancelTag) {
		// TODO Auto-generated method stub
		
		Dialog dlg = new Dialog(actv);
		
		//
		dlg.setContentView(layoutId);
		
		// Title
		dlg.setTitle(title);
		
		/****************************
		* 2. Add listeners => OnTouch
		****************************/
		//
		Button btn_cancel = (Button) dlg.findViewById(cancelButtonId);
		
		//
		btn_cancel.setTag(cancelTag);
		
		//
		btn_cancel.setOnTouchListener(new DB_OTL(actv, dlg));
		
		/****************************
		* 3. Add listeners => OnClick
		****************************/
		//
		btn_cancel.setOnClickListener(new DB_OCL(actv, dlg));
		
		//
		//dlg.show();
		
		return dlg;		
	}//dlg_Template_Cancel

	public static void 
	conf_Delete_Folder
	(Activity actv, Dialog dlg1,
			String folderName, String dlg1_Choice) {
		// TODO Auto-generated method stub
		
		Dialog dlg2 = new Dialog(actv);

		// layout
//		dlg2.setContentView(R.layout.dlg_tmpl_confirm_simple);
		dlg2.setContentView(R.layout.dlg_tmpl_confirm_simple);
		
		// Title
		dlg2.setTitle(R.string.generic_tv_confirm);

		////////////////////////////////

		// Set: Message

		////////////////////////////////
		TextView tv_Message = (TextView) dlg2.findViewById(
//							R.id.dlg_tmpl_confirm_simple_tv_message);
							R.id.dlg_tmpl_confirm_simple_tv_message);
//							R.id.dlg_tmpl_confirm_simple_cb_tv_message);
		
//		tv_Message.setText(actv.getString(R.string.dlg_conf_delete_bm_tv_message));
		tv_Message.setText(actv.getString(
								R.string.dlg_actvmain_lv_delete_confirm_message));

		////////////////////////////////

		// Set: Folder name

		////////////////////////////////
		TextView tv_ItemName = (TextView) dlg2.findViewById(
							R.id.dlg_tmpl_confirm_simple_tv_item_name);
//							R.id.dlg_tmpl_confirm_simple_cb_tv_item_name);

		tv_ItemName.setText(folderName);

		////////////////////////////////

		// Add listeners => OnTouch

		////////////////////////////////
		Button btn_ok = (Button) dlg2.findViewById(
								R.id.dlg_tmpl_confirm_simple_btn_ok);
		
		Button btn_cancel = (Button) dlg2.findViewById(
								R.id.dlg_tmpl_confirm_simple_btn_cancel);
		
		//
		btn_ok.setTag(Tags.DialogTags.DLG_DELETE_FOLDER_CONF_OK);
		btn_cancel.setTag(Tags.DialogTags.GENERIC_DISMISS_SECOND_DIALOG);
		
		//
		btn_ok.setOnTouchListener(new DB_OTL(actv, dlg2));
		btn_cancel.setOnTouchListener(new DB_OTL(actv, dlg2));
		
		/****************************
		 * 4. Add listeners => OnClick
			****************************/
		//
		btn_ok.setOnClickListener(
					new DB_OCL(actv, dlg1, dlg2, folderName));
		btn_cancel.setOnClickListener(new DB_OCL(actv, dlg1, dlg2));
		
		/****************************
		 * 5. Show dialog
			****************************/
		dlg2.show();
		
	}//conf_DeleteFolder

	public static void 
	dlg_MoveFiles
	(Activity actv) {
		// TODO Auto-generated method stub

		////////////////////////////////

		// dialog

		////////////////////////////////
		Dialog dlg1 = Methods_dlg.dlg_Template_Cancel(
				actv, R.layout.dlg_tmpl_list_cancel, 
				R.string.dlg_move_files_title, 
				R.id.dlg_tmpl_list_cancel_bt_cancel, 
//				R.id.dlg_db_admin_bt_cancel, 
				Tags.DialogTags.GENERIC_DISMISS);

		////////////////////////////////

		// Prep => List

		////////////////////////////////
		String[] choices = {
		
			actv.getString(R.string.dlg_move_files_item_folder),
			actv.getString(R.string.dlg_move_files_item_remote),
		
		};
		
		List<String> list = new ArrayList<String>();
		
		for (String item : choices) {
		
			list.add(item);
		
		}
		
		////////////////////////////////

		// Adapter

		////////////////////////////////
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				
					actv,
					R.layout.list_row_simple_1,
					list
					
		);
		
		/****************************
		* 4. Set adapter
		****************************/
		ListView lv = (ListView) dlg1.findViewById(R.id.dlg_tmpl_list_cancel_lv);
		
		lv.setAdapter(adapter);
		
		/****************************
		* 5. Set listener to list
		****************************/
//		lv.setTag(Tags.DialogItemTags.dlg_move_files);
		lv.setTag(Tags.DialogItemTags.DLG_MOVE_FILES);
		
		lv.setOnItemClickListener(new DOI_CL(actv, dlg1));
		
		/****************************
		* 6. Show dialog
		****************************/
		dlg1.show();
		
	}

	public static void 
	dlg_MoveFiles__Folder
	(Activity actv, Dialog dlg1) {
		// TODO Auto-generated method stub
		
		////////////////////////////////

		// dialog

		////////////////////////////////
		Dialog dlg2 = Methods_dlg.dlg_Template_Cancel_SecondDialog(
				actv, dlg1,
				R.layout.dlg_tmpl_list_cancel_2, 
				R.string.dlg_move_files_title_folder, 
				R.id.dlg_tmpl_list_cancel_2_bt_cancel, 
//				R.id.dlg_db_admin_bt_cancel, 
				Tags.DialogTags.GENERIC_DISMISS_SECOND_DIALOG);

		////////////////////////////////

		// Prep => List

		////////////////////////////////
		boolean res = _dlg_MoveFiles__Folder__BuildList(actv);

		if (res == false) {
			
			String msg = "Can't build folders list";
			Methods_dlg.dlg_ShowMessage(actv, msg, R.color.red);
			
			return;
		}
		
		////////////////////////////////

		// set: pref: current path

		////////////////////////////////
		res = 
				Methods.set_Pref_String(
							actv, 
							CONS.Pref.pname_MainActv, 
							CONS.Pref.pkey_TNActv__CurPath_Move, 
							CONS.DB.tname_IFM11);

		////////////////////////////////

		// Adapter

		////////////////////////////////
		CONS.TNActv.adp_DirList = new ArrayAdapter<String>(
				
					actv,
					R.layout.list_row_simple_1,
					CONS.TNActv.dir_List
//					list
					
		);
		
		/****************************
		* 4. Set adapter
		****************************/
		ListView lv = (ListView) dlg2.findViewById(R.id.dlg_tmpl_list_cancel_2_lv);
		
		lv.setAdapter(CONS.TNActv.adp_DirList);
//		lv.setAdapter(adapter);
		
		////////////////////////////////

		// Set listener to list

		////////////////////////////////
		// Item click
//		lv.setTag(Tags.DialogItemTags.dlg_move_files);
		lv.setTag(Tags.DialogItemTags.DLG_MOVE_FILES_FOLDER);
		
		lv.setOnItemClickListener(new DOI_CL(actv, dlg1, dlg2));

		// Long click
		lv.setOnItemLongClickListener(new DLOI_LCL(actv, dlg1, dlg2));
//		lv.setOnItemLongClickListener(new LOI_LCL(actv, dlg1, dlg2));
		
		/****************************
		* 6. Show dialog
		****************************/
		dlg2.show();
		
	}//dlg_MoveFiles__Folder

	private static boolean 
	_dlg_MoveFiles__Folder__BuildList
	(Activity actv) {
		// TODO Auto-generated method stub
		
		List<String> list = new ArrayList<String>();
		
//		////////////////////////////////
//
//		// set: pref: current path
//
//		////////////////////////////////
//		boolean res = 
//				Methods.set_Pref_String(
//							actv, 
//							CONS.Pref.pname_MainActv, 
//							CONS.Pref.pkey_TNActv__CurPath_Move, 
//							CONS.DB.tname_IFM11);
//		
		////////////////////////////////

		// get: top directory

		////////////////////////////////
		String currentPath = StringUtils.join(
				new String[]{
						CONS.Paths.dpath_Storage_Sdcard, 
						CONS.Paths.dname_Base},
				File.separator);

		if (currentPath == null) {
			
//			String msg = "Can't get the current path: " + currentPath;
//			Methods_dlg.dlg_ShowMessage(actv, msg);
			
			return false;
			
		}
		
		//CONS.ALActv.dir_List = Methods.get_DirList(currentPath);
		List<String> dir_List = Methods.get_DirList(currentPath);
		CONS.TNActv.dir_List = new ArrayList<String>();
		
		for (String dirName : dir_List) {
		//	for (String dirName : CONS.ALActv.dir_List) {
			
			CONS.TNActv.dir_List.add(
							CONS.Paths.dname_Base + File.separator + dirName);
		//	dirName = CONS.DB.tname_CM7 + File.separator + dirName;
			
		}
		
		// Log
		String msg_Log = "dir list => modified";
		Log.d("Methods_dlg.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		CONS.TNActv.dir_List.add(CONS.Admin.dirString_UpperDir);		
		
		////////////////////////////////

		// return

		////////////////////////////////
		return true;
		
//		return list;
		
	}//_dlg_MoveFiles__Folder__BuildList

	public static void 
	conf_MoveFiles__Folder
	(Activity actv, 
		Dialog dlg1, Dialog dlg2, String choice) {
		// TODO Auto-generated method stub
		
		////////////////////////////////

		// dialog

		////////////////////////////////
		Dialog dlg3 = 
				Methods_dlg.dlg_Tmpl_OkCancel_ThirdDialog(
						actv, 
						R.layout.dlg_tmpl_confirm_simple, 
						R.string.generic_tv_confirm, 
						
						R.id.dlg_tmpl_confirm_simple_btn_ok, 
						R.id.dlg_tmpl_confirm_simple_btn_cancel, 
						
						Tags.DialogTags.DLG_CONF_MOVE_FILES_FOLDER_OK, 
						Tags.DialogTags.GENERIC_DISMISS_THIRD_DIALOG, 
						
						dlg1, dlg2);
		
		////////////////////////////////

		// view: message

		////////////////////////////////
		TextView tv_Msg = 
				(TextView) dlg3.findViewById(R.id.dlg_tmpl_confirm_simple_tv_message);
		
		tv_Msg.setText(actv.getString(
								R.string.dlg_move_files_confirm_move_to_folder_msg));
		
		////////////////////////////////

		// view: item name

		////////////////////////////////
		TextView tv_ItemName = 
				(TextView) dlg3.findViewById(R.id.dlg_tmpl_confirm_simple_tv_item_name);
//		dlg_tmpl_confirm_simple_tv_message
		
		tv_ItemName.setText(choice);
		
		////////////////////////////////

		// show

		////////////////////////////////
		dlg3.show();
		
	}//conf_MoveFiles__Folder
	
	public static void 
	conf_MoveFiles__Folder_Top
	(Activity actv, 
			Dialog dlg1, Dialog dlg2) {
		// TODO Auto-generated method stub
		
		////////////////////////////////
		
		// dialog
		
		////////////////////////////////
		Dialog dlg3 = 
				Methods_dlg.dlg_Tmpl_OkCancel_ThirdDialog(
						actv, 
						R.layout.dlg_tmpl_confirm_simple, 
						R.string.generic_tv_confirm, 
						
						R.id.dlg_tmpl_confirm_simple_btn_ok, 
						R.id.dlg_tmpl_confirm_simple_btn_cancel, 
						
						Tags.DialogTags.DLG_CONF_MOVE_FILES_FOLDER_TOP_OK, 
						Tags.DialogTags.GENERIC_DISMISS_THIRD_DIALOG, 
						
						dlg1, dlg2);
		
		////////////////////////////////
		
		// view: message
		
		////////////////////////////////
		TextView tv_Msg = 
				(TextView) dlg3.findViewById(R.id.dlg_tmpl_confirm_simple_tv_message);
		
		tv_Msg.setText(actv.getString(
				R.string.dlg_move_files_confirm_move_to_flolder_top_msg));
		
		////////////////////////////////
		
		// view: item name
		
		////////////////////////////////
		TextView tv_ItemName = 
				(TextView) dlg3.findViewById(R.id.dlg_tmpl_confirm_simple_tv_item_name);
//		dlg_tmpl_confirm_simple_tv_message
		
		tv_ItemName.setText(CONS.Paths.dname_Base);
		
		////////////////////////////////
		
		// show
		
		////////////////////////////////
		dlg3.show();
		
	}//conf_MoveFiles__Folder

	public static
	Dialog dlg_Tmpl_OkCancel_ThirdDialog
	(Activity actv, 
		int layoutId, int titleStringId,
		
		int okButtonId, int cancelButtonId,
		Tags.DialogTags okTag, Tags.DialogTags cancelTag,
		
		Dialog dlg1, Dialog dlg2) {
		/****************************
		 * Steps
		 * 1. Set up
		 * 2. Add listeners => OnTouch
		 * 3. Add listeners => OnClick
		 ****************************/
		
		// 
		Dialog dlg3 = new Dialog(actv);
		
		//
		dlg3.setContentView(layoutId);
		
		// Title
		dlg3.setTitle(titleStringId);
		
		/****************************
		 * 2. Add listeners => OnTouch
		 ****************************/
		//
		Button btn_ok = (Button) dlg3.findViewById(okButtonId);
		Button btn_cancel = (Button) dlg3.findViewById(cancelButtonId);
		
		//
		btn_ok.setTag(okTag);
		btn_cancel.setTag(cancelTag);
		
		//
		btn_ok.setOnTouchListener(new DB_OTL(actv, dlg3));
		btn_cancel.setOnTouchListener(new DB_OTL(actv, dlg3));
		
		/****************************
		 * 3. Add listeners => OnClick
		 ****************************/
		//
		btn_ok.setOnClickListener(new DB_OCL(actv, dlg1, dlg2, dlg3));
		btn_cancel.setOnClickListener(new DB_OCL(actv, dlg1, dlg2, dlg3));
		
		
		return dlg3;
		
	}//public static Dialog dlg_template_okCancel_SecondDialog()

	public static void 
	dlg_SeratchItem
	(Activity actv) {
		/*----------------------------
		 * Steps
		 * 1. Dialog
		 * 9. Show
			----------------------------*/
		Dialog dlg = dlg_Tmpl_OkCancel(actv,
				R.layout.dlg_search, R.string.dlg_search_title,
				
				R.id.dlg_search_bt_ok, R.id.dlg_search_cancel,
				DialogTags.DLG_SEARCH_OK, DialogTags.GENERIC_DISMISS);

		/*----------------------------
		 * 9. Show
			----------------------------*/
		dlg.show();
		
	}//public static void dlg_seratchItem(Activity actv)

	public static Dialog 
	dlg_Tmpl_OkCancel
	(Activity actv, 
		int layoutId, int titleStringId,
		int okButtonId, int cancelButtonId, 
		DialogTags okTag, DialogTags cancelTag) {
		/*----------------------------
		* Steps
		* 1. Set up
		* 2. Add listeners => OnTouch
		* 3. Add listeners => OnClick
		----------------------------*/
		
		// 
		Dialog dlg = new Dialog(actv);
		
		//
		dlg.setContentView(layoutId);
		
		// Title
		dlg.setTitle(titleStringId);
		
		/*----------------------------
		* 2. Add listeners => OnTouch
		----------------------------*/
		//
		Button btn_ok = (Button) dlg.findViewById(okButtonId);
		Button btn_cancel = (Button) dlg.findViewById(cancelButtonId);
		
		//
		btn_ok.setTag(okTag);
		btn_cancel.setTag(cancelTag);
		
		//
		btn_ok.setOnTouchListener(new DB_OTL(actv, dlg));
		btn_cancel.setOnTouchListener(new DB_OTL(actv, dlg));
		
		/*----------------------------
		* 3. Add listeners => OnClick
		----------------------------*/
		//
		btn_ok.setOnClickListener(new DB_OCL(actv, dlg));
		btn_cancel.setOnClickListener(new DB_OCL(actv, dlg));
		
		//
		//dlg.show();
		
		return dlg;
	
	}//public static Dialog dlg_template_okCancel()

	public static void 
	dlg_TNActv_LongClick
	(Activity actv, TI ti) {
		// TODO Auto-generated method stub
		
		Dialog dlg1 = Methods_dlg.dlg_Template_Cancel(
				actv, R.layout.dlg_tmpl_cancel_lv, 
				R.string.generic_tv_menu, 
				R.id.dlg_tmpl_cancel_lv_bt_cancel, 
				Tags.DialogTags.GENERIC_DISMISS);

		/*----------------------------
		* 2. Prep => List
		----------------------------*/
		String[] choices = {
				actv.getString(R.string.generic_tv_edit),
				actv.getString(R.string.generic_tv_delete),
				actv.getString(R.string.generic_tv_upload),
		};
		
		List<String> list = new ArrayList<String>();
		
		for (String item : choices) {
		
			list.add(item);
		
		}
		
		/*----------------------------
		* 3. Adapter
		----------------------------*/
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
						actv,
						//R.layout.dlg_db_admin,
						R.layout.list_row_simple_1,
//						android.R.layout.simple_list_item_1,
						list
		);
		
		/*----------------------------
		* 4. Set adapter
		----------------------------*/
		ListView lv = (ListView) dlg1.findViewById(R.id.dlg_tmpl_cancel_lv_lv);
		
		lv.setAdapter(adapter);
		
		/*----------------------------
		* 5. Set listener to list
		----------------------------*/
		lv.setTag(Tags.DialogItemTags.DLG_ACTV_TN_LONG_CLICK);
		
		lv.setOnItemClickListener(new DOI_CL(actv, dlg1, ti));
		
		/*----------------------------
		* 6. Show dialog
		----------------------------*/
		dlg1.show();
		
		
	}//dlg_TNActv_LongClick

	public static void
	conf_Delete_TI
	(Activity actv, Dialog dlg1, TI ti) {
		// TODO Auto-generated method stub
		// 
		Dialog dlg2 = new Dialog(actv);
		
		//
		dlg2.setContentView(R.layout.dlg_tmpl_confirm_simple_cb);
		
		// Title
		dlg2.setTitle(R.string.generic_tv_confirm);
		
		////////////////////////////////

		// Set: Message

		////////////////////////////////
		TextView tvMessage = 
				(TextView) dlg2.findViewById(R.id.dlg_tmpl_confirm_simple_cb_tv_message);
		
//		tvMessage.setText("このアイテムを削除しますか？");
		tvMessage.setText("このアイテムを削除しますか？");
		
		////////////////////////////////

		// Set folder name to text view

		////////////////////////////////
		TextView tv = 
				(TextView) dlg2.findViewById(R.id.dlg_tmpl_confirm_simple_cb_tv_item_name);
		
		tv.setText(ti.getFile_name());
		
		/*----------------------------
		 * 3. Add listeners => OnTouch
			----------------------------*/
		//
		Button btn_ok = (Button) dlg2.findViewById(R.id.dlg_tmpl_confirm_simple_cb_btn_ok);
		Button btn_cancel = (Button) dlg2.findViewById(R.id.dlg_tmpl_confirm_simple_cb_btn_cancel);
		
		//
		btn_ok.setTag(DialogTags.DLG_DELETE_TI_CONF_OK);
		btn_cancel.setTag(DialogTags.GENERIC_DISMISS_SECOND_DIALOG);
		
		//
		btn_ok.setOnTouchListener(new DB_OTL(actv, dlg1, dlg2));
		btn_cancel.setOnTouchListener(new DB_OTL(actv, dlg1, dlg2));
		
		/*----------------------------
		 * 4. Add listeners => OnClick
			----------------------------*/
		//
		btn_ok.setOnClickListener(new DB_OCL(actv, dlg1, dlg2, ti));
		btn_cancel.setOnClickListener(new DB_OCL(actv, dlg1, dlg2));
		
		/*----------------------------
		 * 5. Show dialog
			----------------------------*/
		dlg2.show();

		
	}//conf_DeleteTI

	public static void
	dlg_EditTI(Activity actv, Dialog dlg1, TI ti) {
		// TODO Auto-generated method stub

		Dialog dlg2 = new Dialog(actv);
		
		//
		dlg2.setContentView(R.layout.dlg_edit_ti);
		
		// Title
		dlg2.setTitle(R.string.dlg_edit_ti_title);

		////////////////////////////////

		// view: buttons

		////////////////////////////////
		Button btn_ok = (Button) dlg2.findViewById(R.id.dlg_edit_ti_bt_ok);
		Button btn_cancel = (Button) dlg2.findViewById(R.id.dlg_edit_ti_bt_cancel);
		
		////////////////////////////////

		// listener: ontouch

		////////////////////////////////
//		btn_ok.setTag(DialogTags.dlg_edit_ti_bt_ok);
		btn_ok.setTag(DialogTags.DLG_EDIT_TI_BT_OK);
		btn_cancel.setTag(DialogTags.GENERIC_DISMISS_SECOND_DIALOG);
		
		//
		btn_ok.setOnTouchListener(new DB_OTL(actv, dlg1, dlg2));
		btn_cancel.setOnTouchListener(new DB_OTL(actv, dlg1, dlg2));
		
		////////////////////////////////

		// listener: onclick

		////////////////////////////////
		btn_ok.setOnClickListener(new DB_OCL(actv, dlg1, dlg2, ti));
		btn_cancel.setOnClickListener(new DB_OCL(actv, dlg1, dlg2));

//		Dialog dlg2 = dlg_template_okCancel_2Dialogues_TI(
//					actv,
//					R.layout.dlg_edit_ti,
//					R.string.dlg_edit_ti_title,
//					
//					R.id.dlg_edit_ti_bt_ok,
//					R.id.dlg_edit_ti_bt_cancel,
//					
//					DialogTags.dlg_edit_ti_bt_ok,
//					DialogTags.GENERIC_DISMISS_SECOND_DIALOG,
//					
//					dlg1,
//					ti);
		
		/***************************************
		 * Set: Texts
		 ***************************************/
		EditText etFileName = (EditText) dlg2.findViewById(
							R.id.dlg_edit_ti_et_file_name);
		
		etFileName.setText(ti.getFile_name());
		
		EditText etFilePath = (EditText) dlg2.findViewById(
				R.id.dlg_edit_ti_et_file_path);
		
		etFilePath.setText(ti.getFile_path());
		
		EditText etMemos = (EditText) dlg2.findViewById(
				R.id.dlg_edit_ti_et_memos);
		
		etMemos.setText(ti.getMemo());
		
		TextView tvTableName = (TextView) dlg2.findViewById(
				R.id.dlg_edit_ti_tv_table_name_value);
		
		tvTableName.setText(ti.getTable_name());
		
		////////////////////////////////

		// show

		////////////////////////////////
		dlg2.show();
		
	}//dlg_editTI(Activity actv, Dialog dlg1, TI ti)

	public static void 
	dlg_patterns
	(Activity actv) {
		/*----------------------------
		 * memo
			----------------------------*/
		Dialog dlg1 = Methods_dlg.dlg_Template_Cancel(
				actv, 
				R.layout.dlg_tmpl_cancel_lv, 
				R.string.dlg_memo_patterns_title, 
				
				R.id.dlg_tmpl_cancel_lv_bt_cancel, 
				Tags.DialogTags.GENERIC_DISMISS);
//				.dlg_template_cancel(
//						actv, R.layout.dlg_admin_patterns, 
//						R.string.dlg_memo_patterns_title, 
//						R.id.dlg_admin_patterns_bt_cancel, 
//						Tags.DialogTags.dlg_generic_dismiss);
//		Dialog dlg = Methods_dlg.dlg_template_cancel(
//				actv, R.layout.dlg_admin_patterns, 
//				R.string.dlg_memo_patterns_title, 
//				R.id.dlg_admin_patterns_bt_cancel, 
//				Tags.DialogTags.dlg_generic_dismiss);
		
		/*----------------------------
		 * 2. Prep => List
			----------------------------*/
		String[] choices = {
				actv.getString(R.string.generic_tv_register),
				actv.getString(R.string.generic_tv_edit),
				actv.getString(R.string.generic_tv_delete)
		};
		
		List<String> list = new ArrayList<String>();
		
		for (String item : choices) {
			
			list.add(item);
			
		}
		
		/*----------------------------
		 * 3. Adapter
			----------------------------*/
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				actv,
//				R.layout.dlg_db_admin,
				R.layout.list_row_simple_1,
//				android.R.layout.simple_list_item_1,
				list
				);

		/*----------------------------
		 * 4. Set adapter
			----------------------------*/
		ListView lv = (ListView) dlg1.findViewById(R.id.dlg_tmpl_cancel_lv_lv);
		
		lv.setAdapter(adapter);

		/*----------------------------
		 * 5. Set listener to list
			----------------------------*/
//		lv.setTag(Tags.DialogItemTags.dlg_admin_patterns_lv);
		lv.setTag(Tags.DialogItemTags.DLG_ADMIN_PATTERNS_LV);
		
		lv.setOnItemClickListener(new DOI_CL(actv, dlg1));
//		lv.setOnItemClickListener(new DialogOnItemClickListener(actv, dlg));
		
		/*----------------------------
		 * 6. Show dialog
			----------------------------*/
		dlg1.show();
		
	}//public static void dlg_patterns(Activity actv)

	public static void 
	dlg_RegisterPatterns
	(Activity actv, Dialog dlg1) {
		/*----------------------------
		 * Steps
		 * 1. Dialog
		 * 9. Show
			----------------------------*/
		Dialog dlg2 = new Dialog(actv);
		
		//
		dlg2.setContentView(R.layout.dlg_register_patterns);
		
		// Title
		dlg2.setTitle(R.string.dlg_register_patterns_title);
		
		/*----------------------------
		* 2. Add listeners => OnTouch
		----------------------------*/
		//
		Button btn_ok = (Button) dlg2.findViewById(R.id.dlg_register_patterns_btn_create);
		Button btn_cancel = (Button) dlg2.findViewById(R.id.dlg_register_patterns_btn_cancel);
		
		//
//		btn_ok.setTag(DialogTags.dlg_register_patterns_register);
		btn_ok.setTag(DialogTags.DLG_REGISTER_PATTERNS_REGISTER);
		btn_cancel.setTag(DialogTags.GENERIC_DISMISS_SECOND_DIALOG);
		
		//
		btn_ok.setOnTouchListener(new DB_OTL(actv, dlg1, dlg2));
		btn_cancel.setOnTouchListener(new DB_OTL(actv, dlg1, dlg2));
		
		/*----------------------------
		* 3. Add listeners => OnClick
		----------------------------*/
		//
		btn_ok.setOnClickListener(new DB_OCL(actv, dlg1, dlg2));
		btn_cancel.setOnClickListener(new DB_OCL(actv, dlg1, dlg2));
		
		/*----------------------------
		 * 9. Show
			----------------------------*/
		dlg2.show();
		
	}//dlg_register_patterns

	public static void 
	dlg_register_patterns_isInputEmpty
	(Activity actv, Dialog dlg, Dialog dlg2) {
		/*----------------------------
		 * Steps
		 * 1. Get views
		 * 2. Prepare data
		 * 3. Register data
		 * 4. Dismiss dialog
			----------------------------*/
		// Get views
		EditText et_word = (EditText) dlg2.findViewById(R.id.dlg_register_patterns_et_word);
		EditText et_table_name = 
					(EditText) dlg2.findViewById(R.id.dlg_register_patterns_et_table_name);
		
		if (et_word.getText().length() == 0) {
//			// debug
//			Toast.makeText(actv, "語句を入れてください", 3000).show();
			
			String msg = actv.getString(R.string.dlg_register_patterns_tv_prompot_input);
			Methods_dlg.dlg_ShowMessage(actv, msg, R.color.gold2);
			
			return;
		}// else {//if (et_column_name.getText().length() == 0)
		
		/*----------------------------
		 * 2. Prepare data
			----------------------------*/
		//
		String pattern = et_word.getText().toString();
		String table_name = et_table_name.getText().toString();
		
		/*----------------------------
		 * 3. Register data
			----------------------------*/
		int result = DBUtils.insert_Data_Patterns_single(actv, pattern);
		
		/*----------------------------
		 * 4. Dismiss dialog
			----------------------------*/
		if (result >= 1) {
		
			dlg.dismiss();
			dlg2.dismiss();
			
			String msg = "Pattern => stored: " + pattern;
			Methods_dlg.dlg_ShowMessage(actv, msg);
			
//			// debug
//			Toast.makeText(actv, "定型句を保管しました", Toast.LENGTH_LONG).show();
			
		} else {//if (result == true)

			String msg = null;
//			-1 => Table doesn't exist
//					-2 => Insertion failed
//					-3 => Exception

			switch(result) {
			
			case -1: msg = "Table doesn't exist";
				
				break;
				
			case -2: msg = "Insertion failed";
				
				break;
				
			case -3: msg = "Exception";
				
				break;
				
			default:
				break;
				
			}
			
			Methods_dlg.dlg_ShowMessage(actv, msg, R.color.red);
			
//			// debug
//			Toast.makeText(actv, "定型句を保管できませんでした", 3000).show();

		}//if (result == true)
		
		
	}//dlg_register_patterns_isInputEmpty

	public static void 
	dlg_DeletePatterns
	(Activity actv, Dialog dlg1) {
		/*----------------------------
		 * 1. Set up
		 * 2. Add listeners => OnTouch
		 * 3. Add listeners => OnClick
		 * 
		 * 4. Prep => List
		 * 5. Prep => Adapter
		 * 6. Set adapter
		 * 
		 * 7. Show dialog
			----------------------------*/
		Dialog dlg2 = new Dialog(actv);
		
		//
		dlg2.setContentView(R.layout.dlg_delete_patterns);
		
		// Title
		dlg2.setTitle(R.string.dlg_delete_patterns_title);
		
		/*----------------------------
		* 2. Add listeners => OnTouch
		----------------------------*/
		//
		Button btn_cancel = (Button) dlg2.findViewById(R.id.dlg_delete_patterns_bt_cancel);
		
		//
		btn_cancel.setTag(Tags.DialogTags.GENERIC_DISMISS_SECOND_DIALOG);
		
		//
		btn_cancel.setOnTouchListener(new DB_OTL(actv, dlg1, dlg2));
		
		/*----------------------------
		* 3. Add listeners => OnClick
		----------------------------*/
		//
		btn_cancel.setOnClickListener(new DB_OCL(actv, dlg1, dlg2));
		
		/*----------------------------
		 * 4. Prep => List
		 * 5. Prep => Adapter
		 * 6. Set adapter
			----------------------------*/
		GridView gv = _dlg_DeletePatterns__GridView(actv, dlg1, dlg2);

		/*----------------------------
		 * 7. Show dialog
			----------------------------*/
		dlg2.show();
		
	}//public static void dlg_delete_patterns(Activity actv, Dialog dlg)

	private static GridView 
	_dlg_DeletePatterns__GridView
	(Activity actv, Dialog dlg1, Dialog dlg2) {
		/*----------------------------
		 * 1. Set up db
		 * 1-1. Get grid view
		 * 2. Table exists?
		 * 3. Get cursor
		 * 
		 * 4. Get list
		 * 5. Prep => Adapter
		 * 6. Set adapter to view
		 * 
		 * 7. Set listener
			----------------------------*/
		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
		
		SQLiteDatabase rdb = dbu.getReadableDatabase();

		/*----------------------------
		 * 1-1. Get grid view
			----------------------------*/
		GridView gv = (GridView) dlg2.findViewById(R.id.dlg_delete_patterns_gv);
		
		/*----------------------------
		 * 2. Table exists?
			----------------------------*/
		String tableName = CONS.DB.tname_MemoPatterns;
		
		boolean res = dbu.tableExists(rdb, tableName);
		
		if (res == true) {
			
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Table exists: " + tableName);
			
			rdb.close();
			
//			return;
			
		} else {//if (res == false)
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Table doesn't exist: " + tableName);
			
			rdb.close();
			
			return gv;
			
//			SQLiteDatabase wdb = dbu.getWritableDatabase();
//			
//			res = dbu.createTable(wdb, tableName, CONS.cols_memo_patterns, CONS.col_types_memo_patterns);
//			
//			if (res == true) {
//				// Log
//				Log.d("Methods.java"
//						+ "["
//						+ Thread.currentThread().getStackTrace()[2]
//								.getLineNumber() + "]", "Table created: " + tableName);
//				
//				wdb.close();
//				
//			} else {//if (res == true)
//				// Log
//				Log.d("Methods.java"
//						+ "["
//						+ Thread.currentThread().getStackTrace()[2]
//								.getLineNumber() + "]", "Create table failed: " + tableName);
//				
//				wdb.close();
//				
//				return gv;
//				
//			}//if (res == true)

			
		}//if (res == false)
		
		/*----------------------------
		 * 3. Get cursor
			----------------------------*/
		rdb = dbu.getReadableDatabase();
		
//		String sql = "SELECT * FROM " + tableName + " ORDER BY word ASC";
		
//		android.provider.BaseColumns._ID,		// 0
//		"created_at", "modified_at",			// 1,2
//		"word",									// 3
//		"table_name"							// 4
//		Cursor c = rdb.rawQuery(sql, null);
		Cursor c = rdb.query(
						tableName, 
						CONS.DB.col_names_MemoPatterns_full, 
						null, null, null, null, 
						CONS.DB.col_names_MemoPatterns_full[3] + " ASC");
		
		actv.startManagingCursor(c);
		
		c.moveToFirst();
		
		/*----------------------------
		 * 4. Get list
			----------------------------*/
//		CONS.IMageActv.patternList = new ArrayList<String>();
//		List<String> patternList = new ArrayList<String>();
		
		CONS.IMageActv.patternList = new ArrayList<WordPattern>();
//		List<WordPattern> patternList = new ArrayList<WordPattern>();
		
		WordPattern wp = null;
		
		if (c.getCount() > 0) {
			
			for (int i = 0; i < c.getCount(); i++) {
				
//				CONS.IMageActv.patternList.add(c.getString(3));	// word
				
				wp = new WordPattern.Builder()
							.setDb_Id(c.getLong(0))
							.setCreated_at(c.getString(1))
							.setModified_at(c.getString(2))
							.setWord(c.getString(3))
							.build();
				
				CONS.IMageActv.patternList.add(wp);
//				patternList.add(wp);
				
				c.moveToNext();
				
			}//for (int i = 0; i < patternList.size(); i++)
			
		} else {//if (c.getCount() > 0)
			
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "!c.getCount() > 0");
			
		}//if (c.getCount() > 0)
		
		
//		Collections.sort(CONS.IMageActv.patternList);

		/*----------------------------
		 * 5. Prep => Adapter
			----------------------------*/
//		Adp_WordPatterns adapter = new Adp_WordPatterns(
		CONS.IMageActv.adapter = new Adp_WordPatterns(
										actv,
										R.layout.add_memo_grid_view,
										CONS.IMageActv.patternList
//										patternList
										);
//		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
//				actv,
//				R.layout.add_memo_grid_view,
//				CONS.IMageActv.patternList
//				);
		
		/*----------------------------
		 * 6. Set adapter to view
			----------------------------*/
		gv.setAdapter(CONS.IMageActv.adapter);
//		gv.setAdapter(adapter);
		
		/*----------------------------
		 * 7. Set listener
			----------------------------*/
//		gv.setTag(DialogTags.dlg_add_memos_gv);
		gv.setTag(Tags.DialogItemTags.DLG_DELETE_PATTERNS_GV);
		
		gv.setOnItemClickListener(new DOI_CL(actv, dlg1, dlg2));
		
		
		// Log
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "GridView setup => Done");
		
		/*----------------------------
		 * 8. Close db
			----------------------------*/
		rdb.close();

		return gv;
	}//private static GridView dlg_delete_patterns_2_grid_view(Activity actv, Dialog dlg, Dialog dlg2)

	public static void 
	conf_Delete_Pattern
	(Activity actv, 
		Dialog dlg1, Dialog dlg2, WordPattern wp) {
		// TODO Auto-generated method stub
		
		Dialog dlg3 = new Dialog(actv);
		
		//
		dlg3.setContentView(R.layout.dlg_tmpl_confirm_simple);
		
		// Title
		dlg3.setTitle(R.string.generic_tv_confirm);
		
		////////////////////////////////

		// Set: Message

		////////////////////////////////
		TextView tvMessage = 
				(TextView) dlg3.findViewById(R.id.dlg_tmpl_confirm_simple_tv_message);
		
//		tvMessage.setText("このアイテムを削除しますか？");
		tvMessage.setText(actv.getString(R.string.dlg_delete_patterns_conf_message));
		
		////////////////////////////////

		// Set: item name

		////////////////////////////////
		TextView tv = 
				(TextView) dlg3.findViewById(R.id.dlg_tmpl_confirm_simple_tv_item_name);
		
		String itemName = String.format("%s(id=%d)", wp.getWord(), (int)wp.getDb_Id());
		tv.setText(itemName);
		
		/*----------------------------
		 * 3. Add listeners => OnTouch
			----------------------------*/
		//
		Button btn_ok = (Button) dlg3.findViewById(R.id.dlg_tmpl_confirm_simple_btn_ok);
		Button btn_cancel = (Button) dlg3.findViewById(R.id.dlg_tmpl_confirm_simple_btn_cancel);
		
		//
		btn_ok.setTag(DialogTags.DLG_DELETE_PATTERN_CONF_OK);
		btn_cancel.setTag(DialogTags.GENERIC_DISMISS_THIRD_DIALOG);
		
		//
		btn_ok.setOnTouchListener(new DB_OTL(actv, dlg1, dlg2, dlg3));
		btn_cancel.setOnTouchListener(new DB_OTL(actv, dlg1, dlg2, dlg3));
		
		/*----------------------------
		 * 4. Add listeners => OnClick
			----------------------------*/
		//
		btn_ok.setOnClickListener(new DB_OCL(actv, dlg1, dlg2, dlg3, wp));
		btn_cancel.setOnClickListener(new DB_OCL(actv, dlg1, dlg2, dlg3));
		
		/*----------------------------
		 * 5. Show dialog
			----------------------------*/
		dlg3.show();
		
	}//conf_Delete_Pattern

	public static void 
	conf_Exec_Sql
	(Activity actv, 
		Dialog dlg1, String sql_Type) {
		// TODO Auto-generated method stub
		
		////////////////////////////////

		// dispatch

		////////////////////////////////
		if (sql_Type.equals(CONS.DB.Sqls._20140817_154650_addCol_IFM11_UpdatedAt_TITLE)) {
			
			_conf_Exec_Sql__AddCol_IFM11(actv, dlg1);
//			// Log
//			String msg_Log = "sql type => " + sql_Type;
//			Log.d("Methods_dlg.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", msg_Log);
			
		} else {
			
			String msg = "Unknown sql operation => " + sql_Type;
			Methods_dlg.dlg_ShowMessage(actv, msg, R.color.red);
			
			return;

		}
		
	}//conf_Exec_Sql

	private static void 
	_conf_Exec_Sql__AddCol_IFM11
	(Activity actv, Dialog dlg1) {
		// TODO Auto-generated method stub
		
		Dialog dlg2 = new Dialog(actv);
		
		//
		dlg2.setContentView(R.layout.dlg_tmpl_confirm_simple);
		
		// Title
		dlg2.setTitle(R.string.generic_tv_confirm);
		
		////////////////////////////////

		// set: message

		////////////////////////////////
		TextView tv_Message = (TextView) dlg2.findViewById(
								R.id.dlg_tmpl_confirm_simple_tv_message);
		
		tv_Message.setText(actv.getString(
					R.string.dlg_db_admin_item_exec_sql_confirm));

		////////////////////////////////
		
		// set: item name
		
		////////////////////////////////
		TextView tv_ItemName = (TextView) dlg2.findViewById(
				R.id.dlg_tmpl_confirm_simple_tv_item_name);
		
		tv_ItemName.setText(CONS.DB.Sqls._20140817_154650_addCol_IFM11_UpdatedAt_TITLE);
		
		////////////////////////////////

		// button: cancel

		////////////////////////////////
		Button btn_Cancel = (Button) dlg2.findViewById(
						R.id.dlg_tmpl_confirm_simple_btn_cancel);
		
		//
//		btn_Cancel.setTag(Tags.DialogTags.dlg_generic_dismiss_second_dialog);
		btn_Cancel.setTag(Tags.DialogTags.GENERIC_DISMISS_SECOND_DIALOG);
		
		//
		btn_Cancel.setOnTouchListener(new DB_OTL(actv, dlg2));
		
		btn_Cancel.setOnClickListener(new DB_OCL(actv, dlg1, dlg2));
		
		////////////////////////////////
		
		// button: ok
		
		////////////////////////////////
		Button btn_Ok = (Button) dlg2.findViewById(
				R.id.dlg_tmpl_confirm_simple_btn_ok);
		
		//
		btn_Ok.setTag(Tags.DialogTags.EXEC_SQL_OK);
		
		//
		btn_Ok.setOnTouchListener(new DB_OTL(actv, dlg2));
		
		btn_Ok.setOnClickListener(new DB_OCL(
							actv, 
							dlg1, dlg2, 
							CONS.DB.Sqls._20140817_154650_addCol_IFM11_UpdatedAt_TITLE));
		
		////////////////////////////////

		// show

		////////////////////////////////
		dlg2.show();		
		
	}//_conf_Exec_Sql__AddCol_IFM11

	public static void 
	upload_Image
	(Activity actv, Dialog dlg1, TI ti) {
		// TODO Auto-generated method stub

		Dialog dlg2 = Methods_dlg.dlg_Template_Cancel_SecondDialog(
				actv, dlg1,
				R.layout.dlg_tmpl_cancel_lv_2, 
				
				actv.getString(R.string.dlg_upload_image_title)
					+ ": "
					+ ti.getFile_name(), 
				
				R.id.dlg_tmpl_cancel_lv_2_bt_cancel, 
				Tags.DialogTags.GENERIC_DISMISS_SECOND_DIALOG);

//		Dialog dlg2 = Methods_dlg.dlg_Template_Cancel_SecondDialog(
//				actv, dlg1,
//				R.layout.dlg_tmpl_toast_ok, 
//				R.string.generic_tv_confirm, 
//				
//				R.id.dlg_tmpl_toast_ok_bt_cancel, 
//				Tags.DialogTags.GENERIC_DISMISS_SECOND_DIALOG);

		/*----------------------------
		* 2. Prep => List
		----------------------------*/
		String[] choices = {
				actv.getString(R.string.dlg_upload_image_item_remote),
				actv.getString(R.string.dlg_upload_image_item_email),
		};
		
		List<String> list = new ArrayList<String>();
		
		for (String item : choices) {
		
			list.add(item);
		
		}
		
		/*----------------------------
		* 3. Adapter
		----------------------------*/
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
						actv,
						//R.layout.dlg_db_admin,
						R.layout.list_row_simple_1,
//						android.R.layout.simple_list_item_1,
						list
		);
		
		/*----------------------------
		* 4. Set adapter
		----------------------------*/
		ListView lv = (ListView) dlg2.findViewById(R.id.dlg_tmpl_cancel_lv_2_lv);
		
		lv.setAdapter(adapter);
		
		/*----------------------------
		* 5. Set listener to list
		----------------------------*/
		lv.setTag(Tags.DialogItemTags.DLG_ACTV_TN_LIST_UPLOAD);
		
		lv.setOnItemClickListener(new DOI_CL(actv, dlg1, dlg2, ti));
		
		/*----------------------------
		* 6. Show dialog
		----------------------------*/
		dlg2.show();
		
	}//upload_Image

	public static void 
	conf_Upload_Image
	(Activity actv, 
		Dialog dlg1, Dialog dlg2, TI ti) {
		// TODO Auto-generated method stub
		
		Dialog d3 = new Dialog(actv);
		
		//
		d3.setContentView(R.layout.dlg_tmpl_confirm_simple_cb);
		
		// Title
		d3.setTitle(R.string.generic_tv_confirm);
		
		////////////////////////////////

		// set: message

		////////////////////////////////
		TextView tv_Message = (TextView) d3.findViewById(
								R.id.dlg_tmpl_confirm_simple_cb_tv_message);
		
		tv_Message.setText(actv.getString(
					R.string.dlg_upload_image_confirm));

		////////////////////////////////
		
		// set: item name
		
		////////////////////////////////
		TextView tv_ItemName = (TextView) d3.findViewById(
				R.id.dlg_tmpl_confirm_simple_cb_tv_item_name);
		
		tv_ItemName.setText(ti.getFile_name());
		
		////////////////////////////////

		// button: cancel

		////////////////////////////////
		Button btn_Cancel = (Button) d3.findViewById(
						R.id.dlg_tmpl_confirm_simple_cb_btn_cancel);
		
		//
//		btn_Cancel.setTag(Tags.DialogTags.dlg_generic_dismiss_second_dialog);
		btn_Cancel.setTag(Tags.DialogTags.GENERIC_DISMISS_THIRD_DIALOG);
		
		//
		btn_Cancel.setOnTouchListener(new DB_OTL(actv, dlg1, d3));
		
		btn_Cancel.setOnClickListener(new DB_OCL(actv, dlg1, dlg2, d3));
		
		////////////////////////////////
		
		// button: ok
		
		////////////////////////////////
		Button btn_Ok = (Button) d3.findViewById(
				R.id.dlg_tmpl_confirm_simple_cb_btn_ok);
		
		//
		btn_Ok.setTag(Tags.DialogTags.UPLOAD_REMOTE_OK);
		
		//
		btn_Ok.setOnTouchListener(new DB_OTL(actv, dlg1, dlg2, d3));
		
		btn_Ok.setOnClickListener(new DB_OCL(actv, dlg1, dlg2, d3, ti));
		
		////////////////////////////////

		// show

		////////////////////////////////
		d3.show();		
		
	}//conf_Upload_Image

	/******************************
		@param duration => millseconds
	 ******************************/
	public static void
	dlg_ShowMessage_Duration
	(Activity actv, String message, int duration) {

		////////////////////////////////

		// validate

		////////////////////////////////
		String actv_Name = actv.getClass().getName();
		
		if (actv_Name.equals(CONS.Admin.actvName_TNActv)) {
			
			if (CONS.Admin.isRunning_TNActv == false) {
				
				// Log
				String msg_Log = "TNActv => not running";
				Log.i("Methods_dlg.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", msg_Log);
				
				return;
				
			}
			
		}
		
		////////////////////////////////

		// build dlg

		////////////////////////////////
		final Dialog dlg = Methods_dlg.dlg_Template_Cancel(
				actv, R.layout.dlg_tmpl_toast_ok, 
				R.string.generic_tv_confirm, 
				R.id.dlg_tmpl_toast_ok_bt_cancel, 
//				R.id.dlg_db_admin_bt_cancel, 
				Tags.DialogTags.GENERIC_DISMISS);
		
		TextView tv_Message = 
				(TextView) dlg.findViewById(R.id.dlg_tmpl_toast_ok_tv_message);
		
		tv_Message.setText(message);
		
		////////////////////////////////

		// show

		////////////////////////////////
		dlg.show();
		
		//REF http://xjaphx.wordpress.com/2011/07/13/auto-close-dialog-after-a-specific-time/
		final Timer t = new Timer();
        t.schedule(new TimerTask() {
            public void run() {
                dlg.dismiss(); // when the task active then close the dialog
                t.cancel(); // also just top the timer thread, otherwise, you may receive a crash report
            }
        }, duration); // after 2 second (or 2000 miliseconds), the task will be active.
		
	}

	/******************************
		@param duration => millseconds
	 ******************************/
	public static void
	dlg_ShowMessage_Duration
	(Activity actv, String message, int colorID, int duration) {
		
		final Dialog dlg = Methods_dlg.dlg_Template_Cancel(
				actv, R.layout.dlg_tmpl_toast_ok, 
				R.string.generic_tv_confirm, 
				R.id.dlg_tmpl_toast_ok_bt_cancel, 
//				R.id.dlg_db_admin_bt_cancel, 
				Tags.DialogTags.GENERIC_DISMISS);
		
		TextView tv_Message = 
				(TextView) dlg.findViewById(R.id.dlg_tmpl_toast_ok_tv_message);
		
		tv_Message.setText(message);
		
		////////////////////////////////

		// background

		////////////////////////////////
//		tv_Message.setBackgroundColor(colorID);
		tv_Message.setBackgroundColor(actv.getResources().getColor(colorID));

		////////////////////////////////
		
		// show
		
		////////////////////////////////
		dlg.show();
		
		//REF http://xjaphx.wordpress.com/2011/07/13/auto-close-dialog-after-a-specific-time/
		final Timer t = new Timer();
		t.schedule(new TimerTask() {
			public void run() {
				dlg.dismiss(); // when the task active then close the dialog
				t.cancel(); // also just top the timer thread, otherwise, you may receive a crash report
			}
		}, duration); // after 2 second (or 2000 miliseconds), the task will be active.
		
	}

	public static void 
	conf_Upload_DB
	(Activity actv, Dialog d1) {
		// TODO Auto-generated method stub

//		Dialog dlg2 = Methods_dlg.dlg_Template_Cancel_SecondDialog(
		Dialog d2 = Methods_dlg.dlg_Template_OkCancel_SecondDialog(
				actv, d1,
				R.layout.dlg_tmpl_confirm_simple, 
				R.string.generic_tv_confirm, 
				
				R.id.dlg_tmpl_confirm_simple_btn_ok, 
				Tags.DialogTags.UPLOAD_DB_FILE_OK,
				
				R.id.dlg_tmpl_confirm_simple_btn_cancel, 
				Tags.DialogTags.GENERIC_DISMISS_SECOND_DIALOG
				);
		
		////////////////////////////////

		// set: message

		////////////////////////////////
		TextView tv_Message = 
				(TextView) d2.findViewById(R.id.dlg_tmpl_confirm_simple_tv_message);
		
		tv_Message.setText(actv.getString(R.string.dlg_upload_image_confirm));
		
		////////////////////////////////
		
		// set: item name
		
		////////////////////////////////
		TextView tv_ItemName = 
				(TextView) d2.findViewById(R.id.dlg_tmpl_confirm_simple_tv_item_name);
		
		// "DB"
		tv_ItemName.setText(actv.getString(R.string.main_opt_menu_db_activity));

		////////////////////////////////

		// show

		////////////////////////////////
		d2.show();

	}//conf_Upload_DB

	public static void 
	dlg_OptMenu_MainActv_Others
	(Activity actv) {
		// TODO Auto-generated method stub
		
		////////////////////////////////

		// vars

		////////////////////////////////
		String msg_Log;
		
		
		////////////////////////////////

		// dlg

		////////////////////////////////
		Dialog d1 = Methods_dlg.dlg_Template_Cancel(
						actv,
						R.layout.dlg_tmpl_cancel_lv,
						R.string.main_opt_menu_others,
						
						R.id.dlg_tmpl_cancel_lv_bt_cancel,
						Tags.DialogTags.GENERIC_DISMISS);
		
		/****************************
		* 2. Prep => List
		****************************/
//		String[] choices = {
//					actv.getString(R.string.dlg_actvmain_lv_delete),
//					};
		
		List<ListItem> list = new ArrayList<ListItem>();
//		List<String> list = new ArrayList<String>();
		
		list.add(new ListItem.Builder()
						.setText(actv.getString(
									R.string.dlg_actv_main_other_see_log))
						.setIconID(R.drawable.menu_icon_admin_32x32_blue)
						.setTextColor_ID(R.color.blue1)
						.build());
		
		/****************************
		* 3. Adapter
		****************************/
		Adp_ListItems adapter = new Adp_ListItems(
							actv,
							//R.layout.dlg_db_admin,
							R.layout.list_row_simple_iv_1,
							//android.R.layout.simple_list_item_1,
							list
		);
//		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
//							actv,
//							//R.layout.dlg_db_admin,
//							R.layout.list_row_simple_1,
//							//android.R.layout.simple_list_item_1,
//							list
//		);
		
		/****************************
		* 4. Set adapter
		****************************/
		ListView lv = (ListView) d1.findViewById(R.id.dlg_tmpl_cancel_lv_lv);
		
		lv.setAdapter(adapter);
		
		////////////////////////////////

		// Set listener to list

		////////////////////////////////
		lv.setTag(Tags.DialogItemTags.ACTV_MAIN_OPTMENU_OTHERS);
		
		lv.setOnItemClickListener(new DOI_CL(actv, d1));
		
		/***************************************
		* Modify the list view height
		***************************************/
//		lv.setLayoutParams(
//				new LinearLayout.LayoutParams(
//						300,	//	Width
//						LayoutParams.WRAP_CONTENT	//	Height
//				));
		
		/***************************************
		* Modify: Button layout
		***************************************/
		LinearLayout llButton =
					(LinearLayout) d1.findViewById(R.id.dlg_tmpl_cancel_lv_ll_filepath);
//		(LinearLayout) dlg1.findViewById(R.id.actv_imp_ll_filepath);
		
		LinearLayout.LayoutParams params =
				new LinearLayout.LayoutParams(
								LayoutParams.WRAP_CONTENT,
								LayoutParams.WRAP_CONTENT);
		
		params.gravity = Gravity.CENTER_HORIZONTAL;
		
		llButton.setLayoutParams(params);

		////////////////////////////////

		// get: screen size

		////////////////////////////////
		//REF size http://stackoverflow.com/questions/19155559/how-to-get-android-device-screen-size answered Oct 3 '13 at 10:00
		DisplayMetrics displayMetrics = actv.getResources()
                			.getDisplayMetrics();
		
		int w = displayMetrics.widthPixels;
		
//		// Log
//		String msg_Log = "w => " + w;
//		Log.d("Methods_dlg.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_Log);
		
		int dialog_Width = w * CONS.Admin.ratio_Dialog_to_Screen_W / 100;
		
//		// Log
//		msg_Log = "dialog_Width => " + dialog_Width;
//		Log.d("Methods_dlg.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_Log);
		
		////////////////////////////////

		// linear layot: main

		////////////////////////////////
		LinearLayout ll_Main = (LinearLayout) d1.findViewById(R.id.dlg_tmpl_cancel_lv_ll_main);
		
		// Log
		msg_Log = "ll_Main => created";
		Log.d("Methods_dlg.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		//REF parent layout http://stackoverflow.com/questions/4631966/set-relativelayout-layout-params-programmatically-throws-classcastexception answered Jan 8 '11 at 5:42
//		08-21 11:30:45.434: E/AndroidRuntime(20722): java.lang.ClassCastException: android.widget.LinearLayout$LayoutParams
//		08-21 11:30:45.434: E/AndroidRuntime(20722): 	at android.widget.FrameLayout.onLayout(FrameLayout.java:293)
//		08-21 11:30:45.434: E/AndroidRuntime(20722): 	at android.view.View.layout(View.java:7184)

		FrameLayout.LayoutParams params2 =
				new FrameLayout.LayoutParams(
//						LinearLayout.LayoutParams params2 =
//						new LinearLayout.LayoutParams(
						dialog_Width,
//						400,
//						200,
//						LayoutParams.WRAP_CONTENT,
						LayoutParams.WRAP_CONTENT);
		
		// Log
		msg_Log = "setting params...";
		Log.d("Methods_dlg.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		ll_Main.setLayoutParams(params2);
		
		// Log
		msg_Log = "params => set";
		Log.d("Methods_dlg.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		/****************************
		* 6. Show dialog
		****************************/
		d1.show();
		
		
	}//dlg_OptMenu_MainActv_Others

	public static void 
	dlg_MoveFiles__Remote
	(Activity actv, Dialog d1) {
		// TODO Auto-generated method stub
		
		Dialog d2 = Methods_dlg.dlg_Template_OkCancel_SecondDialog(
				actv, d1,
				R.layout.dlg_tmpl_confirm_simple_cb, 
//				R.layout.dlg_tmpl_confirm_simple, 
				R.string.generic_tv_confirm, 
				
				R.id.dlg_tmpl_confirm_simple_cb_btn_ok, 
				Tags.DialogTags.UPLOAD_REMOTE_MULTIPLE_IMAGES_OK,
				
				R.id.dlg_tmpl_confirm_simple_cb_btn_cancel, 
				Tags.DialogTags.GENERIC_DISMISS_SECOND_DIALOG
				);
		
		////////////////////////////////

		// set: message

		////////////////////////////////
		TextView tv_Message = 
				(TextView) d2.findViewById(R.id.dlg_tmpl_confirm_simple_cb_tv_message);
		
		tv_Message.setText(actv.getString(R.string.dlg_upload_image_confirm));
		
		////////////////////////////////
		
		// set: item name
		
		////////////////////////////////
		TextView tv_ItemName = 
				(TextView) d2.findViewById(R.id.dlg_tmpl_confirm_simple_cb_tv_item_name);
		
		// "DB"
		tv_ItemName.setText(String.valueOf(CONS.TNActv.checkedPositions.size()) + " items");

		////////////////////////////////

		// show

		////////////////////////////////
		d2.show();		
		
	}//dlg_MoveFiles__Remote

	public static void 
	dlg_ACTV_MAIN_Operations
	(Activity actv, Dialog d1) {
		// TODO Auto-generated method stub
		
		////////////////////////////////

		// prep: dialog

		////////////////////////////////
		Dialog d2 = Methods_dlg.dlg_Template_Cancel_SecondDialog(
				actv, d1,
				R.layout.dlg_tmpl_cancel_lv_with_btn, 
				R.string.dlg_db_admin_item_operations, 
				
				R.id.dlg_tmpl_cancel_lv_with_btn_bt_cancel, 
				Tags.DialogTags.GENERIC_DISMISS_SECOND_DIALOG);

		////////////////////////////////

		// list

		////////////////////////////////
		ListView lv = _dlg_Admin_Ops__Setup_List(actv, d2);
		
		////////////////////////////////
		
		// setup: layout

		////////////////////////////////
		_dlg_Admin_Ops__Setup_Layout(actv, d2);

		////////////////////////////////

		// listener

		////////////////////////////////
		_dlg_Admin_Ops__Setup_Listener(actv, d1, d2, lv);
		
		////////////////////////////////

		// show

		////////////////////////////////
		d2.show();
		
	}//dlg_ACTV_MAIN_Operations

	private static void 
	_dlg_Admin_Ops__Setup_Listener
	(Activity actv, Dialog d1, Dialog d2, ListView lv) {
		// TODO Auto-generated method stub

		////////////////////////////////

		// Set listener to list

		////////////////////////////////
		lv.setTag(Tags.DialogItemTags.ACTV_MAIN_ADMIN_LV_OPS);
		
		lv.setOnItemClickListener(new DOI_CL(actv, d1, d2));

		////////////////////////////////

		// button: all dismiss

		////////////////////////////////
		ImageButton ib_AllClear = 
				(ImageButton) d2.findViewById(R.id.dlg_tmpl_cancel_lv_with_btn_ib);
//		ImageButton ib_AllClear = (ImageButton) findViewById(R.id.dlg_tmpl_cancel_lv_wi);
		
		ib_AllClear.setTag(Tags.DialogTags.GENERIC_SECOND_DIALOG_CLEAR_ALL);
		
		ib_AllClear.setOnTouchListener(new DB_OTL(actv, d1, d2));
		
		ib_AllClear.setOnClickListener(new DB_OCL(actv, d1, d2));
		
		
	}//_dlg_Admin_Ops__Setup_Listener

	private static void 
	_dlg_Admin_Ops__Setup_Layout
	(Activity actv, Dialog d2) {
		// TODO Auto-generated method stub
	
		////////////////////////////////

		// layout: button

		////////////////////////////////
		LinearLayout llButton =
					(LinearLayout) d2.findViewById(
							R.id.dlg_tmpl_cancel_lv_with_btn_ll_filepath);
//		(LinearLayout) dlg1.findViewById(R.id.actv_imp_ll_filepath);
		
		LinearLayout.LayoutParams params =
				new LinearLayout.LayoutParams(
								LayoutParams.WRAP_CONTENT,
								LayoutParams.WRAP_CONTENT);
		
		params.gravity = Gravity.CENTER_HORIZONTAL;
		
		llButton.setLayoutParams(params);

		////////////////////////////////

		// get: screen size

		////////////////////////////////
		//REF size http://stackoverflow.com/questions/19155559/how-to-get-android-device-screen-size answered Oct 3 '13 at 10:00
		DisplayMetrics displayMetrics = actv.getResources()
                			.getDisplayMetrics();
		
		int w = displayMetrics.widthPixels;
		
		// Log
		String msg_Log = "w => " + w;
		Log.d("Methods_dlg.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		int dialog_Width = w * CONS.Admin.ratio_Dialog_to_Screen_W / 100;
		
		// Log
		msg_Log = "dialog_Width => " + dialog_Width;
		Log.d("Methods_dlg.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		////////////////////////////////

		// linear layot: main

		////////////////////////////////
		LinearLayout ll_Main = (LinearLayout) d2.findViewById(
						R.id.dlg_tmpl_cancel_lv_with_btn_ll_main);
		
		// Log
		msg_Log = "ll_Main => created";
		Log.d("Methods_dlg.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		FrameLayout.LayoutParams params2 =
				new FrameLayout.LayoutParams(
						dialog_Width,
						LayoutParams.WRAP_CONTENT);
		
		ll_Main.setLayoutParams(params2);
		
		////////////////////////////////

		// ListView: height

		////////////////////////////////
//		// Log
//		msg_Log = "ll_Main.getHeight() => " + ll_Main.getHeight();
//		Log.d("Methods_dlg.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_Log);
//		
//		ListView lv = (ListView) d2.findViewById(R.id.dlg_tmpl_cancel_lv_with_btn_lv);
//		
//		// Log
//		msg_Log = "lv.getHeight() => " + lv.getHeight();
//		Log.d("Methods_dlg.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_Log);
		
	}//_dlg_Admin_Ops__Setup_Layout

	private static ListView 
	_dlg_Admin_Ops__Setup_List
	(Activity actv, Dialog d2) {
		// TODO Auto-generated method stub
		
		List<ListItem> list = new ArrayList<ListItem>();
		
		list.add(new ListItem.Builder()
						.setText(actv.getString(
								R.string.dlg_db_ops_item_drop_create_tbl_patterns))
								.setIconID(R.drawable.menu_icon_admin_32x32_blue)
								.setTextColor_ID(R.color.blue1)
								.build());
		
		list.add(new ListItem.Builder()
						.setText(actv.getString(
									R.string.dlg_db_ops_item_import_patterns_from_previous))
						.setIconID(R.drawable.menu_icon_admin_32x32_brown)
						.setTextColor_ID(R.color.black)
						.build());
		
		////////////////////////////////

		// Adapter

		////////////////////////////////
		Adp_ListItems adapter = new Adp_ListItems(
							actv,
							//R.layout.dlg_db_admin,
							R.layout.list_row_simple_iv_1,
							//android.R.layout.simple_list_item_1,
							list
		);
		
		////////////////////////////////

		// Set adapter

		////////////////////////////////
		ListView lv = (ListView) d2.findViewById(R.id.dlg_tmpl_cancel_lv_with_btn_lv);
		
		lv.setAdapter(adapter);

		////////////////////////////////

		// return

		////////////////////////////////
		return lv;
		
	}//_dlg_Admin_Ops__Setup_List

}//public class Methods_dialog
