package com.example.bar.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Producto {
	@DatabaseField(generatedId = true)
	private int codigo_producto;
	@DatabaseField(foreign = true)
	private Categoria categoria;
	@DatabaseField
	private String nombre_producto;
	@DatabaseField
	private double precio;
	private String foto;
	
	public Producto(){}
	
		
	public Producto(Categoria categoria, String nombre, double precio){
		this.categoria = categoria;
		this.nombre_producto = nombre;
		this.precio = precio;
		
	}

	public int getCodigo_producto() {
		return codigo_producto;
	}

	public void setCodigo_producto(int codigo_producto) {
		this.codigo_producto = codigo_producto;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public String getNombre_producto() {
		return nombre_producto;
	}

	public void setNombre_producto(String nombre_producto) {
		this.nombre_producto = nombre_producto;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return nombre_producto;
	}


	public String getFoto() {
		return foto;
	}


	public void setFoto(String foto) {
		this.foto = foto;
	}
	
	
	
}
