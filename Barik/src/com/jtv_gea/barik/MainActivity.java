package com.jtv_gea.barik;

import com.jtv_gea.barik.R;
import com.jtv_gea.barik.modelo.BarikUser;
import com.jtv_gea.barik.modelo.Persistencia;




import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

public class MainActivity extends ActionBarActivity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		Persistencia persistencia= new Persistencia(this.getApplicationContext());
		BarikUser user =persistencia.loadUser();
		if (!user.getUsername().equals("")){
			Intent saldoActivity = new Intent(this, SaldoActivity.class);
			startActivity(saldoActivity);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		return super.onOptionsItemSelected(item);
	}
	
	
	
	public void login(View view){
		EditText et_user = (EditText)findViewById(R.id.et_user);
		EditText et_pass = (EditText)findViewById(R.id.et_pass);
		
		String user = et_user.getText().toString();
		String pass = et_pass.getText().toString();
		
		if(user.matches("")){
			et_user.setError(getResources().getString(R.string.text_user_vacio));
		}
		
		if(pass.matches("")){
			et_pass.setError(getResources().getString(R.string.text_pass_vacio));
		}
		
		if(!user.matches("") && !pass.matches("")){
			Persistencia db = new Persistencia(this);
			BarikUser barikUser = new BarikUser();
			barikUser.setUsername(user);
			barikUser.setPassword(pass);
			db.saveUser(barikUser);
			
			Intent saldoActivity = new Intent(this, SaldoActivity.class);
			startActivity(saldoActivity);

		}
		
	}
}
