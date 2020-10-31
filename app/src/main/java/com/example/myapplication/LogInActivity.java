package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LogInActivity extends AppCompatActivity {

    private TextView loginText;
    private EditText usernameEt,passwordEt;
    private Button loginButton, signupButton;

    public static final  String SHARED_PREFS = "sharedPrefs"; //name for sharedpreferences
    public static final String TEXT = "text";
    public static final String TEXT2 = "text2";
    private String text,text2;

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
        usernameEt = findViewById(R.id.et_login);
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
                final String usernameText = usernameEt.getText().toString();
                final String passwordText = passwordEt.getText().toString();
                if (usernameText.isEmpty()) {
                    Toast.makeText(LogInActivity.this, "Enter your username to continue!", Toast.LENGTH_SHORT).show();
                } else if(passwordText.isEmpty()) {
                    Toast.makeText(LogInActivity.this, "Enter your password to continue!", Toast.LENGTH_SHORT).show();
                }else{
                    //trimitere catre pagina homeScreen
                    //query pe baza de date cu thread nou
                    //UserDAO userDAO = userDatabase.userDAO();
                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            //query pe tabela
                            userEntity = userDatabase.userDAO().login(usernameText,passwordText);
                            if(userEntity == null){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(LogInActivity.this,"Invaliid creditentials!",Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }else{
                                String name = userEntity.name;
                                saveData();
                                toHomePage(view,name);
                            }
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
        editor.putString(TEXT,usernameEt.getText().toString()); //ce se scrie in username edit text se pune in sahred preferences
        editor.putString(TEXT2,passwordEt.getText().toString()); //ce se scire in password se salveasa in shared
        editor.apply();
    }

    public void loadData(){
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        text = prefs.getString(TEXT,"");
        text2=prefs.getString(TEXT2,"");
    }

    public void updateViews(){
        usernameEt.setText(text);
        passwordEt.setText(text2);
    }

    public void toRegisterPage(View view){
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
    }

    public void toHomePage(View view,String name){
        Intent intent = new Intent(this, HomeScreen.class)
                .putExtra("name",name);
        startActivity(intent);
    }

}
