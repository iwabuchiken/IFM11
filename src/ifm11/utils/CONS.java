package ifm11.utils;

import ifm11.adapters.Adp_HistUpload;
import ifm11.adapters.Adp_LogFileList;
import ifm11.adapters.Adp_MainList;
import ifm11.adapters.Adp_ShowLogFile_List;
import ifm11.adapters.Adp_TIList;
import ifm11.adapters.Adp_TIList_Move;
import ifm11.adapters.Adp_WordPatterns;
import ifm11.items.HistUpload;
import ifm11.items.LogItem;
import ifm11.items.MyView;
import ifm11.items.TI;
import ifm11.items.WordPattern;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.TextView;

public class CONS {

//	// Sort order
//	public static enum SORT_ORDER {
//			ASC, DEC,
//			CREATED_AT,
//	};

	
	

	// Table => show_history
//	public static String tname_show_history = "show_history";
	
//	public static enum MoveMode {
//		// TIListAdapter.java
//		ON, OFF,
//		
//	}//public static enum MoveMode

	public static class Intent {
		
		////////////////////////////////

		// commons

		////////////////////////////////
		public static long dflt_LongExtra_value = -1;
		
		public static int dflt_IntExtra_value = -1;
		
		
		////////////////////////////////
		
		// PlayActv
		
		////////////////////////////////
		// Used in Service_ShowProgress
		public static String iKey_PlayActv_TaskPeriod
								= "iKey_PlayActv_TaskPeriod";
		
		
		////////////////////////////////

		// MainActv

		////////////////////////////////
		public static final String iKey_CurrentPath_MainActv = "current_path";

		////////////////////////////////

		// ShowLogActv

		////////////////////////////////
		public static final String iKey_LogActv_LogFileName =
													"iKey_LogActv_LogFileName";
		
		/***************************************
		 * Request codes
		 ***************************************/
		public final static int REQUEST_CODE_SEE_BOOKMARKS = 0;
		
		public final static int REQUEST_CODE_HISTORY = 1;
		
		/***************************************
		 * Result code
		 ***************************************/
		public final static int RESULT_CODE_SEE_BOOKMARKS_OK = 1;
		
		public final static int RESULT_CODE_SEE_BOOKMARKS_CANCEL = 0;
		
	}//public static class Intent
	
	public static class DB {
		////////////////////////////////

		// Paths and names

		////////////////////////////////
		public final static String dbName = "ifm11.db";
		public final static String dbName_IFM10 = "ifm10.db";
		public final static String dbName_Previous = "ifm11_previous.db";
		
		public static String dPath_dbFile;
		
		// Do not hardcode "/data/"; use Context.getFilesDir().getPath()
//		public static String dPath_dbFile = "/data/data/cm7.main/databases";

		public static String dPath_Data_SDCard_Ext = "/mnt/sdcard-ext";
		
		public static String dPath_Data_SDCard = "/mnt/sdcard";
		
		public static String dPath_Data_SDCard_Camera = 
						dPath_Data_SDCard_Ext + "/dcim/Camera";
		
		public static String dPath_Data_SDCard_Camera_SHARP100 = 
				dPath_Data_SDCard_Ext + "/dcim/100SHARP";
		
		public static String dPath_Data_Root = "/mnt/sdcard-ext/ifm11_data";
		
		public static String dPath_dbFile_backup = dPath_Data_Root + "/backup";
//		public static String dPath_dbFile_backup = dPath_Data_Root + "/ifm11_backup";
//		public static String dPath_dbFile_backup = "/mnt/sdcard-ext/ifm11_backup";
		
		public static String dPath_dbFile_backup_IFM10 = 
										"/mnt/sdcard-ext/IFM10_backup";
		
		public static String dPath_Log = dPath_Data_Root + "/log";
		
