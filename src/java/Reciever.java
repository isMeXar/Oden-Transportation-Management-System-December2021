package com.example.myapp;

public class Reciever {
    private int idRec;
    private String cinRec;
    private String nomRec;
    private String telRec;

    public Reciever(int idRec, String cinRec, String nomRec, String telRec) {
        this.idRec = idRec;
        this.cinRec = cinRec;
        this.nomRec = nomRec;
        this.telRec = telRec;
    }

    public int getIdRec() {
        return idRec;
    }

    public void setIdRec(int idRec) {
        this.idRec = idRec;
    }

    public String getCinRec() {
        return cinRec;
    }

    public void setCinRec(String cinRec) {
        this.cinRec = cinRec;
    }

    public String getNomRec() {
        return nomRec;
    }

    public void setNomRec(String nomRec) {
        this.nomRec = nomRec;
    }

    public String getTelRec() {
        return telRec;
    }

    public void setTelRec(String telRec) {
        this.telRec = telRec;
    }
}
