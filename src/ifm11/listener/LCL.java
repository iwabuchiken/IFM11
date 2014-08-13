package ifm11.listener;

import ifm11.utils.CONS;
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
		
		return false;
	}

}
