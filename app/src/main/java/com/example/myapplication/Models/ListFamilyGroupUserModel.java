package com.example.myapplication.Models;

public class ListFamilyGroupUserModel {

    private String nameFamilyGroupUser, firstnameFamilyGroupUSer, statusFamilyGroupUser,idFamilyGroupUser;

    public ListFamilyGroupUserModel(String nameFamilyGroupUser, String firstnameFamilyGroupUSer, String statusFamilyGroupUser, String idFamilyGroupUser) {
        this.nameFamilyGroupUser = nameFamilyGroupUser;
        this.firstnameFamilyGroupUSer = firstnameFamilyGroupUSer;
        this.statusFamilyGroupUser = statusFamilyGroupUser;
        this.idFamilyGroupUser = idFamilyGroupUser;
    }

    public String getNameFamilyGroupUser() {
        return nameFamilyGroupUser;
    }

    public void setNameFamilyGroupUser(String nameFamilyGroupUser) {
        this.nameFamilyGroupUser = nameFamilyGroupUser;
    }

    public String getFirstnameFamilyGroupUSer() {
        return firstnameFamilyGroupUSer;
    }

    public void setFirstnameFamilyGroupUSer(String firstnameFamilyGroupUSer) {
        this.firstnameFamilyGroupUSer = firstnameFamilyGroupUSer;
    }

    public String getStatusFamilyGroupUser() {
        return statusFamilyGroupUser;
    }

    public void setStatusFamilyGroupUser(String statusFamilyGroupUser) {
        this.statusFamilyGroupUser = statusFamilyGroupUser;
    }

    public String getIdFamilyGroupUser() {
        return idFamilyGroupUser;
    }

    public void setIdFamilyGroupUser(String idFamilyGroupUser) {
        this.idFamilyGroupUser = idFamilyGroupUser;
    }


}
