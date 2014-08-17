package ifm11.tasks;

import ifm11.items.TI;
import ifm11.utils.CONS;
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
			TI ti, boolean delete) {
		// TODO Auto-generated constructor stub
		this.actv	= actv;
		
		this.ti		= ti;
		
		this.d1	= dlg1;
		this.d2	= dlg2;
		this.d3	= dlg3;
		
		this.delete	= delete;

		
	}

	@Override
//	protected String doInBackground(String... ftpTags) {
	protected Integer
	doInBackground(String... ftpTags) {
		
		vib = (Vibrator) actv.getSystemService(Context.VIBRATOR_SERVICE);

		// Log
		String msg_Log = "starting... doInBackground";
		Log.d("TaskFTP.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		////////////////////////////////

		// upload

		////////////////////////////////
		int res = Methods.ftp_Image_to_Remote(actv, ti);
		
		return res;
//		return 0;

	}//doInBackground(String... ftpTags)

	@Override
//	protected void onPostExecute(String result) {
	protected void onPostExecute(Integer res) {
		
		super.onPostExecute(res);
		
		String msg = "Upload done. result => " + res.intValue();
		Methods_dlg.dlg_ShowMessage_Duration(
						actv, msg, CONS.Admin.dflt_MessageDialog_Length);
		
		// Log
		Log.d("Task_FTP.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg);
		
	}//protected void onPostExecute(String result)

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();

		String msg = "Uploading... " + ti.getFile_name();
		Methods_dlg.dlg_ShowMessage_Duration(
						actv, msg, CONS.Admin.dflt_MessageDialog_Length);
		
	}
	
}
