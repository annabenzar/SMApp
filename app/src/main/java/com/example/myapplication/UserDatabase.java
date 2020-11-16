package com.example.myapplication;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {UserEntity.class}, version = 2,exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {
    private static final String DB_NAME="USER";
    private static UserDatabase instance;

    //?
    public static synchronized UserDatabase getInstance(Context context)
    {
        if (instance == null) //dc referinta nu are alocata nicio conexiune la baza de date
        {
            //face conexiunea cu databaseBuilder
            instance = Room.databaseBuilder(context, UserDatabase.class, DB_NAME)
                    .fallbackToDestructiveMigration() //distructevly recreate the tables in database app
                    /* Warning: Setting this option in your app's database builder
                    means that Room permanently deletes all data from the tables in your database when it attempts
                    to perform a migration with no defined migration path. */
                    // example : migrating from a higher database version to a lower one
                    .build();
        }
        return instance; //retruneaza clasa adnotata cu @DAO
    }

    public abstract UserDAO userDAO(); //met abstr cu 0 parametrii
}
