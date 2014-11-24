package com.jtv_gea.barik;



import com.jtv_gea.barik.interaccion.InteraccionWeb;
import com.jtv_gea.barik.interaccion.JavaScriptInterface;
import com.jtv_gea.barik.modelo.BarikUser;
import com.jtv_gea.barik.modelo.Persistencia;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class SaldoActivity extends BaseActivity {

	private static String ACTIVA = "Activa";
	private static String PERSONALIZADA = "Personalizada";
	private static Integer ACTIVITY_INDEX = 0;
	private static String LOGIN_PAGE = "https://barikweb.cotrabi.com/sagb/faces/Login.jspx";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		final ProgressBar progressBar = new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal);
		progressBar.setId(R.id.progressBar);
		progressBar.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 34));

		final FrameLayout decorView = (FrameLayout) getWindow().getDecorView();
		decorView.addView(progressBar);

		// Se añade la progress bar
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
		
		
		super.setCurrentActivityIndex(ACTIVITY_INDEX);
		super.createNavigationDrawer();
		ListView  mDrawerList = (ListView) findViewById(R.id.left_drawer);
		mDrawerList.setItemChecked(ACTIVITY_INDEX, true);
        
		//Mostrar en la pantalla el ultimo dato de saldo etc. guardado
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
        getActionBar().setTitle(R.string.title_activity_saldo);
        
        //Se hace la llamada para actualizar el saldo
      	this.getSaldo();
	}

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
    	
        return super.onPrepareOptionsMenu(menu);
    }
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		getMenuInflater().inflate(R.menu.saldo, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
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
		
		/* Se habilita JavaScript*/
		browser.getSettings().setJavaScriptEnabled(true);
		browser.setWebChromeClient(new WebChromeClient());
		browser.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
		browser.getSettings().setSaveFormData(false);
		browser.getSettings().setLoadsImagesAutomatically(false);
		/* Register nueva interfaz JavaScript ,HTMLOUT */
		browser.addJavascriptInterface(new JavaScriptInterface(this, new Handler()), "HTMLOUT");

		browser.setWebViewClient(new InteraccionWeb());
		browser.loadUrl(LOGIN_PAGE);
	}
	@Override
	protected void onPause() {
		super.onPause();
		
		//destruir WebView para evitar que se ejecute el javascript varias veces a la vez
		WebView browser = (WebView)findViewById(R.id.browser);
		ViewGroup webViewPlaceholder = (ViewGroup) getWindow().getDecorView().findViewById(android.R.id.content);
		 webViewPlaceholder.removeView(browser);
		browser.removeAllViews(); browser.destroy();
		browser.destroy();
	}
}
