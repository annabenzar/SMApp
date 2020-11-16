package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.ui.home.StorageHelper;
import com.google.android.gms.auth.api.signin.internal.Storage;

public class LogInActivity extends AppCompatActivity {

    private TextView loginText;
    private EditText emailEt,passwordEt;
    private Button loginButton, signupButton;

    public static final  String SHARED_PREFS = "sharedPrefs"; //name for sharedpreferences
    public static final String TEXT = "text";
    public static final String TEXT2 = "text2";
    private String text,text2;
    StorageHelper storageHelper = StorageHelper.getInstance(); //instantiat pt prima data

    //baza de date
    UserEntity userEntity;
    private UserDatabase userDatabase;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initializeViews();
        setOnClickListeners();
        getIntent();
        loadData();
        updateViews();
    }

    public void initializeViews(){

        loginText = findViewById(R.id.tv_login);
        emailEt = findViewById(R.id.et_login);
        passwordEt = findViewById(R.id.et_password_login);
        loginButton = findViewById(R.id.btn_first_login);
        signupButton = findViewById(R.id.btn_second_login);

        userDatabase = UserDatabase.getInstance(this);
    }

    private void setOnClickListeners()
    {

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final String emailText = emailEt.getText().toString();
                final String passwordText = passwordEt.getText().toString();
                if (emailText.isEmpty()) {
                    Toast.makeText(LogInActivity.this, "Enter your username to continue!", Toast.LENGTH_SHORT).show();
                } else if(passwordText.isEmpty()) {
                    Toast.makeText(LogInActivity.this, "Enter your password to continue!", Toast.LENGTH_SHORT).show();
                }else{
                    //trimitere catre pagina homeScreen
                    //query pe baza de date cu thread nou
                    //UserDAO userDAO = userDatabase.userDAO();
                   // loginText.append("\n" + emailText + " " + passwordText);
                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            //query pe tabela
                            testLogin(emailText,passwordText);
                        }
                    });

                }
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                toRegisterPage(view);
            }
        });
    }

    public void saveData(){
        SharedPreferences.Editor editor = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE).edit();
        editor.putString(TEXT, emailEt.getText().toString()); //ce se scrie in username edit text se pune in sahred preferences
        editor.putString(TEXT2,passwordEt.getText().toString()); //ce se scire in password se salveasa in shared
        editor.apply();
    }

    public void loadData(){
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        text = prefs.getString(TEXT,"");
        text2=prefs.getString(TEXT2,"");
    }

    public void updateViews(){
        emailEt.setText(text);
        passwordEt.setText(text2);
    }

    public void toRegisterPage(View view){
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
    }

    public void toHomePage(){
        Intent intent = new Intent(this, HomeScreen.class);
        startActivity(intent);
    }
    public void testLogin(final String email,final String password){
        class TestLogin extends AsyncTask<Void,Void, UserEntity> {

            @Override
            protected UserEntity doInBackground(Void... voids) {
                userEntity = userDatabase.userDAO().loginSearch(email,password);
                return userEntity;
            }
            @Override
            protected void onPostExecute(UserEntity testEntity) {
                super.onPostExecute(testEntity);
                if(userEntity == null){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LogInActivity.this,"Invalid creditentials!",Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    String name = userEntity.firstname;
                    saveData();
                    setValues(userEntity.name,userEntity.firstname,userEntity.email,userEntity.age,userEntity.password,userEntity.id);
                    toHomePage();
                }
            }
        }
        TestLogin testLoginTask = new TestLogin();
        testLoginTask.execute();
    }
    public void setValues(final String name, final String firstname,final String email, final int age, final String password,final int id){
        storageHelper.setProfileEntity(name,firstname,age,email,password);
    }
}
