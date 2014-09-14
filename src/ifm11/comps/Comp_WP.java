package ifm11.comps;


import ifm11.items.WordPattern;
import ifm11.utils.CONS.Enums.SortOrder;
import ifm11.utils.CONS.Enums.SortType;
import ifm11.utils.Methods;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import android.util.Log;

public class Comp_WP implements Comparator<WordPattern> {

	List<WordPattern> ti_List;
	SortType sortType;
	SortOrder sortOrder;
	
	
	public Comp_WP
	(List<WordPattern>
		ti_List, SortType sortType,
		SortOrder sortOrder) {
		
		this.ti_List	= ti_List;
		
		this.sortType	= sortType;
		
		this.sortOrder	= sortOrder;
		
	}

	public Comp_WP
	(SortType sortType, SortOrder sortOrder) {
		// TODO Auto-generated constructor stub
		this.sortType	= sortType;
		
		this.sortOrder	= sortOrder;
		
		// Log
		String msg_Log = "Comp_WP => created (sortType = " + this.sortType.toString();
		Log.d("Comp_WP.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
	}

	@Override
	public int compare(WordPattern a1, WordPattern a2) {
		// TODO Auto-generated method stub
		int res;
		
		switch(sortType) {
		
//		case FileName:
		case WORD:
			
			res = _compare_WORD(a1, a2);
			
			break;
			
		case CREATED_AT:
			
			res = _compare_CREATED_AT(a1, a2);
			
			break;
			
		case USED:
			
			res = _compare_USED(a1, a2);
			
			break;
			
		default:
			
			res = 1;
		
		}
		
		return res;
		
	}//public int compare(AI a1, AI a2)

	private int 
	_compare_WORD
	(WordPattern t1, WordPattern t2) {
		// TODO Auto-generated method stub
		
		int res;
		
		int comp = t1.getWord().compareTo(t2.getWord());
		
		switch (sortOrder) {
		
		case ASC:
			
			res = comp;
//			res = -(comp);
//			res = -(t1.getWord().compareTo(t2.getWord()));
			
//				res = (int) (a1.getCreated_at() - a2.getCreated_at());
//			res = t1.getWord().compareTo(t2.getWord());
			
//			// Log
//			String msg_Log = String.format("wp1 %d | wp2 %d", t1.getUsed(), t2.getUsed());
//			Log.d("Comp_WP.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", msg_Log);
//			
//			boolean res_Used = t1.getUsed() > t2.getUsed();
//			
//			// Log
//			msg_Log = "res_Used => " + res_Used;
//			Log.d("Comp_WP.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", msg_Log);
//			
//			if (res_Used == true) {
//				
//				res = -1;
//				
//			} else {
//				
////				res = t1.getWord().compareTo(t2.getWord());
//				res = 1;
//
//			}
			
			break;
			
		case DESC:
			
//				res = (int) -(a1.getCreated_at() - a2.getCreated_at());
//			res = t2.getWord().compareTo(t1.getWord());
			
			res = -(comp);
//			res = comp;
//			res = -(t1.getWord().compareTo(t2.getWord()));
			
//			res_Used = t2.getUsed() > t1.getUsed();
//			
//			if (res_Used == true) {
//				
//				res = 1;
//				
//			} else {
//				
//				res = t2.getWord().compareTo(t1.getWord());
//
//			}
			
			break;
			
		default:
			
			res = 1;
			
			break;
		}
		
		return res;

	}//private int _compare_FileName(AI a1, AI a2)
	
	private int 
	_compare_USED
	(WordPattern wp1, WordPattern wp2) {
		// TODO Auto-generated method stub
		
		int res;
		
		boolean res_Used = wp1.getUsed() >= wp2.getUsed();
//		boolean res_Used = t1.getUsed() > t2.getUsed();

		// Log
		String msg_Log = String.format(
					Locale.JAPAN,
					"wp1 %d, %s | wp2 %d, %s (comp = %s)", 
					wp1.getUsed(), 
					wp1.getWord(),
					wp2.getUsed(),
					wp2.getWord(),
					res_Used);
		
//		Log.d("Comp_WP.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_Log);

		switch (sortOrder) {
		
		case ASC:
			
//			boolean res_Used = t1.getUsed() > t2.getUsed();

//			res = (res_Used == true) ? 1 : -1;
			
			if (wp1.getUsed() > wp2.getUsed()) {
				
				res = 1;
				
			} else if (wp1.getUsed() == wp2.getUsed()) {
				
				res = 0;
				
			} else if (wp1.getUsed() < wp2.getUsed()) {
				
				res = -1;
				
			} else {
				
				res = 0;

			}
			
//			if (res_Used == true) {
//				
//				res = -1;
//				
//			} else {
//				
////				res = t1.getWord().compareTo(t2.getWord());
//				res = 1;
//				
//			}
			
			break;
			
		case DESC:
			
//			res = (res_Used == true) ? -1 : 1;
			
			if (wp1.getUsed() > wp2.getUsed()) {
				
				res = -1;
				
			} else if (wp1.getUsed() == wp2.getUsed()) {
				
				res = 0;
				
			} else if (wp1.getUsed() < wp2.getUsed()) {
				
				res = 1;
				
			} else {
				
				res = 0;

			}

			
//				res = (int) -(a1.getCreated_at() - a2.getCreated_at());
//			res = t2.getWord().compareTo(t1.getWord());
			
//			res_Used = t2.getUsed() > t1.getUsed();
//			
//			if (res_Used == true) {
//				
//				res = 1;
//				
//			} else {
//				
//				res = t2.getWord().compareTo(t1.getWord());
//				
//			}
			
			break;
			
		default:
			
			res = 1;
			
			break;
		}
		
		return res;
		
	}//_compare_USED
	
	
	private int _compare_CREATED_AT(WordPattern t1, WordPattern t2) {
		// TODO Auto-generated method stub
		
		int res;
		
		long time1 = Methods.conv_TimeLabel_to_MillSec(t1.getCreated_at());
		long time2 = Methods.conv_TimeLabel_to_MillSec(t2.getCreated_at());
		
		switch (sortOrder) {
		
		case ASC:
			
			res = (time1 < time2) ? 1 : 0;
			
			break;
			
		case DESC:

			res = (time1 > time2) ? 1 : 0;
			
			break;
			
		default:
			
			res = 1;
			
			break;
		}
		
		return res;
		
	}//private int _compare_FileName(AI a1, AI a2)

}//public class AIComparator implements Comparator<AI>
