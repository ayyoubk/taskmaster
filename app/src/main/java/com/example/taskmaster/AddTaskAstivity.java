package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Team;
import com.amplifyframework.datastore.generated.model.Todo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AddTaskAstivity extends AppCompatActivity {
    List<Team> teamsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task_astivity);
        setTitle("Add Task");
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(AddTaskAstivity.this);
        String teamName = sharedPreferences.getString("team", "Team 1");
        teamsList = new ArrayList<>();


        Amplify.API.query(
                ModelQuery.list(Team.class),
                response -> {
                    for (Team t : response.getData()) {
                        teamsList.add(t);
                    }
                },
                error -> Log.e("MyAmplifyApp", "Team Query failure", error)
        );

        Team team=null;
        for (Team t : teamsList) {
            if(t.getName().equals(teamName)){
                team=t;
                System.out.println(t.getName());
                return;
            }
        }
//        Toast.makeText(getApplicationContext(), "onCreate callback!", Toast.LENGTH_SHORT).show();
//        AppDB appDB = Room.databaseBuilder(getApplicationContext(), AppDB.class, "tasks").allowMainThreadQueries().build();
//        TaskDao taskDao = appDB.taskDao();
        TextView totalTasks = findViewById(R.id.sumOfTasks);
//        totalTasks.setText("Total Tasks: " + taskDao.getAllTasks().size());


        Button addBtn = findViewById(R.id.addTaskButton);
//        Team finalTeam = team;
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
                Team team=null;
                for (Team t : teamsList) {
                    if(t.getName().equals(teamName)){
                        team=t;
                        System.out.println(t.getName());
                        break;
                    }
                }
                Todo task = Todo.builder()
                        .title(taskTitle)
                        .bode(taskBody)
                        .state(taskState)
                        .team(team)
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