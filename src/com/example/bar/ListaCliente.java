package com.example.bar;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.bar.db.DatabaseManager;
import com.example.bar.model.Cliente;
import com.example.bar.model.Constantes;
import com.example.bar.model.Mesa;

public class ListaCliente extends Activity {

	private ListView listView1;
	private Button btnAgregar, btnVolver;
	private List<Cliente> clientes;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lista_cliente);

		listView1 = (ListView) findViewById(R.id.listViewClientes);
		btnAgregar = (Button) findViewById(R.id.btnAgregarClientes);
		btnVolver = (Button) findViewById(R.id.btnVolverClientes);

		btnVolver.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(ListaCliente.this, MenuPrincipal.class);
				startActivity(i);

			}
		});
		
		btnAgregar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(getApplicationContext(), AgregarCliente.class);
				startActivity(i);
			}
		});
		
	}

	public void setData() {
		clientes = DatabaseManager.getInstance().getAllClientes();

		ArrayAdapter<Cliente> adapter = new ArrayAdapter<Cliente>(
				ListaCliente.this, android.R.layout.simple_list_item_1,
				clientes);

		listView1.setAdapter(adapter);
		
		listView1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Cliente c = clientes.get(arg2);
				Intent i = new Intent(ListaCliente.this, DetalleCliente.class);
				i.putExtra(Constantes.id_cliente, c.getId_cliente());
				startActivity(i);
				
			}
			
			
		});
	
	}

}
