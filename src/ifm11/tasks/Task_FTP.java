/********************************************
 * Task_FTP
 * 
 * 18/08/2014 10:30:12
 * 
 * <What for>
 * 	1. Ftp image file to remote server
 * 	2. Ftp db file to remote server
 * 
 * <Usage>
 * 	1. The "ftp_Type" parameter in doInBackground()
 * 		=> use CONS.Remote.FtpType
 *********************************************/
package ifm11.tasks;

import ifm11.items.TI;
import ifm11.main.R;
import ifm11.utils.CONS;
import ifm11.utils.DBUtils;
import ifm11.utils.Methods;
import ifm11.utils.Methods_dlg;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Vibrator;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

//public class TaskFTP extends AsyncTask<String, Integer, String> {
public class Task_FTP extends AsyncTask<String, Integer, Integer> {

	Activity actv;
	
	TI ti;
	
	boolean delete;
	
	String ftpTag;//=> Use this field for ftp_upload_db_file

	private String ftp_Type;
	
	Vibrator vib;

	private Dialog d1;

	private Dialog d2;

	private Dialog d3;
	
	public Task_FTP(Activity actv) {
		
		this.actv = actv;
		
	}
	
	public Task_FTP(Activity actv, TI ti) {
		// TODO Auto-generated constructor stub
		this.actv	= actv;
		this.ti		= ti;
		
	}

	
	/*********************************
	 * @param boolean delete<br>
	 * 			true => Delete the file when uploaded<br>
	 * 			false => Doesn't delete the file when uploaded
	 *********************************/
	public Task_FTP(Activity actv, TI ti, boolean delete) {

		this.actv	= actv;
		this.ti		= ti;
		
		this.delete	= delete;

	}

	public Task_FTP
	(Activity actv, 
		Dialog dlg1, Dialog dlg2, Dialog dlg3,
			String ftp_Type, TI ti, boolean delete) {
		// TODO Auto-generated constructor stub
		this.actv	= actv;
		
		this.d1	= dlg1;
		this.d2	= dlg2;
		this.d3	= dlg3;
		
		this.ti		= ti;
		this.ftp_Type	= ftp_Type;
		
		this.delete	= delete;

		
	}

	public Task_FTP
	(Activity actv, Dialog d1, Dialog d2, boolean delete) {
		// TODO Auto-generated constructor stub
		
		this.actv	= actv;
		
		this.d1	= d1;
		this.d2	= d2;
		
		this.delete	= delete;
		
	}

	public Task_FTP
	(Activity actv, Dialog d1, Dialog d2, String ftp_Type) {
		
		this.actv	= actv;
		
		this.d1	= d1;
		this.d2	= d2;
		
		this.ftp_Type	= ftp_Type;
		
	}
	

	/******************************
		Used when: Uploading DB file
	 ******************************/

