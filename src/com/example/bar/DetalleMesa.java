package com.example.bar;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.bar.db.DatabaseManager;
import com.example.bar.model.Categoria;
import com.example.bar.model.Constantes;
import com.example.bar.model.Pedido;
import com.example.bar.model.Producto;

public class DetalleMesa extends Activity {

	ListView listView;
	List<Pedido> pedidos;
	List<String> nombreProductos;
	private DatabaseManager manager;
	private Context mContext;
	private int nro_mesa;
	private Button btnAgregar, btnBorrarPedidos, btnVolver;
	private TextView tvTotal;

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detalle_mesa2);

		mContext = DetalleMesa.this;
		manager = DatabaseManager.getInstance();

		nro_mesa = getIntent().getExtras().getInt(Constantes.nro_mesa);
		setTitle("Mesa " + nro_mesa);
		
		btnVolver = (Button) findViewById(R.id.btnVolverDetalleMesa);
		btnAgregar = (Button) findViewById(R.id.btnAgregarPedido);
		btnBorrarPedidos = (Button) findViewById(R.id.btnBorrarPedidos);
		tvTotal = (TextView) findViewById(R.id.tvImporteTotal);
		listView = (ListView) findViewById(R.id.listaDetalle);

		updateList();
		updateImporteTotal();
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Pedido p = pedidos.get(arg2);
				final int cod_pedido = p.getCod_pedido();
				
				AlertDialog alert = new AlertDialog.Builder(DetalleMesa.this).create();
				alert.setTitle("Opciones");
				alert.setMessage("¿Qué desea hacer con el pedido?");
				alert.setButton(DialogInterface.BUTTON_POSITIVE, "Modificar", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						Intent i = new Intent(DetalleMesa.this, ModificarPedido.class);
						i.putExtra("cod_pedido", cod_pedido);
						i.putExtra(Constantes.nro_mesa, nro_mesa);
						startActivity(i);
					}
				});
				
				alert.setButton(DialogInterface.BUTTON_NEGATIVE, "Borrar", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						DatabaseManager.getInstance().deletePedidoById(cod_pedido);
						updateList();
						updateImporteTotal();
						dialog.dismiss();
					}
				});
				
				alert.setButton(DialogInterface.BUTTON_NEUTRAL, "Cancelar", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						
					}
				});
				
				alert.show();
			}
				
		
		});

		btnAgregar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(mContext, ElegirCategoria.class);
				i.putExtra(Constantes.nro_mesa, nro_mesa);
				startActivity(i);
				finish();

			}
		});

		btnBorrarPedidos.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog alert = new AlertDialog.Builder(DetalleMesa.this)
						.create();
				alert.setTitle("Borrar Datos");
				alert.setMessage("Desea borrar todos los datos de la mesa?");
				
				alert.setButton(-2, "No",
						new DialogInterface.OnClickListener() { // -2 Button
							// Negative

							@Override
							public void onClick(DialogInterface dialog,
									int which) {

								dialog.dismiss();
							}
						});
				
				alert.setButton(-1, "Si",
						new DialogInterface.OnClickListener() { // -1 Button
																// Positive

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								DatabaseManager.getInstance().deleteAllPedidosMesa(nro_mesa);
								updateList();
								updateImporteTotal();
								dialog.dismiss();
								
								
								
								
							}
						});

				alert.show();
			}
		});
		
		btnVolver.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
				
			}
		});
	
	}
	
/*	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {

			menu.setHeaderTitle("Opciones");
			String [] menuItems = {"Borrar", "Modificar", "Cancelar"};
			for (int i = 0; i < menuItems.length; i++) {
				menu.add(Menu.NONE,i, i, menuItems[i]);
			}

	
	}*/
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		updateList();
		updateImporteTotal();
	}

	private void updateList() {
		pedidos = manager.getPedidosMesa(nro_mesa);

//		if (pedidos.size() > 0) {

			CustomAdapter_productos customAdapter = new CustomAdapter_productos(
					mContext, pedidos);

//			customAdapter.notifyDataSetChanged();
			listView.setAdapter(customAdapter);
			registerForContextMenu(listView);
			
//		}
		
		

	}

	private void updateImporteTotal() {
		List<Pedido> pedidosTmp = manager.getPedidosMesa(nro_mesa);
		double importeTotal = 0;
		for (Pedido pedido : pedidosTmp) {
			importeTotal += pedido.calcularImporte();
		}
		tvTotal.setText("" + importeTotal);
	}

	public class CustomAdapter_productos extends BaseAdapter {
		private Context mContext;
		private List<Pedido> pedidos;

		public CustomAdapter_productos(Context ctx, List<Pedido> list) {
			this.mContext = ctx;
			this.pedidos = list;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return pedidos.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return pedidos.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public View getView(final int arg0, View arg1, ViewGroup arg2) {

			View mView = arg1;
			if (mView == null) {
				LayoutInflater vi = (LayoutInflater) mContext
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				mView = vi.inflate(R.layout.fila_productos_custom, null);
			}

			TextView tvNombreProd = (TextView) mView
					.findViewById(R.id.nombre_fila_productos); // hay que poner
																// mView.findViewById
																// porque sino
																// devuelve null
			TextView tvCantidad = (TextView) mView
					.findViewById(R.id.cantidad_fila_productos);
			TextView tvImporte = (TextView) mView
					.findViewById(R.id.importe_fila_productos);
			TextView tvCategoria = (TextView) mView
					.findViewById(R.id.categoria_fila_productos);

			if (pedidos.get(arg0) != null) {
		
				int id_prod = pedidos.get(arg0).getProducto().getCodigo_producto(); 
				Producto p = DatabaseManager.getInstance().getProductoById(id_prod);

				Categoria c = DatabaseManager.getInstance().getCategoriaById(
						p.getCategoria().getCod_categoria());
				String categoria = "Categoria: " + c.getNombre_categoria();
				tvCategoria.setText(categoria);

				String nombreProd = p.getNombre_producto();
				tvNombreProd.setText(nombreProd);

				String cantidad = "Cantidad: "
						+ pedidos.get(arg0).getCantidad();
				tvCantidad.setText(cantidad);

				String importe = "Importe: " + pedidos.get(arg0).calcularImporte();
				tvImporte.setText(importe);
				notifyDataSetChanged();
			}
			
			

			return mView;
		}

	}
}
