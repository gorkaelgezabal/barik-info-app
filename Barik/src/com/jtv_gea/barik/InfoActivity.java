package com.jtv_gea.barik;

import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class InfoActivity extends BaseActivity {
	private static Integer ACTIVITY_INDEX = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info);
		
		super.setCurrentActivityIndex(ACTIVITY_INDEX);
		super.createNavigationDrawer();
		ListView  mDrawerList = (ListView) findViewById(R.id.left_drawer);
		mDrawerList.setItemChecked(ACTIVITY_INDEX, true);
	}
	
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.info, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}else{
			return super.onOptionsItemSelected(item);
		}
	}
}
