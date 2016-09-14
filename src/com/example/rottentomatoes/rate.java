package com.example.rottentomatoes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class rate extends Activity {

	Button back;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rate);
		back=(Button)findViewById(R.id.button1);
		back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {

				Intent backward=new Intent(getApplicationContext(),Appmenu.class);
				startActivity(backward);
			}
		});
}
}