		// thumbnails
		public static String dPath_TNs = dPath_Data_Root + "/tns";
//		public final static String dPath_Data_SDCard_Ext = "/mnt/sdcard-ext";
//		
//		public final static String dPath_Data_SDCard_Camera = 
//				dPath_Data_SDCard_Ext + "/dcim/Camera";
//		
//		public final static String dPath_Data_Root = "/mnt/sdcard-ext/ifm11_data";
//		
//		public final static String dPath_dbFile_backup = dPath_Data_Root + "/backup";
////		public final static String dPath_dbFile_backup = dPath_Data_Root + "/ifm11_backup";
////		public static String dPath_dbFile_backup = "/mnt/sdcard-ext/ifm11_backup";
//		
//		public final static String dPath_dbFile_backup_IFM10 = 
//				"/mnt/sdcard-ext/IFM10_backup";
//		
//		public final static String dPath_Log = dPath_Data_Root + "/log";
//		
//		// thumbnails
//		public final static String dPath_TNs = dPath_Data_Root + "/tns";
		
//		public static String dPath_dbFile = 
//							Methods.get_DirPath(new MainActv().getFilesDir().getPath());
		
		public final static String fname_DB_Backup_Trunk = "ifm11_backup";
		
		public final static String fname_DB_Backup_ext = ".bk";
		
		public final static String fname_Log = "log.txt";
		
		public final static String fname_Log_Trunk = "log";
		
		public final static String fname_Log_ext = ".txt";
		
		public static final long logFile_MaxSize = 40000;
		
		// admin items
		public static final String admin_LastBackup = "last_backup";
		
//		public static String dname_TapeATalk_Sdcard = "tapeatalk_records";
		
		////////////////////////////////
		
		// Table: ifm11
		
		////////////////////////////////
		public static final String tname_IFM11 = "ifm11";

		public static final String[] col_names_IFM11 = {
			
			"file_id", "file_path", "file_name",	// 0,1,2
			"date_added", "date_modified",			// 3,4
			"memos", "tags",						// 5,6
			"last_viewed_at",						// 7
			"table_name",							// 8
			"uploaded_at",							// 9
			
		};
		
		public static final String[] col_names_IFM11_full = {
			
			//	0
			android.provider.BaseColumns._ID,		// 0
			"created_at", "modified_at",			// 1,2
			"file_id", "file_path", "file_name",	// 3,4,5
			"date_added", "date_modified",			// 6,7
			"memos", "tags",						// 8,9
			"last_viewed_at",						// 10
			"table_name",							// 11
			"uploaded_at",							// 12
			
		};

		public static final String[] col_names_IFM10_full = {
			
			//	0
			android.provider.BaseColumns._ID,		// 0
			"created_at", "modified_at",			// 1,2
			"file_id", "file_path", "file_name",	// 3,4,5
			"date_added", "date_modified",			// 6,7
			"memos", "tags",						// 8,9
			"last_viewed_at",						// 10
			"table_name",							// 11
//			"uploaded_at",							// 12
			
		};
		
		public static final String[] col_types_IFM11 = {
			"INTEGER", "TEXT", "TEXT",	// 0,1,2
			"TEXT", "TEXT",				// 3,4
			"TEXT", "TEXT",				// 5,6
			"TEXT",						// 7
			"TEXT",						// 8
			"TEXT",						// 9
		};
		
		////////////////////////////////

		// Table: media

		////////////////////////////////
		public static final String[] proj = {
				MediaStore.Images.Media._ID, 			// 0
				MediaStore.Images.Media.DATA,			// 1
				MediaStore.Images.Media.DISPLAY_NAME,	// 2
				MediaStore.Images.Media.DATE_ADDED,		// 3
				MediaStore.Images.Media.DATE_MODIFIED,	// 4
				};
		
		public static final String[] proj_for_get_data = {
				MediaStore.Images.Media._ID, 
				MediaStore.Images.Media.DATA,
				MediaStore.Images.Media.DISPLAY_NAME,
				MediaStore.Images.Media.DATE_ADDED,
				MediaStore.Images.Media.DATE_MODIFIED,
				"memos",
				"tags"
				};
		
		////////////////////////////////

		// Table: refresh log

		////////////////////////////////
		public static final String tname_RefreshLog = "refresh_log";
		
		public static final String[] col_names_refresh_log = {
			
				"last_refreshed", "num_of_items_added"
			
		};
		
		public static final String[] col_names_refresh_log_full = {
			
			android.provider.BaseColumns._ID,		// 0
			"created_at", "modified_at",			// 1,2
			"last_refreshed", "num_of_items_added"
			
		};
		
		public static final String[] col_types_refresh_log = {
			
				"TEXT", 			"INTEGER"
				
		};
		
		public static final String[] col_types_refresh_log_full = {
			
			"INTEGER", "TEXT", "TEXT",		// 0,1,2
			"TEXT", 			"INTEGER"	// 3,4
			
		};
		
