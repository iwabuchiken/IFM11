package ifm11.main;

import ifm11.adapters.Adp_LogFileList;
import ifm11.adapters.Adp_MainList;
import ifm11.adapters.Adp_ShowLogFile_List;
import ifm11.items.LogItem;
import ifm11.listeners.button.BO_CL;
import ifm11.listeners.button.BO_TL;
import ifm11.utils.CONS;
import ifm11.utils.Methods;
import ifm11.utils.Methods_dlg;
import ifm11.utils.Tags;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

public class ShowLogActv extends ListActivity {

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
        
        setContentView(R.layout.actv_showlog);
        
    }//public void onCreate(Bundle savedInstanceState)

    private void do_debug() {
		

    	
	}//private void do_debug()

	@Override
	protected void onListItemClick(ListView lv, View v, int position, long id) {
		
		super.onListItemClick(lv, v, position, id);
		
		// Log
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "onListItemClick()");
		//
//		CONS.Admin.vib.vibrate(CONS.Admin.vibLength_click);
////		
//		////////////////////////////////
//
//		// get: item name
//
//		////////////////////////////////
//		String itemName = (String) lv.getItemAtPosition(position);
//		
//		/******************************
//			validate: null
//		 ******************************/
//		if (itemName != null) {
//			
//			// Log
//			Log.d("MainActv.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", "itemName=" + itemName);
//			
//		} else {//if (item_)
//			
//			String msg = "itemName => null";
//			Methods_dlg.dlg_ShowMessage(this, msg);
//			
//			return;
//
//		}//if (item_)
//
//		////////////////////////////////
//
//		// validate: file exists
//
//		////////////////////////////////
//		File fpath_Log = new File(CONS.DB.dPath_Log, itemName);
//		
//		if (!fpath_Log.exists()) {
//			
//			String msg = "File doesn't exist";
//			Methods_dlg.dlg_ShowMessage(this, msg, R.color.red);
//			
//			return;
//			
//		}
//		
//		////////////////////////////////
//
//		// start activity
//
//		////////////////////////////////
//		Methods.start_Activity_ShowLogActv(this, itemName);
		
	}//protected void onListItemClick(ListView l, View v, int position, long id)

	@Override
	protected void onDestroy() {
		/*----------------------------
		 * 1. Reconfirm if the user means to quit
		 * 2. super
		 * 3. Clear => prefs
		 * 4. Clear => file_names
			----------------------------*/
		
		super.onDestroy();
		
	}//protected void onDestroy()

