package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.List;

public class AllTasksActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_tasks);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button homeButton = findViewById(R.id.homeButton);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToMainActivity = new Intent(AllTasksActivity.this, MainActivity.class);
                startActivity(goToMainActivity);
            }
        });

        AppDB appDB = Room.databaseBuilder(getApplicationContext(), AppDB.class, "tasks").allowMainThreadQueries().build();
        TaskDao taskDao = appDB.taskDao();
        List<Task> tasks = taskDao.getAllTasks();

        RecyclerView tasksRecyclerView = findViewById(R.id.recycleId);
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tasksRecyclerView.setAdapter(new TaskAdapter(tasks));
    }
}