package ifm11.main;

import ifm11.listeners.button.BO_CL;
import ifm11.listeners.button.BO_TL;
import ifm11.listeners.view.V_OCL;
import ifm11.listeners.view.V_OTL;
import ifm11.utils.CONS;
import ifm11.utils.Methods;
import ifm11.utils.Tags;
import ifm11.utils.Tags.ButtonTags;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class CanvasActv extends Activity {

	public static Vibrator vib;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	/*----------------------------
		 * 1. super
		 * 2. Set content
		 * 2-2. Set title
		 * 3. Initialize => vib
		 * 
		 *  4. Set list
		 *  5. Set listener => Image buttons
		 *  6. Set path label
		 *  
		 *  7. Initialize preferences
		 *  
		 *  8. Refresh DB
			----------------------------*/
		
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actv_main_cv);
//        setContentView(R.layout.actv_main);
        
        /*----------------------------
		 * 2-2. Set title
			----------------------------*/
		this.setTitle(this.getClass().getName());
        
        vib = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);

        
    }//public void onCreate(Bundle savedInstanceState)

    @Override
	protected void onDestroy() {
		/*----------------------------
		 * 1. Reconfirm if the user means to quit
		 * 2. super
		 * 3. Clear => prefs_main
		 * 4. Clear => list_root_dir
			----------------------------*/
		// Log
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "onDestroy()");
		
		super.onDestroy();
		
	}//protected void onDestroy()

	@Override
	public boolean 
	onKeyDown
	(int keyCode, KeyEvent event) {
		
		// Log
		String msg_Log = "onKeyDown()";
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		return super.onKeyDown(keyCode, event);
		
	}

	@Override
	public void 
	onBackPressed() {
		/*----------------------------
		 * memo
			----------------------------*/
		this.finish();
		
		overridePendingTransition(0, 0);
		
		// Log
		String msg_Log = "onBackPressed";
		Log.d("CanvasActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
	}//public void onBackPressed()

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// 
//		MenuInflater mi = getMenuInflater();
//		mi.inflate(R.menu.menu_actv_main, menu);
		
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

//		case R.id.menu_main_admin://----------------------------------
//			
//			Methods_dlg.dlg_ACTV_MAIN_Admin(this);
//			
//			break;// case R.id.main_opt_menu_create_folder
			
		}//switch (item.getItemId())
		
		return super.onOptionsItemSelected(item);
		
	}//public boolean onOptionsItemSelected(MenuItem item)

	@Override
	protected void onPause() {
		
		super.onPause();

		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "onPause()");

	}

	@Override
	protected void onResume() {
		/*********************************
		 * 1. super
		 * 2. Set enables
		 *********************************/
		super.onResume();

		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "onResume()");
		
	}//protected void onResume()

	@Override
	protected void onStart() {
		
		boolean res;
		String msg_Log;
		
		super.onStart();
		
//		_test_DrawLine();
		
		this._Setup();
		
		////////////////////////////////

		// listener

		////////////////////////////////
		
		_Setup__SetListener();

	}//protected void onStart()

	private void 
	_Setup__SetListener() {
		// TODO Auto-generated method stub
		
		////////////////////////////////

		// canvas

		////////////////////////////////
		final View cv = (View) findViewById(R.id.actv_main_cv_canvas);
		
		cv.setTag(Tags.ViewTags.CANVAS_MAIN);
		
		// onTouch
		cv.setOnTouchListener(new V_OTL(this, (ifm11.views.CV) cv));
//		cv.setOnTouchListener(new V_OTL(this, (CV) cv));

		// onClick
		cv.setOnClickListener(new V_OCL(this, (ifm11.views.CV) cv));
		
		////////////////////////////////

		// button: go

		////////////////////////////////
		Button bt_Go = (Button) findViewById(R.id.actv_main_cv_bt_go);
		
		bt_Go.setTag(Tags.ButtonTags.ACTV_MAIN_BT_GO);
		
		bt_Go.setOnTouchListener(new BO_TL(this));
		bt_Go.setOnClickListener(new BO_CL(this));
		
		////////////////////////////////
		
		// button: clear
		
		////////////////////////////////
		Button bt_Clear = (Button) findViewById(R.id.actv_main_cv_bt_clear);
		
		bt_Clear.setTag(ButtonTags.ACTV_MAIN_BT_CLEAR);
		
		bt_Clear.setOnTouchListener(new BO_TL(this));
		bt_Clear.setOnClickListener(new BO_CL(this));
		
	}//_Setup__SetListener

	private void 
	_Setup() {
		// TODO Auto-generated method stub
		
		////////////////////////////////
		
		// paint
		
		////////////////////////////////
		Paint paint = new Paint();
		paint.setColor(Color.BLUE);
//		paint.setColor(0xFF4444FF);
//		paint.setStyle(Paint.Style.FILL);
		paint.setStrokeWidth(30);
		
		////////////////////////////////
		
		// get: view
		
		////////////////////////////////
		ifm11.views.CV cv = (ifm11.views.CV) findViewById(R.id.actv_main_cv_canvas);
		
	}//_Setup

}
