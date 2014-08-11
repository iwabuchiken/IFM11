package ifm11.utils;

import ifm11.items.TI;
import ifm11.listener.dialog.DB_OCL;
import ifm11.listener.dialog.DB_OTL;
import ifm11.listener.dialog.DOI_CL;
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

	public static void dlg_Db_Actv(Activity actv) {
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
				
				actv.getString(R.string.dlg_db_admin_item_restore_db),
				
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
	dlg_addMemo
	(Activity actv, long file_id) {
		// TODO Auto-generated method stub
		
		////////////////////////////////

		// get: dlg

		////////////////////////////////
		Dialog dlg = Methods_dlg.dlg_addMemo_1_get_dialog(actv, file_id);
		
		////////////////////////////////

		// show

		////////////////////////////////
		dlg.show();
		
	}//dlg_addMemo
	
	public static Dialog 
	dlg_addMemo_1_get_dialog
	(Activity actv, long file_id) {
		
		// 
		Dialog dlg = new Dialog(actv);
		
		//
		dlg.setContentView(R.layout.dlg_add_memos);
		
		// Title
		dlg.setTitle(R.string.dlg_add_memos_tv_title);
		
		/*----------------------------
		 * 1-2. Set text to edit text
			----------------------------*/
		TI ti = DBUtils.get_TI_From_FileId(actv, file_id);
		
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
		btn_add.setOnClickListener(new DB_OCL(actv, dlg, file_id));
		btn_cancel.setOnClickListener(new DB_OCL(actv, dlg));
		
		btn_patterns.setOnClickListener(new DB_OCL(actv, dlg));

		
		return dlg;
		
	}//public static Dialog dlg_addMemo(Activity actv, long file_id, String tableName)

}//public class Methods_dialog
