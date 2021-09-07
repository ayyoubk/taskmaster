package com.example.taskmaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Todo;

import java.util.ArrayList;
import java.util.List;

public class AllTasksActivity extends AppCompatActivity {

    List<Todo> tasksList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_tasks);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerView allTasksRecyclerView = findViewById(R.id.recycleId);

        Handler handler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                allTasksRecyclerView.getAdapter().notifyDataSetChanged();
                return false;
            }
        });

        Amplify.API.query(
                ModelQuery.list(com.amplifyframework.datastore.generated.model.Todo.class),
                response -> {
                    for (Todo t : response.getData()) {
                        tasksList.add(t);
                        Log.i("MyAmplifyApp", t.getTitle());
                        Log.i("MyAmplifyApp", t.getBode());
                    }
                    handler.sendEmptyMessage(1);

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