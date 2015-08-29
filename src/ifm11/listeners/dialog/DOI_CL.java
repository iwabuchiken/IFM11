package ifm11.listeners.dialog;


import ifm11.items.ListItem;
import ifm11.items.TI;
import ifm11.items.WordPattern;
import ifm11.main.ImageActv;
import ifm11.main.R;
import ifm11.tasks.Task_RefreshDB;
import ifm11.utils.CONS;
import ifm11.utils.DBUtils;
import ifm11.utils.Methods;
import ifm11.utils.Methods_dlg;
import ifm11.utils.RGB;
import ifm11.utils.STD;
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
	Dialog d1;
	Dialog d2;
	
	//
	Vibrator vib;
	private String file_Name;
	private TI ti;
	
	//
//	Methods.DialogTags dlgTag = null;

	public DOI_CL(Activity actv, Dialog dlg) {
		// 
		this.actv = actv;
		this.d1 = dlg;
		
		vib = (Vibrator) actv.getSystemService(Context.VIBRATOR_SERVICE);
		
	}//public DialogOnItemClickListener(Activity actv, Dialog dlg)

	public DOI_CL(Activity actv, Dialog dlg, Dialog dlg2) {
		// 
		this.actv = actv;
		this.d1 = dlg;
		this.d2 = dlg2;
		
		vib = (Vibrator) actv.getSystemService(Context.VIBRATOR_SERVICE);
		
	}//public DialogOnItemClickListener(Activity actv, Dialog dlg)


	public DOI_CL
	(Activity actv, Dialog dlg1, String file_Name) {
		// TODO Auto-generated constructor stub
		
		this.actv = actv;
		this.d1 = dlg1;
		
		this.file_Name	= file_Name;
		
		vib = (Vibrator) actv.getSystemService(Context.VIBRATOR_SERVICE);

	}

	public DOI_CL(Activity actv, Dialog dlg1, TI ti) {
		// TODO Auto-generated constructor stub
		
		this.actv = actv;
		this.d1 = dlg1;

		this.ti		= ti;
		
		vib = (Vibrator) actv.getSystemService(Context.VIBRATOR_SERVICE);

	}

	public DOI_CL
	(Activity actv, Dialog dlg1, Dialog dlg2, TI ti) {
		// TODO Auto-generated constructor stub
		this.actv = actv;
		
		this.d1 = dlg1;
		this.d2 = dlg2;

		this.ti		= ti;
		
		vib = (Vibrator) actv.getSystemService(Context.VIBRATOR_SERVICE);
		
	}

	public DOI_CL
	(Activity actv) {
		// TODO Auto-generated constructor stub
	}

	//	@Override
	public void 
	onItemClick
	(AdapterView<?> parent, View v, int position, long id) {
		/*----------------------------
		 * Steps
		 * 1. Get tag
		 * 2. Vibrate
		 * 3. Switching
			----------------------------*/
		
		Tags.DialogItemTags tag = (Tags.DialogItemTags) parent.getTag();
//		
		vib.vibrate(CONS.Admin.vibLength_click);
		
		ListItem li;
		WordPattern	wp;
		String item;

//		String item = (String) parent.getItemAtPosition(position);
		
		/*----------------------------
		 * 3. Switching
			----------------------------*/
		switch (tag) {
		
		case ACTV_CANVAS_OPS://----------------------------------------------
			
			li = (ListItem) parent.getItemAtPosition(position);
			
			case_ACTV_CANVAS_OPS(li);
			
			break;// case dlg_add_memos_gv
			
		case ACTV_IMAGE_OPTMENU_LABS://----------------------------------------------
			
			li = (ListItem) parent.getItemAtPosition(position);
			
			case_ACTV_IMAGE_OPTMENU_LABS(li);
			
			break;// case dlg_add_memos_gv
			
//		case dlg_db_admin_lv://----------------------------------------------
		case DLG_DB_ADMIN_LV://----------------------------------------------
			
			li = (ListItem) parent.getItemAtPosition(position);
//			item = (String) parent.getItemAtPosition(position);
			
			case_DLG_DB_ADMIN_LV(li);
			
			break;// case dlg_add_memos_gv

//		case dlg_bmactv_list_long_click://----------------------------------------------
			
		case DLG_ADD_MEMOS_GV://----------------------------------------------
			
			wp = (WordPattern) parent.getItemAtPosition(position);
//			String word = (String) parent.getItemAtPosition(position);
			
			case_DLG_ADD_MEMOS_GV(wp);
//			case_DLG_ADD_MEMOS_GV(word);
//			Methods.add_pattern_to_text(dlg1, position, word);
			
			break;

		case DLG_ACTVMAIN_LONGCLICK://----------------------------------------------
			
			String choice = (String) parent.getItemAtPosition(position);
			
			case_DLG_ACTVMAIN_LONGCLICK(choice);
			
			break;// case dlg_bmactv_list_long_click

		case DLG_MOVE_FILES://----------------------------------------------
			
			choice = (String) parent.getItemAtPosition(position);
			
			case_DLG_MOVE_FILES(choice);
			
			break;// case dlg_bmactv_list_long_click
			
		case DLG_MOVE_FILES_FOLDER://----------------------------------------------
			
			choice = (String) parent.getItemAtPosition(position);
			
			case_DLG_MOVE_FILES_FOLDER(choice);
			
			break;// case dlg_bmactv_list_long_click
			
		case DLG_ACTV_TN_LONG_CLICK://----------------------------------------------
			
			choice = (String) parent.getItemAtPosition(position);
			
			case_DLG_ACTV_TN_LONG_CLICK(choice);
			
			break;// case dlg_bmactv_list_long_click
			
		case DLG_ADMIN_PATTERNS_LV://----------------------------------------------
			
			choice = (String) parent.getItemAtPosition(position);
			
			case_DLG_ADMIN_PATTERNS_LV(choice);
			
			break;// case dlg_bmactv_list_long_click
			
//		case dlg_delete_patterns_gv://----------------------------------------------
		case DLG_DELETE_PATTERNS_GV://----------------------------------------------
			
			wp = (WordPattern) parent.getItemAtPosition(position);
			
			case_DLG_DELETE_PATTERNS_GV(wp);
			
			break;// case dlg_bmactv_list_long_click
			
		case DLG_ACTV_TN_LIST_UPLOAD://----------------------------------------------
			
			choice = (String) parent.getItemAtPosition(position);
			
			case_DLG_ACTV_TN_LIST_UPLOAD(choice);
			
			break;// case dlg_bmactv_list_long_click
			
		case ACTV_MAIN_OPTMENU_OTHERS://----------------------------------------------
			
			li = (ListItem) parent.getItemAtPosition(position);
			
			case_ACTV_MAIN_OPTMENU_OTHERS(li);
//			case_Admin_LV(li);
			
			break;// case dlg_add_memos_gv

		case ACTV_MAIN_ADMIN_LV_OPS://----------------------------------------------
			
			li = (ListItem) parent.getItemAtPosition(position);
			
			case_ACTV_MAIN_ADMIN_LV_OPS(li);
//			case_Admin_LV(li);
			
			break;// case dlg_add_memos_gv
			
		case ACTV_IMAGE_ADD_MEMO_LV_1://----------------------------------------------
			
			wp = (WordPattern) parent.getItemAtPosition(position);
			
			case_ACTV_IMAGE_ADD_MEMO_LV_1(wp);
//			case_Admin_LV(li);
			
			break;// case dlg_add_memos_gv
			
		case ACTV_IMAGE_ADD_MEMO_LV_2://----------------------------------------------
		case ACTV_IMAGE_ADD_MEMO_LV_3://----------------------------------------------
			
			wp = (WordPattern) parent.getItemAtPosition(position);
			
			case_ACTV_IMAGE_ADD_MEMO_LV_2(wp);
//			case_Admin_LV(li);
			
			break;// case dlg_add_memos_gv
			
		default:
			break;
		}//switch (tag)
		
	}//public void onItemClick(AdapterView<?> parent, View v, int position, long id)

	private void 
	case_ACTV_CANVAS_OPS
	(ListItem li) {
		// TODO Auto-generated method stub

		if (li.getText().equals(actv.getString(
				R.string.menu_actv_canvas_Ops__GetRGB))) {

			RGB.show_RGB(actv);
			
			ImageActv.bm.recycle();
			
			// Log
			String msg_Log = "ImageActv.bm => recycled";
			Log.d("DOI_CL.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			d1.dismiss();
			
//		} else if (li.getText().equals(actv.getString(
//				R.string.opt_Menu_LABS__Rotate_Image))) {
			
//			Methods.roate_Image(actv);
//			
//			d1.dismiss();
			
		} else {
			
		}

	}//case_ACTV_CANVAS_OPS
	

	private void 
	case_ACTV_IMAGE_OPTMENU_LABS
	(ListItem li) {
		// TODO Auto-generated method stub
		if (li.getText().equals(actv.getString(
				R.string.opt_Menu_LABS__Change_RGB))) {

			Methods.change_RGB(actv);
			
			d1.dismiss();
			
		} else if (li.getText().equals(actv.getString(
				R.string.opt_Menu_LABS__Rotate_Image))) {
			
			Methods.roate_Image(actv);
			
			d1.dismiss();
			
		} else if (li.getText().equals(actv.getString(
				R.string.opt_Menu_LABS__RedColor_zero))) {

			Methods.RGB_Zero(actv, 
						actv.getString(R.string.opt_Menu_LABS__RedColor_zero));
			
			d1.dismiss();
			
		} else if (li.getText().equals(actv.getString(
				R.string.opt_Menu_LABS__BlueColor_zero))) {
			
			Methods.RGB_Zero(actv, 
					actv.getString(R.string.opt_Menu_LABS__BlueColor_zero));
			
			d1.dismiss();
			
		} else if (li.getText().equals(actv.getString(
				R.string.opt_Menu_LABS__GreenColor_zero))) {
			
			Methods.RGB_Zero(actv, 
					actv.getString(R.string.opt_Menu_LABS__GreenColor_zero));
			
			d1.dismiss();
			
		} else if (li.getText().equals(actv.getString(
				R.string.opt_Menu_LABS__Canvas))) {
			
			Methods.start_Activity_CanvasActv(actv);
			
			d1.dismiss();
			
		}
			

		
//		d1.dismiss();
		
	}//case_ACTV_IMAGE_OPTMENU_LABS

	private void 
	case_ACTV_IMAGE_ADD_MEMO_LV_2
	(WordPattern wp) {
		// TODO Auto-generated method stub
		
		int res = Methods.add_WP_to_Memo(actv, d1, wp);
		
//		-1 find pattern => failed
//				-2 SQLException
//				1 update => executed

		
	}//case_ACTV_IMAGE_ADD_MEMO_LV_1

	/******************************
		symbols => contain such symbols as "[]", "「」"<br>
		=> cursor needs to be placed between the symbols<br>
	 ******************************/
	private void 
	case_ACTV_IMAGE_ADD_MEMO_LV_1
	(WordPattern wp) {
		// TODO Auto-generated method stub
		////////////////////////////////

		// vars

		////////////////////////////////
		int res;
		
		////////////////////////////////

		// get: word

		////////////////////////////////
		String w = wp.getWord();
		
		////////////////////////////////

		// dispatch

		////////////////////////////////
		if (Methods.is_SpecialChars(actv, w)) {
			
			res = Methods.add_WP_to_Memo_SpecialChars(actv, d1, wp);
			
		} else {
			
			res = Methods.add_WP_to_Memo(actv, d1, wp);
			
		}//if(Methods.is_SpecialChars(actv, w))
		
		
		
//		-1 find pattern => failed
//				-2 SQLException
//				1 update => executed
		
		
	}//case_ACTV_IMAGE_ADD_MEMO_LV_1
	
	private void 
	case_ACTV_MAIN_ADMIN_LV_OPS
	(ListItem li) {
		// TODO Auto-generated method stub
		
		if (li.getText().equals(actv.getString(
				R.string.dlg_db_ops_item_drop_create_tbl_patterns))) {

			Methods_dlg.conf_DropCreate_TablePatterns(actv, d1, d2);
			
		} else if (li.getText().equals(actv.getString(
				R.string.dlg_db_ops_item_drop_create_tbl_admin))) {
			
			Methods_dlg.conf_DropCreate_Table_Admin(actv, d1, d2);
			
		} else if (li.getText().equals(actv.getString(
				R.string.dlg_db_ops_item_import_patterns_from_previous))) {
			
			Methods.import_Patterns_Previous(actv, d1, d2);
			
		} else {
			
			String msg = "Unknow item => " + li.getText();
			Methods_dlg.dlg_ShowMessage(actv, msg, R.color.gold2);
			
			return;
			
		}
		
	}//case_ACTV_MAIN_ADMIN_LV_OPS

	private void 
	case_ACTV_MAIN_OPTMENU_OTHERS
	(ListItem item) {
		// TODO Auto-generated method stub

		////////////////////////////////

		// dispatch

		////////////////////////////////
		if (item.getText().equals(actv.getString(
				R.string.dlg_actv_main_other_see_log))) {

			Methods.start_Activity_LogActv(actv, d1);
			
		} else if (item.getText().equals(actv.getString(
				R.string.dlg_actv_main_other_Import_From10))) {
			
			Methods.importData_From_IFM10(actv, d1);
			
		} else if (item.getText().equals(actv.getString(
				R.string.dlg_actv_main_other_Create_TNs))) {
			
			String msg = "Sorry. Not in use";
			Methods_dlg.dlg_ShowMessage_SecondDialog(actv, msg, d1);
			
//			Methods.create_TNs_main_V2(actv, d1);
//			Methods.create_TNs_main(actv, d1);
			
		} else if (item.getText().equals(actv.getString(
				R.string.dlg_actv_main_other_Fix_DB))) {
			
			String msg = "Sorry. Not in use";
			Methods_dlg.dlg_ShowMessage_SecondDialog(actv, msg, d1);
//			Methods_dlg.dlg_ShowMessage(actv, msg, R.color.gold2);

			
//			Methods.fix_DB(actv, d1);
//			Methods.create_TNs_main(actv, d1);
			
		} else {
			
		}

	}//case_ACTV_MAIN_OPTMENU_OTHERS

	
	private void 
	case_DLG_ACTV_TN_LIST_UPLOAD
	(String choice) {
		// TODO Auto-generated method stub
		if (choice.equals(actv.getString(
							R.string.dlg_upload_image_item_remote))) {
			
			Methods_dlg.conf_Upload_Image(actv, d1, d2, ti);
			
		} else if (choice.equals(actv.getString(
						R.string.dlg_upload_image_item_email))) {//if (choice.equals(actv.getString(R.string.generic_tv_delete))))

			String msg = actv.getString(
					R.string.dlg_upload_image_item_email)
					+ " => "
					+ "Sorry. Under construction";
			
			Methods_dlg.dlg_ShowMessage(actv, msg, R.color.gold2);
			
			
		} else {
			
			String msg = "Unknown choice => " + choice;
			Methods_dlg.dlg_ShowMessage(actv, msg, R.color.red);
			
		}
		
	}//case_DLG_ACTV_TN_LIST_UPLOAD

	private void 
	case_DLG_DELETE_PATTERNS_GV
	(WordPattern wp) {
		// TODO Auto-generated method stub

		Methods_dlg.conf_Delete_Pattern(actv, d1, d2, wp);
		
		
	}//case_DLG_DELETE_PATTERNS_GV

	private void 
	case_DLG_ADMIN_PATTERNS_LV
	(String choice) {
		// TODO Auto-generated method stub
		if (choice.equals(actv.getString(R.string.generic_tv_register))) {
			
			Methods_dlg.dlg_RegisterPatterns(actv, d1);
			
		} else if (choice.equals(actv.getString(R.string.generic_tv_edit))) {//if (choice.equals(actv.getString(R.string.generic_tv_delete))))
			
//			Methods_dlg.dlg_EditTI(actv, dlg1, ti);
			
		} else if (choice.equals(actv.getString(R.string.generic_tv_delete))) {//if (choice.equals(actv.getString(R.string.generic_tv_delete))))
			
//			Methods_dlg.dlg_EditTI(actv, dlg1, ti);
			Methods_dlg.dlg_DeletePatterns(actv, d1);
			
		} else {
			
		}
		
		
	}//case_DLG_ADMIN_PATTERNS_LV

	private void 
	case_DLG_ACTV_TN_LONG_CLICK
	(String choice) {
		// TODO Auto-generated method stub

		if (choice.equals(actv.getString(R.string.generic_tv_delete))) {
			
			Methods_dlg.conf_Delete_TI(actv, d1, ti);
			
		} else if (choice.equals(actv.getString(R.string.generic_tv_edit))) {//if (choice.equals(actv.getString(R.string.generic_tv_delete))))
			
			Methods_dlg.dlg_EditTI(actv, d1, ti);
			
		} else if (choice.equals(actv.getString(
						R.string.generic_tv_upload))) {//if (choice.equals(actv.getString(R.string.generic_tv_delete))))
			
			Methods_dlg.upload_Image(actv, d1, ti);
			
//			Methods_dlg.uploadImageFile_main(actv, dlg1, ti);
//			Methods_dlg.dlg_confirm_uploadImageFile(actv, dlg1, ti);
			
		}//if (choice.equals(actv.getString(R.string.generic_tv_delete))))

		
		
	}//case_DLG_ACTV_TN_LONG_CLICK

	private void 
	case_DLG_MOVE_FILES_FOLDER
	(String choice) {
		// TODO Auto-generated method stub
		
		////////////////////////////////

		// validate: table name and the choice => same?

		////////////////////////////////
//		String tname_New = Methods.conv_CurrentPath_to_TableName(choice);
		String tname_New = Methods.conv_CurrentPathMove_to_TableName(choice);
	
		String pref_CurPath = Methods.get_Pref_String(
				actv, 
				CONS.Pref.pname_MainActv, 
				CONS.Pref.pkey_CurrentPath, 
				null);

		String tname_Current = Methods.conv_CurrentPath_to_TableName(pref_CurPath);
		
		if (tname_Current.equals(tname_New)) {
			
			String msg = "You are choosing the same table as the current one";
			Methods_dlg.dlg_ShowMessage(actv, msg);
			
			return;
			
		}
		
		////////////////////////////////

		// get: current path-move

		////////////////////////////////
		String curPath_Move = Methods.get_Pref_String(
						actv, 
						CONS.Pref.pname_MainActv, 
						CONS.Pref.pkey_TNActv__CurPath_Move, 
						CONS.DB.tname_IFM11);

		////////////////////////////////

		// choice => Upper dir? ("..")

		////////////////////////////////
		if (choice.equals(CONS.Admin.dirString_UpperDir)) {

			////////////////////////////////

			// Uppder dir => top dir?

			////////////////////////////////
			if (curPath_Move.equals(CONS.DB.tname_IFM11)) {

			
				Methods_dlg.conf_MoveFiles__Folder_Top(actv, d1, d2);

			////////////////////////////////

			// Upper dir => not the top dir

			////////////////////////////////
			} else {
				
				Methods.go_Up_Dir_Move(actv);
				
			}
			
			return;
			
		}

		
		////////////////////////////////

		// dlg: confirm

		////////////////////////////////
		Methods_dlg.conf_MoveFiles__Folder(actv, d1, d2, choice);
		
	}

	private void 
	case_DLG_MOVE_FILES
	(String choice) {
		// TODO Auto-generated method stub
	
		////////////////////////////////

		// dispatch

		////////////////////////////////
		if (choice.equals(actv.getString(
				R.string.dlg_move_files_item_folder))) {

			Methods_dlg.dlg_MoveFiles__Folder(actv, d1);
			
		} else if (choice.equals(actv.getString(		// Refresh DB
				R.string.dlg_move_files_item_remote))) {
			
			Methods_dlg.dlg_MoveFiles__Remote(actv, d1);
			
		}
		
	}//case_DLG_MOVE_FILES

	private void 
	case_DLG_ADD_MEMOS_GV
	(WordPattern wp) {
//		(String word) {
		// TODO Auto-generated method stub

		Methods.add_Memo_to_GridView(actv, d1, wp.getWord());
//		Methods.add_Memo_to_GridView(actv, dlg1, word);
		
	}//case_DLG_ADD_MEMOS_GV

	private void
	case_DLG_DB_ADMIN_LV(ListItem li) {
		// TODO Auto-generated method stub
		
		// Log
		String msg_Log = "case_Dlg_Db_Admin_lv()";
		Log.d("DOI_CL.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		////////////////////////////////

		// Dispatch

		////////////////////////////////
		if (li.getText().equals(actv.getString(
				R.string.dlg_db_admin_item_backup_db))) {
			
			Methods.backup_DB(actv);
			
		} else if (li.getText().equals(actv.getString(		// Refresh DB
				R.string.dlg_db_admin_item_refresh_db))) {
			
			_case_Dlg_Db_Admin_lv__RefreshDB();
//			Methods.refresh_MainDB(actv);
			
		} else if (li.getText().equals(actv.getString(		// Refresh DB
				R.string.dlg_db_admin_item_upload_db))) {
			
			_case_Dlg_Db_Admin_lv__UploadDB();
			
			return;
			
//			Methods.refresh_MainDB(actv);
			
		} else if (li.getText().equals(actv.getString(		// Refresh DB
				R.string.dlg_db_admin_item_operations))) {
			
			_case_Dlg_Db_Admin_lv__Operations(actv);
			
			return;
			
//			Methods.refresh_MainDB(actv);
			
		////////////////////////////////

		// create/drop: ifm11

		////////////////////////////////
		} else if (li.getText().equals(actv.getString(		// Create table: cm7
				R.string.dlg_db_admin_item_create_table_ifm11))) {
			
			_case_Dlg_Db_Admin_lv__CreateTable(actv, CONS.DB.tname_IFM11);
//			Methods.create_Table(actv, CONS.DB.tname_CM7);
			
		} else if (li.getText().equals(actv.getString(		// Drop table: cm7
				R.string.dlg_db_admin_item_drop_table_ifm11))) {
			
//			Methods_dlg.conf_DropTable(actv, dlg1, CONS.DB.tname_IFM11);
			
			_case_Dlg_Db_Admin_lv__DropTable(actv, CONS.DB.tname_IFM11);
			
			return;
			
		////////////////////////////////
		
		// create/drop: refresh log
		
		////////////////////////////////
		} else if (li.getText().equals(actv.getString(		// Create table: cm7
				R.string.dlg_db_admin_item_create_table_refresh_log))) {
			
			_case_Dlg_Db_Admin_lv__CreateTable(actv, CONS.DB.tname_RefreshLog);
//			Methods.create_Table(actv, CONS.DB.tname_CM7);
			
		} else if (li.getText().equals(actv.getString(		// Drop table: cm7
				R.string.dlg_db_admin_item_drop_table_refresh_log))) {
			
//			Methods_dlg.conf_DropTable(actv, dlg1, CONS.DB.tname_IFM11);
			
			_case_Dlg_Db_Admin_lv__DropTable(actv, CONS.DB.tname_RefreshLog);
			
			return;
			
		////////////////////////////////
		
		// create/drop: memo_patterns
		
		////////////////////////////////
		} else if (li.getText().equals(actv.getString(		// Create table: cm7
				R.string.dlg_db_admin_item_create_table_memo_patterns))) {
			
			_case_Dlg_Db_Admin_lv__CreateTable(actv, CONS.DB.tname_MemoPatterns);
//			Methods.create_Table(actv, CONS.DB.tname_CM7);
			
		} else if (li.getText().equals(actv.getString(		// Drop table: cm7
				R.string.dlg_db_admin_item_drop_table_memo_patterns))) {
			
//			Methods_dlg.conf_DropTable(actv, dlg1, CONS.DB.tname_IFM11);
			
			_case_Dlg_Db_Admin_lv__DropTable(actv, CONS.DB.tname_MemoPatterns);
			
			return;
			
		////////////////////////////////
		
		// restore
		
		////////////////////////////////
		} else if (li.getText().equals(actv.getString(		// Drop table: cm7
				R.string.dlg_db_admin_item_restore_db))) {
			
			STD.restore_DB(actv);
			
		////////////////////////////////
		
		// import: db
		
		////////////////////////////////
		} else if (li.getText().equals(actv.getString(		// Drop table: cm7
				R.string.dlg_db_admin_item_import_db_file))) {
			
			STD.import_DB(actv, d1);
			
			return;
			
//			Methods.restore_DB(actv);
			
		////////////////////////////////
		
		// import: patterns
		
		////////////////////////////////
		} else if (li.getText().equals(actv.getString(		// Drop table: cm7
				R.string.dlg_db_admin_item_import_patterns))) {
			
			Methods.import_Patterns(actv, d1);
			
			return;
			
			////////////////////////////////
			
			// exec sql
			
			////////////////////////////////
		} else if (li.getText().equals(
				CONS.DB.Sqls._20140817_154650_addCol_IFM11_UpdatedAt_TITLE)) {
			
			Methods_dlg.conf_Exec_Sql(
							actv, 
							d1, 
							CONS.DB.Sqls._20140817_154650_addCol_IFM11_UpdatedAt_TITLE);
			
//			Methods.exec_Sql(actv, dlg1, CONS.DB.Sqls.addCol_IFM11_Updated_at);
			
			return;
			
		} else if (li.getText().equals(
				CONS.DB.Sqls._20140817_154650_addCol_IFM11_UpdatedAt_TITLE)) {
			
			Methods_dlg.conf_Exec_Sql(
					actv, 
					d1, 
					CONS.DB.Sqls._20140817_154650_addCol_IFM11_UpdatedAt_TITLE);
			
//			Methods.exec_Sql(actv, dlg1, CONS.DB.Sqls.addCol_IFM11_Updated_at);
			
			return;
			
		} else {

		}
	
		
		d1.dismiss();
		
	}//case_Dlg_Db_Admin_lv(String item)

	private void 
	_case_Dlg_Db_Admin_lv__Operations
	(Activity actv) {
		// TODO Auto-generated method stub
		
		Methods_dlg.dlg_ACTV_MAIN_Operations(actv, d1);
		
		
	}//_case_Dlg_Db_Admin_lv__Operations

	private void 
	_case_Dlg_Db_Admin_lv__UploadDB() {
		// TODO Auto-generated method stub
		
		Methods_dlg.conf_Upload_DB(actv, d1);
		
	}//_case_Dlg_Db_Admin_lv__UploadDB

	private void 
	_case_Dlg_Db_Admin_lv__RefreshDB() {
		// TODO Auto-generated method stub
	
		Task_RefreshDB task_ = new Task_RefreshDB(actv);
		
		// debug
		Toast.makeText(actv, "Starting a task...", Toast.LENGTH_LONG)
				.show();
		
		task_.execute("Start");
		
		d1.dismiss();
		
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
			Methods_dlg.dlg_ShowMessage_SecondDialog(actv, msg, d1);
			
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
			Methods_dlg.dlg_ShowMessage_SecondDialog(actv, msg, d1);
			
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
			Methods_dlg.dlg_ShowMessage_SecondDialog(actv, msg, d1);
			
			return;
			
		}

		////////////////////////////////

		// drop table

		////////////////////////////////
		Methods_dlg.conf_DropTable(actv, d1, tableName);
//		Methods_dlg.conf_DropTable(actv, dlg1, CONS.DB.tname_IFM11);
		
////		Methods.drop_Table(actv, CONS.DB.tname_CM7);
//		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
//		
//		boolean res = dbu.dropTable(actv, tableName);

	}

	private void 
	case_DLG_ACTVMAIN_LONGCLICK
	(String choice) {
		// TODO Auto-generated method stub

		if (choice.equals(actv.getString(
				R.string.dlg_actvmain_lv_delete))) {	// Edit

			Methods_dlg.conf_Delete_Folder(actv, d1, file_Name, choice);
			
		} else {//if (item.equals(actv.getString(R.string.generic_tv_edit)))
			
			
		}
		
	}//case_DLG_ACTVMAIN_LONGCLICK

}//public class DialogOnItemClickListener implements OnItemClickListener
