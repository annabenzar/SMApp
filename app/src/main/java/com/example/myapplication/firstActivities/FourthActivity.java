package com.example.myapplication.firstActivities;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FourthActivity extends AppCompatActivity {

    private TextView fileTitle, textFromFile,texttoShow;
    private EditText editText;
    private Button buttonWrite, buttonRead,buttonNext;
    private String file = "myfile";
    private String fromEditToFile;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);
        getIntent();
        initializeViews();
        setOnClickListeners();
    }

    @SuppressLint("WrongViewCast")
    private void initializeViews()
    {
        fileTitle = findViewById(R.id.tv_fourth);
        textFromFile = findViewById(R.id.tv_fourth_two);
        texttoShow =findViewById(R.id.tv_fourth_three);
        editText = findViewById(R.id.et_fourth);
        buttonWrite = findViewById(R.id.bt_fourth);
        buttonRead = findViewById(R.id.bt_fourth_two);
        buttonNext = findViewById(R.id.bt_fourth_three);
    }

    private void setOnClickListeners()
    {
        //cand apas pe write va scrie in fisierul file ales de mine
        buttonWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
               fromEditToFile = editText.getText().toString();
               try{
                   FileOutputStream fOut = openFileOutput(file,MODE_PRIVATE);
                   fOut.write(fromEditToFile.getBytes());
                   fOut.close();
                   //this nu arata catre obiectul acitivitatii ci toast este in obiectul ClickListener
                   Toast.makeText(getApplicationContext(),"Text saved succesfully to the file!",Toast.LENGTH_SHORT).show();
               }
               catch (IOException e){
                   Toast.makeText(getApplicationContext(),"Could not wirte the file!",Toast.LENGTH_SHORT).show();
                   Log.e("Exception","File write failed"+e.toString());
               }
            }
        });

        //citire din fisier
        buttonRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                String stringFromFile = "";
                try{
                    //deschide fis cu inputStream - un fel de file descriptor
                    InputStream inputStream = getApplicationContext().openFileInput(file);

                    if(inputStream != null){//inputstream returneaza daca s-a deschis sau nu
                        //instantiaza un obiect imputstreamreader pentru citire
                        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                        //instantiaza un buffer pentru citire
                        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                        //stringul in care va salva
                        String receiveString ="";
                        //instantiaza un string builder ca sa isi formateze stringul din fisier
                        StringBuilder stringBuilder = new StringBuilder();

                        while((receiveString = bufferedReader.readLine()) != null){ //cat timp citeste din fisier
                            stringBuilder.append(receiveString); //appenduie in stringbuilder ce primeste in receive
                        }
                        //inchide fisierul cu descriptorul inputstream
                        inputStream.close();
                        //abea in stringfromfile va pune ce a construit cu ajutorul bufferului, receive string si stringbuilder
                        stringFromFile = stringBuilder.toString();
                        textFromFile.setText(stringFromFile);
                    }
                }
                catch (FileNotFoundException e){
                    Log.e("reading form file","File not found: "+e.toString());
                }
                catch (IOException e){
                    Log.e("reading from file","Can not read from file: "+e.toString());
                }
            }
        });


    }
    public void toRoomActivity(View view){
        Intent intent = new Intent(this, RoomActivity.class);
        startActivity(intent);
    }
}

