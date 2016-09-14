package com.example.rottentomatoes;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class availmovies extends Activity {
	
	Button mmovies,back;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.availm);
		mmovies=(Button)findViewById(R.id.button1);
		back=(Button)findViewById(R.id.button2);
		mmovies.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				final ProgressDialog myPd_ring=ProgressDialog.show(availmovies.this, "Please wait", "Loading please wait..", true);
    	        myPd_ring.setCancelable(true);
    	        new Thread(new Runnable() {  
    	              @Override
    	              public void run() {
    	                    // TODO Auto-generated method stub
    	                    try
    	                    {
    	                          Thread.sleep(5000);
    	          				Intent mm=new Intent(getApplicationContext(),Moremovies1.class);
    	        				startActivity(mm);
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