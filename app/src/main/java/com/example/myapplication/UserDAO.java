package com.example.myapplication;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.myapplication.firstActivities.TestEntity;

import java.util.List;

@Dao
public interface UserDAO {


    @Query("SELECT * FROM userentity")
    List<UserEntity> getAll();

    @Query("SELECT * FROM userentity WHERE id IN (:userIds)")
    List<UserEntity> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM userentity WHERE name LIKE :first LIMIT 1")
    UserEntity findByName(String first);

    //Cautare dupa email
    @Query("SELECT * FROM userentity where email=(:email)")
    UserEntity searchByEmail(String email);

    //cautare pentru login
    @Query("SELECT * FROM userentity where email= :email and password= :password")
    UserEntity loginSearch(String email,String password);

    @Query("UPDATE userentity SET name=:name, firstname=:firstname, email=:email,age=:age,password=:password WHERE id=:id")
    void update(String name,String firstname,String email,int age, String password,int id);

    @Insert
    void insertAll(UserEntity... users);

    @Query("DELETE FROM userentity")
    void delete();
}
