package com.example.dimon;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Task") //this is the name of the table in Parse for our data

public class Task extends ParseObject{
  public Task(){

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

  public void setDueDate(String duedate){
      put("DueDate", duedate);
  }
  
  public String getDueDate(){
      return getString("DueDate");
  }
  
  public void setwho(String who){
      put("who", who);
  }
  
  public String getwho(){
      return getString("who");
  }


  public void setDescription(String description){
      put("description", description);
  }
  
  public void setUser(ParseUser currentUser) {
		put("user", currentUser);
	}
  
  
}