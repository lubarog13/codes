package com.example.traininglog.data.model;

import com.google.gson.annotations.SerializedName;

public class Building {
    @SerializedName("id")
    private int id;
    @SerializedName("city")
    private String city;
    @SerializedName("address")
    private String address;
    @SerializedName("number")
    private int number;
    @SerializedName("liter")
    private String liter;

    public Building(int id) {
        this.id = id;
    }

    public Building(int id, String city, String address, int number) {
        this.id = id;
        this.city = city;
        this.address = address;
        this.number = number;
    }

    public Building(int id, String city, String address, int number, String liter) {
        this.id = id;
        this.city = city;
        this.address = address;
        this.number = number;
        this.liter = liter;
    }

    public Building() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getLiter() {
        return liter;
    }

    public void setLiter(String liter) {
        this.liter = liter;
    }
}
