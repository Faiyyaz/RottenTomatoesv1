package com.example.rottentomatoes;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Appmenu extends Activity{

	Button movies2016,movies2015,availmovies,rate,exit;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.appmenu);
		movies2016=(Button)findViewById(R.id.movies2016);
		movies2015=(Button)findViewById(R.id.movies2015);
		availmovies=(Button)findViewById(R.id.availmovies);
		rate=(Button)findViewById(R.id.rate);
		exit=(Button)findViewById(R.id.exit);
		AlertDialog.Builder b=new AlertDialog.Builder(this);
		b.setTitle("Please Read This");
		b.setMessage("Please See the list of available movies to rate in this app by going to List of available movies menu. For instructions about how to rate, please go to how to rate movies menu.");
		b.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {

				dialog.dismiss();
			}
		});
		b.create();
		b.show();
	
		movies2016.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				final ProgressDialog myPd_ring=ProgressDialog.show(Appmenu.this, "Please wait", "Loading please wait..", true);
    	        myPd_ring.setCancelable(true);
    	        new Thread(new Runnable() {  
    	              @Override
    	              public void run() {
    	                    // TODO Auto-generated method stub
    	                    try
    	                    {
    	                          Thread.sleep(5000);
    	          				Intent m16=new Intent(getApplicationContext(),com.example.sixteen.MovieActivity.class);
    	        				startActivity(m16);
    	                    }catch(Exception e){}
    	                    myPd_ring.dismiss();
    	              }
    	        }).start();
			}
		});
		
		movies2015.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
		
				final ProgressDialog myPd_ring=ProgressDialog.show(Appmenu.this, "Please wait", "Loading please wait..", true);
    	        myPd_ring.setCancelable(true);
    	        new Thread(new Runnable() {  
    	              @Override
    	              public void run() {
    	                    // TODO Auto-generated method stub
    	                    try
    	                    {
    	                          Thread.sleep(5000);
    	          				Intent m15=new Intent(getApplicationContext(),com.example.fifteen.Movie.class);
    	        				startActivity(m15);
    	                    }catch(Exception e){}
    	                    myPd_ring.dismiss();
    	              }
    	        }).start();
			}
		});
		
		availmovies.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {

				final ProgressDialog myPd_ring=ProgressDialog.show(Appmenu.this, "Please wait", "Loading please wait..", true);
    	        myPd_ring.setCancelable(true);
    	        new Thread(new Runnable() {  
    	              @Override
    	              public void run() {
    	                    // TODO Auto-generated method stub
    	                    try
    	                    {
    	                          Thread.sleep(5000);
    	          				Intent am=new Intent(getApplicationContext(),availmovies.class);
    	        				startActivity(am);
    	                    }catch(Exception e){}
    	                    myPd_ring.dismiss();
    	              }
    	        }).start();
			}
		});
		
		rate.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {

				Intent rate=new Intent(getApplicationContext(),rate.class);
				startActivity(rate);
			}
		});
		
		exit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				AlertDialog.Builder b=new AlertDialog.Builder(Appmenu.this);
				b.setTitle("Quit");
				b.setMessage("Do You Want to Go Back?");
				b.setNegativeButton("No",null);
				b.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {

						Intent exit=new Intent(getApplicationContext(),LoginActivity.class);
						startActivity(exit);
						
					}
				});
				b.show();
			}
		});
}
}