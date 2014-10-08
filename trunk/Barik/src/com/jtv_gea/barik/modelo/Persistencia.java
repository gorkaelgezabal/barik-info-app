package com.jtv_gea.barik.modelo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;

import android.content.Context;

public class Persistencia {
	private static final String FILENAME = "UserFile";
	private Context mContext;
	
	public Persistencia (Context context){
		this.mContext = context;
	}
	
	/**
	 * Carga el usuario de fichero
	 * @return
	 */
	public BarikUser loadUser(){
		try {
			FileInputStream fis = mContext.openFileInput(FILENAME);
			ObjectInputStream ois = new ObjectInputStream(fis);
			@SuppressWarnings("unchecked")
			BarikUser barikUser = (BarikUser) ois.readObject();
			
			ois.close();
			fis.close();
			return barikUser;
		} catch (StreamCorruptedException e) {
			e.printStackTrace();
		} catch (OptionalDataException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return new BarikUser();		
	}
	
	
	/**
	 * Guarda el usuario en fichero
	 * @param barikUser
	 */
	public void saveUser(BarikUser barikUser){
		try {
			FileOutputStream fos = mContext.openFileOutput(FILENAME, Context.MODE_PRIVATE);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(barikUser);
			oos.close();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
