package ifm11.tasks;

import ifm11.utils.Methods;
import ifm11.utils.Methods_dlg;
import ifm11.utils.STD;
import android.app.Activity;
import android.os.AsyncTask;

public class Task_RefreshDB extends AsyncTask<String, Integer, Integer> {

	Activity actv;
	
	
	public Task_RefreshDB(Activity actv) {
		// TODO Auto-generated constructor stub
		
		this.actv	= actv;
		
	}

	@Override
	protected Integer doInBackground(String... arg0) {
		// TODO Auto-generated method stub
		
		return STD.refresh_MainDB(actv);
		
	}

	@Override
	protected void onCancelled() {
		// TODO Auto-generated method stub
		super.onCancelled();
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
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
	}

}
