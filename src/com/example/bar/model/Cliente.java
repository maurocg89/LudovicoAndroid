package com.example.bar.model;

import com.j256.ormlite.field.DatabaseField;

public class Cliente {
	
	@DatabaseField(generatedId=true)
	private int id_cliente;
	@DatabaseField
	private String nombre;
	@DatabaseField
	private String apellido;
	
	public Cliente(){}
	
	public Cliente(String nombre){
		this.nombre = nombre;
	}
	
	public Cliente(String nombre, String apellido){
		this.nombre = nombre;
		this.apellido = apellido;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	
	
	
	public int getId_cliente() {
		return id_cliente;
	}

	public void setId_cliente(int id_cliente) {
		this.id_cliente = id_cliente;
	}

	public String toString() {
		if(!apellido.equals("") && !apellido.equals(null)){
		return nombre+" "+apellido;
		}else{
			return nombre;
		}
		
	}
	
}
