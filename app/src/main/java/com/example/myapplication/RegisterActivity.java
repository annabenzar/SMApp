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
import androidx.recyclerview.widget.LinearLayoutManager;

public class RegisterActivity extends AppCompatActivity {

    private TextView registerText,testTv;
    private EditText registerUsername,registerPassword;
    private Button registerButton;

    //var pentru preluare
    private String name,password;

    //pentru baza de date
    UserEntity userEntity; //tabela
    private UserDatabase userDatabase; //baza de date

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initializeViews();
        setOnClickListeners();
        getIntent();
    }

    public void initializeViews(){

        registerText = findViewById(R.id.tv_register);
        registerUsername = findViewById(R.id.et_username_register);
        registerPassword = findViewById(R.id.et_password_register);
        registerButton = findViewById(R.id.btn_register);

        //baza de date
        userDatabase = UserDatabase.getInstance(this); //instantiere legatura
    }

    private void setOnClickListeners()
    {
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view){

                //preluare din ce a fost introdus in editText-uri
                name = registerUsername.getText().toString();
                password = registerPassword.getText().toString();

                //validare campuri goale
                if(validateInput(name,password)){
                    //inserare in baza de date
                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            //cauta daca nu mai este acelasi user
                            userEntity = userDatabase.userDAO().register(name);
                            if(userEntity != null){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(RegisterActivity.this,"Username already exists!",Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }else {
                                insertToDatabase(name,password);
                                Toast.makeText(RegisterActivity.this,"User registered",Toast.LENGTH_SHORT).show();
                                toLogInPage(view);
                            }
                        }
                    });
                }else{
                    Toast.makeText(RegisterActivity.this,"Please fill all fields!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validateInput(String name,String password){
        return !name.isEmpty() &&
                !password.isEmpty();
    }

    public void toLogInPage(View view){
        Intent intent = new Intent(this, LogInActivity.class);
        startActivity(intent);
    }

    private void insertToDatabase(final String name,final String password)
    {
        @SuppressLint("StaticFieldLeak")
        class InsertValue extends AsyncTask<Void, Void, UserEntity> {

            @Override
            protected UserEntity doInBackground(Void... voids) {
                userEntity = new UserEntity(name,password); //instantiem tabela cu ce am primit in edit text
                userDatabase.userDAO().registerUser(userEntity);
                return userEntity;
            }
            @Override
            protected void onPostExecute(UserEntity testEntity) {
                super.onPostExecute(testEntity);
            }
        }
        InsertValue insertTask = new InsertValue();
        insertTask.execute();
    }
}
