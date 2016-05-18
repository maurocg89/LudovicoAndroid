package com.example.bar;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bar.db.DatabaseManager;
import com.example.bar.model.Categoria;
import com.example.bar.model.Constantes;
import com.example.bar.model.Producto;




public class AgregarProducto extends Activity {

	private EditText editNombre, editPrecio;
	private Button btnAgregar, btnAgregarFoto, btnCancelar;
	private ImageView imageViewFoto;
	private TextView titulo;

	private Context mContext;
	private boolean isEditMode = false;
	private String nombreProd;
	private String nombreCat;
	
	private final int CAMERA_PIC_REQUEST = 1;
	Uri imageUri = null;
	private Bitmap bmp;
	private Bitmap thumbnail;
	private String pathFoto = "";
	private String pathDir  = "fotosProductos";
	private String nombreFoto = "";
	
	private int cod_cat;
	private Spinner spCat;
	private List<Categoria> cat;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.agregar_producto);
		
		if(getIntent().getExtras().getString("MODE")!=null){
			String mode = getIntent().getExtras().getString("MODE");
			if (mode.equals("EDIT")) {
				isEditMode = true;
				nombreProd = getIntent().getExtras().getString("nombreProd");
				
			}
		}
		

		cod_cat = getIntent().getExtras().getInt(Constantes.cod_categoria);
	
		mContext = AgregarProducto.this;
		
		titulo = (TextView) findViewById(R.id.tvTituloAgregarProducto);
		spCat = (Spinner) findViewById(R.id.spCat1);
		editNombre = (EditText) findViewById(R.id.etAgregarProd1);
		editPrecio = (EditText) findViewById(R.id.etAgregarProd2);
		imageViewFoto = (ImageView) findViewById(R.id.ivFotoProducto);
		btnAgregarFoto = (Button) findViewById(R.id.btnAgregarFoto);

		btnAgregar = (Button) findViewById(R.id.btnAgregarProd1);
		btnCancelar = (Button) findViewById(R.id.btnCancelarAgregarProducto);
		

		
	
			setTitle("Agregar Producto");
			cat = DatabaseManager.getInstance().getAllCategorias();

			List<String> categorias = new ArrayList<String>();

			for (Categoria categ : cat) {
				categorias.add(categ.getNombre_categoria());
			}
			ArrayAdapter<String> adp = new ArrayAdapter<String>(mContext,
					android.R.layout.simple_spinner_dropdown_item, categorias);
			
			spCat.setAdapter(adp);
			
			if(cod_cat != 0){
				nombreCat = DatabaseManager.getInstance().getCategoriaById(cod_cat).getNombre_categoria();
				int spPos = adp.getPosition(nombreCat);
				spCat.setSelection(spPos);
			}
			
			btnAgregar.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if(validar()){
					String nombreCat = spCat.getSelectedItem().toString();
					Categoria c1 = DatabaseManager.getInstance()
							.getCategoriaByNombre(nombreCat);

					double precio = Double.parseDouble(editPrecio.getText()
							.toString());
					String nombre = editNombre.getText().toString();

					Producto p = new Producto(c1, nombre, precio);
					p.setFoto(pathFoto);
					
					DatabaseManager.getInstance().addProducto(p);
					Toast.makeText(getApplicationContext(),
							"Se agrego el producto: " + p.getNombre_producto(),
							Toast.LENGTH_SHORT).show();
					editNombre.setText("");
					editPrecio.setText("");
					editNombre.requestFocus();
					}
				}
			});

			btnCancelar.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					Intent i = new Intent(AgregarProducto.this, ListaProducto.class);
					startActivity(i);
					finish();
				}
			});

			btnAgregarFoto.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					startActivityForResult(i, CAMERA_PIC_REQUEST);
				}
			});
			
			
		//Edit Mode
		
			if(isEditMode){
				setTitle("Modificar Producto");
				titulo.setText("Modificar Producto");
				btnAgregar.setText("Modificar");
				Producto p = DatabaseManager.getInstance().getProductoByNombre(nombreProd); 
			

				nombreCat = DatabaseManager.getInstance().getCategoriaById(p.getCategoria().getCod_categoria()).getNombre_categoria();
				pathFoto  = p.getFoto();
				
				ArrayAdapter<String> adptmp = (ArrayAdapter<String>)spCat.getAdapter();
				int spinnerPos = adptmp.getPosition(nombreCat);
				

				spCat.setSelection(spinnerPos);
				editNombre.setText(p.getNombre_producto());
				editPrecio.setText(""+p.getPrecio());
				
				
				try {
					if(!pathFoto.equals("")){
						String strFile = Environment.getExternalStorageDirectory()+ File.separator + pathDir + File.separator + pathFoto;
						File file = new File(strFile);
						if(file.exists()){
							Bitmap bmTmp = BitmapFactory.decodeFile(file.getAbsolutePath());
							imageViewFoto.setImageBitmap(bmTmp);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				btnAgregar.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						if(validar()){
						String nomCat = spCat.getSelectedItem().toString();
						Categoria c1 = DatabaseManager.getInstance()
								.getCategoriaByNombre(nomCat);

						double precio = Double.parseDouble(editPrecio.getText()
								.toString());
						String nombre = editNombre.getText().toString();
						
						DatabaseManager.getInstance().updateProducto(nombreProd, c1,nombre,precio);
						
					
						Toast.makeText(AgregarProducto.this,
								"Se modifico el producto: " + nombreProd,
								Toast.LENGTH_SHORT).show();
						finish();
						}
					}
				});

						
								
		}
			
	

	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
				
		if(resultCode == Activity.RESULT_OK){
			if(bmp != null){
				bmp.recycle();
			}
			if(checkExternalMedia() == false){
				
				Toast.makeText(this, "No se puede crear el directorio para guardar la imagen", Toast.LENGTH_LONG).show();
				
				return ;
			}
			File fotoDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "CamaraAPIDemo");
			
			if(!fotoDir.exists() && !fotoDir.mkdirs()){
				Toast.makeText(this, "No se puede crear el directorio para guardar la imagen", Toast.LENGTH_LONG).show();
				
			}
			
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			bmp = (Bitmap) data.getExtras().get("data");
			bmp.compress(CompressFormat.JPEG, 100, bos);
			
			imageViewFoto.setImageBitmap(bmp);
