package com.example.myapplication.Models;

public class UserEntity {

    public String id, name, firstname, email, age, password;

    public UserEntity(String id,String name, String firstname, String email, String age, String password) {
        this.id = id;
        this.name = name;
        this.firstname = firstname;
        this.email = email;
        this.age = age;
        this.password = password;
    }
    public UserEntity(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getEmail() {
        return email;
    }

    public String getAge() {
        return age;
    }

    public String getPassword() {
        return password;
    }
}

