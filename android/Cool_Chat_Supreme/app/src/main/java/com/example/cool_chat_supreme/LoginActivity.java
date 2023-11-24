package com.example.cool_chat_supreme;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;

import com.example.cool_chat_supreme.DatabaseROOM.AppDB;
import com.example.cool_chat_supreme.DatabaseROOM.AppDatabase;
import com.example.cool_chat_supreme.ViewModels.UserViewModel;
import com.example.cool_chat_supreme.entities.User;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.concurrent.CompletableFuture;

public class LoginActivity extends AppCompatActivity {
    private AppDB db;
    UserViewModel userViewModel;
    Button loginButton;
    TextView toRegister;
    private boolean passwordShowing= false;

    String token = "";
    String username = "";
    String firebaseToken = "";
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks());
        }

        // ask the user to turn on notifications
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // Create an intent to open the app's notification settings
            Intent intent1 = new Intent(android.provider.Settings.ACTION_APP_NOTIFICATION_SETTINGS);
            intent1.putExtra(android.provider.Settings.EXTRA_APP_PACKAGE, getPackageName());

            // Check if the intent can be resolved (supported on the device)
            if (intent1.resolveActivity(getPackageManager()) != null) {
                // Start the activity to open the notification settings
                startActivity(intent1);
            } else {
                // Handle the case when the intent cannot be resolved
                // Display a message or provide an alternative way for the user to enable notifications
                Toast.makeText(this, "no notifications allowed", Toast.LENGTH_SHORT).show();
            }
        }


        //set shared preference for remembering account
        SharedPreferences sharedPreferences = getSharedPreferences("MODE", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        token = sharedPreferences.getString("token", "");
        username = sharedPreferences.getString("username", "");
        firebaseToken = sharedPreferences.getString("firebaseToken" , "");
        if (!token.equals("")) {
            //aready logged in

            Intent intent = new Intent(LoginActivity.this, ChatContactsActivity.class);
            intent.putExtra("token", token);
            intent.putExtra("username", username);
            intent.putExtra("firebaseToken", firebaseToken);

            startActivity(intent);
        }
        //set nightmode if needed
        Boolean nightMode = sharedPreferences.getBoolean("night", false);
        if (nightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        NotificationManager manager = getSystemService(NotificationManager.class);
        FirebaseMessageHandler firebaseMessageHandler = new FirebaseMessageHandler();
        firebaseMessageHandler.createNotificationChannel(manager);

        ImageView settings=findViewById(R.id.settings);
        settings.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SettingsPage.class);
                startActivity(intent);
                finish();
            }
        });

//        FirebaseMessageHandler.createNotificationChannel();

        db = AppDatabase.getDB();
        String baseUrl = sharedPreferences.getString("baseUrl", "http://10.0.2.2:5000/api/");
        userViewModel = new UserViewModel(db, baseUrl);
        ImageView showPassword=findViewById(R.id.showPassword);
        loginButton= findViewById(R.id.submitButtonLogin);
        toRegister=findViewById(R.id.notRegisterd);

        EditText username = findViewById(R.id.usernameInputLogin);
        EditText password = findViewById(R.id.passwordLogin);
//        User example = new User("daniel", "123456qw", "dani", R.drawable.cat);
//        userViewModel.getToken(example);
        toRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String enteredUsername = username.getText().toString();
                String enteredPassword = password.getText().toString();
                // if the fields are not empty
                if (!enteredUsername.equals("") && !enteredPassword.equals("")) {
                    User testUser = new User(enteredUsername, enteredPassword, "", "");
                    CompletableFuture<String> tokenFuture = userViewModel.getToken(testUser);
                    // set this lambda to act as soon as the future completes
                    tokenFuture.whenComplete((responseBody, throwable) -> {
                        // if the token is not "" then the details are correct. move to chats
                        if (!responseBody.equals("")) {
                            FirebaseInstanceId.getInstance().getInstanceId()
                                    .addOnCompleteListener(task -> {
                                        if (!task.isSuccessful()) {
                                            return;
                                        }
                                        // Get the registration token
                                        String firebaseToken = task.getResult().getToken();
                                        // put in intent the details of the current user and their token
                                        Intent intent = new Intent(LoginActivity.this, ChatContactsActivity.class);
                                        intent.putExtra("token", responseBody);
                                        intent.putExtra("username", enteredUsername);

                                        intent.putExtra("firebaseToken", firebaseToken);

                                        //remember login
                                        editor.putString("token", responseBody);
                                        editor.putString("username", enteredUsername);
                                        editor.putString("firebaseToken", firebaseToken);
                                        editor.commit();

                                        startActivity(intent);
                                        // Send the token to your server or perform any additional logic
                                    });

                        } else {
                            Toast.makeText(LoginActivity.this, "incorrect login details", Toast.LENGTH_SHORT).show();
                        }
                    });
                    tokenFuture.handle((result, exception) -> {
                        if (exception == null) {
                            return null;
                        }
                        Toast.makeText(LoginActivity.this, "incorrect login details", Toast.LENGTH_SHORT).show();
                        return null;
                    });
                }
            }
        });

        showPassword.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (passwordShowing){
                    passwordShowing=false;
                    password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    showPassword.setImageResource(R.drawable.ic_hidepassword);

                }else {
                    passwordShowing=true;
                    password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    showPassword.setImageResource(R.drawable.ic_showpassword);

                }
                password.setSelection(password.length());
            }
        });
    }
}