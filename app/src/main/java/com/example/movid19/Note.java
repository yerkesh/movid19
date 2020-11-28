package com.example.movid19;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.ArrayList;

public class Note implements Serializable {
    private String Email;
    private String Name;
    private String Surname;
    private String Password;
    private String PhoneNumber;
    private String isAdmin;
    private String isManager;
    private String isUser;
    private String documentId;
    private String isActive;

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    @Exclude
    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public Note(){}

    public Note(String email, String name, String surname, String password, String phoneNumber, String isAdmin, String isManager, String isUser) {
        Email = email;
        Name = name;
        Surname = surname;
        Password = password;
        PhoneNumber = phoneNumber;
        this.isAdmin = isAdmin;
        this.isManager = isManager;
        this.isUser = isUser;
    }

    public String getEmail() {
        return Email;
    }

    public String getName() {
        return Name;
    }

    public String getSurname() {
        return Surname;
    }

    public String getPassword() {
        return Password;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public String getIsAdmin() {
        return isAdmin;
    }

    public String getIsManager() {
        return isManager;
    }

    public String getIsUser() {
        return isUser;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setSurname(String surname) {
        Surname = surname;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public void setIsAdmin(String isAdmin) {
        this.isAdmin = isAdmin;
    }

    public void setIsManager(String isManager) {
        this.isManager = isManager;
    }

    public void setIsUser(String isUser) {
        this.isUser = isUser;
    }
}