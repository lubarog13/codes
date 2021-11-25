package com.example.traininglog.data.model;

import com.google.gson.annotations.SerializedName;

public class TypesResponse {
    @SerializedName("Cardio")
    private int cardio;
    @SerializedName("Strength")
    private int strength;
    @SerializedName("For_all")
    private int for_all;
    @SerializedName("For_tech")
    private int for_tech;
    @SerializedName("Another")
    private int another;

    public TypesResponse(int cardio, int strength, int for_all, int for_tech, int another) {
        this.cardio = cardio;
        this.strength = strength;
        this.for_all = for_all;
        this.for_tech = for_tech;
        this.another = another;
    }

    public TypesResponse() {
    }

    public int getCardio() {
        return cardio;
    }

    public void setCardio(int cardio) {
        this.cardio = cardio;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getFor_all() {
        return for_all;
    }

    public void setFor_all(int for_all) {
        this.for_all = for_all;
    }

    public int getFor_tech() {
        return for_tech;
    }

    public void setFor_tech(int for_tech) {
        this.for_tech = for_tech;
    }

    public int getAnother() {
        return another;
    }

    public void setAnother(int another) {
        this.another = another;
    }
}
