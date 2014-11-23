package com.example.dimon;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("TaskOwedToMe") //this is the name of the table in Parse for our data of things people owe the user

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
}