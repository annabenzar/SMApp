package com.example.myapplication.Models;

public class UserEntity {

    public String name, firstname, email, age, password;

    public UserEntity(String name, String firstname, String email, String age, String password) {
        this.name = name;
        this.firstname = firstname;
        this.email = email;
        this.age = age;
        this.password = password;
    }
    public UserEntity(){

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

