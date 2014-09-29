package com.jtv_gea.barik;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class WebInteractor {
	private final String PATH_BARIK = "https://barikweb.cotrabi.com/sagb/faces/Login.jspx";
	private final String USERNAME_FIELD= "username";
	private final String PASSWORD_FIELD= "it1";
	private final String USERNAME= "jtv_30@hotmail.com";
	private final String PASSWORD= "julen300591";
	private final String PESTAÑA_MOVIMIENTOS_REALIZADOS= "pt1:j_id_id14:0:j_id_id23";
	private final String DIV_TABLA= "pt1:j_id_id36:subform:j_id_id32pc2::db";
	
	
	
	public void pruebaConexion (){
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
	}

}
