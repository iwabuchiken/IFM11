package ifm11.tasks;

import java.util.Locale;

import ifm11.utils.CONS;
import ifm11.utils.Methods;
import ifm11.utils.Methods_dlg;
import ifm11.utils.STD;
import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

public class Task_RefreshDB extends AsyncTask<String, Integer, Integer> {

	Activity actv;
	
	
	public Task_RefreshDB(Activity actv) {
		// TODO Auto-generated constructor stub
		
		this.actv	= actv;
		
	}

	/*******************************
	 * @return
	 * 	-1	=> Can't create a table<br>
	 * 	-2	=> Can't build cursor<br>
	 * 	-3	=> No entry<br>
	 * 	-4	=> Can't build TI list<br>
	 * 	-5	=> task => cancelled<br>
	 *******************************/
	@Override
	protected Integer doInBackground(String... arg0) {
		// TODO Auto-generated method stub

		///////////////////////////////////
		//
		// cancelled?
		//
		///////////////////////////////////
		if (this.isCancelled() == true) {

			// Log
			String msg_Log;
			
			msg_Log = String.format(
					Locale.JAPAN,
					"task => cancelled"
					);
			
			Log.e("Task_RefreshDB.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			return -5;

		}//if (this.isCancelled() == true)
		
		///////////////////////////////////
		//
		// validate: confirm refresh => decided?
		//
		///////////////////////////////////
		while(CONS.DB.REFRESH_YES == false);

		///////////////////////////////////
		//
		// execute
		//
		///////////////////////////////////
		// Log
		String msg_Log;
		
		msg_Log = String.format(
				Locale.JAPAN,
				"started => doInBackground"
				);
		
		Log.i("Task_RefreshDB.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
//		return -1;
		return STD.refresh_MainDB(actv);
		
	}//doInBackground

	@Override
	protected void onCancelled() {
		// TODO Auto-generated method stub
		super.onCancelled();
		
		// Log
		String msg_Log;
		
		msg_Log = String.format(
				Locale.JAPAN,
				"onCancelled => called"
				);
		
		Log.i("Task_RefreshDB.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
	}
	

	@Override
	protected void onPostExecute(Integer result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		
		String msg = null;
		
		int val = result.intValue();
		
		
		switch(val) {
		
		case -1://----------------------------------
			
			msg = "Can't create a table";
			
			break;
			
		case -2://----------------------------------
			
			msg = "Can't build cursor";
			
			break;
			
		case -3://----------------------------------
			
			msg = "No entry";
			
			break;
			
		case -4://----------------------------------
			
			msg = "Can't build TI list";
			
			break;
			
		case -5://----------------------------------
			
			msg = "task => cancelled";
			
			break;
			
		default:
			
			msg = "Refreshed => " + val;
			
			String log_msg = "Refresh => done: " + val;
			Methods.write_Log(actv, log_msg, Thread.currentThread()
					.getStackTrace()[2].getFileName(), Thread.currentThread()
					.getStackTrace()[2].getLineNumber());
			
			break;
				
		}
		
		Methods_dlg.dlg_ShowMessage(actv, msg);
		
		
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		
		// pre-execute
		int result = STD.refresh_MainDB__PreExecute(actv, this);
		
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
	}

}
