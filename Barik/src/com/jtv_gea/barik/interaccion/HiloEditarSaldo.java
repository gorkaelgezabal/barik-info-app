package com.jtv_gea.barik.interaccion;

import android.os.Handler;
import android.widget.TextView;

import com.jtv_gea.barik.ProgressBarController;
import com.jtv_gea.barik.R;
import com.jtv_gea.barik.SaldoActivity;
import com.jtv_gea.barik.modelo.BarikUser;

public class HiloEditarSaldo implements Runnable{
	private SaldoActivity saldoActivity;
	private BarikUser usuario;
	public HiloEditarSaldo(BarikUser usuario,  SaldoActivity saldoActivity){
		this.usuario=usuario;
		this.saldoActivity = saldoActivity;
	}

	@Override
	public void run() {

        //editar textos
		TextView saldoText= (TextView) this.saldoActivity.findViewById(R.id.resultado_saldo_barik);
        saldoText.setText(usuario.getSaldo());
        TextView fechaActualizacionText= (TextView) this.saldoActivity.findViewById(R.id.resultado_fecha_actualizacion_barik);
        fechaActualizacionText.setText(usuario.getFechaUltimaActualizacionFormateada());
        TextView fechaCaducidadText= (TextView) this.saldoActivity.findViewById(R.id.resultado_fecha_caducidad_barik);
        fechaCaducidadText.setText(usuario.getCaducidad());
        TextView tipoText= (TextView) this.saldoActivity.findViewById(R.id.resultado_tipo_barik);
        tipoText.setText(usuario.getTipo());
        TextView numClienteText= (TextView) this.saldoActivity.findViewById(R.id.resultado_numero_cliente_barik);
        numClienteText.setText(usuario.getnCliente());
        TextView numSituacionText= (TextView) this.saldoActivity.findViewById(R.id.resultado_situacion_barik);
        numSituacionText.setText(usuario.getSituacion());
        TextView numTarjetaText= (TextView) this.saldoActivity.findViewById(R.id.resultado_numero_tarjeta_barik);
        numTarjetaText.setText(usuario.getnTarjeta());
        
        
        Handler mHandler = new Handler();
        
        //actualizar progress bar
		mHandler.post(new ProgressBarController(this.saldoActivity, 00));
		
	}
}
