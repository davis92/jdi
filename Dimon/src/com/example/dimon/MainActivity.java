package com.example.dimon;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQuery.CachePolicy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity implements OnItemClickListener {

	private EditText mTaskInput; //this describes what is in the textbox field
	private ListView mListView;// this is the view that is under textbox
	private TaskAdapter mAdapter;//this is used to covert EditText into ListView
	Button button;
	Intent intent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		addListenerOnButton();
		
		Parse.initialize(this, "qVN3MRKBp42CrVX7AwN8sriRt7gwHtyOqAysl90R", "51k427k2YqLC5sWOAK5oKeUYTAydABJgc9UdtoDc");
		ParseObject.registerSubclass(Task.class);
		mTaskInput = (EditText) findViewById(R.id.task_input);
		mListView = (ListView) findViewById(R.id.task_list);
		
		mAdapter = new TaskAdapter(this, new ArrayList<Task>());
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(this);
		
		updateData();
		
	}
	
	public void addListenerOnButton() {
		 
		final Context context = this;
 
		button = (Button) findViewById(R.id.button1);
 
		button.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) 
			
			{
			    intent = new Intent(MainActivity.this, Test.class);
                startActivity(intent);   
 
			}
 
		});
 
	}
	
	//got these from one of the Parse tutorial
	
	public void updateData(){
		  ParseQuery<Task> query = ParseQuery.getQuery(Task.class);
		  query.setCachePolicy(CachePolicy.CACHE_THEN_NETWORK);
		  query.findInBackground(new FindCallback<Task>() {
		          
		      @Override
		      public void done(List<Task> tasks, ParseException error) {
		          if(tasks != null){
		              mAdapter.clear();
		              mAdapter.addAll(tasks);
		          }
		      }
		  });
		}
	
	//this was gotten from google
	
	public void createTask(View v) 
	{
	      if (mTaskInput.getText().length() >= 1){
	          Task t = new Task();
	          t.setDescription(mTaskInput.getText().toString());
	          t.setCompleted(false);
	          t.saveEventually();
	          mAdapter.insert(t, 0);
	          mTaskInput.setText("");
	      }
	  }

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	  Task task = mAdapter.getItem(position);
	  TextView taskDescription = (TextView) view.findViewById(R.id.task_description);

	  task.setCompleted(!task.isCompleted());

	  if(task.isCompleted()){ //@Stefan - this is one of two parts that strikes out the text. You might be able to make it delete
	      taskDescription.setPaintFlags(taskDescription.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
	  }else{
	      taskDescription.setPaintFlags(taskDescription.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
	  }

	  task.saveEventually();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
