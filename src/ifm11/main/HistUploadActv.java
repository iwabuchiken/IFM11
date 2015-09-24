package ifm11.main;

import ifm11.adapters.Adp_HistUpload;
import ifm11.adapters.Adp_LogFileList;
import ifm11.adapters.Adp_MainList;
import ifm11.adapters.Adp_TIList;
import ifm11.adapters.Adp_TIList_Move;
import ifm11.items.TI;
import ifm11.listeners.LOI_LCL;
import ifm11.listeners.button.BO_CL;
import ifm11.listeners.button.BO_TL;
import ifm11.tasks.Task_CreateTN;
import ifm11.utils.CONS;
import ifm11.utils.DBUtils;
import ifm11.utils.Methods;
import ifm11.utils.Methods_dlg;
import ifm11.utils.Tags;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class HistUploadActv extends ListActivity {

	/****************************************
	 * Methods
	 ****************************************/
	@Override
	public void onCreate(Bundle savedInstanceState) {
		/*----------------------------
		 * Steps
		 * 1. Super
		 * 2. Set content
		 * 3. Basics
		 * 
		 * 4. Set up
		 * 5. Initialize vars
		----------------------------*/
		super.onCreate(savedInstanceState);

		// Log
		Log.d("HistUploadActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "onCreate()");
		
		//
		setContentView(R.layout.thumb_activity);
		
		/*----------------------------
		 * 3. Basics
			----------------------------*/
		this.setTitle(this.getClass().getName());
		
	}//public void onCreate(Bundle savedInstanceState)


	@Override
	protected void onPause() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		super.onPause();
	}

	@Override
	protected void onResume() {
		/*********************************
		 * 1. super
		 * 2. Notify adapter
		 * 
		 * 3. Set selection
		 *********************************/
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		super.onResume();
		
		// Log
		String msg_Log;
		
		msg_Log = String.format(
				Locale.JAPAN,
				"onResume() => done"
				);
		
		Log.i("HistUploadActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
	}//protected void onResume()

	@Override
	protected void onStart() {
		/*********************************
		 * 1. super
		 * 2. Set selection
		 *********************************/
		super.onStart();
		
		// Log
		Log.d("HistUploadActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "onStart()");
		
		////////////////////////////////

		// init: vib

		////////////////////////////////
		if (CONS.Admin.vib == null) {
			
			CONS.Admin.vib = 
					(Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
			
		}
		
		////////////////////////////////
		
		// is running => true
		
		////////////////////////////////
		//REF http://stackoverflow.com/questions/5446565/android-how-do-i-check-if-activity-is-running answered Mar 27 '11 at 1:48
		if (CONS.Admin.isRunning_HistUploadActv == false) {
			
			CONS.Admin.isRunning_HistUploadActv = true;
			
			// Log
			String msg_Log;
			
			msg_Log = String.format(
					Locale.JAPAN,
					"CONS.Admin.isRunning_HistUploadActv => change to true"
					);
			
			Log.i("HistUploadActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
		}
		
		//debug
		else {
			
			// Log
			String msg_Log;
			
			msg_Log = String.format(
					Locale.JAPAN,
					"CONS.Admin.isRunning_HistUploadActv => true"
					);
			
			Log.i("HistUploadActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
		}

//		////////////////////////////////
//
//		// debug
//
//		////////////////////////////////
//		do_debug();
		
		////////////////////////////////

		// set: list

		////////////////////////////////
		boolean res = _Setup_SetList();
		
		if (res == false) {
			
			return;
			
		}

		////////////////////////////////

		// adapter

		////////////////////////////////
		_Setup_Adapter();
		
//		////////////////////////////////
//
//		// set: selection
//
//		////////////////////////////////
//		_Setup_SetSelection();
//		
//		////////////////////////////////
//
//		// setup: options
//
//		////////////////////////////////
//		_Setup_Options();
//		
//		////////////////////////////////
//
//		// setup: listeners
//
//		////////////////////////////////
//		_Setup_SetListeners();
//		
//		////////////////////////////////
//		
//		// setup: listeners: navigation
//		
//		////////////////////////////////
//		this._Setup_SetListeners_Navigation();

//		////////////////////////////////
//
//		// test
//
//		////////////////////////////////
//		this.do_debug();
		
	}//protected void onStart()

	private void 
	_Setup_SetListeners() {
		// TODO Auto-generated method stub
		
		////////////////////////////////

		// long click

		////////////////////////////////
		ListView lv = this.getListView();
		
		lv.setTag(Tags.ListTags.ACTV_TN_LV);
		
		lv.setOnItemLongClickListener(new LOI_LCL(this));

		
	}//_Setup_SetListeners

	private void 
	_Setup_SetListeners_Navigation() {
		// TODO Auto-generated method stub
		
		////////////////////////////////
		
		// Back
		
		////////////////////////////////
		ImageButton ib_Back = (ImageButton) findViewById(R.id.thumb_activity_ib_back);
		
//		ib_Back.setTag(Tags.ButtonTags.thumb_activity_ib_back);
		ib_Back.setTag(Tags.ButtonTags.ACTV_TN_IB_BACK);
		
		ib_Back.setOnTouchListener(new BO_TL(this));
		ib_Back.setOnClickListener(new BO_CL(this));
		
		////////////////////////////////
		
		// Top
		
		////////////////////////////////
		ImageButton ib_TOP = (ImageButton) findViewById(R.id.thumb_activity_ib_toTop);
		
//		ib_Back.setTag(Tags.ButtonTags.thumb_activity_ib_back);
//		ib_TOP.setTag(Tags.ButtonTags.thumb_activity_ib_top);
		ib_TOP.setTag(Tags.ButtonTags.ACTV_TN_IB_TOP);
		
		ib_TOP.setOnTouchListener(new BO_TL(this));
		ib_TOP.setOnClickListener(new BO_CL(this));
		
		////////////////////////////////
		
		// Bottom
		
		////////////////////////////////
		ImageButton ib_Bottom = (ImageButton) findViewById(R.id.thumb_activity_ib_toBottom);
		
//		ib_Back.setTag(Tags.ButtonTags.thumb_activity_ib_back);
//		ib_Bottom.setTag(Tags.ButtonTags.thumb_activity_ib_top);
		ib_Bottom.setTag(Tags.ButtonTags.ACTV_TN_IB_BOTTOM);
		
		ib_Bottom.setOnTouchListener(new BO_TL(this));
		ib_Bottom.setOnClickListener(new BO_CL(this));
		
		////////////////////////////////
		
		// Down
		
		////////////////////////////////
		ImageButton ib_Down = (ImageButton) findViewById(R.id.thumb_activity_ib_next_page);
		
//		ib_Back.setTag(Tags.ButtonTags.thumb_activity_ib_back);
//		ib_Down.setTag(Tags.ButtonTags.thumb_activity_ib_top);
		ib_Down.setTag(Tags.ButtonTags.ACTV_TN_IB_DOWN);
		
		ib_Down.setOnTouchListener(new BO_TL(this));
		ib_Down.setOnClickListener(new BO_CL(this));
		
		////////////////////////////////
		
		// Up
		
		////////////////////////////////
		ImageButton ib_Up = (ImageButton) findViewById(R.id.thumb_activity_ib_prev_page);
		
//		ib_Back.setTag(Tags.ButtonTags.thumb_activity_ib_back);
//		ib_Up.setTag(Tags.ButtonTags.thumb_activity_ib_top);
		ib_Up.setTag(Tags.ButtonTags.ACTV_TN_IB_UP);
		
		ib_Up.setOnTouchListener(new BO_TL(this));
		ib_Up.setOnClickListener(new BO_CL(this));
		
	}//_Setup_SetListeners_Navigations
	

	private void 
	_Setup_Options() {
		// TODO Auto-generated method stub
		////////////////////////////////

		// move mode

		////////////////////////////////
		// Log
		String msg_Log = "CONS.TNActv.moveMode => " + CONS.TNActv.moveMode;
		Log.d("HistUploadActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		if (CONS.TNActv.moveMode == true) {
			
			// Log
			msg_Log = "setting icon...";
			Log.d("HistUploadActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			////////////////////////////////

			// icon: move mode

			////////////////////////////////
			MenuItem item = CONS.TNActv.menu.findItem(R.id.thumb_actv_menu_move_mode);
			
			item.setIcon(R.drawable.ifm8_thumb_actv_opt_menu_move_mode_on);

			////////////////////////////////

			// icon: move files

			////////////////////////////////
			MenuItem item_MoveFiles = 
							CONS.TNActv.menu.findItem(R.id.thumb_actv_menu_move_files);
			
			item_MoveFiles.setIcon(R.drawable.ifm8_thumb_actv_opt_menu_move_file_2);
			
			item_MoveFiles.setEnabled(true);

		} else {
			
			// Log
			msg_Log = "icon => stays as is";
			Log.d("HistUploadActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			////////////////////////////////

			// icon: move files

			////////////////////////////////
			// icon: Move files
			if (CONS.TNActv.menu != null) {
				
				MenuItem item_MoveFiles = 
						CONS.TNActv.menu.findItem(R.id.thumb_actv_menu_move_files);
				
				item_MoveFiles.setIcon(R.drawable.main_opt_move_disabled);
				//			main_opt_move_disabled

				item_MoveFiles.setEnabled(false);
				
				msg_Log = String.format(
						Locale.JAPAN,
						"CONS.TNActv.menu != null"
						);
				
				Log.i("HistUploadActv.java" + "["
						+ Thread.currentThread().getStackTrace()[2].getLineNumber()
						+ "]", msg_Log);
				
			}
			
			//debug
			else {
				
				// Log
//				String msg_Log;
				
				msg_Log = String.format(
						Locale.JAPAN,
						"CONS.TNActv.menu => null"
						);
				
				Log.e("HistUploadActv.java" + "["
						+ Thread.currentThread().getStackTrace()[2].getLineNumber()
						+ "]", msg_Log);
				
			}
			
		}//if (CONS.TNActv.moveMode == true)
		
	}//_Setup_Options


	private void 
	_Setup_SetSelection() {
		// TODO Auto-generated method stub
		
		int target_Position;
		
		// If the current is larger than the previous,
		//	i.e. the position is increasing
		//	i.e. the list is scrolling downward
		//	=> modify the target
		if (CONS.TNActv.list_Pos_Current
				> CONS.TNActv.list_Pos_Prev) {
			
			int diff = CONS.TNActv.list_Pos_Current - 4;
			
			if (diff < 0) {
				
				diff = 0;
				
			}
			
			target_Position = diff;
//			target_Position = CONS.TNActv.list_Pos_Current - 5;
			
		} else {
			
			// If the current is smaller than the previous,
			//	i.e. the position is decreasing
			//	=> set the target with the current
			target_Position = CONS.TNActv.list_Pos_Current;

		}
		
		// Log
		String msg_Log = "CONS.TNActv.list_Pos_Current = "
						+ CONS.TNActv.list_Pos_Current
						+ " // "
						+ "CONS.TNActv.list_Pos_Prev = "
						+ CONS.TNActv.list_Pos_Prev
						+ " // "
						+ "target_Position = "
						+ target_Position
						;
		Log.d("HistUploadActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		//REF http://stackoverflow.com/questions/7561353/programmatically-scroll-to-a-specific-position-in-an-android-listview answered Sep 26 '11 at 21:39
		this.getListView().setSelection(target_Position);
		
	}//_Setup_SetSelection()


	private void 
	do_debug() {
		// TODO Auto-generated method stub
		
//		_test_D_23_3_V_1_1__GetTNfileNumbers();
//		_test_D_23_3_V_1_1__SaveThumbnail();
		
//		_do_debug__Pref_FontSize();
		
	}//do_debug


	private void _do_debug__Pref_FontSize() {
		// TODO Auto-generated method stub
		
		String fontSize = Methods.get_Pref_String(
				this, 
				CONS.Pref.pname_MainActv, 
				this.getString(R.string.prefs_tnactv_list_font_size_key), 
				null);
		
		// Log
		String msg_Log = "fontSize => " + fontSize;
		Log.d("HistUploadActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);

		
	}


	private void
	_Setup_Adapter() {
		// TODO Auto-generated method stub
		
		CONS.HistUploadActv.adp_HistUploadActv_Main = 
				new Adp_HistUpload(
						this,
						R.layout.list_row_histupload,
	//				R.layout.thumb_activity,
						CONS.HistUploadActv.list_HistUploadActv_Main
				);
		
//			CONS.TNActv.adp_TNActv_Main = new Adp_TIList(
//					this,
//					R.layout.list_row,
////				R.layout.thumb_activity,
//					CONS.TNActv.list_TNActv_Main
//					);
//			
		////////////////////////////////
		
		// Set adapter
		
		////////////////////////////////
		this.setListAdapter(CONS.HistUploadActv.adp_HistUploadActv_Main);
		
		// Log
		String msg_Log;
		
		msg_Log = String.format(
				Locale.JAPAN,
				"Adp_TIList => set"
				);
		
		Log.i("HistUploadActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
			
	}//_Setup_Adapter


	/******************************
		@return false => 1. can't build list<br>
	 ******************************/
	private boolean 
	_Setup_SetList() {
		// TODO Auto-generated method stub
		
		////////////////////////////////

		// build: list

		////////////////////////////////
		CONS.HistUploadActv.list_HistUploadActv_Main = 
								DBUtils.find_All_HistUpload(this);
		
		/******************************
			validate: null
		 ******************************/
		if (CONS.HistUploadActv.list_HistUploadActv_Main == null) {
			
			String msg = "list_HistUploadActv_Main => null";
			Methods_dlg.dlg_ShowMessage(this, msg);
			
			return false;
			
		}
		
		// Log
		String msg_Log;
		
		msg_Log = "list.size => " + CONS.HistUploadActv.list_HistUploadActv_Main.size();
		Log.d("HistUploadActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		////////////////////////////////

		// sort

		////////////////////////////////
		Methods.sort_List_HistUpload(
				CONS.HistUploadActv.list_HistUploadActv_Main, 
				CONS.Enums.SortType.CREATED_AT, 
//				CONS.Enums.SortType.FileName, 
				CONS.Enums.SortOrder.DESC);

		///////////////////////////////////
		//
		// show numOf TIs in the title bar
		//
		///////////////////////////////////
		// Log
//		String msg_Log;
		
		msg_Log = String.format(
				Locale.JAPAN,
				"%s (%d)", 
//				this.getClass().getName(),
//				Methods.conv_CurrentPath_to_DisplayPath(currentPath),
				CONS.DB.tname_UploadHistory,
				CONS.HistUploadActv.list_HistUploadActv_Main.size()
				);
		
		this.setTitle(msg_Log);
		
		return true;
		
	}//_Setup_SetList


	private void 
	_Setup_SetList__Search() {
		// TODO Auto-generated method stub
		
		////////////////////////////////

		// build: TI list

		////////////////////////////////
//		List<TI> list_TNActv_Main = DBUtils.find_All_TI__Search(this);
		CONS.TNActv.list_TNActv_Main = DBUtils.find_All_TI__Search(this);
		
		if (CONS.TNActv.list_TNActv_Main == null) {
//			if (list_TNActv_Main == null) {
			
			// Log
			String msg_Log = "CONS.TNActv.list_TNActv_Main => null";
			Log.d("HistUploadActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
		} else {
		
			// Log
			String msg_Log = "CONS.TNActv.list_TNActv_Main.size => "
							+ CONS.TNActv.list_TNActv_Main.size();
			Log.d("HistUploadActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
		
		}
		
		///////////////////////////////////
		//
		// sort
		//
		///////////////////////////////////
		Methods.sort_List_TI(
						CONS.TNActv.list_TNActv_Main, 
						CONS.Enums.SortType.FileName, 
						CONS.Enums.SortOrder.DESC);
		
		///////////////////////////////////
		//
		// show numOf TIs in the title bar
		//
		///////////////////////////////////
		// Log
		String msg_Log;
		
		msg_Log = String.format(
				Locale.JAPAN,
				"%s (%d)", 
//				this.getClass().getName(),
//				Methods.conv_CurrentPath_to_DisplayPath(currentPath),
				CONS.TNActv.list_TNActv_Main.get(0).getTable_name(),
				CONS.TNActv.list_TNActv_Main.size()
				);
		
		this.setTitle(msg_Log);

	}//_Setup_SetList__Search


	@Override
	protected void onStop() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		super.onStop();
		
		// Log
		Log.d("HistUploadActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "onStop()");

		////////////////////////////////

		// is running => false

		////////////////////////////////
		CONS.Admin.isRunning_HistUploadActv = false;
		
	}//onStop

	@Override
	protected void onDestroy() {
		/*********************************
		 * 1. super
		 * 2. move_mode => falsify
		 * 
		 * 3. History mode => Off
		 *********************************/
		
		super.onDestroy();
		
		// Log
		Log.d("HistUploadActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "onDestroy()");
		
		////////////////////////////////

		// reset: searchDone

		////////////////////////////////
		CONS.TNActv.searchDone = false;
		
		////////////////////////////////

		// reset: pref: list type

		////////////////////////////////
		boolean res = Methods.set_Pref_String(
							this, 
							CONS.Pref.pname_MainActv, 
							CONS.Pref.pkey_TNActv__ListType, 
							null);
		
		if (res == true) {
			
			// Log
			String msg_Log = "pref: list type => set to null";
			Log.d("HistUploadActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
		} else {

			// Log
			String msg_Log = "pref: list type => NOT set to null";
			Log.d("HistUploadActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
		}
		
	}//protected void onDestroy()

	@Override
	public void onBackPressed() {
		
		////////////////////////////////

		// reset: move mode

		////////////////////////////////
		if (CONS.TNActv.moveMode == true) {
			
			CONS.TNActv.moveMode = false;
			
//			MenuItem mi = CONS.TNActv.menu.getItem(0);
//			
//			// Log
//			String msg_Log = "mi => " + mi.getTitle().toString();
//			Log.d("HistUploadActv.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", msg_Log);
//			
//			Methods.reset_MoveMode_True(this, mi);
			
		}//if (move_mode == true)

		
		
		
		////////////////////////////////

		// finish

		////////////////////////////////
		this.finish();
		
		overridePendingTransition(0, 0);
		
	}//public void onBackPressed()

	@Override
	protected void 
	onListItemClick
	(ListView lv, View v, int position, long id) {
		/*----------------------------
		 * Steps
		 * 0. Vibrate
		 * 1. Get item
		 * 2. Intent
		 * 		2.1. Set data
		 * 
		 * 2-2. Record history
		 * 2-2-a. Update data
		 * 
		 * 2-3. Save preferences
		 * 
		 * 3. Start intent
			----------------------------*/
		/*----------------------------
		 * 0. Vibrate
			----------------------------*/
		CONS.Admin.vib.vibrate(CONS.Admin.vibLength_click);
		
		
		super.onListItemClick(lv, v, position, id);
		
	}//onListItemClick

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// 
//		MenuInflater mi = getMenuInflater();
//		mi.inflate(R.menu.thumb_actv_menu, menu);
//		
//		// get instance
//		CONS.TNActv.menu = menu;
		
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		return super.onOptionsItemSelected(item);
		
	}//public boolean onOptionsItemSelected(MenuItem item)


	//REF http://stackoverflow.com/questions/7066657/android-how-to-dynamically-change-menu-item-text-outside-of-onoptionsitemssele answered Aug 15 '11 at 15:27
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		
		return super.onPrepareOptionsMenu(menu);
		
	}


}//public class LogActv extends ListActivity
//