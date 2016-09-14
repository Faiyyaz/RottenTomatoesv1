package com.example.rottentomatoes;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Moremovies1 extends Activity {
	
	Button pmovies,back;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mm1);
		pmovies=(Button)findViewById(R.id.button2);
		back=(Button)findViewById(R.id.button1);
		
		pmovies.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				final ProgressDialog myPd_ring=ProgressDialog.show(Moremovies1.this, "Please wait", "Loading please wait..", true);
    	        myPd_ring.setCancelable(true);
    	        new Thread(new Runnable() {  
    	              @Override
    	              public void run() {
    	                    // TODO Auto-generated method stub
    	                    try
    	                    {
    	                          Thread.sleep(5000);
    	          				Intent pm=new Intent(getApplicationContext(),availmovies.class);
    	        				startActivity(pm);
    	                    }catch(Exception e){}
    	                    myPd_ring.dismiss();
    	              }
    	        }).start();
			}
		});
		back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent back=new Intent(getApplicationContext(),Appmenu.class);
				startActivity(back);
			}
		});
}
}