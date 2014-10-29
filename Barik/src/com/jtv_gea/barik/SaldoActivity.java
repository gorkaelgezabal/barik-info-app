package com.jtv_gea.barik;


import com.jtv_gea.barik.interaccion.InteraccionWeb;
import com.jtv_gea.barik.interaccion.JavaScriptInterface;
import com.jtv_gea.barik.modelo.BarikUser;
import com.jtv_gea.barik.modelo.Persistencia;

import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class SaldoActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// create new ProgressBar and style it
		final ProgressBar progressBar = new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal);
		progressBar.setId(R.id.progressBar);
		progressBar.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 24));
//		progressBar.setProgress(65);

		// retrieve the top view of our application
		final FrameLayout decorView = (FrameLayout) getWindow().getDecorView();
		decorView.addView(progressBar);

		// Here we try to position the ProgressBar to the correct position by looking
		// at the position where content area starts. But during creating time, sizes 
		// of the components are not set yet, so we have to wait until the components
		// has been laid out
		// Also note that doing progressBar.setY(136) will not work, because of different
		// screen densities and different sizes of actionBar
		ViewTreeObserver observer = progressBar.getViewTreeObserver();
		observer.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
		    @Override
		    public void onGlobalLayout() {
		        View contentView = decorView.findViewById(android.R.id.content);
		        progressBar.setY(contentView.getY() - 10);

		        ViewTreeObserver observer = progressBar.getViewTreeObserver();
		        observer.removeOnGlobalLayoutListener(this);
		    }
		});
		
		
		
		setContentView(R.layout.activity_saldo);
		super.createNavigationDrawer();
		
//		!!!!!!!!!!!!!!!!!!!!!!!!!
		ListView  mDrawerList = (ListView) findViewById(R.id.left_drawer);
		mDrawerList.setItemChecked(1, true);
		

        
        
		//Mostrar en la pantalla el ultimo dato de saldo guardado
		Persistencia persistencia= new Persistencia(this.getApplicationContext());
		BarikUser user =persistencia.loadUser();
		TextView saldoText= (TextView) this.findViewById(R.id.resultado_saldo_barik);
        saldoText.setText(user.getSaldo());
        TextView fechaActualizacionText= (TextView) this.findViewById(R.id.resultado_fecha_actualizacion_barik);
        fechaActualizacionText.setText(user.getFechaUltimaActualizacionFormateada());
        TextView fechaCaducidadText= (TextView) this.findViewById(R.id.resultado_fecha_caducidad_barik);
        fechaCaducidadText.setText(user.getCaducidad());
        TextView tipoText= (TextView) this.findViewById(R.id.resultado_tipo_barik);
        tipoText.setText(user.getTipo());
        TextView numClienteText= (TextView) this.findViewById(R.id.resultado_numero_cliente_barik);
        numClienteText.setText(user.getnCliente());
        TextView numSituacionText= (TextView) this.findViewById(R.id.resultado_situacion_barik);
        numSituacionText.setText(user.getSituacion());
        TextView numTarjetaText= (TextView) this.findViewById(R.id.resultado_numero_tarjeta_barik);
        numTarjetaText.setText(user.getnTarjeta());
        
      //actualizar el saldo
      	this.getSaldo();
        
	}

	/* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
//        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
//        menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.saldo, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_reargar) {
			this.getSaldo();
			return true;
		}
		else{
			return super.onOptionsItemSelected(item);
		}
		
		
	}

	@SuppressLint("SetJavaScriptEnabled")
	public void getSaldo(){
		WebView browser = (WebView)findViewById(R.id.browser);
		
		/* JavaScript must be enabled if you want it to work, obviously */
		browser.getSettings().setJavaScriptEnabled(true);
		browser.setWebChromeClient(new WebChromeClient());
		browser.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
//		browser.getSettings().setLoadsImagesAutomatically(false);
		browser.getSettings().setSaveFormData(false);
		/* Register a new JavaScript interface called HTMLOUT */
		browser.addJavascriptInterface(new JavaScriptInterface(this, new Handler()), "HTMLOUT");

		/* WebViewClient must be set BEFORE calling loadUrl! */
		browser.setWebViewClient(new InteraccionWeb());
		browser.loadUrl("https://barikweb.cotrabi.com/sagb/faces/Login.jspx");
		
	}
	
	
	

}
