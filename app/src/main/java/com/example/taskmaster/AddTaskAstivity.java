package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class AddTaskAstivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task_astivity);
//        Toast.makeText(getApplicationContext(), "onCreate callback!", Toast.LENGTH_SHORT).show();
        AppDB appDB = Room.databaseBuilder(getApplicationContext(), AppDB.class, "tasks").allowMainThreadQueries().build();
        TextView totalTasks = findViewById(R.id.sumOfTasks);
        TaskDao taskDao = appDB.taskDao();
        totalTasks.setText("Total Tasks: " + taskDao.getAllTasks().size());


        Button addBtn = findViewById(R.id.addTaskButton);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "submitted!", Toast.LENGTH_LONG).show();
                EditText titleInput = findViewById(R.id.titleInput);
                String taskTitle = titleInput.getText().toString();
                EditText bodyInput = findViewById(R.id.bodyInput);
                String taskBody = bodyInput.getText().toString();
                EditText stateInput = findViewById(R.id.stateInput);
                String taskState = stateInput.getText().toString();
                Task newTask = new Task(taskTitle, taskBody, taskState);
                taskDao.insertAll(newTask);
            }
        });
    }

}