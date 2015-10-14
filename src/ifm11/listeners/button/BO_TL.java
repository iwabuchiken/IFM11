package ifm11.listeners.button;

import ifm11.main.R;
import ifm11.utils.CONS;
import ifm11.utils.Tags;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageButton;

public class BO_TL implements OnTouchListener {

	/*----------------------------
	 * Fields
		----------------------------*/
	//
	Activity actv;

	//
	Vibrator vib;

	Button bt = null;
	
	public BO_TL(Activity actv) {
		//
		this.actv = actv;
		
		vib = (Vibrator) actv.getSystemService(Context.VIBRATOR_SERVICE);
	}

//	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		Tags.ButtonTags tag = (Tags.ButtonTags) v.getTag();
		
//		vib.vibrate(CONS.Admin.vibLength_click);
		
		switch (event.getActionMasked()) {
		case MotionEvent.ACTION_DOWN:
			
			switch (tag) {
			
			case ib_up://----------------------------------------------------
				
				ImageButton ib = (ImageButton) v;
				ib.setImageResource(R.drawable.ifm8_up_disenabled);
				
//				v.setBackgroundColor(Color.GRAY);
				
				break;// case ib_up
				
			case ACTV_TN_IB_BOTTOM://----------------------------------------------------
			case ACTV_SHOWLOG_IB_BOTTOM://----------------------------------------------------
			case ACTV_LOG_IB_BOTTOM://----------------------------------------------------
			case ACTV_HISTUPLOAD_IB_BOTTOM://----------------------------------------------------
				
				ib = (ImageButton) v;
				ib.setImageResource(R.drawable.ifm8_thumb_bottom_50x50_disenabled);
						
				break;// case thumb_activity_ib_bottom

			case image_activity_prev://----------------------------------------------------
				
				ib = (ImageButton) v;
				ib.setImageResource(R.drawable.ifm8_back_disenabled);
				
				break;// case image_activity_prev

			case image_activity_next://----------------------------------------------------
				
				ib = (ImageButton) v;
				ib.setImageResource(R.drawable.ifm8_forward_disenabled);
				
				break;// case image_activity_next
				
			case ACTV_TN_IB_BACK://----------------------------------------------------
			case ACTV_SHOWLOG_IB_BACK:
			case ACTV_HISTUPLOAD_IB_BACK:
				
				ib = (ImageButton) v;
				ib.setImageResource(R.drawable.ifm8_thumb_back_50x50_disenabled);
				
				break;// case image_activity_next
				
			case ACTV_TN_IB_TOP://----------------------------------------------------
			case ACTV_SHOWLOG_IB_TOP://----------------------------------------------------
			case ACTV_LOG_IB_TOP://----------------------------------------------------
			case ACTV_HISTUPLOAD_IB_TOP://----------------------------------------------------
				
				ib = (ImageButton) v;
				ib.setImageResource(R.drawable.ifm8_thumb_top_50x50_disenabled);
				
				break;// case image_activity_next
				
			case ACTV_TN_IB_DOWN://----------------------------------------------------
			case ACTV_SHOWLOG_IB_DOWN://----------------------------------------------------
			case ACTV_LOG_IB_DOWN://----------------------------------------------------
			case ACTV_HISTUPLOAD_IB_DOWN://----------------------------------------------------
				
				ib = (ImageButton) v;
				ib.setImageResource(R.drawable.ifm8_thumb_down_50x50_disenabled);
				
				break;// case image_activity_next
				
			case ACTV_TN_IB_UP://----------------------------------------------------
			case ACTV_SHOWLOG_IB_UP://----------------------------------------------------
			case ACTV_LOG_IB_UP://----------------------------------------------------
			case ACTV_HISTUPLOAD_IB_UP://----------------------------------------------------
				
				ib = (ImageButton) v;
				ib.setImageResource(R.drawable.ifm8_thumb_up_50x50_disenabled);
				
				break;// case image_activity_next

			case image_activity_back://----------------------------------------------------
				
				ib = (ImageButton) v;
				ib.setImageResource(R.drawable.ifm8_image_actv_back_70x70_touched);
				
				break;// case image_activity_next
				
			case GENERIC_ACTV_BT_CANCEL://----------------------------------------------------
			case ACTV_SEARCH_BT_OK://----------------------------------------------------
				
				bt = (Button) v;
				bt.setBackgroundColor(Color.GRAY);
				
				break;// case image_activity_next
				
			}//switch (tag)
			
			break;//case MotionEvent.ACTION_DOWN:
			
			
		case MotionEvent.ACTION_UP:
			switch (tag) {
			case ib_up://----------------------------------------------------
				
				ImageButton ib = (ImageButton) v;
				ib.setImageResource(R.drawable.ifm8_up);
				
				break;// case ib_up
				
			case ACTV_TN_IB_BOTTOM://----------------------------------------------------
			case ACTV_SHOWLOG_IB_BOTTOM:
			case ACTV_LOG_IB_BOTTOM:
			case ACTV_HISTUPLOAD_IB_BOTTOM:
				
				ib = (ImageButton) v;
				ib.setImageResource(R.drawable.actv_tn_bt_bottom_45x45);

				break;// case thumb_activity_ib_bottom
				
			case ACTV_TN_IB_TOP://----------------------------------------------------
			case ACTV_SHOWLOG_IB_TOP:
			case ACTV_LOG_IB_TOP:
			case ACTV_HISTUPLOAD_IB_TOP:
				
				ib = (ImageButton) v;
				ib.setImageResource(R.drawable.actv_tn_bt_top_45x45);
				
				break;// case thumb_activity_ib_top

			case image_activity_prev://----------------------------------------------------
				
				ib = (ImageButton) v;
				ib.setImageResource(R.drawable.ifm8_back);
				
				break;// case image_activity_prev

			case image_activity_next://----------------------------------------------------
				
				ib = (ImageButton) v;
				ib.setImageResource(R.drawable.ifm8_forward);
				
				break;// case image_activity_next
				
			case ACTV_TN_IB_BACK://----------------------------------------------------
			case ACTV_SHOWLOG_IB_BACK:
			case ACTV_HISTUPLOAD_IB_BACK:
				ib = (ImageButton) v;
				ib.setImageResource(R.drawable.ifm8_thumb_back_50x50);
				
				break;// case image_activity_next

			case ACTV_TN_IB_DOWN://----------------------------------------------------
			case ACTV_SHOWLOG_IB_DOWN://----------------------------------------------------
			case ACTV_LOG_IB_DOWN://----------------------------------------------------
			case ACTV_HISTUPLOAD_IB_DOWN://----------------------------------------------------
				
				ib = (ImageButton) v;
				ib.setImageResource(R.drawable.ifm8_thumb_down_50x50);
				
				break;// case image_activity_next

			case ACTV_TN_IB_UP://----------------------------------------------------
			case ACTV_SHOWLOG_IB_UP://----------------------------------------------------
			case ACTV_LOG_IB_UP://----------------------------------------------------
			case ACTV_HISTUPLOAD_IB_UP://----------------------------------------------------
				
				ib = (ImageButton) v;
				ib.setImageResource(R.drawable.ifm8_thumb_up_50x50);
				
				break;// case image_activity_next

			case image_activity_back://----------------------------------------------------
				
				ib = (ImageButton) v;
				ib.setImageResource(R.drawable.ifm8_image_actv_back_70x70);
				
				break;// case image_activity_next
				
			case GENERIC_ACTV_BT_CANCEL://----------------------------------------------------
			case ACTV_SEARCH_BT_OK://----------------------------------------------------
				
				bt = (Button) v;
				bt.setBackgroundColor(Color.WHITE);
				
				break;// case image_activity_next

			}//switch (tag)
			
			break;//case MotionEvent.ACTION_UP:
		}//switch (event.getActionMasked())
		return false;
	}

}
