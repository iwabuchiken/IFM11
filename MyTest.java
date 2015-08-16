/*
 * javac C:/WORKS/WS/WS_Android/IFM11/src/ifm11/utils/MyTest.java
 * 
 */

// 

import java.util.Locale;

public class MyTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String th = "2015-08-14_07";
//		String th = "2015-08-14";
		
//		08-16 12:38:14.857: I/STD.java[472](15369): TI file name => 2015-08-16_08-40-42_767.jpg
		String s1 = "2015-08-16_08-40-42_767.jpg";
//		String s1 = "2015-08-16";
		
		int res_i = s1.compareToIgnoreCase(th);
		
		String msg;
		msg = String.format(Locale.JAPAN, "[%s : %d] res => %d (th = %s, s1 = %s)", Thread
				.currentThread().getStackTrace()[1].getFileName(), Thread
				.currentThread().getStackTrace()[1].getLineNumber(), res_i, th, s1);

		System.out.println(msg);
		
		
//		String msg;
		msg = String.format(Locale.JAPAN, "[%s : %d] done", Thread
				.currentThread().getStackTrace()[1].getFileName(), Thread
				.currentThread().getStackTrace()[1].getLineNumber(), args);

		System.out.println(msg);
		
		
	}

}
