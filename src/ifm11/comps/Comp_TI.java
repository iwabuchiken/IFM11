package ifm11.comps;

import ifm11.items.TI;
import ifm11.utils.CONS.Enums.SortOrder;
import ifm11.utils.CONS.Enums.SortType;
import ifm11.utils.Methods;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import android.util.Log;

public class Comp_TI implements Comparator<TI> {

	List<TI> ti_List;
	SortType sortType;
	SortOrder sortOrder;
	
	
	public Comp_TI
	(List<TI>
		ti_List, SortType sortType,
		SortOrder sortOrder) {
		
		this.ti_List	= ti_List;
		
		this.sortType	= sortType;
		
		this.sortOrder	= sortOrder;
		
		// Log
		String msg_Log = "Comp_TI => created";
		Log.d("Comp_TI.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
	}

	@Override
	public int compare(TI a1, TI a2) {
		// TODO Auto-generated method stub
		int res;
		
		switch(sortType) {
		
		case FileName:
			
			res = _compare_FileName(a1, a2);
			
			break;
			
		case CREATED_AT:
			
			res = _compare_CREATED_AT(a1, a2);
			
			break;
			
		default:
			
			res = 1;
		
		}
		
		return res;
		
	}//public int compare(AI a1, AI a2)

	private int _compare_FileName(TI t1, TI t2) {
		// TODO Auto-generated method stub
		
		int res;
		
		switch (sortOrder) {
		
		case ASC:
			
//				res = (int) (a1.getCreated_at() - a2.getCreated_at());
			try {
				
				res = t1.getFile_name().compareTo(t2.getFile_name());
				
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				
				// Log
				String msg_Log;
				
				msg_Log = String.format(
						Locale.JAPAN,
						"Exception => %s", e1.getMessage()
						);
				
				Log.e("Comp_TI.java" + "["
						+ Thread.currentThread().getStackTrace()[2].getLineNumber()
						+ "]", msg_Log);
				
				e1.printStackTrace();
				
				res = 1;
				
			}
			
			break;
			
		case DESC:
			
//				res = (int) -(a1.getCreated_at() - a2.getCreated_at());
			try {
				
				res = t2.getFile_name().compareTo(t1.getFile_name());
				
			} catch (Exception e) {
			
				res = 1;

				// Log
				String msg_Log;
				
				msg_Log = String.format(
						Locale.JAPAN,
						"Exception => %s", e.getMessage()
						);
				
				Log.e("Comp_TI.java" + "["
						+ Thread.currentThread().getStackTrace()[2].getLineNumber()
						+ "]", msg_Log);

				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
			
			break;
			
		default:
			
			res = 1;
			
			break;
		}
		
		return res;

	}//private int _compare_FileName(AI a1, AI a2)
	
	private int _compare_CREATED_AT(TI t1, TI t2) {
		// TODO Auto-generated method stub
		
		int res;
		
		long time1 = Methods.conv_TimeLabel_to_MillSec(t1.getCreated_at());
		long time2 = Methods.conv_TimeLabel_to_MillSec(t2.getCreated_at());

//		// Log
//		String msg_Log = String.format(
//					Locale.JAPAN,
//					"t1 = %s | t2 = %s", 
//					t1.getCreated_at(),
//					t2.getCreated_at());
//		
//		Log.d("Comp_TI.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_Log);
//		
//		msg_Log = String.format(
//				Locale.JAPAN,
//				"time1 = %d | time2 = %d", 
//				time1,
//				time2);
//		
//		Log.d("Comp_TI.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_Log);
		
		switch (sortOrder) {
		
		case ASC:
			
			res = (time1 < time2) ? 1 : 0;

//			// Log
//			msg_Log = "ASC: res => " + res;
//			Log.d("Comp_TI.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", msg_Log);

			break;
			
		case DESC:

			if (time1 > time2) {
				
				res = 1;
				
			} else if (time1 == time2) {

				res = 0;
				
			} else {
				
				res = -1;
			}
			
//			res = (time1 > time2) ? 1 : 0;
		
//			// Log
//			msg_Log = "DESC: res => " + res;
//			Log.d("Comp_TI.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", msg_Log);
			
			break;
			
		default:
			
			res = 1;
			
			break;
		}
		
		return res;
		
	}//private int _compare_FileName(AI a1, AI a2)

}//public class AIComparator implements Comparator<AI>