		////////////////////////////////

		// table: memo_patterns

		////////////////////////////////
		public static String tname_MemoPatterns = "memo_patterns";
		
		public static String[] col_names_MemoPatterns = {
			
			"word",							// 0
			"used",							// 1
			"used_at",							// 1
//			"table_name"					// 1
					
		};
		
		public static String[] col_names_MemoPatterns_full = {
			
			android.provider.BaseColumns._ID,		// 0
			"created_at", "modified_at",			// 1,2
			"word",									// 3
			"used",									// 4
			"used_at",								// 5
//			"table_name"							// 4
			
		};
		
		public static String[] col_types_MemoPatterns = {
			
			"TEXT", 			// 0
			"INTEGER",			// 1
			"TEXT",				// 2
//			"TEXT"				// 1
					
		};
		
		public static String[] col_types_MemoPatterns_full = {
			
			"INTEGER", "TEXT", "TEXT",	// 0,1,2
			"TEXT", 					// 3
			"INTEGER",					// 4
			"TEXT", 					// 5
			
		};
		
		////////////////////////////////
		
		// table: admin
		
		////////////////////////////////
		public static String tname_Admin = "admin";
		
		public static String[] col_names_Admin = {
			
			"name",							// 0
			"val",							// 1
			
		};
		
		public static String[] col_names_Admin_full = {
			
			android.provider.BaseColumns._ID,		// 0
			"created_at", "modified_at",			// 1,2
			"name",									// 3
			"val",									// 4
			
		};
		
		public static String[] col_types_Admin = {
			
			"TEXT", 			// 0
			"TEXT", 			// 0
			
		};
		
		public static String[] col_types_Admin_full = {
			
			"INTEGER", "TEXT", "TEXT",	// 0,1,2
			"TEXT", 					// 3
			"TEXT", 					// 3
			
		};

		////////////////////////////////

		// Table: upload history

		////////////////////////////////
		public static final String tname_UploadHistory = "upload_history";
		
		public static final String[] col_names_Upload_History = {
			
			"db_id",								// 0
			"file_name", "file_path"				// 1,2
			
		};
		
		public static final String[] col_names_Upload_History_full = {
			
			android.provider.BaseColumns._ID,		// 0
			"created_at", "modified_at",			// 1,2
			"db_id",								// 3
			"file_name", "file_path"				// 4,5
			
		};
		
		public static final String[] col_types_Upload_History = {
			
			"INTEGER",						// 0
			"TEXT", "TEXT"					// 1,2
				
		};
		
		public static final String[] col_types_upload_history_full = {
			
			"INTEGER", "TEXT", "TEXT",		// 0,1,2
			"INTEGER",						// 3
			"TEXT", "TEXT"					// 4,5
			
		};
		
		////////////////////////////////
		
		// table: search_history
		
		////////////////////////////////
		public static String tname_Search_History = "search_history";
//		public static String tname_Search_History = "filter_history";
		
		public static String[] col_names_Search_History = {
			
			"keywords",							// 0
			"all_table",						// 1
			"by_file_name",						// 2
			"type",								// 3	=> AND, OR, NOT
//			"operator",							// 1
			
		};
		
		public static String[] col_names_Search_History_full = {
			
			android.provider.BaseColumns._ID,	// 0
			"created_at", "modified_at",		// 1,2
			"keywords",							// 3
			"all_table",						// 4
			"by_file_name",						// 5
			"type",								// 6	=> AND, OR, NOT
			
//			"operator",									// 4
//			"op_label",									// 5
			
		};
		
		public static String[] col_types_Search_History = {
			//			"keywords",							// 0
			//			"all_table",						// 1
			//			"by_file_name",						// 2
			//			"type",								// 3	=> AND, OR, NOT

			"TEXT", 			// 0
			"INTEGER", 			// 1	=> yes: 1, no: 0
			"INTEGER", 			// 2	=> yes: 1, no: 0
			"INTEGER", 			// 3	=> AND: 1, OR: 2, NOT: 0
//			"INTEGER", 			// 1
			
		};
		
