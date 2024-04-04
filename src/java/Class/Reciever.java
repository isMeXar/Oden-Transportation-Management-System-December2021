package com.example.myapp.Class;

public class Reciever {
    private int idRec;
    private String cinRec;
    private String nomRec;
    private String telRec;
    public Reciever(int idRec,String cinRec,String nomRec,String telRec){
        this.idRec= idRec ;
        this.cinRec= cinRec ;
        this.nomRec= nomRec ;
        this.telRec= telRec ;
    }
    public int getId(){return idRec;};
    public String getCin(){return cinRec;}
    public String getName(){return nomRec;}
    public String getPhone(){return telRec;}
}
