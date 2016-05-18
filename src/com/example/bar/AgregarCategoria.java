package com.example.bar;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bar.db.DatabaseManager;
import com.example.bar.model.Categoria;

public class AgregarCategoria extends Activity {
	
	private Button btnAgregar, btnVolver;
	private EditText editNombre;
	private DatabaseManager manager;
	private boolean isEditMode = false;
	private String nombreCat;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.agregar_categoria);
		
		if(getIntent().getExtras().getString("MODE")!=null){
			String mode = getIntent().getExtras().getString("MODE");
			if (mode.equals("EDIT")) {
				isEditMode = true;
				nombreCat = getIntent().getExtras().getString("nombreCat");
			}
		}
		
		
		
		
		manager = DatabaseManager.getInstance();

		editNombre = (EditText) findViewById(R.id.etAgregarCat2);
		
		btnAgregar = (Button) findViewById(R.id.btnAgregarCat1);
		btnVolver = (Button) findViewById(R.id.btnAgregarCat2);
		
		if(isEditMode == false){
		setTitle("Agregar Categoria");
		btnAgregar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(validar()){
				Categoria c = new Categoria(editNombre.getText().toString());
				manager.addCategoria(c);
				Toast.makeText(getApplicationContext(), "Se agrego la categoria: "+c.getNombre_categoria(), Toast.LENGTH_SHORT).show();
				editNombre.setText("");
				editNombre.requestFocus();
				}
			}
		});
		
		} else {
			setTitle("Modificar Categoria");
			btnAgregar.setText("Modificar");
			editNombre.setText(nombreCat);
			Categoria cat = manager.getCategoriaByNombre(nombreCat);
			final int idCat = cat.getCod_categoria();
			
			btnAgregar.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if(validar()){
					String nNombreCat = editNombre.getText().toString();
					manager.updateCategoria(idCat, nNombreCat);
					Toast.makeText(AgregarCategoria.this,
							"Se modifico la categoria ",
							Toast.LENGTH_SHORT).show();
					finish();
					} 
				}
			});
			
		}
		
		
		btnVolver.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
				
			}
		});
		
		
	}
	
	private boolean validar(){
		if(editNombre.getText().toString().equals("")){
			Toast.makeText(this, "Debe completar todos los campos", Toast.LENGTH_LONG).show();
			return false;
		}
		
		return true;
	}
	
	
}


	

