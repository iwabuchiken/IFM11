package ifm11.listeners.button;

import ifm11.utils.CONS;
import ifm11.utils.DBUtils;
import ifm11.utils.Methods;
import ifm11.utils.Tags;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
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
			
			case_ACTV_TN_IB_BACK();
			
			break;
			
		case ACTV_TN_IB_TOP://-----------------------------------------------------------------------------
			
			case_ACTV_TN_IB_TOP();
			
			break;
			
		case ACTV_SHOWLOG_IB_TOP://-----------------------------------------------------------------------------
			
			case_ACTV_SHOWLOG_IB_TOP();
			
			break;
			
		case ACTV_TN_IB_BOTTOM://-----------------------------------------------------------------------------
			
			case_ACTV_TN_IB_BOTTOM();
			
			break;
			
		case ACTV_SHOWLOG_IB_BOTTOM://-----------------------------------------------------------------------------
			
			case_ACTV_SHOWLOG_IB_BOTTOM();
			
			break;
			
		case ACTV_TN_IB_DOWN://-----------------------------------------------------------------------------
			
			case_ACTV_TN_IB_DOWN();
			
			break;
			
		case ACTV_SHOWLOG_IB_DOWN://-----------------------------------------------------------------------------
			
			case_ACTV_SHOWLOG_IB_DOWN();
			
			break;
			
		case ACTV_TN_IB_UP://-----------------------------------------------------------------------------
			
			case_ACTV_TN_IB_UP();
			
			break;
			
		case ACTV_SHOWLOG_IB_UP://-----------------------------------------------------------------------------
			
			case_ACTV_SHOWLOG_IB_UP();
			
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

//		case image_activity_prev://----------------------------------------------------
//			
//			image_activity_prev();
//			
//			
//			break;// case image_activity_prev
			
//		case image_activity_next://----------------------------------------------------
//
//			image_activity_next();
//			
//			break;// case image_activity_next
			
		default:
			break;
		}//switch (tag)
		
	}//public void onClick(View v)

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
