package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Button addTaskButton = findViewById(R.id.addButton);
//        addTaskButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent toAddActivity = new Intent(MainActivity.this, AddTaskAstivity.class);
//                startActivity(toAddActivity);
//            }
//        });
//        Button allTasksButton = findViewById(R.id.allTasksButton);
//        allTasksButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent goToAllTasksActivity = new Intent(MainActivity.this, AllTasksActivity.class);
//                startActivity(goToAllTasksActivity);
//
//            }});

//        Button title1 = findViewById(R.id.title1);
//        title1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent goToTaskDetails = new Intent(MainActivity.this, TaskDetailPage.class);
//                goToTaskDetails.putExtra("title","Submit CC");
//                startActivity(goToTaskDetails);
//            }
//        });
//
//        Button title2 = findViewById(R.id.title2);
//        title2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent goToTaskDetails = new Intent(MainActivity.this, TaskDetailPage.class);
//                goToTaskDetails.putExtra("title","Submit Read");
//                startActivity(goToTaskDetails);
//            }
//        });
//
//        Button title3 = findViewById(R.id.title3);
//        title3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent goToTaskDetails = new Intent(MainActivity.this, TaskDetailPage.class);
//                goToTaskDetails.putExtra("title","Submit Lab");
//                startActivity(goToTaskDetails);
//            }
//        });

        Button settingsBtn = findViewById(R.id.settingsBtn);
        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToSettings = new Intent(MainActivity.this, SettingsPage.class);
                startActivity(goToSettings);
            }
        });

        ArrayList<Task> tasks = new ArrayList<Task>();
        tasks.add(new Task("Read Task", "description", "complete"));
        tasks.add(new Task("Lab Task", "description", "in progress"));
        tasks.add(new Task("C.C Task", "description", "assigned"));

        RecyclerView tasksRecyclerView = findViewById(R.id.recycleId);
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tasksRecyclerView.setAdapter(new TaskAdapter(tasks));
    }

    @Override
    protected void onResume() {
        super.onResume();

        String headerTitle = "User's Tasks";
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        String userName = sharedPreferences.getString("userName", "User");

        TextView headerElement = findViewById(R.id.headTitle);
        headerElement.setText(userName + "\'s Tasks");

    }
}