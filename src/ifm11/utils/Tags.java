package ifm11.utils;

public class Tags {

	public static enum DialogTags {
		// Generics
		GENERIC_DISMISS, GENERIC_DISMISS_SECOND_DIALOG,
		GENERIC_DISMISS_THIRD_DIALOG,

		GENERIC_SECOND_DIALOG_CLEAR_ALL, 
		
		GENERIC_ACTV_BT_CANCEL,
		
		// dlg_create_folder.xml
		dlg_create_folder_ok, dlg_create_folder_cancel,

		// dlg_input_empty.xml
		dlg_input_empty_reenter, dlg_input_empty_cancel,
		
		// dlg_confirm_create_folder.xml
		dlg_confirm_create_folder_ok, dlg_confirm_create_folder_cancel,

		// dlg_confirm_remove_folder.xml
		dlg_confirm_remove_folder_ok, dlg_confirm_remove_folder_cancel,

		// dlg_drop_table.xml
		dlg_drop_table_btn_cancel, dlg_drop_table,
		
		// dlg_confirm_drop.xml
		dlg_confirm_drop_table_btn_ok, dlg_confirm_drop_table_btn_cancel,
		
		// dlg_add_memos.xml
		DLG_ADD_MEMOS_BT_ADD, dlg_add_memos_bt_cancel, DLG_ADD_MEMOS_BT_PATTERNS,
		dlg_add_memos_gv,

		// dlg_move_files.xml
		dlg_move_files_move, dlg_move_files,
		
		// dlg_confirm_move_files.xml	=> ok, cancel, dlg tag
		dlg_confirm_move_files_ok, dlg_confirm_move_files_cancel, dlg_confirm_move_files,

		// dlg_item_menu.xml
		dlg_item_menu_bt_cancel, dlg_item_menu,

		// dlg_create_table.xml
		dlg_create_table_bt_create,

		// dlg_memo_patterns.xml
		dlg_memo_patterns,
		
		// dlg_register_patterns.xml
		DLG_REGISTER_PATTERNS_REGISTER,

		// dlg_search.xml
		dlg_search, DLG_SEARCH_OK,

		// dlg_admin_patterns.xml

		// dlg_confirm_delete_patterns.xml
		dlg_confirm_delete_patterns_ok,
		
		// dlg_edit_title.xml
		DLG_PLAYACTV_EDIT_TITLE_BT_OK,
		
		// dlg_edit_title.xml(Dialog: egit memo)
		dlg_edit_memo_bt_ok,

		// dlg_confirm_move_files_search	=> ok, cancel, dlg tag
		dlg_confirm_move_files_search_ok,
		
		// dlg_edit_item.xml
		DLG_EDIT_ITEM_BT_OK,
		
		// dlg: confirm delete BM
		dlg_conf_delete_BM_ok,
		
		// dlg: drop table
		DROP_TABLE_OK, 
		
		// dlg: create dir
		DLG_CREATE_DIR_OK, 
		
		// dlg: create dir: confirm
		DLG_CONFIRM_CREATE_FOLDER_OK, 
		
		// dlg: delete folder: conf
		DLG_DELETE_FOLDER_CONF_OK,
		
		// dlg: conf: move files: to folder
		DLG_CONF_MOVE_FILES_FOLDER_OK, DLG_CONF_MOVE_FILES_FOLDER_TOP_OK,
		DLG_DELETE_TI_CONF_OK,
		
		// dlg: edit TI
		DLG_EDIT_TI_BT_OK,
		
		// dlg: delete patterns
		DLG_DELETE_PATTERN_CONF_OK, 
		
		// dlg: db admin
		EXEC_SQL_OK,
		
		// dlg: conf upload remote => image file
		UPLOAD_REMOTE_OK,
		
		// dlg: conf upload => db file
		UPLOAD_DB_FILE_OK, 
		UPLOAD_REMOTE_MULTIPLE_IMAGES_OK, 
		
		DROP_CREATE_TABLE_PATTERNS_OK, 
		DROP_CREATE_DROP_TABLE_ADMIN_OK, DLG_FILTER_SHOWLIST_OK, DLG_FILTER_SHOWLIST_CLEAR, DLG_FILTER_SHOWLIST_RESET, REFRESH_YES, REFRESH_NO, DROP_CREATE_DROP_TABLE_UPLOADHISTORY_OK,
		
	}//public static enum DialogTags
	
	public static enum DialogItemTags {
		// dlg_moveFiles(Activity actv)
		DLG_MOVE_FILES, DLG_MOVE_FILES_FOLDER,
		DLG_MOVE_FILES_REMOTE,
		
		// dlg_add_memos.xml
		DLG_ADD_MEMOS_GV,

		// dlg_db_admin.xml
		DLG_DB_ADMIN_LV,

