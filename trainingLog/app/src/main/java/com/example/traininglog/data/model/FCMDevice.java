package com.example.traininglog.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FCMDevice {
    @SerializedName("id")
    private Integer id;
    @SerializedName("name")
    private String name;
    @SerializedName("active")
    private boolean active;
    @SerializedName("date_created")
    private String date_created;
    @SerializedName("device_id")
    private String device_id;
    @SerializedName("registration_id")
    private String registration_id;
    @SerializedName("type")
    private String type;
    @SerializedName("user")
    private Integer user_id;

    @Data
    public static class DeviceResponse {
        @SerializedName("Devices")
        private List<FCMDevice> devices;
    }

    public FCMDevice(String name, boolean active, String date_created, String registration_id, String type, int user_id) {
        this.name = name;
        this.active = active;
        this.date_created = date_created;
        this.registration_id = registration_id;
        this.type = type;
        this.user_id = user_id;
    }
}
