package ifm11.listeners.button;

import java.io.File;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

import ifm11.items.TI;
import ifm11.main.ImageActv;
import ifm11.main.TNActv;
import ifm11.utils.CONS;
import ifm11.utils.DBUtils;
import ifm11.utils.Methods;
import ifm11.utils.Methods_dlg;
import ifm11.utils.Tags;
import ifm11.utils.STD;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

public class BO_CL implements OnClickListener {
	/*----------------------------
	 * Fields
		----------------------------*/
	//
	Activity actv;

	//
	Vibrator vib;
	
	//
	int position;
	
	public BO_CL(Activity actv, int position) {
		//
		this.actv = actv;
		this.position = position;
		
		//
		CONS.Admin.vib = (Vibrator) actv.getSystemService(Context.VIBRATOR_SERVICE);
		
		
		
	}//public ButtonOnClickListener(Activity actv, int position)

	public BO_CL(Activity actv) {
		
		this.actv = actv;
		
		//
		CONS.Admin.vib = (Vibrator) actv.getSystemService(Context.VIBRATOR_SERVICE);

	}

	//	@Override
	public void onClick(View v) {
//		//
		Tags.ButtonTags tag = (Tags.ButtonTags) v.getTag();
//
		CONS.Admin.vib.vibrate(CONS.Admin.vibLength_click);
		
		//
		switch (tag) {

		case ACTV_SEARCH_BT_OK://-----------------------------------------------------
			
			case_ACTV_SEARCH_BT_OK();
			
			break;
			
		case GENERIC_ACTV_BT_CANCEL://------------------------------------------------
			
			case_GENERIC_ACTV_BT_CANCEL();
			
			break;
			
		case ib_up:
			
			case_ACTV_MAIN_IB_UP();
			
			break;

		case TILIST_CB://-----------------------------------------------------------------------------
			/*----------------------------
			 * Steps
			 * 1. If already checked, unlist from ThumbnailActivity.checkedPositions
			 * 2. If not yet, enlist into it
			 * 3. Then, notify to adapter
				----------------------------*/
			/*----------------------------
			 * 1. If already checked, unlist from ThumbnailActivity.checkedPositions
				----------------------------*/
			case_TILIST_CB();
			
			/*----------------------------
			 * 3. Then, notify to adapter
				----------------------------*/
//			TNActv.aAdapter.notifyDataSetChanged();
//			CONS.TNActv.adp_TNActv_Main_Move.notifyDataSetChanged();
			
			break;

		case ACTV_TN_IB_BACK://-----------------------------------------------------------------------------
		case ACTV_SHOWLOG_IB_BACK://-----------------------------------------------------------------------------
		case image_activity_back://-----------------------------------------------------------------------------
		case ACTV_HISTUPLOAD_IB_BACK://-----------------------------------------------------------------------------
			
			case_ACTV_TN_IB_BACK();
			
			break;
			
		case ACTV_TN_IB_TOP://-----------------------------------------------------------------------------
			
			case_ACTV_TN_IB_TOP();
			
			break;
			
		case ACTV_HISTUPLOAD_IB_TOP://-----------------------------------------------------------------------------
			
			case_ACTV_HISTUPLOAD_IB_TOP();
			
			break;
			
		case ACTV_SHOWLOG_IB_TOP://-----------------------------------------------------------------------------
			
			case_ACTV_SHOWLOG_IB_TOP();
			
			break;
			
		case ACTV_LOG_IB_TOP://-----------------------------------------------------------------------------
			
			case_ACTV_LOG_IB_TOP();
			
			break;
			
		case ACTV_TN_IB_BOTTOM://-----------------------------------------------------------------------------
			
			case_ACTV_TN_IB_BOTTOM();
			
			break;
			
		case ACTV_HISTUPLOAD_IB_BOTTOM://-----------------------------------------------------------------------------
			
			case_ACTV_HISTUPLOAD_IB_BOTTOM();
			
			break;
			
		case ACTV_SHOWLOG_IB_BOTTOM://-----------------------------------------------------------------------------
			
			case_ACTV_SHOWLOG_IB_BOTTOM();
			
			break;
			
		case ACTV_LOG_IB_BOTTOM://-----------------------------------------------------------------------------
			
			case_ACTV_LOG_IB_BOTTOM();
			
			break;
			
		case ACTV_TN_IB_DOWN://-----------------------------------------------------------------------------
			
			case_ACTV_TN_IB_DOWN();
			
			break;
			
		case ACTV_HISTUPLOAD_IB_DOWN://-----------------------------------------------------------------------------
			
			case_ACTV_HISTUPLOAD_IB_DOWN();
			
			break;
			
		case ACTV_SHOWLOG_IB_DOWN://-----------------------------------------------------------------------------
			
			case_ACTV_SHOWLOG_IB_DOWN();
			
			break;
			
		case ACTV_LOG_IB_DOWN://-----------------------------------------------------------------------------
			
			case_ACTV_LOG_IB_DOWN();
			
			break;
			
		case ACTV_TN_IB_UP://-----------------------------------------------------------------------------
			
			case_ACTV_TN_IB_UP();
			
			break;
			
		case ACTV_HISTUPLOAD_IB_UP://-----------------------------------------------------------------------------
			
			case_ACTV_HISTUPLOAD_IB_UP();
			
			break;
			
		case ACTV_SHOWLOG_IB_UP://-----------------------------------------------------------------------------
			
			case_ACTV_SHOWLOG_IB_UP();
			
			break;
			
		case ACTV_LOG_IB_UP://-----------------------------------------------------------------------------
			
			case_ACTV_LOG_IB_UP();
			
			break;
			
//		case thumb_activity_ib_bottom: //----------------------------------------------
//			
//			int numOfGroups = TNActv.tiList.size() / lv.getChildCount();
//			
//			int indexOfLastChild = lv.getChildCount() * numOfGroups;
//			
//			lv.setSelection(indexOfLastChild);
//
//			break;// case thumb_activity_ib_bottom 
			
//		case thumb_activity_ib_top://--------------------------------------------
//			
//			vib.vibrate(CONS.vibLength_click);
//			
//			lv.setSelection(0);
//			
//			break;// thumb_activity_ib_top

		case image_activity_prev://----------------------------------------------------
			
			image_activity_prev();
			
			
			break;// case image_activity_prev
			
		case image_activity_next://----------------------------------------------------

			image_activity_next();
			
			break;// case image_activity_next
			
		default:
			break;
		}//switch (tag)
		
	}//public void onClick(View v)

