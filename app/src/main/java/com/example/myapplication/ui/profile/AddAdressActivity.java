package com.example.myapplication.ui.profile;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class AddAdressActivity extends AppCompatActivity {
    EditText name, addres, city, postalCode, phoneNumber;
    Button addAddressButton;
    FirebaseFirestore firestore;
    FirebaseAuth auth;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        setContentView(R.layout.activity_add_adress);

        name = findViewById(R.id.ad_name);
        addres = findViewById(R.id.ad_address);
        city = findViewById(R.id.ad_city);
        postalCode = findViewById(R.id.ad_code);
        phoneNumber = findViewById(R.id.ad_phone);
        addAddressButton =findViewById(R.id.ad_add_address);
        setupListeners();
    }

    public void setupListeners() {
        addAddressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = name.getText().toString();
                String userCity = city.getText().toString();
                String userAddress = addres.getText().toString();
                String userCode = postalCode.getText().toString();
                String userNumber = phoneNumber.getText().toString();

                String final_address = "";

                if(!userName.isEmpty()){
                    final_address+=userName;
                    final_address+=" ";
                }
                if(!userCity.isEmpty()){
                    final_address+=userCity;
                    final_address+=" ";
                }
                if(!userAddress.isEmpty()){
                    final_address+=userAddress;
                    final_address+=" ";
                }
                if(!userCode.isEmpty()){
                    final_address+=userCode;
                    final_address+=" ";
                }
                if(!userNumber.isEmpty()){
                    final_address+=userNumber;
                    final_address+=" ";
                }

                if(!userName.isEmpty() && !userCity.isEmpty() && !userAddress.isEmpty() && !userCode.isEmpty() && !userNumber.isEmpty()){
                    Map<String,String> map = new HashMap<>();
                    map.put("userAddress",final_address);
                    firestore.collection("CurrentUserAddress").document(auth.getCurrentUser().getUid())
                            .collection("Address").add(map).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    Toast.makeText(getApplicationContext(), "Address added", Toast.LENGTH_SHORT).show();
                                }
                            });
                }else{
                    Toast.makeText(getApplicationContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

}
