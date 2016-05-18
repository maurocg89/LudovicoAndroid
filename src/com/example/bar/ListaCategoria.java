package com.example.bar;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.bar.model.Categoria;
import com.example.bar.model.Constantes;
import com.example.bar.model.Producto;

public class ListaCategoria extends Activity implements OnEditorActionListener {

	// (Error) Cuando uso el filtro para buscar un producto y toco el boton de
	// modificar o eliminar, me devuelve la posicion de otro producto
	// No permitir borrar una categoria que tiene asociado productos

	private ListView listview1;
	private List<Categoria> categorias;
	private Button btnAgregar, btnSearch, btnCancel, btnVolver;
	private MySimpleSearchAdapter mAdapter;
	private EditText edSearch;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lista_categorias);

		listview1 = (ListView) findViewById(R.id.lvListaCategorias);
		btnAgregar = (Button) findViewById(R.id.btnAgregarListaCategorias);
		btnVolver = (Button) findViewById(R.id.btnVolverCat);

		btnSearch = (Button) findViewById(R.id.btnSearchCat);
		btnCancel = (Button) findViewById(R.id.btnCancelCat);
		edSearch = (EditText) findViewById(R.id.edSearchCat);
		mAdapter = new MySimpleSearchAdapter(this);
		setData();

		btnAgregar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(ListaCategoria.this,
						AgregarCategoria.class);
				i.putExtra("MODE", "ADD");
				startActivity(i);

			}
		});

		btnVolver.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(ListaCategoria.this, MenuPrincipal.class);
				startActivity(i);
			}
		});

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

		btnCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				edSearch.setText("");
				setData();

			}
		});

	}

	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void onResume() {

		super.onResume();
		setData();
	}

	private void setData() {
		mAdapter = new MySimpleSearchAdapter(this);
		try {
			categorias = DatabaseManager.getInstance().getAllCategorias();
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (Categoria c : categorias) {

			mAdapter.addItem(c.getNombre_categoria());
		}

		listview1.setAdapter(mAdapter);

	}

	public void onBackPressed() {
		setResult(Activity.RESULT_CANCELED);
		finish();

	}

	private void setSearchResult(String str) {
		mAdapter = new MySimpleSearchAdapter(this);
		for (Categoria tmp : categorias) {
			if (tmp.getNombre_categoria().toLowerCase()
					.contains(str.toLowerCase())) {
				mAdapter.addItem(tmp.getNombre_categoria());

			}

		}

		listview1.setAdapter(mAdapter);

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
			holder.img.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Categoria c = categorias.get(position);
					Intent i = new Intent(ListaCategoria.this,
							ListaProducto.class);
					i.putExtra(Constantes.cod_categoria, c.getCod_categoria());
					startActivity(i);

				}
			});

			holder.btnImgUpdate.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Categoria tmp = categorias.get(position);
					Intent i = new Intent(ListaCategoria.this,
							AgregarCategoria.class);
					i.putExtra("MODE", "EDIT");
					i.putExtra("nombreCat", tmp.getNombre_categoria());
					startActivity(i);

				}
			});

			holder.btnImgDelete.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					AlertDialog alert = new AlertDialog.Builder(
							ListaCategoria.this).create();
					Categoria c = categorias.get(position);
					// List<Producto> p1 = c.getProductos();
					// int size = p1.size();
					if (c.getProductos().size() > 0) {
						alert.setTitle("Error");
						alert.setMessage("La categoria que quiere borrar tiene productos asociados");
						alert.setButton(-1, "OK",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
										dialog.dismiss();
									}
								});
						alert.show();
					} else {

						alert.setTitle("Borrar Categoria");
						alert.setMessage("Desea borrar la categoria?");

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
										Categoria tmp = categorias
												.get(position);

										DatabaseManager.getInstance()
												.deleteCategoriaById(
														tmp.getCod_categoria());

										setData();
										notifyDataSetChanged();

									}
								});
						alert.show();
					}
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
