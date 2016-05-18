package com.example.bar.db;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.example.bar.model.Categoria;
import com.example.bar.model.Cliente;
import com.example.bar.model.Mesa;
import com.example.bar.model.Pedido;
import com.example.bar.model.Producto;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

	private static final String DATABASE_NAME = "Ludovico.sqlite";
	private static final int DATABASE_VERSION = 1;
	private static String DB_PATH = "/data/data/com.example.bar/databases/";
	private SQLiteDatabase myDataBase;
	private final Context myContext;

	private Dao<Producto, Integer> productoDao = null;
	private Dao<Categoria, Integer> categoriaDao = null;
	private Dao<Pedido, Integer> pedidoDao = null;
	private Dao<Mesa, Integer> mesaDao = null;
	private Dao<Cliente, Integer> clienteDao = null;
	
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.myContext = context;
	}

	@Override
	public void onCreate(SQLiteDatabase database,
			ConnectionSource connectionSource) {
					
	

		/*
		 * try { TableUtils.createTable(connectionSource, Producto.class);
		 * TableUtils.createTable(connectionSource, Categoria.class);
		 * TableUtils.createTable(connectionSource, Pedido.class);
		 * TableUtils.createTable(connectionSource, Mesa.class);
		 * 
		 * } catch (SQLException e) { Log.e(DatabaseHelper.class.getName(),
		 * "Can't create database", e); throw new RuntimeException(e); } catch
		 * (java.sql.SQLException e) { e.printStackTrace(); }
		 */

	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, ConnectionSource arg1, int arg2,
			int arg3) {
		// TODO Auto-generated method stub

	}

	public void createDataBase() throws IOException {

		boolean dbExist = checkDataBase();

		if (dbExist) {
			// do nothing - database already exist
		} else {

			// By calling this method and empty database will be created into
			// the default system path
			// of your application so we are gonna be able to overwrite that
			// database with our database.
			this.getReadableDatabase();

			try {

				copyDataBase();

			} catch (IOException e) {

				throw new Error("Error copying database");

			}
		}

	}

	/**
	 * Check if the database already exist to avoid re-copying the file each
	 * time you open the application.
	 * 
	 * @return true if it exists, false if it doesn't
	 */
	private boolean checkDataBase() {

		SQLiteDatabase checkDB = null;

		try {
			String myPath = DB_PATH + DATABASE_NAME;
			checkDB = SQLiteDatabase.openDatabase(myPath, null,
					SQLiteDatabase.OPEN_READONLY);

		} catch (SQLiteException e) {

			// database does't exist yet.

		}

		if (checkDB != null) {

			checkDB.close();

		}

		return checkDB != null ? true : false;
	}

	/**
	 * Copies your database from your local assets-folder to the just created
	 * empty database in the system folder, from where it can be accessed and
	 * handled. This is done by transfering bytestream.
	 * */
	private void copyDataBase() throws IOException {

		// Open your local db as the input stream
		InputStream myInput = myContext.getAssets().open(DATABASE_NAME);

		// Path to the just created empty db
		String outFileName = DB_PATH + DATABASE_NAME;

		// Open the empty db as the output stream
		OutputStream myOutput = new FileOutputStream(outFileName);

		// transfer bytes from the inputfile to the outputfile
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer)) > 0) {
			myOutput.write(buffer, 0, length);
		}

		// Close the streams
		myOutput.flush();
		myOutput.close();
		myInput.close();

	}

	public void openDataBase() throws SQLException {

		// Open the database
		String myPath = DB_PATH + DATABASE_NAME;
		myDataBase = SQLiteDatabase.openDatabase(myPath, null,
				SQLiteDatabase.OPEN_READONLY);

	}

	@Override
	public synchronized void close() {

		if (myDataBase != null)
			myDataBase.close();

		super.close();

	}

	public Dao<Producto, Integer> getProductoDao() {
		if (null == productoDao) {
			try {
				productoDao = getDao(Producto.class);
			} catch (java.sql.SQLException e) {
				e.printStackTrace();
			}
		}
		return productoDao;
	}

	public Dao<Categoria, Integer> getCategoriaDao() {
		if (null == categoriaDao) {
			try {
				categoriaDao = getDao(Categoria.class);
			} catch (java.sql.SQLException e) {
				e.printStackTrace();
			}
		}
		return categoriaDao;
	}

	public Dao<Pedido, Integer> getPedidoDao() {
		if (null == pedidoDao) {
			try {
				pedidoDao = getDao(Pedido.class);
			} catch (java.sql.SQLException e) {
				e.printStackTrace();
			}
		}
		return pedidoDao;
	}

	public Dao<Mesa, Integer> getMesaDao() {
		if (null == mesaDao) {
			try {
				mesaDao = getDao(Mesa.class);
			} catch (java.sql.SQLException e) {
				e.printStackTrace();
			}
		}
		return mesaDao;
	}
	
	public Dao<Cliente, Integer> getClienteDao() {
		if (null == clienteDao) {
			try {
				clienteDao = getDao(Cliente.class);
			} catch (java.sql.SQLException e) {
				e.printStackTrace();
			}
		}
		return clienteDao;
	}

}
