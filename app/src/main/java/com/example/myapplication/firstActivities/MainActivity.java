package com.example.myapplication.firstActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;

public class MainActivity extends AppCompatActivity {
    private TextView mainTextView;
    private EditText mainEditText;
    private Button mainButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeViews();
        setOnClickListeners();
        getIntent();
    }

    private void initializeViews()
    {
        mainTextView = findViewById(R.id.tv_main_text);
        mainEditText = findViewById(R.id.et_main_input);
        mainButton = findViewById(R.id.btn_main_button);
    }

    private void setOnClickListeners()
    {

        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                String inputValue = mainEditText.getText().toString();
                if (inputValue.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Enter a text to continue", Toast.LENGTH_SHORT).show();
                    return;
                } else {

                    //dialog
                    AlertDialog.Builder a_builder = new AlertDialog.Builder(MainActivity.this);
                    a_builder.setMessage("Go further?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //va merge in activitatea urmatoare
                                    toSecondActivitiy(view);
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert = a_builder.create();
                    alert.setTitle("Dialog");
                    alert.show();
                }
            }
        });
    }

    public void toSecondActivitiy(View view) {
            Intent intent = new Intent(this, SecondActivity.class);
            startActivity(intent);

    }
}