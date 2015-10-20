package ifm11.main;

import ifm11.adapters.Adp_HistSearch;
import ifm11.items.SearchHistory;
import ifm11.listeners.button.BO_CL;
import ifm11.listeners.button.BO_TL;
import ifm11.utils.CONS;
import ifm11.utils.DBUtils;
import ifm11.utils.Methods;
import ifm11.utils.Methods_dlg;
import ifm11.utils.Tags;

import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;

public class SearchActv extends Activity {

	///////////////////////////////////
	//
	// fields
	//
	///////////////////////////////////
	List<SearchHistory> list_SearchHistories = null;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	/*----------------------------
		 * 1. super
		 * 2. Set content
		 * 2-2. Set title
		 * 3. Initialize => vib
		 * 
		 *  4. Set list
		 *  5. Set listener => Image buttons
		 *  6. Set path label
		 *  
		 *  7. Initialize preferences
		 *  
		 *  8. Refresh DB
			----------------------------*/
		
        super.onCreate(savedInstanceState);
        
        this.setTitle(this.getClass().getName());
        
        setContentView(R.layout.dlg_search_2);
//        setContentView(R.layout.dlg_search);
//        setContentView(R.layout.actv_main);
        
    }//public void onCreate(Bundle savedInstanceState)

	@Override
	protected void onDestroy() {
		/*----------------------------
		 * 1. Reconfirm if the user means to quit
		 * 2. super
		 * 3. Clear => prefs
		 * 4. Clear => file_names
			----------------------------*/
		
		// Log
		String msg_Log;
		
		msg_Log = String.format(
				Locale.JAPAN,
				"calling => super.onDestroy()"
				);
		
		Log.i("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		super.onDestroy();
		
		// Log
//		String msg_Log;
		
		msg_Log = String.format(
				Locale.JAPAN,
				"onDestroy() => done"
				);
		
		Log.i("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
	}//protected void onDestroy()

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		this.finish();
		
		this.overridePendingTransition(0, 0);
		
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// 
		MenuInflater mi = getMenuInflater();
		mi.inflate(R.menu.menu_main, menu);
		
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		
		case R.id.opt_menu_main_db://------------------------
			
			Methods_dlg.dlg_Db_Actv(this);
			
			break;
		
		case R.id.main_opt_menu_preferences://------------------
			
//			Methods_dlg.dlg_Db_Actv(this);
			Methods.start_Activity_PrefActv(this);
			
			break;
			
		case R.id.main_opt_menu_create_folder://------------------

			Methods_dlg.dlg_Create_Dir(this);
			
			break;
			
		case R.id.main_opt_menu_search://------------------
			
			Methods_dlg.dlg_SeratchItem(this);
			
			break;
			
		case R.id.main_opt_menu_others://------------------
			
			Methods_dlg.dlg_OptMenu_MainActv_Others(this);
			
			break;
			
		case R.id.main_opt_menu_history://------------------
			
			Methods.start_Activity_HistActv(this);
			
			break;
			
		default://------------------------
			break;

		}//switch (item.getItemId())
		
		return super.onOptionsItemSelected(item);
		
	}//public boolean onOptionsItemSelected(MenuItem item)

	@Override
	protected void onPause() {
		
		super.onPause();

		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "onPause()");
		
	}

	@Override
	protected void onResume() {
		// TODO ?��?��?��?��?��?��?��?��?��?��?��ꂽ?��?��?��\?��b?��h?��E?��X?��^?��u
		super.onResume();

	}//protected void onResume()

	@Override
	protected void onStart() {
		
		super.onStart();
		
		///////////////////////////////////
		//
		// listener
		//
		///////////////////////////////////
		this._Setup_SetListeners();
		
		///////////////////////////////////
		//
		// search history
		//
		///////////////////////////////////
		this._Setup_Search_History();
		
		///////////////////////////////////
		//
		// adapters:
		//		depend: this._Setup_Search_History()
		//
		///////////////////////////////////
		this._Setup_Adapters();
		
	}//protected void onStart()

	private void _Setup_Search_History() {
		// TODO Auto-generated method stub
		
		///////////////////////////////////
		//
		// build: list
		//
		///////////////////////////////////
		int limit = 20;
		
		String col = CONS.DB.col_names_Search_History_full[0];	// _id
		
		String direction = CONS.Admin.lbl_Direc_DESC;
		
//		List<SearchHistory> list_SearchHistories = 
		list_SearchHistories = 
						DBUtils.find_All_SearchHistories(this, col, direction, limit);
//		DBUtils.find_All_SearchHistories(this, limit);
		
		///////////////////////////////////
		//
		// valid: null
		//
		///////////////////////////////////
		if (list_SearchHistories == null) {

			// Log
			String msg_Log;
			
			msg_Log = String.format(
					Locale.JAPAN,
					"list_SearchHistories => null"
					);
			
			Log.e("SearchActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);

			return;
			
		}//if (list_SearchHistories == null)
		
		//debug
		// Log
		String msg_Log;
		
		msg_Log = String.format(
				Locale.JAPAN,
				"size of list_SearchHistories => %d", list_SearchHistories.size()
				);
		
		Log.i("SearchActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);

		///////////////////////////////////
		//
		// list: sort
		//
		///////////////////////////////////
//		for (int i = 0; i < 3; i++) {
//			
//			msg_Log = String.format(
//					Locale.JAPAN,
//					"created at (%d) => %s", 
//					i, list_SearchHistories.get(i).getCreated_at()
//					);
//			
//			Log.i("SearchActv.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", msg_Log);
//			
//		}//for (int i = 0; i < 3; i++)
		

//		List<SearchHistory> list_SearchHistories__Sorted
//				= Methods.sort_List__SearchHistories(
//		list_SearchHistories = Methods.sort_List__SearchHistories(
		Methods.sort_List__SearchHistories(
									list_SearchHistories, 
									CONS.Enums.SortType.CREATED_AT, 
									CONS.Enums.SortOrder.DESC);
		
//		// Log
////		String msg_Log;
//
//		for (int i = 0; i < 3; i++) {
//			
//			msg_Log = String.format(
//					Locale.JAPAN,
//					"created at (%d) => %s", 
//					i, list_SearchHistories.get(i).getCreated_at()
//					);
//			
//			Log.i("SearchActv.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", msg_Log);
//			
//		}//for (int i = 0; i < 3; i++)

		
	}//_Setup_Search_History

	private void _Setup_Adapters() {
		
		////////////////////////////////

		// get adapter

		////////////////////////////////
		CONS.SearchHistory.adp_SearchHistory = new Adp_HistSearch(
				
						this,
						R.layout.list_row_search_history,
						this.list_SearchHistories
		);

		/******************************
			validate
		 ******************************/
		if (CONS.SearchHistory.adp_SearchHistory == null) {
			
			// Log
			String msg_Log = "CONS.SearchHistory.adp_SearchHistory => null";
			Log.e("ShowListActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			String msg = "adapter => null";
			Methods_dlg.dlg_ShowMessage(this, msg, R.color.red);
			
			return;
			
		}

		////////////////////////////////

		// set

		////////////////////////////////
		CONS.SearchHistory.lv_SearchHistory = 
						(ListView) this.findViewById(R.id.dlg_search_2_LV);
		
		// valid: null
		if (CONS.SearchHistory.lv_SearchHistory == null) {

			// Log
			String msg_Log;
			
			msg_Log = String.format(
					Locale.JAPAN,
					"CONS.SearchHistory.lv_SearchHistory => null"
					);
			
			Log.e("SearchActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);

			return;
			
		}//if (CONS.SearchHistory.lv_SearchHistory == null)

		CONS.SearchHistory.lv_SearchHistory
					.setAdapter(CONS.SearchHistory.adp_SearchHistory);

	}//private void _Setup_Adapters()
	
	private void 
	_Setup_SetListeners() {
		
		String msg_Log;

		///////////////////////////////////
		//
		// button: cancel
		//
		///////////////////////////////////
		Button bt_Cancel = (Button) findViewById(R.id.dlg_search_2_cancel);
		
		bt_Cancel.setTag(Tags.ButtonTags.GENERIC_ACTV_BT_CANCEL);

		bt_Cancel.setOnClickListener(new BO_CL(this));
		bt_Cancel.setOnTouchListener(new BO_TL(this));
		
		///////////////////////////////////
		//
		// button: ok
		//
		///////////////////////////////////
		Button bt_OK = (Button) findViewById(R.id.dlg_search_2_bt_ok);
		
		bt_OK.setTag(Tags.ButtonTags.ACTV_SEARCH_BT_OK);
		
		bt_OK.setOnClickListener(new BO_CL(this));
		bt_OK.setOnTouchListener(new BO_TL(this));

		///////////////////////////////////
		//
		// listview: history
		//
		///////////////////////////////////
		
	}//_Setup_SetListeners

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		
		// Log
		String msg_Log;
		
		msg_Log = String.format(
				Locale.JAPAN,
				"onStop()"
				);
		
		Log.i("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
	}

}//public class MainActv extends Activity
