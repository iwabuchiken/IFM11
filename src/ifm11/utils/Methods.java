package ifm11.utils;



import ifm11.listener.dialog.DL;
import ifm11.main.R;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.SocketException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.os.AsyncTask;
import android.os.Looper;




// Apache
import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTPClient;

// REF=> http://commons.apache.org/net/download_net.cgi
//REF=> http://www.searchman.info/tips/2640.html

//import org.apache.commons.net.ftp.FTPReply;

public class Methods {
	
	public static void confirm_quit(Activity actv, int keyCode) {
		
		if (keyCode==KeyEvent.KEYCODE_BACK) {
			
			AlertDialog.Builder dialog=new AlertDialog.Builder(actv);
			
	        dialog.setTitle(actv.getString(R.string.generic_tv_confirm));
	        dialog.setMessage(actv.getString(R.string.generic_tv_quit_app));
	        
	        dialog.setPositiveButton(
	        				actv.getString(R.string.generic_bt_ok),
	        				new DL(actv, dialog, 0));
	        
	        dialog.setNegativeButton(
	        				actv.getString(R.string.generic_bt_cancel),
	        				new DL(actv, dialog, 1));
	        
	        dialog.create();
	        dialog.show();
			
		}//if (keyCode==KeyEvent.KEYCODE_BACK)
		
	}//public static void confirm_quit(Activity actv, int keyCode)


}//public class Methods

