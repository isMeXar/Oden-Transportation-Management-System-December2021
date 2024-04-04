package com.example.myapp;

public class Country {
    private int idCountry;
    private String Country;
    private String PricePerKilo;

    public Country(int idCountry, String country, String pricePerKilo) {
        this.idCountry = idCountry;
        Country = country;
        PricePerKilo = pricePerKilo;
    }

    public int getIdCountry() {
        return idCountry;
    }

    public void setIdCountry(int idCountry) {
        this.idCountry = idCountry;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getPricePerKilo() {
        return PricePerKilo;
    }

    public void setPricePerKilo(String pricePerKilo) {
        PricePerKilo = pricePerKilo;
    }
}
