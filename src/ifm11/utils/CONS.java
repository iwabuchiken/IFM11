package ifm11.utils;

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
	
	public static enum MoveMode {
		// TIListAdapter.java
		ON, OFF,
		
	}//public static enum MoveMode

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
		public static String dbName = "cm7.db";
		
		public static String dPath_dbFile;
//		public static String dPath_dbFile = "/data/data/cm7.main/databases";
		
		public static String dPath_dbFile_backup = "/mnt/sdcard-ext/cm7_backup";
		
//		public static String dPath_dbFile = 
//							Methods.get_DirPath(new MainActv().getFilesDir().getPath());
		
		public static String fname_DB_Backup_Trunk = "cm7_backup";
		
		public static String fname_DB_Backup_ext = ".bk";
		
		public static String dname_TapeATalk_Sdcard = "tapeatalk_records";
		
		////////////////////////////////
		
		// Table: cm7
		
		////////////////////////////////
		public static final String tname_CM7 = "cm7";

		public static final String[] col_names_CM7 = {
			
			"file_name", "file_path",	// 0, 1
			"title", "memo",			// 2, 3
			"last_played_at",			// 4
			"table_name",				// 5
			"length",					// 6
			"audio_created_at"			// 7
			
		};
		
		public static final String[] col_names_CM7_full = {
			
			android.provider.BaseColumns._ID,	// 0
			"created_at", "modified_at",		// 1, 2
			"file_name", "file_path",			// 3, 4
			"title", "memo",					// 5, 6
			"last_played_at",					// 7
			"table_name",						// 8
			"length",							// 9
			"audio_created_at"					// 10
			
		};

		public static final String[] col_types_CM7 = {
			"TEXT", "TEXT",			// 0, 1
			"TEXT", "TEXT",			// 2,3
			"TEXT",					// 4
			"TEXT",					// 5
			"TEXT",				// 6
//			"INTEGER",				// 6
			"TEXT"					// 7
		};
		
		
		////////////////////////////////

		// Table: BM (bookmark)

		////////////////////////////////
		public static final String tname_BM = "bm";

		public static final String[] col_names_BM = {
			"ai_id", "position", 			// 0,1
			"title", "memo", "aiTableName"	// 2,3,4
		};
		
		public static final String[] col_names_BM_full = {
			android.provider.BaseColumns._ID,		// 0
			"created_at", "modified_at",			// 1,2
			"ai_id", "position",					// 3,4
			"title", "memo", "aiTableName"			// 5,6,7
		};

		public static final String[] col_types_BM = {
//			"INTEGER", "INTEGER",			// 0,1
			"INTEGER", "TEXT",			// 0,1
			"TEXT", "TEXT", "TEXT"			// 2,3,4
		};

		////////////////////////////////
		
		// Table: refresh_history
		
		////////////////////////////////
		public static String tname_RefreshHistory = "refresh_history";
		
		public static String[] col_names_RefreshHistory = {
			"last_refreshed", "num_of_items_added"
		};
		
		public static String[] col_names_RefreshHistory_full = {
			android.provider.BaseColumns._ID,		// 0
			"created_at", "modified_at",			// 1,2
			"last_refreshed", "num_of_items_added"	// 3,4
		};
		
		public static String[] col_types_RefreshHistory = {
			"TEXT", 			"INTEGER"
//			"INTEGER", 			"INTEGER"
		};

		////////////////////////////////
		
		// Table: memo_patterns
		
		////////////////////////////////
		public static String tname_MemoPatterns = "memo_patterns";
		
		public static String[] col_names_MemoPatterns = {
			"word"
		};
		
		public static String[] col_names_MemoPatterns_full = {
			android.provider.BaseColumns._ID,		// 0
			"created_at", "modified_at",			// 1,2
			"word"									// 3
		};
		
		public static String[] col_types_MemoPatterns = {
			"TEXT"
//			"INTEGER", 			"INTEGER"
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
		
		////////////////////////////////

		// PlayActv.java

		////////////////////////////////
		public static final String pname_PlayActv = "pname_PlayActv";
		
		public static final String pkey_PlayActv_CurrentPosition = 
							"pkey_PlayActv_CurrentPosition";
		
		public static final String pkey_PlayActv_CurrentFileName = 
							"pkey_PlayActv_CurrentFileName";
		
		////////////////////////////////

		// MainActv.java

		////////////////////////////////
		
		public static SharedPreferences prefs_MainActv;
		
		public static String pname_MainActv = "main_activity";
//		public static String pname_CurrentPath = "current_path";
		
		public static String pkey_CurrentPath = "pkey_CurrentPath";
		
		public static String pkey_CurrentPosition = "pkey_CurrentPosition";
		
		////////////////////////////////

		// ALActv

		////////////////////////////////
		public static SharedPreferences prefs_ALActv;
		
		public static String pname_ALActv = "al_activity";
		
		public static String pkey_CurrentPosition_ALActv
									= "pkey_CurrentPosition_ALActv";
		
	}

	public static class MainActv {
		
		public static List<String> list_RootDir = null;
		
		public static ArrayAdapter<String> adp_dir_list = null;
		
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
		
		public static final String format_Date = "yyyy/MM/dd hh:mm:ss.SSS";
//		public static final String date_Format = "yyyy/MM/dd hh:mm:ss.SSS";
		
		public static final String format_Clock = "%02d:%02d";
		
	}//public static class Admin

	public static class Paths {
		////////////////////////////////

		// MainActv.java

		////////////////////////////////
		
		public static String dpath_Storage_Sdcard = "/mnt/sdcard-ext";
		
		public static String dpath_Storage_Internal = "/mnt/sdcard";
		
		public static String  dname_Base = "cm7";
		
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

	public static class Enums {
		
		public static enum SortType {
			
			FileName, POSITION,
			
		}

		// Sort order
		public static enum SortOrder {
				ASC, DEC,
				CREATED_AT,
		};

	}
}//public class CONS
