package com.example.dimon;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Task2") //this is the name of the table in Parse for our data of things people owe the user

public class TaskOwedToMe extends ParseObject{
  
public TaskOwedToMe(){

  }

  public boolean isCompleted(){
      return getBoolean("completed");
  }

  public void setCompleted(boolean complete){
      put("completed", complete);
  }

  public String getDescription(){
      return getString("description");
  }

  public void setDescription(String description){
      put("description", description);
  }
  public void setUser(ParseUser currentUser) {
		put("user", currentUser);
	}
}