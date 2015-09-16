/*
 * javac C:/WORKS/WS/WS_Android/IFM11/src/ifm11/utils/MyTest.java
 * 
 */

// 

import java.util.Locale;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class MyTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		test_D_43_V_2_0__Regex();
		
//		String th = "2015-08-14_07";
////		String th = "2015-08-14";
//		
////		08-16 12:38:14.857: I/STD.java[472](15369): TI file name => 2015-08-16_08-40-42_767.jpg
//		String s1 = "2015-08-16_08-40-42_767.jpg";
////		String s1 = "2015-08-16";
//		
//		int res_i = s1.compareToIgnoreCase(th);
//		
//		String msg;
//		msg = String.format(Locale.JAPAN, "[%s : %d] res => %d (th = %s, s1 = %s)", Thread
//				.currentThread().getStackTrace()[1].getFileName(), Thread
//				.currentThread().getStackTrace()[1].getLineNumber(), res_i, th, s1);
//
//		System.out.println(msg);
//		
//		
////		String msg;
//		msg = String.format(Locale.JAPAN, "[%s : %d] done", Thread
//				.currentThread().getStackTrace()[1].getFileName(), Thread
//				.currentThread().getStackTrace()[1].getLineNumber(), args);
//
//		System.out.println(msg);
		
		
	}

	public static void
	test_D_43_V_2_0__Regex() {
		
		String str = "\\d\\d\\d\\d";
		
		Pattern p = Pattern.compile(str);
		
		String s1 = "1435";
		String s2 = "2015-08-16_08-40-42_767.jpg";
		
		Matcher m = p.matcher(s1);
		Matcher m2 = p.matcher(s2);
		
		String msg;
		msg = String.format(Locale.JAPAN, "[%s : %d] s1 = %s / find => %s", Thread
				.currentThread().getStackTrace()[1].getFileName(), Thread
				.currentThread().getStackTrace()[1].getLineNumber(), s1, m.find());

		System.out.println(msg);
		
		msg = String.format(Locale.JAPAN, "[%s : %d] s2 = %s / find => %s", Thread
				.currentThread().getStackTrace()[1].getFileName(), Thread
				.currentThread().getStackTrace()[1].getLineNumber(), s2, m2.find());
		
		System.out.println(msg);
		
		
	}//test_D_43_V_2_0__Regex
	
}
