package com.example.bar.model;

import com.example.bar.db.DatabaseManager;
import com.j256.ormlite.field.DatabaseField;

public class Pedido {
	//cambiar tipo Mesa y Producto por enteros
	
	@DatabaseField(generatedId = true)
	private Integer cod_pedido;
	@DatabaseField (foreign = true, columnName = "nro_mesa")
	private Mesa mesa;
	//@DatabaseField (foreign = true, columnName = "id_cliente")
	private Cliente cliente;
//	@DatabaseField
//	private int nro_mesa;
	@DatabaseField (foreign = true, columnName = "codigo_producto")
	private Producto producto;
	@DatabaseField
	private Double cantidad;
	@DatabaseField
	private Double importe;
	
	public Pedido() {
		// TODO Auto-generated constructor stub
	}
	
	public Pedido(Mesa mesa, Producto prod, double cantidad) {
		this.mesa = mesa;
		this.producto = prod;
		this.cantidad = cantidad;
		this.importe = calcularImporte();
		
	}
	
	public Pedido(Cliente cliente, Producto prod, double cantidad){
		this.cliente = cliente;
		this.producto = prod;
		this.cantidad = cantidad;
		this.importe = calcularImporte();
		
	}
		
	public int getCod_pedido() {
		return cod_pedido;
	}

	public void setCod_pedido(int cod_pedido) {
		this.cod_pedido = cod_pedido;
	}

	public double getCantidad() {
		return cantidad;
	}

	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}

	public double calcularImporte() {
		double precio = DatabaseManager.getInstance().getProductoById(producto.getCodigo_producto()).getPrecio();
		importe = precio* this.cantidad;
		return importe;
	}
	
	public double Importe(){
		return importe;
	}

	public Mesa getMesa() {
		return mesa;
	}

	public void setMesa(Mesa mesa) {
		this.mesa = mesa;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	
	public Double getImporte(){
		return importe;
	}

	public void setImporte(double importe) {
		this.importe = importe;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	

	

	
	
	
	/*public List<Producto> getProductos() {
		ArrayList<Producto> listaProducto = new ArrayList<Producto>();
		for (Producto producto : productos) {
			productos.add(producto);
		}
		return listaProducto;
	}

	public void setProductos(ForeignCollection<Producto> productos) {
		this.productos = productos;
	}	
	*/
	
	/*public double getImporte(List<Producto> p){
		double tmp = 0.0 ;
		if(p.size()>0){
			for (Producto producto : p) {
				
				tmp += producto.getPrecio();
			}
			
		}
		
		return tmp;
	}*/
	/*
	public double getCantidad(Producto pr, List<Producto>p){
		double tmp = 0.0;
		if(p.size()>0){
			tmp = (double)Collections.frequency(p, pr);			
		}
		
		return tmp;
	}
	*/
}
