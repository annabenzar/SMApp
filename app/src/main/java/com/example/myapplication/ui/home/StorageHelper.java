package com.example.myapplication.ui.home;

import com.example.myapplication.UserEntity;

public class StorageHelper {
    private static StorageHelper instance;
    private final UserEntity userEntity= new UserEntity();

    private StorageHelper() {}
    public static StorageHelper getInstance(){
        if(instance ==null){
            return new StorageHelper();
        }
        return instance;
    }
    public UserEntity getProfileEntity() {
        return userEntity;
    }
    public void setProfileEntity(String name, String firstName, int age, String email, String password) {
        userEntity.setName(name);
        userEntity.setFirstname(firstName);
        userEntity.setEmail(email);
        userEntity.setAge(age);
        userEntity.setPassword(password);
    }

}
