package ifm11.main;

import ifm11.utils.CONS;
import android.app.ListActivity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class PrefActv extends PreferenceActivity 
					implements OnSharedPreferenceChangeListener{

	@Override
	public void onCreate(Bundle savedInstanceState) {
		/*----------------------------
		 * Steps
		 * 1. Super
		 * 2. Set content
		 * 
		 * 3. Set preferences
		----------------------------*/
		super.onCreate(savedInstanceState);

		//
		setContentView(R.layout.main_pref);

		this.setTitle(this.getClass().getName());
		
		/*----------------------------
		 * 3. Set preferences
			----------------------------*/
		getPreferenceManager()
				.setSharedPreferencesName(
						CONS.Pref.pname_MainActv);
		
//		this.getString(R.string.prefs_shared_prefs_name));
		
		addPreferencesFromResource(R.xml.preferences);
		
	}//public void onCreate(Bundle savedInstanceState)


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onPause() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		super.onResume();
	}

	@Override
	protected void onStart() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		
		_Setup_Pref_FontSize();
		
		this._Setup_Pref_AutoBk();
		
		_Setup_Listeners();
		
		super.onStart();
		
	}//protected void onStart()

	private void _Setup_Listeners() {
		// TODO Auto-generated method stub
		
		getPreferenceScreen().getSharedPreferences()
    	.registerOnSharedPreferenceChangeListener(this);
		
	}


	private void _Setup_Pref_FontSize() {
		// TODO Auto-generated method stub
		////////////////////////////////

		// pref: font size

		////////////////////////////////
		EditTextPreference prefEditText = 
				(EditTextPreference) findPreference(
						this.getString(R.string.prefs_tnactv_list_font_size_key));
//		this.getString(R.string.prefs_history_size_key));
		
		prefEditText.getEditText().setInputType(InputType.TYPE_CLASS_NUMBER);

		////////////////////////////////

		// show: current

		////////////////////////////////
		String current = prefEditText.getText();

		prefEditText.setSummary("Current = " + current);
		
	}

	private void _Setup_Pref_AutoBk() {
		// TODO Auto-generated method stub
		String msg_Log;
		
		////////////////////////////////
		
		// pref: auto backup
		
		////////////////////////////////
		EditTextPreference pref_AutoBk = 
				(EditTextPreference) findPreference(
						this.getString(R.string.prefs_tnactv_db_auto_backup_key));
//		this.getString(R.string.prefs_history_size_key));
		
		pref_AutoBk.getEditText().setInputType(InputType.TYPE_CLASS_NUMBER);
		
		////////////////////////////////
		
		// show: current
		
		////////////////////////////////
		String current = pref_AutoBk.getText();
		
//		// Log
//		msg_Log = "current => " + current;
//		Log.d("PrefActv.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_Log);
//		
		String summary = pref_AutoBk.getSummary().toString();
//		
//		// Log
//		msg_Log = "summary(current) => " + summary;
//		Log.d("PrefActv.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_Log);
		
		
		if (current == null) {
//			if (summary == null || current == null) {
			
			summary = this.getString(R.string.prefs_tnactv_db_auto_backup_summary);
			
		} else {

			//REF http://stackoverflow.com/questions/12377838/android-replace-with-regex answered Sep 11 '12 at 21:01
			summary = summary.replaceAll("\\d{1,}", current);
//			summary = summary.replaceAll("[\\d+?X]", current);
//			summary = summary.replaceAll("X", current);
			
		}
		
//		// Log
//		msg_Log = "summary(new) => " + summary;
//		Log.d("PrefActv.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_Log);
		
		pref_AutoBk.setSummary(summary);
//		pref_AutoBk.setSummary("Current = " + current);
		
	}
	

	@Override
	protected void onStop() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		super.onDestroy();
	}


	@Override
	public void onSharedPreferenceChanged(SharedPreferences pref, String key) {
		// TODO Auto-generated method stub
		////////////////////////////////

		// update

		////////////////////////////////
		_Setup_Pref_FontSize();
		
		this._Setup_Pref_AutoBk();
		
	}
	
}//public class PrefActv extends ListActivity
