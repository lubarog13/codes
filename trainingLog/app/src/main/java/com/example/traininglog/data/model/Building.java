package com.example.traininglog.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity
public class Building {
    @ColumnInfo(name = "id")
    @PrimaryKey
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    @Ignore
    private String name;
    @SerializedName("city")
    @ColumnInfo(name = "city")
    private String city;
    @SerializedName("address")
    @ColumnInfo(name = "address")
    private String address;
    @SerializedName("number")
    @ColumnInfo(name = "number")
    private int number;
    @SerializedName("liter")
    @ColumnInfo(name = "liter")
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
