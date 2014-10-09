package com.jtv_gea.barik;

import android.widget.ProgressBar;
import android.widget.TextView;

public class ProgressBarController implements Runnable{
	private SaldoActivity saldoActivity;
	private int progress;
	private static final int MAXVALUE=100;
	private static final int MINVALUE=00;
	
	public ProgressBarController(SaldoActivity activity, int progress){
		this.saldoActivity = activity;
		if(progress<MINVALUE){
			progress=MINVALUE;
		}else if(progress>MAXVALUE){
			progress=MAXVALUE;
		}
		this.progress=progress;
	}

	@Override
	public void run() {
		
		ProgressBar progressBar=(ProgressBar)this.saldoActivity.findViewById(R.id.progressBarLoading);
		progressBar.setProgress(this.progress);
		
		if(this.progress==MAXVALUE){
			//cambiar el texto
			 TextView loadingText=(TextView)this.saldoActivity.findViewById(R.id.textLoading);
			 loadingText.setText(this.saldoActivity.getString(R.string.text_actualizado));
		}
		
	}

}
