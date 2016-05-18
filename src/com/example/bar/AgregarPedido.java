package com.example.bar;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

import com.example.bar.db.DatabaseManager;
import com.example.bar.model.Constantes;
import com.example.bar.model.Mesa;
import com.example.bar.model.Pedido;
import com.example.bar.model.Producto;

public class AgregarPedido extends Activity {

	private Spinner spOpciones, spCantidad;
	private List<Producto> productos;

	private Button btnAgregar, btnTerminar;

	private List<String> nombre_productos;
	private int cod_categoria;
	private int nro_mesa;
	private Double[] cantidades;
	private Integer[] cantidades1;
	private TextView tvImporte;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.agregar_pedido2);

		spOpciones = (Spinner) findViewById(R.id.spOpciones1);
		spCantidad = (Spinner) findViewById(R.id.spCantidad2);
		nro_mesa = getIntent().getExtras().getInt(Constantes.nro_mesa);
		tvImporte = (TextView) findViewById(R.id.tvImporte2);
		cod_categoria = (getIntent().getExtras()
				.getInt(Constantes.cod_categoria));

		String nombre_categoria = DatabaseManager.getInstance()
				.getCategoriaById(cod_categoria).getNombre_categoria();
		setTitle(nombre_categoria);

		prepararSpinners();
		prepararBotones();

	}

	private void prepararSpinners() {
		cantidades = new Double[] { 0.5, 1.0, 1.5, 2.0, 2.5, 3.0, 3.5, 4.0,
				4.5, 5.0, 5.5, 6.0, 6.5, 7.0, 7.5, 8.0, 8.5, 9.0, 9.5, 10.0 };

		cantidades1 = new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };

		productos = DatabaseManager.getInstance().getProductoByCatId(
				cod_categoria);
		nombre_productos = new ArrayList<String>();

		for (Producto p : productos) {
			nombre_productos.add(p.getNombre_producto());
		}

		ArrayAdapter<String> adapterProductos = new ArrayAdapter<String>(
				AgregarPedido.this,
				android.R.layout.simple_spinner_dropdown_item, nombre_productos);
		spOpciones.setAdapter(adapterProductos);

		if (cod_categoria == 1) {
			ArrayAdapter<Double> adapterCantidades = new ArrayAdapter<Double>(
					AgregarPedido.this,
					android.R.layout.simple_spinner_dropdown_item, cantidades);
			spCantidad.setAdapter(adapterCantidades);

		} else {
			ArrayAdapter<Integer> adapterCantidades = new ArrayAdapter<Integer>(
					AgregarPedido.this,
					android.R.layout.simple_spinner_dropdown_item, cantidades1);
			spCantidad.setAdapter(adapterCantidades);

		}

		spOpciones.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				Producto p = productos.get(arg2);
				double precio = p.getPrecio();

				if (cod_categoria == 1) {
					double cantidad = Double.parseDouble(spCantidad
							.getSelectedItem().toString());
					tvImporte.setText("" + (precio * cantidad));
				} else {

					int cantidad = Integer.parseInt(spCantidad
							.getSelectedItem().toString());
					tvImporte.setText("" + (precio * cantidad));

				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}

		});

		spCantidad.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				Producto p = DatabaseManager.getInstance().getProductoByNombre(
						spOpciones.getSelectedItem().toString());
				double precio = p.getPrecio();
				if (cod_categoria == 1) {

					double cantidad = cantidades[arg2];
					tvImporte.setText("" + (precio * cantidad));

				} else {
					int cantidad = cantidades1[arg2];
					tvImporte.setText("" + (precio * cantidad));

				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}

		});
	}

	private void prepararBotones() {
		btnAgregar = (Button) findViewById(R.id.btnAgregar2);
		btnTerminar = (Button) findViewById(R.id.btnTerminar2);

		btnAgregar.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// guarda el pedido en la bd

				String nombre_prod = spOpciones.getSelectedItem().toString();
				Producto prod = DatabaseManager.getInstance()
						.getProductoByNombre(nombre_prod);
				Mesa mesa = DatabaseManager.getInstance().getMesaById(nro_mesa);
				double cantidad = Double.parseDouble(spCantidad
						.getSelectedItem().toString());

				Pedido pedido = new Pedido(mesa, prod, cantidad);

				DatabaseManager.getInstance().addPedido(pedido);
				if (cod_categoria == 1) {
					Toast.makeText(
							getApplicationContext(),
							"Se agrego " + cantidad + " "
									+ prod.getNombre_producto(),
							Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(
							getApplicationContext(),
							"Se agrego " + (int) cantidad + " "
									+ prod.getNombre_producto(),
							Toast.LENGTH_SHORT).show();
				}

				spCantidad.setSelection(0);
				spOpciones.setSelection(0);

			}

		});

		btnTerminar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				finish();

			}
		});

	}

}
