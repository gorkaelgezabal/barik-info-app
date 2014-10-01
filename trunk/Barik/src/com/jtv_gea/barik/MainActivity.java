package com.jtv_gea.barik;

import com.jtv_gea.barik.R;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

public class MainActivity extends ActionBarActivity {

	Integer cont = 0;
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
	
	class MyJavaScriptInterface
	{
	    @JavascriptInterface
	    @SuppressWarnings("unused")
	    public void processHTML(String html)
	    {
	    	
	    	if(cont == 1){
	    		final WebView browser = (WebView)findViewById(R.id.browser);
	    		
	    		browser.post(new Runnable() {
	    		    @Override
	    		    public void run() {
	    		    	browser.loadUrl("javascript:getElementById('username').value = 'gorkaelgezabal@gmail.com');");
	    	    		browser.loadUrl("javascript:getElementById('it1').value = 'contrase�a';");
	    		    	browser.loadUrl("javascript:getElementById('enter').click();");
	    		    }
	    		});
	    		
	    	}
	    	cont++;
	        //System.out.println(html);
	    }
	}
	
	
	public void login(View view){
		final WebView browser = (WebView)findViewById(R.id.browser);
		
		/* JavaScript must be enabled if you want it to work, obviously */
		browser.getSettings().setJavaScriptEnabled(true);

		/* Register a new JavaScript interface called HTMLOUT */
		browser.addJavascriptInterface(new MyJavaScriptInterface(), "HTMLOUT");

		/* WebViewClient must be set BEFORE calling loadUrl! */
		browser.setWebViewClient(new WebViewClient() {
		    @Override
		    public void onPageFinished(WebView view, String url)
		    {
		        /* This call inject JavaScript into the page which just finished loading. */
		        browser.loadUrl("javascript:window.HTMLOUT.processHTML('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');");
		    }
		});
		browser.loadUrl("https://barikweb.cotrabi.com/sagb/faces/Login.jspx");
	}
}