		public static String[] col_types_Search_History_full = {
			//			android.provider.BaseColumns._ID,		// 0
			//			"created_at", "modified_at",			// 1,2
			//			"keywords",							// 3
			//			"all_table",						// 4
			//			"by_file_name",						// 5
			//			"type",								// 6	=> AND, OR, NOT
			
			"INTEGER", "TEXT", "TEXT",	// 0,1,2
			
			"TEXT", 			// 3
			"INTEGER", 			// 4	=> yes: 1, no: 0
			"INTEGER", 			// 5	=> yes: 1, no: 0
			"INTEGER", 			// 6	=> AND: 1, OR: 2, NOT: 0

//			"INTEGER", 					// 4
			
		};

		////////////////////////////////

		// Others

		////////////////////////////////
		public static String jointString_TableName = "__";
		
		public static int pastXDays		= -10;

		// refresh db
		public static final int REFRESH_MAX = 10;
		
		public static boolean REFRESH_YES	= true;

		////////////////////////////////

		// FileFilter

		////////////////////////////////
		public static enum FFType {
			
			RefreshHistory
		}
		
		////////////////////////////////

		// sqls

		////////////////////////////////
		public static class Sqls {
			
			public static String _20140817_154650_addCol_IFM11_UpdatedAt_TITLE = 
									"Add column: uploaded_at";
			
			//REF http://stackoverflow.com/questions/7622122/sqlite-add-column-keep-data answered Oct 1 '11 at 18:32
			public static String _20140817_154650_addCol_IFM11_UpdatedAt_SQL = 
					"ALTER TABLE" + " "
					+ CONS.DB.tname_IFM11 + " "
					+ "ADD COLUMN" + " "
					+ "uploaded_at" + " "
					+ "TEXT"
					;
			
		}
		
		///////////////////////////////////
		//
		// others
		//
		///////////////////////////////////
		public static String modelname_IS13SH	= "IS13SH";
		
	}//public static class DB

	public static class 
	Pref {
		////////////////////////////////

		// Commons

		////////////////////////////////
		public static long dflt_LongExtra_value = -1;
		
		public static int dflt_IntExtra_value = -1;
		
		////////////////////////////////

		// MainActv.java

		////////////////////////////////
		
		public static SharedPreferences prefs_MainActv;
		
		public static String pname_MainActv = "pname_MainActv";
//		public static String pname_CurrentPath = "current_path";
		
		public static String pkey_CurrentPath = "pkey_CurrentPath";
		
		public static String pkey_CurrentPosition_MainActv = "pkey_CurrentPosition";
		
		////////////////////////////////

		// HistUploadActv

		////////////////////////////////
		public static SharedPreferences prefs_HistUploadActv;
		
		public static String pkey_CurrentPosition_HistUploadActv
									= "pkey_CurrentPosition_HistUploadActv";
		
		
		////////////////////////////////
		
		// TNActv
		
		////////////////////////////////
		public static SharedPreferences prefs_TNActv;
		
		public static String pkey_CurrentPosition_TNActv
		= "pkey_CurrentPosition_TNActv";
		
		public static String pkey_TNActv__CurPath_Move
									= "pkey_TNActv__CurPath_Move";
		
		// standard, search, history, a.o.
		public static final String pkey_TNActv__ListType
									= "pkey_TNActv__ListType";

		////////////////////////////////

		// LogActv

		////////////////////////////////
		public static String pkey_CurrentPosition_LogActv = 
									"pkey_CurrentPosition_LogActv";
		
		////////////////////////////////

		// ShowListActv

		////////////////////////////////
		public static String pname_ShowListActv = "pname_ShowListActv"; 
		
		
		public static final String 
		pkey_ShowListActv_Filter_String = 
							"pkey_ShowListActv_Filter_String";

		public static final String 
		pkey_ShowListActv_Current_Position = 
							"pkey_ShowListActv_Current_Position";

	}//Pref

	public static class MainActv {
		
		public static List<String> list_RootDir = null;
		
		public static ArrayAdapter<String> adp_dir_list = null;

		public static Adp_MainList aAdapter;
		
	}

	public static class LogActv {
		
		public static List<String> list_LogFiles = null;
		
//		public static ArrayAdapter<String> adp_LogFile_List = null;
		
		public static Adp_LogFileList adp_LogFile_List;
		
	}
	
	public static class ShowLogActv {
		
		public static List<LogItem> list_ShowLog_Files = null;
		
//		public static ArrayAdapter<String> adp_LogFile_List = null;
		
		public static Adp_ShowLogFile_List adp_ShowLog_File_List;
		
		public static String fname_Target_LogFile = null;
		
