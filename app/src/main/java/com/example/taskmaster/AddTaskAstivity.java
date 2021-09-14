package com.example.taskmaster;

import androidx.annotation.CallSuper;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AddTaskAstivity extends AppCompatActivity {
    List<Team> teamsList;
    String fileName;
    Uri dataUri;

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


        Team team = null;
        for (Team t : teamsList) {
            if (t.getName().equals(teamName)) {
                team = t;
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
        Button fileBtn = findViewById(R.id.fileBtn);
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

                try {
                    if (dataUri!=null){
                        InputStream exampleInputStream = getContentResolver().openInputStream(dataUri);
                        Amplify.Storage.uploadInputStream(
                                fileName,
                                exampleInputStream,
                                result -> Log.i("MyAmplifyApp", "Successfully uploaded: " + result.getKey()),
                                storageFailure -> Log.e("MyAmplifyApp", "Upload failed", storageFailure)
                        );
                    }

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                Team team = null;
                for (Team t : teamsList) {
                    if (t.getName().equals(teamName)) {
                        team = t;
                        System.out.println(t.getName());
                        break;
                    }
                }
                Todo task = Todo.builder()
                        .title(taskTitle)
                        .bode(taskBody)
                        .state(taskState)
                        .fileKey(fileName)
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

        fileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickFile();

            }
        });

    }

    private void uploadFile() {
        File exampleFile = new File(getApplicationContext().getFilesDir(), "ExampleKey");

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(exampleFile));
            writer.append("Example file contents");
            writer.close();
        } catch (Exception exception) {
            Log.e("MyAmplifyApp", "Upload failed", exception);
        }

        Amplify.Storage.uploadFile(
                "ExampleKey",
                exampleFile,
                result -> Log.i("MyAmplifyApp", "Successfully uploaded: " + result.getKey()),
                storageFailure -> Log.e("MyAmplifyApp", "Upload failed", storageFailure)
        );
    }

    private void pickFile() {
        Intent selctedFile = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        selctedFile.setType(("*/*"));
        selctedFile = Intent.createChooser(selctedFile, "Select File");
        startActivityForResult(selctedFile, 1234);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        dataUri = data.getData();
        File file = new File(dataUri.getPath());
        fileName = file.getName();


        super.onActivityResult(requestCode, resultCode, data);
    }
}