package com.example.bar.db;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.example.bar.model.Categoria;
import com.example.bar.model.Cliente;
import com.example.bar.model.Mesa;
import com.example.bar.model.Pedido;
import com.example.bar.model.Producto;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;

public class DatabaseManager {

	static private DatabaseManager instance;
	private DatabaseHelper helper;

	private DatabaseManager(Context ctx) {
		helper = new DatabaseHelper(ctx);
		try {
			helper.createDataBase();
			
		} catch (IOException e) {
			
			throw new Error("Unable to create database");

		}
		
		try {

			helper.openDataBase();
		} catch (Exception sqle) {

			throw sqle;

		}
		
		
		

	}

	private DatabaseHelper getHelper() {
		return helper;
		
		
	}

	static public void init(Context ctx) {
		if (null == instance) {
			instance = new DatabaseManager(ctx);
		}
	}

	static public DatabaseManager getInstance() {
		return instance;
	}

// Pedidos
	public void addPedido(Pedido p) {

		try {
			getHelper().getPedidoDao().create(p);

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	public void addPedidos(List<Pedido>pedidos){
		
		try {
			for (Pedido pedido : pedidos) {
				getHelper().getPedidoDao().create(pedido);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<Pedido> getPedidosMesa(int nro_mesa) {
		List<Pedido> pedidos = null;
		try {

			pedidos = getHelper().getPedidoDao().queryForEq("nro_mesa",
					nro_mesa);

		} catch (SQLException e) {
			e.printStackTrace();

		}

		return pedidos;

	}
	
	public Pedido getPedidoById(int cod_pedido) {
		Pedido p = null;
		try {
			p = getHelper().getPedidoDao().queryForId(cod_pedido);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return p;
	}

	public int updatePedido(int cod_pedido, double cantidad) {
		Pedido tmp = null;
		int i = 0;
		try {
			tmp = getHelper().getPedidoDao().queryForId(cod_pedido);
			
		if(tmp != null){
			tmp.setCantidad(cantidad);
			tmp.calcularImporte();
		}
				
		i = getHelper().getPedidoDao().update(tmp);
			
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}

	
	
	

	public void deleteAllPedidosMesa(int nro_mesa) {
		try {
			Dao<Pedido, Integer> pedidoDao = getHelper().getPedidoDao();
			DeleteBuilder<Pedido, Integer> db = pedidoDao.deleteBuilder();
			db.where().eq("nro_mesa", nro_mesa);
			pedidoDao.delete(db.prepare());
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void deletePedidoById(int cod_pedido) {
		try {
			getHelper().getPedidoDao().deleteById(cod_pedido);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	
	
//	Productos
	public List<Producto> getAllProductos() {
		List<Producto> productos = null;
		try {
			productos = getHelper().getProductoDao().queryForAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return productos;
	}


	public void addProducto(Producto p) {
		try {
			getHelper().getProductoDao().create(p);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	

	public Producto getProductoByNombre(String nombre_prod) {
		Producto p = null;
		List<Producto> p1 = null;
		try {
			/*
			 * QueryBuilder qb = getHelper().getProductoDao().queryBuilder();
			 * qb.where().eq("nombre", nombre_prod); PreparedQuery<Producto> pq
			 * = qb.prepare(); p1 = getHelper().getProductoDao().query(pq);
			 */
			p1 = getHelper().getProductoDao().queryForEq("nombre_producto",
					nombre_prod);

			if (p1.size() > 0) {
				p = p1.get(0);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return p;

	}

	public Producto getProductoById(int id) {
		Producto p = null;
		try {
			p = getHelper().getProductoDao().queryForId(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return p;
	}

	public List<Producto> getProductoByCatId(int categoria_id) {
		List<Producto> productos = null;
		try {
			productos = getHelper().getProductoDao().queryForEq("categoria_id",
					categoria_id);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return productos;
	}
	
	public int updateProducto(String nombreProd, Categoria cat, String nombre, double precio){
//		UpdateBuilder<Cliente, Integer> builder = getHelper().getClientDao().updateBuilder();
		List<Producto> productos = new ArrayList<Producto>();
		int i = 0;
		try {
			productos =	 getHelper().getProductoDao().queryForEq("nombre_producto", nombreProd);
			
			if (productos.size()==1) {
				Producto p = productos.get(0);
			 
			if(!nombre.equals("")){
				p.setNombre_producto(nombre);
				}
				p.setPrecio(precio);
				p.setCategoria(cat);
				
				i = getHelper().getProductoDao().update(p);
			
			/*builder.where().eq("dni", dni);
			builder.updateColumnValue("nombre", nombre);
			builder.updateColumnValue("apellido", apellido);
			builder.prepare();
			getHelper().getClientDao().u*/
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}
	
	public void deleteProductoById(int cod_producto) {
		try {
			getHelper().getProductoDao().deleteById(cod_producto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
//	Categorias
	public List<Categoria> getAllCategorias() {
		List<Categoria> categorias = null;
		try {
			categorias = getHelper().getCategoriaDao().queryForAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return categorias;
	}

	public void addCategoria(Categoria c) {
		try {

			getHelper().getCategoriaDao().create(c);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	

	public Categoria getCategoriaById(int id) {
		Categoria c = null;
		try {
			c = getHelper().getCategoriaDao().queryForId(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return c;
	}
	
	public Categoria getCategoriaByNombre(String nombreCat) {
		Categoria c = null;
		List<Categoria> categorias = new ArrayList<Categoria>();
		try {
			categorias = getHelper().getCategoriaDao().queryForEq("nombre_categoria", nombreCat);
			if(categorias.size()>0){
				c = categorias.get(0);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return c;
	
	}
	
	public void deleteCategoriaById(int cod_categoria) {
		try {
			getHelper().getCategoriaDao().deleteById(cod_categoria);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public int updateCategoria(int idCat, String nNombreCat){
		Categoria cat = null;
		int i = 0;
		try {
			cat = getHelper().getCategoriaDao().queryForId(idCat);
			
			cat.setNombre_categoria(nNombreCat);
			
			i = getHelper().getCategoriaDao().update(cat);
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}
	
	
	
//	Mesas
	public Mesa getMesaById(int nro_mesa){
		Mesa mesa = null;
		try {
			mesa = getHelper().getMesaDao().queryForId(nro_mesa);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
		return mesa;
	}
	
	public void addMesa(Mesa mesa){
		try {
			getHelper().getMesaDao().create(mesa);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteMesaById(int nro_mesa){
		try {
			getHelper().getMesaDao().deleteById(nro_mesa);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<Mesa> getAllMesas(){
		List <Mesa> mesas = null;
		try {
				mesas = getHelper().getMesaDao().queryForAll();	
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return mesas;
	}

	//Clientes
	public void addCliente(Cliente cliente){
		try {
			getHelper().getClienteDao().create(cliente);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteClienteByNombre(int idCliente){
		try {
			getHelper().getClienteDao().deleteById(idCliente);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<Cliente> getAllClientes(){
		List <Cliente> clientes = null;
		try {
				clientes = getHelper().getClienteDao().queryForAll();	
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return clientes;
	}
	
	public Cliente getClienteByNombre(String nombreCliente){
		Cliente c = null;
		List<Cliente> clientes = new ArrayList<Cliente>();
		try {
			clientes = getHelper().getClienteDao().queryForEq("nombre_cliente", nombreCliente);
			if(clientes.size()>0){
				c = clientes.get(0);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return c;
	}
	
	public int updateCliente(int idCliente, String nNombreCliente, String nApellidoCliente){
		Cliente c = null;
		int i = 0;
		try {
			c = getHelper().getClienteDao().queryForId(idCliente);
			
			c.setNombre(nNombreCliente);
			c.setApellido(nApellidoCliente);
			
			i = getHelper().getClienteDao().update(c);
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}
	

}
