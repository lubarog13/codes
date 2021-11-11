package com.example.traininglog.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity
public class Hall {
    @ColumnInfo(name = "id")
    @PrimaryKey
    @SerializedName("id")
    private int id;
    @SerializedName("building")
    @Ignore
    private Building building;
    @SerializedName("name")
    @ColumnInfo(name = "name")
    private String name;
    @SerializedName("number")
    @ColumnInfo(name = "number")
    private int number;
    @SerializedName("occupancy")
    @ColumnInfo(name = "occupancy")
    private int occupancy;

    public Hall(int id) {
        this.id = id;
    }

    public Hall(int id, Building building, String name, int number, int occupancy) {
        this.id = id;
        this.building = building;
        this.name = name;
        this.number = number;
        this.occupancy = occupancy;
    }

    public Hall() {
    }

    public Hall(int id, Building building, String name, int occupancy) {
        this.id = id;
        this.building = building;
        this.name = name;
        this.occupancy = occupancy;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getOccupancy() {
        return occupancy;
    }

    public void setOccupancy(int occupancy) {
        this.occupancy = occupancy;
    }
}
