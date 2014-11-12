package com.jtv_gea.barik;

import java.util.Locale;

import com.jtv_gea.barik.R;
import com.jtv_gea.barik.modelo.BarikUser;
import com.jtv_gea.barik.modelo.Persistencia;




import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
		setContentView(R.layout.activity_main);
		Persistencia persistencia= new Persistencia(this.getApplicationContext());
		BarikUser user =persistencia.loadUser();
		
		//Se comprueba si el usuario se a logeado anteriormente.
		//Si es asi se abre directamente la pantalla de saldo.
		if (!user.getUsername().equals("")){
			Intent saldoActivity = new Intent(this, SaldoActivity.class);
			startActivity(saldoActivity);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		return super.onOptionsItemSelected(item);
	}
	
	public void login(View view){
		
		EditText et_user = (EditText)findViewById(R.id.et_user);
		EditText et_pass = (EditText)findViewById(R.id.et_pass);
		
		String user = et_user.getText().toString();
		String pass = et_pass.getText().toString();
		
		//Validaciones usuario y password
		if(user.matches("")){
			et_user.setError(getResources().getString(R.string.text_user_vacio));
		}
		
		if(pass.matches("")){
			et_pass.setError(getResources().getString(R.string.text_pass_vacio));
		}
		
		if(!user.matches("") && !pass.matches("")){
			//Se guarda el usuario
			Persistencia db = new Persistencia(this);
			BarikUser barikUser = new BarikUser();
			barikUser.setUsername(user);
			barikUser.setPassword(pass);
			db.saveUser(barikUser);
			
			//Se guarda el idioma
			SharedPreferences prefs = this.getSharedPreferences("com.jtv_gea.barik", Context.MODE_PRIVATE);
			Locale current = getResources().getConfiguration().locale;
			String locale = current.toString();
			
			SharedPreferences.Editor editor= prefs.edit();
			editor.putString("locale",locale );
			editor.commit();
			
			Intent saldoActivity = new Intent(this, SaldoActivity.class);
			startActivity(saldoActivity);

		}
	}
}
