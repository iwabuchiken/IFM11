package ifm11.utils;

import ifm11.items.TI;
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

import org.apache.commons.lang.StringUtils;

import android.app.Activity;
import android.app.Dialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
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
		Dialog dlg = Methods_dlg.dlg_Template_Cancel(
									actv, R.layout.dlg_tmpl_list_cancel, 
									R.string.dlg_db_admin_title, 
									R.id.dlg_tmpl_list_cancel_bt_cancel, 
//									R.id.dlg_db_admin_bt_cancel, 
									Tags.DialogTags.GENERIC_DISMISS);
		
		/****************************
		 * 2. Prep => List
			****************************/
		String[] choices = {
				
				actv.getString(R.string.dlg_db_admin_item_backup_db),
				actv.getString(R.string.dlg_db_admin_item_refresh_db),
				
				actv.getString(R.string.dlg_db_admin_item_drop_table_ifm11),
				actv.getString(R.string.dlg_db_admin_item_create_table_ifm11),
				
				actv.getString(
							R.string.dlg_db_admin_item_drop_table_refresh_log),
				actv.getString(
							R.string.dlg_db_admin_item_create_table_refresh_log),
				
				actv.getString(
						R.string.dlg_db_admin_item_create_table_memo_patterns),
				actv.getString(
						R.string.dlg_db_admin_item_drop_table_memo_patterns),
											
				actv.getString(R.string.dlg_db_admin_item_restore_db),
				
				actv.getString(R.string.dlg_db_admin_item_import_db_file),
				actv.getString(R.string.dlg_db_admin_item_import_patterns),
				
					};
		
		List<String> list = new ArrayList<String>();
		
		for (String item : choices) {
			
			list.add(item);
			
		}
		
		/****************************
		 * 3. Adapter
			****************************/
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				actv,
//				R.layout.dlg_db_admin,
//				android.R.layout.simple_list_item_1,
				R.layout.list_row_simple_1,
				list
				);

		/****************************
		 * 4. Set adapter
			****************************/
		ListView lv = (ListView) dlg.findViewById(R.id.dlg_tmpl_list_cancel_lv);
//		ListView lv = (ListView) dlg.findViewById(R.id.dlg_db_admin_lv);
		
		lv.setAdapter(adapter);
		
		/****************************
		 * 5. Set listener to list
			****************************/
		lv.setTag(Tags.DialogItemTags.DLG_DB_ADMIN_LV);
		
		lv.setOnItemClickListener(new DOI_CL(actv, dlg));
		
		/****************************
		 * 6. Show dialog
			****************************/
		dlg.show();
		
		
	}//public static void dlg_db_activity(Activity actv)

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
		
		////////////////////////////////

		// gridview

		////////////////////////////////
		dlg = _dlg_AddMemo_Set_Gridview(actv, dlg);

		//test
		GridView gv = (GridView) dlg.findViewById(R.id.dlg_add_memos_gv);
		
		// Log
		String msg_Log = "gv.getChildCount() => " + gv.getChildCount();
		Log.d("Methods_dlg.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);

		// Log
		msg_Log = "CONS.IMageActv.patternList.size() => "
							+ CONS.IMageActv.patternList.size();
		Log.d("Methods_dlg.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		////////////////////////////////

		// show

		////////////////////////////////
		dlg.show();
		
	}//dlg_addMemo
	
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
		
		Button btn_patterns = (Button) dlg.findViewById(R.id.dlg_add_memos_bt_patterns);
		
		// Tags
//		btn_add.setTag(DialogTags.dlg_add_memos_bt_add);
		btn_add.setTag(DialogTags.DLG_ADD_MEMOS_BT_ADD);
		btn_cancel.setTag(DialogTags.GENERIC_DISMISS);
//		btn_cancel.setTag(DialogTags.dlg_generic_dismiss);
		
//		btn_patterns.setTag(DialogTags.dlg_add_memos_bt_patterns);
		btn_patterns.setTag(DialogTags.DLG_ADD_MEMOS_BT_PATTERNS);
		
		//
		btn_add.setOnTouchListener(new DB_OTL(actv, dlg));
		btn_cancel.setOnTouchListener(new DB_OTL(actv, dlg));
		
		btn_patterns.setOnTouchListener(new DB_OTL(actv, dlg));
		
		////////////////////////////////

		// Add listeners => OnClick

		////////////////////////////////
		btn_add.setOnClickListener(new DB_OCL(actv, dlg, db_Id));
		btn_cancel.setOnClickListener(new DB_OCL(actv, dlg));
		
		btn_patterns.setOnClickListener(new DB_OCL(actv, dlg));

		
		return dlg;
		
	}//public static Dialog dlg_addMemo(Activity actv, long file_id, String tableName)

	/******************************
		@return 
		1. Cursor returned null => parameter dlg<br>
		2. Cursor has no entry => parameter dlg<br>
		
	 ******************************/
	public static Dialog 
	_dlg_AddMemo_Set_Gridview
	(Activity actv, Dialog dlg) {
		////////////////////////////////

		// setup: db, view

		////////////////////////////////
		GridView gv = (GridView) dlg.findViewById(R.id.dlg_add_memos_gv);
		
		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
		
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
			
			rdb.close();
			
//			return;
			
		} else {//if (res == false)
			////////////////////////////////

			// no table => creating one

			////////////////////////////////
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Table doesn't exist: " + tableName);
			
			rdb.close();
			
			SQLiteDatabase wdb = dbu.getWritableDatabase();
			
			res = dbu.createTable(
							wdb, 
							tableName, 
							CONS.DB.col_names_MemoPatterns, 
							CONS.DB.col_types_MemoPatterns);
			
			if (res == true) {
				// Log
				Log.d("Methods.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", "Table created: " + tableName);
				
				wdb.close();
				
			} else {//if (res == true)
				// Log
//				Log.d("Methods.java"
//						+ "["
//						+ Thread.currentThread().getStackTrace()[2]
//								.getLineNumber() + "]", "Create table failed: " + tableName);
				
				String msg = "Create table failed: " + tableName;
				Methods_dlg.dlg_ShowMessage(actv, msg);
				
				
				wdb.close();
				
				return dlg;
				
			}//if (res == true)

			
		}//if (res == false)
		
		
		////////////////////////////////

		// Get cursor

		////////////////////////////////
		rdb = dbu.getReadableDatabase();
		
