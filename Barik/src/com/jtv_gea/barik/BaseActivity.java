package com.jtv_gea.barik;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
import android.widget.ListView;

import com.jtv_gea.barik.modelo.BarikUser;
import com.jtv_gea.barik.modelo.DrawerItem;
import com.jtv_gea.barik.modelo.Persistencia;

public class BaseActivity extends Activity {

	private DrawerLayout mDrawerLayout;
	private String[] menuTitles;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private Integer currentActivityIndex;
	
	/*Iconos del navigation drawer*/
	public static final Integer[] images = { R.drawable.ic_credit_card_white_48dp,
        R.drawable.ic_language_white_48dp, R.drawable.ic_help_white_48dp, R.drawable.ic_exit_to_app_white_48dp };
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//Idioma
        SharedPreferences prefs = this.getSharedPreferences("com.jtv_gea.barik", Context.MODE_PRIVATE);
        
        String languageToLoad  = prefs.getString("locale", "nope");
        Locale locale = new Locale(languageToLoad); 
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, 
        getBaseContext().getResources().getDisplayMetrics());
	}
	
	public void createNavigationDrawer(){

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		menuTitles = getResources().getStringArray(R.array.menu_array);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		
		 List<DrawerItem> rowItems = new ArrayList<DrawerItem>();
        for (int i = 0; i < menuTitles.length; i++) {
        	DrawerItem item = new DrawerItem( images[i], menuTitles[i]);
            rowItems.add(item);
        }
		CustomListViewAdapter adapter = new CustomListViewAdapter(this,
                R.layout.drawer_list_item, rowItems);
		
		mDrawerList.setAdapter(adapter);
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
		
		mDrawerToggle = new ActionBarDrawerToggle(
                this,                  
                mDrawerLayout,         
                R.drawable.ic_drawer,  
                R.string.drawer_open,  
                R.string.drawer_close  
                ) {

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
	}
	
	private class DrawerItemClickListener implements ListView.OnItemClickListener {
	    @Override
	    public void onItemClick(AdapterView parent, View view, int position, long id) {
	    	
	    	Intent intent;
	    	mDrawerLayout.closeDrawer(mDrawerList);   
	    	if(currentActivityIndex != position){
	    		switch (position) {
				case 0:/*saldo*/
					intent = new Intent(view.getContext(), SaldoActivity.class);
					view.getContext().startActivity(intent);
					break;
				case 1:/*Idioma*/
					intent = new Intent(view.getContext(), SettingsActivity.class);
					view.getContext().startActivity(intent);
					break;
				case 2:/*Informacion*/
					intent = new Intent(view.getContext(), InfoActivity.class);
					view.getContext().startActivity(intent);
					break;
				case 3:/*Salir*/
					
					/*Primero se borra el usuario*/
					Persistencia persistencia = new Persistencia(view.getContext());
			        BarikUser barikUser = new BarikUser("", "");
			        persistencia.saveUser(barikUser);
			        
			        /*Se borra el historial de navegacion, y se manda a la pantalla de login*/
			        intent = new Intent(view.getContext(), MainActivity.class);
			        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
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
        mDrawerToggle.syncState();
    }
 
 @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.base, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		
        if (mDrawerToggle.onOptionsItemSelected(item)) {
          return true;
        }
		return super.onOptionsItemSelected(item);
	}

	public Integer getCurrentActivityIndex() {
		return currentActivityIndex;
	}

	public void setCurrentActivityIndex(Integer currentActivityIndex) {
		this.currentActivityIndex = currentActivityIndex;
	}
}
