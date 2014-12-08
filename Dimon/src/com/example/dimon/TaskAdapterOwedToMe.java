package com.example.dimon;

import java.util.List;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TaskAdapterOwedToMe extends ArrayAdapter<TaskOwedToMe> {
	
  private Context mContext;
  private List<TaskOwedToMe> mTasksOwedMe;

  public TaskAdapterOwedToMe(Context context, List<TaskOwedToMe> objects) {
      super(context, R.layout.task_row_item_owe_me, objects);
      this.mContext = context;
      this.mTasksOwedMe = objects;
  }

  public View getView(int position, View convertView, ViewGroup parent){
      if(convertView == null){
          LayoutInflater mLayoutInflater = LayoutInflater.from(mContext);
          convertView = mLayoutInflater.inflate(R.layout.task_row_item_owe_me, null);
      }

      TaskOwedToMe taskowedme2 = mTasksOwedMe.get(position);

      TextView descriptionView = (TextView) convertView.findViewById(R.id.task_description2);

      descriptionView.setText(taskowedme2.getDescription());

      return convertView;
  }

}