package com.example.dimon;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
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
	
	//for date
	private TextView text_date;
	//private DatePicker date_picker;
	private Button change_date_button;

	private int year;
	private int month;
	private int day;
	

	
	static final int DATE_DIALOG_ID = 100;
	
	
	
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
		
		//date
		setCurrentDate();
		addButtonListener();
		
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
	
	
	public void createTask(View v) 
	{
	      if (mTaskInput.getText().length() >= 1){
	          Task t = new Task();
	          t.setACL(new ParseACL(ParseUser.getCurrentUser()));
	          t.setUser(ParseUser.getCurrentUser());
	          String description = mTaskInput.getText().toString();
	          String Month_Name = monthname(month);
	          String due_date = "/n Due: "+ Month_Name+ " "+ day + ", "+year;
	          description = description + due_date;
	          t.setDescription(description);
	          t.setDueDate(due_date);
	     

	          t.setCompleted(false);
	          t.saveEventually();
	          mAdapter.insert(t, 0);
	          mTaskInput.setText("");
	      }
	  }
	
	public static String monthname(int m){
		 String mn = "Month Name";
		
		if (m == 0 )
        {
      	  mn="January";
        } 
        if (m == 1)
        {
      	  mn="February";
        }
        if (m == 2 )
        {
      	  mn="March";
        } 
        if (m == 3)
        {
      	  mn="April";
        }
        if (m == 4 )
        {
      	  mn="May";
        } 
        if (m == 5)
        {
      	  mn="June";
        }
        if (m == 6 )
        {
      	  mn="July";
        } 
        if (m == 7)
        {
      	  mn="August";
        }
        if (m == 8)
        {
      	  mn="September";
        }
        if (m == 9 )
        {
      	  mn="October";
        } 
        if (m == 10)
        {
      	  mn="November";
        }
        if (m == 11 )
        {
      	  mn="December";
        } 
       
        
        return mn;
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
	//button listener for date to call datepicker


	// display current date both on the text view and the Date Picker when the application starts.
	public void setCurrentDate() {

		text_date = (TextView) findViewById(R.id.text_date);
		

		final Calendar calendar = Calendar.getInstance();

		year = calendar.get(Calendar.YEAR);
		month = calendar.get(Calendar.MONTH);
		day = calendar.get(Calendar.DAY_OF_MONTH);

		// set current date into textview
		text_date.setText(new StringBuilder()
			// Month is 0 based, so you have to add 1
			.append(month + 1).append("-")
			.append(day).append("-")
			.append(year).append(" "));

		

	}
	
	
	
	
	public void addButtonListener() 
	{
		 
		button = (Button) findViewById(R.id.change_date_button);
 
		button.setOnClickListener(new View.OnClickListener() {

			@SuppressWarnings("deprecation")
			public void onClick(View view) 
			
			{
				showDialog(DATE_DIALOG_ID);
			}
 
		});
 
	}
	
	@Override
	protected Dialog onCreateDialog (int id){
		
		switch (id) {
		case DATE_DIALOG_ID:
			//set date icker as current date
			return new DatePickerDialog(this, datePickerListener, year, month, day);
		}
		
		return null;
	}
	
	
	private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

		// when dialog box is closed, below method will be called.
		public void onDateSet(DatePicker view, int selectedYear,int selectedMonth, int selectedDay) {
			year = selectedYear;
			month = selectedMonth;
			day = selectedDay;

			// set selected date into Text View
			text_date.setText(new StringBuilder().append(month + 1)
			   .append("-").append(day).append("-").append(year).append(" "));

			// set selected date into Date Picker
			//date_picker.init(year, month, day, null);

		}
	};


	
	
	
	
	
	
	
	
	
	
	
	
	//public void setdate( )
	
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
