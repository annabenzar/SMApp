package com.example.myapplication.Helpers;

import androidx.room.Database;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FirebaseHelper {
    public static final DatabaseReference usersDatabase = FirebaseDatabase.getInstance().getReference("users");
    public static final DatabaseReference recipeDatabase = FirebaseDatabase.getInstance().getReference("recipesData");
    public static final DatabaseReference ingredientsDatabase = FirebaseDatabase.getInstance().getReference("ingredientsTable");
    public static final DatabaseReference favoritesDatabase = FirebaseDatabase.getInstance().getReference("favoritesTable");
    public static final DatabaseReference requestsDatabase = FirebaseDatabase.getInstance().getReference("requestsTable");
    public static final StorageReference imageStorage = FirebaseStorage.getInstance().getReference();
}