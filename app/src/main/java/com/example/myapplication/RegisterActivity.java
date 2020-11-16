package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    private TextView registerText, testTv;
    private EditText regName, regFirstname, regEmail, regAge, regPassword;
    private Button registerButton;
    private TextView room_tv;

    //var pentru preluare
    private String name, firstname, email, age, password;

    //pentru baza de date
    private List<UserEntity> userEntityList; //tabela
    private UserDatabase userDatabase; //baza de date
    private UserEntity userEntity;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initializeViews();
        userDatabase = UserDatabase.getInstance(this);
        setOnClickListeners();
        getIntent();

    }

    public void initializeViews() {

        registerText = findViewById(R.id.tv_register);
        regName = findViewById(R.id.et_name_register);
        regFirstname = findViewById(R.id.et_firstname_register);
        regEmail = findViewById(R.id.et_email_register);
        regAge = findViewById(R.id.et_age_register);
        regPassword = findViewById(R.id.et_password_register);
        registerButton = findViewById(R.id.btn_register);

        //baza de date
        userDatabase = UserDatabase.getInstance(this); //instantiere legatura
        /*AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                deleteFromDatabase();
            }
        });*/
      /*AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                getFromDatabase();
            }
        });*/
    }

    private void setOnClickListeners() {
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                //preluare din ce a fost introdus in editText-uri
                name = regName.getText().toString();
                firstname = regFirstname.getText().toString();
                email = regEmail.getText().toString();
                age = regAge.getText().toString();
                final int finalAge = Integer.parseInt(age);
                password = regPassword.getText().toString();


                //validare campuri goale
                if (validateInput(name, firstname, email, age, password)) {
                    //inserare in baza de date
                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            //cauta daca nu mai este acelasi user
                            userEntity = userDatabase.userDAO().searchByEmail(email);
                            if (userEntity != null) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(RegisterActivity.this, "User already exists!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                insertToDatabase(name, firstname, email, finalAge, password, view);

                            }
                        }
                    });
                } else {
                    Toast.makeText(RegisterActivity.this, "Please fill all fields!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validateInput(String name, String firstname, String email, String password, String age) {
        return !name.isEmpty() && !firstname.isEmpty()
                && !email.isEmpty() && !password.isEmpty() && !age.isEmpty();
    }

    public void toLogInPage(View view) {
        Intent intent = new Intent(this, LogInActivity.class);
        startActivity(intent);
    }

    private void insertToDatabase(final String name, final String firstname, final String email, final int age, final String password, final View view) {
        @SuppressLint("StaticFieldLeak")
        class InsertValue extends AsyncTask<Void, Void, UserEntity> {

            @Override
            protected UserEntity doInBackground(Void... voids) {
                UserEntity userEntity = new UserEntity();
                userEntity.setName(name);
                userEntity.setFirstname(firstname);
                userEntity.setEmail(email);
                userEntity.setAge(age);
                userEntity.setPassword(password);
                //de pus setere pentru fiecare field(fara constructor)
                userDatabase.userDAO().insertAll(userEntity);
                return userEntity;
            }

            @Override
            protected void onPostExecute(UserEntity testEntity) {
                super.onPostExecute(testEntity);
                Toast.makeText(RegisterActivity.this, "User registered", Toast.LENGTH_SHORT).show();
                toLogInPage(view);
            }
        }
        InsertValue insertTask = new InsertValue();
        insertTask.execute();
    }

    public void getFromDatabase() {
        @SuppressLint("StaticFieldLeak")
        class GetValue extends AsyncTask<Void, Void, List<UserEntity>> {
            @Override
            protected List<UserEntity> doInBackground(Void... voids) {
                userEntityList = userDatabase.userDAO().getAll(); //ca si cum am face add in second activity
                return userEntityList;
            }
            @Override
            protected void onPostExecute(List<UserEntity> testEntity) {
                super.onPostExecute(testEntity);
//                for (UserEntity model : userEntityList) {
//                    registerText.append("\n" + model.name + " " + model.firstname+" "+model.email+" "+model.age+" "+model.password);
//                }
            }
        }
        GetValue insertTask = new GetValue();
        insertTask.execute();
    }

    public void deleteFromDatabase(){
        class DeleteValue extends AsyncTask<Void,Void, UserEntity>{

            @Override
            protected UserEntity doInBackground(Void... voids) {
                userDatabase.userDAO().delete();
                return null;
            }
            @Override
            protected void onPostExecute(UserEntity testEntity) {
                super.onPostExecute(testEntity);
            }
        }
        DeleteValue deleteTask = new DeleteValue();
        deleteTask.execute();
    }
}
