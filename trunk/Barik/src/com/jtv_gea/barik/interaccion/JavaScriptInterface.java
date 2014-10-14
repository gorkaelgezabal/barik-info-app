package com.jtv_gea.barik.interaccion;

import android.os.Handler;
import android.webkit.JavascriptInterface;

import com.jtv_gea.barik.SaldoActivity;
import com.jtv_gea.barik.modelo.BarikUser;
import com.jtv_gea.barik.modelo.Persistencia;

/**
 * Clase a la que se llama desde javascript para actualizar el saldo
 *
 */
public class JavaScriptInterface {
	private SaldoActivity saldoActivity;
	private Handler mHandler;
	
	
    public JavaScriptInterface(SaldoActivity saldoActivity, Handler handler) {
		this.saldoActivity=saldoActivity;
		this.mHandler = handler;
	}
    
    /**
     * Se llama mediante JavaScript desde la pagina a este metodo para
     * poder actualizar la informacion en del saldo en la pantalla
     * 
     * @param saldo recogido de la pagina web
     */
	@JavascriptInterface
    public void processHTML(String saldo)
    {
		System.out.println("Saldo: "+saldo);
		//Editar el saldo en la pantalla
		mHandler.post(new HiloEditarSaldo(saldo, this.saldoActivity));
		
		//Guardar en fichero el nuevo dato
		Persistencia persistencia= new Persistencia(saldoActivity.getApplicationContext());
		BarikUser user =persistencia.loadUser();
		user.setLastBalance(saldo);
		persistencia.saveUser(user);
    }
}
