package com.example.myapplication;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface UserDAO {

    //pentru inregistrare
    @Insert
    void registerUser(UserEntity userEntity);

    //pentru register
    @Query("SELECT * from userentity where name=(:username)")
    UserEntity register(String username);

    //pentru login
    @Query("SELECT * from userentity where name=(:username) and password=(:password)")
    UserEntity login(String username,String password);
}