	private void case_ACTV_SEARCH_BT_OK() {
		// TODO Auto-generated method stub
		
		Methods.searchItem_SearchActv(actv);
		
	}//case_ACTV_SEARCH_BT_OK

	private void case_GENERIC_ACTV_BT_CANCEL() {
		// TODO Auto-generated method stub
		
		actv.finish();
		
		actv.overridePendingTransition(0, 0);
		
	}//case_GENERIC_ACTV_BT_CANCEL

	private void 
	image_activity_next() {
		/*********************************
		 * 1. Get prefs => current position
		 * 2. No more next?
		 * 
		 * 3. Get the next item in the ti list
		 * 4. New image file path
		 * 
		 * 5. Show the next image
		 * 
		 * 6. Set new pref value
		 * 
		 * 7. Update the file name label
		 * 
		 * 8. Save history
		 * 
		 *********************************/
		int savedPosition = Methods.get_Pref_Int(
				actv, 
				CONS.Pref.pname_MainActv, 
				CONS.Pref.pkey_CurrentPosition_TNActv, 
				CONS.Pref.dflt_IntExtra_value);
		
		// Log
		Log.d("BO_CL.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "savedPosition=" + savedPosition);
		
		/*********************************
		 * 2. No more next?
		 *********************************/
//		if (savedPosition == 0) {
		if (savedPosition >= CONS.TNActv.list_TNActv_Main.size() - 1) {
			
//			// debug
//			Toast.makeText(actv, "No next images", Toast.LENGTH_SHORT).show();
			
			String msg = "No next images";
			Methods_dlg.dlg_ShowMessage(actv, msg);
			
			return;
			
		}//if (savedPosition == 0)

		/*********************************
		 * 3. Get the next item in the ti list
		 *********************************/
		TI ti_Next = CONS.TNActv.list_TNActv_Main.get(savedPosition + 1);
//		
		/*********************************
		 * 4. New image file path
		 *********************************/
		String image_file_path_new = StringUtils.join(
				
				new String[]{
						ti_Next.getFile_path(),
						ti_Next.getFile_name()
				},
				File.separator
		);
	
		// Log
		Log.d("BO_CL.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "image_file_path_new=" + image_file_path_new);
		
		////////////////////////////////

		// validate: file exists

		////////////////////////////////
		File f = new File(image_file_path_new);
		
		if (!f.exists()) {
			
			///////////////////////////////////
			//
			// validate: internal?
			//
			///////////////////////////////////
			String fpath = StringUtils.join(
							new String[]{
									
									CONS.DB.dPath_Data_SDCard + "/DCIM/Camera",
//									ti.getFile_path(),
									ti_Next.getFile_name()
//									ti.getFile_name()
							}, 
							File.separator);

			if (Methods.file_Exists(actv, fpath)) {

				// Log
				String msg_Log;
				
				msg_Log = String.format(
						Locale.JAPAN,
						"Next file exists => %s", fpath
						);
				
				Log.i("TNActv.java" + "["
						+ Thread.currentThread().getStackTrace()[2].getLineNumber()
						+ "]", msg_Log);

				///////////////////////////////////
				//
				// reset: file
				//
				///////////////////////////////////
				image_file_path_new = fpath;
				
				f = new File(image_file_path_new);
				
			} else {//if (Methods.file_Exists(this, fpath))
				
				String msg = "No such file => " + fpath;
//				String msg = "File doesn't exist => " + fpath;
				Methods_dlg.dlg_ShowMessage(actv, msg);
				
				return;
				
			}//if (Methods.file_Exists(this, fpath))

			
//			String msg = "No such file => " + f.getAbsolutePath();
//			Methods_dlg.dlg_ShowMessage(actv, msg);
//			
//			return;
			
		}//if (!f.exists())
		
//		
		/*********************************
		 * 5. Show the Next image
		 *********************************/
//		ImageActv.bm = null;
		CONS.IMageActv.bm_Modified.recycle();
		
//		CONS.IMageActv.bm_Modified = null;	//=> n/c --> "11985408-byte external allocation too large for this process."

		CONS.IMageActv.bm_Modified = BitmapFactory.decodeFile(image_file_path_new);
		
		ImageActv.LL.removeView(CONS.IMageActv.v);
		
		CONS.IMageActv.v.setImageBitmap(CONS.IMageActv.bm_Modified);
		
		ImageActv.LL.addView(CONS.IMageActv.v);
		
		
//		ImageActv.LL.removeView(ImageActv.v);
//		
//		ImageActv.v.setImageBitmap(ImageActv.bm);
		
//		ImageActv.LL.addView(ImageActv.v);
//		ImageActv.bm.recycle();
//		
//		ImageActv.bm = BitmapFactory.decodeFile(image_file_path_new);
//		
//		ImageActv.LL.removeView(ImageActv.v);
//		
//		ImageActv.v.setImageBitmap(ImageActv.bm);
//		
////		ImageActv.LL.addView(ImageActv.v);
//		
		// Log
		Log.d("BO_CL.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "New image added");
//		
		/*********************************
		 * 6. Set new pref value
		 *********************************/
		boolean res = Methods.set_Pref_Int(
				actv, 
				CONS.Pref.pname_MainActv, 
				CONS.Pref.pkey_CurrentPosition_TNActv, 
				savedPosition + 1);

		// Log
		if (res == true) {
			
			Log.d("BO_CL.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Current position updated => " + (savedPosition + 1));
			
		} else {
			
			Log.d("BO_CL.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Current position can't be updated => " + (savedPosition + 1));

		}

		// Log
		Log.d("BO_CL.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "Prefs set: " + (savedPosition + 1));

		/*********************************
		 * 7. Update the file name label
		 *********************************/
		ImageActv.tv_file_name.setText(ti_Next.getFile_name());

		/*********************************
		 * 8. Save history
		 *********************************/
		////////////////////////////////

		// update: last viewed at

		////////////////////////////////
		int res_i = DBUtils.update_TI(
				actv, 
				ti_Next, 
				CONS.DB.col_names_IFM11_full[10],
				Methods.conv_MillSec_to_TimeLabel(
						STD.getMillSeconds_now())
				);
		
		String msg = null;
		
		switch(res_i) {
//			-1 => Insertion failed
//			-2 => Exception
//			1 => Insertion done
		case -1:
			
			msg = "last_viewed_at => not inserted";
			
			break;
			
		case -2:
			
			msg = "last_viewed_at => Exception";
			
			break;
			
		case 1:
			
			msg = "Insertion done";
			
			break;
			
		default:
			msg = "Unknown result => " + res;
			
			break;
			
		}
		
		// Log
		Log.d("BO_CL.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg);
		
	}//private void image_activity_next()

	private void 
	image_activity_prev() {
		/*********************************
		 * 1. Get prefs => current position
		 * 2. No more prev?
		 * 
		 * 3. Get the previous item in the ti list
		 * 4. New image file path
		 * 
		 * 5. Show the previous image
		 * 
		 * 6. Set new pref value
		 * 
		 * 7. Update the file name label
		 * 
		 * 8. Save history
		 *********************************/
//		SharedPreferences prefs = actv.getSharedPreferences(
//					MainActv.prefName_tnActv,
//					actv.MODE_PRIVATE);
		
 
		//Methods.PrefenceLabels.thumb_actv.name()
		
		//int savedPosition = prefs.getInt("chosen_list_item", -1);
		int savedPosition = Methods.get_Pref_Int(
						actv, 
						CONS.Pref.pname_MainActv, 
						CONS.Pref.pkey_CurrentPosition_TNActv, 
						CONS.Pref.dflt_IntExtra_value);
		
//		int savedPosition = prefs.getInt(
//				MainActv.prefName_tnActv_current_image_position,
//				-1);
		
		// Log
		Log.d("BO_CL.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "savedPosition=" + savedPosition);
		
		/*********************************
		 * 2. No more prev?
		 *********************************/
		if (savedPosition == 0) {

			String msg = "No previous images";
			Methods_dlg.dlg_ShowMessage(actv, msg);
			
//			// debug
//			Toast.makeText(actv, "No previous images", Toast.LENGTH_SHORT).show();
			
			return;
			
		}//if (savedPosition == 0)

		/*********************************
		 * 3. Get the previous item in the ti list
		 *********************************/
		TI ti_prev = CONS.TNActv.list_TNActv_Main.get(savedPosition - 1);
		
		/*********************************
		 * 4. New image file path
		 *********************************/
//		String image_file_path_new = ti_prev.getFile_path();
		String image_file_path_new = StringUtils.join(
				
				new String[]{
						ti_prev.getFile_path(),
						ti_prev.getFile_name()
				},
				File.separator
		);
	
		// Log
		Log.d("BO_CL.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "image_file_path_new=" + image_file_path_new);

		////////////////////////////////

		// validate: file exists

		////////////////////////////////
		File f = new File(image_file_path_new);
		
		if (!f.exists()) {

			///////////////////////////////////
			//
			// validate: internal?
			//
			///////////////////////////////////
			String fpath = StringUtils.join(
							new String[]{
									
									CONS.DB.dPath_Data_SDCard + "/DCIM/Camera",
//									ti.getFile_path(),
									ti_prev.getFile_name()
//									ti.getFile_name()
							}, 
							File.separator);

			if (Methods.file_Exists(actv, fpath)) {

				// Log
				String msg_Log;
				
				msg_Log = String.format(
						Locale.JAPAN,
						"Previous file exists => %s", fpath
						);
				
				Log.i("TNActv.java" + "["
						+ Thread.currentThread().getStackTrace()[2].getLineNumber()
						+ "]", msg_Log);

				///////////////////////////////////
				//
				// reset: file
				//
				///////////////////////////////////
				image_file_path_new = fpath;
				
				f = new File(image_file_path_new);
				
			} else {//if (Methods.file_Exists(this, fpath))
				
				String msg = "No such file => " + fpath;
//				String msg = "File doesn't exist => " + fpath;
				Methods_dlg.dlg_ShowMessage(actv, msg);
				
				return;
				
			}//if (Methods.file_Exists(this, fpath))

			
//			String msg = "No such file => " + f.getAbsolutePath();
//			Methods_dlg.dlg_ShowMessage(actv, msg);
//			
//			return;

//			String msg = "No such file => " + f.getAbsolutePath();
//			Methods_dlg.dlg_ShowMessage(actv, msg);
//			
//			return;
			
		}

		/*********************************
		 * 5. Show the previous image
		 *********************************/
		CONS.IMageActv.bm_Modified.recycle();
		
		CONS.IMageActv.bm_Modified = BitmapFactory.decodeFile(image_file_path_new);
		
		ImageActv.LL.removeView(CONS.IMageActv.v);
		
		CONS.IMageActv.v.setImageBitmap(CONS.IMageActv.bm_Modified);
		
		ImageActv.LL.addView(CONS.IMageActv.v);

		
//		ImageActv.bm.recycle();
//		
//		ImageActv.bm = BitmapFactory.decodeFile(image_file_path_new);
//		
//		ImageActv.LL.removeView(ImageActv.v);
//		
//		ImageActv.v.setImageBitmap(ImageActv.bm);
//		
//		ImageActv.LL.addView(ImageActv.v);
//		
//		// Log
//		Log.d("BO_CL.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", "New image added");
		
		/*********************************
		 * 6. Set new pref value
		 *********************************/
//		SharedPreferences.Editor editor = prefs.edit();
		
//		editor.putInt(MainActv.prefName_tnActv_current_image_position, savedPosition - 1);
//		editor.commit();

		boolean res = Methods.set_Pref_Int(
				actv, 
				CONS.Pref.pname_MainActv, 
				CONS.Pref.pkey_CurrentPosition_TNActv, 
				savedPosition - 1);

		// Log
		Log.d("BO_CL.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "Prefs updated => " + (savedPosition - 1));
		
		/*********************************
		 * 7. Update the file name label
		 *********************************/
		ImageActv.tv_file_name.setText(ti_prev.getFile_name());

		////////////////////////////////

		// update: last viewed at

		////////////////////////////////
		int res_i = DBUtils.update_TI(
				actv, 
				ti_prev, 
				CONS.DB.col_names_IFM11_full[10],
				Methods.conv_MillSec_to_TimeLabel(
						STD.getMillSeconds_now())
				);
		
		String msg = null;
		
		switch(res_i) {
//			-1 => Insertion failed
//			-2 => Exception
//			1 => Insertion done
		case -1:
			
			msg = "last_viewed_at => not inserted";
			
			break;
			
		case -2:
			
			msg = "last_viewed_at => Exception";
			
			break;
			
		case 1:
			
			msg = "Insertion done";
			
			break;
			
		default:
			msg = "Unknown result => " + res;
			
			break;
			
		}
		
		// Log
		Log.d("BO_CL.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg);
		
	}//private void image_activity_prev()

	private void 
	case_ACTV_TN_IB_TOP() {
		// TODO Auto-generated method stub
		
		/******************************
			validate: list
		 ******************************/
		if (CONS.TNActv.list_TNActv_Main == null) {
			
//			DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
			
			CONS.TNActv.list_TNActv_Main = DBUtils.find_All_TI(actv, CONS.DB.tname_IFM11);
			
		}
		
		ListView lv = ((ListActivity) actv).getListView();
		
		lv.setSelection(0);
		
	}//case_ACTV_TN_IB_TOP

	private void 
	case_ACTV_HISTUPLOAD_IB_TOP() {
		// TODO Auto-generated method stub
		
		/******************************
			validate: list
		 ******************************/
		if (CONS.HistUploadActv.list_HistUploadActv_Main == null) {
//			if (CONS.TNActv.list_TNActv_Main == null) {
			
//			DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
			
//			CONS.TNActv.list_TNActv_Main = DBUtils.find_All_TI(actv, CONS.DB.tname_IFM11);

			// build: list
			CONS.HistUploadActv.list_HistUploadActv_Main = 
									DBUtils.find_All_HistUpload(actv);

			// validate
			if (CONS.HistUploadActv.list_HistUploadActv_Main == null) {
				
				String msg = "list_HistUploadActv_Main => null";
				Methods_dlg.dlg_ShowMessage(actv, msg);
				
				return;
				
			}

			////////////////////////////////

			// sort

			////////////////////////////////
			Methods.sort_List_HistUpload(
					CONS.HistUploadActv.list_HistUploadActv_Main, 
					CONS.Enums.SortType.CREATED_AT, 
//					CONS.Enums.SortType.FileName, 
					CONS.Enums.SortOrder.DESC);
			
		}//if (CONS.HistUploadActv.list_HistUploadActv_Main == null)
		
		ListView lv = ((ListActivity) actv).getListView();
		
		lv.setSelection(0);
		
	}//case_ACTV_HISTUPLOAD_IB_TOP
	
	private void 
	case_ACTV_SHOWLOG_IB_TOP() {
		// TODO Auto-generated method stub
		
//		/******************************
//			validate: list
//		 ******************************/
//		if (CONS.ShowLogActv.list_ShowLog_Files == null) {
//			
////			DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
//			
//			CONS.ShowLogActv.list_ShowLog_Files = 
//							Methods.get_LogItem_List(actv);
//			
//		}
		
		ListView lv = ((ListActivity) actv).getListView();
		
		lv.setSelection(0);
		
	}//case_ACTV_TN_IB_TOP
	
	private void 
	case_ACTV_LOG_IB_TOP() {
		// TODO Auto-generated method stub
		
//		/******************************
//			validate: list
//		 ******************************/
//		if (CONS.ShowLogActv.list_ShowLog_Files == null) {
//			
////			DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
//			
//			CONS.ShowLogActv.list_ShowLog_Files = 
//							Methods.get_LogItem_List(actv);
//			
//		}
		
		ListView lv = ((ListActivity) actv).getListView();
		
		lv.setSelection(0);
		
	}//case_ACTV_TN_IB_TOP
	
	
	private void 
	case_ACTV_TN_IB_BOTTOM() {
		// TODO Auto-generated method stub
		
		/******************************
			validate: list
		 ******************************/
		if (CONS.TNActv.list_TNActv_Main == null) {
			
//			DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
			
			CONS.TNActv.list_TNActv_Main = DBUtils.find_All_TI(actv, CONS.DB.tname_IFM11);
			
		}
		
		ListView lv = ((ListActivity) actv).getListView();
		
		int numOfGroups = CONS.TNActv.list_TNActv_Main.size() / lv.getChildCount();
		
		int indexOfLastChild = lv.getChildCount() * numOfGroups;
		
		lv.setSelection(indexOfLastChild);
		
	}//case_ACTV_TN_IB_TOP
	
	private void 
	case_ACTV_HISTUPLOAD_IB_BOTTOM() {
		// TODO Auto-generated method stub
		
		/******************************
			validate: list
		 ******************************/
		if (CONS.HistUploadActv.list_HistUploadActv_Main == null) {
	//		if (CONS.TNActv.list_TNActv_Main == null) {
			
	//		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
			
	//		CONS.TNActv.list_TNActv_Main = DBUtils.find_All_TI(actv, CONS.DB.tname_IFM11);
	
			// build: list
			CONS.HistUploadActv.list_HistUploadActv_Main = 
									DBUtils.find_All_HistUpload(actv);
	
			// validate
			if (CONS.HistUploadActv.list_HistUploadActv_Main == null) {
				
				String msg = "list_HistUploadActv_Main => null";
				Methods_dlg.dlg_ShowMessage(actv, msg);
				
				return;
				
			}
	
			////////////////////////////////
	
			// sort
	
			////////////////////////////////
			Methods.sort_List_HistUpload(
					CONS.HistUploadActv.list_HistUploadActv_Main, 
					CONS.Enums.SortType.CREATED_AT, 
	//				CONS.Enums.SortType.FileName, 
					CONS.Enums.SortOrder.DESC);
			
		}//if (CONS.HistUploadActv.list_HistUploadActv_Main == null)
		
		ListView lv = ((ListActivity) actv).getListView();
		
		int numOfGroups = CONS.HistUploadActv.list_HistUploadActv_Main.size() 
								/ lv.getChildCount();
//		int numOfGroups = CONS.TNActv.list_TNActv_Main.size() / lv.getChildCount();
		
		int indexOfLastChild = lv.getChildCount() * numOfGroups;
		
		lv.setSelection(indexOfLastChild);
		
	}//case_ACTV_HISTUPLOAD_IB_BOTTOM
	
	private void 
	case_ACTV_SHOWLOG_IB_BOTTOM() {
		// TODO Auto-generated method stub
		
		/******************************
			validate: list
		 ******************************/
		if (CONS.ShowLogActv.list_ShowLog_Files == null) {
			
			CONS.ShowLogActv.list_ShowLog_Files = 
							Methods.get_LogItem_List(actv);
			
		}
			
		ListView lv = ((ListActivity) actv).getListView();
		
		int numOfGroups = 
					CONS.ShowLogActv.list_ShowLog_Files.size() / lv.getChildCount();
		
		int indexOfLastChild = lv.getChildCount() * numOfGroups;
		
		lv.setSelection(indexOfLastChild);
		
	}//case_ACTV_TN_IB_TOP
	
	private void 
	case_ACTV_LOG_IB_BOTTOM() {
		// TODO Auto-generated method stub
		
		/******************************
			validate: list
		 ******************************/
		if (CONS.LogActv.list_LogFiles == null) {
			
			CONS.LogActv.list_LogFiles = 
					Methods.get_LogFileNames_List(actv);
			
		}
		
		ListView lv = ((ListActivity) actv).getListView();
		
		int numOfGroups = 
				CONS.LogActv.list_LogFiles.size() / lv.getChildCount();
		
		int indexOfLastChild = lv.getChildCount() * numOfGroups;
		
		lv.setSelection(indexOfLastChild);
		
	}//case_ACTV_LOG_IB_BOTTOM
	
	
	private void 
	case_ACTV_TN_IB_DOWN() {
		// TODO Auto-generated method stub
		
		/******************************
			validate: list
		 ******************************/
		if (CONS.TNActv.list_TNActv_Main == null) {
			
//			DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
			
			CONS.TNActv.list_TNActv_Main = DBUtils.find_All_TI(actv, CONS.DB.tname_IFM11);
			
		}
		
		ListView lv = ((ListActivity) actv).getListView();
		
		int new_Position = lv.getLastVisiblePosition();
		
		if((new_Position + lv.getChildCount()) 
				> CONS.TNActv.list_TNActv_Main.size()) {
			
			new_Position = CONS.TNActv.list_TNActv_Main.size() - lv.getChildCount();
			
		}
		
		lv.setSelection(new_Position);
		
	}//case_ACTV_TN_IB_TOP
	
	private void 
	case_ACTV_HISTUPLOAD_IB_DOWN() {
		// TODO Auto-generated method stub
		
		/******************************
			validate: list
		 ******************************/
		if (CONS.HistUploadActv.list_HistUploadActv_Main == null) {
//			if (CONS.TNActv.list_TNActv_Main == null) {
			
//			DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
			
//			CONS.TNActv.list_TNActv_Main = DBUtils.find_All_TI(actv, CONS.DB.tname_IFM11);

			// build: list
			CONS.HistUploadActv.list_HistUploadActv_Main = 
									DBUtils.find_All_HistUpload(actv);

			// validate
			if (CONS.HistUploadActv.list_HistUploadActv_Main == null) {
				
				String msg = "list_HistUploadActv_Main => null";
				Methods_dlg.dlg_ShowMessage(actv, msg);
				
				return;
				
			}

			////////////////////////////////

			// sort

			////////////////////////////////
			Methods.sort_List_HistUpload(
					CONS.HistUploadActv.list_HistUploadActv_Main, 
					CONS.Enums.SortType.CREATED_AT, 
//					CONS.Enums.SortType.FileName, 
					CONS.Enums.SortOrder.DESC);
			
		}//if (CONS.HistUploadActv.list_HistUploadActv_Main == null)

		ListView lv = ((ListActivity) actv).getListView();
		
		int new_Position = lv.getLastVisiblePosition();
		
		if((new_Position + lv.getChildCount()) 
				> CONS.HistUploadActv.list_HistUploadActv_Main.size()) {
//			> CONS.TNActv.list_TNActv_Main.size()) {
			
			new_Position = CONS.HistUploadActv.list_HistUploadActv_Main.size() - lv.getChildCount();
//			new_Position = CONS.TNActv.list_TNActv_Main.size() - lv.getChildCount();
			
		}
		
		lv.setSelection(new_Position);
		
	}//case_ACTV_HISTUPLOAD_IB_DOWN
	
	private void 
	case_ACTV_SHOWLOG_IB_DOWN() {
		// TODO Auto-generated method stub
		
		/******************************
			validate: list
		 ******************************/
		if (CONS.ShowLogActv.list_ShowLog_Files == null) {
			
			CONS.ShowLogActv.list_ShowLog_Files = 
							Methods.get_LogItem_List(actv);
			
		}
		
		ListView lv = ((ListActivity) actv).getListView();
		
		int new_Position = lv.getLastVisiblePosition();
		
		if((new_Position + lv.getChildCount()) 
				> CONS.ShowLogActv.list_ShowLog_Files.size()) {
			
			new_Position = 
					CONS.ShowLogActv.list_ShowLog_Files.size() - lv.getChildCount();
			
		}
		
		lv.setSelection(new_Position);
		
	}//case_ACTV_TN_IB_TOP
	
	private void 
	case_ACTV_LOG_IB_DOWN() {
		// TODO Auto-generated method stub
		
		/******************************
			validate: list
		 ******************************/
		if (CONS.LogActv.list_LogFiles == null) {
			
			CONS.LogActv.list_LogFiles = 
					Methods.get_LogFileNames_List(actv);
			
		}
		
		ListView lv = ((ListActivity) actv).getListView();
		
		int new_Position = lv.getLastVisiblePosition();
		
		if((new_Position + lv.getChildCount()) 
				> CONS.LogActv.list_LogFiles.size()) {
			
			new_Position = 
					CONS.LogActv.list_LogFiles.size() - lv.getChildCount();
			
		}
		
		lv.setSelection(new_Position);
		
	}//case_ACTV_TN_IB_TOP
	
	
	private void 
	case_ACTV_TN_IB_UP() {
		// TODO Auto-generated method stub
		
		/******************************
			validate: list
		 ******************************/
		if (CONS.TNActv.list_TNActv_Main == null) {
			
//			DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
			
			CONS.TNActv.list_TNActv_Main = DBUtils.find_All_TI(actv, CONS.DB.tname_IFM11);
			
		}
		
		ListView lv = ((ListActivity) actv).getListView();
		
		int lastPos = lv.getLastVisiblePosition();
		
		int childCount = lv.getChildCount();
		
		int new_Position;
		
		if (lastPos - (childCount * 2) + 2 > 0) {
			
			new_Position = lastPos - (childCount * 2) + 2;
			
		} else {
			
			new_Position = 0;

		}
		
		lv.setSelection(new_Position);		
		
	}//case_ACTV_TN_IB_TOP
	
	private void 
	case_ACTV_HISTUPLOAD_IB_UP() {
		// TODO Auto-generated method stub
		
		/******************************
			validate: list
		 ******************************/
		if (CONS.HistUploadActv.list_HistUploadActv_Main == null) {
//			if (CONS.TNActv.list_TNActv_Main == null) {
			
//			DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
			
//			CONS.TNActv.list_TNActv_Main = DBUtils.find_All_TI(actv, CONS.DB.tname_IFM11);

			// build: list
			CONS.HistUploadActv.list_HistUploadActv_Main = 
									DBUtils.find_All_HistUpload(actv);

			// validate
			if (CONS.HistUploadActv.list_HistUploadActv_Main == null) {
				
				String msg = "list_HistUploadActv_Main => null";
				Methods_dlg.dlg_ShowMessage(actv, msg);
				
				return;
				
			}

			////////////////////////////////

			// sort

			////////////////////////////////
			Methods.sort_List_HistUpload(
					CONS.HistUploadActv.list_HistUploadActv_Main, 
					CONS.Enums.SortType.CREATED_AT, 
//					CONS.Enums.SortType.FileName, 
					CONS.Enums.SortOrder.DESC);
			
		}//if (CONS.HistUploadActv.list_HistUploadActv_Main == null)
		
		ListView lv = ((ListActivity) actv).getListView();
		
		int lastPos = lv.getLastVisiblePosition();
		
		int childCount = lv.getChildCount();
		
		int new_Position;
		
		if (lastPos - (childCount * 2) + 2 > 0) {
			
			new_Position = lastPos - (childCount * 2) + 2;
			
		} else {
			
			new_Position = 0;
			
		}
		
		lv.setSelection(new_Position);		
		
	}//case_ACTV_HISTUPLOAD_IB_UP
	
	private void 
	case_ACTV_SHOWLOG_IB_UP() {
		// TODO Auto-generated method stub
		
//		/******************************
//			validate: list
//		 ******************************/
//		if (CONS.ShowLogActv.list_ShowLog_Files == null) {
//			
//			CONS.ShowLogActv.list_ShowLog_Files = 
//							Methods.get_LogItem_List(actv);
//			
//		}		
		
		ListView lv = ((ListActivity) actv).getListView();
		
		int lastPos = lv.getLastVisiblePosition();
		
		int childCount = lv.getChildCount();
		
		int new_Position;
		
		if (lastPos - (childCount * 2) + 2 > 0) {
			
			new_Position = lastPos - (childCount * 2) + 2;
			
		} else {
			
			new_Position = 0;
			
		}
		
		lv.setSelection(new_Position);		
		
	}//case_ACTV_TN_IB_TOP
	
	private void 
	case_ACTV_LOG_IB_UP() {
		// TODO Auto-generated method stub
		
//		/******************************
//			validate: list
//		 ******************************/
//		if (CONS.ShowLogActv.list_ShowLog_Files == null) {
//			
//			CONS.ShowLogActv.list_ShowLog_Files = 
//							Methods.get_LogItem_List(actv);
//			
//		}		
		
		ListView lv = ((ListActivity) actv).getListView();
		
		int lastPos = lv.getLastVisiblePosition();
		
		int childCount = lv.getChildCount();
		
		int new_Position;
		
		if (lastPos - (childCount * 2) + 2 > 0) {
			
			new_Position = lastPos - (childCount * 2) + 2;
			
		} else {
			
			new_Position = 0;
			
		}
		
		lv.setSelection(new_Position);		
		
	}//case_ACTV_TN_IB_TOP
	
	
	private void 
	case_ACTV_TN_IB_BACK() {
		// TODO Auto-generated method stub
		
		actv.finish();
		
		actv.overridePendingTransition(0, 0);
		
	}

	private void case_ACTV_MAIN_IB_UP() {
		// TODO Auto-generated method stub
		
		Methods.go_Up_Dir(actv);
		
//		String msg_Toa = "UP";
//		Toast.makeText(actv, msg_Toa, Toast.LENGTH_SHORT).show();
		
	}

	private void 
	case_TILIST_CB() {
		
		if (CONS.TNActv.checkedPositions.contains((int)position)) {
			// Log
			Log.d("BO_CL.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "position exists => " + position);
			
//			TNActv.checkedPositions.add(position);
//			TNActv.checkedPositions.remove(position);
			CONS.TNActv.checkedPositions.remove((Integer) position);
			
			// Log
			Log.d("BO_CL.java"
					+ "["
					+ Thread.currentThread().getStackTrace()[2]
							.getLineNumber() + "]", "position removed => " + position);
			
		} else {//if (TNActv.checkedPositions.contains((int)position))
			/*----------------------------
			 * 2. If not yet, enlist into it
				----------------------------*/
			
			CONS.TNActv.checkedPositions.add(position);
			
			// Log
			String temp = "new position added => " + String.valueOf(position) +
					"(size=" + CONS.TNActv.checkedPositions.size() + ")" + "[";
			
			StringBuilder sb = new StringBuilder();
			
			sb.append(temp);
			for (int i = 0; i < CONS.TNActv.checkedPositions.size(); i++) {
				
				sb.append(CONS.TNActv.checkedPositions.get(i) + ",");
				
			}//for (int i = 0; i < TNActv.checkedPositions.size(); i++)
			sb.append("]");
			
			
			Log.d("BO_CL.java"
					+ "["
					+ Thread.currentThread().getStackTrace()[2]
							.getLineNumber() + "]", sb.toString());
//							.getLineNumber() + "]", "new position added => " + String.valueOf(position) +
//							"(size=" + TNActv.checkedPositions.size() + ")" + "[" +);
			
			
		}//if (TNActv.checkedPositions.contains((int)position))
		
		////////////////////////////////

		// notify

		////////////////////////////////
		CONS.TNActv.adp_TNActv_Main_Move.notifyDataSetChanged();
		
	}//case_TILIST_CB

}//public class ButtonOnClickListener implements OnClickListener
