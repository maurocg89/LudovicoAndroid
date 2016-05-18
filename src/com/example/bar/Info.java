package com.example.bar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Info extends Activity{
	
	private Button btnVolver;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.info);
		
		btnVolver = (Button) findViewById(R.id.btnVolverInfo);
		
		btnVolver.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Info.this, MenuPrincipal.class);
				startActivity(i);
			}
		});
		
	}
}
