
//import ifm11.utils.CONS;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.nio.file.Files;

public class ChangeFileName {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		do_D_45_V_1_0__Change_FileName();
		
		String msg;
		msg = String.format(Locale.JAPAN, "[%s : %d] done", Thread
				.currentThread().getStackTrace()[1].getFileName(), Thread
				.currentThread().getStackTrace()[1].getLineNumber());

		System.out.println(msg);
		
		
	}

	public static void 
	do_D_45_V_1_0__Change_FileName() {
		
		String fpath = "C:\\WORKS\\Storage\\images\\100SHARP";
		
		File f = new File(fpath);
		
		// validate
		if (!f.exists()) {
			
			String msg;
			msg = String.format(Locale.JAPAN, "[%s : %d] dir => not exist: %s", Thread
					.currentThread().getStackTrace()[1].getFileName(), Thread
					.currentThread().getStackTrace()[1].getLineNumber(), f.getAbsolutePath());

			System.out.println(msg);
			
			return;
			
		} else {//if (!f.exists())
			
			String msg;
			msg = String.format(Locale.JAPAN, "[%s : %d] dir => exists: %s", Thread
					.currentThread().getStackTrace()[1].getFileName(), Thread
					.currentThread().getStackTrace()[1].getLineNumber(), f.getAbsolutePath());

			System.out.println(msg);
			
		}//if (!f.exists())

		///////////////////////////////////
		//
		// files list
		//
		///////////////////////////////////
		File[] files = f.listFiles();
		
		String msg;
		msg = String.format(Locale.JAPAN, "[%s : %d] files => %d", Thread
				.currentThread().getStackTrace()[1].getFileName(), Thread
				.currentThread().getStackTrace()[1].getLineNumber(), files.length);

		System.out.println(msg);
		
		///////////////////////////////////
		//
		// report
		//
		///////////////////////////////////
		int tmp_i = files.length;
		
		for (int i = 0; i < tmp_i; i++) {
			
//			String msg;
			msg = String.format(Locale.JAPAN, "[%s : %d] file => %s (date = %s)", Thread
//					msg = String.format(Locale.JAPAN, "[%s : %d] file => %s (date = %ld)", Thread
					.currentThread().getStackTrace()[1].getFileName(), Thread
					.currentThread().getStackTrace()[1].getLineNumber(), 
					files[i].getName(), 
					conv_MillSec_to_TimeLabel(files[i].lastModified())
//					files[i].lastModified()
					);

			System.out.println(msg);
			
			
		}
		
		///////////////////////////////////
		//
		// copy: files
		//
		///////////////////////////////////
		File src = null;
		File dst = null;
		
		for (int i = 0; i < tmp_i; i++) {
			
			src = files[i];
			
			dst = new File(fpath, conv_MillSec_to_TimeLabel(files[i].lastModified()) + ".jpg");
			
//			String msg;
			msg = String.format(Locale.JAPAN, "[%s : %d] src = %s || dst = %s", Thread
					.currentThread().getStackTrace()[1].getFileName(), Thread
					.currentThread().getStackTrace()[1].getLineNumber(), 
					src.getAbsolutePath(), 
					dst.getAbsolutePath());

			System.out.println(msg);
			
			try {
				
				Files.copy(src.toPath(), dst.toPath());
				
			} catch (Exception e) {
				
//				String msg;
				msg = String.format(Locale.JAPAN, "[%s : %d] exception", Thread
						.currentThread().getStackTrace()[1].getFileName(),
						Thread.currentThread().getStackTrace()[1]
								.getLineNumber());
				
				e.printStackTrace();

				System.out.println(msg);
				
			}
			
		}
		
		
	}//do_D_45_V_1_0__Change_FileName

	/*******************************
	 * format => CONS.Admin.format_Date<br>
	 * 
	 * "yyyy/MM/dd HH:mm:ss.SSS"
	 * 
	 *******************************/
	public static String
	conv_MillSec_to_TimeLabel(long millSec) {
		//REF http://stackoverflow.com/questions/7953725/how-to-convert-milliseconds-to-date-format-in-android answered Oct 31 '11 at 12:59
		String dateFormat = "yyyy-MM-dd_hh-mm-ss_SSS";
//		String dateFormat = "yyyyMMdd_hhmmss_SSS";
//		String dateFormat = "yyyy/MM/dd hh:mm:ss.SSS";
//		String dateFormat = "yyyy/MM/dd hh:mm:ss.SSS";
		
		DateFormat formatter = new SimpleDateFormat(dateFormat, Locale.JAPAN);
//		DateFormat formatter = new SimpleDateFormat(dateFormat);

		// Create a calendar object that will convert the date and time value in milliseconds to date. 
		Calendar calendar = Calendar.getInstance();
		
		calendar.setTimeInMillis(millSec);
		
		return formatter.format(calendar.getTime());
		
	}//conv_MillSec_to_TimeLabel(long millSec)

}
