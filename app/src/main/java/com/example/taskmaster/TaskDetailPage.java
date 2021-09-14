package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.amplifyframework.core.Amplify;

import java.io.File;

public class TaskDetailPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail_page);
        setTitle("More About Task");
        Intent intent = getIntent();
        String title = intent.getExtras().getString("title");
        TextView taskTitle = findViewById(R.id.taskdetailhead);
        taskTitle.setText(title);

        String body = intent.getExtras().getString("body");
        TextView taskBody = findViewById(R.id.taskBodyId);
        taskBody.setText(body);

        String state = intent.getExtras().getString("state");
        TextView taskState = findViewById(R.id.StateId);
        taskState.setText(state);

        String fileKey = intent.getExtras().getString("fileKey");

//        Amplify.Storage.getUrl(
//                fileKey,
//                result -> {
//                    Log.i("MyAmplifyApp", "Successfully generated: " + result.getUrl());
//                    ImageView image = findViewById(R.id.imageView3);
////                    image.setImageURI(Uri.parse(result.getUrl().getPath()));
//                    image.set;
//                },
//                error -> Log.e("MyAmplifyApp", "URL generation failure", error)
//        );
//        Amplify.Storage.downloadFile(
//                fileKey,
//                new File(getApplicationContext().getFilesDir() + fileKey),
//                result -> {
//                    Log.i("MyAmplifyApp", "Successfully downloaded: " + result.getFile().getName());
//                    ImageView image = findViewById(R.id.imageView3);
//                    Uri x = result.getFile().toURI();
//                    image.;
//                },
//                error -> Log.e("MyAmplifyApp",  "Download Failure", error)
//        );

    }
}