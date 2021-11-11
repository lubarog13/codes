package com.example.traininglog.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity
public class Club {
    @ColumnInfo(name = "id")
    @PrimaryKey
    @SerializedName("id")
    private int id;
    @SerializedName("identifier")
    @ColumnInfo(name = "identifier")
    private String identifier;
    @SerializedName("name")
    @ColumnInfo(name = "name")
    private String name;
    @SerializedName("group")
    @ColumnInfo(name = "group")
    private String group;
    @SerializedName("coach")
    @Ignore
    private Coach coach;
    @SerializedName("building")
    @Ignore
    private Building building;



    public Club(int id) {
        this.id = id;
    }

    public Club(int id, String identifier, String name, String group, Coach coach, Building building) {
        this.id = id;
        this.identifier = identifier;
        this.name = name;
        this.group = group;
        this.coach = coach;
        this.building = building;
    }

    public Club(int id, String name, String group, Coach coach, Building building) {
        this.id = id;
        this.name = name;
        this.group = group;
        this.coach = coach;
        this.building = building;
    }

    public Club() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Coach getCoach() {
        return coach;
    }

    public void setCoach(Coach coach) {
        this.coach = coach;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

}
