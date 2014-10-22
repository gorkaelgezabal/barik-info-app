package com.jtv_gea.barik.interaccion;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import android.widget.ImageView;



import com.jtv_gea.barik.MainActivity;
import com.jtv_gea.barik.ProgressBarController;
import com.jtv_gea.barik.R;
import com.jtv_gea.barik.SaldoActivity;
import com.jtv_gea.barik.modelo.BarikUser;
import com.jtv_gea.barik.modelo.Persistencia;

public class InteraccionWeb extends WebViewClient {
	private static final String URL_PASO0 = "https://barikweb.cotrabi.com/sagb/faces/Login.jspx"; // equals
	private static final String URL_PASO1 = "https://barikweb.cotrabi.com/sagb/faces/Login.jspx?"; // contains
	private static final String URL_PASO2 = "https://barikweb.cotrabi.com/sagb/faces/page/mainUsuarioTarjeta.jspx"; // equals
	private static final String URL_PASO3 = "https://barikweb.cotrabi.com/sagb/faces/page/mainUsuarioTarjeta.jspx?"; // equals
	
	private static final String PARAM_1 = "_adf.ctrl-state";
	private static final String PARAM_2 = "_afrWindowMode";
	private static final String PARAM_3 = "_afrLoop";

	//codigo javascript
	private static final String JAVASCRIPT_TABSALDO = "javascript: (function(){ "
			+ "document.getElementById('pt1:j_id_id14:1:j_id_id23').click(); "
			+ "})();";
	
	//private static final String JAVASCRIPT_DATOSUSUARIO = "javascript: (function(){try{window.HTMLOUT.processHTML(document.getElementById('pt1:j_id_id36:subform:j_id_id23pc2::db').getElementsByClassName('xxf')[1].getElementsByTagName('nobr')[0].innerHTML);}catch (e) {}})();";
	
	private static final String JAVASCRIPT_DATOSUSUARIO = "javascript: ("
			+ "function(){"
			+ "try{"
			+ "var saldo =document.getElementById('pt1:j_id_id36:subform:j_id_id23pc2::db').getElementsByClassName('xxf')[1].getElementsByTagName('nobr')[0].innerHTML;"
			+ "var caducidad = document.getElementById('pt1:j_id_id36:subform:j_id_id8pc2::content').innerHTML;"
			+ "var nCliente = document.getElementById('pt1:j_id_id36:subform:j_id_id10pc2::content').innerHTML;"
			+ "var situacion = document.getElementById('pt1:j_id_id36:subform:j_id_id12pc2::content').innerHTML;"
			+ "var tipo = document.getElementById('pt1:j_id_id36:subform:j_id_id9pc2::content').innerHTML;"
			+ "var nTarjeta = document.getElementById('pt1:j_id_id36:subform:j_id_id6pc2::content').innerHTML;"
			+ "window.HTMLOUT.processHTML(saldo, caducidad, nCliente, situacion, tipo, nTarjeta);"
			+ "}catch (e) {}"
			+ "}"
			+ ")();";
	

	@Override
	public void onPageFinished(WebView view, String url) {
		if (url.equals(URL_PASO0)) {
			Log.i(this.getClass().getName(), "paso 0");
			Handler mHandler = new Handler();
			mHandler.post(new ProgressBarController((SaldoActivity) view
					.getContext(), 15));
		} else if (url.contains(URL_PASO1)) {// Login
			Log.i(this.getClass().getName(), "pagina de login...");
			boolean loginError;
			String[] urlSplited = url.split("\\?");
			String params = urlSplited[1];
			
			loginError = params.contains(PARAM_1) && !params.contains(PARAM_2)&&!params.contains(PARAM_3);
			
			if(loginError){
				Log.i(this.getClass().getName(), "error de logeo");

				//Se borra el usuario
				Persistencia persistencia = new Persistencia(view.getContext());
				BarikUser user = new BarikUser("", "");
				persistencia.saveUser(user);
				
				Toast.makeText(view.getContext(),view.getContext().getResources().getString(R.string.text_user_pass_incorrectos),
						   Toast.LENGTH_LONG).show();
				
				//Se abre la pantalla principal
				Intent intent = new Intent(view.getContext(), MainActivity.class);
				view.getContext().startActivity(intent);
				
			}
			else{
				Log.i(this.getClass().getName(), "logeando");
				Persistencia persistencia = new Persistencia(view.getContext());
				BarikUser user = persistencia.loadUser();
				String javaScript = "(function(){ "
						+ "document.getElementById('username::content').value = '"
						+ user.getUsername() + "'; "
						+ "document.getElementById('it1::content').value = '"
						+ user.getPassword() + "'; "
						+ "document.getElementById('enter').click(); " + "})()";
				view.loadUrl("javascript: " + javaScript);
				Handler mHandler = new Handler();
				mHandler.post(new ProgressBarController((SaldoActivity) view
						.getContext(), 33));
			}

		} else if (url.equals(URL_PASO2)) {
			// transicion, unicamente actualizar progress bar
			Log.i(this.getClass().getName(), "paso 2, llamada sin nada");
			Handler mHandler = new Handler();
			mHandler.post(new ProgressBarController((SaldoActivity) view
					.getContext(), 60));
		} else if (url.contains(URL_PASO3)) {
			Log.i(this.getClass().getName(), "abriendo tab de saldo");
			// abrir la pestaña del saldo
			view.loadUrl(JAVASCRIPT_TABSALDO);
			Handler mHandler = new Handler();
			mHandler.post(new ProgressBarController((SaldoActivity) view
					.getContext(), 77));

		}

	}

	@Override
	public void onLoadResource(WebView view, String url) {
		if (url.equals("https://barikweb.cotrabi.com/sagb/afr/info.png")) {
			
			view.loadUrl(JAVASCRIPT_DATOSUSUARIO);
		}

	}
	
	
}
