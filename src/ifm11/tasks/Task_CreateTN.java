package ifm11.tasks;

import ifm11.utils.Methods;
import ifm11.utils.Methods_dlg;
import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

public class Task_CreateTN extends AsyncTask<String, Integer, Integer> {

	Activity actv;
	
	
	public Task_CreateTN(Activity actv) {
		// TODO Auto-generated constructor stub
		
		this.actv	= actv;
		
	}

	@Override
	protected Integer doInBackground(String... arg0) {
		// TODO Auto-generated method stub
		
		return Methods.create_TNs(actv);
		
	}

	@Override
	protected void onCancelled() {
		// TODO Auto-generated method stub
		super.onCancelled();
	}

	@Override
	protected void 
	onPostExecute
	(Integer result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		
		String msg = null;
		
		int val = result.intValue();
		
		
		switch(val) {
		
		case -1://----------------------------------
			
			msg = "Can't create a dir";
			
			break;
			
		case -2://----------------------------------
			
			msg = "TIs list ==> returned null";
			
			break;
			
		default:
			
			msg = "Create tns => done: " + val;
			
			break;
				
		}//switch(val)
		
		Methods.write_Log(actv, msg, Thread.currentThread()
				.getStackTrace()[2].getFileName(), Thread.currentThread()
				.getStackTrace()[2].getLineNumber());
		
		// Log
		Log.d("Task_CreateTN.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg);

//		Methods_dlg.dlg_ShowMessage(actv, msg);
		
	}//onPostExecute

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
