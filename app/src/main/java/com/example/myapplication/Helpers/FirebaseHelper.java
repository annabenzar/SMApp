package com.example.myapplication.Helpers;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseHelper {
    public static final DatabaseReference usersDatabase = FirebaseDatabase.getInstance().getReference("users");
    public static final DatabaseReference urlDatabase = FirebaseDatabase.getInstance().getReference("All_Image_Uploads_Database");
}