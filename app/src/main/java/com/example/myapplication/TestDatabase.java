package com.example.myapplication;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

//conexiunea la baza de date

//punctul principal de acces pentru conexiunea cu datele salvate local
@Database(entities = {TestEntity.class}, version = 1, exportSchema = false) //lista entitatiilor din tabel
public abstract class TestDatabase extends RoomDatabase {
    private static final String DB_NAME="TEST_DB";
    private static TestDatabase instance;

    //?
    public static synchronized TestDatabase getInstance(Context context)
    {
        if (instance == null) //dc referinta nu are alocata nicio conexiune la baza de date
        {
            //face conexiunea cu databaseBuilder
            instance = Room.databaseBuilder(context, TestDatabase.class, DB_NAME)
                    .fallbackToDestructiveMigration() //distructevly recreate the tables in database app
                    /* Warning: Setting this option in your app's database builder
                    means that Room permanently deletes all data from the tables in your database when it attempts
                    to perform a migration with no defined migration path. */
                    // example : migrating from a higher database version to a lower one
                    .build();
        }
        return instance; //retruneaza clasa adnotata cu @DAO
    }

    public abstract TestDAO testDAO(); //met abstr cu 0 parametrii
}
