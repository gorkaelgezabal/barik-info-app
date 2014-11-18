package com.jtv_gea.barik.interaccion;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.jtv_gea.barik.MainActivity;
import com.jtv_gea.barik.ProgressBarController;
import com.jtv_gea.barik.R;
import com.jtv_gea.barik.SaldoActivity;
import com.jtv_gea.barik.modelo.BarikUser;
import com.jtv_gea.barik.modelo.Persistencia;

/** En esta clase se implementa toda la interaccion que se haria de forma manual en la web.
 * En primer lugar se accede a la pagina de login. Ya en esta pagina se recogen el usuario y el password proporcionados por el usuario
 * y se injectan en el formulario de login mediante Javascript. Dependiendo de la pagina a la que se nos redirige, se comprueba si 
 * el usuario y el password son correctos o no. En el caso de que sean correctos, se hace click en la pestaña de saldo, se recoge la informacion
 * y por ultima se pasa la informacion recogida la interfaz Javascript la cual es la que se encarga de guardar los datos en persistencia */

public class InteraccionWeb extends WebViewClient {
	private static final String URL_PASO0 = "https://barikweb.cotrabi.com/sagb/faces/Login.jspx"; // equals
	private static final String URL_PASO1 = "https://barikweb.cotrabi.com/sagb/faces/Login.jspx?"; // contains
	private static final String URL_PASO2 = "https://barikweb.cotrabi.com/sagb/faces/page/mainUsuarioTarjeta.jspx"; // equals
	private static final String URL_PASO3 = "https://barikweb.cotrabi.com/sagb/faces/page/mainUsuarioTarjeta.jspx?"; // equals
	
	private static final String PARAM_1 = "_adf.ctrl-state";
	private static final String PARAM_2 = "_afrWindowMode";
	private static final String PARAM_3 = "_afrLoop";

	boolean entrar = false;
	///codigo javascript
	private static final String JAVASCRIPT_TABSALDO = "javascript: (function(){ "
			+"element=document.getElementById('pt1:j_id_id14:1:j_id_id23');"
			+"console.log('entra funcion click');"
			+"if(element.click){"
			+"    element.click();"
			+"    console.log('click');}"
			+" else if(document.createEvent)"
		    +"{"
		    +"    var eventObj = document.createEvent('MouseEvents');"
		    +"    console.log('crear evento');"
		    +"    eventObj.initEvent('click',true,true);"
		    +"    element.dispatchEvent(eventObj);}"
	+ "})();";

	/*Dependiendo de cada navegador puede que la funcion click() no este implementetada. En esos casos hay que crearlo manualmente.*/
//private static final String JAVASCRIPT_TABSALDO = "javascript: ("
//	+"function(){"
//	+"var clickTab = function(cont){"
//			+"try{"
//			//+ "document.getElementById('pt1:j_id_id14:1:j_id_id23').click(); "
//			+"element=document.getElementById('pt1:j_id_id14:1:j_id_id23');"
//			+"console.log('entra funcion click');"
//			+"if(element.click){"
//			+"    element.click();"
//			+"    console.log('click');}"
//			+" else if(document.createEvent)"
//		    +"{"
//		    +"    var eventObj = document.createEvent('MouseEvents');"
//		    +"    console.log('crear evento');"
//		    +"    eventObj.initEvent('click',true,true);"
//		    +"    element.dispatchEvent(eventObj);"
//		    +"}"
//				+"}catch (e) {"
//				+"if(cont < 6){"
//					+"console.log(e.message);"
//					
//					+"setTimeout(function(){clickTab(cont+1)}, 100);"
//					+"console.log('catch de click'+cont);"
//					+"}"
//					+"}"
//					+"};"
//		+"clickTab(0);"
//		+"}"
//+"());";

	
	/*Javascript que recoge los valores. Puede pasar  que los datos no esten listos. En ese caso se vuelve a lanzar el script despues de un tiempo
	 * varias veces hasta que se encuentran los valores o se llega al limite de intentos.*/
	private static final String JAVASCRIPT_DATOSUSUARIO = "javascript: ("
			+"function(){"
			+"var getInfo = function(cont){"
					+"try{"
						+"var saldo =document.getElementById('pt1:j_id_id36:subform:j_id_id23pc2::db').getElementsByClassName('xxf')[1].getElementsByTagName('nobr')[0].innerHTML;"
						+"var caducidad = document.getElementById('pt1:j_id_id36:subform:j_id_id8pc2::content').innerHTML;"
						+"var nCliente = document.getElementById('pt1:j_id_id36:subform:j_id_id10pc2::content').innerHTML;"
						+"var situacion = document.getElementById('pt1:j_id_id36:subform:j_id_id12pc2::content').innerHTML;"
						+"var tipo = document.getElementById('pt1:j_id_id36:subform:j_id_id9pc2::content').innerHTML;"
						+"var nTarjeta = document.getElementById('pt1:j_id_id36:subform:j_id_id6pc2::content').innerHTML;"
						+"window.HTMLOUT.processHTML(saldo, caducidad, nCliente, situacion, tipo, nTarjeta);"
						+"}catch (e) {"
						+"if(cont < 250){"
							+"setTimeout(function(){getInfo(cont+1)}, 100);"
							+"console.log('entra'+cont)"
							+"}else{window.HTMLOUT.processHTML(null, null, null, null, null, null);}"
							+"}"
							+"};"
				+"getInfo(0);"
				+"}"
		+"());";
	

	

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
				
				//Se abre la pantalla de login
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
			entrar = true;
			// abrir la pestaña del saldo
			view.loadUrl(JAVASCRIPT_TABSALDO);
			Handler mHandler = new Handler();
			mHandler.post(new ProgressBarController((SaldoActivity) view
					.getContext(), 77));
		}
	}

	@Override
	public void onLoadResource(WebView view, String url) {
		if(entrar){
			//if (url.equals("https://barikweb.cotrabi.com/favicon.ico")) {
			if (url.contains(this.URL_PASO3+this.PARAM_1)) {
				Log.i(this.getClass().getName(), "entrando datos");
				view.loadUrl(JAVASCRIPT_DATOSUSUARIO);
			}
		}
		

	}
	
	
}
