package com.example.dimon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginPage extends Activity {
	
	private EditText mPasswordInput;
	private EditText mUsernameInput;
	private TextView mErrorOutput;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sign_in);

		mPasswordInput = (EditText) findViewById(R.id.existing_password);
		mUsernameInput = (EditText) findViewById(R.id.existing_username);
		mErrorOutput = (TextView) findViewById(R.id.error_output);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	public void LogMeIn(final View v){
		v.setEnabled(false);
		ParseUser.logInInBackground(mUsernameInput.getText().toString(), mPasswordInput.getText().toString(), new LogInCallback() {
			@Override
			public void done(ParseUser user, ParseException e) {
				if (user != null) {
					Intent intent = new Intent(LoginPage.this, Test.class);
					startActivity(intent);
					finish();
				} else {
					// Signup failed. Look at the ParseException to see what happened.
					switch(e.getCode()){
					case ParseException.USERNAME_MISSING:
						mErrorOutput.setText("Sorry, you must supply a username to login.");
						break;
					case ParseException.PASSWORD_MISSING:
						mErrorOutput.setText("Sorry, you must supply a password to login.");
						break;
					case ParseException.OBJECT_NOT_FOUND:
						mErrorOutput.setText("Sorry, those credentials were invalid.");
						break;
					default:
						mErrorOutput.setText(e.getLocalizedMessage()); //I'm not sure what this does, but Parse recommended having this
						break;
					}
					v.setEnabled(true);
				}
			}
		});
	}

	public void gotoCreate(View v) {
		Intent intent = new Intent(this, MakeAccount.class);
		startActivity(intent);
		finish();
	}
}
