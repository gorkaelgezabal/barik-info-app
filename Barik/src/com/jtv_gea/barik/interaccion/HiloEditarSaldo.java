package com.jtv_gea.barik.interaccion;

import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import com.jtv_gea.barik.ProgressBarController;
import com.jtv_gea.barik.R;
import com.jtv_gea.barik.SaldoActivity;

public class HiloEditarSaldo implements Runnable{
	private SaldoActivity saldoActivity;
	private String saldo;
	public HiloEditarSaldo(String saldo,  SaldoActivity saldoActivity){
		this.saldo=saldo;
		this.saldoActivity = saldoActivity;
	}

	@Override
	public void run() {

        //editar texto
		TextView saldoText= (TextView) this.saldoActivity.findViewById(R.id.text_saldo_barik);
        saldoText.setText(this.saldoActivity.getString(R.string.text_saldo_barik)+" "+saldo);
        Handler mHandler = new Handler();
        
        //actualizar progress bar
		mHandler.post(new ProgressBarController(this.saldoActivity, 100));
		
	}
}
