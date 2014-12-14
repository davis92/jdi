package com.example.dimon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;

public class SplashActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) { //this code was taken from online and modified to
	    super.onCreate(savedInstanceState);				//fit the preferences of our project
	    setContentView(R.layout.splash_activity);
	    Handler handler = new Handler();
	    handler.postDelayed(new Runnable() {
	        @Override
	        public void run() {
	            Intent openTest =  new Intent(SplashActivity.this, Test.class);
	            startActivity(openTest);
	            finish();

	        }
	    }, 2500);    
	}
}