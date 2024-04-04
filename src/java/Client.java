package com.example.myapp;

public class Client {
    private int idClient;
    private String cinClient;
    private String nomClient;
    private String telClient;
    private String emailClient;

    public Client(int idClient, String cinClient, String nomClient, String telClient, String emailClient) {
        this.idClient = idClient;
        this.cinClient = cinClient;
        this.nomClient = nomClient;
        this.telClient = telClient;
        this.emailClient = emailClient;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public String getCinClient() {
        return cinClient;
    }

    public void setCinClient(String cinClient) {
        this.cinClient = cinClient;
    }

    public String getNomClient() {
        return nomClient;
    }

    public void setNomClient(String nomClient) {
        this.nomClient = nomClient;
    }

    public String getTelClient() {
        return telClient;
    }

    public void setTelClient(String telClient) {
        this.telClient = telClient;
    }

    public String getEmailClient() {
        return emailClient;
    }

    public void setEmailClient(String emailClient) {
        this.emailClient = emailClient;
    }
}
