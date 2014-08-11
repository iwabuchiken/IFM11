package ifm11.listener.dialog;


import ifm11.main.R;
import ifm11.tasks.Task_RefreshDB;
import ifm11.utils.CONS;
import ifm11.utils.DBUtils;
import ifm11.utils.Methods;
import ifm11.utils.Methods_dlg;
import ifm11.utils.Tags;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class DOI_CL implements OnItemClickListener {

	//
	Activity actv;
	Dialog dlg1;
	Dialog dlg2;
	
	//
	Vibrator vib;
	
	//
//	Methods.DialogTags dlgTag = null;

	public DOI_CL(Activity actv, Dialog dlg) {
		// 
		this.actv = actv;
		this.dlg1 = dlg;
		
		vib = (Vibrator) actv.getSystemService(Context.VIBRATOR_SERVICE);
		
	}//public DialogOnItemClickListener(Activity actv, Dialog dlg)

	public DOI_CL(Activity actv, Dialog dlg, Dialog dlg2) {
		// 
		this.actv = actv;
		this.dlg1 = dlg;
		this.dlg2 = dlg2;
		
		vib = (Vibrator) actv.getSystemService(Context.VIBRATOR_SERVICE);
		
	}//public DialogOnItemClickListener(Activity actv, Dialog dlg)


	//	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		/*----------------------------
		 * Steps
		 * 1. Get tag
		 * 2. Vibrate
		 * 3. Switching
			----------------------------*/
		
		Tags.DialogItemTags tag = (Tags.DialogItemTags) parent.getTag();
//		
		vib.vibrate(CONS.Admin.vibLength_click);
		
		String item = (String) parent.getItemAtPosition(position);
		
		/*----------------------------
		 * 3. Switching
			----------------------------*/
		switch (tag) {
		
//		case dlg_db_admin_lv://----------------------------------------------
		case DLG_DB_ADMIN_LV://----------------------------------------------
			
			case_Dlg_Db_Admin_lv(item);
			
			break;// case dlg_add_memos_gv

//		case dlg_bmactv_list_long_click://----------------------------------------------
			
		case DLG_ADD_MEMOS_GV://----------------------------------------------
			
			String word = (String) parent.getItemAtPosition(position);
			
//			Methods.add_pattern_to_text(dlg1, position, word);
			
			break;
			
		default:
			break;
		}//switch (tag)
		
	}//public void onItemClick(AdapterView<?> parent, View v, int position, long id)

	private void
	case_Dlg_Db_Admin_lv(String item) {
		// TODO Auto-generated method stub
		
		// Log
		String msg_Log = "case_Dlg_Db_Admin_lv()";
		Log.d("DOI_CL.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		////////////////////////////////

		// Dispatch

		////////////////////////////////
		if (item.equals(actv.getString(
				R.string.dlg_db_admin_item_backup_db))) {
			
			Methods.backup_DB(actv);
			
		} else if (item.equals(actv.getString(		// Refresh DB
				R.string.dlg_db_admin_item_refresh_db))) {
			
			_case_Dlg_Db_Admin_lv__RefreshDB();
//			Methods.refresh_MainDB(actv);
			
		////////////////////////////////

		// create/drop: ifm11

		////////////////////////////////
		} else if (item.equals(actv.getString(		// Create table: cm7
				R.string.dlg_db_admin_item_create_table_ifm11))) {
			
			_case_Dlg_Db_Admin_lv__CreateTable(actv, CONS.DB.tname_IFM11);
//			Methods.create_Table(actv, CONS.DB.tname_CM7);
			
		} else if (item.equals(actv.getString(		// Drop table: cm7
				R.string.dlg_db_admin_item_drop_table_ifm11))) {
			
//			Methods_dlg.conf_DropTable(actv, dlg1, CONS.DB.tname_IFM11);
			
			_case_Dlg_Db_Admin_lv__DropTable(actv, CONS.DB.tname_IFM11);
			
			return;
			
		////////////////////////////////
		
		// create/drop: refresh log
		
		////////////////////////////////
		} else if (item.equals(actv.getString(		// Create table: cm7
				R.string.dlg_db_admin_item_create_table_refresh_log))) {
			
			_case_Dlg_Db_Admin_lv__CreateTable(actv, CONS.DB.tname_RefreshLog);
//			Methods.create_Table(actv, CONS.DB.tname_CM7);
			
		} else if (item.equals(actv.getString(		// Drop table: cm7
				R.string.dlg_db_admin_item_drop_table_refresh_log))) {
			
//			Methods_dlg.conf_DropTable(actv, dlg1, CONS.DB.tname_IFM11);
			
			_case_Dlg_Db_Admin_lv__DropTable(actv, CONS.DB.tname_RefreshLog);
			
			return;
			
		////////////////////////////////
		
		// create/drop: memo_patterns
		
		////////////////////////////////
		} else if (item.equals(actv.getString(		// Create table: cm7
				R.string.dlg_db_admin_item_create_table_memo_patterns))) {
			
			_case_Dlg_Db_Admin_lv__CreateTable(actv, CONS.DB.tname_MemoPatterns);
//			Methods.create_Table(actv, CONS.DB.tname_CM7);
			
		} else if (item.equals(actv.getString(		// Drop table: cm7
				R.string.dlg_db_admin_item_drop_table_memo_patterns))) {
			
//			Methods_dlg.conf_DropTable(actv, dlg1, CONS.DB.tname_IFM11);
			
			_case_Dlg_Db_Admin_lv__DropTable(actv, CONS.DB.tname_MemoPatterns);
			
			return;
			
		////////////////////////////////
		
		// restore
		
		////////////////////////////////
		} else if (item.equals(actv.getString(		// Drop table: cm7
				R.string.dlg_db_admin_item_restore_db))) {
			
			Methods.restore_DB(actv);
			
		////////////////////////////////
		
		// import: db
		
		////////////////////////////////
		} else if (item.equals(actv.getString(		// Drop table: cm7
				R.string.dlg_db_admin_item_import_db_file))) {
			
			Methods.import_DB(actv, dlg1);
			
			return;
			
//			Methods.restore_DB(actv);
			
		////////////////////////////////
		
		// import: patterns
		
		////////////////////////////////
		} else if (item.equals(actv.getString(		// Drop table: cm7
				R.string.dlg_db_admin_item_import_patterns))) {
			
			Methods.import_Patterns(actv, dlg1);
			
			return;
			
		} else {

		}
	
		
		dlg1.dismiss();
		
	}//case_Dlg_Db_Admin_lv(String item)

	private void 
	_case_Dlg_Db_Admin_lv__RefreshDB() {
		// TODO Auto-generated method stub
	
		Task_RefreshDB task_ = new Task_RefreshDB(actv);
		
		// debug
		Toast.makeText(actv, "Starting a task...", Toast.LENGTH_LONG)
				.show();
		
		task_.execute("Start");
		
		dlg1.dismiss();
		
//		Methods.refresh_MainDB(actv);
		
	}

	private void 
	_case_Dlg_Db_Admin_lv__CreateTable
	(Activity actv, String tableName) {
		// TODO Auto-generated method stub
		////////////////////////////////

		// validate: table exists?

		////////////////////////////////
		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
		
		boolean res = false;
		
		res = DBUtils.tableExists(actv, CONS.DB.dbName, tableName);
		
		if (res == true) {
			
			String msg = "Table exists => " + tableName;
			Methods_dlg.dlg_ShowMessage_SecondDialog(actv, msg, dlg1);
			
			return;
			
		}
		
		////////////////////////////////

		// dispatch

		////////////////////////////////
		if (tableName.equals(CONS.DB.tname_IFM11)) {
			
			res = dbu.createTable(actv, 
					tableName, 
					CONS.DB.col_names_IFM11, 
					CONS.DB.col_types_IFM11);
			
		} else if (tableName.equals(CONS.DB.tname_RefreshLog)) {

			res = dbu.createTable(actv, 
					tableName, 
					CONS.DB.col_names_refresh_log, 
					CONS.DB.col_types_refresh_log);
			
		} else if (tableName.equals(CONS.DB.tname_MemoPatterns)) {
			
			res = dbu.createTable(actv, 
					tableName, 
					CONS.DB.col_names_MemoPatterns, 
					CONS.DB.col_types_MemoPatterns);
			
		} else {
			
			String msg = "Unknown table=> " + tableName;
			Methods_dlg.dlg_ShowMessage_SecondDialog(actv, msg, dlg1);
			
			return;
			
		}
		
		////////////////////////////////

		// Report

		////////////////////////////////
		if (res == true) {
			
			// debug
			String msg_Toast = "Table => created: " + tableName;
			
			Methods_dlg.dlg_ShowMessage(actv, msg_Toast);
			
//			Toast.makeText(actv, msg_Toast, Toast.LENGTH_SHORT).show();
			
		} else {

			// debug
			String msg_Toast = "Table => not created: " + tableName;
			
			Methods_dlg.dlg_ShowMessage(actv, msg_Toast);
			
//			Toast.makeText(actv, msg_Toast, Toast.LENGTH_SHORT).show();
			
		}
		
	}//_case_Dlg_Db_Admin_lv__CreateTable

	private void 
	_case_Dlg_Db_Admin_lv__DropTable
	(Activity actv, String tableName) {

		////////////////////////////////

		// validate: table exists?

		////////////////////////////////
		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
		
		boolean res = false;
		
		res = DBUtils.tableExists(actv, CONS.DB.dbName, tableName);
		
		if (res == false) {
			
			String msg = "Table doesn't exist => " + tableName;
			Methods_dlg.dlg_ShowMessage_SecondDialog(actv, msg, dlg1);
			
			return;
			
		}

		////////////////////////////////

		// drop table

		////////////////////////////////
		Methods_dlg.conf_DropTable(actv, dlg1, tableName);
//		Methods_dlg.conf_DropTable(actv, dlg1, CONS.DB.tname_IFM11);
		
////		Methods.drop_Table(actv, CONS.DB.tname_CM7);
//		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
//		
//		boolean res = dbu.dropTable(actv, tableName);

	}

}//public class DialogOnItemClickListener implements OnItemClickListener