		public static List<String> list_RawLines = null;
		
	}
	
	public static class TNActv {
		
		public static List<TI> list_TNActv_Main = null;
		
		public static Adp_TIList adp_TNActv_Main;
		
		public static Adp_TIList_Move adp_TNActv_Main_Move;
		
		public static int list_Pos_Current = -1;
		public static int list_Pos_Prev = -1;
		
		public static boolean moveMode;

		public static Menu menu;
		
		public static List<Integer> checkedPositions = new ArrayList<Integer>();
		
		// TNActv, long click, move files
		public static ArrayAdapter<String> adp_DirList;
		
		// TNActv, long click, move files
		public static List<String> dir_List;

		// MainActv: option "Search"
		public static List<Long> searchedItems;
		
		// TNActv refers to this var; if true, _Setup_SetList()
		//		builds other TI list using the var "searchedItems"
		public static boolean searchDone	= false;

		// Used => Deleting TI in TNActv list view
		public static int inList_Pos;
		
	}
	
	public static class HistUploadActv {

		public static List<HistUpload> list_HistUploadActv_Main = null;

		public static Adp_HistUpload adp_HistUploadActv_Main;

		public static int dflt_ListSize		= 30;
		
	}
	
	public static class HistActv {
		
		public static List<TI> list_HistActv_Main = null;
		
		public static Adp_TIList adp_HistActv_Main;
		
		public static Adp_TIList_Move adp_HistActv_Main_Move;
		
		public static int list_Pos_Current = -1;
		public static int list_Pos_Prev = -1;
		
		public static boolean moveMode;
		
		public static Menu menu;
		
		public static List<Integer> checkedPositions = new ArrayList<Integer>();
		
		// HistActv, long click, move files
		public static ArrayAdapter<String> adp_DirList;
		
		// HistActv, long click, move files
		public static List<String> dir_List;
		
		// MainActv: option "Search"
		public static List<Long> searchedItems;
		
		// HistActv refers to this var; if true, _Setup_SetList()
		//		builds other TI list using the var "searchedItems"
		public static boolean searchDone	= false;
		
		// Used => Deleting TI in TNActv list view
		public static int inList_Pos;

		public static int dflt_ListSize		= 20;
		
	}//public static class HistActv
	
	public static class 
	IMageActv {
		
		public static ArrayAdapter<String> adp_ImageActv_GridView = null;
		
//		public static List<String> patternList = null;
		
		public static List<WordPattern> patternList;
		
		public static Adp_WordPatterns adapter;
		
		public static TI ti;
		
		////////////////////////////////

		// listview-related

		////////////////////////////////
		public static List<WordPattern> list_WP_1;
		
		public static List<WordPattern> list_WP_2;
		
		public static List<WordPattern> list_WP_3;
		
		public static Adp_WordPatterns adp_WPList_1 = null;
		
		public static Adp_WordPatterns adp_WPList_2 = null;
		
		public static Adp_WordPatterns adp_WPList_3 = null;

		////////////////////////////////

		// bitmap-related

		////////////////////////////////
		public static Bitmap bm_Modified = null;
		
		public static MyView v = null;
		
	}//IMageActv
	
	public static class Admin {
		
		////////////////////////////////

		// commons

		////////////////////////////////
		public static final String dirString_UpperDir	= "..";
		
		public static final float DLG_WIDTH_RATIO = 0.8f;
		
		public static final String dName_backup = "cm5_backup";
		
		public static final String char_Space_Half	= " ";
		
		public static final String char_Space_Whole	= "　";
		
		// millseconds; used in Methods_dlg.dlg_ShowMessage_Duration
		public static final int dflt_MessageDialog_Length	= 3000;
		
		// X out of 100
		// Usage => e.g. width = screen_width * 100 / ratio_Dialog_to_Screen_W
		public static final int ratio_Dialog_to_Screen_W = 70;

		// is running
		public static boolean isRunning_TNActv = true;
		public static boolean isRunning_HistUploadActv = true;
		
		public static String actvName_TNActv = "ifm11.main.TNActv";
		
		public static String[] special_Chars = new String[]{
			
			"()", "[]",
			"（）", "「」", "『』", "〈〉", "【】", "｛｝",
			
		};
		
		////////////////////////////////

		// MainActv.java

		////////////////////////////////
		public static String fname_List = "list.txt";
		
