package com.example.bar;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bar.db.DatabaseManager;
import com.example.bar.model.Mesa;

public class AgregarMesa extends Activity{
	
	private Button btnAgregar, btnVolver;
	private EditText editCantidad;
	private DatabaseManager manager;
	private List<Mesa> mesas;

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.agregar_mesa);
		btnAgregar = (Button) findViewById(R.id.btnAgregarMesa);
		btnVolver = (Button) findViewById(R.id.btnVolverMesa);
		editCantidad = (EditText) findViewById(R.id.etAgregarMesa);
		
		manager = DatabaseManager.getInstance();
		mesas = manager.getAllMesas();
		
		btnVolver.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(AgregarMesa.this, ActivityMesas.class);
				startActivity(i);
			}
		});
		
		btnAgregar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int m = Integer.parseInt(editCantidad.getText()
						.toString());
				
				 int b = mesas.size(); 

				 for (int i = 1; i < m+1; i++){
					 Mesa m2 = new Mesa(b+i);
					 DatabaseManager.getInstance().addMesa(m2);
				 }
				
				 Toast.makeText(getApplicationContext(), "Se agregaron "+m+" mesas", Toast.LENGTH_SHORT).show();
			}
		});
		
		
			
		   
	}
	
}
