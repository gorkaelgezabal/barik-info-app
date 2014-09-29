package com.jtv_gea.barik;

import java.util.concurrent.ExecutionException;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {
	
	public static final String BALANCE_MESSAGE = "com.jtv_gea.barik.USER";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
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
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void login(View view){
		Intent intent = new Intent(this, SaldoActualActivity.class);
		EditText editTextEmail = (EditText) findViewById(R.id.et_user);
		EditText editTextPassword = (EditText) findViewById(R.id.et_pass);
		
		String email = editTextEmail.getText().toString();
		String password = editTextPassword.getText().toString();
		
		//GET pagina de login
		//resultado: recoger parametros necesarios
		//POST con los parametros recogidos y email y pass
		//resultado: recoger saldo
	
		AsyncLogin asyncLogin= new AsyncLogin(email, password);
		asyncLogin.execute();
		Integer balance;
		try {
			balance = asyncLogin.get();
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//crear nuevo intent con el saldo
		intent.putExtra(BALANCE_MESSAGE, email);
		startActivity(intent);
		
		
		
	}
}
