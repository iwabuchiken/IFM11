package ifm11.listeners;

import ifm11.utils.CONS;
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
public class
LOI_LCL implements OnItemLongClickListener {

	Activity actv;

	Dialog dlg1;
	Dialog dlg2; 
	
	Vibrator vib;
	
	public LOI_LCL(Activity actv) {
		// TODO Auto-generated constructor stub
		this.actv = actv;
		
		CONS.Admin.vib = (Vibrator) actv.getSystemService(actv.VIBRATOR_SERVICE);
		
	}

	public 
	LOI_LCL
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
		
		Tags.ListTags tag = null;
		
			
		tag = (Tags.ListTags) parent.getTag();
		
		CONS.Admin.vib.vibrate(CONS.Admin.vibLength_click);
//		vib.vibrate(CONS.Admin.vibLength_click);
		

		
		switch (tag) {
			
		case actv_main_lv://----------------------------------------------------

			String item = (String) parent.getItemAtPosition(position);
			
			case_ACTV_MAIN_LV(item);
			
			break;// case actv_bm_lv
			
		default:
			break;
		
		}//switch (tag)
		
		return true;
		
	}//onItemLongClick (AdapterView<?> parent, View v, int position, long id)

	private void 
	case_ACTV_MAIN_LV(String item) {
		// TODO Auto-generated method stub
		
		Methods_dlg.dlg_ACTV_MAIN_LV(actv, item);
		
	}

}//public class ListOnItemLongClickListener implements OnItemLongClickListener
