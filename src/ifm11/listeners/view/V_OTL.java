package ifm11.listeners.view;

import ifm11.utils.Tags;
import ifm11.views.CV;

import java.util.Locale;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;

public class V_OTL implements OnTouchListener {

	/*----------------------------
	 * Fields
		----------------------------*/
	//
	Activity actv;
	Dialog d1;
	private Dialog d2;
	private Dialog d3;
	private Dialog d4;
	private CV cv;
	
	public V_OTL(Activity actv) {
		//
		this.actv = actv;
	}

	public V_OTL(Activity actv, CV cv) {
		// TODO Auto-generated constructor stub
		
		this.actv	= actv;
		this.cv		= cv;
		
	}

	//	@Override
	public boolean onTouchEvent(MotionEvent event) {
//		public boolean onTouch(View v, MotionEvent event) {
		
//		Tags.ViewTags tag_name = (Tags.ViewTags) v.getTag();
		
		ImageButton ib;
		
		float x;
		float y;
		
		switch (event.getActionMasked()) {
		
			case MotionEvent.ACTION_DOWN://--------------------
				
				x = event.getX();
				y = event.getY();
				
				// Log
				String msg_Log = String.format(
							Locale.JAPAN,
							"[DOWN] x = %f / y = %f", x, y);
				
				Log.d("V_OTL.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", msg_Log);
				
				break;//case MotionEvent.ACTION_DOWN:
			
			case MotionEvent.ACTION_UP://--------------------
				
				x = event.getX();
				y = event.getY();
				
				// Log
				msg_Log = String.format(
							Locale.JAPAN,
							"[UP] x = %f / y = %f", x, y);
				
				Log.d("V_OTL.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", msg_Log);
				
				break;//case MotionEvent.ACTION_UP:
		
			case MotionEvent.ACTION_MOVE://--------------------
				
				x = event.getX();
				y = event.getY();
				
				// Log
				msg_Log = String.format(
							Locale.JAPAN,
							"[MOVE] x = %f / y = %f", x, y);
				
				Log.d("V_OTL.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", msg_Log);
				
				
		}//switch (event.getActionMasked())
		
		return false;
		
	}//public boolean onTouch(View v, MotionEvent event)

	//	@Override
//	public boolean onTouchEvent(MotionEvent event) {
	public boolean 
	onTouch(View v, MotionEvent event) {
		
		Tags.ViewTags tag_name = (Tags.ViewTags) v.getTag();
		
		ImageButton ib;
		
		float x;
		float y;
		
		switch (event.getActionMasked()) {
		
		case MotionEvent.ACTION_DOWN://--------------------

			break;//case MotionEvent.ACTION_DOWN:
			
		case MotionEvent.ACTION_UP://--------------------
			
			break;//case MotionEvent.ACTION_UP:
			
		case MotionEvent.ACTION_MOVE://--------------------
			
			break;//case MotionEvent.ACTION_MOVE:
			
		}//switch (event.getActionMasked())
		
//		return true;
		return false;	//=> if false, MOVE_DOWN only; no further cases logged out
		
	}//public boolean onTouch(View v, MotionEvent event)
	
}//public class DB_OTL implements OnTouchListener
