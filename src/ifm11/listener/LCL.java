package ifm11.listener;

import ifm11.utils.CONS;
import ifm11.utils.Tags;
import android.app.Activity;
import android.content.Context;
import android.os.Vibrator;
import android.view.View;
import android.view.View.OnLongClickListener;

public class LCL implements OnLongClickListener {

	Activity actv;
	int position;
	
	public LCL(Activity actv, int position) {
		// TODO Auto-generated constructor stub
		
		this.actv		= actv;
		this.position	= position;
		
		CONS.Admin.vib	= (Vibrator) actv.getSystemService(Context.VIBRATOR_SERVICE);
		
	}

	@Override
	public boolean onLongClick(View v) {
		
		CONS.Admin.vib.vibrate(CONS.Admin.vibLength_click);
		
		Tags.ButtonTags tag = (Tags.ButtonTags) v.getTag();
		
		switch (tag) {
		
		case TILIST_CB:
			
			case_TILIST_CB();	
			
			break;
			
		default:
			break;
			
		}
//		TILIST_CB
		
		return false;
	}

	private void 
	case_TILIST_CB() {
		// TODO Auto-generated method stub
		
		if (CONS.TNActv.checkedPositions.contains((Integer) position)) {

			CONS.TNActv.checkedPositions.clear();
			
//			TNActv.aAdapter.notifyDataSetChanged();
			CONS.TNActv.adp_TNActv_Main_Move.notifyDataSetChanged();
			
		/*----------------------------
		 * 2. If not
			----------------------------*/
		} else {//if (CONS.TNActv.checkedPositions.contains((Integer) position))
			
			CONS.TNActv.checkedPositions.clear();
			
			for (int i = 0; i < CONS.TNActv.list_TNActv_Main.size(); i++) {
				
				CONS.TNActv.checkedPositions.add((Integer) i);
				
			}//for (int i = 0; i < TNActv.tiList.size(); i++)
			
//			TNActv.aAdapter.notifyDataSetChanged();
			CONS.TNActv.adp_TNActv_Main_Move.notifyDataSetChanged();
			
		}//if (CONS.TNActv.checkedPositions.contains((Integer) position))	
	}

}
