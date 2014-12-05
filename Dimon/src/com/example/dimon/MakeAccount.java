package com.example.dimon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class MakeAccount extends Activity {
	
	private EditText mPasswordInput;
	private EditText mUsernameInput;
	private TextView mErrorOutput;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sign_up);
		
		mPasswordInput = (EditText) findViewById (R.id.new_password);
		mUsernameInput = (EditText) findViewById (R.id.new_username);
		mErrorOutput = (TextView) findViewById (R.id.error_output);
		
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create, menu);
		return true;
	}
	
	public void CreateAccount (final View v){
		  if(mUsernameInput.getText().length() == 0 || mPasswordInput.getText().length() == 0)
		      return;

		  //This is from how to use parse tutorial. We coded it, but had to use a tutorial to know what how to get our app to do what we wanted it to do with parse
		  v.setEnabled(false);
		  ParseUser user = new ParseUser();
		  user.setUsername(mUsernameInput.getText().toString());
		  user.setPassword(mPasswordInput.getText().toString());
		  mErrorOutput.setText("");

		  //this is from parse and an import, we modified it to our needs, but the basis for how we learned the commands is from parse tutorial
		  user.signUpInBackground(new SignUpCallback() {
		      @Override
		      public void done(ParseException e) {
		          if (e == null) {
		              Intent intent = new Intent(MakeAccount.this, Test.class);
		              //note to self - add a popup later if time permits to say you've been registered
		              startActivity(intent);
		              finish();
		          } else {
		              // Sign up didn't succeed. ParseException pops up and allows user to see where mistake is
							// Sign up didn't succeed. Look at the ParseException
							// to figure out what went wrong
							// I learned how to do this from a parse tutorial and modifided my code to match our group needs
							switch(e.getCode()){
							case ParseException.USERNAME_TAKEN:
								mErrorOutput.setText("Sorry, this username has already been taken.");
								break;
							case ParseException.USERNAME_MISSING:
								mErrorOutput.setText("Sorry, you must type a username to register.");
								break;
							case ParseException.PASSWORD_MISSING:
								mErrorOutput.setText("Sorry, you must type a password to register.");
								break;
							default: //this is taken from parse with no modification (not sure what it does actually, but it was recommended to have by parse
								mErrorOutput.setText(e.getLocalizedMessage());
							}
		              v.setEnabled(true);
		          }
		      }
		  });}
			public void gotoLogin(View v) {
				Intent intent = new Intent(this, LoginPage.class);
				startActivity(intent);
				finish();
	}
}
