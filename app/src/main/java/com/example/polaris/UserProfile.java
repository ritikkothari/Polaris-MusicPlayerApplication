package com.example.polaris;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;

public class UserProfile extends AppCompatActivity {

    private static final int PERMISSION_CODE = 1000;
    private static final int IMAGE_CAPTURE_CODE = 1001;
    TextInputLayout fullName, email, phoneNo, password;
    TextView fullNameLabel, userNameLabel;
    ImageView profile, capture;
    Uri image_uri;

    //Global Variables to hold user data inside this activity
    String user_name, user_email, user_phoneno, user_password;
    String user_username = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //Hiding the Status Bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_profile);
        final Intent intent = getIntent();
        user_username = intent.getStringExtra("username");

        //Hooks
        profile = findViewById(R.id.profile_image);
        capture = findViewById(R.id.capture_image);
        fullName = findViewById(R.id.full_name_profile);
        email = findViewById(R.id.email_address_profile);
        phoneNo = findViewById(R.id.phone_no_profile);
        password = findViewById(R.id.password_profile);
        fullNameLabel = findViewById(R.id.profile_full_name);
        userNameLabel = findViewById(R.id.profile_username);

        //Show all UserData
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(user_username).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                if (snapshot != null) {
                    user_username = intent.getStringExtra("username");
                    user_name = intent.getStringExtra("name");
                    user_email = intent.getStringExtra("email");
                    user_phoneno = intent.getStringExtra("phoneno");
                    user_password = intent.getStringExtra("password");

                    fullNameLabel.setText(user_name);
                    userNameLabel.setText(user_username);
                    fullName.getEditText().setText(user_name);
                    email.getEditText().setText(user_email);
                    phoneNo.getEditText().setText(user_phoneno);
                    password.getEditText().setText(user_password);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserProfile.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });

        capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED ||
                            checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                        String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permission, PERMISSION_CODE);
                    } else {
                        openCamera();
                    }
                } else {
                    openCamera();
                }
            }
        });
    }

    private void openCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera");
        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        //Camera Intent
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera();
                } else {
                    Toast.makeText(UserProfile.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            profile.setImageURI(image_uri);
        }
    }

    //Checking if the data has been updated or not
    public void update(View view) {
        if (isNameChanged() | isPasswordChanged()) {
            Toast.makeText(this, "Data has been updated", Toast.LENGTH_LONG).show();
        } else
            Toast.makeText(this, "Data is same and can not be updated", Toast.LENGTH_LONG).show();
    }

    private boolean isPasswordChanged() {
        DatabaseReference reference_update = FirebaseDatabase.getInstance().getReference("Users");
        if (reference_update == null) {
            Log.d("REF UPDATE", "NULL");
        }
        if (!user_password.equals(password.getEditText().getText().toString())) {
            reference_update.child(user_username).child("password").setValue(password.getEditText().getText().toString());
            user_password = password.getEditText().getText().toString();
            return true;
        } else {
            return false;
        }
    }

    private boolean isNameChanged() {
        DatabaseReference reference_update = FirebaseDatabase.getInstance().getReference("Users");
        if (reference_update == null) {
            Log.d("REF UPDATE", "NULL");
        }
        if (!user_name.equals(fullName.getEditText().getText().toString())) {
            reference_update.child(user_username).child("name").setValue(fullName.getEditText().getText().toString());
            user_name = fullName.getEditText().getText().toString();
            return true;
        } else {
            return false;
        }
    }
}