package com.example.dimon;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Test extends Activity 

{
		Button button1;
		Button button2;
		Intent intent;
	 
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.choose_your_destiny);
			addListenerOnButton1();
			addListenerOnButton2();
		}
	 
		public void addListenerOnButton1() 
		{
			 
			button1 = (Button) findViewById(R.id.button1);
	 
			button1.setOnClickListener(new View.OnClickListener() {

				public void onClick(View view) 
				
				{
					intent = new Intent(Test.this, MainActivity2.class);
	                startActivity(intent);   
	 
				}
	 
			});
	 
		}
		
		public void addListenerOnButton2() 
		{
			 
			button2 = (Button) findViewById(R.id.button2);
	 
			button2.setOnClickListener(new View.OnClickListener() {

				public void onClick(View view) 
				
				{
				    intent = new Intent(Test.this, MainActivity.class);
	                startActivity(intent);   
	 
				}
	 
			});
	 
		}
	}
