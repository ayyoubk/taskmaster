package com.example.taskmaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.UserStateDetails;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.pinpoint.PinpointConfiguration;
import com.amazonaws.mobileconnectors.pinpoint.PinpointManager;
import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.auth.options.AuthSignOutOptions;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.storage.s3.AWSS3StoragePlugin;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    String teamName;
    public String userName;
    Button loginBtn;
    Button logoutBtn;
    Button addTaskButton;


    // 000

    public static final String TAG = MainActivity.class.getSimpleName();

    private static PinpointManager pinpointManager;

    public static PinpointManager getPinpointManager(final Context applicationContext) {
        if (pinpointManager == null) {
            final AWSConfiguration awsConfig = new AWSConfiguration(applicationContext);
            AWSMobileClient.getInstance().initialize(applicationContext, awsConfig, new Callback<UserStateDetails>() {
                @Override
                public void onResult(UserStateDetails userStateDetails) {
                    Log.i("INIT", String.valueOf(userStateDetails.getUserState()));
                }

                @Override
                public void onError(Exception e) {
                    Log.e("INIT", "Initialization error.", e);
                }
            });

            PinpointConfiguration pinpointConfig = new PinpointConfiguration(
                    applicationContext,
                    AWSMobileClient.getInstance(),
                    awsConfig);

            pinpointManager = new PinpointManager(pinpointConfig);

            FirebaseMessaging.getInstance().getToken()
                    .addOnCompleteListener(new OnCompleteListener<String>() {
                        @Override
                        public void onComplete(@NonNull com.google.android.gms.tasks.Task<String> task) {
                            if (!task.isSuccessful()) {
                                Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                                return;
                            }
                            final String token = task.getResult();
                            Log.d(TAG, "Registering push notifications token: " + token);
                            pinpointManager.getNotificationClient().registerDeviceToken(token);
                        }
                    });
        }
        return pinpointManager;
    }

    // 000

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Task Master");
//        Toast.makeText(getApplicationContext(), "onCreate !!!", Toast.LENGTH_SHORT).show();

        loginBtn = findViewById(R.id.loginBtn);
        logoutBtn = findViewById(R.id.logoutBtn);
        addTaskButton = findViewById(R.id.addButton);

        try {
            // Add these lines to add the AWSApiPlugin plugins
            Amplify.addPlugin(new AWSApiPlugin());
            // Add this line, to include the Auth plugin.
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            // To include the S3 Storage plugin.
            Amplify.addPlugin(new AWSS3StoragePlugin());
            Amplify.configure(getApplicationContext());
            getPinpointManager(getApplicationContext());
//            AuthSignUpOptions options = AuthSignUpOptions.builder()
//                    .userAttribute(AuthUserAttributeKey.email(), "ayotv59@gmail.com")
//                    .build();
//            Amplify.Auth.signUp("ayoub", "AA123456", options,
//                    result -> Log.i("AuthQuickStart", "Result: " + result.toString()),
//                    error -> Log.e("AuthQuickStart", "Sign up failed", error)
//            );

//            Amplify.Auth.confirmSignUp(
//                    "ayoub",
//                    "933384",
//                    result -> Log.i("AuthQuickstart", result.isSignUpComplete() ? "Confirm signUp succeeded" : "Confirm sign up not complete"),
//                    error -> Log.e("AuthQuickstart", error.toString())
//            );


//            Amplify.Auth.signInWithWebUI(
//                    this,
//                    result -> Log.i("AuthQuickStart", result.toString()),
//                    error -> Log.e("AuthQuickStart", error.toString())
//            );


//            Amplify.Auth.signIn(
//                    "ayoub",
//                    "AA123456",
//                    result -> Log.i("AuthQuickstart", result.isSignInComplete() ? "Sign in succeeded" : "Sign in not complete"),
//                    error -> Log.e("AuthQuickstart", error.toString())
//            );
//
            Amplify.Auth.fetchAuthSession(
                    result -> {
                        Log.i("AmplifyQuickstart", result.toString());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // Stuff that updates the UI
                                if (result.isSignedIn()) {
                                    loginBtn.setVisibility(View.INVISIBLE);
                                    logoutBtn.setVisibility(View.VISIBLE);
                                } else {
                                    logoutBtn.setVisibility(View.INVISIBLE);
                                    loginBtn.setVisibility(View.VISIBLE);
                                    addTaskButton.setVisibility(View.INVISIBLE);
                                }
                            }
                        });
                    },
                    error -> Log.e("AmplifyQuickstart", error.toString())
            );
            Log.i("MyAmplifyApp", "Initialized Amplify");
        } catch (AmplifyException error) {
            Log.e("MyAmplifyApp", "Could not initialize Amplify", error);
        }

