package com.example.myapplication.Helpers;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FirebaseHelper {
    public static final DatabaseReference usersDatabase = FirebaseDatabase.getInstance().getReference("users");
    public static final DatabaseReference urlDatabase = FirebaseDatabase.getInstance().getReference("imagesUrl");
    public static final DatabaseReference exampleDatabase = FirebaseDatabase.getInstance().getReference("example");
    public static final StorageReference imageStorage = FirebaseStorage.getInstance().getReference();
}