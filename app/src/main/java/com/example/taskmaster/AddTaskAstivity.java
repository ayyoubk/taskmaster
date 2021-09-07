package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Todo;

import java.util.List;

public class AddTaskAstivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task_astivity);
//        Toast.makeText(getApplicationContext(), "onCreate callback!", Toast.LENGTH_SHORT).show();
//        AppDB appDB = Room.databaseBuilder(getApplicationContext(), AppDB.class, "tasks").allowMainThreadQueries().build();
//        TaskDao taskDao = appDB.taskDao();
        TextView totalTasks = findViewById(R.id.sumOfTasks);
//        totalTasks.setText("Total Tasks: " + taskDao.getAllTasks().size());


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
//                taskDao.insertAll(newTask);

                Todo task = Todo.builder()
                        .title(taskTitle)
                        .bode(taskBody)
                        .state(taskState)
                        .build();

                Amplify.API.mutate(
                        ModelMutation.create(task),
                        response -> Log.i("MyAmplifyApp", "Added Todo with id: " + response.getData().getId()),
                        error -> Log.e("MyAmplifyApp", "Create failed", error)
                );

                Intent intent = getIntent();
                finish();
                startActivity(intent);

            }
        });
    }

}