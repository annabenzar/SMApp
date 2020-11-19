package com.example.myapplication.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.FirebaseLoginActivity;
import com.example.myapplication.FirebaseRegisterActivity;
import com.example.myapplication.Helpers.StorageHelper;
import com.example.myapplication.HomeScreen;
import com.example.myapplication.R;
import com.example.myapplication.UserEntity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;

import static com.example.myapplication.Helpers.FirebaseHelper.usersDatabase;

public class EditActivity extends AppCompatActivity {

    private TextView nametv,firstnametv,emailtv,agetv,message,passwordtv;
    private EditText editablename,editablefirstname,editableemail,editableage,editablePassword;
    private Button button;
    StorageHelper storageHelper = StorageHelper.getInstance();

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        initializers();
        setValues();

        setOnClickListeners();
    }

    public void initializers(){

        message = findViewById(R.id.text_home);
        nametv = findViewById(R.id.profile_name);
        editablename = findViewById(R.id.profile_yourname);
        firstnametv = findViewById(R.id.profile_firstname);
        editablefirstname = findViewById(R.id.profile_yourfirstname);
        emailtv = findViewById(R.id.profile_email);
        editableemail = findViewById(R.id.profile_youremail);
        agetv = findViewById(R.id.profile_age);
        editableage = findViewById(R.id.profile_yourage);
        passwordtv = findViewById(R.id.profile_password);
        editablePassword = findViewById(R.id.profile_yourpassword);
        button = findViewById(R.id.profile_button);
    }
    public void setValues(){
        editablename.setText(storageHelper.getUserEntity().getName());
        editablefirstname.setText(storageHelper.getUserEntity().getFirstname());
        editableemail.setText(storageHelper.getUserEntity().getEmail());
        editableage.setText(storageHelper.getUserEntity().getAge());
        editablePassword.setText(storageHelper.getUserEntity().getPassword());
    }
    public void setOnClickListeners(){
        button.setOnClickListener(new View.OnClickListener() {
            String name,firstname,email,age,password;
            @Override
            public void onClick(View v) {
                name = editablename.getText().toString();
                firstname = editablefirstname.getText().toString();
                email = editableemail.getText().toString();
                age = editableage.getText().toString();
                password = editablePassword.getText().toString();

                //validare campuri goale
                if (validateInput(name, firstname, email, age, password)) {
                    //inserare in baza de date
                    updateDatabase(name,firstname,email,age,password);
                } else {
                    Toast.makeText(EditActivity.this, "Please fill all fields!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private boolean validateInput(String name, String firstname, String email, String password, String age) {
            return !name.isEmpty() && !firstname.isEmpty()
                    && !email.isEmpty() && !password.isEmpty() && !age.isEmpty();
    }

    public void updateDatabase(final String name,final String firstname,final String email,final String age, final String password){
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            FirebaseUser curentUser = firebaseAuth.getCurrentUser();
            if (curentUser == null){
                return;
            }
            HashMap<String,Object> map = new HashMap<>();
            map.put("name",name);
            map.put("firstname",firstname);
            map.put("email",email);
            map.put("age",age);
            map.put("password",password);
            usersDatabase.child(curentUser.getUid()).updateChildren(map);
            UserEntity userEntity = new UserEntity(name,firstname,email,age,password);
            StorageHelper.getInstance().setUserEntity(userEntity);
            startActivity(new Intent(EditActivity.this, HomeScreen.class));
    }
}
