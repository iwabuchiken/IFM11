package ifm11.listeners.view;

import ifm11.utils.CONS;
import ifm11.views.CV;

import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class V_OCL implements OnClickListener {

	Activity actv;
	
	CV cv;
	
	public V_OCL(Activity actv, CV cv) {
		// TODO Auto-generated constructor stub
		
		this.actv	= actv;
		this.cv		= cv;
		
		CONS.Admin.vib = (Vibrator) actv.getSystemService(Context.VIBRATOR_SERVICE);
		
	}

	@Override
	public void 
	onClick(View v) {
		// TODO Auto-generated method stub
		
		String msg_Log;
		
		// Log
		msg_Log = "clicked!";
		Log.d("V_OCL.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);

		
	}//onClick
	
}
