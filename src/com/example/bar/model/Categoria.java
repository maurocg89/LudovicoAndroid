package com.example.bar.model;

import java.util.ArrayList;
import java.util.List;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Categoria {
	
@DatabaseField(generatedId = true)
private int cod_categoria;
@DatabaseField(canBeNull=false)
private String nombre_categoria;
@ForeignCollectionField
private ForeignCollection<Producto>productos;

public Categoria(){}

public Categoria(int codigo, String nombre){
	this.cod_categoria = codigo;
	this.nombre_categoria = nombre;
}

public Categoria(String nombre){
	this.nombre_categoria = nombre;
}

public int getCod_categoria() {
	return cod_categoria;
}
public void setCod_categoria(int cod_categoria) {
	this.cod_categoria = cod_categoria;
}
public String getNombre_categoria() {
	return nombre_categoria;
}
public void setNombre_categoria(String nombre_categoria) {
	this.nombre_categoria = nombre_categoria;
}

public List<Producto> getProductos() {
	ArrayList<Producto> listaProducto = new ArrayList<Producto>();
	for (Producto producto : productos) {
//		productos.add(producto);
		listaProducto.add(producto);
	}
	return listaProducto;
}

public void setProductos(ForeignCollection<Producto> productos) {
	this.productos = productos;
}	

@Override
	public String toString() {
		
		return nombre_categoria;
	}



}