		// dlg_admin_patterns.xml
		DLG_ADMIN_PATTERNS_LV,

		// dlg_delete_patterns.xml
		DLG_DELETE_PATTERNS_GV,

		// dlg_moveFiles_search(Activity actv)
		dlg_move_files_search,

		// main_opt_menu_admin
		main_opt_menu_admin,
		
		// dlg_bmactv_list_long_click
		DLG_BMACTV_LIST_LONGCLICK, 
		
		// dlg: ActvMain, long click
		DLG_ACTVMAIN_LONGCLICK,
		
		// dlg: TNActv, long click
		DLG_ACTV_TN_LONG_CLICK,
		
		// dlg: upload image
		DLG_ACTV_TN_LIST_UPLOAD, 
		
		ACTV_MAIN_OPTMENU_OTHERS, 
		ACTV_MAIN_ADMIN_LV_OPS, 
		
		ACTV_IMAGE_ADD_MEMO_LV_1, 
		ACTV_IMAGE_ADD_MEMO_LV_2, ACTV_IMAGE_ADD_MEMO_LV_3, ACTV_IMAGE_OPTMENU_LABS, ACTV_CANVAS_OPS,
		
		
	}//public static enum DialogItemTags
	
	
	public static enum ButtonTags {
		// MainActivity.java
		ib_up,
		
		// DBAdminActivity.java
		db_manager_activity_create_table, db_manager_activity_drop_table, 
		db_manager_activity_register_patterns,
		
		// thumb_activity.xml
		ACTV_TN_IB_BACK, ACTV_TN_IB_BOTTOM, ACTV_TN_IB_TOP,
		ACTV_TN_IB_DOWN, ACTV_TN_IB_UP,
		
		// image_activity.xml
		image_activity_back, image_activity_prev, image_activity_next,
		
		// AILAdapter.java
		ailist_cb,
		
		// actv_play.xml
		actv_play_bt_play, actv_play_bt_stop, actv_play_bt_back,
		actv_play_tv_title, actv_play_tv_memo,
		
		actv_play_bt_see_bm, actv_play_bt_add_bm,
		
		// SearchActv.java
		actv_search_ib_bottom, actv_search__ib_top,

		// AILAdapter_move.java
		ailist_cb_search,
		
		// actv_bm.xml
		actv_bm_bt_back,
		
		// actv_hist.xml
		actv_hist_ib_back, actv_hist_ib_bottom, actv_hist_ib_top, 
		
		// TNActv, move mode = true
		TILIST_CB, 
		
		ACTV_SHOWLOG_IB_BACK, 
		ACTV_SHOWLOG_IB_TOP, 
		ACTV_SHOWLOG_IB_BOTTOM, 
		ACTV_SHOWLOG_IB_DOWN, 
		ACTV_SHOWLOG_IB_UP, 
		
		ACTV_LOG_IB_BACK, 
		ACTV_LOG_IB_TOP, 
		ACTV_LOG_IB_BOTTOM, 
		ACTV_LOG_IB_DOWN, 
		ACTV_LOG_IB_UP, 
		
		ACTV_MAIN_BT_GO, 
		ACTV_MAIN_BT_CLEAR, 
		
		ACTV_HISTUPLOAD_IB_BACK, 
		ACTV_HISTUPLOAD_IB_TOP, 
		ACTV_HISTUPLOAD_IB_BOTTOM, 
		ACTV_HISTUPLOAD_IB_DOWN, ACTV_HISTUPLOAD_IB_UP, 
		
		ACTV_SEARCH_BT_OK, GENERIC_ACTV_BT_CANCEL,

	}//public static enum ButtonTags
	
	public static enum ItemTags {
		
		// MainActivity.java
		dir_list,
		
		// ThumbnailActivity.java
		dir_list_thumb_actv,
		
		// Methods.java
		dir_list_move_files,
		
		// TIListAdapter.java
		tilist_checkbox,
		
		// SearchActv.java
		dir_list_actv_search,
		
		// actv_hist.xml
		dir_list_actv_hist,
		
	}//public static enum ItemTags

//	public static enum MoveMode {
//		// TIListAdapter.java
//		ON, OFF,
//		
//	}//public static enum MoveMode

	public static enum PrefenceLabels {
		
		CURRENT_PATH,
		
		thumb_actv,
		
		chosen_list_item,
		
	}//public static enum PrefenceLabels

	public static enum ListTags {
		// MainActivity.java
		actv_main_lv,
		
		// BMActv.java
		actv_bm_lv, 
		
		// TNActv
		ACTV_TN_LV,
		
	}//public static enum ListTags

	public static enum TVTags {
		
		// PlayActv
		PLAYACTV_TITLE,
	}

	public static enum ViewTags {
		
		CANVAS_MAIN,
		
	}

	
}//public class Tags