//        AuthSignUpOptions options = AuthSignUpOptions.builder()
//                .userAttribute(AuthUserAttributeKey.email(), "test@test.com")
//                .build();
//        Amplify.Auth.signUp("amq", "AA123456", options,
//                result -> Log.i("AuthQuickStart", "Result: " + result.toString()),
//                error -> Log.e("AuthQuickStart", "Sign up failed", error)
//        );
        System.out.println("===================================");

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Amplify.Auth.signInWithWebUI(
                        MainActivity.this,
                        result -> {
                            Log.i("AuthQuickStart", result.toString());
                            Amplify.Auth.fetchAuthSession(
                                    result2 -> {
                                        Log.i("AmplifyQuickstart", result2.toString());
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                // Stuff that updates the UI
                                                if (result2.isSignedIn()) {
                                                    loginBtn.setVisibility(View.INVISIBLE);
                                                    logoutBtn.setVisibility(View.VISIBLE);
                                                } else {
                                                    logoutBtn.setVisibility(View.INVISIBLE);
                                                    loginBtn.setVisibility(View.VISIBLE);
                                                }
                                            }
                                        });
                                        finish();
                                        startActivity(getIntent());
                                    },
                                    error -> Log.e("AmplifyQuickstart", error.toString())
                            );
                        },
                        error -> Log.e("AuthQuickStart", error.toString())
                );


            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Amplify.Auth.signOut(
                        AuthSignOutOptions.builder().globalSignOut(true).build(),
                        () -> {
                            Log.i("AuthQuickstart", "Signed out globally");
                            finish();
                            startActivity(getIntent());
                        },
                        error -> Log.e("AuthQuickstart", error.toString())
                );
            }
        });


        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toAddActivity = new Intent(MainActivity.this, AddTaskAstivity.class);

                startActivity(toAddActivity);
            }
        });
        Button allTasksButton = findViewById(R.id.allTasksButton);
        allTasksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToAllTasksActivity = new Intent(MainActivity.this, AllTasksActivity.class);
                startActivity(goToAllTasksActivity);

            }
        });


        Button settingsBtn = findViewById(R.id.settingsBtn);
        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToSettings = new Intent(MainActivity.this, SettingsPage.class);
                startActivity(goToSettings);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
//        TextView headerElement = findViewById(R.id.headTitle);
//        if (Amplify.Auth.getCurrentUser().getUsername()!=null){
//            headerElement.setText(Amplify.Auth.getCurrentUser().getUsername() + "\'s Tasks");
//        }else {
//            headerElement.setText(userName + "\'s Tasks");
//        }
//        Toast.makeText(getApplicationContext(), "OnStart !!!", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
//        Toast.makeText(getApplicationContext(), "onStop !!!", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
//        Toast.makeText(getApplicationContext(), "onRestart !!!", Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onResume() {
        super.onResume();
//        Toast.makeText(getApplicationContext(), "onResume !!!", Toast.LENGTH_SHORT).show();

        String headerTitle = "User's Tasks";
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
//        teamName = sharedPreferences.getString("userName", "User");
        String userNameFromSettings = sharedPreferences.getString("userName", "User");
//        userName =Amplify.Auth.getCurrentUser().getUsername();

        Amplify.Auth.fetchAuthSession(
                result -> {
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            // Stuff that updates the UI
                            if (result.isSignedIn()) {
                                TextView headerElement = findViewById(R.id.headTitle);
                                loginBtn.setVisibility(View.INVISIBLE);
                                logoutBtn.setVisibility(View.VISIBLE);
                                headerElement.setText(Amplify.Auth.getCurrentUser().getUsername() + "\'s Tasks");
                            } else {
                                TextView headerElement = findViewById(R.id.headTitle);
                                headerElement.setText(userNameFromSettings + "\'s Tasks");
                                logoutBtn.setVisibility(View.INVISIBLE);
                                loginBtn.setVisibility(View.VISIBLE);
                            }
                        }
                    });


                },
                error -> {
                    Log.e("AmplifyQuickstart", error.toString());
                    TextView headerElement = findViewById(R.id.headTitle);

                    headerElement.setText(userNameFromSettings + "\'s Tasks");

                }
        );
//        if (Amplify.Auth.getCurrentUser().getUsername().isEmpty()) {
//            headerElement.setText(userNameFromSettings + "\'s Tasks");
//        } else {
//            headerElement.setText(Amplify.Auth.getCurrentUser().getUsername() + "\'s Tasks");
//        }

    }
}