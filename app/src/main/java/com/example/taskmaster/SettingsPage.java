package com.example.taskmaster;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class SettingsPage extends AppCompatActivity {
    List<Team> teamsList;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_page);
        setTitle("Settings");
        RecyclerView teamsRecyclerView = findViewById(R.id.teamsRycycler);

        CompletableFuture.supplyAsync(() -> {
            teamsList = new ArrayList<>();

//            @SuppressLint("NotifyDataSetChanged") Handler handler = new Handler(Looper.getMainLooper(), msg -> {
//                Objects.requireNonNull(teamsRecyclerView.getAdapter()).notifyDataSetChanged();
//                return false;
//            });

            Amplify.API.query(
                    ModelQuery.list(Team.class),
                    response -> {
                        for (Team t : response.getData()) {
                            teamsList.add(t);
                        }
//                        handler.sendEmptyMessage(1);
                    },
                    error -> Log.e("MyAmplifyApp", "Team Query failure", error)
            );


            return teamsList;
        }).thenAcceptAsync(theResult -> {
            teamsRecyclerView.setLayoutManager(new LinearLayoutManager(
                    SettingsPage.this
            ));
            teamsRecyclerView.setAdapter(new TeamAdapter(teamsList));

        });


//        teamsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        teamsRecyclerView.setAdapter(new TeamAdapter(teamsList));
        Button submitUserName = findViewById(R.id.submitName);
        submitUserName.setOnClickListener(view -> {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(SettingsPage.this);
            SharedPreferences.Editor sharedEditor = sharedPreferences.edit();
            EditText userNameField = findViewById(R.id.nameInput);
            String userName = userNameField.getText().toString();
            sharedEditor.putString("userName", userName);
            CheckBox checkBox = findViewById(R.id.newTeamBox);
            if (checkBox.isChecked()) {
                EditText teamNameField = findViewById(R.id.newTeamName);
                Team team = Team.builder().name(teamNameField.getText().toString()).build();
                Amplify.API.mutate(
                        ModelMutation.create(team),
                        response -> Log.i("MyAmplifyApp", "Added Team with id: " + response.getData().getId()),
                        error -> Log.e("MyAmplifyApp", "Create team failed", error)
                );
            }
//            RadioGroup radioGroup = findViewById(R.id.radioGroup);

            RadioButton radioButton1=findViewById(0);
            @SuppressLint("ResourceType") RadioButton radioButton2=findViewById(1);
            @SuppressLint("ResourceType") RadioButton radioButton3=findViewById(2);
            Team team=teamsList.get(0);
            if(radioButton1.isChecked()){
                team =teamsList.get(0);
                radioButton1.setChecked(false);
            } else if(radioButton2.isChecked()){
                team =teamsList.get(1);
                radioButton2.setChecked(false);
            }else if(radioButton3.isChecked()){
                team =teamsList.get(2);
                radioButton3.setChecked(false);
            }
//            System.out.println(team.getName());
            sharedEditor.putString("team", team.getName());
            sharedEditor.putString("teamId", team.getId());
            sharedEditor.apply();
            Intent goToHome = new Intent(SettingsPage.this, MainActivity.class);
            startActivity(goToHome);
        });
    }


}