package com.example.traininglog.data.model;

import com.google.gson.annotations.SerializedName;

public class Club {
    @SerializedName("id")
    private int id;
    @SerializedName("identifier")
    private String identifier;
    @SerializedName("name")
    private String name;
    @SerializedName("group")
    private String group;
    @SerializedName("coach")
    private Coach coach;
    @SerializedName("building")
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
