package com.example.myapplication.ui.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.myapplication.Models.ListPhotoModel;
import com.example.myapplication.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

import static com.example.myapplication.Helpers.FirebaseHelper.recipeDatabase;
import static com.example.myapplication.Helpers.FirebaseHelper.imageStorage;


public class PhotoGalleryActivity extends AppCompatActivity {

    private TextView textView;
    private ImageView imageView;
    private EditText nameUpload,timeUpload,typeUpload, ingredientsUpload,preparationUpload;
    private Button btnChoose, btnUpload, btnShow;



    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 71;

    // Folder path for Firebase Storage.
    String Storage_Path = "Images/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_gallery);
        initializeViews();
        setOnClickListeners();
    }

    public void initializeViews() {
        textView = findViewById(R.id.text_view_upload);
        imageView = findViewById(R.id.img_View);

        nameUpload=findViewById(R.id.name_et_upload);
        timeUpload=findViewById(R.id.time_et_upload);
        typeUpload=findViewById(R.id.type_et_upload);
        ingredientsUpload=findViewById(R.id.ingredients_et_upload);
        preparationUpload=findViewById(R.id.preparation_et_upload);


        btnChoose = findViewById(R.id.btn_photo_choose);
        btnUpload = findViewById(R.id.btn_photo_upload);

        //btnShow = findViewById(R.id.btn_photo_show);
    }

    public void setOnClickListeners() {
        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });

        /*btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PhotoGalleryActivity.this, ShowGalleryActivity.class));
            }
        });*/
    }

    private void chooseImage() {
        Intent intent = new Intent(); //creates an image chooser dialog(allows user to browse through the device gallery)
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST); //receive the result (selected image)
    }
    //for diplaying the image _ getting image path ->  onActivityResult

    /* onActivityResult receives a request code, result code, and the data. In this method, you will check to see if the request code equals PICK_IMAGE_REQUEST, with the result code equal to RESULT_OK and the data available. If all this is true, you want to display the selected image in the ImageView.*/

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null)
        //displaying the image
        {
            filePath = data.getData(); //path of the choosen image
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Creating Method to get the selected image file Extension from File Path URI.
    public String GetFileExtension(Uri uri) {

        ContentResolver contentResolver = getContentResolver();

        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        // Returning the file Extension.
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    //uploading to firebase

    private void uploadImage() {

        final String name= nameUpload.getText().toString();
        final String time = timeUpload.getText().toString();
        final String type = typeUpload.getText().toString();
        final String ingredients = ingredientsUpload.getText().toString();
        final String prep = preparationUpload.getText().toString();

        if (nameUpload.getText().toString().isEmpty() || timeUpload.getText().toString().isEmpty() || typeUpload.getText().toString().isEmpty())
        {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (filePath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            //second storage ref
            final StorageReference storageReference2 = imageStorage.child(Storage_Path + System.currentTimeMillis() + "." + GetFileExtension(filePath));
            UploadTask uploadTask = storageReference2.putFile(filePath);

            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return storageReference2.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        Uri downloadUri = task.getResult();
                        String mUri = downloadUri.toString();
                        ListPhotoModel listPhotoModel = new ListPhotoModel(mUri,name,time,type,ingredients,prep);
                        String ImageUploadId = recipeDatabase.push().getKey();
                        recipeDatabase.child(ImageUploadId).setValue(listPhotoModel);
                    } else {
                        Toast.makeText(PhotoGalleryActivity.this, "Failed upload", Toast.LENGTH_SHORT).show();
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