//			String root = Environment.getExternalStorageDirectory().toString();
//			String fileName = nombreProd+".jpg";
//			bmp = (Bitmap) data.getExtras().get("data");
//			imageViewFoto.setImageBitmap(bmp);
			
//			new File(root+File.separator+"photos").mkdirs();
			try {

				byte[] foto = bos.toByteArray();

				Date fecha = new Date();
				SimpleDateFormat ft = new SimpleDateFormat("yyyy_MM_dd_hhmmss");
				String fechaCompleta = ft.format(fecha);

				

				File imagesFolder = null;
				try {
					imagesFolder = new File(Environment.getExternalStorageDirectory(), pathDir);
				
					Log.d("MAKE DIR", imagesFolder.mkdirs() + "");
					imagesFolder.mkdirs();
				} catch (Exception e) {
					Log.d("MAKE DIR", e.getMessage().toString());
					return ;
				}
				
				nombreFoto = fechaCompleta + ".jpg";
				
				pathFoto = nombreFoto;
				
				
				String strFile = Environment.getExternalStorageDirectory() + "/" + pathDir +"/"+ nombreFoto;
				System.out.println ("addproduct foto : " + strFile);
				File picture = new File(strFile);
		
				
				if (picture.exists() == true) {
					System.out.println("Se ha creado el siguiente Path: "+ picture.getAbsolutePath());
				} else {
					//System.out.println("No se ha creado nada...");
					picture.createNewFile();
				}

				FileOutputStream out = new FileOutputStream(picture);

				out.write(foto);
				out.close();

				thumbnail = BitmapFactory.decodeByteArray(foto, 0, foto.length);
				final Bitmap imagenfinal = BitmapFactory.decodeByteArray(foto,0, foto.length);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

			
		}
	
	
	
	private boolean checkExternalMedia() {

		boolean mExternalStorageAvailable = false;
		boolean mExternalStorageWriteable = false;
		String state = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED.equals(state)) {
			// We can read and write the media
			mExternalStorageAvailable = mExternalStorageWriteable = true;
		} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
			// We can only read the media
			mExternalStorageAvailable = true;
			mExternalStorageWriteable = false;
		} else if (Environment.MEDIA_SHARED.equals(state)) {
			mExternalStorageAvailable = mExternalStorageWriteable = false;
		} else {
			// Something else is wrong. It may be one of many other states, but
			// all we need to know is we can neither read nor write
			Log.i("err", "State=" + state + " Not good");
			mExternalStorageAvailable = mExternalStorageWriteable = false;
		}
		Log.i("UTIL", "Available=" + mExternalStorageAvailable + " Writeable="
				+ mExternalStorageWriteable + " State" + state);

		return (mExternalStorageAvailable && mExternalStorageWriteable);
	}
	
	private boolean validar(){
		if(editNombre.getText().toString().equals("") || editPrecio.getText().toString().equals("")){
			Toast.makeText(this, "Debe completar todos los campos", Toast.LENGTH_LONG).show();
			return false;
		}
		
		return true;
	}
}
