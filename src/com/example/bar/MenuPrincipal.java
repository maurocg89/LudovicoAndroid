package com.example.bar;

import java.util.ArrayList;

import com.example.bar.db.DatabaseManager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//Falta probar si guarda y muestra las fotos de los productos en la lista y en el imageview
//Diseño a todas las activities


public class MenuPrincipal extends Activity {

	private GridView gridOpciones;
	private ArrayList<MenuItems> opciones;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_principal);
		DatabaseManager.init(this);

		gridOpciones = (GridView) findViewById(R.id.gridopciones);
		opciones = new ArrayList<MenuItems>();
		final String[] titulos = { "Mesas", "Categorias", "Productos", "Clientes", "Info"};
		MenuItems items = new MenuItems("", "");

		for (int i = 0; i < titulos.length; i++) {
			items = new MenuItems("", titulos[i]);
			opciones.add(items);
		}

		gridOpciones.setAdapter(new AdaptadorTitulos(this, opciones));

		gridOpciones.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View v, int pos,
					long id) {

				try {
					String titulo = opciones.get(pos).getTitulo();
					Toast.makeText(MenuPrincipal.this,
							titulo, Toast.LENGTH_SHORT)
							.show();

				switch(titulo){
				case "Mesas":
					Intent i = new Intent(getApplicationContext(), ActivityMesas.class);
					startActivity(i);
					break;
				case "Categorias":
					Intent i2 = new Intent(getApplicationContext(), ListaCategoria.class);
					startActivity(i2);
					break;
				case "Productos":
					Intent i3 = new Intent(getApplicationContext(), ListaProducto.class);
					startActivity(i3);
					break;
				case "Info":
					Intent i4 = new Intent(getApplicationContext(), Info.class);
					startActivity(i4);
					break;
				case "Clientes":
					Intent i5 = new Intent(getApplicationContext(), ListaCliente.class);
					startActivity(i5);
					break;
												
				}
					
					
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});

	}

	public class AdaptadorTitulos extends BaseAdapter {

		Activity context;
		private ArrayList<MenuItems> arrayListMenuItems;

		public AdaptadorTitulos(Activity context,
				ArrayList<MenuItems> arrayListMenuItems) {
			this.context = context;
			this.arrayListMenuItems = arrayListMenuItems;
		}

		@Override
		public int getCount() {
			return arrayListMenuItems.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View gridView;
			if (convertView == null) {
				gridView = new View(context);
				gridView = inflater.inflate(R.layout.iconrow, null);

				TextView tv1 = (TextView) gridView
						.findViewById(R.id.iconrow_title);
				tv1.setText(arrayListMenuItems.get(position).getTitulo());

				Drawable iconDrawable = null;

				if ("Mesas"
						.equals(arrayListMenuItems.get(position).getTitulo())) {
					iconDrawable = context.getResources().getDrawable(
							R.drawable.mesas);
				}
				if ("Categorias".equals(arrayListMenuItems.get(position)
						.getTitulo())) {
					iconDrawable = context.getResources().getDrawable(
							R.drawable.descarga);
				}
				if ("Productos".equals(arrayListMenuItems.get(position)
						.getTitulo())) {
					iconDrawable = context.getResources().getDrawable(
							R.drawable.food_icon);
				}
				if ("Clientes".equals(arrayListMenuItems.get(position).getTitulo())) {
					iconDrawable = context.getResources().getDrawable(
							R.drawable.clients);
				}
				if ("Info".equals(arrayListMenuItems.get(position).getTitulo())) {
					iconDrawable = context.getResources().getDrawable(
							R.drawable.info);
				}

				ImageView imageView = (ImageView) gridView
						.findViewById(R.id.iconrow_icon);
				imageView.setImageDrawable(iconDrawable);
			} else {
				gridView = (View) convertView;
			}

			return gridView;
		}

	}

	class MenuItems {
		String icono;
		String titulo;

		public MenuItems(String icono, String titulo) {
			super();
			this.icono = icono;
			this.titulo = titulo;
		}

		public String getIcono() {
			return icono;
		}

		public void setIcono(String icono) {
			this.icono = icono;
		}

		public String getTitulo() {
			return titulo;
		}

		public void setTitulo(String titulo) {
			this.titulo = titulo;
		}

	}
}
