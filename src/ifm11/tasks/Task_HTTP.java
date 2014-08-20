package ifm11.tasks;


import ifm11.items.TI;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class Task_HTTP extends AsyncTask<String, Integer, Integer> {

	Activity actv;
	
	private TI ti;
	private String http_Type;
	
	public Task_HTTP(Activity actv) {
		
		this.actv	= actv;
		
	}

	public Task_HTTP(Activity actv, TI ti, String http_Type) {
		// TODO Auto-generated constructor stub
		
		this.actv	= actv;
		
		this.ti		= ti;
		
		this.http_Type	= http_Type;
		
	}

	@Override
	protected Integer doInBackground(String... tags) {
		
//		if (tags[0].equals(actv.getString(R.string.http_post_file_name_lollipop))) {
//		
//			
//		} else {//if (tags[0].equals(actv.getString(R.string.http_post_file_name_lollipop)))
//			
//			
//		}//if (tags[0].equals(actv.getString(R.string.http_post_file_name_lollipop)))
		
		
		return 0;

		
	}//protected Integer doInBackground(String... arg0)
	
	

	@Override
	protected void onCancelled() {
		// TODO Auto-generated method stub
		super.onCancelled();
	}

	@Override
	protected void onPostExecute(Integer result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		
		// debug
		Toast.makeText(actv, "HTTP task => Done", Toast.LENGTH_LONG).show();
		
		// Log
		Log.d("TaskHTTP.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ ":"
				+ Thread.currentThread().getStackTrace()[2].getMethodName()
				+ "]", "HTTP task => Done");
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		
		// debug
		Toast.makeText(actv, "Starting HTTP task...", Toast.LENGTH_LONG).show();
		
		// Log
		Log.d("TaskHTTP.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ ":"
				+ Thread.currentThread().getStackTrace()[2].getMethodName()
				+ "]", "Starting HTTP task...");

	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
	}

	
}
