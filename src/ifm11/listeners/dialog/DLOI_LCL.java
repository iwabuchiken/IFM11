package ifm11.listeners.dialog;

import ifm11.utils.CONS;
import ifm11.utils.Methods;
import ifm11.utils.Methods_dlg;
import ifm11.utils.Tags;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.os.Vibrator;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Toast;

// ListOnItemLongClickListener
// Dialog List On Item Long Click Listener
public class
DLOI_LCL implements OnItemLongClickListener {

	Activity actv;

	Dialog dlg1;
	Dialog dlg2; 
	
	Vibrator vib;
	
	public DLOI_LCL(Activity actv) {
		// TODO Auto-generated constructor stub
		this.actv = actv;
		
		CONS.Admin.vib = (Vibrator) actv.getSystemService(actv.VIBRATOR_SERVICE);
		
	}

	public 
	DLOI_LCL
	(Activity actv, Dialog dlg1, Dialog dlg2) {
		// TODO Auto-generated constructor stub
		this.actv	= actv;
		this.dlg1	= dlg1;
		this.dlg2	= dlg2;
		
		CONS.Admin.vib = (Vibrator) actv.getSystemService(actv.VIBRATOR_SERVICE);

	}

	public boolean
	onItemLongClick
	(AdapterView<?> parent, View v, int position, long id) {
		
		Tags.DialogItemTags tag = null;
		
			
		tag = (Tags.DialogItemTags) parent.getTag();
		
		CONS.Admin.vib.vibrate(CONS.Admin.vibLength_click);
		
		switch (tag) {
			
		case DLG_MOVE_FILES_FOLDER://----------------------------------------------------

			String item = (String) parent.getItemAtPosition(position);
			
			case_DLG_MOVE_FILES_FOLDER(item);
			
			break;// case actv_bm_lv
			
		default:
			break;
		
		}//switch (tag)
		
		return true;
		
	}//onItemLongClick (AdapterView<?> parent, View v, int position, long id)

	private void 
	case_DLG_MOVE_FILES_FOLDER
	(String item) {
		// TODO Auto-generated method stub
		
		// Log
		String msg_Log = "item => " + item;
		Log.d("DLOI_LCL.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		////////////////////////////////

		// get: current path-move

		////////////////////////////////
		String curPath_Move = Methods.get_Pref_String(
						actv, 
						CONS.Pref.pname_MainActv, 
						CONS.Pref.pkey_TNActv__CurPath_Move, 
						CONS.DB.tname_IFM11);

		////////////////////////////////

		// case: upper dir

		////////////////////////////////
		if (item.equals(CONS.Admin.dirString_UpperDir)) {
			
			// top dir("cm7") ?
			if (curPath_Move.equals(CONS.DB.tname_IFM11)) {
//				if (curPath_Move.equals(CONS.DB.tname_CM7)) {
				
				Methods_dlg.conf_MoveFiles__Folder_Top(actv, dlg1, dlg2);
				
//				String msg = "Move to cm7?";
//				Methods_dlg.dlg_ShowMessage(actv, msg);
				
				return;
				
			} else {
				// not the top dir
//				String newPath_Move = 
//						Methods.conv_CurrentPathMove_to_CurrentPathMove_New(curPath_Move);
//				
//				// Log
//				msg_Log = "newPath_Move => " + newPath_Move;
//				Log.d("LOI_LCL.java"
//						+ "["
//						+ Thread.currentThread().getStackTrace()[2]
//								.getLineNumber() + "]", msg_Log);
				
//				String msg_Toa = "Upper dir";
//				Toast.makeText(actv, msg_Toa, Toast.LENGTH_SHORT).show();
				
				Methods.go_Up_Dir_Move(actv);
//				Ops.go_Up_Dir_Move(actv);
				
				return;
				
			}
			
		}//if (item.equals(CONS.Admin.dirString_UpperDir))
		
		////////////////////////////////

		// lower dir

		////////////////////////////////
		// Log
		msg_Log = "lower dir => " + item;
		Log.d("DLOI_LCL.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		Methods.go_Down_Dir_Move(actv, item);
		
		return;
		
//		////////////////////////////////
//
//		// set: pref: pkey_ALActv__CurPath_Move
//
//		////////////////////////////////
//		boolean res = Methods.set_Pref_String(
//				actv, 
//				CONS.Pref.pname_ALActv, 
//				CONS.Pref.pkey_ALActv__CurPath_Move, 
//				item);
//		
//		if (res == true) {
//			
//			// Log
//			msg_Log = "pref set => " + item;
//			Log.d("LOI_LCL.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", msg_Log);
//			
//		}
//
//		////////////////////////////////
//
//		// update: list
//
//		////////////////////////////////
//		Methods.update_MoveFilesList(actv, dlg1, dlg2, ai, aiList_Position, item);

		
	}//case_DLG_MOVE_FILES_FOLDER

}//public class ListOnItemLongClickListener implements OnItemLongClickListener
