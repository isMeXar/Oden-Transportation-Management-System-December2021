package com.example.myapp;

public class Admin {
    private int idAdmin;
    private String fullName;
    private String aName;
    private String aPassword;
    private String aEmail;
    private String imageUrl;

    public Admin(int idAdmin, String fullName, String aName, String aPassword, String aEmail, String imageUrl) {
        this.idAdmin = idAdmin;
        this.fullName = fullName;
        this.aName = aName;
        this.aPassword = aPassword;
        this.aEmail = aEmail;
        this.imageUrl = imageUrl;
    }

    public int getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(int idAdmin) {
        this.idAdmin = idAdmin;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getaName() {
        return aName;
    }

    public void setaName(String aName) {
        this.aName = aName;
    }

    public String getaPassword() {
        return aPassword;
    }

    public void setaPassword(String aPassword) {
        this.aPassword = aPassword;
    }

    public String getaEmail() {
        return aEmail;
    }

    public void setaEmail(String aEmail) {
        this.aEmail = aEmail;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
