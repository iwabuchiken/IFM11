package ifm11.utils;

import ifm11.adapters.Adp_MainList;
import ifm11.adapters.Adp_TIList;
import ifm11.items.TI;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.provider.MediaStore;
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
		public static String iKey_CurrentPath_MainActv = "current_path";

		
		////////////////////////////////

		// ALActv

		////////////////////////////////
		public static String iKey_AI_FilePath_Full = "iKey_AI_FilePath_Full";
		
		public static String iKey_AI_Db_Id = "iKey_AI_Db_Id";
		
		public static String iKey_AI_TableName = "iKey_AI_TableName";
		
		////////////////////////////////

		// BMActv

		////////////////////////////////
		public static String iKey_BMActv_AI_Id = "bmactv_key_ai_id";
//		public static String bmactv_key_ai_id = "bmactv_key_ai_id";
		
		public static String iKey_BMActv_TableName = "bmactv_key_table_name";
		
		public static String iKey_BMActv_Position = "bmactv_key_position";
//		public static String bmactv_key_position = "bmactv_key_position";
		
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
		public static String dbName = "ifm11.db";
		
		public static String dPath_dbFile;
//		public static String dPath_dbFile = "/data/data/cm7.main/databases";
		
		public static String dPath_dbFile_backup = "/mnt/sdcard-ext/ifm11_backup";
		
//		public static String dPath_dbFile = 
//							Methods.get_DirPath(new MainActv().getFilesDir().getPath());
		
		public static String fname_DB_Backup_Trunk = "ifm11_backup";
		
		public static String fname_DB_Backup_ext = ".bk";
		
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
			"table_name"							// 8
			
		};
		
		public static final String[] col_names_IFM11_full = {
			
			//	0
			android.provider.BaseColumns._ID,		// 0
			"created_at", "modified_at",			// 1,2
			"file_id", "file_path", "file_name",	// 3,4,5
			"date_added", "date_modified",			// 6,7
			"memos", "tags",						// 8,9
			"last_viewed_at",						// 10
			"table_name"							// 11
			
		};

		public static final String[] col_types_IFM11 = {
			"INTEGER", "TEXT", "TEXT",	// 0,1,2
			"TEXT", "TEXT",				// 3,4
			"TEXT", "TEXT",				// 5,6
			"TEXT",						// 7
			"TEXT"						// 8
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
			"table_name"					// 1
					
		};
		
		public static String[] col_names_MemoPatterns_full = {
			
			android.provider.BaseColumns._ID,		// 0
			"created_at", "modified_at",			// 1,2
			"word",									// 3
			"table_name"							// 4
			
		};
		
		public static String[] col_types_MemoPatterns = {
			
			"TEXT", 			// 0
			"TEXT"				// 1
					
		};
		
		public static String[] col_types_MemoPatterns_full = {
			
			"INTEGER", "TEXT", "TEXT",	// 0,1,2
			"TEXT", 					// 3
			"TEXT"						// 4
			
		};
		
		////////////////////////////////

		// Others

		////////////////////////////////
		public static String jointString_TableName = "__";
		
		public static int pastXDays		= -10;

		////////////////////////////////

		// FileFilter

		////////////////////////////////
		public static enum FFType {
			
			RefreshHistory
		}
		
		
	}//public static class DB

	public static class Pref {
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

		// TNActv

		////////////////////////////////
		public static SharedPreferences prefs_TNActv;
		
		public static String pkey_CurrentPosition_TNActv
									= "pkey_CurrentPosition_TNActv";
		
	}

	public static class MainActv {
		
		public static List<String> list_RootDir = null;
		
		public static ArrayAdapter<String> adp_dir_list = null;

		public static Adp_MainList aAdapter;
		
	}

	public static class TNActv {
		
		public static List<TI> list_TNActv_Main = null;
		
		public static Adp_TIList adp_TNActv_Main;
		
		public static int list_Pos_Current = -1;
		public static int list_Pos_Prev = -1;
		
	}
	
	public static class Admin {
		
		public static final float DLG_WIDTH_RATIO = 0.8f;
		
		public static final String dName_backup = "cm5_backup";
		
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
		
		public static String dpath_Storage_Internal = "/mnt/sdcard";
		
		public static String  dname_Base = "ifm11";
		
		public static String dpath_Storage_Camera = "/mnt/sdcard-ext/dcim/Camera";
		
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

	public static class 
	Enums {
		
		public static enum SortType {
			
			FileName, POSITION, CREATED_AT,
			
		}

		// Sort order
		public static enum SortOrder {
			
				ASC, DEC,
				
		};

		public static enum MoveMode {
			// TIListAdapter.java
			ON, OFF,
			
		}//public static enum MoveMode

	}//Enums
	
}//public class CONS
