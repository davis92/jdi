package com.example.dimon;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQuery.CachePolicy;
import com.parse.ParseUser;

public class MainActivity2 extends Activity implements OnItemClickListener {

	private EditText mTaskInput2; //this describes what is in the textbox field
	private ListView mListView2;// this is the view that is under textbox
	private TaskAdapterOwedToMe mAdapter2;//this is used to covert EditText into ListView
	Button button3;
	Intent intent;
	Button deleteButton;
	int backgroundColor;
	int cyan = Color.CYAN;
	TaskOwedToMe task;
	TextView taskDescription2;
	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.owe_me);
		
		Parse.initialize(this, "qVN3MRKBp42CrVX7AwN8sriRt7gwHtyOqAysl90R", "51k427k2YqLC5sWOAK5oKeUYTAydABJgc9UdtoDc");
		ParseObject.registerSubclass(TaskOwedToMe.class);
		
		//the following code gets who the current user and if there is no user (signing out) then the app user is sent to the beginning og the app (the login)
		ParseUser currentUser = ParseUser.getCurrentUser();
		if(currentUser == null){
		  Intent intent = new Intent(this, LoginPage.class);
		  startActivity(intent);
		  finish();
		}
		
		mTaskInput2 = (EditText) findViewById(R.id.task_input2);
		mListView2 = (ListView) findViewById(R.id.task_list2);
		
		mAdapter2 = new TaskAdapterOwedToMe (this, new ArrayList<TaskOwedToMe>()); //Davis to Davis - fix this later
		mListView2.setAdapter(mAdapter2);
		mListView2.setOnItemClickListener(this);
		
		updateData();
		addListenerOnButton();
		
		
		
		
	}
	
	public void addListenerOnButton() 
	{
		 
 
		button3 = (Button) findViewById(R.id.button3);
 
		button3.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) 
			
			{
			    intent = new Intent(MainActivity2.this, MainActivity.class);
                startActivity(intent);   
 
			}
 
		});
 
	}
	
	//got these from one of the Parse tutorial
	
	public void updateData(){
		  ParseQuery<TaskOwedToMe> query = ParseQuery.getQuery(TaskOwedToMe.class);
		  query.whereEqualTo("user", ParseUser.getCurrentUser());
		  query.setCachePolicy(CachePolicy.CACHE_THEN_NETWORK);
		  query.findInBackground(new FindCallback<TaskOwedToMe>() {
		          
		      @Override
		      public void done(List<TaskOwedToMe> t1, ParseException error) {
		          if(t1 != null){
		              mAdapter2.clear();
		              mAdapter2.addAll(t1);
		          }
		      }
		  });
		}
	
	
	public void createTask(View v) 
	{
	      if (mTaskInput2.getText().length() >= 1){
	    	  TaskOwedToMe p = new TaskOwedToMe();
	          p.setACL(new ParseACL(ParseUser.getCurrentUser()));
	          p.setUser(ParseUser.getCurrentUser());
	          String description = mTaskInput2.getText().toString();
	         
	          p.setDescription(description);
	       
	          
	          p.setCompleted(false);
	          p.saveEventually();
	          mAdapter2.insert(p, 0);
	          mTaskInput2.setText("");
	      }
	  }

	

	
	
	@Override //from parse tutorial
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		  task = mAdapter2.getItem(position);
		  taskDescription2 = (TextView) view.findViewById(R.id.task_description2); //changed this from task_description
		  task.setCompleted(!task.isCompleted());
		  if (task.isCompleted()){
			  taskDescription2.setBackgroundColor(Color.CYAN);
			  backgroundColor = Color.CYAN;
		  }
		  else{
			  taskDescription2.setBackgroundColor(Color.TRANSPARENT);
			  backgroundColor = Color.TRANSPARENT;
		  }
		}
		
	public void deleteTask(View v){
		if (backgroundColor == cyan){
			mAdapter2.remove(task);
			task.deleteEventually();
		}
		deleteButton = (Button) findViewById(R.id.deleteButton);
		deleteButton.setOnLongClickListener(new View.OnLongClickListener() { //sets up delete all function upon 
			public boolean onLongClick(View v) {                            //holding down on the delete button
				mAdapter2.clear();
				task.deleteEventually();
				return true;
			}
		});
	}
	
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
			switch (item.getItemId()) //from parse guide on signing out of an user account
			{
				case R.id.action_so: 
					ParseUser.logOut();
					Intent intent = new Intent(this, LoginPage.class);
					startActivity(intent);
					finish();
					return true; 
					
				/*case R.id.close_app: 
					android.os.Process.killProcess(android.os.Process.myPid());
		            finish();
		            System.exit(0);
					return true; 
					
				case R.id.sign_close: 
					ParseUser.logOut();
					android.os.Process.killProcess(android.os.Process.myPid());
		            finish();
		            System.exit(0);
					return true;*/ //apparently it is not good practice to allow the user to quit the app from anywhere in the app. I think it is stupid, but I have included the code here so you know I know how to close out an app (or at last close the current activity) 
					
				}
			return false; 
		}
	}

