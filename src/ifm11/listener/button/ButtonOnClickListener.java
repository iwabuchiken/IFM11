package ifm11.listener.button;

import ifm11.utils.CONS;
import ifm11.utils.Methods;
import ifm11.utils.Tags;

import java.io.File;

import android.app.Activity;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

public class ButtonOnClickListener implements OnClickListener {
	/*----------------------------
	 * Fields
		----------------------------*/
	//
	Activity actv;

	//
	Vibrator vib;
	
	//
	int position;
	
	//
	ListView lv;
	
	public ButtonOnClickListener(Activity actv) {
		//
		this.actv = actv;
		
		//
		vib = (Vibrator) actv.getSystemService(actv.VIBRATOR_SERVICE);
	}

	public ButtonOnClickListener(Activity actv, int position) {
		//
		this.actv = actv;
		this.position = position;
		
		//
		vib = (Vibrator) actv.getSystemService(actv.VIBRATOR_SERVICE);
		
		
		
	}//public ButtonOnClickListener(Activity actv, int position)

	public ButtonOnClickListener(Activity actv, ListView lv) {
		// 
		this.actv = actv;
		this.lv = lv;
		
		vib = (Vibrator) actv.getSystemService(actv.VIBRATOR_SERVICE);
	}

//	@Override
	public void onClick(View v) {
//		//
		Tags.ButtonTags tag = (Tags.ButtonTags) v.getTag();
//
		vib.vibrate(CONS.Admin.vibLength_click);
		
		//
		switch (tag) {
		case ib_up://---------------------------------------------------------
			
//			Methods.upDir(actv);
			
			break;

//		case tilist_cb://------------------------------------------------------------------------------
//			/*----------------------------
//			 * Steps
//			 * 1. If already checked, unlist from ThumbnailActivity.checkedPositions
//			 * 2. If not yet, enlist into it
//			 * 3. Then, notify to adapter
//				----------------------------*/
//			/*----------------------------
//			 * 1. If already checked, unlist from ThumbnailActivity.checkedPositions
//				----------------------------*/
//			case_tilist_cb();
//			
//			/*----------------------------
//			 * 3. Then, notify to adapter
//				----------------------------*/
//			TNActv.aAdapter.notifyDataSetChanged();
//			TNActv.bAdapter.notifyDataSetChanged();
//			
//			break;

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

}//public class ButtonOnClickListener implements OnClickListener
