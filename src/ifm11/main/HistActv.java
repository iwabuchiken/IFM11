package ifm11.main;


import ifm11.adapters.Adp_TIList;
import ifm11.adapters.Adp_TIList_Move;
import ifm11.items.TI;
import ifm11.listeners.LOI_LCL;
import ifm11.listeners.button.BO_CL;
import ifm11.listeners.button.BO_TL;
import ifm11.utils.CONS;
import ifm11.utils.DBUtils;
import ifm11.utils.Methods;
import ifm11.utils.Methods_dlg;
import ifm11.utils.Tags;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class HistActv extends ListActivity {

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
		Log.d("HistActv.java" + "["
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
		
//		// Log
//		Log.d("HistActv.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", "onResume()");
//
//		if (TNActv.aAdapter != null) {
//					
//			TNActv.aAdapter.notifyDataSetChanged();
//			
//		}
//		TNActv.aAdapter.notifyDataSetChanged();

//		/*********************************
//		 * 3. Set selection
//		 *********************************/
//		lv_main = this.getListView();
//		
//		SharedPreferences prefs = this.getSharedPreferences(
//				MainActv.prefName_tnActv,
//				MODE_PRIVATE);
//	
//
//		//Methods.PrefenceLabels.thumb_actv.name()
//		
//		//int savedPosition = prefs.getInt("chosen_list_item", -1);
//		int savedPosition = prefs.getInt(
//							MainActv.prefName_tnActv_current_image_position,
//							-1);
//		
//		int target_position = savedPosition - (lv_main.getChildCount() / 2);
//		
//		if (target_position < 0) {
//			
//			target_position = 0;
//			
//		}//if (target_position == 0)
//
//		// Log
//		Log.d("HistActv.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", "target_position=" + target_position);
//		
//		
//		lv_main.setSelection(target_position);

		
//		// Log
//		Log.d("HistActv.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", "lv_main.getCheckedItemPosition()=" + lv_main.getCheckedItemPosition());
		
	}//protected void onResume()

	@Override
	protected void onStart() {
		/*********************************
		 * 1. super
		 * 2. Set selection
		 *********************************/
		super.onStart();
		
		// Log
		Log.d("HistActv.java" + "["
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
		if (CONS.Admin.isRunning_TNActv == false) {
			
			CONS.Admin.isRunning_TNActv = true;
			
		}

		////////////////////////////////

		// debug

		////////////////////////////////
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
		
		////////////////////////////////

		// set: selection

		////////////////////////////////
		_Setup_SetSelection();
		
		////////////////////////////////

		// setup: options

		////////////////////////////////
		_Setup_Options();
		
		////////////////////////////////

		// setup: listeners

		////////////////////////////////
		_Setup_SetListeners();
		
		////////////////////////////////
		
		// setup: listeners: navigation
		
		////////////////////////////////
		_Setup_SetListeners_Navigation();
		
//		/*----------------------------
//		 * 5. Initialize vars
//			----------------------------*/
//		checkedPositions = new ArrayList<Integer>();

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
		Log.d("HistActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		if (CONS.TNActv.moveMode == true) {
			
			// Log
			msg_Log = "setting icon...";
			Log.d("HistActv.java" + "["
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
			Log.d("HistActv.java" + "["
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
			
			target_Position = CONS.TNActv.list_Pos_Current - 5;
			
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
		Log.d("HistActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		//REF http://stackoverflow.com/questions/7561353/programmatically-scroll-to-a-specific-position-in-an-android-listview answered Sep 26 '11 at 21:39
		this.getListView().setSelection(target_Position);
		
	}//_Setup_SetSelection()


	private void do_debug() {
		// TODO Auto-generated method stub
//		_do_debug__Pref_FontSize();
		
	}


	private void _do_debug__Pref_FontSize() {
		// TODO Auto-generated method stub
		
		String fontSize = Methods.get_Pref_String(
				this, 
				CONS.Pref.pname_MainActv, 
				this.getString(R.string.prefs_tnactv_list_font_size_key), 
				null);
		
		// Log
		String msg_Log = "fontSize => " + fontSize;
		Log.d("HistActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);

		
	}


	private void 
	_Setup_Adapter() {
		// TODO Auto-generated method stub
		////////////////////////////////

		// dispatch: adapter

		////////////////////////////////
		if (CONS.TNActv.moveMode == false) {
			
			CONS.HistActv.adp_HistActv_Main = new Adp_TIList(
					this,
					R.layout.list_row,
//				R.layout.thumb_activity,
					CONS.HistActv.list_HistActv_Main
					);
			
			////////////////////////////////
			
			// Set adapter
			
			////////////////////////////////
			this.setListAdapter(CONS.HistActv.adp_HistActv_Main);
			
		} else {
			
			CONS.HistActv.adp_HistActv_Main_Move = new Adp_TIList_Move(
//					CONS.TNActv.adp_TNActv_Main_Move = new Adp_TIList_Move(
					this,
					R.layout.list_row,
//					R.layout.thumb_activity,
					CONS.HistActv.list_HistActv_Main
//					CONS.TNActv.list_TNActv_Main
					);

			////////////////////////////////
			
			// Set adapter
			
			////////////////////////////////
			this.setListAdapter(CONS.HistActv.adp_HistActv_Main_Move);

		}
		
//		CONS.ALActv.adp_AIList = new Adp_AIList(
//				this,
//				R.layout.list_row_ai_list,
//				CONS.ALActv.list_AI
//				);
		
		
	}//_Setup_Adapter


	/******************************
		@return false => 1. can't build list<br>
	 ******************************/
	private boolean 
	_Setup_SetList() {
		// TODO Auto-generated method stub

		String msg_Log;
		
		////////////////////////////////

		// build: list

		////////////////////////////////
		int limit = 20;
		
		CONS.HistActv.list_HistActv_Main = DBUtils.find_TIs_History(this, limit);
		
		/******************************
			validate: null
		 ******************************/
		if (CONS.HistActv.list_HistActv_Main == null) {
			
			String msg = "list_HistActv_Main => null";
			Methods_dlg.dlg_ShowMessage(this, msg);
			
			return false;
			
		}
		
		// Log
		msg_Log = "list.size => " + CONS.HistActv.list_HistActv_Main.size();
		Log.d("HistActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
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
				this.getClass().getName(),
				CONS.HistActv.list_HistActv_Main.size()
				);
		
		this.setTitle(msg_Log);

		
//		////////////////////////////////
//
//		// sort
//
//		////////////////////////////////
//		Methods.sort_List_TI(
//				CONS.TNActv.list_TNActv_Main, 
//				CONS.Enums.SortType.CREATED_AT, 
//				CONS.Enums.SortOrder.ASC);
		
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
			Log.d("HistActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
		} else {
		
			// Log
			String msg_Log = "CONS.TNActv.list_TNActv_Main.size => "
							+ CONS.TNActv.list_TNActv_Main.size();
			Log.d("HistActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
		
		}
		
	}//_Setup_SetList__Search


	@Override
	protected void onStop() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		super.onStop();
		
		// Log
		Log.d("HistActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "onStop()");

		////////////////////////////////

		// is running => false

		////////////////////////////////
		CONS.Admin.isRunning_TNActv = false;
		
	}

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
		Log.d("HistActv.java" + "["
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
			Log.d("HistActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
		} else {

			// Log
			String msg_Log = "pref: list type => NOT set to null";
			Log.d("HistActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
		}
		
	}//protected void onDestroy()

	@Override
	public void onBackPressed() {
		/*----------------------------
		 * memo
			----------------------------*/
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
		
		////////////////////////////////

		// get: item

		////////////////////////////////
		TI ti = (TI) lv.getItemAtPosition(position);
		
		////////////////////////////////

		// set: pref: position

		////////////////////////////////
		boolean res = Methods.set_Pref_Int(
							this, 
							CONS.Pref.pname_MainActv, 
							CONS.Pref.pkey_CurrentPosition_TNActv, 
							position);
		
		// Log
		String msg_Log = "set pref: current position => " + res;
		Log.d("HistActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		// Notify
		CONS.HistActv.adp_HistActv_Main.notifyDataSetChanged();
//		CONS.TNActv.adp_TNActv_Main.notifyDataSetChanged();
		
		////////////////////////////////

		// validate: file exists

		////////////////////////////////
		String fpath = StringUtils.join(
				new String[]{
						
						ti.getFile_path(),
						ti.getFile_name()
				}, 
				File.separator);
		
		res = Methods.file_Exists(this, fpath);
		
		if (res == false) {
			
			String msg = "File doesn't exist => " + fpath;
			Methods_dlg.dlg_ShowMessage(this, msg);
			
			return;
			
		}
		
		////////////////////////////////

		// Setup: intent

		////////////////////////////////
		Intent i = new Intent();
		
		i.setClass(this, ImageActv.class);
		
		// Log
		msg_Log = "ti.getDb_Id() => " + ti.getDb_Id();
		Log.d("HistActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		i.putExtra("file_id", ti.getFileId());
		i.putExtra("db_id", ti.getDb_Id());
		i.putExtra("file_path", fpath);
//		i.putExtra("file_path", ti.getFile_path());
		i.putExtra("file_name", ti.getFile_name());
		
		i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

		startActivity(i);
		
	}//onListItemClick

	private void 
	onListItemClick__Move
	(ListView lv, int position) {
		// TODO Auto-generated method stub
		////////////////////////////////

		// get: item

		////////////////////////////////
		TI ti = (TI) lv.getItemAtPosition(position);

		////////////////////////////////

		// register: position

		////////////////////////////////
		if (CONS.TNActv.checkedPositions == null) {
			
			CONS.TNActv.checkedPositions = new ArrayList<Integer>();
					
		}
		
		CONS.TNActv.checkedPositions.add(position);
		
		////////////////////////////////

		// notify

		////////////////////////////////
		if (CONS.TNActv.adp_TNActv_Main_Move != null) {
			
			CONS.TNActv.adp_TNActv_Main_Move.notifyDataSetChanged();
			
		}//if (bAdapter != null)
		
		// Log
		Log.d("HistActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "New position => " + position +
				" / " + "(length=" + CONS.TNActv.checkedPositions.size() + ")");
		
	}//onListItemClick__Move


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// 
		MenuInflater mi = getMenuInflater();
		mi.inflate(R.menu.thumb_actv_menu, menu);
		
		// get instance
		CONS.TNActv.menu = menu;
		
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		/*----------------------------
		 * Steps
		 * 1. R.id.thumb_actv_menu_move_mode
		 * 2. R.id.thumb_actv_menu_move_files
			----------------------------*/
		
		
		case R.id.thumb_actv_menu_move_mode://---------------------------------------
			
			// Log
			String msg_Log = "thumb_actv_menu_move_mode";
			Log.d("HistActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			Methods.move_Mode(this, item);
			
			break;// case R.id.thumb_actv_menu_move_files
		
		case R.id.thumb_actv_menu_move_files:	//------------------------------------------
			
			_case_Opt_MoveFiles();
			
//			if (move_mode == false) {
//				
//				// debug
//				Toast.makeText(this, "Move mode is not on", 2000)
//						.show();
//				
//				return false;
//				
//			} else if (move_mode == true) {
//				/*----------------------------
//				 * Steps
//				 * 1. checkedPositions => Has contents?
//				 * 2. If yes, show dialog
//					----------------------------*/
//				if (checkedPositions.size() < 1) {
//					
//					// debug
//					Toast.makeText(TNActv.this, "No item selected", 2000).show();
//					
//					return false;
//					
//				}//if (checkedPositions.size() < 1)
//				
//				
//				/*----------------------------
//				 * 2. If yes, show dialog
//					----------------------------*/
////				Methods_dlg.dlg_ChooseMoveMode(this);
////				Methods_dlg.dlg_moveFiles(this);
//				
//			}//if (move_mode == false)
			
			break;// case R.id.thumb_actv_menu_move_files
			
		}//switch (item.getItemId())
		
		
		
		return super.onOptionsItemSelected(item);
		
	}//public boolean onOptionsItemSelected(MenuItem item)


	private void 
	_case_Opt_MoveFiles() {
		// TODO Auto-generated method stub
		
		////////////////////////////////

		// validate: any positions

		////////////////////////////////
		if (CONS.TNActv.checkedPositions.size() < 1) {
			
			String msg = "No checked positions";
			Methods_dlg.dlg_ShowMessage(this, msg, R.color.red);
			
			return;
			
		}
		
		////////////////////////////////

		// start: dialog

		////////////////////////////////
		Methods_dlg.dlg_MoveFiles(this);
		
	}//_case_Opt_MoveFiles


	//REF http://stackoverflow.com/questions/7066657/android-how-to-dynamically-change-menu-item-text-outside-of-onoptionsitemssele answered Aug 15 '11 at 15:27
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		
		// Log
		String msg_Log = "onPrepareOptionsMenu";
		Log.d("HistActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		// get instance
		CONS.TNActv.menu = menu;
		
		this._Setup_Options();
		
		
		return super.onPrepareOptionsMenu(menu);
		
	}


}//public class TNActv