//		String sql = "SELECT * FROM " + tableName + " ORDER BY word ASC";
//		
//		Cursor c = rdb.rawQuery(sql, null);
//		
//		actv.startManagingCursor(c);
		
		// "word"
		String orderBy = CONS.DB.col_names_MemoPatterns_full[3] + " ASC"; 
		
		Cursor c = rdb.query(
						CONS.DB.tname_MemoPatterns,
						CONS.DB.col_names_MemoPatterns_full,
		//				CONS.DB.col_types_refresh_log_full,
						null, null,		// selection, args 
						null, 			// group by
						null, 		// having
						orderBy);

		actv.startManagingCursor(c);
		
		/******************************
			validate: null
		 ******************************/
		if (c == null) {
	
			// Log
			String msg_Log = "query => null";
			Log.e("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);

			rdb.close();
			
			return dlg;
			
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
			
			rdb.close();
			
			return dlg;
			
		}

		////////////////////////////////

		// cursor: move to first

		////////////////////////////////
		c.moveToFirst();
		
		////////////////////////////////

		// Get list

		////////////////////////////////
		CONS.IMageActv.patternList = new ArrayList<String>();
//		List<String> patternList = new ArrayList<String>();
		
		if (c.getCount() > 0) {
			
			for (int i = 0; i < c.getCount(); i++) {
				
				CONS.IMageActv.patternList.add(c.getString(3));	// 3 => "word"
//				patternList.add(c.getString(3));	// 3 => "word"
//				patternList.add(c.getString(1));
				
				c.moveToNext();
				
			}//for (int i = 0; i < patternList.size(); i++)
			
		} else {//if (c.getCount() > 0)
			
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "!c.getCount() > 0");
			
		}//if (c.getCount() > 0)
		
		
		Collections.sort(CONS.IMageActv.patternList);
//		Collections.sort(patternList);

		// Log
		String msg_Log = "CONS.IMageActv.patternList.size() => " 
						+ CONS.IMageActv.patternList.size();
//		String msg_Log = "patternList => " + patternList.size();
		Log.d("Methods_dlg.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);

		////////////////////////////////

		// Adapter

		////////////////////////////////
//		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
		CONS.IMageActv.adp_ImageActv_GridView = new ArrayAdapter<String>(
										actv,
										R.layout.add_memo_grid_view,
										CONS.IMageActv.patternList
//										patternList
										);
		
		gv.setAdapter(CONS.IMageActv.adp_ImageActv_GridView);
//		gv.setAdapter(adapter);

		// Log
		msg_Log = "adapter.getCount() => " 
					+ CONS.IMageActv.adp_ImageActv_GridView.getCount();
		Log.d("Methods_dlg.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		////////////////////////////////

		// Set listener

		////////////////////////////////
////		gv.setTag(DialogTags.dlg_add_memos_gv);
		gv.setTag(Tags.DialogItemTags.DLG_ADD_MEMOS_GV);
//		
		gv.setOnItemClickListener(new DOI_CL(actv, dlg));
//		gv.setOnItemClickListener(new DialogOnItemClickListener(actv, dlg));
//		
//		
//		// Log
//		Log.d("Methods.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", "GridView setup => Done");
		
		/*----------------------------
		 * 8. Close db
			----------------------------*/
		rdb.close();
		

		////////////////////////////////

		// return

		////////////////////////////////
		// Log
		msg_Log = "gridview => set";
		Log.d("Methods_dlg.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		// Log
		msg_Log = "patternList => " + CONS.IMageActv.patternList.size();
//		msg_Log = "patternList => " + patternList.size();
		Log.d("Methods_dlg.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);

		// Log
		msg_Log = "gv.getChildCount() => " + gv.getChildCount();
		Log.d("Methods_dlg.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		return dlg;
		
	}//public static Dialog dlg_addMemo(Activity actv, long file_id, String tableName)

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
	conf_DeleteFolder
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
		ListView lv = (ListView) dlg2.findViewById(R.id.dlg_tmpl_list_cancel_2_lv);
		
		lv.setAdapter(adapter);
		
		////////////////////////////////

		// Set listener to list

		////////////////////////////////
		// Item click
//		lv.setTag(Tags.DialogItemTags.dlg_move_files);
		lv.setTag(Tags.DialogItemTags.DLG_MOVE_FILES_FOLDER);
		
		lv.setOnItemClickListener(new DOI_CL(actv, dlg1, dlg2));

		// Long click
		lv.setOnItemLongClickListener(new LOI_LCL(actv, dlg1, dlg2));
		
		/****************************
		* 6. Show dialog
		****************************/
		dlg2.show();
		
	}//dlg_MoveFiles__Folder

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

}//public class Methods_dialog
