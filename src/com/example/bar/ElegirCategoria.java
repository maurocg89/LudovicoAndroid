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
import com.example.bar.model.Categoria;
import com.example.bar.model.Constantes;

public class ElegirCategoria extends Activity {
	
	private ListView listview1; 
	private List<Categoria> categorias;
	private int nro_mesa;
	private Button btnVolver;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.elegir_categorias);
		setTitle("Categorias");		
		listview1 = (ListView) findViewById(R.id.listView1);
		btnVolver = (Button) findViewById(R.id.btnVolver);
		nro_mesa = getIntent().getExtras().getInt(Constantes.nro_mesa);		
		categorias = DatabaseManager.getInstance().getAllCategorias();
		
		ArrayAdapter<Categoria>adapter = new ArrayAdapter<Categoria>(ElegirCategoria.this, android.R.layout.simple_list_item_1,categorias);
		listview1.setAdapter(adapter);
		
		listview1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				Categoria c = categorias.get(pos);
				Intent i = new Intent(ElegirCategoria.this, AgregarPedido.class);
				i.putExtra(Constantes.cod_categoria, c.getCod_categoria());
				i.putExtra(Constantes.nro_mesa, nro_mesa);
				startActivity(i);
			}
			
		});
		
		btnVolver.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(ElegirCategoria.this, DetalleMesa.class);
				i.putExtra(Constantes.nro_mesa, nro_mesa);
				startActivity(i);
				finish();
			}
		});
		
	}

	
	
	
}
