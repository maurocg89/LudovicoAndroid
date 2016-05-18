package com.example.bar;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.example.bar.db.DatabaseManager;
import com.example.bar.model.Constantes;
import com.example.bar.model.Producto;

public class ListaProducto extends Activity implements OnEditorActionListener {

	// (Error) Cuando uso el filtro para buscar un producto y toco el boton de modificar o eliminar, me devuelve la posicion de otro producto 
	
	private ListView listview1;
	private List<Producto> productos;
	private List<String> nombreProd;
	private Button btnAgregar, btnSearch, btnCancel, btnVolver;
	private MySimpleSearchAdapter mAdapter;
	
	private String pathDir  = "fotosProductos";
	private String nombreFoto = "";

	private EditText edSearch;
	private int cod_cat;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lista_productos);
		setTitle("Categorias");

		edSearch = (EditText) findViewById(R.id.edSearch);
		listview1 = (ListView) findViewById(R.id.lvListaProductos);
		btnAgregar = (Button) findViewById(R.id.btnAgregarListaProductos);
		btnSearch = (Button) findViewById(R.id.btnSearch);
		btnCancel = (Button) findViewById(R.id.btnCancel);
		btnVolver = (Button) findViewById(R.id.btnVolverProd);
		
		mAdapter = new MySimpleSearchAdapter(this);

		setData();
		
		edSearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				if (0 != edSearch.getText().length()) {
					String spnId = edSearch.getText().toString();
					setSearchResult(spnId);
				} else {
					setData();
				}// TODO Auto-generated method stub

			}
		});
		
		btnVolver.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(cod_cat == 0){
				Intent i = new Intent(ListaProducto.this, MenuPrincipal.class);
				startActivity(i);
				} else {
				Intent i = new Intent(ListaProducto.this, ListaCategoria.class);
				startActivity(i);
				}
			}
		});
		
		btnAgregar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent i = new Intent(ListaProducto.this, AgregarProducto.class);
				i.putExtra("MODE", "ADD");
				i.putExtra(Constantes.cod_categoria, cod_cat);
				startActivity(i);
			}
		});

		btnSearch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});

		btnCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				edSearch.setText("");
				setData();

			}
		});

	}

	@Override
	protected void onResume() {

		super.onResume();
		setData();
	}

	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

		return false;
	}

	private void setData() {
		mAdapter = new MySimpleSearchAdapter(this);
		try {
			cod_cat = getIntent().getExtras().getInt(Constantes.cod_categoria);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (cod_cat == 0) {
			productos = DatabaseManager.getInstance().getAllProductos();
		} else {

			productos = DatabaseManager.getInstance().getProductoByCatId(
					cod_cat);
		}

		nombreProd = new ArrayList<String>();

		for (Producto p : productos) {
			nombreProd.add(p.getNombre_producto());
			mAdapter.addItem(p.getNombre_producto());
		}

		listview1.setAdapter(mAdapter);

	}

	private void setSearchResult(String str) {
		mAdapter = new MySimpleSearchAdapter(this);
		for (String tmp : nombreProd) {
			if (tmp.toLowerCase().contains(str.toLowerCase())) {
				mAdapter.addItem(tmp);

			}

		}

		listview1.setAdapter(mAdapter);

	}

	@Override
	public void onBackPressed() {
		setResult(Activity.RESULT_CANCELED);
		finish();

	}

	public class MySimpleSearchAdapter extends BaseAdapter {

		private ArrayList<String> mData = new ArrayList<String>();
		private LayoutInflater mInflater;

		public MySimpleSearchAdapter(Activity activity) {
			mInflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		public void addItem(String item) {
			mData.add(item);
			notifyDataSetChanged();
		}

		public int getCount() {
			return mData.size();
		}

		public String getItem(int position) {
			return mData.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(final int position, View convertView,
				ViewGroup parent) {

			ViewHolder holder = null;

			if (convertView == null) {
				holder = new ViewHolder();
				convertView = mInflater.inflate(R.layout.item_one, null);

				holder.textView = (TextView) convertView
						.findViewById(R.id.text);
				holder.img = (ImageView) convertView
						.findViewById(R.id.imgProductPhoto);
				holder.btnImgUpdate = (ImageButton) convertView
						.findViewById(R.id.imgUpdateProduct);
				holder.btnImgDelete = (ImageButton) convertView
						.findViewById(R.id.imgDeleteProduct);

				
				convertView.setTag(holder);

			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			
			String strFoto = DatabaseManager.getInstance().getProductoByNombre(nombreProd.get(position)).getFoto();
			
			try {
				if(!strFoto.equals("")){
					String strFile = Environment.getExternalStorageDirectory()+ "/" + pathDir  + "/" + strFoto ;
					File file = new File( strFile );
					
					System.out.println ("productactivity  foto : " + strFile);
					if(file.exists())
					{ 
						Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
						holder.img.setImageBitmap(myBitmap);
					}
					else
					{
						
					}
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			
			holder.btnImgUpdate.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					
					String tmp = nombreProd.get(position);
					Intent i = new Intent(ListaProducto.this,
							AgregarProducto.class);
					i.putExtra("MODE", "EDIT");
					i.putExtra("nombreProd", tmp);
					i.putExtra(Constantes.cod_categoria, cod_cat);
					startActivity(i);

				}
			});

			holder.btnImgDelete.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					AlertDialog alert = new AlertDialog.Builder(
							ListaProducto.this).create();
					alert.setTitle("Borrar Producto");
					alert.setMessage("Desea borrar el producto?");

					alert.setButton(-2, "No",
							new DialogInterface.OnClickListener() { // -2
								// Button
								// Negative

								@Override
								public void onClick(DialogInterface dialog,
										int which) {

									dialog.dismiss();
								}
							});

					alert.setButton(-1, "Si",
							new DialogInterface.OnClickListener() { // -1
								// Button
								// Positive

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									String tmp = nombreProd.get(position);
									Producto p = DatabaseManager.getInstance()
											.getProductoByNombre(tmp);
									DatabaseManager.getInstance()
											.deleteProductoById(
													p.getCodigo_producto());
									setData();
									notifyDataSetChanged();

								}
							});
					alert.show();
				}

			});

			String str = mData.get(position);
			holder.textView.setText(str);
			return convertView;
		}

		public class ViewHolder {
			public TextView textView;
			public ImageView img;
			public ImageButton btnImgDelete, btnImgUpdate;
		}

	}

}
