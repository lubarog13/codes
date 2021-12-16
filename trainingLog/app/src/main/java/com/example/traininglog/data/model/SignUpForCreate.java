package com.example.traininglog.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

import lombok.Data;

@Data
public class SignUpForCreate {
    @SerializedName("identifier")
    private String identifier;
    @SerializedName("user")
    private int user;
    @SerializedName("start_date")
    private Date start_date;
    @SerializedName("end_date")
    private Date end_date;

    public SignUpForCreate(String identifier, int user, Date start_date, Date end_date) {
        this.identifier = identifier;
        this.user = user;
        this.start_date = start_date;
        this.end_date = end_date;
    }


    public SignUpForCreate() {
    }

}
