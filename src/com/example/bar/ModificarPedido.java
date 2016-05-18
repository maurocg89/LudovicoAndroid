package com.example.bar;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bar.db.DatabaseManager;
import com.example.bar.model.Categoria;
import com.example.bar.model.Constantes;
import com.example.bar.model.Pedido;
import com.example.bar.model.Producto;


public class ModificarPedido extends Activity {

	private Button btnCancelar, btnModificar;
	private TextView tvCategoria, tvProducto, tvImporte;
	private EditText etCantidad;
	private int cod_pedido, nro_mesa;
	private Pedido p;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.modificar_pedido);

		try {
			cod_pedido = getIntent().getExtras().getInt("cod_pedido");
			nro_mesa = getIntent().getExtras().getInt(Constantes.nro_mesa);
		} catch (Exception e) {
			e.printStackTrace();
		}

		tvCategoria = (TextView) findViewById(R.id.tvNombreCatModif);
		tvProducto = (TextView) findViewById(R.id.tvNombreProdModif);
		tvImporte = (TextView) findViewById(R.id.tvImporteModif);
		etCantidad = (EditText) findViewById(R.id.etCantidadModif);

		btnCancelar = (Button) findViewById(R.id.btnCancelarPedido);
		btnModificar = (Button) findViewById(R.id.btnModificarPedido);

		p = DatabaseManager.getInstance().getPedidoById(cod_pedido);
		
		Producto tmp = DatabaseManager.getInstance().getProductoById(p.getProducto().getCodigo_producto());
		Categoria c = DatabaseManager.getInstance().getCategoriaById(tmp.getCategoria().getCod_categoria());
		final double precio = tmp.getPrecio();
		
		tvCategoria.setText(c.getNombre_categoria());
		
		tvProducto.setText(tmp.getNombre_producto());
		
		tvImporte.setText("" + p.calcularImporte());
		etCantidad.setText("" + p.getCantidad());
		
		etCantidad.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				double cantidad;
				if(!etCantidad.getText().toString().equals("")){
				 cantidad = Double.parseDouble(etCantidad.getText().toString());
				} else {
				 cantidad = 0.0;	
				}
				tvImporte.setText(""+(precio*cantidad));
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
			
				
			}
			
			@Override
			public void afterTextChanged(Editable arg0) {
			
				
			}
		});

		btnModificar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
			if(validar()){	
			double cantidad = Double.parseDouble(etCantidad.getText()
							.toString());
			
			DatabaseManager.getInstance().updatePedido(cod_pedido, cantidad);
			tvImporte.setText(""+(cantidad*precio));
			Toast.makeText(ModificarPedido.this, "El pedido se modifico con éxito", Toast.LENGTH_SHORT).show();
			finish();
				}
			}
		});

		btnCancelar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
		
				finish();
			}
		});

	}
		
	private boolean validar(){
		if(etCantidad.getText().toString().equals("")){
			Toast.makeText(this, "Debe completar todos los campos", Toast.LENGTH_LONG).show();
			return false;
		}
		if(Double.parseDouble(etCantidad.getText().toString()) == 0){
			Toast.makeText(this, "La cantidad no puede ser 0", Toast.LENGTH_LONG).show();
			return false;
		}
		
		return true;
	}
	
}
