package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Models.ListRecipeModel;
import com.example.myapplication.Models.UserEntity;
import com.example.myapplication.ui.profile.UploadARecipeActivity;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import static com.example.myapplication.Helpers.FirebaseHelper.imageStorage;
import static com.example.myapplication.Helpers.FirebaseHelper.profilePicStorage;
import static com.example.myapplication.Helpers.FirebaseHelper.recipeDatabase;
import static com.example.myapplication.Helpers.FirebaseHelper.usersDatabase;

public class FirebaseRegisterActivity extends AppCompatActivity {

    private TextView registerText;
    private TextInputEditText nameEt,firstnameEt,emailEt,ageEt,passwordEt;
    private Button choosePicButton, registerButton;
    //pt autentificare
    FirebaseAuth firebaseAuth;

    private Uri filePath;
    private final int PICK_PROFILEPIC = 1111;

    // Folder path for Firebase Storage.
    String Storage_Path = "ProfilePictures/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_register);
        initializeViews();
        setOnClickListeners();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    private void initializeViews() {
        nameEt = findViewById(R.id.et_register_name);
        firstnameEt = findViewById(R.id.et_register_firstname);
        emailEt = findViewById(R.id.et_register_email);
        ageEt = findViewById(R.id.et_register_age);
        passwordEt = findViewById(R.id.et_register_password);
        choosePicButton = findViewById(R.id.btn_choose_profilePic);
        registerButton = findViewById(R.id.btn_register);
    }
    public void setOnClickListeners() {

        choosePicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               chooseProfilePic();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRegister(v);
            }
        });
    }

    private void chooseProfilePic() {
        Intent intent = new Intent(); //creates an image chooser dialog(allows user to browse through the device gallery)
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_PROFILEPIC); //receive the result (selected video)
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //PENTRU IMAGINE
        if (requestCode == PICK_PROFILEPIC && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData(); //path of the choosen file(image/video)
        }
    }

    public String GetFileExtension(Uri uri) {

        ContentResolver contentResolver = getContentResolver();

        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        // Returning the file Extension.
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }


    public void onRegister(View view) {
        //preluare input-uri
        final String name = nameEt.getText().toString();
        final String firstname = firstnameEt.getText().toString();
        final String email = emailEt.getText().toString();
        final String age = ageEt.getText().toString();
        final String password = passwordEt.getText().toString();
        //validare input-uri
        if (emailEt.getText().toString().isEmpty() || passwordEt.getText().toString().isEmpty())
        {
            Toast.makeText(this, "Please fill in email and password", Toast.LENGTH_SHORT).show();
            return;
        }
        if(password.length() < 6){
            Toast.makeText(this, "The password must be at least 6 character long!", Toast.LENGTH_SHORT).show();
            return;
        }
        //inregistrare User

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    final FirebaseUser user = firebaseAuth.getCurrentUser();
                    if (user == null){
                        return;
                    }
                    final String idUser = user.getUid();
                    if (filePath != null) {
                        //second storage ref
                        final StorageReference storageReferenceProfilePic = profilePicStorage.child(Storage_Path + System.currentTimeMillis() + "." + GetFileExtension(filePath));

                        UploadTask uploadTask = storageReferenceProfilePic.putFile(filePath);

                        uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                            @Override
                            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                if (!task.isSuccessful()) {
                                    throw task.getException();
                                }
                                return storageReferenceProfilePic.getDownloadUrl();
                            }
                        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                if (task.isSuccessful()) {
                                    Uri downloadUri = task.getResult();
                                    String mUri = downloadUri.toString();
                                    UserEntity userEntity = new UserEntity(mUri,idUser,name,firstname,email,age,password);
                                    usersDatabase.child(user.getUid()).setValue(userEntity);
                                    startActivity(new Intent(FirebaseRegisterActivity.this, FirebaseLoginActivity.class));
                                } else {
                                    Toast.makeText(FirebaseRegisterActivity.this, "Failed to download the url", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                            }
                        });
                    }
                }
            }
        });
    }
}