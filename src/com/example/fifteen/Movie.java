package com.example.fifteen;

import java.util.List;

import com.example.rottentomatoes.Appmenu;
import com.example.rottentomatoes.R;
import com.example.sixteen.ThankYou;
import com.example.fifteen.MyObject;
import android.os.Bundle;
import android.view.View;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
 
public class Movie extends Activity {
 
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
        setContentView(R.layout.activity_fifteen);
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
					if(m.equalsIgnoreCase("")||(rev.equalsIgnoreCase(""))){

                        Toast.makeText(getApplicationContext(), "Please Fill All Details", Toast.LENGTH_LONG).show();
	}
	else{
		
                        Intent thank=new Intent(getApplicationContext(),ThankYou.class);
    					startActivity(thank);

            }
      }
			}
});
         
    
        try{
             
            // instantiate database handler
            databaseH = new DatabaseHandler(Movie.this);
             
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
        databaseH.create( new MyObject("Alone") );
        databaseH.create( new MyObject("Baby") ); 
        databaseH.create( new MyObject("Dolly ki Doli") );
        databaseH.create( new MyObject("Hawaizaada") );
        databaseH.create( new MyObject("Khamoshiyan") );
        databaseH.create( new MyObject("Shamitabh") );
        databaseH.create( new MyObject("Badlapur") );
        databaseH.create( new MyObject("Dum Laga Ke Haisha") );
        databaseH.create( new MyObject("NH 10") );
        databaseH.create( new MyObject("Mr.X") );
        databaseH.create( new MyObject("Gabbar is Back") );
        databaseH.create( new MyObject("Piku") );
        databaseH.create( new MyObject("Dil Dhadakne Do") ); 
        databaseH.create( new MyObject("Hamari Aadhori Kahani") );
        databaseH.create( new MyObject("ABCD2") );
        databaseH.create( new MyObject("Guddu Rangeela") );
        databaseH.create( new MyObject("Baahubali") );
        databaseH.create( new MyObject("Bajrangi Bhaijaan") );
        databaseH.create( new MyObject("Drishyam") );
        databaseH.create( new MyObject("Manjhi") );
        databaseH.create( new MyObject("Phantom") );
        databaseH.create( new MyObject("Welcome Back") );
        databaseH.create( new MyObject("Pyaar Ka Punchnama 2") );
        databaseH.create( new MyObject("Prem Ratan Dhan Payo") );
        databaseH.create( new MyObject("Tamasha") );
        databaseH.create( new MyObject("Hate Story 3") );
        databaseH.create( new MyObject("Bajirao Mastani") );
        databaseH.create( new MyObject("Dilwale") );
         
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


