package com.example.myapplication.firstActivities;

public class ListExampleModel {
    private String name;
    private String firstname;

    public ListExampleModel(String name, String firstname) {
        this.name=name;
        this.firstname=firstname;
        //this.age=age;
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
