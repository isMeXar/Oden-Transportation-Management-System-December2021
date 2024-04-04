package com.example.myapp;

public class Command {
    private int idCommand;
    private int idClient;
    private int idRec;
    private int idCountry;
    private String City;
    private String Address;
    private String QteCommand;
    private String Price;
    private String Status;

    public Command(int idCommand, int idClient, int idRec, int idCountry, String city, String address, String qteCommand, String price, String status) {
        this.idCommand = idCommand;
        this.idClient = idClient;
        this.idRec = idRec;
        this.idCountry = idCountry;
        City = city;
        Address = address;
        QteCommand = qteCommand;
        Price = price;
        Status = status;
    }

    public int getIdCommand() {
        return idCommand;
    }

    public void setIdCommand(int idCommand) {
        this.idCommand = idCommand;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public int getIdRec() {
        return idRec;
    }

    public void setIdRec(int idRec) {
        this.idRec = idRec;
    }

    public int getIdCountry() {
        return idCountry;
    }

    public void setIdCountry(int idCountry) {
        this.idCountry = idCountry;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getQteCommand() {
        return QteCommand;
    }

    public void setQteCommand(String qteCommand) {
        QteCommand = qteCommand;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
