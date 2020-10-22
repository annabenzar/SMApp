package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class ThirdActivity extends AppCompatActivity {
    private TextView mainTextView,firstText;
    private EditText firstEditText;
    private Button mainButton;
    private String text;

    public static final  String SHARED_PREFS = "sharedPrefs"; //name for sharedpreferences
    public static final String TEXT ="text";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        initializeViews();
        getIntent();
        setOnClickListeners();
        loadData();
        updateViews();
    }

    private void initializeViews()
    {
        firstText = findViewById(R.id.tv_third_first);
        mainTextView = findViewById(R.id.tv_third);
        firstEditText = findViewById(R.id.et_name);
        mainButton = findViewById(R.id.third_button);
    }
    private void setOnClickListeners()
    {

        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                String name = firstEditText.getText().toString();
                if (name.isEmpty()) {
                    Toast.makeText(ThirdActivity.this, "Enter a text to continue", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    mainTextView.setText(firstEditText.getText().toString());
                    saveData();
                }
            }
        });
    }

    public void saveData(){
        //scriere in fis SHARED_PREFS
        SharedPreferences.Editor editor = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE).edit();
        editor.putString(TEXT,mainTextView.getText().toString());
        editor.apply();
    }
    public void loadData(){
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        text = prefs.getString(TEXT,"");
    }

    public void updateViews(){
        mainTextView.setText(text);
    }

    public void nextActivity(View view){
        Intent intent = new Intent(this, FourthActivity.class);
        startActivity(intent);
    }
}
