package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Helpers.FirebaseHelper;
import com.example.myapplication.Helpers.StorageHelper;
import com.example.myapplication.Models.UserEntity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;


public class FirebaseLoginActivity extends AppCompatActivity {

    private TextView loginText;
    private TextInputEditText emailEt;
    private TextInputEditText passwordEt;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_login);
        initializeViews();
    }


//    protected void onStart() {
//
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
//        updateUI(currentUser);
//
//    }
//    public void updateUI(FirebaseUser currentUser){
//        if(currentUser != null){
//            firebaseText.setText("There is a user signed in.");
//            email_login.setText(String.valueOf(currentUser.getEmail()));
//        }
//        else
//            firebaseText.setText("There is not a user signed in.");
//    }


    @Override
    protected void onResume() {
        super.onResume();
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        //logare automata
       /*if (user != null){
           FirebaseHelper.usersDatabase.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot snapshot) {
                   UserEntity userEntity = snapshot.getValue(UserEntity.class);
                   StorageHelper.getInstance().setUserEntity(userEntity);
                   startActivity(new Intent(FirebaseLoginActivity.this, HomeScreen.class));
               }

               @Override
               public void onCancelled(@NonNull DatabaseError error) {

               }
           });
        }*/
    }

    private void initializeViews() {

        loginText = findViewById(R.id.tv_login);
        emailEt = findViewById(R.id.et_login_email);
        passwordEt = findViewById(R.id.et_login_password);
    }

    public void goToRegister(View view) {
        startActivity(new Intent(FirebaseLoginActivity.this, FirebaseRegisterActivity.class));
    }

    public void onLogin(View view) {
        firebaseAuth = FirebaseAuth.getInstance();
        //verif. campuri goale
        if (emailEt.getText().toString().isEmpty() || passwordEt.getText().toString().isEmpty())
        {
            Toast.makeText(this, "Please fill in email and password", Toast.LENGTH_SHORT).show();
            return;
        }
        //preluare val
        String email = emailEt.getText().toString();
        String password = passwordEt.getText().toString();
        //auth
        loginUser(email,password);
    }

    public void loginUser(final String email,final String password){
        //autentificare propriu-zisa
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            FirebaseHelper.usersDatabase.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    UserEntity userEntity = snapshot.getValue(UserEntity.class);
                                    StorageHelper.getInstance().setUserEntity(userEntity);
                                    startActivity(new Intent(FirebaseLoginActivity.this, HomeScreen.class));
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                        else {
                            Toast.makeText(FirebaseLoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );}
}
