package com.example.taskmaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Todo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AllTasksActivity extends AppCompatActivity {

    List<Todo> tasksList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_tasks);
        setTitle("All Tasks");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(AllTasksActivity.this);
        String teamName = sharedPreferences.getString("team", "Team 1");
        String teamId = sharedPreferences.getString("teamId", "8aaa9141-3b6d-40dd-8568-ad7193a41f14");
//        Amplify.API.query(
//                ModelQuery.list(Team.class),
//                response -> {
//                    for (Team t : response.getData()) {
//                        teamsList.add(t);
//                    }
//                    for (Team t : teamsList) {
//                        if(t.getName().equals(teamName)){
//                            team=t;
//                            System.out.println(t.getName());
//                            return;
//                        }
//                    }
//                    tasksList =team.getTeamTasks();
////                    RecyclerView allTasksRecyclerView = findViewById(R.id.recycleId);
//                    allTasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//                    allTasksRecyclerView.setAdapter(new TaskAdapter(tasksList));
//
//                },
//                error -> Log.e("MyAmplifyApp", "Team Query failure", error)
//        );


//        Handler handler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
//            @Override
//            public boolean handleMessage(@NonNull Message msg) {
//                allTasksRecyclerView.getAdapter().notifyDataSetChanged();
//                return false;
//            }
//        });

        Amplify.API.query(
                ModelQuery.list(com.amplifyframework.datastore.generated.model.Todo.class),
                response -> {
                    for (Todo t : response.getData()) {
                        if(t.getTeam().getId().equals("ecd9eee8-b555-4b0a-b5d4-e0bfb1b0a210")){
                            tasksList.add(t);
                        }
                        Log.i("MyAmplifyApp", t.getTitle());
                        Log.i("MyAmplifyApp", teamId);
                        Log.i("MyAmplifyApp", t.getTeam().getId().getClass().toString());
                        Log.i("MyAmplifyApp", t.getTeam().getId());
                    }
//                    RecyclerView allTasksRecyclerView = findViewById(R.id.recycleId);
//                    allTasksRecyclerView.setLayoutManager(new LinearLayoutManager(AllTasksActivity.this));
//                    allTasksRecyclerView.setAdapter(new TaskAdapter(tasksList));
//                    handler.sendEmptyMessage(1);
//                    allTasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//                    allTasksRecyclerView.setAdapter(new TaskAdapter(tasksList));

//                    System.out.println("test " + tasksList.get(1).getDescription());
                },
                error -> Log.e("MyAmplifyApp", "Query failure", error)
        );

        Button homeButton = findViewById(R.id.homeButton);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToMainActivity = new Intent(AllTasksActivity.this, MainActivity.class);
                startActivity(goToMainActivity);
            }
        });



//        AppDB appDB = Room.databaseBuilder(getApplicationContext(), AppDB.class, "tasks").allowMainThreadQueries().build();
//        TaskDao taskDao = appDB.taskDao();
//        List<Task> tasks = taskDao.getAllTasks();
//
//        RecyclerView tasksRecyclerView = findViewById(R.id.recycleId);
//        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        tasksRecyclerView.setAdapter(new TaskAdapter(tasks));
    }

    protected void onStart() {
        super.onStart();
        RecyclerView allTasksRecyclerView = findViewById(R.id.recycleId);
        allTasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        allTasksRecyclerView.setAdapter(new TaskAdapter(tasksList));
    }
}