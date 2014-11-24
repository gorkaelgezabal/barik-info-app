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
		
		SharedPreferences prefs = this.getSharedPreferences("com.jtv_gea.barik", Context.MODE_PRIVATE);
		String locale = prefs.getString("locale", "es_ES");
		RadioButton rb;
		
		if (locale.equals("es_ES")){
			 rb = (RadioButton) findViewById(R.id.radio_espaniol);
			
		}else{
			rb = (RadioButton) findViewById(R.id.radio_euskera);
		}
		rb.setChecked(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	public void onRadioButtonClicked(View view) {
	    boolean checked = ((RadioButton) view).isChecked();
	    SharedPreferences prefs = this.getSharedPreferences("com.jtv_gea.barik", Context.MODE_PRIVATE);
	    Locale locale = new Locale("es_ES");
	    SharedPreferences.Editor editor= prefs.edit();
	    
	    switch(view.getId()) {
	        case R.id.radio_euskera:
	            if (checked){
	            	editor.putString("locale", "eu_ES");
	            	 locale = new Locale("eu_ES");
	            }
	            	
	            break;
	        case R.id.radio_espaniol:
	            if (checked){
	            	editor.putString("locale", "es_ES");
	            	 locale = new Locale("es_ES");
	            }
	            	
	            break;
	    }
	    
	   	 editor.commit();
		 Locale.setDefault(locale);
		 Configuration config = new Configuration();
		 config.locale = locale;
		 getBaseContext().getResources().updateConfiguration(config, 
			      getBaseContext().getResources().getDisplayMetrics());
		 
		 super.createNavigationDrawer();
	}
}
