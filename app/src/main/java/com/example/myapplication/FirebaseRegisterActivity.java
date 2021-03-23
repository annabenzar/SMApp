package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Models.UserEntity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.example.myapplication.Helpers.FirebaseHelper.usersDatabase;

public class FirebaseRegisterActivity extends AppCompatActivity {

    private TextView registerText;
    private TextInputEditText nameEt,firstnameEt,emailEt,ageEt,passwordEt;
    //pt autentificare
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_register);
        initializeViews();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    private void initializeViews() {
        nameEt = findViewById(R.id.et_register_name);
        firstnameEt = findViewById(R.id.et_register_firstname);
        emailEt = findViewById(R.id.et_register_email);
        ageEt = findViewById(R.id.et_register_age);
        passwordEt = findViewById(R.id.et_register_password);
    }

    public void onRegister(View view) {
        //preluare input-uri
        final String name = nameEt.getText().toString();
        final String firstname = firstnameEt.getText().toString();
        final String email = emailEt.getText().toString();
        final String age = ageEt.getText().toString();
        final String password = passwordEt.getText().toString();
        //validare input-uri
        if (emailEt.getText().toString().isEmpty() || passwordEt.getText().toString().isEmpty())
        {
            Toast.makeText(this, "Please fill in email and password", Toast.LENGTH_SHORT).show();
            return;
        }
        if(password.length() < 6){
            Toast.makeText(this, "The password must be at least 6 character long!", Toast.LENGTH_SHORT).show();
            return;
        }
        //inregistrare User
        registerUser(name,firstname,email,age,password);
    }



    public void registerUser(final String name, final String firstname, final String email, final String age, final String password){

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    if (user == null){
                        return;
                    }
                    final String idUser = user.getUid();
                    UserEntity userEntity = new UserEntity(idUser,name,firstname,email,age,password);
                    usersDatabase.child(user.getUid()).setValue(userEntity);
                    startActivity(new Intent(FirebaseRegisterActivity.this, FirebaseLoginActivity.class));
                }
                else {
                    Toast.makeText(FirebaseRegisterActivity.this, "Register failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}