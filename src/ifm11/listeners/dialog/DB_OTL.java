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
	Dialog dlg;
	
	public DB_OTL(Activity actv, Dialog dlg) {
		//
		this.actv = actv;
		this.dlg = dlg;
	}
	
	public DB_OTL(Activity actv) {
		//
		this.actv = actv;
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

				case dlg_search_ok:
					
				case dlg_register_patterns_register:

				case dlg_confirm_delete_patterns_ok:
					
				case DLG_PLAYACTV_EDIT_TITLE_BT_OK:
					
				case DLG_EDIT_ITEM_BT_OK:
					
				case dlg_conf_delete_BM_ok:
					
				case DROP_TABLE_OK:
					
				case DLG_ADD_MEMOS_BT_ADD:
					
				case DLG_ADD_MEMOS_BT_PATTERNS:
					
				case DLG_CREATE_DIR_OK:
					
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
				
			case dlg_search_ok:
				
			case dlg_register_patterns_register:
				
			case dlg_confirm_delete_patterns_ok:
				
			case DLG_PLAYACTV_EDIT_TITLE_BT_OK:
				
			case DLG_EDIT_ITEM_BT_OK:
				
			case dlg_conf_delete_BM_ok:
				
			case DROP_TABLE_OK:
				
			case DLG_ADD_MEMOS_BT_ADD:
				
			case DLG_ADD_MEMOS_BT_PATTERNS:
				
			case DLG_CREATE_DIR_OK:
				
					v.setBackgroundColor(Color.WHITE);
					
					break;
				}//switch (tag_name)
		
			break;//case MotionEvent.ACTION_UP:
		
		}//switch (event.getActionMasked())
		
		return false;
		
	}//public boolean onTouch(View v, MotionEvent event)

}//public class DB_OTL implements OnTouchListener
