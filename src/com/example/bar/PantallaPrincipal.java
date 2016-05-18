package com.example.bar;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;

import com.example.bar.db.DatabaseManager;

// Falta probar si guarda y muestra las fotos de los productos en la lista y en el imageview
// Diseño a todas las activities


public class PantallaPrincipal extends TabActivity implements OnTabChangeListener{

	TabHost tabHost;
	Context mContext;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pantalla_principal);
		DatabaseManager.init(this);
		mContext = getApplicationContext();
		tabHost = getTabHost();
		
		tabHost.setOnTabChangedListener(this);
		
		TabHost.TabSpec spec;
		Intent intent;
		
		intent = new Intent(mContext, ActivityMesas.class);
		spec = tabHost.newTabSpec("First").setIndicator("")
                    .setContent(intent);
		
		tabHost.addTab(spec);
		
		
		intent = new Intent(this, ListaCategoria.class);
		spec = tabHost.newTabSpec("Second").setIndicator("")
                .setContent(intent);
	
		tabHost.addTab(spec);
		
		
		intent = new Intent(this, ListaProducto.class);
		spec = tabHost.newTabSpec("Third").setIndicator("")
                .setContent(intent);
		tabHost.addTab(spec);
		
		
		intent = new Intent(mContext, Info.class);
		spec = tabHost.newTabSpec("Fourth").setIndicator("")
                    .setContent(intent);
		
		tabHost.addTab(spec);
		
			
		
			
		  tabHost.getTabWidget().getChildAt(1).setBackgroundResource(R.drawable.descarga);
		  tabHost.getTabWidget().getChildAt(2).setBackgroundResource(R.drawable.food_icon);
		  tabHost.getTabWidget().getChildAt(3).setBackgroundResource(R.drawable.info);
		  // Set Tab1 as Default tab and change image	
	      tabHost.getTabWidget().setCurrentTab(0);
	      tabHost.getTabWidget().getChildAt(0).setBackgroundResource(R.drawable.mesas);
		
		
		
		
		
		
	}
	
	
	
	@Override
	public void onTabChanged(String arg0) {
		// TODO Auto-generated method stub
		
	}
}
