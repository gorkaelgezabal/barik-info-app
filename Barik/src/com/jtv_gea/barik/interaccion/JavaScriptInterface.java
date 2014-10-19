package com.jtv_gea.barik.interaccion;

import java.util.Date;

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
    public void processHTML(String saldo, String caducidad, String nCliente, String situacion, String tipo, String nTarjeta)
    {
		System.out.println("Saldo: "+saldo);
		
		
		//Guardar en fichero el nuevo dato
		Persistencia persistencia= new Persistencia(saldoActivity.getApplicationContext());
		BarikUser user =persistencia.loadUser();
		user.setSaldo(saldo);
		user.setFechaUltimaActualizacion(new Date());
		user.setCaducidad(caducidad);
		user.setnCliente(nCliente);
		user.setSituacion(situacion);
		user.setTipo(tipo);
		user.setnTarjeta(nTarjeta);
		
		persistencia.saveUser(user);
		
		//Editar el saldo en la pantalla
		mHandler.post(new HiloEditarSaldo(user, this.saldoActivity));
		
    }
}