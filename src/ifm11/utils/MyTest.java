/*
 * javac C:/WORKS/WS/WS_Android/IFM11/src/ifm11/utils/MyTest.java
 * 
 */

// 

package ifm11.utils;

import java.util.Locale;

public class MyTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String msg;
		msg = String.format(Locale.JAPAN, "[%s : %d] done", Thread
				.currentThread().getStackTrace()[1].getFileName(), Thread
				.currentThread().getStackTrace()[1].getLineNumber(), args);

		System.out.println(msg);
		
		
	}

}
