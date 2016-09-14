package com.example.rottentomatoes;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import com.example.rottentomatoes.DatabaseAdapter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
/**
 * The main application activity which serves as a login page. 
 * @author Andrei
 *
 */
public class LoginActivity extends Activity {
	
	public static final String MY_PREFS = "SharedPreferences";
	private DatabaseAdapter dbHelper;
	private EditText theUsername;
	private EditText thePassword;
	private Button loginButton;
	private Button registerButton;
	private Button clearButton;
	private	CheckBox rememberDetails;
	
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        SharedPreferences mySharedPreferences = getSharedPreferences(MY_PREFS, 0);
        Editor editor = mySharedPreferences.edit();
        editor.putLong("uid", 0);
        editor.commit();
        
        dbHelper = new DatabaseAdapter(this);
        dbHelper.open();
        
        setContentView(R.layout.activity_login);
        initControls();
    }
    
    private void initControls() {
    	//Set the activity layout.
    	theUsername = (EditText) findViewById(R.id.Username);
    	thePassword = (EditText) findViewById(R.id.Password);
    	loginButton = (Button) findViewById(R.id.Login);
    	registerButton = (Button) findViewById(R.id.Register);
    	clearButton = (Button) findViewById(R.id.Clear);
    	rememberDetails = (CheckBox) findViewById(R.id.RememberMe);
    	
    	//Create touch listeners for all buttons.
    	loginButton.setOnClickListener(new Button.OnClickListener(){
    		public void onClick (View v){
    			LogMeIn(v);
    		}
    	});
    	
    	registerButton.setOnClickListener(new Button.OnClickListener(){
    		public void onClick (View v){
    			Register(v);
    		}
    	});
    	
    	clearButton.setOnClickListener(new Button.OnClickListener(){
    		public void onClick (View v){
    			ClearForm();
    		}
    	});
    	
    	//Create remember password check box listener.
    	rememberDetails.setOnClickListener(new CheckBox.OnClickListener(){
    		public void onClick (View v){
    			RememberMe();
    		}
    	});
    	
    	//Handle remember password preferences.
    	SharedPreferences prefs = getSharedPreferences(MY_PREFS, 0);
    	String thisUsername = prefs.getString("username", "");
    	String thisPassword = prefs.getString("password", "");
    	boolean thisRemember = prefs.getBoolean("remember", false);
    	if(thisRemember) {
    		theUsername.setText(thisUsername);
    		thePassword.setText(thisPassword);
    		rememberDetails.setChecked(thisRemember);
    	}
    	
    }
    
    /**
     * Deals with Exit option - exits the application.
     */
    
    /**
     * Clears the login form.
     */
    private void ClearForm() {
    	saveLoggedInUId(0,"","");
    	theUsername.setText("");
    	thePassword.setText("");
    }
    
    /**
     * Handles the remember password option.
     */
    private void RememberMe() {
    	boolean thisRemember = rememberDetails.isChecked();
    	SharedPreferences prefs = getSharedPreferences(MY_PREFS, 0);
    	Editor editor = prefs.edit();
    	editor.putBoolean("remember", thisRemember);
    	editor.commit();
    }
    
    /**
     * This method handles the user login process.  
     * @param v
     */
    private void LogMeIn(View v) {
    	//Get the username and password
    	String thisUsername = theUsername.getText().toString();
    	String thisPassword = thePassword.getText().toString();
    	
    	//Assign the hash to the password
    	thisPassword = md5(thisPassword);
    	
    	// Check the existing user name and password database
    	Cursor theUser = dbHelper.fetchUser(thisUsername, thisPassword);
    	if (theUser != null) {
    		if (theUser.getCount() > 0) {
    			saveLoggedInUId(theUser.getLong(theUser.getColumnIndex(DatabaseAdapter.COL_ID)), thisUsername, thePassword.getText().toString());
    		    theUser.close();
    		    final ProgressDialog myPd_ring=ProgressDialog.show(LoginActivity.this, "Please wait", "Loading please wait..", true);
    	        myPd_ring.setCancelable(true);
    	        new Thread(new Runnable() {  
    	              @Override
    	              public void run() {
    	                    // TODO Auto-generated method stub
    	                    try
    	                    {
    	                          Thread.sleep(5000);
    	              		    Intent menu = new Intent(getApplicationContext(), Appmenu.class);
    	            		    startActivity(menu);
    	                    }catch(Exception e){}
    	                    myPd_ring.dismiss();
    	              }
    	        }).start();
    		}
    		
    		//Returns appropriate message if no match is made
    		else {
    			 final ProgressDialog myPd_ring=ProgressDialog.show(LoginActivity.this, "Please wait", "Loading please wait..", true);
     	        myPd_ring.setCancelable(true);
     	        new Thread(new Runnable() {  
     	              @Override
     	              public void run() {
     	                    // TODO Auto-generated method stub
     	                    try
     	                    {
     	                          Thread.sleep(5000);
     	             			Toast.makeText(getApplicationContext(), 
     	           					"You have entered an incorrect username or password.", 
     	           					Toast.LENGTH_LONG).show();
     	           			saveLoggedInUId(0, "", "");
     	                    }catch(Exception e){}
     	                    myPd_ring.dismiss();
     	              }
     	        }).start();
    		}
    		theUser.close();
    	}
    	
    	else {
			 final ProgressDialog myPd_ring=ProgressDialog.show(LoginActivity.this, "Please wait", "Loading please wait..", true);
  	        myPd_ring.setCancelable(true);
  	        new Thread(new Runnable() {  
  	              @Override
  	              public void run() {
  	                    // TODO Auto-generated method stub
  	                    try
  	                    {
  	                          Thread.sleep(5000);
  	                		Toast.makeText(getApplicationContext(), 
  	              				"Database query error", 
  	              				Toast.LENGTH_LONG).show();
  	           			saveLoggedInUId(0, "", "");
  	                    }catch(Exception e){}
  	                    myPd_ring.dismiss();
  	              }
  	        }).start();
    	}
    }
    
    /**
     * Open the Registration activity.
     * @param v
     */
    private void Register(View v)
    {
    	final ProgressDialog myPd_ring=ProgressDialog.show(LoginActivity.this, "Please wait", "Loading please wait..", true);
        myPd_ring.setCancelable(true);
        new Thread(new Runnable() {  
              @Override
              public void run() {
                    // TODO Auto-generated method stub
                    try
                    {
                          Thread.sleep(5000);
                      	Intent i = new Intent(getApplicationContext(), Register.class);
                    	startActivity(i);
                    }catch(Exception e){}
                    myPd_ring.dismiss();
              }
        }).start();
    }
    
    private void saveLoggedInUId(long id, String username, String password) {
    	SharedPreferences settings = getSharedPreferences(MY_PREFS, 0);
    	Editor myEditor = settings.edit();
    	myEditor.putLong("uid", id);
    	myEditor.putString("username", username);
    	myEditor.putString("password", password);
    	boolean rememberThis = rememberDetails.isChecked();
    	myEditor.putBoolean("rememberThis", rememberThis);
    	myEditor.commit();
    }
    
    /**
	 * Deals with the password encryption. 
	 * @param s The password.
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
    	} 
    	
    	catch (NoSuchAlgorithmException e) {
    		return s;
    	}
    }
}