package ifm11.comps;

import ifm11.items.SearchHistory;
import ifm11.utils.CONS.Enums.SortOrder;
import ifm11.utils.CONS.Enums.SortType;
import ifm11.utils.Methods;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import android.util.Log;

public class Comp_SearchHistory implements Comparator<SearchHistory> {

	List<SearchHistory> list_SearchHistories;
	SortType sortType;
	SortOrder sortOrder;
	
	String msg_Log;
	
	public Comp_SearchHistory
	(List<SearchHistory>
		list_SearchHistories, SortType sortType,
		SortOrder sortOrder) {
		
		this.list_SearchHistories	= list_SearchHistories;
		
		this.sortType	= sortType;
		
		this.sortOrder	= sortOrder;
		
		// Log
		String msg_Log = "Comp_SearchHistory => created";
		Log.d("Comp_SearchHistory.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
	}

	@Override
	public int compare(SearchHistory a1, SearchHistory a2) {
		// TODO Auto-generated method stub
		int res;
		
		switch(sortType) {
		
//		case FileName:
//			
//			res = _compare_FileName(a1, a2);
//			
//			break;
			
		case CREATED_AT:
			
			res = this._compare_CREATED_AT__tmp(a1, a2);
//			res = _compare_CREATED_AT(a1, a2);
			
			break;
			
		default:
			
			res = 1;
		
		}
		
		return res;
		
	}//public int compare(AI a1, AI a2)

	private int 
	_compare_CREATED_AT__tmp
	(SearchHistory t1, SearchHistory t2) {
		// TODO Auto-generated method stub
		
		int res;
		
		String time1 = t1.getCreated_at();
		String time2 = t2.getCreated_at();

		switch (sortOrder) {
		
		case ASC:
			
			res = (time1.compareToIgnoreCase(time2) < 0) ? 1 : 0;
//			res = (time1 < time2) ? 1 : 0;

			break;
			
		case DESC:

//			// Log
////			String msg_Log;
//			
////			msg_Log = String.format(
////					Locale.JAPAN,
////					"comparing... => t1 = %s, t2 = %s", t1.getCreated_at(), t2.getCreated_at()
////					);
////			
////			Log.i("Comp_SearchHistory.java" + "["
////					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
////					+ "]", msg_Log);
//			
//			if (time1.compareToIgnoreCase(time2) > 0) {
////				if (time1 > time2) {
//
//				msg_Log = String.format(
//							Locale.JAPAN,
//							"comparing... => t1 = %s, t2 = %s [time1.compareToIgnoreCase(time2) > 0]", 
//							t1.getCreated_at(), t2.getCreated_at()
//				);
//		
//				Log.i("Comp_SearchHistory.java" + "["
//						+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//						+ "]", msg_Log);
//
//				res = 1;
//				
//			} else if (time1.compareToIgnoreCase(time2) == 0) {
////			} else if (time1 == time2) {
//
//				msg_Log = String.format(
//						Locale.JAPAN,
//						"comparing... => t1 = %s, t2 = %s [time1.compareToIgnoreCase(time2) == 0]", 
//						t1.getCreated_at(), t2.getCreated_at()
//				);
//		
//				Log.i("Comp_SearchHistory.java" + "["
//						+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//						+ "]", msg_Log);
//
//				res = 0;
//				
//			} else {
//
//				msg_Log = String.format(
//						Locale.JAPAN,
//						"comparing... => t1 = %s, t2 = %s [else]", 
//						t1.getCreated_at(), t2.getCreated_at()
//				);
//		
//				Log.i("Comp_SearchHistory.java" + "["
//						+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//						+ "]", msg_Log);
//
//				res = -1;
//			}
			
			try {
				
				res = t1.getCreated_at().compareTo(t2.getCreated_at());
//				res = t1.getFile_name().compareTo(t2.getFile_name());
				
				res = -1 * res;
				
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				
				// Log
				String msg_Log;
				
				msg_Log = String.format(
						Locale.JAPAN,
						"Exception => %s", e1.getMessage()
						);
				
				Log.e("Comp_HistUpload.java" + "["
						+ Thread.currentThread().getStackTrace()[2].getLineNumber()
						+ "]", msg_Log);
				
				e1.printStackTrace();
				
				res = 1;
				
			}

			
			break;
			
		default:
			
			res = 1;
			
			break;
		}
		
		return res;
		
	}//private int _compare_FileName(AI a1, AI a2)

	private int _compare_CREATED_AT(SearchHistory t1, SearchHistory t2) {
		// TODO Auto-generated method stub
		
		int res;
		
		long time1 = Methods.conv_TimeLabel_to_MillSec(t1.getCreated_at());
		long time2 = Methods.conv_TimeLabel_to_MillSec(t2.getCreated_at());
		
		switch (sortOrder) {
		
		case ASC:
			
			res = (time1 < time2) ? 1 : 0;
			
			break;
			
		case DESC:
			
			// Log
//			String msg_Log;
			
//			msg_Log = String.format(
//					Locale.JAPAN,
//					"comparing... => t1 = %s, t2 = %s", t1.getCreated_at(), t2.getCreated_at()
//					);
//			
//			Log.i("Comp_SearchHistory.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", msg_Log);
			
			if (time1 > time2) {
				
				res = 1;
				
			} else if (time1 == time2) {
				
				res = 0;
				
			} else {
				
				res = -1;
			}
			
			break;
			
		default:
			
			res = 1;
			
			break;
		}
		
		return res;
		
	}//private int _compare_FileName(AI a1, AI a2)
	
}//public class AIComparator implements Comparator<AI>
