package com.example.sixteen;

import java.util.List;

import com.example.rottentomatoes.Appmenu;
import com.example.rottentomatoes.R;
import com.example.sixteen.MyObject;
import android.os.Bundle;
import android.view.View;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
	 
	public class MovieActivity extends Activity {
	 
		private Button bbutton;
		private Button sbutton;
		private EditText review;
		
    /*
     * Change to type CustomAutoCompleteView instead of AutoCompleteTextView 
     * since we are extending to customize the view and disable filter
     * The same with the XML view, type will be CustomAutoCompleteView
     */
    CustomAutoCompleteView myAutoComplete;
     
    // adapter for auto-complete
    ArrayAdapter<String> myAdapter;
     
    // for database operations
    DatabaseHandler databaseH;
     
    // just to add some initial value
    String[] item = new String[] {"Please search..."};
     
    @Override
    protected void onCreate(Bundle savedInstanceState) {
         
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sixteen);
        bbutton=(Button)findViewById(R.id.button2);
        sbutton=(Button)findViewById(R.id.button1);
        review=(EditText)findViewById(R.id.editText1);
        
        bbutton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i=new Intent(getApplicationContext(),Appmenu.class);
				startActivity(i);
			}
		});
         sbutton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String m=myAutoComplete.getText().toString();
				String rev=review.getText().toString();
				if(m.equalsIgnoreCase("")||(rev.equalsIgnoreCase(""))){

	    	                          Toast.makeText(getApplicationContext(), "Please Fill All Details", Toast.LENGTH_LONG).show();
				}
				else{
					
	    	                          Intent thank=new Intent(getApplicationContext(),ThankYou.class);
	    	      					startActivity(thank);

	    	              }
	    	        }
         });
				
        try{
             
            // instantiate database handler
            databaseH = new DatabaseHandler(MovieActivity.this);
             
            // put sample data to database
            insertSampleData();
            
            myAutoComplete = (CustomAutoCompleteView) findViewById(R.id.myautocomplete);

             
            // add the listener so it will tries to suggest while the user types
            myAutoComplete.addTextChangedListener(new CustomAutoCompleteTextChangedListener(this));
             
            // set our adapter
            myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, item);
            myAutoComplete.setAdapter(myAdapter);
         
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	     
	    public void insertSampleData(){
	        
	        // CREATE
	        databaseH.create( new MyObject("Wazir") );
	        databaseH.create( new MyObject("Airlift") ); 
	        databaseH.create( new MyObject("Kyaa Kool Hain Hum 3") );
	        databaseH.create( new MyObject("Mastizaade") );
	        databaseH.create( new MyObject("Sanam Teri Kasam") );
	        databaseH.create( new MyObject("Fitoor") );
	        databaseH.create( new MyObject("Sanam Re") );
	        databaseH.create( new MyObject("Neerja") );
	        databaseH.create( new MyObject("Kapoor and Sons") );
	        databaseH.create( new MyObject("Rocky Handsome") );
	        databaseH.create( new MyObject("Ki and Ka ") );
	        databaseH.create( new MyObject("Fan") );
	        databaseH.create( new MyObject("Azhar") ); 
	        databaseH.create( new MyObject("HouseFull 3") );
	        databaseH.create( new MyObject("Jagga Jasoos") );
	        databaseH.create( new MyObject("Do Lafzo Ki Kahani") );
	        databaseH.create( new MyObject("Raees") );
	        databaseH.create( new MyObject("Sultan") );
	        databaseH.create( new MyObject("Dishoom") );
	        databaseH.create( new MyObject("Phir Hera Pheri 3") );
	        databaseH.create( new MyObject("M.S Dhoni: The Untold Story") );
	        databaseH.create( new MyObject("Baar Baar Dekho") );
	        databaseH.create( new MyObject("Ae Dil Hai Mushkil") );
	        databaseH.create( new MyObject("Rock On 2") );
	        databaseH.create( new MyObject("Dangal") );
	         
	    }
	     
	    // this function is used in CustomAutoCompleteTextChangedListener.java
	    public String[] getItemsFromDb(String searchTerm){
	         
	        // add items on the array dynamically
	        List<MyObject> products = databaseH.read(searchTerm);
	        int rowCount = products.size();
	         
	        String[] item = new String[rowCount];
	        int x = 0;
	         
	        for (MyObject record : products) {
	             
	            item[x] = record.objectName;
	            x++;
	        }
	         
	        return item;
	    }
	 
	}

