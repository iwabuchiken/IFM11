package ifm11.comps;

import ifm11.items.LogItem;
import ifm11.items.TI;
import ifm11.utils.CONS.Enums.SortOrder;
import ifm11.utils.CONS.Enums.SortType;
import ifm11.utils.Methods;

import java.util.Comparator;
import java.util.List;

public class Comp_LogItem implements Comparator<LogItem> {

	List<LogItem> ti_List;
	SortType sortType;
	SortOrder sortOrder;
	
	
	public Comp_LogItem
	(List<LogItem>
		ti_List, SortType sortType,
		SortOrder sortOrder) {
		
		this.ti_List	= ti_List;
		
		this.sortType	= sortType;
		
		this.sortOrder	= sortOrder;
		
	}

	@Override
	public int compare(LogItem a1, LogItem a2) {
		// TODO Auto-generated method stub
		int res;
		
		switch(sortType) {
		
		case FileName:
			
//			res = _compare_FileName(a1, a2);
			
			break;
			
		case CREATED_AT:
			
//			res = _compare_CREATED_AT(a1, a2);
			
			break;
			
		default:
			
			res = 1;
		
		}
		
		//test
		res = 1;
		
		return res;
		
	}//public int compare(AI a1, AI a2)

//	private int _compare_FileName(LogItem t1, LogItem t2) {
//		// TODO Auto-generated method stub
//		
//		int res;
//		
//		switch (sortOrder) {
//		
//		case ASC:
//			
////				res = (int) (a1.getCreated_at() - a2.getCreated_at());
//			res = t1.getFile_name().compareTo(t2.getFile_name());
//			
//			break;
//			
//		case DEC:
//			
////				res = (int) -(a1.getCreated_at() - a2.getCreated_at());
//			res = t2.getFile_name().compareTo(t1.getFile_name());
//			
//			break;
//			
//		default:
//			
//			res = 1;
//			
//			break;
//		}
//		
//		return res;
//
//	}//private int _compare_FileName(AI a1, AI a2)
//	
//	private int _compare_CREATED_AT(LogItem t1, LogItem t2) {
//		// TODO Auto-generated method stub
//		
//		int res;
//		
//		long time1 = Methods.conv_TimeLabel_to_MillSec(t1.getCreated_at());
//		long time2 = Methods.conv_TimeLabel_to_MillSec(t2.getCreated_at());
//		
//		switch (sortOrder) {
//		
//		case ASC:
//			
//			res = (time1 < time2) ? 1 : 0;
//			
//			break;
//			
//		case DEC:
//
//			res = (time1 > time2) ? 1 : 0;
//			
//			break;
//			
//		default:
//			
//			res = 1;
//			
//			break;
//		}
//		
//		return res;
//		
//	}//private int _compare_FileName(AI a1, AI a2)

}//public class AIComparator implements Comparator<AI>
