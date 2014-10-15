package com.jtv_gea.barik.modelo;

import java.io.Serializable;
import java.util.Date;

public class BarikUser implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	private String saldo;
	private Date fechaUltimaActualizacion;
	private String caducidad;
	private String tipo;
	private String nCliente;
	private String situacion;
	private String nTarjeta;
	
	public BarikUser (){
		this.username="";
		this.password="";
		this.saldo="-";
		this.fechaUltimaActualizacion=null;
		this.caducidad="-";
		this.tipo="-";
		this.nCliente="-";
		this.situacion="-";
		this.nTarjeta="-";
	}
	public BarikUser (String username, String password){
		this.username=username;
		this.password=password;
		this.saldo="-";
		this.fechaUltimaActualizacion=null;
		this.caducidad="-";
		this.tipo="-";
		this.nCliente="-";
		this.situacion="-";
		this.nTarjeta="-";
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSaldo() {
		return saldo;
	}
	public void setSaldo(String saldo) {
		this.saldo = saldo;
	}
	public Date getFechaUltimaActualizacion() {
		return fechaUltimaActualizacion;
	}
	public void setFechaUltimaActualizacion(Date fechaUltimaActualizacion) {
		this.fechaUltimaActualizacion = fechaUltimaActualizacion;
	}
	public String getCaducidad() {
		return caducidad;
	}
	public void setCaducidad(String caducidad) {
		this.caducidad = caducidad;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getSituacion() {
		return situacion;
	}
	public void setSituacion(String situacion) {
		this.situacion = situacion;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getnCliente() {
		return nCliente;
	}
	public void setnCliente(String nCliente) {
		this.nCliente = nCliente;
	}
	public String getnTarjeta() {
		return nTarjeta;
	}
	public void setnTarjeta(String nTarjeta) {
		this.nTarjeta = nTarjeta;
	}
	
	

}
