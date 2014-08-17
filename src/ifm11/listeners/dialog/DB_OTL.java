package ifm11.listeners.dialog;

import ifm11.utils.Tags;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class DB_OTL implements OnTouchListener {

	/*----------------------------
	 * Fields
		----------------------------*/
	//
	Activity actv;
	Dialog dlg1;
	private Dialog dlg2;
	private Dialog dlg3;
	
	public DB_OTL(Activity actv, Dialog dlg) {
		//
		this.actv = actv;
		this.dlg1 = dlg;
	}
	
	public DB_OTL(Activity actv) {
		//
		this.actv = actv;
	}

	public 
	DB_OTL
	(Activity actv, Dialog dlg1, Dialog dlg2) {
		// TODO Auto-generated constructor stub
		
		this.actv = actv;
		this.dlg1 = dlg1;
		this.dlg2 = dlg2;

	}

	public DB_OTL(Activity actv, Dialog dlg1, Dialog dlg2, Dialog dlg3) {
		// TODO Auto-generated constructor stub
		
		this.actv = actv;
		
		this.dlg1 = dlg1;
		this.dlg2 = dlg2;
		this.dlg3 = dlg3;
		
	}

	//	@Override
	public boolean onTouch(View v, MotionEvent event) {
		
		Tags.DialogTags tag_name = (Tags.DialogTags) v.getTag();
		
		switch (event.getActionMasked()) {
		case MotionEvent.ACTION_DOWN:
				switch (tag_name) {
				
				case GENERIC_DISMISS:
				case GENERIC_DISMISS_THIRD_DIALOG:
				case GENERIC_DISMISS_SECOND_DIALOG:
					
				case dlg_create_folder_ok:
				case dlg_create_folder_cancel:
				
				case dlg_input_empty_cancel:
				case dlg_input_empty_reenter:

				case dlg_confirm_create_folder_ok:
				case dlg_confirm_create_folder_cancel:
					
				case dlg_confirm_remove_folder_cancel:
				case dlg_confirm_remove_folder_ok:

				case dlg_confirm_move_files_ok:

				case DLG_SEARCH_OK:
					
				case DLG_REGISTER_PATTERNS_REGISTER:

				case dlg_confirm_delete_patterns_ok:
					
				case DLG_PLAYACTV_EDIT_TITLE_BT_OK:
					
				case DLG_EDIT_ITEM_BT_OK:
					
				case dlg_conf_delete_BM_ok:
					
				case DROP_TABLE_OK:
					
				case DLG_ADD_MEMOS_BT_ADD:
					
				case DLG_ADD_MEMOS_BT_PATTERNS:
					
				case DLG_CREATE_DIR_OK:
					
				case DLG_DELETE_TI_CONF_OK:
					
				case DLG_DELETE_PATTERN_CONF_OK:
					
				case UPLOAD_REMOTE_OK:
					
					v.setBackgroundColor(Color.GRAY);
					
					break;
				}//switch (tag_name)
		
			break;//case MotionEvent.ACTION_DOWN:
			
		case MotionEvent.ACTION_UP:
			switch (tag_name) {

			case GENERIC_DISMISS:
			case GENERIC_DISMISS_SECOND_DIALOG:
			case GENERIC_DISMISS_THIRD_DIALOG:

			case dlg_create_folder_ok:
			case dlg_create_folder_cancel:

			case dlg_input_empty_cancel:
			case dlg_input_empty_reenter:

			case dlg_confirm_create_folder_ok:
			case dlg_confirm_create_folder_cancel:

			case dlg_confirm_remove_folder_cancel:
			case dlg_confirm_remove_folder_ok:

			case dlg_confirm_move_files_ok:
				
			case DLG_SEARCH_OK:
				
			case DLG_REGISTER_PATTERNS_REGISTER:
				
			case dlg_confirm_delete_patterns_ok:
				
			case DLG_PLAYACTV_EDIT_TITLE_BT_OK:
				
			case DLG_EDIT_ITEM_BT_OK:
				
			case dlg_conf_delete_BM_ok:
				
			case DROP_TABLE_OK:
				
			case DLG_ADD_MEMOS_BT_ADD:
				
			case DLG_ADD_MEMOS_BT_PATTERNS:
				
			case DLG_CREATE_DIR_OK:
				
			case DLG_DELETE_TI_CONF_OK:
				
			case DLG_DELETE_PATTERN_CONF_OK:
				
			case UPLOAD_REMOTE_OK:
				
					v.setBackgroundColor(Color.WHITE);
					
					break;
				}//switch (tag_name)
		
			break;//case MotionEvent.ACTION_UP:
		
		}//switch (event.getActionMasked())
		
		return false;
		
	}//public boolean onTouch(View v, MotionEvent event)

}//public class DB_OTL implements OnTouchListener
