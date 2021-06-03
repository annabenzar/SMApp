package com.example.myapplication.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Helpers.StorageHelper;
import com.example.myapplication.HomeScreen;
import com.example.myapplication.R;
import com.example.myapplication.Models.UserEntity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;

import static com.example.myapplication.Helpers.FirebaseHelper.usersDatabase;

public class EditActivity extends AppCompatActivity {

    private TextView nametvEdit, firstnametvEdit, emailtvEdit, agetvEdit, messageEdit, passwordtvEdit;
    private EditText editablenameEdit, editablefirstnameEdit, editableemailEdit, editableageEdit, editablePasswordEdit;
    private Button buttonEdit;
    StorageHelper storageHelper = StorageHelper.getInstance();

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        initializers();
        setValues();

        setOnClickListeners();
    }

    public void initializers(){
        messageEdit = findViewById(R.id.text_edit);

        nametvEdit = findViewById(R.id.edit_name);
        editablenameEdit = findViewById(R.id.edit_yourname);

        firstnametvEdit = findViewById(R.id.edit_firstname);
        editablefirstnameEdit = findViewById(R.id.edit_yourfirstname);

        emailtvEdit = findViewById(R.id.edit_email);
        editableemailEdit = findViewById(R.id.edit_youremail);

        agetvEdit = findViewById(R.id.edit_age);
        editableageEdit = findViewById(R.id.edit_yourage);

        passwordtvEdit = findViewById(R.id.edit_password);
        editablePasswordEdit = findViewById(R.id.edit_yourpassword);

        buttonEdit = findViewById(R.id.edit_button);
    }
    public void setValues(){
        editablenameEdit.setText(storageHelper.getUserEntity().getName());
        editablefirstnameEdit.setText(storageHelper.getUserEntity().getFirstname());
        editableemailEdit.setText(storageHelper.getUserEntity().getEmail());
        editableageEdit.setText(storageHelper.getUserEntity().getAge());
        editablePasswordEdit.setText(storageHelper.getUserEntity().getPassword());
    }
    public void setOnClickListeners(){
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            String name,firstname,email,age,password;
            @Override
            public void onClick(View v) {
                name = editablenameEdit.getText().toString();
                firstname = editablefirstnameEdit.getText().toString();
                email = editableemailEdit.getText().toString();
                age = editableageEdit.getText().toString();
                password = editablePasswordEdit.getText().toString();

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
            final String userId = curentUser.getUid();
            usersDatabase.child(curentUser.getUid()).updateChildren(map);
            String mUri = StorageHelper.getInstance().getUserEntity().getProfilePicURL();
            UserEntity userEntity = new UserEntity(mUri,userId,name,firstname,email,age,password);
            StorageHelper.getInstance().setUserEntity(userEntity);
            startActivity(new Intent(EditActivity.this, HomeScreen.class));
    }
}