//	@Override
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		
//		Methods.confirm_quit(this, keyCode);
//		
//		return super.onKeyDown(keyCode, event);
//	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// 
		MenuInflater mi = getMenuInflater();
		mi.inflate(R.menu.menu_actv_showlog, menu);
		
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		
		case R.id.menu_showlog_filter://------------------------
			
			case_OPT_Filter();
			
			break;
		
		case R.id.menu_showlog_filter_history://------------------------
			
			
			break;
			
			
		default://------------------------
			break;

		}//switch (item.getItemId())
		
		return super.onOptionsItemSelected(item);
		
	}//public boolean onOptionsItemSelected(MenuItem item)

	private void 
	case_OPT_Filter() {
		// TODO Auto-generated method stub
		
		Methods_dlg.dlg_filter_ShowLogList(this);
		
	}

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
	protected void 
	onStart() {
		
		super.onStart();

		boolean res;
		
		////////////////////////////////

		// debug

		////////////////////////////////
		do_debug();
		
		////////////////////////////////

		// Init vars

		////////////////////////////////
		res = _Setup_InitVars();
		
		if (res == false) return;
		
		////////////////////////////////

		// build: list

		////////////////////////////////
		res = _Setup_List();

		if (res == false) return;

		////////////////////////////////

		// adapter

		////////////////////////////////
		res = _Setup_Adapter();
		
		if (res == false) return;

		////////////////////////////////

		// listeners

		////////////////////////////////
		res = this._Setup_SetListeners_Navigation();
		
		if (res == false) return;
		
	}//protected void onStart()

	private boolean
	_Setup_SetListeners_Navigation() {
		// TODO Auto-generated method stub
		
		////////////////////////////////
		
		// Back
		
		////////////////////////////////
		ImageButton ib_Back = (ImageButton) findViewById(R.id.actv_showlog_ib_back);
		
//		ib_Back.setTag(Tags.ButtonTags.thumb_activity_ib_back);
		ib_Back.setTag(Tags.ButtonTags.ACTV_SHOWLOG_IB_BACK);
		
		ib_Back.setOnTouchListener(new BO_TL(this));
		ib_Back.setOnClickListener(new BO_CL(this));
		
		////////////////////////////////
		
		// Top
		
		////////////////////////////////
		ImageButton ib_TOP = (ImageButton) findViewById(R.id.actv_showlog_ib_toTop);
		
//		ib_Back.setTag(Tags.ButtonTags.thumb_activity_ib_back);
//		ib_TOP.setTag(Tags.ButtonTags.thumb_activity_ib_top);
		ib_TOP.setTag(Tags.ButtonTags.ACTV_SHOWLOG_IB_TOP);
		
		ib_TOP.setOnTouchListener(new BO_TL(this));
		ib_TOP.setOnClickListener(new BO_CL(this));
		
		////////////////////////////////
		
		// Bottom
		
		////////////////////////////////
		ImageButton ib_Bottom = (ImageButton) findViewById(R.id.actv_showlog_ib_toBottom);
		
//		ib_Back.setTag(Tags.ButtonTags.thumb_activity_ib_back);
//		ib_Bottom.setTag(Tags.ButtonTags.thumb_activity_ib_top);
		ib_Bottom.setTag(Tags.ButtonTags.ACTV_SHOWLOG_IB_BOTTOM);
		
		ib_Bottom.setOnTouchListener(new BO_TL(this));
		ib_Bottom.setOnClickListener(new BO_CL(this));
		
		////////////////////////////////
		
		// Down
		
		////////////////////////////////
		ImageButton ib_Down = (ImageButton) findViewById(R.id.actv_showlog_ib_next_page);
		
//		ib_Back.setTag(Tags.ButtonTags.thumb_activity_ib_back);
//		ib_Down.setTag(Tags.ButtonTags.thumb_activity_ib_top);
		ib_Down.setTag(Tags.ButtonTags.ACTV_SHOWLOG_IB_DOWN);
		
		ib_Down.setOnTouchListener(new BO_TL(this));
		ib_Down.setOnClickListener(new BO_CL(this));
		
		////////////////////////////////
		
		// Up
		
		////////////////////////////////
		ImageButton ib_Up = (ImageButton) findViewById(R.id.actv_showlog_ib_prev_page);
		
//		ib_Back.setTag(Tags.ButtonTags.thumb_activity_ib_back);
//		ib_Up.setTag(Tags.ButtonTags.thumb_activity_ib_top);
		ib_Up.setTag(Tags.ButtonTags.ACTV_SHOWLOG_IB_UP);
		
		ib_Up.setOnTouchListener(new BO_TL(this));
		ib_Up.setOnClickListener(new BO_CL(this));
		
		return true;
		
	}//_Setup_SetListeners_Navigations

	private boolean 
	_Setup_Adapter() {
		// TODO Auto-generated method stub
		////////////////////////////////

		// build: adapter

		////////////////////////////////
		CONS.ShowLogActv.adp_ShowLog_File_List = new Adp_ShowLogFile_List(
				
				this,
				R.layout.list_row_logitem,
				CONS.ShowLogActv.list_ShowLog_Files
				);

		/******************************
			validate
		 ******************************/
		if (CONS.ShowLogActv.adp_ShowLog_File_List == null) {
			
			// Log
			String msg_Log = "CONS.ShowLogActv.adp_ShowLog_File_List => null";
			Log.e("ShowLogActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			String msg = "can't build adapter";
			Methods_dlg.dlg_ShowMessage(this, msg, R.color.red);
			
			return false;
			
		}
		
		////////////////////////////////

		// set dapter

		////////////////////////////////
		this.setListAdapter(CONS.ShowLogActv.adp_ShowLog_File_List);
		
		return true;
		
	}//_Setup_Adapter
	

	/******************************
		@return
			false => 1. Log file => doesn't exist<br>
					2. CONS.ShowLogActv.list_RawLines => null<br>
	 ******************************/
	private boolean 
	_Setup_List() {
		// TODO Auto-generated method stub
		
		String msg_Log;
		
		////////////////////////////////

		// validate: files exists

		////////////////////////////////
		File fpath_Log = new File(
				CONS.DB.dPath_Log,
				CONS.ShowLogActv.fname_Target_LogFile);
		
		if (!fpath_Log.exists()) {
			
			String msg = "Log file => doesn't exist";
			Methods_dlg.dlg_ShowMessage(this, msg, R.color.red);
			
			return false;
		}
		
		////////////////////////////////

		// read file

		////////////////////////////////
		FileInputStream fis = null;
		
//		CONS.ShowLogActv.list_RawLines = new ArrayList<String>();
		
		List<String> list = 
						Methods.get_LogLines(this, fpath_Log.getAbsolutePath());
		
		/******************************
			validate
		 ******************************/
		if (list == null) {
			
			return false;
			
		} else {
			
			////////////////////////////////
			
			// list => reverse
			
			////////////////////////////////
			Collections.reverse(list);
			
			////////////////////////////////

			// add all

			////////////////////////////////
			CONS.ShowLogActv.list_RawLines.addAll(list);
			
		}

//		// Log
//		String msg_Log = "list.get(0) => " + list.get(0);
//		Log.d("ShowLogActv.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_Log);
//		//REF http://stackoverflow.com/questions/5412499/android-reverse-the-order-of-an-array answered Mar 23 '11 at 22:38
//		Collections.reverse(list);
//		
//		// Log
//		msg_Log = "[reverse] list.get(0) => " + list.get(0);
//		Log.d("ShowLogActv.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_Log);
		
		////////////////////////////////

		// build: LogItem list

		////////////////////////////////
		List<LogItem> list_LogItem = 
				Methods.conv_LogLinesList_to_LogItemList(
									this, CONS.ShowLogActv.list_RawLines);

		/******************************
			validate
		 ******************************/
		if (list_LogItem == null) {
			
			// Log
			msg_Log = "list_LogItem => null";
			Log.e("ShowLogActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			return false;
			
		} else {

			// Log
			msg_Log = "list_LogItem => not null"
						+ "(" + list_LogItem.size() + ")";
			Log.d("ShowLogActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			CONS.ShowLogActv.list_ShowLog_Files.addAll(list_LogItem);
			
			////////////////////////////////

			// sort

			////////////////////////////////
			
			
			return true;
			
		}
		
	}//_Setup_List

	private boolean
	_Setup_InitVars() {
		// TODO Auto-generated method stub
		
		CONS.Admin.vib = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);

		CONS.ShowLogActv.list_ShowLog_Files = new ArrayList<LogItem>();
		
		CONS.ShowLogActv.list_RawLines = new ArrayList<String>();
		
		////////////////////////////////

		// intent

		////////////////////////////////
		boolean res = _Setup_InitVars__Intent();
		
		if (res == false) {
			
			return false;
		}
		
		return true;
		
	}//_Setup_InitVars

	private boolean 
	_Setup_InitVars__Intent() {
		// TODO Auto-generated method stub
		
//		Intent i = new Intent();
		Intent i = this.getIntent();
		
		CONS.ShowLogActv.fname_Target_LogFile = 
						i.getStringExtra(CONS.Intent.iKey_LogActv_LogFileName);
		
		// Log
		String msg_Log = "CONS.ShowLogActv.fname_Target_LogFile => " 
					+ CONS.ShowLogActv.fname_Target_LogFile;
		Log.d("ShowLogActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		/******************************
			validate
		 ******************************/
		if (CONS.ShowLogActv.fname_Target_LogFile == null) {
			
			String msg = "Can't get the log file name";
			Methods_dlg.dlg_ShowMessage(this, msg, R.color.red);
			
			// Log
			Log.d("ShowLogActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg);
			
			return false;
			
		}
		
		return true;
		
	}

	@Override
	public void onBackPressed() {
		/*----------------------------
		 * memo
			----------------------------*/
		this.finish();
		
		overridePendingTransition(0, 0);
		
	}//public void onBackPressed()


}
