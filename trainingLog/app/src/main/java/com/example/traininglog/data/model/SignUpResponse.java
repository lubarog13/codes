package com.example.traininglog.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SignUpResponse {

    @SerializedName("Sign_Ups")
    private List<SignUp> signUps;

    public List<SignUp> getSignUps() {
        return signUps;
    }

    public void setSignUps(List<SignUp> signUps) {
        this.signUps = signUps;
    }
}
