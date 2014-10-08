package com.jtv_gea.barik;


import android.os.AsyncTask;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class AsyncDownload extends AsyncTask<String, String, Integer> {
	
	Integer cont = 0;
	SaldoActivity activity;
	
	public AsyncDownload (SaldoActivity saldoActivity){
		this.activity = saldoActivity;
	}
	@Override
	protected Integer doInBackground(String... params) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void getSaldo(){
		WebView browser = (WebView)activity.findViewById(R.id.browser);
		
		/* JavaScript must be enabled if you want it to work, obviously */
		browser.getSettings().setJavaScriptEnabled(true);
		browser.setWebChromeClient(new WebChromeClient());
		browser.getSettings().setDomStorageEnabled(true);
		browser.getSettings().setSaveFormData(false);
		/* Register a new JavaScript interface called HTMLOUT */
		browser.addJavascriptInterface(new MyJavaScriptInterface(activity), "HTMLOUT");

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
		private SaldoActivity saldoActivity;
	    public MyJavaScriptInterface(SaldoActivity saldoActivity) {
			this.saldoActivity=saldoActivity;
		}

		@JavascriptInterface
	    @SuppressWarnings("unused")
	    public void processHTML(String saldo)
	    {
	        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n!\n!\n!\n!\n!\n!\n!"+saldo);
	        TextView saldoText= (TextView) this.saldoActivity.findViewById(R.id.text_saldo);
	        saldoText.setText(saldoText.getText()+saldo);	        
	    }
	}


}