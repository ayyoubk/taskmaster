package com.example.taskmaster;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.core.Amplify;
import com.bumptech.glide.Glide;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;

public class TaskDetailPage extends AppCompatActivity implements OnMapReadyCallback {
    GoogleMap googleMap;

    private double latitude;
    private double longitude;

    private FusedLocationProviderClient fusedLocationProviderClient;

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

        latitude = intent.getExtras().getDouble("latitude");
        longitude = intent.getExtras().getDouble("longitude");


        // Get the SupportMapFragment and request notification when the map is ready to be used.
        if (latitude == 0 && longitude == 0) {
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }


        Amplify.Storage.downloadFile(
                fileKey,
                new File(getApplicationContext().getFilesDir() + fileKey),
                result -> {
                    Log.i("MyAmplifyApp", "Successfully downloaded: " + result.getFile().getName());
                    ImageView image = findViewById(R.id.imageView3);
                    // https://stackoverflow.com/questions/19172154/convert-a-file-object-to-bitmap
//                    image.setImageBitmap(BitmapFactory.decodeFile(result.getFile().getPath()));
                    //https://github.com/bumptech/glide
                    Glide.with(TaskDetailPage.this).load(result.getFile().getPath()).centerCrop().into(image);
                },
                error -> Log.e("MyAmplifyApp", "Download Failure", error)
        );

    }


    @SuppressLint("MissingPermission")

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Add a marker in Sydney, Australia,
        // and move the map's camera to the same location.
        LatLng location = new LatLng(latitude, longitude);
        googleMap.addMarker(new MarkerOptions()
                .position(location)
                .title("Location"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(location));
    }


}

