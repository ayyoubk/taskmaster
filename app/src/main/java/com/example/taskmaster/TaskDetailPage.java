package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class TaskDetailPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail_page);

        Intent intent = getIntent();
        String title = intent.getExtras().getString("title");
        TextView taskTitle = findViewById(R.id.taskdetailhead);
        taskTitle.setText(title);
    }
}