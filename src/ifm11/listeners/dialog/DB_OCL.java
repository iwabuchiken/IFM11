package ifm11.listeners.dialog;

import ifm11.main.R;
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
			

		default: // ----------------------------------------------------
			break;
		}//switch (tag_name)
	}//public void onClick(View v)

	private void 
	dlg_DLG_CONF_MOVE_FILES_FOLDER_OK() {
		// TODO Auto-generated method stub
		
		////////////////////////////////

		// get: choice

		////////////////////////////////
		TextView tv_ItemName = 
				(TextView) dlg3.findViewById(R.id.dlg_tmpl_confirm_simple_tv_item_name);
		
		String chosen_Path = tv_ItemName.getText().toString();
		
		Toast.makeText(actv, chosen_Path, Toast.LENGTH_SHORT).show();
		
		
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
