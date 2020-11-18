package com.example.myapplication.ui.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.AppExecutors;
import com.example.myapplication.HomeScreen;
import com.example.myapplication.R;
import com.example.myapplication.UserDatabase;
import com.example.myapplication.UserEntity;

public class EditActivity extends AppCompatActivity {

    private TextView nametv,firstnametv,emailtv,agetv,message,passwordtv;
    private TextView editablename,editablefirstname,editableemail,editableage,editablePassword;
    private Button button;
    String name,firstname,email,age,password;
    private UserEntity userEntity;
    private UserDatabase userDatabase;
    private int id;
    StorageHelper storageHelper = StorageHelper.getInstance();

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        initializers();
        userDatabase = UserDatabase.getInstance(this);
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

            editablename.setText(storageHelper.getUserEntity().getName());
            editablefirstname.setText(storageHelper.getUserEntity().getFirstname());
            editableemail.setText(storageHelper.getUserEntity().getEmail());
            String s = String.valueOf(storageHelper.getUserEntity().getAge());
            editableage.setText(s);
            editablePassword.setText(storageHelper.getUserEntity().getPassword());
    }
    public void setOnClickListeners(){
        button.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                name = editablename.getText().toString();
                firstname = editablefirstname.getText().toString();
                email = editableemail.getText().toString();
                age = editableage.getText().toString();
                final int finalAge = Integer.parseInt(age);
                password = editablePassword.getText().toString();
                id = storageHelper.getUserEntity().getId();

                //validare campuri goale
                if (validateInput(name, firstname, email, age, password)) {
                    //inserare in baza de date
                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                                updateDatabase(name, firstname, email, finalAge,password,id); //cred ca astia
                        }
                    });
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

    private void updateDatabase(final String name, final String firstname, final String email, final int age,final String password,final int id) {
        @SuppressLint("StaticFieldLeak")
        class UpdateValue extends AsyncTask<Void, Void, UserEntity> {

            @Override
            protected UserEntity doInBackground(Void... voids) {
                UserEntity userEntity = new UserEntity(name,firstname,email,age,password);
                userDatabase.userDAO().update(name,firstname,email,age,password,id);
                return userEntity;
            }
            @Override
            protected void onPostExecute(UserEntity testEntity) {
                super.onPostExecute(testEntity);
                Toast.makeText(EditActivity.this, "Fields edited", Toast.LENGTH_SHORT).show();
                storageHelper.setUserEntity(userEntity);
                setIntent();
            }
        }
        UpdateValue updateTask = new UpdateValue();
        updateTask.execute();
    }

    public void setIntent(){
        Intent intent = new Intent(this, HomeScreen.class);
        startActivity(intent);
    }
}
