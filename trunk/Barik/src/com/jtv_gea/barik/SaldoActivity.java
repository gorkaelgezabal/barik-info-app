package com.jtv_gea.barik;


import com.jtv_gea.barik.interaccion.HiloEditarSaldo;
import com.jtv_gea.barik.interaccion.InteraccionWeb;
import com.jtv_gea.barik.interaccion.JavaScriptInterface;
import com.jtv_gea.barik.modelo.BarikUser;
import com.jtv_gea.barik.modelo.Persistencia;

import android.support.v7.app.ActionBarActivity;
import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class SaldoActivity extends ActionBarActivity {
	

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

		//Mostrar en la pantalla el ultimo dato de saldo guardado
		Persistencia persistencia= new Persistencia(this.getApplicationContext());
		BarikUser user =persistencia.loadUser();
		TextView saldoText= (TextView) this.findViewById(R.id.text_saldo_barik);
        saldoText.setText(this.getString(R.string.text_saldo_barik)+" "+user.getSaldo());
        
        //actualizar el saldo
		this.getSaldo();
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
		return super.onOptionsItemSelected(item);
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
