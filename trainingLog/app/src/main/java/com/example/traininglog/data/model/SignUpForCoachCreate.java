package com.example.traininglog.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpForCoachCreate {
    @SerializedName("club")
    private int club;
    @SerializedName("user")
    private int user;
    @SerializedName("start_date")
    private String start_date;
    @SerializedName("end_date")
    private String end_date;
}
