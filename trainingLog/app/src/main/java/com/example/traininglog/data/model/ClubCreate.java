package com.example.traininglog.data.model;


import androidx.room.ColumnInfo;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClubCreate {
    @SerializedName("name")
    private String name;
    @SerializedName("group")
    private String group;
    @SerializedName("building")
    private int building;
    @SerializedName("coach")
    private int coach;
    @SerializedName("identifier")
    private String identifier;
}
