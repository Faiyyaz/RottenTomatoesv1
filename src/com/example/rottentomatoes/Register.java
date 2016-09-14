package com.example.rottentomatoes;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.rottentomatoes.DatabaseAdapter;
/**
 * Handles the user registration activity.
 * 
 */
public class Register extends Activity {
	private EditText newUsername;
	private EditText newPassword;
	private EditText newConfiPass;
	private Button registerButton;
	private Button clearButton;
	private Button backButton;
	
	private DatabaseAdapter dbHelper;
	
	 @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	        
        SharedPreferences settings = getSharedPreferences(LoginActivity.MY_PREFS, 0);
        Editor editor = settings.edit();
        editor.putLong("uid", 0);
        editor.commit();
        
        dbHelper = new DatabaseAdapter(this);
        dbHelper.open();
        setContentView(R.layout.register);
        initControls();
	    }
	 
	 /**
	  * Handles interface controls.
	 */
	 private void initControls()
	    {
		 	newUsername = (EditText) findViewById(R.id.nUsername);
		 	newPassword = (EditText) findViewById(R.id.nPassword);
		 	newConfiPass = (EditText) findViewById(R.id.nConfiPass);
		 	registerButton = (Button) findViewById(R.id.nRegister);
		 	clearButton = (Button) findViewById(R.id.nClear);
		 	backButton = (Button) findViewById(R.id.nBack);
	    	
		 	registerButton.setOnClickListener(new Button.OnClickListener() { 
		 		public void onClick (View v){ 
		 			RegisterMe(v); }});
		 	
		 	clearButton.setOnClickListener(new Button.OnClickListener() { 
	    		public void onClick (View v){ 
	    			ClearForm(); }});
	    	
		 	backButton.setOnClickListener(new Button.OnClickListener() { 
	    		public void onClick (View v){ 
	    			BackToLogin(); }});
	    }
	 
	 /**
     * Clears the registration fields.
     */
	 private void ClearForm()
	    {
	    	saveLoggedInUId(0, "", "");
	    	newUsername.setText("");
	    	newPassword.setText("");
	    	newConfiPass.setText("");
	    }
	 
	 /**
	  * Takes user back to login.
	  */
	  private void BackToLogin()
	    {
	    	finish();
	    }
	 
	  /**
	   * Handles the registration process.
	   * @param v
	   */
	 private void RegisterMe(View v)
	    {
		 	//Get user details. 
	    	String username = newUsername.getText().toString();
	    	String password = newPassword.getText().toString();
	    	String confirmpassword = newConfiPass.getText().toString();
	    	
	    	//Check if all fields have been completed.
	    	if (username.equals("") || password.equals("")){
	    		final ProgressDialog myPd_ring=ProgressDialog.show(Register.this, "Please wait", "Loading please wait..", true);
    	        myPd_ring.setCancelable(true);
    	        new Thread(new Runnable() {  
    	              @Override
    	              public void run() {
    	                    // TODO Auto-generated method stub
    	                    try
    	                    {
    	                          Thread.sleep(5000);
    	          	    		Toast.makeText(getApplicationContext(), 
    	        	    				"Please ensure all fields have been completed.",
    	        				          Toast.LENGTH_LONG).show();
    	        	  		return;
    	                    }catch(Exception e){}
    	                    myPd_ring.dismiss();
    	              }
    	        }).start();
	    	}
	    	
	    	//Check password match. 
	    	if (!password.equals(confirmpassword)) {
	    		
	    		Toast.makeText(getApplicationContext(), 
	    				"The password does not match.",
				          	Toast.LENGTH_LONG).show();
	    					newPassword.setText("");
	    					newConfiPass.setText("");
	    		return;
	    	}
	    	
	    	//Encrypt password with MD5.
	    	password = md5(password);
	    	
	    	//Check database for existing users.
	    	Cursor user = dbHelper.fetchUser(username, password);
	    	if (user == null) {
	    		Toast.makeText(getApplicationContext(), "Database query error",
				          Toast.LENGTH_LONG).show();
	    	} else {
	    		
	    		//Check for duplicate usernames
	    		if (user.getCount() > 0) {
	    			Toast.makeText(getApplicationContext(), "The username is already registered",
	  			          Toast.LENGTH_LONG).show();
	        		user.close();
	    			return;
	    		}
	    		user.close();
	    		user = dbHelper.fetchUser(username, password);
	        	if (user == null) {
	        		Toast.makeText(getApplicationContext(), "Database query error",
	  			          Toast.LENGTH_LONG).show();
	        		return;
	        	} else {
	        		
	        		if (user.getCount() > 0) {
	        			Toast.makeText(getApplicationContext(), "The username is already registered",
	        			          Toast.LENGTH_LONG).show();
	            		user.close();
	        			return;
	        		}
	        		user.close();
	        	}
	        	//Create the new username.
	    		long id = dbHelper.createUser(username, password);
	    		if (id > 0) {
	    			Toast.makeText(getApplicationContext(), "Your username was created",
	    			          Toast.LENGTH_LONG).show();
	    			saveLoggedInUId(id, username, newPassword.getText().toString());
		    		finish();
	    		} else {
	    			Toast.makeText(getApplicationContext(), "Failed to create new username",
	    			          Toast.LENGTH_LONG).show();
	    		}
	    	}
	    }
	 
	 private void saveLoggedInUId(long id, String username, String password) {
			SharedPreferences settings = getSharedPreferences(LoginActivity.MY_PREFS, 0);
			Editor editor = settings.edit();
			editor.putLong("uid", id);
			editor.putString("username", username);
			editor.putString("password", password);
			editor.commit();
	}
	/**
	 * Hashes the password with MD5.  
	 * @param s
	 * @return
	 */
	private String md5(String s) {
	    try {
	     
	        MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
	        digest.update(s.getBytes());
	        byte messageDigest[] = digest.digest();

	        
	        StringBuffer hexString = new StringBuffer();
	        for (int i=0; i<messageDigest.length; i++)
	            hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
	        return hexString.toString();

	    } catch (NoSuchAlgorithmException e) {
	        return s;
	    }
	}
}

