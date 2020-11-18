package com.example.myapplication;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.net.UnknownServiceException;

@Entity//(tableName="") case-insensitive
public class UserEntity { //tabel in baza de date
    //contine id nume prenume - creeeaza o coloana pentru fiecare field
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "name")
    public String name;
    @ColumnInfo(name = "firstname")
    public String firstname;
    @ColumnInfo(name="email")
    public String email;
    @ColumnInfo(name="age")
    public int age;
    @ColumnInfo(name="password")
    public String password;

    public UserEntity(String name, String firstname, String email, int age, String password) {
        this.name = name;
        this.firstname = firstname;
        this.email = email;
        this.age = age;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
