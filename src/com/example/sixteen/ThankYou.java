package com.example.sixteen;

import com.example.rottentomatoes.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class ThankYou extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.thank);
		Toast.makeText(getApplicationContext(), "Review Submitted", Toast.LENGTH_LONG).show();
}
}