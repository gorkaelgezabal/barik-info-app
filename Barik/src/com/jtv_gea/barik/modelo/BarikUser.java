package com.jtv_gea.barik.modelo;

import java.io.Serializable;

public class BarikUser implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	private String lastBalance;
	
	public BarikUser (){
		this.username="";
		this.password="";
		this.lastBalance="-";
	}
	public BarikUser (String username, String password){
		this.username=username;
		this.password=password;
		this.lastBalance="-";
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
	public String getLastBalance() {
		return lastBalance;
	}
	public void setLastBalance(String lastBalance) {
		this.lastBalance = lastBalance;
	}
	
	

}
