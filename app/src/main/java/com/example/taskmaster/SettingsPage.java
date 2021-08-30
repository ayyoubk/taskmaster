package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SettingsPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_page);

        Button submitUserName = findViewById(R.id.submitName);
        submitUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(SettingsPage.this);
                SharedPreferences.Editor sharedEditor = sharedPreferences.edit();
                EditText userNameField = findViewById(R.id.nameInput);
                String userName = userNameField.getText().toString();
                sharedEditor.putString("userName", userName);
                sharedEditor.apply();
                Intent goToHome = new Intent(SettingsPage.this, MainActivity.class);
                startActivity(goToHome);
            }
        });

    }
}