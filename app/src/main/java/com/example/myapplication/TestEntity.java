package com.example.myapplication;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity//(tableName="numele pe care vreau eu sa il dau tabelei") case-insensitive
public class TestEntity { //tabel in baza de date
    //contine id nume prenume - creeeasza o coloana pentru fiecare field
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "name")
    public String name;
    @ColumnInfo(name = "firstname")
    public String firstname;


    @Ignore //ignora construcotrul care seteaza name si firstname(nu apare in tabel)
    public TestEntity(int id, String name, String firstname) {
        this.id = id;
        this.name = name;
        this.firstname = firstname;
    }

    public TestEntity(String name, String firstname) {
        this.name = name;
        this.firstname = firstname;
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

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
}
