package com.example.taskmaster;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Team;
import com.amplifyframework.datastore.generated.model.Todo;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class AddTaskAstivity extends AppCompatActivity {
    List<Team> teamsList;
    String fileName;
    Uri dataUri;

    private FusedLocationProviderClient fusedLocationClient;
    private double latitude;
    private double longitude;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task_astivity);
        setTitle("Add Task");

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(AddTaskAstivity.this);
        String teamName = sharedPreferences.getString("team", "Team 1");
        teamsList = new ArrayList<>();


        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                handleSendText(intent); // Handle text being sent
            } else if (type.startsWith("image/")) {
                handleSendImage(intent); // Handle single image being sent
            }
        }

        Amplify.API.query(
                ModelQuery.list(Team.class),
                response -> {
                    for (Team t : response.getData()) {
                        teamsList.add(t);
                    }
                },
                error -> Log.e("MyAmplifyApp", "Team Query failure", error)
        );


        Team team;
        for (Team t : teamsList) {
            if (t.getName().equals(teamName)) {
                team = t;
                System.out.println(t.getName());
                TextView totalTasks = findViewById(R.id.sumOfTasks);
                Team finalTeam = team;
                runOnUiThread(() -> totalTasks.setText("Total Tasks: " + finalTeam.getTeamTasks().size()));
                return;
            }
        }
//        Toast.makeText(getApplicationContext(), "onCreate callback!", Toast.LENGTH_SHORT).show();
//        AppDB appDB = Room.databaseBuilder(getApplicationContext(), AppDB.class, "tasks").allowMainThreadQueries().build();
//        TaskDao taskDao = appDB.taskDao();
//        TextView totalTasks = findViewById(R.id.sumOfTasks);
//        totalTasks.setText("Total Tasks: " + team.getTeamTasks().size());



        Button addBtn = findViewById(R.id.addTaskButton);
        Button fileBtn = findViewById(R.id.fileBtn);
//        Team finalTeam = team;
        addBtn.setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(), "submitted!", Toast.LENGTH_LONG).show();
            EditText titleInput = findViewById(R.id.titleInput);
            String taskTitle = titleInput.getText().toString();
            EditText bodyInput = findViewById(R.id.bodyInput);
            String taskBody = bodyInput.getText().toString();
            EditText stateInput = findViewById(R.id.stateInput);
            String taskState = stateInput.getText().toString();
//            Task newTask = new Task(taskTitle, taskBody, taskState);
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
            Team team1 = null;
            for (Team t : teamsList) {
                if (t.getName().equals(teamName)) {
                    team1 = t;
                    System.out.println(t.getName());
                    break;
                }
            }
            Todo task = Todo.builder()
                    .title(taskTitle)
                    .team(team1)
                    .latitude(latitude)
                    .longitude(longitude)
                    .bode(taskBody)
                    .state(taskState)
                    .fileKey(fileName)
                    .build();

            Amplify.API.mutate(
                    ModelMutation.create(task),
                    response -> Log.i("MyAmplifyApp", "Added Todo with id: " + response.getData().getId()),
                    error -> Log.e("MyAmplifyApp", "Create failed", error)
            );

            Intent intent1 = getIntent();
            finish();
            startActivity(intent1);

        });

        fileBtn.setOnClickListener(view -> pickFile());

        Button locationBtn = findViewById(R.id.locationBtn);
        locationBtn.setOnClickListener(view -> getLastLocation());

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

    @SuppressLint("IntentReset")
    private void pickFile() {
        @SuppressLint("IntentReset") Intent selectedFile = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        selectedFile.setType(("*/*"));
        selectedFile = Intent.createChooser(selectedFile, "Select File");
        startActivityForResult(selectedFile, 1234);
    }

    void handleSendText(Intent intent) {
        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        if (sharedText != null) {
            // Update UI to reflect text being shared
            TextView bodyInput = findViewById(R.id.bodyInput);
            bodyInput.setText(sharedText);
        }
    }

    void handleSendImage(Intent intent) {
        Uri imageUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
        if (imageUri != null) {
            // Update UI to reflect image being shared
            startActivityForResult(intent.getParcelableExtra(Intent.EXTRA_STREAM), 1234);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        assert data != null;
        dataUri = data.getData();
        File file = new File(dataUri.getPath());
        fileName = file.getName();
        Log.i("0000000000",fileName);

        super.onActivityResult(requestCode, resultCode, data);
    }

    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        // check if permissions are given
        if (checkPermissions()) {
            // check if location is enabled
            if (isLocationEnabled()) {
                // getting last
                // location from
                // FusedLocationClient
                // object
                fusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<Location> task) {
                        Location location = task.getResult();
                        if (location == null) {
                            requestNewLocationData();
                        } else {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                            Log.i("getLastLocation", "onCreate: latitude => "+ latitude);
                            Log.i("getLastLocation", "onCreate: longitude => "+ longitude);
                        }
                    }
                });
            } else {
                Toast.makeText(this, "Please turn on" + " your location...", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            // if permissions aren't available,
            // request for permissions
            requestPermissions();
        }
    }



    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED
                &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED;

        // If we want background location
        // on Android 10.0 and higher,
        // use:
        // ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED
    }
    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        // Initializing LocationRequest
        // object with appropriate methods
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5);
        locationRequest.setFastestInterval(0);
        locationRequest.setNumUpdates(1);

        // setting LocationRequest
        // on FusedLocationClient
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
    }

    private final LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
        }
    };
    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, 0);
    }
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
//                                           @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
////        if (requestCode == PERMISSION_REQUEST_CODE) {// If request is cancelled, the result arrays are empty.
//            if (grantResults.length > 0 &&
//                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                // Permission is granted. Continue the action or workflow
//                // in your app
//                Log.i("PermissionsResult", "PERMISSION_GRANTED");
//            } else {
//                // Explain to the user that the feature is unavailable because
//                // the features requires a permission that the user has denied.
//                // At the same time, respect the user's decision. Don't link to
//                // system settings in an effort to convince the user to change
//                // their decision.
//            }
//            return;
////        }
//        // Other 'case' lines to check for other
//        // permissions this app might request.
//    }

}