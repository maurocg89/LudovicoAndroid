package com.example.bar.model;

import com.j256.ormlite.field.DatabaseField;

public class Mesa {
	@DatabaseField(id = true)
	private int nro_mesa;
	
	private boolean cerrada;

	public Mesa(){}
	
	public Mesa(int nro_mesa){
		this.nro_mesa = nro_mesa;
		this.cerrada = false;
	}
	
	public int getNro_mesa() {
		return nro_mesa;
	}

	public void setNro_mesa(int nro_mesa) {
		this.nro_mesa = nro_mesa;
	}

	public boolean isCerrada() {
		return cerrada;
	}

	public void setCerrada(boolean estaCerrada) {
		this.cerrada = estaCerrada;
	}
	
	@Override
		public String toString() {
			// TODO Auto-generated method stub
			return "Mesa "+nro_mesa;
		}
	
}
