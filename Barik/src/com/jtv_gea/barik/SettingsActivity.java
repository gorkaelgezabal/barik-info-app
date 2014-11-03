package com.jtv_gea.barik;

import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.RadioButton;

public class SettingsActivity extends BaseActivity {
	
	private static Integer ACTIVITY_INDEX = 1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
		super.setCurrentActivityIndex(ACTIVITY_INDEX);
		super.createNavigationDrawer();
		ListView  mDrawerList = (ListView) findViewById(R.id.left_drawer);
		mDrawerList.setItemChecked(ACTIVITY_INDEX, true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void onRadioButtonClicked(View view) {
	    // Is the button now checked?
	    boolean checked = ((RadioButton) view).isChecked();
	    SharedPreferences prefs = this.getSharedPreferences("com.jtv_gea.barik", Context.MODE_PRIVATE);
	    
	    // Check which radio button was clicked
	    switch(view.getId()) {
	        case R.id.radio_euskera:
	            if (checked){
	            	
	            	 prefs.edit().putString("locale", "eu_ES");
	            	 Locale locale = new Locale("eu_ES");
	            	 Locale.setDefault(locale);
	            	 Configuration config = new Configuration();
	            	 config.locale = locale;
//	            	 view.getContext().getResources().updateConfiguration(config, null);
	            	 getBaseContext().getResources().updateConfiguration(config, 
	            		      getBaseContext().getResources().getDisplayMetrics());
	            }
	            	
	            break;
	        case R.id.radio_espaniol:
	            if (checked){
	            	
	            	 prefs.edit().putString("locale", "es_ES");
	            	 Locale locale = new Locale("es_ES");
	            	 Locale.setDefault(locale);
	            	 Configuration config = new Configuration();
	            	 config.locale = locale;
//	            	 view.getContext().getResources().updateConfiguration(config, null);
	            	 getBaseContext().getResources().updateConfiguration(config, 
	            		      getBaseContext().getResources().getDisplayMetrics());
	            }
	            	
	            break;
	    }
	}
}