	@Override
//	protected String doInBackground(String... ftpTags) {
	protected Integer
	doInBackground(String... ftp_Type) {
		
		vib = (Vibrator) actv.getSystemService(Context.VIBRATOR_SERVICE);

//		this.ftp_Type = ftp_Type[0];
		
		// Log
		String msg_Log = "starting... doInBackground";
		Log.d("TaskFTP.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);

		// Log
		msg_Log = "ftp type[0] => " + ftp_Type[0];
		Log.d("Task_FTP.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		////////////////////////////////

		// dispatch

		////////////////////////////////
		int res = CONS.Remote.initial_IntValue;
		
		if (this.ftp_Type.equals(CONS.Remote.FtpType.IMAGE.toString())) {
			
			res = Methods.ftp_Image_to_Remote(actv, ti);
			
			////////////////////////////////

			// post data

			////////////////////////////////
			if (res == CONS.Remote.status_220) {
				
				//REF http://wikiwiki.jp/android/?AsyncTask%A4%C7%A5%D0%A5%C3%A5%AF%A5%B0%A5%E9%A5%A6%A5%F3%A5%C9%BD%E8%CD%FD%A4%F2%B9%D4%A4%A6 "進捗ダイアログの更新"
				this.publishProgress(CONS.Remote.status_220);
				
				res = Methods.post_ImageData_to_Remote(actv, ti);
				
			}
			
		} else if (this.ftp_Type.equals(CONS.Remote.FtpType.DB_FILE.toString())) {
//		} else if (ftp_Type[0].equals(CONS.Remote.FtpType.DB_FILE.toString())) {
			
			res = Methods.ftp_Remote_DB(actv);
//			// Log
//			msg_Log = "Sorry. Under construction...";
//			Log.d("Task_FTP.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", msg_Log);
//			
//			return 0;
			
		} else {

			// Log
			msg_Log = "Unknown ftp type => " + this.ftp_Type;
//			msg_Log = "Unknown ftp type => " + ftp_Type[0];
			Log.d("Task_FTP.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
		}
		
		return res;
//		return 0;

	}//doInBackground(String... ftpTags)

	@Override
	protected void onCancelled() {
		// TODO Auto-generated method stub
		super.onCancelled();
		
		
		
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
		
		String msg = "Image file => uploaded. Posting info....";
		Methods_dlg.dlg_ShowMessage_Duration(
					actv, msg, 
					CONS.Admin.dflt_MessageDialog_Length);
		
	}
	

	@Override
//	protected void onPostExecute(String result) {
	protected void onPostExecute(Integer res) {
		
		super.onPostExecute(res);
		
		////////////////////////////////

		// dispatch

		////////////////////////////////
		if (ftp_Type.equals(CONS.Remote.FtpType.IMAGE.toString())) {
			
			_onPostExecute__Upload_Image(res);
			
		} else if (ftp_Type.equals(CONS.Remote.FtpType.DB_FILE.toString())) {
			
			_onPostExecute__Upload_DB(res);
			
		} else {

//			// Log
//			msg_Log = "Unknown ftp type => " + ftp_Type[0];
//			Log.d("Task_FTP.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", msg_Log);
			
		}
		
//		String msg = "Upload result => " + res.intValue();
//		Methods_dlg.dlg_ShowMessage_Duration(
//						actv, msg, CONS.Admin.dflt_MessageDialog_Length);
//		
//		// Log
//		Log.d("Task_FTP.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg);
		
	}//protected void onPostExecute(String result)

	private void 
	_onPostExecute__Upload_DB
	(Integer res) {
		// TODO Auto-generated method stub
		////////////////////////////////

		// setup

		////////////////////////////////
		int res_i = res.intValue();
		
		String msg = null;
		int colorID = 0;
		
		////////////////////////////////

		// dispatch

		////////////////////////////////
		switch(res_i) {
		
		case 220:
			
			msg = "Upload result => " + res_i;
			colorID = R.color.green4;
			
			////////////////////////////////

			// dismiss

			////////////////////////////////
			if(d3 != null) d3.dismiss();
			if(d2 != null) d2.dismiss();
			if(d1 != null) d1.dismiss();
			
			break;

		case 0:
			
			msg = "Sorry. Uploading DB is not ready yet";
			colorID = R.color.gold2;
			
			if(d2 != null) d2.dismiss();

			break;
			
		default:
			
			msg = "Upload result => " + res_i;
			colorID = R.color.red;
			
			break;
			
		}
		
		// Log
		String msg_Log = "colorID => " + colorID;
		Log.d("Task_FTP.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		Methods_dlg.dlg_ShowMessage_Duration(
						actv, 
						msg,
						colorID,
						CONS.Admin.dflt_MessageDialog_Length);
		
		// Log
		Log.d("Task_FTP.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg);


	}//_onPostExecute__Upload_DB

	private void 
	_onPostExecute__Upload_Image
	(Integer res) {
		// TODO Auto-generated method stub
	
		////////////////////////////////

		// setup

		////////////////////////////////
		int res_i = res.intValue();
		
		String msg = null;
		int colorID = 0;
		
		////////////////////////////////

		// dispatch

		////////////////////////////////
		if (res_i >= 200 && res_i <= 220) {
			
			boolean tmp_b = DBUtils.update_TI__UploadedAt(actv, ti);
			
			if (tmp_b == true) {
				
				msg = "Upload result => " + res.intValue();
				colorID = R.color.green4;
				
				////////////////////////////////

				// log

				////////////////////////////////
				String log_msg = "Upload => done: " + ti.getFile_name();
				Methods.write_Log(actv, log_msg, Thread.currentThread()
						.getStackTrace()[2].getFileName(), Thread
						.currentThread().getStackTrace()[2].getLineNumber());
				
			} else {
				
				msg = "Upload result => " + res.intValue()
						+ " / "
						+ "record not saved!";
				
				colorID = R.color.gold2;

				////////////////////////////////

				// log

				////////////////////////////////
				String log_msg = "Upload => done, record not saved: " 
							+ ti.getFile_name();
				Methods.write_Log(actv, log_msg, Thread.currentThread()
						.getStackTrace()[2].getFileName(), Thread
						.currentThread().getStackTrace()[2].getLineNumber());

			}
			
			
			////////////////////////////////

			// dismiss

			////////////////////////////////
			if(d3 != null) d3.dismiss();
			if(d2 != null) d2.dismiss();
			if(d1 != null) d1.dismiss();
			
		} else {
			
			String log_msg = "Upload => Exception occurred: " 
							+ ti.getFile_name();
			Methods.write_Log(actv, log_msg, Thread.currentThread()
					.getStackTrace()[2].getFileName(), Thread.currentThread()
					.getStackTrace()[2].getLineNumber());
			
			switch(res_i) {
//			-1 => SocketException
//			-2 => IOException
//			-3 => IOException in disconnecting
			
			case -1: 
			
				msg = "Upload result => SocketException";
				colorID = R.color.red;
				
				
				
				break;
			
			case -2: 
				
				msg = "Upload result => IOException";
				colorID = R.color.red;
				
				break;
				
			case -3: 
				
				msg = "Upload result => IOException in disconnecting";
				colorID = R.color.red;
				
				break;
				
//				-4 => Login failed
//				-5 => IOException in logging-in
//				-6 => storeFile returned false
				
			case -4: 
				
				msg = "Upload result => Login failed";
				colorID = R.color.red;
				
				break;
				
			case -5: 
				
				msg = "Upload result => IOException in logging-in";
				colorID = R.color.red;
				
				break;
				
			case -6: 
				
				msg = "Upload result => storeFile returned false";
				colorID = R.color.red;
				
				break;

			case -7: 
				
				msg = "Upload result => can't find the source file";
				colorID = R.color.red;
				
				break;
				
//				-7 => can't find the source file
			case -8: 
				
				msg = "Upload result => can't find the " +
						"source file; can't disconnect FTP client";
				colorID = R.color.red;
				
				break;
				
//				-8 => can't find the source file; can't disconnect FTP client
			case -9: 
				
				msg = "Upload result => storeFile ---> IOException";
				colorID = R.color.red;
				
				break;
				
//				-9 => storeFile ---> IOException
			case -10: 
				
				msg = "Upload result => storeFile ---> " +
						"IOException; can't disconnect FTP client";
				colorID = R.color.red;
				
				break;
				
//				-10 => storeFile ---> IOException; can't disconnect FTP client
//				-11 => set file type ---> failed
//				-12 => IOException in logging-in; can't disconnect FTP client
//				>0 => Reply code

				
			default:
				
				msg = "Upload result => " + res.intValue();
				colorID = R.color.red;
				
				break;
				
			}
		}
		
		Methods_dlg.dlg_ShowMessage_Duration(
						actv, 
						msg,
						colorID,
						CONS.Admin.dflt_MessageDialog_Length);
		
		// Log
		Log.d("Task_FTP.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg);
		
	}//_onPostExecute__Upload_Image

	@Override
	protected void 
	onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		
		////////////////////////////////

		// setup

		////////////////////////////////
		String msg = null;
		int colorID = 0;
		
		////////////////////////////////

		// dispatch

		////////////////////////////////
		if (this.ftp_Type.equals(CONS.Remote.FtpType.IMAGE.toString())) {
//			if (ftp_Type[0].equals(CONS.Remote.FtpType.IMAGE.toString())) {
			
			msg = "Uploading... " + ti.getFile_name();
			colorID = R.color.green4;
			
		} else if (this.ftp_Type.equals(CONS.Remote.FtpType.DB_FILE.toString())) {
//		} else if (ftp_Type[0].equals(CONS.Remote.FtpType.DB_FILE.toString())) {
			
			msg = "Uploading db file... ";
			colorID = R.color.green4;
			
		} else {

			// Log
			msg = "Unknown ftp type => " + this.ftp_Type;
			colorID = R.color.red;
			
		}

		Methods_dlg.dlg_ShowMessage_Duration(
						actv, msg, 
						colorID,
						CONS.Admin.dflt_MessageDialog_Length);
		
	}//onPreExecute
	
}