		////////////////////////////////

		// Utilities

		////////////////////////////////
		public static Vibrator vib;
		
		public static final int vibLength_click = 35;
		
		public static final String format_Date = "yyyy/MM/dd HH:mm:ss.SSS";
//		public static final String format_Date = "yyyy/MM/dd hh:mm:ss.SSS";
//		public static final String date_Format = "yyyy/MM/dd hh:mm:ss.SSS";
		
		public static final String format_Clock = "%02d:%02d";
		
	}//public static class Admin

	public static class Paths {
		////////////////////////////////

		// MainActv.java

		////////////////////////////////
		
		public static String dpath_Storage_Sdcard = "/mnt/sdcard-ext";
		
		public static String dpath_Storage_Camera = "/mnt/sdcard-ext/dcim/Camera";
		
		public static String dpath_Storage_Internal = "/mnt/sdcard";
		
		public static String  dname_Base = "ifm11";
		
	}
	
	public static class Retval {
		////////////////////////////////

		// Errors

		////////////////////////////////
		/******************************
			Refresh DB
		 ******************************/
		public static int CantCreateTable =		-10;
		
		public static int CantRefreshAudioDir=	-11;
		
		public static int NoNewFiles =			-12;
		
		
		
	}

	public static class Remote {
		
		public static enum FtpType {
			
			IMAGE, DB_FILE, IMAGE_MULTIPLE,
			
		}
		
		public static enum HttpType {
			
			IMAGE,
			
		}
		
		////////////////////////////////

		// ftp

		////////////////////////////////
		public static String server_Name = "ftp.benfranklin.chips.jp";
		
		public static String uname = "chips.jp-benfranklin";
		
		public static String passwd = "9x9jh4";
		
		public static String remote_Root_Image = "./cake_apps/images/ifm11";

		public static String remote_Root_DBFile = "./android_app_data/IFM11";
		
		// initialize res:int in Task_FTP.doInBackground()
		public static int initial_IntValue = -100;
		
		////////////////////////////////

		// status code

		////////////////////////////////
		public static final int status_220		= 220;

		public static final int status_Created	= 201;
		
		public static final int status_NOT_CREATED	= -201;
		
		public static final int status_OK		= 200;
		
		public static class Http {
			
			public static final String url_Post_ImageData =
						"http://benfranklin.chips.jp/cake_apps/Cake_IFM11/images/add";
			
		}
		
	}

	
	public static class 
	Enums {
		
		public enum TimeLabelType {

			/*******************************
			 * STANDARD => yyyy/MM/dd HH:mm:ss.SSS<br>
			 * 	label for ==> created_at, modified_at
			 *******************************/
			STANDARD, 		// yyyy/MM/dd HH:mm:ss.SSS
			SERIAL, 		// yyyyMMdd_hhmmss_SSS
			SERIAL2,		// yyyy-MM-dd_hh-mm-ss_SSS
			
		}

		public static enum SortType {
			
			FileName, POSITION, CREATED_AT, WORD, USED,
			
		}

		// Sort order
		public static enum SortOrder {
			
				ASC, 
//				DEC, 
				DESC,
				
		};

		public static enum MoveMode {
			// TIListAdapter.java
			ON, OFF,
			
		}//public static enum MoveMode

		public static enum ListType {
			
			STANDARD, SEARCH, HISTORY, ANY,
			
		}
		
	}//Enums

	public static class Canvas {
		
		////////////////////////////////

		// commons

		////////////////////////////////
		public static int lineWidth		= 10;
		
		public static List<Long> list_Lows_R;
		public static List<Long> list_Highs_R;
		
		public static List<Long> list_Lows_G;
		public static List<Long> list_Highs_G;
		
		public static List<Long> list_Lows_B;
		public static List<Long> list_Highs_B;
		
		////////////////////////////////

		// paints

		////////////////////////////////
		public static Paint p1;


		////////////////////////////////

		// data

		////////////////////////////////
		public static int[] col_R_adj;
		public static int[] col_G_adj;
		public static int[] col_B_adj;
		
		////////////////////////////////

		// flags

		////////////////////////////////
		public static boolean f_RGB_Lines		= false;
		
		////////////////////////////////

		// color names

		////////////////////////////////
		public static enum ColNames {
			
			RED, GREEN, BLUE
			
		}
		
		
	}

}//public class CONS
