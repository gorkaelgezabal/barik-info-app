package com.jtv_gea.barik.interaccion;

import android.os.Handler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jtv_gea.barik.ProgressBarController;
import com.jtv_gea.barik.SaldoActivity;
import com.jtv_gea.barik.modelo.BarikUser;
import com.jtv_gea.barik.modelo.Persistencia;

public class InteraccionWeb extends WebViewClient {
	private static final String URL_PASO0 = "https://barikweb.cotrabi.com/sagb/faces/Login.jspx"; // equals
	private static final String URL_PASO1 = "https://barikweb.cotrabi.com/sagb/faces/Login.jspx?"; // contains
	private static final String URL_PASO2 = "https://barikweb.cotrabi.com/sagb/faces/page/mainUsuarioTarjeta.jspx"; // equals
	private static final String URL_PASO3 = "https://barikweb.cotrabi.com/sagb/faces/page/mainUsuarioTarjeta.jspx?"; // equals

	//codigo javascript
	private static final String JAVASCRIPT_TABSALDO = "javascript: (function(){ "
			+ "document.getElementById('pt1:j_id_id14:1:j_id_id23').click(); "
			+ "})();";
	
	private static final String JAVASCRIPT_OBTENERSALDO = "javascript: (function(){try{window.HTMLOUT.processHTML(document.getElementById('pt1:j_id_id36:subform:j_id_id23pc2::db').getElementsByClassName('xxf')[1].getElementsByTagName('nobr')[0].innerHTML);}catch (e) {}})();";
	

	@Override
	public void onPageFinished(WebView view, String url) {
		if (url.equals(URL_PASO0)) {
			Handler mHandler = new Handler();
			mHandler.post(new ProgressBarController((SaldoActivity) view
					.getContext(), 15));
		} else if (url.contains(URL_PASO1)) {// Login
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

		} else if (url.equals(URL_PASO2)) {
			// transicion, unicamente actualizar progress bar
			Handler mHandler = new Handler();
			mHandler.post(new ProgressBarController((SaldoActivity) view
					.getContext(), 60));
		} else if (url.contains(URL_PASO3)) {
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
			
			view.loadUrl(JAVASCRIPT_OBTENERSALDO);
		}

	}

}
