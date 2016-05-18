package com.example.bar;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.bar.db.DatabaseManager;
import com.example.bar.model.Constantes;
import com.example.bar.model.Mesa;

public class ActivityMesas extends Activity {

	private ListView listview1;
	private List<Mesa> mesas;
	private Button btnVolver;
	private Button btnAgregarMesas;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mesas);

		listview1 = (ListView) findViewById(R.id.listView1);
		btnVolver = (Button) findViewById(R.id.btnVolverMesas);
		btnAgregarMesas = (Button) findViewById(R.id.btnAgregarMesas);
		// btnBorrarMesas = (Button) findViewById(R.id.btnBorrarMesas);
		setData();

		btnAgregarMesas.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				Intent i = new Intent(ActivityMesas.this, AgregarMesa.class);
				startActivity(i);
			}
		});
		
		btnVolver.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(getApplicationContext(),
						MenuPrincipal.class);
				startActivity(i);

			}
		});
		
		
	}
		/*
		 * btnBorrarMesas.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View arg0) { /* for (int i = 0; i <
		 * mesas.size(); i++) {
		 * DatabaseManager.getInstance().deleteAllPedidosMesa(i);
		 * 
		 * 
		 * }
		 * 
		 * finish();
		 * 
		 * 
		 * } });
		 * 
		 * 
		 * }
		 * 
		 * @Override protected void onResume() {
		 * 
		 * super.onResume();
		 * 
		 * updateMesas(); }
		 * 
		 */ 
		
		 
		  

	/*
	 * private void updateMesas(){ List<Mesa> mesastmp = new ArrayList<Mesa>();
	 * mesastmp = DatabaseManager.getInstance().getAllMesas();
	 * ArrayAdapter<Mesa> adp = new
	 * ArrayAdapter<Mesa>(ActivityMesas.this,android
	 * .R.layout.simple_list_item_1, mesastmp);
	 * 
	 * listview1.setAdapter(adp);
	 * 
	 * }
	 */

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		setData();
	}

	public void setData() {
		mesas = DatabaseManager.getInstance().getAllMesas();

		ArrayAdapter<Mesa> adapter = new ArrayAdapter<Mesa>(ActivityMesas.this,
				android.R.layout.simple_list_item_1, mesas);
		listview1.setAdapter(adapter);

		listview1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				Mesa m = mesas.get(pos);
				Intent i = new Intent(ActivityMesas.this, DetalleMesa.class);
				i.putExtra(Constantes.nro_mesa, m.getNro_mesa());
				startActivity(i);
			}

		});

	
	}

}
