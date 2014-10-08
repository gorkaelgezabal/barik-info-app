package com.jtv_gea.barik;


import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class SaldoActivity extends ActionBarActivity {

	Integer cont = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_saldo);
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
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void login(View view){
		WebView browser = (WebView)findViewById(R.id.browser);
		
		/* JavaScript must be enabled if you want it to work, obviously */
		browser.getSettings().setJavaScriptEnabled(true);
		browser.setWebChromeClient(new WebChromeClient());
		browser.getSettings().setDomStorageEnabled(true);
		browser.getSettings().setSaveFormData(false);
		/* Register a new JavaScript interface called HTMLOUT */
		browser.addJavascriptInterface(new MyJavaScriptInterface(), "HTMLOUT");

		/* WebViewClient must be set BEFORE calling loadUrl! */
		browser.setWebViewClient(new WebViewClient() {
		    @Override
		    public void onPageFinished(WebView view, String url)
		    {
		        /* This call inject JavaScript into the page which just finished loading. */
		        
		        
//		        	browser.loadUrl("javascript:window.HTMLOUT.processHTML('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');");

		    	if(cont == 1){//Login
		    		String javaScript = "(function(){ " +
		    		        "document.getElementById('username::content').value = 'gorkaelgezabal@gmail.com'; " +
		    		        "document.getElementById('it1::content').value = '3kIwOwA7'; " +
		    		        "document.getElementById('enter').click(); " +
		    		        "})()";
//					view.loadUrl("javascript:document.getElementById(\"username::content\").innerHTML = \"gorka\";");
		    		view.loadUrl("javascript: "+javaScript);

		    	}
		    	else if (cont ==3){//Pagina principal
		    		//pt1:j_id_id14:1:j_id_id23
		    		//String jqueryLoad ="if(!(window.jQuery && window.jQuery.fn.jquery == '1.3.2')) {var s = document.createElement('script');s.setAttribute('src', 'http://ajax.googleapis.com/ajax/libs/jquery/1.3.2/jquery.min.js');s.setAttribute('type', 'text/javascript');document.getElementsByTagName('head')[0].appendChild(s);}";
		    		String javaScript = "(function(){ " +
		    		        "document.getElementById('pt1:j_id_id14:1:j_id_id23').click(); " +
		    		        "})();";
		    		//String javaScript2 ="javascript:$(document).ajaxComplete(function (event, request, settings) {window.HTMLOUT.processHTML(document.getElementById('pt1:j_id_id36:subform:j_id_id23pc2::db').getElementsByClassName('xxf')[1].innerHTML);});";
//					view.loadUrl("javascript:document.getElementById(\"username::content\").innerHTML = \"gorka\";");
		    		view.loadUrl("javascript: "+javaScript);
		    		
		    		
		    	}
		    	else if(cont == 4){//Titulos validos
		    		
		    		//Aqui no entra porque creo que al hacer click en titulos validos hace una llamada ajax, y por eso no salta OnPageFinished
		    		view.loadUrl("javascript:window.HTMLOUT.processHTML(document.getElementById('pt1:j_id_id36:subform:j_id_id23pc2::db').getElementsByClassName('xxf')[1].innerHTML);");
		    	}
		    	cont++;	
		    	
		    }
		    @Override
		    public void onLoadResource(WebView view, String url)
		    {
		    	if (url.equals("https://barikweb.cotrabi.com/sagb/afr/info.png")){
		    		view.loadUrl("javascript:window.HTMLOUT.processHTML(document.getElementById('pt1:j_id_id36:subform:j_id_id23pc2::db').getElementsByClassName('xxf')[1].getElementsByTagName('nobr')[0].innerHTML);");
		    	}
		    	
		    }
		    
		    
		    
		});
		browser.loadUrl("https://barikweb.cotrabi.com/sagb/faces/Login.jspx");
		
	}

	class MyJavaScriptInterface
	{
	    @JavascriptInterface
	    @SuppressWarnings("unused")
	    public void processHTML(String saldo)
	    {
	        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n!\n!\n!\n!\n!\n!\n!"+saldo);
	    }
	}
}