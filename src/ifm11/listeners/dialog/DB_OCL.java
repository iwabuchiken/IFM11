package ifm11.listeners.dialog;

import ifm11.items.TI;
import ifm11.items.WordPattern;
import ifm11.main.R;
import ifm11.tasks.Task_FTP;
import ifm11.utils.CONS;
import ifm11.utils.Methods;
import ifm11.utils.Methods_dlg;
import ifm11.utils.Tags;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DB_OCL implements OnClickListener {
	/*----------------------------
	 * Fields
		----------------------------*/
	//
	Activity actv;
	Dialog dlg1;
	Dialog dlg2;		//=> Used in dlg_input_empty_btn_XXX
	Dialog dlg3;

	//
	Vibrator vib;
	
	// Used in => Methods.dlg_addMemo(Activity actv, long file_id, String tableName)
	long db_Id;
	String tableName;
	private String item_Name;	// Methods_dlg.conf_DropTable
	private TI ti;
	private WordPattern wp;
	
	public DB_OCL(Activity actv, Dialog dlg1) {
		//
		this.actv = actv;
		this.dlg1 = dlg1;
		
		//
//		vib = (Vibrator) actv.getSystemService(actv.VIBRATOR_SERVICE);
		CONS.Admin.vib = (Vibrator) actv.getSystemService(Context.VIBRATOR_SERVICE);
	}

	public DB_OCL(Activity actv, Dialog dlg1,
			Dialog dlg2) {
		//
		this.actv = actv;
		this.dlg1 = dlg1;
		this.dlg2 = dlg2;
		
		//
//		vib = (Vibrator) actv.getSystemService(actv.VIBRATOR_SERVICE);
		CONS.Admin.vib = (Vibrator) actv.getSystemService(Context.VIBRATOR_SERVICE);
		
	}

	public DB_OCL(Activity actv, Dialog dlg1,
			Dialog dlg2, Dialog dlg3) {
		//
		this.actv = actv;
		this.dlg1 = dlg1;
		this.dlg2 = dlg2;
		this.dlg3 = dlg3;
		
		//
//		vib = (Vibrator) actv.getSystemService(actv.VIBRATOR_SERVICE);
		CONS.Admin.vib = (Vibrator) actv.getSystemService(Context.VIBRATOR_SERVICE);
		
	}

	public DB_OCL(Activity actv, Dialog dlg1, long file_id, String tableName) {
		// 
		this.actv = actv;
		this.dlg1 = dlg1;
		
		this.tableName = tableName;
		
		this.db_Id = file_id;
		
//		vib = (Vibrator) actv.getSystemService(actv.VIBRATOR_SERVICE);
		CONS.Admin.vib = (Vibrator) actv.getSystemService(Context.VIBRATOR_SERVICE);
		
	}//public DialogButtonOnClickListener(Activity actv, Dialog dlg1, long file_id, String tableName)


	public DB_OCL
	(Activity actv, Dialog dlg1, Dialog dlg2, String item_Name) {
		// TODO Auto-generated constructor stub
		
		this.actv = actv;
		this.dlg1 = dlg1;
		this.dlg2 = dlg2;

		this.item_Name	= item_Name;
		
//		vib = (Vibrator) actv.getSystemService(actv.VIBRATOR_SERVICE);
		CONS.Admin.vib = (Vibrator) actv.getSystemService(Context.VIBRATOR_SERVICE);
		
	}

	public 
	DB_OCL
	(Activity actv, Dialog dlg1, long db_id) {
		// TODO Auto-generated constructor stub
		
		this.actv	= actv;
		this.dlg1	= dlg1;
		
		this.db_Id	= db_id;
		
		CONS.Admin.vib = (Vibrator) actv.getSystemService(Context.VIBRATOR_SERVICE);

	}

	public 
	DB_OCL
	(Activity actv, Dialog dlg1, Dialog dlg2, TI ti) {
		// TODO Auto-generated constructor stub
		
		this.actv	= actv;
		this.dlg1	= dlg1;
		
		this.dlg2	= dlg2;
		this.ti		= ti;
		
		CONS.Admin.vib = (Vibrator) actv.getSystemService(Context.VIBRATOR_SERVICE);

	}

	public DB_OCL
	(Activity actv, 
		Dialog dlg1, Dialog dlg2, Dialog dlg3,
		WordPattern wp) {
		// TODO Auto-generated constructor stub
		
		this.actv	= actv;
		
		this.dlg1	= dlg1;
		this.dlg2	= dlg2;
		this.dlg3	= dlg3;
		
		this.wp		= wp;
		
		CONS.Admin.vib = (Vibrator) actv.getSystemService(Context.VIBRATOR_SERVICE);
		
	}

	public DB_OCL
	(Activity actv, 
		Dialog dlg1, Dialog dlg2, Dialog d3, 
		TI ti) {
		// TODO Auto-generated constructor stub
		this.actv	= actv;
		
		this.dlg1	= dlg1;
		this.dlg2	= dlg2;
		this.dlg3	= d3;

		this.ti		= ti;
		
		CONS.Admin.vib = (Vibrator) actv.getSystemService(Context.VIBRATOR_SERVICE);
		
	}

	public void onClick(View v) {
		//
		Tags.DialogTags tag_name = (Tags.DialogTags) v.getTag();

		CONS.Admin.vib.vibrate(CONS.Admin.vibLength_click);
		
		// Log
		Log.d("DialogButtonOnClickListener.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "tag_name.name()=" + tag_name.name());
		
		//
		switch (tag_name) {
		
		case GENERIC_DISMISS://------------------------------------------------
			
			dlg1.dismiss();
			
			break;

		case GENERIC_DISMISS_SECOND_DIALOG: // ----------------------------------------------------
			
			dlg2.dismiss();
			
			break;// case dlg_generic_dismiss_second_dialog

		case GENERIC_DISMISS_THIRD_DIALOG://------------------------------------------------
			
			dlg3.dismiss();
			
			break;

		case DROP_TABLE_OK://------------------------------------------------
			
			case_DROP_TABLE_OK();
			
			break;
			
		case DLG_ADD_MEMOS_BT_ADD://------------------------------------------------
			
			case_DLG_ADD_MEMOS_BT_ADD();
			
			break;
			
		case DLG_CREATE_DIR_OK://------------------------------------------------
			
			case_DLG_CREATE_DIR_OK();
			
			break;
			
		case DLG_CONFIRM_CREATE_FOLDER_OK://------------------------------------------------
			
			case_DLG_CONFIRM_CREATE_FOLDER_OK();
			
			break;
			
		case DLG_DELETE_FOLDER_CONF_OK://------------------------------------------------
			
			dlg_DLG_DELETE_FOLDER_CONF_OK();
			
			break;
			
		case DLG_CONF_MOVE_FILES_FOLDER_OK://------------------------------------------------
			
			dlg_DLG_CONF_MOVE_FILES_FOLDER_OK();
			
			break;
			
		case DLG_CONF_MOVE_FILES_FOLDER_TOP_OK://------------------------------------------------
			
			dlg_DLG_CONF_MOVE_FILES_FOLDER_OK();
			
			break;
			
		case DLG_SEARCH_OK://------------------------------------------------
			
			dlg_DLG_SEARCH_OK();
			
			break;
			
		case DLG_DELETE_TI_CONF_OK://------------------------------------------------
			
			dlg_DLG_DELETE_TI_CONF_OK();
			
			break;
			
		case DLG_EDIT_TI_BT_OK://------------------------------------------------
			
			case_DLG_EDIT_TI_BT_OK();
			
			break;
			
		case DLG_REGISTER_PATTERNS_REGISTER://------------------------------------------------
			
			case_DLG_REGISTER_PATTERNS_REGISTER();
			
			break;
			
		case DLG_DELETE_PATTERN_CONF_OK://------------------------------------------------
			
			case_DLG_DELETE_PATTERN_CONF_OK();
			
			break;
			
		case EXEC_SQL_OK://------------------------------------------------
			
			case_EXEC_SQL_OK();
			
			break;
			
		case UPLOAD_REMOTE_OK://------------------------------------------------
			
			case_UPLOAD_REMOTE_OK();
			
			break;
			
		case UPLOAD_DB_FILE_OK://------------------------------------------------
			
			case_UPLOAD_DB_FILE_OK();
			
			break;
			
		case UPLOAD_REMOTE_MULTIPLE_IMAGES_OK://------------------------------------------------
			
			case_UPLOAD_REMOTE_MULTIPLE_IMAGES_OK();
			
			break;
			
		default: // ----------------------------------------------------
			break;
		}//switch (tag_name)
	}//public void onClick(View v)

	private void 
	case_UPLOAD_REMOTE_MULTIPLE_IMAGES_OK() {
		// TODO Auto-generated method stub
		
		////////////////////////////////

		// validate: network status

		////////////////////////////////
		boolean res = Methods.isOnline(actv);
		
		if (res == false) {
			
			String msg = "Sorry. Network is not ready";
			Methods_dlg.dlg_ShowMessage(actv, msg, R.color.gold2);
			
			return;
			
		} else {
			
			// Log
			String msg_Log = "Network is ready";
			Log.d("DB_OCL.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
		}
		
		////////////////////////////////

		// task

		////////////////////////////////
		Task_FTP task = new Task_FTP(
				actv, dlg1, dlg2,
				CONS.Remote.FtpType.IMAGE_MULTIPLE.toString());

		task.execute(CONS.Remote.FtpType.IMAGE_MULTIPLE.toString());
		
	}//case_UPLOAD_REMOTE_MULTIPLE_IMAGES_OK

	private void 
	case_UPLOAD_DB_FILE_OK() {
		// TODO Auto-generated method stub

		////////////////////////////////

		// validate: network status

		////////////////////////////////
		boolean res = Methods.isOnline(actv);
		
		if (res == false) {
			
			String msg = "Sorry. Network is not ready";
			Methods_dlg.dlg_ShowMessage(actv, msg, R.color.gold2);
			
			return;
			
		} else {
			
			// Log
			String msg_Log = "Network is ready";
			Log.d("DB_OCL.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
		}
		
//		////////////////////////////////
//
//		// get view: checkbox
//
//		////////////////////////////////
//		CheckBox cb = (CheckBox) dlg2.findViewById(
//							R.id.dlg_tmpl_confirm_simple_cb_delete_file);

		////////////////////////////////

		// task

		////////////////////////////////
		Task_FTP task = new Task_FTP(
							actv, dlg1, dlg2,
							CONS.Remote.FtpType.DB_FILE.toString()
							);
//		cb.isChecked());
		
		task.execute(CONS.Remote.FtpType.DB_FILE.toString());
		
	}//case_UPLOAD_DB_FILE_OK

	/******************************
		Upload image files
	 ******************************/
	private void 
	case_UPLOAD_REMOTE_OK() {
		// TODO Auto-generated method stub
		
		////////////////////////////////

		// validate: network status

		////////////////////////////////
		boolean res = Methods.isOnline(actv);
		
		if (res == false) {
			
			String msg = "Sorry. Network is not ready";
			Methods_dlg.dlg_ShowMessage(actv, msg, R.color.gold2);
			
			return;
			
		} else {
			
			// Log
			String msg_Log = "Network is ready";
			Log.d("DB_OCL.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
		}
		
		////////////////////////////////

		// get view: checkbox

		////////////////////////////////
		CheckBox cb = (CheckBox) dlg3.findViewById(
							R.id.dlg_tmpl_confirm_simple_cb_delete_file);
		
		Task_FTP task = new Task_FTP(
							actv, dlg1, dlg2, dlg3,
							CONS.Remote.FtpType.IMAGE.toString(),
							ti, cb.isChecked());
		
		task.execute(CONS.Remote.FtpType.IMAGE.toString());
//		task.execute("abc");
		
	}//case_UPLOAD_REMOTE_OK

	private void 
	case_EXEC_SQL_OK() {
		// TODO Auto-generated method stub
		////////////////////////////////

		// dispatch

		////////////////////////////////
		if (item_Name.equals(CONS.DB.Sqls._20140817_154650_addCol_IFM11_UpdatedAt_TITLE)) {
			
			Methods.exec_Sql(actv, dlg1, dlg2, item_Name);
			
		} else {

			// Log
			String msg_Log = "Unknown case => " + item_Name;
			Log.d("DB_OCL.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
		}
		
	}

	private void 
	case_DLG_DELETE_PATTERN_CONF_OK() {
		// TODO Auto-generated method stub
		
		Methods.del_Pattern(actv, dlg1, dlg2, dlg3, wp);
		
	}

	private void 
	case_DLG_REGISTER_PATTERNS_REGISTER() {
		// TODO Auto-generated method stub
		
		Methods_dlg.dlg_register_patterns_isInputEmpty(actv, dlg1, dlg2);
		
	}//case_DLG_REGISTER_PATTERNS_REGISTER

	private void 
	case_DLG_EDIT_TI_BT_OK() {
		// TODO Auto-generated method stub
		
		Methods.edit_TI(actv, dlg1, dlg2, ti);
		
	}//DLG_EDIT_TI_BT_OK

	private void 
	dlg_DLG_DELETE_TI_CONF_OK() {
		// TODO Auto-generated method stub
		
		Methods.delete_TI(actv, dlg1, dlg2, ti);
		
		
	}//dlg_DLG_DELETE_TI_CONF_OK

	private void 
	dlg_DLG_SEARCH_OK() {
		// TODO Auto-generated method stub
		
		Methods.searchItem(actv, dlg1);
		
	}

	private void 
	dlg_DLG_CONF_MOVE_FILES_FOLDER_OK() {
		// TODO Auto-generated method stub

		Methods.move_Files(actv, dlg1, dlg2, dlg3);
		
		
	}//dlg_DLG_CONF_MOVE_FILES_FOLDER_OK

	private void 
	case_DLG_CONFIRM_CREATE_FOLDER_OK() {
		// TODO Auto-generated method stub
	
		Methods.create_Dir(actv, dlg1, dlg2);
		
	}

	private void 
	case_DLG_CREATE_DIR_OK() {
		// TODO Auto-generated method stub
	
		Methods_dlg.dlg_IsEmpty(actv, dlg1);
		
	}

	private void 
	case_DLG_ADD_MEMOS_BT_ADD() {
		// TODO Auto-generated method stub
		
		Methods.add_Memo(actv, dlg1, db_Id);
		
	}//case_DLG_ADD_MEMOS_BT_ADD

	private void 
	case_DROP_TABLE_OK() {
		// TODO Auto-generated method stub
		
		Methods.drop_Table(actv, dlg1, dlg2, item_Name);
		
	}//case_DROP_TABLE_OK

	private void 
	dlg_DLG_DELETE_FOLDER_CONF_OK() {
		// TODO Auto-generated method stub

		// Log
		String msg_Log = "delete folder => confirmed";
		Log.d("DB_OCL.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		Methods.del_Folder(actv, dlg1, dlg2, this.item_Name);
		
	}

}//DialogButtonOnClickListener
