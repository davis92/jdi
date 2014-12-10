package com.example.dimon;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.v4.app.DialogFragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
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

@SuppressLint("NewApi")
public class MainActivity extends Activity implements OnItemClickListener {

	private EditText mTaskInput; //this describes what is in the textbox field
	private ListView mListView;// this is the view that is under textbox
	private TaskAdapter mAdapter;//this is used to covert EditText into ListView
	Button button;
	Intent intent;
	Button deleteButton;
	int backgroundColor;
	int yellow = Color.YELLOW;
	Task task;
	Task remove;
	TextView taskDescription;
	List<Task> mainList = new ArrayList<Task>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Parse.initialize(this, "qVN3MRKBp42CrVX7AwN8sriRt7gwHtyOqAysl90R", "51k427k2YqLC5sWOAK5oKeUYTAydABJgc9UdtoDc");
		ParseObject.registerSubclass(Task.class);
		
		//the following code gets who the current user and if thee is no user (signing out) then the app user is sent to the beginning og the app (the login)
		ParseUser currentUser = ParseUser.getCurrentUser();
		if(currentUser == null){
		  Intent intent = new Intent(this, LoginPage.class);
		  startActivity(intent);
		  finish();
		}
		
		mTaskInput = (EditText) findViewById(R.id.task_input);
		mListView = (ListView) findViewById(R.id.task_list);
		
		mAdapter = new TaskAdapter(this, mainList);
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(this);
		
		updateData();
		addListenerOnButton();
		
		deleteButton = (Button) findViewById(R.id.deleteButton);
		deleteButton.setOnLongClickListener(new View.OnLongClickListener() { //sets up delete all function upon 
			public boolean onLongClick(View v) {                            //holding down on the delete button
				mAdapter.clear();
				mainList.removeAll(mainList);
				return true;
			}
		});
	}
	
	public void addListenerOnButton() 
	{
		 
		button = (Button) findViewById(R.id.button1);
 
		button.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) 
			
			{
			    intent = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(intent);   
 
			}
 
		});
 
	}
	
	//got these from one of the Parse tutorial
	
	public void updateData(){
		  ParseQuery<Task> query = ParseQuery.getQuery(Task.class);
		  query.whereEqualTo("user", ParseUser.getCurrentUser()); //this is an if statement for parse to tell it where to 
		  query.setCachePolicy(CachePolicy.CACHE_THEN_NETWORK);//taken from parse directly
		  query.findInBackground(new FindCallback<Task>() {
			  
			  //taken from parse directly
		          
		      @Override
		      public void done(List<Task> t2, ParseException error) {
		          if(t2 != null){
		              mAdapter.clear();
		              mAdapter.addAll(t2);
		          }
		      }
		  });
		}
	
	//this was gotten from google
	
	public void createTask(View v) 
	{
	      if (mTaskInput.getText().length() >= 1){
	          Task t = new Task();
	          t.setACL(new ParseACL(ParseUser.getCurrentUser()));
	          t.setUser(ParseUser.getCurrentUser());
	          t.setDescription(mTaskInput.getText().toString());
	          t.setCompleted(false);
	          t.saveEventually();
	          mAdapter.insert(t, 0);
	          mTaskInput.setText("");
	      }
	  }
	

	@Override 
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) { //from parse tutorial
	  task = mAdapter.getItem(position);
	  taskDescription = (TextView) view.findViewById(R.id.task_description);
	  task.setCompleted(!task.isCompleted());
	  if (task.isCompleted()){ //checks to see if button has been clicked
		  taskDescription.setBackgroundColor(Color.YELLOW); //changes background color to yellow
		  backgroundColor = Color.YELLOW;
	  }
	  else{ //returns item view to original color
		  taskDescription.setBackgroundColor(Color.TRANSPARENT);
		  backgroundColor = Color.TRANSPARENT;
	  }
	}
	
	public void deleteTask(View v){ //connected to action upon clicking delete button
		if (backgroundColor == yellow){ //checks to see if item's background has been changed (therefore clicked)
			mAdapter.remove(task);
			task.deleteEventually();
			taskDescription.setBackgroundColor(Color.TRANSPARENT);
		}
	}
	
	//public void showDatePickerDialog(View v) {
	    //DialogFragment newFragment = new DatePickerFragment();
	    //newFragment.show(getFragmentManager(), "datePicker");
	//}
	
	///this is the sign out function. A few bugs I'll figure this out if we have time.
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	//this signs person out or makes them close the app or both
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) 
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
