package ifm11.utils;

import ifm11.main.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

public class RGB {

	public static void 
	show_RGB
	(Activity actv) {
		// TODO Auto-generated method stub
		
		String msg_Log;
		
		////////////////////////////////

		// get: image

		////////////////////////////////
		if (CONS.IMageActv.ti == null) {
			
			// Log
			msg_Log = "CONS.IMageActv.ti => null";
			Log.d("RGB.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			return;
			
		}
		
		if (CONS.IMageActv.bm_Modified == null) {
			
			// Log
			msg_Log = "CONS.IMageActv.bm_Modified => null";
			Log.d("RGB.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			return;
			
		}
		
		// Log
		msg_Log = "createing a new Bitmap instance...";
		Log.d("RGB.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		Bitmap bm = CONS.IMageActv.bm_Modified;
		
		int bmp_W = bm.getWidth();
		int bmp_H = bm.getHeight();

		// Log
		msg_Log = String.format(
				Locale.JAPAN,
				"bmp_W => %d, bmp_H => %d", bmp_W, bmp_H);
		Log.d("RGB.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		////////////////////////////////

		// pixels

		////////////////////////////////
		int[] pixels = new int[bmp_W * bmp_H];
				
		bm.getPixels(pixels, 0, bmp_W, 0, 0, bmp_W, bmp_H);

		// Log
		msg_Log = "pixels => obtained";
		Log.d("RGB.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		

		////////////////////////////////

		// get: RGB

		////////////////////////////////
		int[] col_R = new int[256];
		int[] col_G = new int[256];
		int[] col_B = new int[256];
		
		int count = 0;
		
		for (int y = 0; y < bmp_H; y++) {
			
			for (int x = 0; x < bmp_W; x++) {
				
				int pixel = pixels[x + y * bmp_W];
				
//				if (count > 50 && count < 100) {
//					
//					// Log
//					msg_Log = "Color.red(pixel) => " + Color.red(pixel);
//					Log.d("RGB.java"
//							+ "["
//							+ Thread.currentThread().getStackTrace()[2]
//									.getLineNumber() + "]", msg_Log);
//				}
				
				col_R[Color.red(pixel)] ++;
				col_G[Color.green(pixel)] ++;
				col_B[Color.blue(pixel)] ++;
				
				count ++;
				
			}
			
		}//for (int y = 0; y < bmp_H; y++)

		
//		for (int i = 100; i < 150; i++) {
//			
//			// Log
//			msg_Log = "col_R[" + i + "] => " + col_R[i];
//			Log.d("RGB.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", msg_Log);
//			
//		}
		
		////////////////////////////////

		// get: max values

		////////////////////////////////

		int max_R = RGB.get_Max(col_R);
		int max_G = RGB.get_Max(col_G);
		int max_B = RGB.get_Max(col_B);
		
		// Log
		msg_Log = "max_R => " + max_R;
		Log.d("RGB.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		////////////////////////////////

		// adjust: values

		////////////////////////////////
		int range = 256;
		
		int[] col_R_adj = RGB.adj_Pixel_Values(actv, col_R, max_R, range);
		int[] col_G_adj = RGB.adj_Pixel_Values(actv, col_G, max_G, range);
		int[] col_B_adj = RGB.adj_Pixel_Values(actv, col_B, max_B, range);
		
		max_R = RGB.get_Max(col_R_adj);
		
		// Log
		msg_Log = "max_R(adj) => " + max_R;
		Log.d("RGB.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		////////////////////////////////

		// input

		////////////////////////////////
		CONS.Canvas.col_R_adj = col_R_adj;
		CONS.Canvas.col_G_adj = col_G_adj;
		CONS.Canvas.col_B_adj = col_B_adj;
		
		ifm11.views.CV cv = (ifm11.views.CV) actv.findViewById(R.id.actv_main_cv_canvas);
		
		////////////////////////////////

		// draw

		////////////////////////////////
		CONS.Canvas.f_RGB_Lines = true;
		
		cv.invalidate();
		
		// Log
		msg_Log = "cv => invalidated";
		Log.d("RGB.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		////////////////////////////////

		// highs, lows

		////////////////////////////////
		RGB.get_RGB_HighsLows(actv, range);
		
	}//show_RGB

	private static int 
	get_Max
	(int[] col_R) {
		// TODO Auto-generated method stub
		
		int max = -1;
		
		for (int i : col_R) {
			
			if (i > max) {
				
				max = i;
				
			}
		}
		
		return max;
		
	}//get_Max

	private static void 
	get_RGB_HighsLows
	(Activity actv, int range) {
		// TODO Auto-generated method stub
		
		String msg_Log;
		
		/******************************
			validate
		 ******************************/
		if (CONS.Canvas.col_R_adj == null) {
			
			// Log
			msg_Log = "CONS.Canvas.col_R_adj => null";
			Log.d("RGB.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			return;
			
		} else if (CONS.Canvas.col_R_adj.length < 2) {
			
			// Log
			msg_Log = "CONS.Canvas.col_R_adj => < 2";
			Log.d("RGB.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			return;
						
		}
		
		////////////////////////////////

		// process

		////////////////////////////////
//		int col_Cur;
//		int col_N;
		
//		int[] cols = CONS.Canvas.col_R_adj;
//		
//		int i = 0;
//		
//		col_Cur = cols[0];
////		col_N = cols[i + 1];
//		
//		int tmp;
//		
//		int incli = 1;
		
		List<Integer> list_Lows = new ArrayList<Integer>();
		List<Integer> list_Highs = new ArrayList<Integer>();
		
		RGB._highLows__ForLoop(actv, list_Lows, list_Highs, range);

		/***************
		for (i = 1; i < cols.length - 1; i++) {
			
			tmp = cols[i];	// get next value

			////////////////////////////////

			// validate

			////////////////////////////////
			if (tmp == 0 && col_Cur >= range * 0.1) {
				
				continue;
				
			}

			////////////////////////////////

			// next value	:p2

			////////////////////////////////
			col_N = tmp;
			
			////////////////////////////////

			// judge: 		:j3

			////////////////////////////////
			if (col_N > col_Cur) {
				
				if (incli == 1) {
					
					// update: incli => remains 1
					incli = 1;
					
					// update: col_Cur
					col_Cur = col_N;
					
				} else if (incli == -1) {
					
					list_Lows.add(col_Cur);
					
					// update: incli => change to 1
					incli = 1;
					
					// update: col_Cur
					col_Cur = col_N;
					
				} else {
					
					// Log
					msg_Log = "incli =>+ unknown value: " + incli
							+ "(i = " + i + ")";
					
					Log.d("RGB.java"
							+ "["
							+ Thread.currentThread().getStackTrace()[2]
									.getLineNumber() + "]", msg_Log);
					
					// update: incli => force-change to 1
					incli = 1;
					
					// update: col_Cur
					col_Cur = col_N;
					
				}//if (incli == 1)
				
			} else if (col_N < col_Cur) {//if (col_N > col_Cur)

				if (incli == 1) {
					
					list_Highs.add(col_Cur);
					
					// update: incli => change to -1
					incli = -1;
					
					// update: col_Cur
					col_Cur = col_N;
					
				} else if (incli == -1) {
					
					// update: incli => remains -1
					incli = -1;
					
					// update: col_Cur
					col_Cur = col_N;
					
				} else {
					
					// Log
					msg_Log = "incli =>+ unknown value: " + incli
							+ "(i = " + i + ")";
					
					Log.d("RGB.java"
							+ "["
							+ Thread.currentThread().getStackTrace()[2]
									.getLineNumber() + "]", msg_Log);
					
					// update: incli => force-change to -1
					incli = -1;
					
					// update: col_Cur
					col_Cur = col_N;
					
				}//if (incli == 1)
				
			} else if (col_N == col_Cur) {//if (col_N > col_Cur)
				
				// update: col_Cur
				col_Cur = col_N;
				
			} else {//if (col_N > col_Cur)

				// Log
				msg_Log = "col_N, col_Cur => comparison undecided";
				Log.i("RGB.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", msg_Log);
				
			}//if (col_N > col_Cur)
			
		}//for (int i = 0; i < CONS.Canvas.col_R_adj.length; i++)
	 	**************/
		
		// Log
		msg_Log = "HighsLows => done";
		Log.d("RGB.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		////////////////////////////////

		// report

		////////////////////////////////
		// Log
		msg_Log = "list_Lows.size() => " + list_Lows.size()
				+ " / "
				+ "list_Highs.size() => " + list_Highs.size();
		
		Log.d("RGB.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
	}//get_RGB_HighsLows

	private static void 
	_highLows__ForLoop
	(Activity actv,
		List<Integer> list_Lows, List<Integer> list_Highs, int range) {
		// TODO Auto-generated method stub

		String msg_Log;
		
		int[] cols = CONS.Canvas.col_R_adj;
		
		int col_Cur = cols[0], col_N, tmp, incli = 1, i = 0;

//		col_Cur = cols[0];
		
		for (i = 1; i < cols.length - 1; i++) {
			
			tmp = cols[i];	// get next value

			////////////////////////////////

			// validate

			////////////////////////////////
			if (tmp == 0 && col_Cur >= range * 0.1) {
				
				continue;
				
			}

			////////////////////////////////

			// next value	:p2

			////////////////////////////////
			col_N = tmp;
			
			////////////////////////////////

			// judge: 		:j3

			////////////////////////////////
			if (col_N > col_Cur) {
				
				if (incli == 1) {
					
					// update: incli => remains 1
					incli = 1;
					
					// update: col_Cur
					col_Cur = col_N;
					
				} else if (incli == -1) {
					
					list_Lows.add(col_Cur);

					msg_Log = String.format(
								Locale.JAPAN, 
								"Lows: col_Cur = %d, col_N = %d, incli = %d (i = %d)", 
								col_Cur, col_N, incli, i);

					Log.d("RGB.java"
							+ "["
							+ Thread.currentThread().getStackTrace()[2]
									.getLineNumber() + "]", msg_Log);
					
					
					// update: incli => change to 1
					incli = 1;
					
					// update: col_Cur
					col_Cur = col_N;
					
				} else {
					
					// Log
					msg_Log = "incli =>+ unknown value: " + incli
							+ "(i = " + i + ")";
					
					Log.d("RGB.java"
							+ "["
							+ Thread.currentThread().getStackTrace()[2]
									.getLineNumber() + "]", msg_Log);
					
					// update: incli => force-change to 1
					incli = 1;
					
					// update: col_Cur
					col_Cur = col_N;
					
				}//if (incli == 1)
				
			} else if (col_N < col_Cur) {//if (col_N > col_Cur)

				if (incli == 1) {
					
					list_Highs.add(col_Cur);

					list_Lows.add(col_Cur);

					msg_Log = String.format(
								Locale.JAPAN, 
								"Highs: col_Cur = %d, col_N = %d, incli = %d (i = %d)", 
								col_Cur, col_N, incli, i);

					Log.d("RGB.java"
							+ "["
							+ Thread.currentThread().getStackTrace()[2]
									.getLineNumber() + "]", msg_Log);

					// update: incli => change to -1
					incli = -1;
					
					// update: col_Cur
					col_Cur = col_N;
					
				} else if (incli == -1) {
					
					// update: incli => remains -1
					incli = -1;
					
					// update: col_Cur
					col_Cur = col_N;
					
				} else {
					
					// Log
					msg_Log = "incli =>+ unknown value: " + incli
							+ "(i = " + i + ")";
					
					Log.d("RGB.java"
							+ "["
							+ Thread.currentThread().getStackTrace()[2]
									.getLineNumber() + "]", msg_Log);
					
					// update: incli => force-change to -1
					incli = -1;
					
					// update: col_Cur
					col_Cur = col_N;
					
				}//if (incli == 1)
				
			} else if (col_N == col_Cur) {//if (col_N > col_Cur)
				
				// update: col_Cur
				col_Cur = col_N;
				
			} else {//if (col_N > col_Cur)

				// Log
				msg_Log = "col_N, col_Cur => comparison undecided";
				Log.i("RGB.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", msg_Log);
				
			}//if (col_N > col_Cur)
			
		}//for (int i = 0; i < CONS.Canvas.col_R_adj.length; i++)

	}//_highLows__ForLoop
	

	private static int[] 
	adj_Pixel_Values
	(Activity actv, int[] col_R, int max, int range) {
		// TODO Auto-generated method stub
		
		int len = col_R.length;
		
		int[] adj = new int[len];
		
		for (int i = 0; i < col_R.length; i++) {
			
			adj[i] = (int)(((float)col_R[i] / max) * range);
			
		}
		
		return adj;
		
	}//adj_Pixel_Values

}
