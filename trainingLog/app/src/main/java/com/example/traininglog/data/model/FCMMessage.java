package com.example.traininglog.data.model;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FCMMessage {
    @SerializedName("club")
    private int club;
    @SerializedName("title")
    private String title;
    @SerializedName("message")
    private String message;
}
