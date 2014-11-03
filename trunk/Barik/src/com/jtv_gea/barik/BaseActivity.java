package com.jtv_gea.barik;

import java.util.Locale;

import com.jtv_gea.barik.modelo.BarikUser;
import com.jtv_gea.barik.modelo.Persistencia;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class BaseActivity extends Activity {

	private DrawerLayout mDrawerLayout;
	private String[] menuTitles;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	private Integer currentActivityIndex;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	public void createNavigationDrawer(){
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		menuTitles = getResources().getStringArray(R.array.menu_array);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		
		mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, menuTitles));
		
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
		
		mDrawerToggle = new ActionBarDrawerToggle(
                this,                  
                mDrawerLayout,         
                R.drawable.ic_drawer,  
                R.string.drawer_open,  
                R.string.drawer_close  
                ) {

//             Called when a drawer has settled in a completely closed state. 
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getActionBar().setTitle(mTitle);
            }

//             Called when a drawer has settled in a completely open state. 
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActionBar().setTitle(mDrawerTitle);
            }
        };
        
        

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        
        SharedPreferences prefs = this.getSharedPreferences("com.jtv_gea.barik", Context.MODE_PRIVATE);
        
        String languageToLoad  = prefs.getString("locale", "es_ES");
        Locale locale = new Locale(languageToLoad); 
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, 
        getBaseContext().getResources().getDisplayMetrics());
	}
	private class DrawerItemClickListener implements ListView.OnItemClickListener {
	    @Override
	    public void onItemClick(AdapterView parent, View view, int position, long id) {
	    	
	    	Intent intent;
	    	
	    	if(currentActivityIndex != position){
	    		switch (position) {
				case 0:
					intent = new Intent(view.getContext(), SaldoActivity.class);
					view.getContext().startActivity(intent);
					break;
				case 1:
					intent = new Intent(view.getContext(), SettingsActivity.class);
					view.getContext().startActivity(intent);
					break;
				case 2:
					intent = new Intent(view.getContext(), InfoActivity.class);
					view.getContext().startActivity(intent);
					break;
				case 3:
					Persistencia persistencia = new Persistencia(view.getContext());
			        BarikUser barikUser = new BarikUser("", "");
			        persistencia.saveUser(barikUser);
			        
			        intent = new Intent(view.getContext(), MainActivity.class);
					view.getContext().startActivity(intent);
				default:
					break;
				}
	    	}
	    	
	        
	        
	    }
	}
	
	@Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }
 
 @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.base, menu);
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
		
		// Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
          return true;
        }
        // Handle your other action bar items...
		return super.onOptionsItemSelected(item);
	}

	public Integer getCurrentActivityIndex() {
		return currentActivityIndex;
	}

	public void setCurrentActivityIndex(Integer currentActivityIndex) {
		this.currentActivityIndex = currentActivityIndex;
	}
	
	
}
