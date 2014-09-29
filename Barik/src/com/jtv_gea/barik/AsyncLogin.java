package com.jtv_gea.barik;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import android.os.AsyncTask;
import android.util.Log;

public class AsyncLogin extends AsyncTask<String, String , Integer> {

	private String user = "";
	private String pass = "";
	private final String PATH_BARIK = "https://barikweb.cotrabi.com/sagb/faces/Login.jspx";
	private final String USERNAME_FIELD= "username";
	private final String PASSWORD_FIELD= "it1";
	private final String USERNAME= "jtv_30@hotmail.com";
	private final String PASSWORD= "julen300591";
	private final String PESTAÑA_MOVIMIENTOS_REALIZADOS= "pt1:j_id_id14:0:j_id_id23";
	private final String DIV_TABLA= "pt1:j_id_id36:subform:j_id_id32pc2::db";
	
	public AsyncLogin (String user, String pass){
		
		super();
		this.user = user;
		this.pass = pass;
		
	}
	InputStream inputStream = null;
	String result = ""; 

	@Override
	protected Integer doInBackground(String... params) {
		
		
		// Create a new instance of the html unit driver
        // Notice that the remainder of the code relies on the interface, 
        // not the implementation.
        WebDriver driver = new FirefoxDriver();
        try{
        	
        
        // And now use this to visit the web
        driver.get(PATH_BARIK);
        
        String pageSource = driver.getPageSource();
        
        // Find the text input element by its name
        WebElement elementUsername = driver.findElement(By.name(USERNAME_FIELD));
        WebElement elementPassword= driver.findElement(By.name(PASSWORD_FIELD));

        // Enter the data to the input elements
        elementUsername.sendKeys(USERNAME);
        elementPassword.sendKeys(PASSWORD);

        // Now submit the form. 
        //elementUsername.submit();
        WebElement botonFormulario= driver.findElement(By.id("enter"));
        botonFormulario.click();

        //ir a la pestaña de movimientos
        Thread.sleep(2000);
        WebElement link_movimientos= driver.findElement(By.id(PESTAÑA_MOVIMIENTOS_REALIZADOS));
        link_movimientos.click();
        Thread.sleep(2000);
        //obtener saldo de la tabla
        WebElement div_tabla = driver.findElement(By.id(DIV_TABLA));
        WebElement tabla = div_tabla.findElement(By.tagName("table"));
        List<WebElement> fila = tabla.findElement(By.tagName("tr")).findElements(By.tagName("td"));
        WebElement saldo = fila.get(fila.size()-1);
        
        System.out.println("El saldo de la cuenta es: "+saldo.getText());
        }catch(Exception e){
        	e.printStackTrace();
        }

        driver.quit();
		
//		String URL_BASE = "https://barikweb.cotrabi.com/sagb/faces/Login.jspx";
//		
//
//		ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
//
//		try {
//			// Set up HTTP post
//
//			// HttpClient is more then less deprecated. Need to change to URLConnection
//			HttpClient httpClient = new DefaultHttpClient();
//
//			HttpPost httpPost = new HttpPost(URL_BASE);
//			httpPost.setEntity(new UrlEncodedFormEntity(param));
//			HttpResponse httpResponse = httpClient.execute(httpPost);
//			HttpEntity httpEntity = httpResponse.getEntity();
//			inputStream = httpEntity.getContent();
//			
//		} catch (UnsupportedEncodingException e1) {
//			Log.e("UnsupportedEncodingException", e1.toString());
//			e1.printStackTrace();
//		} catch (ClientProtocolException e2) {
//			Log.e("ClientProtocolException", e2.toString());
//			e2.printStackTrace();
//		} catch (IllegalStateException e3) {
//			Log.e("IllegalStateException", e3.toString());
//			e3.printStackTrace();
//		} catch (IOException e4) {
//			Log.e("IOException", e4.toString());
//			e4.printStackTrace();
//		}
//		
//		// Convert response to string using String Builder
//		try {
//			BufferedReader bReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"), 8);
//			StringBuilder sBuilder = new StringBuilder();
//
//			String line = null;
//			while ((line = bReader.readLine()) != null) {
//				sBuilder.append(line + "\n");
//			}
//
//			inputStream.close();
//			result = sBuilder.toString();
//
//			//parse HTML
//
//
//			//return data_value;
//		} catch (Exception e) {
//			Log.e("StringBuilding & BufferedReader", "Error converting result " + e.toString());
//		}
//		
//		try {
//
//		} catch (Exception e) {
//			Log.e("StringBuilding & BufferedReader", "Error converting result " + e.toString());
//		}
		return null;
		
	}

}
