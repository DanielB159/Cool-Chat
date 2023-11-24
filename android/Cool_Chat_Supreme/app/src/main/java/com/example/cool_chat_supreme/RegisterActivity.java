package com.example.cool_chat_supreme;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cool_chat_supreme.DatabaseROOM.AppDB;
import com.example.cool_chat_supreme.DatabaseROOM.AppDatabase;
import com.example.cool_chat_supreme.ViewModels.UserViewModel;
import com.example.cool_chat_supreme.entities.User;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {


    private static final int PICK_IMAGE_REQUEST = 1;
    private boolean passwordShowing= false;
    private boolean confirmPasswordShowing= false;


    private static final String PASSWORD_PATTERN = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";
    public static boolean isPasswordValid(String password) {
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        return pattern.matcher(password).matches();
    }
    AppDB db = AppDatabase.getDB();
    UserViewModel userViewModel;
    ImageView selectedImage,showPassword,showConfirmPassword,settings,information;
    Button signUpButtom,uploadButton;
    EditText usernameInput,passwordInput,confirmPasswordInput,displayNameInput;
    TextView toLogin;
    String uploadedPic;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        SharedPreferences sharedPreferences = getSharedPreferences("MODE", Context.MODE_PRIVATE);
        String baseUrl = sharedPreferences.getString("baseUrl", "http://10.0.2.2:5000/api/");
        userViewModel = new UserViewModel(db, baseUrl);
        usernameInput=findViewById(R.id.usernameInputRegister);
        passwordInput=findViewById(R.id.passwordRegister);
        confirmPasswordInput=findViewById(R.id.confirmPassword);
        displayNameInput=findViewById(R.id.displayName);
        signUpButtom = findViewById(R.id.submitButtonRegister);
        toLogin=findViewById(R.id.allreadyRegisterd);
        selectedImage = findViewById(R.id.selectedImage);
        uploadButton = findViewById(R.id.uploadButton);
        uploadedPic = getResources().getString(R.string.profilePlaceholder);
        showPassword=findViewById(R.id.showPassword);
        showConfirmPassword=findViewById(R.id.showConfirmPassword);
        settings=findViewById(R.id.settings);
        information=findViewById(R.id.information);


        information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(RegisterActivity.this)
                        .setMessage("\"Password must be at least 8 chars and have a letter and digits")
                        .setNegativeButton(android.R.string.no, null).show();
            }
        });
        settings.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, SettingsPage.class);
                startActivity(intent);
                finish();
            }
        });


        showConfirmPassword.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if (confirmPasswordShowing){
                    confirmPasswordShowing=false;
                    confirmPasswordInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    showConfirmPassword.setImageResource(R.drawable.ic_hidepassword);

                }else {
                    confirmPasswordShowing=true;
                    confirmPasswordInput.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    showConfirmPassword.setImageResource(R.drawable.ic_showpassword);

                }
                confirmPasswordInput.setSelection(confirmPasswordInput.length());
            }
        });

        showPassword.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if (passwordShowing){
                    passwordShowing=false;
                    passwordInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    showPassword.setImageResource(R.drawable.ic_hidepassword);

                }else {
                    passwordShowing=true;
                    passwordInput.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    showPassword.setImageResource(R.drawable.ic_showpassword);

                }
                passwordInput.setSelection(passwordInput.length());
            }
        });

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
            }
        });
        toLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        signUpButtom.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String username = usernameInput.getText().toString();
                String password = passwordInput.getText().toString();
                String displayName=displayNameInput.getText().toString();
                String confirmPassword=confirmPasswordInput.getText().toString();
                if(TextUtils.isEmpty(username)||TextUtils.isEmpty(password)||TextUtils.isEmpty(displayName)
                        ||TextUtils.isEmpty(confirmPassword)){
                    Toast.makeText(getApplicationContext(), "Required Filed", Toast.LENGTH_SHORT).show();
                    return;
                }if(!isPasswordValid(password)){
                    Toast.makeText(getApplicationContext(), "Invalid password!", Toast.LENGTH_SHORT).show();
                    return;
                }if (!confirmPassword.equals(password)){
                    Toast.makeText(getApplicationContext(), "Passwords do not match.", Toast.LENGTH_SHORT).show();
                    return;
                }
                String base64 = "data:image/jpeg;base64," + uploadedPic;
                User user = new User(username, password, displayName, base64);
                CompletableFuture<String> future = new CompletableFuture<>();
                userViewModel.add(user, future);
                future.whenComplete(((response, throwable) -> {
                    if (response.equals("succeeded")) {
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else if (response.equals("User Already Exists")){
                        Toast.makeText(getApplicationContext(), "User Already Exists", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        Toast.makeText(getApplicationContext(), "Failed Creating user", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }));

            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            // Get the selected image URI
            Uri selectedImageUri = data.getData();
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImageUri));

                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                byte[] imageBytes = outputStream.toByteArray();
                outputStream.close();

                uploadedPic = Base64.encodeToString(imageBytes, Base64.DEFAULT);

                //  Toast.makeText(this, "Image uploaded successfully", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ImageView selectedImageView = findViewById(R.id.selectedImage);
            selectedImageView.setImageURI(selectedImageUri);
        }
    }
}



